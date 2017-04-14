<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 短信配置</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<c:set var="cur_sub" value="sel-smsConfig" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>短信配置</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateSmsConfig.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${config.id}"/>
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
						<label class="col-md-3 control-label">短信余额</label>
						<div class="col-md-4">
						    <p class="form-control-static">
								<b id="smsBalance">${config.balance == null ? 0:config.balance}</b> 条  &nbsp; &nbsp; 
								<a href="javascript:refreshSmsBalance()">刷新</a>
							</p>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label"><span class="jxd_required">*</span>报警</label>
						<div class="col-md-4">
						<input name="alarmNum" class="form-control" placeholder="设置短信余额报警阀值(默认500)" maxlength="200" value="${config.alarmNum}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label"><span class="jxd_required">*</span>接收手机号</label>
						<div class="col-md-4">
						<input name="mobiles" class="form-control" placeholder="报警信息接收手机，多个手机号使用‘逗号’隔开" maxlength="200" value="${config.mobiles}"/>
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
<script src="/static/company/js/smsConfig.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<%--内容区域 结束--%>
<script>
$(function() {
	SmsConfig.init();
});

function refreshSmsBalance() {
	$.ajax({
		url:"/company/refreshSmsBalance.do",
		success:function(data) {
			$("#smsBalance").html(data);
		}
	});
}
</script>
<jsp:include page="/common/bootbox.jsp" />