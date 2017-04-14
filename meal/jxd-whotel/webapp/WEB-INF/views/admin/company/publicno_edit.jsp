<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 公众号信息</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/admin/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>公众号信息</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updatePublicNo.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" id="publicNoId" name="id" value="${publicNo.id}"/>
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
						<label class="col-md-3 control-label">名称</label>
						<div class="col-md-4">
						<input name="name" class="form-control js_trim" placeholder="公众号名称" maxlength="200" value="${publicNo.name}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">原始ID</label>
						<div class="col-md-4">
						<input id="developerCode" name="developerCode" class="form-control js_trim" placeholder="原始ID" maxlength="200" value="${publicNo.developerCode}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">账号</label>
						<div class="col-md-4">
						<input id="account" name="account" class="form-control js_trim" placeholder="账号" maxlength="200" value="${publicNo.account}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">密码</label>
						<div class="col-md-4">
						<input name="pwd" class="form-control js_trim" placeholder="密码" maxlength="100" value="${publicNo.pwd}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">微信号</label>
						<div class="col-md-4">
						<input id="account" name="weixinId" class="form-control js_trim" placeholder="微信号" maxlength="200" value="${publicNo.weixinId}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">appId</label>
						<div class="col-md-4">
						<input name="appId" class="form-control js_trim" placeholder="应用appId" maxlength="100" value="${publicNo.appId}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">appSecret</label>
						<div class="col-md-4">
						<input name="appSecret" class="form-control js_trim" placeholder="应用密钥" maxlength="100" value="${publicNo.appSecret}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">类型</label>
						<div class="col-md-4">
							<select class="form-control" name="type">
								<option value="SERVICE" <c:if test="${publicNo.type == 'SERVICE'}">selected</c:if>>服务号</option> 
								<option value="SUBSCRIBE" <c:if test="${publicNo.type == 'SUBSCRIBE'}">selected</c:if>>订阅号</option> 
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">认证</label>
						<div class="col-md-4">
							<select class="form-control" name="auth">
								<option value="false" <c:if test="${publicNo.auth == false}">selected</c:if>>未认证</option> 
								<option value="true" <c:if test="${publicNo.auth == true}">selected</c:if>>已认证</option> 
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">对接Token</label>
						<div class="col-md-4">
						<input name="authToken" class="form-control js_trim" placeholder="对接Token" maxlength="100" value="${publicNo.authToken}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">对接地址</label>
						<div class="col-md-7">
						    <p class="form-control-static">
								${weixinCallHost}?cid=${company.id}
							</p>
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
<script src="/static/admin/js/publicno.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	PublicNo.init();
	
	$("#submitForm").submit(function(){
		$(".js_trim").each(function(){
			$(this).val($(this).val().trim());
		});
	});
});
</script>
<%--内容区域 结束--%>
<jsp:include page="/common/bootbox.jsp" />