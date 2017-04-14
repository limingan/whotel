package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 酒店城市列表查询
 * @author 冯勇
 * 
 */
public class TicketCityQuery extends AbstractInputParam {
	
	private String opType = "酒店城市列表";
	
	private String orderCategory = "WqTicket";
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}
	
	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}

}
