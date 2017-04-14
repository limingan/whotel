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
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common2.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain2.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/meal/css/main.css?v=${version}">
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
</head>
<body>
<c:set var="headerTitle" value="支付方式" scope="request"/>
<c:set var="order" value="${MEAL_ORDER}"/>
<jsp:include page="meal_header.jsp" />
<input type="hidden" value="${memberVO==null?0:memberVO.balance}" id="memberBalance"/>
<input type="hidden" value="${order.totalFee}" id="orderTotalFee"/>
<input type="hidden" value="${order.orderSn}" id="orderSn"/>
<input type="hidden" value="${order.couponCode}" id="couponCode"/>
<div class="payselectsbox">
<ul class="block_pd">
	<c:forEach items="${order.restaurant.payMents}" var="payMent">
		<c:if test="${payMent == 'WXPAY'}">
			<c:if test="${weixinPay}">
				<li class="form_cr_style_B"><input id="wenxipay"  name="payType" type="radio" value="WXPAY" checked="checked" /><label for="wenxipay" class="pd5" >微信支付</label></li>
			</c:if>
		</c:if>
		<c:if test="${payMent == 'OFFLINEPAY'}">
			<li class="form_cr_style_B"><input id="ddpay" name="payType" type="radio" value="OFFLINEPAY" /><label for="ddpay" class="pd5">到店支付</label></li>
		</c:if>
		<c:if test="${payMent == 'BALANCEPAY'}">
			<li class="form_cr_style_B"><input id="yepay" name="payType" type="radio" value="BALANCEPAY"  /><label for="yepay" class="pd5">余额支付</label></li>
		</c:if>
	</c:forEach>
</ul>

<div class="gzshum">
<div class="ydPolicy form_cr_style">
<input id="provision" type="checkbox" class="mrg-r10" checked/>
<label for="provision" >我已经阅读并接受了</label>
<a href="javascript:" class="js-bookPolicy">购买须知</a>
<div class="ydPolicy_txt js-policyContent">
${order.restaurant.notes}
</div>
</div>
<div class=" pd20">
<input id="confirmPay" type="button" value="确认支付" class="butstyleB" onclick="payOrder()"/></div>
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

<div class="am-modal am-modal-prompt" tabindex="-1" id="my-prompt">
  <div class="am-modal-dialog">
    <div class="am-modal-hd"></div>
    <div class="am-modal-bd">
                  订单总额：￥${order.totalFee}
      <input name="payPwd" type="password" placeholder="请输入支付密码" class="am-modal-prompt-input" id="js-payPwd">
      <p id="js-errorTip" style="color:red;"></p>
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
      <span class="am-modal-btn" data-am-modal-confirm>提交</span>
    </div>
  </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="my-recharge">
  <div class="am-modal-dialog">
    <div class="am-modal-hd"></div>
    <div class="am-modal-bd">
    	<div align="left" style="padding-left: 20px;">您的余额不足，是否充值？<br/>您的余额为：${memberVO==null?0:memberVO.balance}元</div>
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
      <span class="am-modal-btn" data-am-modal-confirm>充值</span>
    </div>
  </div>
</div>

<div class="am-modal am-modal-alert" tabindex="-1" id="setPwdTip">
  <div class="am-modal-dialog">
    <div class="am-modal-hd"></div>
    <div class="am-modal-bd">
      为了您的账户安全，请先设置支付密码！
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn js-setPwd">好的</span>
    </div>
  </div>
</div>
<div class="loading" id="loading"></div>
<input type="hidden" value="${member.payPwd}" id="payPwd"/>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
	$(function() {
		$(".js-bookPolicy").click(function() {
			if($(".js-policyContent").is(":hidden")){
				$(".js-policyContent").show();
			}else{
				$(".js-policyContent").hide();
			}
		});
		
		$(".js-setPwd").click(function() {
			document.location="/oauth/member/toSetPayPwd.do?action=bookRoom";
		});
		$(":radio").click(function() {
			if($(this).val()=="OFFLINEPAY"){
				$("#confirmPay").val("提交订单");
			}else{
				$("#confirmPay").val("确认支付");
			}
		});
	});
	var $loading = $("#loading");
	var isComplete = true;//防止重复点击
	
	function payOrder() {
		if (checkReadPolicy() && isComplete) {
			isComplete = false;
			var payType = $("input[name='payType']:checked").val();
			if (payType && payType != "") {
				$loading.show();
				$.ajax({
					type : "GET",
					url : "/meal/saveMealOrder.do",
					cache : false,
					data : {
						payMent : payType
					},
					dataType : 'text',
					success : function(rs) {
						$loading.hide();
						isComplete = true;
						if(rs == "-2"){
							$("#alertMsg").html("此时间段选定的包间已被预订了，请重新预订");
							$("#alertTip").modal();
						}else if (rs != "-1") {
							if (payType == "WXPAY") {
								wxPay();
							} else if (payType == "BALANCEPAY") {
								var memberBalance = Number($("#memberBalance").val());
								var orderTotalFee = Number($("#orderTotalFee").val());
								if (memberBalance < orderTotalFee) {
									$("#my-recharge").modal({
										relatedTarget: this,
										onConfirm : function(e) {
											window.location.href="/pay/toCardCharge.do";
										},
										onCancel : function(e) {
										}
									});
								} else {
									var payPwd = $("#payPwd").val();
									if (payPwd == "") {
										$("#setPwdTip").modal();
									} else {
										$('#my-prompt').modal({
											relatedTarget : this,
											closeOnConfirm : false,
											onConfirm : function(e) {
												var payPwd = $("#js-payPwd").val();
												var errorTip = $("#js-errorTip");
												if (payPwd != "") {
													$("#my-prompt").modal();
													$loading.show();
													$.ajax({
														url : "/pay/balancePay.do",
														cache : false,
														data : {
															payPwd : payPwd
														},
														dataType : "text",
														success : function(data) {
															if (data == "true") {
																//sendMessage(rs);
																document.location = "/meal/showMealBookRs.do?orderSn="+ rs;
															} else {
																//errorTip.html(data);
																$("#alertMsg").html(data);
																$("#alertTip").modal();
															}
														},
														complete:function (xhr, status) {
															$loading.hide();
															isComplete = true;
														}
													});
												} else {
													errorTip.html("支付密码不能为空！");
												}
												return false;
											},
											onCancel : function(e) {
											}
										});
									}
								}
							} else if (payType == "OFFLINEPAY") {
								//sendMessage(rs);
								document.location = "/meal/showMealBookRs.do?orderSn=" + rs;
							}
						} else {
							// document.referrer
							$("#alertMsg").html("订单已经生成，无需重复操作！");
							$("#alertTip").modal();
						}
					},
					complete:function (xhr, status) {
						$loading.hide();
						isComplete = true;
					}
				});
			} else {
				$("#alertMsg").html("请选择支付方式");
				$("#alertTip").modal();
			}
		}
	}

	function checkReadPolicy() {
		if ($("#provision").prop("checked") == false) {
			$("#alertMsg").html("您还没有阅读预订须知");
			$("#alertTip").modal();
			return false;
		} else {
			return true;
		}
	}

	function wxPayCallBack(orderSn, res) {
		if (res.err_msg == 'get_brand_wcpay_request:ok') {
			//sendMessage(orderSn);
			document.location = "/meal/showMealBookRs.do?orderSn=" + orderSn;
		} else {
			$("#alertMsg").html("支付失败！");
			$("#alertTip").modal();
		}
		//get_brand_wcpay_request:cancel ;  //用户取消 
		//get_brand_wcpay_request:fail  发送失败 
		//get_brand_wcpay_request:ok 发送成功 12
	}

	function wxPay() {
		$loading.show();
		$.ajax({
			type : "GET",
			url : "/pay/wxSessionpay.do",
			cache : false,
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
			error : function(request) {
				$loading.hide();
				$("#alertMsg").html("支付失败！");
				$("#alertTip").modal();
			}
		});
	}
	function sendMessage(rs) {
		$.ajax({
			url : "/meal/sendMealBookMessage.do",
			data : {
				'orderSn' : rs
			},
			dataType : "json",
			success : function(data) {
			}
		});
	}
	function useMemberCoupon(rs) {
		$.ajax({
			url : "/oauth/member/useMemberCoupon.do",
			data : {
				'orderSn' : rs
			},
			dataType : "json",
			success : function(data) {
			}
		});
	}
</script>
</body>
</html>