package com.whotel.thirdparty.jxd.mode.vo;

import java.util.Date;

import com.whotel.common.util.RepoUtil;
import com.whotel.front.entity.WeixinFan;

public class WeixinFanVO {
	
	private int notReadNum;//未读消息数
	
	private String openId;//微信粉
	
	private Date createTime;

	public int getNotReadNum() {
		return notReadNum;
	}

	public void setNotReadNum(int notReadNum) {
		this.notReadNum = notReadNum;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public WeixinFan getWeixinFan(){
		return RepoUtil.getWeixinFan(openId);
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
