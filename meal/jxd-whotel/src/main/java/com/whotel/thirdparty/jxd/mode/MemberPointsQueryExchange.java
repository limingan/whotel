package com.whotel.thirdparty.jxd.mode;

import java.util.Date;
import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 封装积分兑换记录查询请求
 * @author 冯勇
 *
 */
public class MemberPointsQueryExchange extends AbstractInputParam {
	private String opType = "积分兑换记录查询";

	private String ProfileId;
	private String BeginDate;
	private String EndDate;
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

	public String getProfileId() {
		return ProfileId;
	}

	public void setProfileId(String profileId) {
		ProfileId = profileId;
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

	public String getBeginDate() {
		return BeginDate;
	}

	public void setBeginDate(String beginDate) {
		BeginDate = beginDate;
	}

}
