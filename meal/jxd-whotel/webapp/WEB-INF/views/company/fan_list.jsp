<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 商户粉丝管理</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<c:set var="cur_sub" value="sel-company-fan" scope="request"/>
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
							<a href="/company/listWeixinFan.do">商户粉丝管理</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
					<form action="/company/listWeixinFan.do" id="pageForm" method="post">
		    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
		    	
		    	<div class="table-toolbar">
					<div class="input-group"> 
						<label>昵称:</label>
						<input name="params[nickname]" class="form-control input-medium" style="height:28px" type="text" value="${queryParam.params.nickname}">
						<span>&nbsp;&nbsp;&nbsp;</span>
						<label>开始时间:</label>
						<input name="params[startDate]" value="${queryParam.params.startDate}" class="form-control input-medium" onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						<span>&nbsp;&nbsp;&nbsp;</span>
						<label>结束时间:</label>
						<input name="params[endDate]" value="${queryParam.params.endDate}" class="form-control input-medium" onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
						<span>&nbsp;&nbsp;&nbsp;</span>
						<button onclick="pageFormSubmit()" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
					</div>
				</div>
		    	
				</form>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				
				<tr role="row">
				    <th width="5%">序号</th>
					<th width="15%">昵称</th>
					<th width="7%">头像</th>
					<th width="14%">创建时间</th>
					<th width="10%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="fan" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${fan.nickName}</td>
						<td><img alt="头像" src="${fan.avatar}" width="60" height="40"></td>
						<td><fmt:formatDate value="${fan.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
						 <span style="position:relative;"> 
						    <a data-url="${fan.openId}" href="javascript:" class="btn btn-sm green copy_btn"><i class="fa fa-file-o"></i> 复制openId</a>
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
<jsp:include page="/common/bootbox.jsp" />
<script type="text/javascript">
function pageFormSubmit(){
	$("#pageNo").val("1");
	$("#pageForm").submit();
}
</script>


