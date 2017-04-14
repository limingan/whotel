package com.whotel.ext.session;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Map;

/**
 * Session管理器，定义了Session管理的基础操作
 * 
 * @author
 * 
 */
public interface SessionManager {

	/**
	 * 根据会话ID获得会话的属性键值Map
	 * 
	 * @param sessionId
	 * @return
	 */
	Map<String, Serializable> getSession(String sessionId);

	/**
	 * 设置指定会话的一个属性键值
	 * 
	 * @param sessionId
	 * @param attributeName
	 * @param attributeValue
	 */
	void setAttribute(final String sessionId, final String attributeName, final Object attributeValue);

	/**
	 * 获取指定会话的指定属性值
	 * 
	 * @param sessionId
	 * @param attributeName
	 * @return
	 */
	Object getAttribute(final String sessionId, final String attributeName);

	/**
	 * 获得指定会话的所有属性名
	 * 
	 * @param sessionId
	 * @return
	 */
	Enumeration<String> getAttributeNames(String sessionId);

	/**
	 * 移除指定会话的指定属性
	 * 
	 * @param sessionId
	 * @param attributeName
	 */
	void removeAttribute(final String sessionId, final String attributeName);

	/**
	 * 移除指定会话的所有属性键值
	 * 
	 * @param sessionId
	 */
	void removeSession(String sessionId);

}