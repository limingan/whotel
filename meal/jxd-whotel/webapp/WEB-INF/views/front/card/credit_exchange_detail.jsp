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
.header {
    height: 45px;
    background: #FFFFFF;
    color: #363636;
    position: relative;
}
.header_icon_back {
    background: url(/static/front/images/fanhui.png) no-repeat center center;
    background-size: 25px 25px;
    left: 10px;
}
</style>
</head>
<body style="background: #FFFFFF;">

<c:set var="headerTitle" value="积分兑换详情" scope="request"/>
<jsp:include page="card_header.jsp" />

<div class="ticketlist_topad">
	<img src="${exchangeGiftVO.giftPic}" />
</div>
<div class="block_pd" style="border-bottom: 4px solid #DDDDDD;padding: 10px 20px;"><dl>
	<dd><span>${exchangeGiftVO.itemCName}</span></dd>
	<dd style="font-size: 12px;">
		<strong id="js_score" style="color: #FDA62E;font-size: 20px;">
			<fmt:formatNumber value="${exchangeGiftVO.score}" pattern="0"/>
		</strong>&nbsp;积分
		<span style="float: right;"><img src="/static/front/images/tanhao.png" width="15px" height="15px;">&nbsp;兑换须知</span>
	</dd>
</dl></div>

<div class="block_pd" style="padding: 10px 20px;"><dl>
	<dd><span>商品详情</span></dd>
	<dd style="font-size: 14px;margin-top: 20px;line-height: 1.5;">
		${exchangeGiftVO.remark}
	</dd>
</dl></div>

<input type="hidden" id="js_getMode" value="${exchangeGiftVO.getMode}"/>
<div class="TotalOrders" id="js-exchange" style="z-index:1120;text-align: center;color: #fff;background: #ED9414;line-height: 45px;font-size: 18px;">立即兑换</div>

<form action="/oauth/member/creditExchange.do" method="post" id="exchangeForm">
<input name="itemId" type="hidden" id="giftItemId" value="${exchangeGiftVO.itemId}"/>
<!----信息弹框---->
<div class="exchangetips block_pd js-exchangeTip" style="display:none;margin-bottom: 45px;">
  <p class="closebut js-closeTip"></p>
  <h5 class="">温馨提示</h5>
  <div class="exchangetips_info"> <i class="tips_icon"></i>
  <p> 现有<b class="red" id="js_canUseScore"><fmt:formatNumber value="${memberVO.canUseScore}" type="currency" pattern="#######.##"/></b>积分<br />
    本次将花费<b class="red" id="js-tradeCredit">${exchangeGiftVO.score}</b>积分，是否继续？ </p></div><!-- js-tradeCredit -->
  <div class="exchangetips_receive">
    <p class="fs form_cr_style_C">
      <input id="zc" name="getMode" type="radio" value="2" />
      <label id="js_zc" for="zc">到店领取</label>
      <input id="yj" name="getMode" type="radio" value="1" />
      <label id="js_yj" for="yj">快递邮寄</label>
    </p>
    <p class="fs form_cr_style_C js-getHotel">
      领取酒店：<select name="resortID" id="getHotel">
           <c:forEach items="${hotelVOs}" var="hotelVO">
           	<option value="${hotelVO.hotelCode}">${hotelVO.cName}</option>
           </c:forEach>
         </select>
         <input id="memberName" type="hidden" value="${memberName}">
    </p>
    <p class="dz js-address" ><!-- style="display:none" -->
    	<a href="javascript:selectContactAddres();" class="colorking fr">选择地址<i class="more_icon_12"></i></a>
    	<label id="contactAddr"><c:if test="${contactAddress != null && contactAddress.addr != null}">${contactAddress.addr}</c:if></label>
    	<input id="contactId" type="hidden" name="contactAddressId" value='<c:if test="${contactAddress != null && contactAddress.id != null}">${contactAddress.id}</c:if>'/>
    </p>
  </div>
  <div class="exchangetips_butbox pdtd10">
    <p>
      <input type="button" value="确定" class="butstyleB js-exchangeSubmit"/>
    </p>
    <p>
      <input type="button" value="取消" class="butstyleC js-closeTip"/>
    </p>
  </div>
</div><!----弹框end---->
</form>

<div class="am-modal am-modal-alert" tabindex="-1" id="alertTip">
  <div class="am-modal-dialog">
    <div class="am-modal-bd" id="alertMsg">
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn">确定</span>
    </div>
  </div>
</div>

<div class="am-popup" id="js-coupon-popup">
	<div class="am-popup-inner">
		<div class="am-popup-hd">
			<h4 class="am-popup-title" style="font-size: 18px">选择联系人信息</h4>
			<span data-am-modal-close class="am-close">&times;</span>
		</div>
		<div class="am-popup-bd">
			<c:forEach items="${contactAddressList}" var="contactAddress">
				<div class="addressbox pd10">
					<div class="addresslist js-select-btn" style="overflow: hidden;">
						<div class="editbut fr">
							<a href="toEditContactAddress.do?id=${contactAddress.id}"><i class="edit_icon"></i>编辑</a>
						</div>
						<div class="addresslistinfo">
							<p>姓名：${contactAddress.name}<c:if test="${contactAddress.def==true}">&nbsp;&nbsp;(&nbsp;默认&nbsp;)</c:if></p>
							<p>手机：${contactAddress.mobile}</p>
							<p>地址：${contactAddress.addr}</p>
						</div>
						<input class="contactAddressId" type="hidden" value="${contactAddress.id}">
						<input class="contactAddressAddr" type="hidden" value="${contactAddress.addr}">
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="addbut">
			<a href="toEditContactAddress.do"  style="display:block; background:#fff; border:1px solid #ccc;  line-height:40px; width:100%; padding:0 10px">
			<i class="add_icon fr">+</i>新增地址</a>
		</div>
	</div>
</div>

<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
	$(function() {
		var message = "${param.message}";
		if(message != "") {
			$("#alertMsg").html(message);
			$("#alertTip").modal();
		}
		
		$("#js-exchange").click(function() {
			var _this = $(this);
			var getMode = $("#js_getMode").val();
			var address = $(".js-address");
			
			if(getMode.indexOf("2")!=-1 && getMode.indexOf("1")==-1){
				$("#js_zc").show();
				$("#zc").click();
				$("#js_yj").hide();
				address.hide();
			}else if(getMode.indexOf("2")==-1 && getMode.indexOf("1")!=-1){
				$("#js_yj").show();
				$("#yj").click();
				$("#js_zc").hide();
				address.show();
			}else {
				$("#js_zc").show();
				$("#js_yj").show();
				$("#yj").click();
				address.show();
			}
			
			//$(".js-tradeCredit").html($("#js_score").text());
			$(".js-exchangeTip").show();
		});
		
		$(".js-closeTip").click(function() {
			$(".js-exchangeTip").hide();
		});
		
		$(".js-exchangeSubmit").click(function() {
			if(checkField()) {
				$("#exchangeForm").submit();
			}
		});
		
		$("input[name=getMode]").click(function() {
			var getMode = $("input[name=getMode]:checked").val();
			//var hotel = $(".js-getHotel");
			var address = $(".js-address");
			if(getMode == "1") {
				//hotel.hide();
				address.show();
			} else {
				//hotel.show();
				address.hide();
			}
		});
		$(".js-select-btn").click(function() {
			
			var _this = $(this);
			var contactAddressIds = _this.find(".contactAddressId");
			var contactAddressAddrs = _this.find(".contactAddressAddr");
			
	    	$("#contactId").val($(contactAddressIds[0]).val());
	    	$("#contactAddr").html("<label id=\"contactAddr\">"+$(contactAddressAddrs[0]).val()+"</label>");
	    	
			$("#js-coupon-popup").modal('close');
		});
	});
	
	function checkField() {
		var totalPoint = parseInt($("#js_canUseScore").text());
		var usePoint = parseInt($("#js-tradeCredit").text());
		if(totalPoint<usePoint){
			$("#alertMsg").html("积分不足！");
			$("#alertTip").modal();
			return false;
		}
		
		var getMode = $("input[name=getMode]:checked").val();
		if(getMode && getMode != "") {
			//var getHotel = $("#getHotel option:selected").val();
			var getHotel = $("#getHotel option:selected").text();
			if(getHotel == "集团") {
				$("#alertMsg").html("请选择领取酒店！");
				$("#alertTip").modal();
				return false;
			}
			if(getMode == "1"){
				//var iObj = $(".js-address").find("i");
				//if(!iObj || iObj.length == 0) {
				
				var contactId = $("#contactId").val();
				if(contactId=="") {
					$("#alertMsg").html("请设置收货地址！");
					$("#alertTip").modal();
					return false;
				}
			}
			
			if($("#memberName").val()==""){
				$("#alertMsg").html("请先在会员中心填写会员姓名！");
				$("#alertTip").modal();
				return false;
			}
		} else {
			$("#alertMsg").html("请选择领取方式！");
			$("#alertTip").modal();
			return false;
		}
		return true;
	}
	function selectContactAddres(){
		$("#js-coupon-popup").modal();
	}
</script>
</body>
</html>