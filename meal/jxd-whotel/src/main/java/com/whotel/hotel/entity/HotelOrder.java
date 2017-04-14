package com.whotel.hotel.entity;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.hotel.enums.HotelOrderStatus;

@Entity(noClassnameStored=true)
public class HotelOrder extends BaseOrder {

	private static final long serialVersionUID = 633945348958294209L;
	
	private HotelOrderStatus status;
	
	private String hotelOrderNo;
	
	private String roomType;
	
	private String roomCode;
	
	private String hotelCode;
	
	private String priceSystemId;
	
	private String salesPromotionId;
	
	private Integer roomQty;
	
	@Embedded
	private List<RoomPrice> roomPrices;
	
	@Embedded
	private List<HotelOtherService> otherServices;
	
	private String roomSpecial;
	
	private String guestRemark;
	
	private String guestName;
	
	private String arriveTime;
	
	private Date checkInTime;
	
	private Date checkOutTime;
	
	private String priceName;
	
	private String recommendOpenId;//推荐人openid，后续可以查出某人推荐的订单
	
	private String saleName;
	
	private String rateCode;
	
	private String orderOperate;
	
	public String getHotelOrderNo() {
		return hotelOrderNo;
	}

	public void setHotelOrderNo(String hotelOrderNo) {
		this.hotelOrderNo = hotelOrderNo;
	}

	public HotelOrderStatus getStatus() {
		return status;
	}

	public void setStatus(HotelOrderStatus status) {
		this.status = status;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getPriceSystemId() {
		return priceSystemId;
	}

	public void setPriceSystemId(String priceSystemId) {
		this.priceSystemId = priceSystemId;
	}

	public String getSalesPromotionId() {
		return salesPromotionId;
	}

	public void setSalesPromotionId(String salesPromotionId) {
		this.salesPromotionId = salesPromotionId;
	}

	public Integer getRoomQty() {
		return roomQty;
	}

	public void setRoomQty(Integer roomQty) {
		this.roomQty = roomQty;
	}

	public List<RoomPrice> getRoomPrices() {
		return roomPrices;
	}

	public void setRoomPrices(List<RoomPrice> roomPrices) {
		this.roomPrices = roomPrices;
	}

	public String getRoomSpecial() {
		return roomSpecial;
	}

	public void setRoomSpecial(String roomSpecial) {
		this.roomSpecial = roomSpecial;
	}

	public String getGuestRemark() {
		return guestRemark;
	}

	public void setGuestRemark(String guestRemark) {
		this.guestRemark = guestRemark;
	}
	
	public List<HotelOtherService> getOtherServices() {
		return otherServices;
	}

	public void setOtherServices(List<HotelOtherService> otherServices) {
		this.otherServices = otherServices;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Date getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}

	public Date getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(Date checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public String getPriceName() {
		return priceName;
	}

	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}

	public String getRecommendOpenId() {
		return recommendOpenId;
	}

	public void setRecommendOpenId(String recommendOpenId) {
		this.recommendOpenId = recommendOpenId;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public String getOrderOperate() {
		return orderOperate;
	}

	public void setOrderOperate(String orderOperate) {
		this.orderOperate = orderOperate;
	}
}
