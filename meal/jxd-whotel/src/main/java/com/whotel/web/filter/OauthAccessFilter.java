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

import com.whotel.common.base.Constants;
import com.whotel.common.util.SpringContextHolder;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.service.CompanyService;
import com.whotel.company.service.PublicNoService;
import com.whotel.webiste.entity.Theme;
import com.whotel.webiste.service.ThemeService;
import com.whotel.weixin.service.OauthService;

/**
 * 微信应用授权过滤器
 * @author 冯勇
 *
 */
public class OauthAccessFilter implements Filter{

	private  List<String> ignoreLoginURI = new ArrayList<String>(); //忽略授权相关的URI
	
	private static final Logger logger = Logger.getLogger(OauthAccessFilter.class);
	
	private PublicNoService publicNoService;
	
	private CompanyService companyService;
	
	private ThemeService themeService;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		publicNoService = SpringContextHolder.getBean(PublicNoService.class);
		companyService = SpringContextHolder.getBean(CompanyService.class);
		themeService = SpringContextHolder.getBean(ThemeService.class);
		ignoreLoginURI.add("/oauth/meal/login.do");
		ignoreLoginURI.add("/oauth/meal/outLogin.do");
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
		if(StringUtils.isEmpty(comid)){
			comid = "55e65986cb0d7463a708d003";
		}
		if(StringUtils.isEmpty(wxid)){
			wxid = "oLI_KjsR4DUKt9gp-m8jYrJyggZQ";
		}
		
		String companyId = (String) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_COMPANYID);
		String openId = (String) session.getAttribute(Constants.Session.WEIXINFAN_LOGIN_OPENID);
		System.out.println("comid:"+comid+"-------------companyId:"+companyId);
		System.out.println("wxid:"+wxid+"-------------openId:"+openId);
		if(StringUtils.isNotBlank(comid)) {
			if(StringUtils.isBlank(companyId) && !StringUtils.equals(comid, companyId)) {
				companyId = comid;
				session.setAttribute(Constants.Session.WEIXINFAN_LOGIN_COMPANYID, companyId);
				session.removeAttribute(Constants.Session.WEIXINFAN_LOGIN_OPENID);
				
				Company company = companyService.getCompanyById(comid);
				session.setAttribute(Constants.Session.THEME, company.getTheme());
				session.setAttribute(Constants.Session.WEIXINFAN_LOGIN_COMPANYNAME, company.getName());
				
				Theme theme = themeService.getEnableTheme(companyId);
				session.setAttribute(Constants.Session.COMPANY_THEME, theme);
				openId = null;
			}
		}
		
		if(StringUtils.isNotBlank(wxid)) {
			if(StringUtils.isBlank(openId) && !StringUtils.equals(wxid, openId)) {
				openId = wxid;
				session.setAttribute(Constants.Session.WEIXINFAN_LOGIN_OPENID, openId);
			}
		}
		
		if(!ignoreLoginURI.contains(uri)) {
			
			if(StringUtils.isBlank(openId)) { 
				PublicNo publicNo = publicNoService.getPublicNoByCompanyId(companyId);
				
				if(publicNo != null && publicNo.isAuth()) {
					
					logger.info("has not oauth, uri :"+uri+" can not access! queryString:"+queryString+", publicNo:"+publicNo);
					if(StringUtils.equalsIgnoreCase(oauth2, "true")) {
						resp.sendRedirect(OauthService.buildOauth2Url(publicNo.getAppId(), uri, queryString));
					} else {
						resp.sendRedirect(OauthService.buildOauthUrl(publicNo.getAppId(), uri, queryString));
					}
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
