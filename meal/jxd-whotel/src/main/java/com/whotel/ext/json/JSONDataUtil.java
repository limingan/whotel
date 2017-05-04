package com.whotel.ext.json;

import java.lang.reflect.Type;
import java.util.List;

/**
 * json data opration util
 * @author fengyong
 *
 */
public interface JSONDataUtil {

	public String jsonfromObject(Object obj);

	public String jsonfromObject(Object obj, String dateFormat);

	public <T> T objectFromString(String str, Class<T> clz);
	
	public <T> List<T> listFromString(String str, Class<T> clz);

	public <T> T objectFromStringType(String str, Type type);

	<T> T readValue(String jsonStr, Class<T> valueType);

	public String extJsonSupportPage(Object obj, int totalCount);
}
