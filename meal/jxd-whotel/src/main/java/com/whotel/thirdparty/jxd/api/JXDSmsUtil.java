package com.whotel.thirdparty.jxd.api;

import java.util.HashMap;
import java.util.Map;

import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.common.util.MD5Util;

/**
 * 捷信达短信处理工具
 * @author 冯勇
 *
 */
public class JXDSmsUtil {

	private static final String sendUrl = "http://www.jc-chn.cn/smsSend.do";
	
	private static final String surplusUrl = "http://www.jc-chn.cn/balanceQuery.do";
	
	public static long send(String username, String password, String mobiles, String content) {
		
		Map<String, String> data = new HashMap<String, String>();
		
		try {
			data.put("username", username);
			data.put("password", MD5Util.MD5(username+MD5Util.MD5(password).toLowerCase()).toLowerCase());
			data.put("mobile", mobiles);
			data.put("content", content);
			
			Response response = HttpHelper.connect(sendUrl).data(data).charset("UTF-8").get();
			String html = response.html();
			
			return Long.parseLong(html);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int surplus(String username, String password) {
		Map<String, String> data = new HashMap<String, String>();
		
		try {
			data.put("username", username);
			data.put("password", MD5Util.MD5(username+MD5Util.MD5(password).toLowerCase()).toLowerCase());
			Response response = HttpHelper.connect(surplusUrl).data(data).get();
			String html = response.html();
			
			return Integer.parseInt(html);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
