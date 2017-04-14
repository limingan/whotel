<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 会员政策记录</title>
</head>
<c:set var="cur" value="sel-webcard" scope="request"/>
<c:set var="cur_sub" value="sel-webcard-memberPolicy" scope="request"/>
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
							<a href="/company/listWeixinFan.do">会员政策记录</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			     <form action="/company/webcard/toMemberPolicyRecord.do" id="pageForm" method="post">
		    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
		    	
		    	<div class="table-toolbar">
					<div class="input-group"> 
						<a href="/company/webcard/toEditMemberPolicy.do" class="btn btn-sm green"><i class="fa fa-plus"></i>会员政策</a>
						<span>&nbsp;&nbsp;&nbsp;</span>
						<label>姓名:</label>
						<input name="params[name]" class="form-control input-medium" style="height:28px" type="text" value="${queryParam.params.name}">
						<span>&nbsp;&nbsp;&nbsp;</span>
						<label>手机:</label>
						<input name="params[mobile]" class="form-control input-medium" style="height:28px" type="text" value="${queryParam.params.mobile}">
						<span>&nbsp;&nbsp;&nbsp;</span>
						<button type="submit" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
						<span>&nbsp;&nbsp;&nbsp;</span>
					</div>
				</div>
		    	
				</form>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				
				<tr role="row">
				    <th width="10%">序号</th>
					<th width="10%">姓名</th>
					<th width="10%">性别</th>
					<th width="10%">手机</th>
					<th width="10%">邮箱</th>
					<th width="15%">地址</th>
					<th width="15%">创建时间</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="member" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${member.name}</td>
						<td>${member.gender.label}</td>
						<td>${member.mobile}</td>
						<td>${member.email}</td>
						<td>${member.addr}</td>
						<td><fmt:formatDate value="${member.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
<script src="/static/metronic/assets/plugins/datetimepicker/laydate.js" type="text/javascript" ></script>
<script src="/static/common/js/zclip/jquery.zclip.min.js"></script>
<script src="/static/common/js/copy.js"></script>
<jsp:include page="/common/bootbox.jsp" />
