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
	<dl class="pay-title">
	 	<p class="pay-num-tip">
	 	<span style="position: absolute; top:2px;">￥</span>
	 	<span style="padding-left: 35px;">
	 		<input type="text" id="js_totalFee" style="width: 80%;">
	 	</span>
	 	</p>
	</dl>
	<dl class="pay-desc-box">
		<ul class="pay-desc">
  			<li>收款方<span>${company.name}</span></li>
		</ul>
	</dl>
	<dl class="pay-sub-box">
		<a href="javascript:void(0)" id="payNow">微信支付</a>
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
	
	<input type="hidden" value="<c:if test="${hotelCode!=null && hotelCode!='null'}">${hotelCode}</c:if>" id="hotelCode">
	<script type="text/javascript" src="/static/common/js/jquery-1.11.2.js"></script>
	<script src="/static/common/js/amazeui/amazeui.min.js"></script>
	<script type="text/javascript">
		function sendMessage(rs) {
			$.ajax({
				url : "/oauth/pay/sendPaySuccessMessage.do",
				data : {'orderSn' : rs},
				dataType : "json",
				success : function(data) {
				}
			});
		}
	
		function wxPayCallBack(orderSn,res) {
			if(res.err_msg=='get_brand_wcpay_request:ok'){
				$("#alertMsg").html("支付成功！");
				$("#alertTip").modal();
				//sendMessage(orderSn);
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
			
			$("#payNow").click(function (){
				if($("#js_totalFee").val()==""){
					$("#alertMsg").html("请输入金额！");
					$("#alertTip").modal();
					return;
				}
				var totalFee = parseFloat($("#js_totalFee").val())*100;
				$.ajax({
					type : "GET",
					url : "/oauth/pay/ajaxSavePayOrder.do",
					data:{totalFee:parseInt(totalFee),hotelCode:$("#hotelCode").val()},
					async:false,
					dataType:"json",
					success : function(rs) {
						if (rs != "-1") {
							pay();
						} else {
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
			});
			$("#js_totalFee").keyup(function(){
		    	var tmptxt=$(this).val();
		    	var reg = /^\d+\.{0,1}\d{0,2}$/;
		    	if(reg.test(tmptxt)){
			    	$(this).val(tmptxt);
		    	}else{
		    		$(this).val('');
		    	}
		    });
		});
		
	</script>
</body>
</html>
