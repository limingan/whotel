package com.weixin.core.api;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.weixin.core.common.AccessToken;
import com.weixin.core.common.BaseToken;
import com.weixin.core.common.Token;
import com.whotel.common.http.HttpHelper;
import com.whotel.ext.json.JSONConvertFactory;
import com.whotel.ext.json.JSONDataUtil;

/**
 * access toke管理接口
 * @author 冯勇
 *
 */
public class TokenManager {
	
	private static final Logger logger = Logger.getLogger(TokenManager.class);
	
	private TokenManager() {
	}

	/** 访问超时 */
	public static final int TIME_OUT = 30 * 1000;
	/** Token存活期 存活2小时 */
	public static final int SurviveTime = 1000 * 60 * 60 * 2;

	/** 获取access_token的接口地址（GET） 限200（次/天）存活2小时 */
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={$APPID}&secret={$APPSECRET}";

	/**
	 * 使用公众号的appid和appsecret，获取访问令牌
	 * 
	 * @param appId
	 * @param appSecret
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static BaseToken getBaseToken(String appId, String appSecret, int timeout) throws Exception {
		String requestUrl = ACCESS_TOKEN_URL.replace("{$APPID}", appId.trim()).replace("{$APPSECRET}", appSecret.trim());
		String str = HttpHelper.connect(requestUrl).timeout(timeout).get().html();
		JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
		BaseToken baseToken = jacksonConverter.objectFromString(str, BaseToken.class);
		logger.info("getBaseToken"+baseToken==null?"":baseToken.getAccess_token());
		return baseToken;
	}

	public static BaseToken getBaseToken(String appId, String appSecret) throws Exception {
		return getBaseToken(appId, appSecret, TIME_OUT);
	}

	public static int getSurviveTime() {
		return SurviveTime;
	}

	public static boolean isValidToken(Token token) {
		if (token != null && (StringUtils.isBlank(token.getErrcode()) || token.getErrcode().equals("0"))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 网页授权接口调用凭证令牌
	 * 
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static AccessToken getAccessToken(String appId, String appSecret, String code) {
		try {
			logger.info("TokenManager getAccessToken appId = " + appId + " appSecret = " + appSecret + "code =" + code);
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId.trim() + "&secret=" + appSecret.trim()
					+ "&code=" + code.trim() + "&grant_type=authorization_code";
			String resp = HttpHelper.connect(url).timeout(TIME_OUT).post().html();
			JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
			AccessToken token = jacksonConverter.objectFromString(resp, AccessToken.class);
			logger.info("get token:" + token);
			return token;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
