package com.whotel.thirdparty.jxd.mode.vo;

import java.util.Date;

import com.whotel.common.util.RepoUtil;
import com.whotel.front.entity.WeixinFan;

public class RecommendFanVO {

	private String openId;
	
	private String recommendOpenId;
	
	private Float credit;
	
	private Float commissionMoney;
	
	private Date createTime;
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public WeixinFan getWeixinFan() {
		return RepoUtil.getWeixinFan(openId);
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Float getCredit() {
		return credit;
	}

	public void setCredit(Float credit) {
		this.credit = credit;
	}

	public Float getCommissionMoney() {
		return commissionMoney;
	}

	public void setCommissionMoney(Float commissionMoney) {
		this.commissionMoney = commissionMoney;
	}

	public String getRecommendOpenId() {
		return recommendOpenId;
	}

	public void setRecommendOpenId(String recommendOpenId) {
		this.recommendOpenId = recommendOpenId;
	}
}
