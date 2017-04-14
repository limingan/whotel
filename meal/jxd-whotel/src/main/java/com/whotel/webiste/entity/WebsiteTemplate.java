package com.whotel.webiste.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.UnDeletedEntity;
import com.whotel.common.util.QiniuUtils;

@Entity(noClassnameStored=true)
public class WebsiteTemplate extends UnDeletedEntity {

	private static final long serialVersionUID = -4008869254904908670L;

	private String name;
	
	private String template;

	private String banner;
	
	private String bannerRemark;

	private String cssPath;

	private String jsPath;

	private String thumbnail;
	
	@Embedded
	private List<WebsiteColumn> columns;
	
	private Date createTime;
	
	private Date updateTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
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

	public String getCssPath() {
		return cssPath;
	}
	
	public String getCssPathUrl() {
		return QiniuUtils.getResUrl(cssPath);
	}

	public void setCssPath(String cssPath) {
		this.cssPath = cssPath;
	}

	public String getJsPath() {
		return jsPath;
	}
	
	public String getJsPathUrl() {
		return QiniuUtils.getResUrl(jsPath);
	}

	public void setJsPath(String jsPath) {
		this.jsPath = jsPath;
	}

	public String getThumbnail() {
		return thumbnail;
	}
	
	public String getThumbnailUrl() {
		return QiniuUtils.getResUrl(thumbnail);
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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

	public String getBannerRemark() {
		return bannerRemark;
	}

	public void setBannerRemark(String bannerRemark) {
		this.bannerRemark = bannerRemark;
	}
}
