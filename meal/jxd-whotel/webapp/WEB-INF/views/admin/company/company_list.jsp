<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 商户信息管理</title>
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
			<form action="/admin/company/listCompany.do" method="post">
		    	<div class="table-toolbar">
					<div class="input-group"> 
						<label>名称:</label>
						<input name="params[name]" class="form-control input-medium" style="height:28px" type="text" value="${queryParam.params.name}">
						<span>&nbsp;</span>
						
						<label>编码:</label>
						<input name="params[code]" class="form-control input-medium" style="height:28px" type="text" value="${queryParam.params.code}">
						<span>&nbsp;</span>
						<button type="submit" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
					</div>
				</div>
				</form>
			    <form action="/admin/company/listCompany.do" id="pageForm" method="post">
		    		<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
				</form>
				
				<div class="table-toolbar">
					<a href="/admin/company/toCompanyRegister.do" class="btn btn-sm green"><i class="fa fa-plus"></i>注册商户</a>
				</div>
				
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr role="row">
				    <th width="5%">序号</th>
					<th width="15%">名称</th>
					<th width="5%">编码</th>
					<th width="8%">电话</th>
					<th width="7%">二维码</th>
					<th width="5%">状态</th>
					<th width="9%">有效期</th>
					<th width="14%">创建时间</th>
					<th width="35%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="company" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td><a href="/admin/company/toCompanyIndex.do?id=${company.id}" target="_blank" title="进入商户后台">${company.name}</td>
						<td>${company.code}</td>
						<td>${company.tel}</td>
						<td><img alt="二维码" src="${company.qrcodeUrl}" width="60" height="40"></td>
						<td>${company.valid ? "有效":"禁用"}</td>
						<td><fmt:formatDate value="${company.validTime}" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${company.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<a href="toEditCompany.do?id=${company.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
							<a href="toEditPublicNo.do?companyId=${company.id}" class="btn btn-sm green"><i class="fa fa-comments-o"></i> 公众号</a>
					    	<a href="interfaceConfig.do?companyId=${company.id}" class="btn btn-sm yellow"><i class="fa fa-code-fork"></i> 数据接口</a>
					    	<a href="smsConfig.do?companyId=${company.id}" class="btn btn-sm yellow"><i class="fa fa-code-fork"></i> 短信接口</a>
					    	<a href="listOauthInterface.do?companyId=${company.id}" class="btn btn-sm yellow"><i class="fa fa-code-fork"></i> 授权接口</a>
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
<script>
function download(companyId){
	window.location.href = '/company/marketing/synchronizeOldData.do?companyId='+companyId;
}
</script>
<jsp:include page="/common/bootbox.jsp" />
