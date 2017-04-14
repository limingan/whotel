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
<body class="bgnone">
<jsp:include page="meal_header.jsp"/>
	<div class="Htopbanner">
		<div data-am-widget="slider" class="am-slider am-slider-a1" data-am-slider='{"directionNav":false}'>
			<ul class="am-slides">
				<c:forEach items="${hotelBranchs}" var="hotelBranch">
					<li><img src="${hotelBranch.hotelPic}" /></li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<!-- <div class="homeAD">
<div data-am-widget="slider" class="am-slider am-slider-a1" data-am-slider='{"directionNav":false}'>
  <ul class="am-slides">
    <li> <a href="choujiang.html"><img src="imags/homeAD.png" /></a> </li>
    <li> <a href="vote.html"><img src="imags/homeAD.png" /></a> </li>
    <li> <a href="index-3.html"><img src="imags/homeAD.png" /> </a></li>
  </ul>
</div></div> -->
	<div class="block_pd searchhotel">
		<form action="/oauth/meal/listRestaurant.do" method="post">
			<h2>门店选择</h2>
			<ul>
				<li class="selelistbox locationicon"><select class="selectstyleA">
					<option value="">选择城市</option>
					<c:forEach items="${hotelCitys}" var="hotelCity">
						<option value="${hotelCity.city}">${hotelCity.city}</option>
					</c:forEach>
				</select></li>
				<li class="selelistbox hotelicon"><select name="hotelCode" class="selectstyleA">
					<option value="">全部</option>
					<c:forEach items="${hotelBranchs}" var="hotelBranch">
						<option value="${hotelBranch.code}">${hotelBranch.cname}</option>
					</c:forEach>
				</select></li>
				<li class=" mrg-t10"><input type="submit" value="查找" class="butstyleD" /></li>
			</ul>
		</form>
	</div>

<script src="/static/common/js/jquery-1.11.2.js"></script>
<script src="/static/common/js/jquery.qrcode.min.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script src="/static/common/js/qrapi.js"></script>
</body>
</html>