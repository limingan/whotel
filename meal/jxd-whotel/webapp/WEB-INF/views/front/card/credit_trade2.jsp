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
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
<style type="text/css">
.header {
    height: 45px;
    background: #FFFFFF;
    color: #363636;
    position: relative;
}
.uesrtop_box {
    background: #FFFFFF;
    height: 160px;
    padding: 20px 30px;
}
.uesrtop_ye {
    color: #363636;
}
.header_icon_back {
    background: url(/static/front/images/fanhui.png) no-repeat center center;
    background-size: 25px 25px;
    left: 10px;
}
</style>
</head>
<body style="background:#CFCFCF;">

<header class="header">
  <div class="header_left"><a href="javascript:history.go(-1)" class="header_icon_back"></a></div>
  <div class="header_right">
    <nav data-am-widget="menu" class="am-menu  am-menu-dropdown1"  
     data-am-menu-collapse  style="display:block" > <a href="javascript: void(0)" class="header_icon_bar am-menu-toggle"> </a>
      <ul class="am-menu-nav am-avg-sm-1 am-collapse">
		<c:choose>
			<c:when test="${COMPANY_THEME!=null}">
				<c:forEach items="${COMPANY_THEME.columns}" var="column">
					<li><a href="${column.target}">${column.name}</a></li>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<li><a href="/oauth/website.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">首页</a></li>
			   	<li><a href="/oauth/hotel/hotelSearch.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">酒店预订</a></li>
			   	<li><a href="/oauth/member/index.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">会员中心</a></li>
			   	<li><a href="/oauth/hotel/listHotelOrder.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">我的订单</a></li>
			</c:otherwise>
		</c:choose>
      </ul>
    </nav>
  </div>
</header>

<div class="uesrtop_box pd30">
    <div style="text-align: center;margin:-15px auto 0px auto;background: url(/static/front/images/wdjf.png) no-repeat;background-size: 100% 100%;width: 115px;height: 115px;line-height: 115px;color: #ffffff;font-size: 18px;font-weight: bold;">
    	<fmt:formatNumber value="${memberVO.canUseScore==null?0:memberVO.canUseScore}" pattern="0"/>
    </div>
  	<div style="text-align: center;font-size: 14px;">可用积分</div>
</div>

<ul style="background: #FFFFFF;margin-bottom: 3px;text-align: center;">
	<li style="width: 50%;border: 1px solid #E3E3E3;float: right;height: 45px;line-height: 45px;">
		<a href="/oauth/member/creditTradeDetail.do?tradeType=DEDUCT"><img src="/static/front/images/dhjl.png" width="21px" height="21px;">&nbsp;兑换记录</a>
	</li>
	<li style="width: 50%;border: 1px solid #E3E3E3;height: 45px;line-height: 45px;">
		<a href="/oauth/member/creditTradeDetail.do"><img src="/static/front/images/jfmx.png" width="21px" height="21px;">&nbsp;积分明细
	</a></li>
</ul>

<div style="background: #FFFFFF;">
	<p style="height: 45px;line-height: 45px;padding-left: 20px;">
		<img src="/static/front/images/jfsc.png" width="17px" height="17px;" style="margin-bottom: 5px;">&nbsp;积分商城
	</p>
	<ul><c:forEach items="${giftVOs}" var="gift" varStatus="vs">
		<li style="width: 50%;border: 1px solid #E3E3E3;padding: 10px;float: left;background: #FFFFFF;height: 250px;"><a href="/oauth/member/toCreditExchangeDetail.do?itemId=${gift.itemId}">
			<p><img src="/static/front/images/remai.png" width="21px" height="21px;">${gift.itemCName}</p>
			<p style="color: #FDA62E;"><img src="/static/front/images/yingbi.png" width="21px" height="21px;"><fmt:formatNumber value="${gift.score}" type="currency" pattern="#######.##"/></p>
			<p style="height: 170px;;margin-top: 8px;background: #FFFFFF;"><img src="${gift.giftPic}" width="100%" height="165px"></p>
		</a></li>
	</c:forEach></ul>
<div>
<div class="js_loadData lodingtxet" style="margin-bottom: 0px;">没有更多数据</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="js_alert">
  <div class="am-modal-dialog">
    <div class="am-modal-hd"></div>
    <div class="am-modal-bd">
    	<span>${message}</span>
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>确定</span>
    </div>
  </div>
</div>

<input type="hidden" id="js_message" value="${message}"/>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
$(function() {
	var msg = $("#js_message").val();
	if(msg.length > 0){
		$("#js_alert").modal();
		$("#js_message").val("");
	}
});
</script>
</body>
</html>