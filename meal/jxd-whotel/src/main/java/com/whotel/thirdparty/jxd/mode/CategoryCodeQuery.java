package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 类别代码查询
 * @author 冯勇
 *
 */
public class CategoryCodeQuery extends AbstractInputParam {

	private String opType = "代码查询";
	
	private String categoryCode;
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	@Override
	public String getRoot() {
		return null;
	}

}
