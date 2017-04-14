package com.whotel.thirdparty.jxd.mode.vo;

import java.util.Date;

public class PointCouponVO {
	private String name;
	private String code;
	private Integer couponNumber;
	private Date exchangeBegin;
	private Date exchangeEnd;
	private String usedVlid;
	private int delayDate;
	private Date useBegin;
	private Date useEnd;
	private float cash;
	private String cashFlag;
	private String hotelCode;
	private String hotelName;
	private String couponTypeCode;
	private String couponTypeName;
	private int sortID;
	private int points;
	private String memo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getExchangeBegin() {
		return exchangeBegin;
	}

	public void setExchangeBegin(Date exchangeBegin) {
		this.exchangeBegin = exchangeBegin;
	}

	public Date getExchangeEnd() {
		return exchangeEnd;
	}

	public void setExchangeEnd(Date exchangeEnd) {
		this.exchangeEnd = exchangeEnd;
	}

	public String getUsedVlid() {
		return usedVlid;
	}

	public void setUsedVlid(String usedVlid) {
		this.usedVlid = usedVlid;
	}

	public int getDelayDate() {
		return delayDate;
	}

	public void setDelayDate(int delayDate) {
		this.delayDate = delayDate;
	}

	public Date getUseBegin() {
		return useBegin;
	}

	public void setUseBegin(Date useBegin) {
		this.useBegin = useBegin;
	}

	public Date getUseEnd() {
		return useEnd;
	}

	public void setUseEnd(Date useEnd) {
		this.useEnd = useEnd;
	}

	public float getCash() {
		return cash;
	}

	public void setCash(float cash) {
		this.cash = cash;
	}

	public String getCashFlag() {
		return cashFlag;
	}

	public void setCashFlag(String cashFlag) {
		this.cashFlag = cashFlag;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getCouponTypeCode() {
		return couponTypeCode;
	}

	public void setCouponTypeCode(String couponTypeCode) {
		this.couponTypeCode = couponTypeCode;
	}

	public String getCouponTypeName() {
		return couponTypeName;
	}

	public void setCouponTypeName(String couponTypeName) {
		this.couponTypeName = couponTypeName;
	}

	public int getSortID() {
		return sortID;
	}

	public void setSortID(int sortID) {
		this.sortID = sortID;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Integer getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(Integer couponNumber) {
		this.couponNumber = couponNumber;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
