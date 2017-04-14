package com.weixin.core.api;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.core.common.BaseToken;
import com.whotel.ext.json.JSONConvertFactory;
import com.whotel.ext.json.JSONDataUtil;
import com.whotel.weixin.service.TokenService;

/**
 * 微信素材上传类
 * @author 冯勇
 *
 */
public class MediaUploador {

	private static final Logger log = LoggerFactory.getLogger(MediaUploador.class);
	private static String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token={$ACCESS_TOKEN}&type={$TYPE}";
	
	private static String UPLOAD_IMAGE_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token={$ACCESS_TOKEN}";
	
	/** 其它素材创建（POST） */
	public static final String MATERIAL_ADD_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token={$ACCESS_TOKEN}&type={$TYPE}";

	public static final String MATERIAL_GET_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token={$ACCESS_TOKEN}&media_id={$MEDIA_ID}";

	/**
	 * 模拟form表单的形式 ，上传文件 以输出流的形式把文件写入到url中，然后用输入流来获取url的响应
	 * @throws IOException
	 */
	public static String upload(String url, BaseToken token, String type, byte[] data) {
		String result = null;

		url = url.replace("{$ACCESS_TOKEN}", token.getAccess_token())
				.replace("{$TYPE}", type);
		
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		
		try {
			URL urlObj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
			/**
			 * 设置关键值
			 */
			con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false); // post方式不能使用缓存
			// 设置请求头信息
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			// 设置边界
			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			// 请求正文信息
			// 第一部分：
			StringBuilder sb = new StringBuilder();
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + System.currentTimeMillis()
					+ ".jpg\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			byte[] head = sb.toString().getBytes("utf-8");
			// 获得输出流
			OutputStream out = new DataOutputStream(con.getOutputStream());
			// 输出表头
			out.write(head);
			// 文件正文部分
			// 把文件已流文件的方式 推入到url中
			out.write(data);
			// 结尾部分
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
			out.write(foot);
			out.flush();
			out.close();

			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static String uploadGetMediaId(String url, BaseToken token, String type, byte[] data) {
		String result = upload(url, token, type, data);
		JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
		JSONObject jsonObj = jacksonConverter.objectFromString(result, JSONObject.class);
		String mediaId = jsonObj.getString("media_id");
		System.out.println(result);
		if (StringUtils.isBlank(mediaId)) {
			log.error("MediaUploador resp msg->" + result);
		}
		return mediaId;
	}
	
	public static String uploadGetMediaURL(String url, BaseToken token, String type, byte[] data) {
		String result = upload(url, token, type, data);
		JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
		JSONObject jsonObj = jacksonConverter.objectFromString(result, JSONObject.class);
		String mediaUrl = jsonObj.getString("url");
		System.out.println(result);
		if (StringUtils.isBlank(mediaUrl)) {
			log.error("MediaUploador resp msg->" + result);
		}
		return mediaUrl;
	}
	
	public static String[] uploadGetMediaIdAndUrl(String url, BaseToken token, String type, byte[] data) {
		String result = upload(url, token, type, data);
		JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
		JSONObject jsonObj = jacksonConverter.objectFromString(result, JSONObject.class);
		System.out.println(result);
		String mediaId = jsonObj.getString("media_id");
		String mediaUrl = jsonObj.getString("url");
		if (StringUtils.isBlank(mediaId)) {
			log.error("MediaUploador resp msg->" + result);
		}
		return new String[]{mediaId, mediaUrl};
	}
	
	public static String uploadTemp(BaseToken token, String type, byte[] data) {
		return uploadGetMediaId(UPLOAD_URL, token, type, data);
	}
	
	public static String uploadTempWebPic(String appid, String appSecret, String type, String filePath) {
		try {
			BaseToken token = TokenService.getTokenService().getAccessToken(appid, appSecret);
			return uploadGetMediaId(UPLOAD_URL, token, type, readPicStream(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String uploadTempWebPic(BaseToken token, String type, String filePath) throws Exception {
		return uploadGetMediaId(UPLOAD_URL, token, type, readPicStream(filePath));
	}
	
	public static String[] uploadPersistent(BaseToken token, String type, byte[] data) {
		return uploadGetMediaIdAndUrl(MATERIAL_ADD_URL, token, type, data);
	}
	
	public static String[] uploadPersistentWebPic(String appid, String appSecret, String type, String filePath) {
		try {
			BaseToken token = TokenManager.getBaseToken(appid, appSecret);
			return uploadGetMediaIdAndUrl(MATERIAL_ADD_URL, token, type, readPicStream(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] uploadPersistentWebPic(BaseToken token, String type, String filePath) throws Exception {
		return uploadGetMediaIdAndUrl(MATERIAL_ADD_URL, token, type, readPicStream(filePath));
	}
	
	public static String uploadPersistentImage(BaseToken token, String type, byte[] data) {
		return uploadGetMediaURL(UPLOAD_IMAGE_URL, token, type, data);
	}
	
	public static String uploadPersistentImage(String appid, String appSecret, String type, String filePath) {
		try {
			BaseToken token = TokenManager.getBaseToken(appid, appSecret);
			return uploadGetMediaURL(UPLOAD_IMAGE_URL, token, type, readPicStream(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String uploadPersistentImageWebPic(BaseToken token, String type, String filePath) throws Exception {
		return uploadGetMediaURL(UPLOAD_IMAGE_URL, token, type, readPicStream(filePath));
	}
	
	public static byte[] downloadMaterial(BaseToken token, String mediaId) throws Exception {
		
		String url = MATERIAL_GET_URL.replace("{$ACCESS_TOKEN}", token.getAccess_token())
				.replace("{$MEDIA_ID}", mediaId);
		
		return readPicStream(url);
	}
	
	public static byte[] downloadMaterial(String appid, String appSecret, String mediaId) throws Exception {
		BaseToken token = TokenManager.getBaseToken(appid, appSecret);
		String url = MATERIAL_GET_URL.replace("{$ACCESS_TOKEN}", token.getAccess_token())
				.replace("{$MEDIA_ID}", mediaId);
		return readPicStream(url);
	} 

	public static byte[] readPicStream(String webPic) throws Exception {
		URL url = new URL(webPic);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(15 * 1000);
		
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
}
