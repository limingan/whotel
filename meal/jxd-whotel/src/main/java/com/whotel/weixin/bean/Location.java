package com.whotel.weixin.bean;

import java.io.Serializable;
import java.util.Date;

public class Location implements Serializable {

	private static final long serialVersionUID = 1226859577889367078L;
	public static final int FromWeixin = 1;
	public static final int FromHtml5 = 2;
	private int from;//
	private long createTime;
	private double lat;
	private double lon;

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public static Location createWeixinLocation(double lat, double lon) {
		return createLocation(FromWeixin, lat, lon);
	}

	public static Location createHtml5Location(double lat, double lon) {
		return createLocation(FromHtml5, lat, lon);
	}

	private static Location createLocation(int from, double lat, double lon) {
		Location location = new Location();
		location.setCreateTime(new Date().getTime());
		location.setLat(lat);
		location.setLon(lon);
		return location;
	}
}
