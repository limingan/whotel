package com.whotel.company.enums;

import com.whotel.common.base.Labeled;

/**
 * <pre>
 * 关键词匹配方式，对应微信公众平台的关键词自动回复的：模糊匹配&全匹配
 * 已全匹配 - 关键词和内容完全一样
 * 未全匹配 - 关键词出现在内容中
 * 
 * @author 冯勇
 * 
 */
public enum KeywordMatchType implements Labeled {
	ALL("全匹配"), PART("模糊匹配");

	private String label;

	private KeywordMatchType(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}

}
