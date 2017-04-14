package com.whotel.webiste.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.entity.OwnerCheck;
import com.whotel.common.util.QiniuUtils;

/**
 * 微网站
 * @author 冯勇
 */
@Entity(noClassnameStored=true)
public class Website extends BaseEntity implements OwnerCheck {

	private static final long serialVersionUID = -3719069924184286501L;
	
	private String name;
	
	private String templateId;
	
	private String companyId;
	
	private String banner;
	
	private String bannerLink;
	
	@Embedded
	private List<WebsiteColumn> columns;
	
	private Boolean enable;
	
	private Date createTime;
	
	private Date updateTime;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	public String[] getBanners() {
		return StringUtils.split(banner, ",");
	}
	
	public String[] getBannerUrls() {
		String[] attachs = getBanners();
		if(attachs != null) {
			for(int i=0; i<attachs.length; i++) {
				attachs[i] = QiniuUtils.getResUrl(attachs[i]);
			}
		}
		return attachs;
	}

	public List<WebsiteColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<WebsiteColumn> columns) {
		this.columns = columns;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getBannerLink() {
		return bannerLink;
	}

	public void setBannerLink(String bannerLink) {
		this.bannerLink = bannerLink;
	}
	
	public String[] getBannerLinks() {
		return StringUtils.split(bannerLink, ",");
	}
}
