package com.whotel.front.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import com.whotel.common.entity.UnDeletedEntity;

@Entity(noClassnameStored=true)
public class WeixinFan extends UnDeletedEntity {
	
	private static final long serialVersionUID = -8419795941629565515L;

	@Indexed(unique = true)
	private String openId;
	
	private String unionId;
	
	private String nickName;
	
	private int sex;
	
	private String avatar;
	
	private String province;
	
	private String city;
	
	private String devCode;
	
	private boolean focus;
	
	private Date createTime;
	
	private Date updateTime;
	
	private Boolean focusGifts;//记录是否领取过关注礼品
	
	private String sceneId;//微现场
	
	private Integer loginState;//微现场登录状态，0或者null:未登录，1正在登录，2登录成功
	
	private Boolean isWinner;//微现场是否中过奖
	
	public String getDevCode() {
		return devCode;
	}

	public void setDevCode(String devCode) {
		this.devCode = devCode;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isFocus() {
		return focus;
	}

	public void setFocus(boolean focus) {
		this.focus = focus;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getFocusGifts() {
		return focusGifts;
	}

	public void setFocusGifts(Boolean focusGifts) {
		this.focusGifts = focusGifts;
	}

	@Override
	public String toString() {
		return "WeixinFan [openId=" + openId + ", unionId=" + unionId + ", nickName=" + nickName + ", sex=" + sex
				+ ", avatar=" + avatar + ", province=" + province + ", city=" + city + ", devCode=" + devCode
				+ ", focus=" + focus + ", createTime=" + createTime + ", updateTime=" + updateTime + ", focusGifts="
				+ focusGifts + "]";
	}

	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	public Integer getLoginState() {
		return loginState;
	}

	public void setLoginState(Integer loginState) {
		this.loginState = loginState;
	}

	public Boolean getIsWinner() {
		return isWinner;
	}

	public void setIsWinner(Boolean isWinner) {
		this.isWinner = isWinner;
	}

}
