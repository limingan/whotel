package com.whotel.front.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.meal.entity.MealOrder;
import com.whotel.meal.enums.MealOrderStatus;
import com.whotel.meal.service.MealOrderService;
import com.whotel.card.entity.Member;
import com.whotel.card.service.MemberService;
import com.whotel.card.service.MemberTradeService;
import com.whotel.common.base.Constants;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.PayMent;
import com.whotel.common.enums.PayMode;
import com.whotel.common.enums.TradeStatus;
import com.whotel.common.enums.TradeType;
import com.whotel.common.util.BeanUtil;
import com.whotel.common.util.MoneyUtil;
import com.whotel.company.entity.Company;
import com.whotel.company.service.CompanyService;
import com.whotel.front.dao.OrderSnDao;
import com.whotel.front.dao.PayOrderDao;
import com.whotel.front.entity.PayOrder;
import com.whotel.hotel.entity.Hotel;
import com.whotel.hotel.entity.HotelOrder;
import com.whotel.hotel.entity.HotelOtherService;
import com.whotel.hotel.entity.RoomPrice;
import com.whotel.hotel.enums.HotelOrderStatus;
import com.whotel.hotel.enums.SynStatus;
import com.whotel.hotel.service.HotelOrderService;
import com.whotel.hotel.service.HotelService;
import com.whotel.system.entity.SysMemberLog;
import com.whotel.system.service.SystemLogService;
import com.whotel.thirdparty.jxd.mode.MbrCardUpgrade;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;
import com.whotel.weixin.service.WeixinMessageService;

import net.sf.json.JSONObject;

/**
 * 支付订单服务实现类
 * @author 冯勇
 *
 */
@Service
public class PayOrderService {
	
	private static final Logger logger = Logger.getLogger(PayOrderService.class);

	@Autowired
	private PayOrderDao payOrderDao;
	
	@Autowired
	private OrderSnDao orderSnDao;
	
	@Autowired
	private HotelOrderService hotelOrderService;
	
	@Autowired
	private MemberTradeService memberTradeService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private WeixinMessageService weixinMessageService;
	
	@Autowired
	private SystemLogService systemLogService;
	
	@Autowired
	private MealOrderService mealOrderService;
	
	@Autowired
	private HotelService hotelService;
	
	public String genPayOrderSn() {
		long sn = orderSnDao.getOrderSn();
		return Constants.OrderSnPre.PAY_SN + String.format("%0"+Constants.OrderSnPre.SN_LENGTH+"d", sn);
	}
	
	public void savePayOrder(PayOrder payOrder) {
		if(payOrder != null) {
			String orderSn = payOrder.getOrderSn();
			if(StringUtils.isBlank(orderSn)) {
				orderSn = genPayOrderSn();
			}
			payOrder.setOrderSn(orderSn);
			PayOrder oldPayOrder = getPayOrderByOrderSn(orderSn);
			if(oldPayOrder != null) {
				BeanUtil.copyProperties(payOrder, oldPayOrder);
			} else {
				payOrder.setStatus(TradeStatus.WAIT_PAY);
				payOrder.setCreateTime(new Date());
			}
			payOrderDao.save(payOrder);
		}
	}

	public void deletePayOrder(String id) {
		if(StringUtils.isNotBlank(id)) {
			PayOrder payOrder = getPayOrderById(id);
			if(payOrder != null) {
				payOrderDao.delete(payOrder);
			}
		}
	}

	public void deletePayOrder(PayOrder payOrder) {
		if(payOrder != null) {
			payOrderDao.delete(payOrder);
		}
	}
	
	public void deleteMorePayOrder(String[] ids) {
		if(ids != null) {
			for(String id:ids) {
				deletePayOrder(id);
			}
		}
	}

	public List<PayOrder> findAllPayOrders() {
		return payOrderDao.findAll();
	}

	public void findPayOrders(Page<PayOrder> page) {
		payOrderDao.find(page);
	}

	public PayOrder getPayOrderById(String id) {
		if(StringUtils.isNotBlank(id)) {
			return payOrderDao.get(id);
		}
		return null;
	}
	
	public PayOrder getPayOrderByOrderSn(String orderSn) {
		if(StringUtils.isNotBlank(orderSn)) {
			return payOrderDao.getByProperty("orderSn", orderSn);
		}
		return null;
	}
	
	public boolean checkPayOrder(PayOrder payOrder) {
		//可以检测储值余额与支付金额 之和是否足够购买商品
		if(payOrder != null && payOrder.getTotalFee() != null) {// && payOrder.getTotalFee() >= 0
			return true;
		}
		return false;
	}
	
	public boolean handlePayOrder(String orderSn, String tradeSn, PayMent payMent) {
		boolean rs = false;
		if(StringUtils.isNotBlank(orderSn)) {
			PayOrder payOrder = getPayOrderByOrderSn(orderSn);
			if(payOrder != null) {
				if(TradeStatus.FINISHED.equals(payOrder.getStatus())) {
					logger.info("order has pay success!"); //支付业务已经处理，无需再处理
					rs = true;
				} else {
					String openId = "";
					String profileId = "";//会员id
					String couponSeqid = "";//优惠劵
					Float incamount = 0f;//返现
					String incamountRemark = "";//返现的备注
					String hotelCode = "";//酒店code
					
					if(PayMode.BOOKHOTEL.equals(payOrder.getPayMode())) {
						// 订房成功处理
						HotelOrder hotelOrder = hotelOrderService.getHotelOrderById(payOrder.getBusinessId());
						hotelOrder.setStatus(HotelOrderStatus.CONFIRMED);
						hotelOrder.setTradeSn(tradeSn);
						hotelOrder.setTradeStatus(TradeStatus.FINISHED);
						hotelOrder.setPayMent(payMent);
						hotelOrder.setPayFee(MoneyUtil.round(payOrder.getTotalFee() / 100f));
						hotelOrderService.saveHotelOrder(hotelOrder);
						hotelOrder.setOrderOperate("Add");
						rs = hotelOrderService.synchronizeHotelOrderToJXD(hotelOrder);
						
						couponSeqid = hotelOrder.getCouponSeqid();
						openId = hotelOrder.getOpenId();
						incamount = hotelOrder.getIncamount();
						incamountRemark = hotelOrder.getName()+"使用返现";
						hotelCode = hotelOrder.getHotelCode();
						profileId = hotelOrder.getProfileId();
//						Company company = companyService.getCompanyById(hotelOrder.getCompanyId());
//						if(StringUtils.isNotBlank(couponSeqid)) {
//							memberTradeService.useMemberCoupon(hotelOrder.getCompanyId(), couponSeqid, "预订房型使用券", hotelOrder.getOpenId(),company.getCode());  //核销券
//						}
//						if(hotelOrder.getIncamount()>0){//返现
//							rs = memberTradeService.useMemberIncamount(company.getId(),hotelOrder.getProfileId(), company.getOutletCode(),hotelOrder.getIncamount(),company.getCode());
//						}
						if(rs){
							weixinMessageService.sendRoomBookMessage(orderSn);
						}
					} else if(PayMode.BOOKMEAL.equals(payOrder.getPayMode())) {
						// 订餐成功处理
						MealOrder mealOrder = mealOrderService.getMealOrderById(payOrder.getBusinessId());
						mealOrder.setStatus(MealOrderStatus.NOARRIVE);
						mealOrder.setTradeStatus(TradeStatus.FINISHED);
						mealOrder.setTradeSn(tradeSn);
						mealOrder.setConfirmationID(orderSn);
						mealOrder.setPayMent(payMent);
						mealOrder.setPayFee(MoneyUtil.round(payOrder.getTotalFee() / 100f));
						mealOrderService.saveMealOrder(mealOrder);
						rs = mealOrderService.synchronizeMealOrderToJXD(mealOrder);
						
						
						couponSeqid = mealOrder.getCouponSeqid();
						openId = mealOrder.getOpenId();
//						Company company = companyService.getCompanyById(mealOrder.getCompanyId());
//						if(StringUtils.isNotBlank(couponSeqid)) {
//							memberTradeService.useMemberCoupon(mealOrder.getCompanyId(), couponSeqid, "预订房型使用券", mealOrder.getOpenId(),company.getCode());  //核销券
//						}
						if(rs){
							weixinMessageService.sendMealOrderMsgToCompany(orderSn);
						}
					} else if(PayMode.BOOKGOODS.equals(payOrder.getPayMode())) {
						
					} else if(PayMode.TICKETBOOK.equals(payOrder.getPayMode())) {
						
					} else if(PayMode.SCENIC_TICKET_BOOK.equals(payOrder.getPayMode())){
						//景区门票预订
					} else if(PayMode.CHARGE.equals(payOrder.getPayMode())) {
						// 充值成功处理
						rs = memberTradeService.memberTrade(orderSn, payOrder.getOpenId(), payOrder.getTotalFee(), TradeType.CHARGE, "充值",null);
						
					} else if(PayMode.RESERVATION.equals(payOrder.getPayMode())) {
						
					}else if(PayMode.MBRUPGRADE.equals(payOrder.getPayMode())){
						Company company = companyService.getCompanyById(payOrder.getCompanyId());
						MemberVO memberVO = memberService.getMemberVOByOpenId(company.getId(), payOrder.getOpenId(), company.getCode());
						MbrCardUpgrade upgrade = new MbrCardUpgrade();
						upgrade.setOldMbrCardType(memberVO.getMbrCardType());
						upgrade.setUpgradetype(1);//按售卖金额
						upgrade.setProfileid(memberVO.getProfileId());//44615001
						upgrade.setAmt(Float.valueOf(payOrder.getTotalFee())/100);
						upgrade.setNewMbrCardType(payOrder.getMbrCardTypeCode());
						rs = memberService.mbrCardUpgrade(company.getId(),company.getCode(),upgrade);
					}else if(PayMode.PAY.equals(payOrder.getPayMode())){
						payOrder.setRemark("微信支付完成！");
						weixinMessageService.sendPaySuccessToCompany(orderSn);
						rs = true;
						/*if(PayMode.BOOKHOTEL.equals(payOrder.getPayMode())) {//客房
							HotelOrder hotelOrder = hotelOrderService.getHotelOrderById(payOrder.getBusinessId());
							couponSeqid = hotelOrder.getCouponSeqid();
							openId = hotelOrder.getOpenId();
							incamount = hotelOrder.getIncamount();
							profileId = hotelOrder.getProfileId();
						}*/
					}else if(PayMode.COMBOBOOK.equals(payOrder.getPayMode())){
						
					}
					payOrder.setStatus(TradeStatus.FINISHED);
					
					Company company = companyService.getCompanyById(payOrder.getCompanyId());
					if(StringUtils.isNotBlank(couponSeqid)) {
						memberTradeService.useMemberCoupon(company.getId(), couponSeqid, "预订房型使用券,订单号:"+orderSn, openId,company.getCode());  //核销券
					}
					if(incamount!=null && incamount>0){//返现
						Hotel hotel = hotelService.getHotel(payOrder.getCompanyId(), hotelCode);
						incamountRemark = hotel.getName()+incamountRemark;
						rs = memberTradeService.useMemberIncamount(company.getId(),profileId, company.getOutletCode(),incamount,company.getCode(),orderSn,incamountRemark);
					}
					
					if(rs) {
						payOrder.setTradeSn(tradeSn);
						payOrder.setWxnotifyState(true);
						payOrderDao.save(payOrder);
					}else{
						payOrder.setTradeSn(tradeSn);
						payOrder.setWxnotifyState(false);
						payOrderDao.save(payOrder);
					}
				}
			}
		}
		return rs;
	}
	
	
	
}
