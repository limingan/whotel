package com.whotel.common.util;

import java.util.List;

/**
 * 搜索分页工具类
 * @author 冯勇
 *
 * @param <T>
 */
public class SearchPage<T> {

	private int pageNo = 1;

	private int start = 0;

	private int end;

	private int pageSize = 15;

	private int totalPage;

	private int totalCount;

	private List<T> result;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getStart() {
		start = (pageNo - 1) * pageSize;
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		end = (pageNo == totalPage ? totalCount : pageSize * pageNo);
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		totalPage = (totalCount + pageSize - 1) / pageSize;
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	/**
	 * 分页导航
	 * @return
	 */
	public String getPagingNavigation() {
		getTotalPage();
		StringBuffer displayInfo = new StringBuffer();
		displayInfo.append("<ul class='pagelist'>");
		if (pageNo == 0 || totalCount == 0) {
			displayInfo.append("<li>没有分页的信息!</li>");
		} else {
			if (pageNo > 1) {
				displayInfo.append("<li><a href='javascript:void(0)' class='firstPage'>首页</a></li>");
				displayInfo.append("<li><a href='javascript:void(0)' class='prePage'>上一页</a></li>");
			}

			int startIndex = pageNo - 2; // 假定左边界
			int endIndex = pageNo + 2; // 假定右边界

			if (startIndex < 1) {
				startIndex = 1; // 微调
			}
			if (endIndex > totalPage) {
				endIndex = totalPage;
			}

			for (int i = startIndex; i <= endIndex; i++) {
				if (i == pageNo) {
					displayInfo.append("<li class='thisclass'>" + i + "</li>");
				} else {
					displayInfo.append("<li><a href='javascript:void(0)'><span class='thisPage'>" + i
							+ "</span></a></li>");
				}
			}
			if (pageNo < totalPage) {
				displayInfo.append("<li><a href='javascript:void(0)' class='nextPage'><span>下一页</span></a></li>");
				displayInfo.append("<li><a href='javascript:void(0)' class='lastPage'><span>末页</span></a></li>");
			}

			displayInfo.append("<li><span class='pageinfo'>共<strong>" + totalCount + "</strong>条信息</span></li>");
			displayInfo.append("<li><span class='pageinfo'>第<strong>" + pageNo + "</strong>/<strong>" + totalPage
					+ "</strong>页</li></span>");
			displayInfo.append("<li><span class='pageinfo'>本页共<strong>"
					+ (pageNo == totalPage ? totalCount - (pageNo - 1) * pageSize : pageSize)
					+ "</strong>条</li></span></ul>");
		}
		return displayInfo.toString();
	}
}
