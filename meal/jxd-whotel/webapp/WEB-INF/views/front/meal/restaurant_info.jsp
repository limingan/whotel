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

<body class="badypd">
<c:set var="backUrl" value="/oauth/meal/listRestaurant.do?comid=${WEIXINFAN_LOGIN_COMPANYID}&hotelCode=${restaurant.hotelCode}" scope="request"/>
<jsp:include page="meal_header.jsp"/>
<div class="homeAD">
	<div data-am-widget="slider" class="am-slider am-slider-a1" data-am-slider='{"directionNav":false}'>
	  <ul class="am-slides">
	    <c:forEach items="${restaurant.bannerUrls}" var="url">
	    	<li><img src="${url}?imageView2/0/w/380/h/280"/></li>
	    </c:forEach>
	  </ul>
	</div>
</div>
 
<div class="block_pdbr">
	<div class="canting_cont">
		<div class="butfr"><a href="javascript:dishesList('${WEIXINFAN_LOGIN_COMPANYID}','${restaurant.id}')">看菜单</a></div>
		<h1 class="title">${restaurant.name}</h1>
		<ul class="jyifno clorshowH">
			<li class="address">${restaurant.address}</li>
			<li class="hours">营业时间：${restaurant.businessTime}</li>
		<ul>
	</div>
</div>

<div class=" block_pdbr">
  <ul class="serviceMark">
    <li><a href="http://api.map.baidu.com/direction?region=${restaurant.region.name}&mode=driving&src=${restaurant.name}&output=html&destination=latlng:${restaurant.coords[1]},${restaurant.coords[0]}|name:${restaurant.name}&origin=latlng:${location.lat},${location.lon}|name:你的位置" class="Navgt">导航</a></li>
    <li><a href="tel:${restaurant.tel}" class="tel">电话</a></li>
    <li><a href="javascript:mealTabList('${WEIXINFAN_LOGIN_COMPANYID}','${restaurant.id}')" class="bf">包房</a></li>
    <li><a href="#" class="wifi">WIFI</a></li>
  </ul>
</div>
<div class="block_pdbr mrg-t5">
  <h3> 简介</h3>
  <div class=" txtblcok clorshowH">
    <p>${restaurant.brief}</p>
  </div>
</div>

<div class="footerblcok"><button class="butstyleE_sm dwbut" onclick="mealTabBook('${WEIXINFAN_LOGIN_COMPANYID}','${restaurant.id}')">订位</button></div>

<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/jquery.lazyload.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
$(function(){
	var srcList = [];
	var imgs = $.find('img');
	for (i = 0; i < imgs.length; i++) {
		srcList[i] = $(imgs[i]).attr("src");
		$(imgs[i]).attr("data-index",i);
	}
	$('img').click(function() {
		var index = $(this).attr("data-index");
		imagePreview(srcList[index], srcList);
	});
});
function imagePreview(curSrc, srcList) {
	//这个检测是否参数为空
	if (!curSrc || !srcList || srcList.length == 0) {
		return;
	}
	//这个使用了微信浏览器提供的JsAPI 调用微信图片浏览器
	WeixinJSBridge.invoke('imagePreview', {
		'current' : curSrc,
		'urls' : srcList
	});
};

function mealTabList(comid, restaurantId){
	document.location="/oauth/meal/mealTabList.do?comid="+comid+"&restaurantId="+restaurantId;
}

function dishesList(comid, restaurantId){
	document.location="/oauth/meal/dishesList.do?comid="+comid+"&restaurantId="+restaurantId;
}

function mealTabBook(comid, restaurantId){
	//document.location="/oauth/meal/mealTabList.do?comid="+comid+"&restaurantId="+restaurantId;
	document.location="/oauth/meal/dishesBook.do?comid="+comid+"&restaurantId="+restaurantId;
}
</script>
</body>
</html>
