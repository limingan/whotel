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
<c:set var="headerTitle" value="地址信息" scope="request"/>
<jsp:include page="card_header.jsp" />

<c:forEach items="${contactAddressList}" var="contactAddress">
	<div class="addressbox pd10">
		<div class="addresslist"  style="overflow:hidden;">
			<div class="editbut fr"><a href="toEditContactAddress.do?id=${contactAddress.id}"><i class="edit_icon"></i>编辑</a></div>
			<div class="addresslistinfo">
				<p>姓名：${contactAddress.name}<c:if test="${contactAddress.def==true}">&nbsp;&nbsp;(&nbsp;默认&nbsp;)</c:if></p>
				<p>手机：${contactAddress.mobile}</p>
				<p>地址：${contactAddress.addr}</p>
			</div>
		</div>
	</div>
</c:forEach>
<div class="addbut" >
<a href="toEditContactAddress.do"  style="display:block; background:#fff; border:1px solid #ccc;  line-height:40px; width:100%; padding:0 10px">
<i class="add_icon fr">+</i>新增地址</a>
</div>

<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
</body>
</html>