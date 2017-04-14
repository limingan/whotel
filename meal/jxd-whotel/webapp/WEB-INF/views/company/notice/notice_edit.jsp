<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 商户公告编辑</title>
</head>
<c:set var="cur" value="sel-notice" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>商户公告编辑</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateCompanyNotice.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${notice.id}"/>
				<div class="form-body">
					<div class="form-group first">
						<label class="col-md-3 control-label">标题</label>
						<div class="col-md-4">
						   <input name="title" class="form-control" placeholder="标题" maxlength="50" value="${notice.title}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">公告内容</label>
						<div class="col-md-4">
						<textarea name="content" rows="10" cols="80" class="form-control">${notice.content}</textarea>
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
<script src="/static/company/js/notice.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	Notice.init();
});
</script>
<jsp:include page="/common/bootbox.jsp" />