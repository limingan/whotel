<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 商户信息注册</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/admin/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>商户信息注册</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="companyRegister.do" class="form-horizontal" method="post" id="submitForm">
				<div class="form-body">
					
					<div class="form-group first">
						<label class="col-md-3 control-label">编码</label>
						<div class="col-md-4">
						<input name="code" class="form-control js_trim" placeholder="编码" maxlength="50"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">名称</label>
						<div class="col-md-4">
						   <input name="companyName" class="form-control js_trim" placeholder="名称" maxlength="50"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">登录账号</label>
						<div class="col-md-4">
						<input name="account" class="form-control js_trim" placeholder="登录账号" maxlength="50"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">密码</label>
						<div class="col-md-4">
						<input class="form-control js_trim" type="password" placeholder="密码" name="password" id="password" maxlength="50"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">确认密码</label>
						<div class="col-md-4">
						<input class="form-control js_trim" type="password" placeholder="确认密码" name="repassword"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">邮箱</label>
						<div class="col-md-4">
						<input name="email" class="form-control js_trim" placeholder="邮箱" maxlength="50"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">集团</label>
						<div class="col-md-4">
						<label class="radio-inline">
						<span><input type="radio" name="group" value="false" checked></span> 否</label>
						<label class="radio-inline">
						<input type="radio" name="group" value="true"> 是</label>
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

<script src="/static/admin/js/companyRegister.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<%--内容区域 结束--%>
<script>
	$(function() {
		CompanyRegister.init();
		
		$("#submitForm").submit(function(){
			$(".js_trim").each(function(){
				$(this).val($(this).val().trim());
			});
		});
	});
</script>

<jsp:include page="/common/bootbox.jsp" />