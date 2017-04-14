<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${WEIXINFAN_LOGIN_COMPANYNAME}</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common${THEME}.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain${THEME}.css?v=${version}">
</head>
<body>
<c:set var="headerTitle" value="发票信息" scope="request"/>
<jsp:include page="card_header.jsp" />

<div class="plinfobox blockbg">
<ul class="listinfofill invoiceInfo_show">
<li><p class="fr wd70"><label class="inputinfofillstyle_A">${invoiceInfo.name}</label></p>姓名</li>
<li><p class="fr wd70"><label class="inputinfofillstyle_A">
<c:choose>
	<c:when test="${invoiceInfo.type==false}"><label class="invoiceInfo-type">单位</label></c:when>
	<c:otherwise><label class="invoiceInfo-type">个人</label></c:otherwise>
</c:choose>
</label></p>类型
</li>
<c:choose>
	<c:when test="${invoiceInfo.type==null || invoiceInfo.type==false}">
		<li><p class="fr wd70"><label class="inputinfofillstyle_A" >${invoiceInfo.identifyNo}</label></p>税号</li>
		<li><p class="fr wd70"><label class="inputinfofillstyle_A" >${invoiceInfo.address}</label></p>单位地址</li>
		<li><p class="fr wd70"><label class="inputinfofillstyle_A" >${invoiceInfo.phone}</label></p>电话号码</li>
		<li><p class="fr wd70"><label class="inputinfofillstyle_A" >${invoiceInfo.bankName}</label></p>开户银行</li>
		<li><p class="fr wd70"><label class="inputinfofillstyle_A" >${invoiceInfo.account}</label></p>银行账户</li>
	</c:when>
	<c:otherwise>
		<li><p class="fr wd70"><label class="inputinfofillstyle_A">${invoiceInfo.mobile}</label></p>手机</li>
		<li><p class="fr wd70"><label class="inputinfofillstyle_A">${invoiceInfo.email}</label></p>邮箱</li>
	</c:otherwise>
</c:choose>
</ul>
<div>

<div style="text-align: center;padding: 10px;">
	<div id="js_qrCode">
	</div>
	<p>扫描二维码快速获取信息</p>
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

<div class="am-modal am-modal-confirm" tabindex="-1" id="confirmTip">
  <div class="am-modal-dialog">
    <div class="am-modal-bd">
      你，确定要删除吗？
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
    </div>
  </div>
</div>
<input type="hidden" value="${invoiceInfo.def}" id="js_def">
<input type="hidden" id="qrCode" value="11${invoiceInfo.name}→2${invoiceInfo.identifyNo}→3${invoiceInfo.address}、${(invoiceInfo.phone==null||invoiceInfo.phone.trim()=='')?invoiceInfo.mobile:invoiceInfo.phone}→4${invoiceInfo.bankName}${invoiceInfo.account}→GSHISPAY→">
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/jquery.qrcode.min.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script src="/static/common/js/qrapi.js"></script>
<script type="text/javascript">
$(function(){
	showQr("js_qrCode", 150, 150, $("#qrCode").val());
});

</script>

</body>
</html>