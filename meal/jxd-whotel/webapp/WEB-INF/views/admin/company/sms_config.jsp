<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 短信接口配置</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/admin/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>短信接口配置</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateSmsConfig.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${config.id}"/>
				<input type="hidden" name="companyId" value="${company.id}"/>
				<div class="form-body">
					<div class="form-group first">
						<label class="col-md-3 control-label">商户</label>
						<div class="col-md-4">
						    <p class="form-control-static">
								${company.name}
							</p>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">账号</label>
						<div class="col-md-4">
						<input name="account" class="form-control" placeholder="账号" maxlength="200" value="${config.account}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">密码</label>
						<div class="col-md-4">
						<input name="pwd" class="form-control" type="text" placeholder="密码" maxlength="200" value="${config.pwd}"/>
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
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<%--内容区域 结束--%>
<jsp:include page="/common/bootbox.jsp" />