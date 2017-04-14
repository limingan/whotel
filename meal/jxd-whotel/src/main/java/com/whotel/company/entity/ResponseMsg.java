package com.whotel.company.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Embedded;

import com.whotel.common.util.RepoUtil;
import com.whotel.company.enums.ResponseType;

/**
 * 响应消息
 * @author 冯勇
 */
@Embedded
public class ResponseMsg {
	
	private String newsId;
	
	private String url;
	
	private String text;
	
	private String pic;
	
	private String voice;
	
	private String music;
	
	private String video;
	
	private String unlock;
	
	private String scancode;
	
	private Date createTime = new Date();
	
	public ResponseType getResponseType() {
		if(StringUtils.isNotBlank(newsId)) {
			return ResponseType.NEWS;
		} else if(StringUtils.isNotBlank(url)) {
			return ResponseType.LINK;
		} else if(StringUtils.isNotBlank(unlock)) {
			return ResponseType.UNLOCK;
		} else if(StringUtils.isNotBlank(text)) {
			return ResponseType.TEXT;
		} else if(StringUtils.isNotBlank(pic)) {
			return ResponseType.PIC;
		} else if(StringUtils.isNotBlank(voice)) {
			return ResponseType.VOICE;
		} else if(StringUtils.isNotBlank(music)) {
			return ResponseType.MUSIC;
		} else if(StringUtils.isNotBlank(video)) {
			return ResponseType.VIDEO;
		} else if(StringUtils.isNotBlank(scancode)) {
			return ResponseType.SCANCODE;
		}
		return null;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		if(StringUtils.isBlank(newsId)) {
			newsId = null;
		}
		this.newsId = newsId;
	}
	
	public NewsResource getNewsResource() {
		return RepoUtil.getNewsResource(newsId);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if(StringUtils.isBlank(url)) {
			url = null;
		}
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if(StringUtils.isBlank(text)) {
			text = null;
		}
		this.text = text;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		if(StringUtils.isBlank(pic)) {
			pic = null;
		}
		this.pic = pic;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		if(StringUtils.isBlank(voice)) {
			voice = null;
		}
		this.voice = voice;
	}

	public String getMusic() {
		return music;
	}

	public void setMusic(String music) {
		if(StringUtils.isBlank(music)) {
			music = null;
		}
		this.music = music;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		if(StringUtils.isBlank(video)) {
			video = null;
		}
		this.video = video;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getUnlock() {
		return unlock;
	}

	public void setUnlock(String unlock) {
		if(StringUtils.isBlank(unlock)) {
			unlock = null;
		}
		this.unlock = unlock;
	}

	public String getScancode() {
		return scancode;
	}

	public void setScancode(String scancode) {
		if(StringUtils.isBlank(scancode)) {
			scancode = null;
		}
		this.scancode = scancode;
	}
}
