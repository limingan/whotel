package com.whotel.company.entity;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.OwnerCheck;
import com.whotel.common.entity.UnDeletedEntity;

/**
 * 图文资源
 * @author 冯勇
 */
@Entity(noClassnameStored = true)
public class NewsResource extends UnDeletedEntity implements OwnerCheck {

	private static final long serialVersionUID = 5648711789530497079L;
	
	private String companyId;
	
	private String name;
	
	private int count;
	
	private String mediaId;                 //微信公众平台素材ID

	@Embedded
	private List<NewsItem> news;
	
	private Date createTime;
	
	private Date updateTime;
	
	/**
	 * 是否是多图文
	 * @return
	 */
	public boolean getMultiple() {
		return news!=null?(news.size() > 1):false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<NewsItem> getNews() {
		return news;
	}

	public void setNews(List<NewsItem> news) {
		this.news = news;
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
}
