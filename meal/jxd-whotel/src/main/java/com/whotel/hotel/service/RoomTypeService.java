package com.whotel.hotel.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Page;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.hotel.dao.RoomTypeDao;
import com.whotel.hotel.entity.RoomType;
import com.whotel.thirdparty.jxd.api.JXDHotelInfoService;
import com.whotel.thirdparty.jxd.api.JXDPmsService;
import com.whotel.thirdparty.jxd.mode.RoomInfoListQuery;
import com.whotel.thirdparty.jxd.mode.RoomPriceQuery;
import com.whotel.thirdparty.jxd.mode.vo.RoomInfoVO;
import com.whotel.thirdparty.jxd.mode.vo.RoomPriceVO;

/**
 * 酒店房型服务类
 * @author 冯勇
 *
 */
@Service
public class RoomTypeService {
	
	private static final Logger logger = Logger.getLogger(RoomTypeService.class);
	
	@Autowired
	private RoomTypeDao houseTypeDao;
	
	@Autowired
	private InterfaceConfigService interfaceConfigService;
	
	/**
	 * 加载客房列表
	 * @param companyId
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<RoomInfoVO, List<RoomInfoVO>> listRoomInfoVO(String companyId, RoomInfoListQuery query) {
		
		Map<RoomInfoVO, List<RoomInfoVO>> roomInfoMap = new LinkedMap();
		
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		
		if(interfaceConfig != null) {
			List<RoomInfoVO> roomInfos = null;
			try {
				if(StringUtils.equals(interfaceConfig.getChannel(), "pms")){
					JXDPmsService pmsService = new JXDPmsService();
					roomInfos = pmsService.loadRoomInfos(query,interfaceConfig.getCompany().getCode(),interfaceConfig.getSign(),interfaceConfig.getHost());
				}else{
					JXDHotelInfoService hotelInfoService = new JXDHotelInfoService();
					roomInfos = hotelInfoService.loadRoomInfos(query,interfaceConfig.getHost());
				}
			} catch (Exception e) {
				logger.error("酒店客房列表接口获取数据出错！");
				e.printStackTrace();
			}
			
			if(roomInfos != null) {
				for(RoomInfoVO roomInfo: roomInfos) {
					List<RoomInfoVO> list = roomInfoMap.get(roomInfo);
					if(list == null) {
						list = new ArrayList<RoomInfoVO>();
						roomInfoMap.put(roomInfo, list);
					}
					list.add(roomInfo);
					if(StringUtils.isNotBlank(roomInfo.getLargessReturnType())) {
						roomInfo.setSign(true);
					}
				}
			}
		}
		return roomInfoMap;
	}
	
	public List<RoomPriceVO> findRoomPriceVOs(String companyId, RoomPriceQuery query) {
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		
		if(interfaceConfig != null) {
			List<RoomPriceVO> roomPriceVOs = null;
			try {
				if(StringUtils.equals(interfaceConfig.getChannel(), "pms")){
					JXDPmsService pmsService = new JXDPmsService();
					roomPriceVOs = pmsService.loadRoomPrices(query,interfaceConfig.getCompany().getCode(),interfaceConfig.getSign(),interfaceConfig.getHost());
				}else{//E云通
					JXDHotelInfoService hotelInfoService = new JXDHotelInfoService();
					roomPriceVOs = hotelInfoService.loadRoomPrices(query,interfaceConfig.getHost());
				}
			} catch (Exception e) {
				logger.error("酒店客房列表接口获取数据出错！");
				e.printStackTrace();
			}
			return roomPriceVOs;
		}
		return null;
	}
	
	public void saveHouseType(RoomType houseType) {
		if(houseType != null) {
			String id = houseType.getId();
			if(StringUtils.isBlank(id)) {
				houseType.setCreateTime(new Date());
			}
			houseTypeDao.save(houseType);
		}
	}
	
	public void deleteHouseType(String id) {
		if(StringUtils.isNotBlank(id)) {
			RoomType houseType = getHouseTypeById(id);
			if(houseType != null) {
				houseTypeDao.delete(houseType);
			}
		}
	}

	public void deleteHouseType(RoomType houseType) {
		if(houseType != null) {
			houseTypeDao.delete(houseType);
		}
	}
	
	public void deleteMoreHouseType(String[] ids) {
		if(ids != null) {
			for(String id:ids) {
				deleteHouseType(id);
			}
		}
	}

	public List<RoomType> findAllHouseTypes() {
		return houseTypeDao.findAll();
	}

	public void findhouseTypes(Page<RoomType> page) {
		houseTypeDao.find(page);
	}

	public RoomType getHouseTypeById(String id) {
		if(StringUtils.isNotBlank(id)) {
			return houseTypeDao.get(id);
		}
		return null;
	}
}
