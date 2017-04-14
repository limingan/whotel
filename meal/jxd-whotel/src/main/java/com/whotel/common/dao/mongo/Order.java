package com.whotel.common.dao.mongo;

import java.io.Serializable;

/**
 * 属性排序
 * 
 * @author
 */
@SuppressWarnings("serial")
public class Order implements Serializable {
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	private boolean asc;
	private String name;

	/**
	 * Constructor for Order.
	 */
	private Order(String name, boolean asc) {
		this.name = name;
		this.asc = asc;
	}

	/**
	 * @return the asc
	 */
	public boolean isAsc() {
		return asc;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Ascending order
	 * 
	 * @param name
	 * @return Order
	 */
	public static Order asc(String name) {
		return new Order(name, true);
	}

	/**
	 * Descending order
	 * 
	 * @param name
	 * @return Order
	 */
	public static Order desc(String name) {
		return new Order(name, false);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (asc ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Order other = (Order) obj;
		if (name == null) {
			if (other.name != null)
				return false;
			if (asc != other.asc)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name + ' ' + (asc ? ASC : DESC);
	}

}
