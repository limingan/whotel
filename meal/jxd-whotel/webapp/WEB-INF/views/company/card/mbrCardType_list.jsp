<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 优惠券类型列表</title>
</head>
<c:set var="cur" value="sel-webcard" scope="request"/>
<c:set var="cur_sub" value="sel-webcard-mbrCardType" scope="request"/>
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
							<a href="/company/card/listCouponTypes.do">会员卡类型列表</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr role="row">
				    <th width="5%">序号</th>
				    <th width="10%">代码</th>
					<th width="25%">名称</th>
					<th width="25%">售卖金额</th>
					<th width="25%">余额</th>
					<th width="10%">操作</th>
				</tr>
				</thead>
				<tbody> 
				   <c:forEach items="${mbrCardTypes}" var="mbrCardType" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${mbrCardType.code}</td>
						<td>${mbrCardType.name}</td>
						<td>${mbrCardType.salePrice}</td>
						<td>${mbrCardType.chargeAmt}</td>
						<td>
						    <a href="toEditMbrCardType.do?code=${mbrCardType.code}&name=${mbrCardType.name}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
					    </td>
					  </tr>
				   </c:forEach>
				</tbody>
				</table>
				</div>
		</div>
</div>
<jsp:include page="/common/bootbox.jsp" />