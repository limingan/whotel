package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 封装会员消费查询请求
 * 
 * @author 冯勇
 * 
 */
public class MemberTransQuery extends AbstractInputParam {
	private String opType = "会员消费查询";
	private Map<String, String> mbrTransQuery;

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Map<String, String> getMbrTransQuery() {
		return mbrTransQuery;
	}

	public void setMbrTransQuery(Map<String, String> mbrTransQuery) {
		this.mbrTransQuery = mbrTransQuery;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
