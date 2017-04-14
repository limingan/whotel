package com.whotel.front.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.whotel.card.entity.Member;
import com.whotel.card.service.MemberService;
import com.whotel.common.base.Constants;
import com.whotel.common.base.controller.BaseController;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.service.CompanyService;
import com.whotel.company.service.PublicNoService;
import com.whotel.front.entity.WeixinFan;
import com.whotel.front.service.WeixinFanService;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;

@Controller
public class FanBaseController extends BaseController {
	
	@Autowired
	protected WeixinFanService weixinFanService;
	
	@Autowired
	protected CompanyService companyService;
	
	@Autowired
	protected MemberService memberService;
	
	@Autowired
	protected PublicNoService publicNoService;
	
	public WeixinFan getCurrentFan(HttpServletRequest req) {
		HttpSession session = req.getSession();
		
		String openId = (String) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_OPENID);
		
		WeixinFan weixinFan = weixinFanService.getWeixinFanByOpenId(openId);
		if(weixinFan == null && StringUtils.isNotBlank(openId)) {
			weixinFan = new WeixinFan();
			weixinFan.setOpenId(openId);
			
			String companyId = (String) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_COMPANYID);
			PublicNo publicNo = publicNoService.getPublicNoByCompanyId(companyId);
			weixinFanService.readUserMsg(publicNo, weixinFan);
		}
		return weixinFan;
	}
	
	public String getCurrentOpenId(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String openId = (String) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_OPENID);
		return openId;
	}
	
	public Company getCurrentCompany(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String companyId = (String) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_COMPANYID);
		Company company = companyService.getCompanyById(companyId);
		if(company != null) {
			session.setAttribute(Constants.Session.WEIXINFAN_LOGIN_COMPANYNAME, company.getName());
		}
		return companyService.getCompanyById(companyId);
	}
	
	public String getCurrentCompanyId(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String companyId = (String) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_COMPANYID);
		return companyId;
	}
	
	public PublicNo getCurrentPublicNo(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String companyId = (String) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_COMPANYID);
		return publicNoService.getPublicNoByCompanyId(companyId);
	}
	
	public MemberVO getCurrentMemberVO(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Company company = getCurrentCompany(req);
		String companyId = (String) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_COMPANYID);
		String openId = (String) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_OPENID);
		return memberService.getMemberVOByOpenId(companyId, openId,company.getCode());
	}
	
	public MemberVO getCurrentQuickMemberVO(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Company company = getCurrentCompany(req);
		String companyId = (String) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_COMPANYID);
		String openId = (String) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_OPENID);
		return memberService.getQuickMemberVOByOpenId(companyId, openId,company.getCode());
	}
	
	public Member getCurrentMember(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String openId = (String) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_OPENID);
		return memberService.getMemberByOpendId(openId);
	}
}
