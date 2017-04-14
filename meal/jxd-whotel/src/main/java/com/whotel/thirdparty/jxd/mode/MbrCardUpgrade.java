package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

public class MbrCardUpgrade extends AbstractInputParam {
	
	private String opType = "会员卡升级";
	
	private Integer upgradetype;//升级类型
	
	private String profileid;//会员编号
	
	private String oldMbrCardType;//原会员卡类型
	
	private String newMbrCardType;//升级后的卡类型
	
	private Float amt;//传入的升级金额
	
	private String remark;//备注

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Integer getUpgradetype() {
		return upgradetype;
	}

	public void setUpgradetype(Integer upgradetype) {
		this.upgradetype = upgradetype;
	}

	public String getProfileid() {
		return profileid;
	}

	public void setProfileid(String profileid) {
		this.profileid = profileid;
	}

	public String getOldMbrCardType() {
		return oldMbrCardType;
	}

	public void setOldMbrCardType(String oldMbrCardType) {
		this.oldMbrCardType = oldMbrCardType;
	}

	public String getNewMbrCardType() {
		return newMbrCardType;
	}

	public void setNewMbrCardType(String newMbrCardType) {
		this.newMbrCardType = newMbrCardType;
	}

	public Float getAmt() {
		return amt;
	}

	public void setAmt(Float amt) {
		this.amt = amt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
