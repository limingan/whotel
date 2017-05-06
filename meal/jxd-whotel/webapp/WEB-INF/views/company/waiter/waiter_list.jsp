<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 服务员管理</title>
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
<style type="text/css">
input[type='checkbox']{width: 17px;height: 17px;}
</style>
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-waiter-waiter" scope="request"/>
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
					<li>服务员管理</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>
		<form action="/company/waiter/listWaiter.do" id="pageForm" method="post">
			<div class="portlet-body">
			    <input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
			    <div class="input-group">
					<label>名称</label>
					<input name="params[userName]" class="form-control input-medium" maxlength="50" value="${queryParam.params.userName}"/>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<label>编码</label>
					<input name="params[userNo]" class="form-control input-medium" maxlength="50" value="${queryParam.params.userNo}"/>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<button type="submit" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<a href="javascript:synchronousData()" class="btn btn-sm green"><i class="fa fa-plus">更新服务员</i></a>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<a href="javascript:deleteMoreWaiter()" class="btn btn-sm default"><i class="fa fa-times"></i>批量删除</a>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable">
					<thead>
						<tr role="row">
							<th width="5%">
							  <input type="checkbox" class="group-checkable js-allCheckbox"/>
						    </th>
						    <th width="5%">序号</th>
							<th width="10%">所属分店</th>
							<th width="10%">所属餐厅</th>
							<th width="10%">服务员名称</th>
							<th width="10%">服务员编码</th>
							<th width="10%">IC卡号</th>
							<th width="10%">状态</th>
							<th width="25%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.result}" var="waiter" varStatus="vs">
							<tr class="odd gradeX">
								<td>
									 <div class="checker"><span><input type="checkbox" class="checkboxes js-listCheckbox" value="${waiter.id}"></span></div>
								</td>
						   		<td>${vs.count}</td>
						   		<td>${waiter.hotelName}</td>
								<td>${waiter.restaurantName}</td>
						   	    <td>${waiter.userName}</td>
						   	    <td>${waiter.userNo}</td>
						   	    <td>${waiter.iCCard}</td>
						   	    <td>${waiter.status =='0'?"可用":"停用"}</td>
								<td><span style="position:relative;"> 
									<a href="toEditWaiter.do?id=${waiter.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
									<a href="javascript:deleteWaiter('${waiter.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
								</span></td>
						 	</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="row">${page.pageNavigation}</div>
		</form>
	</div>
</div>

<div class="loading" id="loading"></div>

<script src="/static/common/js/checkAll.js" type="text/javascript"></script>
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/jquery.qrcode.min.js"></script>
<jsp:include page="/common/bootbox.jsp" />
<script type="text/javascript">
var $loading = $("#loading");
function synchronousData(){
	$.ajax({
		url:"/company/meal/promptSynchronizeOrder.do",
		type : 'post',
		data:{"seq":1},
		dataType:"json",
		success:function(data) {
			if(data.length == 0){
				$loading.show();
				window.location.href="/company/waiter/synchronizeMealTabByJXD.do";
			}else{
				alert(data);
			}
		}
	});
}

var checkTool = new checkTools();
$(function() {
	checkTool.init();
});

function deleteWaiter(ids) {
	var url = "/company/waiter/deleteWaiter.do?ids="+ids;
	confirmDeleteData("真的需要删除吗？", url);
}	

function deleteMoreWaiter() {
	var ids = checkTool.getAllCheckValues();
	if(ids.length > 0) {
		deleteWaiter(ids);
	} else {
		boxAlert("您至少需要选择一条数据!");
	}
}

</script>