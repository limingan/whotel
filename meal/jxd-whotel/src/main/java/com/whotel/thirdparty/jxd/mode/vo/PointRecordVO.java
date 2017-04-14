package com.whotel.thirdparty.jxd.mode.vo;

import java.util.Date;

import com.whotel.common.enums.TradeType;

public class PointRecordVO {
	
	private String profileId;
	private String outlet;
	private float amount;
	private float balance;
	private String remark;
	private Date createDate;
	private TradeType tradeType;
	private String imgUrl;
	private String status;
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
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
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
	public TradeType getTradeType() {
		return tradeType;
	}
	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "PointRecordVO [profileId=" + profileId + ", outlet=" + outlet
				+ ", amount=" + amount + ", balance=" + balance + ", remark="
				+ remark + ", createDate=" + createDate + ", tradeType="
				+ tradeType + ", imgUrl=" + imgUrl + ", status=" + status + "]";
	}
}
