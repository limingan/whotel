package com.whotel.thirdparty.jxd.mode.vo;

public class MbrCardUpgradeVO {
	
	private String mbrCardTypeCode;//会员卡类型代码
	
	private String mbrCardTypeCname;//会员卡类型名称
	
	private Float ellAmount;//升级差额
	
	private String remark;//备注：升级后的好处

	public String getMbrCardTypeCode() {
		return mbrCardTypeCode;
	}

	public void setMbrCardTypeCode(String mbrCardTypeCode) {
		this.mbrCardTypeCode = mbrCardTypeCode;
	}

	public String getMbrCardTypeCname() {
		return mbrCardTypeCname;
	}

	public void setMbrCardTypeCname(String mbrCardTypeCname) {
		this.mbrCardTypeCname = mbrCardTypeCname;
	}

	public Float getEllAmount() {
		return ellAmount;
	}

	public void setEllAmount(Float ellAmount) {
		this.ellAmount = ellAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
