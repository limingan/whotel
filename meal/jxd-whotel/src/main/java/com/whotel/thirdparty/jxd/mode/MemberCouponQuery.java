package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 会员券明细查询
 * @author 冯勇
 * 
 */
public class MemberCouponQuery extends AbstractInputParam {
	
	private String opType = "会员券明细查询";
	
	private String profileId;
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}
	
	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
