package com.whotel.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.whotel.common.base.Constants;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.entity.CompanyModule;

/**
 * 运营后台管理员权限过滤器
 * @author 冯勇
 *
 */
public class CompanyPrivilegeFilter implements Filter{

	private  List<String> ignorePrivilegeURI = new ArrayList<String>(); //忽略登录相关的URI
	
	private static final Logger logger = Logger.getLogger(CompanyPrivilegeFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ignorePrivilegeURI.add("/company/toLogin.do");
		ignorePrivilegeURI.add("/company/login.do");
		ignorePrivilegeURI.add("/company/toRegister.do");
		ignorePrivilegeURI.add("/company/register.do");
		ignorePrivilegeURI.add("/company/index.do");
		ignorePrivilegeURI.add("/company/logout.do");
		//找回密码
		ignorePrivilegeURI.add("/company/toPwdRetrieve.do");
		ignorePrivilegeURI.add("/company/pwdRetrieve.do");
		//重置密码
		ignorePrivilegeURI.add("/company/toResetPwd.do");
		ignorePrivilegeURI.add("/company/resetPwd.do");
		//无权限
		ignorePrivilegeURI.add("/company/authority/noPrivilege.do");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String contextPath = req.getContextPath();
		String url = req.getServletPath();

		if ((!url.endsWith(".do") && !url.endsWith(".jsp"))||(ignorePrivilegeURI.contains(url))) {
			chain.doFilter(request, response);
			return;
		}
		
		Map<String,CompanyModule> noPrivilegeMap = (Map<String, CompanyModule>) req.getSession().getAttribute(Constants.Session.COMPANY_PRIVILEGE_MAP);
//		if(noPrivilegeMap == null || noPrivilegeMap.get(url)!=null) { 
//			logger.info("has not privileges, url :"+url+" can not access!");
//			resp.sendRedirect(contextPath + "/company/authority/noPrivilege.do");
//			return;
//		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}
}
