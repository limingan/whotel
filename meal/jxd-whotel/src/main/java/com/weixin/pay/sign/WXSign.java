package com.weixin.pay.sign;

import java.util.SortedMap;

public class WXSign {

	public static String wxSha1Sign(SortedMap<String, String> params) {
		WXPaySignGen wxPaySignGen = new WXPaySignGen(params);
		return wxPaySignGen.createSHA1Sign();
	}
	
	public static String wxMd5Sign(String key, SortedMap<String, String> params) {
		WXPackageGen wxPackageGen = new WXPackageGen(params);
		wxPackageGen.setKey(key);
		return wxPackageGen.createSign();
	}
}
