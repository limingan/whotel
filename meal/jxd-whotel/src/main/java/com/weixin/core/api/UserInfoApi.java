package com.weixin.core.api;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.core.bean.FanInfo;
import com.weixin.core.common.AccessToken;
import com.weixin.core.common.BaseToken;
import com.whotel.common.http.HttpHelper;
import com.whotel.ext.json.JSONConvertFactory;
import com.whotel.ext.json.JSONDataUtil;

/**
 * 微信用户信息管理接口
 * @author 冯勇
 *
 */
public class UserInfoApi {
	private static Logger log = LoggerFactory.getLogger(UserInfoApi.class);

	// 获取用户基本信息接口
	private static final String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
	// 拉取用户信息
	private static final String SNS_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
	// 获取关注者列表
	private static final String GET_FAN_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s&next_openid=%s";

	public static FanInfo getUserInfo(String appId, String appSecret, String openId) throws Exception {
		BaseToken token = TokenManager.getBaseToken(appId, appSecret);
		return getUserInfo(token, openId);
	}
	
	public static FanInfo getUserInfo(BaseToken token, String openId) throws Exception {
		String url = String.format(USER_INFO_URL, token.getAccess_token(), openId);
		String str = HttpHelper.connect(url + token.getAccess_token()).timeout(30000).post().html();
		JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
		return jacksonConverter.objectFromString(str, FanInfo.class);
	}

	public static FanInfo getSnsUserInfo(AccessToken token, String openId) throws Exception {
		String url = String.format(SNS_USER_INFO_URL, token.getAccess_token(), openId);
		log.info(url);
		String str = HttpHelper.connect(url).timeout(30000).post().html();
		log.info(str);
		JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
		return jacksonConverter.objectFromString(str, FanInfo.class);
	}
	
	public static FanInfo getSnsUserInfo(String appId, String appSecret, String code, String openId) throws Exception {
		AccessToken token = TokenManager.getAccessToken(appId, appSecret, code);
		return getSnsUserInfo(token, openId);
	}

	/**
	 * 已有BaseToken获取所有关注者列表，不会废掉现有Token
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static List<String> getFanList(BaseToken token) throws Exception {
		String url = String.format(GET_FAN_LIST, token.getAccess_token(), "");
		log.info(url);
		String result = HttpHelper.connect(url).timeout(30000).get().html();
		JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
		JSONObject obj = jacksonConverter.objectFromString(result, JSONObject.class);
		if (StringUtils.isNotBlank(obj.getString("errmsg"))) {
			log.warn(result);
		}
		Integer total = (Integer) obj.get("total");
		if (total == null) {
			return new ArrayList<String>();
		}
		// int count = obj.getInteger("count");
		String nextOpenId = obj.getString("next_openid");
		log.info("OpenId Cnt: " + total);
		int pg = total / 10000;
		// int mod = total % 10000;

		JSONObject data = obj.getJSONObject("data");
		String openIdJson = data.getString("openid");
		List<String> list = jacksonConverter.listFromString(openIdJson, String.class);
		for (int i = 0; i < pg; i++) {
			url = String.format(GET_FAN_LIST, token.getAccess_token(), nextOpenId);
			result = HttpHelper.connect(url).timeout(30000).get().html();
			obj = jacksonConverter.objectFromString(result,JSONObject.class);
			nextOpenId = obj.getString("next_openid");
			data = obj.getJSONObject("data");
			openIdJson = data.getString("openid");
			List<String> arr = jacksonConverter.listFromString(openIdJson, String.class);
			list.addAll(arr);
		}

		return list;
	}

	/**
	 * 通过appId和appSecret获取BaseToken，然后再获取关注者列表，会废掉现有的Token
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	public static List<String> getFanList(String appId, String appSecret) throws Exception {
		BaseToken token = TokenManager.getBaseToken(appId, appSecret, 400000);
		return getFanList(token);
	}

}
