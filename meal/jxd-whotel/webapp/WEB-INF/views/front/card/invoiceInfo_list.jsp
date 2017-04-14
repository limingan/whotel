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
<style type="text/css">
</style>
</head>
<body>
<c:set var="headerTitle" value="发票管家" scope="request"/>
<jsp:include page="card_header.jsp" />

<c:forEach items="${invoiceInfos}" var="invoiceInfo">
	<div class="addressbox pd10 invoiceInfo" data-id="${invoiceInfo.id}">
		<div class="addresslist"  style="overflow:hidden;">
			<div class="editbut fr"><a href="toEditInvoiceInfo.do?id=${invoiceInfo.id}"><i class="edit_icon"></i>编辑</a></div>
			<div class="addresslistinfo">
				<p class="invoiceInfo-name">${invoiceInfo.name}</p>
				<label class="invoiceInfo-type">${invoiceInfo.ticketType?"专票":"普票"}</label>
				<c:choose>
					<c:when test="${!invoiceInfo.type}"><label class="invoiceInfo-type">单位</label></c:when>
					<c:otherwise><label class="invoiceInfo-type">个人</label></c:otherwise>
				</c:choose>
				<c:if test="${invoiceInfo.def==true}"><label class="invoiceInfo-def">默认</label></c:if>
			</div>
		</div>
	</div>
</c:forEach>
<div class="pd20">
	<input type="button" value="添加" class="butstyleB" onclick="updateInvoiceInfo();"/>
</div>

<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
function updateInvoiceInfo(){
	window.location.href="/oauth/invoice/toEditInvoiceInfo.do";
}
$(function(){
	$(".invoiceInfo").click(function(){
		window.location.href="/oauth/invoice/showInvoiceInfo.do?id="+$(this).attr("data-id");
	});
});
</script>
</body>
</html>