<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 优惠券类型列表</title>
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
</head>
<c:set var="cur" value="sel-webcard" scope="request"/>
<c:set var="cur_sub" value="sel-webcard-couponType" scope="request"/>
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
							<a href="/company/webcard/listCouponTypes.do">券类型列表</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
				<div class="table-toolbar">
					<a href="/company/webcard/sendCouponRecord.do" class="btn btn-sm green"><i class="fa fa-plus"></i>派劵记录</a>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<a href="javascript:synchronousData()" class="btn btn-sm green"><i class="fa fa-plus"></i>同步数据</a>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr role="row">
				    <th width="5%">序号</th>
				    <th width="20%">代码</th>
					<th width="55%">名称</th>
					<th width="10%">操作</th>
				</tr>
				</thead>
				<tbody> 
				   <c:forEach items="${couponTypes}" var="couponType" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${couponType.code}</td>
						<td>${couponType.name}</td>
						<td>
						    <a href="toEditCouponType.do?id=${couponType.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
							<a href="javascript:deleteNotice('${couponType.id}')" class="btn btn-sm default"><i class="fa fa-times"></i>删除</a>
					    </td>
					  </tr>
				   </c:forEach>
				</tbody>
				</table>
				</div>
		</div>
</div>

<div class="loading" id="loading"></div>
<jsp:include page="/common/bootbox.jsp" />
<script type="text/javascript">
var $loading = $("#loading");
function synchronousData(){
	$loading.show();
	window.location.href="/company/webcard/synchronizeCouponType.do";
}

function deleteNotice(id) {
	var url = "/company/webcard/deleteCouponType.do?id="+id;
	confirmDeleteData("真的需要删除吗？", url);
}
</script>
