package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 酒店城市列表查询
 * @author 冯勇
 * 
 */
public class HotelCityQuery extends AbstractInputParam {
	
	private String opType = "酒店城市列表";
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}
	
	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
