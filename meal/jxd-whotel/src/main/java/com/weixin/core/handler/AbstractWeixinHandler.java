package com.weixin.core.handler;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.core.api.UserInfoApi;
import com.weixin.core.bean.FanInfo;
import com.weixin.core.bean.WeixinMsg;
import com.weixin.core.common.BaseToken;
import com.whotel.company.entity.PublicNo;
import com.whotel.front.entity.WeixinFan;
import com.whotel.front.service.WeixinFanService;
import com.whotel.weixin.service.TokenService;

public abstract class AbstractWeixinHandler implements WeixinMsgHandler {
	
	private static Logger log = LoggerFactory.getLogger(AbstractWeixinHandler.class);
	
	private WeixinMsgHandler handler;
	private ServletContext servletContext;
	protected WeixinFanService weixinFanService;

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public WeixinMsgHandler getHandler() {
		return handler;
	}

	@Override
	public void setHandler(WeixinMsgHandler handler) {
		this.handler = handler;
	}

	@Override
	public WeixinMsg handle(WeixinMsg msg) throws Exception {
		if (this.getHandler() != null) {
			WeixinMsg respMsg = this.getHandler().handle(msg);
			if (respMsg != null)
				return respMsg;
		}
		return handleMsg(msg);
	}

	public abstract WeixinMsg handleMsg(WeixinMsg msg) throws Exception;
	
	public void readUserMsg(PublicNo publicNo, WeixinFan fan){
		try {
			if (publicNo.isAuth()) {
				BaseToken token = TokenService.getTokenService().getAccessToken(publicNo.getAppId(), publicNo.getAppSecret());
				log.info(" 使用API读取访问令牌！"+token);
				FanInfo info = UserInfoApi.getUserInfo(token, fan.getOpenId());
				log.info(" 使用API读取用户资料！"+info);
				if (info.getErrcode() == null && info.getSubscribe() == 1) {
					weixinFanService.updateWeixinFan(info, publicNo.getDeveloperCode());
				}
			}
		} catch (Exception e) {
			log.error("Error", e);
		}
	}
}
