package com.weixin.core.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.core.common.BaseToken;
import com.weixin.core.enums.QrActionType;
import com.whotel.common.http.HttpHelper;
import com.whotel.ext.json.JSONConvertFactory;
import com.whotel.ext.json.JSONDataUtil;
import com.whotel.weixin.service.TokenService;

/**
 * 场景二维码接口
 * @author 冯勇
 *
 */
public class SceneQrcodeCreator {
	private static final Logger log = LoggerFactory.getLogger(SceneQrcodeCreator.class);
	private static final String REQ_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
	
	private static final String QR_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";
	public static final int TIME_OUT = 30 * 1000;
	
	
	/**
	 * @return 返回ticket
	 */
	public static String create(String appId, String appSecret, QrActionType type, String scene_id,Integer time) {
		BaseToken t;
		String ticket = null;
		try {
			for (int i = 0; i < 2; i++) {
				t = TokenService.getTokenService().getAccessToken(appId, appSecret);
				ticket = create(t, type, scene_id,time);
				if(StringUtils.isNotEmpty(ticket)){
					break;
				}else{
					TokenService.getTokenService().refreshAccessToken(appId, appSecret, t);
				}
			}
			return ticket;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @return 返回ticket
	 */
	public static String create(BaseToken token, QrActionType type, String scene_id) {
		return create(token, type, scene_id,null);
	}
	
	public static String create(BaseToken token, QrActionType type, String scene_id,Integer time) {
		
		String data = null;
		if(time==null){
			time=604800;
		}
		
		if(QrActionType.QR_LIMIT_SCENE.equals(type)) {
			data = "{\"action_name\": \"" + type + "\", \"action_info\": {\"scene\": {\"scene_id\": " + scene_id
					+ "}}}";
		} else if(QrActionType.QR_SCENE.equals(type)){
			data = "{\"expire_seconds\": "+time+", \"action_name\": \"" + type + "\", \"action_info\": {\"scene\": {\"scene_id\": " + scene_id
					+ "}}}";
		}
		
		String result;
		try {
			result = HttpHelper.connect(REQ_URL + token.getAccess_token()).timeout(TIME_OUT).post(data).html();
			JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
			JSONObject jsonObj = jacksonConverter.objectFromString(result, JSONObject.class);
			System.out.println("resp->" + result);
			log.info("SceneQrcodeCreator create data->" + data);
			log.info("SceneQrcodeCreator create resp->" + result);
			return (String) jsonObj.get("ticket");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getQrUrl(String ticket) {
		try {
			return QR_URL + URLEncoder.encode(ticket, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
