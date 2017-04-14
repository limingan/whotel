package com.whotel.webiste.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.util.QiniuUtils;
import com.whotel.common.util.RepoUtil;

/**
 * 栏目图文文章信息实体
 * @author 冯勇
 *
 */
@Entity(noClassnameStored=true)
public class NewsArticle extends BaseEntity {

	private static final long serialVersionUID = -2652957189491841811L;
	
	private String newsId;
	
	private String title;
	
	private String thumbnail;
	
	private String url;
	
	private String brief;
	
	private String content;
	
	private Date createTime;
	
	private Integer orderIndex;//显示顺序

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	
	public ColumnNews getColumnNews() {
		return RepoUtil.getColumnNews(newsId);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getThumbnailUrl() {
		return QiniuUtils.getResUrl(thumbnail);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
}
