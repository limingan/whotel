package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 会员券发放
 * @author 冯勇
 * 
 */
public class SendCouponQuery extends AbstractInputParam {
	
	private String opType = "会员券发放";
	
	private String profileid;
	private String tickettype;
	private Integer quantity;
	private String operator;
	private String remark;
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getProfileid() {
		return profileid;
	}

	public void setProfileid(String profileid) {
		this.profileid = profileid;
	}

	public String getTickettype() {
		return tickettype;
	}

	public void setTickettype(String tickettype) {
		this.tickettype = tickettype;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
