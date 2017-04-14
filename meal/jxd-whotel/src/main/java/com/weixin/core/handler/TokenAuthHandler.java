package com.weixin.core.handler;

/**
 * 获取授权token
 */
public interface TokenAuthHandler {
	
	/**
	 * @param cid
	 * @return
	 */
	String auth(String cid);
}
