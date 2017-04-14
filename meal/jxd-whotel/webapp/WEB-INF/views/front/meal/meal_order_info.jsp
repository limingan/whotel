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
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common2.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain2.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/meal/css/food.css?v=${version}">
</head>
<body class="bodypd">
<c:set var="headerTitle" value="订单详情" scope="request"/>
<jsp:include page="meal_order_header.jsp" />

<div class="ordl_contentbox">
  <div class="orderTopinfoBox ">
    <div class="orderTopinfo_tbox pd10">
      <div class="orderTopinfo_ma fr" id="js-qr"></div>
      <ul class="orderTopinfo_l" id="js_orderTopinfo_l">
        <li class="zticon"><c:choose>
				<c:when test="${mealOrder.status=='WAIT_PAY'}"><i class="RoomStatus_icon_wpay"></i></c:when>
				<c:when test="${mealOrder.status=='NOARRIVE'}"><i class="RoomStatus_icon_wait"></i></c:when>
				<c:when test="${mealOrder.status=='ARRIVE'}"><i class="RoomStatus_icon_used"></i></c:when>
				<c:when test="${mealOrder.status=='CANCELED' || mealOrder.status=='EXPIRED'}"><i class="RoomStatus_icon_cancel"></i></c:when>
			</c:choose></li>
        <li>订单状态<span>${mealOrder.status.label}</span></li>
        <li>订单编号<span>${mealOrder.orderSn}</span></li>
        <li>订单日期<span><fmt:formatDate value="${mealOrder.createDate}" pattern="yyyy/MM/dd"/></span></li>
      </ul>
      <ul class="payfettle">
        <li>支付方式<span>${mealOrder.payMent.label}</span> </li>
        <li>支付状态<span>${mealOrder.tradeStatus.label}</span> </li>
      </ul>
    </div>
  </div>
  
  <div class=" mrgtd10">
    <h3 class="pdlr10_5">订单明细</h3>
    <ul class="block_pd orderdetailstxt">
    	<li><a href="/oauth/meal/restaurantInfo.do?restaurantId=${mealOrder.restaurant.id}&comid=${mealOrder.companyId}" class=""><i class="more_icon_12 fr mrg-t5"></i>${mealOrder.name}</a></li>
      	<c:forEach items="${mealOrder.items}" var="item">
     		<li>
     			<span class="food-order-details">${item.name}</span>
     			<span>×${item.itemQuantity}</span>
     			<span class="food-order-details-price fr">¥${item.itemPrice}</span>
     		</li>
      	</c:forEach>
      <c:if test="${mealOrder.mealTabId != null}">
	      <li>包房：${mealOrder.mealTab.tabName}<span class="fr">¥${mealOrder.mealTab.deposit==null?0:mealOrder.mealTab.deposit}</span></li>
      </c:if>
      <li>服务费<span class="fr">¥<fmt:formatNumber pattern="0.00" value="${mealOrder.serviceFee==null?0:mealOrder.serviceFee}"/></span></li> 
     <%--  <li>订单总额<span><b class="colorking fr">¥<fmt:formatNumber value="${mealOrder.orderTotalFee}" type="currency" pattern="#######.##"/></b></span> </li> --%>
      <li>优惠金额<span><b class="colorking fr"><em class="colorgreen">¥<fmt:formatNumber value="${mealOrder.chargeamt == null ? 0:mealOrder.chargeamt}" type="currency" pattern="0.00"/></em></b></span></li>
      <li>总价<span><b class="colorking fr">¥<fmt:formatNumber pattern="0.00" value="${mealOrder.totalFee}"/></b></span></li>  
    </ul>
  </div>
  
  <div class=" mrgtd10">
    <h3 class="pdlr10_5">酒店信息</h3>
    <ul class="block_pd orderdetailstxt">
       <li><a href="/oauth/hotel/showHotel.do?comid=${mealOrder.companyId}&code=${mealOrder.restaurant.hotelCode}" class="colorking"><i class="more_icon_12 fr mrg-t5"></i><b>${mealOrder.restaurant.hotelName}</b>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #999;">${mealOrder.name}</span></a></li>
      <%-- <li><a href="#" class=""><i class="more_icon_12 fr mrg-t5"></i>${mealOrder.restaurant.hotelName}</a></li> --%>
      <li><a href="http://api.map.baidu.com/marker?location=${mealOrder.restaurant.coords[1]},${mealOrder.restaurant.coords[0]}&title=${mealOrder.restaurant.name}&content=${mealOrder.restaurant.address}&output=html&src=jxd">
      <em class="fr colorking">查看地图<i class="more_icon_12"></i></em>
      <p class="txtwny"><i class="sm20_icon_location fl">地址</i><span>${mealOrder.restaurant.address}</span></p></a></li>
      <li><a href="tel:${mealOrder.restaurant.tel}"><em class="fr colorking">拔打电话<i class="more_icon_12"></i></em><i class="sm20_icon_tel">电话</i>${mealOrder.restaurant.tel}</a></li>
    </ul>
  </div>
  
	<div class=" mrgtd10">
    	<h3 class="pdlr10_5">个人信息</h3>
    	<ul class="block_pd orderdetailstxt">
	     	<li>联系人<span class="personal-info">${mealOrder.contactName}</span></li>
	     	<c:if test="${mealOrder.guestNum != null}">
		     	<li>用餐人数<span class="personal-info">${mealOrder.guestNum}人</span></li>
	     	</c:if>
	      	<li>手机号码<span class="personal-info">${mealOrder.contactMobile}</span></li>
	        <li>用餐时间<span class="personal-info"><fmt:formatDate value="${mealOrder.arrDate}" pattern="yyyy-MM-dd"/>&nbsp;${mealOrder.arriveTime}</span></li>
    	</ul>
  	</div>
</div>

<c:if test="${sameOpenId}">
	<c:if test="${(mealOrder.tradeStatus!='FINISHED' || mealOrder.totalFee<=0) && mealOrder.status!='CANCELED'}">
		<div class="pd10">
		  <input type="button" value="取消订单" class="butstyleC js-cancelOrder"/>
		</div>
	</c:if>
	
	<div class="TotalOrders" style="z-index:1120;">
		<div class="TotalOrders_r" ><c:if test="${mealOrder.tradeStatus == 'WAIT_PAY' && mealOrder.status != 'CANCELED'}"><button class="TotalOrders_r_but" onclick="toOrderPayment()">去支付</button></c:if></div>
		<div class="TotalOrders_price"><span>¥<fmt:formatNumber value="${mealOrder.totalFee}" type="currency" pattern="0.00"/></span></div>
	</div>
</c:if>

<div class="am-modal am-modal-confirm" tabindex="-1" id="confirmTip">
  <div class="am-modal-dialog">
    <div class="am-modal-bd">
      你，确定要取消订单吗？
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
    </div>
  </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="my-prompt">
  <div class="am-modal-dialog">
    <div class="am-modal-hd"></div>
    <div class="am-modal-bd">
                 您，确定要取消订单吗？
      <input name="cancelReason" placeholder="请输入取消原因" class="am-modal-prompt-input" id="js-cancelReason">
      <p id="js-errorTip" style="color:red;"></p>
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
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

<div class="am-modal am-modal-no-btn" tabindex="-1" id="orderEwm">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">
      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd" id="showOrderEwm">
    </div>
  </div>
</div>

<form action="/oauth/meal/toOrderPayment.do" method="post" id="orderForm">
	<input type="hidden" name="orderSn" value="${mealOrder.orderSn}" id="js-orderSn"/>
</form>

<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script src="/static/common/js/jquery.qrcode.min.js"></script>
<script src="/static/common/js/qrapi.js"></script>
<script type="text/javascript">
	$(function() {
		var orderNo = $("#js-orderNo").val();
		var qrContent = "";
		if(orderNo == "") {
			qrContent = "无效订单";
		} else {
			qrContent = "订单号："+orderNo;
		}
		showQr("js-qr", 50, 50, utf16to8(qrContent));
		
		var bo = true;
		$(".js-cancelOrder").click(function() {
			if(bo){
			   $('#my-prompt').modal({
				   relatedTarget: this,
				   closeOnConfirm:false,
				   onConfirm: function(e) {
					   var cancelReason = $("#js-cancelReason").val();
					   var errorTip = $("#js-errorTip");
					   if(cancelReason != "") {
						   bo = false;
						   $('#my-prompt').modal('close');
						   cancelHotelOrder();
					   } else {
						   errorTip.html("取消原因不能为空！");
						   bo = true;
					   }
					   return false;
				   },
				   onCancel: function(e) {
					   bo = true;
				   }
			   });
			}
		});
		
		showQr("showOrderEwm", 150, 150, utf16to8(qrContent));
		$("#js-qr").click(function() {
			$("#orderEwm").modal();
		});
		
		var trs = $("#hotelService").find("tr");
		var lis = $("#hotelService").find("li");
		
		if(trs.length==0&&lis.length==0){
			$("#hotelService").hide();
		}
	});
	
	function cancelHotelOrder() {
		var orderSn = $("#js-orderSn").val();
		var cancelReason = $("#js-cancelReason").val();
		$.ajax({
			url:"/oauth/meal/cancelMealOrder.do",
			type:"post",
			data:{orderSn:orderSn, cancelReason:cancelReason},
			dataType:"json",
			success:function(data) {
				if(data == true) {
					document.location="/oauth/meal/listMealOrder.do";
				} else {
					$("#alertMsg").html("订单取消失败！");
					$("#alertTip").modal();
				}
			}
		});
	}
	
	function toOrderPayment(){
		$("#orderForm").submit();
	}
</script>
</body>
</html>
