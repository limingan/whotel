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
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common2.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain2.css?v=${version}">
</head>
<body>

<c:set var="headerTitle" value="订单列表" scope="request"/>
<jsp:include page="meal_order_header.jsp" />

<c:if test="${moduleTypes.size() > 1}">
	<div class="Ordertype">
		<ul><c:forEach items="${moduleTypes}" var="moduleType" varStatus="vs">
			<li style="width: ${100/moduleTypes.size()}%;">
				<a href="${moduleType.url}?comid=${WEIXINFAN_LOGIN_COMPANYID}&&tradeStatus=${tradeStatus}" <c:if test="${moduleType == 'MEAL'}">class="hover"</c:if>>${moduleType.label}</a>
			</li>
		</c:forEach></ul>
	</div>
</c:if>

<div class="blockbg">
  <ul class="myorderlist">
    <c:if test="${mealOrders == null || mealOrders.size() == 0}">
        <c:if test="${param.tradeStatus != null}">
        	<p style="color:red; text-align:center; padding:10px 10px;">暂时没有未付款订单！</p>
        </c:if>
        <c:if test="${param.tradeStatus == null}">
        	<p style="color:red; text-align:center; padding:10px 10px;">您还没有任何餐饮订单！</p>
        </c:if>
	</c:if>
    <c:forEach items="${mealOrders}" var="mealOrder">
    <li>
    <a href="/oauth/meal/showMealOrder.do?orderSn=${mealOrder.orderSn}">
      <p class="myorderlist_fettle fr">
      <b class="pe colorking">¥<fmt:formatNumber value="${mealOrder.totalFee}" type="currency" pattern="#######.##"/></b>
      <span class="zt">
      		<c:choose>
				<c:when test="${mealOrder.status=='WAIT_PAY'}"><i class="RoomStatus_icon_wpay"></i></c:when>
				<c:when test="${mealOrder.status=='NOARRIVE'}"><i class="RoomStatus_icon_wait"></i></c:when>
				<c:when test="${mealOrder.status=='ARRIVE'}"><i class="RoomStatus_icon_used"></i></c:when>
				<c:when test="${mealOrder.status=='CANCELED' || mealOrder.status=='EXPIRED'}"><i class="RoomStatus_icon_cancel"></i></c:when>
			</c:choose>${mealOrder.status.label}
	  </span>
	  </p>
	  ${mealOrder.name}<br/>
	  <span class="indate">市别：${mealOrder.shuffleName}</span><br/>
	  <span class="indate">用餐时间：<fmt:formatDate value="${mealOrder.arrDate}" pattern="yyyy/MM/dd"/>&nbsp;${mealOrder.arriveTime}</span>
    </a>
    </li>
    </c:forEach>
  </ul>
</div>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
</body>
</html>
