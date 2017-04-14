<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${website.name}</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"   content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/home10.css">
</head>
<body class="bodypd">
<div class="homeAD">
  <div data-am-widget="slider" class="am-slider am-slider-default" data-am-slider='{playAfterPaused: 8000}'>
    <ul class="am-slides">
	    <c:forEach items="${website.bannerUrls}" var="bannerUrl" varStatus="vs">
	    	<c:set var="bannerLink" value="${website.bannerLinks[vs.index]}"/>
	    	<li><a href="<c:choose><c:when test="${bannerLink == null || bannerLink == ''}">javascript:</c:when><c:otherwise>${bannerLink}</c:otherwise></c:choose>"><img src="${bannerUrl}"/></a></li>
	    </c:forEach>
    </ul>
  </div>
</div>

<div class="homeNav">
	<c:forEach items="${website.columns}" var="column" varStatus="vs">
    	<c:if test="${column.type != 'NAVIGATION' && column.type != 'CONTACT'}">
			<dl class="homeNavlist">
				<dt class="picL"><a href="<c:choose><c:when test="${param.edit || column.target == null || column.target == ''}">javascript:</c:when><c:otherwise>${column.target}</c:otherwise></c:choose>"><img src="${column.iconUrl}"/></a></dt>
				<dd class="cont"><a href="<c:choose><c:when test="${param.edit || column.target == null || column.target == ''}">javascript:</c:when><c:otherwise>${column.target}</c:otherwise></c:choose>"><h1 class="title">${column.name}</h1><p>${column.remarks}</p></a></dd>
			</dl>
    	</c:if>
	</c:forEach>
</div>
<div class="footerbar">
	<c:forEach items="${website.columns}" var="column" varStatus="vs">
	   	<c:if test="${column.type == 'CONTACT'}">
			<p><a href="javascript:void(0)" class="js-contact">${column.name}</a></p>
	   	</c:if>
	   	<c:if test="${column.type == 'NAVIGATION'}">
			<p><a href="http://api.map.baidu.com/direction?region=${company.region.name}&mode=driving&src=${company.name}&output=html&destination=latlng:${company.coords[1]},${company.coords[0]}|name:${company.name}&origin=latlng:${location.lat},${location.lon}|name:你的位置">${column.name}</a></p>
	   	</c:if>
	</c:forEach>
</div>

<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<jsp:include page="common_contact.jsp" />
<script type="text/javascript">
$(function() {
	$('.am-slider').flexslider();
});
</script>
</body>
</html>
