package com.whotel.waiter.service;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.util.BeanUtil;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.hotel.entity.Hotel;
import com.whotel.hotel.service.HotelService;
import com.whotel.thirdparty.jxd.api.JXDMealService;
import com.whotel.thirdparty.jxd.mode.WaiterQuery;
import com.whotel.thirdparty.jxd.mode.vo.WaiterVO;
import com.whotel.waiter.dao.WaiterDao;
import com.whotel.waiter.entity.Waiter;

/** 
 * @ClassName: WaiterService 
 * @Description: 服务员service层
 * @author 李中辉 
 * @date 2017年5月5日 下午7:06:23  
 */
@Service
public class WaiterService {
	@Autowired
	private WaiterDao waiterDao;
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private InterfaceConfigService interfaceConfigService;
	
	/** 
	 * @author 李中辉 
	 * @Description: 查询服务员列表
	 * @date 2017年5月5日
	 */
	public Page<Waiter> findWaiter(Page<Waiter> page){
		return waiterDao.find(page);
	}
	
	/** 
	 * @author 李中辉 
	 * @Description: 保存或更新服务员
	 * @date 2017年5月5日
	 */
	public void saveOrUpdateWaiter(Waiter waiter){
		if(StringUtils.isNotBlank(waiter.getId())){
			Waiter updateWaiter = getWaiterById(waiter.getId());
			BeanUtil.copyProperties(updateWaiter, waiter, true);
			waiter = updateWaiter;
			waiter.setUpdateDate(new Date());
		}else{
			waiter.setCreateDate(new Date());
		}
		waiterDao.save(waiter);
	}
	
	/** 
	 * @author 李中辉 
	 * @Description: 根据ID 获取服务员对象
	 * @date 2017年5月5日
	 */
	public Waiter getWaiterById(String id){
		return waiterDao.get(id);
	}
	
	/** 
	 * @author 李中辉 
	 * @Description: 删除， 支持批量删除
	 * @date 2017年5月5日
	 */
	public void deleteWaiterByIds(String ids){
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				waiterDao.delete(id);
			}
		}
	}
	
	/** 
	 * @author 李中辉 
	 * @Description: 同步服务员
	 * @date 2017年5月5日
	 */
	public void synchronizeMealTabByJXD(String companyId) throws Exception {
		JXDMealService jxdMealService = new JXDMealService();
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		String url = null;
		if(interfaceConfig != null){
			url = interfaceConfig.getHost();
		}
		
		String mealUrl = null;
		Map<String, Serializable> properties = null;
		Waiter waiter = null;
		List<Waiter> waiterList = null;
		List<Hotel> hotels = hotelService.findAllHotels(companyId);
		for (Hotel hotel : hotels) {
			WaiterQuery query = new WaiterQuery();
			query.setHotelCode(hotel.getCode());
			mealUrl = hotel.getMealUrl();
			
			if(StringUtils.isBlank(mealUrl)) {
				mealUrl = url;
			}
				
			if(StringUtils.isNotBlank(mealUrl)){
				List<WaiterVO> allWaiterVO = jxdMealService.loadWaiterList(query,mealUrl);
				if(allWaiterVO != null && !allWaiterVO.isEmpty()) {
					for(WaiterVO vo : allWaiterVO) {
						properties = new HashMap<String, Serializable>();
						properties.put("companyId", companyId);
						properties.put("userNo", vo.getUserNo());
						properties.put("hotelCode", hotel.getCode());
						
						waiterList = this.findWaiterByProperties(properties);
						
						if(waiterList == null || waiterList.isEmpty()) {
							waiter = new Waiter();
						} else {
							waiter = waiterList.get(0);
						}
						
						waiter.setCompanyId(companyId);
						waiter.setHotelCode(hotel.getCode());
						waiter.setHotelName(hotel.getName());
						waiter.setCreateDate(new Date());
						BeanUtil.copyProperties(waiter,vo, true);
						this.waiterDao.save(waiter);
					}
				}
			}
		}
	}
	
	/** 
	 * @author 李中辉 
	 * @Description: 根据条件查询服务员对象
	 * @date 2017年5月5日
	 */
	public List<Waiter> findWaiterByProperties(Map<String, Serializable> properties, Order ... orders) {
		return waiterDao.findByProperties(properties, orders);
	}
}
