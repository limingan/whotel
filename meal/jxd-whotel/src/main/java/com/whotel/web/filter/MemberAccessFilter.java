package com.whotel.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.whotel.card.service.MemberService;
import com.whotel.common.base.Constants;
import com.whotel.common.util.SpringContextHolder;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.service.CompanyService;
import com.whotel.company.service.PublicNoService;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;
import com.whotel.webiste.entity.Theme;
import com.whotel.webiste.service.ThemeService;
import com.whotel.weixin.service.OauthService;

/**
 * 会员授权过滤器
 * @author 冯勇
 *
 */
public class MemberAccessFilter implements Filter{

	private  List<String> ignoreLoginURI = new ArrayList<String>(); //忽略授权相关的URI
	
	private static final Logger logger = Logger.getLogger(MemberAccessFilter.class);
	
	private PublicNoService publicNoService;
	
	private MemberService memberService;
	
	private CompanyService companyService;
	
	private ThemeService themeService;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		publicNoService = SpringContextHolder.getBean(PublicNoService.class);
		memberService = SpringContextHolder.getBean(MemberService.class);
		companyService = SpringContextHolder.getBean(CompanyService.class);
		themeService = SpringContextHolder.getBean(ThemeService.class);
		ignoreLoginURI.add("/oauth/member/toBindMember.do");
		ignoreLoginURI.add("/oauth/member/bindMember.do");
		ignoreLoginURI.add("/oauth/member/ajaxBindMember.do");
		ignoreLoginURI.add("/oauth/member/ajaxSkipBindMember.do");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String uri = req.getServletPath();
		String queryString = req.getQueryString();
		
		if (!uri.endsWith(".do") && !uri.endsWith(".jsp")) {
			chain.doFilter(request, response);
			return;
		}
		String wxid = req.getParameter("wxid");
		String comid = req.getParameter("comid");
		String oauth2 = req.getParameter("oauth2");
		
		comid = "55e65986cb0d7463a708d003";
		wxid = "oLI_KjsR4DUKt9gp-m8jYrJyggZQ";
		
		session.removeAttribute(Constants.Session.WEIXINFAN_LOGIN_IS_MEMBER);
		String companyId = (String) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_COMPANYID);
		String openId = (String) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_OPENID);
		if(StringUtils.isNotBlank(comid)) {
			if(StringUtils.isBlank(companyId) || !StringUtils.equals(comid, companyId)) {
				companyId = comid;
				session.setAttribute(Constants.Session.WEIXINFAN_LOGIN_COMPANYID, companyId);
				session.removeAttribute(Constants.Session.WEIXINFAN_LOGIN_OPENID);
				session.removeAttribute(Constants.Session.WEIXINFAN_LOGIN_IS_MEMBER);
				
				Company company = companyService.getCompanyById(comid);
				session.setAttribute(Constants.Session.THEME, company.getTheme());
				session.setAttribute(Constants.Session.WEIXINFAN_LOGIN_COMPANYNAME, company.getName());
				
				Theme theme = themeService.getEnableTheme(companyId);
				session.setAttribute(Constants.Session.COMPANY_THEME, theme);
				openId = null;
			}
		}
		
		if(StringUtils.isNotBlank(wxid)) {
			if(StringUtils.isBlank(openId) || !StringUtils.equals(wxid, openId)) {
				openId = wxid;
				session.setAttribute(Constants.Session.WEIXINFAN_LOGIN_OPENID, openId);
				session.removeAttribute(Constants.Session.WEIXINFAN_LOGIN_IS_MEMBER);
			}
		}
		
		if(!ignoreLoginURI.contains(uri)) {
			
			if(StringUtils.isBlank(openId)) { 
				PublicNo publicNo = publicNoService.getPublicNoByCompanyId(companyId);
				
				logger.info("has not oauth, uri :"+uri+" can not access! queryString:"+queryString+", publicNo:"+publicNo);
				if(StringUtils.equalsIgnoreCase(oauth2, "true")) {
					resp.sendRedirect(OauthService.buildOauth2Url(publicNo.getAppId(), uri, queryString));
				} else {
					resp.sendRedirect(OauthService.buildOauthUrl(publicNo.getAppId(), uri, queryString));
				}
				return;
			}
			
			Boolean isMember = (Boolean) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_IS_MEMBER);
			if(isMember == null || !isMember) {
				Company company = companyService.getCompanyById(companyId);
				MemberVO member = memberService.getMemberVOByOpenId(companyId, openId,company.getCode());
				if(member != null) {
					session.setAttribute(Constants.Session.WEIXINFAN_LOGIN_IS_MEMBER, true);
				} else {
					resp.sendRedirect("/oauth/member/toBindMember.do");
					return;
				}
			} 
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}
}
