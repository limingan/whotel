package com.whotel.ext.json;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json data opration util
 * @author fengyong
 *
 */
public class JSONDataUtilByJackson implements JSONDataUtil {

	private static Log log = LogFactory.getFactory().getInstance(JSONDataUtilByJackson.class);

	private static ObjectMapper objectMapper = new ObjectMapper();
	static {
	    objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
	    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * get string from object
	 * @param obj
	 * @return
	 */
	@Override
	public String jsonfromObject(Object obj) {
		StringWriter sw = new StringWriter();
		String jsonString = "";
		try {
			objectMapper.writeValue(sw, obj);
			jsonString = sw.toString();
		} catch (Exception e) {
			log.error("Cannot convert object: " + obj);
			return null;
		} finally {
			try {
				sw.close();
				sw = null;
			} catch (IOException e) {
				log.error("Convert error of object: " + obj);
			}
		}
		return jsonString;
	}

	@Override
	public String jsonfromObject(Object obj, String dateFormat) {
		StringWriter sw = new StringWriter();
		String jsonString = "";
		String df;
		if (StringUtils.isBlank(dateFormat)) {
			df = "yyyy-MM-dd HH:mm:ss";
		} else {
			df = dateFormat;
		}
		try {
			objectMapper.writer(new SimpleDateFormat(df)).writeValue(sw, obj);
			jsonString = sw.toString();
		} catch (Exception e) {
			log.error("Cannot convert object: " + obj);
			return null;
		} finally {
			try {
				sw.close();
				sw = null;
			} catch (IOException e) {
				log.error("Convert error of object: " + obj);
			}
		}
		return jsonString;
	}

	@Override
	public <T> T objectFromString(String str, Class<T> clz) {
		// read from file, convert it to user class
		T ret = null;
		try {
			ret = objectMapper.readValue(str, clz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	@Override
	public <T> List<T> listFromString(String str, Class<T> clz) {
		ObjectMapper mapper = new ObjectMapper();  
		JavaType javaType = mapper.getTypeFactory().constructParametrizedType(ArrayList.class, List.class, clz);  
		List<T> list = null;
		try {
			list = mapper.readValue(str, javaType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return list;
	}
	
	@Override
	public <T> T objectFromStringType(String str, Type type) {
		throw new RuntimeException("not implements");
	}

	@Override
	public <T> T readValue(String jsonStr, Class<T> valueType) {
		try {
			return objectMapper.readValue(jsonStr, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
