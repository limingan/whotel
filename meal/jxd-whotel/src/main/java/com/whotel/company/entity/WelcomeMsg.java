package com.whotel.company.entity;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.entity.OwnerCheck;

/**
 * 欢迎消息
 * @author 冯勇
 */
@Entity(noClassnameStored=true)
public class WelcomeMsg extends BaseEntity implements OwnerCheck {
	
	private static final long serialVersionUID = 5848651471512507183L;

	private String companyId;
	
	private ResponseMsg responseMsg;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public ResponseMsg getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(ResponseMsg responseMsg) {
		this.responseMsg = responseMsg;
	}
}
