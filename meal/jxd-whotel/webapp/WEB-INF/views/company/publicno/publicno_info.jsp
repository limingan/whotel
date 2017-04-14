<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 公众号信息</title>
</head>
<c:set var="cur" value="sel-publicno" scope="request"/>
<c:set var="cur_sub" value="sel-publicno-info" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>公众号信息</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updatePublicNo.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${publicNo.id}"/>
				<input type="hidden" name="auth" value="${publicNo.auth}"/>
				<div class="form-body">
					
					<div class="form-group first">
						<label class="col-md-3 control-label">名称</label>
						<div class="col-md-4">
						<p class="form-control-static">${publicNo.name}</p>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">原始ID</label>
						<div class="col-md-4">
						<p class="form-control-static">${publicNo.developerCode}</p>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">账号</label>
						<div class="col-md-4">
						<p class="form-control-static">${publicNo.account}</p>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">appId</label>
						<div class="col-md-4">
						<p class="form-control-static">${publicNo.appId}</p>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">类型</label>
						<div class="col-md-4">
							<p class="form-control-static">${publicNo.type.label}</p>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">认证</label>
						<div class="col-md-4">
							<p class="form-control-static">
							<c:choose>
								<c:when test="${publicNo.auth == false}">未认证</c:when>
								<c:when test="${publicNo.auth == true}">已认证</c:when>
							</c:choose>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">微信号</label>
						<div class="col-md-4">
						<input id="account" name="weixinId" class="form-control" placeholder="微信号" maxlength="200" value="${publicNo.weixinId}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">关注链接</label>
						<div class="col-md-4">
						<textarea name="attentionUrl" rows="4" cols="60" class="form-control" id="js-attentionUrl">${publicNo.attentionUrl}</textarea>
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
<script src="/static/company/js/publicno.js?v=${version}" type="text/javascript"></script>
<script>
$(function() {
	Publicno.init();
});
</script>
<jsp:include page="/common/bootbox.jsp" />