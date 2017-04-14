package com.whotel.card.entity;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.card.enums.PrizeType;
import com.whotel.common.entity.BaseEntity;

@Entity(noClassnameStored=true)
public class Prize  extends BaseEntity {

	public Prize(){}
	
	public Prize(Integer serialNumber){
		this.serialNumber = serialNumber;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String activityId;
	
	private String name;//奖品名称
	
	private String code;//代码
	
	private Integer number;//奖品数量
	
	private Integer serialNumber;//奖品序号
	
	private Integer count=0;//记录抽中的次数
	
	private Float winningRate;//中奖率
	
	private PrizeType type;
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	public PrizeType getType() {
		return type;
	}
	public void setType(PrizeType type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Float getWinningRate() {
		return winningRate;
	}
	public void setWinningRate(Float winningRate) {
		this.winningRate = winningRate;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public Integer getRestAmount(){
		return number-count;
	} 
	public Integer getRobRate(){
		if(number == 0){
			return 100;
		}
		return count*100/number;
	} 
}
