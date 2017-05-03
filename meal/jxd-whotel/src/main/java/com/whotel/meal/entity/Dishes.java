package com.whotel.meal.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.UnDeletedEntity;
import com.whotel.common.util.QiniuUtils;
import com.whotel.common.util.RepoUtil;
/**
 * 菜式
 * @author Administrator
 *
 */
@Entity(noClassnameStored=true)
public class Dishes extends UnDeletedEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String companyId;
	
	private String hotelCode;

	private Date createDate;
	
	private Date updateTime;

	private String dishNo;//
	
	private String dishName;//菜式的名称
	
	private String unit;//菜式单位
	
	private Float price;//价格
	
	private String remark;//菜式的说明

	private String dishClass;//菜式的大类名称

	private String dishType;//菜式的小类名称

	private String restaurantId;//分厅编码

	private String miniature;//缩图

	private String banner;//广告图
	
	private Integer orderIndex;//显示顺序
	
//	private String mealType;//所属餐式
	
	private Boolean isEnable;

	private String notes;//预订须知
	
	private String brief;//菜肴简介
	
	private Integer orderWay;//点餐方式：0 单点，1 套餐
	
	private String dishno1;//分类编码
	
	private Float marketPrice;//市场价
	
	private String shuffleNo;//市别id

	private String multiStyle;//规格列表

	private Integer isMultiStyle;//是否多规格

	private Integer isSuite;//是否套餐


	private String suiteData;//套餐内容字符串

	public String getSuiteData() {
		return suiteData;
	}

	public void setSuiteData(String suiteData) {
		this.suiteData = suiteData;
	}

	public Integer getIsMultiStyle() {
		return isMultiStyle;
	}

	public void setIsMultiStyle(Integer isMultiStyle) {
		this.isMultiStyle = isMultiStyle;
	}

	public Integer getIsSuite() {
		return isSuite;
	}

	public void setIsSuite(Integer isSuite) {
		this.isSuite = isSuite;
	}

	public String getDishno1() {
		return dishno1;
	}

	public void setDishno1(String dishno1) {
		this.dishno1 = dishno1;
	}

	public Integer getOrderWay() {
		return orderWay;
	}

	public void setOrderWay(Integer orderWay) {
		this.orderWay = orderWay;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDishClass() {
		return dishClass;
	}

	public void setDishClass(String dishClass) {
		this.dishClass = dishClass;
	}

	public String getDishType() {
		return dishType;
	}

	public void setDishType(String dishType) {
		this.dishType = dishType;
	}

	public String getMiniature() {
		return miniature;
	}

	public void setMiniature(String miniature) {
		this.miniature = miniature;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDishNo() {
		return dishNo;
	}

	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}
	
	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String[] getBanners() {
		return StringUtils.split(banner, ",");
	}
	
	public String[] getBannerUrls() {
		String[] attachs = getBanners();
		if(attachs != null) {
			for(int i=0; i<attachs.length; i++) {
				attachs[i] = QiniuUtils.getResUrl(attachs[i]);
			}
		}
		return attachs;
	}
	
	public String getMiniatureUrl(){
		return QiniuUtils.getResUrl(miniature);
	}
	
	public Restaurant getRestaurant(){
		return RepoUtil.getRestaurantById(restaurantId);
	}
	
//	public boolean isHasMealType(String mealType){
//		if(StringUtils.isBlank(this.mealType)||StringUtils.isBlank(mealType)){
//			return false;
//		}
//		return this.mealType.contains(mealType);
//	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Float getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Float marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getShuffleNo() {
		return shuffleNo;
	}

	public void setShuffleNo(String shuffleNo) {
		this.shuffleNo = shuffleNo;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getShuffleName(){
		String name = "";
		if(StringUtils.isNotBlank(shuffleNo)){
			String[] names = shuffleNo.split(",");
			for (int i = 0; i < names.length; i++) {
				if(i != 0){
					name += ",";
				}
				Shuffle shuffle = RepoUtil.getShuffleById(names[i]);
				if(shuffle == null){
					return "未同步或已删除此市别，请重新同步市别，再同步菜肴";
				}
				name += shuffle.getShuffleName();
			}
		}
		return name;
	}

	public String getMultiStyle() {
		return multiStyle;
	}

	public void setMultiStyle(String multiStyle) {
		this.multiStyle = multiStyle;
	}
}
