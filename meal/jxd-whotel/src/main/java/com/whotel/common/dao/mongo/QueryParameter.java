package com.whotel.common.dao.mongo;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 封装分页和排序查询请求参数.
 * 
 * @author
 */
@SuppressWarnings("serial")
public class QueryParameter implements Serializable {

	public static final String ASC = "asc";
	public static final String DESC = "desc";
	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final boolean DEFAULT_AUTO_COUNT = true;
	/**
	 * 排序器
	 */
	private Set<Order> orders = new LinkedHashSet<Order>(0);

	/**
	 * 过滤器
	 */
	private Map<String, Object> filters = new LinkedHashMap<String, Object>();

	protected int pageNo = 1;
	protected int pageSize = DEFAULT_PAGE_SIZE;
	protected boolean autoCount = DEFAULT_AUTO_COUNT;

	/**
	 * 获得每页的记录数量,无默认值.
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize > 0) {
			this.pageSize = pageSize;
		}
	}

	/**
	 * 是否已设置每页的记录数量.
	 */
	public boolean isPageSizeSetted() {
		return pageSize > -1;
	}

	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		if (pageNo > 0) {
			this.pageNo = pageNo;
		}
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
	 */
	public int getFirst() {
		if (pageNo < 1 || pageSize < 1)
			return -1;
		else
			return ((pageNo - 1) * pageSize);
	}

	/**
	 * 是否已设置第一条记录记录在总结果集中的位置.
	 */
	public boolean isFirstSetted() {
		return (pageNo > 0 && pageSize > 0);
	}

	/**
	 * 是否已设置过滤条件
	 */
	public boolean isFilterSetted() {
		return filters != null && !filters.isEmpty();
	}

	/**
	 * 是否已设置排序字段.
	 */
	public boolean isOrderBySetted() {
		return orders != null && !orders.isEmpty();
	}

	/**
	 * 获得排序.
	 */
	public Set<Order> getOrders() {
		return orders;
	}

	/**
	 * 设置排序.
	 */
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	/**
	 * 增加排序
	 * 
	 * @param order
	 */
	public void addOrder(Order order) {
		this.orders.add(order);
	}

	public void addOrder(String propertyName, boolean ascending) {
		if (ascending) {
			this.orders.add(Order.asc(propertyName));
		} else {
			this.orders.add(Order.desc(propertyName));
		}
	}

	/**
	 * 是否自动获取总页数,默认为false. 注意本属性仅于query by Criteria时有效,query by HQL时本属性无效.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	/**
	 * @return the filter
	 */
	public Map<String, Object> getFilters() {
		return filters;
	}

	/**
	 * @param filters
	 *            the filters to set
	 */
	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public boolean isFiltered() {
		return filters != null && filters.size() > 0;
	}

	/**
	 * 增加过滤
	 * 
	 */
	public void addFilter(String propertyName, Object propertyValue) {
		this.filters.put(propertyName, propertyValue);
	}
}