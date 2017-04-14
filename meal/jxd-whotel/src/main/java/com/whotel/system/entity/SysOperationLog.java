package com.whotel.system.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.util.RepoUtil;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.CompanyAdmin;

@Entity(noClassnameStored=true)
public class SysOperationLog extends BaseEntity{
	
	private static final long serialVersionUID = 2183539253140955699L;
	
	private String companyId;
	
	private String adminId;//操作人员
	
	private String moduleTypes;//操作对象
	
	private String type;//delete:删除   add：新增   update：修改
	
	private String paramXml;
	
	private String remark;
	
	private Date createTime;
	
	public SysOperationLog(){}

	public SysOperationLog(String companyId, String adminId, String moduleTypes, String type, String paramXml,String remark) {
		this.companyId = companyId;
		this.setAdminId(adminId);
		this.moduleTypes = moduleTypes;
		this.type = type;
		this.paramXml = paramXml;
		this.remark = remark;
	}

	public Company getCompany() {
		return RepoUtil.getCompany(companyId);
	}
	
	public CompanyAdmin getCompanyAdmin() {
		return RepoUtil.getCompanyAdminById(adminId);
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getModuleTypes() {
		return moduleTypes;
	}

	public void setModuleTypes(String moduleTypes) {
		this.moduleTypes = moduleTypes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParamXml() {
		return paramXml;
	}

	public void setParamXml(String paramXml) {
		this.paramXml = paramXml;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
}
