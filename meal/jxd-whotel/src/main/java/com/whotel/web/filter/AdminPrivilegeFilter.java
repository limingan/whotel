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

import org.apache.log4j.Logger;

import com.whotel.common.base.Constants;

/**
 * 运营后台管理员权限过滤器
 * @author 冯勇
 *
 */
public class AdminPrivilegeFilter implements Filter{

	private  List<String> ignorePrivilegeURI = new ArrayList<String>(); //忽略登录相关的URI
	
	private static final Logger logger = Logger.getLogger(AdminPrivilegeFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String contextPath = req.getContextPath();
		String uri = req.getServletPath();
		
		if (!uri.endsWith(".do") && !uri.endsWith(".jsp")) {
			chain.doFilter(request, response);
			return;
		}
		if(!ignorePrivilegeURI.contains(uri)) {
			List<String> noPrivilegeUrls = (List<String>) req.getSession().getAttribute(Constants.Session.ADMIN_PRIVILEGE_KEY);
			if(noPrivilegeUrls != null && noPrivilegeUrls.contains(uri)) { 
				logger.info("has not privileges, uri :"+uri+" can not access!");
				resp.sendRedirect(contextPath + "/admin/sys/noPrivilege.do");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}
}
