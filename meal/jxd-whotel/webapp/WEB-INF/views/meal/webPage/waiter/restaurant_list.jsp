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
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common2.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/meal/css/main.css?v=${version}">
</head>
<body>
<c:set var="headerTitle" value="餐厅列表" scope="request"/>
<jsp:include page="meal_header.jsp"/>

<div class="ImgTxtlistboxA" id="js_restaurantList">
	<c:if test="${restaurantList == null || restaurantList.size() == 0}">
		<p style="color:red; text-align:center; padding:10px 10px;">此条件下没有可预订餐厅！</p>
	</c:if>
	<c:forEach items="${restaurantList}" var="restaurant" varStatus="vs">
		<div class="item_cy">
			<div class="item_cy_dal">
				<a href="/oauth/waiter/toMealTabList.do?restaurantId=${restaurant.id}">
					<p class="pic_L"><img src="${restaurant.miniatureUrl}?imageView2/0/w/120/h/100"/></p>
					<h2 class="clorshowH">${restaurant.name}</h2>
					<p class="fx clorshowHs">营业时间&nbsp;<span class="clorshowY">${restaurant.businessTime}</span></p>
					<c:if test="${restaurant.cuisine != null && restaurant.cuisine != ''}">
						<p class="ctstyle"><span class="ctZ">${restaurant.cuisine}</span></p><!-- ctX,ctT -->
					</c:if>
				</a>
			</div>
		</div>
	</c:forEach>
</div>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/jquery.lazyload.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
</body>
</html>
