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
<style type="text/css">
.qrCss{margin: 4px;}
.imgShow{width: 60%;height: 270px;background: #3E3E3E;margin-left: 21%;border-radius: 19px;margin: 10px 21% 10px 21%;text-align: -webkit-center;}
.ermCss{background: #fff;width: 129px;height: 129px;border-radius: 8px;position: relative;top: 20%;}
.mbrCardDetails{background: #fff;padding: 15px;color:#555;height: 100%;}
.detailsShown{margin: 6px;border-bottom: 1px solid #E8E7E7;}
.detailsShownLast{margin: 6px;}
.img{padding: 0px 8px 5px 15px;}
.validTerm{margin: 6px 0px 6px 24px;/* float: left; */}
.remark{text-align:right;margin: 6px;}
.showMoney{color:red;}
.mbrExpired{color: black;}
.mbrTypeCss{color: #f5f5f5;position: relative;top: 100px;text-align: left;padding: 10px;}
.remarkCss{border: 1px solid #E8E7E7;border-radius: 7px;padding: 10px;display: none;}
</style>
</head>
<body>

<c:set var="headerTitle" value="会员卡" scope="request"/>
<jsp:include page="card_header.jsp" />
<div class="Ordertype">
	<ul>
		<li style="width: 50%;"><a class="js-bindMbrCard">绑定会员卡</a></li>
		<li style="width: 50%;"><a class="hover"><c:choose><c:when test="${WEIXINFAN_LOGIN_COMPANYID=='5796b223d5e1ea4e5c3b4e2a'}">皇室卡</c:when><c:otherwise>会员卡</c:otherwise></c:choose></a></li>
	</ul>
</div>

<c:if test="${mbrCards.size()>0}">
	<div class="am-slider am-slider-default" id="js_slider">
		  <ul class="am-slides">
		    <c:forEach items="${mbrCards}" var="mbrCard" varStatus="vs">
		    	<li style="height: 300px;">
		    		<div class="imgShow" <c:if test="${mbrCard.picUrl!=null && mbrCard.picUrl!=''}">style="background: url(${mbrCard.picUrl})  no-repeat;background-size: 100% 100%;"</c:if>>
		    			<div class="ermCss">
				    		<a href="javascript:" class="erm" id="js-qr${vs.index}" data-code="<c:choose><c:when test="${mbrCard.onLinePay=='1'}">${mbrCard.mbrGuId}</c:when><c:otherwise>0</c:otherwise></c:choose>"></a>
		    			</div>
		    			<div class="mbrTypeCss">[${mbrCard.mbrCardTypeName}]</div>
		    		</div>
		    		<div id="js_cardDetails${vs.count}" style="display: none;">
		    			<input class="js_mbrCardNo" value="${mbrCard.mbrCardNo}"/>
		    			<input class="js_baseamtbalance" value="<fmt:formatNumber value="${mbrCard.baseamtbalance}" type="currency" pattern="0.00"/>"/>
		    			<input class="js_incamount" value="<fmt:formatNumber value="${mbrCard.incamount}" type="currency" pattern="0.00"/>"/>
		    			<input class="js_validTerm" value="${mbrCard.mbrExpired}"/>
		    			<input class="js_remark" value="${typeMap[mbrCard.mbrCardType]}"/>
		    		</div>
		    	</li>
		    </c:forEach>
		  </ul>
	</div>
	
	<div class="mbrCardDetails">
		<div style="border: 1px solid #E8E7E7;border-radius: 7px;">
			<p class="detailsShown"><img class="img" src="/static/front/images/cardNo.png"/>卡&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp号：<span id="js_mbrCardNo"></span></p>
			<p class="detailsShown"><img class="img" src="/static/front/images/fanx.png"/>本金余额：<span class="showMoney" id="js_baseamtbalance">￥0.00</span></p>
			<p class="detailsShownLast"><img class="img" src="/static/front/images/money.png"/>赠送余额：<span class="showMoney" id="js_incamount">￥0.00</span></p>
		</div>
		<p class="validTerm mbrExpired" id="js_validTerm">有效期限：</p>
		<p class="remark"><a style="color: #26b3ee;" href="javascript:showRemark()">使用权益说明∨</a></p>
		<div id="js_remark" class="remarkCss"></div>
	</div>
</c:if>

<!-- <div class="pd10">
  <input type="button" value="绑定会员卡" class="butstyleC js-bindMbrCard"/>
</div> -->

<div class="am-modal am-modal-no-btn" tabindex="-1" id="couponCode">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">
      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd" id="showCouponCode">
    </div>
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
<c:if test="${mbrCards.size()>0}">
	<input type="hidden" id="js_genGuIdDate" value="${mbrCards[0].residualTime}">
</c:if>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/jquery.qrcode.min.js"></script>
<script src="/static/common/js/amazeui/amazeui.js"></script>
<script type="text/javascript">
	$(function() {
		$(".erm").each(function(index) {
			var _this = $(this);
			showQr("js-qr"+index, 120, 120, _this.attr("data-code"));
			$("canvas").addClass("qrCss");
		});
		
		$('#js_slider').flexslider({
			slideshow: false,
			directionNav:false,
			after: function(slider) {
				updateCardDetails();
			}
		});
		
		$(".erm").click(function() {
			
			var code = $(this).attr("data-code");
			if(code!="0"){
				$("#showCouponCode").html("");
				showQr("showCouponCode", 150, 150, code);
				$("#couponCode").modal();
			}else{
				$("#alertMsg").html("线下绑定的会员卡只能查看余额，不能用于支付");
				$("#alertTip").modal();
			}
		});
		
		$(".js-bindMbrCard").click(function() {
			window.location.href="/oauth/member/toMbrCardBind.do";
		});
		
		updateCardDetails();
		setInterval(refresh,1000);
	});
	
	function updateCardDetails(){
		var index = $(".am-active").text();
		if(index == ""){
			index = "1";
		}
		var _details = $("#js_cardDetails"+index);
		$("#js_mbrCardNo").text(_details.find(".js_mbrCardNo").val());
		$("#js_baseamtbalance").text("￥"+_details.find(".js_baseamtbalance").val());
		$("#js_incamount").text("￥"+_details.find(".js_incamount").val());
		$("#js_validTerm").text("有效期限："+_details.find(".js_validTerm").val());
		$("#js_remark").text(_details.find(".js_remark").val());
		$("#js_remark").hide();
	}
	
	function showQr(id, width, height, text) {
		$("#"+id).qrcode({width:width,height:height,text:text});
	}
	
	function refresh(){
		var time = parseInt($("#js_genGuIdDate").val())-1;
		if(time==0){
			window.location.reload();
		}else{
			$("#js_genGuIdDate").val(time)
		}
	}
	
	function showRemark(){
		if($("#js_remark").is(":hidden")){
			$("#js_remark").show();
		}else{
			$("#js_remark").hide();
		}
	}
	
</script>
</body>
</html>