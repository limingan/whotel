package com.whotel.common.dao.dbutil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whotel.common.base.Constants;
import com.whotel.common.dto.FilterDto;
import com.whotel.common.enums.FilterModel;

/**
 * page util
 * @author fengyong
 *
 */
public class DataPageUtil<T> {

	private List<T> result;

	private List<FilterDto> filter;

	private Order[] orders = null;

	private int page = 1;

	private int limit = 20;

	private int rowCount;

	private int rowNumOffset = 0;
	
	private int pageCount;

	public DataPageUtil() {
	}

	public DataPageUtil(int limit, int rowCount) {
		this.limit = limit < 1 ? Constants.DFT_CONSOLE_PAGE_SIZE : limit;
		this.rowCount = rowCount;
	}

	public DataPageUtil(Integer page, Integer limit, int dftLimit) {
		this.page = page == null ? 1 : page;
		this.limit = limit == null || limit < 1 ? dftLimit : limit;
	}

	/**
	 * data strat number
	 * @return
	 */
	public int getStart() {
		return (page - 1) * limit;
	}

	/**
	 * data end number
	 * @return
	 */
	public int getEnd() {
		return page == getPageCount() ? rowCount : page * limit;
	}

	public int getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page == null ? 1 : page;
	}

	public int getLimit() {
		return limit;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageCount() {
		pageCount = (getRowCount() + limit - 1) / limit;
		return pageCount;
	}

	public int getRowCount() {
		return rowCount - rowNumOffset;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	@JsonIgnore
	public List<FilterDto> getFilter() {
		return filter;
	}

	public void setFilter(List<FilterDto> filter) {
		this.filter = filter;
	}

	public DataPageUtil<T> addOrder(Order order) {
		if(orders != null) {
			ArrayUtils.add(orders, order);
		} else {
			orders = new Order[]{order};
		}
		return this;
	}

	@JsonIgnore
	public Order[] getOrders() {
		return orders;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		int pageCount = getPageCount();
		if (page < 0) {
			setPage(1);
		} else if (pageCount != 0 && page > pageCount) {
			setPage(pageCount);
		}
	}

	public int getRowNumOffset() {
		return rowNumOffset;
	}

	public void setRowNumOffset(int rowNumOffset) {
		this.rowNumOffset = rowNumOffset;
	}

	public DataPageUtil<T> addFilter(String fieldName, FilterModel model, Object fieldValue) {
		if (this.filter == null) {
			this.filter = new ArrayList<>();
		}
		FilterDto one = new FilterDto(fieldName, model, fieldValue);
		this.filter.add(one);
		return this;
	}
	
	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (page + 1 <= getPageCount());
	}

	/**
	 * 返回下页的页号,序号从1开始.
	 */
	public int getNextPage() {
		if (isHasNext())
			return page + 1;
		else
			return page;
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (page - 1 >= 1);
	}

	/**
	 * 返回上页的页号,序号从1开始.
	 */
	public int getPrePage() {
		if (isHasPre())
			return page - 1;
		else
			return page;
	}
	
	@JsonIgnore
	public String getAdminPageNavigation() {

		StringBuffer displayInfo = new StringBuffer();
		displayInfo.append("<div class='col-md-5 col-sm-12'>");
		displayInfo.append("<div class='dataTables_info'>");
		if (getRowCount() == 0) {
			displayInfo.append("总共" + getRowCount() + "条");
		} else {
			int start = getStart();
			int end = getEnd();
			displayInfo.append("显示  " + (start + 1) + " to " + end + " 总共" + getRowCount() + "条");
		}
		displayInfo.append("</div></div>");
		if (getRowCount() > 0) {
			displayInfo.append("<div class='col-md-7 col-sm-12'>");
			displayInfo.append("<div class='dataTables_paginate paging_bootstrap'>");
			displayInfo.append("<div class='pagination' style='visibility: visible;'>");
			if (getPage() == 1) {
				displayInfo.append("<li class='prev disabled'>");
			} else {
				displayInfo.append("<li class='prev'>");
			}
			displayInfo.append("<a href=\"javascript:pageInfo('" + getPrePage() + "');\" title='Prev'>");
			displayInfo.append("<i class='fa fa-angle-left'></i></a></li>");

			int sp = 1;
			int ep = getPageCount() > 10 ? 10 : getPageCount();

			if (getPage() > 6) {
				sp = getPage() - 5;
				ep = sp + 9;
				if (ep > getPageCount()) {
					ep = getPageCount();
				}
			}
			for (int i = sp; i <= ep; i++) {
				if (i == getPage()) {
					displayInfo.append("<li class='active'>");
				} else {
					displayInfo.append("<li>");
				}
				displayInfo.append("<a href=\"javascript:pageInfo('" + i + "');\">" + i + "</a></li>");
			}

			if (getPage() == getPageCount()) {
				displayInfo.append("<li class='next disabled'>");
			} else {
				displayInfo.append("<li class='next'>");
			}
			displayInfo.append("<a href=\"javascript:pageInfo('" + getNextPage() + "')\" title='Next'>");
			displayInfo.append("<i class='fa fa-angle-right'></i></a></li>");
			displayInfo.append("</ul></div></div>");
		}
		return displayInfo.toString();
	}

	@JsonIgnore
	public String getPageNavigation() {
		StringBuffer displayInfo = new StringBuffer();
		int pageCount = getPageCount();
		if (page == 0 || rowCount == 0) {
			displayInfo.append("<li>没有分页的信息!</li>");
		} else {
			if (page > 1) {
				displayInfo.append("<li><a href='javascript:pageInfo(\"" + getPrePage() + "\");' class='prev prePage'><</a></li>");
			} else {
				displayInfo.append("<li><a href='javascript:void(0)' class='prev disable'><</a></li>");
			}

			int startIndex = page - 2; // 假定左边界
			int endIndex = page + 2; // 假定右边界

			if (startIndex < 1) {
				startIndex = 1; // 微调
			}
			if (endIndex > pageCount) {
				endIndex = pageCount;
			}

			for (int i = startIndex; i <= endIndex; i++) {
				if (i == page) {
					displayInfo.append("<li class='cur'><a href='javascript:void(0)' class='current'>" + i + "</a></li>");
				} else {
					displayInfo.append("<li><a href='javascript:pageInfo(\"" + i + "\");' class='thisPage'>" + i + "</a></li>");
				}
			}
			if (page < pageCount) {
				displayInfo.append("<li><a href='javascript:pageInfo(\"" + getNextPage() + "\");' class='next nextPage'>></a></li>");
			} else {
				displayInfo.append("<li><a href='javascript:void(0)' class='next disable'>></a></li>");
			}
			/*
			displayInfo.append("<span> " + page + "/" + pageCount + " 总共 " + rowCount
					+ " 条</span><span><input type='text' style='width: 30px' class='specifyPage'/></span>");
			displayInfo.append("<a href='javascript:void(0)' class='confirmSpecify'>GO</a>");
			*/
		}
		return displayInfo.toString();
	}

}
