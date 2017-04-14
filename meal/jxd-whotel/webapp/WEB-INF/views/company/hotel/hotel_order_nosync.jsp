<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 酒店订单列表</title>
</head>
<c:set var="cur" value="sel-order" scope="request"/>
<c:set var="cur_sub" value="sel-all-order" scope="request"/>
<div class="page-content-wrapper">
	<div class="page-content">
		
		<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				<!-- 	<h3 class="page-title">
					Managed Datatables <small>managed datatable samples</small>
					</h3>  -->
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i>
							<a href="/company/index.do">首页</a>
							<i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="/company/hotel/listHotels.do">酒店订单列表</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
				<form action="" id="pageForm" method="post">
		    		<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">		    		
					<div class="input-group">	
						<label>订单类型</label>
						<select name="params[orderType]" class="form-control input-medium js_orderType" onchange="orderType()">
							<option value="hotel" <c:if test="${queryParam.params.orderType == 'hotel'}">selected="selected"</c:if>>酒店</option> 
							<option value="ticket" <c:if test="${queryParam.params.orderType == 'ticket'}">selected="selected"</c:if>>门票</option>
						</select>
						<span>&nbsp;&nbsp;</span>	
						<label>订单号</label>
						<input name="params[orderSn]" class="form-control input-medium" maxlength="50" value="${queryParam.params.orderSn}"/>
						<span>&nbsp;&nbsp;</span>			
						<label>预订人</label>
						<input name="params[contactName]" class="form-control input-medium" maxlength="50" value="${queryParam.params.contactName}"/>
						<span>&nbsp;&nbsp;&nbsp;</span>
						<label>开始日期</label>
						<input type="text" class="form-control input-medium date-picker" name="params[beginDate]" value='${queryParam.params.beginDate}'>
						<span>&nbsp;&nbsp;</span>
						<label>结束日期</label>
						<input type="text" class="form-control input-medium date-picker" name="params[endDate]" value='${queryParam.params.endDate}'>
						<span>&nbsp;&nbsp;</span>	
						<button type="submit" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
					</div>
				</form>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr role="row">
					<th width="2%">序号</th>
					<th width="8%">订单名</th>
					<th width="8%">订单号</th>
					<th width="6%">预订人</th>
					<th width="6%">手机</th>
					<th width="5%">金额</th>
					<th width="10%">支付类型</th>
					<!-- <th width="10%">订单类型</th> -->
					<th width="12%">同步状态</th>
					<th width="25%">失败原因</th>
					<th width="10%">创建时间</th>
					<th width="8%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="payOrder" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${payOrder.name}</td>
						<td>${payOrder.orderSn}</td>
						<td>${payOrder.contactName}</td>
						<td>${payOrder.contactMobile}</td>
						<td>¥<fmt:formatNumber value="${payOrder.totalFee}" type="currency" pattern="#######.##"/></td>
						<td>${payOrder.payMent.label}</td>
						<%-- <td>${payOrder.payMode.label}</td> --%>
						<td>${payOrder.synchronizeState==false?"同步失败":"同步成功"}
						</td>
						<td>${payOrder.errorMsg}</td>
						<td><fmt:formatDate value="${payOrder.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
						<td style="position:relative;">
							<a href="${queryParam.params.orderType==null?'hotel':queryParam.params.orderType}OrderSync.do?orderSn=${payOrder.orderSn}" class="btn btn-sm purple"><i class="fa fa-edit"></i>同步</a>
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
<jsp:include page="/common/bootbox.jsp" />
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/zclip/jquery.zclip.min.js"></script>
<script src="/static/common/js/copy.js"></script>
<script src="/static/metronic/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript" ></script>
<script src="/static/metronic/assets/scripts/datePicker.js?v=${version}" type="text/javascript"></script>
<script src="/static/metronic/assets/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js" type="text/javascript" ></script>
<script type="text/javascript">
$(function(){
	DatePicker.init();
	
	orderType();
});

function orderType(){
	var type = $(".js_orderType").val();
	if(type == 'ticket'){
		$("#pageForm").attr("action","/company/ticket/listOrderTickets.do");
	}else{
		$("#pageForm").attr("action","/company/hotel/listOrderHotels.do");
	}
}
</script>