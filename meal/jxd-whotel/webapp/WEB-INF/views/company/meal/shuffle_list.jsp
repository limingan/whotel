<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 菜肴管理</title>
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-meal-shuffle" scope="request"/>

<div class="page-content-wrapper">
	<div class="page-content">
		<div class="row">
			<div class="col-md-12">
				<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i><a href="/company/index.do">首页</a><i class="fa fa-angle-right"></i></li>
					<li><a href="/company/meal/listShuffle.do">市别管理</a></li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>
		<form action="/company/meal/listShuffle.do" id="pageForm" method="post">
			<div class="portlet-body">
	    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
	    	<div class="input-group">
				<label>市别名称</label>
				<input name="params[shuffleName]" class="form-control input-medium" maxlength="50" value="${queryParam.params.shuffleName}"/>
				<span>&nbsp;&nbsp;&nbsp;</span>
				<label>市别编码</label>
				<input name="params[shuffleNo]" class="form-control input-medium" maxlength="50" value="${queryParam.params.shuffleNo}"/>
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
				<button type="submit" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
				<span>&nbsp;&nbsp;&nbsp;</span>
				<a href="javascript:synchronousData()" class="btn btn-sm green"><i class="fa fa-plus"></i>更新市别</a>
			</div>
			<table class="table table-striped table-bordered table-hover dataTable" style="word-break:break-all; word-wrap:break-all;">
				<thead>
					<tr role="row">
				    	<th width="5%">序号</th>
						<th width="10%">所属分店</th>
						<th width="10%">所属餐厅</th>
						<th width="10%">市别名称</th>
						<th width="10%">市别编码</th>
						<th width="10%">开始时间</th>
						<th width="10%">结束时间</th>
						<th width="20%">说明</th>
						<th width="15%">操作</th>
					</tr>
				</thead>
				<tbody>
				 	<c:forEach items="${page.result}" var="shuffle" varStatus="vs">
				   	  	<tr class="odd gradeX">
					   	    <td>${vs.count}</td>
							<td>${shuffle.restaurant.hotelName}</td>
							<td>${shuffle.restaurant.name}</td>
							<td>${shuffle.shuffleName}</td>
							<td>${shuffle.shuffleNo}</td>
							<td>${shuffle.startTime}</td>
							<td>${shuffle.endTime}</td>
							<td>${shuffle.remark}</td>
							<td><a href="javascript:deleteNotice('${shuffle.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a></td>
							<%-- <td>
								<a href="toEditShuffle.do?id=${shuffle.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
								<a href="javascript:deleteNotice('${category.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
						    </td> --%>
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
<jsp:include page="/common/bootbox.jsp" />
<script type="text/javascript">
var $loading = $("#loading");
function synchronousData(){
	$.ajax({
		url:"/company/meal/promptSynchronizeOrder.do",
		type : 'post',
		data:{"seq":2},
		dataType:"json",
		success:function(data) {
			if(data.length == 0){
				$loading.show();
				window.location.href="/company/meal/synchronizeShuffleByJXD.do";
			}else{
				alert(data);
			}
		}
	});
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

function deleteNotice(ids) {
	var url = "/company/meal/deleteShuffle.do?ids="+ids;
	confirmDeleteData("真的需要删除吗？", url);
}
</script>