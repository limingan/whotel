package com.whotel.common.batchjob;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whotel.card.entity.MarketingFan;
import com.whotel.card.entity.MarketingRecord;
import com.whotel.card.entity.MarketingRule;
import com.whotel.card.entity.RecommendFan;
import com.whotel.card.service.MemberTradeService;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.FilterModel;
import com.whotel.common.enums.TradeType;
import com.whotel.common.util.DateUtil;
import com.whotel.hotel.entity.HotelOrder;
import com.whotel.hotel.enums.HotelOrderStatus;
import com.whotel.hotel.service.HotelOrderService;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderVO;
import com.whotel.weixin.service.WeixinMessageService;

@Component
public class OrderHandleJob implements Serializable {
	
	private static final Logger logger = Logger.getLogger(OrderHandleJob.class);

	private static final long serialVersionUID = -3506927715164362584L;
	
	@Autowired
	private HotelOrderService hotelOrderService;
	
	@Autowired
	private MemberTradeService memberTradeService;
	
	@Autowired
	private WeixinMessageService weixinMessageService;
	
	/**
	 * 自动取消已过期订单
	 */
	public void autoCancelHotelOrder(JobExecutionContext context) {
		Page<HotelOrder> page = new Page<>();
		page.addFilter("checkOutTime", FilterModel.LE, DateUtil.getStartTime(new Date()));
		page.addFilter("status", FilterModel.NE, HotelOrderStatus.CONFIRMED);
		page.addFilter("status", FilterModel.NE, HotelOrderStatus.USED);
		page.addFilter("status", FilterModel.NE, HotelOrderStatus.CANCELED);
		int pageNo = 1;
		do {
			page.setPageNo(pageNo);
			hotelOrderService.findHotelOrders(page);
			pageNo ++;
			
			List<HotelOrder> hotelOrders = page.getResult();
			if(hotelOrders != null) {
				for(HotelOrder order:hotelOrders) {
					logger.info(order.getStatus().getLabel()+"---------------"+DateUtil.format(order.getCheckOutTime(), "yyyy-MM-dd HH:mm:ss 过期自动取消"));
					hotelOrderService.cancelHotelOrder(order.getCompanyId(), order.getOpenId(), order.getOrderSn(), "微信平台自动取消");
				}
			}
		} while(pageNo <= page.getTotalPages());
	}
	
	/**
	 * 已入住订单，执行营销政策
	 */
	
	
	
	/**
	 * 预订订单使用提醒
	 */
	public void autoSendUseRemind(JobExecutionContext context){
		logger.info("-------预订订单使用提醒--------");
		Page<HotelOrder> page = new Page<>();
		page.addFilter("status", FilterModel.EQ, HotelOrderStatus.CONFIRMED);//可入住
		page.addFilter("checkInTime", FilterModel.GE, DateUtil.getStartTime(new Date()));//今天
		int pageNo = 1;
		do {
			page.setPageNo(pageNo);
			hotelOrderService.findHotelOrders(page);
			pageNo ++;
			
			List<HotelOrder> hotelOrders = page.getResult();
			if(hotelOrders != null) {
				for(HotelOrder order:hotelOrders) {
					String checkInTime = DateUtil.format(order.getCheckInTime(), "yyyy-MM-dd")+" "+order.getArriveTime()+":00";
					int minute = (int)((DateUtil.parseDate(checkInTime,"yyyy-MM-dd HH:mm:ss").getTime()-new Date().getTime())/1000/60);
					if(minute<=60 && minute>=40){
						logger.info(order.getStatus().getLabel()+"---------------"+DateUtil.format(order.getCheckInTime(), "yyyy-MM-dd HH:mm:ss 预订订单使用提醒"));
						weixinMessageService.sendUseRemind(order);
					}
				}
			}
		} while(pageNo <= page.getTotalPages());
	}
	
}
