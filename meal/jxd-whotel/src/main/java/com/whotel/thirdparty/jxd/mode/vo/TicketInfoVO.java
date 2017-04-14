package com.whotel.thirdparty.jxd.mode.vo;

import org.apache.commons.lang3.StringUtils;

public class TicketInfoVO {

	private String orderItemCode;
	private String orderItemCName;
	private String cDescribe;
	private String imageid2;
	private String priceName;
	private String aveprice;
	private String avebreakfast;
	private String paymethod;
	private String mode;
	private String ratecode;
	private String pricesystemid;
	private String salepromotionid;
	private String hotelImageLoadUrl;
	private String bookingNoticeCdesc;
	private String imageNameList;
	private Boolean canBooking;//是否可预订
	private String noBookingReason;//不可预订原因
	private Boolean isWqCombine;//是否是温泉套票（温泉门票+儿童票/西餐）0否1是
	private String avaQty;
	private String isScenic;//景区1，门票0
	private Float maxReturnMoneyPay;//返现：小于等于1就是按比例，大于1按金额
	private Float maxScorePay;//积分：小于等于1就是按比例，大于1按金额
	private Float maxCouponCountPay;//优惠劵
	private String largessReturnType; //赠送规则
	private String largessReturnValue;//赠送规则的值
	
	public Float getMaxReturnMoneyPay() {
		return maxReturnMoneyPay;
	}
	public void setMaxReturnMoneyPay(Float maxReturnMoneyPay) {
		this.maxReturnMoneyPay = maxReturnMoneyPay;
	}
	public Float getMaxScorePay() {
		return maxScorePay;
	}
	public void setMaxScorePay(Float maxScorePay) {
		this.maxScorePay = maxScorePay;
	}
	public Float getMaxCouponCountPay() {
		return maxCouponCountPay;
	}
	public void setMaxCouponCountPay(Float maxCouponCountPay) {
		this.maxCouponCountPay = maxCouponCountPay;
	}
	public String getOrderItemCode() {
		return orderItemCode;
	}
	public void setOrderItemCode(String orderItemCode) {
		this.orderItemCode = orderItemCode;
	}
	public String getOrderItemCName() {
		return orderItemCName;
	}
	public void setOrderItemCName(String orderItemCName) {
		this.orderItemCName = orderItemCName;
	}
	public String getcDescribe() {
		return cDescribe;
	}
	public void setcDescribe(String cDescribe) {
		this.cDescribe = cDescribe;
	}
	public String getImageid2() {
		return imageid2;
	}
	public void setImageid2(String imageid2) {
		this.imageid2 = imageid2;
	}
	public String getPriceName() {
		return priceName;
	}
	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}
	public String getAveprice() {
		return aveprice;
	}
	public void setAveprice(String aveprice) {
		this.aveprice = aveprice;
	}
	public String getAvebreakfast() {
		return avebreakfast;
	}
	public void setAvebreakfast(String avebreakfast) {
		this.avebreakfast = avebreakfast;
	}
	public String getPaymethod() {
		return paymethod;
	}
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getRatecode() {
		return ratecode;
	}
	public void setRatecode(String ratecode) {
		this.ratecode = ratecode;
	}
	public String getPricesystemid() {
		return pricesystemid;
	}
	public void setPricesystemid(String pricesystemid) {
		this.pricesystemid = pricesystemid;
	}
	
	public String getSalepromotionid() {
		return salepromotionid;
	}
	public void setSalepromotionid(String salepromotionid) {
		this.salepromotionid = salepromotionid;
	}
	public String getHotelImageLoadUrl() {
		return hotelImageLoadUrl;
	}
	public void setHotelImageLoadUrl(String hotelImageLoadUrl) {
		this.hotelImageLoadUrl = hotelImageLoadUrl;
	}
	
	public String getBookingNoticeCdesc() {
		return bookingNoticeCdesc;
	}
	public void setBookingNoticeCdesc(String bookingNoticeCdesc) {
		this.bookingNoticeCdesc = bookingNoticeCdesc;
	}

	public String getImageNameList() {
		return imageNameList;
	}
	public void setImageNameList(String imageNameList) {
		this.imageNameList = imageNameList;
	}
	public Boolean getCanBooking() {
		return canBooking;
	}
	public void setCanBooking(Boolean canBooking) {
		this.canBooking = canBooking;
	}
	public String getNoBookingReason() {
		return noBookingReason;
	}
	public void setNoBookingReason(String noBookingReason) {
		this.noBookingReason = noBookingReason;
	}
	public String getRoomPic() {
		if(StringUtils.isNotBlank(hotelImageLoadUrl) && StringUtils.isNotBlank(imageid2)) {
			return hotelImageLoadUrl.replace("{0}", imageid2);
		}
		return null;
	}
	
	public String[] getRoomPics() {
		if(StringUtils.isNotBlank(hotelImageLoadUrl) && StringUtils.isNotBlank(imageNameList)) {
			String[] imageids = imageNameList.split(",");
			if(imageids != null && imageids.length > 0) {
				String[] imageUrls = new String[imageids.length];
				
				for(int i=0; i<imageids.length; i++) {
					imageUrls[i] = hotelImageLoadUrl.replace("{0}", imageids[i]);
				}
				return imageUrls;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "RoomInfoVO [orderItemCode=" + orderItemCode
				+ ", orderItemCName=" + orderItemCName + ", cDescribe="
				+ cDescribe + ", imageid2=" + imageid2 + ", priceName="
				+ priceName + ", aveprice=" + aveprice + ", avebreakfast="
				+ avebreakfast + ", paymethod=" + paymethod + ", mode=" + mode
				+ ", ratecode=" + ratecode + ", pricesystemid=" + pricesystemid
				+ ", salepromotionid=" + salepromotionid
				+ ", hotelImageLoadUrl=" + hotelImageLoadUrl
				+ ", bookingNoticeCdesc=" + bookingNoticeCdesc
				+ ", imageNameList=" + imageNameList + "]";
	}
	
	@Override
	public int hashCode() {
		if(StringUtils.isNotBlank(orderItemCode)) {
			return orderItemCode.hashCode();
		}
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof TicketInfoVO) {
			return StringUtils.equals(this.getOrderItemCode(), ((TicketInfoVO)obj).getOrderItemCode());
		}
		return super.equals(obj);
	}
	public Boolean getIsWqCombine() {
		return isWqCombine;
	}
	public void setIsWqCombine(Boolean isWqCombine) {
		this.isWqCombine = isWqCombine;
	}
	public String getAvaQty() {
		return avaQty;
	}
	public void setAvaQty(String avaQty) {
		this.avaQty = avaQty;
	}
	public String getIsScenic() {
		return isScenic;
	}
	public void setIsScenic(String isScenic) {
		this.isScenic = isScenic;
	}
	public String getLargessReturnType() {
		return largessReturnType;
	}
	public void setLargessReturnType(String largessReturnType) {
		this.largessReturnType = largessReturnType;
	}
	public String getLargessReturnValue() {
		return largessReturnValue;
	}
	public void setLargessReturnValue(String largessReturnValue) {
		this.largessReturnValue = largessReturnValue;
	}
}
