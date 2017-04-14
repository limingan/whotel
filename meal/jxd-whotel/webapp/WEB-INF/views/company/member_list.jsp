<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 商户会员管理</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<c:set var="cur_sub" value="sel-company-member" scope="request"/>
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
							<a href="/company/listWeixinFan.do">商户会员管理</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			     <form action="/company/listMember.do" id="pageForm" method="post">
		    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
		    	
		    	<div class="table-toolbar">
					<div class="input-group"> 
						<label>姓名:</label>
						<input name="params[name]" class="form-control input-medium" style="height:28px" type="text" value="${queryParam.params.name}">
						<span>&nbsp;&nbsp;&nbsp;</span>
						<label>手机:</label>
						<input name="params[mobile]" id="js_mobile" class="form-control input-medium" style="height:28px" type="text" value="${queryParam.params.mobile}">
						<span>&nbsp;&nbsp;&nbsp;</span>
						<label>开始时间:</label>
						<input name="params[startDate]" id="js_startDate" value="${queryParam.params.startDate}" class="form-control input-medium" onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						<span>&nbsp;&nbsp;&nbsp;</span>
						<label>结束时间:</label>
						<input name="params[endDate]" id="js_endDate" value="${queryParam.params.endDate}" class="form-control input-medium" onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						<span>&nbsp;&nbsp;&nbsp;</span>
						<button type="submit" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
						<span>&nbsp;&nbsp;&nbsp;</span>
						<a href="javascript:download()" class="btn btn-sm yellow"><i class="fa fa-file-o"></i>导出Excel</a>
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
					<th width="20%">操作</th>
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
						<td><fmt:formatDate value="${member.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
						 <span style="position:relative;"> 
						    <a data-url="${member.openId}" href="javascript:" class="btn btn-sm green copy_btn"><i class="fa fa-file-o"></i> 复制openId</a>
						    <a href="/company/deleteMember.do?id=${member.id}" class="btn btn-sm default"><i class="fa fa-times"></i>删除</a>
						 </span>
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
<script src="/static/metronic/assets/plugins/datetimepicker/laydate.js" type="text/javascript" ></script>
<script src="/static/common/js/zclip/jquery.zclip.min.js"></script>
<script src="/static/common/js/copy.js"></script>
<script>
function download(){
	window.location.href = '/company/downloadMember.do?mobile='+$("#js_mobile").val()+'&startDate='+$("#js_startDate").val()+'&endDate='+$("#js_endDate").val();
}
</script>
<jsp:include page="/common/bootbox.jsp" />
