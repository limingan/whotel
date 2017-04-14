<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 支付订单</title>
</head>
<c:set var="cur" value="sel-order" scope="request"/>
<c:set var="cur_sub" value="sel-pay-order" scope="request"/>
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
						<li>支付订单</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			 	<form action="/company/payOrder/toPayOrder.do" id="pageForm" method="post">
		    		<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
				</form>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				
				<tr role="row">
				    <th width="5%">序号</th>
					<th width="15%">订单名</th>
					<th width="15%">订单号</th>
					<th width="15%">金额</th>
					<th width="10%">支付类型</th>
					<th width="10%">订单类型</th>
					<th width="10%">支付状态</th>
					<th width="10%">创建时间</th>
					<th width="10%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="payOrder" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${payOrder.name}</td>
						<td>${payOrder.orderSn}</td>
						<td>¥<fmt:formatNumber value="${payOrder.totalFee/100}" type="currency" pattern="#######.##"/></td>
						<td>${payOrder.payMent.label}</td>
						<td>${payOrder.payMode.label}</td>
						<td>${payOrder.status.label}</td>
						<td><fmt:formatDate value="${payOrder.createTime}" pattern="yyyy-MM-dd"/></td>
						<td style="position:relative;">
							<a href="handlePayOrder.do?orderSn=${payOrder.orderSn}" class="btn btn-sm purple"><i class="fa fa-edit"></i>同步</a>
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
<jsp:include page="/common/bootbox.jsp" />