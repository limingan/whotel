package com.weixin.core.handler;

import javax.servlet.ServletContext;

import com.weixin.core.bean.WeixinMsg;

/**
 * 微信消息处理接口，用户应该自定义自己的消息处理器，实现该接口，配置好WeixinMsgService的handler，进行业务逻辑开发
 * 
 */
public interface WeixinMsgHandler {
	/**
	 * 对接到的消息进行业务处理，响应一个新的消息
	 * 
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public WeixinMsg handle(WeixinMsg msg) throws Exception;

	public WeixinMsgHandler getHandler();
	public void setHandler(WeixinMsgHandler handler);
	public ServletContext getServletContext();
	public void setServletContext(ServletContext s);
	// 把消息转化成XML
	// public String turnMsg2Xml(WeixinMsg msg)throws Exception;
}
