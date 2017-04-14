package com.whotel.thirdparty.jxd.mode.vo;


/**
 * 房型
 * 
 * @author 冯勇
 * 
 */
public class RoomTypeVO {

	private String hotelCode;
	private String code;
	private String cname; 				//中文名称
	private String ename; 				//英文名
	private String alias; 				//别名
	private String rateCode;            //房价代码
	private String rackRate;            //牌价
	private String stdPAX; 				//标准人数
	private String maxPAX; 				//最大人数
	private String feature; 			//特色
	private String area; 				//面积
	private String remark; 				//备注
	
	public String getHotelCode() {
		return hotelCode;
	}
	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}
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
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getRateCode() {
		return rateCode;
	}
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}
	public String getRackRate() {
		return rackRate;
	}
	public void setRackRate(String rackRate) {
		this.rackRate = rackRate;
	}
	public String getStdPAX() {
		return stdPAX;
	}
	public void setStdPAX(String stdPAX) {
		this.stdPAX = stdPAX;
	}
	public String getMaxPAX() {
		return maxPAX;
	}
	public void setMaxPAX(String maxPAX) {
		this.maxPAX = maxPAX;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "RoomTypeVO [hotelCode=" + hotelCode + ", code=" + code
				+ ", cname=" + cname + ", ename=" + ename + ", alias=" + alias
				+ ", rateCode=" + rateCode + ", rackRate=" + rackRate
				+ ", stdPAX=" + stdPAX + ", maxPAX=" + maxPAX + ", feature="
				+ feature + ", area=" + area + ", remark=" + remark + "]";
	}
}
