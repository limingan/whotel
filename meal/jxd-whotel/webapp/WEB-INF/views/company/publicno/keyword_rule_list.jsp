<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 关键词自动回复</title>
</head>
<c:set var="cur" value="sel-publicno" scope="request"/>
<c:set var="cur_sub" value="sel-keywords" scope="request"/>
<div class="page-content-wrapper">
	<div class="page-content">
		
		<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i>
							<a href="/company/index.do">首页</a>
							<i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="/company/publicno/listKeywordRule.do">自动回复关键词管理</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			     <form action="/company/publicno/listKeywordRule.do" id="pageForm" method="post">
		    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
				</form>
				<div class="table-toolbar">
					<a href="/company/publicno/toEditKeywordRule.do" class="btn btn-sm green"><i class="fa fa-plus"></i>新增规则</a>
					<a href="javascript:deleteMoreKeywordRule()" class="btn btn-sm default"><i class="fa fa-times"></i>批量删除</a>
				</div>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				
				<tr role="row">
				    <th width="5%" class="table-checkbox sorting_disabled" role="columnheader">
					  <div class="checker"><span><input type="checkbox" class="group-checkable js-allCheckbox"></span></div>
				    </th>
				    <th width="5%">序号</th>
					<th width="20%">名称</th>
					<th width="8%">系统默认</th>
					<th width="30%">关键词</th>
					<th width="8%">回复类型</th>
					<th width="15%">创建时间</th>
					<th width="13%">操作</th>
				</tr>
				</thead>
				<tbody>
				   <c:forEach items="${page.result}" var="keywordRule" varStatus="vs">
				   	  <c:choose>
							<c:when test="${keywordRule.def == true}">
								<tr class="odd gradeX">
						   	     <td>
								   <div class="checker"><span><input type="checkbox" class="checkboxes js-listCheckbox" value="${keywordRule.id}"></span></div>
								</td>
						   	    <td>${vs.count}</td>
								<td>${keywordRule.name}</td>
								<td>
									是
								</td>
								<td colspan="2">
									${keywordRule.responseMsg.text}
								</td>
								<td><fmt:formatDate value="${keywordRule.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>
									<a href="toEditKeywordRule.do?id=${keywordRule.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
							    </td>
							  </tr>
							</c:when>
							<c:otherwise>
								<tr class="odd gradeX">
						   	     <td>
								   <div class="checker"><span><input type="checkbox" class="checkboxes js-listCheckbox" value="${keywordRule.id}"></span></div>
								</td>
						   	    <td>${vs.count}</td>
								<td>${keywordRule.name}</td>
								<td>
									<c:choose>
										<c:when test="${keywordRule.def == true}">
										是
										</c:when>
										<c:otherwise>
										否
										</c:otherwise>
									</c:choose>
								</td>
								<td>
								<c:forEach items="${keywordRule.keywords}" var="keyword">
									${keyword.keyword} [${keyword.type.label}] [匹配：<b style="color:red">${keyword.count}</b>次]<br>
								</c:forEach>
								</td>
								<td>${keywordRule.responseMsg.responseType.label}</td>
								<td><fmt:formatDate value="${keywordRule.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>
									<a href="toEditKeywordRule.do?id=${keywordRule.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i> 编辑</a>
									<a href="javascript:deleteKeywordRule('${keywordRule.id}')" class="btn btn-sm default"><i class="fa fa-times"></i> 删除</a>
							    </td>
							  </tr>
							</c:otherwise>
					 </c:choose>
				   </c:forEach>
				</tbody>
				</table>
				</div>
				<div class="row">
					${page.pageNavigation}
				</div>
		</div>
</div>
<script src="/static/common/js/checkAll.js" type="text/javascript"></script>
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/bootbox.jsp" />
<script type="text/javascript">
    
    var checkTool = new checkTools();
	$(function() {
		checkTool.init();
	});
	
	function deleteKeywordRule(ids) {
		var url = "/company/publicno/deleteKeywordRule.do?ids="+ids;
		confirmDeleteData("真的需要删除吗？", url);
	}
	
	function deleteMoreKeywordRule() {
		var ids = checkTool.getAllCheckValues();
		if(ids.length > 0) {
			deleteKeywordRule(ids);
		} else {
			boxAlert("您至少需要选择一条数据!");
		}
	}
</script>