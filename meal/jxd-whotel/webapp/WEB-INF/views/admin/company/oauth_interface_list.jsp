<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 商户信息管理</title>
<style type="text/css">
.whotel-modal .modal-content{
	width:230px;
	text-algin:center;
	margin-left:30%;
}
</style>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
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
							<a href="/admin/company/listCompany.do">商户信息管理</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
				
				<div class="table-toolbar">
					<a href="/admin/company/editOauthInterface.do?companyId=${companyId}" class="btn btn-sm green"><i class="fa fa-plus"></i>新建</a>
				</div>
				
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr role="row">
				    <th width="5%">序号</th>
					<th width="15%">代码</th>
					<th width="30%">链接</th>
					<th width="15%">秘钥</th>
					<th width="10%">状态</th>
					<th width="25%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${oauthInterfaces}" var="oauthInterface" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${oauthInterface.code}</td>
						<td>${oauthInterface.url}</td>
						<td>${oauthInterface.secretKey}</td>
						<td>${oauthInterface.enable?"启用":"禁用"}</td>
						<td>
							<span style="position:relative;"> 
								<a href="editOauthInterface.do?id=${oauthInterface.id}&compnayId=${companyId}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
								<a href="javascript:" class="btn btn-sm green copy_btn" data-url="http://${pageContext.request.serverName}/oauth/toOauthInterface.do?comid=${companyId}&code=${oauthInterface.code}"><i class="fa fa-file-o"></i>复制链接</a>
								<a href="javascript:deleteById('${oauthInterface.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
							</span>
					    </td>
					  </tr>
				   </c:forEach>
				</tbody>
				</table>
				</div>
		</div>
</div>
<jsp:include page="/common/bootbox.jsp" />
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/zclip/jquery.zclip.min.js"></script>
<script src="/static/common/js/copy.js"></script>
<script>
function deleteById(id){
	var url = "/admin/company/deleteOauthInterface.do?id="+id;
	confirmDeleteData("真的需要删除吗？", url);
}
</script>
