<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 支付配置</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<c:set var="cur_sub" value="sel-pay-config" scope="request"/>
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
						<li>支付配置</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
				<div class="table-toolbar">
					<a href="/company/ToEditPayConfig.do" class="btn btn-sm green"><i class="fa fa-plus"></i>新增支付配置</a>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				
				<tr role="row">
				    <th width="5%">序号</th>
					<th width="15%">支付类型</th>
					<th width="20%">商户ID</th>
					<th width="15%">应用ID</th>
					<th width="15%">应用KEY</th>
					<th width="5%">状态</th>
					<th width="15%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${payConfigs}" var="payConfig" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${payConfig.payType.label}</td>
						<td>${payConfig.mchId}</td>
						<td>${payConfig.appId}</td>
						<td>${payConfig.apiKey}</td>
						<td>${payConfig.valid ? "有效":"禁用"}</td>
						<td>
						    <span style="position:relative;"> 
							<a href="ToEditPayConfig.do?id=${payConfig.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
							<a href="javascript:deletePayConfig('${payConfig.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
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
function deletePayConfig(id) {
	var url = "/company/deletePayConfig.do?id="+id;
	confirmDeleteData("真的需要删除吗？", url);
}
</script>