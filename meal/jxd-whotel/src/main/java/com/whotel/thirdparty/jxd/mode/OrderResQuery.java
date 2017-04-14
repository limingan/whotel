package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 封装预订查询
 * @author 冯勇
 * 
 */
public class OrderResQuery extends AbstractInputParam {
	
	private String opType = "E云通预订查询";
	
	private Map<String, Object> resQuery;
	
	private String orderCategory;

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

	@Override
	public String getRoot() {
		return null;
	}

	public String getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}

}
