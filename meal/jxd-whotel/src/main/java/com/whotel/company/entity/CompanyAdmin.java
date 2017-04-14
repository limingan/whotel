package com.whotel.company.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Transient;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.entity.OwnerCheck;
import com.whotel.common.util.RepoUtil;
import com.whotel.company.enums.MessageType;
import com.whotel.thirdparty.jxd.mode.vo.HotelBranchVO;

/**
 * 商户管理员信息实体
 * @author 冯勇
 *
 */
@Entity(noClassnameStored=true)
@Indexes(@Index(fields = {@Field("companyId"), @Field("account")}, options=@IndexOptions(unique=true)))
public class CompanyAdmin extends BaseEntity implements OwnerCheck {

	private static final long serialVersionUID = -6985893860457141496L;
	
	private String companyId;
	
	private String account;
	
	private String password;
	
	private String name;
	
	private String mobile;
	
	private String email;
	
	private boolean admin;
	
	private Date createTime;
	
	private Date updateTime;
	
	@Transient
	private String oldPassword;
	
	private String roleId;
	
	private String openId;
	
	private String hotelCode;
	
	private Integer errorCount;

	@Embedded
	private List<MessageType> messageTypes;
	
	private String startTime;//客服开始时间
	
	private String endTime;//客服结束时间
	
	private String kfAccount;//客服账号
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<MessageType> getMessageTypes() {
		return messageTypes;
	}

	public void setMessageTypes(List<MessageType> messageTypes) {
		this.messageTypes = messageTypes;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Company getCompany() {
		return RepoUtil.getCompany(companyId);
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public CompanyRole getRole() {
		return RepoUtil.getCompanyRole(roleId);
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}
	
	public String[] getHotelCodes(){
		if(hotelCode!=null){
			String[] hotelCodes = this.hotelCode.split(",");
			return hotelCodes;
		}
		return null;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	
	public Boolean getServiceTime(Integer now){
		if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
			Integer start = Integer.valueOf(startTime.replace(":", ""));
			Integer end = Integer.valueOf(endTime.replace(":", ""));
			if((start<=now && end>=now) || (start==0 && end==0)){
				return true;
			}
			return false;
		}
		return true;
	}

	public String getKfAccount() {
		return kfAccount;
	}

	public void setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
	}
}
