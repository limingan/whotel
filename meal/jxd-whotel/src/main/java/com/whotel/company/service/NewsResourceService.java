package com.whotel.company.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.weixin.core.api.MediaUploador;
import com.weixin.core.api.NewsResourceApi;
import com.weixin.core.bean.MaterialBatch;
import com.weixin.core.bean.MaterialContent;
import com.weixin.core.bean.MaterialItem;
import com.weixin.core.bean.News;
import com.weixin.core.bean.NewsArticle;
import com.weixin.core.common.BaseToken;
import com.weixin.core.common.WeixinConstant;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.util.QiniuUtils;
import com.whotel.company.dao.NewsResourceDao;
import com.whotel.company.entity.NewsItem;
import com.whotel.company.entity.NewsResource;
import com.whotel.company.entity.PublicNo;
import com.whotel.weixin.service.TokenService;

/**
 * 图文素材信息管理服务类
 * @author 冯勇
 *
 */
@Service
public class NewsResourceService {
	
	private static final Logger logger = Logger.getLogger(NewsResourceService.class);
	
	@Autowired
	private NewsResourceDao newsResourceDao;
	
	@Autowired
	private PublicNoService publicNoService;
	
	@Autowired
	private PicResourceService picResourceService;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	/**
	 * 保存图文
	 * @param newsResource
	 */
	public void saveNewsResource(NewsResource newsResource) {
		if(newsResource != null) {
			String name = null;
			
			Date createTime = newsResource.getCreateTime();
			if(createTime == null) {
				newsResource.setCreateTime(new Date());
			}
			newsResource.setUpdateTime(new Date());
			List<NewsItem> news = newsResource.getNews();
			List<NewsItem> items = new ArrayList<NewsItem>();
			if(news != null) {
				int index = 0;
				for(NewsItem item:news) {
					if(item != null && StringUtils.isNotBlank(item.getTitle())) {
						//防止删除引起的空数组
						item.setKey(index);
						index ++;
						items.add(item);
						
						if(StringUtils.isBlank(name)) {
							name = item.getTitle();
						}
					}
				}
			}
			newsResource.setName(name);
			newsResource.setNews(items);
			newsResourceDao.save(newsResource);
		}
	}

	/**
	 * 保存图文并同步更新到微信公众平台
	 * @param newsResource
	 */
	public void saveAndSynchronizeNewsToMP(final NewsResource newsResource) {
		saveNewsResource(newsResource);
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				synchronizeNewsToMP(newsResource);
			}
		});
	}
	
	/**
	 * 同步素材到微信公众后台
	 * @param newsResource
	 * @return
	 */
	public String synchronizeNewsToMP(NewsResource newsResource) {
		
		String mediaId = newsResource.getMediaId();
		String companyId = newsResource.getCompanyId();
		PublicNo publicNo = publicNoService.getPublicNoByCompanyId(companyId);
		if(publicNo == null) {
			logger.warn("未绑定公众号！");
			return null;
		}
		
		List<NewsItem> newsItems = newsResource.getNews();
		
		if(newsItems != null && newsItems.size() > 0) {
			try {
				BaseToken accessToken = TokenService.getTokenService().getAccessToken(publicNo.getAppId(), publicNo.getAppSecret());
				News news = new News();
				List<NewsArticle> newsArticles = new ArrayList<NewsArticle>();
				news.setArticles(newsArticles);
				List<String> thumbMediaIds = new ArrayList<String>();
				for(NewsItem item:newsItems) {
					
					thumbMediaIds.add(item.getThumbMediaId());
					NewsArticle article = new NewsArticle();
					
					article.setTitle(item.getTitle());
					article.setAuthor(item.getAuthor());
					article.setDigest(item.getBrief());
					article.setShow_cover_pic((item.getShowCover() != null && item.getShowCover()) ? "1":"0");
					if(StringUtils.isBlank(item.getContent())) {
						article.setContent("");
					} else {
						article.setContent(item.getContent());
					}
					article.setContent_source_url(item.getUrl());
					
					if(StringUtils.isNotBlank(item.getCoverUrl())) {
						String[] thumb_media = MediaUploador.uploadPersistentWebPic(accessToken, WeixinConstant.MsgType.ImageMsg, item.getCoverUrl());
						if(thumb_media != null) {
							article.setThumb_media_id(thumb_media[0]);
							item.setThumbMediaId(thumb_media[0]);
						}
					}
					newsArticles.add(article);
				}
			
				if(StringUtils.isNotBlank(mediaId)) {
					if(thumbMediaIds != null && thumbMediaIds.size() > 0) {
						for(String thumbMediaId:thumbMediaIds) {
							NewsResourceApi.deleteMaterial(thumbMediaId, accessToken);  //如果已经存在先删除
						}
					}
					NewsResourceApi.deleteMaterial(mediaId, accessToken);    //如果已经存在先删除
				}
				mediaId = NewsResourceApi.createNews(news, accessToken);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		newsResource.setMediaId(mediaId);
		saveNewsResource(newsResource);
		return mediaId;
	}
	
	/**
	 * 同步加载微信公众平台素材数据，并保存到本地
	 * @param companyId
	 * @param type
	 * @return
	 */
	public Boolean synchronizeMPResource(String companyId, String type) {
		PublicNo publicNo = publicNoService.getPublicNoByCompanyId(companyId);
		if(publicNo == null) {
			logger.warn("未绑定公众号！");
			return false;
		}
		
		try {
			BaseToken accessToken = TokenService.getTokenService().getAccessToken(publicNo.getAppId(), publicNo.getAppSecret());
			int offset = 0;
			int count = 20;
			int total_count = 0;
			do {
				MaterialBatch batchNews = NewsResourceApi.batchGetNews(offset, count, type, accessToken);
				if(batchNews != null) {
					total_count = batchNews.getTotal_count();
					
					List<MaterialItem> items = batchNews.getItem();
					
					if(items != null) {
						for(MaterialItem item:items) {
							if(WeixinConstant.MsgType.NewsMsg.equals(type)) {
								transferToResource(accessToken, item, companyId);
							} else if(WeixinConstant.MsgType.ImageMsg.equals(type)) {
								picResourceService.transferToResource(accessToken, item, companyId);
							}
						}
					}
					
					offset = offset + count;
				}
			} while(offset - count < total_count);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
    
		return false;
	}

	/**
	 * 将微信公众平台素材数据转换为第三方平台素材数据
	 * @param accessToken
	 * @param item
	 * @param companyId
	 * @return
	 */
	private NewsResource transferToResource(BaseToken accessToken, MaterialItem item, String companyId) {
		
		String media_id = item.getMedia_id();
		
		NewsResource newsResource = getNewsResourceByMediaId(media_id);
		System.out.println(media_id + "-----resource------" + newsResource);
		
		if(newsResource == null) {
			newsResource = new NewsResource();
			
			newsResource.setMediaId(media_id);
			Date update_time = item.getUpdate_time();
			
			newsResource.setCreateTime(update_time);
			newsResource.setUpdateTime(update_time);
			
			
			MaterialContent content = item.getContent();
			if(content != null) {
				List<NewsArticle> news_item = content.getNews_item();
				List<NewsItem> newsItems = new ArrayList<NewsItem>();
				
				newsResource.setNews(newsItems);
				if(news_item != null) {
					int index = 0;
					for(NewsArticle article:news_item) {
						NewsItem newsItem = new NewsItem();
						
						String name = newsResource.getName();
						if(StringUtils.isBlank(name)) {
							newsResource.setName(name);
						}
						newsItem.setTitle(article.getTitle());
						newsItem.setAuthor(article.getAuthor());
						newsItem.setBrief(article.getDigest());
						
						try {
							byte[] data = NewsResourceApi.getImageMaterial(article.getThumb_media_id(), accessToken);   //获取封面素材数据。
							String key = QiniuUtils.upload(data);                                                       //上传到七牛服务器
							newsItem.setCover(key);
						} catch (Exception e) {
							e.printStackTrace();
						}
						newsItem.setShowCover(StringUtils.equals(article.getShow_cover_pic(), "0") ? false:true);
						newsItem.setThumbMediaId(article.getThumb_media_id());
						newsItem.setContent(article.getContent());
						newsItem.setKey(index);
						newsItem.setUrl(article.getContent_source_url());
						newsItems.add(newsItem);
						index ++;
					}
				}
			}
			newsResource.setCompanyId(companyId);
			saveNewsResource(newsResource);
		}
		return newsResource;
	}
	
	/**
	 * 分页查找图文
	 * @param page 分页工具类
	 */
	public void findNewsResources(Page<NewsResource> page) {
		newsResourceDao.find(page);
	}
	
	public NewsResource getNewsResourceById(String id) {
		return newsResourceDao.get(id);
	}
	
	public NewsResource getNewsResourceByMediaId(String mediaId) {
		return newsResourceDao.getByProperty("mediaId", mediaId);
	}
	
	public void deleteNewsResource(NewsResource newsResource) {
		newsResourceDao.delete(newsResource);
	}
	
	/**
	 * 获取图文中图文项
	 * @param newsId
	 * @param key
	 * @return
	 */
	public NewsItem getNewsItem(String newsId, int key) {
		if(StringUtils.isNotBlank(newsId)) {
			NewsResource newsResource = getNewsResourceById(newsId);
			if(newsResource != null) {
				List<NewsItem> news = newsResource.getNews();
				if(news != null) {
					for(NewsItem item:news) {
						if(key == item.getKey()) {
							return item;
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取图文中图文项
	 * @param newsResource
	 * @param key
	 * @return
	 */
	public NewsItem getNewsItem(NewsResource newsResource, int key) {
		if(newsResource != null) {
			if(newsResource != null) {
				List<NewsItem> news = newsResource.getNews();
				if(news != null) {
					for(NewsItem item:news) {
						if(key == item.getKey()) {
							return item;
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 批量删除栏图文
	 * @param ids
	 */
	public void deleteMoreNewsResource(String ids, String companyId) {
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				NewsResource newsResource = getNewsResourceById(id);
				if(newsResource != null) {
					newsResource.setCompanyId(companyId);
					deleteNewsResource(newsResource);
				}
			}
		}
	}
}
