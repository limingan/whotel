package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 封装门票预订请求
 * @author 柯鹏程
 * 
 */
public class TiketOrderStateQuery extends AbstractInputParam {
	private String opType = "预订查询";
	private Map<String, Object> orderQuery;

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

	public Map<String, Object> getOrderQuery() {
		return orderQuery;
	}

	public void setOrderQuery(Map<String, Object> orderQuery) {
		this.orderQuery = orderQuery;
	}
}
