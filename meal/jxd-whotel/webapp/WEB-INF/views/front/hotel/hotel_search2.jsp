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
<link rel="stylesheet" type="text/css" href="/static/common/js/kalendae/kalendae.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common${THEME}.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain${THEME}.css?v=${version}">
<style>
.dateDailog{position:relative;}
.dateDailog abbr{position:absolute;left:0;top:0;}
.dateDailog abbr input{opacity:0;width:100px;cursor:pinter;}
.js_selectCity{border: 1px solid #DFDFDF;border-radius: 4px;margin-right: 15px;display: inline-block; width: 27%;text-align: center;}
.js_selectHotel{margin-bottom: 15px;}
</style>
</head>
<body>
<c:set var="headerTitle" value="酒店搜索" scope="request"/>
<jsp:include page="hotel_header.jsp" />

<div class="block cityselecbox">
	
		<form action="/oauth/hotel/listRooms.do" method="post" id="submit_photo">
			<input type="hidden" name="comid" value="${WEIXINFAN_LOGIN_COMPANYID}" />

			<div class="cityselec">
				<p class="cityselec_ci">
					入住城市 <span>
					 <input name="addr" placeholder="全部" value="" id="js_addr" readonly="readonly" onclick="cccc()" class="inputstyle_A wd100" 
					 	style="background: url(/static/front/hotel/images/moreae_iocn2.png) no-repeat right center;background-size: 16px 16px;color: #A9A9A9;"/>
					 <%-- <select name="addr" class="inputstyle_A wd100">
							<option value="">全部</option>
							<c:forEach items="${hotelCitys}" var="hc" varStatus="vs">
								<option value="${hc.city}">${hc.city}</option>
							</c:forEach>
					</select> --%>
					</span>
				</p>
				<p class="cityselec_date">
					<span>入住时间<input name="beginDate" type="text" readonly
						placeholder="入住时间"
						value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>"
						class="inputstyle_A wd100" id="js-beginDate" /></span> <span>离店时间<input
						name="endDate" type="text" readonly placeholder="离店时间"
						value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>"
						class="inputstyle_A wd100" id="js-endDate" /></span>
				</p>
				<p class="keywordsearch">
					关键字搜索<span><input name="name" type="text" id="js_name" class="inputstyle_A wd100" placeholder="酒店名" /></span>
				</p>
			</div>
			<p class="pd10">
				<button class="butstyleB">
					<i class="seach_icon_20"></i>搜索
				</button>
			</p>
		</form>
</div>


<div class="am-popup" id="js-coupon-popup-lxr">
	<div class="am-popup-inner">
		<div class="am-popup-hd">
			<!-- <p class="am-popup-title" style="font-size: 18px">入住城市</p> -->
			<!-- <span data-am-modal-close class="am-close">&times;</span> -->
			<header class="header">
				<div class="header_left"><a data-am-modal-close href="javascript:" class="header_icon_back"></a></div>
			  <h1 class="header_title">酒店搜索</h1>
			</header>
		</div>
		<div class="am-popup-bd" style="margin: 20px 20px;padding:0px;background: #FFFFFF;" id="am-popup-bd123">
			<p style="margin: 10px 0px;color: #9D9D9D;">请选择城市</p>
			<div style="color: #6D6D6D;border-bottom: 1px solid #EDEDED;">
				<p style="margin-bottom: 15px;">
				<c:forEach items="${hotelCitys}" var="hc" varStatus="vs">
					<span class="js_selectCity" value="${hc.city}">${hc.city}</span>
					<c:if test="${vs.count%3 == 0}">
						</p><p style="margin-bottom: 15px;">
					</c:if>
				</c:forEach></p>
			</div>
			
			<p style="margin: 10px 0px;color: #9D9D9D;">请选择酒店</p>
			<ul id="js_showHotels" style="color: #6D6D6D;">
				<c:forEach items="${hotels}" var="hotel" varStatus="vs">
					<li style="cursor: pointer;" class="js_selectHotel" data-city="${hotel.city}" value="${hotel.cname}">${hotel.cname}</li>
				</c:forEach>
			</ul>
		</div>
		
	</div>
</div>

<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script src="/static/common/js/kalendae/kalendae.standalone.js?v=${version}"></script>
<script type="text/javascript" src="/static/common/js/dateUtil.js?v=${version}"></script>
<script type="text/javascript">

$(function() {
	
	$("#js-beginDate").click(function(){
		kal1.show();
	});
	$("#js-endDate").click(function(){
		kal2.show();
	});
	
	var kal1 = new Kalendae.Input('js-beginDate', {
		months:1,
		mode:'single',
		direction:'today-future',
		format: 'YYYY-MM-DD'
	});
	
	kal1.subscribe('change', function (date) {
		kal2.hide();
		updateInvalidDate();
		//$(".k-btn-close").click();
		//$("#js-beginDate").blur();
		//kal1.close();
		//$(".kalendae").toggle();
		kal1.hide();
	});
	
	var kal2 = new Kalendae.Input('js-endDate', {
		months:1,
		mode:'single',
		direction:'today-future',
		format: 'YYYY-MM-DD'
	});
	
	kal2.subscribe('change', function (date) {
		kal1.hide();
		updateInvalidDate();
		//$("#js-endDate").blur();
		kal2.hide();
	});
	
	var kRit=(($(window).width()-$(".kalendae").width()-15)/2);
	var kBot=(($(window).height()-$(".kalendae").height())/2);
	$(".kalendae").css({"right":kRit});
	
	$(".js_selectCity").click(function(){
		$(".js_selectCity").each(function(){
			$(this).css("color","#6D6D6D");
			$(this).css("background","#FFFFFF");
		});
		
		var _this = $(this);
		_this.css("background","#259EFC");
		_this.css("color","#f5f5f5");
		var city = _this.text();
		$("#js_addr").val(city);
		
		$(".js_selectHotel").each(function(){
			var _this = $(this);
			if(city != _this.attr("data-city")){
				_this.css("display","none");
			}else{
				_this.css("display","block");
			}
		});
		
	});
	
	$("body").on("click", ".js_selectHotel", function() {
		var _this = $(this);
		_this.css("background","#259EFC");
		_this.css("color","#f5f5f5");
		_this.siblings().css("color","#6D6D6D");
		_this.siblings().css("background","#FFFFFF");
		$("#js_name").val(_this.text());
		$("#submit_photo").submit();
		//$("#js-coupon-popup-lxr").modal('close');
	});
	
});

function cccc(){
	$("#js-coupon-popup-lxr").modal();
}

function updateInvalidDate() {
	var beginDate = $("#js-beginDate").val();
	var endDate = $("#js-endDate").val();
	
	if(beginDate != "" && endDate != "") {
		if(endDate <= beginDate) {
			beginDate = new Date(beginDate);
			beginDate.setDate(beginDate.getDate() + 1);
			$("#js-endDate").val(beginDate.Format("yyyy-MM-dd"));
		}
	}
}

</script>
</body>
</html>
