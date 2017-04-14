<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 模版消息</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<c:set var="cur_sub" value="sel-template_msg" scope="request"/>
<div class="page-content-wrapper">
	<div class="page-content">
		
		<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i>
							<a href="/company/index.do">首页</a>
							<i class="fa fa-angle-right"></i>
						</li>
						<li>模版消息</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			 	<form action="/company/toTemplateMessage.do" id="pageForm" method="post">
		    		<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
				</form>
				<div class="table-toolbar">
					<a href="/company/toEditTemplateMessage.do" class="btn btn-sm green"><i class="fa fa-plus"></i>新增模版消息</a>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				
				<tr role="row">
				    <th width="7%">序号</th>
					<th width="26%">消息名称</th>
					<th width="26%">消息类型</th>
					<th width="26%">模板ID</th>
					<th width="15%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="tm" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${tm.messageType.name}</td>
				   	    <td>${tm.messageType.label}</td>
				   	    <td>${tm.templateId}</td>
						<td>
						    <span style="position:relative;"> 
							<a href="toEditTemplateMessage.do?id=${tm.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
							<a href="javascript:deleteTemplateMessage('${tm.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
							</span>
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
function deleteTemplateMessage(id) {
	var url = "/company/deleteTemplateMessage.do?id="+id;
	confirmDeleteData("真的需要删除吗？", url);
}
</script>