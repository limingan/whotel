package com.weixin.core.bean;

import com.weixin.core.common.WeixinConstant;

/**
 * 图片消息（接受+回复）
 * 
 */
@SuppressWarnings("serial")
public class ImageMsg extends WeixinMsg {
	// 接受：图片链接
	private String picUrl;
	// 接受：图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	// 回复：通过上传多媒体文件，得到的id。
	private String mediaId;
	
	private Image image;
	
	public ImageMsg() {
		super();
		this.msgType = WeixinConstant.MsgType.ImageMsg;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((mediaId == null) ? 0 : mediaId.hashCode());
		result = prime * result + ((picUrl == null) ? 0 : picUrl.hashCode());
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
		ImageMsg other = (ImageMsg) obj;
		if (mediaId == null) {
			if (other.mediaId != null)
				return false;
		} else if (!mediaId.equals(other.mediaId))
			return false;
		if (picUrl == null) {
			if (other.picUrl != null)
				return false;
		} else if (!picUrl.equals(other.picUrl))
			return false;
		return true;
	}

}
