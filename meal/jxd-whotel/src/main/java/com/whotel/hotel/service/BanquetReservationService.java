package com.whotel.hotel.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.thirdparty.jxd.api.JXDOrderService;
import com.whotel.thirdparty.jxd.mode.BanquetReservation;

/**
 * 会议宴会服务类
 * @author 柯鹏程
 */
@Service
public class BanquetReservationService {
	
	private static final Logger logger = Logger.getLogger(BanquetReservationService.class);
	
	
	@Autowired
	private InterfaceConfigService interfaceConfigService;
	
	/**
	 * 会议宴会预订
	 * @return
	 */
	public boolean saveBanquetReservation(BanquetReservation banquetReservation,String companyId) {
		
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		
		if(interfaceConfig != null) {
			JXDOrderService orderService = new JXDOrderService();
			try {
				return orderService.saveBanquetReservation(banquetReservation,interfaceConfig.getHost());
			} catch (Exception e) {
				logger.error("酒点订单列表接口获取数据出错！");
				e.printStackTrace();
			}
		}
		return false;
	}
	
}
