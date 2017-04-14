package com.whotel.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.whotel.common.enums.FilterModel;
import com.whotel.common.enums.FilterRelation;

public class FilterDto  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String filedName;
	
	private Object filedValue;
	
	private FilterModel model;
	
	private FilterRelation filterRelation = FilterRelation.END;
	
	private List<FilterDto> filterDtos = new ArrayList<FilterDto>();
	
	public FilterDto() {}
	
	
	public FilterDto(String filedName, FilterModel model, Object filedValue) {
		super();
		this.filedName = filedName;
		this.model = model;
		this.filedValue = filedValue;
	}
	
	public FilterDto(String filedName, FilterModel model) {
		this.filedName = filedName;
		this.model = model;
	}
	
	public void or(FilterDto filterDto) {
		filterDto.setFilterRelation(FilterRelation.OR);
		filterDtos.add(filterDto);
	}
	
	public void end(FilterDto filterDto) {
		filterDto.setFilterRelation(FilterRelation.END);
		filterDtos.add(filterDto);
	}

	public String getFiledName() {
		return filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public FilterModel getModel() {
		return model;
	}

	public void setModel(FilterModel model) {
		this.model = model;
	}

	public FilterRelation getFilterRelation() {
		return filterRelation;
	}

	public void setFilterRelation(FilterRelation filterRelation) {
		this.filterRelation = filterRelation;
	}
	
	public Object getFiledValue() {
		return filedValue;
	}

	public void setFiledValue(Object filedValue) {
		this.filedValue = filedValue;
	}

	public List<FilterDto> getFilterDtos() {
		return filterDtos;
	}


	public void setFilterDtos(List<FilterDto> filterDtos) {
		this.filterDtos = filterDtos;
	}


	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(obj instanceof FilterDto) {
			if((filedName != null && filedName.equals(((FilterDto) obj).getFiledName())) 
					&& (model != null && model.equals(((FilterDto) obj).getModel()))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return filedName.hashCode() + model.hashCode();
	}
}
