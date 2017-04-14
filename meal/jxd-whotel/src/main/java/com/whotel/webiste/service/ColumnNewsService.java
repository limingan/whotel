package com.whotel.webiste.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Page;
import com.whotel.webiste.dao.ColumnNewsDao;
import com.whotel.webiste.dao.NewsArticleDao;
import com.whotel.webiste.entity.ColumnNews;
import com.whotel.webiste.entity.NewsArticle;

/**
 * 栏目图文管理服务类
 * @author 冯勇
 *
 */
@Service
public class ColumnNewsService {
	
	@Autowired
	private ColumnNewsDao columnNewsDao;
	
	@Autowired
	private NewsArticleDao newsArticleDao;
	
	/**
	 * 保存栏目图文
	 * @param columnNews
	 */
	public void saveColumnNews(ColumnNews columnNews) {
		if(columnNews != null) {
			columnNews.setCreateTime(new Date());
			columnNewsDao.save(columnNews);
		}
	}
	
	/**
	 * 分页查找栏目图文
	 * @param page 分页工具类
	 */
	public void findColumnNewss(Page<ColumnNews> page) {
		columnNewsDao.find(page);
	}
	
	public List<ColumnNews> findAllColumnNewss() {
		return columnNewsDao.findAll();
	}
	
	public List<ColumnNews> findColumnNewssByCompanyId(String companyId) {
		return columnNewsDao.findByProperty("companyId", companyId);
	}
	
	public ColumnNews getColumnNewsById(String id) {
		return columnNewsDao.get(id);
	}
	
	public void deleteColumnNews(String id) {
		columnNewsDao.delete(id);
	}
	
	public void deleteColumnNews(ColumnNews columnNews) {
		columnNewsDao.delete(columnNews);
	}
	
	/**
	 * 批量删除栏目图文
	 * @param ids
	 */
	public void deleteMoreColumnNews(String ids, String companyId) {
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				ColumnNews columnNews = getColumnNewsById(id);
				
				List<NewsArticle> newsArticles = findNewsArticleByNewsId(columnNews.getId());
				if(newsArticles != null) {
					
					columnNews.setCompanyId(companyId);
					deleteColumnNews(columnNews);
					
					for(NewsArticle article:newsArticles) {
						deleteNewsArticle(article);
					}
				}
			}
		}
	}
	
	/**
	 * 保存图文文章
	 * @param newsArticle
	 */
	public void saveNewsArticle(NewsArticle newsArticle) {
		if(newsArticle != null) {
			newsArticle.setCreateTime(new Date());
			newsArticleDao.save(newsArticle);
		}
	}
	
	public NewsArticle getNewsArticleById(String id) {
		return newsArticleDao.get(id);
	}
	
	/**
	 * 分页查找栏目图文文章
	 * @param page 分页工具类
	 */
	public void findNewsArticle(Page<NewsArticle> page) {
		newsArticleDao.find(page);
	}
	
	public List<NewsArticle> findNewsArticleByNewsId(String newsId) {
		return newsArticleDao.findByProperty("newsId", newsId);
	}
	
	public void deleteNewsArticle(String id) {
		newsArticleDao.delete(id);
	}
	
	public void deleteNewsArticle(NewsArticle article) {
		newsArticleDao.delete(article);
	}
	
	/**
	 * 批量删除栏目图文文章
	 * @param ids
	 */
	public String deleteMoreNewsArticle(String ids, String companyId) {
		String newsId = null;
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				NewsArticle newsArticle = getNewsArticleById(id);
				if(newsArticle != null) {
					newsId = newsArticle.getNewsId();
					if(StringUtils.isNotBlank(newsId)) {
						ColumnNews columnNews = getColumnNewsById(newsId);
						if(columnNews != null && StringUtils.equals(columnNews.getCompanyId(), companyId)) {
							deleteNewsArticle(id);
						}
					}
				}
			}
		}
		return newsId;
	}
}
