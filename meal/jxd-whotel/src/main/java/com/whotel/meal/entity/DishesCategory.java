package com.whotel.meal.entity;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.UnDeletedEntity;
import com.whotel.common.util.RepoUtil;
/**
 * 餐饮分类
 * @author kf
 *
 */
@Entity(noClassnameStored=true)
public class DishesCategory extends UnDeletedEntity {

	private static final long serialVersionUID = 1L;

	private String companyId;

	private String hotelCode;
	
	private String restaurantId;//分厅编码
	
	private String dishNo;//菜式分类编码
	
	private String dishName;//菜式分类名称
	
	private String remark;//说明
	
	private Date createTime;//创建时间
	
	private Integer displayOrder;  //排序

	private List<Dishes> dishesList;//该类下的菜品

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getDishNo() {
		return dishNo;
	}

	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Restaurant getRestaurant(){
		return RepoUtil.getRestaurantById(restaurantId);
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public List<Dishes> getDishesList() {
		return dishesList;
	}

	public void setDishesList(List<Dishes> dishesList) {
		this.dishesList = dishesList;
	}
}
