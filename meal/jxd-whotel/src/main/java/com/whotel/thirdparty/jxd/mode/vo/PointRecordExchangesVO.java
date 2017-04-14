package com.whotel.thirdparty.jxd.mode.vo;

import java.util.Date;

public class PointRecordExchangesVO {
	
	private String profileId;
	private String itemCName;
	private Date transDate;
	private float score;
	private String status;
	
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getItemCName() {
		return itemCName;
	}
	public void setItemCName(String itemCName) {
		this.itemCName = itemCName;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
}
