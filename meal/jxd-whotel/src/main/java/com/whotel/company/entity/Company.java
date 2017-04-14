package com.whotel.company.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import com.whotel.common.entity.Region;
import com.whotel.common.entity.UnDeletedEntity;
import com.whotel.common.util.QiniuUtils;
import com.whotel.common.util.RepoUtil;
import com.whotel.company.enums.ModuleType;

/**
 * 商户信息实体
 * @author 冯勇
 *
 */
@Entity(noClassnameStored=true)
public class Company extends UnDeletedEntity {

	private static final long serialVersionUID = -5663308053619538545L;
	
	private String name;     //名称
	@Indexed(unique = true)
	private String code;     //编码
	
	private String logo;     //logo
	
	private Integer regionId;
	
	private String addr;     //地址
	
	private String coord;    //坐标
	
	private String tel;      //电话
	
	private String fax;      //传真
	
	private String zipcode;  //邮政编码
	
	private String email;    //公司邮件
	
	private String website;  //官网
	
	private String descr;    //说明

	private String qrcode;   //二维码
	
	private Date validTime;  //有效时间
	
	private Boolean valid;   //是否有效
	
	private Boolean group;   //是否是集团
	
	private String outletCode;
	
	private Date createTime;
	
	private Date updateTime;
	
	private String adminAccount;//商户管理员账号
	
	private String adminPwd;//商户管理员密码
	
	private String createPerson;//商户创建人
	
	private String englishName;//英文名称
	
	private String theme="";//主题
	
	private Boolean multipleMbr;//允许多个会员
	
	@Embedded
	private List<ModuleType> moduleTypes;//权限
	
	public List<ModuleType> getModuleTypes() {
		return moduleTypes;
	}

	public void setModuleTypes(List<ModuleType> moduleTypes) {
		this.moduleTypes = moduleTypes;
	}

	@Embedded
	private List<CompanyModule> modules;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String getLogoUrl() {
		return QiniuUtils.getResUrl(logo);
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	
	public String getQrcodeUrl() {
		return QiniuUtils.getResUrl(qrcode);
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

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getOutletCode() {
		return outletCode;
	}

	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}

	public Boolean getGroup() {
		return group;
	}

	public void setGroup(Boolean group) {
		this.group = group;
	}

	public Region getRegion() {
		return RepoUtil.getRegionByRegionId(regionId);
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
	
	public String[] getCoords() {
		String[] coords = new String[]{"", ""};
		if(StringUtils.isNotBlank(coord)) {
			coords = coord.split(",");
		}
		return coords;
	}

	public String getAdminAccount() {
		return adminAccount;
	}

	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public List<CompanyModule> getModules() {
		return modules;
	}

	public void setModules(List<CompanyModule> modules) {
		this.modules = modules;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Boolean getMultipleMbr() {
		return multipleMbr;
	}

	public void setMultipleMbr(Boolean multipleMbr) {
		this.multipleMbr = multipleMbr;
	}
}
