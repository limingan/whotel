<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${WEIXINFAN_LOGIN_COMPANYNAME}</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common${THEME}.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain${THEME}.css?v=${version}">
</head>
<body>

<c:set var="headerTitle" value="酒店简介" scope="request"/>
<jsp:include page="hotel_header.jsp" />

<div class="ticketlist_topad">
	<div data-am-widget="slider" class="am-slider am-slider-a1" data-am-slider='{"directionNav":false}'>
	  <ul class="am-slides">
	    <c:forEach items="${hotel.bannerUrls}" var="url">
	    	<li><img src="${url}?imageView2/0/w/400/h/270/format/jpg" /> </li>
	    </c:forEach>
	  </ul>
	</div>
	
	<%-- <div class="title">
		<a href="/oauth/hotelMsg/listHotelComment.do?hotelCode=${hotelBranchVO.code}&comid=${WEIXINFAN_LOGIN_COMPANYID}">
			<span class="fr">点评<i class="Wmore_icon"></i></span><h1>${hotelBranchVO.cname}</h1>
		</a>
	</div> --%>
</div>
<%-- <h2 class="hotelsinfo_title"><a href="http://api.map.baidu.com/marker?location=${hotel.coords[1]},${hotel.coords[0]}&title=${hotelBranchVO.cname}&content=${hotelBranchVO.address}&output=html&src=jxd" class="fr"><i class="sm20_icon_location"></i></a>${hotelBranchVO.cname}</h2> --%>

<div class="hoteltopinfobox block_pd"><dl>
	<dd><a href="http://api.map.baidu.com/marker?location=${hotel.coords[1]},${hotel.coords[0]}&title=${hotelBranchVO.cname}&content=${hotelBranchVO.address}&output=html&src=jxd"><i class="sm20_icon_location"></i><span>地址：${hotelBranchVO.address}</span></a></dd>
	<dd><a href="tel:${hotelBranchVO.tel}"><i class="sm20_icon_tel"></i><span>电话：${hotelBranchVO.tel}</span></a></dd>
</dl></div>

<div  class="hotelsinfo_txt">
  <div data-am-widget="tabs"
       class="am-tabs am-tabs-default" >
    <ul class="am-tabs-nav am-cf">
      <li class="am-active" ><a href="[data-tab-panel-0]">酒店介绍</a></li>
      <li class=""><a href="[data-tab-panel-1]">酒店设施</a></li>
      <li class=""><a href="[data-tab-panel-2]">联络我们</a></li>
      <li class=""><a href="[data-tab-panel-3]">周边环境</a></li>
    </ul>
    <div class="am-tabs-bd">
      <div data-tab-panel-0 class="am-tab-panel hotelsinfo_txt_dl am-active ">
          ${hotel.descr}
      </div>
      <div data-tab-panel-1 class="am-tab-panel hotelsinfo_txt_dl pd10">  ${hotel.facility} </div>
      <div data-tab-panel-2 class="am-tab-panel hotelsinfo_txt_dl">  ${hotelBranchVO.tel} </div>
      <div data-tab-panel-3 class="am-tab-panel hotelsinfo_txt_dl">  ${hotel.around} </div>
    </div>
  </div>
</div>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
</body>
</html>
