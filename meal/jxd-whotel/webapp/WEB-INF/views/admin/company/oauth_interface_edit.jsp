<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 授权接口配置</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/admin/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>授权接口配置</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateOauthInterface.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${oauthInterface.id}"/>
				<input type="hidden" name="companyId" id="js_companyId" value="${oauthInterface.companyId}"/>
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-3 control-label">地址代码</label>
						<div class="col-md-4">
						<input type="text" name="code" id="js_code" class="form-control js_trim" placeholder="地址代码" maxlength="200" required="required" value="${oauthInterface.code}" onchange="ajaxOauthInterfaceCodeExist()"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">跳转地址</label>
						<div class="col-md-4">
						<input type="url" name="url" class="form-control js_trim" placeholder="跳转地址" maxlength="200" required="required" value="${oauthInterface.url}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">秘钥</label>
						<div class="col-md-4">
						<input type="text" name="secretKey" id="js_secretKey" class="form-control js_trim" placeholder="16位大小写字母加数字" maxlength="16" required="required" value="${oauthInterface.secretKey}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">状态</label>
						<div class="col-md-4">
							<select class="form-control" name="enable">
								<option value="false" <c:if test="${oauthInterface.enable == false}">selected</c:if>>禁用</option> 
								<option value="true" <c:if test="${oauthInterface.enable == true}">selected</c:if>>启用</option> 
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
<input id="js_isSubmit"type="hidden" value="true"/>
<%--内容区域 结束--%>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script>
$(function() {
	$("#submitForm").submit(function(){
		
		if($("#js_secretKey").val().length!=16){
			alert("秘钥必须16位");
			return false;
		}

		ajaxOauthInterfaceCodeExist();
		if($("#js_isSubmit").val() == 'false'){
			return false;
		}
		
		$(".js_trim").each(function(){
			$(this).val($(this).val().trim());
		});
	});
});

function ajaxOauthInterfaceCodeExist(){
	var code = $("#js_code").val();
	var companyId = $("#js_companyId").val();
	$.ajax({
		url:"/admin/company/ajaxOauthInterfaceCodeExist.do",
		type : 'post',
		data:{"companyId":companyId,"code":code},
		dataType:"json",
		async:false,
		success:function(data) {
			if(data == false){
				$("#js_isSubmit").val("false");
				alert("地址代码不允许重复值");
			}else{
				$("#js_isSubmit").val("true");
			}
		}
	});
}
</script>
<jsp:include page="/common/bootbox.jsp" />