package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 封装会员交易请求
 * @author 冯勇
 *
 */
public class MemberTrade extends AbstractInputParam {
	private String opType = "会员交易";
	private Map<String, String> profileCa;

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Map<String, String> getProfileCa() {
		return profileCa;
	}

	public void setProfileCa(Map<String, String> profileCa) {
		this.profileCa = profileCa;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}
}
