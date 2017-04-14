<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${WEIXINFAN_LOGIN_COMPANYNAME}</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"   content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/common/js/kalendae/kalendae.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common2.css?v=${version}"><!-- ${THEME} -->
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain2.css?v=${version}">
</head>
<body class="bg-co-gray">
<c:set var="headerTitle" value="包房简介" scope="request"/>
<jsp:include page="meal_header.jsp" />

<div data-am-widget="slider" class="am-slider am-slider-a1" data-am-slider='{"directionNav":false}'>
  <ul class="am-slides">
    <c:forEach items="${mealTab.bannerUrls}" var="url">
    	<li><img src="${url}?imageView2/2/w/267/h/160" /> </li>
    </c:forEach>
  </ul>
</div>

<div class="notes">
	<h2>详细说明</h2>
	<p><i class="icon-point"></i>包房名：${mealTab.tabName}</p>
	 <p><i class="icon-point"></i>适合人数：${mealTab.seats}人</p>
	 <p><i class="icon-point"></i>包房介绍：${mealTab.remark}</p>
	 <p><i class="icon-point"></i>预订须知：${mealTab.notes}</p>
</div>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/jquery.lazyload.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">

</script>
</body>
</html>
