<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${erryTitle}</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain.css?v=${version}">
</head>

<body>
	<div class="bactipsbox">
		<h1 class="title">
			<i class=" tips_icon"></i>${erryTitle}
		</h1>
		<dl class="cont">
			<dt>详细信息</dt>
			<dd>${erryInfo}</dd>
		</dl>
		<p class=" pd10">
			<button class="butstyleE" onclick="window.open('${erryUrl}')">确定</button>
		</p>
	</div>
</body>
</html>