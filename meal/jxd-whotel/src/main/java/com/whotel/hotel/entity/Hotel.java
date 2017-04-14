package com.whotel.hotel.entity;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Entity;
import com.whotel.common.entity.UnDeletedEntity;
import com.whotel.common.util.QiniuUtils;

/**
 * 酒店门店实体
 * @author 冯勇
 */
@Entity(noClassnameStored=true)
public class Hotel extends UnDeletedEntity {

	private static final long serialVersionUID = -6174284121768172721L;
	
	private String companyId;
	
	private String code;            		//系统对接code
	
	private String name;
	
	private String banner;
	
	private String ticketBanner;
	
	private String miniature;//缩图
	
	private String descr;
	
	private String facility;
	
	private String around;
	
	private String coord;
	
	private Date createTime;
	
	private Date updateTime;
	
	private String city;
	
	private String address;
	
	private String tel;//酒店电话
	
	private String ticketTel;//门票温泉电话
	
	private String mealUrl;//餐饮接口地址
	
	private String ticketUrl;//票券接口地址
	
	private String key;//秘钥
	
	private String corpCode;//企业码
	
	private String userName;//账号
	
	private String facilities;//酒店设施
	
	private Boolean isHotelFacilityConvert;//是否启用酒店设施
	
	//private List<Facilitys> facilitys;
	private List<Facilitys> facilitys;
	
	private Boolean isAllChecked; // 是否全选

	public Boolean getIsAllChecked() {
		return isAllChecked;
	}

	public void setIsAllChecked(Boolean isAllChecked) {
		this.isAllChecked = isAllChecked;
	}

	public Boolean getIsHotelFacilityConvert() {
		return isHotelFacilityConvert;
	}

	public List<Facilitys> getFacilitys() {
		return facilitys;
	}

	public void setFacilitys(List<Facilitys> facilitys) {
		this.facilitys = facilitys;
	}

	public void setIsHotelFacilityConvert(Boolean isHotelFacilityConvert) {
		this.isHotelFacilityConvert = isHotelFacilityConvert;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
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

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public String getAround() {
		return around;
	}

	public void setAround(String around) {
		this.around = around;
	}

	public String getCoord() {
		return coord;
	}

	public void setCoord(String coord) {
		this.coord = coord;
	}
	
	public String[] getCoords() {
		String[] coords = new String[]{"", ""};
		if(StringUtils.isNotBlank(coord)) {
			coords = coord.split(",");
		}
		return coords;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMiniature() {
		return miniature;
	}

	public void setMiniature(String miniature) {
		this.miniature = miniature;
	}
	
	public String getMiniatureUrl(){
		return QiniuUtils.getResUrl(miniature);
	}

	public String getMealUrl() {
		return mealUrl;
	}

	public void setMealUrl(String mealUrl) {
		this.mealUrl = mealUrl;
	}

	public String getTicketUrl() {
		return ticketUrl;
	}

	public void setTicketUrl(String ticketUrl) {
		this.ticketUrl = ticketUrl;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCorpCode() {
		return corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTicketBanner() {
		return ticketBanner;
	}

	public void setTicketBanner(String ticketBanner) {
		this.ticketBanner = ticketBanner;
	}
	
	public String[] getTicketBanners() {
		return StringUtils.split(ticketBanner, ",");
	}
	
	public String[] getTicketBannerUrls() {
		String[] attachs = getTicketBanners();
		if(attachs != null) {
			for(int i=0; i<attachs.length; i++) {
				attachs[i] = QiniuUtils.getResUrl(attachs[i]);
			}
		}
		return attachs;
	}

	public String getFacilities() {
		return facilities;
	}

	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}
	
	public String[] getFacilitie() {
		return StringUtils.split(facilities, ",");
	}
	
	public String[] getFacilitiesUrls() {
		String[] attachs = getFacilitie();
		if(attachs != null) {
			for(int i=0; i<attachs.length; i++) {
				attachs[i] = QiniuUtils.getResUrl(attachs[i]);
			}
		}
		return attachs;
	}

	public String getTicketTel() {
		if(StringUtils.isBlank(ticketTel)){
			ticketTel = tel;
		}
		return ticketTel;
	}

	public void setTicketTel(String ticketTel) {
		this.ticketTel = ticketTel;
	}
}
