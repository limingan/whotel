package com.whotel.thirdparty.jxd.mode;

import java.util.Date;
import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 封装宴会预订请求
 * @author 柯鹏程
 *
 */
public class BanquetReservation extends AbstractInputParam {
	private String opType = "E云通宴会会议预订";
	
	private String guestName;//客人姓名
	
	private String contactMobile;//联系人手机
	
	private String contactTel;//联系人电话
	
	private String contactEmail;//联系人邮箱
	
	private Date arriveDate;//到达日期
	
	private String guestDemand;//其它要求
	

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public Date getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}

	public String getGuestDemand() {
		return guestDemand;
	}

	public void setGuestDemand(String guestDemand) {
		this.guestDemand = guestDemand;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}
}
