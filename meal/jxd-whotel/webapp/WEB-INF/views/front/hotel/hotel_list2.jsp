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
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
</head>
<body>
<c:set var="headerTitle" value="酒店列表" scope="request"/>
<jsp:include page="hotel_header.jsp" />

<form action="/oauth/hotel/listRooms.do" id="hotelListForm">
	<input type="hidden" name="comid" value="${WEIXINFAN_LOGIN_COMPANYID}"/>
	<input type="hidden" name="addr" value="${query.addr}"/>
	<input type="hidden" name="beginDate" value="<fmt:formatDate value="${query.beginDate}" pattern="yyyy-MM-dd"/>" id="beginDate"/>
	<input type="hidden" name="endDate" value="<fmt:formatDate value="${query.endDate}" pattern="yyyy-MM-dd"/>" id="endDate"/>
</form>
<form method="post" id="queryHotelBranch">
	<input type="hidden" name="index" id="js_index" value="1"/>
	<input type="hidden" name="beginDate" value="<fmt:formatDate value="${query.beginDate}" pattern="yyyy-MM-dd"/>" id="beginDate"/>
	<input type="hidden" name="endDate" value="<fmt:formatDate value="${query.endDate}" pattern="yyyy-MM-dd"/>" id="endDate"/>
	<input type="hidden" name="companyId" id="js_companyId" value="${WEIXINFAN_LOGIN_COMPANYID}"/>
	<input type="hidden" name="addr" value="${query.addr}"/>
	<input type="hidden" name="name" value="${query.name}"/>
</form>

<div class="ht_Cdselec">
    <div class="check-date">
	    <a href="#">
	    	<p class="date">入住日期<span><em id="js_emBeginDate"><fmt:formatDate value="${query.beginDate}" pattern="yyyy-MM-dd"/></em><i class="more_icon_12"></i></span></p>
	    </a>
	    <span class="hotel_List_date">
	    	<abbr><input id="js-beginDate" readonly="readonly" value="<fmt:formatDate value="${query.beginDate}" pattern="yyyy-MM-dd"/>"/></abbr>
	    </span>
    </div>
  	<div class="dateSelection hotel-endDate">
	  	<a href="#">
	    	<p class="date">离店日期<span><em id="js_emEndDate"><fmt:formatDate value="${query.endDate}" pattern="yyyy-MM-dd"/></em><i class="more_icon_12"></i></span></p>
	    </a>
    	<span class="hotel_List_date">
	    	<abbr><input id="js-endDate" readonly="readonly" value="<fmt:formatDate value="${query.endDate}" pattern="yyyy-MM-dd"/>"/></abbr>
	    </span>
    </div>
</div>


<div class="GroupHotelslistbox">
  
   <c:if test="${hotelBranchs == null || hotelBranchs.size() == 0}">
		<p style="color:red; text-align:center; padding:10px 10px;">此条件下没有可预订酒店！</p>
</c:if>

<c:forEach items="${hotelBranchs}" var="hotelBranch">
<div class="GroupHotelslist">
	<ul>
		<li class="GroupHotelslist_pic">
			<a href="/oauth/hotel/showHotel.do?comid=${WEIXINFAN_LOGIN_COMPANYID}&code=${hotelBranch.code}">
				<c:choose>
					<c:when test="${hotelMap[hotelBranch.code]!=null && hotelMap[hotelBranch.code]!=''}">
						<img src="${hotelMap[hotelBranch.code]}?imageView2/0/w/150/h/150"/>
					</c:when>
					<c:otherwise>
						<img data-original="${hotelBranch.hotelPic}&width=150&height=150&flag=50"/>
					</c:otherwise>
				</c:choose>
			</a>
		</li>
		<li class="GroupHotelslist_info"  onclick="bookRoom('${WEIXINFAN_LOGIN_COMPANYID}', '${hotelBranch.code}')">
			<h3>${hotelBranch.cname}</h3>
			<span><i class="sm12_icon_tel"></i>${hotelBranch.tel}</span> 
			<span style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden;"><i class="sm12_icon_ad"></i>${hotelBranch.address}</span>
		</li>
		<li class="Groupotelslist_eia" onclick="bookRoom('${WEIXINFAN_LOGIN_COMPANYID}', '${hotelBranch.code}')">
			<p class="Price">
			¥<em><fmt:formatNumber value="${hotelBranch.minPrice}" type="currency" pattern="#######.##"/></em>起
			</p>
		</li>
	</ul>
</div>
</c:forEach>
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
<input type="hidden" id="js_totalPages" value="${totalPages}"/>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/jquery.lazyload.js"></script>
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
	
	$("#js-beginDate").change(function() {
		kal2.hide();
		var beginDate = $('#js-beginDate').val();
		$("#beginDate").val(beginDate);
		$("#js_emBeginDate").text(beginDate);
		updateInvalidDate();
		kal1.hide();
		var form = $("#hotelListForm");
		form.submit();
	});
	
	var kal2 = new Kalendae.Input('js-endDate', {
		months:1,
		mode:'single',
		direction:'today-future',
		format: 'YYYY-MM-DD'
	});
	
	//kal2.subscribe('change', function (date) {
	$("#js-endDate").change(function() {
		kal1.hide();
		var endDate = $('#js-endDate').val();
		$("#endDate").val(endDate);
		$("#js_emEndDate").text(endDate);
		updateInvalidDate();
		var form = $("#hotelListForm");
		form.submit();
	});
	
	function updateInvalidDate() {
		var beginDate = $("#js-beginDate").val();
		var endDate = $("#js-endDate").val();
		
		if(beginDate != "" && endDate != "") {
			if(endDate <= beginDate) {
				beginDate = new Date(beginDate);
				beginDate.setDate(beginDate.getDate() + 1);
				var date = beginDate.Format("yyyy-MM-dd");
				$("#endDate").val(date);
				$("#js_emEndDate").val(date);
			}
		}
	}
	
	/* var kal = new Kalendae.Input('js-kalendae', {
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
				var form = $("#hotelListForm");
				form.submit();
			}
		}
	}); */
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
		    				var html ='<div class="GroupHotelslist">';
//		    				html+='<ul>';
//		    				html+='		<a href="/oauth/hotel/showHotel.do?comid='+$("#js_companyId").val()+'&code='+hotelBranch.code+'">';
//		    				html+='			<li class="GroupHotelslist_pic"><img  class="lazy" data-original="'+hotelBranch.hotelPic+'"/></li>';
//		    				html+='			<li class="GroupHotelslist_info">';
//		    				html+='				<h3>'+hotelBranch.cname+'</h3>';
//		    				html+='				<span><i class="sm12_icon_tel"></i>'+hotelBranch.tel+'</span>';
//		    				html+='				<span style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden;"><i class="sm12_icon_ad"></i>'+hotelBranch.address+'</span>';
//		    				html+='			</li>';
//		    				html+='		</a>';
//		    				html+='		<li class="Groupotelslist_eia" onclick="bookRoom(\''+$("#js_companyId").val()+'\', \''+hotelBranch.code+'\')">';
//		    				html+='			<p class="Price">';
//		    				html+='				<i class="zdjg_icon_20">最低价格</i>¥<em>'+hotelBranch.minPrice+'</em>起';
//		    				html+='			</p>';
//		    				html+='		</li>';
//		    				html+='</ul>';
//		    				html+='</div>';
					    	html+='<ul>';
					    	html+='		<li class="GroupHotelslist_pic">';
					    	html+='			<a href="/oauth/hotel/showHotel.do?comid='+$("#js_companyId").val()+'&code='+hotelBranch.code+'">';
					    	html+='				<img class="lazy" data-original="'+hotelBranch.hotelPic+'"/>';
					    	html+='			</a>';
					    	html+='		</li>';
					    	html+='		<li class="GroupHotelslist_info"  onclick="bookRoom(\''+$("#js_companyId").val()+'\', \''+hotelBranch.code+'\')">';
					    	html+='			<h3>'+hotelBranch.cname+'</h3>';
					    	html+='			<span><i class="sm12_icon_tel"></i>'+hotelBranch.tel+'</span>';
					    	html+='			<span style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden;"><i class="sm12_icon_ad"></i>'+hotelBranch.address+'</span>';
					    	html+='		</li>';
					    	html+='		<li class="Groupotelslist_eia" onclick="bookRoom(\''+$("#js_companyId").val()+'\', \''+hotelBranch.code+'\')">';
					    	html+='			<p class="Price">';
					    	html+='				¥<em>'+hotelBranch.minPrice+'</em>起';
					    	html+='			</p>';
					    	html+='		</li>';
					    	html+='</ul>';
					    	html+='</div>';
						    $(".GroupHotelslistbox").append(html);
						    $("img").lazyload();
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
	var $loading = $("#loading");
	$loading.show();
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	document.location="/oauth/hotel/listRooms.do?comid="+comid+"&hotelCode="+hotelCode+"&beginDate="+beginDate+"&endDate="+endDate;
}
function showMessage(message) {
	$("#alertMsg").html(message);
	$("#alertTip").modal();
}
</script>
</body>
</html>
