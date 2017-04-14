package com.weixin.pay.sign;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

import com.whotel.common.util.Sha1Util;

public class WXPaySignGen extends WXParamsGen {
	
	public WXPaySignGen() {
		super();
	}
	
	public WXPaySignGen(SortedMap<String, String> params) {
		super(params);
	}
	
	/**
	 * 创建签名SHA1
	 * @param signParams
	 * @return
	 * @throws Exception
	 */
	public String createSHA1Sign() {
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = getAllParameters().entrySet();
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
//			sb.append(k + "=" + v + "&");
			sb.append(k);
		}
		String params = sb.toString();//.substring(0, sb.lastIndexOf("&"));
		System.out.println("paySign:"+params);
		String appsign = Sha1Util.Sha1(params);
		return appsign;
	}
}
