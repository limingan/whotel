package com.whotel.card.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.whotel.card.entity.Department;
import com.whotel.common.util.RepoUtil;

public class RecommendFanStatisticsVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String departmentId;//部门id
	
	private String hotelName;//酒店名称
	
	private Float thisYearTask;//年任务
	
	private Integer dayCount = 0;
	
	private Integer monthCount = 0;
	
	private Integer yearCount = 0;
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getDayCount() {
		return dayCount;
	}

	public void setDayCount(Integer dayCount) {
		this.dayCount = dayCount;
	}

	public Integer getMonthCount() {
		return monthCount;
	}

	public void setMonthCount(Integer monthCount) {
		this.monthCount = monthCount;
	}

	public Integer getYearCount() {
		return yearCount;
	}

	public void setYearCount(Integer yearCount) {
		this.yearCount = yearCount;
	}
	
	public Department getDepartment(){
		Department dept = new Department();
		if(StringUtils.isNotBlank(departmentId)){
			dept = RepoUtil.getDepartmentById(departmentId);
		}else{
			dept.setHotelName("其他");
			dept.setName("其他");
		}
		return dept;
	}
	
	public String getYearCompleteRate(){
//		Department dept = getDepartment();
//		if(dept != null){
//			if(dept.getThisYearTask()!=null && dept.getThisYearTask()!=0){
//				Float yearCompleteRate = yearCount*100/dept.getThisYearTask();
//				return String.format("%.2f", yearCompleteRate)+"%";
//			}
//		}
//		return "100.00%";
		if(thisYearTask!=null){
			if(thisYearTask!=0){
				Float yearCompleteRate = yearCount*100/thisYearTask;
				return String.format("%.2f", yearCompleteRate)+"%";
			}
		}
		return "100.00%";
	}

	public Float getThisYearTask() {
		return thisYearTask;
	}

	public void setThisYearTask(Float thisYearTask) {
		this.thisYearTask = thisYearTask;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	
}
