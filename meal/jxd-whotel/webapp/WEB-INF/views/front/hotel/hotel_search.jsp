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
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain.css?v=${version}">
<style>
.dateDailog{position:relative;}
.dateDailog abbr{position:absolute;left:0;top:0;}
.dateDailog abbr input{opacity:0;width:100px;cursor:pinter;}
</style>
</head>
<body>
<c:set var="headerTitle" value="酒店搜索" scope="request"/>
<jsp:include page="hotel_header.jsp" />

<div class=" block">
  <form action="/oauth/hotel/listRooms.do" id="hotelListForm">
  <input type="hidden" name="comid" value="${WEIXINFAN_LOGIN_COMPANYID}"/>
  <div class="cityselec">
 	<p class="cityselec_ci">入住城市 <span>
 		<select name="addr"  class="inputstyle_A wd100">
			<option value="">请选择入住城市</option>
			<c:forEach items="${hotelCitys}" var="hc" varStatus="vs">
				<option value="${hc.city}" >${hc.city}</option>
			</c:forEach>
		</select>
 		</span>
    </p>
    <p class="cityselec_date">
    <span>入住时间<input name="beginDate" type="text" readonly placeholder="入住时间" value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>" class="inputstyle_A wd100" id="js-beginDate"/></span>
    <span>离店时间<input name="endDate" type="text"  readonly placeholder="离店时间" value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>" class="inputstyle_A wd100" id="js-endDate"/></span>
    </p>
    <p class="">关健字搜索<span><input name="name" type="text" class="inputstyle_A wd100"  placeholder="酒店名" /></span></p>
    <p class="pdtd20" ><button class="butstyleB"><i class="seach_icon_20"></i>搜索</button>
  </p>
  </div>
  </form>
</div>
  
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script src="/static/common/js/kalendae/kalendae.standalone.js?v=${version}"></script>
<script type="text/javascript" src="/static/common/js/dateUtil.js?v=${version}"></script>
<script type="text/javascript">

$(function() {
	
	var kal1 = new Kalendae.Input('js-beginDate', {
		months:1,
		mode:'single',
		direction:'today-future',
		format: 'YYYY-MM-DD'
	});
	
	kal1.subscribe('change', function (date) {
		updateInvalidDate();
		$("#js-beginDate").blur();
		kal1.hide();
	});
	
	var kal2 = new Kalendae.Input('js-endDate', {
		months:1,
		mode:'single',
		direction:'today-future',
		format: 'YYYY-MM-DD'
	});
	
	kal2.subscribe('change', function (date) {
		updateInvalidDate();
		$("#js-endDate").blur();
		kal2.hide();
	});
	
	var kRit=(($(window).width()-$(".kalendae").width()-15)/2);
	var kBot=(($(window).height()-$(".kalendae").height())/2);
	$(".kalendae").css({"right":kRit});
	
});

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
