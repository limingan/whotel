package com.whotel.common.entity;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.util.RepoUtil;

@Entity(noClassnameStored=true)
public class Region extends BaseEntity {

	private static final long serialVersionUID = 6619630507796756034L;
	
	private Integer regionId;
	
	private String code;
	
	private String name;
	
	private Integer parentId;
	
	private Integer level;
	
	private Integer displayOrder;

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
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


	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	public Region getParent() {
		return RepoUtil.getRegionByRegionId(parentId);
	}

	public Integer[] getRegionIds() {
		Integer[] regionIds = new Integer[3];
		if(this != null) {
			if(this.getLevel() == 3) {
				regionIds[2] = this.getRegionId();
				Region city = this.getParent();
				regionIds[1] = city.getRegionId();
				regionIds[0] = city.getParent().getRegionId();
			} else if(this.getLevel() == 2) {
				regionIds[1] = this.getRegionId();
				regionIds[0] = this.getParent().getRegionId();
			} else if(this.getLevel() == 1) {
				regionIds[0] = this.getRegionId();
			}
		}
		return regionIds;
	}
	
	public String getRegionPath() {
		String name = "";
		if(this != null) {
			name = this.getName();
			Region parent = this.getParent();
			do {
				if(parent != null) {
					name = parent.getName() + " " + name;
					parent = parent.getParent();
				}
			} while(parent != null);
		}
		return name;
	}
}
