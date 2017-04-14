<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 修改管理员密码</title>
</head>
<c:set var="cur" value="sel-pwd" scope="request" />
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/admin/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>修改管理员密码</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="modifyPassword.do" class="form-horizontal" method="post" id="submitForm">
				<div class="form-body">
					<div class="form-group first">
						<label class="col-md-3 control-label">旧密码</label>
						<div class="col-md-4">
							<input type="password" name="oldPassword" class="form-control" placeholder="旧密码">
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-3 control-label">新密码</label>
						<div class="col-md-4">
							<input type="password" name="password" class="form-control" id="password" placeholder="密码">
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-3 control-label">确认密码</label>
						<div class="col-md-4">
							<input type="password" name="rePassword" class="form-control" id="repassword" placeholder="确认密码">
						</div>
					</div>

					<div class="form-actions fluid">
						<div class="col-md-offset-3 col-md-9">
							<button type="submit" class="btn blue">提交</button>
							<button type="button" class="btn default" onclick="history.go(-1)">取消</button>
						</div>
					</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
</div>
<script src="/static/admin/js/adminPwd.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script>

	$(function() {
		AdminPwd.init();
	});
</script>
<jsp:include page="/common/bootbox.jsp" />
