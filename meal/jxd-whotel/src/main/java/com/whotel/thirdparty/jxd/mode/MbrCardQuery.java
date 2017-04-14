package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 会员卡查询
 * @author 柯鹏程
 *
 */
public class MbrCardQuery extends AbstractInputParam {

	private String opType = "会员储值卡查询";
	
	private Map<String,String> mbrCardQuery;//会员卡类型 传代码，多个代码用逗号分开
	
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Map<String,String> getMbrCardQuery() {
		return mbrCardQuery;
	}

	public void setMbrCardQuery(Map<String,String> mbrCardQuery) {
		this.mbrCardQuery = mbrCardQuery;
	}
	
	@Override
	public String getRoot() {
		return null;
	}
}
