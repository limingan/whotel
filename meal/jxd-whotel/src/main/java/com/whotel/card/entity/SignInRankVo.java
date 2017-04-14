package com.whotel.card.entity;

import java.io.Serializable;

import com.whotel.common.util.RepoUtil;
import com.whotel.front.entity.WeixinFan;

public class SignInRankVo implements Serializable{
	
	private static final long serialVersionUID = 6834021205981167892L;
	
	private String openId;
	
	private Integer signInCount = 0;//签到次数

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getSignInCount() {
		return signInCount;
	}

	public void setSignInCount(Integer signInCount) {
		this.signInCount = signInCount;
	}

	public WeixinFan getWeixinFan(){
		return RepoUtil.getWeixinFan(openId);
	}
}
