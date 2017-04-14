package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 封装会员积分查询请求
 * @author 冯勇
 *
 */
public class MemberPointsQuery extends AbstractInputParam {
	private String opType = "会员积分查询";
	private Map<String, String> mbrScoreQuery;

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Map<String, String> getMbrScoreQuery() {
		return mbrScoreQuery;
	}

	public void setMbrScoreQuery(Map<String, String> mbrScoreQuery) {
		this.mbrScoreQuery = mbrScoreQuery;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}
}
