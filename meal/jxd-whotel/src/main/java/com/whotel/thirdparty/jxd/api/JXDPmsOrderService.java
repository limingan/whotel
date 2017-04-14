package com.whotel.thirdparty.jxd.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.common.util.MD5Util;
import com.whotel.common.util.SpringContextHolder;
import com.whotel.system.entity.SysOrderLog;
import com.whotel.system.service.SystemLogService;
import com.whotel.thirdparty.jxd.ApiException;
import com.whotel.thirdparty.jxd.JXDConstants;
import com.whotel.thirdparty.jxd.mode.HotelOrderQuery;
import com.whotel.thirdparty.jxd.mode.OrderAirportPickupService;
import com.whotel.thirdparty.jxd.mode.OrderDetail;
import com.whotel.thirdparty.jxd.mode.OrderDetailPrice;
import com.whotel.thirdparty.jxd.mode.OrderDetailQuery;
import com.whotel.thirdparty.jxd.mode.OrderResQuery;
import com.whotel.thirdparty.jxd.mode.OrderValueAddedService;
import com.whotel.thirdparty.jxd.mode.RoomBindQuery;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderDetailVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderVO;
import com.whotel.thirdparty.jxd.mode.vo.ReservationResult;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderVO.OrderDetailPriceVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderVO.OrderDetailVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderVO.OrderValueAddedServiceVO;
import com.whotel.thirdparty.jxd.mode.vo.OccupancyManVO;
import com.whotel.thirdparty.jxd.util.ApiXmlVoParser;
import com.whotel.thirdparty.jxd.util.JxdXmlUtils;

public class JXDPmsOrderService {
	
	private static final Logger log = Logger.getLogger(JXDPmsOrderService.class);
	private SystemLogService systemLogService = SpringContextHolder.getBean(SystemLogService.class);
	
	/////////////////////////////////////////客房//////////////////////////////////////////////////
	public ReservationResult createHotelOrder(HotelOrderVO order,String code,String key,String url) throws Exception {
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
		reservation.put("ContactName", order.getContactName());
		reservation.put("ContactMobile", order.getContactMobile());
		reservation.put("ContactTel", order.getContactTel()); 
		reservation.put("ContactEmail", order.getContactEmail());
		reservation.put("ConsumerRevertMsg", order.getConsumerRevertMsg());
		reservation.put("SalesCName", order.getSaleName());
		reservation.put("OrderType", "0");  // 0:客房订单,1:餐饮订单,2:温泉门票订单,3:直通车订单,4:综合订单
		//reservation.put("BookChannel", order.getBookChannel());//预订渠道，hotel(酒店)shop(商城)
		
		reservation.put("ScorePaidAmount", order.getScorePaidAmount());
		reservation.put("ReturnMoneyPaidAmount", order.getReturnMoneyPaidAmount());
		reservation.put("MbrCardPaidAmount", order.getMbrCardPaidAmount());
		reservation.put("ScorePaidRefId", order.getScorePaidRefId()); 
		reservation.put("ReturnMoneyPaidRefId", order.getReturnMoneyPaidRefId());
		reservation.put("MbrCardPaidRefId", order.getMbrCardPaidRefId());
		
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
		return createHotelOrder(reservation,code,key,url);
	}

	private ReservationResult createHotelOrder(Map<String, Object> params,String code,String key,String url) throws Exception {
		ReservationResult result = null;
		String param = "";
		String html = "";
		String hotelUrl = url+"?grpid="+code+"&channel=C01&sign=";
		try {
			HotelOrderQuery order = new HotelOrderQuery();
			order.setReservation(params);
			order.setOpType("订单维护");
			order.setxType("JxdBSPms");
			
			param = JxdXmlUtils.toXml(order);
			if(log.isDebugEnabled()) {
				log.debug("param: \n" + param);
			}
			
			hotelUrl += MD5Util.MD5(key+code+"C01"+param).toLowerCase();
			
			// 请求接口并获得响应
			Response res = HttpHelper.connect(hotelUrl).header("Content-Type", "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
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

	public boolean cancelHotelOrder(String orderNo, String cancelReason,String code,String key,String url) throws Exception {
		if (StringUtils.isBlank(orderNo)) {
			throw new ApiException("订单号不能为null!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("OrderOperate", "Cancel");
		params.put("OrderNo", orderNo);
		params.put("ConsumerRevertMsg", cancelReason);
		ReservationResult result = this.createHotelOrder(params,code, key,url);
		if(result != null && StringUtils.isNotBlank(result.getErrorMsg())) {
			return false;
		}
		return true;
	}
	
	public HotelOrderDetailVO getHotelOrderDetailVO(String orderNo,String code,String key,String url) throws Exception {
		OrderDetailQuery query = new OrderDetailQuery();
		query.setxType("JxdBSPms");
		Map<String, Object> resQuery = new HashMap<String, Object>();
		query.setResQuery(resQuery);
		resQuery.put("RegId", orderNo);
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		
		String hotelUrl = url+"?grpid="+code+"&channel=C01&sign="+MD5Util.MD5(key+code+"C01"+param).toLowerCase();
		
		Response res = HttpHelper.connect(hotelUrl).header("Content-Type", "text/xml")
				.timeout(JXDConstants.TIMEOUT).post(param);
		ApiXmlVoParser.checkReturnContent(res);
		
		HotelOrderDetailVO result = ApiXmlVoParser.parseHotelOrderDetailVO(res.html(), res.charset());
		return result;
	}

	public List<HotelOrderVO> queryHotelOrder(String confirmationId, String profileId, String weixinId,String code,String key,String url) throws Exception {
		OrderResQuery query = new OrderResQuery();
		Map<String, Object> resQuery = new HashMap<String, Object>();
		query.setResQuery(resQuery);
		resQuery.put("ProfileId", profileId);
		resQuery.put("confirmationId", confirmationId);
		resQuery.put("WeixinID", weixinId);
		query.setOpType("订单查询");
		query.setxType("JxdBSPms");
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		String hotelUrl = url+"?grpid="+code+"&channel=C01&sign="+MD5Util.MD5(key+code+"C01"+param).toLowerCase();
		
		// 请求接口并获得响应
		List<HotelOrderVO> result = null;
		Response res = HttpHelper.connect(hotelUrl).header("Content-Type", "text/xml")
				.timeout(JXDConstants.TIMEOUT).post(param);
		ApiXmlVoParser.checkReturnContent(res);

		result = ApiXmlVoParser.parseHotelOrderVOs(res.html(), res.charset());
		return result;
	}
	
	public List<OccupancyManVO> queryOccupancyManVOs(String idNo, String roomNo, String code,String key,String url) throws Exception {
		RoomBindQuery query = new RoomBindQuery();
		Map<String, Object> roomBindQuery = new HashMap<String, Object>();
		query.setRoomBind(roomBindQuery);
		roomBindQuery.put("idNo", idNo);
		roomBindQuery.put("roomNo", roomNo);
		query.setxType("JxdBSPms");
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		String hotelUrl = url+"?grpid="+code+"&channel=C01&sign="+MD5Util.MD5(key+code+"C01"+param).toLowerCase();
		
		// 请求接口并获得响应
		List<OccupancyManVO> result = null;
		Response res = HttpHelper.connect(hotelUrl).header("Content-Type", "text/xml")
				.timeout(JXDConstants.TIMEOUT).post(param);
		ApiXmlVoParser.checkReturnContent(res);

		String html = res.html();
		System.out.println("queryOccupancyManVOs"+html);
		
		result = ApiXmlVoParser.parseOccupancyManVOs(html, res.charset());
		return result;
	}
}
