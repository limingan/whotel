package com.whotel.hotel.entity;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.util.QiniuUtils;

@Entity(noClassnameStored=true)
public class CommentConfig extends BaseEntity  {

	private static final long serialVersionUID = -9055869835168898960L;

	private String backgroundImg;//背景图片
	
	private String companyId;
	
	private Boolean isHotelComment;//是否允许点评
	
	public String getBackgroundImgUrl() {
		return QiniuUtils.getResUrl(backgroundImg);
	}

	public String getBackgroundImg() {
		return backgroundImg;
	}

	public void setBackgroundImg(String backgroundImg) {
		this.backgroundImg = backgroundImg;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Boolean getIsHotelComment() {
		return isHotelComment;
	}

	public void setIsHotelComment(Boolean isHotelComment) {
		this.isHotelComment = isHotelComment;
	}
}
