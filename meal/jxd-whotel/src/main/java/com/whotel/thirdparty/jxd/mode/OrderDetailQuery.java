package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

public class OrderDetailQuery extends AbstractInputParam {

	private String opType="订单明细查询";

	private Map<String, Object> resQuery;
	
	@Override
	public String getRoot() {
		return "RealOperate";
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Map<String, Object> getResQuery() {
		return resQuery;
	}

	public void setResQuery(Map<String, Object> resQuery) {
		this.resQuery = resQuery;
	}
}
