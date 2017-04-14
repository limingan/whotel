<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 编辑抵店时间</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-meal-config" scope="request"/>
<c:set var="cur_sub_leaf" value="sel-meal-manage-leaf" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>编辑抵店时间</li>
				</ul>
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="saveMealConfig.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${mealConfig.id}"/>
				<div class="form-group first">
					<label class="col-md-3 control-label">抵店时间</label>
					<div class="col-md-4">
						<input type="text" name="arriveTime" value="${mealConfig.arriveTime}" placeholder="18:30" class="form-control">
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">所属餐市</label>
					<div class="col-md-4">
						<select name="mealType" class="form-control">
							<option value="BREAKFAST" <c:if test="${mealConfig.mealType=='BREAKFAST'}">selected="selected"</c:if>>早餐</option>
							<option value="LUNCH" <c:if test="${mealConfig.mealType=='LUNCH'}">selected="selected"</c:if>>午餐</option>
							<option value="AFTERNOONTEA" <c:if test="${mealConfig.mealType=='AFTERNOONTEA'}">selected="selected"</c:if>>下午茶</option>
							<option value="DINNER" <c:if test="${mealConfig.mealType=='DINNER'}">selected="selected"</c:if>>晚餐</option>
						</select>
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
<script>
</script>

<jsp:include page="/common/bootbox.jsp" />
