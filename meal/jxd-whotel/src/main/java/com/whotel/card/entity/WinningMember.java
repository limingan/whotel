package com.whotel.card.entity;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.card.enums.ActivityType;
import com.whotel.card.enums.PrizeType;
import com.whotel.common.entity.BaseEntity;
import com.whotel.common.util.QiniuUtils;
import com.whotel.common.util.RepoUtil;
import com.whotel.company.entity.Company;
import com.whotel.front.entity.WeixinFan;

/**
 * 获奖会员
 * @author 柯鹏程
 *
 */
@Entity(noClassnameStored=true)
public class WinningMember extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String companyId;//公司id

	private String memberName;//会员名称
	
	private String memberId;//会员id
	
	private String nickName;//微信名称
	
	private String openId;//微信id
	
	private String activityId;//活动id
	
	private String activityName;//活动名称

	private ActivityType activityType;//活动类型
	
	private String prizeCode;//优惠券代码或红包id或实体奖品id
	
	private String prizeName;//奖品名称
	
	private String pic;//优惠券或红包或实体奖品的图片
	
	private PrizeType prizeType;
	
	private Date date;//获奖时间
	
	private Boolean isOk;//发送成功
	
	private String remark;
	
	private String orderSn;//快递单号
	
	private String courierCompany;//快递商家
	
	private String memberMobile;//会员手机
	
	private String recordId;//派劵记录id
	
	private String avatar;
	
	private Long ranking;
	
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getPicUrl() {
		return QiniuUtils.getResUrl(pic);
	}

	public String getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}

	public String getCourierCompany() {
		return courierCompany;
	}

	public void setCourierCompany(String courierCompany) {
		this.courierCompany = courierCompany;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
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

	public String getPrizeCode() {
		return prizeCode;
	}

	public void setPrizeCode(String prizeCode) {
		this.prizeCode = prizeCode;
	}

	public PrizeType getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(PrizeType prizeType) {
		this.prizeType = prizeType;
	}

	public Boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(Boolean isOk) {
		this.isOk = isOk;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	public WeixinFan getWeixinFan(){
		return RepoUtil.getWeixinFan(openId);
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Long getRanking() {
		return ranking;
	}

	public void setRanking(Long ranking) {
		this.ranking = ranking;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}
