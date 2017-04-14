package com.whotel.company.entity;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.entity.OwnerCheck;

/**
 * 微信菜单
 * @author 冯勇
 */
@Entity(noClassnameStored = true)
public class MenuItem extends BaseEntity implements OwnerCheck {
	
	private static final long serialVersionUID = -5161467986262781730L;

	private String companyId;
	
	private String name;
	
	private Integer order;

	private String parentId;
	
	@Embedded
	private ResponseMsg responseMsg;

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
	
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Transient
	private List<MenuItem> children;

	public List<MenuItem> getChildren() {
		return children;
	}

	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}
	
	public boolean isLeaf() {
		if(children == null || children.size() == 0) {
			return true;
		}
		return false;
	}

	public ResponseMsg getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(ResponseMsg responseMsg) {
		this.responseMsg = responseMsg;
	}
}
