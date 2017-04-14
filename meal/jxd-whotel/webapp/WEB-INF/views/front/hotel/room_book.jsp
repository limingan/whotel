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
<style type="text/css">
.checkboxCss{width: 18px;height: 18px;background-color: #fff;border: 1px solid #999;box-shadow: inset 0px 2px 3px 0px rgba(0, 0, 0, .3), 0px 1px 0px 0px rgba(255, 255, 255, .8);}
.buttonCss{    width: 45px;
    height: 21px;
    position: relative;
    background: #d6d6d6;
    border: 1px solid #ccc;
    border-radius: 15px;
        display: inline-block;
    margin-top: 7px;
    }
.buttonCss::before{
	content: "";
    width: 21px;
    height: 20px;
    background: #fff;
    position: absolute;
    transition: 0.3s;
    left: 0px;
    border-radius: 10px;
    display: block;
    border: 1px solid #CCC;
    text-shadow: 1px 1px 1px #FFF;
}
#js_incamountCheck:checked + .buttonCss::before {
    background: #fff;
    left: 24px;
    transition: 0.3s;
    border: 1px solid #277010;
}
#js_incamountCheck:checked + .buttonCss {
    background: #41a423;
    border: 1px solid #277010;
}
.roomtime_nbifno_incamount{
	border-top: 1px dotted #ccc;
}
</style>
</head>
<body class="bodypd">

<c:set var="headerTitle" value="${hotelBranchName}" scope="request"/>
<jsp:include page="hotel_header.jsp" />

<div class="roomtime_nbifno">
  <p class="roomtime_nbifno_time">入住时间<fmt:formatDate value="${query.arriveDate}" pattern="MM/dd"/>-离店时间<fmt:formatDate value="${query.leaveDate}" pattern="MM/dd"/><span class="fr">共<b class="colorking">${days}</b>晚</span></p>
  <p class="roomtime_nbifno_room"> ${query.roomName} <span class="fr">共<b class="colorking">${query.roomQty}</b>间</span> </p>
  <p class="roomtime_nbifno_roomrate">房费<span class="fr">总价<b class="colorking">¥<fmt:formatNumber value="${totalFee}" type="currency" pattern="#######.##"/></b></span></p>
  <c:if test="${incamount>0 && maxIncamount!=0}">
  	  <%-- <p class="roomtime_nbifno_room">返现<span class="fr">
	  	<input type="checkbox" id="js_incamountCheck" value="${incamount}" class="checkboxCss" onchange="updateTotalFee()"/>
	  	<b class="colorking" id="js_showIncamount">¥<fmt:formatNumber value="${incamount}" type="currency" pattern="#######.##"/></b>
	  </span></p> --%>
	  <p class="roomtime_nbifno_incamount">使用返现
	  	  <b class="colorking" id="js_showIncamount">¥<fmt:formatNumber value="${incamount}" type="currency" pattern="#######.##"/></b>
		  <span class="fr">
		  	<input type="checkbox" style="display: none;" id="js_incamountCheck" value="${incamount}" class="checkboxCss" onchange="updateTotalFee()"/>
		  	<label for="js_incamountCheck" class="buttonCss"></label>
		  </span>
	  </p>
  </c:if>
</div>

<input type="hidden" value="${totalFee}" id="js-initTotalFee"/>
<form action="/hotel/toCashierDesk.do" method="post" id="orderForm">

<input type="hidden" name="priceName" value="${query.priceName}"/>
<input type="hidden" name="roomCode" value="${query.roomCode}"/>
<input type="hidden" name="name" value="${query.roomName}"/>
<input type="hidden" name="priceSystemId" value="${query.priceSystemId}"/>
<input type="hidden" name="salesPromotionId" value="${query.salesPromotionId}"/>
<input type="hidden" name="price" value="${query.price}"/>
<input type="hidden" name="payMent" value="${query.payMent == '到店支付' ? 'OFFLINEPAY':'ONLINEPAY'}"/>
<input type="hidden" name="rateCode" value="${query.rateCode}"/>
<input type="hidden" name="roomQty" id="roomQty" value="${query.roomQty}"/>
<input type="hidden" name="hotelCode" value="${query.hotelCode}"/>
<input type="hidden" name="totalFee" value="${totalFee}" class="js-payTotalFee"/>
<input type="hidden" name="incamount" value="0" id="js-incamountInput"/>
<input type="hidden" name="promotionFee" value="0" class="js-promotionFee"/>
<input type="hidden" name="checkInTime" value="<fmt:formatDate value="${query.arriveDate}" pattern="yyyy-MM-dd"/>" />
<input type="hidden" name="checkOutTime" value="<fmt:formatDate value="${query.leaveDate}" pattern="yyyy-MM-dd"/>" />
<div class="listinfofillbox blockbg">
<ul class="listinfofill">
<li>预订人<div class="inputfill fr"><input name="contactName" placeholder="预订人" value="${contactName}" class="inputinfofillstyle_A required"/></div></li>
<li>入住人${num}<div class="inputfill fr namelist">
	<a href="javascript:showCheckInPerson()"> <i class="more_icon_12 fr"></i></a>
	<span class="nameinfo">
		<input name="guestName" placeholder="入住人" readOnly="true" value="${(guests[0].name==null||guests[0].name=='')?contactName:guests[0].name}" class="inputinfofillstyle_A required guestName"/>
		<%-- <c:forEach begin="2" end="${query.roomQty}" var="num" varStatus="vs">
			<input name="guestName" readOnly="true" placeholder="入住人" value="${guests[vs.index].name}" class="inputinfofillstyle_A required guestName"/>
		</c:forEach> --%>
	</span>
</div></li>
<li>手机<div class="inputfill fr"><input name="contactMobile" type="tel" placeholder="手机" value="${contactMobile}" class="inputinfofillstyle_A required"/></div></li>
</ul>
</div>

<div class="blockbg mrgtd10">
<ul class="listinfofill">
<li>预抵时间<div class="inputfill fr">
	<!-- <input name="arriveTime" placeholder="到店时间" value="18:00" class="inputinfofillstyle_A required time"/> -->
	<select name="arriveTime" class="inputinfofillstyle_A required time" placeholder="预抵时间">
		<c:if test="${arriveTimes.size()==0}"><option value="18:00">18:00</option></c:if>
		<c:forEach items="${arriveTimes}" var="at" varStatus="vs">
			<option value="${at.arriveTime}" <c:if test="${at.isDefault==true}">selected</c:if>>${at.arriveTime}之前</option>
		</c:forEach>
	</select>
	</div>
</li>
<li>特殊要求<div class="inputfill fr"><input name="roomSpecial" placeholder="特殊要求" class="inputinfofillstyle_A"/></div></li>
</ul>
</div>
<c:if test="${maxCouponCountPay<=0}">
	<div class="mrgtd10">
	  <div class="blockbg">
	    <ul class="listinfofill">
	      <li style="color: #B3B3B3;">此项活动不允许使用优惠券</li>
	    </ul>
	  </div>
	</div>
</c:if>
<c:if test="${maxCouponCountPay>0 && memberCoupons != null && memberCoupons.size() > 0}">
	<div class="mrgtd10">
	  <div class="blockbg">
	    <ul class="listinfofill">
	      <li>优惠券类型
	      <span class="form_cr_style_D"><input type="radio" id="couponA" class="js-selectCouponOpt"/><label for="couponA">优惠券</label></span>
	      <p class="diyhcont js-selectedCoupon"></p>
	      </li>
	    </ul>
	  </div>
	</div>
	
	<div class="am-popup" id="js-coupon-popup">
	  <div class="am-popup-inner">
	    <div class="am-popup-hd">
	      <h4 class="am-popup-title" style="font-size:18px">选择优惠券</h4>
	      <span data-am-modal-close class="am-close">&times;</span>
	    </div>
	    <div class="am-popup-bd">
	     
	     <div class="blockbg">
			  <ul class="coupon_listsl ">
			    <c:forEach items="${memberCoupons}" var="coupon">
			    <li class="block_pd" data-chargeamtmodel="${coupon.chargeamtmodel}" data-code="${coupon.code}" data-seqid="${coupon.seqid}" data-amount="<fmt:formatNumber value="${coupon.amount}" type="currency" pattern="#######.##"/>"> 
			    <a href="javascript:" class="js-select-btn"><span class="fr">未使用<i class="more_icon_12"></i></span> 
			    <span class="sum colorking">¥<b><fmt:formatNumber value="${coupon.amount}" type="currency" pattern="#######.##"/></b> </span> 
			    <span class="date"><b>${coupon.ticketTypeCname}</b><br />
			      	有效期至<fmt:formatDate value="${coupon.limitdate}" pattern="yyyy/MM/dd"/></span>
			    </a>
			    </li>
			    </c:forEach> 
			  </ul>
		 </div>
	    </div>
	  </div>
	</div>
</c:if>

<c:if test="${hotelServiceVOs != null && hotelServiceVOs.size() > 0}">
	<h3 class="listinfotitle pdlr10_5"><a href="javascript:">客制服务<i class="Dmore_icon"></i></a></h3>
	<div class="blockbg" id="service">
	<div class="Roominfosv">
		<table width="100%" border="1" class="tablestyleA" frame="box">
			<c:forEach items="${hotelServiceVOs}" var="hotelServiceVO" varStatus="vs">
				<c:if test="${hotelServiceVO.includedQty>0}">
					<tr>
						<td width="70%">${hotelServiceVO.cname}</td>
						<td width="15%">免费</td>
						<td width="15%">${hotelServiceVO.includedQty}${hotelServiceVO.unit}</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
	</div>
	
	<ul class="listinfofill">
	<c:forEach items="${hotelServiceVOs}" var="hotelServiceVO" varStatus="vs">
		<li class="form_cr_style">
		<input name="otherServices[${vs.index}].serviceId" type="hidden" value="${hotelServiceVO.servicesId}" />
		<input name="otherServices[${vs.index}].name" type="hidden" value="${hotelServiceVO.cname}" class="serviceName" />
		<input name="otherServices[${vs.index}].typeCode" type="hidden" value="${hotelServiceVO.typeCode}" />
		<input name="otherServices[${vs.index}].price" type="hidden" value="${hotelServiceVO.availablePrice}" class="servicePrice"/>
		<input name="otherServices[${vs.index}].number" type="hidden" class="serviceNumber" value="1"/>
		<input name="otherServices[${vs.index}].unit" type="hidden" value="${hotelServiceVO.unit}" />
		<c:if test="${hotelServiceVO.typeCode == 'valueAdd' && hotelServiceVO.availableQty > 0}">
			<input name="otherServices[${vs.index}].checked" id="otherServiceCheckBox${vs.index}" type="checkbox" value="true" class="mrg-r10 js-hotelServiceCheck" />
			<label for="otherServiceCheckBox${vs.index}">${hotelServiceVO.cname}</label>
			<div class="fr"><span class="fl mrg-r10"><b class="colorking">¥<fmt:formatNumber value="${hotelServiceVO.availablePrice}" type="currency" pattern="#######.##"/></b></span>
			<div class="vCount fr" data-qty="${hotelServiceVO.availableQty}"> <em class="reduce">-</em><span class="nb">1</span><em class="add">+</em></div>
			</div>
		</c:if>
		<c:if test="${hotelServiceVO.typeCode == 'airportPickup'}">
		<div class="Flighttime fr"><input name="otherServices[${vs.index}].airplanInfo" placeholder="航班信息" class="inputinfofillstyle_A js-airplanInfo" /><b class="colorking">¥<fmt:formatNumber value="${hotelServiceVO.availablePrice}" type="currency" pattern="#######.##"/></b></div>
		</c:if>
		</li>
	</c:forEach>
	</ul>
	</div>
</c:if>

<div class="am-modal-actions" id="my-actions" style="text-align: left;margin-bottom: 50px;">
	<div class="priceDetailsbox block_pd js-priceDetail">
		<dl class="priceDetails">
			<dt>账单明细</dt>
			<dd class="listbox"><h3><span class="fr colorking"><fmt:formatNumber value="${totalFee}" type="currency" pattern="#######.##"/></span>房费</h3>
			<c:forEach items="${roomPrices}" var="roomPrice" varStatus="vs">
				<input name="roomPrices[${vs.index}].date" type="hidden" value="${roomPrice.priceDate}" />
				<input name="roomPrices[${vs.index}].price" type="hidden" value="${roomPrice.price}"/>
				<input name="roomPrices[${vs.index}].breakfast" type="hidden" value="${roomPrice.breakfast}"/>
				<input name="roomPrices[${vs.index}].servicerate" type="hidden" value="${roomPrice.servicerate}"/>
				<input name="roomPrices[${vs.index}].roomCode" type="hidden" value="${roomPrice.orderItemCode}"/>
				<input name="roomPrices[${vs.index}].bookingNotice" type="hidden" value="${roomPrice.bookingNoticeCdesc}"/>
				<input name="roomPrices[${vs.index}].cancelNotice" type="hidden" value="${roomPrice.cancelNoticeCdesc}"/>
			<p class="mx_li"><span class="fr "><fmt:formatNumber value="${roomPrice.price}" type="currency" pattern="#######.##"/>*${query.roomQty}</span>${roomPrice.priceDate}</p>
			</c:forEach>
			</dd>
			<dd class="align_R">总价：<b class="colorking js-totalFeeShow">￥<fmt:formatNumber value="${totalFee}" type="currency" pattern="#######.##"/></b></dd>
		</dl>
	</div>
</div>

<div class="TotalOrders" style="z-index:1120;">

<div class="TotalOrders_r"><a href="javascript:" class="TotalDetails js-orderPriceDetail" data-am-modal="{target: '#my-actions'}">帐单明细></a><button class="TotalOrders_r_but" onclick="return toCashierDesk()">提交订单</button>

</div><div class="TotalOrders_price"><span class="js-totalFeeShow">¥<fmt:formatNumber value="${totalFee}" type="currency" pattern="#######.##"/></span></div>
</div>
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

<div class="am-modal am-modal-alert" tabindex="-1" id="checkInPersonAlert">
  <div class="am-modal-dialog">
    <div class="am-modal-bd" id="checkInPersonMsg">
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn">确定</span>
    </div>
  </div>
</div>

<div id="checkInPerson" style="display:none;">
	<div class="selectGI_add">
		<h4>新增入住人</h4>
		<p class=" block_pd">
			<span><input name="" type="button" value="添加" class="butstylea fr" onclick="addCheckInPerson()"/></span>
			<input id="name" type="text" placeholder="姓名" class="inputinfofillstyle_A wd60" />
		</p>
	</div>
	<div class="guestlistbox pd10">
		<ul class="guestlist" id="names">
			<c:forEach items="${guests}" var="guest" varStatus="vs">
				<li class=" form_cr_style">
					<input id="${vs.index}" type="checkbox" value="${guest.name}"  onclick="selectCheckInPerson('${vs.index}')"/><label for="${vs.index}">${guest.name}</label>
				</li>
			</c:forEach>
		</ul>
	</div>
	<!-- <div class="pd20">
		<input type="button" value="确定" class="butstyleC js-deleteAddr mrgtd10" onclick="selectCheckInPerson()"/>
	</div> -->
</div>

<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script>
	$(function() {
		
		$(".add").click(function() {
			var _this = $(this);
			var nbObj = _this.siblings(".nb");
			var num = nbObj.html();
			num = parseInt(num) + 1;
			
			var qty = parseInt(_this.closest("div").attr("data-qty"));
			if(num <= qty) {
				nbObj.html(num);
				_this.closest("li").find(".serviceNumber").val(num);
				updateTotalFee();
			} else {
				$("#alertMsg").html("最多增加"+qty+"个");
				$("#alertTip").modal();
			}
		});
		
		$(".reduce").click(function() {
			var _this = $(this);
			var nbObj = _this.siblings(".nb");
			var num = nbObj.html();
			num = parseInt(num) - 1;
			if(num >= 1) {
				nbObj.html(num);
				_this.closest("li").find(".serviceNumber").val(num);
				updateTotalFee();
			}
		});
		
		$(".js-hotelServiceCheck").click(function() {
			updateTotalFee();
		});
		
		$(".Dmore_icon,.Umore_icon").click(function() {
			if($("#service").is(":hidden")){
				$(this).attr("class","Umore_icon");
			}else{
				$(this).attr("class","Dmore_icon");
			}
			$("#service").slideToggle();
		});
		
		$(".js-select-btn").click(function() {
			var _this = $(this);
			var liObj = _this.closest("li");
			var amount = liObj.attr("data-amount");
			var code = liObj.attr("data-code");
			var couponSeqid = liObj.attr("data-seqid");
			
			var chargeamtmodel = liObj.attr("data-chargeamtmodel");
			
			var payTotalFee = $(".js-payTotalFee").val();
			if(parseFloat(payTotalFee) < parseFloat(amount)) {
				$("#alertMsg").html("订单总额需大于等于"+amount+"元才可以使用此优惠券");
				$("#alertTip").modal();
				return false;
			}
			
			$(".js-promotionFee").val(amount);
			$(".js-selectedCoupon").html("所选优惠券：<em class='colorking'>"+amount+"元</em>"+
					"<input type='hidden' name='chargeamt' value='"+amount+"'>"+
					"<input type='hidden' name='chargeamtmodel' value='"+chargeamtmodel+"'>"+
					"<input type='hidden' name='couponSeqid' value='"+couponSeqid+"'>"+
					"<input type='hidden' name='couponCode' value='"+code+"'>");
			$("#js-coupon-popup").modal('close');
			updateTotalFee();
		});
		
		$(".js-selectCouponOpt").click(function() {
			$("#js-coupon-popup").modal();
		});
		
		$("#orderForm input[name=guestName]").click(function() {
			showCheckInPerson();
		});
	});
	
	function updateTotalFee() {
		var listBox = $(".listbox");
		$(".js-serviceFee").remove();
		$(".js-promotion").remove();
		$(".js_incamountShow").remove();
		var serviceFeeHtml = "";
		var serviceTotalFee = 0;
		var promotionFee = parseFloat($(".js-promotionFee").val());
		$(".js-hotelServiceCheck").each(function() {
			var _this = $(this);
			var liObj = _this.closest("li");
			var serviceName = liObj.find(".serviceName").val();
			var servicePrice = liObj.find(".servicePrice").val();
			var serviceNumber = liObj.find(".serviceNumber").val();
			if(_this.prop("checked") == true) {
				var serviceFee = parseFloat(servicePrice) * parseInt(serviceNumber);
				serviceFeeHtml += '<h3><span class="fr colorking">'+servicePrice+'*'+serviceNumber+'</span>'+serviceName+'</h3>';
				serviceTotalFee += serviceFee;
			}
		});
		
		if(promotionFee > 0) {
			listBox.after('<dd class="listbox js-promotion"><h3><span class="fr colorking">-'+promotionFee+'</span>优惠券</h3></dd>');
		}
		
		var orderTotalFee = parseFloat($("#js-initTotalFee").val()) + serviceTotalFee - promotionFee;
		
		var incamount = 0;
		if($("#js_incamountCheck").is(':checked')){
			incamount = parseFloat($("#js_incamountCheck").val());
			//if(incamount>orderTotalFee){
			//	incamount = orderTotalFee;
			//}
			orderTotalFee -= incamount;
			//$("#js_showIncamount").text("¥"+incamount);
			listBox.after('<dd class="listbox js_incamountShow"><h3><span class="fr colorking">-'+incamount.toFixed(2)+'</span>返现</h3></dd>');
		}
		$("#js-incamountInput").val(incamount.toFixed(2));
		
		$(".js-totalFeeShow").html("￥"+orderTotalFee.toFixed(2));
		$(".js-payTotalFee").val(orderTotalFee);
		if(serviceFeeHtml != "") {
			listBox.after("<dd class='listbox js-serviceFee'>"+serviceFeeHtml+"</dd>");
		}
	}
	
	function checkOrderInfo() {
		var requiredObj = $(".required");
		var rs = true;
		requiredObj.each(function() {
			var _this = $(this);
			var inputValue = _this.val();
			if(inputValue == "") {
				$("#alertMsg").html(_this.attr("placeholder")+"必须填写");
				$("#alertTip").modal();
				rs = false;
				return rs;
			} else if(_this.hasClass("time")) {
				var reg = /^(([0-1][0-9])|([2][0-3])):([0-6][0-9])$/;
				if(!reg.test(inputValue)) {
					$("#alertMsg").html("请输入正确的"+_this.attr("placeholder"));
					$("#alertTip").modal();
					rs = false;
					return rs;
				}
			}
		});
		
		if(rs) {
			$(".js-hotelServiceCheck").each(function() {
				var _this = $(this);
				if(_this.prop("checked") == true) {
					var airplanInfo = _this.closest("li").find(".js-airplanInfo");
					if(airplanInfo && airplanInfo.val() == "") {
						$("#alertMsg").html(airplanInfo.attr("placeholder")+"必须填写");
						$("#alertTip").modal();
						rs = false;
						return rs;
					}
				}
			});
		}
		return rs;
	}
	var isComplete = true;//防止重复点击
	function toCashierDesk() {
		if(checkOrderInfo() && isComplete) {
			isComplete = false;
			$("#orderForm").submit();
			return true;
		} else {
			return false;
		}
	}
	
	function showCheckInPerson(){
		$("#orderForm").css("display","none");
		$(".roomtime_nbifno").css("display","none");
		$("#checkInPerson").show();
	}
	
	function addCheckInPerson(){
		var name = $("#name").val();
		$.ajax({
			url:"/hotel/updateGuest.do",
			type:"post",
			data:{'name':name},
			async: false,
			beforeSend:function(){
				if(name==""){
					$("#checkInPersonMsg").html("请输姓名");
					$("#checkInPersonAlert").modal();
					return false;
				}
				var cbs = $("#checkInPerson input[type=checkbox]");// onclick='selectCheckInPerson()'
				var li = "<li class='form_cr_style'><input id='"+(cbs.length+1)+"' onclick='selectCheckInPerson(\""+(cbs.length+1)+"\")' type='checkbox' value='"+name+"'/><label for='"+(cbs.length+1)+"'>"+name+"</label></li>"
				$("#names").append(li);
				$("#name").val("");
				return true;
			},
			success:function(data) {
			}
		});
	}

	function selectCheckInPerson(index) {
		var cbs = $("#checkInPerson input[type=checkbox]:checked");
		var persons = $("#orderForm input[name=guestName]");
		$(persons).val(cbs[0].value);
		
		$("#checkInPerson input[type=checkbox]").each(function(){
			if($(this).is(':checked') && $(this).attr("id")!=index){
				$(this).click();
			}
		});

		$("#orderForm").css("display", "block");
		$(".roomtime_nbifno").css("display", "block");
		$("#checkInPerson").hide();
	}
</script>
</body>
</html>
