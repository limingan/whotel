package com.weixin.core.bean;

import org.springframework.util.StringUtils;

import com.weixin.core.common.WeixinConstant;

/**
 * 文本消息（接受+回复）
 */
@SuppressWarnings("serial")
public class TextMsg extends WeixinMsg {
	private String content;

	public TextMsg() {
		super();
		this.msgType = WeixinConstant.MsgType.TextMsg;
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
		TextMsg other = (TextMsg) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		return true;
	}

}
