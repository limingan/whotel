<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 订单列表</title>
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-meal-order" scope="request"/>
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
					<li>订单列表</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>
		<form action="/company/meal/listMealOrder.do" id="pageForm" method="post">
			<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
	    	<div class="input-group">
				<label>订单号</label>
				<input name="params[orderSn]" class="form-control input-medium" maxlength="50" value="${queryParam.params.orderSn}"/>
				<span>&nbsp;&nbsp;&nbsp;</span>
				<label>联系人</label>
				<input name="params[contactName]" class="form-control input-medium" maxlength="50" value="${queryParam.params.contactName}"/>
				<span>&nbsp;&nbsp;&nbsp;</span>
				<button type="submit" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
				</div>
			<div class="portlet-body">
				<table class="table table-striped table-bordered table-hover dataTable">
					<thead>
						<tr role="row">
						    <th width="5%">序号</th>
							<th width="8%">餐厅</th>
							<th width="10%">订单号</th>
							<!-- <th width="10%">类型</th> -->
							<!-- <th width="10%">处理状态</th> -->
							<th width="8%">订单状态</th>
							<th width="8%">支付状态</th>
							<th width="8%">联系人</th>
							<th width="8%">用餐时间</th>
							<th width="8%">餐段</th>
							<th width="8%">总额</th>
							<th width="11%">下单时间</th>
							<th width="8%">同步状态</th>
							<th width="10%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.result}" var="mealOrder" varStatus="vs">
							<tr class="odd gradeX">
						   		<td>${vs.count}</td>
						   	    <td>${mealOrder.name}</td>
						   	    <td>${mealOrder.orderSn}</td>
						   	    <td>${mealOrder.status.label}</td>
						   	    <td>${mealOrder.tradeStatus.label}</td>
						   	    <td>${mealOrder.contactName}</td>
						   	    <td><fmt:formatDate value="${mealOrder.arrDate}" pattern="yyyy-MM-dd"/>&nbsp;${mealOrder.arriveTime}</td>
						   	    <td>${mealOrder.shuffleName}</td>
						   	    <td>${mealOrder.totalFee}</td>
						   	    <td><fmt:formatDate value="${mealOrder.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						   	    <td>${mealOrder.synchronizeState == false?'同步失败':'同步成功'}</td>
								<td>
								    <span style="position:relative;"> 
								    <!-- toEditMealOrder.do?orderSn=${mealOrder.orderSn} -->
								    <a href="mealOrderDetails.do?id=${mealOrder.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i>详情</a>
								    <%-- <c:if test="${mealOrder.synchronizeState == false}"> --%>
									<a href="syncOrderMeals.do?orderSn=${mealOrder.orderSn}" class="btn btn-sm purple"><i class="fa fa-edit"></i>同步</a>
								    <%-- </c:if> --%>
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
	
</script>