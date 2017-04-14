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
.js-chargeMoney{color:#6B6B6B;display: inline-block;width: 25%;text-align: center;height: 50px;border: 1px solid #EBEBEB;border-radius: 4px;margin: 0px 15px 15px 15px;}
.checkChargeMoney{color:#ff8400;display: inline-block;width: 25%;text-align: center;height: 50px;border: 1px solid #ff8400;border-radius: 4px;margin: 0px 15px 15px 15px;}
.sendMoney{font-size: 12px;}
</style>
</head>
<body>
<c:set var="headerTitle" value="充值" scope="request"/>
<jsp:include page="card_header.jsp" />

<div class="Recharge_over" style="border-bottom: 1px solid #E8E7E7;">
  <h3>帐户余额：<b class="colorking">¥<fmt:formatNumber value="${memberVO.balance}" type="currency" pattern="#######.##"/></b></h3>
</div>
<div style="background: #fff;border-bottom: 1px solid #E8E7E7;"><!-- class="RechargeMoney" -->
	<div class="Othermony"><!-- <a href="javascript:" id="js-otherChargeMoney"><i class=" Dmore_icon_12"></i></a> -->
      	<input type="text" placeholder="手动输入充值金额" class="inputstyle_A" id="chargeMoney" style="border: none;margin: 10px;outline:none;"/>
		<!-- <p class="AMinput js-otherMoney"></p> -->
		<input type="hidden" id="js_chargeMoney" value="0"/>
		<input type="hidden" id="js_incamount" value="0"/>
  	</div>
  	<ul><c:forEach items="${chargeMoneyVO.moneys}" var="money">
    	<li class="js-chargeMoney" data-money="${money}"><%-- <a href="javascript:"> --%>
    		<span style="font-weight: bold;">${money}</span><br/>
    		<span class="sendMoney">赠送：${money}元</span>
    	<!-- </a> --></li>
    </c:forEach></ul>
</div>

<div class="form_cr_style_C pdtd20_10">
  <input name="payType" type="radio" id="storedpay_wx" value="WXPAY" class="mrg-r10 " checked/>
  <label for="storedpay_wx" >微信支付</label>
</div>
<div style="padding: 0px 20px;">
   <input type="hidden" id="js_isRecharge" value="${sysParamConfig.isRecharge}"/>
   <button class="butstyleB" id="chargeBtn">充值</button>
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
	  $("#chargeMoney").focus(function(){
		  $(".js-chargeMoney").removeClass("checkChargeMoney");
		  
		  $("#js_chargeMoney").val(0);
		  $("#js_incamount").val(0);
	  });
	  
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
			  
			  var activeMoney = $(".checkChargeMoney").attr("data-money");
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
		  //$(".js-chargeMoney").removeClass("active");
		  _this.addClass("checkChargeMoney").siblings("li").removeClass("checkChargeMoney");
		  $("#chargeMoney").val("");
		  $("#js_chargeMoney").val(_this.attr("data-money"));
		  $("#js_incamount").val(_this.attr("data-money"));
		  //_this.find(".sendMoney").css("color","#ff8400").siblings("span").css("color","#A8A8A8");
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
		/* if(typeof(chargeMoney)!="undefined" && chargeMoney != "") {
			totalFee = parseInt(parseFloat(chargeMoney) * 100);
		} else {
			totalFee = parseInt(parseFloat($(".active").attr("data-money")) * 100);
		} */
		if(chargeMoney.length<=0){
			totalFee = parseInt(parseFloat($("#js_chargeMoney").val()) * 100);
		}else{
			totalFee = parseInt(parseFloat(chargeMoney) * 100);
		}
		
		var incamount = parseInt($("#js_incamount").val());
		
		$.ajax({
			type : "POST",
			url : "/pay/wxpay.do",
			data:{name:"会员充值", totalFee:totalFee,incamount:incamount},
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