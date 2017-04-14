package com.whotel.company.entity;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

@Entity(noClassnameStored = true)
public class SysParamConfig extends BaseEntity  {
	
	private static final long serialVersionUID = 667766050764372009L;

	private String companyId;
	
	private Boolean isRecharge;//充值
	
	private Boolean isHotelRegister;//酒店注册会员
	
	private Boolean isIntegralConvert;//积分兑换
	
	private Boolean isRefund;//开启已支付订单取消
	
	private Boolean isChargeConvert;//会员卡充值
	
	private String mbrCardTheme;//会员卡主题
	
	private Integer showMbrQrTime;//显示会员二维码的时间
	
	public Boolean getIsIntegralConvert() {
		return isIntegralConvert;
	}

	public void setIsIntegralConvert(Boolean isIntegralConvert) {
		this.isIntegralConvert = isIntegralConvert;
	}
	
	public Boolean getIsHotelRegister() {
		return isHotelRegister;
	}

	public void setIsHotelRegister(Boolean isHotelRegister) {
		this.isHotelRegister = isHotelRegister;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Boolean getIsRecharge() {
		return isRecharge;
	}

	public void setIsRecharge(Boolean isRecharge) {
		this.isRecharge = isRecharge;
	}

	public String getMbrCardTheme() {
		return mbrCardTheme;
	}

	public void setMbrCardTheme(String mbrCardTheme) {
		this.mbrCardTheme = mbrCardTheme;
	}

	public Integer getShowMbrQrTime() {
		return showMbrQrTime;
	}

	public void setShowMbrQrTime(Integer showMbrQrTime) {
		this.showMbrQrTime = showMbrQrTime;
	}

	public Boolean getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(Boolean isRefund) {
		this.isRefund = isRefund;
	}
	public Boolean getIsChargeConvert() {
		return isChargeConvert;
	}
	public void setIsChargeConvert(Boolean isChargeConvert) {
		this.isChargeConvert = isChargeConvert;
	}
}
