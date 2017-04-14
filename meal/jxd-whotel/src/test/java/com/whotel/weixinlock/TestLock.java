package com.whotel.weixinlock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.common.util.DataPackUtil;
import com.whotel.common.util.MD5Util;

import net.sf.json.JSONObject;

public class TestLock {
	
	public static String access_token = "bfrClqHvVlS54MWcAeQt_hNs5cteXXp8y-Mp6C00BrCqqtf4MUIHb73SyrzAKFuhiTiaTeBqeEwqFHZ6PuCBBHHqz16WDpTRpiZEs25OkF4hQOWaBUTJCqvCgKacLXwtNQRgAAAUXY";
	
	@org.junit.Test
	public void findRoooms(){
		QueryRooms query = new QueryRooms();
		query.setAppId("wx4cea1ae3f21c72e8");
		query.setNonceStr(DataPackUtil.getNonceStr());
		query.setTimestamp(DataPackUtil.getTimeStamp());
		query.setSign(getQueryRoomsSign(query));
		query.setPageNo("1");
		query.setPageNum("20");
		query.setVersion("1.0");
		//http://api.weixinlock.com/centralservice/query_rooms?appId=wx4cea1ae3f21c72e8&nonceStr=AWMxaYiP7AY0q&pageNo=1&pageNum=20&sign=B46657B840DEA3C60DF8747ADF6B6B35&timestamp=1481162736&version=1.0
		String url = "http://api.weixinlock.com/centralservice/jwmlock/test";
		
		SortedMap<String, String> signMap = new TreeMap<String, String>();
		signMap.put("appId", query.getAppId());
		signMap.put("nonceStr", query.getNonceStr());
		signMap.put("timestamp", query.getTimestamp());
		signMap.put("sign", query.getSign());
		signMap.put("pageNo", query.getPageNo());
		signMap.put("pageNum", query.getPageNum());
		signMap.put("version", query.getVersion());
		
		try {
			String param = JSONObject.fromObject(signMap).toString();
			System.out.println("param====="+param);
			// dataType:"json",contentType:'application/json;charset=UTF-8',
			Response res = HttpHelper.connect(url).header("Content-Type","application/x-www-form-urlencoded").data(signMap).post();
			System.out.println(res.html());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void compelBind(){
		Map<String,String> param = new HashMap<>();
		param.put("device_id", "A36C427F97D5");
		param.put("openid", "o0_O0t_lEmBOLwJZ2gBd4MzwQt5Q");
		
		try {
			String url = "https://api.weixin.qq.com/device/compel_bind?access_token="+access_token;
			String data = JSONObject.fromObject(param).toString();
			System.out.println("param====="+param);
			// dataType:"json",contentType:'application/json;charset=UTF-8',
			Response res = HttpHelper.connect(url).post(data);
			String errmsg = JSONObject.fromObject(res.html()).getJSONObject("base_resp").get("errmsg").toString();
			if(StringUtils.equals(errmsg, "ok")){
				System.out.println(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void compelUnbind(){
		Map<String,String> param = new HashMap<>();
		param.put("device_id", "A36C427F97D5");
		param.put("openid", "o0_O0t_lEmBOLwJZ2gBd4MzwQt5Q");
		
		try {
			String url = "https://api.weixin.qq.com/device/compel_unbind?access_token="+access_token;
			String data = JSONObject.fromObject(param).toString();
			System.out.println("param====="+param);
			Response res = HttpHelper.connect(url).header("dataType","json").header("Content-Type","application/json").post(data);
			System.out.println(res.html());
			String errmsg = JSONObject.fromObject(res.html()).getJSONObject("base_resp").get("errmsg").toString();
			if(StringUtils.equals(errmsg, "ok")){
				System.out.println(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void transmsg(){
		Map<String,String> param = new HashMap<>();
		param.put("device_type", "gh_ed3b212926d0");
		param.put("device_id", "A36C427F97D5");
		param.put("open_id", "o0_O0t_lEmBOLwJZ2gBd4MzwQt5Q");
		param.put("content", "RFpGX0NNRDoIBwAABVNPQx4=");
		
		try {
			String url = "https://api.weixin.qq.com/device/transmsg?access_token="+access_token;
			String data = JSONObject.fromObject(param).toString();
			System.out.println("param====="+param);
			System.out.println("url======="+url);
			Response res = HttpHelper.connect(url).header("dataType","json").header("Content-Type","application/json").post(data);
			System.out.println(res.html());
			JSONObject json = JSONObject.fromObject(res.html());
			LinkedList<String> errorCodes = new LinkedList<>();
			errorCodes.add("42001");
			errorCodes.add("42002");
			errorCodes.add("42003");
			if(json.get("errcode")!=null && StringUtils.isNotBlank(json.get("errcode").toString())
					&& errorCodes.contains(json.get("errcode").toString())){
				System.out.println("123");
			}
			String errmsg = json.get("ret_info").toString();
			if(StringUtils.equals(errmsg, "ok")){
				System.out.println(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getQueryRoomsSign(QueryRooms query){
		StringBuilder sb = new StringBuilder();
		sb.append(query.getAppId());
		sb.append("c1fe95a0fc32dcbfbc8e70403b6177b1");
		sb.append(query.getNonceStr());
		sb.append(query.getTimestamp());
		System.out.println(sb.toString());
		String sign = MD5Util.MD5(sb.toString()).toLowerCase();
		return sign;
	}
	
	
	public class QueryRooms {
		private String appId;//": "微信公众号的 appId",
		private String nonceStr;//": "随机字符串",
		private String timestamp;//": "时间戳",
		private String sign;//": "签名字符串",
		private String pageNo;//": "分页的当前页",
		private String pageNum;//": "分页的记录数",
		private String version;//": "接口版本号”
		public String getAppId() {
			return appId;
		}
		public void setAppId(String appId) {
			this.appId = appId;
		}
		public String getNonceStr() {
			return nonceStr;
		}
		public void setNonceStr(String nonceStr) {
			this.nonceStr = nonceStr;
		}
		public String getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		public String getPageNo() {
			return pageNo;
		}
		public void setPageNo(String pageNo) {
			this.pageNo = pageNo;
		}
		public String getPageNum() {
			return pageNum;
		}
		public void setPageNum(String pageNum) {
			this.pageNum = pageNum;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
	}
}