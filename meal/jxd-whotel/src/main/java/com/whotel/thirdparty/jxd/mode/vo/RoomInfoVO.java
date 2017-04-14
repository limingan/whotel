package com.whotel.thirdparty.jxd.mode.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class RoomInfoVO {

	private String orderItemCode;
	private String orderItemCName;
	private String cDescribe;
	private String imageid2;
	private String priceName;
	private String aveprice;
	private String basePrice;
	private String avebreakfast;
	private String paymethod;
	private String mode;
	private String ratecode;
	private String pricesystemid;
	private String salepromotionid;
	private String hotelImageLoadUrl;
	private String bookingNoticeCdesc;
	private String imageNameList;
	private String serviceList;
	private Boolean canBooking;//是否可预订
	private String noBookingReason;//不可预订原因
	
	private Float maxReturnMoneyPay;//返现：小于等于1就是按比例，大于1按金额
	
	private Float maxScorePay;//积分：小于等于1就是按比例，大于1按金额
	
	private Float maxCouponCountPay;//优惠劵
	
	private String orderBy;//排序号
	
	private String IsWqCombine;//是否温泉套票 0：不是，1：是
	
	private String avaQty;//可用配额
	
	private String largessReturnType;//赠送规则
	
	private String largessReturnValue;//赠送规则的值
	
	private boolean sign;//标记
	
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getIsWqCombine() {
		return IsWqCombine;
	}
	public void setIsWqCombine(String isWqCombine) {
		IsWqCombine = isWqCombine;
	}
	public String getAvaQty() {
		return avaQty;
	}
	public void setAvaQty(String avaQty) {
		this.avaQty = avaQty;
	}
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
	public String getRoomPic() {
		if(StringUtils.isNotBlank(hotelImageLoadUrl) && StringUtils.isNotBlank(imageid2)) {
			return hotelImageLoadUrl.replace("{0}", imageid2);
		}else{
			return hotelImageLoadUrl+"?v=1";
		}
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
		if(obj != null && obj instanceof RoomInfoVO) {
			return StringUtils.equals(this.getOrderItemCode(), ((RoomInfoVO)obj).getOrderItemCode());
		}
		return super.equals(obj);
	}
	
	public List<String> getServiceImgUrls(){
		List<String> list = new ArrayList<>();
		if(this.serviceList!=null){
			String[] serviceImgs = this.serviceList.split(",");
			for (int i = 0; i < serviceImgs.length; i++) {
				if(StringUtils.isNotBlank(serviceImgs[i])){
					String serviceImgUrl = hotelImageLoadUrl.replace("{0}", serviceImgs[i]);
					list.add(serviceImgUrl);
				}
			}
		}
		return list;
	}
	public String getServiceList() {
		return serviceList;
	}
	public void setServiceList(String serviceList) {
		this.serviceList = serviceList;
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
	public String getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
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
	public boolean isSign() {
		return sign;
	}
	public void setSign(boolean sign) {
		this.sign = sign;
	}
	
}
