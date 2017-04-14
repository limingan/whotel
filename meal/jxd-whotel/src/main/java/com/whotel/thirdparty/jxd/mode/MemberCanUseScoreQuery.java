package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 封装会员查询请求
 * 
 * @author 冯勇
 * 
 */
public class MemberCanUseScoreQuery extends AbstractInputParam {
	private String opType = "会员实时积分查询";

	private String profileId;
	
	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}
}
