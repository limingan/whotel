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
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain.css?v=${version}">
</head>
<body>
<c:set var="headerTitle" value="会员中心" scope="request"/>
<jsp:include page="card_header.jsp" />

<input type="hidden" id="js-cardNo" value="${memberVO.mbrCardNo}"/>
<div class="user_topbox">
  <a href="javascript:" id="js-qr" class="erm"></a>
  <div class="user_avatar_box">
  <a href="/oauth/member/fillInfo.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">
  <span class="user_avatar"><img src="${fan.avatar}"/></span>${member.name}<i class="Wmore_icon"></i>
  </a>
  </div>
  </div>

<c:if test="${companyNotice != null}">
<div class="i_notice"><a href="/oauth/member/listCompanyNotices.do?id=${companyNotice.id}&comid=${WEIXINFAN_LOGIN_COMPANYID}"><i class=" more_icon_12 fr"></i><p><i class="sm12_icon_notice"></i>${companyNotice.title}</p></a></div>
</c:if>
<div class="user_infobox">
  <ul class="user_info">
    <li class="aek"> 
      <a href="/oauth/member/balanceTrade.do?comid=${WEIXINFAN_LOGIN_COMPANYID}"> <i class="user_icon_ye"></i>
      <p>余额<b>￥<fmt:formatNumber value="${memberVO.balance}" type="currency" pattern="#######.##"/></b></p>
      </a>
    </li>
    <li> 
      <a href="/oauth/member/cashTrade.do?comid=${WEIXINFAN_LOGIN_COMPANYID}"> <i class="user_icon_fx"></i>
      <p>返现<b>￥<fmt:formatNumber value="${memberVO.incamount}" type="currency" pattern="#######.##"/></b></p>
      </a> 
    </li>
    <li> 
      <a href="/oauth/member/creditTrade.do?comid=${WEIXINFAN_LOGIN_COMPANYID}"> <i class="user_icon_jf"></i>
      <p>积分<b><fmt:formatNumber value="${memberVO.validScore}" type="currency" pattern="#######.##"/>分</b></p>
      </a> 
    </li>
    <li> 
      <a href="/oauth/member/memberCoupon.do?comid=${WEIXINFAN_LOGIN_COMPANYID}"> <i class="user_icon_yh"></i>
      <p>优惠券<b>${memberVO.ticketqty}张</b></p>
      </a>
     </li>
  </ul>
</div>
<div class="user_list_box">
	<c:if test="${memberVO.subCardCount>0}">
	  <h4 class=" pdlr10_5">会员卡</h4>
	  <ul class="user_list">
	    <li><i class="sm20_icon_mbrCard" ></i><a href="/oauth/member/listMbrCard.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">会员卡<i class="more_icon_12 fr"></i></a></li>
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

<c:set var="mbrQrTime" value="${sysParamConfig.showMbrQrTime==null?0:sysParamConfig.showMbrQrTime}"/>
<div class="am-modal am-modal-no-btn" tabindex="-1" id="cardEwm">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">
      <a href="javascript: closeQr()" class="am-close am-close-spin">&times;</a><!--  data-am-modal-close -->
    </div>
    <c:if test="${mbrQrTime>0}">
		<div>倒计时：<span id="js_countdown" style="color: red;">${sysParamConfig.showMbrQrTime}</span></div>
	</c:if>
    <div class="am-modal-bd" id="showCardEwm">
    </div>
  </div>
</div>

<!--二维码弹框-->
<!-- 
	<div class="Hyewm" >
	<div class="Hyewm_cont">
	</div></div>
 -->
<!--二维码弹框END-->

<input id="js_showMbrQrTime" type="hidden" value="${mbrQrTime}"/>
<script src="/static/common/js/jquery-1.11.2.js"></script>
<script src="/static/common/js/jquery.qrcode.min.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script src="/static/common/js/qrapi.js"></script>
<script type="text/javascript">
var time = 0;
var interval;
	$(function() {
		//showQr("js-qr", 35, 35, utf16to8("会员号："+$("#js-cardNo").val()));
		showQr("js-qr", 35, 35, $("#js-cardNo").val());
		
		//showQr("showCardEwm", 150, 150, utf16to8("会员号："+$("#js-cardNo").val()));
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
	
</script>
</body>
</html>