package com.whotel.meal.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.UnDeletedEntity;
import com.whotel.common.util.QiniuUtils;
import com.whotel.common.util.RepoUtil;
/**
 * 餐台
 * @author Administrator
 *
 */
@Entity(noClassnameStored=true)
public class MealTab extends UnDeletedEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String companyId;
	
	private String hotelCode;

	private Date createDate;
	
	private String tabNo;//餐台代码
	
	private String restaurantId;//分厅的id

	private String tabName;//餐台的名称

	private Integer status;//状态     1: 不可订  2:可订

	private Integer seats;//座位数/容纳人数

	private String remark;//餐台的说明

	private String miniature;//缩图

	private String banner;//广告图

	private Boolean isEnable;
	
	private Integer orderIndex;//显示顺序
	
	private Float minimums;//最低消费
	
	private Float deposit = 0f;//定金
	
	private String bookTel;//预订电话
	
	private String notes;//预订须知
	
	private String timeStamp;
	
	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getTabNo() {
		return tabNo;
	}

	public void setTabNo(String tabNo) {
		this.tabNo = tabNo;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public Float getMinimums() {
		return minimums;
	}

	public void setMinimums(Float minimums) {
		this.minimums = minimums;
	}

	public Float getDeposit() {
		return deposit;
	}

	public void setDeposit(Float deposit) {
		this.deposit = deposit;
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

	public String getBookTel() {
		return bookTel;
	}

	public void setBookTel(String bookTel) {
		this.bookTel = bookTel;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
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
	
	public Restaurant getRestaurant(){
		return RepoUtil.getRestaurantById(restaurantId);
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
}
