package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;


/**
 * 封装会员类型请求
 * @author 冯勇
 *
 */
public class MemberTypeQuery extends AbstractInputParam {
	private String opType = "代码查询";
	private String categoryCode = "mbrcardtype";

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
		// TODO Auto-generated method stub
		return null;
	}
}
