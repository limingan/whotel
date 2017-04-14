package com.whotel.ext.sms;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class SmsUtil {

	private static Logger log = LoggerFactory.getLogger(SmsUtil.class);

	private static HttpClient httpClient = new HttpClient();
	private static final String url = "http://www.mxtong.net.cn/GateWay/Services.asmx/DirectSend?";
	private static final String userID = "";
	private static final String accountID = "";
	private static final String password = "";
	private static final String SIGN = "";

	public static final String SEND_SUCCESS = "RetCode=Sucess";

	static {
		httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
	}

	/**
	 * 发送短信
	 * 
	 * @param phones 手机号码列表，(;)分割，形如：13800000000;13900000000;13500000000;
	 * @param content 短信内容
	 * @param sign 短信签名，短信运营商要求的，一段显示在【】之间的内容
	 * @return
	 */
	public static String send(String phones, String content, String sign) {
		DirectSendDto directSendDTO = new DirectSendDto();
		directSendDTO.setPhones(phones);
		if (StringUtils.isBlank(sign)) {
			sign = SIGN;
		} else {
			sign = "【" + sign + "】";
		}
		directSendDTO.setContent(content + sign);
		return send(directSendDTO);
	}

	/**
	 * 发送短信
	 * 
	 * @param directSendDTO
	 * @return
	 */
	public static String send(DirectSendDto directSendDTO) {
		Assert.hasText(directSendDTO.getContent(), "短信内容不能为空");
		String phones = directSendDTO.getPhones();
		directSendDTO.setUserID(userID);
		directSendDTO.setAccountID(accountID);
		directSendDTO.setPassword(password);
		directSendDTO.setSendType("1");
		if (!StringUtils.endsWith(phones, ";")) {
			directSendDTO.setPhones(phones + ";");
		}
		String msg = "Send SMS: " + directSendDTO.getContent() + " to " + directSendDTO.getPhones();
		log.info(msg);
		return directSend(url, directSendDTO);
	}

	// 调用接口DirectSend,对于参数为中文的调用采用以下方法,为防止中文参数为乱码.
	private static String directSend(String url, DirectSendDto directSendDTO) {
		// String response = "";

		// String responseValue;
		PostMethod getMethod = new UTF8PostMethod(url);
		NameValuePair[] data = { new NameValuePair("UserID", directSendDTO.getUserID()),
				new NameValuePair("Account", directSendDTO.getAccountID()),
				new NameValuePair("Password", directSendDTO.getPassword()),
				new NameValuePair("Phones", directSendDTO.getPhones()),
				new NameValuePair("SendType", directSendDTO.getSendType()),
				new NameValuePair("SendTime", directSendDTO.getSendTime()),
				new NameValuePair("PostFixNumber", directSendDTO.getPostFixNumber()),
				new NameValuePair("Content", directSendDTO.getContent()) };
		getMethod.setRequestBody(data);
		getMethod.addRequestHeader("Connection", "close");
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
			byte[] responseBody = getMethod.getResponseBody();
			String str = new String(responseBody, "GBK");
			if (str.contains("GBK")) {
				str = str.replaceAll("GBK", "utf-8");
			}
			int beginPoint = str.indexOf("<RetCode>");
			int endPoint = str.indexOf("</RetCode>");
			String result = "RetCode=";
			return result + str.substring(beginPoint + 9, endPoint);
			// return getMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			return "1";
		} catch (IOException e) {
			return "2";
		} finally {
			getMethod.releaseConnection();
		}
	}

	public static class UTF8PostMethod extends PostMethod {
		public UTF8PostMethod(String url) {
			super(url);
		}

		@Override
		public String getRequestCharSet() {
			// return Charset.defaultCharset().name();
			// return super.getRequestCharSet();
			return "UTF-8";
		}
	}

	public static void main(String[] args) {
		DirectSendDto directSendDTO = new DirectSendDto();
		directSendDTO.setPhones("13530632798;");
		directSendDTO.setContent("您好，您的验证码 ！");
		System.out.println(send(directSendDTO));
	}
}
