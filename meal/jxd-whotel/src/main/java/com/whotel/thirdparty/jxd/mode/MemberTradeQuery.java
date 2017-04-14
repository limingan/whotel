package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 封装会员交易请求
 * @author 冯勇
 *
 */
public class MemberTradeQuery extends AbstractInputParam {
	private String opType = "会员储值查询";
	private Map<String, String> mbrAmountQuery;

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Map<String, String> getMbrAmountQuery() {
		return mbrAmountQuery;
	}

	public void setMbrAmountQuery(Map<String, String> mbrAmountQuery) {
		this.mbrAmountQuery = mbrAmountQuery;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}
}
