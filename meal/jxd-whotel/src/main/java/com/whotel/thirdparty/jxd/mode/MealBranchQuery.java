package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 餐厅分店列表查询
 * @author 柯鹏程
 * 
 */
public class MealBranchQuery extends AbstractInputParam {
	
	private String opType = "酒店列表查询";

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
}
