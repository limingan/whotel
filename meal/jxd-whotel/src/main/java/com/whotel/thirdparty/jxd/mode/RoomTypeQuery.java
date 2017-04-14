package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 房间类型查询
 * 
 * @author 冯勇
 * 
 */
public class RoomTypeQuery extends AbstractInputParam {
	private String opType = "房间类型查询";
	private String hotelCode; // 酒店代码，三位数字

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
