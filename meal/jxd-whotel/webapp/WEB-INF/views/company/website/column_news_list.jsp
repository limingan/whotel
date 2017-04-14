<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 栏目图文</title>
</head>
<c:set var="cur" value="sel-website" scope="request"/>
<c:set var="cur_sub" value="sel-column-news" scope="request"/>
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
							<a href="/company/website/listColumnNews.do">栏目图文</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			     <form action="/company/website/listColumnNews.do" id="pageForm" method="post">
			    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
				</form>
			    
				<div class="table-toolbar">
					<a href="/company/website/toEditColumnNews.do" class="btn btn-sm green"><i class="fa fa-plus"></i>新增栏目图文</a>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr role="row">
				    <th width="5%">序号</th>
					<th width="30%">名称</th>
					<th width="10%">模板</th>
					<th width="15%">创建时间</th>
					<th width="32%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="columnNews" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${columnNews.name}</td>
						<td>
						 <img src="${columnNews.template.thumbnailUrl}" width="80" height="120"/>
						</td>
						<td><fmt:formatDate value="${columnNews.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
						 <span style="position:relative;"> 
						    <a data-url="${DOMAIN}/front/listNewsArticle.do?newsId=${columnNews.id}" href="javascript:" class="btn btn-sm green copy_btn	"><i class="fa fa-link"></i> 复制外链</a>
						    <a href="listNewsArticle.do?newsId=${columnNews.id}" class="btn btn-sm yellow"><i class="fa fa-book"></i> 文章管理</a>
							<a href="toEditColumnNews.do?id=${columnNews.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
							<a href="javascript:deleteColumnNews('${columnNews.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
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
function deleteColumnNews(id) {
	var url = "/company/website/deleteColumnNews.do?ids="+id;
	confirmDeleteData("图文下的文章会全部删除，真的需要删除吗？", url);
}
</script>