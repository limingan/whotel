<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 系统角色</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<c:set var="cur_sub" value="sel-role" scope="request"/>
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
							<a href="/company/authority/listCompanyRole.do">系统角色</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			     <form action="/company/authority/listCompanyRole.do" id="pageForm" method="post">
		    	<input type="hidden" name="page" value="${page.pageNo}" id="pageNo">
				</form>
				<div class="table-toolbar">
					<a href="/company/authority/toEditCompanyRole.do" class="btn btn-sm green"><i class="fa fa-plus"></i>新增角色</a>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr role="row">
				    <th width="10%">序号</th>
					<th width="16%">名称</th>
					<th width="60%">权限</th>
					<th width="14%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="role" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${role.name}</td>
						<td>
						<c:forEach items="${role.modules}" var="module" varStatus="vs">
						    ${module.name}
							<c:if test="${module.name!=null&&!vs.last}">,</c:if>
						</c:forEach>
						</td>
						<td>
							<a href="toEditCompanyRole.do?id=${role.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
							<a href="javascript:deleteRole('${role.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
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
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/bootbox.jsp" />
<script>
function deleteRole(id) {
	var url = "/company/authority/deleteCompanyRole.do?id="+id;
	confirmDeleteData("真的需要删除吗？", url);
}
</script>