package com.whotel.company.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import com.whotel.common.entity.UnDeletedEntity;
import com.whotel.company.enums.PublicType;

/**
 * 商户公众号信息
 * @author 冯勇
 *
 */
@Entity(noClassnameStored=true)
public class PublicNo extends UnDeletedEntity {

	private static final long serialVersionUID = -7361883998833874510L;
	
	@Indexed(unique = true)
	private String account;
	
	private String pwd;
	
	@Indexed
	private String companyId;

	private String name;
	
	private String alias; 
	
	private String avatar;
	
	private String qrcode;
	
	private PublicType type;
	
	private boolean auth;              //是否认证
	
	@Indexed(unique = true)
	private String developerCode;
	
	private String appId;
	private String appSecret;
	
	private String authToken;
	
	private String attentionUrl;
	
	private String weixinId;//微信号
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getDeveloperCode() {
		return developerCode;
	}
	public void setDeveloperCode(String developerCode) {
		this.developerCode = developerCode;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	public PublicType getType() {
		return type;
	}
	public void setType(PublicType type) {
		this.type = type;
	}
	public boolean isAuth() {
		return auth;
	}
	public void setAuth(boolean auth) {
		this.auth = auth;
	}
	public String getAttentionUrl() {
		return attentionUrl;
	}
	public void setAttentionUrl(String attentionUrl) {
		this.attentionUrl = attentionUrl;
	}
	public String getWeixinId() {
		return weixinId;
	}
	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}
}
