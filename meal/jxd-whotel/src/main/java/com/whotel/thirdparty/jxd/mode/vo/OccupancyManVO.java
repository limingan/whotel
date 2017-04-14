package com.whotel.thirdparty.jxd.mode.vo;

/**
 * 入住人
 * @author 柯鹏程
 */
public class OccupancyManVO {
	
	private String regId;//登记号
	private String roomNo;//房号
	private String lockNo;//锁号
	private String guestCname;//客人姓名
	private String arrDate;//入住时间
	private String depDate;//离店时间
	private String idNo;//身份证号码
	private String mobile;//手机号码
	
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getLockNo() {
		return lockNo;
	}
	public void setLockNo(String lockNo) {
		this.lockNo = lockNo;
	}
	public String getGuestCname() {
		return guestCname;
	}
	public void setGuestCname(String guestCname) {
		this.guestCname = guestCname;
	}
	public String getArrDate() {
		return arrDate;
	}
	public void setArrDate(String arrDate) {
		this.arrDate = arrDate;
	}
	public String getDepDate() {
		return depDate;
	}
	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
