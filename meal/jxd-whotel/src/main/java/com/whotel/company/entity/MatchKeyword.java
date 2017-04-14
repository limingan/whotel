package com.whotel.company.entity;

import org.apache.commons.lang.StringUtils;

import com.whotel.company.enums.KeywordMatchType;

/**
 * <pre>
 * 根据匹配方式检测传入的内容是否匹配该关键词，两种匹配方式的区别：
 * 全匹配 - 关键词和内容完全一样
 * 模糊匹配 - 关键词出现在内容中
 * @author 冯勇
 * 
 */
public class MatchKeyword {
	
	private String keyword; // 关键词
	
	private KeywordMatchType type; // 匹配方式
	
	private long count;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public KeywordMatchType getType() {
		return type;
	}

	public void setType(KeywordMatchType type) {
		this.type = type;
	}
	
	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * 根据匹配规则判断是否命中
	 * @param content
	 * @return
	 */
	public boolean isMatch(String content) {
		content = StringUtils.trim(content);
		if (StringUtils.isBlank(content)) {
			return false;
		} else if (KeywordMatchType.PART.equals(type)) {
			return StringUtils.containsIgnoreCase(keyword.toLowerCase(),content.toLowerCase());
		} else {
			return StringUtils.equalsIgnoreCase(content, keyword);
		}
	}

	/**
	 * 是否全匹配
	 * @param content
	 * @return
	 */
	public boolean isAbsMatch(String content) {
		content = StringUtils.trim(content);
		if (StringUtils.isBlank(content)) {
			return false;
		} else if (KeywordMatchType.PART.equals(type)) {
			return false;
		} else {
			return StringUtils.equalsIgnoreCase(content, this.keyword);
		}
	}
}
