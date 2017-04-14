package com.whotel.card.vo;

import java.io.Serializable;

public class RecommendFanVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String recommendOpenId;
	
	private Integer fanNums = 0;//粉丝数量
	
	private Integer cancelNums = 0;//取消关注的粉丝数量
	
	private Integer memberFanNums = 0;//已注册会员的粉丝数量
	
	private Boolean isMember;
	
	private Boolean isFocus;
	
	private Integer count = 0;//粉丝数量

	public Boolean getIsMember() {
		return isMember;
	}

	public void setIsMember(Boolean isMember) {
		this.isMember = isMember;
	}

	public Boolean getIsFocus() {
		return isFocus;
	}

	public void setIsFocus(Boolean isFocus) {
		this.isFocus = isFocus;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getFanNums() {
		return fanNums;
	}

	public void setFanNums(Integer fanNums) {
		this.fanNums = fanNums;
	}

	public Integer getCancelNums() {
		return cancelNums;
	}

	public void setCancelNums(Integer cancelNums) {
		this.cancelNums = cancelNums;
	}

	public Integer getMemberFanNums() {
		return memberFanNums;
	}

	public void setMemberFanNums(Integer memberFanNums) {
		this.memberFanNums = memberFanNums;
	}

	public String getRecommendOpenId() {
		return recommendOpenId;
	}

	public void setRecommendOpenId(String recommendOpenId) {
		this.recommendOpenId = recommendOpenId;
	}
	
}
