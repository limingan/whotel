<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 图文文章</title>
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
							<a href="/company/website/listNewsArticle.do?newsId=${param.newsId}">图文文章</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			     <form action="/company/website/listNewsArticle.do?newsId=${param.newsId}" id="pageForm" method="post">
			    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
				</form>
			    
				<div class="table-toolbar">
					<a href="/company/website/toEditNewsArticle.do?newsId=${param.newsId}" class="btn btn-sm green"><i class="fa fa-plus"></i>新增文章</a>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr role="row">
				    <th width="5%">序号</th>
					<th width="15%">标题</th>
					<th width="15%">所属图文</th>
					<th width="10%">封面</th>
					<th width="7%">类型</th>
					<th width="8%">排序</th>
					<th width="14%">创建时间</th>
					<th width="25%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="article" varStatus="vs">
				      <c:if test="${article.url != null && article.url != ''}">
				      <c:set var="articleUrl" value="${article.url}"/>
				      </c:if>
				      <c:if test="${article.url == null || article.url == ''}">
				      <c:set var="articleUrl" value="${DOMAIN}/front/showNewsArticle.do?id=${article.id}"/>
				      </c:if>
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${article.title}</td>
						<td>${article.columnNews.name}</td>
						<td><img alt="封面" src="${article.thumbnailUrl}" width="60" height="60"></td>
						<td>${article.url != null && article.url != "" ? "外链":"正文"}</td>
						<td>${article.orderIndex}</td>
						<td><fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
					    	 <span style="position:relative;"> 
							    <a data-url="${articleUrl}" href="javascript:" class="btn btn-sm green copy_btn"><i class="fa fa-link"></i> 复制外链</a>
							    <a href="toEditNewsArticle.do?id=${article.id}&newsId=${param.newsId}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
								<a href="javascript:deleteNewsArticle('${article.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
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
function deleteNewsArticle(id) {
	var url = "/company/website/deleteNewsArticle.do?ids="+id;
	confirmDeleteData("真的需要删除吗？", url);
}
</script>