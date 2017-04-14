package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 可预订分店列表查询
 * @author 冯勇
 * 
 */
public class TicketBranchQuery extends AbstractInputParam {
	
	private String opType = "可预订分店列表查询";
	
	private String beginDate;
	
	private String endDate;
	
	private String source = "weixin";
	
	private String orderCategory = "WqTicket";

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}
	
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}

}
