package com.weixin.core.bean;

import org.springframework.util.StringUtils;

import com.weixin.core.common.WeixinConstant;

/**
 * 设备消息（接受+回复）
 */
@SuppressWarnings("serial")
public class DeviceTextMsg extends WeixinMsg {
	private String content;
	private String deviceID;
	private String deviceType;
	private String sessionID;

	public DeviceTextMsg() {
		super();
		this.msgType = WeixinConstant.MsgType.DeviceText;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = StringUtils.trimWhitespace(content);
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((deviceID == null) ? 0 : deviceID.hashCode());
		result = prime * result + ((deviceType == null) ? 0 : deviceType.hashCode());
		result = prime * result + ((sessionID == null) ? 0 : sessionID.hashCode());
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
		DeviceTextMsg other = (DeviceTextMsg) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (deviceID == null) {
			if (other.deviceID != null)
				return false;
		} else if (!deviceID.equals(other.deviceID))
			return false;
		if (deviceType == null) {
			if (other.deviceType != null)
				return false;
		} else if (!deviceType.equals(other.deviceType))
			return false;
		if (sessionID == null) {
			if (other.sessionID != null)
				return false;
		} else if (!sessionID.equals(other.sessionID))
			return false;
		return true;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
}
