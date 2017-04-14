package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 封装会员查询请求
 * 
 * @author 冯勇
 * 
 */
public class MemberQuery extends AbstractInputParam {
	private String opType = "会员查询";
	private Map<String, String> mbrQuery;

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Map<String, String> getMbrQuery() {
		return mbrQuery;
	}

	public void setMbrQuery(Map<String, String> mbrQuery) {
		this.mbrQuery = mbrQuery;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}
}
