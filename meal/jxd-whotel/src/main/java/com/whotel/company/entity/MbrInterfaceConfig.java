package com.whotel.company.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import com.whotel.common.entity.BaseEntity;

/**
 * 会员数据接口配置信息
 * @author 柯鹏程
 */
@Entity(noClassnameStored = true)
public class MbrInterfaceConfig extends BaseEntity {

	private static final long serialVersionUID = -6044346253794339277L;
	
	private String opType;
	
	private String url;
	
	private String hotelCode;
	
	private Date createDate;

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
