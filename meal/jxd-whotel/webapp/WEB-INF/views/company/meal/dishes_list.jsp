<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 菜肴管理</title>
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
<style type="text/css">
input[type='checkbox']{width: 17px;height: 17px;}
</style>
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-meal-dishes" scope="request"/>
<c:set var="cur_sub_leaf" value="sel-dishes-manage-leaf" scope="request"/>

<div class="page-content-wrapper">
	<div class="page-content">
		<div class="row">
			<div class="col-md-12">
				<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i><a href="/company/index.do">首页</a><i class="fa fa-angle-right"></i></li>
					<li><a href="/company/meal/listDishes.do">菜肴管理</a></li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>
		<form action="/company/meal/listDishes.do" id="pageForm" method="post">
			<div class="portlet-body">
	    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
	    	<input type="hidden" name="params[order]" value="${queryParam.params.order}" id="js_order"/>
	    	<div class="input-group">
				<label>菜肴名称</label>
				<input name="params[dishName]" class="form-control input-medium" maxlength="50" value="${queryParam.params.dishName}"/>
				<span>&nbsp;&nbsp;&nbsp;</span>
				<label>菜肴编码</label>
				<input name="params[dishNo]" class="form-control input-medium" maxlength="50" value="${queryParam.params.dishNo}"/>
				<span>&nbsp;&nbsp;&nbsp;</span>
				<label>分类</label>&nbsp;
				<input name="params[dishType]" class="form-control input-medium" maxlength="50" value="${queryParam.params.dishType}"/>
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
				<!-- <a href="/company/notice/toEditCompanyNotice.do" class="btn btn-sm green"><i class="fa fa-plus"></i>归类</a>
				<span>&nbsp;&nbsp;&nbsp;</span>
				<a href="/company/meal/toEditDishes.do" class="btn btn-sm green"><i class="fa fa-plus"></i>添加</a>
				<span>&nbsp;&nbsp;&nbsp;</span> -->
				<a href="javascript:synchronousData()" class="btn btn-sm green"><i class="fa fa-plus"></i>更新菜肴</a>
				<span>&nbsp;&nbsp;&nbsp;</span>
				<a href="javascript:deleteMoreNotice()" class="btn btn-sm default"><i class="fa fa-times"></i>批量删除</a>
			</div>
			<table class="table table-striped table-bordered table-hover dataTable" style="word-break:break-all; word-wrap:break-all;">
				<thead>
					<tr role="row">
						<th width="5%" class="table-checkbox sorting_disabled" role="columnheader">
						  <div class="checker"><span><input type="checkbox" class="group-checkable js-allCheckbox"></span></div>
					    </th>
				    	<th width="5%">序号</th>
						<th width="8%">所属分店</th>
						<th width="8%">所属餐厅</th>
						<th width="8%">菜肴名称</th>
						<th width="8%">菜肴编码</th>
						<th width="8%">图片</th>
						<th width="8%">价格</th>
						<th width="8%">分类</th>
						<th width="8%">状态</th>
						<th width="8%"><span style="color: #0033CC;cursor: pointer;" onclick="orderAscDesc()">排序
							<img src="/static/company/images/${queryParam.params.order=='true'?'asc':'desc'}.png"/>
						</span></th>
						<th width="10%">创建时间</th>
						<th width="13%">操作</th>
					</tr>
				</thead>
				<tbody>
				 	<c:forEach items="${page.result}" var="dishes" varStatus="vs">
				   	  	<tr class="odd gradeX">
				   	  		<td>
							   <div class="checker"><span><input type="checkbox" class="checkboxes js-listCheckbox" value="${dishes.id}"></span></div>
							</td>	
					   	    <td>${vs.count}</td>
							<td>${dishes.restaurant.hotelName}</td>
							<td>${dishes.restaurant.name}</td>
							<td>${dishes.dishName}</td>
							<td>${dishes.dishNo}</td>
							<td><img src="${dishes.miniatureUrl}" width="80" height="80"/></td>
							<td>${dishes.price}</td>
							<td>${dishes.dishType}</td>
							<td>${dishes.isEnable?"上架":"下架"}</td>
							<td>${dishes.orderIndex}</td>
							<td><fmt:formatDate value="${dishes.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>
								<c:choose>
									<c:when test="${dishes.isEnable}">
										<a href="saveDishes.do?id=${dishes.id}&&isEnable=false" class="btn btn-sm purple"><i class="fa fa-edit"></i> 下架</a>
									</c:when>
									<c:otherwise>
										<a href="saveDishes.do?id=${dishes.id}&&isEnable=true" class="btn btn-sm purple"><i class="fa fa-edit"></i> 发布</a>
									</c:otherwise>
								</c:choose>
								<a href="toEditDishes.do?id=${dishes.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
								<a href="javascript:deleteNotice('${dishes.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
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
		data:{"seq":4},
		dataType:"json",
		success:function(data) {
			if(data.length == 0){
				$loading.show();
				window.location.href="/company/meal/synchronizeDishesByJXD.do";
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
		var url = "/company/meal/deleteDishes.do?ids="+ids;
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