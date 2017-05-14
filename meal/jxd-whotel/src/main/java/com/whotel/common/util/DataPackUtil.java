package com.whotel.common.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class DataPackUtil {

	private DataPackUtil() {}
	
	public static String getNonceStr() {
		return getRandomString(13);
	}

	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // 生成字符串从此序列中取
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	public static String mapToXml(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		if (map != null) {
			sb.append("<xml>");
			for (Entry<String, String> entry : map.entrySet()) {
				sb.append("<" + entry.getKey() + ">").append("<![CDATA[").append(entry.getValue()).append("]]>").append("</" + entry.getKey() + ">");
			}
			sb.append("</xml>");
		}
		return sb.toString();
	}
}
