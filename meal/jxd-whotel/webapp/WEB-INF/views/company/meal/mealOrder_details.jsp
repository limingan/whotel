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
	<div class="portlet page-content">
		<div class="row">
			<div class="col-md-12">
				<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i><a href="/company/index.do">首页</a><i class="fa fa-angle-right"></i></li><li>订单列表</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>
		<div class="portlet-body form">
			<div class="form-body">
				<table class="goods" style="width: 600px;">
					<tr><td><label>订单号：</label>${mealOrder.orderSn}</td>
						<td><label>订单状态：</label>${mealOrder.status.label}</td>
						<td></td>
					</tr>
					<tr><td><label>支付状态：</label>${mealOrder.tradeStatus.label}</td>
						<td><label>支付方式：</label>${mealOrder.payMent.label}</td>
						<td></td>
					</tr>
					<tr><td><label>所属分店：</label>${mealOrder.restaurant.hotelName}</td>
						<td><label>所属餐厅：</label>${mealOrder.restaurant.name}</td>
						<td></td>
					</tr>
					<tr><td colspan="3">&nbsp;</td></tr>
					<tr><td colspan="3"><label>订单日期：</label>
						<fmt:formatDate value="${mealOrder.createDate}" pattern="yyyy/MM/dd"/>
						<table class="table table-striped table-bordered table-hover dataTable">
							<thead><tr>
								<th>序号</th>
								<!-- <th>菜肴图片</th> -->
								<th>菜肴名称</th>
								<th>菜肴价格</th>
								<th>菜肴数量</th>
							</tr></thead>
							<tbody><c:forEach var="item" items="${mealOrder.items}" varStatus="vs"><tr>
								<td>${vs.index+1}</td>
								<%-- <td><img width="80px" height="80px" src="${item.thumbUrl}"/></td> --%>
								<td>${item.name}</td>
								<td>¥${item.itemPrice}</td>
								<td>X${item.itemQuantity}</td>
							</tr></c:forEach></tbody>
						</table>
					</td></tr>
					<tr><td colspan="3">
						<label>包房：</label>${mealOrder.mealTab.tabName}<span>¥<fmt:formatNumber value="${mealOrder.mealTab.deposit==null?0:mealOrder.mealTab.deposit}" type="currency" pattern="#######.##"/></span><br/>
						<label>服务费：</label><span>¥<fmt:formatNumber value="${mealOrder.serviceFee}" type="currency" pattern="#######.##"/></span><br/>
						<label>优惠券：</label><span>¥<fmt:formatNumber value="${mealOrder.promotionFee == null ? 0:mealOrder.promotionFee}" type="currency" pattern="#######.##"/></span><br/>
						<label>实付金额：</label><span>¥<b><fmt:formatNumber value="${mealOrder.totalFee}" type="currency" pattern="#######.##"/></b></span><br/>
					</td></tr>
					<tr><td colspan="3">&nbsp;</td></tr>
					<c:if test="${mealOrder.contactName != null && mealOrder.contactName != ''}">
						<tr><td><label>联系人信息：</label>${mealOrder.contactName}</td>
							<td>${mealOrder.contactMobile}</td>
						</tr>
					</c:if>
					
					<c:if test="${mealOrder.cancelReason != null}">
						<tr><td colspan="3">&nbsp;</td></tr>
						<tr><td colspan="3">
							<label>取消原因：</label>
							<span>${mealOrder.cancelReason}</span><br/>
						</td></tr>
					</c:if>
					
					<tr><td colspan="3">&nbsp;</td></tr>
						<tr><td colspan="3">
							<label>失败原因：</label>
							<span>${mealOrder.errorMsg}</span><br/>
						</td></tr>
					
					<tr><td colspan="3">&nbsp;</td></tr>
					<tr><td></td>
						<td colspan="2">
							<button type="button" class="btn default goback">返回</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/jquery.qrcode.min.js"></script>
<jsp:include page="/common/bootbox.jsp" />