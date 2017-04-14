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
<body>
<c:set var="headerTitle" value="餐厅列表" scope="request"/>
<c:set var="backUrl" value="/oauth/meal/mealBranchSearch.do?comid=${WEIXINFAN_LOGIN_COMPANYID}" scope="request"/>
<jsp:include page="meal_header.jsp"/>

<form method="post" id="queryRestaurant">
	<input type="hidden" name="pageNo" id="js_index" value="1"/>
	<input type="hidden" name="companyId" id="js_companyId" value="${WEIXINFAN_LOGIN_COMPANYID}"/>
	<input type="hidden" name="hotelCode" value="${hotelCode}"/>
</form>

<div class="ImgTxtlistboxA" id="js_restaurantList">
	<c:if test="${page.result == null || page.result.size() == 0}">
		<p style="color:red; text-align:center; padding:10px 10px;">此条件下没有可预订餐厅！</p>
	</c:if>
	<c:forEach items="${page.result}" var="restaurant" varStatus="vs">
		<div class="item_cy">
			<div class="item_cy_dal">
				<a href="/oauth/meal/restaurantInfo.do?restaurantId=${restaurant.id}">
					<p class="pic_L"><img src="${restaurant.miniatureUrl}?imageView2/0/w/120/h/100"/></p>
					<h2 class="clorshowH">${restaurant.name}</h2>
					<p class="fx clorshowHs">营业时间<span class="clorshowY">${restaurant.businessTime}</span></p>
					<%-- <p class="fx clorshowHs">菜系：<span class="clorshowY">${restaurant.cuisine}</span></p> --%>
					<p class="ctstyle"><span class="ctZ">${restaurant.cuisine}</span></p><!-- ctX,ctT -->
					<%-- <span class="cuisine btn-o"><i class="navigation"></i>
						<a href="http://api.map.baidu.com/direction?region=${restaurant.region.name}&mode=driving&src=${restaurant.name}&output=html&destination=latlng:${restaurant.coords[1]},${restaurant.coords[0]}|name:${restaurant.name}&origin=latlng:${location.lat},${location.lon}|name:你的位置">导航</a>
					</span> --%>
				</a>
			</div>
			<div class="But_sel">
				<!-- <a class="name" href="javascript:mealBook('${WEIXINFAN_LOGIN_COMPANYID}','${restaurant.id}')"> -->
				<%-- <a class="btn-book" href="javascript:mealBook('${WEIXINFAN_LOGIN_COMPANYID}','${restaurant.id}')">预订</a> --%>
				<button class="butstyleD_sm" onclick="dishesList('${WEIXINFAN_LOGIN_COMPANYID}','${restaurant.id}')">订餐</button>
				<button class="butstyleE_sm" onclick="mealTabBook('${WEIXINFAN_LOGIN_COMPANYID}','${restaurant.id}')">订位</button>
			</div>
		</div>
	</c:forEach>
</div>

<input type="hidden" id="js_totalPages" value="${page.totalPages}"/>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/jquery.lazyload.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
$(function() {
	var finished = true;
	var pageNo = 1;
	$(window).scroll(function() {
		if(finished && $(document).scrollTop() >= (($(document).height() - $(window).height())/2)){
			var totalPages = parseInt($("#js_totalPages").val());
			if(pageNo<totalPages){
				finished = false;
				$("#js_restaurantList").append('<div class="js_loadData lodingtxet"><img src="/static/front/images/loading1.gif"/>正在加载更多数据</div>');
				pageNo = pageNo+1;
				$("#js_index").val(pageNo);
				
				$.ajax({
		    		url:"/oauth/meal/ajaxListRestaurant.do",
		    		data:$("#queryRestaurant").serialize(),
		    		dataType:"json",
		    		success:function(data) {
		    			var data = data.result;
		    			for (var i = 0; i < data.length; i++) {
		    				var restaurant = data[i];
		    				var html ='	<div class="item_cy">';
		    				html+='			<div class="item_cy_dal">';
		    				html+='				<a href="/oauth/meal/restaurantInfo.do?restaurantId='+restaurant.id+'">';
		    				html+='					<p class="pic_L"><img src="'+restaurant.miniatureUrl+'?imageView2/0/w/120/h/100"/></p>';
		    				html+='					<h2 class="clorshowH">'+restaurant.name+'</h2>';
		    				html+='					<p class="fx clorshowHs">营业时间<span class="clorshowY">'+restaurant.businessTime+'</span></p>';
		    				html+='					<p class="ctstyle"><span class="ctZ">'+restaurant.cuisine+'</span></p>';
		    				html+='				</a>';
					    	html+='			</div>';
					    	html+='			<div class="But_sel">';
					    	html+='				<button class="butstyleD_sm" onclick="dishesList(\''+restaurant.companyId+'\',\''+restaurant.id+'\')">订餐</button>';
					    	html+='				<button class="butstyleE_sm" onclick="mealTabBook(\''+restaurant.companyId+'\',\''+restaurant.id+'\')">订位</button>';
					    	html+='			</div>';
					    	html+='		</div>';
						    $("#js_restaurantList").append(html);
							$("img.lazy").lazyload({effect: "fadeIn"});
						}
						finished = true;
						$(".js_loadData").remove();
		    		}
		    	});
			}else{
				$(".js_loadData").remove();
				$("#js_restaurantList").append('<div class="js_loadData lodingtxet">没有更多数据</div>');
			}
		}
	});
});

function mealBook(comid, restaurantId) {
	document.location="/oauth/meal/mealBook.do?comid="+comid+"&restaurantId="+restaurantId;
}

function mealTabBook(comid, restaurantId){
	//document.location="/oauth/meal/mealTabList.do?comid="+comid+"&restaurantId="+restaurantId;
	document.location="/oauth/meal/dishesBook.do?comid="+comid+"&restaurantId="+restaurantId;
}

function dishesList(comid, restaurantId){
	document.location="/oauth/meal/dishesList.do?comid="+comid+"&restaurantId="+restaurantId;
}
</script>
</body>
</html>
