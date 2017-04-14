package com.whotel.web.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.whotel.common.base.Constants;
import com.whotel.common.util.DateUtil;
import com.whotel.common.util.SpringContextHolder;
import com.whotel.company.entity.Company;
import com.whotel.company.service.CompanyService;

/**
 * 商户后台登录授权过滤器
 * @author 冯勇
 *
 */
public class CompanyAccessFilter implements Filter{

	private  List<String> ignoreLoginURI = new ArrayList<String>(); //忽略登录相关的URI
	
	private static final Logger logger = Logger.getLogger(CompanyAccessFilter.class);
	
	private CompanyService companyService;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		companyService = SpringContextHolder.getBean(CompanyService.class);
		ignoreLoginURI.add("/company/toLogin.do");
		ignoreLoginURI.add("/company/login.do");
		ignoreLoginURI.add("/company/toRegister.do");
		ignoreLoginURI.add("/company/register.do");
		//找回密码
		ignoreLoginURI.add("/company/toPwdRetrieve.do");
		ignoreLoginURI.add("/company/pwdRetrieve.do");
		//重置密码
		ignoreLoginURI.add("/company/toResetPwd.do");
		ignoreLoginURI.add("/company/resetPwd.do");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String contextPath = req.getContextPath();
		String uri = req.getServletPath();
		String queryString = req.getQueryString();
		
		String queryParams = "";
		if(StringUtils.isNotBlank(queryString)) {
			queryParams = "?" + queryString;
		}
		if (!uri.endsWith(".do") && !uri.endsWith(".jsp")) {
			chain.doFilter(request, response);
			return;
		}
		if(!ignoreLoginURI.contains(uri)) {
			String account = (String) req.getSession().getAttribute(Constants.Session.COMPANY_ADMIN_LOGIN_KEY);
			if(StringUtils.isBlank(account)) { 
				logger.info("has not login, uri :"+uri+" can not access!");
				resp.sendRedirect(contextPath + "/company/toLogin.do?redir="+uri+queryParams+"&message="+URLEncoder.encode("您还未登录，请先登录", "UTF8"));
				return;
			} else {
				String code = (String) req.getSession().getAttribute(Constants.Session.COMPANY_ADMIN_LOGIN_CODE);
				Company company = companyService.getCompanyByCode(code);
				if(company != null) {
					Date validTime = company.getValidTime();
					Date now = new Date();
					if((company.getValid() != null && !company.getValid()) || (validTime != null && validTime.before(DateUtil.getStartTime(now)))) {
						logger.info("weixin service is invalid, uri :"+uri+" can not access!");
						resp.sendRedirect(contextPath + "/company/toLogin.do?redir="+uri+queryParams+"&message="+URLEncoder.encode("您的微信服务已经被终止，请联系供应商！", "UTF8"));
						return;
					}
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}
}
