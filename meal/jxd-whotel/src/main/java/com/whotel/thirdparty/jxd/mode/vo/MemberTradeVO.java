package com.whotel.thirdparty.jxd.mode.vo;

import java.util.Date;

public class MemberTradeVO {
	
	private String profileId;
	private String outlet;
	private float amount;
	private float balance;
	private String remark;
	private Date createDate;
	
	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getOutlet() {
		return outlet;
	}

	public void setOutlet(String outlet) {
		this.outlet = outlet;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "MemberTradeVO [profileId=" + profileId + ", outlet=" + outlet
				+ ", amount=" + amount + ", balance=" + balance + ", remark="
				+ remark + ", createDate=" + createDate + "]";
	}
}
