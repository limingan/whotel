package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 封装会员资料修改请求
 * @author 冯勇
 * 
 */
public class MemberProfileUpdate extends AbstractInputParam {
	private String opType = "会员资料修改";
	private Map<String, String> profileUpdate;

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Map<String, String> getProfileUpdate() {
		return profileUpdate;
	}

	public void setProfileUpdate(Map<String, String> profileUpdate) {
		this.profileUpdate = profileUpdate;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
