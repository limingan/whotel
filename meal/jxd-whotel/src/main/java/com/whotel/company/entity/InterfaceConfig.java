package com.whotel.company.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.util.MD5Util;
import com.whotel.common.util.RepoUtil;

/**
 * 商户第三方数据接口配置信息
 * @author 冯勇
 */
@Entity(noClassnameStored = true)
public class InterfaceConfig extends BaseEntity {

	private static final long serialVersionUID = -6044346253794339277L;
	
	@Indexed(unique=true)
	private String companyId;
	
	private boolean enable;
	
	private String host;//服务地址
	
	private String account;
	
	private String pwd;
	
	private String url;//接口列表地址
	
	private String channel;//e:e云通(默认),pms
	
	private String sign;//秘钥
	
	private String unlock;//门锁地址
	
	public Company getCompany() {
		return RepoUtil.getCompany(companyId);
	}

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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getUnlock() {
		return unlock;
	}

	public void setUnlock(String unlock) {
		this.unlock = unlock;
	}
}
