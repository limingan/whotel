package com.weixin.pay.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.weixin.pay.config.WxTradeType;
import com.weixin.pay.config.WxpayConfig;
import com.weixin.pay.sign.WXSign;
import com.whotel.common.http.HttpHelper;
import com.whotel.common.util.DataPackUtil;
import com.whotel.common.util.Dom4jHelper;
import com.whotel.company.entity.PayConfig;
import com.whotel.company.enums.PayType;
import com.whotel.ext.json.JSONConvertFactory;
import com.whotel.front.entity.PayOrder;

/**
 * 微信支付工具类
 * @author 冯勇
 *
 */
public class WxPayUtil {

	private static Logger log = Logger.getLogger(WxPayUtil.class);
	
	/**
	 * 生成微信支付js api
	 * @param config
	 * @param payOrder
	 * @param ip
	 * @param goodsTag
	 * @return
	 */
	public static String genJsApi(PayConfig config, PayOrder payOrder, String ip, String goodsTag) {
		
		String signType = "MD5";
		String prepayid = getPrepayid(config, payOrder, ip, goodsTag);
		Map<String, String> dataMap = null;
		if(StringUtils.isNotBlank(prepayid)) {
			SortedMap<String, String> signMap = new TreeMap<String, String>();
			String nonceStr = DataPackUtil.getNonceStr();
			String timestamp = DataPackUtil.getTimeStamp();
			
			String apiKey = config.getApiKey();
			String packageValue = "prepay_id=" + prepayid;
			if(config.getPayType()==PayType.WX_PROVIDER){
				signMap.put("appId", "wx4cea1ae3f21c72e8");
				apiKey = "ShenZhenJieXinDa4007755123364567";
			}else{
				signMap.put("appId", config.getAppId());
			}
			
			signMap.put("timeStamp", timestamp);
			signMap.put("nonceStr", nonceStr);
			signMap.put("package", packageValue);
			signMap.put("signType", signType);
			
			String sign = WXSign.wxMd5Sign(apiKey, signMap);
	
			dataMap = new HashMap<String, String>();
			dataMap.putAll(signMap);
			dataMap.put("paySign", sign);
		}
		
		if(dataMap != null) {
			String dataJson = JSONConvertFactory.getJacksonConverter().jsonfromObject(dataMap);
			String htmlContent = "<script type=\"text/javascript\">WeixinJSBridge.invoke('getBrandWCPayRequest', "+dataJson+", function(data){wxPayCallBack('"+payOrder.getOrderSn()+"', data)});</script>";  //微支付js api接口
			log.info("wx pay js api: " + htmlContent);
			return htmlContent;
		}
		return null;
	}
	
	/**
	 * 获取预支付ID
	 * @param config
	 * @param payOrder
	 * @param ip
	 * @param goodsTag
	 * @param tradeType
	 * @return
	 */
	public static String getPrepayid(PayConfig config, PayOrder payOrder, String ip, String goodsTag) {
		
		try {
			String apiKey = config.getApiKey();
			SortedMap<String, String> signMap = new TreeMap<String, String>();
			String nonceStr = DataPackUtil.getNonceStr();
			
			if(config.getPayType()==PayType.WX_PROVIDER){
				signMap.put("appid", "wx4cea1ae3f21c72e8");
				signMap.put("mch_id", "1345752201");
				signMap.put("sub_appid", config.getAppId());
				signMap.put("sub_mch_id", config.getMchId());
				signMap.put("sub_openid", payOrder.getOpenId());
				apiKey = "ShenZhenJieXinDa4007755123364567";
			}else{
				signMap.put("appid", config.getAppId());
				signMap.put("mch_id", config.getMchId());
				signMap.put("openid", payOrder.getOpenId());
			}
			if(StringUtils.isNotBlank(payOrder.getHotelNmae())){
				signMap.put("device_info", payOrder.getHotelNmae());
			}
			signMap.put("body", payOrder.getName());
			signMap.put("out_trade_no", payOrder.getOrderSn());
			signMap.put("total_fee", String.valueOf(payOrder.getTotalFee()));
			//signMap.put(openIdKey, payOrder.getOpenId());
			signMap.put("spbill_create_ip", ip);
			signMap.put("trade_type", WxTradeType.JSAPI.toString());
			signMap.put("notify_url", WxpayConfig.NOTIFY_URL);
			signMap.put("nonce_str", nonceStr);
			
			if(StringUtils.isNotBlank(goodsTag)) {
				signMap.put("goods_tag", goodsTag);
			}
			
			String sign = WXSign.wxMd5Sign(apiKey, signMap);

			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.putAll(signMap);
			dataMap.put("sign", sign);

			String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			String xmlData = DataPackUtil.mapToXml(dataMap);
			log.info(xmlData);
			String resp = HttpHelper.connect(url).post(xmlData).html();

			Document respData = DocumentHelper.parseText(resp);
			log.info(respData.asXML());
			
			String returnc = respData.selectSingleNode("//return_code").getText();
			if(StringUtils.equals(returnc, "SUCCESS")) {
				String resultc = respData.selectSingleNode("//result_code").getText();
				if(StringUtils.equals(resultc, "SUCCESS")) {
					return respData.selectSingleNode("//prepay_id").getText();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String queryOrder(PayConfig payConfig, String orderSn) {

		try {
			String apiKey = payConfig.getApiKey();
			SortedMap<String, String> signMap = new TreeMap<String, String>();
			String nonceStr = DataPackUtil.getNonceStr();
			signMap.put("appid", payConfig.getAppId());
			signMap.put("mch_id", payConfig.getMchId());
			if(payConfig.getPayType()==PayType.WX_PROVIDER){
				signMap.put("appid", "wx4cea1ae3f21c72e8");
				signMap.put("mch_id", "1345752201");
				signMap.put("sub_appid", payConfig.getAppId());
				signMap.put("sub_mch_id", payConfig.getMchId());
				apiKey = "ShenZhenJieXinDa4007755123364567";
			}else{
				signMap.put("appid", payConfig.getAppId());
				signMap.put("mch_id", payConfig.getMchId());
			}
			signMap.put("out_trade_no", orderSn);
			signMap.put("nonce_str", nonceStr);

			String sign = WXSign.wxMd5Sign(apiKey, signMap);

			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.putAll(signMap);
			dataMap.put("sign", sign);

			String url = "https://api.mch.weixin.qq.com/pay/orderquery";
			String xmlData = DataPackUtil.mapToXml(dataMap);

			String resp = HttpHelper.connect(url).post(xmlData).html();

			Document respData = DocumentHelper.parseText(resp);
			log.info(respData.asXML());
			String return_code = respData.selectSingleNode("//return_code").getText();
			String result_code = null;
			if (StringUtils.equals(return_code, "SUCCESS")) {
				result_code = respData.selectSingleNode("//result_code").getText();
			}
			if (StringUtils.equals(return_code, "SUCCESS") && StringUtils.equals(result_code, "SUCCESS")) {
				String trade_state = respData.selectSingleNode("//trade_state").getText();
				String transaction_id = respData.selectSingleNode("//transaction_id").getText();
				Node selectSingleNode = respData.selectSingleNode("//cash_fee");
				if(selectSingleNode != null) {
					String cash_fee = selectSingleNode.getText();
					System.out.println(cash_fee);
				}
				if(StringUtils.equals(trade_state, "SUCCESS")) {
					return transaction_id;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String substringByByteLength(String str,int byteLength){
		try {
			if(str.getBytes("UTF-8").length<=byteLength){
				return str;
			}else{
				for (int i = 1; i <= str.length(); i++) {
					int length = str.substring(str.length()-i).getBytes("UTF-8").length;
					if(length>byteLength){
						return str.substring(str.length()-i+1);
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	@SuppressWarnings("deprecation")
	public static String orderRefund(PayConfig config, PayOrder payOrder){
		try {
			String apiKey = config.getApiKey();
			SortedMap<String, String> signMap = new TreeMap<String, String>();
			
			String mchId = "1345752201";
			if(config.getPayType()==PayType.WX_PROVIDER){
				signMap.put("appid", "wx4cea1ae3f21c72e8");
				signMap.put("mch_id", mchId);
				signMap.put("sub_appid", config.getAppId());
				signMap.put("sub_mch_id", config.getMchId());
				apiKey = "ShenZhenJieXinDa4007755123364567";
			}else{
				signMap.put("appid", config.getAppId());
				signMap.put("mch_id", config.getMchId());
			}
			signMap.put("nonce_str", DataPackUtil.getNonceStr());
			if(StringUtils.isNotBlank(payOrder.getHotelNmae())){
				signMap.put("device_info", payOrder.getHotelNmae());
			}
			signMap.put("out_refund_no", payOrder.getOrderSn());
			signMap.put("out_trade_no", payOrder.getOrderSn());
			signMap.put("refund_fee", String.valueOf(payOrder.getRefundFee()));
			signMap.put("total_fee", String.valueOf(payOrder.getTotalFee()));
			signMap.put("op_user_id", config.getMchId());
			
			String sign = WXSign.wxMd5Sign(apiKey, signMap);

			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.putAll(signMap);
			dataMap.put("sign", sign);

			KeyStore keyStore  = KeyStore.getInstance("PKCS12");
			InputStream inputStream = null;
	    	if(config.getPayType()==PayType.WX_PROVIDER){
	    		URL url = new URL("http://res.gshis.net/jxd-res:2rVbBUWLl4fqZXyBA3hSCt");
	    		inputStream = url.openStream();
	    	}else{
	    		URL url = new URL(config.getCertUrl());
	    		inputStream = url.openStream();
	    	}
	    	
	    	try {
	    		keyStore.load(inputStream, mchId.toCharArray());
	    	} finally {
	    		inputStream.close();
	    	}
	    	
	    	SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
	    	SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,new String[] { "TLSv1" },null,
	    			SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	    	CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

	    	HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
	    	String xmlData = DataPackUtil.mapToXml(dataMap);
			StringEntity myEntity = new StringEntity(xmlData, "UTF-8");
			httpPost.setEntity(myEntity);
			
	        CloseableHttpResponse response = httpclient.execute(httpPost);
	        HttpEntity entity = response.getEntity();
			Dom4jHelper dom4jHelper = new Dom4jHelper(entity.getContent(), "UTF-8");
			String msg = dom4jHelper.getElementValue("return_msg");
			System.out.println(msg);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "退款失败";
	}
	
//	public static void main(String[] args) {
//		PayConfig config = new PayConfig();
//		config.setPayType(PayType.WX_PROVIDER);
//		config.setAppId("wx27405708bae7f4ab");
//		config.setMchId("1355792802");
//		
//		PayOrder payOrder = new PayOrder();
//		payOrder.setHotelNmae("test");
//		payOrder.setOrderSn("01000000251924");
//		payOrder.setRefundFee(1l);
//		payOrder.setTotalFee(1l);
//		
//		orderRefund(config, payOrder);
//	}
}
