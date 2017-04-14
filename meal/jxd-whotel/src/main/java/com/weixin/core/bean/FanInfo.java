package com.weixin.core.bean;

public class FanInfo {
	private int subscribe;// 是否关注
	private String nickname;
	private String headimgurl;
	private Integer errcode;
	private int sex;
	private String province;
	private String city;
	private String unionid;
	private String openid;
	
	public int getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public Integer getErrcode() {
		return errcode;
	}
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
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
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	@Override
	public String toString() {
		return "FanInfo [subscribe=" + subscribe + ", nickname=" + nickname
				+ ", headimgurl=" + headimgurl + ", errcode=" + errcode
				+ ", sex=" + sex + ", province=" + province + ", city=" + city
				+ ", unionid=" + unionid + "]";
	}
}