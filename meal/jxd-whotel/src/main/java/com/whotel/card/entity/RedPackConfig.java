package com.whotel.card.entity;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.util.QiniuUtils;

@Entity(noClassnameStored=true)
public class RedPackConfig extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String companyId;
	
	private String name;
	
	private Float amount;
	
	private String wishing;
	
	private String remark;
	
	private String pic;
	
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getPicUrl() {
		return QiniuUtils.getResUrl(pic);
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

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getWishing() {
		return wishing;
	}

	public void setWishing(String wishing) {
		this.wishing = wishing;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
