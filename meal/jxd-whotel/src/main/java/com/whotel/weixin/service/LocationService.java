package com.whotel.weixin.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.core.bean.EventMsg;
import com.whotel.common.base.Constants;
import com.whotel.common.http.HttpHelper;
import com.whotel.common.util.SpringContextHolder;
import com.whotel.ext.json.JSONConvertFactory;
import com.whotel.ext.json.JSONDataUtil;
import com.whotel.ext.redis.RedisCache;
import com.whotel.weixin.bean.Location;

/**
 * 单例
 * @author 冯勇
 * 
 */
public class LocationService {

	private RedisCache cache;

	private static final Logger log = LoggerFactory.getLogger(LocationService.class);

	private LocationService() {
		cache = SpringContextHolder.getBean(RedisCache.class);
	}

	private final static String CACHE_PRE_WEIXIN = "Location_wx_";
	private final static String CACHE_PRE_HTML5 = "Location_h5_";
	//
	private volatile static LocationService service;
	private static double[] Default = new double[] { 22.540439, 114.067612 };

	public static LocationService getLocationService() {
		if (service == null) {
			synchronized (LocationService.class) {
				if (service == null) {
					service = new LocationService();
				}
			}
		}
		return service;
	}

	public double[] turnGps2BaiduXY(double lon,double lat){
		try{
			String param="ak=84c3749022397e70bf264280c5292316&from=1&to=5&coords="+lon+","+lat;
			String resp = HttpHelper.connect("http://api.map.baidu.com/geoconv/v1/").timeout(5000).post(param).html();
			JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
			JSONObject jsonObj= jacksonConverter.objectFromString(resp, JSONObject.class);
			Integer  status = null;
			if(jsonObj != null && jsonObj.get("status") != null ){
				status = (Integer)jsonObj.get("status");
			}			
			if(status!=null&&status==0){
				JSONArray jsonArr=jsonObj.getJSONArray("result");
				JSONObject pos= (JSONObject)jsonArr.get(0);
				return new double[]{pos.getDouble("x"),pos.getDouble("y")};
			}else{
				return new double[]{lon,lat};
			}
		}catch(Exception e){
			e.printStackTrace();
			return new double[]{lon,lat};
		}
		
	}
	
	public void recordFanLocation(EventMsg msg) {
		log.info(msg.getFromUserName() + ",weixin获取地址坐标信息－＞" + msg.getLatitude() + "," + msg.getLongitude());
		double baiduPos[]=turnGps2BaiduXY(msg.getLongitude(),msg.getLatitude());
		Location loc = Location.createWeixinLocation(baiduPos[1], baiduPos[0]);
		cache.put(CACHE_PRE_WEIXIN + msg.getFromUserName(), loc, Constants.CacheKey.DAY_TIMIE_OUT);
	}

	public void recordFanHtml5Location(String openId, double lat, double lon) {
		log.info(openId + ",html5获取地址坐标信息－＞" + lat + "," + lon);
		Location loc = Location.createHtml5Location(lat, lon);
		cache.put(CACHE_PRE_HTML5 + openId, loc, Constants.CacheKey.DAY_TIMIE_OUT);
	}

	public Location getLocation(String openId) {
		Object obj = cache.get(CACHE_PRE_HTML5 + openId);
		if (obj != null) {
			return (Location) obj;
		}
		obj = cache.get(CACHE_PRE_WEIXIN + openId);
		if (obj != null) {
			return (Location) obj;
		}
		return null;
	}
	
	public Location getLocation(String openId, boolean returnDefault) {
		Location loc=getLocation(openId);
		if(loc==null && returnDefault){
			loc = new Location();
			loc.setLat(Default[0]);
			loc.setLon(Default[1]);
			return loc;
		}
		return loc;
		
	}

	public double[] getFanLocation(String openId) {
		return getFanLocation(openId, true);
	}

	public double[] getFanLocation(String openId, boolean returnDefault) {

		Location loc = getLocation(openId);
		if (loc != null) {
//			log.info("get location from--->" + (loc.getFrom() == Location.FromHtml5 ? "HTML5" : "Weixin"));
			return new double[] { loc.getLat(), loc.getLon() };
		}
//		log.warn("获取位置信息失败－＞" + openId);
		if (returnDefault) {
			return Default;
		} else {
			return null;
		}

	}
	/**
	 * 加载所需对象
	 */
}
