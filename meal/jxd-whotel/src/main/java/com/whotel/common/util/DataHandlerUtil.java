package com.whotel.common.util;

import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.whotel.common.dto.ResultData;

@Component
@Scope("prototype")
public class DataHandlerUtil {

	private HashMap<String, Object> map = null;
	
	public void clear() {
		if(map != null) {
			map.clear();
		}
		map = null;
	}
	
	public void add(String key, Object value) {
		if(map == null) {
			map = new HashMap<String, Object>();
		}
		if(value != null) {
			map.put(key, value);
		}
	}
	
	public ResultData genResultData(String code, String message) {
		ResultData rd = new ResultData(code, message);
		rd.setData(map);
		return rd;
	}
	
	public ResultData genResultData(String code, String message, Object obj) {
		ResultData rd = null;
		if(obj == null) {
			rd = new ResultData();
			rd.setCode("-1");
			rd.setMessage("not data");
			return rd;
		}
		rd = new ResultData(code, message);
		rd.setData(obj);
		return rd;
	}
}
