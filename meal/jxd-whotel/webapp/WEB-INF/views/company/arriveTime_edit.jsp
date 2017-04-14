<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 到店时间</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<c:set var="cur_sub" value="sel-arrive-time" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>到店时间</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateArriveTime.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${arriveTime.id}"/>
				
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-3 control-label">到店时间</label>
						<div class="col-md-4">
							<input id="time" name="arriveTime" class="form-control" value="${arriveTime.arriveTime}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">是否默认</label>
						<div class="col-md-4">
						<label class="radio-inline">
						<span><input type="radio" name="isDefault" value="false" <c:if test="${arriveTime.isDefault==null || arriveTime.isDefault==false}">checked</c:if>></span>否</label>
						<label class="radio-inline">
						<input type="radio" name="isDefault" value="true" <c:if test="${arriveTime.isDefault==true}">checked</c:if>>是</label>
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
<script src="/static/company/js/arriveTime.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<%--内容区域 结束--%>
<script>
$(function() {
	ArriveTime.init();
});
</script>
<jsp:include page="/common/bootbox.jsp" />