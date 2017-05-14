package com.whotel.meal.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.Region;
import com.whotel.common.entity.UnDeletedEntity;
import com.whotel.common.enums.PayMent;
import com.whotel.common.util.QiniuUtils;
import com.whotel.common.util.RepoUtil;
/**
 * 餐厅
 * @author Administrator
 *
 */
@Entity(noClassnameStored=true)
public class Restaurant extends UnDeletedEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String companyId;
	
	private String hotelCode;

	private String hotelName;//酒店名称

	private Date createDate;
	
	private String refeNo;//餐厅编码
	
	private String name;//名称
	
	private String miniature;//缩图

	private String banner;//广告图
	
	private String address;//地址
	
	private String remark;//说明
	
	private String businessTime;//营业时间
	
	private Boolean isEnable;//状态    false 1: 不可订 true 2:可订

	private Float serviceFee = 0f;//服务费
	
//	private String mealType;//餐段
	
	private String tel;//电话
	
	private String floor;//楼层
	
	private Integer orderIndex;//显示顺序
	
	private String notes;//预订须知
	
	private String brief;//简介
	
	@Embedded
	private List<PayMent> payMents;//支付
	
	private Boolean isCashBack;//可否返现
	
	private Integer useWay;//使用方式 ：0 固定金额，1 百分比 
	
	private Integer moneyPercentage;//钱或百分比
	
	private Integer reservationWay;//订位方式 ：0 电话预定，1 在线预定
	
	private String cuisine;//菜系
	
	private Integer regionId;//地区编号
	
	private String coord;    //坐标

	private String imgUrl;//点餐进入微信号时的图片链接

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getCoord() {
		return coord;
	}

	public void setCoord(String coord) {
		this.coord = coord;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public Integer getReservationWay() {
		return reservationWay;
	}

	public void setReservationWay(Integer reservationWay) {
		this.reservationWay = reservationWay;
	}

	public Integer getUseWay() {
		return useWay;
	}

	public void setUseWay(Integer useWay) {
		this.useWay = useWay;
	}

	public Integer getMoneyPercentage() {
		return moneyPercentage;
	}

	public void setMoneyPercentage(Integer moneyPercentage) {
		this.moneyPercentage = moneyPercentage;
	}

	public Boolean getIsCashBack() {
		return isCashBack;
	}

	public void setIsCashBack(Boolean isCashBack) {
		this.isCashBack = isCashBack;
	}

	public List<PayMent> getPayMents() {
		return payMents;
	}

	public void setPayMents(List<PayMent> payMents) {
		this.payMents = payMents;
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

	public String getRefeNo() {
		return refeNo;
	}

	public void setRefeNo(String refeNo) {
		this.refeNo = refeNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(String businessTime) {
		this.businessTime = businessTime;
	}

	public Float getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Float serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	
	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
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

//	public boolean isHasMealType(String mealType){
//		if(StringUtils.isBlank(this.mealType)||StringUtils.isBlank(mealType)){
//			return false;
//		}
//		return this.mealType.contains(mealType);
//	}
	
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

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}
	
	public boolean isHasPayMent(PayMent payMent){
		if(payMents == null){
			return false;
		}
		return payMents.contains(payMent);
	}
	
	public Region getRegion() {
		return RepoUtil.getRegionByRegionId(regionId);
	}
	
	public String[] getCoords() {
		String[] coords = new String[]{"", ""};
		if(StringUtils.isNotBlank(coord)) {
			coords = coord.split(",");
		}
		return coords;
	}

}
