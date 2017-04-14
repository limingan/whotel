package com.whotel.webiste.enums;

import com.whotel.common.base.Labeled;

public enum TargetType implements Labeled {
	
	LINK("链接"), NEWS("图文"), CONTACT("联系方式"), NAVIGATION("导航"),SYSTEM("系统连接");
	
	private String label;

	private TargetType(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}
}
