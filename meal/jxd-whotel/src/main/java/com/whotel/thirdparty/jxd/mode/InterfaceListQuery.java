package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * @author 柯鹏程
 */
public class InterfaceListQuery extends AbstractInputParam {
	private String opType = "接口列表";

	private String code;
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String hotelCode) {
		this.code = hotelCode;
	}
	
	@Override
	public String getRoot() {
		return null;
	}
}
