package com.whotel.thirdparty.jxd.mode.vo;

import org.apache.commons.lang3.StringUtils;

/**
 * 酒店
 * @author 冯勇
 */
public class TicketBranchVO {
	
	private String code;
	private String cname;
	private String alias;
	private String detailCName;
	private String ename;
	private String city;
	private String address;
	private String tel;
	private String feature;
	private String photoID;
	private Float minPrice;
	private String hotelImageLoadUrl;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getDetailCName() {
		return detailCName;
	}
	public void setDetailCName(String detailCName) {
		this.detailCName = detailCName;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getPhotoID() {
		return photoID;
	}
	public void setPhotoID(String photoID) {
		this.photoID = photoID;
	}
	
	public Float getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Float minPrice) {
		this.minPrice = minPrice;
	}
	public String getHotelImageLoadUrl() {
		return hotelImageLoadUrl;
	}
	public void setHotelImageLoadUrl(String hotelImageLoadUrl) {
		this.hotelImageLoadUrl = hotelImageLoadUrl;
	}
	
	public String getHotelPic() {
		if(StringUtils.isNotBlank(hotelImageLoadUrl) && StringUtils.isNotBlank(photoID)) {
			return hotelImageLoadUrl.replace("{0}", photoID);
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "HotelInfoVO [code=" + code + ", cname=" + cname + ", alias="
				+ alias + ", detailCName=" + detailCName + ", ename=" + ename
				+ ", city=" + city + ", address=" + address + ", tel=" + tel
				+ ", feature=" + feature + ", photoID=" + photoID
				+ ", minPrice=" + minPrice + ", hotelImageLoadUrl="
				+ hotelImageLoadUrl + "]";
	}
}
