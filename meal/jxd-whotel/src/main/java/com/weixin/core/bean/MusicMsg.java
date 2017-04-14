package com.weixin.core.bean;

import com.weixin.core.common.WeixinConstant;

/**
 * 音乐消息（回复）
 */
@SuppressWarnings("serial")
public class MusicMsg extends WeixinMsg {
	private Music music;

	public MusicMsg() {
		super();
		this.msgType = WeixinConstant.MsgType.MusicMsg;
		music = new Music();
		music.setDescription("电视剧《他来自天堂》主题曲歌手李克勤");
		music.setTitle("红日");
		music.setMusicUrl("http://stream10.qqmusic.qq.com/31432174.mp3");
		music.sethQMusicUrl("http://stream10.qqmusic.qq.com/31432174.mp3");
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((music == null) ? 0 : music.hashCode());
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
		MusicMsg other = (MusicMsg) obj;
		if (music == null) {
			if (other.music != null)
				return false;
		} else if (!music.equals(other.music))
			return false;
		return true;
	}

}
