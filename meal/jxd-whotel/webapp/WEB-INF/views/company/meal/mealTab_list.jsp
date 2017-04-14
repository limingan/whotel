<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 包间管理</title>
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
<style type="text/css">
input[type='checkbox']{width: 17px;height: 17px;}
</style>
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-meal-mealTab" scope="request"/>
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
					<li>包间管理</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>
		<form action="/company/meal/listMealTab.do" id="pageForm" method="post">
			<%-- <input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo"> --%>
			<div class="portlet-body">
			    <input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
			    <input type="hidden" name="params[order]" value="${queryParam.params.order}" id="js_order"/>
			    <div class="input-group">
					<label>名称</label>
					<input name="params[tabName]" class="form-control input-medium" maxlength="50" value="${queryParam.params.tabName}"/>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<label>编码</label>
					<input name="params[tabNo]" class="form-control input-medium" maxlength="50" value="${queryParam.params.tabNo}"/>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<label>所属分店</label>
					<select name="params[hotelCode]" class="form-control input-medium" id="js_hotelCode" onchange="ajaxRestaurantByHotel()">
						<option value="">全部</option>
						<c:forEach items="${hotels}" var="hotel">
							<option <c:if test="${hotel.code==queryParam.params.hotelCode}">selected="selected"</c:if> value="${hotel.code}">${hotel.name}</option>
						</c:forEach>
					</select>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<label>所属餐厅</label>
					<select name="params[restaurantId]" class="form-control input-medium" id="js_restaurant">
						<option value="">全部</option>
						<c:forEach items="${restaurantList}" var="restaurant">
							<option <c:if test="${restaurant.id==queryParam.params.restaurantId}">selected="selected"</c:if> value="${restaurant.id}">${restaurant.name}</option>
						</c:forEach>
					</select>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<label>状态</label>
					<select class="form-control input-medium" name="params[isEnable]">
						<option value="">全部</option>
						<option value="true" <c:if test="${queryParam.params.isEnable == 'true'}">selected</c:if>>可预订</option> 
						<option value="false" <c:if test="${queryParam.params.isEnable == 'false'}">selected</c:if>>不可预订</option> 
					</select>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<button type="submit" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<a href="javascript:synchronousData()" class="btn btn-sm green"><i class="fa fa-plus">更新包间</i></a>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<a href="javascript:deleteMoreNotice()" class="btn btn-sm default"><i class="fa fa-times"></i>批量删除</a>
				</div>
				<!-- <div class="table-toolbar">
					<a href="/company/meal/toEditMealTab.do" class="btn btn-sm green"><i class="fa fa-plus"></i>新增包间</a>
					<a href="/company/meal/synchronizeMealTabByJXD.do" class="btn btn-sm green"><i class="fa fa-plus">更新包间</i></a>
				</div> -->
				<table class="table table-striped table-bordered table-hover dataTable">
					<thead>
						<tr role="row">
							<th width="5%" class="table-checkbox sorting_disabled" role="columnheader">
							  <div class="checker"><span><input type="checkbox" class="group-checkable js-allCheckbox"></span></div>
						    </th>
						    <th width="5%">序号</th>
							<th width="8%">所属分店</th>
							<th width="8%">所属餐厅</th>
							<th width="8%">包房名称</th>
							<th width="8%">包房编码</th>
							<th width="8%">图片</th>
							<th width="8%">状态</th>
							<th width="8%">容纳人数</th>
							<th width="8%">最低消费</th>
							<th width="8%">定金</th>
							<th width="8%"><span style="color: #0033CC;cursor: pointer;" onclick="orderAscDesc()">排序
								<img src="/static/company/images/${queryParam.params.order=='true'?'asc':'desc'}.png"/>
							</span></th>
							<th width="15%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.result}" var="mealTab" varStatus="vs">
							<tr class="odd gradeX">
								<td>
								   <div class="checker"><span><input type="checkbox" class="checkboxes js-listCheckbox" value="${mealTab.id}"></span></div>
								</td>
						   		<td>${vs.count}</td>
								<td>${mealTab.restaurant.hotelName}</td>
						   	    <td>${mealTab.restaurant.name}</td>
						   	    <td>${mealTab.tabName}</td>
						   	    <td>${mealTab.tabNo}</td>
						   	    <td><img src="${mealTab.miniatureUrl}" width="80" height="80"/></td>
						   	    <td>${mealTab.isEnable?"可预订":"不可预订"}</td>
						   	    <td>${mealTab.seats}</td>
						   	    <td>${mealTab.minimums}</td>
						   	    <td>${mealTab.deposit}</td>
						   	    <td>${mealTab.orderIndex}</td>
								<td><span style="position:relative;"> 
									<a href="toEditMealTab.do?id=${mealTab.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
									<a href="javascript:deleteNotice('${mealTab.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
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
		data:{"seq":3},
		dataType:"json",
		success:function(data) {
			if(data.length == 0){
				$loading.show();
				window.location.href="/company/meal/synchronizeMealTabByJXD.do";
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

function deleteNotice(ids) {
	var url = "/company/meal/deleteMealTab.do?ids="+ids;
	confirmDeleteData("真的需要删除吗？", url);
}	

function deleteMoreNotice() {
	var ids = checkTool.getAllCheckValues();
	if(ids.length > 0) {
		deleteNotice(ids);
	} else {
		boxAlert("您至少需要选择一条数据!");
	}
}

function ajaxRestaurantByHotel(){
	var hotelCode = $("#js_hotelCode").val();
	$.ajax({
		type : "post",
		url : "/company/meal/ajaxRestaurantByHotel.do",
		data : {hotelCode : hotelCode},
		dataType : 'json',
		success : function(data) {
			var hl = '<option value="">全部</option>';
			if(data.length > 0){
				for (var i = 0; i < data.length; i++) {
					hl += '<option value="'+data[i].id+'">'+data[i].name+'</option>';
				}
			}
			$("#js_restaurant").html(hl);
		}
	});
}

function orderAscDesc(){
	var order = $("#js_order").val();
	if(order == "true"){
		$("#js_order").val("fasle");
	}else{
		$("#js_order").val("true");
	}
	$("#pageForm").submit();
}
</script>