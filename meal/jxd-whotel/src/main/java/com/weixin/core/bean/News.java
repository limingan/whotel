package com.weixin.core.bean;

import java.util.List;

import com.weixin.core.common.WeixinBean;

public class News implements WeixinBean {

	private static final long serialVersionUID = 2449090970132190651L;
	
	private List<NewsArticle> articles;

	public List<NewsArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<NewsArticle> articles) {
		this.articles = articles;
	}
}
