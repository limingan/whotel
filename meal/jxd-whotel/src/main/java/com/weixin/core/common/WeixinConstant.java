package com.weixin.core.common;

import org.apache.commons.lang.StringUtils;

/**
 * 全局定义
 * 
 */
public class WeixinConstant {
	public static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 微信消息类型定义
	 * 
	 */
	public static class MsgType {
		public static final String TextMsg = "text";
		public static final String EventMsg = "event";
		public static final String ImageMsg = "image";
		public static final String MusicMsg = "music";
		public static final String LinkMsg = "link";
		public static final String NewsMsg = "news";
		public static final String LocationMsg = "location";
		public static final String VoiceMsg = "voice";
		public static final String VideoMsg = "video";
		public static final String DeviceText = "device_text";
		public static final String DeviceEvent = "device_event";
	}

	public static enum WeixinMode {
		EDIT(1), DEVELOP(2);
		private int v;

		private WeixinMode(int v) {
			this.v = v;
		}

		public int getV() {
			return this.v;
		}
	}

	public static enum WeixinModeControl {
		COLSE(0), OPEN(1);
		private int v;

		private WeixinModeControl(int v) {
			this.v = v;
		}

		public int getV() {
			return this.v;
		}
	}

	/**
	 * 微信事件类型定义
	 * 
	 */
	public static class EventType {
		public static final String Subscribe = "subscribe";
		public static final String Unsubscribe = "unsubscribe";
		public static final String Click = "CLICK";
		public static final String LOCATION = "LOCATION";
		public static final String SCAN = "SCAN";
		public static final String CARDPASS = "card_pass_check";
		public static final String CARDNOTPASS = "card_not_pass_check";
		public static final String GETCARD = "user_get_card";
		public static final String DELCARD = "user_del_card";
		public static final String SCANCODE_PUSH = "scancode_push";
		
	}

	/**
	 * 解析EVENT 关注或扫码事件里的场景值 qrscene_123123
	 * 
	 */
	public static int parseSence(String sence) {
		int beg = sence.indexOf("qrscene_");
		if (beg != -1) {
			String s = sence.substring(beg + 8);
			if (StringUtils.isNotBlank(s)) {
				return Integer.parseInt(s);
			}
		}
		return 0;
	}

	/**
	 * 微信网页登录失败响应错误码描述
	 */
	public static String turnWxWebPageLoginErrorDesc(int errCode) {
		switch (errCode) {
		case -1:
			return "系统错误";
		case -2:
			return "帐号或密码错误";
		case -3:
			return "密码错误";
		case -4:
			return "不存在该帐户";
		case -5:
			return "访问受限";
		case -6:
			return "需要输入验证码";
		case -7:
			return "此帐号已绑定私人微信号，不可用于公众平台登录";
		case -8:
			return "邮箱已存在";
		case -32:
			return "验证码输入错误";
		case -200:
			return "因频繁提交虚假资料，该帐号被拒绝登录";
		case -94:
			return "请使用邮箱登陆";
		case 10:
			return "该公众号已经过期，无法再登录使用";
		case 65201:
		case 65202:
			return "成功登陆，正在跳转...";
		case 40001:
			return "invalid credential";
		case 40008:
			return "api unauthorized";
		case 0:
			return "成功登陆，正在跳转...";
		default:
			return "未知返回";
		}
	}

}
