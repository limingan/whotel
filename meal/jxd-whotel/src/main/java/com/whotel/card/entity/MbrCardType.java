package com.whotel.card.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.util.QiniuUtils;

@Entity(noClassnameStored=true)
public class MbrCardType extends BaseEntity {

	private static final long serialVersionUID = 6258887558915668430L;
	
	private String companyId; //公司id
	
	@Indexed(unique = true)
	private String code;      //会员卡类型编码
	private String name;      //会员卡类型名称
	private String pic;    //图片
	
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getPicUrl(){
		return QiniuUtils.getResUrl(pic);
	}
}
