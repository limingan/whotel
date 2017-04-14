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
</head>
<body class="bodypd">

<c:set var="headerTitle" value="订单详情" scope="request"/>
<jsp:include page="hotel_header.jsp" />
<input value="${hotelOrder.hotelOrderNo}" id="js-orderNo" type="hidden"/>
<input value="${hotelOrder.status}" id="js-orderStatus" type="hidden"/>

<c:forEach items="${hotelOrder.otherServices}" var="otherService" varStatus="vs">
 	<c:choose>
 		<c:when test="${otherService.typeCode=='valueAdd'}">
 		    <c:set var="hasOtherService" value="true"/>
 		</c:when>
 		<c:when test="${otherService.typeCode=='airportPickup'}">
 			<c:set var="hasAirportPickup" value="true"/>
 		</c:when>
 	</c:choose>
</c:forEach>

<div class="ordl_contentbox">
  <div class="orderTopinfoBox ">
    <div class="orderTopinfo_tbox pd10">
      <div class="orderTopinfo_ma fr" id="js-qr"></div>
      <ul class="orderTopinfo_l" id="js_orderTopinfo_l">
        <li class="zticon"><c:choose>
				<c:when test="${hotelOrder.status=='NEW'}"><i class="RoomStatus_icon_wait"></i></c:when>
				<c:when test="${hotelOrder.status=='WAIT_PAY'}"><i class="RoomStatus_icon_wpay"></i></c:when>
				<c:when test="${hotelOrder.status=='CONFIRMED'}"><i class="RoomStatus_icon_confirm"></i></c:when>
				<c:when test="${hotelOrder.status=='USED'}"><i class="RoomStatus_icon_used"></i></c:when>
				<c:when test="${hotelOrder.status=='LEAVE'}"><i class="RoomStatus_icon_used"></i></c:when>
				<c:when test="${hotelOrder.status=='CANCELED'}"><i class="RoomStatus_icon_cancel"></i></c:when>
			</c:choose></li>
        <li>订单状态<span>${hotelOrder.status.label}</span></li>
        <li>订单日期<span><fmt:formatDate value="${hotelOrder.createTime}" pattern="yyyy/MM/dd"/></span></li>
        <li>订单号<span><c:choose>
				<c:when test="${hotelOrder.hotelOrderNo!=null&&hotelOrder.hotelOrderNo!=''}">${hotelOrder.hotelOrderNo}</c:when>
				<c:otherwise>${hotelOrder.orderSn}</c:otherwise>
			</c:choose></span></li>
      </ul>
      <ul class="payfettle">
        <li>支付方式<span>${hotelOrder.payMent.label}</span> </li>
        <li>支付状态<span>${hotelOrder.tradeStatus.label}</span> </li>
      </ul>
    </div>
  </div>
  <div class="mrgtd10">
    <h3 class="pdlr10_5">订单价格</h3>
    <ul class="block_pd orderdetailstxt">
      <li>价格名称<span>${hotelOrder.priceName}</span></li>
      <li>订单总额<span>¥<fmt:formatNumber value="${hotelOrder.orderTotalFee}" type="currency" pattern="#######.##"/></span> </li>
      <li>优惠金额<span><em class="colorgreen">¥<fmt:formatNumber value="${hotelOrder.promotionFee == null ? 0:hotelOrder.promotionFee}" type="currency" pattern="#######.##"/></em></span> </li>
      <li>实付金额<span><b class="colorking">¥<fmt:formatNumber value="${hotelOrder.totalFee}" type="currency" pattern="#######.##"/></b></span></li>
    </ul>
  </div>
  <div class=" mrgtd10">
    <h3 class="pdlr10_5">酒店信息</h3>
    <ul class="block_pd orderdetailstxt">
      <li><a href="/oauth/hotel/showHotel.do?comid=${hotelOrder.companyId}&code=${hotelBranchVO.code}" class="colorking"><i class="more_icon_12 fr mrg-t5"></i><b>${hotelBranchVO.cname}</b></a></li>
      <li>房间类型<span>${hotelOrder.name}</span></li>
      <li>入住时间<span>入住<fmt:formatDate value="${hotelOrder.checkInTime}" pattern="yyyy/MM/dd"/>离店<fmt:formatDate value="${hotelOrder.checkOutTime}" pattern="yyyy/MM/dd"/></span></li>
      
      <c:if test="${hasOtherService == 'true'}">
      <li>其它服务<span>
      <c:forEach items="${hotelOrder.otherServices}" var="otherService" varStatus="vs">
      		<c:if test="${otherService.typeCode=='valueAdd'}">
      		${otherService.name}*${otherService.number}&nbsp;&nbsp;
      		</c:if>
      </c:forEach>
      </span></li>
      </c:if> <!-- http://api.map.baidu.com/direction?region=${company.region.name}&mode=driving&src=${hotelBranchVO.cname}&output=html&destination=latlng:${hotel.coords[1]},${hotel.coords[0]}|name:${hotelBranchVO.cname}&origin=latlng:${location.lat},${location.lon}|name:你的位置 -->
      <li><a href="http://api.map.baidu.com/marker?location=${hotel.coords[1]},${hotel.coords[0]}&title=${hotelBranchVO.cname}&content=${hotelBranchVO.address}&output=html&src=jxd">
      <em class="fr colorking">查看地图<i class="more_icon_12"></i></em>
      <p class="txtwny"><i class="sm20_icon_location fl">地址</i><span>${hotelBranchVO.address}</span></p></a></li>
      <li><a href="tel:${hotelBranchVO.tel}"><em class="fr colorking">拔打电话<i class="more_icon_12"></i></em><i class="sm20_icon_tel">电话</i>${hotelBranchVO.tel}</a></li>
    </ul>
  </div>
  <div class=" mrgtd10">
    <h3 class="pdlr10_5">个人信息</h3>
    <ul class="block_pd orderdetailstxt">
     <li>联系人<span>${hotelOrder.contactName}</span></li>
     <li>入住人<span>${hotelOrder.guestName}</span></li>
     <li>手机号码<span style="display:inline-block">${hotelOrder.contactMobile}</span></li>
    </ul>
  </div>
<%--   <c:if test="${(hotelOrder.roomSpecial != null && hotelOrder.roomSpecial != '') || (hasAirportPickup == 'true')}">
  <div class=" mrgtd10">
    <h3 class="pdlr10_5">客制服务</h3>
    <ul class="block_pd orderdetailstxt">
      <li>特殊要求：<span>${hotelOrder.roomSpecial}</span></li>
      <c:if test="${hasAirportPickup == 'true'}">
       <li>机场接机服务：<span>需要</span></li>
      </c:if>
    </ul>
  </div>
  </c:if> --%>

<div class=" mrgtd10 " id="hotelService">
	<h3 class="pdlr10_5 ">客制服务</h3>
	<div class="Kzsv">
		<div class="Roominfosv">
			<table width="100%" border="1" class="tablestyleA" frame=box>
				<c:forEach items="${hotelServiceVOs}" var="hotelServiceVO"
					varStatus="vs">
					<c:if test="${hotelServiceVO.includedQty>0}">
						<tr>
							<td width="70%">${hotelServiceVO.cname}</td>
							<td width="15%">免费</td>
							<td width="15%">${hotelServiceVO.includedQty}${hotelServiceVO.unit}</td>
						</tr>
					</c:if>
				</c:forEach>
				<c:forEach items="${hotelOrder.otherServices}" var="otherService" varStatus="vs">
					<tr>
						<td width="70%">${otherService.name}</td>
						<td width="15%">￥${otherService.price}</td>
						<td width="15%">${otherService.number}${otherService.unit}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<ul class="orderdetailstxt">
			<c:if test="${hotelOrder.roomSpecial != null && hotelOrder.roomSpecial != ''}">
			<li>特殊要求<span>${hotelOrder.roomSpecial}</span></li>
			</c:if>
			<%-- <c:forEach items="${hotelOrder.otherServices}" var="otherService" varStatus="vs">
				<li>${otherService.name}<span>￥${otherService.price}</span></li>
			</c:forEach> --%>
		</ul>
	</div>
</div>
</div>

<c:if test="${(hotelOrder.status=='USED' || hotelOrder.status=='LEAVE') && commentConfig.isHotelComment}">
	<div class="pd10"><form action="/oauth/hotelMsg/toHotelComment.do?comid=${WEIXINFAN_LOGIN_COMPANYID}" method="post">
	  <input name="orderSn" type="hidden" value="${hotelOrder.orderSn}"/>
	  <input type="submit" value="我要点评" class="butstyleC" />
	</form></div>
</c:if>
<!-- -->
<c:if test="${hotelOrder.tradeStatus != 'FINISHED' && hotelOrder.status != 'CANCELED' && hotelOrder.status != 'USED' && hotelOrder.status != 'WAIT_PAY'}">
<div class="pd10">
  <input type="button" value="取消订单" class="butstyleC js-cancelOrder"/>
</div>
</c:if>

<%-- <div class=" mrgtd10 " id="hotelService">
	<h3 class="pdlr10_5 ">房价明细</h3>
	<div class="Kzsv">
		<div class="Roominfosv">
			<table width="100%" border="1" class="tablestyleA" frame=box>
				<tr>
					<td width="35%">日期</td>
					<td width="35%">价格</td>
					<td width="30%">数量</td>
				</tr>
				<c:forEach items="${hotelOrder.roomPrices}" var="roomPrice" varStatus="vs">
					<tr>
						<td><fmt:formatDate value="${roomPrice.date}" pattern="yyyy/MM/dd"/></td>
						<td><fmt:formatNumber value="${roomPrice.price}" type="currency" pattern="#######.##"/></td>
						<td>${hotelOrder.roomQty}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div> --%>
<%-- <div class="TotalOrders js-goPay" style="z-index:1120;">
  <div class="TotalOrders_r">
    <button class="TotalOrders_r_but" onclick="toOrderPayment()">去支付</button>
  </div>
  <div class="TotalOrders_price" data-am-modal="{target: '#my-actions'}"><span>¥${hotelOrder.orderTotalFee}</span></div>
</div> --%>

<div class="TotalOrders" style="z-index:1120;">
	<div class="TotalOrders_r" ><a href="javascript:" class="TotalDetails" data-am-modal="{target: '#my-actions'}" style="line-height:4em;" >帐单明细></a><%-- <c:if test="${hotelOrder.tradeStatus == 'WAIT_PAY' && hotelOrder.status != 'CANCELED'}"><button class="TotalOrders_r_but" onclick="toOrderPayment()">去支付</button></c:if> --%></div>
	<div class="TotalOrders_price"><span>¥<fmt:formatNumber value="${hotelOrder.totalFee}" type="currency" pattern="#######.##"/></span></div>
</div>

<div class="am-modal-actions" id="my-actions" style="text-align: left;margin-bottom: 50px;">
	<div class="priceDetailsbox block_pd js-priceDetail">
		<dl class="priceDetails">
			<dt>账单明细</dt>
			<dd class="listbox"><h3><span class="fr colorking"><fmt:formatNumber value="${roomFee}" type="currency" pattern="#######.##"/></span>房费</h3>
				<c:forEach items="${hotelOrder.roomPrices}" var="roomPrice" varStatus="vs">
					<p class="mx_li"><span class="fr "><fmt:formatNumber value="${roomPrice.price}" type="currency" pattern="#######.##"/>*${hotelOrder.roomQty}</span><fmt:formatDate value="${roomPrice.date}" pattern="yyyy/MM/dd"/></p>
				</c:forEach>
			</dd>
			<c:if test="${hotelOrder.otherServices!=null&&hotelOrder.otherServices.size()!=0}">
				<dd class="listbox">
					<c:forEach items="${hotelOrder.otherServices}" var="otherService" varStatus="vs">
						<h3><span class="fr colorking"><fmt:formatNumber value="${otherService.price}" type="currency" pattern="#######.##"/>*${otherService.number}</span>${otherService.name}</h3>
					</c:forEach>
				</dd>
			</c:if>
			<c:if test="${hotelOrder.promotionFee!=null&&hotelOrder.promotionFee!=0}">
				<dd class="listbox js-promotion"><h3><span class="fr colorking">-${hotelOrder.promotionFee}</span>优惠券</h3></dd>
			</c:if>
			<c:if test="${hotelOrder.incamount!=null&&hotelOrder.incamount!=0}">
				<dd class="listbox js-promotion"><h3><span class="fr colorking">-${hotelOrder.incamount}</span>返现</h3></dd>
			</c:if>
			<dd class="align_R">总价：<b class="colorking js-totalFeeShow">￥<fmt:formatNumber value="${hotelOrder.totalFee}" type="currency" pattern="#######.##"/></b></dd>
		</dl>
	</div>
</div>

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

<form action="/hotel/toOrderPayment.do" method="post" id="orderForm">
	<input type="hidden" name="orderSn" value="${hotelOrder.orderSn}" id="js-orderSn"/>
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
			url:"/hotel/cancelHotelOrder.do",
			type:"post",
			data:{orderSn:orderSn, cancelReason:cancelReason},
			dataType:"json",
			success:function(data) {
				if(data == true) {
				/*	$("#alertMsg").html("订单取消成功！");
					$("#alertTip").modal();
					$("#js-cancelStatus").html("已取消");
					$(".js-goPay").hide();
					$(".js-cancelOrder").closest("div").hide();
				*/	
					document.location="/oauth/hotel/listHotelOrder.do";
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
