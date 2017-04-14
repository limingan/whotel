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
<c:set var="headerTitle" value="会员卡" scope="request"/>
<jsp:include page="card_header.jsp" />

<input type="hidden" id="js-cardNo" value="${memberVO.mbrCardNo}"/>
<div class="zh_card_bg">
  <div class="zh_card" >
    <p class="zh_card_Nb">NO:${memberVO.mbrCardNo}</p>
    <div class="zh_card_en">
      <div class="zh_card_lx" >
        <h1>会员卡</h1>
        <p>${memberVO.mbrCardTypeName}</p>
      </div>
      <p class="erm"><a href="javascript:" id="js-qr"></a></p>
    </div>
  </div>
</div>
<div class="zh_infobox">
  <ul class="zh_info">
    <li>
     <a href="/oauth/member/balanceTrade.do">
      <i class="more_icon fr"></i> 
      <p><i class="user_icon_ye"></i>储值余额：</p>
      <p><b>¥<fmt:formatNumber value="${memberVO.balance}" type="currency" pattern="#######.##"/></b></p>
     </a>
    </li>
    <li><a href="/oauth/member/cashTrade.do"><i class="more_icon fr"></i>
      <p><i class="user_icon_fx"></i>返现余额：</p>
      <p><b>¥<fmt:formatNumber value="${memberVO.incamount}" type="currency" pattern="#######.##"/></b></p>
      </a>
    </li>
    <li><a href="/oauth/member/creditTrade.do"><i class="more_icon fr"></i>
      <p><i class="user_icon_jf"></i>积分：</p>
      <p><b><fmt:formatNumber value="${memberVO.validScore}" type="currency" pattern="#######.##"/></b></p>
      </a>
    </li>
    <li><a href="/oauth/member/memberCoupon.do"><i class="more_icon fr"></i>
      <p><i class="user_icon_yh"></i>优惠券：</p>
      <p><b>${memberVO.ticketqty}</b></p>
      </a>
    </li>
  </ul>
</div>
<script src="/static/common/js/jquery-1.11.2.js"></script>
<script src="/static/common/js/jquery.qrcode.min.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
	$(function() {
		showQr("js-qr", 65, 65, $("#js-cardNo").val());
	});
	
	function showQr(id, width, height, text) {
		$("#"+id).qrcode({width:width,height:height,text:text});
	}
	</script>
</body>
</html>