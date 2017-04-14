package com.whotel.hotel.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.weixin.core.enums.QrActionType;
import com.whotel.common.entity.BaseEntity;

/**
 * 关注二维码
 */
@Entity(noClassnameStored=true)
public class SceneQrcode extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String companyId;

	private QrActionType type;
	
	private Integer sceneId;

	private Integer time;
	
	private String ticket;
	
	private String qrUrl;
	
	private Date createDate;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public QrActionType getType() {
		return type;
	}

	public void setType(QrActionType type) {
		this.type = type;
	}

	public Integer getSceneId() {
		return sceneId;
	}

	public void setSceneId(Integer sceneId) {
		this.sceneId = sceneId;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getQrUrl() {
		return qrUrl;
	}

	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}

	@Override
	public String toString() {
		return "SceneQrcode [companyId=" + companyId + ", type=" + type + ", sceneId=" + sceneId + ", time=" + time
				+ ", ticket=" + ticket + ", qrUrl=" + qrUrl + ", createDate=" + createDate + "]";
	}
}
