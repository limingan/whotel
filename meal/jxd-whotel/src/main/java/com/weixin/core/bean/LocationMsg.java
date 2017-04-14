package com.weixin.core.bean;

import com.weixin.core.common.WeixinConstant;

/**
 * 位置信息（接受）
 */
@SuppressWarnings("serial")
public class LocationMsg extends WeixinMsg {
	private double Location_X;
	private double Location_Y;
	private int scale;
	private String label;

	public LocationMsg() {
		super();
		this.msgType = WeixinConstant.MsgType.LocationMsg;
	}

	public double getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(double location_X) {
		Location_X = location_X;
	}

	public double getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(double location_Y) {
		Location_Y = location_Y;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(Location_X);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(Location_Y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + scale;
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
		LocationMsg other = (LocationMsg) obj;
		if (Double.doubleToLongBits(Location_X) != Double
				.doubleToLongBits(other.Location_X))
			return false;
		if (Double.doubleToLongBits(Location_Y) != Double
				.doubleToLongBits(other.Location_Y))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (scale != other.scale)
			return false;
		return true;
	}

}
