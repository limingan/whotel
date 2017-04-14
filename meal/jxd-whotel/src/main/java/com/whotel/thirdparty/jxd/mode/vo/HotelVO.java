package com.whotel.thirdparty.jxd.mode.vo;

/**
 * 酒店
 * @author 冯勇
 * 
 */
public class HotelVO {
	
	private String hotelCode;
	
	private String cName;
	
	private String address;
	
	private String tel;
	
	private String email;
	
	private String area;
	
	private String province;
	
	private String feature;      //特色
	
	private String zipCode;
	
	private String certificate;   //星级
	
	private String priceDesc;     //价位描述
	
	private String rooms;         //客房数
	
	private String announcement;  //重要提示
	
	private String facility;      //房间设施
	
	private String availbleService; //酒店服务项目
	
	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}
	
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getPriceDesc() {
		return priceDesc;
	}

	public void setPriceDesc(String priceDesc) {
		this.priceDesc = priceDesc;
	}

	public String getRooms() {
		return rooms;
	}

	public void setRooms(String rooms) {
		this.rooms = rooms;
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public String getAvailbleService() {
		return availbleService;
	}

	public void setAvailbleService(String availbleService) {
		this.availbleService = availbleService;
	}

	@Override
	public String toString() {
		return "HotelVO [hotelCode=" + hotelCode + ", cName=" + cName
				+ ", address=" + address + ", tel=" + tel + ", email=" + email
				+ ", area=" + area + ", province=" + province + ", feature="
				+ feature + ", zipCode=" + zipCode + ", certificate="
				+ certificate + ", priceDesc=" + priceDesc + ", rooms=" + rooms
				+ ", announcement=" + announcement + ", facility=" + facility
				+ ", availbleService=" + availbleService + "]";
	}
}
