package com.weixin.core.api;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.core.bean.MaterialBatch;
import com.weixin.core.bean.News;
import com.weixin.core.common.BaseToken;
import com.whotel.common.http.HttpHelper;
import com.whotel.ext.json.JSONConvertFactory;
import com.whotel.ext.json.JSONDataUtil;

/**
 * 微信素材管理接口
 * @author 冯勇
 *
 */
public class NewsResourceApi {
	private static Logger log = LoggerFactory.getLogger(NewsResourceApi.class);

	/** 素材创建（POST）*/
	public static final String NEWS_ADD_URL = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token={$ACCESS_TOKEN}";
	/** 素材删除 */
	public static final String MATERIAL_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token={$ACCESS_TOKEN}";
	
	public static final String BATCHGET_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token={$ACCESS_TOKEN}";
	
	public static final String MATERIAL_GET_URL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token={$ACCESS_TOKEN}";
	
	public static final int TIME_OUT = 30 * 1000;

	public static MaterialBatch batchGetNews(int offset, int count, String type, String appId, String appSecret) throws Exception {
		BaseToken token = TokenManager.getBaseToken(appId, appSecret, TIME_OUT);
		return batchGetNews(offset, count, type, token, TIME_OUT);
	}
	
	public static MaterialBatch batchGetNews(int offset, int count, String type, BaseToken token) throws Exception {
		return batchGetNews(offset, count, type, token, TIME_OUT);
	}

	public static MaterialBatch batchGetNews(int offset, int count, String type, BaseToken token, int timeout) throws Exception {
		if (token.getErrcode() != null && !token.getErrcode().equals("0")) {
			return null;
		}
		String url = BATCHGET_URL.replace("{$ACCESS_TOKEN}",
				token.getAccess_token());
		String json = "{\"type\":\""+type+"\",\"offset\":"+offset+",\"count\":"+count+"}";
		
		log.info("send news json->" + json);
		String resp = HttpHelper.connect(url).timeout(timeout).post(json)
				.html();
		log.info(resp);
		if (resp != null && !resp.isEmpty()) {
			JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
			MaterialBatch materialBatch = jacksonConverter.objectFromString(resp, MaterialBatch.class);
			return materialBatch;
		}
		return null;
	}
	
	public static byte[] getImageMaterial(String mediaId, String appId, String appSecret) throws Exception {
		BaseToken token = TokenManager.getBaseToken(appId, appSecret, TIME_OUT);
		return getImageMaterial(mediaId, token, TIME_OUT);
	}
	
	public static byte[] getImageMaterial(String mediaId,  BaseToken token) throws Exception {
		return getImageMaterial(mediaId, token, TIME_OUT);
	}
	
	public static byte[] getImageMaterial(String mediaId, BaseToken token, int timeout) throws Exception {
		if (token.getErrcode() != null && !token.getErrcode().equals("0")) {
			return null;
		}
		String url = MATERIAL_GET_URL.replace("{$ACCESS_TOKEN}",
				token.getAccess_token());
		String json = "{\"media_id\":\""+mediaId+"\"}";
		
		log.info("send news json->" + json);
		return readImageStream(url, json);
	}
	
	public static byte[] readImageStream(String getImageUrl, String param) throws Exception {
		URL url = new URL(getImageUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(15 * 1000);
		
		// 发送POST请求必须设置如下两行  
		conn.setDoOutput(true);  
		conn.setDoInput(true); 
        
		PrintWriter printWriter = new PrintWriter(conn.getOutputStream()); 
		printWriter.write(param);  
        // flush输出流的缓冲  
        printWriter.flush();  
		
		InputStream inStream = conn.getInputStream();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}
	/**
	 * 创建图文素材
	 * @param news
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	public static String createNews(News news, String appId, String appSecret)
			throws Exception {
		return createNews(news, appId, appSecret, TIME_OUT);
	}
	
	/**
	 * 创建图文素材
	 * @param news
	 * @param token
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static String createNews(News news, BaseToken token) throws Exception {
		return createNews(news,token,TIME_OUT);
	}
	/**
	 * 创建图文素材
	 * @param news
	 * @param token
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static String createNews(News news,  BaseToken token,int timeout) throws Exception {
		if (token.getErrcode() != null && !token.getErrcode().equals("0")) {
				return null;
		}
		String url = NEWS_ADD_URL.replace("{$ACCESS_TOKEN}",
				token.getAccess_token());
		// 将图文对象转换成json字符串
		JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
		String jsonNews = jacksonConverter.jsonfromObject(news);
		// 调用接口创建图文
		log.info("send news json->" + jsonNews);
		String resp = HttpHelper.connect(url).timeout(timeout).post(jsonNews)
				.html();
		log.info(resp);
		if (resp != null && !resp.isEmpty()) {
			JSONObject obj = jacksonConverter.objectFromString(resp, JSONObject.class);
			return obj.getString("media_id");
		}
		return null;
	}
	/**
	 * 创建图文素材
	 * @param news
	 * @param appId
	 * @param appSecret
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static String createNews(News news, String appId, String appSecret,
			int timeout) throws Exception {
		BaseToken token = TokenManager.getBaseToken(appId, appSecret, timeout);
	    log.info("token:"+token);
		return createNews(news,token,timeout);
	}
	
	/**
	 * 删除素材
	 * @return
	 * @throws Exception
	 */
	public static BaseToken deleteMaterial(String media_id, BaseToken token) throws Exception {
		return deleteMaterial(media_id, token, TIME_OUT);
	}

	/**
	 * 删除素材
	 * @return
	 * @throws Exception
	 */
	public static BaseToken deleteMaterial(String media_id, BaseToken token,int timeout)
			throws Exception {
		if (token.getErrcode() != null && !token.getErrcode().equals("0")&&!token.isExceed()) {
			return null;
		}
		String url = MATERIAL_DELETE_URL.replace("{$ACCESS_TOKEN}",
				token.getAccess_token());
		String resp = HttpHelper.connect(url).timeout(timeout).post("{\"media_id\":\""+media_id+"\"}").html();
		log.info(resp);
		if (resp != null && !resp.isEmpty()) {
			JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
			BaseToken baseToken = jacksonConverter.objectFromString(resp, BaseToken.class);
			return baseToken;
		}
		return null;
	}
	
	/**
	 * 删除素材
	 * @param media_id
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	public static BaseToken deleteMaterial(String media_id, String appId, String appSecret)
			throws Exception {
		BaseToken token = TokenManager.getBaseToken(appId, appSecret);
		return deleteMaterial(media_id, token, TIME_OUT);
	}
}
