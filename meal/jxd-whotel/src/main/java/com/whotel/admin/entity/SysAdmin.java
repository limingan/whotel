package com.whotel.admin.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Transient;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.util.RepoUtil;

@Entity(noClassnameStored=true)
public class SysAdmin extends BaseEntity {

	private static final long serialVersionUID = 667766050764372009L;

	@Indexed(unique=true)
	private String userName;
	private String password;
	private String roleId;
	private Boolean enable;
	private Integer errorCount;
	
	private Date createTime;
	private Date updateTime;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
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

	@Transient
	private String oldPassword;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public SysRole getRole() {
		return RepoUtil.getSysRole(roleId);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj != null && (obj instanceof SysAdmin)) {
			return ((SysAdmin)obj).getId().equals(getId());
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		if (getId() == null) {
			return super.hashCode();
		} else {
			return this.getId().hashCode();
		}
	}
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
}
