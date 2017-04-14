<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 欢迎信息</title>
<link rel="stylesheet" href="/static/company/css/resource.css" />
</head>
<c:set var="cur" value="sel-publicno" scope="request"/>
<c:set var="cur_sub" value="sel-welcome-info" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>欢迎信息</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateWelcomeMsg.do" class="form-horizontal" method="post" onsubmit="handleData()" id="submitForm">
				<input type="hidden" name="id" value="${welcomeMsg.id}"/>
				<jsp:include page="/WEB-INF/views/company/publicno/news_select.jsp" />
                <div class="saveBtn"><a href="javascript:$('#submitForm').submit()"  class=" save">保存</a></div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
</div>

<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/bootbox.jsp" />
