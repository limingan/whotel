package com.weixin.core.bean;

import com.weixin.core.common.WeixinBean;

/**
 * 音乐BEAN
 */
@SuppressWarnings("serial")
public class Music implements WeixinBean {
	private String title;
	private String description;
	private String musicUrl;
	private String hQMusicUrl;
	/** 缩略图的媒体id，通过上传多媒体文件，得到的id */
	private String thumbMediaId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String gethQMusicUrl() {
		return hQMusicUrl;
	}

	public void sethQMusicUrl(String hQMusicUrl) {
		this.hQMusicUrl = hQMusicUrl;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	public String toString() {
		return "Music [title=" + title + ", description=" + description
				+ ", musicUrl=" + musicUrl + ", hQMusicUrl=" + hQMusicUrl
				+ ", thumbMediaId=" + thumbMediaId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((hQMusicUrl == null) ? 0 : hQMusicUrl.hashCode());
		result = prime * result
				+ ((musicUrl == null) ? 0 : musicUrl.hashCode());
		result = prime * result
				+ ((thumbMediaId == null) ? 0 : thumbMediaId.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Music other = (Music) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (hQMusicUrl == null) {
			if (other.hQMusicUrl != null)
				return false;
		} else if (!hQMusicUrl.equals(other.hQMusicUrl))
			return false;
		if (musicUrl == null) {
			if (other.musicUrl != null)
				return false;
		} else if (!musicUrl.equals(other.musicUrl))
			return false;
		if (thumbMediaId == null) {
			if (other.thumbMediaId != null)
				return false;
		} else if (!thumbMediaId.equals(other.thumbMediaId))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
