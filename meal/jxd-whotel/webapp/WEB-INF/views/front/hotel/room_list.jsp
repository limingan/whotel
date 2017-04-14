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
<link rel="stylesheet" type="text/css" href="/static/common/js/kalendae/kalendae.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain.css?v=${version}">
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
<link rel="stylesheet" type="text/css" href="/static/metronic/assets/css/pages/ticket/ticket_list.css?v=${version}">
<style>
.dateDailog{position:relative;}
.dateDailog abbr{position:absolute;left:0;top:0;}
.dateDailog abbr input{opacity:0;width:100px;cursor:pinter;}
</style>
</head>
<body>
<c:set var="headerTitle" value="酒店列表" scope="request"/>
<jsp:include page="hotel_header.jsp" />

<form action="/oauth/hotel/listRooms.do" id="roomListForm" method="post">
	<input type="hidden" name="comid" value="${WEIXINFAN_LOGIN_COMPANYID}"/>
	<input type="hidden" name="hotelCode" value="${query.hotelCode}"/>
	<input type="hidden" name="addr" value="${query.addr}"/>
	<input type="hidden" name="roomQty" class="roomQty" value="${query.roomQty}"/>
	<input type="hidden" name="beginDate" value="<fmt:formatDate value="${query.beginDate}" pattern="yyyy-MM-dd"/>" id="beginDate"/>
	<input type="hidden" name="endDate" value="<fmt:formatDate value="${query.endDate}" pattern="yyyy-MM-dd"/>" id="endDate"/>
	<input type="hidden" name="index" id="js_index" value="1"/>
	<input type="hidden" name="companyId" value="${WEIXINFAN_LOGIN_COMPANYID}"/>
	<input type="hidden" name="profileId" value="${query.profileId}"/>
	<input type="hidden" name="mbrCardTypeCode" value="${query.mbrCardTypeCode}"/>
</form>

<form action="/hotel/roomBooking.do" id="roomBookForm" method="post">
	<input type="hidden" name="roomCode" id="roomCode"/>
	<input type="hidden" name="roomName" id="roomName"/>
	<input type="hidden" name="priceSystemId" id="priceSystemId"/>
	<input type="hidden" name="salesPromotionId" id="salesPromotionId"/>
	<input type="hidden" name="price" id="price"/>
	<input type="hidden" name="payMent" id="payMent"/>
	<input type="hidden" name="rateCode" id="rateCode"/>
	<input type="hidden" name="arriveDate" id="arriveDate" value="<fmt:formatDate value="${query.beginDate}" pattern="yyyy-MM-dd"/>"/>
	<input type="hidden" name="leaveDate" id="leaveDate" value="<fmt:formatDate value="${query.endDate}" pattern="yyyy-MM-dd"/>"/>
	<input type="hidden" name="roomQty" class="roomQty" value="${query.roomQty}"/>
	<input type="hidden" name="hotelCode" value="${query.hotelCode}"/>
	<input type="hidden" name="priceName" id="priceName"/>
</form>
<%--
<div class="blocka pd5">
  <p class="dateSelection">
	  <span>入住-离店日期</span>
	  <span class="dateDailog"> 
	     <em class="pd10"><fmt:formatDate value="${query.beginDate}" pattern="MM/dd"/>-<fmt:formatDate value="${query.endDate}" pattern="MM/dd"/></em>
	     <abbr>
	       <input size="35" id="js-kalendae" readonly="readonly"/>
	     </abbr>
	  </span>
  </p>
</div>
 --%>

<div class="ht_Cdselec">
  <div class="roomvcount">
  <div class="roomvcount_c">
  	房间数量
    <div class="vCount"><em class="reduce">-</em><span class="nb">${query.roomQty}</span><em class="add">+</em></div>
  </div></div>
  <div class="dateSelection"><a href="javascript:">
    <p class="date">入住-离店日期<span class="dateDailog">
    <em ><fmt:formatDate value="${query.beginDate}" pattern="MM/dd"/>-<fmt:formatDate value="${query.endDate}" pattern="MM/dd"/></em>
    <abbr><input size="35" id="js-kalendae" readonly="readonly" value="<fmt:formatDate value="${query.beginDate}" pattern="yyyy/MM/dd"/>-<fmt:formatDate value="${query.endDate}" pattern="yyyy/MM/dd"/>"/></abbr> 
    <i class="more_icon_12"></i></span></p>
    </a></div>
</div>

<div class="Hotelslistbox mrgtd10">
	<c:if test="${roomInfoMaps == null || roomInfoMaps.size() == 0}">
		<p style="color:red; text-align:center; padding:10px 10px;">此条件下没有可预订客房！</p>
	</c:if>
    <c:if test="${roomInfoMaps != null && roomInfoMaps.size() > 0}">
    <c:forEach items="${roomInfoMaps}" var="roomInfoMap">
       <div class="Hotelslist js-roomList">
        <c:set var="roomInfo" value="${roomInfoMap.key}"/>
    	<div class="Hotelslist_L pd10"><a href="javascript:" class="roomDetail">
	      <div class="pic_L"> <img src="${roomInfo.roomPic}"/> </div>
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
	      <span class="Price"> <small>¥</small><b><fmt:formatNumber value="${roomInfo.aveprice}" type="currency" pattern="#######.##"/></b>
	      <small>起</small></span>
	      <i class="priceds_icon"></i>
	      </a>
	      </div>
	    </div>
	    <ul class="Hotelslistmoer js-roomPrices"  style="display:none;text-align: left;" >
	      <c:forEach items="${roomInfoMap.value}" var="roomPrice">
	      	 <li data-code="${roomPrice.orderItemCode}" data-priceName="${roomPrice.priceName}" data-name="${roomPrice.orderItemCName}" data-ratecode="${roomPrice.ratecode}" data-pricesystemId="${roomPrice.pricesystemid}" data-salepromotionid="${roomPrice.salepromotionid}" data-price="<fmt:formatNumber value="${roomPrice.aveprice}" type="currency" pattern="#######.##"/>" data-payment="${roomPrice.paymethod}">
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
		          <span class="Price"><small>¥</small>
		          <b><fmt:formatNumber value="${roomPrice.aveprice}" type="currency" pattern="#######.##"/></b>
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
    <div class="HotelsDetailbg" style="display:none">
      <div class="HotelsDetail_top">
        <h2 class="HotelsDetail_title">${roomInfo.orderItemCName}</h2>
        <div class="closebox"><a href="javascript:"  class="closebut"></a></div>
      </div>
      <div class="HotelsDetail pd10">
        <div class="roomlistDetail_pic">
          <div data-am-widget="slider" class="am-slider am-slider-a1"  data-am-flexslider>
            <ul class="am-slides">
             <c:if test="${roomInfo.roomPics != null}">
			  <c:forEach items="${roomInfo.roomPics}" var="roomPic" varStatus="vs">
               		<li><img src="${roomPic}"></li>
			  </c:forEach>
			 </c:if>
            </ul>
          </div>
        </div>
        <div  class="roomlistDetail_txt">
          <div data-am-widget="tabs"
       class="am-tabs am-tabs-default" >
            <ul class="am-tabs-nav am-cf">
              <li class="am-active"><a href="[data-tab-panel-0]">客房详情</a></li>
              <li class=""><a href="[data-tab-panel-1]">特别提示</a></li>
            </ul>
            <div class="am-tabs-bd">
              <div data-tab-panel-0 class="am-tab-panel am-active">
	                <div class="roomlistDetail_infobox">
	                  ${roomInfo.cDescribe}
	                </div>
	              </div>
	              <div data-tab-panel-1 class="am-tab-panel">
	              ${roomInfo.bookingNoticeCdesc}
	              </div>
	            </div>
	          </div>
	        </div>
	      </div>
	    </div>
	    <!--酒店房间详情弹框end-->  
	  </div>
    </c:forEach>
     </c:if>
</div>


<div class="am-modal am-modal-no-btn" tabindex="-1" id="js-memberBind">
  <div class="am-modal-dialog">
    <div class="am-modal-hd" style="font-size:18px">会员绑定
      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
      <div class="belinkedbox">
		  <form method="post" id="bindForm">
		  <input type="hidden" name="oldMobile" value="${member.mobile}"/>
		  <ul class="belinkedform  pd20">
		     <li class="min">
				<input name="name" type="text" placeholder="填写姓名" value="${member.name}" class="mobile_nb wd100" id="name"/>
		    </li>
		    <li class="hm clearfix">
		      <input type="button" value="获取验证码" class="butCode_hq" id="verifBtn"/>
		      <input type="tel" name="mobile" placeholder="输入手机号" value="${member.mobile}" class="mobile_nb" id="mobile"/>
		    </li>
		    <li class="min">
		      <input type="tel" name="verifyCode" placeholder="输入验证码" id="verifyCode"/>
		    </li>
		  </ul>
		  </form>
		  <div class="pd20">
		     <input type="button" value="保存资料" class="butstyleB js-saveMember"/>
    		 <input type="button" value="跳过" class="butstyleC mrg-t10 js-skip"/>
		  </div>
	  </div>
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

<div class="loading" id="loading"></div>
<div id="roomListContent"></div>
<input type="hidden" value="${isMember}" id="checkIsMember"/>
<input type="hidden" value="${SKIP_BIND}" id="checkIsSkip"/>
<input type="hidden" id="js_totalPageSize" value="${totalPageSize}"/>
<script src="/static/common/js/jquery-1.11.2.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script src="/static/common/js/kalendae/kalendae.standalone.js"></script>
<script src="/static/common/js/dateUtil.js?v=${version}"></script>
<script src="/static/common/js/smsvalide.js?v=${version}"></script>
<script type="text/javascript">

$(function() {
	
	//$(".Hotelslistbox").load("/oauth/hotel/ajaxFindlistRoomContent.do",$("#roomListForm").serialize(),function(){});
	
	var checkIsMember = $("#checkIsMember").val();
	var checkIsSkip = $("#checkIsSkip").val();
	if(checkIsMember == "" && checkIsSkip == "") {
		$("#js-memberBind").modal({width:"300px"});
	}
	
	var kal = new Kalendae.Input('js-kalendae', {
		months:1,
		mode:'range',
		direction:'today-future',
		format: 'YYYY/MM/DD',
		parseSplitDelimiter:'-'
	});
	
	var kRit=(($(window).width()-$(".kalendae").width()-15)/2);
	var kBot=(($(window).height()-$(".kalendae").height())/2);
	$(".kalendae").css({"right":kRit});

	$("#js-kalendae").change(function() {
		var _this = $(this);
		var date = _this.val();
		if(date.indexOf("-") > -1) {
			
			var dates = kal.getSelectedAsDates();
			var beginDate = dates[0].Format("yyyy-MM-dd");
			var endDate = dates[1].Format("yyyy-MM-dd");
			if(beginDate != endDate) {
				$("#beginDate").val(beginDate);
				$("#endDate").val(endDate);
				
				var form = $("#roomListForm");
				form.submit();
			}
		}
	});
	
	$(".add").click(function() {
		var _this = $(this);
		var nbObj = _this.siblings(".nb");
		var num = nbObj.html();
		num = parseInt(num) + 1;
		nbObj.html(num);
		$(".roomQty").val(num);
		var form = $("#roomListForm");
		form.submit();
	});
	
	$(".reduce").click(function() {
		var _this = $(this);
		var nbObj = _this.siblings(".nb");
		var num = nbObj.html();
		num = parseInt(num) - 1;
		if(num >= 1) {
			nbObj.html(num);
			$(".roomQty").val(num);
			
			var form = $("#roomListForm");
			form.submit();
		}
	});
	
	$("#verifBtn").click(function() {
		  var $this = $(this);
		  if(!$this.hasClass("over")) {
			  sendMsm($this, errorMobile);
		  }
	});
	
	$(".js-saveMember").click(function() {
		if(checkName()) {
			$(".js-saveMember").attr("disabled",true);
			checkSmsCode(codeRight, codeError);
		}
	});
	
	$(".js-skip").click(function() {
		$.ajax({
			url:"/oauth/member/ajaxSkipBindMember.do",
			success:function(data) {
				$("#js-memberBind").modal('close');
			}
		});
	});
	
	init();
	initShowRoomPrice();
	
	var finished = true;
	var pageNo = 1;
	$(window).scroll(function() {
		if(finished && $(document).scrollTop() >= (($(document).height() - $(window).height())/2)){
			finished = false;
			$(".Hotelslistbox").append('<div class="js_loadData lodingtxet"><img src="/static/front/images/loading1.gif"/>正在加载更多数据</div>');
			var index = parseInt($("#js_index").val());
			var totalPageSize = parseInt($("#js_totalPageSize").val());
			if(pageNo<totalPageSize){
				pageNo = pageNo+1;
				$("#js_index").val(pageNo);
				$("#roomListContent").load("/oauth/hotel/ajaxFindlistRoomContent.do",$("#roomListForm").serialize(),function(){
					$(".Hotelslistbox").append($("#roomListContent").html());
					$("#roomListContent").html("");
					//$("img.lazy").lazyload({effect: "fadeIn"});
					$('.am-slider').flexslider();
					$(".am-tabs").tabs();
					
					init();
					finished = true;
					$(".js_loadData").remove();
				});
			}else{
				$(".js_loadData").remove();
				$(".Hotelslistbox").append('<div class="js_loadData lodingtxet">没有更多数据</div>');
			}
		}
	});
});

function init(){
	$(".roomDetail").unbind("click").bind("click",function(){
		var _this = $(this);
		var hotelDetailObj = _this.closest(".Hotelslist").find(".HotelsDetailbg");
		hotelDetailObj.show();
		$(window).resize();
		
		$(hotelDetailObj).find("img").each(function(){
			$(this).attr("height",this.width*0.7);
		});
	});		
	
	//$(".closebut").click(function() {
	$(".closebut").unbind("click").bind("click",function(){
		var _this = $(this);
		_this.closest(".HotelsDetailbg").hide();
	});
	
	//$(".js-bookRoom").click(function() {
	$(".js-bookRoom").unbind("click").bind("click",function(){
		var _this = $(this);
		var form = $("#roomBookForm");
		
		var liObj = _this.closest("li")
		var dataCode = liObj.attr("data-code");
		var dataName = liObj.attr("data-name");
		var dataPricesystemId = liObj.attr("data-pricesystemId");
		var dataSalepromotionId = liObj.attr("data-salepromotionid");
		var dataPrice = liObj.attr("data-price");
		var dataPayment = liObj.attr("data-payment");
		var dataRateCode = liObj.attr("data-ratecode");
		var priceName = liObj.attr("data-priceName");
		
		$("#roomCode").val(dataCode);
		$("#priceSystemId").val(dataPricesystemId);
		$("#salesPromotionId").val(dataSalepromotionId);
		$("#price").val(dataPrice);
		$("#payMent").val(dataPayment);
		$("#rateCode").val(dataRateCode);
		$("#roomName").val(dataName);
		$("#priceName").val(priceName);
		form.submit();
	});
	
	//$(".js-viewMore").click(function() {
	$(".js-viewMore").unbind("click").bind("click",function(){
		var _this = $(this);
		var iObj = _this.find("i");
		var roomListObj = _this.closest(".js-roomList");
		var roomPricesObj = roomListObj.find(".js-roomPrices");
		
		if(iObj.hasClass("priceds_icon_active")) {
			iObj.removeClass("priceds_icon_active");
			iObj.addClass("priceds_icon");
			roomPricesObj.hide();
		} else {
			var rooms = $.find(".js-roomPrices");
			for(var i =0;i<rooms.length;i++){
				if($(rooms[i]).is(":visible")&&$(rooms[i]!=roomPricesObj)){
					$(rooms[i]).closest(".js-roomList").find("i").removeClass("priceds_icon_active");
					$(rooms[i]).closest(".js-roomList").find("i").addClass("priceds_icon");
					$(rooms[i]).hide();
				}
			}
			
			iObj.removeClass("priceds_icon");
			iObj.addClass("priceds_icon_active");
			roomPricesObj.show();
		}
	});
}

function initShowRoomPrice() {
	var firstRoom = $(".js-roomList").eq(0);
	firstRoom.find(".js-roomPrices").show();
	firstRoom.find(".priceds_icon").removeClass("priceds_icon").addClass("priceds_icon_active");
}

function errorMobile(msg) { 
	$("#alertMsg").html(msg);
	$("#alertTip").modal();
}

function codeRight() {
	var $loading = $("#loading");
	$loading.show();
	//$(".js-saveMember").unbind("click");
	$.ajax({
		url:"/oauth/member/ajaxBindMember.do",
		data:$("#bindForm").serialize(),
		dataType:"json",
		success:function(data) {
			$loading.hide();
			if(data == "") {
				//document.reload();
				window.location.reload();
			} else {
				$("#alertMsg").html(data);
				$("#alertTip").modal();
			}
			$(".js-saveMember").attr("disabled",false);
			/* $(".js-saveMember").click(function() {
				if(checkName()) {
					checkSmsCode(codeRight, codeError);
				}
			}); */
		}
	});
}

function codeError() {
	$("#alertMsg").html("手机验证码不正确");
	$("#alertTip").modal();
	$(".js-saveMember").attr("disabled",false);
}

function checkName() {
	var name = $("#name").val();
	if($.trim(name) == "") {
		$("#alertMsg").html("姓名不能为空！");
		$("#alertTip").modal();
		return false;
	}
	return true;
}

function showNoBookingReason(noBookingReason){
	$("#alertMsg").html(noBookingReason);
	$("#alertTip").modal();
}
</script>
</body>
</html>
