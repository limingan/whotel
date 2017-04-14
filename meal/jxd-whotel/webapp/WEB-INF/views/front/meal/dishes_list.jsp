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
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common2.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/meal/css/main.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/common/js/kalendae/kalendae.css">
<style>body,html{ height:100%}</style>
</head>
<body class="badypd">
<c:set var="headerTitle" value="菜单" scope="request"/>
<c:set var="mealOrder" value="${MEAL_BOOK}"></c:set>
<c:set var="backUrl" value="/oauth/meal/restaurantInfo.do?comid=${WEIXINFAN_LOGIN_COMPANYID}&restaurantId=${mealOrder.restaurantId}" scope="request"/>
<jsp:include page="meal_header.jsp"/>

<div class="dcdatabox">
  	<div class="dcdatabox_data"> <span class="title clorshowHs">订餐日期</span>
    	<input type="text" class="inputdata" id="js-kalendae" readonly="readonly" name="arrDate" value="<fmt:formatDate value="${mealOrder.arrDate}" pattern="yyyy-MM-dd"/>"/>
    	<input type="hidden" id="js_startDate" value=""/>
    	<input type="hidden" id="js_endDate" value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>"/>
  	</div>
  	<div class="dcdatabox_sd"> <span class="title clorshowHs">用餐时段</span>
    	<select class="selsd" id="js_shuffle" onchange="ajaxRefreshMenu()">
      		<%-- <c:forEach items="${shuffles}" var="shuffle">
				<option value="${shuffle.id}" data-start="${shuffle.startTime}" data-end="${shuffle.endTime}" <c:if test="${mealOrder.shuffleNo == shuffle.id}">selected="selected"</c:if>>${shuffle.shuffleName}</option>
			</c:forEach> --%>
    	</select>
  	</div>
  	<div id="js_shuffleOption" style="display: none;">
  		<c:forEach items="${shuffles}" var="shuffle">
			<option value="${shuffle.id}" data-start="${shuffle.startTime}" data-end="${shuffle.endTime}" <c:if test="${mealOrder.shuffleNo == shuffle.id}">selected="selected"</c:if>>${shuffle.shuffleName}</option>
		</c:forEach>
  	</div>
</div>

<div class="cdconten">
	<div class="cdcont_menu" style="overflow:auto;height: 100%;">
    	<ul class="" id="js_dishNo">
      		<li><a href="javascript:updateDishes('-1');"id="js_dishCat-1" class="hover" data-dishNo="-1">全部</a></li>
      		<c:forEach items="${dishesCategories}" var="cat" varStatus="vs">
				<li><a href="javascript:updateDishes('${vs.index}');" id="js_dishCat${vs.index}" data-dishNo="${cat.id}">${cat.dishName}</a></li>
			</c:forEach>
    	</ul>
  	</div>
  
  	<div class="cdcont_list" style="overflow:auto;" id="js_items">
  		<c:if test="${page.result == null || page.result.size() == 0}">
			<p style="color:red; text-align:center; padding:10px 10px;">此条件下没有可预订菜肴！</p>
		</c:if>
		<c:forEach items="${page.result}" var="dishes" varStatus="vs">
	    	<div class="cdcont_list_itme js_dishes">
	    		<c:set var="num" value="0"/>
				<c:forEach items="${mealOrder.items}" var="item">
					<c:if test="${item.dishesId == dishes.id}"><c:set var="num" value="${num+item.itemQuantity}"/></c:if>
				</c:forEach>
	    		<%--<a class="link-img" href="javascript:showDishesInfo('${WEIXINFAN_LOGIN_COMPANYID}','${dishes.id}')"> --%>
		      		<p class="pic_L" style="cursor:pointer;" onclick="showDishesInfo('${WEIXINFAN_LOGIN_COMPANYID}','${dishes.id}')"><img src="${dishes.miniatureUrl}" alt="暂无图片" width="238" height="182" /></p>
		      		<h3 class="title" id="js_dishName${dishes.id}">${dishes.dishName}<!-- <span class="clorshowHs">当天送新鲜青菜</span> --></h3>
		      		<p class="price" data-price="${dishes.price}" id="js_price${dishes.id}"><b class="clorshowY">￥${dishes.price}</b>
		      		<c:if test="${empty fn:trim(dishes.unit)}">/例</p></c:if>
           			<c:if test="${!empty fn:trim(dishes.unit)}">/${fn:trim(dishes.unit)}</p></c:if>
<!-- 	      		</a> -->
	      		<div class="vCount"> 
	      			<em class="add" onclick="reduceQuantity('${dishes.id}')">-</em>
	      			<span class="nb" id="js_number${dishes.id}">${num}</span>
	      			<em class="reduce" onclick="addQuantity('${dishes.id}')">+</em> 
	      		</div>
	    	</div>
    	</c:forEach>
  	</div>
</div>

<!-- <div class="footer_spcartbox">
  <div class="footer_spcart"> <a href="#" class="Ic"></a></div>
  <div class="total">
    <div class="none clorshowHs">购物车是空的</div>
  </div>
  <div class="fr">
    <button class="footer_spcartbut">结算</button>
  </div>
</div> -->

<div class="footer_spcartbox">
  	<div class="footer_spcart"><a href="javascript:void()" data-am-modal="{target: '#my-actions'}" class="Ic">
  		<em <c:if test="${mealOrder.items.size()==null || mealOrder.items.size()==0}">style="display: none;"</c:if> class="nb js_itemSize">${mealOrder.items.size()}</em>
  	</a></div>
  	<div class="total ">
  		<span class="price clorshowY totalFeeShow">合计<b class="js_totalPrice">${mealOrder.totalFee==null?"0.00":mealOrder.totalFee}</b>元</span>
  		<a href="javascript:void()" data-am-modal="{target: '#my-actions'}" class="dalmx clorshowHs">查看明细</a>
  	</div>
  	<div class="fr">
    	<button class="footer_spcartbut" onclick="return bookDishes()">结算</button>
  	</div>
</div>

<div class="am-modal-actions" id="my-actions" style="text-align: left;margin-bottom: 45px;display: none;">
	<div class="priceDetailsbox block_pd js-priceDetail">
		<dl class="priceDetails">
			<dt>账单明细</dt>
			<dd>
			<form action="/oauth/meal/dishesBook.do?comid=${WEIXINFAN_LOGIN_COMPANYID}" id="js_mealOrderItem" method="post" >
				<input type="hidden" id="js_index" value="${mealOrder.items.size()}"/>
				<input type="hidden" id="js_arrDateInput" name="arrDate" value="<fmt:formatDate value="${mealOrder.arrDate}" pattern="yyyy-MM-dd"/>"/>
				<input type="hidden" name="shuffleNo" id="js_shuffleInput" value="${mealOrder.shuffleNo}"/>
				<input type="hidden" name="shuffleName" id="js_shuffleName" value="${mealOrder.shuffleName}"/>
				<div id="js_mealOrderItems">
					<c:forEach items="${mealOrder.items}" var="item" varStatus="vs">
						<div class="dishesItem"><label class="fleft">${item.name}</label>
							<label class="fright">${item.itemPrice}x${item.itemQuantity}</label>
							<input type="hidden" name="items[${vs.index}].dishesId" value="${item.dishesId}" id="js_itemId${item.dishesId}">
							<input type="hidden" name="items[${vs.index}].itemQuantity" class="js_itemQuantity" data-price="${item.itemPrice}" value="${item.itemQuantity}" id="js_itemQuantity${item.dishesId}">
						</div>
					</c:forEach>
				</div>
			</form>
			</dd>
			<dd class="align_R">总价：<b class="colorking js_totalPrice">¥<fmt:formatNumber value="${mealOrder.totalFee==null?'0.00':mealOrder.totalFee}" type="currency" pattern="0.00"/></b></dd>
		</dl>
	</div>
</div>

<!-- <div class="TotalOrders">
	<div class="TotalOrders_r">
		<a href="javascript:void(0)" class="TotalDetails" data-am-modal="{target: '#my-actions'}">菜肴明细></a>
		<button class="TotalOrders_r_but" onclick="return bookDishes()">结算</button>
	</div>
	<div class="TotalOrders_price">
		<span class="js-totalFeeShow"><b class="js_totalPrice">¥0.00</b></span>
	</div>
</div> -->

<div class="am-modal am-modal-alert" tabindex="-1" id="alertTip">
  <div class="am-modal-dialog">
    <div class="am-modal-bd" id="alertMsg">
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn">确定</span>
    </div>
  </div>
</div>

<input id="js_comid" type="hidden" value="${WEIXINFAN_LOGIN_COMPANYID}"/>
<input type="hidden" id="js_totalPages" value="${page.totalPages}"/>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/jquery.lazyload.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script src="/static/common/js/kalendae/kalendae.standalone.js"></script>
<script type="text/javascript" src="/static/common/js/scrollLoad.js?v=${version}"></script>
<script type="text/javascript">
var finished = true;
var pageNo = 1;

$(function() {
	
	showShuffle();
	
	var kal = new Kalendae.Input('js-kalendae', {
		//months:1,
		mode:'single',
		direction:'today-future',
		format: 'YYYY-MM-DD',
		selectEndDate:$("#js_endDate").val(),
		selectStartDate:$("#js_startDate").val()
	});
	
	var kRit=(($(window).width()-$(".kalendae").width()-15)/2);
	var kBot=(($(window).height()-$(".kalendae").height())/2);
	$(".kalendae").css({"right":kRit});
	
	$("#js-kalendae").click(function(){
		kal.show();
	});
	
	kal.subscribe('change', function (date) {
		//$("#js-kalendae").blur();
		kal.hide();
		$("#js_arrDateInput").val($("#js-kalendae").val());
		
		showShuffle();
	});
	
	$("#js_items").scroll(function() {
		if(finished && $("#js_items").scrollTop() >= (($("#js_items")[0].scrollHeight - $(window).height())/2)){
			var totalPages = parseInt($("#js_totalPages").val());
			if(pageNo<totalPages){
				finished = false;
				loadDishes();
			}else{
				$(".js_loadData").remove();
				$("#js_items").append('<div class="js_loadData lodingtxet">没有更多数据</div>');
			}
		}
	});
});
function loadDishes(){
	$("#js_items").append('<div class="js_loadData lodingtxet"><img src="/static/front/images/loading1.gif"/>正在加载更多数据</div>');
	pageNo = pageNo+1;
	var dishno1 = $(".hover").attr("data-dishNo");
	var shuffleNo = $("#js_shuffle").val();
	var comid = $("#js_comid").val();
	$.ajax({
		url:"/oauth/meal/ajaxDishesList.do?pageNo="+pageNo,
		data : {dishno1:dishno1,shuffleNo:shuffleNo},
		async: false,
		dataType:"json",
		success:function(data) {
			for (var i = 0; i < data.length; i++) {
				var dishes = data[i];
				var html ='<div class="cdcont_list_itme js_dishes">';
				html+='		<a class="link-img" href="javascript:showDishesInfo(\''+comid+'\',\''+dishes.id+'\')">';
		    	html+='			<p class="pic_L"><img src="'+dishes.miniatureUrl+'" width="238" height="182" /></p>';
		    	html+='			<h3 class="title" id="js_dishName'+dishes.id+'">'+dishes.dishName+'</h3>';
		    	html+='			<p class="price" data-price="'+dishes.price+'" id="js_price'+dishes.id+'"><b class="clorshowY">¥'+dishes.price;
		    	
		    	if(dishes.unit == null || $.trim(dishes.unit) == '') {
		    		html+='</b>/例</p>';
		    	} else {
		    		html+='</b>/' + dishes.unit + '</p>';
		    	}
		    	
		    	html+='		</a>';
		    	html+='		<div class="vCount"> ';
		    	html+='			<em class="add" onclick="reduceQuantity(\''+dishes.id+'\')">-</em>';
		    	html+='			<span class="nb" id="js_number'+dishes.id+'">0</span>';
		    	html+='			<em class="reduce" onclick="addQuantity(\''+dishes.id+'\')">+</em>';
		    	html+='		</div>';
		    	html+='</div>';
			    $("#js_items").append(html);
				$("img.lazy").lazyload({effect: "fadeIn"});
			}
			finished = true;
			$(".js_loadData").remove();
		}
	});
	iDontKnow();
}

function updateDishes(index){
	$(".js_dishes").remove();
	var _id = "js_dishCat"+index;
	$("#"+_id).addClass("hover")//.siblings("a").removeClass("hover");
	$(".hover").each(function(){
		if($(this).attr("id") != _id){
			$(this).removeClass("hover");
		}
	});
	pageNo=0;
	loadDishes();
}

var index = parseInt($("#js_index").val().length==0?0:$("#js_index").val());
function addQuantity(dishesId){
	var quantity = 1;
	var price = $("#js_price"+dishesId).attr("data-price");
	var dishName = $("#js_dishName"+dishesId).text();
	if($("#js_itemId"+dishesId).val()==undefined){
		var html = '<div class="dishesItem">';
		html+= '<label class="fleft">'+dishName+'</label><label class="fright">'+price+'x'+quantity+'</label>';
		html+= '<input type="hidden" name="items['+index+'].dishesId" value="'+dishesId+'" id="js_itemId'+dishesId+'">';
		html+= '<input type="hidden" name="items['+index+'].itemQuantity" class="js_itemQuantity" data-price="'+price+'" value="'+quantity+'" id="js_itemQuantity'+dishesId+'">';
		html+= '</div>';
		$("#js_mealOrderItems").append(html);
		//$("#js_dishName"+dishesId).addClass("color-num-add");
		index +=1;
	}else{
		quantity = parseInt($("#js_itemQuantity"+dishesId).val())+1;
		$("#js_itemQuantity"+dishesId).val(quantity);
		$("#js_itemId"+dishesId).closest("div").find("label[class=fright]").text(price+'x'+quantity);
	}
	$("#js_number"+dishesId).html(quantity);
	updateTotalPrice();
}
function reduceQuantity(dishesId){
	var quantity = parseInt($("#js_number"+dishesId).html())-1;
	if(quantity>=0){
		$("#js_number"+dishesId).html(quantity);
		$("#js_itemQuantity"+dishesId).val(quantity);
		var price = $("#js_price"+dishesId).attr("data-price");
		$("#js_itemId"+dishesId).closest("div").find("label[class=fright]").text(price+'x'+quantity);
	}
	if(quantity==0){
		$("#js_itemId"+dishesId).closest("div").remove();
		//$("#js_dishName"+dishesId).removeClass("color-num-add");
	}
	updateTotalPrice();
}

function updateTotalPrice(){
	var items = $("#js_mealOrderItems").find(".js_itemQuantity");
	var totalPrice = 0.0;
	for (var i = 0; i < items.length; i++) {
		var price = parseFloat($(items[i]).attr("data-price"));
		var number = parseInt($(items[i]).val());
		totalPrice+=price*number;
	}
	$(".js_totalPrice").text("¥"+totalPrice.toFixed(2));
	if(items.length>0){
		$(".js_itemSize").text(items.length);
		$(".js_itemSize").show();
	}else{
		$(".js_itemSize").hide();
	}
}

function bookDishes(){
	var isInput = $("#js_mealOrderItems").find("input");
	if(isInput.length>0){
		$("#js_mealOrderItem").submit();
	}else{
		showMessage("请至少选一样菜");
	}
}

function showMessage(message) {
	$("#alertMsg").html(message);
	$("#alertTip").modal();
}

function iDontKnow(){
	$("#js_mealOrderItems").find(".dishesItem").each(function(i){
		var dishesId = $(this).find(":input:first").val();
		//$("#js_dishName"+dishesId).addClass("color-num-add");
		$("#js_number"+dishesId).text($("#js_itemQuantity"+dishesId).val());
	});
}

function ajaxRefreshMenu(){
	$(".js_dishes").remove();
	$("#js_shuffleInput").val($("#js_shuffle").val());
	$("#js_shuffleName").val($("#js_shuffle").find("option:selected").text());
	$("#js_mealOrderItems").empty();
	updateTotalPrice();
	pageNo=0;
	loadDishes();
}

function showDishesInfo(comid,dishesId){
	$("#js_mealOrderItem").attr("action","/oauth/meal/dishesInfo.do?comid="+comid+"&dishesId="+dishesId);
	$("#js_mealOrderItem").submit();
}

Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}

function showShuffle(){
	$("#js_startDate").val(addDay(-1).Format("yyyy-MM-dd"));
	$("#js_shuffle").html($("#js_shuffleOption").html());
	
	var now = (new Date()).Format("yyyy-MM-dd");
	var arrDate = $("#js-kalendae").val();
	if(now == arrDate){
		$("#js_shuffle").find("option").each(function(){
			var _this = $(this);
			var nowTime = parseInt((new Date()).Format("hh:mm").replace(":",""));
			var end = parseInt(_this.attr("data-end").replace(":",""));
			if(nowTime>end){
				_this.remove();
			}
		});
		if($("#js_shuffle").find("option").length == 0){
			$("#js_startDate").val(now);
			$("#js-kalendae").val(addDay(1).Format("yyyy-MM-dd"));
			$("#js_shuffle").html($("#js_shuffleOption").html());
		}
	}
}

//方法 增添dayNumber天（整形），date：如果没传就使用今天（日期型）
function addDay(dayNumber) {
    var date = new Date();
    var ms = dayNumber * (1000 * 60 * 60 * 24)
    var newDate = new Date(date.getTime() + ms);
    return newDate;
}

function closeOrGoback() {
	if(document.referrer === '') {
		WeixinJSBridge.call('closeWindow');
	} else {
		window.history.go(-1);
	}
}
</script>
</body>
</html>
