<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${website.name}</title>
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta name="format-detection" content="telephone=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta http-equiv="pragma" content="no-cache" />
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/home12.css"/>
</head>
<body>
<div class="banner">
	 <div data-am-widget="slider" class="am-slider am-slider-default" data-am-slider='{playAfterPaused: 8000}'>
	    <ul class="am-slides">
		    <c:forEach items="${website.bannerUrls}" var="bannerUrl" varStatus="vs">
		    	<c:set var="bannerLink" value="${website.bannerLinks[vs.index]}"/>
		    	<li><a href="<c:choose><c:when test="${bannerLink == null || bannerLink == ''}">javascript:</c:when><c:otherwise>${bannerLink}</c:otherwise></c:choose>"><img src="${bannerUrl}"/></a></li>
		    </c:forEach>
	    </ul>
	  </div>
	</div>
<div class="content">
  <ul><c:forEach items="${website.columns}" var="column" varStatus="vs">
	  <c:if test="${vs.count <= 4}">
		<li><!--  class="icon${vs.count}" -->
			<a href="<c:choose><c:when test="${param.edit || column.target == null || column.target == ''}">javascript:</c:when><c:when test="${column.type == 'NAVIGATION'}">http://api.map.baidu.com/direction?region=${company.region.name}&mode=driving&src=${company.name}&output=html&destination=latlng:${company.coords[1]},${company.coords[0]}|name:${company.name}&origin=latlng:${location.lat},${location.lon}|name:你的位置</c:when><c:otherwise>${column.target}</c:otherwise></c:choose>" <c:if test="${column.type == 'CONTACT'}">class="js-contact"</c:if>>
				<%-- <span style="<c:if test="${column.bgColor != null && column.bgColor != ''}">background-color:${column.bgColor}</c:if>"><img src="${column.iconUrl}"/></span> --%>
				<i class="icon" style="<c:if test="${column.bgUrl != null && column.bgUrl != ''}">background:url(${column.bgUrl}) no-repeat;</c:if>"></i><br/>${column.name}
			</a>
		</li>
      </c:if>
  </c:forEach></ul>
  
  <ul><c:forEach items="${website.columns}" var="column" varStatus="vs">
	  <c:if test="${vs.count > 4}">
		<li><!--  class="icon${vs.count}" -->
			<a href="<c:choose><c:when test="${param.edit || column.target == null || column.target == ''}">javascript:</c:when><c:when test="${column.type == 'NAVIGATION'}">http://api.map.baidu.com/direction?region=${company.region.name}&mode=driving&src=${company.name}&output=html&destination=latlng:${company.coords[1]},${company.coords[0]}|name:${company.name}&origin=latlng:${location.lat},${location.lon}|name:你的位置</c:when><c:otherwise>${column.target}</c:otherwise></c:choose>" <c:if test="${column.type == 'CONTACT'}">class="js-contact"</c:if>>
				<%-- <span style="<c:if test="${column.bgColor != null && column.bgColor != ''}">background-color:${column.bgColor}</c:if>"><img src="${column.iconUrl}"/></span> --%>
				<i class="icon" style="<c:if test="${column.bgUrl != null && column.bgUrl != ''}">background:url(${column.bgUrl}) no-repeat;</c:if>"></i><br/>${column.name}
			</a>
		</li>
      </c:if>
  </c:forEach></ul>
   
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
