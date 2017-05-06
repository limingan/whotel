package com.whotel.waiter.entity;
import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

/** 
 * @ClassName: Waiter 
 * @Description: 李中辉
 * @author 李中辉 
 * @date 2017年5月6日 上午8:47:46  
 */
@Entity(noClassnameStored=true)
public class Waiter extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String companyId;
	
	private String hotelCode;//存的是编码
	
	private String hotelName; //分店名称，冗余（提高查询效率）

	private String restaurantId;//存的是ID, 而不是餐厅编码
	
	private String restaurantName;//餐厅名称， 冗余（提高查询效率）
	
	private Date createDate;
	
	private Date updateDate;
	
	private String userNo;
	
	private String userName;
	
	private String loginUserName; //服务员登录用户名， 在本公司内唯一
	
	private String pwd;
	
	private String iCCard;
	
    private String status;//0: 可用， 1：停用

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public String getiCCard() {
		return iCCard;
	}


	public void setiCCard(String iCCard) {
		this.iCCard = iCCard;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
	
}
