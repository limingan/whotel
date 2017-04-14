package com.whotel.hotel.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.card.entity.Member;
import com.whotel.card.service.MemberTradeService;
import com.whotel.common.base.Constants;
import com.whotel.common.enums.PayMent;
import com.whotel.common.enums.PayMode;
import com.whotel.common.enums.TradeType;
import com.whotel.common.util.EncryptUtil;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.PayConfig;
import com.whotel.company.enums.PayType;
import com.whotel.company.service.PayConfigService;
import com.whotel.front.controller.FanBaseController;
import com.whotel.front.entity.PayOrder;
import com.whotel.front.entity.WeixinFan;
import com.whotel.front.service.PayOrderService;
import com.whotel.thirdparty.jxd.mode.vo.MbrCardVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;

@RequestMapping("/pay")
@Controller
public class CashierController extends FanBaseController {
	
	@Autowired
	private PayOrderService payOrderService;
	
	@Autowired
	private MemberTradeService memberTradeService;
	
	@Autowired
	private PayConfigService payConfigService;
	
	@RequestMapping("/toCashierDesk")
	public String toCashierDesk(HttpServletRequest req) {
		MemberVO memberVO = getCurrentMemberVO(req);
		Member member = getCurrentMember(req);
		req.setAttribute("memberVO", memberVO);
		req.setAttribute("member", member);
		req.setAttribute("weixinPay", checkWxPay(getCurrentCompany(req).getId()));
		return "/front/hotel/cashier_desk";
	}
	
	@RequestMapping("/toTicketCashierDesk")
	public String ticketToCashierDesk(HttpServletRequest req) {
		MemberVO memberVO = getCurrentMemberVO(req);
		Member member = getCurrentMember(req);
		req.setAttribute("memberVO", memberVO);
		req.setAttribute("member", member);
		req.setAttribute("weixinPay", checkWxPay(getCurrentCompany(req).getId()));
		return "/front/ticket/cashier_desk";
	}
	
	@RequestMapping("/toComboCashierDesk")
	public String toComboCashierDesk(HttpServletRequest req) {
		MemberVO memberVO = getCurrentMemberVO(req);
		Member member = getCurrentMember(req);
		req.setAttribute("memberVO", memberVO);
		req.setAttribute("member", member);
		req.setAttribute("weixinPay", checkWxPay(getCurrentCompany(req).getId()));
		return "/front/combo/cashier_desk";
	}
	
	/**
	 * 餐饮订单支付
	 * @return
	 */
	@RequestMapping("/toMealCashierDesk")
	public String toMealCashierDesk(HttpServletRequest req) {
		MemberVO memberVO = getCurrentMemberVO(req);
		Member member = getCurrentMember(req);
		req.setAttribute("memberVO", memberVO);
		req.setAttribute("member", member);
		req.setAttribute("weixinPay", checkWxPay(getCurrentCompany(req).getId()));
		return "/front/meal/cashier_desk";
	}
	
	@RequestMapping("/balancePay")
	@ResponseBody
	public String balancePay(String payPwd,String mbrCardNo, HttpServletRequest req) throws UnsupportedEncodingException {
		Member member = getCurrentMember(req);
		if(!StringUtils.equals(member.getPayPwd(), EncryptUtil.md5(payPwd))) {
			return "支付密码不正确";
		}
		
		PayOrder payOrder = (PayOrder) req.getSession().getAttribute(Constants.Session.PAY_ORDER);
		if(payOrder == null || !payOrderService.checkPayOrder(payOrder)) {
			return "订单数据错误，请重新下单！";
		}
		
		MemberVO memberVO = getCurrentMemberVO(req);
		Company company =getCurrentCompany(req);
		String subProfileId = null;
		if(StringUtils.isNotBlank(mbrCardNo)){
			MbrCardVO mbrCardVO = memberTradeService.getMbrCardVO(company,memberVO.getProfileId(), mbrCardNo);
			if(mbrCardVO==null){
				return "订单数据错误，卡号找不到，请重新下单！";
			}else if(mbrCardVO.getBalance()<(payOrder.getTotalFee()/100)){
				return "余额不足";
			}
			subProfileId = mbrCardVO.getProfileId();
		}else{
			if(memberVO.getBalance()<(payOrder.getTotalFee()/100)){
				return "余额不足";
			}
		}
		
		WeixinFan fan = getCurrentFan(req);
		
		payOrder.setOpenId(fan.getOpenId());
		payOrder.setCompanyId(company.getId());
		payOrder.setMbrCardNo(mbrCardNo);
		payOrder.setPayMent(PayMent.BALANCEPAY);
		payOrderService.savePayOrder(payOrder);
		String tradeNo = payOrder.getOrderSn();
		boolean memberTrade = memberTradeService.memberTrade(tradeNo, fan.getOpenId(), payOrder.getTotalFee(), TradeType.DEDUCT, payOrder.getRemark(),subProfileId);
		if(memberTrade) {
			boolean rs = payOrderService.handlePayOrder(tradeNo, tradeNo, PayMent.BALANCEPAY);
			if(rs) {
				PayMode payMode = payOrder.getPayMode();
				if(PayMode.BOOKHOTEL.equals(payMode)||PayMode.TICKETBOOK.equals(payMode)||PayMode.BOOKMEAL.equals(payMode)||PayMode.COMBOBOOK.equals(payMode)) {
					return "true";
				}
			}
		}
		
		return "支付失败，请重新下单";
	}
	
	private Boolean checkWxPay(String companyId){
		List<PayConfig> payConfigs = payConfigService.findPayConfigAll(companyId);
		Boolean weixinPay = false;
		for (PayConfig payConfig : payConfigs) {
			if((payConfig.getPayType()==PayType.WX || payConfig.getPayType()==PayType.WX_PROVIDER) && payConfig.getValid()==true){
				weixinPay = true;
				break;
			}
		}
		return weixinPay;
	}
}
