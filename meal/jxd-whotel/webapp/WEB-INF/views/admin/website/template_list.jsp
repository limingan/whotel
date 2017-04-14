<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 微网站模板管理</title>
</head>
<c:set var="cur" value="sel-website-template" scope="request"/>
<c:set var="cur_sub" value="sel-website-list" scope="request"/>
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
							<a href="/admin/website/listWebsiteTemplate.do">微网站模板</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			     <form action="/admin/website/listWebsiteTemplate.do" id="pageForm" method="post">
		    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
		    	
				</form>
				<div class="table-toolbar">
					<a href="/admin/website/toEditWebsiteTemplate.do" class="btn btn-sm green"><i class="fa fa-plus"></i>新增模板</a>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				
				<tr role="row">
				    <th width="5%">序号</th>
					<th width="15%">名称</th>
					<th width="30%">预览图</th>
					<th width="10%">栏目</th>
					<th width="14%">创建时间</th>
					<th width="22%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="template" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${template.name}</td>
						<td><img alt="预览模板" src="${template.thumbnailUrl}" width="60" height="120"></td>
						<td>
						<c:forEach items="${template.columns}" var="column">
							${column.name}<br>
						</c:forEach>
						</td>
						<td><fmt:formatDate value="${template.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<a href="toEditWebsiteTemplate.do?id=${template.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
							<a href="javascript:deleteWebsiteTemplate('${template.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
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
function deleteWebsiteTemplate(id) {
	var url = "/admin/website/deleteWebsiteTemplate.do?ids="+id;
	confirmDeleteData("删除将导致商户微网站无法使用，真的需要删除吗？", url);
}
</script>