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
<link rel="stylesheet" type="text/css" href="/static/common/js/kalendae/kalendae.css">
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/meal/css/food.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common2.css?v=${version}"><!-- ${THEME} -->
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain2.css?v=${version}">
</head>
<body>
<c:set var="headerTitle" value="餐饮预订" scope="request"/>
<c:set var="mealOrder" value="${MEAL_BOOK}"></c:set>
<c:set var="backUrl" value="/oauth/meal/listRestaurant.do?comid=${WEIXINFAN_LOGIN_COMPANYID}&hotelCode=${mealOrder.hotelCode}" scope="request"/>
<jsp:include page="meal_header.jsp" />

<form action="" method="post" id="js_mealTab">
	<input type="hidden" name="restaurantId" id="js_restaurantId">
	<!-- <input type="hidden" name="mealType">
	<input type="hidden" name="arrDate">
	<input type="hidden" name="arriveTime"> -->
	<div class="content">
		<ul class="book-about-a">
			<li class="select-date clearfix">
				<label class="fleft dining-time">到店日期</label>
				<span class="date-w">
					<input class="select-date-box111" id="js-kalendae" readonly="readonly" name="arrDate" value="<fmt:formatDate value="${mealOrder.arrDate}" pattern="yyyy-MM-dd"/>"/>
				</span>
				<select class="select-time-box fright" name="shuffleNo" id="js_shuffle" onchange="ajaxListArriveTime()">
					<c:forEach items="${shuffles}" var="shuffle">
						<option value="${shuffle.id}" data-start="${shuffle.startTime}" data-end="${shuffle.endTime}" <c:if test="${mealOrder.shuffleNo == shuffle.id}">selected="selected"</c:if>>${shuffle.shuffleName}</option>
					</c:forEach>
				</select>
				<input type="hidden" name="shuffleName" id="js_shuffleName"/>
			</li>
			<li class="place">抵店时间
				<input type="hidden" id="js_arriveTimeValue" value="${mealOrder.arriveTime}"/>
				<select class="arrive-time-box" name="arriveTime" id="js_arriveTime">
					
				</select>
			</li>
			<li>用餐人数<span class="fright"><input type="text" name="guestNum" value="${mealOrder.guestNum}" placeholder="输入用餐人数"></span></li>
		</ul>
		<ul class="book-about-b">
			<li>包房预订<span class="fright"><a class="select-rooms-btn" href="javascript:mealTabList('${restaurant.id}')">选择包间</a></span></li>
			<li>包房名称<span class="fright">${mealOrder.mealTab.tabName}</span></li>
			<li>容纳人数<span class="fright">${mealOrder.mealTab.seats}人</span></li>
			<li>预定金额<span class="fright">${mealOrder.mealTab.deposit}</span></li>
			<li>最低消费<span class="fright">${mealOrder.mealTab.minimums}</span></li>
		</ul>
		<a class="select-food-btn" href="javascript:dishesList()">选菜</a>
		<a class="select-food-btn<c:if test="${mealOrder.mealTab.id == null}">1</c:if>" href="javascript:<c:if test="${mealOrder.mealTab.id != null}">submitOrder()</c:if>">提交订单</a>
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

<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/jquery.lazyload.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script src="/static/common/js/kalendae/kalendae.standalone.js"></script>
<script type="text/javascript">
$(function(){
	var kal = new Kalendae.Input('js-kalendae', {
		months:1,
		mode:'single',
		direction:'today-future',
		format: 'YYYY-MM-DD'
	});
	
	kal.subscribe('change', function (date) {
		$("#js-kalendae").blur();
		kal.hide();
	});
	
	ajaxListArriveTime();
	/* 
	$("body").on("change", "#js_mealType", function() {
		
	}); */
	
});

function mealTabList(restaurantId){
	$("#js_restaurantId").val(restaurantId);
	$("#js_mealTab").attr("action","/oauth/meal/mealTabList.do");
	$("#js_mealTab").submit();
	//document.location="/oauth/meal/mealTabList.do?comid="+comid+"&restaurantId="+restaurantId;
}

function dishesList(){
	$("#js_mealTab").attr("action","/oauth/meal/dishesList.do");
	if(checkInput()){
		$("#js_mealTab").submit();
	}
	//document.location="/oauth/meal/mealTabList.do?comid="+comid+"&restaurantId="+restaurantId;
}

function submitOrder(){
	$("#js_mealTab").attr("action","/oauth/meal/dishesBook.do");
	if(checkInput()){
		$("#js_mealTab").submit();
	}
}

function checkInput(){
	var arrDate = $("#js-kalendae").val();
	if(arrDate.length==0){
		showMessage("请选择到店日期！！！");
		return false;
	}
	
	return true;
}

function showMessage(message) {
	$("#alertMsg").html(message);
	$("#alertTip").modal();
}

/* function ajaxListArriveTime(){
	var mealType = $("#js_mealType").val();
	$.ajax({
		url:"/oauth/meal/ajaxListArriveTime.do",
		data:{"mealType":mealType},
		async:false,
		dataType:"json",
		success:function(data) {
			var hl = '';
			if(data.length > 0){
				for (var i = 0; i<data.length; i++) {
					hl += '<option value='+data[i].arriveTime+'>'+data[i].arriveTime+'</option>';
				}
			}
			$("#js_arriveTime").html(hl);
		}
	});
} */

function ajaxListArriveTime(){
	$("#js_shuffleName").val($("#js_shuffle").find("option:selected").text());
	var arriveTime = $("#js_arriveTimeValue").val();
	
	var startTime = $("#js_shuffle").find("option:selected").attr("data-start");
	var start = parseInt(startTime.substring(0,2));
	
	var endTime = $("#js_shuffle").find("option:selected").attr("data-end");
	var end = parseInt(endTime.substring(0,2));
	
	var hl = '<option ';
	if(arriveTime==startTime){
		hl += 'selected="selected" ';
	}
	hl += 'value="'+startTime+'">'+startTime+'</option>';
	
	for (var i=start+1; i<end;i++) {
		var seleted = '';
		if(arriveTime==(i+":00")){
			seleted = 'selected="selected"';
		}
		
		hl += '<option '+seleted+' value="'+i+':00">';
		if(i<10){
			hl += '0';
		}
		hl += i+':00</option>';
	}
	hl += '<option ';
	if(arriveTime==endTime){
		hl += 'selected="selected" ';
	}
	hl += 'value="'+endTime+'">'+endTime+'</option>';
	$("#js_arriveTime").html(hl);
}
</script>
</body>
</html>
