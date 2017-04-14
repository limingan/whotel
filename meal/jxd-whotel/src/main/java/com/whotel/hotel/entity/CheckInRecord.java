package com.whotel.hotel.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

/**
 * 酒店客人入住记录
 */
@Entity(noClassnameStored=true)
public class CheckInRecord extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String companyId;//公司id

	private String name;//入住人名称
	
	private String mobile;//入住人手机
	
	private String ticket;//
	
	private String deviceId;//设备id
	
	private String content;//门锁秘钥
	
	private String orderNo;//捷云订单号
	
	private String openId;//微信id
	
	private String idNo;//身份证号码

	private String roomNo;//房号
	
	private Date createDate;//创建时间
	
	private Date updateDate;//修改时间
	
	private String errorMsg;//错误信息

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "CheckInRecord [companyId=" + companyId + ", name=" + name + ", mobile=" + mobile + ", ticket=" + ticket
				+ ", deviceId=" + deviceId + ", content=" + content + ", orderNo=" + orderNo + ", openId=" + openId
				+ ", idNo=" + idNo + ", roomNo=" + roomNo + ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", errorMsg=" + errorMsg + "]";
	}
}