package com.weixin.core.bean;

import com.weixin.core.common.WeixinConstant;

/**
 * 视频消息（接受+回复）
 */
@SuppressWarnings("serial")
public class VideoMsg extends WeixinMsg {
	/**
	 * <pre>
	 * 接受：视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 * 回复：通过上传多媒体文件，得到的id
	 */
	private String mediaId;
	/**
	 * 接受：视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。 回复：缩略图的媒体id，通过上传多媒体文件，得到的id
	 */
	private String thumbMediaId;

	public VideoMsg() {
		super();
		this.msgType = WeixinConstant.MsgType.VideoMsg;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((mediaId == null) ? 0 : mediaId.hashCode());
		result = prime * result
				+ ((thumbMediaId == null) ? 0 : thumbMediaId.hashCode());
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
		VideoMsg other = (VideoMsg) obj;
		if (mediaId == null) {
			if (other.mediaId != null)
				return false;
		} else if (!mediaId.equals(other.mediaId))
			return false;
		if (thumbMediaId == null) {
			if (other.thumbMediaId != null)
				return false;
		} else if (!thumbMediaId.equals(other.thumbMediaId))
			return false;
		return true;
	}

}
