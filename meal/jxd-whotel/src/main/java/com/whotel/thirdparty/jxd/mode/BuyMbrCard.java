package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 购买会员卡
 * @author 柯鹏程
 *
 */
public class BuyMbrCard extends AbstractInputParam {
	
	private String opType = "会员网上买卡";
	
	private Map<String,String> mbrCardSale;
	
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
	public Map<String,String> getMbrCardSale() {
		return mbrCardSale;
	}
	public void setMbrCardSale(Map<String,String> mbrCardSale) {
		this.mbrCardSale = mbrCardSale;
	}
}
