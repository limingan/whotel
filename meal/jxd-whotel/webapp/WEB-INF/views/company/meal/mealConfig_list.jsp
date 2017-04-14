<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 餐厅参数配置</title>
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-meal-config" scope="request"/>
<c:set var="cur_sub_leaf" value="sel-meal-manage-leaf" scope="request"/>

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
					<li>参数配置</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>
		<form action="/company/meal/listMealConfig.do" id="pageForm" method="post">
			<div class="portlet-body">
				<div class="table-toolbar">
					<a href="/company/meal/toEditMealConfig.do" class="btn btn-sm green"><i class="fa fa-plus"></i>新增配置</a>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable">
					<thead>
						<tr role="row">
						    <th width="5%">序号</th>
							<th width="15%">地点时间</th>
							<th width="10%">所属餐段</th>
							<th width="10%">创建时间</th>
							<th width="10%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.result}" var="mealConfig" varStatus="vs">
							<tr class="odd gradeX">
						   		<td>${vs.count}</td>
						   	    <td>${mealConfig.arriveTime}</td>
						   	    <td>${mealConfig.mealType.label}</td>
						   	    <td><fmt:formatDate value="${mealConfig.createDate}" pattern="yyyy-MM-dd" /></td>
								<td>
								    <span style="position:relative;"> 
									<a href="toEditMealConfig.do?id=${mealConfig.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
									<a href="javascript:deleteMealConfig('${mealConfig.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
									</span>
							    </td>
						 	</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="row">${page.pageNavigation}</div>
		</form>
	</div>
</div>
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/jquery.qrcode.min.js"></script>
<jsp:include page="/common/bootbox.jsp" />
<script type="text/javascript">
function deleteMealConfig(id) {
	var url = "/company/meal/deleteMealConfig.do?ids="+id;
	confirmDeleteData("真的需要删除吗？", url);
}	
</script>