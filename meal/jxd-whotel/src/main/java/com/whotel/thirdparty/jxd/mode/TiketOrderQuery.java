package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 封装门票预订请求
 * @author 柯鹏程
 * 
 */
public class TiketOrderQuery extends AbstractInputParam {
	private String opType = "票券预订";
	private Map<String, Object> resQuery;

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	@Override
	public String getRoot() {
		return null;
	}

	public Map<String, Object> getResQuery() {
		return resQuery;
	}

	public void setResQuery(Map<String, Object> resQuery) {
		this.resQuery = resQuery;
	}
}
