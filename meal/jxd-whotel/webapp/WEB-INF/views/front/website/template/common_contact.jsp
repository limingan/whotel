<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common${THEME}.css?v=${version}">
<style type="text/css">

.Contactbox a{ display:inline-block; padding:5px 0}
.Contactbox p{ padding:0 10%}
.contactusblack{left:0; width:100% !important; background:#fff; padding:50px 0}
.contactus_addresstxt{width:90%; margin:0 auto; margin-top:10px; padding:5px; clear:both; text-align:center;background:#EFEFEF; border:1px solid #ccc; font-size:14px;display: none;}
.contactus_but:after{ content:""; clear:both; display:table }
.contactusblack p{ float:left; width:33.3333%}
.contactusblack p a{ display:block; margin:0 auto; width:90px;text-align:center;font-size:14px}
[class*="contact_icon_"]{ display:block; width:50px; height:50px; margin:0 auto;}
.contact_icon_tel{background:url(/static/front/images/home_icon_4_2.png) no-repeat;}
.contact_icon_add{background:url(/static/front/images/loc_1.png) no-repeat}
.contact_icon_weixin{background:url(/static/front/images/weixin_1.png) no-repeat;}
[class*="contact_icon_"]{ background-size:50px 50px}
.contactusblackbox .am-modal{margin-left:0px !important}
 ::-webkit-scrollbar-track-piece{background-color: rgba(0,0,0,0.1);border:0;border-radius: 8px;}
::-webkit-scrollbar{width: 8px;height: 8px;border:0;border-radius: 8px;}
::-webkit-scrollbar-thumb {background-color: #ccc;background-clip: padding-box;min-height: 28px;border:0;border-radius: 8px;}
</style>
</head>
<body>

<!----联系我们弹框----->
<div class="contactusblackbox">
	<div class="contactusblack am-modal"  tabindex="-1" id="js-contactDialog">
		<div class="contactus_but">
			<c:set var="call" value="tel:${company.tel}"/>
			<p>
			<c:choose>
				<c:when test="${!empty company.tel}">
					<a href="tel:${company.tel}"><i class="contact_icon_tel"></i>拔打电话</a>
				</c:when>
				<c:when test="${fn:length(hotel) == 1}">
					<a href="tel:${hotel[0].tel}"><i class="contact_icon_tel"></i>拔打电话</a>
				</c:when>
				<c:otherwise>
					<a href="javascript:call()"><i class="contact_icon_tel"></i>拔打电话</a>
				</c:otherwise>
			</c:choose>
			</p>
			 <p>
				<c:choose>
					<c:when test="${!company.group || fn:length(hotel) == 1}">
						<a href="http://api.map.baidu.com/marker?location=${company.coords[1]},${company.coords[0]}&title=${company.name}&content=${company.addr}&output=html&src=jxd">
						<i class="contact_icon_add"></i>查看地址</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:" class="js-showAddr"><i class="contact_icon_add"></i>查看地址</a>
					</c:otherwise>
				</c:choose>
			</p>
			<p><a href="/wxContact.do"><i class="contact_icon_weixin"></i>微信</a></p>
		</div>
	</div>
</div>

<div class="am-modal am-modal-alert" tabindex="-1" id="js-alertTel">
	<div class="am-modal-dialog" style="border-radius: 6px;">
		<div class="am-modal-bd" style="border-bottom: 0px solid #f8f8f8;padding: 0px;max-height:216px; overflow-y: scroll;">
			<c:forEach var="hotel" items="${hotels}">
				<c:if test="${hotel.tel!=null && hotel.tel!=''}">
					<p style="border-bottom: 1px solid #dedede;padding: 5px 10px;border-radius: 6px;">
						<a href="tel:${hotel.tel}" style="width: 100%;">${hotel.name}</a>
					</p>
				</c:if>
			</c:forEach>
		</div>
		 <div class="am-modal-footer" style="height: 0px;"></div>
	</div>
</div>

<div class="am-modal am-modal-alert" tabindex="-1" id="js-alertAdd">
	<div class="am-modal-dialog" style="border-radius: 6px;">
		<div class="am-modal-bd" style="border-bottom: 0px solid #f8f8f8;padding: 0px;max-height:216px; overflow-y: scroll;">
			<c:forEach var="hotel" items="${hotels}">
				<c:if test="${hotel.coord!=null && hotel.coord!=''}">
					<p style="border-bottom: 1px solid #dedede;padding: 5px 10px;border-radius: 6px;" class="map">
						<a href="http://api.map.baidu.com/marker?location=${hotel.coords[1]},${hotel.coords[0]}&title=${hotel.name}&content=${hotel.address}&output=html&src=jxd" 
						 style="width: 100%;">${hotel.name}</a>
					</p>
				</c:if>
			</c:forEach>
		</div>
		 <div class="am-modal-footer" style="height: 0px;"></div>
	</div>
</div>

<script type="text/javascript">
$(function() {
	$("#cover").css("height",$(window).height());
	$("#cover").css("width",$(window).width());
	$(".js-contact").click(function(e) {
		$("#js-contactDialog").modal();
		e.stopPropagation();
	});
	$(".js-showAddr").click(function() {
		$("#js-contactDialog").modal('close');
		$("#js-alertAdd").modal();
	});
	$(".map").click(function(){
		window.location.href = $(this).find("a").attr("href");
	});
});

function call(){
	$("#js-contactDialog").modal('close');
	$("#js-alertTel").modal();
}
</script>
</body>
</html>
