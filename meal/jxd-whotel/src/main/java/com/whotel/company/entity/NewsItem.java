package com.whotel.company.entity;

import org.mongodb.morphia.annotations.Embedded;

import com.whotel.common.util.QiniuUtils;

/**
 * 图文项
 * @author 冯勇
 */
@Embedded
public class NewsItem {

	private int key;
	private String title; // 标题
	private String author; // 描述（作者）
	private String brief;// 摘要
	private String cover; // 封面
	private Boolean showCover; // 封面图片是否显示在正文中
	private String content; // 正文
	private String url; // 链接
	
	private String thumbMediaId;
	
	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getCover() {
		return cover;
	}
	
	public String getCoverUrl() {
		return QiniuUtils.getResUrl(cover);
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Boolean getShowCover() {
		return showCover;
	}

	public void setShowCover(Boolean showCover) {
		this.showCover = showCover;
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

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
}
