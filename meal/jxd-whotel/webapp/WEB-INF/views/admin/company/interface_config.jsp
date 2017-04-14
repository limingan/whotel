<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 数据接口配置</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/admin/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>数据接口配置</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateInterfaceConfig.do" class="form-horizontal" method="post" id="submitForm">
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
						<label class="col-md-3 control-label">使用渠道</label>
						<div class="col-md-4">
							<select class="form-control" name="channel" id="js_channel" onchange="showInput()">
								<option value="e" <c:if test="${config.channel == 'e'}">selected</c:if>>E云通</option> 
								<option value="pms" <c:if test="${config.channel == 'pms'}">selected</c:if>>PMS</option> 
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">服务地址</label>
						<div class="col-md-4">
						<input name="host" class="form-control js_trim" placeholder="服务地址" maxlength="200" value="${config.host}"/>
						</div>
					</div>
					
					<div class="form-group" id="js_eyuntong">
						<label class="col-md-3 control-label">接口列表地址</label>
						<div class="col-md-4">
						<input name="url" class="form-control js_trim" placeholder="服务地址" maxlength="200" value="${config.url}"/>
						</div>
					</div>
					
					<div class="form-group" id="js_bspms">
						<label class="col-md-3 control-label">秘钥</label>
						<div class="col-md-4">
						<input name="sign" class="form-control js_trim" placeholder="安全秘钥" maxlength="200" value="${config.sign}"/>
						</div>
					</div>
					
					<div class="form-group" id="js_msunlock">
						<label class="col-md-3 control-label">门锁地址</label>
						<div class="col-md-4">
						<input name="unlock" class="form-control js_trim" placeholder="门锁地址" maxlength="200" value="${config.unlock}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">状态</label>
						<div class="col-md-4">
							<select class="form-control" name="enable">
								<option value="false" <c:if test="${config.enable == false}">selected</c:if>>禁用</option> 
								<option value="true" <c:if test="${config.enable == true}">selected</c:if>>启用</option> 
							</select>
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
<%--内容区域 结束--%>
<script src="/static/common/js/jquery-1.11.2.js"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script>
$(function() {
	$("#submitForm").submit(function(){
		$(".js_trim").each(function(){
			$(this).val($(this).val().trim());
		});
	});
	showInput();
});

function showInput(){
	if($("#js_channel").val() == 'pms'){
		$("#js_eyuntong").hide();
		$("#js_bspms").show();
		$("#js_msunlock").show();
	}else{
		$("#js_eyuntong").show();
		$("#js_bspms").hide();
		$("#js_msunlock").hide();
	}
}
</script>
<jsp:include page="/common/bootbox.jsp" />