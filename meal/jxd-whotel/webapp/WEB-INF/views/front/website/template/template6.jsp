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
<link rel="stylesheet" type="text/css"href="/static/front/hotel/css/home6.css">
</head>
<body class="demobge" style="background-image: url('${website.bannerUrls[0]}'); ">
	<div class="demo_zee">
		<ul class="Navlistbox_g">
			<c:forEach items="${website.columns}" var="column" varStatus="vs">
				<c:if test="${column.type != 'NAVIGATION' && column.type != 'CONTACT'}">
					<li class="r${vs.count}"><a href="<c:choose><c:when test="${param.edit || column.target == null || column.target == ''}">javascript:</c:when><c:otherwise>${column.target}</c:otherwise></c:choose>"><img src="${column.iconUrl}"/><span>${column.name}</span></a></li>
				</c:if>
			</c:forEach>
		</ul>
	</div>

	<ul class="ciNav">
		<c:forEach items="${website.columns}" var="column" varStatus="vs">
			<c:if test="${column.type == 'NAVIGATION'}">
				<li><a href="http://api.map.baidu.com/direction?region=${company.region.name}&mode=driving&src=${company.name}&output=html&destination=latlng:${company.coords[1]},${company.coords[0]}|name:${company.name}&origin=latlng:${location.lat},${location.lon}|name:你的位置">${column.name}</a></li>
			</c:if>
			<c:if test="${column.type == 'CONTACT'}">
				<li><a href="javascript:void(0)"
					<c:if test="${column.type == 'CONTACT'}">class="js-contact"</c:if>>${column.name}</a></li>
			</c:if>
		</c:forEach>
	</ul>

	<script src="/static/common/js/jquery-1.11.2.js"></script>
	<script src="/static/common/js/amazeui/amazeui.min.js"></script>
	<c:if test="${template.jsPathUrl != null}">
		<script src="${template.jsPathUrl}"></script>
	</c:if>

	<jsp:include page="common_contact.jsp" />
	<script type="text/javascript">
	$(function(remove) {
		var length = $(".Navlistbox_g").find("li").length;	
		if(length>4){
			$('.am-slider').flexslider();
			var li = $(".Navlistbox_g").find("li:last");
			$(".ciNav").append("<li>"+li.html()+"</li>");
			li.remove();
			$(".ciNav").find("img:last").remove();
		}
		});
	</script>
</body>
</html>
