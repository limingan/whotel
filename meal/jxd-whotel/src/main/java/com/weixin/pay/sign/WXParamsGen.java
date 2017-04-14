package com.weixin.pay.sign;

import java.util.SortedMap;
import java.util.TreeMap;

public class WXParamsGen {
	
	/** 请求的参数 */
	private SortedMap<String, String> parameters;
	
	private String encode = "utf-8";
	
	public WXParamsGen() {
		this.parameters = new TreeMap<String, String>();
	}
	
	public WXParamsGen(SortedMap<String, String> params) {
		this.parameters = params;
	}

	/**
	 * 获取参数值
	 * @param parameter 参数名称
	 * @return String 
	 */
	public String getParameter(String parameter) {
		String s = this.parameters.get(parameter); 
		return (null == s) ? "" : s;
	}
	
	/**
	 * 设置参数值
	 * @param parameter 参数名称
	 * @param parameterValue 参数值
	 */
	public void setParameter(String parameter, String parameterValue) {
		String v = "";
		if(null != parameterValue) {
			v = parameterValue.trim();
		}
		this.parameters.put(parameter, v);
	}
	
	/**
	 * 返回所有的参数
	 * @return SortedMap
	 */
	public SortedMap<String, String> getAllParameters() {		
		return this.parameters;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
}
