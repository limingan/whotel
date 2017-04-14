package com.whotel.card.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.card.entity.ContactAddress;
import com.whotel.card.entity.Member;
import com.whotel.card.enums.CouponStates;
import com.whotel.card.service.MemberTradeService;
import com.whotel.common.enums.TradeType;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.SysParamConfig;
import com.whotel.company.service.SysParamConfigService;
import com.whotel.front.controller.FanBaseController;
import com.whotel.front.entity.WeixinFan;
import com.whotel.hotel.entity.HotelOrder;
import com.whotel.hotel.service.HotelOrderService;
import com.whotel.hotel.service.HotelService;
import com.whotel.thirdparty.jxd.mode.ExchangeGiftQuery;
import com.whotel.thirdparty.jxd.mode.vo.ChargeMoneyVO;
import com.whotel.thirdparty.jxd.mode.vo.ExchangeGiftVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberCouponVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberTradeVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;
import com.whotel.thirdparty.jxd.mode.vo.PointRecordVO;
import com.whotel.weixin.service.WeixinInterfaceService;
import com.whotel.weixin.service.WeixinMessageService;

@Controller
public class MemberTradeController extends FanBaseController {
	
	@Autowired
	private MemberTradeService memberTradeService;
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	WeixinInterfaceService weixinInterfaceService;
	
	@Autowired
	private WeixinMessageService weixinMessageService;
	
	@Autowired
	private SysParamConfigService sysParamConfigService;
	
	@RequestMapping("/oauth/member/balanceTrade")
	public String balanceTrade(TradeType tradeType, HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		MemberVO memberVO = getCurrentMemberVO(req);
		req.setAttribute("memberVO", memberVO);
		List<MemberTradeVO> tradeVOs = memberTradeService.findMemberTradeVO(company.getId(), memberVO, null,company.getCode());
		List<MemberTradeVO> tradeLogVOs = new ArrayList<MemberTradeVO>();
		if(tradeVOs != null) {
			for(MemberTradeVO tradeVO:tradeVOs) {
				float amount = tradeVO.getAmount();
				if(TradeType.CHARGE.equals(tradeType)) {
					if(amount > 0) {
						tradeLogVOs.add(tradeVO);
					}
				} else if(TradeType.DEDUCT.equals(tradeType)) {
					if(amount < 0) {
						tradeLogVOs.add(tradeVO);
					}
				} else {
					tradeLogVOs.add(tradeVO);
				}
			}
		}
		req.setAttribute("tradeVOs", tradeLogVOs);
		req.setAttribute("tradeType", tradeType);
		return "/front/card/balance_trade";
	}
	
	@RequestMapping("/oauth/member/cashTrade")
	public String cashTrade(TradeType tradeType, HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		MemberVO memberVO = getCurrentMemberVO(req);
		req.setAttribute("memberVO", memberVO);
		
		List<MemberTradeVO> tradeVOs = memberTradeService.findMemberTradeVO(company.getId(), memberVO, "18",company.getCode());
		List<MemberTradeVO> tradeLogVOs = new ArrayList<MemberTradeVO>();
		Float totalAmount = 0f;
		if(tradeVOs != null) {
			for(MemberTradeVO tradeVO:tradeVOs) {
				float amount = tradeVO.getAmount();
				if(TradeType.CHARGE.equals(tradeType)) {
					if(amount > 0) {
						tradeLogVOs.add(tradeVO);
						totalAmount +=totalAmount+amount;
					}
				} else if(TradeType.DEDUCT.equals(tradeType)) {
					if(amount < 0) {
						tradeLogVOs.add(tradeVO);
						totalAmount +=totalAmount+amount;
					}
				} else {
					totalAmount = memberVO.getIncamount();
					tradeLogVOs.add(tradeVO);
				}
			}
		}
		req.setAttribute("totalAmount", totalAmount);
		req.setAttribute("tradeVOs", tradeLogVOs);
		req.setAttribute("tradeType", tradeType);
		return "/front/card/cash_trade";
	}
	
	@RequestMapping("/oauth/member/creditTrade")
	public String creditTrade(TradeType tradeType,String message, HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		MemberVO memberVO = getCurrentMemberVO(req);
		float canUseScore = memberTradeService.getCanUseScoreByProfileId(company.getId(),memberVO.getProfileId(),company.getCode());
		memberVO.setCanUseScore(canUseScore<0?0:canUseScore);
		req.setAttribute("memberVO", memberVO);
		List<ExchangeGiftVO> giftVOs = memberTradeService.findExchangeGiftVO(company.getId(),company.getCode());
		req.setAttribute("giftVOs", giftVOs);
		req.setAttribute("message", message);
		return "/front/card/credit_trade2";
	}
	
	@RequestMapping("/oauth/member/creditTradeDetail")
	public String creditTradeDetail(TradeType tradeType, HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		MemberVO memberVO = getCurrentMemberVO(req);
		req.setAttribute("memberVO", memberVO);
		
		List<PointRecordVO> pointVOs = null; 
		List<PointRecordVO> pointExchangeVOs = null;
		
		List<PointRecordVO> pointTradeVOs = new ArrayList<PointRecordVO>();
//		if(TradeType.CHARGE.equals(tradeType)){
//			pointVOs = memberTradeService.findMemberPointVO(company.getId(), memberVO,company.getCode());
//			if(pointVOs!=null){
//				pointTradeVOs.addAll(pointVOs);
//			}
//		}else if(TradeType.DEDUCT.equals(tradeType)){
//			pointExchangeVOs = memberTradeService.findMemberPointExchangeVO(company.getId(), memberVO,company.getCode());
//			if(pointExchangeVOs!=null){
//				pointTradeVOs.addAll(pointExchangeVOs);
//			}
//		}else{
//			pointVOs = memberTradeService.findMemberPointVO(company.getId(), memberVO,company.getCode());
//			if(pointVOs!=null){
//				pointTradeVOs.addAll(pointVOs);
//			}
//			pointExchangeVOs = memberTradeService.findMemberPointExchangeVO(company.getId(), memberVO,company.getCode());
//			if(pointExchangeVOs!=null){
//				pointTradeVOs.addAll(pointExchangeVOs);
//			}
//		}
		if(TradeType.DEDUCT.equals(tradeType)){
			pointExchangeVOs = memberTradeService.findMemberPointExchangeVO(company.getId(), memberVO,company.getCode());
			if(pointExchangeVOs!=null){
				pointTradeVOs.addAll(pointExchangeVOs);
			}
		}else{
			pointVOs = memberTradeService.findMemberPointVO(company.getId(), memberVO,company.getCode());
			if(pointVOs!=null){
				pointTradeVOs.addAll(pointVOs);
			}
		}
		
		for (PointRecordVO vo : pointTradeVOs) {
			if(vo.getRemark()!=null&&vo.getRemark().indexOf("粉丝")>0&&vo.getRemark().indexOf(",")>0){
				String[] openid = vo.getRemark().split(",");
				WeixinFan weixinFan = weixinFanService.getWeixinFanByOpenId(openid[1]);
				if(weixinFan!=null&&weixinFan.getNickName()!=null){
					vo.setRemark("推荐粉丝:"+weixinFan.getNickName());
				}else{
					vo.setRemark("推荐粉丝");
				}
			}
		}
		
		req.setAttribute("pointVOs", pointTradeVOs);
		req.setAttribute("tradeType", tradeType);
		return "/front/card/credit_trade_detail";
	}
	
	@RequestMapping("/oauth/member/memberCoupon")
	public String memberCoupon(CouponStates couponState,HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		Member member = getCurrentMember(req);
		if(couponState==null){
			couponState = CouponStates.NOT_USED;
		}
		List<MemberCouponVO> couponVOs = memberTradeService.findMemberCouponVO(company.getId(), member.getProfileId(),company.getCode(),couponState,null);
		req.setAttribute("couponVOs", couponVOs);
		req.setAttribute("couponState", couponState);
		return "/front/card/coupon_list";
	}
	
	@RequestMapping("/oauth/member/toCreditExchange")
	public String toCreditExchange(HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		WeixinFan fan = getCurrentFan(req);
		MemberVO memberVO = getCurrentMemberVO(req);
		
		List<ExchangeGiftVO> giftVOs = memberTradeService.findExchangeGiftVO(company.getId(),company.getCode());
		req.setAttribute("giftVOs", giftVOs);
		
		float canUseScore = memberTradeService.getCanUseScoreByProfileId(company.getId(),memberVO.getProfileId(),company.getCode());
		memberVO.setCanUseScore(canUseScore<0?0:canUseScore);
		req.setAttribute("memberVO", memberVO);
		
		Member member = getCurrentMember(req);
		req.setAttribute("memberName", member.getName());
		
		ContactAddress contactAddress = memberService.getDefContactAddress(fan.getOpenId());
		req.setAttribute("contactAddress", contactAddress);
		
		List<ContactAddress> contactAddressList = memberService.findContactAddressByOpendId(fan.getOpenId());
		req.setAttribute("contactAddressList", contactAddressList);
		
		List<HotelVO> hotelVOs = hotelService.listHotelVO(company.getId());
		req.setAttribute("hotelVOs", hotelVOs);
		return "/front/card/credit_exchange";
	}
	
	@RequestMapping("/oauth/member/toCreditExchangeDetail")
	public String toCreditExchangeDetail(String itemId,HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		WeixinFan fan = getCurrentFan(req);
		MemberVO memberVO = getCurrentMemberVO(req);
		
		ExchangeGiftVO exchangeGiftVO = memberTradeService.getExchangeGiftVO(itemId,company.getCode());
		req.setAttribute("exchangeGiftVO", exchangeGiftVO);
		
		float canUseScore = memberTradeService.getCanUseScoreByProfileId(company.getId(),memberVO.getProfileId(),company.getCode());
		memberVO.setCanUseScore(canUseScore<0?0:canUseScore);
		req.setAttribute("memberVO", memberVO);
		
		Member member = getCurrentMember(req);
		req.setAttribute("memberName", member.getName());
		
		ContactAddress contactAddress = memberService.getDefContactAddress(fan.getOpenId());
		req.setAttribute("contactAddress", contactAddress);
		
		List<ContactAddress> contactAddressList = memberService.findContactAddressByOpendId(fan.getOpenId());
		req.setAttribute("contactAddressList", contactAddressList);
		
		List<HotelVO> hotelVOs = hotelService.listHotelVO(company.getId());
		req.setAttribute("hotelVOs", hotelVOs);
		return "/front/card/credit_exchange_detail";
	}
	
	@RequestMapping("/pay/toCardCharge")
	public String toCardCharge(HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		MemberVO memberVO = getCurrentMemberVO(req);
		WeixinFan fan = getCurrentFan(req);
		List<ChargeMoneyVO> chargeMoneys = memberTradeService.getChargeMoneyVO(company.getId(), company.getCode(),
				fan.getOpenId());
		SysParamConfig sysParamConfig = sysParamConfigService.getSysParamConfig(company.getId());
		req.setAttribute("memberVO", memberVO);
		req.setAttribute("sysParamConfig", sysParamConfig);
		req.setAttribute("chargeMoneys", chargeMoneys);
		return "/front/card/balance_charge2";
	}
	
	@RequestMapping("/oauth/member/toCardCharge")
	public String toMbrCardCharge(HttpServletRequest req) {
		return "redirect:/pay/toCardCharge.do?showwxpaytitle=1";
	}
	
	@RequestMapping("/oauth/member/creditExchange")
	public String creditExchange(ExchangeGiftQuery query,String contactAddressId, HttpServletRequest req) throws UnsupportedEncodingException {
		Company company = getCurrentCompany(req);
		WeixinFan fan = getCurrentFan(req);
		boolean rs = memberTradeService.exchangeGift(company.getId(), fan.getOpenId(),contactAddressId, query,company.getCode());
		String message = null;
		if(rs) {
			message = "兑换申请提交成功，请您耐心等待酒店审批！";
			//发送积分兑换消息
			weixinMessageService.sendCreditExchangeMsg(company,fan,query);
		} else {
			message = "兑换失败，请重试！";
		}
//		return "redirect:/oauth/member/toCreditExchange.do?message="+URLEncoder.encode(message, "UTF8");
		return "redirect:/oauth/member/creditTrade.do?message="+URLEncoder.encode(message, "UTF8");
	}
}
