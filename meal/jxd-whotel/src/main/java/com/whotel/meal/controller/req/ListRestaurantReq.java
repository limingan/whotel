package com.whotel.meal.controller.req;

/**
 * Created by limingan on 2017/4/18.
 */
public class ListRestaurantReq {

    private String companyId;   //公司ID

    private String hotelCode;   //分店Code
    
    private String restaurantId;//餐厅ID
    
    private Boolean isEnable;   // 状态
    
    public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

}
