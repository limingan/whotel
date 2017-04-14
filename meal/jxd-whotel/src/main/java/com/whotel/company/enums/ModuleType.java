package com.whotel.company.enums;

import com.whotel.common.base.Labeled;

/**
 * 模块类型
 * @author 柯鹏程
 */
public enum ModuleType implements Labeled {

	HOTEL("酒店"),TICKET("门票"),MALL("商城"),MEAL("餐饮"),COMBO("套票");
	
	private String label;
	
	private Boolean checked;
	
	private String url;//链接

	public String getUrl() {
		if(this == HOTEL){
			url = "/oauth/hotel/listHotelOrder.do";
		}else if(this == TICKET){
			url = "/oauth/ticket/listTicketOrder.do";
		}else if(this == MALL){
			url = "/oauth/shop/listGoodsOrderWeiXin.do";
		}else if(this == MEAL){
			url = "/oauth/meal/listMealOrder.do";
		}else if(this == COMBO){
			url = "/oauth/combo/listComboOrder.do";
		}
		return url;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	private ModuleType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
