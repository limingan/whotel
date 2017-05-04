package com.whotel.ext.json;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * json data opration util
 * @author fengyong
 *
 */
public class JSONDataUtilByGson implements JSONDataUtil {

	private static Log log = LogFactory.getFactory().getInstance(JSONDataUtilByGson.class);

	private static final String DFT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static Gson dftGson = new GsonBuilder().setDateFormat(DFT_DATE_FORMAT).setPrettyPrinting().create();

	private Map<String, Gson> instanceMap = new HashMap<>();

	/**
	 * get string from object
	 * @param obj
	 * @return
	 */
	@Override
	public String jsonfromObject(Object obj) {
		return jsonfromObject(obj, DFT_DATE_FORMAT);
	}

	@Override
	public String jsonfromObject(Object obj, String dateFormat) {
		String df;
		if (StringUtils.isBlank(dateFormat)) {
			df = DFT_DATE_FORMAT;
		} else {
			df = dateFormat;
		}

		Gson gs;
		if (instanceMap.containsKey(df)) {
			gs = instanceMap.get(df);
		} else {
			synchronized (instanceMap) {
				gs = new GsonBuilder().setDateFormat(df).setPrettyPrinting().create();
				instanceMap.put(df, gs);
			}
		}
		return gs.toJson(obj);
	}

	@Override
	public <T> T objectFromString(String str, Class<T> clz) {
		try {
			// read from file, convert it to user class
			T ret = dftGson.fromJson(str, clz);
			return ret;
		} catch (Exception e) {
			log.error("Cannot convert object", e);
		}
		return null;
	}
	
	@Override
	public <T> List<T> listFromString(String str, Class<T> clz) {
		List<T> list = dftGson.fromJson(str,  
                new TypeToken<List<T>>() {  
                }.getType());  
		return list;
	}

	@Override
	public <T> T objectFromStringType(String str, Type type) {
		try {
			// read from file, convert it to user class
			T ret = dftGson.fromJson(str, type);
			return ret;
		} catch (Exception e) {
			log.error("Cannot convert object", e);
		}
		return null;
	}

	@Override
	public <T> T readValue(String jsonStr, Class<T> valueType) {
		return null;
	}

	/**
	 * get json string for ext
	 * @param obj
	 * @param totalCount
	 * @return
	 */
	@Override
	public String extJsonSupportPage(Object obj, int totalCount) {
		String json = "{'rows':" + jsonfromObject(obj) + ", 'totalCount':" + totalCount + "}";
		return json;
	}

}
