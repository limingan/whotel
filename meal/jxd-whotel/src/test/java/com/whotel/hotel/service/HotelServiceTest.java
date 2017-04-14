package com.whotel.hotel.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.whotel.common.batchjob.OrderHandleJob;
import com.whotel.hotel.entity.HotelOrder;

@ContextConfiguration(locations = { "/applicationContext-*test.xml" })
public class HotelServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private HotelOrderService hotelOrderService;
	
	@Autowired
	private OrderHandleJob orderHandleJob;
	
	@Test
	public void testSynchronizeHotelOrderToJXD() {
		HotelOrder hotelOrder = hotelOrderService.getHotelOrderById("56e07a24d5e1ea7792583edd");
		hotelOrderService.synchronizeHotelOrderToJXD(hotelOrder);
	}
	
	@Test
	public void testLoadHotelOrders() {
		List<HotelOrder> hotelOrders = hotelOrderService.loadHotelOrders("55c5cdfd8797621e28a26000", "oM_FUt2ONAUdu01fSHuYKMBE-uyY", null);
		if(hotelOrders != null) {
			for(HotelOrder order:hotelOrders) {
				System.out.println(order.getName());
			}
		}
	}
	
	@Test
	public void testUpdateHotelOrders() {
		List<HotelOrder> hotelOrders = hotelOrderService.findAllHotelOrders();
		if(hotelOrders != null) {
			for(HotelOrder order:hotelOrders) {
				if(order != null && order.getStatus() == null) {
					hotelOrderService.cancelHotelOrder(order.getCompanyId(), order.getOpenId(), order.getOrderSn(), "测试取消");
				}
			}
		}
	}
	
	@Test
	public void testCancelHotelOrder() {
		orderHandleJob.autoCancelHotelOrder(null);
	}
}
