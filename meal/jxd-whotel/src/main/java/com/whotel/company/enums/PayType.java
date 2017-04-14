package com.whotel.company.enums;

import com.whotel.common.base.Labeled;

/**
 * 支付类型
 * @author 柯鹏程
 */
public enum PayType implements Labeled {

	WX("微信支付"),ZFB("支付宝"),CFT("财付通"), WY("网银"),WX_PROVIDER("微信服务商支付");
	
	private String label;

	private PayType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
