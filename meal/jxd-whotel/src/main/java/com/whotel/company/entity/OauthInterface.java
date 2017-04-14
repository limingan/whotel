package com.whotel.company.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import com.whotel.common.entity.BaseEntity;

/**
 * 商户第三方授权接口配置信息
 * @author 柯鹏程
 */
@Entity(noClassnameStored = true)
public class OauthInterface extends BaseEntity {

	private static final long serialVersionUID = -6044346253794339277L;
	
	private String companyId;
	
	private boolean enable;
	
	private String code;
	
	private String url;
	
	private String secretKey;
	
	private Date createTime;
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
