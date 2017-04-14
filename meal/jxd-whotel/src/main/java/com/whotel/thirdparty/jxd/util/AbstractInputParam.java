package com.whotel.thirdparty.jxd.util;

import com.whotel.common.base.ExtendParent;

/**
 * 输入参数
 * @author 冯勇
 * 
 */
@ExtendParent(extendParentField = true)
public abstract class AbstractInputParam implements XmlBean {

	private String xType = "GemStar";

	public String getxType() {
		return xType;
	}

	public void setxType(String xType) {
		this.xType = xType;
	}

}
