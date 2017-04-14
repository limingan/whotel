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

<c:set var="headerTitle" value="设置余额支付密码" scope="request"/>
<jsp:include page="card_header.jsp" />

<form action="setPayPwd.do" method="post" id="payPwdForm">
<input type="hidden" name="action" value="${param.action}"/>
<div class="cardpassword_steupbox">
  <h4 class="pdtd10"><i class="sm20_icon_setup"></i>设置余额支付密码</h4>
  <ul class="cardpassword_steup" >
    <c:if test="${member.payPwd != null && member.payPwd != ''}">
    <li><span class="wd30">旧密码</span>
      <input name="oldPayPwd" type="password" class="inputstyle_A wd70" id="oldPayPwd"/>
    </li>
    </c:if>
    <li><span class="wd30">支付密码</span>
      <input name="payPwd" type="password" class="inputstyle_A wd70" id="payPwd"/>
    </li>
    <li><span class="wd30">确认密码</span>
      <input name="rePwd" type="password" class="inputstyle_A wd70" id="rePwd"/>
    </li>
  </ul>
</div>
</form>
<!-- 
<div class="ydPolicy form_cr_style pdlr20_10">
  <input name="" id="provision" type="checkbox" value=""  class="mrg-r10"/>
  <label for="provision" >我已经阅读并接受了</label>
  <a href="#">会员支付密码协议</a>
  <div class="ydPolicy_txt" style="display:none"> 如果您在当地时间下午六点之前入住酒店，我们会保证您入住预订的房间。如果您六点之后到达，我们可能不能保证提供您所需要的房间或房型选择。</div>
</div>  -->
<div class=" pd20">
  <input type="button" value="提交设置" class="butstyleB js-setPayPwd"/>
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
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
$(function() {
	var message = '${param.message}';
	if(message != '') {
		$("#alertMsg").html(message);
		$("#alertTip").modal();
	}
	
	$(".js-setPayPwd").click(function() {
		var oldPayPwd = $("#oldPayPwd").val();
		var payPwd = $("#payPwd").val();
		var rePwd = $("#rePwd").val();
		
		if(typeof(oldPayPwd) != "undefined" && $.trim(oldPayPwd) == "") {
			$("#alertMsg").html("旧密码不能为空！");
			$("#alertTip").modal();
			return false;
		}
		
		if($.trim(payPwd) == "") {
			$("#alertMsg").html("支付密码不能为空！");
			$("#alertTip").modal();
			return false;
		}
		
		if($.trim(payPwd) != $.trim(rePwd)) {
			$("#alertMsg").html("确认密码不一致！");
			$("#alertTip").modal();
			return false;
		}
		
		$("#payPwdForm").submit();
	});
});
</script>
</body>
</html>