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
<body class="bodypd">
<c:set var="backUrl" value="/oauth/meal/mealBranchSearch.do?comid=${WEIXINFAN_LOGIN_COMPANYID}" scope="request"/>
<c:set var="headerTitle" value="餐饮预订结果" scope="request"/>
<jsp:include page="meal_order_header.jsp" />
<div>
  <div class="tipInfobox mrgtd30">
    <h2><i class="cge_icon marcenter"></i><span>订单已提交</span></h2>
    <p>酒店稍后确认，请注意查收手机信息！</p>
  </div>
  <div class="pd20 mrgtd30">
    <input type="button" class="butstyleB" value="查看订单信息" onclick="window.open('/oauth/meal/showMealOrder.do?orderSn=${order.orderSn}')"/>
    <input type="button" class="butstyleC mrgtd20" value="继续预订" onclick="window.open('/oauth/meal/listRestaurant.do?comid=${order.companyId}&hotelCode=${order.hotelCode}')"/>
  </div>
</div>

<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
</body>
</html>
