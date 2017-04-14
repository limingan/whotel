<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 餐厅管理</title>
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-meal-restaurant" scope="request"/>
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
					<li>餐厅管理</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>
		<div class="portlet-body">
			 <form action="/company/meal/listRestaurant.do" id="pageForm" method="post">
		    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
		    	<div class="input-group">
					<label>餐厅编码</label>
					<input name="params[refeNo]" class="form-control input-medium" maxlength="50" value="${queryParam.params.refeNo}"/>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<label>所属分店</label>
					<select name="params[hotelCode]" class="form-control input-medium">
						<option value="">全部</option>
						<c:forEach items="${hotels}" var="hotel">
							<option <c:if test="${hotel.code==queryParam.params.hotelCode}">selected="selected"</c:if> value="${hotel.code}">${hotel.name}</option>
						</c:forEach>
					</select>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<button type="submit" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<!-- <a href="/company/meal/toEditRestaurant.do" class="btn btn-sm green"><i class="fa fa-plus"></i>新增餐厅</a> -->
					<a href="javascript:synchronousData()" class="btn btn-sm green"><i class="fa fa-plus"></i>同步数据</a>
				</div>
			</form>
			<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
					<tr role="row">
					    <th width="5%">序号</th>
						<th width="8%">分店名称</th>
						<th width="8%">餐厅名称</th>
						<th width="8%">餐厅编码</th>
						<th width="8%">餐厅图片</th>
						<th width="8%">营业时间</th>
						<th width="8%">餐厅状态</th>
						<th width="8%">服务费</th>
						<th width="8%">餐段</th>
						<th width="8%">菜系</th>
						<th width="8%">预订电话</th>
						<th width="15%">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.result}" var="restaurant" varStatus="vs">
						<tr class="odd gradeX">
					   		<td>${vs.count}</td>
					   	    <td>${restaurant.hotelName}</td>
					   	    <td>${restaurant.name}</td>
					   	    <td>${restaurant.refeNo}</td>
					   	    <td><img src="${restaurant.miniatureUrl}" width="80" height="80"/></td>
					   	    <td>${restaurant.businessTime}</td>
					   	    <td>${restaurant.isEnable?"可预订":"不可预订"}</td>
					   	    <td>${restaurant.serviceFee}</td>
					   	    <td><c:forEach items="${shuffleMap.get(restaurant.id)}" var="map" varStatus="vs">
				   	    		<c:if test="${vs.index != 0}">,</c:if>${map.shuffleName}
					   	    </c:forEach></td>
					   	    <td>${restaurant.cuisine}</td>
					   	    <td>${restaurant.tel}</td>
							<td><span style="position:relative;"> 
								<a href="toEditRestaurant.do?id=${restaurant.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
								<a href="javascript:deleteNotice('${restaurant.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
							</span></td>
					 	</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row">${page.pageNavigation}</div>
	</div>
</div>

<div class="loading" id="loading"></div>

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
				window.location.href="/company/meal/synchronizeRestaurantByJXD.do";
			}else{
				alert(data);
			}
		}
	});
}

function deleteNotice(ids) {
	var url = "/company/meal/deleteRestaurant.do?ids="+ids;
	confirmDeleteData("真的需要删除吗？", url);
}		
</script>