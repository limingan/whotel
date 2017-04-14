package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 微信门锁绑定查询
 * @author 柯鹏程
 * 
 */
public class RoomBindQuery extends AbstractInputParam {
	
	private String opType = "微信门锁绑定";
	
	private Map<String, Object> roomBind;
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	@Override
	public String getRoot() {
		return null;
	}

	public Map<String, Object> getRoomBind() {
		return roomBind;
	}

	public void setRoomBind(Map<String, Object> roomBind) {
		this.roomBind = roomBind;
	}
}
