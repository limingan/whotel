package com.whotel.webiste.entity;

import org.mongodb.morphia.annotations.Embedded;

import com.whotel.common.util.QiniuUtils;
import com.whotel.webiste.enums.TargetType;

/**
 * 微网站栏目
 * @author 冯勇
 */
@Embedded
public class WebsiteColumn {

	private String name;
	
	private String icon;
	
	private String bg;
	
	private String bgSize;
	
	private String bgColor;
	
	private Boolean iconEdit;
	
	private Boolean bgEdit;
	
	private Boolean bgColorEdit;
	
	private Boolean orderEdit;
	
	private Boolean remarksEdit;
	
	private String remarks;
	
	private TargetType type;
	
	private String target;
	
	private String defaultLink;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	
	public String getIconUrl() {
		return QiniuUtils.getResUrl(icon);
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getBg() {
		return bg;
	}
	public void setBg(String bg) {
		this.bg = bg;
	}
	
	public String getBgUrl() {
		return QiniuUtils.getResUrl(bg);
	}
	
	public String getBgColor() {
		return bgColor;
	}
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
	public Boolean getIconEdit() {
		return iconEdit;
	}
	public void setIconEdit(Boolean iconEdit) {
		this.iconEdit = iconEdit;
	}
	public Boolean getBgEdit() {
		return bgEdit;
	}
	public void setBgEdit(Boolean bgEdit) {
		this.bgEdit = bgEdit;
	}
	public Boolean getBgColorEdit() {
		return bgColorEdit;
	}
	public void setBgColorEdit(Boolean bgColorEdit) {
		this.bgColorEdit = bgColorEdit;
	}
	
	public Boolean getOrderEdit() {
		return orderEdit;
	}
	public void setOrderEdit(Boolean orderEdit) {
		this.orderEdit = orderEdit;
	}
	public TargetType getType() {
		return type;
	}
	public void setType(TargetType type) {
		this.type = type;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getDefaultLink() {
		return defaultLink;
	}
	public void setDefaultLink(String defaultLink) {
		this.defaultLink = defaultLink;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Boolean getRemarksEdit() {
		return remarksEdit;
	}
	public void setRemarksEdit(Boolean remarksEdit) {
		this.remarksEdit = remarksEdit;
	}
	public String getBgSize() {
		return bgSize;
	}
	public void setBgSize(String bgSize) {
		this.bgSize = bgSize;
	}
}
