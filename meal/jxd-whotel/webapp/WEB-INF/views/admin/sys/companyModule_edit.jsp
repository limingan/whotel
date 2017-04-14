<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 模块编辑</title>
</head>
<c:set var="cur" value="sel-sys" scope="request"/>
<c:set var="cur_sub" value="sel-companyModule" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/admin/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>模块编辑</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateCompanyModule.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${module.id}"/>
				<div class="form-group first">
					<label class="col-md-3 control-label">模块</label>
					<div class="col-md-4">
						<select class="form-control" name="moduleType">
							<option value="HOTEL" <c:if test="${module.moduleType=='HOTEL'}">selected</c:if>>酒店</option>
							<option value="TICKET" <c:if test="${module.moduleType=='TICKET'}">selected</c:if>>门票</option> 
							<option value="MALL" <c:if test="${module.moduleType=='MALL'}">selected</c:if>>商城</option> 
							<option value="MEAL" <c:if test="${module.moduleType=='MEAL'}">selected</c:if>>餐饮</option> 
						</select>
					</div>
				</div>
				<div class="form-body">
					<div class="form-group first">
						<label class="col-md-3 control-label">名称</label>
						<div class="col-md-4">
						   <input name="name" class="form-control" placeholder="名称" maxlength="50" value="${module.name}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">显示名</label>
						<div class="col-md-4">
						<input name="displayName" class="form-control" placeholder="显示名" maxlength="50" value="${module.displayName}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">编码</label>
						<div class="col-md-4">
						 <input name="typeCode" class="form-control" placeholder="编码" maxlength="50" value="${module.typeCode}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">排序</label>
						<div class="col-md-4">
						 <input name="displayOrder" class="form-control" placeholder="排序" maxlength="50" value="${module.displayOrder}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">链接</label>
						<div class="col-md-4">
						 <input name="linkUrl" class="form-control" placeholder="链接" maxlength="50" value="${module.linkUrl}"/>
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

<script src="/static/admin/js/module.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script>
	$(function() {
		Module.init();
	});
</script>
<jsp:include page="/common/bootbox.jsp" />