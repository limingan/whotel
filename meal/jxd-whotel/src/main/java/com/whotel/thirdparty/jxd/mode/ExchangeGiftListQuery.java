package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 积分兑换列表查询
 * @author 冯勇
 *
 */
public class ExchangeGiftListQuery extends AbstractInputParam {

	private String opType = "可兑换列表查询";
	
	private String beginDate;
	
	private String endDate;
	
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
		return null;
	}

}
