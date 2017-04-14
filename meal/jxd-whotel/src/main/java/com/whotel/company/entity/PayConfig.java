package com.whotel.company.entity;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.entity.OwnerCheck;
import com.whotel.common.util.QiniuUtils;
import com.whotel.common.util.RepoUtil;
import com.whotel.company.enums.PayType;

@Entity(noClassnameStored = true)
public class PayConfig extends BaseEntity implements OwnerCheck {

	private static final long serialVersionUID = -1417684528745831840L;
	
	private String companyId;
	
	private String appId;
	
	private String mchId;
	
	private String apiKey;
	
	private String cert;
	
	private PayType payType;//支付类型
	
	private Boolean valid;
	
	private String hotelCode;

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}
	
	public String getCertUrl() {
		return QiniuUtils.getResUrl(cert);
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	
	public Company getCompany() {
		return RepoUtil.getCompany(companyId);
	}
}
