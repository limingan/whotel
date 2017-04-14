package com.whotel.ext.session;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import com.whotel.common.util.SpringContextHolder;

/**
 * 接管了Session数据存取工作，根据注入的sessionManager来实现不同存取机制的切换
 * 比如可以使用RedisSessionManager
 * 
 * @author 冯勇
 * 
 */
public class CustomHttpSessionWrapper extends HttpSessionWrapper {
	private static final String SESSION_MGR = "sessionManager";

	private String sessionId = "";
	private SessionManager sessionManager = null;

	public CustomHttpSessionWrapper(String sessionId, HttpSession session) {
		super(session);
		this.sessionId = sessionId;
		sessionManager = (SessionManager) SpringContextHolder.getBean(SESSION_MGR);
	}

	@Override
	public String getId() {
		return sessionId;
	}

	@Override
	public Object getAttribute(String name) {
		return sessionManager.getAttribute(sessionId, name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		sessionManager.setAttribute(sessionId, name, value);
	}

	@Override
	public void removeAttribute(String name) {
		sessionManager.removeAttribute(sessionId, name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return sessionManager.getAttributeNames(sessionId);
	}

	@Override
	public void invalidate() {
		super.invalidate();
		sessionManager.removeSession(sessionId);
	}

}
