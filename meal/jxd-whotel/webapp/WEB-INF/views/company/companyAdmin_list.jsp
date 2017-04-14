<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 商户员工管理</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<c:set var="cur_sub" value="sel-company-admin" scope="request"/>
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
							<a href="/company/listCompanyAdmin.do">商户员工</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			     <form action="listCompanyAdmin.do" id="pageForm" method="post">
		    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
		    	
				</form>
				<div class="table-toolbar">
					<a href="/company/toEditCompanyAdmin.do" class="btn btn-sm green"><i class="fa fa-plus"></i>新增员工</a>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				
				<tr role="row">
				    <th width="5%">序号</th>
					<th width="15%">账号</th>
					<th width="15%">姓名</th>
					<th width="10%">角色</th>
					<!-- <th width="15%">邮箱</th> -->
					<th width="10%">手机</th>
					<th width="14%">创建时间</th>
					<th width="20%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="companyAdmin" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${companyAdmin.account}</td>
						<td>${companyAdmin.name}</td>
						<td>${companyAdmin.role.name}</td>
						<%-- <td>${companyAdmin.email}</td> --%>
						<td>${companyAdmin.mobile}</td>
						<td><fmt:formatDate value="${companyAdmin.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<a href="toEditCompanyAdmin.do?id=${companyAdmin.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
					   		<c:if test="${isAdmin && companyAdmin.errorCount>=10}">
					   			<a href="removeLock.do?id=${companyAdmin.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i>解除锁定</a>
					   		</c:if>
							<a href="javascript:deleteCompanyAdmin('${companyAdmin.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
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
function deleteCompanyAdmin(id) {
	var url = "/company/deleteCompanyAdmin.do?ids="+id;
	confirmDeleteData("真的需要删除吗？", url);
}
</script>