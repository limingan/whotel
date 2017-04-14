package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 会员券核销
 * @author 冯勇
 * 
 */
public class UseCouponQuery extends AbstractInputParam {
	
	private String opType = "会员券核销";
	
	private String profileId;
	private String ticketcode;
	private String operator;
	private String remark;
	
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

	public String getTicketcode() {
		return ticketcode;
	}

	public void setTicketcode(String ticketcode) {
		this.ticketcode = ticketcode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
