package com.whotel.company.enums;

import com.whotel.common.base.Labeled;

/**
 * 资源类型
 * @author 冯勇
 */
public enum ResponseType implements Labeled {

	TEXT("文本"), LINK("链接"), PIC("图片"), VOICE("语音"), MUSIC("音乐"), VIDEO("视频"), NEWS("图文"),UNLOCK("门锁"),SCANCODE("扫码");
	
	private String label;

	private ResponseType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
