package com.whotel.ext.json;

public class JSONConvertFactory {

	private static JSONDataUtil jackson = new JSONDataUtilByJackson();
	private static JSONDataUtil gson = new JSONDataUtilByGson();

	public static JSONDataUtil getJacksonConverter() {
		return jackson;
	}

	public static JSONDataUtil getGsonConverter() {
		return gson;
	}
}
