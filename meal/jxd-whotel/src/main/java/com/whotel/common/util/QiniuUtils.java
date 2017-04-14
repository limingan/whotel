package com.whotel.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.whotel.ext.json.JSONConvertFactory;
import com.whotel.ext.json.JSONDataUtil;

/**
 * About Qiniu
 * @author 冯勇
 * 
 */
public class QiniuUtils {
	private static final Logger log = LoggerFactory.getLogger(QiniuUtils.class);

	private static String upload_url = "http://upload.qiniu.com/";
	private static String defaultBucket = null;
	private static String thumbnail = "";
	private static boolean initialized = false;
	private static Auth auth = null;
	
	private static Map<String, String> bucketDomain = null;
	
//	private static Map<String, String> bucketResType = null;

	private QiniuUtils() {}
	
	public static void init() {
		log.info("QiniuUtils Initializing...");
		String ACCESS_KEY = "7TVp7dAC9xHLtd8VHPnHjAJOy9YLh7rhwbzZe7s2";
		String SECRET_KEY = "Ic96Wia-MQ4T2ma1wQfeqG_zlj1aRMhnZTeIsMGg";
		
		
		
		// audio video picture
		bucketDomain = new HashMap<String, String>();
		bucketDomain.put("jxd-res", "http://res.gshis.net/");
//		bucketDomain.put("jxd-res-audio", "http://7xljym.com1.z0.glb.clouddn.com/");
//		bucketDomain.put("jxd-res-video", "http://7xljym.com1.z0.glb.clouddn.com/");
//		bucketDomain.put("jxd-res-picture", "http://7xljym.com1.z0.glb.clouddn.com/");
		
//		bucketResType = new HashMap<String, String>();
//		bucketResType.put("audio", "jxd-res-audio");
//		bucketResType.put("video", "jxd-res-video");
//		bucketResType.put("picture", "jxd-res-picture");
		
		defaultBucket = "jxd-res";
		
		auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		initialized = true;
		log.info("QiniuUtils Initialized...");
	}
	
	public static String getBucket(String resType) {
		if (!initialized) {
			QiniuUtils.init();
		}
//		String bucket = bucketResType.get(resType);
//		
//		if(StringUtils.isNotBlank(bucket)) {
//			return bucket;
//		}
		
		return defaultBucket;
	}

	/**
	 * 获取上传凭证
	 * @return
	 */
	public static String getToken() {
		// 请确保该bucket已经存在
		if (!initialized) {
			QiniuUtils.init();
		}
		String uptoken = auth.uploadToken(defaultBucket);
		return uptoken;
	}

	/**
	 * 获取上传凭证
	 * @param saveKey
	 * @return
	 */
	public static String getToken(String saveKey) {
		// 请确保该bucket已经存在
		if (!initialized) {
			QiniuUtils.init();
		}
		String uptoken = auth.uploadToken(defaultBucket, saveKey);
		return uptoken;
	}
	
	/**
	 * 获取上传凭证
	 * @param bucket
	 * @param saveKey
	 * @return
	 */
	public static String getToken(String bucket, String saveKey) {
		// 请确保该bucket已经存在
		if (!initialized) {
			QiniuUtils.init();
		}
		String uptoken = auth.uploadToken(bucket, saveKey);
		return uptoken;
	}
	
	public static String getToken(String bucket, String saveKey, String persistentOps, String notifyUrl, String pipeline) {
		// 请确保该bucket已经存在
		if (!initialized) {
			QiniuUtils.init();
		}
		String uptoken = auth.uploadToken(bucket, saveKey, 3600, new StringMap()
        .putNotEmpty("persistentOps", persistentOps)
        .putNotEmpty("persistentNotifyUrl", notifyUrl)
        .putNotEmpty("persistentPipeline", pipeline), true);
		return uptoken;
	}

	public static String getResUrl(String path) {
		if (!initialized) {
			QiniuUtils.init();
		}
		if (StringUtils.isNotBlank(path) && !StringUtils.startsWithIgnoreCase(path, "http://")) {
			String[] paths = StringUtils.split(path, ":");
			String[] localPaths = StringUtils.split(path, "/");
			if(paths != null && paths.length == 2) {
				return bucketDomain.get(paths[0]) + path;
			}else if(localPaths != null){
				return path;
			}
			return bucketDomain.get(defaultBucket) + path;
		} else {
			return path;
		}
	}

	public static String getResThumbUrl(String path) {
		if (!initialized) {
			QiniuUtils.init();
		}
		if (StringUtils.isNotBlank(path) && !StringUtils.startsWithIgnoreCase(path, "http://")) {
			String[] paths = StringUtils.split(path, ":");
			if(paths != null && paths.length == 2) {
				return bucketDomain.get(paths[0]) + path + "/" + QiniuUtils.thumbnail;
			}
			return bucketDomain.get(defaultBucket) + path + "/" + QiniuUtils.thumbnail;
		} else {
			return path + "/" + QiniuUtils.thumbnail;
		}
	}
	
	public static String genSaveKey(String dir, String fileName) {
		String saveKey = null;
		
		if(StringUtils.isBlank(fileName)) {
			fileName = TextUtil.get64UUID();
		}
		if(StringUtils.isNotBlank(dir)) {
			if(StringUtils.endsWith(dir, "/")) {
				dir += DateUtil.format(new Date(), "yyyyMM");
			}
			saveKey = dir + "/" + fileName;
		} else {
			saveKey = fileName;
		}
		return saveKey;
	}
	
	public static String genServerKey(String bucket, String dir, String fileName) {
		return bucket + ":" + genSaveKey(dir, fileName);
	}
	
	public static String upload(byte[] data) {
		String bucket = QiniuUtils.getBucket(null);
		String key = QiniuUtils.genServerKey(bucket, null, null);
		String token = QiniuUtils.getToken(bucket, key);
		return upload(token, key, data);
	}
	
	/**
	 * 模拟form表单的形式 ，上传文件 以输出流的形式把文件写入到url中，然后用输入流来获取url的响应
	 * @throws IOException
	 */
	public static String upload(String token, String key, byte[] data) {
		String result = null;

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		
		try {
			URL urlObj = new URL(upload_url);
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
			sb.append("Content-Disposition: form-data;name=\"key\"\r\n\r\n");
			sb.append(key);
			sb.append("\r\n");
			
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			
			sb.append("Content-Disposition: form-data;name=\"token\"\r\n\r\n");
			sb.append(token);
			sb.append("\r\n");
			
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			
			sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + key + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			System.out.println(sb);
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
				
				JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
				JSONObject jsonObj = jacksonConverter.objectFromString(result, JSONObject.class);
				return jsonObj.getString("key");
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
}
