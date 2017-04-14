<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 栏目链接管理</title>
</head>
<c:set var="cur" value="sel-columnLink" scope="request"/>
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
							<a href="/admin/index.do">首页</a>
							<i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="/admin/website/listColumnLink.do">栏目链接管理</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			     <form action="/admin/website/listColumnLink.do" id="pageForm" method="post">
		    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
				</form>
				<div class="table-toolbar">
					<a href="/admin/website/toEditColumnLink.do" class="btn btn-sm green"><i class="fa fa-plus"></i>新增栏目链接</a>
					<a href="javascript:deleteMoreColumnLink()" class="btn btn-sm default"><i class="fa fa-times"></i>批量删除</a>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				
				<tr role="row">
				    <th width="5%" class="table-checkbox sorting_disabled" role="columnheader">
					  <div class="checker"><span><input type="checkbox" class="group-checkable js-allCheckbox"></span></div>
				    </th>
				    <th width="5%">序号</th>
					<th width="30%">名称</th>
					<th width="45%">链接</th>
					<th width="15%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="columnLink" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	     <td>
						   <div class="checker"><span><input type="checkbox" class="checkboxes js-listCheckbox" value="${columnLink.id}"></span></div>
						</td>
				   	    <td>${vs.count}</td>
						<td>${columnLink.name}</td>
						<td>${columnLink.url}</td>
						<td>
							<a href="toEditColumnLink.do?id=${columnLink.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
							<a href="javascript:deleteColumnLink('${columnLink.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
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
	
	function deleteColumnLink(ids) {
		var url = "/admin/website/deleteColumnLink.do?ids="+ids;
		confirmDeleteData("真的需要删除吗？", url);
	}
	
	function deleteMoreColumnLink() {
		var ids = checkTool.getAllCheckValues();
		if(ids.length > 0) {
			deleteColumnLink(ids);
		} else {
			boxAlert("您至少需要选择一条数据!");
		}
	}
</script>