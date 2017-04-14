package com.weixin.core.bean;

import com.weixin.core.common.WeixinConstant;

/**
 * 语音消息或语音识别消息（接受+回复）
 */
@SuppressWarnings("serial")
public class VoiceMsg extends WeixinMsg {
	/**
	 * <pre>
	 * 接受：语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 * 回复：通过上传多媒体文件，得到的id
	 */
	private String mediaID;
	/** 接受：语音格式，如amr，speex等 */
	private String format;
	/** 接受：语音识别结果（仅当语音识别消息） */
	private String recognition;

	public VoiceMsg() {
		super();
		this.msgType = WeixinConstant.MsgType.VoiceMsg;
	}

	public String getMediaID() {
		return mediaID;
	}

	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((mediaID == null) ? 0 : mediaID.hashCode());
		result = prime * result
				+ ((recognition == null) ? 0 : recognition.hashCode());
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
		VoiceMsg other = (VoiceMsg) obj;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (mediaID == null) {
			if (other.mediaID != null)
				return false;
		} else if (!mediaID.equals(other.mediaID))
			return false;
		if (recognition == null) {
			if (other.recognition != null)
				return false;
		} else if (!recognition.equals(other.recognition))
			return false;
		return true;
	}

}
