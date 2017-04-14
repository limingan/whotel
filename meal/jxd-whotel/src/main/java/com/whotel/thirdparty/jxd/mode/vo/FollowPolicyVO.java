package com.whotel.thirdparty.jxd.mode.vo;

public class FollowPolicyVO {

	private String policyRule;//政策类型ID 0返现，1优惠券，2积分
	private String policyRuleName;//政策类型
	private Float returnAmt;//返现金额或返积分
	private String remark;//备注
	private String ticketTypeCode;//券类型代码
	private Integer ticketCount;//券张数
	private String name;//返回信息

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPolicyRule() {
		return policyRule;
	}

	public void setPolicyRule(String policyRule) {
		this.policyRule = policyRule;
	}

	public String getPolicyRuleName() {
		return policyRuleName;
	}

	public void setPolicyRuleName(String policyRuleName) {
		this.policyRuleName = policyRuleName;
	}

	public Float getReturnAmt() {
		return returnAmt;
	}

	public void setReturnAmt(Float returnAmt) {
		this.returnAmt = returnAmt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTicketTypeCode() {
		return ticketTypeCode;
	}

	public void setTicketTypeCode(String ticketTypeCode) {
		this.ticketTypeCode = ticketTypeCode;
	}

	public Integer getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(Integer ticketCount) {
		this.ticketCount = ticketCount;
	}
	
}
