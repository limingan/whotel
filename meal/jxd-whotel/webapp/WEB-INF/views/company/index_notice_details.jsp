<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微信商户平台 | 首页</title>
<style type="text/css">
.announcement_title {
    padding-bottom: 42px;
    text-align: center;
    font-size: 24px;
}
</style>
</head>
<c:set var="cur" value="sel-index" scope="request"/>
<div class="page-content-wrapper"><div class="page-content">
	<h3 class="announcement_title">${sysNotice.title}</h3>
	<div>${sysNotice.content}</div>
	<p style="text-align: right;font-size: 16px;">捷信达团队<br/><span id="online_time"><fmt:formatDate value="${sysNotice.createTime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
</div></div>
<jsp:include page="/common/bootbox.jsp"></jsp:include>