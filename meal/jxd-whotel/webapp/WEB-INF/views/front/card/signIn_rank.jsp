<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${marketingSignIn.name}</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"   content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link rel="stylesheet" type="text/css" href="/static/common/css/signInDate.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/common/js/kalendae/kalendae.css">
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common${THEME}.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain${THEME}.css?v=${version}">
<style type="text/css">
.weixinfan_avatar{width: 20%;display: inline-block;text-align: center;}
.avatar{width: 70%;border-radius: 1000px;}
.nickName{width: 45%;display: inline-block;font-size: 14px;vertical-align: middle;}
.sort{width: 5%;display: inline-block;text-align: center;}
.signInCount{width: 25%;display: inline-block;text-align: center;font-size: 30px;color: #29C720;vertical-align: middle;}
</style>
</head>
<body>
<c:set var="headerTitle" value="排行榜" scope="request"/>
<jsp:include page="card_header.jsp" />

<div data-am-widget="slider" class="am-slider am-slider-a1" data-am-slider='{"directionNav":false}'>
    <ul class="am-slides">
      	<c:forEach items="${marketingSignIn.signRankBannerUrls}" var="url">
    		<li><img src="${url}?imageView2/0/w/400/h/270/format/jpg" /> </li>
    	</c:forEach>
    </ul>
</div>

<ul class="blockbg">
	<li style="margin-bottom: 10px;padding: 3%;">
		<p class="sort">&nbsp;</p>
		<p class="weixinfan_avatar"><img src="${weixinFan.avatar}" class="avatar"></p>
		<p class="nickName">${weixinFan.nickName}<br/><span style="color: #A09E9F;" id="js_sort">未签到</span></p>
		<p class="signInCount" id="js_signInCount">0</p>
	</li>
</ul>

<ul class="blockbg">
	<c:forEach var="vo" items="${signInRankVos}" varStatus="vs">
		<li style="padding: 3%;border-bottom: 1px solid #dadada;" <c:if test="${weixinFan.openId==vo.weixinFan.openId}">id="js_login"</c:if>>
			<p class="sort">${vs.count}</p>
			<p class="weixinfan_avatar"><img src="${vo.weixinFan.avatar}" class="avatar"></p>
			<p class="nickName">${vo.weixinFan.nickName}</p>
			<p class="signInCount">${vo.signInCount}</p>
		</li>
	</c:forEach>
</ul>
<script src="/static/common/js/jquery-1.11.2.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
$(function(){
	if($("#js_login").length>0){
		var login = $("#js_login");
		$("#js_sort").text("第"+login.find(".sort").text()+"名");
		$("#js_signInCount").text(login.find(".signInCount").text());
	}
})
</script>
</body>
</html>