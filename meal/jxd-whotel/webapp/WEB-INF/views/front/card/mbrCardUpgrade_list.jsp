<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
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
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
</head>
<body>
<c:set var="headerTitle" value="会员卡升级" scope="request"/>
<jsp:include page="card_header.jsp" />
<form action="" id="js_form">
	<input type="hidden" name="mbrCardTypeCode" id="js_code"/>
	<input type="hidden" name="name" value="会员卡升级"/>
	<input type="hidden" name="payMode" value="MBRUPGRADE"/>
	<input type="hidden" name="totalFee" id="js_totalFee"/>
	<c:choose>
		<c:when test="${cardTypes.size() > 0}">
			<div class="Ordertype">
				<ul><c:forEach items="${cardTypes}" var="cardType" varStatus="vs">
					<li style="width: ${100/cardTypes.size()}%;">
						<a href="javascript:cardTypeDetails('${vs.index}')" id="js_hover${vs.index}" data-code="${cardType.mbrCardTypeCode}" data-remark="${cardType.remark}" data-price="${cardType.ellAmount}" <c:if test="${vs.index == 0}">class="hover"</c:if>>${cardType.mbrCardTypeCname}</a>
					</li>
				</c:forEach></ul>
			</div>
			<div class="blockbg">
			  <ul class="myorderlist">
			  	 <li><b style="font-size: 20px;font-weight: bold;"></b><br/>价格：￥<span style="color: red;"></span></li>
			  	 <li></li>
				 <li><input type="button" id="js_submit" value="支付并确定升级" class="butstyleC"/></li>
			  </ul>
			</div>										
		</c:when>
		<c:otherwise>
			<p style="color:red; text-align:center; padding:10px 10px;">无可升级类型！</p>
		</c:otherwise>
	</c:choose>
</form>
<div class="am-modal am-modal-alert" style="z-index: 99999;" tabindex="-1" id="alertTip">
  <div class="am-modal-dialog">
    <div class="am-modal-bd" id="alertMsg">
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn">确定</span>
    </div>
  </div>
</div>

<div class="am-modal am-modal-no-btn" tabindex="-1" id="js-memberBind">
  <div class="am-modal-dialog">
    <div class="am-modal-hd" style="font-size:18px">会员绑定
      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
      <div class="belinkedbox">
		  <form method="post" id="bindForm">
		  <input type="hidden" name="oldMobile" value="${member.mobile}"/>
		  <ul class="belinkedform  pd20">
		     <li class="min">
				<input name="name" type="text" placeholder="填写姓名" value="${member.name}" class="mobile_nb wd100" id="name"/>
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
		     <input type="button" value="保存资料" class="butstyleB js-saveMember"/>
    		 <input type="button" value="跳过" class="butstyleC mrg-t10 js-skip"/>
		  </div>
	  </div>
    </div>
  </div>
</div>

<div id="tradeForm" style="display: none"></div>
<input type="hidden" value="${mbrCardType}" id="js_oldMbrCardType"/>

<div class="loading" id="loading"></div>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script src="/static/common/js/smsvalide.js?v=${version}"></script>
<script type="text/javascript">

$(function() {
	cardTypeDetails(0);
	
	$(".js-saveMember").click(function() {
		if(checkName()) {
			$(".js-saveMember").attr("disabled",true);
			checkSmsCode(codeRight, codeError);
		}
	});
	
	$("#verifBtn").click(function() {
		  var $this = $(this);
		  if(!$this.hasClass("over")) {
			  sendMsm($this, errorMobile);
		  }
	});
	
	$(".js-skip").click(function() {
		$.ajax({
			url:"/oauth/member/ajaxSkipBindMember.do",
			success:function(data) {
				$("#js-memberBind").modal('close');
			}
		});
	});
	
	$("#js_submit").click(function(){
		var oldMbrCardType = $("#js_oldMbrCardType").val();
		if(oldMbrCardType == "") {
			$("#js-memberBind").modal({width:"300px"});
		}else{
			var mbrCardType = $("#js_code").val();
			$.ajax({
				type : "POST",
				url : "/oauth/member/ajaxCheckMbrCardType.do",
				data:{mbrCardType:mbrCardType},
				cache:false,
				async:false,
				success : function(rs) {
					if(rs != ""){
						$("#alertMsg").html(rs);
						$("#alertTip").modal();
					}else{
						wxPay();
					}
				},
				error: function(request) {
					$("#alertMsg").html("网络异常，支付失败！");
					$("#alertTip").modal();
				}
			});
		}
	});
});

function checkName() {
	var name = $("#name").val();
	if($.trim(name) == "") {
		$("#alertMsg").html("姓名不能为空！");
		$("#alertTip").modal();
		return false;
	}
	return true;
}

function errorMobile(msg) { 
	$("#alertMsg").html(msg);
	$("#alertTip").modal();
}

function codeRight() {
	var $loading = $("#loading");
	$loading.show();
	//$(".js-saveMember").unbind("click");
	$.ajax({
		url:"/oauth/member/ajaxBindMember.do",
		data:$("#bindForm").serialize(),
		dataType:"json",
		success:function(data) {
			$loading.hide();
			if(data == "") {
				window.location.href="/oauth/member/listMbrCardUpgrade.do";
			} else {
				$("#alertMsg").html(data);
				$("#alertTip").modal();
			}
			$(".js-saveMember").attr("disabled",false);
			/* $(".js-saveMember").click(function() {
				if(checkName()) {
					checkSmsCode(codeRight, codeError);
				}
			}); */
		}
	});
}

function codeError() {
	$("#alertMsg").html("手机验证码不正确");
	$("#alertTip").modal();
	$(".js-saveMember").attr("disabled",false);
}

function wxPayCallBack(orderSn,res) {
	if(res.err_msg=='get_brand_wcpay_request:ok'){
		$("#loading").show();
		document.location = "/oauth/member/fillInfo.do";
	}else{
		$("#alertMsg").html("支付失败！");
		$("#alertTip").modal();
	}
	//get_brand_wcpay_request:cancel ;  //用户取消 
	//get_brand_wcpay_request:fail  发送失败 
	//get_brand_wcpay_request:ok 发送成功 12
}

var $loading = $("#loading");
function wxPay() {
	$loading.show();
	$.ajax({
		type : "POST",
		url : "/pay/wxpay.do",
		data:$("#js_form").serialize(),
		cache:false,
		async:false,
		success : function(rs) {
			$loading.hide();
			if (rs && rs != "") {
				$("#tradeForm").html(rs);
			} else {
				$("#alertMsg").html("支付失败！");
				$("#alertTip").modal();
			}
		},
		error: function(request) {
			$loading.hide();
			$("#alertMsg").html("网络异常，支付失败！");
			$("#alertTip").modal();
		}
	});
}

function cardTypeDetails(index){
	$("#js_hover"+index).addClass("hover");//.siblings().removeClass("hover");
	$(".Ordertype ul li").each(function(i){
		if(i != index){
			$(this).find("a").removeClass("hover");
		}
	});
	
	
	$(".myorderlist").find("li:first b").text($("#js_hover"+index).text());
	$(".myorderlist").find("li:first span").text($("#js_hover"+index).attr("data-price"));
	$(".myorderlist").find("li:eq(1)").text($("#js_hover"+index).attr("data-remark"));
	$("#js_code").val($("#js_hover"+index).attr("data-code"));
	var price = parseFloat($(".myorderlist").find("li:first span").text())*100;
	$("#js_totalFee").val(price);
}
</script>

</body>
</html>