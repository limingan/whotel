package com.whotel.card.entity;

import java.util.Date;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.util.QiniuUtils;
import com.whotel.common.util.RepoUtil;
import com.whotel.front.entity.WeixinFan;

/**
 * 奖品发放记录
 * @author 柯鹏程
 *
 */
@Entity(noClassnameStored=true)
public class PrizeRecord extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String companyId;//公司id

	private String nickName;//微信名称
	
	private String openId;//微信id
	
	private String activityId;//活动id
	
	private String activityName;//活动名称

	private String prizeName;//奖品名称
	
	private String prizeCode;//奖品代码
	
	private String prizeId;//奖品id
	
	private String pic;//优惠券或红包或实体奖品的图片
	
	private Date date;//获奖时间
	
	private Boolean isOk;
	
	private String remark;

	private Float prizeValue;//红包价值

	public Float getPrizeValue() {
		return prizeValue;
	}

	public void setPrizeValue(Float prizeValue) {
		this.prizeValue = prizeValue;
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

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public WeixinFan getWeixinFan(){
		return RepoUtil.getWeixinFan(openId);
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(Boolean isOk) {
		this.isOk = isOk;
	}

	public String getPrizeCode() {
		return prizeCode;
	}

	public void setPrizeCode(String prizeCode) {
		this.prizeCode = prizeCode;
	}

	public String getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(String prizeId) {
		this.prizeId = prizeId;
	}
	
}
