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
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common${THEME}.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain${THEME}.css?v=${version}">
</head>
<body>

<c:set var="headerTitle" value="订单列表" scope="request"/>
<jsp:include page="hotel_header.jsp" />

<c:if test="${moduleTypes.size() > 1}">
	<div class="Ordertype">
		<ul><c:forEach items="${moduleTypes}" var="moduleType" varStatus="vs">
			<li style="width: ${100/moduleTypes.size()}%;">
				<a href="${moduleType.url}?comid=${WEIXINFAN_LOGIN_COMPANYID}&&tradeStatus=${tradeStatus}" <c:if test="${moduleType == 'HOTEL'}">class="hover"</c:if>>${moduleType.label}</a>
			</li>
		</c:forEach></ul>
	</div>
</c:if>

<div class="blockbg">
  <ul class="myorderlist">
    <c:if test="${hotelOrders == null || hotelOrders.size() == 0}">
    	<p style="color:red; text-align:center; padding:10px 10px;">您还没有任何订单！</p>
	</c:if>
    <c:forEach items="${hotelOrders}" var="hotelOrder">
    <li>
    <a href="/hotel/showHotelOrder.do?orderSn=${hotelOrder.orderSn}">
      <p class="myorderlist_fettle fr">
      <b class="pe colorking">¥<fmt:formatNumber value="${hotelOrder.totalFee}" type="currency" pattern="#######.##"/></b>
      <span class="zt">
      		<c:choose>
				<c:when test="${hotelOrder.status=='NEW'}"><i class="RoomStatus_icon_wait"></i></c:when>
				<c:when test="${hotelOrder.status=='WAIT_PAY'}"><i class="RoomStatus_icon_wpay"></i></c:when>
				<c:when test="${hotelOrder.status=='CONFIRMED'}"><i class="RoomStatus_icon_confirm"></i></c:when>
				<c:when test="${hotelOrder.status=='USED'}"><i class="RoomStatus_icon_used"></i></c:when>
				<c:when test="${hotelOrder.status=='LEAVE'}"><i class="RoomStatus_icon_used"></i></c:when>
				<c:when test="${hotelOrder.status=='CANCELED'}"><i class="RoomStatus_icon_cancel"></i></c:when>
			</c:choose>${hotelOrder.status.label}
	  </span>
	  </p>
	  ${hotelOrder.name}<br/>
	  <span class="indate">入住：<fmt:formatDate value="${hotelOrder.checkInTime}" pattern="yyyy/MM/dd"/><br />离店：<fmt:formatDate value="${hotelOrder.checkOutTime}" pattern="yyyy/MM/dd"/></span>
    </a>
    </li>
    </c:forEach>
  </ul>
</div>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
</body>
</html>
