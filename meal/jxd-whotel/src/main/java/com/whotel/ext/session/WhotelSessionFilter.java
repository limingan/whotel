package com.whotel.ext.session;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whotel.common.util.URLUtil;

/**
 * SessionID过滤器，确保每个新来的请求都有一个唯一的标识ID
 * 
 * @author 冯勇
 * 
 */
@SuppressWarnings("serial")
public class WhotelSessionFilter extends HttpServlet implements Filter {
	private static final String LOCALHOST = "localhost";

	private static String sessionId = "sid";
	private static String cookieDomain = "";
	private static String cookiePath = "/";

	/**
	 * 处理请求时，如果发现没有携带sessionID则分配一个
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		Cookie cookies[] = request.getCookies();

		String sid = "";
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(sessionId)) {
					sid = cookie.getValue();
				}
			}
		}

		if (sid == null || sid.length() == 0) {
			sid = UUID.randomUUID().toString();
			Cookie cookie = new Cookie(sessionId, sid);
			cookie.setMaxAge(-1);
			String hostName = request.getServerName();
			if (!hostName.equals(LOCALHOST) && !URLUtil.isIpAddress(hostName)) {
				cookie.setDomain("." + URLUtil.getDomainName(hostName));
			}
			cookie.setPath(cookiePath);
			response.addCookie(cookie);
		}
		
		filterChain.doFilter(new CustomHttpServletRequestWrapper(sid, request), servletResponse);
	}

	/**
	 * 初始化cookie相关设置
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		sessionId = filterConfig.getInitParameter("sessionId");
		cookieDomain = filterConfig.getInitParameter("cookieDomain");
		if (cookieDomain == null) {
			cookieDomain = "";
		}

		cookiePath = filterConfig.getInitParameter("cookiePath");
		if (cookiePath == null || cookiePath.length() == 0) {
			cookiePath = "/";
		}
	}

}
