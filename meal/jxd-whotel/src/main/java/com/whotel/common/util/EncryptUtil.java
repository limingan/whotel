package com.whotel.common.util;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 * 
 * @author 冯勇
 * 
 */
public class EncryptUtil {

	private EncryptUtil() {}
	/**
	 * MD5加密
	 * 
	 * @param input
	 *            加密前的字符串
	 * @return 加密后字符串
	 */
	public static String md5(String input) {
		if (input == null) {
			return "";
		} else {
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
					'9', 'a', 'b', 'c', 'd', 'e', 'f' };
			try {
				byte[] strTemp = input.getBytes();
				MessageDigest mdTemp = MessageDigest.getInstance("MD5");
				mdTemp.update(strTemp);
				byte[] md = mdTemp.digest();
				int j = md.length;
				char str[] = new char[j * 2];
				int k = 0;
				for (int i = 0; i < j; i++) {
					byte byte0 = md[i];
					str[k++] = hexDigits[byte0 >>> 4 & 0xf];
					str[k++] = hexDigits[byte0 & 0xf];
				}
				return new String(str);
			} catch (Exception e) {
				return null;
			}
		}
	}
}
