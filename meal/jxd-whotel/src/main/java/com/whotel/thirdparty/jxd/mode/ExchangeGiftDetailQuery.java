package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 礼品详情
 * @author 冯勇
 *
 */
public class ExchangeGiftDetailQuery extends AbstractInputParam {

	private String opType = "可兑换礼品详情";
	
	private String beginDate;
	
	private String endDate;
	
	private String itemId;
	
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

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Override
	public String getRoot() {
		return null;
	}

}
