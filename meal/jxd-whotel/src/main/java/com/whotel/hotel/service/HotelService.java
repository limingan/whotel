package com.whotel.hotel.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whotel.meal.controller.req.ListHotelReq;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.hotel.dao.HotelDao;
import com.whotel.hotel.entity.Hotel;
import com.whotel.thirdparty.jxd.api.JXDHotelInfoService;
import com.whotel.thirdparty.jxd.api.JXDPmsService;
import com.whotel.thirdparty.jxd.mode.HotelBranchQuery;
import com.whotel.thirdparty.jxd.mode.HotelCityQuery;
import com.whotel.thirdparty.jxd.mode.HotelServiceQuery;
import com.whotel.thirdparty.jxd.mode.InterfaceListQuery;
import com.whotel.thirdparty.jxd.mode.vo.HotelBranchVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelCityVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelServiceVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelVO;
import com.whotel.thirdparty.jxd.mode.vo.InterfaceListVO;

/**
 * 酒店服务类
 * @author 冯勇
 *
 */
@Service
public class HotelService {
	
	private static final Logger logger = Logger.getLogger(HotelService.class);
	
	@Autowired
	private HotelDao hotelDao;
	
	@Autowired
	private InterfaceConfigService interfaceConfigService;
	
	
	/**
	 * 加载可预订酒店列表
	 * @param companyId
	 * @param query
	 * @return
	 */
	public List<HotelBranchVO> listHotelBranchVO(String companyId, HotelBranchQuery query) {
		
		List<HotelBranchVO> hotelBranchs = null;
		
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		
		if(interfaceConfig != null) {
			try {
				if(StringUtils.equals(interfaceConfig.getChannel(), "pms")){
					JXDPmsService pmsService = new JXDPmsService();
					hotelBranchs = pmsService.loadHotelBranchs(query,interfaceConfig.getCompany().getCode(),interfaceConfig.getSign(),interfaceConfig.getHost());
				}else{//E云通
					JXDHotelInfoService hotelInfoService = new JXDHotelInfoService();
					hotelBranchs = hotelInfoService.loadHotelBranchs(query,interfaceConfig.getHost());
				}
			} catch (Exception e) {
				logger.error("可预订酒店列表接口获取数据出错！");
				e.printStackTrace();
			}
		}
		return hotelBranchs;
	}	
	
	/**
	 * 同步酒店
	 * @param companyId
	 * @return
	 */
	public void synchronizeHotel(String companyId){
		List<HotelBranchVO> hotelBranchs = listHotelBranchVO(companyId, new HotelBranchQuery());
		if(hotelBranchs != null){
			for (HotelBranchVO vo : hotelBranchs) {
				Hotel hotel = getHotel(companyId, vo.getCode());
				if(hotel == null){
					hotel = new Hotel();
					hotel.setCreateTime(new Date());
					hotel.setCode(vo.getCode());
					hotel.setCompanyId(companyId);
				}
				hotel.setName(vo.getCname());
				hotel.setAddress(vo.getAddress());
				hotel.setCity(vo.getCity());
				hotel.setTel(vo.getTel());
				hotelDao.save(hotel);
			}
		}
	}
	
	public List<HotelVO> listHotelVO(String companyId) {
		
		List<HotelVO> loadHotels = null;
		
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		if(interfaceConfig != null) {
			JXDHotelInfoService hotelInfoService = new JXDHotelInfoService();
			try {
				loadHotels = hotelInfoService.loadHotels(interfaceConfig.getHost());
			} catch (Exception e) {
				logger.error("酒店列表接口获取数据出错！");
				e.printStackTrace();
			}
		}
		return loadHotels;
	}
	
	public HotelVO getHotelVO(String companyId, String code) {
		List<HotelVO> hotels = listHotelVO(companyId);
		if(hotels != null) {
			for(HotelVO hotel:hotels) {
				if(StringUtils.equals(hotel.getHotelCode(), code)) {
					return hotel;
				}
			}
		}
		return null;
	}
	
	public HotelBranchVO getHotelBranchVO(String companyId, String code) {
		List<HotelBranchVO> hotelBranchs = listHotelBranchVO(companyId, new HotelBranchQuery());
		if(hotelBranchs != null) {
			for(HotelBranchVO hotel:hotelBranchs) {
				if(StringUtils.equals(hotel.getCode(), code)) {
					return hotel;
				}
			}
		}
		return null;
	}
	
	/**
	 * 加载酒店增值服务
	 * @param companyId
	 * @param query
	 * @return
	 */
	public List<HotelServiceVO> listHotelService(String companyId, HotelServiceQuery query) {
		
		List<HotelServiceVO> hotelServiceVOs = null;
		
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		
		if(interfaceConfig != null) {
			
			try {
				if(StringUtils.equals(interfaceConfig.getChannel(), "pms")){
					hotelServiceVOs = new ArrayList<>();
				}else{//E云通
					JXDHotelInfoService hotelInfoService = new JXDHotelInfoService();
					hotelServiceVOs = hotelInfoService.loadHotelServices(query,interfaceConfig.getHost());
				}
			} catch (Exception e) {
				logger.error("酒店客房列表接口获取数据出错！");
				e.printStackTrace();
			}
			
		}
		return hotelServiceVOs;
	}
	
	public Hotel getHotel(String companyId, String code) {
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("code", code);
		properties.put("companyId", companyId);
		return hotelDao.getByProperties(properties);
	}
	
	public void saveHotel(Hotel hotel) {
		if(hotel != null) {
			String id = hotel.getId();
			if(StringUtils.isBlank(id)) {
				hotel.setCreateTime(new Date());
			} else {
				Hotel updateHotel = getHotelById(id);
				hotel.setCreateTime(updateHotel.getCreateTime());
				hotel.setUpdateTime(new Date());
			}
			hotelDao.save(hotel);
		}
	}
	
	public void deleteHotel(String id) {
		if(StringUtils.isNotBlank(id)) {
			Hotel hotel = getHotelById(id);
			if(hotel != null) {
				hotelDao.delete(hotel);
			}
		}
	}

	public void deleteHotel(Hotel hotel) {
		if(hotel != null) {
			hotelDao.delete(hotel);
		}
	}
	
	public void deleteMoreHotel(String[] ids) {
		if(ids != null) {
			for(String id:ids) {
				deleteHotel(id);
			}
		}
	}
	
	public List<Hotel> findAllHotels(String companyId) {
		return hotelDao.findByProperty("companyId", companyId,Order.asc("code"));
	}
	
	public Map<String,String> getHotelTelMap(String companyId){
		List<Hotel> hotels = findAllHotels(companyId);
		Map<String,String> map = new HashMap<>();
		for (Hotel hotel : hotels) {
			map.put(hotel.getCode(), hotel.getTicketTel());
		}
		return map;
	}

	public Page<Hotel> findHotels(Page<Hotel> page) {
		return hotelDao.find(page);
	}

	public Hotel getHotelById(String id) {
		if(StringUtils.isNotBlank(id)) {
			return hotelDao.get(id);
		}
		return null;
	}
	
	/**
	 * 查询酒店城市列表
	 * @param companyId
	 * @param query
	 * @return
	 */
	public List<HotelCityVO> listHotelCityVO(String companyId, HotelCityQuery query) {
		List<HotelCityVO> hotelCitys = null;
		
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		
		if(interfaceConfig != null) {
			try {
			if(StringUtils.equals(interfaceConfig.getChannel(), "pms")){
				JXDPmsService pmsService = new JXDPmsService();
				hotelCitys = pmsService.loadHotelCityVOs(query,interfaceConfig.getCompany().getCode(),interfaceConfig.getSign(),interfaceConfig.getHost());
			}else{//E云通
				JXDHotelInfoService hotelInfoService = new JXDHotelInfoService();
				hotelCitys = hotelInfoService.loadHotelCityVOs(query,interfaceConfig.getHost());
			}
			} catch (Exception e) {
				logger.error("酒店城市列表接口获取数据出错！");
				e.printStackTrace();
			}
		}
		return hotelCitys;
	}
	
	/**
	 * 查询接口列表
	 * @param companyId
	 * @param query
	 * @return
	 */
	public List<InterfaceListVO> interfaceListVO(String companyId, InterfaceListQuery query) {
		List<InterfaceListVO> interfaceLists = null;
		
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		
		if(interfaceConfig != null) {
			JXDHotelInfoService hotelInfoService = new JXDHotelInfoService();
			try {
				interfaceLists = hotelInfoService.loadInterfaceListVOs(query,interfaceConfig.getUrl());
			} catch (Exception e) {
				logger.error("接口列表获取出错！");
				e.printStackTrace();
			}
		}
		return interfaceLists;
	}
	
	public Map<String, String> getHotelMiniatureUrlByCode(String companyId){
		Map<String, String> map = new HashMap<String, String>();
		List<Hotel> hotels = findAllHotels(companyId);
		for (Hotel hotel : hotels) {
			map.put(hotel.getCode(), hotel.getMiniatureUrl());
		}
		return map;
	}
	
	public List<Hotel> findHotelByCity(String companyId,String city){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		if(StringUtils.isNotBlank(city)){
			properties.put("city", city);
		}
		List<Hotel> hotels = hotelDao.findByProperties(properties);
		
		/*Collections.sort(hotels, new Comparator<Hotel>(){
            public int compare(Hotel o1, Hotel o2) {
            	PinyinHelper.convertToPinyinString(str, separator)
                return null;
            }
        });*/
		return hotels;
	}

	public List<Hotel> findHotel(ListHotelReq param){
		Query<Hotel> query = hotelDao.createQuery();
		query.field("companyId").equal(param.getCompanyId());
		if(StringUtils.isNotEmpty(param.getCity()) && !"0".equals(param.getCity())){
			query.field("city").equal(param.getCity());
		}
		if(null != param.getType() && 1== param.getType()){
			query.field("isTakeOut").equal(true);
		}
		if(StringUtils.isNotEmpty(param.getKeyword())){
			String keyword = param.getKeyword();
			query.or(query.criteria("name").contains(keyword),query.criteria("city").contains(keyword),query.criteria("address").contains(keyword));
		}
		List<Hotel> hotels = hotelDao.findByQuery(query).asList();

		return hotels;
	}
}
