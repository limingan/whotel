package com.whotel.common.base.controller;

import com.whotel.common.dao.dbutil.DataPageUtil;

/**
 * 分页支持控制基类
 * @author 冯勇
 */
public class BasePageController extends BaseController {

	private int page = 1;

	private int limit = 20;

	private int start;
	
	private String pageNavigation;
	
	/**
	 * 创建分页工具类
	 * @param t
	 * @return
	 */
	public <T> DataPageUtil<T> createDataPageUtil(Class<T> t) {
		DataPageUtil<T> dataPageUtil = new DataPageUtil<T>();
		dataPageUtil.setPage(getPage());
		dataPageUtil.setLimit(getLimit());
		return dataPageUtil;
	}
	
	public <T> DataPageUtil<T> createDataPageUtil(Integer page, Integer limit, Class<T> t) {
		DataPageUtil<T> dataPageUtil = new DataPageUtil<T>();
		if(page != null) {
			dataPageUtil.setPage(page);
		}
		
		if(limit != null) {
			dataPageUtil.setLimit(limit);
		}
		return dataPageUtil;
	}


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getPageNavigation() {
		return pageNavigation;
	}

	public void setPageNavigation(String pageNavigation) {
		this.pageNavigation = pageNavigation;
	}
}
