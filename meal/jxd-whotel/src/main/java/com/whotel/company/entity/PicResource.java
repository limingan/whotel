package com.whotel.company.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.OwnerCheck;
import com.whotel.common.entity.UnDeletedEntity;
import com.whotel.common.util.QiniuUtils;

/**
 * 图片资源
 * @author 冯勇
 */
@Entity(noClassnameStored = true)
public class PicResource extends UnDeletedEntity implements OwnerCheck {

	private static final long serialVersionUID = 5648711789530497079L;
	
	private String companyId;
	
	private String name;
	
	private String pic;
	
	private String mediaId;
	
	private Date createTime;

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

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
	public String getPicUrl() {
		return QiniuUtils.getResUrl(pic);
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
