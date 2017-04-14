package com.whotel.common.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whotel.common.dto.FilterDto;
import com.whotel.common.enums.FilterModel;

/**
 * 封装分页和排序查询的结果.
 * 
 * 
 * @param <T> - Page中的记录类型.
 * 
 * @author
 */
@SuppressWarnings("serial")
public class Page<T> extends QueryParameter {

	private List<T> result = new ArrayList<T>(0);
	
	private List<FilterDto> filter;

	private long totalCount = 0;
	private static final int MAX_PAEE_SIZE = 200;

	public Page() {
	}

	public Page(int pageSize) {
		setPageSize(pageSize);
	}

	public Page(int pageSize, boolean autoCount) {
		setPageSize(pageSize);
		setAutoCount(autoCount);
	}

	/**
	 * 获得每页的记录数量,无默认值.
	 */
	@Override
	public int getPageSize() {
		if (pageSize > MAX_PAEE_SIZE)
			return MAX_PAEE_SIZE;
		return pageSize;
	}
	/**
	 * 页内的数据列表.
	 */
	public List<T> getResult() {
		return result;
	}

	@SuppressWarnings("unchecked")
	public <M extends T> void setResult(List<M> result) {
		this.result = (List<T>) result;
	}

	/**
	 * 总记录数.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 计算总页数.
	 */
	public int getTotalPages() {
		if (totalCount == 0)
			return 0;

		int count = (int) (totalCount / pageSize);
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 返回下页的页号,序号从1开始.
	 */
	public int getNextPage() {
		if (isHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 返回上页的页号,序号从1开始.
	 */
	public int getPrePage() {
		if (isHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}
	
	/**
	 * data strat number
	 * @return
	 */
	public long getStart() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * data end number
	 * @return
	 */
	public long getEnd() {
		return pageNo == getTotalPages() ? getTotalCount() : pageNo * pageSize;
	}
	
	@JsonIgnore
	public List<FilterDto> getFilter() {
		return filter;
	}

	public void setFilter(List<FilterDto> filter) {
		this.filter = filter;
	}
	
	public void addFilter(FilterDto filterDto) {
		if(filterDto != null) {
			filter = getFilter();
			if(filter == null) {
				filter = new ArrayList<FilterDto>();
			}
			filter.add(filterDto);
		}
	}
	
	public Page<T> addFilter(String fieldName, FilterModel model, Object fieldValue) {
		if (this.filter == null) {
			this.filter = new ArrayList<>();
		}
		FilterDto one = new FilterDto(fieldName, model, fieldValue);
		this.filter.add(one);
		return this;
	}
	
public String getPageNavigation() {
		
		StringBuffer displayInfo = new StringBuffer();
		displayInfo.append("<div class='col-md-8 col-sm-12'>");
		displayInfo.append("<div >");
		if (getTotalCount() == 0) {
			displayInfo.append("总共" + getTotalCount() + "条");
		} else {
			long start = getStart();
			long end = getEnd();
			//displayInfo.append("显示  " + (start + 1) + " to " + end + " 总共" + getTotalCount() + "条");
			displayInfo.append("<span id='ceshi'>第&nbsp;</span>");
//			displayInfo.append("共"+getTotalPages()+"页&nbsp;|&nbsp;到第<input type='text' id='curPage' value="+getPageNo()+
//					" size='2' style='width: 28px;height: 21px;border-style: solid;border-color: #aaa #ddd #ddd #aaa;text-align: center;font-family: verdana;'>页&nbsp;"
//					+ "<input type='button' class='prev' value='确定' onclick=\"javascript:toPageNo('"+getTotalPages()+"');\" >&nbsp;|每页 " );
//			displayInfo.append("<select id='select' name='select' onchange=\"javascript:showCount();\">");
			displayInfo.append( "&nbsp;页&nbsp;");
			displayInfo.append( "<input type='button' class='prev' value='确定' onclick=\"javascript:toPageNo('"+getTotalPages()+"');\" >");
			displayInfo.append( "<input type='hidden' id='totalPages' value="+getTotalPages()+">");
			displayInfo.append( "&nbsp;共"+getTotalPages()+"页" );
			displayInfo.append( "&nbsp;|每页 " );
			displayInfo.append("<select id='select' name='select' onchange=\"javascript:showCount('"+getTotalCount()+"');\">");
			
			ArrayList<Integer> showPageCount = new ArrayList<Integer>();
			showPageCount.add(10);
			showPageCount.add(20);
			showPageCount.add(50);
			showPageCount.add(100);
			showPageCount.add(200);
			for(int i=0;i<showPageCount.size();i++){
				if(getPageSize()==showPageCount.get(i)){
					displayInfo.append( "<option value='"+showPageCount.get(i)+"' selected='selected'>"+showPageCount.get(i)+"</option>");
				}else{
					displayInfo.append( "<option value='"+showPageCount.get(i)+"' >"+showPageCount.get(i)+"</option>");
				}
			}
			displayInfo.append("</select>");
			displayInfo.append("条|共 " + getTotalCount() + "条记录");
		}
		displayInfo.append("</div></div>");
		if (getTotalCount() > 0) {
			displayInfo.append("<div class='col-md-7 col-sm-12' style='height:50px; >");
			displayInfo.append("<div class='dataTables_paginate paging_bootstrap'>");
			displayInfo.append("<div class='pagination' style='visibility: visible; margin-bottom: -13px;'>");
			if (getPageNo() == 1) {
				displayInfo.append("<li class='prev disabled'>");
			} else {
				displayInfo.append("<li class='prev'>");
			}
			displayInfo.append("<a href=\"javascript:pageInfo('" + getPrePage() + "');\" title='Prev' >");
			displayInfo.append("<i class='fa fa-angle-left' style='width: 0px;height: 0px;'></i></a></li>");

			int sp = 1;
			int ep = getTotalPages() > 10 ? 10 : getTotalPages();

			if (getPageNo() > 6) {
				sp = getPageNo() - 5;
				ep = sp + 9;
				if (ep > getTotalPages()) {
					ep = getTotalPages();
				}
			}
			for (int i = sp; i <= ep; i++) {
				if (i == getPageNo()) {
					displayInfo.append("<li class='active'>");
				} else {
					displayInfo.append("<li>");
				}
				//displayInfo.append("<a href=\"javascript:pageInfo('" + i + "');\">" + i + "</a></li>");
				
			}
			
			displayInfo.append( "<li ><a style='padding:0px;margin:0px 5px 0px 5px;'><input type='text' id='curPage' value="+getPageNo()+
					" size='1' style='width: 38px;height: 31px;border-style: solid;border-color: #aaa #ddd #ddd #aaa;text-align: center;font-family: verdana; '></a></li>");

			if (getPageNo() == getTotalPages()) {
				displayInfo.append("<li class='next disabled'>");
			} else {
				displayInfo.append("<li class='next'>");
			}
			displayInfo.append("<a href=\"javascript:pageInfo('" + getNextPage() + "')\" title='Next'>");
			displayInfo.append("<i class='fa fa-angle-right' style='width: 0px;height: 0px;'></i></a></li>");
			displayInfo.append("</ul></div></div>");
		}
		return displayInfo.toString();
	}
	
//public String getPageNavigation() {
//		
//		StringBuffer displayInfo = new StringBuffer();
//		displayInfo.append("<div class='col-md-5 col-sm-12'>");
//		displayInfo.append("<div class='dataTables_info'>");
//		if (getTotalCount() == 0) {
//			displayInfo.append("总共" + getTotalCount() + "条");
//		} else {
//			long start = getStart();
//			long end = getEnd();
//			//displayInfo.append("显示  " + (start + 1) + " to " + end + " 总共" + getTotalCount() + "条");
//			displayInfo.append("共"+getTotalPages()+"页&nbsp;|&nbsp;到第<input type='text' id='curPage' value="+getPageNo()+
//					" size='2' style='width: 28px;height: 21px;border-style: solid;border-color: #aaa #ddd #ddd #aaa;text-align: center;font-family: verdana;'>页&nbsp;"
//					+ "<input type='button' class='prev' value='确定' onclick=\"javascript:toPageNo('"+getTotalPages()+"');\" >&nbsp;|每页 " );
//			displayInfo.append("<select id='select' name='select' onchange=\"javascript:showCount();\">");
//			ArrayList<Integer> showPageCount = new ArrayList<Integer>();
//			showPageCount.add(10);
//			showPageCount.add(20);
//			showPageCount.add(50);
//			showPageCount.add(100);
//			showPageCount.add(200);
//			for(int i=0;i<showPageCount.size();i++){
//				if(getPageSize()==showPageCount.get(i)){
//					displayInfo.append( "<option value='"+showPageCount.get(i)+"' selected='selected'>"+showPageCount.get(i)+"</option>");
//				}else{
//					displayInfo.append( "<option value='"+showPageCount.get(i)+"' >"+showPageCount.get(i)+"</option>");
//				}
//			}
//			displayInfo.append("</select>");
//			displayInfo.append("条|共 " + getTotalCount() + "条");
//		}
//		displayInfo.append("</div></div>");
//		if (getTotalCount() > 0) {
//			displayInfo.append("<div class='col-md-7 col-sm-12'>");
//			displayInfo.append("<div class='dataTables_paginate paging_bootstrap'>");
//			displayInfo.append("<div class='pagination' style='visibility: visible;'>");
//			if (getPageNo() == 1) {
//				displayInfo.append("<li class='prev disabled'>");
//			} else {
//				displayInfo.append("<li class='prev'>");
//			}
//			displayInfo.append("<a href=\"javascript:pageInfo('" + getPrePage() + "');\" title='Prev'>");
//			displayInfo.append("<i class='fa fa-angle-left'></i></a></li>");
//
//			int sp = 1;
//			int ep = getTotalPages() > 10 ? 10 : getTotalPages();
//
//			if (getPageNo() > 6) {
//				sp = getPageNo() - 5;
//				ep = sp + 9;
//				if (ep > getTotalPages()) {
//					ep = getTotalPages();
//				}
//			}
//			for (int i = sp; i <= ep; i++) {
//				if (i == getPageNo()) {
//					displayInfo.append("<li class='active'>");
//				} else {
//					displayInfo.append("<li>");
//				}
//				displayInfo.append("<a href=\"javascript:pageInfo('" + i + "');\">" + i + "</a></li>");
//			}
//
//			if (getPageNo() == getTotalPages()) {
//				displayInfo.append("<li class='next disabled'>");
//			} else {
//				displayInfo.append("<li class='next'>");
//			}
//			displayInfo.append("<a href=\"javascript:pageInfo('" + getNextPage() + "')\" title='Next'>");
//			displayInfo.append("<i class='fa fa-angle-right'></i></a></li>");
//			displayInfo.append("</ul></div></div>");
//		}
//		return displayInfo.toString();
//	}

}
