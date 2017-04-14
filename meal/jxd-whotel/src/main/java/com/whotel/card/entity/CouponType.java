package com.whotel.card.entity;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.UnDeletedEntity;
import com.whotel.common.util.QiniuUtils;
import com.whotel.company.enums.ModuleType;

@Entity(noClassnameStored=true)
public class CouponType extends UnDeletedEntity {

	private static final long serialVersionUID = 6258887558915668430L;
	
	private String companyId;
	
	private String name;
	
	private String code;
	
	private String pic;
	
	private Float useMoney;//使用金额
	
	@Embedded
	private List<ModuleType> moduleTypes;//权限
	
	public List<ModuleType> getModuleTypes() {
		return moduleTypes;
	}

	public void setModuleTypes(List<ModuleType> moduleTypes) {
		this.moduleTypes = moduleTypes;
	}
	
	public Boolean hasModuleTypes(ModuleType moduleType){
		if(moduleTypes != null){
			return moduleTypes.contains(moduleType);
		}
		return false;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

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

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getPicUrl() {
		return QiniuUtils.getResUrl(pic);
	}

	public Float getUseMoney() {
		return useMoney;
	}

	public void setUseMoney(Float useMoney) {
		this.useMoney = useMoney;
	}
}
