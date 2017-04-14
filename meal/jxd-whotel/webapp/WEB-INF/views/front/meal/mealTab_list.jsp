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
<c:set var="headerTitle" value="选择包房" scope="request"/>
<c:set var="mealOrder" value="${MEAL_BOOK}"></c:set>
<c:if test="${mealOrder.items.size()<=0 || mealOrder.items==null}">
	<c:set var="backUrl" value="/oauth/meal/restaurantInfo.do?comid=${WEIXINFAN_LOGIN_COMPANYID}&restaurantId=${mealOrder.restaurantId}" scope="request"/>
</c:if>
<c:if test="${mealOrder.mealTabId != null}">
	<c:set var="backUrl" value="/oauth/meal/dishesBook.do?comid=${WEIXINFAN_LOGIN_COMPANYID}&restaurantId=${mealOrder.restaurantId}" scope="request"/>
</c:if>
<jsp:include page="meal_header.jsp"/>

<form method="post" id="queryMealTab">
	<input type="hidden" name="pageNo" id="js_index" value="1"/>
	<input type="hidden" name="restaurantId" value="${mealOrder.restaurantId}" id="js_restaurantId"/>
	<input type="hidden" name="guestNum" value="${mealOrder.guestNum}"/>
	<input type="hidden" name="arrDate" value="<fmt:formatDate value="${mealOrder.arrDate}" pattern="yyyy-MM-dd"/>"/>
	<input type="hidden" name="arriveTime" value="${mealOrder.arriveTime}"/>
</form>

<div class="ImgTxtlistboxA" id="js_mealTab">
	<c:if test="${page.result == null || page.result.size() == 0}">
		<p style="color:red; text-align:center; padding:10px 10px;">此条件下没有可预订包房！</p>
	</c:if>
	<c:forEach items="${page.result}" var="mealTab">
		<div class="item_cy"><div class="item_cy_dal">
			<%-- <a href="/oauth/meal/mealTabInfo.do?comid=${WEIXINFAN_LOGIN_COMPANYID}&mealTabId=${mealTab.id}"> --%>
			<div class="js_showInfo">
				<div style="display: none;">
					<span class="js_imgs"><c:forEach items="${mealTab.bannerUrls}" var="url">
				    	<li><img style="height: 100%;" src="${url}?imageView2/2/w/290/h/190" /></li>
				    </c:forEach></span>
					<span class="js_notes">${mealTab.notes}</span>
					<span class="js_remark">${mealTab.remark}</span>
				</div>
				<p class="pic_L"><img src="${mealTab.miniatureUrl}?imageView2/2/w/120/h/100" width="200" height="146" /></p>
				<h2 class="clorshowH">${mealTab.tabName}</h2>
				<p class="fx clorshowHs">适合<span class="js_seats">${mealTab.seats}</span>人</p>
				<%-- <span class="price">最低消费：<i class="font-color">¥${mealTab.minimums==null?0.0:mealTab.minimums}</i></span> --%>
				<p class="price clorshowY">¥<fmt:formatNumber pattern="0.00" value="${mealTab.deposit}"/></p>
			</div>
			<div class="But_yd"><button class="butstyleE_sm" <c:if test="${mealTabMap[mealTab.tabNo]==null || mealTabMap[mealTab.tabNo]==1}">style="border: 1px solid #999;color: #999;" disabled="disabled" </c:if>
				onclick="mealTabBook('${mealTab.restaurantId}','${mealTab.id}')">确定</button></div>
		</div></div>
	</c:forEach>
</div>

<!----弹窗---->
<div class="bfmtbox">
	<div class="am-modal-actions" id="my-actions">
  		<div class="am-modal-actions-group">
     		<div class="bfmtbox_dl"><!-- data-am-slider='{"directionNav":false}' -->
     			<span id="js_slider"><div data-am-widget="slider" class="am-slider am-slider-a1">
  					<ul class="am-slides"></ul>
				</div></span>
				<div class="block_pdbr">
					<h2 id="js_alertName"></h2>
					<p><span class="clorshowH">适合人数：</span><span id="js_alertSeats"></span>人</p>
					<p><span class="clorshowH">包房简介：</span><span id="js_alertRemark"></span></p>
				</div>
				<div class=" block_pdbr mrg-t5">
					<h2>预定须知</h2>
					<p class="clorshowH" id="js_alertNotes"></p>
				</div>
			</div>
  		</div>
  		<button class="bfmtbox_close_but" data-am-modal-close>关闭</button>
	</div>
</div>
<!----弹窗END---->

<input type="hidden" id="js_totalPages" value="${page.totalPages}"/>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/jquery.lazyload.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
$(function() {
	$("body").on("click",".js_showInfo",function(){
		var _this = $(this);
		$("#js_alertName").text(_this.find(".clorshowH").text());
		$("#js_alertRemark").text(_this.find(".js_remark").text());
		$("#js_alertNotes").text(_this.find(".js_notes").text());
		$("#js_alertSeats").text(_this.find(".js_seats").text());
		
		var html = '<div data-am-widget="slider" class="am-slider am-slider-a1" id="js_slider">'
				 + '	<ul class="am-slides">'+_this.find(".js_imgs").html()+'</ul>'
				 + '</div>';
		$("#js_slider").html(html);
		$('.am-slider').flexslider();
		
		$("#my-actions").modal();
	});
	
	var finished = true;
	var pageNo = 1;
	$(window).scroll(function() {
		if(finished && $(document).scrollTop() >= (($(document).height() - $(window).height())/2)){
			var totalPages = parseInt($("#js_totalPages").val());
			if(pageNo<totalPages){
				finished = false;
				$("#js_mealTab").append('<div class="js_loadData lodingtxet"><img src="/static/front/images/loading1.gif"/>正在加载更多数据</div>');
				pageNo = pageNo+1;
				$("#js_index").val(pageNo);
				$.ajax({
		    		url:"/oauth/meal/ajaxListMealTab.do",
		    		data:$("#queryMealTab").serialize(),
		    		dataType:"json",
		    		success:function(data) {
		    			var data = data.result;
		    			for (var i = 0; i < data.length; i++) {
		    				var mealTab = data[i];
		    				var html ='	<div class="item_cy"><div class="item_cy_dal">';
					    	html+='			<div class="js_showInfo">';
					    	html+='				<div style="display: none;">';
					    	html+='					<span class="js_imgs">';
					    	var bannerUrls = mealTab.bannerUrls;
					    	if(bannerUrls != null){
						    	for (var j = 0; j < bannerUrls.length; j++) {
									html+='					<li><img height="210px" src="'+bannerUrls[j]+'?imageView2/2/w/290/h/190"/></li>';
								}
					    	}
					    	html+='					</span>';
					    	html+='					<span class="js_notes">'+mealTab.notes+'</span>';
					    	html+='					<span class="js_remark">'+mealTab.remark+'</span>';
					    	html+='				</div>';
					    	html+='				<p class="pic_L"><img src="'+mealTab.miniatureUrl+'?imageView2/2/w/120/h/100" width="200" height="146" /></p>';
					    	html+='				<h2 class="clorshowH">'+mealTab.tabName+'</h2>';
					    	html+='				<p class="fx clorshowHs">适合<span class="js_seats">'+mealTab.seats+'</span>人</p>';
					    	html+='				<p class="price clorshowY">¥'+(mealTab.deposit).toFixed(2)+'</p>';
					    	html+='			</div>';
					    	var style = '';
					    	if(mealTab.status == 1){
					    		style = 'style="border: 1px solid #999;color: #999;" disabled="disabled"';
					    	}
					    	html+='			<div class="But_yd"><button class="butstyleE_sm" '+style+' onclick="mealTabBook(\''+$("#js_restaurantId").val()+'\',\''+mealTab.id+'\')">确定</button></div>';
					    	html+='		</div></div>';
						    $("#js_mealTab").append(html);
							$("img.lazy").lazyload({effect: "fadeIn"});
						}
						finished = true;
						$(".js_loadData").remove();
		    		}
		    	});
			}else{
				$(".js_loadData").remove();
				$("#js_mealTab").append('<div class="js_loadData lodingtxet">没有更多数据</div>');
			}
		}
	});
});

function mealTabBook(restaurantId,mealTabId) {
	var contactName = "${contactName}";
	var contactMobile = "${contactMobile}";
	document.location="/oauth/meal/dishesBook.do?restaurantId="+restaurantId+"&mealTabId="+mealTabId+"&isRoom=true&contactName=" + contactName + "&contactMobile=" + contactMobile;
}

</script>
</body>
</html>
