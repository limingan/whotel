package com.whotel.card.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

@Entity(noClassnameStored=true)
public class InvoiceInfo extends BaseEntity {

	private static final long serialVersionUID = 6258887558915668430L;
	
	private String companyId;
	
	private Date createDate;
	
	private String openId;
	
	private String name;//名称
	
	private String identifyNo;//纳税人识别号
	
	private String address;//地址
	
	private String phone;//电话
	
	private String bankName;//开户银行
	
	private String account;//账号
	
	private String mobile;//手机
	
	private String email;//邮箱
	
	private Boolean def;//是否默认
	
	private Boolean type;//true是单位，false是个人
	
	private Boolean ticketType;//true是专票，false是普票

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifyNo() {
		return identifyNo;
	}

	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getDef() {
		return def;
	}

	public void setDef(Boolean def) {
		this.def = def;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Boolean getType() {
		return type;
	}

	public void setType(Boolean type) {
		this.type = type;
	}

	public Boolean getTicketType() {
		return ticketType;
	}

	public void setTicketType(Boolean ticketType) {
		this.ticketType = ticketType;
	}
}
