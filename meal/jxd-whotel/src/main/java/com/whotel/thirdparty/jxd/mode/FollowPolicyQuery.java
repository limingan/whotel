package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 封装E云通营销政策查询请求
 * @author 柯鹏程
 *
 */
public class FollowPolicyQuery extends AbstractInputParam {
	
	private String opType = "E云通营销政策查询";
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}
}
