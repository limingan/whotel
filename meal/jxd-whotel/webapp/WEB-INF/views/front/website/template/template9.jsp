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
<link rel="stylesheet" type="text/css"
	href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css"
	href="/static/front/hotel/css/common.css?v=${version}">
<link rel="stylesheet" type="text/css"
	href="/static/front/hotel/css/home9.css">
</head>
<body>
	<div class="topbox">
		<div class="hotelname">
			<div class="logo">
				<img src="${website.bannerUrls[0]}" />
			</div>
			<div class="hotelnamecont">
				<h1 class="title">${company.name}</h1>
				<h2 class="title">${company.englishName.toUpperCase()}</h2>
			</div>
		</div>
	</div>
	<div class="homei_Navbox">
		<ul class="homei_Nav">
			<c:forEach items="${website.columns}" var="column" varStatus="vs">
				<li class="li${vs.count}"><a href="<c:choose><c:when test="${param.edit || column.target == null || column.target == ''}">javascript:</c:when><c:when test="${column.type == 'NAVIGATION'}">http://api.map.baidu.com/direction?region=${company.region.name}&mode=driving&src=${company.name}&output=html&destination=latlng:${company.coords[1]},${company.coords[0]}|name:${company.name}&origin=latlng:${location.lat},${location.lon}|name:你的位置</c:when><c:otherwise>${column.target}</c:otherwise></c:choose>" <c:if test="${column.type == 'CONTACT'}">class="js-contact"</c:if>"><span>${column.name}</span></a></li>
			</c:forEach>
		</ul>
	</div>
	<div class="copytextinfo">
		<p>捷信达软件提供技术支持</p>
	</div>
	<script src="/static/common/js/jquery-1.11.2.js"></script>
	<script src="/static/common/js/amazeui/amazeui.min.js"></script>

	<jsp:include page="common_contact.jsp" />
</body>
</html>
