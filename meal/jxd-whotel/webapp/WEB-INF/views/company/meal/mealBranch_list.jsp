<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 分店列表</title>
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-meal-mealBranch" scope="request"/>
<div class="page-content-wrapper">
	<div class="page-content">
		<div class="row">
			<div class="col-md-12">
				<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i>
						<a href="/company/index.do">首页</a>
						<i class="fa fa-angle-right"></i>
					</li><li><a href="/company/meal/listMealBranch.do">分店列表</a></li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>
		<form action="/company/meal/listMealBranch.do" id="pageForm" method="post">
			<div class="portlet-body">
				<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
		    	<div class="input-group">
					<label>分店名称</label>
					<input name="params[name]" class="form-control input-medium" maxlength="50" value="${queryParam.params.name}"/>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<button type="submit" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable">
					<thead><tr role="row">
					    <th width="5%">序号</th>
					    <th width="5%">分店代码</th>
						<th width="10%">名称</th>
						<th width="10%">英文名</th>
						<th width="10%">电话</th>
						<th width="10%">城市</th>
						<th width="22%">地址</th>
						<th width="28%">操作</th>
					</tr></thead>
					<tbody> 
						<c:forEach items="${page.result}" var="mealBranch" varStatus="vs">
					   		<tr class="odd gradeX">
					   	  		<td>${vs.count}</td>
								<td>${mealBranch.code}</td>
								<td>${mealBranch.cname}</td>
								<td>${mealBranch.ename}</td>
								<td>${mealBranch.tel}</td>
								<td>${mealBranch.city}</td>
								<td>${mealBranch.address}</td>
								<td><span style="position:relative;"> 
							    	<%-- <a data-url="${DOMAIN}/oauth/hotel/showHotel.do?comid=${companyId}&code=${mealBranch.code}" href="javascript:" class="btn btn-sm green copy_btn"> 分店介绍外链</a> --%>
							    	<%-- <a data-url="${DOMAIN}/oauth/hotel/listRooms.do?comid=${companyId}&hotelCode=${mealBranch.code}" href="javascript:" class="btn btn-sm yellow copy_btn"> 餐厅列表外链</a> --%>
							    	<a href="toEditMealBranch.do?id=${mealBranch.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i>编辑</a>
								</span></td>
						  	</tr>
					 	</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="row">${page.pageNavigation}</div>
		</form>
	</div>
</div>
<jsp:include page="/common/bootbox.jsp" />
<script src="/static/common/js/zclip/jquery.zclip.min.js"></script>
<script src="/static/common/js/copy.js"></script>