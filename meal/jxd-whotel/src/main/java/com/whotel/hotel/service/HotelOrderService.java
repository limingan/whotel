package com.whotel.hotel.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.card.entity.Member;
import com.whotel.card.service.MemberService;
import com.whotel.common.base.Constants;
import com.whotel.common.base.service.BaseOrderService;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.PayMent;
import com.whotel.common.enums.TradeStatus;
import com.whotel.common.util.DateUtil;
import com.whotel.common.util.MoneyUtil;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.entity.SysParamConfig;
import com.whotel.company.service.CompanyService;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.company.service.SysParamConfigService;
import com.whotel.front.service.PayOrderService;
import com.whotel.hotel.dao.HotelOrderDao;
import com.whotel.hotel.entity.HotelOrder;
import com.whotel.hotel.entity.HotelOtherService;
import com.whotel.hotel.entity.RoomPrice;
import com.whotel.hotel.enums.HotelOrderStatus;
import com.whotel.thirdparty.jxd.api.JXDMemberService;
import com.whotel.thirdparty.jxd.api.JXDOrderService;
import com.whotel.thirdparty.jxd.api.JXDPmsMemberService;
import com.whotel.thirdparty.jxd.api.JXDPmsOrderService;
import com.whotel.thirdparty.jxd.mode.HotelServiceQuery;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderDetailVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelServiceVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderVO.OrderDetailPriceVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderVO.OrderDetailVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderVO.OrderValueAddedServiceVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;
import com.whotel.thirdparty.jxd.mode.vo.ReservationResult;
import com.whotel.weixin.service.WeixinMessageService;

/**
 * 酒店订单服务类
 * @author 冯勇
 */
@Service
public class HotelOrderService extends BaseOrderService{
	
	private static final Logger logger = Logger.getLogger(HotelOrderService.class);
	
	@Autowired
	private HotelOrderDao hotelOrderDao;
	
	@Autowired
	private WeixinMessageService weixinMessageService;
	
	@Autowired
	private PayOrderService payOrderService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private InterfaceConfigService interfaceConfigService;
	
	@Autowired
	private SysParamConfigService sysParamConfigService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private HotelService hotelService;
	/**
	 * 加载酒店订单
	 * @param companyId
	 * @param confirmationId
	 * @param weixinId
	 * @return
	 */
	public List<HotelOrderVO> listHotelOrderVO(String companyId, String confirmationId, String weixinId) {
		
		List<HotelOrderVO> hotelOrders = null;
		
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		
		if(interfaceConfig != null) {
			try {
				// 通过openid获取会员用户
				if(StringUtils.equals(interfaceConfig.getChannel(), "pms")){
					JXDPmsOrderService pmsOrderService = new JXDPmsOrderService();
					hotelOrders = pmsOrderService.queryHotelOrder(confirmationId, null, weixinId,interfaceConfig.getCompany().getCode(),interfaceConfig.getSign(),interfaceConfig.getHost());
				}else{//E云通
					JXDOrderService orderService = new JXDOrderService();
					hotelOrders = orderService.queryHotelOrder(confirmationId, null, weixinId,interfaceConfig.getHost());
				}
			} catch (Exception e) {
				logger.error("酒点订单列表接口获取数据出错！");
				e.printStackTrace();
			}
		}
		return hotelOrders;
	}
	
	/**
	 * 获取酒店订单详情
	 * @param companyId
	 * @param orderNo
	 * @return
	 */
	public HotelOrderDetailVO getHotelOrderDetailVO(String companyId, String orderNo) {
		
		HotelOrderDetailVO orderDetailVO = null;
		
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		
		if(interfaceConfig != null) {
			try {
				if(StringUtils.equals(interfaceConfig.getChannel(), "pms")){
					JXDPmsOrderService pmsOrderService = new JXDPmsOrderService();
					orderDetailVO = pmsOrderService.getHotelOrderDetailVO(orderNo,interfaceConfig.getCompany().getCode(),interfaceConfig.getSign(),interfaceConfig.getHost());
				}else{//E云通
					JXDOrderService orderService = new JXDOrderService();
					orderDetailVO = orderService.getHotelOrderDetailVO(orderNo,interfaceConfig.getHost());
				}
			} catch (Exception e) {
				logger.error("酒点订单详情接口获取数据出错！");
				e.printStackTrace();
			}
		}
		return orderDetailVO;
	}
	
	public void saveHotelOrder(HotelOrder hotelOrder) {
		if(hotelOrder != null) {
			String id = hotelOrder.getId();
			if(StringUtils.isBlank(id)) {
				hotelOrder.setCreateTime(new Date());
			} else {
				HotelOrder updateHotelOrder = getHotelOrderById(id);
				hotelOrder.setCreateTime(updateHotelOrder.getCreateTime());
				hotelOrder.setUpdateTime(new Date());
			}
			
			String orderSn = hotelOrder.getOrderSn();
			if(StringUtils.isBlank(orderSn)) {
				hotelOrder.setOrderSn(hotelOrder.getHotelCode()+payOrderService.genPayOrderSn());
			}
			hotelOrderDao.save(hotelOrder);
		}
	}
	
	/**
	 * 同步订单到酒店系统
	 * @param hotelOrder
	 * @return
	 */
	public boolean synchronizeHotelOrderToJXD(HotelOrder hotelOrder) {
		String errorMsg = "";
		if(hotelOrder != null) {
			InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(hotelOrder.getCompanyId());
			
			if(interfaceConfig != null) {
				HotelOrderVO hotelOrderVO = new HotelOrderVO();
				if(StringUtils.equals(hotelOrder.getOrderOperate(), "Prepay")){
					hotelOrderVO.setOrderOperate("Prepay");
				}else if(StringUtils.equals(hotelOrder.getOrderOperate(), "Add")) {
					hotelOrderVO.setOrderOperate("Add");
				} else {
					hotelOrderVO.setOrderOperate("Update");
					hotelOrderVO.setOrderNo(hotelOrder.getHotelOrderNo());
				}
				hotelOrderVO.setConfirmationID(hotelOrder.getOrderSn());
				hotelOrderVO.setResortId(hotelOrder.getHotelCode());
				
				
				String profileId = "";   //TODO 会员ID
				String mbrCardNo = "";
				String mbrCardTypeCode = "";
				String guestType = "散客";
				Company company = companyService.getCompanyById(hotelOrder.getCompanyId());
				
				Member member = memberService.getMemberByOpendId(hotelOrder.getOpenId());
				if(member!=null){
					profileId = member.getProfileId();
					mbrCardNo = member.getMbrCardNo();
					mbrCardTypeCode = member.getMbrCardTypeCode();
					guestType = "会员";
					if(StringUtils.isNotBlank(member.getIntroducerName())){
						hotelOrderVO.setSaleName(member.getIntroducerName());
					}else{
						hotelOrderVO.setSaleName(member.getSaleName());
					}
					hotelOrderVO.setContactEmail(member.getEmail());
				}
				
				hotelOrderVO.setProfileId(profileId);
				hotelOrderVO.setMbrCardNo(mbrCardNo);
				hotelOrderVO.setMbrCardTypeCode(mbrCardTypeCode);
				hotelOrderVO.setGuestType(guestType);               // 散客，会员
				hotelOrderVO.setSource(Constants.JXD_API_SOURCE);
				hotelOrderVO.setWeixinID(hotelOrder.getOpenId());
				
				String payMethodType = "";//hotelOrder.getPayMent() == PayMent.WXPAY ? "wxpay" : "Payinhotel";
				if(hotelOrder.getPayMent() == PayMent.WXPAY){
					payMethodType = "wxpay";
				}else if(hotelOrder.getPayMent() == PayMent.BALANCEPAY){
					payMethodType = "mbrCardPay";
					hotelOrderVO.setMbrCardPaidAmount(hotelOrder.getPayFee());
				}else{
					payMethodType = "Payinhotel";
				}
				hotelOrderVO.setPayMethodType(payMethodType);       // Alipay:支付宝, Kqpay:	快钱, Payinhotel:到店支付, Tenpay:财付通(含微信支付)
				hotelOrderVO.setPaidRefId(hotelOrder.getTradeSn());
				hotelOrderVO.setTotalAmount(hotelOrder.getOrderTotalFee());
				hotelOrderVO.setPaidAmount(hotelOrder.getPayFee());
				hotelOrderVO.setContactName(hotelOrder.getContactName());
				hotelOrderVO.setContactMobile(hotelOrder.getContactMobile());
				hotelOrderVO.setContactTel(hotelOrder.getContactTel());
				hotelOrderVO.setCouponCodes(hotelOrder.getCouponSeqid());
				hotelOrderVO.setCouponPaidAmount(hotelOrder.getChargeamt());
				hotelOrderVO.setBookChannel(hotelOrder.getBookChannel());
				hotelOrderVO.setReturnMoneyPaidAmount(hotelOrder.getIncamount());
				
				List<OrderDetailVO> orderDetails = new ArrayList<OrderDetailVO>();
				hotelOrderVO.setOrderDetails(orderDetails);
				
				OrderDetailVO orderDetailVO = hotelOrderVO.new OrderDetailVO();
				orderDetails.add(orderDetailVO);
				
				orderDetailVO.setDetailId(0);
				orderDetailVO.setCode(hotelOrder.getRoomCode());
				orderDetailVO.setSalesPromotionId(hotelOrder.getSalesPromotionId());
				orderDetailVO.setArriveDate(DateUtil.format(hotelOrder.getCheckInTime(), "yyyy-MM-dd"));
				orderDetailVO.setLeaveDate(DateUtil.format(hotelOrder.getCheckOutTime(), "yyyy-MM-dd"));
				orderDetailVO.setRoomQty(hotelOrder.getRoomQty());
				orderDetailVO.setTotalAmount(hotelOrder.getOrderTotalFee());
				orderDetailVO.setPaidAmount(hotelOrder.getPayFee());
				
				orderDetailVO.setPayMethod(hotelOrder.getPayMent().getLabel());
				orderDetailVO.setGuestName(hotelOrder.getGuestName());
				orderDetailVO.setRoomSpecial(hotelOrder.getRoomSpecial());
				orderDetailVO.setArriveTime(DateUtil.format(hotelOrder.getCheckInTime(), "yyyy-MM-dd") + " " + hotelOrder.getArriveTime());
				orderDetailVO.setGuestRemark(hotelOrder.getGuestRemark()==null?"":hotelOrder.getGuestRemark());
//				String guestRemark = "付款方式："+hotelOrder.getPayMent().getLabel()+"(";
//				if(hotelOrder.getPayMent() == PayMent.WXPAY){//支付方式  微信支付
//					guestRemark += "付款单号："+hotelOrder.getTradeSn()+",";
//				}
//				guestRemark += "金额："+hotelOrder.getOrderTotalFee()+")  ";
//				
//				if(StringUtils.isNotBlank(hotelOrder.getCouponSeqid())){//使用了优惠劵
//					guestRemark = "优惠劵("+orderDetailVO.getGuestRemark()+" 券类型："+(hotelOrder.getChargeamtmodel()==0?"项目券":"现金券")+",券代码："+hotelOrder.getCouponSeqid()+",券面值："+hotelOrder.getChargeamt()+")  ";
//				}
//				guestRemark += "支付状态："+hotelOrder.getTradeStatus().getLabel();
//				orderDetailVO.setGuestRemark(guestRemark);
				
				List<OrderDetailPriceVO> detailPriceVOs = new ArrayList<OrderDetailPriceVO>();
				orderDetailVO.setOrderDetailPrices(detailPriceVOs);
				
				List<RoomPrice> roomPrices = hotelOrder.getRoomPrices();
				int index = 0;
				for(RoomPrice roomPrice:roomPrices) {
					index ++;
					OrderDetailPriceVO detailPriceVO = hotelOrderVO.new OrderDetailPriceVO();
					detailPriceVO.setDetailId(index);
					detailPriceVO.setPrice(roomPrice.getPrice());
					detailPriceVO.setPriceDate(DateUtil.format(roomPrice.getDate(), "yyyy-MM-dd"));
					detailPriceVOs.add(detailPriceVO);
				}
				
				List<HotelOtherService> otherServices = hotelOrder.getOtherServices();
				int valueIndex = 0;
				int airportIndex = 0;
				List<OrderValueAddedServiceVO> valueAddedServiceVOs = new ArrayList<OrderValueAddedServiceVO>();
				List<OrderValueAddedServiceVO> airportPickupServiceVOs = new ArrayList<OrderValueAddedServiceVO>();
				if(otherServices != null && otherServices.size() > 0) {
					for(HotelOtherService otherService:otherServices) {
						OrderValueAddedServiceVO otherServiceVO = hotelOrderVO.new OrderValueAddedServiceVO();
						
						otherServiceVO.setValueAddedId(otherService.getServiceId());
						otherServiceVO.setPrice(otherService.getPrice());
						otherServiceVO.setQty(otherService.getNumber());
						otherServiceVO.setAmount(MoneyUtil.round(otherService.getPrice() * otherService.getNumber()));
						otherServiceVO.setExtraValue(otherService.getAirplanInfo());
						if(StringUtils.equals(otherService.getTypeCode(), "valueAdd")) {
							valueIndex ++;
							otherServiceVO.setDetailId(valueIndex);
							valueAddedServiceVOs.add(otherServiceVO);
						} else if(StringUtils.equals(otherService.getTypeCode(), "airportPickup")) {
							airportIndex ++;
							otherServiceVO.setDetailId(airportIndex);
							airportPickupServiceVOs.add(otherServiceVO);
						}
					}
					
					if(airportPickupServiceVOs.size() > 0) {
						orderDetailVO.setOrderAirportPickupServices(airportPickupServiceVOs);
					}
				}
				
				HotelServiceQuery hotelServiceQuery = new HotelServiceQuery();
				hotelServiceQuery.setHotelCode(hotelOrder.getHotelCode());
//					hotelServiceQuery.setRateCode(hotelOrder.getRateCode());
				hotelServiceQuery.setSalePromotionId(hotelOrder.getSalesPromotionId());
				List<HotelServiceVO> hotelServiceVOs = hotelService.listHotelService(company.getId(), hotelServiceQuery);
				if(hotelServiceVOs!=null){
					for (HotelServiceVO hotelServiceVO : hotelServiceVOs) {
						if(StringUtils.equals(hotelServiceVO.getTypeCode(), "valueAdd") && hotelServiceVO.getIncludedQty()!=null&&hotelServiceVO.getIncludedQty()>0){
							valueIndex ++;
							OrderValueAddedServiceVO otherServiceVO = hotelOrderVO.new OrderValueAddedServiceVO();
							otherServiceVO.setValueAddedId(hotelServiceVO.getServicesId());
							otherServiceVO.setPrice(hotelServiceVO.getIncludedPrice());
							otherServiceVO.setQty(hotelServiceVO.getIncludedQty());
							otherServiceVO.setAmount(MoneyUtil.round(hotelServiceVO.getIncludedPrice() * hotelServiceVO.getIncludedQty()));
							otherServiceVO.setDetailId(valueIndex);
							valueAddedServiceVOs.add(otherServiceVO);
						}
					}
				}
				if(valueAddedServiceVOs.size() > 0) {
					orderDetailVO.setOrderValueAddedServices(valueAddedServiceVOs);
				}
				
				try {
					ReservationResult result = null;
					if(StringUtils.equals(interfaceConfig.getChannel(), "pms")){
						JXDPmsOrderService pmsOrderService = new JXDPmsOrderService();
						result = pmsOrderService.createHotelOrder(hotelOrderVO,interfaceConfig.getCompany().getCode(),interfaceConfig.getSign(),interfaceConfig.getHost());
					}else{//E云通
						JXDOrderService orderService = new JXDOrderService();
						result = orderService.createHotelOrder(hotelOrderVO,interfaceConfig.getHost());
					}
					errorMsg = result.getErrorMsg();

					if(StringUtils.isBlank(errorMsg)) {
						hotelOrder.setHotelOrderNo(result.getOrderNo());
						saveHotelOrder(hotelOrder);
						return true;
					}
				} catch (Exception e) {
					logger.error("酒店预订接口出错！");
					e.printStackTrace();
				}
			}
		}
		hotelOrder.setErrorMsg(errorMsg);
		hotelOrder.setSynchronizeState(false);
		weixinMessageService.sendHotelOrderOperationErrorMsg(hotelOrder, hotelOrder.getOrderSn());
		saveHotelOrder(hotelOrder);
		return false;
	}
	
	public void deleteHotelOrder(HotelOrder hotelOrder) {
		if(hotelOrder != null) {
			hotelOrderDao.delete(hotelOrder);
		}
	}
	
	public void deleteMoreHotelOrder(String[] ids, String companyId) {
		if(ids != null) {
			for(String id:ids) {
				HotelOrder hotelOrder = getHotelOrderById(id);
				hotelOrder.setCompanyId(companyId);
				deleteHotelOrder(hotelOrder);
			}
		}
	}

	public List<HotelOrder> findAllHotelOrders() {
		return hotelOrderDao.findAll();
	}

	public void findHotelOrders(Page<HotelOrder> page) {
		hotelOrderDao.find(page);
	}

	public HotelOrder getHotelOrderById(String id) {
		if(StringUtils.isNotBlank(id)) {
			return hotelOrderDao.get(id);
		}
		return null;
	}
	
	public List<HotelOrder> findHotelOrders(String openId) {
		Query<HotelOrder> query = hotelOrderDao.createQuery();
		query.field("openId").equal(openId);
		query.field("bookChannel").notEqual("shop");
		query.order(HotelOrderDao.getOrderString(Order.desc("createTime")));
		return query.asList();
//		Map<String, Serializable> properties = new HashMap<String, Serializable>();
//		properties.put("openId", openId);
//		properties.put("bookChannel", "hotel");
//		return hotelOrderDao.findByProperties(properties, Order.desc("createTime"));
	}
	
	public List<HotelOrder> findNoPayHotelOrders(String openId) {
		Query<HotelOrder> query = hotelOrderDao.createQuery();
		query.field("openId").equal(openId);
		query.field("tradeStatus").equal(TradeStatus.WAIT_PAY);
		query.field("status").notEqual(HotelOrderStatus.CANCELED);
		query.field("status").notEqual(HotelOrderStatus.USED);
		query.order(HotelOrderDao.getOrderString(Order.desc("createTime")));
		return query.asList();
	}
	
	public List<HotelOrder> loadHotelOrders(String companyId, String openId, TradeStatus tradeStatus) {
		List<HotelOrderVO> hotelOrderVOs = listHotelOrderVO(companyId, null, openId);
		Map<String, HotelOrderVO> map = new HashMap<String, HotelOrderVO>();
		if(hotelOrderVOs != null) {
			for(HotelOrderVO hotelOrderVO:hotelOrderVOs) {
				map.put(hotelOrderVO.getConfirmationID(), hotelOrderVO);
			}
		}
		
		List<HotelOrder> hotelOrders = null;
		if(TradeStatus.WAIT_PAY.equals(tradeStatus)) {
			hotelOrders = findNoPayHotelOrders(openId);
		} else {
			hotelOrders = findHotelOrders(openId);
		}
		
		List<HotelOrder> jxdHotelOrders = new ArrayList<HotelOrder>();
		if(hotelOrders != null) {
			for(HotelOrder hotelOrder: hotelOrders) {
				String orderSn = hotelOrder.getOrderSn();
				HotelOrderVO hotelOrderVO = map.get(orderSn);
				if(hotelOrderVO != null) {
					hotelOrder.setHotelOrderNo(hotelOrderVO.getOrderNo());
					hotelOrder.setStatus(transferOrderStatus(hotelOrderVO.getConfirmTypeName()));
				}
				
				jxdHotelOrders.add(hotelOrder);
			}
		}
		return jxdHotelOrders;
	}

	private HotelOrderStatus transferOrderStatus(String confirmTypeName) {
		HotelOrderStatus status = null;
		if(StringUtils.equals(confirmTypeName, "新订单")) {
			status = HotelOrderStatus.NEW;
		} else if(StringUtils.equals(confirmTypeName, "自动确认")
				  || StringUtils.equals(confirmTypeName, "酒店确认")) {
			status = HotelOrderStatus.CONFIRMED;
		} else if(StringUtils.equals(confirmTypeName, "已入住")
				||StringUtils.equals(confirmTypeName, "在住")) {
			status = HotelOrderStatus.USED;
		} else if(StringUtils.equals(confirmTypeName, "酒店取消")
				  || StringUtils.equals(confirmTypeName, "客人取消")
				  || StringUtils.equals(confirmTypeName, "取消")) {
			status = HotelOrderStatus.CANCELED;
		} else if(StringUtils.equals(confirmTypeName, "离店")){
			status = HotelOrderStatus.LEAVE;
		}
		return status;
	}
	
	public HotelOrder getLastestHotelOrderByOpenId(String openId) {
		if(StringUtils.isNotBlank(openId)) {
			HotelOrder hotelOrder = hotelOrderDao.getByProperty("openId", openId, Order.desc("createTime"));
			return hotelOrder;
		}
		return null;
	}
	
	public HotelOrder getHotelOrderByOrderSn(String orderSn) {
		if(StringUtils.isNotBlank(orderSn)) {
			HotelOrder hotelOrder = hotelOrderDao.getByProperty("orderSn", orderSn);
			
			if(hotelOrder != null) {
				List<HotelOrderVO> hotelOrderVOs = listHotelOrderVO(hotelOrder.getCompanyId(), hotelOrder.getOrderSn(), hotelOrder.getOpenId());
				if(hotelOrderVOs != null && hotelOrderVOs.size() > 0) {
					HotelOrderVO hotelOrderVO = hotelOrderVOs.get(0);
					if(hotelOrderVO != null) {
						System.out.println(orderSn + "-----------" + hotelOrderVO.getConfirmTypeName() + "-------" + hotelOrderVO.getConfirmationID());
						hotelOrder.setStatus(transferOrderStatus(hotelOrderVO.getConfirmTypeName()));
					}
				}
			}
			return hotelOrder;
		}
		return null;
	}

	/**
	 * 取消订单
	 * @param companyId
	 * @param openId
	 * @param orderSn
	 * @return
	 */
	public Boolean cancelHotelOrder(String companyId, String openId, String orderSn, String cancelReason) {
		
		HotelOrder hotelOrder = getHotelOrderByOrderSn(orderSn);
		if(hotelOrder != null && StringUtils.equals(openId, hotelOrder.getOpenId())) { 
			hotelOrder.setStatus(HotelOrderStatus.CANCELED);
			hotelOrder.setTradeStatus(TradeStatus.CLOSED);
			hotelOrder.setCancelReason(cancelReason);
			InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
			
			if(interfaceConfig != null) {
				try {
					boolean isOk = true;
					if(StringUtils.isNotBlank(hotelOrder.getHotelOrderNo())) {
						if(StringUtils.equals(interfaceConfig.getChannel(), "pms")){
							JXDPmsOrderService pmsOrderService = new JXDPmsOrderService();
							isOk = pmsOrderService.cancelHotelOrder(hotelOrder.getHotelOrderNo(), cancelReason,interfaceConfig.getCompany().getCode(),interfaceConfig.getSign(),interfaceConfig.getHost());
						}else{//E云通
							JXDOrderService orderService = new JXDOrderService();
							isOk = orderService.cancelHotelOrder(hotelOrder.getHotelOrderNo(), cancelReason,interfaceConfig.getHost());
						}
						
//						Company company = companyService.getCompanyById(companyId);
//						JXDMemberService memberService = new JXDMemberService();
//						Member member = this.memberService.getMemberByOpendId(openId);
//						if(isOk && member!=null && StringUtils.isNotBlank(hotelOrder.getCouponCode())){
							
//							if(StringUtils.isBlank(member.getProfileId())){
//								MemberVO memberVO = memberService.getMemberByWeixinId(openId, company.getCode());
//								if(memberVO!=null){
//									member.setProfileId(memberVO.getProfileId());
//									this.memberService.saveMember(member);
//								}
//							}
							
//							SysParamConfig sysParamConfig = sysParamConfigService.getSysParamConfig(companyId);
//							if(sysParamConfig==null || !Boolean.FALSE.equals(sysParamConfig.getIsRefund())){
//								if(StringUtils.isNotBlank(member.getProfileId())){
//									if(StringUtils.equals("pms", interfaceConfig.getChannel())){
//										JXDPmsMemberService jxdPmsMemberService = new JXDPmsMemberService();
//										jxdPmsMemberService.memberTrade(member.getProfileId(), company.getOutletCode(), "31", 1f, null, "订单取消，补发优惠券","WX", company.getCode(),"30",interfaceConfig.getHost(),interfaceConfig.getSign());//？？？？？？？？？？？？？？
//										
//										if(hotelOrder.getIncamount()!=null && hotelOrder.getIncamount()>0){
//											jxdPmsMemberService.memberTrade(member.getProfileId(), company.getOutletCode(), "02", hotelOrder.getIncamount(), null, "订单取消，返回返现", "WX", company.getCode(),"01",interfaceConfig.getHost(),interfaceConfig.getSign());
//										}
//									}else{
//										memberService.sendMemberCoupon(member, hotelOrder.getCouponCode(), 1, null, "订单取消，补发优惠券",company.getCode(),1);
//										
//										if(hotelOrder.getIncamount()!=null && hotelOrder.getIncamount()>0){
//											memberService.memberTrade(member.getProfileId(), company.getOutletCode(), "18", hotelOrder.getIncamount(), null, "订单取消，返回返现", "WX", company.getCode());
//										}
//									}
//								}
//							}
//						}
					}
					if(isOk){
						orderRefund(hotelOrder);
						saveHotelOrder(hotelOrder);
					}
					return isOk;
				} catch (Exception e) {
					logger.error("酒点订单取消接口出错！");
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
