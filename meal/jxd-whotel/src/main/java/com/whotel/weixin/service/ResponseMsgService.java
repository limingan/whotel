package com.whotel.weixin.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.core.api.MediaUploador;
import com.weixin.core.bean.Image;
import com.weixin.core.bean.ImageMsg;
import com.weixin.core.bean.Item;
import com.weixin.core.bean.LinkMsg;
import com.weixin.core.bean.MusicMsg;
import com.weixin.core.bean.NewsMsg;
import com.weixin.core.bean.TextMsg;
import com.weixin.core.bean.VideoMsg;
import com.weixin.core.bean.VoiceMsg;
import com.weixin.core.bean.WeixinMsg;
import com.weixin.core.common.WeixinConstant;
import com.whotel.common.base.Constants;
import com.whotel.common.util.SystemConfig;
import com.whotel.company.entity.NewsItem;
import com.whotel.company.entity.NewsResource;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.entity.ResponseMsg;
import com.whotel.company.enums.PublicType;
import com.whotel.company.enums.ResponseType;
import com.whotel.company.service.NewsResourceService;
import com.whotel.company.service.PublicNoService;
import com.whotel.ext.redis.RedisCache;
import com.whotel.hotel.service.UnlockService;

/**
 * 微信消息转换接口
 * @author 冯勇
 *
 */
@Service
public class ResponseMsgService {

	private static Logger log = LoggerFactory.getLogger(ResponseMsgService.class);
	
	@Autowired
	private NewsResourceService newsResourceService;
	
	@Autowired
	private PublicNoService publicNoService;
	
	@Autowired
	private RedisCache redisCache;
	
	@Autowired
	private UnlockService unlockService;
	
	private final static String newsItemUrl = SystemConfig.getProperty("server.url") + "/front/showNewsItem.do";
	
	/**
	 * 提供将自身转化为微信消息的方法
	 * @param obj
	 * @return
	 */
	public WeixinMsg toWeixinMsg(ResponseMsg rm, String openId) {
		WeixinMsg weixinMsg = null;
		if(rm != null) {
			ResponseType resourceType = rm.getResponseType();
			switch(resourceType) {
				case TEXT:   //文本消息
					TextMsg textMsg = new TextMsg();
				    String text = rm.getText();
				    if(StringUtils.isNotBlank(text)) {
						text = text.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
						textMsg.setContent(text);
						weixinMsg = textMsg;
				    }
					break;
				case LINK:   //链接消息
					LinkMsg linkMsg = new LinkMsg();
					String url = oauthUrlHandle(rm.getUrl(), openId);
					if(StringUtils.isNotBlank(url)) {
						linkMsg.setUrl(url);
						weixinMsg = linkMsg;
					}
					break;
				case NEWS:   //图文消息
				    String newsId = rm.getNewsId();
				    NewsResource newsResource = newsResourceService.getNewsResourceById(newsId);
				    if(newsResource != null) {
				    	List<NewsItem> news = newsResource.getNews();
						NewsMsg msg = new NewsMsg();
						List<Item> articles = msg.getArticles();
						if (newsResource.getMultiple()) {
							for (NewsItem item : news) {
								genArticles(articles, newsId, item, openId);
							}
						} else {
							NewsItem item = news.get(0);
							genArticles(articles, newsId, item, openId);
						}
						weixinMsg = msg.refreshArticleCount();
				    }
					break;
				case PIC:   //图片消息
					ImageMsg imageMsg = new ImageMsg();
					String pic = rm.getPic();
					if(StringUtils.isNotBlank(pic)) {
						//imageMsg.setPicUrl(pic);
						Date createTime = rm.getCreateTime();
						long t = 0;
						if(createTime != null) {
							t = createTime.getTime();
						}
						String cacheKey = t +"_mediaId";
						String mediaId = (String) redisCache.get(cacheKey);
						
						if(StringUtils.isBlank(mediaId)) {
							PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
							if(publicNo != null) {
								mediaId = MediaUploador.uploadTempWebPic(publicNo.getAppId(), publicNo.getAppSecret(), WeixinConstant.MsgType.ImageMsg, pic);
								if(StringUtils.isNotBlank(mediaId)) {
									redisCache.put(cacheKey, mediaId, Constants.CacheKey.DAY_TIMIE_OUT*2);
								}
							}
						}
						Image image = new Image();
						image.setMediaId(mediaId);
						imageMsg.setImage(image);
						weixinMsg = imageMsg;
					}
					break;
				case VOICE: //语音消息
					VoiceMsg voiceMsg = new VoiceMsg();
					String voice = rm.getVoice();
					if(StringUtils.isNotBlank(voice)) {
						weixinMsg = voiceMsg;
					}
					break;
				case MUSIC: //音乐消息
					MusicMsg musicMsg = new MusicMsg();
					String music = rm.getMusic();
					if(StringUtils.isNotBlank(music)) {
						weixinMsg = musicMsg;
					}
					break;
				case VIDEO: //视频消息
					VideoMsg videoMsg = new VideoMsg();
					String video = rm.getVideo();
					if(StringUtils.isNotBlank(video)) {
						weixinMsg = videoMsg;
					}
					break;
				case UNLOCK: //开锁
					TextMsg textMsg1 = new TextMsg();
				    textMsg1.setContent("正在开锁中，请确认蓝牙是否已打开，请稍后...");
				    weixinMsg = textMsg1;
					unlockService.unlock(openId);
					break;
				default:
					log.warn("not support type!");
					break;
			}
		}
		return weixinMsg;
	}
	
	private void genArticles(List<Item> articles, String newsId, NewsItem item, String openId) {
		Item a = new Item();
		a.setTitle(item.getTitle().replace("\\n", "\n"));
		if (!StringUtils.isBlank(item.getBrief())) {
			a.setDescription(item.getBrief().replace("\\n", "\n"));
		}
		a.setPicUrl(item.getCoverUrl());
		if (StringUtils.isBlank(item.getUrl())) {
			a.setUrl(newsItemUrl+"?idKey="+newsId+","+item.getKey());
		} else {
			a.setUrl(oauthUrlHandle(item.getUrl(), openId));
		}
		articles.add(a);
	}
	
	private String oauthUrlHandle(String url, String openId) {
		if(StringUtils.contains(url, "oauth")) {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			if(publicNo != null && PublicType.SUBSCRIBE.equals(publicNo.getType())) {
				if(url.indexOf("?") > -1) {
					url += "&wxid=" + openId;
				} else {
					url += "?wxid=" + openId;
				}
			}
			
			if(url.indexOf("comid=") == -1 && publicNo != null) {
				url += "&comid="+publicNo.getCompanyId();
			} 
		}
		return url;
	}
}
