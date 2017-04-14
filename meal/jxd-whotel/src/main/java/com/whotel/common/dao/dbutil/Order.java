package com.whotel.common.dao.dbutil;

/**
 * 排序工具类
 * @author 冯勇
 *
 */
public class Order {
	
	private String filedName;
	
	private boolean isDesc;
	
	public Order() {}

	private Order(String filedName, boolean isDesc) {
		super();
		this.filedName = filedName;
		this.isDesc = isDesc;
	}
	
	/**
	 * 根据指定字段升序排列
	 * @param filedName
	 * @return order对象
	 */
	public static Order asc(String filedName) {
		return new Order(filedName, false);
	}
	
	/**
	 * 根据指定字段降序排列
	 * @param filedName
	 * @return order对象
	 */
	public static Order desc(String filedName) {
		return new Order(filedName, true);
	}

	public String getFiledName() {
		return filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public boolean isDesc() {
		return isDesc;
	}

	public void setDesc(boolean isDesc) {
		this.isDesc = isDesc;
	}
}
