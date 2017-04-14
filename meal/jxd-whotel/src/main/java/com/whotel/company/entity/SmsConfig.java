package com.whotel.company.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import com.whotel.common.entity.BaseEntity;

/**
 * 商户第三方短信接口配置信息
 * @author 冯勇
 */
@Entity(noClassnameStored = true)
public class SmsConfig extends BaseEntity {

	private static final long serialVersionUID = -6044346253794339277L;
	
	@Indexed(unique=true)
	private String companyId;
	
	private String account;
	
	private String pwd;
	
	private String mobiles;
	
	private Integer alarmNum;
	
	private Integer balance;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public Integer getAlarmNum() {
		return alarmNum;
	}

	public void setAlarmNum(Integer alarmNum) {
		this.alarmNum = alarmNum;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
}
