package com.whotel.ext.sms;

import java.util.HashMap;
import java.util.Map;

import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;

/**
 * 企信通短信处理工具
 * @author fy
 *
 */
public class QxtSmsUtil {

	private static final String sendUrl = "http://221.179.180.158:9007/QxtSms/QxtFirewall";
	
	private static final String surplusUrl = "http://221.179.180.158:8081/QxtSms_surplus/surplus";
	
	private static final String username = "";
	
	private static final String userpass = "";
	
	public static void send(String mobiles, String content, String type) {
		
		Map<String, String> data = new HashMap<String, String>();
		
		try {
			data.put("OperID", username);
			data.put("OperPass", userpass);
			data.put("DesMobile", mobiles);
			data.put("Content", content);
			
			data.put("ContentType", type);  // 15 普通短信； 8 长短信
			Response response = HttpHelper.connect(sendUrl).data(data).charset("GBK").get();
			String html = response.html();
			
			System.out.println(html);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void surplus() {
		Map<String, String> data = new HashMap<String, String>();
		
		try {
			data.put("OperID", username);
			data.put("OperPass", userpass);
			Response response = HttpHelper.connect(surplusUrl).data(data).get();
			String html = response.html();
			
			System.out.println(html);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
