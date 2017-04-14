package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 会员卡类型
 * @author 柯鹏程
 *
 */
public class MbrCardTypeQuery extends AbstractInputParam {

	private String opType = "会员卡类型查询";
	
	private String mbrCardType;//会员卡类型 传代码，多个代码用逗号分开
	
	private String webSaleFlag;//网上售卖标志 0 表示非网上售卖卡类型，1表示网上可售卖卡类型，999 表示全部。
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getMbrCardType() {
		return mbrCardType;
	}

	public void setMbrCardType(String mbrCardType) {
		this.mbrCardType = mbrCardType;
	}

	public String getWebSaleFlag() {
		return webSaleFlag;
	}

	public void setWebSaleFlag(String webSaleFlag) {
		this.webSaleFlag = webSaleFlag;
	}
	
	@Override
	public String getRoot() {
		return null;
	}
}
