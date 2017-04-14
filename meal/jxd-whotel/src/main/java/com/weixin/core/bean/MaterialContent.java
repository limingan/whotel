package com.weixin.core.bean;

import java.util.List;

public class MaterialContent {
    
	private List<NewsArticle> news_item;

	public List<NewsArticle> getNews_item() {
		return news_item;
	}

	public void setNews_item(List<NewsArticle> news_item) {
		this.news_item = news_item;
	}

	@Override
	public String toString() {
		return "MaterialContent [news_item=" + news_item + "]";
	}
}
