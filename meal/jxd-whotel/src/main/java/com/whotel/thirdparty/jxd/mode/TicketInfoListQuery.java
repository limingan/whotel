package com.whotel.thirdparty.jxd.mode;

import java.util.Date;

import com.whotel.ext.json.JSONConvertFactory;
import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 房型信息列表查询
 * @author 冯勇
 * 
 */
public class TicketInfoListQuery extends AbstractInputParam {
	
	private String opType = "房型信息列表查询";
	private String hotelCode; 	 //酒店代码，三位数字
	private Date beginDate; 	 //开始时间
	private Date endDate;   	 //结束时间
	private String roomTypeCode; //房型代码
	private String saleCode;     //优惠码
	private String profileId;    //会员ID
	private String mbrCardTypeCode;  //会员卡类型
	private String source = "weixin";//预订来源
	private Integer roomQty;      //预订间数
	
	private String name;         //名称
	private String addr;         //地址
	
	private String orderCategory = "WqTicket";
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRoomTypeCode() {
		return roomTypeCode;
	}

	public void setRoomTypeCode(String roomTypeCode) {
		this.roomTypeCode = roomTypeCode;
	}

	public String getSaleCode() {
		return saleCode;
	}

	public void setSaleCode(String saleCode) {
		this.saleCode = saleCode;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getRoomQty() {
		return roomQty;
	}

	public void setRoomQty(Integer roomQty) {
		this.roomQty = roomQty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return JSONConvertFactory.getGsonConverter().jsonfromObject(this, "yyyy-MM-dd");
	}

	public String getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}

	public String getMbrCardTypeCode() {
		return mbrCardTypeCode;
	}

	public void setMbrCardTypeCode(String mbrCardTypeCode) {
		this.mbrCardTypeCode = mbrCardTypeCode;
	}
}
