package com.weixin.core.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.weixin.core.common.WeixinConstant;

/**
 * 图文信息（回复）
 * 
 */
@SuppressWarnings("serial")
public class NewsMsg extends WeixinMsg {
	private int articleCount;
	private List<Item> articles;

	public NewsMsg() {
		super();
		this.msgType = WeixinConstant.MsgType.NewsMsg;
		articles = new ArrayList<Item>();
	}

	// 加一个OPENID在URL后面，这样才知道是谁访问的
	public static NewsMsg createNewMsg(String title, String url, String desc,
			String picUrl) {
		Date d = new Date();
		NewsMsg news = new NewsMsg();
		Item item1 = new Item();
		item1.setTitle(title);
		item1.setUrl(url + "?t=" + d.getTime());
		item1.setDescription(desc);
		item1.setPicUrl(picUrl + "?t=" + d.getTime());
		news.getArticles().add(item1);
		news.setArticleCount(news.getArticles().size());
		return news;
	}

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	/**
	 * 刷新文章数量
	 */
	public NewsMsg refreshArticleCount() {
		this.articleCount = articles.size();
		return this;
	}

	public List<Item> getArticles() {
		return articles;
	}

	public void setArticles(List<Item> articles) {
		this.articles = articles;
		this.articleCount = articles.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + articleCount;
		result = prime * result
				+ ((articles == null) ? 0 : articles.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsMsg other = (NewsMsg) obj;
		if (articleCount != other.articleCount)
			return false;
		if (articles == null) {
			if (other.articles != null)
				return false;
		} else if (!articles.equals(other.articles))
			return false;
		return true;
	}

}
