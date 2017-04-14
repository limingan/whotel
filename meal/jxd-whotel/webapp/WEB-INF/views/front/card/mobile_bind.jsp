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
</head>
<body>
<c:set var="headerTitle" value="注册会员" scope="request"/>
<jsp:include page="card_header.jsp" />

<div class="loading" id="loading"></div>

<div class="belinkedbox">
  <form action="/oauth/member/bindMember.do" method="post" id="bindForm">
  <input type="hidden" value="${toRemindMsg}" id="js_toRemindMsg"/>
  <input type="hidden" name="oldMobile" value="${member.mobile}"/>
  <ul class="belinkedform  pd20">
     <li class="min">
     	<c:if test="${empty hyyljt}">
     		<input name="name" type="text" placeholder="填写姓名" value="${member.name}" class="mobile_nb wd100" id="name"/>
     	</c:if>
     	<c:if test="${!empty hyyljt}">
     		<input name="mbrCardNo" type="text" placeholder="填写卡号" value="${member.mbrCardNo}" class="mobile_nb wd100" id="name"/>
     	</c:if>
    </li>
    <li class="hm clearfix">
      <input type="button" value="获取验证码" class="butCode_hq" id="verifBtn"/>
      <input type="tel" name="mobile" placeholder="输入手机号" value="${member.mobile}" class="mobile_nb" id="mobile"/>
    </li>
    <li class="min">
      <input type="tel" name="verifyCode" placeholder="输入验证码" id="verifyCode"/>
    </li>
  </ul>
  </form>
  <div class="pd20">
    <input type="button" value="下一步" class="butstyleB" id="js-nextBind"/>
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
<script src="/static/common/js/smsvalide.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
$(function() {
	
	var toRemindMsg = $("#js_toRemindMsg").val();
	if(toRemindMsg.length>0){
		$("#alertMsg").html(toRemindMsg);
		$("#alertTip").modal();
	}
	
	$("#verifBtn").click(function() {
		  var $this = $(this);
		  if(!$this.hasClass("over")) {
			  sendMsm($this, errorMobile);
		  }
	});
	
	$("#js-nextBind").click(function() {
		if(checkName()) {
			$(this).attr("disabled",true);
			checkSmsCode(codeRight, codeError);
		}
	});
});

function errorMobile() {
	$("#alertMsg").html("手机号码不正确");
	$("#alertTip").modal();
}

function codeRight() {
	var $loading = $("#loading");
	$loading.show();
	var url = $("#bindForm").attr('action');
	
	$.ajax({
		url : url,
		async:false,
		type : 'post',
		data : $("#bindForm").serialize(),
		dataType : "json",
		success : function(data) {
			$loading.hide();
			if('success' == data) {
				window.location.href = '/oauth/member/index.do';
			} else {
				window.location.href = '/oauth/member/toBindMember.do?toRemindMsg=' + data;
			}
		}
	});
}

function codeError() {
	$("#alertMsg").html("手机验证码不正确");
	$("#alertTip").modal();
	$("#js-nextBind").attr("disabled",false);
}

function checkName() {
	var name = $("#name").val();
	if($.trim(name) == "") {
		var hyyljt = "${hyyljt}";
		if(hyyljt != null && hyyljt.length > 0) {
			$("#alertMsg").html("卡号不能为空！");
		} else {
			$("#alertMsg").html("姓名不能为空！");
		}
		$("#alertTip").modal();
		return false;
	}
	return true;
}
</script>
</body>
</html>