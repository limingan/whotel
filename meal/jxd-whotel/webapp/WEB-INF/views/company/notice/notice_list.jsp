<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 商户公告管理</title>
</head>
<c:set var="cur" value="sel-notice" scope="request"/>
<div class="page-content-wrapper">
	<div class="page-content">
		
		<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				<!-- 	<h3 class="page-title">
					Managed Datatables <small>managed datatable samples</small>
					</h3>  -->
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i>
							<a href="/company/index.do">首页</a>
							<i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="/company/notice/listCompanyNotice.do">平台公告管理</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			     <form action="/company/notice/listCompanyNotice.do" id="pageForm" method="post">
		    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
			    	<div class="input-group">
						<label>标题</label>
						<input name="params[title]" class="form-control input-medium" maxlength="50" value="${queryParam.params.title}"/>
						<span>&nbsp;&nbsp;&nbsp;</span>
						<button type="submit" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
						<span>&nbsp;&nbsp;&nbsp;</span>
						<a href="/company/notice/toEditCompanyNotice.do" class="btn btn-sm green"><i class="fa fa-plus"></i>新增公告</a>
						<span>&nbsp;&nbsp;&nbsp;</span>
						<a href="javascript:deleteMoreNotice()" class="btn btn-sm default"><i class="fa fa-times"></i>批量删除</a>
					</div>
				</form>
				<table class="table table-striped table-bordered table-hover dataTable" style="word-break:break-all; word-wrap:break-all;">
				<thead>
				
				<tr role="row">
				    <th width="5%" class="table-checkbox sorting_disabled" role="columnheader">
					  <div class="checker"><span><input type="checkbox" class="group-checkable js-allCheckbox"></span></div>
				    </th>
				    <th width="5%">序号</th>
					<th width="20%">标题</th>
					<th width="45%">内容</th>
					<th width="15%">创建时间</th>
					<th width="15%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="notice" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	     <td>
						   <div class="checker"><span><input type="checkbox" class="checkboxes js-listCheckbox" value="${notice.id}"></span></div>
						</td>
				   	    <td>${vs.count}</td>
						<td>${notice.title}</td>
						<td>${notice.content}</td>
						<td><fmt:formatDate value="${notice.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<a href="toEditCompanyNotice.do?id=${notice.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
							<a href="javascript:deleteNotice('${notice.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
					    </td>
					  </tr>
				   </c:forEach>
				</tbody>
				</table>
				</div>
				<div class="row">
					${page.pageNavigation}
				</div>
		</div>
</div>
<script src="/static/common/js/checkAll.js" type="text/javascript"></script>
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/bootbox.jsp" />
<script type="text/javascript">
    
    var checkTool = new checkTools();
	$(function() {
		checkTool.init();
	});
	
	function deleteNotice(ids) {
		var url = "/company/notice/deleteCompanyNotice.do?ids="+ids;
		confirmDeleteData("真的需要删除吗？", url);
	}
	
	function deleteMoreNotice() {
		var ids = checkTool.getAllCheckValues();
		if(ids.length > 0) {
			deleteNotice(ids);
		} else {
			boxAlert("您至少需要选择一条数据!");
		}
	}
</script>