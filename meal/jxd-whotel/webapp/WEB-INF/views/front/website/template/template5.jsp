<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${website.name}</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/home5.css">
</head>
<body class="bodybghui"> 
<div class="homeAD">
	<div data-am-widget="slider" class="am-slider am-slider-a1"
		data-am-slider='{"directionNav":false}'>
		<ul class="am-slides">
		    <c:forEach items="${website.bannerUrls}" var="bannerUrl" varStatus="vs">
		    	<c:set var="bannerLink" value="${website.bannerLinks[vs.index]}"/>
		    	<li><a href="<c:choose><c:when test="${bannerLink == null || bannerLink == ''}">javascript:</c:when><c:otherwise>${bannerLink}</c:otherwise></c:choose>"><img src="${bannerUrl}"/></a></li>
		    </c:forEach>
		</ul>
	</div>
</div>
<div class="homeNavbox">
	<div class="Telbox">
		<a href="tel:${company.tel}">联系电话:${company.tel}<i class="more_icon_12 fr"></i></a>
	</div>
	<ul class="homeh_Nav">

		<c:forEach items="${website.columns}" var="column">
			<li>
				<a href="${column.target}"><i class="hicon"><img src="${column.iconUrl}" /></i><span>${column.name}</span></a>
			</li>
		</c:forEach>
	</ul>
</div>

<script src="/static/common/js/jquery-1.11.2.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<c:if test="${template.jsPathUrl != null}">
	<script src="${template.jsPathUrl}"></script>
</c:if>
<jsp:include page="common_contact.jsp" />
<script type="text/javascript">
	$(function(){
		$('.am-slider').flexslider();
	});
</script>
</body>
</html>
