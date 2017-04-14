<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 栏目链接编辑</title>
</head>
<c:set var="cur" value="sel-columnLink" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/admin/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>栏目链接编辑</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateColumnLink.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${columnLink.id}"/>
				<div class="form-body">
					<div class="form-group first">
						<label class="col-md-3 control-label">名称</label>
						<div class="col-md-4">
						   <input name="name" class="form-control" placeholder="名称" maxlength="50" value="${columnLink.name}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">链接</label>
						<div class="col-md-4">
							<input name="url" rows="10" cols="80" placeholder="链接" class="form-control" value="${columnLink.url}" />
						</div>
					</div>
				</div>
				<div class="form-actions fluid">
					<div class="col-md-offset-3 col-md-9">
						<button type="submit" class="btn blue">提交</button>
						<button type="button" class="btn default goback">取消</button>
					</div>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
</div>

<script src="/static/admin/js/columnlink.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	ColumnLink.init();
});
</script>
<jsp:include page="/common/bootbox.jsp" />