<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${WEIXINFAN_LOGIN_COMPANYNAME}</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css" href="/static/metronic/assets/plugins/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/static/front/css/column_news.css">
<link rel="stylesheet" type="text/css" href="/static/front/css/loading.css">
</head>

<body>
	<div id="js_url" style="display: none;">${mapUrl}</div>
<script src="/static/common/js/jquery-1.11.2.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
$(function(){
	getLocation();
});

function getLocation(){
	if (navigator.geolocation){
		var location = navigator.geolocation.getCurrentPosition(function(position){
			//alert("纬度： " + position.coords.latitude + "经度： " + position.coords.longitude);
			var url = $("#js_url").html()+position.coords.latitude+","+position.coords.longitude+"|name:你的位置";
			url = url.replace(new RegExp("amp;", "g"),"");
			window.location.href = url;
		});
	}else{
		alert("不支持");
	}
}
</script>
</body>
</html>