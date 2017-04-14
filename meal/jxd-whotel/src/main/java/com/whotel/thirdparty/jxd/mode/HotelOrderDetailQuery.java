package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 酒店订单详情查询
 * @author 冯勇
 * 
 */
public class HotelOrderDetailQuery extends AbstractInputParam {
	
	private String opType = "E云通预订明细查询";
	
	private Map<String, Object> resQuery;

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
		// TODO Auto-generated method stub
		return null;
	}

}
