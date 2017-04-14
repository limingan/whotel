package com.whotel.ext.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import com.whotel.common.util.SpringContextHolder;

public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private String sessionId = "";

	public CustomHttpServletRequestWrapper(String sessionId, HttpServletRequest request) {
		super(request);
		this.sessionId = sessionId;
	}

	@Override
	public HttpSession getSession(boolean create) {
		if (SpringContextHolder.getBean("sessionManager") != null) {
			return new CustomHttpSessionWrapper(this.sessionId, super.getSession(create));
		} else {
			return super.getSession(create);
		}
	}

	@Override
	public HttpSession getSession() {
		return new CustomHttpSessionWrapper(this.sessionId, super.getSession());
	}

}
