package com.weixin.core.bean;

import java.util.List;

public class MaterialBatch {
    
	private int total_count;
	private int item_count;
	
	private List<MaterialItem> item;

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public int getItem_count() {
		return item_count;
	}

	public void setItem_count(int item_count) {
		this.item_count = item_count;
	}

	public List<MaterialItem> getItem() {
		return item;
	}

	public void setItem(List<MaterialItem> item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "MaterialBatch [total_count=" + total_count + ", item_count="
				+ item_count + ", item=" + item + "]";
	}
}
