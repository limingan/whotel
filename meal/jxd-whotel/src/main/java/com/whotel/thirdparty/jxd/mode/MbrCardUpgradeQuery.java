package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

public class MbrCardUpgradeQuery extends AbstractInputParam {
	
	private String opType = "会员卡升级列表";
	
	private String mbrCardType;

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

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
