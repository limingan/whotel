package com.whotel.admin.entity;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import com.whotel.common.entity.BaseEntity;

@Entity(noClassnameStored=true)
public class SysRole extends BaseEntity {

	private static final long serialVersionUID = 667766050764372009L;

	private String name;
	
	@Embedded
	private List<SysModule> modules;
	
	private String descr;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public List<SysModule> getModules() {
		return modules;
	}
	public void setModules(List<SysModule> modules) {
		this.modules = modules;
	}
	
	@Transient
	public List<Integer> moduleIds;
	
	@Transient
	public String moduleNames;
	
	@Transient
	private List<String> linkUrls;
	
	public List<Integer> getModuleIds() {
		return moduleIds;
	}
	public void setModuleIds(List<Integer> moduleIds) {
		this.moduleIds = moduleIds;
	}
	public String getModuleNames() {
		return moduleNames;
	}
	public void setModuleNames(String moduleNames) {
		this.moduleNames = moduleNames;
	}
	
	public List<String> getLinkUrls() {
		if(modules != null) {
			List<String> linkUrls = new ArrayList<String>();
			for(SysModule module:modules) {
				if(module != null) {
					linkUrls.add(module.getLinkUrl());
				}
			}
			return linkUrls;
		}
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj != null && (obj instanceof SysRole)) {
			return ((SysRole)obj).getId().equals(getId());
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		if (getId() == null) {
			return super.hashCode();
		} else {
			return this.getId().hashCode();
		}
	}
}
