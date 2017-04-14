package com.whotel.card.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.card.entity.ContactAddress;
import com.whotel.card.entity.Guest;
import com.whotel.card.entity.MbrCardType;
import com.whotel.card.entity.Member;
import com.whotel.card.entity.MemberPolicy;
import com.whotel.card.entity.SignInRecord;
import com.whotel.card.service.MemberTradeService;
import com.whotel.common.base.Constants;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.FilterModel;
import com.whotel.common.util.EncryptUtil;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.CompanyNotice;
import com.whotel.company.entity.SysParamConfig;
import com.whotel.company.service.CompanyNoticeService;
import com.whotel.company.service.SysParamConfigService;
import com.whotel.front.controller.FanBaseController;
import com.whotel.front.entity.WeixinFan;
import com.whotel.thirdparty.jxd.mode.MbrCardUpgradeQuery;
import com.whotel.thirdparty.jxd.mode.vo.CategoryCodeVO;
import com.whotel.thirdparty.jxd.mode.vo.GeneralMsg;
import com.whotel.thirdparty.jxd.mode.vo.MbrCardVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;
import com.whotel.weixin.service.WeixinMessageService;

@Controller
public class MemberController extends FanBaseController {
	
	@Autowired
	private CompanyNoticeService noticeService;
	
	@Autowired
	private MemberTradeService memberTradeService;
	
	@Autowired
	private SysParamConfigService sysParamConfigService;
	
	@Autowired
	private WeixinMessageService weixinMessageService;
	
	@RequestMapping("/oauth/member/index")
	public String index(HttpServletRequest req) {
		WeixinFan fan = getCurrentFan(req);
		Member member = getCurrentMember(req);
		Company company = getCurrentCompany(req);
		MemberVO memberVO = null;
		if(member != null) {
			memberVO = memberService.getMemberVOByMobile(member.getCompanyId(), member.getOpenId(), member.getMobile(),member.getCompany().getCode());
			req.setAttribute("memberVO", memberVO);
			
			CompanyNotice companyNotice = noticeService.getLastestCompanyNotice(member.getCompanyId());
			req.setAttribute("companyNotice", companyNotice);
		}
		
		if(member==null || memberVO==null){
			return "redirect:/oauth/member/toBindMember.do";
		}
		
		req.setAttribute("member", member);
		req.setAttribute("fan", fan);
		
		if(memberVO!=null && memberVO.getSubCardCount()>0){
			List<MbrCardVO> mbrCards = memberTradeService.findMbrCardQuery(company, memberVO.getProfileId());
			req.setAttribute("mbrCards", mbrCards);
		}
		req.setAttribute("multipleMbr", Boolean.TRUE.equals(company.getMultipleMbr()));
		
		if(member!=null && memberVO!=null && StringUtils.isBlank(member.getProfileId())){
			member.setProfileId(memberVO.getProfileId());
			memberService.saveMember(member);
		}
		
		SysParamConfig sysParamConfig = sysParamConfigService.getSysParamConfig(company.getId());
		req.setAttribute("sysParamConfig", sysParamConfig);
		if(company.getCode().equals("tmjt")){
			return "/front/card/index3";
		}else{
			return "/front/card/index"+getCurrentCompany(req).getTheme();
		}
		//return "/front/card/index"+getCurrentCompany(req).getTheme();
	}
	
	@RequestMapping("/oauth/member/listCompanyNotices")
	public String listCompanyNotices(HttpServletRequest req) {
		return "/front/card/notice_list";
	}
	
	@RequestMapping("/member/loadCompanyNotices")
	@ResponseBody
	public Page<CompanyNotice> loadCompanyNotices(Page<CompanyNotice> page, HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		page.addFilter("companyId", FilterModel.EQ, company.getId());
		page.addOrder(Order.desc("createTime"));
		noticeService.findCompanyNotices(page);
		return page;
	}
	
	@RequestMapping("/oauth/member/notice")
	public String notice(String id, HttpServletRequest req) {
		CompanyNotice companyNotice = noticeService.getCompanyNoticeById(id);
		req.setAttribute("companyNotice", companyNotice);
		return "/front/card/notice";
	}
	
	@RequestMapping("/oauth/member/toBindMember")
	public String toBindMember(String toRemindMsg,HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		if("hyyljt".equals(company.getCode())) {//
			req.setAttribute("hyyljt", true);
		}
		
		Member member = getCurrentMember(req);
		req.setAttribute("member", member);
		req.setAttribute("toRemindMsg", toRemindMsg);
		return "/front/card/mobile_bind";
	}
	
	@RequestMapping("/oauth/member/toMbrCardBind")
	public String toMbrCardBind(HttpServletRequest req) {
		
		return "/front/card/mbrCard_bind";
	}
	
	@RequestMapping("/oauth/member/bindMember")
	@ResponseBody
	public String bindMember(Member member, String action, HttpServletRequest req) throws UnsupportedEncodingException {
		MemberVO memberVO = null;
		Company company = getCurrentCompany(req);
		
		if("hyyljt".equals(company.getCode())) {//
			//华义裕林度假村注册会员必须在线下存在会员才能在线上注册
			boolean result = this.memberService.checkRegisterInfoOfHyyljt(member);
			if(!result) {
				return "卡号与手机号不匹配！";
			}
		}
		
		WeixinFan fan = getCurrentFan(req);
		Member oldMember = memberService.getMemberByOpendId(fan.getOpenId());
		if(oldMember!=null){
			member.setId(oldMember.getId());
		}
		member.setOpenId(fan.getOpenId());
		member.setCompanyId(company.getId());
		member = memberService.saveMemberAndSyncToJXD(member,action);
		req.setAttribute("member", member);
		req.setAttribute("memberVO", memberVO);
		
		if(StringUtils.equals(action, "save") || member!=null) {
//			//发送注册成功模板消息
			memberVO = getCurrentMemberVO(req);
			if(memberVO != null){
				memberVO.setWeixinId(member.getOpenId());
				weixinMessageService.sendUseRemindLoginSuccess(memberVO);
			}
			return "success";
		}
		return "会员绑定失败,请联系酒店管理员！";
	}
	
	@RequestMapping("/oauth/member/ajaxBindMember")
	@ResponseBody
	public String ajaxBindMember(Member member, HttpServletRequest req) throws UnsupportedEncodingException {
		Company company = getCurrentCompany(req);
		WeixinFan fan = getCurrentFan(req);
		Member oldMember = memberService.getMemberByOpendId(fan.getOpenId());
		if(oldMember!=null){
			//防止线上注册多条
			member.setId(oldMember.getId());
		}
		member.setOpenId(fan.getOpenId());
		member.setCompanyId(company.getId());
		member = memberService.saveMemberAndSyncToJXD(member,null);
		if(member != null) {
			return "";//member.getId()
		}
		return "会员绑定失败，请重试！";
	}
	
	@RequestMapping("/oauth/member/ajaxSkipBindMember")
	@ResponseBody
	public Boolean ajaxSkipBindMember(HttpServletRequest req) throws UnsupportedEncodingException {
		req.getSession().setAttribute(Constants.Session.SKIP_BIND, true);
		return true;
	}
	
	
	@RequestMapping("/oauth/member/fillInfo")
	public String fillInfo(HttpServletRequest req) {
		Member member = getCurrentMember(req);
		MemberVO memberVO = getCurrentMemberVO(req);
		
		String mbrExpired = memberVO.getMbrExpired().substring(0, 10);
		memberVO.setMbrExpired(mbrExpired);
		req.setAttribute("member", member);
		req.setAttribute("memberVO", memberVO);
		
		MemberPolicy memberPolicy = memberService.getMemberPolicy(member.getCompanyId());
		req.setAttribute("memberPolicy", memberPolicy);
		return "/front/card/member_info";
	}
	
	@RequestMapping("/oauth/member/card")
	public String card(HttpServletRequest req) {
		WeixinFan fan = getCurrentFan(req);
		Member member = getCurrentMember(req);
		MemberVO memberVO = getCurrentMemberVO(req);
		req.setAttribute("memberVO", memberVO);
		req.setAttribute("member", member);
		req.setAttribute("fan", fan);
		return "/front/card/card";
	}
	
	@RequestMapping("/oauth/member/toSetPayPwd")
	public String toSetPayPwd(String action, HttpServletRequest req) {
		Member member = getCurrentMember(req);
		req.setAttribute("member", member);
		return "/front/card/card_pwd";
	}
	
	@RequestMapping("/oauth/member/setPayPwd")
	public String setPayPwd(String payPwd, String oldPayPwd, String action, HttpServletRequest req) throws UnsupportedEncodingException {
		Member member = getCurrentMember(req);
		String message = "";
		if(member != null && StringUtils.isNotBlank(payPwd)) {
			String existPayPwd = member.getPayPwd();
			if(StringUtils.isNotBlank(existPayPwd) && !StringUtils.equals(existPayPwd, EncryptUtil.md5(oldPayPwd))) {
				message = "旧密码不正确！";
			} else {
				member.setPayPwd(EncryptUtil.md5(payPwd));
				memberService.saveMember(member);
				message = "支付密码设置成功";
			}
		} else {
			message = "支付密码设置失败，请重试";
		}
		if(StringUtils.equals(action, "bookRoom")) {
			return "redirect:/pay/toCashierDesk.do?showwxpaytitle=1";
		}
		if(StringUtils.equals(action, "ticketRoom")) {
			return "redirect:/pay/toTicketCashierDesk.do?showwxpaytitle=1";
		}
		if(StringUtils.equals(action, "combo")) {
			return "redirect:/pay/toComboCashierDesk.do?showwxpaytitle=1";
		}
		if(StringUtils.equals(action, "shopRoom")) {
			return "redirect:/pay/toShopCashierDesk.do?showwxpaytitle=1";
		}
		return "redirect:/oauth/member/toSetPayPwd.do?message="+URLEncoder.encode(message, "UTF8");
	}
	
	@RequestMapping("/oauth/member/toListContactAddress")
	public String listAdderss(HttpServletRequest req) {
		WeixinFan fan = getCurrentFan(req);
		List<ContactAddress> contactAddressList = memberService.findContactAddressByOpendId(fan.getOpenId());
		req.setAttribute("contactAddressList", contactAddressList);
		return "/front/card/contactAddress_list";
	}
	
	@RequestMapping("/oauth/member/toEditContactAddress")
	public String toEditContactAddress(String id, HttpServletRequest req) {
		ContactAddress contactAddress = memberService.findContactAddressById(id);
		req.setAttribute("contactAddress", contactAddress);
		return "/front/card/contactAddress_edit";
	}
	
	@RequestMapping("/oauth/member/updateContactAddress")
	public String updateContactAddress(ContactAddress contactAddress, HttpServletRequest req) {
		WeixinFan fan = getCurrentFan(req);
		if(fan!=null&&StringUtils.isNotBlank(fan.getOpenId())){
			contactAddress.setOpenId(fan.getOpenId());
		}
		
		if(contactAddress!=null&&Boolean.TRUE.equals(contactAddress.getDef())){
			ContactAddress defContactAddress = memberService.getDefContactAddress(contactAddress.getOpenId());
			if(defContactAddress!=null&&!StringUtils.equals(contactAddress.getId(), defContactAddress.getId())){
				defContactAddress.setDef(false);
				memberService.updateContactAddress(defContactAddress);
			}
		}
		
		memberService.updateContactAddress(contactAddress);
		return "redirect:/oauth/member/toListContactAddress.do";
	}
	
	@RequestMapping("/oauth/member/deleteContactAddress")
	public String deleteContactAddress(String id,HttpServletRequest req) {
		memberService.deleteContactAddress(id);
		return "redirect:/oauth/member/toListContactAddress.do";
	}
	
	@RequestMapping("/oauth/member/listGuests")
	public String listGuests(HttpServletRequest req) {
		WeixinFan fan = getCurrentFan(req);
		List<Guest> guests = memberService.findGuestByOpendId(fan.getOpenId());
		req.setAttribute("guests", guests);
		return "/front/card/guest_list";
	}
	
	@RequestMapping("/oauth/member/editGuest")
	public String editGuest(String id, HttpServletRequest req) {
		Guest guest = memberService.getGuestById(id);
		req.setAttribute("guest", guest);
		return "/front/card/guest_edit";
	}
	
	@RequestMapping("/oauth/member/updateGuest")
	public String updateGuest(Guest guest, HttpServletRequest req) {
		WeixinFan fan = getCurrentFan(req);
		if(fan!=null&&StringUtils.isNotBlank(fan.getOpenId())){
			guest.setOpenId(fan.getOpenId());
		}
		memberService.saveGuest(guest);
		return "redirect:/oauth/member/listGuests.do";
	}
	
	@RequestMapping("/oauth/member/deleteGuest")
	public String deleteGuest(String id,HttpServletRequest req) {
		memberService.deleteGuest(id);
		return "redirect:/oauth/member/listGuests.do";
	}
	
	/**
	 * 会员卡升级列表
	 */
	@RequestMapping("/pay/toMbrCardUpgrade")
	public String toMbrCardUpgrade(String mbrCardType,HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		MbrCardUpgradeQuery query = new MbrCardUpgradeQuery();
		query.setMbrCardType(mbrCardType);
		//List<MbrCardUpgradeVO> cardTypes = memberService.listMbrCardUpgrade(company.getId(),company.getCode(),query);
		req.setAttribute("cardTypes", memberService.listMbrCardUpgrade(company.getId(),company.getCode(),query));
		req.setAttribute("mbrCardType", mbrCardType);
		return "/front/card/mbrCardUpgrade_list";
	}
	
	@RequestMapping("/oauth/member/listMbrCardUpgrade")
	public String listMbrCardUpgrade(HttpServletRequest req) {
		String mbrCardType = "";
		MemberVO memberVO = getCurrentMemberVO(req);
		if(memberVO != null){
			mbrCardType = memberVO.getMbrCardType();
		}
		return "redirect:/pay/toMbrCardUpgrade.do?mbrCardType="+mbrCardType;
	}
	
	@RequestMapping("/oauth/member/ajaxCheckMbrCardType")
	@ResponseBody
	public String ajaxCheckMbrCardType(String mbrCardType,HttpServletRequest req) {
		MemberVO memberVO = getCurrentMemberVO(req);
		if(memberVO != null && StringUtils.equals(mbrCardType, memberVO.getMbrCardType())){
			return "您已购买此会员卡！无需重复购买!";
		}
		return "";
	}
	
	@RequestMapping("/oauth/member/listMbrCard")
	public String listMbrCard(HttpServletRequest req){
		Member member = getCurrentMember(req);
		Company company = getCurrentCompany(req);
		List<MbrCardVO> mbrCards = memberTradeService.findMbrCardQuery(company, member.getProfileId());
		
		List<MbrCardType> mbrCardTypes = memberTradeService.findMbrCardType(company.getId());
		Map<String,String> map = new HashMap<>();
		for (MbrCardType mbrCardType : mbrCardTypes) {
			map.put(mbrCardType.getCode(), mbrCardType.getPicUrl());
		}
		
		for (MbrCardVO mbrCardVO : mbrCards) {
			mbrCardVO.setPicUrl(map.get(mbrCardVO.getMbrCardType()));
		}
		
//		MbrCardVO vo1 = new MbrCardVO();
//		vo1.setBalance(1111f);
//		vo1.setChargeamt(1111f);
//		vo1.setIncamount(1111f);
//		vo1.setBaseamtbalance(11111f);
//		vo1.setMbrExpired("2016-1-1 至 2017-2-3");
//		vo1.setMbrCardTypeName("金卡");
//		vo1.setMbrCardNo("64598754");
//		MbrCardVO vo2 = new MbrCardVO();
//		vo2.setBalance(22222f);
//		vo2.setChargeamt(2222f);
//		vo2.setIncamount(22222f);
//		vo2.setBaseamtbalance(22222f);
//		vo2.setMbrExpired("2016-2-3 至 2017-3-4");
//		vo2.setMbrCardTypeName("银卡");
//		vo2.setMbrCardNo("987564454");
//		MbrCardVO vo = new MbrCardVO();
//		vo.setBalance(999f);
//		vo.setChargeamt(8888f);
//		vo.setIncamount(55555f);
//		vo.setBaseamtbalance(99999f);
//		vo.setMbrExpired("2016-8-8 至 2017-9-9");
//		vo.setMbrCardTypeName("白金卡");
//		vo.setMbrCardNo("12354944");
//		vo.setPicUrl("http://res.gshis.net/jxd-res:0TB1PWaTB1jULnLu9nr6dn");
//		mbrCards.add(vo);
//		mbrCards.add(vo1);
//		mbrCards.add(vo2);
		req.setAttribute("mbrCards", mbrCards);
		
		Map<String,String> typeMap = memberService.getMemberTypeMap(company.getId(),company.getCode());
		req.setAttribute("typeMap", typeMap);
		
		SysParamConfig sysParamConfig = sysParamConfigService.getSysParamConfig(company.getId());
		if(sysParamConfig!=null && StringUtils.isNotBlank(sysParamConfig.getMbrCardTheme())){
			return "/front/card/mbrCard_list"+sysParamConfig.getMbrCardTheme();
		}
		return "/front/card/mbrCard_list";
	}
	
	@RequestMapping("/oauth/member/ajaxMbrCardBinding")
	@ResponseBody
	public String ajaxMbrCardBinding(String mbrCardNo,HttpServletRequest req){
		Member member = getCurrentMember(req);
		Company company = getCurrentCompany(req);
		CategoryCodeVO categoryCode = memberTradeService.getMbrCardByNo(company, mbrCardNo);
		if(categoryCode==null){
			return "绑定失败，会员卡号"+mbrCardNo+"找不到";
		}else if(StringUtils.equals(member.getMobile(), categoryCode.getCname()) && StringUtils.equals(mbrCardNo, categoryCode.getExtraValue())){
			GeneralMsg generalMsg = memberTradeService.mbrCardBinding(member, categoryCode.getCode());
			if(generalMsg.getIsSuccess()){
				return "success";
			}else{
				return generalMsg.getMessage();
			}
		}else{
			return "绑定失败，输入卡号所绑定的手机号码，必须是当前会员的手机号码";
		}
	}
	
	@RequestMapping("/oauth/member/ajaxFindSignInRecords")
	@ResponseBody
	public List<SignInRecord> ajaxFindSignInRecords(String signInId,HttpServletRequest req){
		WeixinFan fan = getCurrentFan(req);
		List<SignInRecord> signInRecords = memberTradeService.findSignInRecords(signInId,fan.getOpenId());
		return signInRecords;
	}
}
