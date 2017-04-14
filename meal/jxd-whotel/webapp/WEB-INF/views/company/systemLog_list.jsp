<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 商户员工管理</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<c:set var="cur_sub" value="sel-syslog" scope="request"/>
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
							<a href="">微商城操作日志</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			    <form action="listSysOperationLogs.do" id="pageForm" method="post">
		    		<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
		    		<div class="input-group">	
						<label>操作对象</label>
						<select name="params[moduleType]" class="form-control input-medium">
							<%-- <c:forEach var="moduleType" items="${company.moduleTypes}">
								<option value="${moduleType}" <c:if test="${queryParam.params.moduleType == moduleType}">selected="selected"</c:if>>${moduleType.label}</option>
							</c:forEach> --%>
							<option value="">全部</option>
							<option value="Login" <c:if test="${queryParam.params.moduleType == 'Login'}">selected="selected"</c:if>>登录</option>
							<option value="SYSTEM" <c:if test="${queryParam.params.moduleType == 'SYSTEM'}">selected="selected"</c:if>>系统</option>
							<option value="MALL" <c:if test="${queryParam.params.moduleType == 'MALL'}">selected="selected"</c:if>>商城</option>
						</select>
						<span>&nbsp;&nbsp;</span>
						<button type="submit" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
					</div>
				</form>
				<!-- <div class="table-toolbar">
					<a href="/company/toEditCompanyAdmin.do" class="btn btn-sm green"><i class="fa fa-plus"></i>新增员工</a>
				</div> -->
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				
				<tr role="row">
				    <th width="5%">序号</th>
					<th width="15%">操作人姓名</th>
					<th width="15%">操作类型</th>
					<th width="50%">说明</th>
					<th width="15%">时间</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="log" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${log.companyAdmin.name}</td>
						<td><c:choose>
							<c:when test="${log.type=='add'}">新增</c:when>
							<c:when test="${log.type=='update'}">修改</c:when>
							<c:when test="${log.type=='delete'}">删除</c:when>
							<c:otherwise>${log.type}</c:otherwise>
						</c:choose></td>
						<td>${log.remark}</td>
						<td><fmt:formatDate value="${log.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
