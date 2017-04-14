package com.weixin.core.api;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.core.common.BaseToken;
import com.weixin.core.common.JsTicketToken;
import com.whotel.common.http.HttpHelper;
import com.whotel.common.util.Sha1Util;
import com.whotel.ext.json.JSONConvertFactory;

/**
 * 微信js-sdk相关ticke api
 * @author 冯勇
 *
 */
public class JsapiTicket {

	private static final String jsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=${ACCESS_TOKEN}&type=";
	public static final String type_jsapi = "jsapi";
	public static final String type_wx_card = "wx_card";
	
	public static final int TIME_OUT = 30 * 1000;
	private static final Logger log = LoggerFactory.getLogger(JsapiTicket.class);

	public static JsTicketToken getJsApiTicket(BaseToken token,String type) {
		if (!TokenManager.isValidToken(token)) {
			log.warn("token invalid!");
			return null;
		}
		String sendUrl = jsapiTicketUrl.replace("${ACCESS_TOKEN}", token.getAccess_token())+type;

		String resp;
		try {
			resp = HttpHelper.connect(sendUrl).timeout(TIME_OUT).get().html();
			if (resp != null && !resp.isEmpty()) {
				JsTicketToken respToken = JSONConvertFactory.getJacksonConverter().objectFromString(resp, JsTicketToken.class);
				if (log.isDebugEnabled()) {
					log.debug(respToken.toString());
				}
				return respToken;
			}
		} catch (Exception e) {
			log.error("get jsapiTicket error!" + e.getMessage());
		}
		return null;
	}

	public static String getJsApiSignature(SortedMap<String, String> params) {
		if(params != null) {
			StringBuffer sb = new StringBuffer();
			Set<Entry<String, String>> es = params.entrySet();
			Iterator<Entry<String, String>> it = es.iterator();
			
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				String k = entry.getKey();
				String v = entry.getValue();
				sb.append(k + "=" + v + "&");
			}
			String signature = sb.substring(0, sb.lastIndexOf("&"));
			return Sha1Util.Sha1(signature);
		}
		return null;
	}
}
