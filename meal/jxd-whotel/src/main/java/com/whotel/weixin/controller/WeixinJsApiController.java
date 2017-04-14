package com.whotel.weixin.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weixin.core.api.JsapiTicket;
import com.weixin.core.common.JsTicketToken;
import com.whotel.company.entity.PublicNo;
import com.whotel.front.controller.FanBaseController;
import com.whotel.weixin.service.TokenService;

/**
 * 获取js api数据签名 
 * @author 冯勇
 *
 */
@Controller
public class WeixinJsApiController extends FanBaseController {
	
	private static final Logger log = Logger.getLogger(WeixinJsApiController.class);

	@RequestMapping("/ajaxGetSignature")
	@ResponseBody
	public Map<String, String> ajaxGetSignature(String nonceStr, String timestamp, String url, HttpServletRequest req) {
		PublicNo publicNo = getCurrentPublicNo(req);
		if(publicNo == null) {
			return null;
		}
		SortedMap<String, String> params = new TreeMap<String,String>();
		params.put("noncestr", nonceStr);
		params.put("timestamp", timestamp);
		params.put("url", url);
		
		String jsTicket = null;
		String signature = "";
		try {
			//可以指定一个公众号的appid
			JsTicketToken jsTicketToken = TokenService.getTokenService().getJsTicketToken(publicNo.getAppId(), publicNo.getAppSecret(),JsapiTicket.type_jsapi);
			if(jsTicketToken != null) {
				jsTicket = jsTicketToken.getTicket();
				params.put("jsapi_ticket", jsTicket);
				signature = JsapiTicket.getJsApiSignature(params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> signMap = new HashMap<String, String>();
		signMap.put("signature", signature);
		signMap.put("appId", publicNo.getAppId());
		if(log.isDebugEnabled()) {
			log.debug("nonceStr:"+nonceStr+", timestamp:"+timestamp+",url:"+url+"  jsTicket:"+jsTicket);
		}
		return signMap;
	}
}
