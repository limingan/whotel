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
<link rel="stylesheet" type="text/css" href="/static/common/js/kalendae/kalendae.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common2.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/meal/css/main.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain2.css?v=${version}">
</head>
<body>
<c:set var="headerTitle" value="酒店列表" scope="request"/>
<jsp:include page="meal_header.jsp" />

<%-- <form method="post" id="queryHotelBranch">
	<input type="hidden" name="index" id="js_index" value="1"/>
	<input type="hidden" name="beginDate" value="<fmt:formatDate value="${query.beginDate}" pattern="yyyy-MM-dd"/>" id="beginDate"/>
	<input type="hidden" name="endDate" value="<fmt:formatDate value="${query.endDate}" pattern="yyyy-MM-dd"/>" id="endDate"/>
	<input type="hidden" name="companyId" id="js_companyId" value="${WEIXINFAN_LOGIN_COMPANYID}"/>
	<input type="hidden" name="addr" value="${query.addr}"/>
	<input type="hidden" name="name" value="${query.name}"/>
</form> --%>

<div class="ImgTxtlistboxA">
	<c:if test="${hotelBranchs == null || hotelBranchs.size() == 0}">
		<p style="color:red; text-align:center; padding:10px 10px;">此条件下没有可预订的酒店！</p>
	</c:if>
	
	<c:forEach items="${hotelBranchs}" var="hotelBranch">
		<div class="item_cy">
			<div class="item_cy_dal">
				<p class="pic_L"><a href="/oauth/hotel/showHotel.do?comid=${WEIXINFAN_LOGIN_COMPANYID}&code=${hotelBranch.code}">
					<img class="lazy" src="${hotelBranch.hotelPic}&width=150&height=150&flag=100"/>
				</a></p>
				<a href="javascript:bookRoom('${WEIXINFAN_LOGIN_COMPANYID}', '${hotelBranch.code}')">
					<h2 class="clorshowH">${hotelBranch.cname}</h2>
					<p class="fx clorshowHs">电话：${hotelBranch.tel}<br/>地址：${hotelBranch.address}</p>
				</a>
			</div>
			<div class="But_sel">
				<!-- http://api.map.baidu.com/marker?location=${hotel.coords[1]},${hotel.coords[0]}&title=${hotelBranchVO.cname}&content=${hotelBranchVO.address}&output=html&src=jxd -->
				<!-- <button class="butstyleF_sm">导航</button> -->
				<button class="butstyleD_sm" onclick="bookRoom('${WEIXINFAN_LOGIN_COMPANYID}', '${hotelBranch.code}')">预订</button>
			</div>
		</div>
	</c:forEach>
</div>

<input type="hidden" id="js_totalPages" value="${totalPages}"/>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/jquery.lazyload.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
$(function() {
	
	$(".GroupHotelslist_pic").find("img").each(function(){
		$(this).attr("height",this.width*0.7);
	});
	
	var finished = true;
	var pageNo = 1;
	$(window).scroll(function() {
		if(finished && $(document).scrollTop() >= (($(document).height() - $(window).height())/2)){
			var totalPages = parseInt($("#js_totalPages").val());
			if(pageNo<totalPages){
				finished = false;
				$(".GroupHotelslistbox").append('<div class="js_loadData lodingtxet"><img src="/static/front/images/loading1.gif"/>正在加载更多数据</div>');
				pageNo = pageNo+1;
				$("#js_index").val(pageNo);
				$.ajax({
		    		url:"/oauth/hotel/ajaxFindHotelBranchVOs.do",
		    		data:$("#queryHotelBranch").serialize(),
		    		dataType:"json",
		    		success:function(data) {
		    			var data = data.result;
		    			for (var i = 0; i < data.length; i++) {
		    				var hotelBranch = data[i];
		    				var html ='	<div class="item_cy">';
						    	html+='		<div class="item_cy_dal">';
						    	html+='			<p class="pic_L"><a href="/oauth/hotel/showHotel.do?comid='+$("#js_companyId").val()+'&code='+hotelBranch.code+'">';
						    	html+='				<img class="lazy" data-original="'+hotelBranch.hotelPic+'&width=150&height=150&flag=100"/>';
						    	html+='			</a></p>';
						    	html+='		</div>';
						    	html+='		<a href="javascript:bookRoom(\''+$("#js_companyId").val()+'\', \''+hotelBranch.code+'\')">';
						    	html+='			<h2 class="clorshowH">'+hotelBranch.cname+'</h2>';
						    	html+='			<p class="fx clorshowHs">电话：'+hotelBranch.tel+'<br/>地址：'+hotelBranch.address+'</p>';
						    	html+='		</a>';
						    	html+='		<div class="But_sel">';
						    	//html+='			<button class="butstyleF_sm">导航</button>';
						    	html+='			<button class="butstyleD_sm" onclick="bookRoom(\''+$("#js_companyId").val()+'\', \''+hotelBranch.code+'\')">预订</button>';
						    	html+='		</div>';
						    	html+='	</div>';
						    $(".GroupHotelslistbox").append(html);
							$("img.lazy").lazyload({effect: "fadeIn"});
						}
						finished = true;
						$(".js_loadData").remove();
		    		}
		    	});
			}else{
				$(".js_loadData").remove();
				$(".GroupHotelslistbox").append('<div class="js_loadData lodingtxet">没有更多数据</div>');
			}
		}
	});
});

function bookRoom(comid, hotelCode) {
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	document.location="/oauth/meal/listRestaurant.do?comid="+comid+"&hotelCode="+hotelCode;
}
</script>
</body>
</html>
