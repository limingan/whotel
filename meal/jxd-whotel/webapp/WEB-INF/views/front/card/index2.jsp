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
.sm20_icon_mbrCharge{ background:url(/static/front/hotel/images/cz.jpg) no-repeat;background-size: 20px 20px;}
</style>
</head>
<body>

<input type="hidden" id="js-cardNo" value="${memberVO.mbrCardNo}" />
<div class="I_header">
	<header class="I_titlebox">
		<div class="I_title_l">
			<a href="javascript:closeOrGoback()" class="header_icon_back"></a>
		</div>
		<h1 class="I_title">会员中心</h1>
		<div class="I_title_r">
			<a href="javascript:void(0)" id="js-qr" class="header_icon_bar"><img src="/static/front/hotel/images/term1.png"></a>
		</div>
	</header>
	<div class="user_topbox">
		<div class="user_avatar_box">
			<a href="/oauth/member/fillInfo.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">
				<p class="user_avatar"> <img src="${fan.avatar}" /></p>
				<p class="name">${member.name}<i class="Wmore_icon"></i></p>
			</a>
		</div>
	</div>
</div>

<c:if test="${companyNotice != null}">
<div class="i_notice"><a href="/oauth/member/listCompanyNotices.do?id=${companyNotice.id}&comid=${WEIXINFAN_LOGIN_COMPANYID}"><i class=" more_icon_12 fr"></i><p><i class="sm12_icon_notice"></i>${companyNotice.title}</p></a></div>
</c:if>
<div class="user_infobox_B">
	<ul class="user_info_B">
		<li><a href="/oauth/member/balanceTrade.do?comid=${WEIXINFAN_LOGIN_COMPANYID}"> <span class="mstxt-1">余额</span>
				<span class="nb">￥<b><fmt:formatNumber value="${memberVO.balance}" type="currency" pattern="#######.##"/></b>
			</span>
		</a></li>
		<li><a href="/oauth/member/cashTrade.do?comid=${WEIXINFAN_LOGIN_COMPANYID}"><span class="mstxt-2">返现</span>
				<span class="nb">￥<b><fmt:formatNumber value="${memberVO.incamount}" type="currency" pattern="#######.##"/></b>
			</span> </a></li>
		<li><a href="javascript:integralConvert()"> <span class="mstxt-3">积分</span><span
				class="nb"><b><fmt:formatNumber value="${memberVO.validScore}" type="currency" pattern="#######.##"/></b></span>
		</a></li>
		<li><a href="/oauth/member/memberCoupon.do?comid=${WEIXINFAN_LOGIN_COMPANYID}"> <span class="mstxt-4">优惠券</span><span
				class="nb"><b>${memberVO.ticketqty}</b>张</span>
		</a></li>
	</ul>
</div>

<div class="user_list_box">
	<c:if test="${multipleMbr}">
	  <h4 class=" pdlr10_5">会员卡</h4>
	  <ul class="user_list">
	    <li><i class="sm20_icon_mbrCard" ></i><a href="/oauth/member/listMbrCard.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">会员卡<i class="more_icon_12 fr"></i></a></li>
	  	<c:if test="${sysParamConfig.isRecharge != false}">
	  		<li><i class="sm20_icon_mbrCharge" ></i><a href="/pay/toCardCharge.do?showwxpaytitle=1">会员充值<i class="more_icon_12 fr"></i></a></li>
	  	</c:if>	
	  </ul>
	</c:if>
  <h4 class=" pdlr10_5">我的订单</h4>
  <ul class="user_list">
    <li><i class="sm20_icon_shoping" ></i><a href="/oauth/hotel/listHotelOrder.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">全部订单<i class="more_icon_12 fr"></i></a></li>
    <li><i class="sm20_icon_Wallet"></i><a href="/oauth/hotel/listHotelOrder.do?tradeStatus=WAIT_PAY&comid=${WEIXINFAN_LOGIN_COMPANYID}">未付款订单<i class="more_icon_12 fr"></i></a></li>
  </ul>
  <h4 class=" pdlr10_5">常用信息</h4>
  <ul class="user_list">
    <li><i class="sm20_icon_infozl"></i><a href="/oauth/member/listGuests.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">常用联系人<i class="more_icon_12 fr"></i></a></li>
    <li><i class="sm20_icon_weizhi"></i><a href="/oauth/member/toListContactAddress.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">邮寄地址<i class="more_icon_12 fr"></i></a></li>
  </ul>
</div>

<jsp:include page="card_header2.jsp" />

<c:set var="mbrQrTime" value="${sysParamConfig.showMbrQrTime==null?0:sysParamConfig.showMbrQrTime}"/>
<div class="am-modal am-modal-no-btn" tabindex="-1" id="cardEwm">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">
			<a href="javascript: closeQr()" class="am-close am-close-spin">&times;</a><!-- data-am-modal-close -->
		</div>
		<c:if test="${mbrQrTime>0}">
			<div>倒计时：<span id="js_countdown" style="color: red;">${sysParamConfig.showMbrQrTime}</span></div>
		</c:if>
		<div class="am-modal-bd" id="showCardEwm"></div>
	</div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="js_alert">
  <div class="am-modal-dialog">
    <div class="am-modal-hd"></div>
    <div class="am-modal-bd">
    	<span>暂未开启此项功能</span>
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>确定</span>
    </div>
  </div>
</div>

<input type="hidden" id="js_isIntegralConvert" value="${sysParamConfig.isIntegralConvert}"/>
<input type="hidden" id="js_companyId" value="${WEIXINFAN_LOGIN_COMPANYID}"/>
<input id="js_showMbrQrTime" type="hidden" value="${mbrQrTime}"/>
<script src="/static/common/js/jquery-1.11.2.js"></script>
<script src="/static/common/js/jquery.qrcode.min.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script src="/static/common/js/qrapi.js"></script>
<script type="text/javascript">
var time = 0;
var interval;
$(function() {
	showQr("showCardEwm", 150, 150, $("#js-cardNo").val());
	
	$("#js-qr").click(function() {
		$("#cardEwm").modal({closeViaDimmer: 0});
		
		time = parseInt($("#js_showMbrQrTime").val());
		$("#js_countdown").text(time);
		if(time>0){
			interval = setInterval("countdown()",1000);
		}
	});
});

function integralConvert(){
	var comid = $("#js_companyId").val();
	var isIntegralConvert = $("#js_isIntegralConvert").val();
	if(isIntegralConvert!='false'){
		window.location.href="/oauth/member/creditTrade.do?comid="+comid; 
	}else{
		$("#js_alert").modal();
	}
}

function closeQr(){
	clearInterval(interval);
	$("#cardEwm").modal('close');
}

function countdown(){
	time = time - 1;
	$("#js_countdown").text(time);
	if(time<=0){
		closeQr();
	}
}
function closeOrGoback() {
	if(document.referrer === '') {
		WeixinJSBridge.call('closeWindow');
	} else {
		window.history.go(-1);
	}
}
</script>
</body>
</html>