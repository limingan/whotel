package com.whotel.ext.session;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

@SuppressWarnings("deprecation")
public class HttpSessionWrapper implements HttpSession {
	public static final int SESSION_EXPIRED_SECEND = 24 * 60 * 60;// 24小时

	private HttpSession session;

	public HttpSessionWrapper(HttpSession session) {
		this.session = session;
	}

	@Override
	public long getCreationTime() {
		return session.getCreationTime();
	}

	@Override
	public String getId() {
		return session.getId();
	}

	@Override
	public long getLastAccessedTime() {
		return session.getLastAccessedTime();
	}

	@Override
	public ServletContext getServletContext() {
		return session.getServletContext();
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		session.setMaxInactiveInterval(interval);
	}

	@Override
	public int getMaxInactiveInterval() {
		return session.getMaxInactiveInterval();
	}

	@Override
	@Deprecated
	public HttpSessionContext getSessionContext() {
		return session.getSessionContext();
	}

	@Override
	public Object getAttribute(String name) {
		return session.getAttribute(name);
	}

	@Override
	@Deprecated
	public Object getValue(String name) {
		return session.getValue(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration<String> getAttributeNames() {
		return session.getAttributeNames();
	}

	@Override
	@Deprecated
	public String[] getValueNames() {
		return session.getValueNames();
	}

	@Override
	public void setAttribute(String name, Object value) {
		session.setAttribute(name, value);
	}

	@Override
	@Deprecated
	public void putValue(String name, Object value) {
		session.putValue(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		session.removeAttribute(name);
	}

	@Override
	@Deprecated
	public void removeValue(String name) {
		session.removeValue(name);
	}

	@Override
	public void invalidate() {
		session.invalidate();
	}

	@Override
	public boolean isNew() {
		return session.isNew();
	}

}
