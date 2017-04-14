package com.whotel.admin.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.admin.enums.AdminActionType;
import com.whotel.common.entity.BaseEntity;

@Entity(noClassnameStored=true)
public class AdminActionLog extends BaseEntity {

	private static final long serialVersionUID = 3662224485487845894L;

	private String adminId;
	
	private AdminActionType action;
	
	private String remark;
	
	private Date createTime;

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public AdminActionType getAction() {
		return action;
	}

	public void setAction(AdminActionType action) {
		this.action = action;
	}
	
	public String getActionLabel() {
		if(action != null) {
			return action.getLabel();
		}
		return null;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
