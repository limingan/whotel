package com.whotel.thirdparty.jxd.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.common.util.DateUtil;
import com.whotel.common.util.MD5Util;
import com.whotel.common.util.SpringContextHolder;
import com.whotel.meal.entity.MealOrder;
import com.whotel.system.entity.SysOrderLog;
import com.whotel.system.service.SystemLogService;
import com.whotel.thirdparty.jxd.ApiException;
import com.whotel.thirdparty.jxd.JXDConstants;
import com.whotel.thirdparty.jxd.mode.AttachProduct;
import com.whotel.thirdparty.jxd.mode.BanquetReservation;
import com.whotel.thirdparty.jxd.mode.HotelOrderDetailQuery;
import com.whotel.thirdparty.jxd.mode.HotelOrderQuery;
import com.whotel.thirdparty.jxd.mode.MealBook;
import com.whotel.thirdparty.jxd.mode.MealOrderResQuery;
import com.whotel.thirdparty.jxd.mode.OrderAirportPickupService;
import com.whotel.thirdparty.jxd.mode.OrderDetail;
import com.whotel.thirdparty.jxd.mode.OrderDetailPrice;
import com.whotel.thirdparty.jxd.mode.OrderResQuery;
import com.whotel.thirdparty.jxd.mode.OrderValueAddedService;
import com.whotel.thirdparty.jxd.mode.TiketOrderQuery;
import com.whotel.thirdparty.jxd.mode.TiketOrderStateQuery;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderDetailVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderVO.OrderDetailPriceVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderVO.OrderDetailVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderVO.OrderValueAddedServiceVO;
import com.whotel.thirdparty.jxd.mode.vo.PWBTicketResult;
import com.whotel.thirdparty.jxd.mode.vo.ReservationResult;
import com.whotel.thirdparty.jxd.mode.vo.TicketOrderVO;
import com.whotel.thirdparty.jxd.util.ApiXmlVoParser;
import com.whotel.thirdparty.jxd.util.JxdXmlUtils;

/**
 * 订单接口
 * @author 冯勇
 * 
 */
public class JXDOrderService {
	private static final Logger log = Logger.getLogger(JXDOrderService.class);
	private SystemLogService systemLogService = SpringContextHolder.getBean(SystemLogService.class);

	public ReservationResult createHotelOrder(HotelOrderVO order,String hotelUrl) throws Exception {
		Map<String, Object> reservation = new HashMap<String, Object>();

		reservation.put("OrderOperate", order.getOrderOperate());
		reservation.put("OrderNo", order.getOrderNo());
		reservation.put("confirmationID", order.getConfirmationID());
		reservation.put("ResortId", order.getResortId());
		reservation.put("MbrCardNo", order.getMbrCardNo());
		reservation.put("MbrCardTypeCode", order.getMbrCardTypeCode());
		reservation.put("GuestType", order.getGuestType());
		reservation.put("Source", order.getSource());
		reservation.put("WeixinID", order.getWeixinID());
		reservation.put("ProfileId", order.getProfileId()==null?0:order.getProfileId());
		reservation.put("PayMethodType", order.getPayMethodType());
		reservation.put("PaidRefId", order.getPaidRefId());
		reservation.put("TotalAmount", order.getTotalAmount());
		reservation.put("CouponCodes", order.getCouponCodes());
		reservation.put("CouponPaidAmount", order.getCouponPaidAmount()); 
		
		reservation.put("PaidAmount", order.getPaidAmount());
		reservation.put("ReturnMoneyPaidAmount", order.getReturnMoneyPaidAmount());
		reservation.put("MbrCardPaidAmount", order.getMbrCardPaidAmount());
		reservation.put("ContactName", order.getContactName());
		reservation.put("ContactMobile", order.getContactMobile());
		reservation.put("ContactTel", order.getContactTel()); 
		reservation.put("ContactEmail", order.getContactEmail());
		reservation.put("ConsumerRevertMsg", order.getConsumerRevertMsg());
		reservation.put("SalesCName", order.getSaleName());
		reservation.put("OrderType", "0");  // 0:客房订单,1:餐饮订单,2:温泉门票订单,3:直通车订单,4:综合订单
		reservation.put("BookChannel", order.getBookChannel());//预订渠道，hotel(酒店)shop(商城)
		
		List<OrderDetailVO> orderDetailVOs = order.getOrderDetails();
		if(orderDetailVOs != null) {
			List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
			reservation.put("OrderDetails", orderDetails);
			int detailNum = 0;
			for(OrderDetailVO orderDetailVO:orderDetailVOs) {
				detailNum ++;
				OrderDetail detail = new OrderDetail();
				orderDetails.add(detail);
				detail.setDetailId(detailNum);
				detail.setCode(orderDetailVO.getCode());
				detail.setSalesPromotionId(orderDetailVO.getSalesPromotionId());
				detail.setArriveDate(orderDetailVO.getArriveDate());
				detail.setLeaveDate(orderDetailVO.getLeaveDate());
				detail.setRoomQty(orderDetailVO.getRoomQty());
				detail.setTotalAmount(orderDetailVO.getTotalAmount());
				detail.setPaidAmount(orderDetailVO.getPaidAmount());
				detail.setPayMethod(orderDetailVO.getPayMethod());
				detail.setGuestName(orderDetailVO.getGuestName());
				detail.setRoomSpecial(orderDetailVO.getRoomSpecial());
				detail.setArriveTime(orderDetailVO.getArriveTime());
				detail.setGuestRemark(orderDetailVO.getGuestRemark());
				
				List<OrderDetailPriceVO> orderDetailPriceVOs = orderDetailVO.getOrderDetailPrices();
				if(orderDetailPriceVOs != null && orderDetailPriceVOs.size() > 0) {
					List<OrderDetailPrice> detailPrices = new ArrayList<OrderDetailPrice>();
					detail.setOrderDetailPrices(detailPrices);
					int priceNum = 0;
					for(OrderDetailPriceVO detailPriceVO: orderDetailPriceVOs) {
						priceNum ++;
						OrderDetailPrice detailPrice = new OrderDetailPrice();
						detailPrice.setDetailId(priceNum);
						detailPrice.setPrice(detailPriceVO.getPrice());
						detailPrice.setPriceDate(detailPriceVO.getPriceDate());
						detailPrices.add(detailPrice);
					}
				}
		        
				List<OrderValueAddedServiceVO> orderValueAddedServiceVOs = orderDetailVO.getOrderValueAddedServices();
				
				if(orderValueAddedServiceVOs != null && orderValueAddedServiceVOs.size() > 0) {
					List<OrderValueAddedService> orderValueAddedServices = new ArrayList<OrderValueAddedService>();
					detail.setOrderValueAddedServices(orderValueAddedServices);
					int serviceNum = 0;
					for(OrderValueAddedServiceVO orderValueAddedServiceVO:orderValueAddedServiceVOs) {
						serviceNum ++;
						OrderValueAddedService valueAddedService = new OrderValueAddedService();
						valueAddedService.setDetailId(serviceNum);
						valueAddedService.setValueAddedId(orderValueAddedServiceVO.getValueAddedId());
						valueAddedService.setQty(orderValueAddedServiceVO.getQty());
						valueAddedService.setPrice(orderValueAddedServiceVO.getPrice());
						valueAddedService.setAmount(orderValueAddedServiceVO.getAmount());
						
						orderValueAddedServices.add(valueAddedService);
					}
				}
				
				List<OrderValueAddedServiceVO> orderAirportPickupServiceVOs = orderDetailVO.getOrderAirportPickupServices();
				
				if(orderAirportPickupServiceVOs != null && orderAirportPickupServiceVOs.size() > 0) {
					List<OrderAirportPickupService> orderAirportPickupServices = new ArrayList<OrderAirportPickupService>();
					detail.setOrderAirportPickupServices(orderAirportPickupServices);
					int serviceNum = 0;
					for(OrderValueAddedServiceVO orderAirportPickupServiceVO:orderAirportPickupServiceVOs) {
						serviceNum ++;
						
						OrderAirportPickupService airportPickupService = new OrderAirportPickupService();
						airportPickupService.setDetailId(serviceNum);
						airportPickupService.setAirportPickupId(orderAirportPickupServiceVO.getValueAddedId());
						airportPickupService.setAirplanInfo(orderAirportPickupServiceVO.getExtraValue());
						airportPickupService.setQty(orderAirportPickupServiceVO.getQty());
						airportPickupService.setPrice(orderAirportPickupServiceVO.getPrice());
						airportPickupService.setAmount(orderAirportPickupServiceVO.getAmount());
						
						orderAirportPickupServices.add(airportPickupService);
					}
				}
			}
		}
		return createHotelOrder(reservation,hotelUrl);
	}

	private ReservationResult createHotelOrder(Map<String, Object> params,String hotelUrl) throws Exception {
		ReservationResult result = null;
		String param = "";
		String html = "";
		try {
			HotelOrderQuery order = new HotelOrderQuery();
			order.setReservation(params);
			
			param = JxdXmlUtils.toXml(order);
			if(log.isDebugEnabled()) {
				log.debug("param: \n" + param);
			}
			
			// 请求接口并获得响应
			Response res = HttpHelper.connect(hotelUrl).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			
			html = res.html();
			result = ApiXmlVoParser.parseReservationResult(res.html(), res.charset());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveSysOrderLog(new SysOrderLog(param,"Hotel",hotelUrl,html));
		}
		return result;
	}

	public ReservationResult updateHotelOrder(HotelOrderVO order,String hotelUrl) throws Exception {
		if (StringUtils.isBlank(order.getOrderNo())) {
			throw new ApiException("订单号不能为null!");
		}
		return this.createHotelOrder(order,hotelUrl);
	}

	public boolean cancelHotelOrder(String orderNo, String cancelReason,String hotelUrl) throws Exception {
		if (StringUtils.isBlank(orderNo)) {
			throw new ApiException("订单号不能为null!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("OrderOperate", "Cancel");
		params.put("OrderNo", orderNo);
		params.put("ConsumerRevertMsg", cancelReason);
		ReservationResult result = this.createHotelOrder(params,hotelUrl);
		if(result != null && StringUtils.isNotBlank(result.getErrorMsg())) {
			return false;
		}
		return true;
	}

	public HotelOrderDetailVO getHotelOrderDetailVO(String orderNo,String hotelUrl) throws Exception {
		
		HotelOrderDetailQuery query = new HotelOrderDetailQuery();
		Map<String, Object> resQuery = new HashMap<String, Object>();
		query.setResQuery(resQuery);
		resQuery.put("OrderNo", orderNo);
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		
		Response res = HttpHelper.connect(hotelUrl).header("Content-Type", "text/xml")
				.timeout(JXDConstants.TIMEOUT).post(param);
		ApiXmlVoParser.checkReturnContent(res);
		
		HotelOrderDetailVO result = ApiXmlVoParser.parseHotelOrderDetailVO(res.html(), res.charset());
		return result;
	}

	public List<HotelOrderVO> queryHotelOrder(String confirmationId, String profileId, String weixinId,String hotelUrl) throws Exception {
		OrderResQuery query = new OrderResQuery();
		Map<String, Object> resQuery = new HashMap<String, Object>();
		query.setResQuery(resQuery);
		resQuery.put("ProfileId", profileId);
		resQuery.put("confirmationId", confirmationId);
		resQuery.put("WeixinID", weixinId);
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		List<HotelOrderVO> result = null;
		Response res = HttpHelper.connect(hotelUrl).header("Content-Type", "text/xml")
				.timeout(JXDConstants.TIMEOUT).post(param);
		ApiXmlVoParser.checkReturnContent(res);

		result = ApiXmlVoParser.parseHotelOrderVOs(res.html(), res.charset());
		return result;
	}
	
	public Boolean saveBanquetReservation(BanquetReservation banquetReservation,String hotelUrl){
		String param = JxdXmlUtils.toXml(banquetReservation);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		try {
			Response res = HttpHelper.connect(hotelUrl).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<TicketOrderVO> queryTicketOrder(OrderResQuery query,String ticketUrl) throws Exception {
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		List<TicketOrderVO> result = null;
		Response res = HttpHelper.connect(ticketUrl).header("Content-Type", "text/xml")
				.timeout(JXDConstants.TIMEOUT).post(param);
		ApiXmlVoParser.checkReturnContent(res);

		result = ApiXmlVoParser.parseTicketOrderVOs(res.html(), res.charset());
		return result;
	}
	public ReservationResult createTicketOrder(TicketOrderVO order,String ticketUrl) throws Exception {
		Map<String, Object> reservation = new HashMap<String, Object>();

		reservation.put("OrderOperate", order.getOrderOperate());
		reservation.put("OrderNo", order.getOrderNo());
		reservation.put("confirmationID", order.getConfirmationID());
		reservation.put("ResortId", order.getResortId());
		reservation.put("MbrCardNo", order.getMbrCardNo());
		reservation.put("GuestType", order.getGuestType());
		reservation.put("Source", order.getSource());
		reservation.put("WeixinID", order.getWeixinID());
		reservation.put("ProfileId", order.getProfileId()==null?0:order.getProfileId());
		reservation.put("PayMethodType", order.getPayMethodType());
		reservation.put("PaidRefId", order.getPaidRefId());
		reservation.put("TotalAmount", order.getTotalAmount());
		reservation.put("CouponCodes", order.getCouponCodes());
		reservation.put("CouponPaidAmount", order.getCouponPaidAmount()); 
		
		reservation.put("PaidAmount", order.getPaidAmount());
		reservation.put("ReturnMoneyPaidAmount", order.getReturnMoneyPaidAmount());
		reservation.put("MbrCardPaidAmount", order.getMbrCardPaidAmount());
		reservation.put("ContactName", order.getContactName());
		reservation.put("ContactMobile", order.getContactMobile());
		reservation.put("ContactTel", order.getContactTel()); 
		reservation.put("ContactEmail", order.getContactEmail());
		reservation.put("ConsumerRevertMsg", order.getConsumerRevertMsg());
		reservation.put("SalesCName", order.getSaleName());
		reservation.put("OrderType", order.getOrderType());  
		
		List<TicketOrderVO.OrderDetailVO> orderDetailVOs = order.getOrderDetails();
		if(orderDetailVOs != null) {
			List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
			reservation.put("OrderDetails", orderDetails);
			int detailNum = 0;
			for(TicketOrderVO.OrderDetailVO orderDetailVO:orderDetailVOs) {
				detailNum ++;
				OrderDetail detail = new OrderDetail();
				orderDetails.add(detail);
				detail.setDetailId(detailNum);
				detail.setCode(orderDetailVO.getCode());
				detail.setSalesPromotionId(orderDetailVO.getSalesPromotionId());
				detail.setArriveDate(orderDetailVO.getArriveDate());
				detail.setLeaveDate(orderDetailVO.getLeaveDate());
				detail.setRoomQty(orderDetailVO.getRoomQty());
				detail.setTotalAmount(orderDetailVO.getTotalAmount());
				detail.setPaidAmount(orderDetailVO.getPaidAmount());
				detail.setPayMethod(orderDetailVO.getPayMethod());
				detail.setGuestName(orderDetailVO.getGuestName());
				detail.setRoomSpecial(orderDetailVO.getRoomSpecial());
				detail.setArriveTime(orderDetailVO.getArriveTime());
				detail.setGuestRemark(orderDetailVO.getGuestRemark());
				
				List<TicketOrderVO.OrderDetailPriceVO> orderDetailPriceVOs = orderDetailVO.getOrderDetailPrices();
				if(orderDetailPriceVOs != null && orderDetailPriceVOs.size() > 0) {
					List<OrderDetailPrice> detailPrices = new ArrayList<OrderDetailPrice>();
					detail.setOrderDetailPrices(detailPrices);
					int priceNum = 0;
					for(TicketOrderVO.OrderDetailPriceVO detailPriceVO: orderDetailPriceVOs) {
						priceNum ++;
						OrderDetailPrice detailPrice = new OrderDetailPrice();
						detailPrice.setDetailId(priceNum);
						detailPrice.setPrice(detailPriceVO.getPrice());
						detailPrice.setPriceDate(detailPriceVO.getPriceDate());
						detailPrice.setComboCode(detailPriceVO.getComboCode());
						detailPrices.add(detailPrice);
					}
				}
		        
				List<TicketOrderVO.OrderValueAddedServiceVO> orderValueAddedServiceVOs = orderDetailVO.getOrderValueAddedServices();
				
				if(orderValueAddedServiceVOs != null && orderValueAddedServiceVOs.size() > 0) {
					List<OrderValueAddedService> orderValueAddedServices = new ArrayList<OrderValueAddedService>();
					detail.setOrderValueAddedServices(orderValueAddedServices);
					int serviceNum = 0;
					for(TicketOrderVO.OrderValueAddedServiceVO orderValueAddedServiceVO:orderValueAddedServiceVOs) {
						serviceNum ++;
						OrderValueAddedService valueAddedService = new OrderValueAddedService();
						valueAddedService.setDetailId(serviceNum);
						valueAddedService.setValueAddedId(orderValueAddedServiceVO.getValueAddedId());
						valueAddedService.setQty(orderValueAddedServiceVO.getQty());
						valueAddedService.setPrice(orderValueAddedServiceVO.getPrice());
						valueAddedService.setAmount(orderValueAddedServiceVO.getAmount());
						
						orderValueAddedServices.add(valueAddedService);
					}
				}
				
				List<TicketOrderVO.OrderValueAddedServiceVO> orderAirportPickupServiceVOs = orderDetailVO.getOrderAirportPickupServices();
				
				if(orderAirportPickupServiceVOs != null && orderAirportPickupServiceVOs.size() > 0) {
					List<OrderAirportPickupService> orderAirportPickupServices = new ArrayList<OrderAirportPickupService>();
					detail.setOrderAirportPickupServices(orderAirportPickupServices);
					int serviceNum = 0;
					for(TicketOrderVO.OrderValueAddedServiceVO orderAirportPickupServiceVO:orderAirportPickupServiceVOs) {
						serviceNum ++;
						
						OrderAirportPickupService airportPickupService = new OrderAirportPickupService();
						airportPickupService.setDetailId(serviceNum);
						airportPickupService.setAirportPickupId(orderAirportPickupServiceVO.getValueAddedId());
						airportPickupService.setAirplanInfo(orderAirportPickupServiceVO.getExtraValue());
						airportPickupService.setQty(orderAirportPickupServiceVO.getQty());
						airportPickupService.setPrice(orderAirportPickupServiceVO.getPrice());
						airportPickupService.setAmount(orderAirportPickupServiceVO.getAmount());
						
						orderAirportPickupServices.add(airportPickupService);
					}
				}
				
				List<TicketOrderVO.AttachProductVO> orderAttachProductVOs = orderDetailVO.getAttachProducts();
				if(orderAttachProductVOs != null && orderAttachProductVOs.size() > 0){
					List<AttachProduct> attachProducts = new ArrayList<AttachProduct>();
					for (TicketOrderVO.AttachProductVO attachProductVO : orderAttachProductVOs) {
						AttachProduct attachProduct = new AttachProduct();
						attachProduct.setCode(attachProductVO.getCode());
						attachProduct.setServiceType(attachProductVO.getServiceType());
						attachProduct.setRoomQty(attachProductVO.getRoomQty());
						attachProduct.setTotalAmount(attachProductVO.getTotalAmount());
						attachProducts.add(attachProduct);
					}
					detail.setAttachProducts(attachProducts);
				}
			}
		}
		return createTicketOrder(reservation,ticketUrl);
	}
	
	private ReservationResult createTicketOrder(Map<String, Object> params,String ticketUrl) throws Exception {
		ReservationResult result = null;
		String param = null;
		String html = null;
		try {
			HotelOrderQuery order = new HotelOrderQuery();
			
			// 0:客房订单,1:餐饮订单,2:温泉门票订单,3:直通车订单,4:综合订单,5套餐预订
			if("2".equals(params.get("OrderType"))){
				order.setOrderCategory("WqTicket");
			}else if("5".equals(params.get("OrderType"))){
				order.setOrderCategory("Combo");
			}
			order.setReservation(params);
			
			param = JxdXmlUtils.toXml(order);
			if(log.isDebugEnabled()) {
				log.debug("param: \n" + param);
			}
			
			// 请求接口并获得响应
			Response res = HttpHelper.connect(ticketUrl).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			
			html = res.html();
			result = ApiXmlVoParser.parseReservationResult(html, res.charset());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveSysOrderLog(new SysOrderLog(param,"Ticket",ticketUrl,html));
		}
		return result;
	}
	
	
	public Boolean cancelTicketOrder(String ticketOrderNo,String ticketUrl){
		TiketOrderQuery query = new TiketOrderQuery();
		query.setOpType("取消预订");
		Map<String, Object> params = new HashMap<>();
		params.put("ReqTime", DateUtil.getCurrDateStr());
		params.put("OrderNo", ticketOrderNo);
		query.setResQuery(params);
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		
		// 请求接口并获得响应
		try {
			Response res = HttpHelper.connect(ticketUrl).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			String html = res.html();
			System.out.println(param);
			System.out.println(res.html());
			if(html!=null && html.contains("取消订单成功")){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<TicketOrderVO> queryTicketOrderList(String ticketOrderNo,String weixinID,String ticketUrl){
		TiketOrderStateQuery query = new TiketOrderStateQuery();
		query.setOpType("预订查询");
		Map<String, Object> params = new HashMap<>();
		if(StringUtils.isNotBlank(ticketOrderNo)){
			params.put("OrderNo", ticketOrderNo);
		}
		//接口暂未实现
//		if(StringUtils.isNotBlank(weixinID)){
//			params.put("weixinID", weixinID);
//		}
		
		query.setOrderQuery(params);
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		List<TicketOrderVO> result = new ArrayList<>();
		// 请求接口并获得响应
		try {
			Response res = HttpHelper.connect(ticketUrl).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			String html = res.html();
			System.out.println(param);
			System.out.println(html);
			
			ApiXmlVoParser.checkReturnContent(res);
			result = ApiXmlVoParser.parseTicketOrderVO(html, res.charset());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 票劵预订查询
	 * @return
	 */
	public TicketOrderVO queryTicketOrder(String ticketOrderNo,String ticketUrl){
		List<TicketOrderVO> result = queryTicketOrderList(ticketOrderNo, null, ticketUrl);
		if(result!=null && result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
	
	
	public String queryTicketOrderQrCode(String ticketUrl,String orderSn,String key,String corpCode,String userName){
		
		StringBuffer sb = new StringBuffer();
		sb.append("<orderRequest>");
		sb.append("<order>");
		sb.append("<orderCode>").append(orderSn).append("</orderCode>");
		sb.append("</order>");
		sb.append("</orderRequest>");
		
		PWBTicketResult result = synchronizedTicketOrder(ticketUrl, key, corpCode, userName, "SEND_CODE_IMG_REQ", sb.toString());
		if(result!=null){
			return result.getImg();
		}
		return null;
	}
	
	public Integer queryTicketOrder(String ticketUrl,String orderSn,String key,String corpCode,String userName){
		StringBuffer sb = new StringBuffer();
		sb.append("<orderRequest>");
		sb.append("<order>");
		sb.append("<orderCode>").append(orderSn).append("</orderCode>");
		sb.append("</order>");
		sb.append("</orderRequest>");
		
		PWBTicketResult result = synchronizedTicketOrder(ticketUrl, key, corpCode, userName, "CHECK_STATUS_QUERY_REQ", sb.toString());
		if(result!=null){
			return result.getAlreadyCheckNum();
		}
		return -1;
	}
	
	public Boolean cancelTicketOrder(String ticketUrl,String orderSn,String key,String corpCode,String userName){
		StringBuffer sb = new StringBuffer();
		sb.append("<orderRequest>");
		sb.append("<order>");
		sb.append("<orderCode>").append(orderSn).append("</orderCode>");
		sb.append("</order>");
		sb.append("</orderRequest>");
		
		PWBTicketResult result = synchronizedTicketOrder(ticketUrl, key, corpCode, userName, "SEND_CODE_CANCEL_NEW_REQ", sb.toString());
		if(result!=null && StringUtils.equals(result.getDescription(), "成功")){
			return true;
		}
		return false;
	}
	
	
	public PWBTicketResult synchronizedTicketOrder(String ticketUrl,String key,String corpCode,String userName,String transactionName,String orderRequest){

		StringBuffer sb = new StringBuffer();
		sb.append("<PWBRequest>");
		
		sb.append("<transactionName>").append(transactionName).append("</transactionName>");
		
		sb.append("<header>");
		sb.append("<application>SendCode</application>");
		sb.append("<requestTime>").append(DateUtil.getCurrDateStr()).append("</requestTime>");
		sb.append("</header>");
		
		sb.append("<identityInfo>");
		sb.append("<corpCode>").append(corpCode).append("</corpCode>");
		sb.append("<userName>").append(userName).append("</userName>");
		sb.append("</identityInfo>");
		
		sb.append(orderRequest);
		
		sb.append("</PWBRequest>");
		
		String sign = MD5Util.MD5("xmlMsg="+sb.toString()+key).toLowerCase();
		System.out.println("xmlMsg============="+sb.toString());
		System.out.println("sign============="+sign);
		
		Map<String, String> param = new HashMap<>();
		param.put("xmlMsg", sb.toString());
		param.put("sign", sign);
		PWBTicketResult result = null;
		try {
			Response res = HttpHelper.connect(ticketUrl).header("Content-Type", "application/x-www-form-urlencoded")
					.timeout(JXDConstants.TIMEOUT).data(param).post();
			System.out.println("synchronizedTicketOrder=="+res.html());
			result = ApiXmlVoParser.parsePWBTicketResult(res.html(), res.charset());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
///////////////////////////////////////////餐饮////////////////////////////////////////
	public List<MealOrder> queryMealOrder(String hotelCode,String orderDate, String refeNo, String openId,String mealUrl) throws Exception {
		MealOrderResQuery resQuery = new MealOrderResQuery();
		resQuery.setHotelCode(hotelCode);
		resQuery.setOrderDate(orderDate);
		resQuery.setRefeNo(refeNo);
		resQuery.setWxid(openId);
		String param = JxdXmlUtils.toXml(resQuery);
		//param.replace("ConfirmationId", "ConfirmationId");

		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		List<MealOrder> result = null;
		Response res = HttpHelper.connect(mealUrl).header("Content-Type", "text/xml")
				.timeout(JXDConstants.TIMEOUT).post(param);
		ApiXmlVoParser.checkReturnContent(res);

		result = ApiXmlVoParser.parseMealOrder(res.html(), res.charset());
		return result;
	}

	public ReservationResult createMealOrder(MealOrder mealOrder,String mealUrl) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ResStatus", "R");
		params.put("HotelCode", mealOrder.getHotelCode());
		params.put("confirmationID", mealOrder.getOrderSn());
		params.put("ResortRegID", mealOrder.getResortRegID());
		params.put("CreateDate", DateUtil.formatDate(mealOrder.getCreateDate())+" "+mealOrder.getArriveTime());
		params.put("ArrDate", DateUtil.formatDate(mealOrder.getArrDate())+" "+mealOrder.getArriveTime());
		params.put("Refe", mealOrder.getRestaurant().getRefeNo());
		if(mealOrder.getMealTab() != null){
			params.put("TabNo", mealOrder.getMealTab().getTabNo());
		}
		params.put("TotalAmount", mealOrder.getTotalAmount());
		params.put("Remark", mealOrder.getRemark());
		params.put("Items", mealOrder.getItems());
		params.put("GuestCname", mealOrder.getContactName());
		params.put("GuestNum", mealOrder.getGuestNum());
		params.put("MbrCardNo", mealOrder.getMbrCardNo());
		params.put("Mobile", mealOrder.getContactMobile());
		params.put("Prepay", mealOrder.getPayFee());
		params.put("PrepayMethod", mealOrder.getPayMent().getLabel());
		params.put("PrepayId", mealOrder.getTradeSn());
		params.put("Wxid", mealOrder.getOpenId());
		params.put("TicketName", mealOrder.getChargeamt()+"元优惠劵");
		params.put("TicketAmount", mealOrder.getChargeamt());
		ReservationResult result = this.createMealOrder(params,mealUrl);
		return result;
	}

	public boolean cancelMealOrder(String confirmationID,String hotelCode, String resortRegID,String cancelReason,String mealUrl) throws Exception {
		if (StringUtils.isBlank(confirmationID)) {
			throw new ApiException("订单号不能为null!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ResStatus", "X");
		params.put("HotelCode", hotelCode);
		params.put("confirmationID", confirmationID);
		params.put("resortRegID", resortRegID);
		params.put("Remark", cancelReason);
		ReservationResult result = this.createMealOrder(params,mealUrl);
		if(result != null && StringUtils.isNotBlank(result.getErrorMsg())) {
			return false;
		}
		return true;
	}

	private ReservationResult createMealOrder(Map<String, Object> params,String mealUrl) throws Exception {

		ReservationResult result = null;
		String param = null;
		String html = null;
		try {
			MealBook order = new MealBook();
			order.setCyReservation(params);

			param = JxdXmlUtils.toXml(order);
			if(log.isDebugEnabled()) {
				log.debug("param: \n" + param);
			}

			// 请求接口并获得响应
			Response res = HttpHelper.connect(mealUrl).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);

			html = res.html();
			result = ApiXmlVoParser.parseReservationResult(html, res.charset(),"Row");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveSysOrderLog(new SysOrderLog(param,"Meal",mealUrl,html));
		}
		
		return result;
	}
	
	/**
	 * 发送取消短信
	 */
	public void sendMsg(String hotelcode,String orderno,String msgtype){
		String html = "";
		Map<String,String> param = new HashMap<>();
		param.put("hotelcode", hotelcode);
		param.put("orderno", orderno);
		param.put("msgtype", msgtype);
		String url = "http://120.25.166.58/CloudBookingSystemManage/OrderManage/OrderAutoSet.aspx/SendMsg";
//		String url = "http://120.25.221.27/CloudBookingSystemManage/OrderManage/OrderAutoSet.aspx/SendMsg";
		try {
			Response res = HttpHelper.connect(url).header("Content-Type", "application/x-www-form-urlencoded")
					.timeout(JXDConstants.TIMEOUT).data(param).post();
			ApiXmlVoParser.checkReturnContent(res);
			html = res.html();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveSysOrderLog(new SysOrderLog(param.toString(),"Sms",url,html));
		}
	}
	
}
