<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 酒店列表</title>
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
</head>
<c:set var="cur" value="sel-whotel" scope="request"/>
<c:set var="cur_sub" value="sel-hotel-list" scope="request"/>
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
							<a href="/company/hotel/listHotels.do">酒店列表</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
				<div class="table-toolbar">
					<a href="javascript:synchronousData()" class="btn btn-sm green"><i class="fa fa-plus"></i>同步数据</a>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr role="row">
				    <th width="5%">序号</th>
				    <th width="10%">分店代码</th>
					<th width="10%">名称</th>
					<th width="10%">电话</th>
					<th width="10%">城市</th>
					<th width="27%">地址</th>
					<th width="28%">操作</th>
				</tr>
				</thead>
				<tbody> 
				   <c:forEach items="${hotels}" var="hotel" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${hotel.code}</td>
						<td>${hotel.name}</td>
						<td>${hotel.tel}</td>
						<td>${hotel.city}</td>
						<td>${hotel.address}</td>
						<td>
						    <span style="position:relative;"> 
						    <a data-url="${DOMAIN}/oauth/hotel/showHotel.do?comid=${companyId}&code=${hotel.code}" href="javascript:" class="btn btn-sm green copy_btn"> 酒店介绍外链</a>
						    <a data-url="${DOMAIN}/oauth/hotel/listRooms.do?comid=${companyId}&hotelCode=${hotel.code}" href="javascript:" class="btn btn-sm yellow copy_btn"> 客房列表外链</a>
						    <a href="toEditHotel.do?code=${hotel.code}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
							</span>
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
<script src="/static/common/js/zclip/jquery.zclip.min.js"></script>
<script src="/static/common/js/copy.js"></script>
<script type="text/javascript">
var $loading = $("#loading");
function synchronousData(){
	$loading.show();
	window.location.href="/company/hotel/synchronizeHotel.do";
}
</script>