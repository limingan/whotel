<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 微网站管理</title>
</head>
<c:set var="cur" value="sel-website" scope="request"/>
<c:set var="cur_sub" value="sel-theme-list" scope="request"/>
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
							<a href="/company/website/listTheme.do">微网站</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			     <form action="/company/website/listTheme.do" id="pageForm" method="post">
			    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
				</form>
			    
				<div class="table-toolbar">
					<a href="/company/website/selectThemeTemplate.do" class="btn btn-sm green"><i class="fa fa-plus"></i>新增主题</a>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr role="row">
				    <th width="5%">序号</th>
					<th width="10%">名称</th>
					<th width="25%">banner图</th>
					<th width="10%">栏目</th>
					<th width="5%">状态</th>
					<th width="14%">创建时间</th>
					<th width="28%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="theme" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${theme.name}</td>
						<td>
						 <c:forEach items="${theme.bannerUrls}" var="bannerUrl" varStatus="vs">
                   			   <img src="${bannerUrl}" width="50" height="80" />
						 </c:forEach>
						</td>
						<td>
						<c:forEach items="${theme.columns}" var="column">
							${column.name}(${column.type.label})<br>
						</c:forEach>
						</td>
						<td>${theme.enable ? "启用":"停用"}</td>
						<td><fmt:formatDate value="${theme.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
						<span style="position:relative;"> 
							<c:if test="${theme.enable}">
							<a href="javascript:setThemeEnable('${theme.id}', false)" class="btn btn-sm default"><i class="fa fa-power-off"></i> 停用</a>
							</c:if>
							<c:if test="${!theme.enable}">
							<a href="javascript:setThemeEnable('${theme.id}', true)" class="btn btn-sm yellow"><i class="fa fa-power-off"></i> 启用</a>
							</c:if>
							<a href="selectThemeTemplate.do?themeId=${theme.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
							<a href="javascript:deleteTheme('${theme.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
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
<jsp:include page="/common/bootbox.jsp" />
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/zclip/jquery.zclip.min.js"></script>
<script src="/static/common/js/copy.js"></script>
<script>
function deleteTheme(id) {
	var url = "/company/website/deleteTheme.do?ids="+id;
	confirmDeleteData("真的需要删除吗？", url);
}

function setThemeEnable(id, enable) {
	var url = "setThemeEnable.do?id="+id+"&enable="+enable;
	var msg = "";
	if(enable == true) {
		msg = "真的需要启用吗？如果启用会停用正在使用的主题！";
	} else {
		msg = "真的需要停用吗？";
	}
	confirmDeleteData(msg, url);
}
</script>