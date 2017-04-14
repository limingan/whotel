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
<c:set var="headerTitle" value="充值" scope="request"/>
<jsp:include page="card_header.jsp" />

<div class="Recharge_over">
  <h3>帐户余额：<b class="colorking">¥<fmt:formatNumber value="${memberVO.balance}" type="currency" pattern="#######.##"/></b></h3>
</div>
<div class="RechargeMoney">
  <%-- <dl>
    <dt>选择充值额</dt>
    <c:forEach items="${chargeMoneyVO.moneys}" var="money">
    	<dd><a href="javascript:" class="js-chargeMoney" data-money="${money}">¥${money}</a> </dd>
    </c:forEach>
  </dl> --%>
  
  <div class="Othermony"><a href="javascript:" id="js-otherChargeMoney">输入金额&nbsp&nbsp<!-- <i class=" Dmore_icon_12"></i> --></a>
      <input type="text" placeholder="输入金额" class="inputstyle_A" id="chargeMoney"/>
    <p class="AMinput js-otherMoney">
    </p>
  </div>
</div>
<div class="form_cr_style_C pdtd20_10">
  <input name="payType" type="radio" id="storedpay_wx" value="WXPAY" class="mrg-r10 " checked/>
  <label for="storedpay_wx" >微信支付</label>
</div>
<div class="pd20">
   <input type="hidden" id="js_isRecharge" value="${sysParamConfig.isRecharge}"/>
   <input type="button" value="充值" class="butstyleB" id="chargeBtn"/>
</div>
  
<div id="tradeForm" style="display: none"></div>
<div class="loading" id="loading"></div>

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
	  $("#chargeBtn").click(function() {
		  var isRecharge  =$("#js_isRecharge").val();
		  if(isRecharge == 'false'){
			  $("#alertMsg").html("此功能暂未开放！");
			  $("#alertTip").modal();
			  return; 
		  }
		  
		  var payType = $("input[name='payType']:checked").val();
		  if(payType && payType != "") { 
			  var floatReg = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
			  var moneyNum = $.trim($("#chargeMoney").val());
			  
			  var activeMoney = $(".active").attr("data-money");
			  if(!activeMoney && moneyNum == "") {
				  $("#alertMsg").html("请指定充值金额！");
				  $("#alertTip").modal();
				  return; 
			  }
			  
			  if (moneyNum != "" && !floatReg.test(moneyNum)) {
				  $("#alertMsg").html("请输入数字类型的金额,小数点后最多保留两位");
				  $("#alertTip").modal();
				  return;
			  }
			  
			  wxPay();
		  } else {
			  $("#alertMsg").html("请选择支付方式");
			  $("#alertTip").modal();
		  }
	  });
	  
	  $(".js-chargeMoney").click(function() {
		  var _this = $(this);
		  $(".js-chargeMoney").removeClass("active");
		  _this.addClass("active");
	  });
	  
	  $("#js-otherChargeMoney").click(function() {
		  var otherMoney = $(".js-otherMoney");
		  if(otherMoney.css("display") == "block") {
			  otherMoney.hide();
		  } else {
			  otherMoney.show();
		  }
	  });
  });
  
  	function wxPayCallBack(orderSn,res) {
		if(res.err_msg=='get_brand_wcpay_request:ok'){
			document.location = "/oauth/member/balanceTrade.do";
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
		var chargeMoney = $("#chargeMoney").val();
		var totalFee = 0;
		if(typeof(chargeMoney)!="undefined" && chargeMoney != "") {
			totalFee = parseInt(parseFloat(chargeMoney) * 100);
		} else {
			totalFee = parseInt(parseFloat($(".active").attr("data-money")) * 100);
		}
		
		$.ajax({
			type : "POST",
			url : "/pay/wxpay.do",
			data:{name:"会员充值", totalFee:totalFee},
			cache:false,
			dataType : 'html',
			success : function(rs) {
				$loading.hide();
				if (rs && rs != "") {
					$("#tradeForm").html(rs);
				} else {
					// document.referrer
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
  </script>
</body>
</html>