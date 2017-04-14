package com.weixin.pay.sign;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

import com.whotel.common.util.MD5Util;

public class WXPackageGen extends WXParamsGen {

	/** 密钥 */
	private String key;
	
	public WXPackageGen() {
		super();
	}
	
	public WXPackageGen(SortedMap<String, String> params) {
		super(params);
	}
	
	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public String createSign() {
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = getAllParameters().entrySet();
		Iterator<Entry<String, String>> it = es.iterator();
		while(it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + this.getKey());
		
		String sign = MD5Util.MD5(sb.toString()).toUpperCase();
	    return sign;
	}
	
	/**
	 * 获取package数据
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	public String getPackage() throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = getAllParameters().entrySet();
		Iterator<Entry<String, String>> it = es.iterator();
		while(it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			
			sb.append(k + "=" + URLEncoder.encode(v, getEncode()) + "&");
		}
//		String found = StrUtils.strBetween(sb.toString(), "notify_url=", "&");
//		String replace = found.toLowerCase();
		sb.append("sign="+this.createSign());
		return sb.toString();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
