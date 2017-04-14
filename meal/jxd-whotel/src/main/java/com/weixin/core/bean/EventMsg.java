package com.weixin.core.bean;

import com.weixin.core.common.WeixinConstant;

/**
 * 事件消息（接受）
 * 
 */
@SuppressWarnings("serial")
public class EventMsg extends WeixinMsg {
	/**
	 * <pre>
	 * subscribe	- 关注(订阅)
	 * unsubscribe	- 取消关注(取消订阅)
	 * scan		- 已关注扫描二维码，
	 * CLICK		- 自定义菜单事件
	 * LOCATION	- 上报地理位置事件
	 * </pre>
	 */
	private String event;
	/**
	 * <pre>
	 * 扫二维码关注事件：该KEY值，qrscene_为前缀，后面为二维码的参数值，如：qrscene_123123
	 * 已关注扫二维码事件：该KEY值是一个32位无符号整数，代表二维码参数
	 * 自定义菜单事件：该KEY值，与自定义菜单接口中KEY值对应
	 * </pre>
	 */
	private String eventKey;
	/**
	 * ticket仅在扫二维码关注事件中使用，二维码的ticket，可用来换取二维码图片
	 */
	private String ticket;
	/**
	 * 仅当event为“LOCATION”时有用， 地理位置纬度
	 */
	private double latitude;
	/**
	 * 仅当event为“LOCATION”时有用， 地理位置经度
	 */
	private double longitude;
	/**
	 * 仅当event为“LOCATION”时有用， 地理位置精确度
	 */
	private double precision;
	
	private String cardId;
	
	private String isGiveByFriend;
	
	private String userCardCode;
	
	public EventMsg() {
		super();
		this.msgType = WeixinConstant.MsgType.EventMsg;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getPrecision() {
		return precision;
	}

	public void setPrecision(double precision) {
		this.precision = precision;
	}
	
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getIsGiveByFriend() {
		return isGiveByFriend;
	}

	public void setIsGiveByFriend(String isGiveByFriend) {
		this.isGiveByFriend = isGiveByFriend;
	}

	public String getUserCardCode() {
		return userCardCode;
	}

	public void setUserCardCode(String userCardCode) {
		this.userCardCode = userCardCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result
				+ ((eventKey == null) ? 0 : eventKey.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(precision);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((ticket == null) ? 0 : ticket.hashCode());
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
		EventMsg other = (EventMsg) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (eventKey == null) {
			if (other.eventKey != null)
				return false;
		} else if (!eventKey.equals(other.eventKey))
			return false;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		if (Double.doubleToLongBits(precision) != Double
				.doubleToLongBits(other.precision))
			return false;
		if (ticket == null) {
			if (other.ticket != null)
				return false;
		} else if (!ticket.equals(other.ticket))
			return false;
		return true;
	}

}
