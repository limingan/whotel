<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 模版消息</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<c:set var="cur_sub" value="sel-template_msg" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>模版消息</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateTemplateMessage.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${templateMessage.id}"/>
				
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-3 control-label">模版ID</label>
						<div class="col-md-4">
							<input name="templateId" class="form-control js_trim" value="${templateMessage.templateId}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">消息名称</label>
						<div class="col-md-4">
							<select class="form-control" name="messageType" id="messageType">
								<c:forEach items="${messageTypes}" var="messageType">
									<option value="${messageType}" code="${messageType.code}" data-label="${messageType.label}" <c:if test="${templateMessage.messageType == messageType}">selected</c:if>>${messageType.name}</option> 
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">消息类型</label>
						<div class="col-md-4">
							<input class="form-control" value="" readOnly="true" id="label"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">模版编号</label>
						<div class="col-md-4">
							<input class="form-control" value="" readOnly="true" id="code"/>
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
<script>
$(function() {
	initSelect();
	$("#messageType").change(function(){
		initSelect();
	});
	
	$("#submitForm").submit(function(){
		$(".js_trim").each(function(){
			$(this).val($(this).val().trim());
		});
	});
});
function initSelect(){
	var options = $("#messageType option");
	for (var i = 0; i < options.length; i++) {
		if(options[i].selected){
			$("#code").val($(options[i]).attr("code"));
			$("#label").val($(options[i]).attr("data-label"));
		}
	}
}
</script>
<jsp:include page="/common/bootbox.jsp" />