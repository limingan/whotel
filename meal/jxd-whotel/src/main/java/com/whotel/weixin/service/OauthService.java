package com.whotel.weixin.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

import com.whotel.common.util.SystemConfig;

/**
 * 微信授权url处理类
 * @author 冯勇
 *
 */
public class OauthService {

	public static String buildOauthUrl(String appId, String jumpUrl, String params){
		String oauth = SystemConfig.getProperty("wx.oauth");
		String redirectUrl = oauth + "?toUrl=" + jumpUrl;
		if(StringUtils.isNotBlank(params)) {
			params = params.replaceAll("&", "@");
		}
		try {
			redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
				+ "&redirect_uri=" + redirectUrl + "&response_type=code&scope=snsapi_base&state=" + params
				+ "#wechat_redirect";
	}

	// wechat_redirect 必须要跟上
	public static String buildOauth2Url(String appId, String jumpUrl, String params) {
		String oauth2 = SystemConfig.getProperty("wx.oauth2");
		String redirectUrl = oauth2 + "?toUrl=" + jumpUrl;
		if(StringUtils.isNotBlank(params)) {
			params = params.replaceAll("&", "@");
		}
		try {
			redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
				+ "&redirect_uri=" + redirectUrl
				+ "&response_type=code&scope=snsapi_userinfo&state=" + params + "#wechat_redirect";
	}
}
