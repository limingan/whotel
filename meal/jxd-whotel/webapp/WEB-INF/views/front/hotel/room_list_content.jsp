<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
</head>
<body>
<c:if test="${roomInfoMaps != null && roomInfoMaps.size() > 0}">
<c:forEach items="${roomInfoMaps}" var="roomInfoMap">
<div class="Hotelslist js-roomList">
	<c:set var="roomInfo" value="${roomInfoMap.key}"/>
	<div class="Hotelslist_L pd10">
		<a href="javascript:" class="roomDetail">
			<div class="pic_L"><img class="lazy" data-original="${roomInfo.roomPic}&width=100&height=100&flag=75"/></div>
			<div class="content">
				<h2 class="content_title">${roomInfo.orderItemCName}
					<c:if test="${roomInfo.sign}">
						<a href="javascript:void(0);" class="icon-renzheng-list">返
							</a>
				</c:if>
				<br/></h2>
				<c:forEach items="${roomInfo.serviceImgUrls}" var="serviceImgUrl">
			        <img src="${serviceImgUrl}" width="25px;" height="25px;">
		        </c:forEach>	
			</div>
		</a>
		<div class="viewmore">
			<a href="javascript:" class="js-viewMore">
				<span class="Price">
						<small>¥</small><b><fmt:formatNumber value="${roomInfo.aveprice}" type="currency" pattern="#######.##" /></b>
						<small>起</small>
				</span>
				<i class="priceds_icon"></i>
			</a>
		</div>
	</div>
	<ul class="Hotelslistmoer js-roomPrices"
		style="display: none; text-align: left;">
		<c:forEach items="${roomInfoMap.value}" var="roomPrice">
			<li data-code="${roomPrice.orderItemCode}"
				data-priceName="${roomPrice.priceName}"
				data-name="${roomPrice.orderItemCName}"
				data-ratecode="${roomPrice.ratecode}"
				data-pricesystemId="${roomPrice.pricesystemid}"
				data-salepromotionid="${roomPrice.salepromotionid}"
				data-price="<fmt:formatNumber value="${roomPrice.aveprice}" type="currency" pattern="#######.##"/>"
				data-payment="${roomPrice.paymethod}">
				<div class="Hotelslistmoer_L pd10">
					<p>${roomPrice.priceName}
						<c:if test="${!empty roomPrice.largessReturnType}">
							<c:choose>
								<c:when test="${roomPrice.largessReturnType == '1'}">
									<a href="javascript:void(0);" class="icon-renzheng-list">返￥${roomPrice.largessReturnValue}
								 </a>
								</c:when>
								<c:when test="${roomPrice.largessReturnType == '2'}">
									<a href="javascript:void(0);" class="icon-renzheng-list">
									返￥<fmt:formatNumber type="number" value="${roomPrice.largessReturnValue * roomPrice.aveprice / 100}" maxFractionDigits="0"/>
									</a>
								</c:when>
								<c:when test="${roomPrice.largessReturnType == '3'}">
									<a href="javascript:void(0);" class="icon-renzheng-list">满送
									</a>
								</c:when>
							</c:choose>
						</c:if>
					</p>
				</div>
				<div class="Hotelslistmoer_R">
					<a href="javascript:">
						<span class="Price">
							<%-- <c:if test="${roomInfo.basePrice!=null && roomInfo.basePrice!=roomPrice.aveprice}">
								<small>¥</small><del>${roomInfo.basePrice}</del>
				          	</c:if> --%>
							<small>¥</small>
							<b><fmt:formatNumber value="${roomPrice.aveprice}" type="currency" pattern="#######.##" /></b>
						</span>
					</a>
					<c:choose>
		          	<c:when test="${roomPrice.canBooking}">
			          <button class="butstylea js-bookRoom">预订</button>
		          	</c:when>
		          	<c:otherwise>
		          		<button class="butstyleF" onclick="showNoBookingReason('${roomPrice.noBookingReason}')">预订</button>
		          	</c:otherwise>
		          </c:choose>
				</div>
			</li>
		</c:forEach>
	</ul>

	<!--酒店房间详情弹框-->
	<div class="HotelsDetailbg" style="display: none">
		<div class="HotelsDetail_top">
			<h2 class="HotelsDetail_title">${roomInfo.orderItemCName}</h2>
			<div class="closebox">
				<a href="javascript:" class="closebut"></a>
			</div>
		</div>
		<div class="HotelsDetail pd10">
			<div class="roomlistDetail_pic">
				<div data-am-widget="slider" class="am-slider am-slider-a1" data-am-flexslider>
					<ul class="am-slides">
						<c:if test="${roomInfo.roomPics != null}">
							<c:forEach items="${roomInfo.roomPics}" var="roomPic" varStatus="vs">
								<li><img class="lazy" data-original="${roomPic}&width=450&height=300&flag=75"></li>
							</c:forEach>
						</c:if>
					</ul>
				</div>
			</div>
			<div class="roomlistDetail_txt">
				<div data-am-widget="tabs" class="am-tabs am-tabs-default">
					<ul class="am-tabs-nav am-cf">
						<li class="am-active"><a href="[data-tab-panel-0]">客房详情</a></li>
						<li><a href="[data-tab-panel-1]">特别提示</a></li>
					</ul>
					<div class="am-tabs-bd">
						<div data-tab-panel-0 class="am-tab-panel am-active">
							<div class="roomlistDetail_infobox">${roomInfo.cDescribe}</div>
						</div>
						<div data-tab-panel-1 class="am-tab-panel">${roomInfo.bookingNoticeCdesc}</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--酒店房间详情弹框end-->
</div>
</c:forEach>
</c:if>
</body>