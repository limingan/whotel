package com.weixin.pay.config;

import com.whotel.common.util.SystemConfig;

public class WxpayConfig {

	public static final String NOTIFY_URL = SystemConfig.getProperty("wxpay.notifyUrl");
	
	public static final String BANK_TYPE = "WX";    		//微信
	public static final String FEE_TYPE = "1";      		//人民币
	
	public static final String INPUT_CHARSET = "UTF-8";       //字符编码
}
