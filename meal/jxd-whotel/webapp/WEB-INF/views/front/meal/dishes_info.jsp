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
</head>

<body class="badypd">
<c:set var="mealOrder" value="${MEAL_BOOK}"></c:set>
<c:set var="backUrl" value="javascript:" scope="request"/>
<jsp:include page="meal_header.jsp" />

<div class="homeAD">
	<div data-am-widget="slider" class="am-slider am-slider-a1" data-am-slider='{"directionNav":false}'>
	  <ul class="am-slides">
	    <c:forEach items="${dishes.bannerUrls}" var="url">
	    	<li><img height="272px" src="${url}?imageView2/0/w/367/h/260"/></li>
	    </c:forEach>
	  </ul>
	</div>
</div>

<div class="block_pdbr cpmaininfo">
  <h1 class="title" id="js_dishName">${dishes.dishName}<!-- <span class="clorshowHs">当天送新鲜青菜</span> --></h1>
  <div class="cartbutbox">
    <button class="butstyleE_sm" onclick="addQuantity('${dishes.id}')">加入购物车</button>
  </div>
  <p class="price"><b class="clorshowY">￥${dishes.price}</b>/例</p>
  <input type="hidden" value="${dishes.price}" id="js_price"/>
</div>

<div class="block_pdbr mrg-t5">
  <h3 class="clorshowH">购物须知</h3>
  <div class="txtblcok clorshowHs">
    <p>用餐时间:${dishes.restaurant.businessTime}</p>
    <p>${dishes.notes}</p><!--  -->
  </div>
</div>

<div class="block_pdbr mrg-t5">
  <h3 class="clorshowH">详细说明</h3>
  <div class="txtblcok clorshowHs">
    <p>${dishes.brief}</p>
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
  		<span class="price clorshowY totalFeeShow">合计<b class="js_totalPrice">${mealOrder.totalFee}</b>元</span>
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
			<dd><input id="js_comid" value="${WEIXINFAN_LOGIN_COMPANYID}" type="hidden"/>
			<form action="/oauth/meal/dishesBook.do?comid=${WEIXINFAN_LOGIN_COMPANYID}" id="js_mealOrderItem" method="post" >
				<input type="hidden" id="js_index" value="${mealOrder.items.size()}"/>
				<input type="hidden" id="js_arrDateInput" name="arrDate" value="<fmt:formatDate value="${mealOrder.arrDate}" pattern="yyyy-MM-dd"/>"/>
				<input type="hidden" name="shuffleNo" id="js_shuffleInput" value="${mealOrder.shuffleNo}"/>
				<input type="hidden" name="shuffleName" id="js_shuffleName" value="${mealOrder.shuffleName}"/>
				<c:forEach items="${mealOrder.items}" var="item" varStatus="vs">
					<div class="dishesItem"><label class="fleft">${item.name}</label>
						<label class="fright">${item.itemPrice}x${item.itemQuantity}</label>
						<input type="hidden" name="items[${vs.index}].dishesId" value="${item.dishesId}" id="js_itemId${item.dishesId}">
						<input type="hidden" name="items[${vs.index}].itemQuantity" class="js_itemQuantity" data-price="${item.itemPrice}" value="${item.itemQuantity}" id="js_itemQuantity${item.dishesId}">
					</div>
				</c:forEach>
			</form>
			</dd>
			<dd class="align_R">总价：<b class="colorking js_totalPrice">¥${mealOrder.totalFee}</b></dd>
		</dl>
	</div>
</div>

<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/jquery.lazyload.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
$(function(){
	$(".header_icon_back").click(function(){
		$("#js_mealOrderItem").attr("action","/oauth/meal/statisticalTotalPrice.do?comid="+$("#js_comid").val());
		$("#js_mealOrderItem").submit();
	});
});

var index = parseInt($("#js_index").val().length==0?0:$("#js_index").val());
function addQuantity(dishesId){
	var quantity = 1;
	var price = $("#js_price").val();
	var dishName = $("#js_dishName").text();
	if($("#js_itemId"+dishesId).val()==undefined){
		var html = '<div class="dishesItem">';
		html+= '<label class="fleft">'+dishName+'</label><label class="fright">'+price+'x'+quantity+'</label>';
		html+= '<input type="hidden" name="items['+index+'].dishesId" value="'+dishesId+'" id="js_itemId'+dishesId+'">';
		html+= '<input type="hidden" name="items['+index+'].itemQuantity" class="js_itemQuantity" data-price="'+price+'" value="'+quantity+'" id="js_itemQuantity'+dishesId+'">';
		html+= '</div>';
		$("#js_mealOrderItem").append(html);
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

function updateTotalPrice(){
	var items = $("#js_mealOrderItem").find(".js_itemQuantity");
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
	var isInput = $("#js_mealOrderItem").find("input");
	if(isInput.length>0){
		$("#js_mealOrderItem").submit();
	}else{
		showMessage("请至少选一样菜");
	}
}
</script>
</body>
</html>
