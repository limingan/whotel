<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1">
<title>确认支付</title>
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}1"/>
<link rel="stylesheet" type="text/css"  href="/static/front/css/pay.css?v=${version}1"/>
</head>

<body>
    <c:set var="order" value="${PAY_ORDER}"/>
	<dl class="pay-title">
	 	<p class="pay-num-tip">
	 	<span style="position: absolute; top:2px;">￥</span>
	 	<span style="padding-left: 35px;">
	 	<fmt:formatNumber value="${order.totalFee / 100}" type="currency" pattern="#######.##"/>
	 	</span>
	 	</p>
	</dl>
	<dl class="pay-desc-box">
		<ul class="pay-desc">
  			<li>收款方<span>${order.company.name}</span></li>
			<li>商　品<span>${order.name}</span></li>
		</ul>
	</dl>
	<dl class="pay-sub-box">
		<a href="javascript:" id="payNow">微信支付</a>
	</dl>

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
	<script type="text/javascript" src="/static/common/js/jquery-1.11.2.js"></script>
	<script src="/static/common/js/amazeui/amazeui.min.js"></script>
	<script type="text/javascript">
		function wxPayCallBack(orderSn,res) {
			if(res.err_msg=='get_brand_wcpay_request:ok'){
				document.location = document.referrer
			}else{
				$("#alertMsg").html("支付失败！");
				$("#alertTip").modal();
			}
			//get_brand_wcpay_request:cancel ;  //用户取消 
			//get_brand_wcpay_request:fail  发送失败 
			//get_brand_wcpay_request:ok 发送成功 12
		}
		
		var $loading = $("#loading");
		function pay() {
			$loading.show();
			$.ajax({
				type : "GET",
				url : "/pay/wxSessionpay.do",
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
					$("#alertMsg").html("支付失败！");
					$("#alertTip").modal();
				}
			});
		}
		
		$(function() {
			$("#payNow").click(pay);
		});
		
	</script>
</body>
</html>
