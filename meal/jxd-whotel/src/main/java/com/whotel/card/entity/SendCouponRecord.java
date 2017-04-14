package com.whotel.card.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

/**
 * 派劵记录
 * @author kf
 *
 */
@Entity(noClassnameStored=true)
public class SendCouponRecord extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3130799055656241011L;
	
	private String hotelCode;
	
	private String name;//姓名          
	
	private String mobile;//手机号码
	
	private String couponCode;//券编码
	
	private String couponName;//劵类型
	
	private Date createTime;//时间
	
	private String reason;//派劵理由
	
	private String synchronizeState;//同步状态
	
	private String errorMsg;//错误消息
	
	private Integer quantity;//数量
	
	private String html;
	
	private String url;
	
	private String param;
	
	public SendCouponRecord(){}

	public SendCouponRecord(String hotelCode, String name, String mobile, String couponCode,Integer quantity,
			String reason, String synchronizeState, String errorMsg, String html, String url,String param) {
		super();
		this.hotelCode = hotelCode;
		this.name = name;
		this.mobile = mobile;
		this.reason = reason;
		this.synchronizeState = synchronizeState;
		this.errorMsg = errorMsg;
		this.html = html;
		this.couponCode = couponCode;
		this.url = url;
		this.param = param;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSynchronizeState() {
		return synchronizeState;
	}

	public void setSynchronizeState(String synchronizeState) {
		this.synchronizeState = synchronizeState;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
