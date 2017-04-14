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

<c:set var="headerTitle" value="优惠券" scope="request"/>
<jsp:include page="card_header.jsp" />

<div class="couponselect block_pd">
	<ul class="couponfilter">
	<li><a href="/oauth/member/memberCoupon.do?couponState=NOT_USED" class="<c:if test="${couponState=='NOT_USED'}">hover</c:if>">未使用</a></li>
	<li><a href="/oauth/member/memberCoupon.do?couponState=USED" class="<c:if test="${couponState=='USED'}">hover</c:if>">已使用</a></li>
	<li><a href="/oauth/member/memberCoupon.do?couponState=ALL" class="<c:if test="${couponState=='ALL'}">hover</c:if>">全部</a></li>
	<%-- <li><a href="/oauth/member/memberCoupon.do?couponState=EXPIRED" class="<c:if test="${couponState=='EXPIRED'}">hover</c:if>">已过期</a></li> --%>
	</ul>
</div>

<div class="couponbox">
  <c:forEach items="${couponVOs}" var="couponVO" varStatus="vs">
  	<div class="coupon_cont"> 
	    <h3 class="time"><span class="fr"><c:choose><c:when test="${couponVO.useflag == 0}">【未使用】</c:when><c:when test="${couponVO.useflag == 1}">【已使用】</c:when><c:otherwise>【已注销】</c:otherwise></c:choose></span>有效期：<fmt:formatDate value="${couponVO.limitdate}" pattern="yyyy-MM-dd"/></h3>
	    <div class="contdl block_pd">
	      <div class="pic"><span class="Nb"><c:if test="${couponVO.picUrl!=null&&couponVO.picUrl!=''}">兑换码:${couponVO.seqid}</c:if></span><img src="${couponVO.picUrl}"/><p class="bottombg"></p></div>
	      <div class="txt">
	      	<a href="javascript:" class="erm fr" id="js-qr${vs.index}" data-code="${couponVO.code}"></a>
	        <p class="colorking">
		        <c:if test="${couponVO.chargeamtmodel==1}">
		        	<b>¥<fmt:formatNumber value="${couponVO.amount}" type="currency" pattern="#######.##"/></b>&nbsp;元代金券
		        </c:if>
	        	<c:if test="${couponVO.chargeamtmodel==0}">${couponVO.ticketTypeCname}</c:if>
	        </p>${couponVO.notice}
	      </div>
		</div>
	</div>
  </c:forEach>
</div>

<div class="am-modal am-modal-no-btn" tabindex="-1" id="couponCode">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">
      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd" id="showCouponCode">
    </div>
  </div>
</div>

<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/jquery.qrcode.min.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
	$(function() {
		$(".erm").each(function(index) {
			var _this = $(this);
			showQr("js-qr"+index, 45, 45, _this.attr("data-code"));
		});
	});
	
	function showQr(id, width, height, text) {
		$("#"+id).qrcode({width:width,height:height,text:text});
	}
	
	$(".erm").click(function() {
		$("#showCouponCode").html("");
		showQr("showCouponCode", 150, 150, $(this).attr("data-code"));
		$("#couponCode").modal();
	});
	
</script>
</body>
</html>