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

<c:set var="headerTitle" value="会员卡" scope="request"/>
<jsp:include page="card_header.jsp" />

<%-- <div class="couponbox">
  <c:forEach items="${mbrCards}" var="mbrCard" varStatus="vs">
  	<div class="coupon_cont"> 
    <h3 class="time"><span class="fr">【${mbrCard.mbrCardTypeName}】</span>有效期：${mbrCard.mbrExpired}</h3>
    <div class="contdl block_pd">
		<div class="pic">
			<img src="http://7xljym.com1.z0.glb.clouddn.com/jxd-res:1FvVPCRX5bnHd7U3uEsLyY"/>
			<div class="txt Nb mbrCard">
				<a href="javascript:" class="erm fr" id="js-qr${vs.index}" data-code="${mbrCard.mbrCardNo}"></a>
				<p class="">卡号:${mbrCard.mbrCardNo}</p>
				<p class="colorking">储值:<b>¥<fmt:formatNumber value="${mbrCard.balance}" type="currency" pattern="#######.##"/></b></p>
				<p class="colorking">返现:<b>¥<fmt:formatNumber value="${mbrCard.incamount}" type="currency" pattern="#######.##"/></b></p>
			</div>
		</div>
	</div>
	</div>
  </c:forEach>
</div> --%>

<c:forEach items="${mbrCards}" var="mbrCard" varStatus="vs">

<div class="mbrCard" <c:if test="${mbrCard.picUrl!=null && mbrCard.picUrl!=''}">style="background: url(${mbrCard.picUrl})  no-repeat;background-size: 100% 100%;"</c:if>>
	<div class="mbr_left">
		<div class="vip"><img src="/static/front/images/vip.png" alt=""/></div>
		<div class="mbrInfo">
			<h4>VIP会员</h4>
			<p class="mbrInfo-2" style="margin-left: -30px;font-size: 15px;">本金：<b>¥<fmt:formatNumber value="${mbrCard.baseamtbalance}" type="currency" pattern="#######.##"/></b></p>
			<p class="mbrInfo-3" style="margin-left: -30px;font-size: 15px;">增值：<b>¥<fmt:formatNumber value="${mbrCard.incamount}" type="currency" pattern="#######.##"/></b></p>
		</div>
		<div class="time"><p>有效期：${mbrCard.mbrExpired}</p></div>
	</div>
	<!--右边内容-->
	<div class="mbr_right">
		<p>卡号：${mbrCard.mbrCardNo}</p>
		<p><a href="javascript:" class="erm" id="js-qr${vs.index}" data-code="<c:choose><c:when test="${mbrCard.onLinePay=='1'}">${mbrCard.mbrCardNo}</c:when><c:otherwise>0</c:otherwise></c:choose>"></a><p>
		<p>[${mbrCard.mbrCardTypeName}]</p>
	</div>
</div>
</c:forEach>

<div class="pd10">
  <input type="button" value="绑定会员卡" class="butstyleC js-bindMbrCard"/>
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

<div class="am-modal am-modal-alert" tabindex="-1" id="alertTip">
  <div class="am-modal-dialog">
    <div class="am-modal-bd" id="alertMsg">
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn">确定</span>
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
			showQr("js-qr"+index, 60, 60, _this.attr("data-code"));
		});
		
		$(".erm").click(function() {
			
			var code = $(this).attr("data-code");
			if(code!="0"){
				$("#showCouponCode").html("");
				showQr("showCouponCode", 150, 150, code);
				$("#couponCode").modal();
			}else{
				$("#alertMsg").html("线下绑定的会员卡只能查看余额，不能用于支付");
				$("#alertTip").modal();
			}
		});
		
		$(".js-bindMbrCard").click(function() {
			window.location.href="/oauth/member/toMbrCardBind.do";
		});
	});
	
	function showQr(id, width, height, text) {
		$("#"+id).qrcode({width:width,height:height,text:text});
	}
</script>
</body>
</html>