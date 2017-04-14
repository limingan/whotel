package com.whotel.thirdparty.jxd.mode.vo;


/**
 * 酒店预订结果
 * @author 冯勇
 * 
 */
public class ReservationResult {
	private String hotelCode;
	private String confirmationID;
	private String orderNo;
	private String errorMsg;

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getConfirmationID() {
		return confirmationID;
	}

	public void setConfirmationID(String confirmationID) {
		this.confirmationID = confirmationID;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
