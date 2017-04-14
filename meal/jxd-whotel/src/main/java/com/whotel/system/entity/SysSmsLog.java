package com.whotel.system.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

/**
 * 短信日志实体
 * @author Administrator
 *
 */
@Entity(noClassnameStored=true)
public class SysSmsLog extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String companyId;

	private String mobile;
	
	private Long result;
	
	private Date createDate;
	
	public SysSmsLog(){
	}
	
	public SysSmsLog(String companyId,String mobile,Long result){
		this.companyId = companyId;
		this.mobile = mobile;
		this.result = result;
	}

	public Long getResult() {
		return result;
	}

	public void setResult(Long result) {
		this.result = result;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
