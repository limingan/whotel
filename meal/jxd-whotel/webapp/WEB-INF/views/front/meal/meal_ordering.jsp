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
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common2.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/meal/css/main.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/metronic/assets/css/weiXin.css?v=${version}">
</head>
<body>
<c:set var="headerTitle" value="订单信息" scope="request"/>
<c:set var="mealOrder" value="${MEAL_BOOK}"></c:set>
<c:choose>
	<c:when test="${mealOrder.items.size()<=0 || mealOrder.items==null}">
		<c:set var="backUrl" value="/oauth/meal/restaurantInfo.do?comid=${WEIXINFAN_LOGIN_COMPANYID}&restaurantId=${mealOrder.restaurantId}" scope="request"/>
	</c:when>
	<c:otherwise>
		<c:set var="backUrl" value="/oauth/meal/dishesList.do?comid=${WEIXINFAN_LOGIN_COMPANYID}&dishno1=-1" scope="request"/>
	</c:otherwise>
</c:choose>
<jsp:include page="meal_header.jsp"/>

<form action="/toMealCashierDesk.do" id="js_submitForm" method="post" onsubmit="return checkInput()">
	<input type="hidden" value="${mealOrder.mealTab.deposit}" id="js_deposit"/>
	<div class="infowrite_listbox mrg-t10"><ul class="infowrite_list">
		<li><input type="text" class="inputinfofillstyle_B fr ct" <c:if test="${mealOrder.items.size()<=0 || mealOrder.items==null}">id="js-kalendae"</c:if> readonly="readonly" name="arrDate" value="<fmt:formatDate value="${mealOrder.arrDate}" pattern="yyyy-MM-dd"/>"/>
			<%-- <input type="hidden" id="js_arrAfterDate" value="<fmt:formatDate value="${arrAfterDate}" pattern="yyyy-MM-dd"/>"/> --%>
			<input type="hidden" id="js_startDate" value=""/>
    		<input type="hidden" id="js_endDate" value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>"/>
			<span class="clorshowH">用餐日期</span>
		</li>
    	<li><c:choose>
    		<c:when test="${mealOrder.items.size()<=0 || mealOrder.items==null}">
    			<select class="selectstylin fr js_shuffle" id="js_shuffle" name="shuffleNo" onchange="ajaxListArriveTime()">
					<%-- <c:forEach items="${shuffles}" var="shuffle">
						<option value="${shuffle.id}" data-start="${shuffle.startTime}" data-end="${shuffle.endTime}" <c:if test="${mealOrder.shuffleNo == shuffle.id}">selected="selected"</c:if>>${shuffle.shuffleName}</option>
					</c:forEach> --%>
				</select>
				<div id="js_shuffleOption" style="display: none;">
			  		<c:forEach items="${shuffles}" var="shuffle">
						<option value="${shuffle.id}" data-start="${shuffle.startTime}" data-end="${shuffle.endTime}" <c:if test="${mealOrder.shuffleNo == shuffle.id}">selected="selected"</c:if>>${shuffle.shuffleName}</option>
					</c:forEach>
			  	</div>
    		</c:when>
    		<c:otherwise>
    			<em class="cd_selshow fr ct">${mealOrder.shuffleName}</em>
	    		<input type="hidden" name="shuffleNo" class="js_shuffle" value="${mealOrder.shuffleNo}" data-start="${mealOrder.shuffle.startTime}" data-end="${mealOrder.shuffle.endTime}"/>
    		</c:otherwise>
    	</c:choose>
    		<span class="clorshowH">用餐时段</span>
    		<input type="hidden" name="shuffleName" id="js_shuffleName" value="${mealOrder.shuffleName}"/>
    	</li>
    	<li><!-- class="selectstylin fr" -->
			<select class="selectstylin fr" name="arriveTime" id="js_arriveTime"></select>
    		<span class="clorshowH">预抵时间</span>
    		<input type="hidden" id="js_arriveTimeValue" value="${mealOrder.arriveTime}"/>
    	</li>
    	<li><input name="guestNum" class="inputinfofillstyle_B fr ct" value="${mealOrder.guestNum}" type="text" placeholder="请输入用餐人数" />
    		<span class="clorshowH">用餐人数</span></li>
      	<li><input type="text" name="contactName" 
      		<c:if test="${empty contactName}">value="${mealOrder.contactName}"</c:if>
      		<c:if test="${!empty contactName}">value="${contactName}"</c:if>
      		placeholder="姓名" class="inputinfofillstyle_B fr ct"/><span class="clorshowH"><span class="jxd_required">*</span>预订人</span></li>
    	<li><input type="text" name="contactMobile" 
    		<c:if test="${empty contactMobile}">value="${mealOrder.contactMobile}"</c:if>
      		<c:if test="${!empty contactMobile}">value="${contactMobile}"</c:if>
    	placeholder="输入手机号码"  class="inputinfofillstyle_B fr ct"/><span class="clorshowH"><span class="jxd_required">*</span>联系方式</span></li>
	</ul></div>
	
	<div class="infowrite_listbox mrg-t5 jd_showMealTab">
  		<ul class="infowrite_list">
		    <li><a href="javascript:mealTabList('${WEIXINFAN_LOGIN_COMPANYID}','${mealOrder.restaurantId}')" class="fr">选择包房<i class=" more_icon_12"></i></a><span class="clorshowH"><i class="bfae_icon_20 mrg-r5"></i>包房</span></li>
		    <c:if test="${mealOrder.mealTabId != null}">
			    <li class="pd10">
	    			<p class="ordcdmxlist clorshowH"><span class="clorshowY fr ">￥${mealOrder.mealTab.deposit}</span><span class="name">${mealOrder.mealTab.tabName}</span></p>
	    			<input type="hidden" name="mealTabId" value="${mealOrder.mealTabId}">
	    			<input type="hidden" value="${mealOrder.mealTab.deposit}" id="js_deposit"/>
	    		</li>
		    </c:if>
  		</ul>
	</div>

	<c:if test="${mealOrder.items!=null && mealOrder.items.size()>0}">
		<div class="infowrite_listbox mrg-t5">
		  	<ul class="infowrite_list">
		    	<li><span class="clorshowH"><i class="shopda_icon_20 mrg-r5"></i>订餐明细</span></li>
	    		<li class="pd10">
	    			<c:forEach items="${mealOrder.items}" var="mealOrderItem" varStatus="vs">
			      		<p class="ordcdmxlist clorshowH"><span class="name">${mealOrderItem.name}</span><span class="nb">X${mealOrderItem.itemQuantity}</span><span class="price">￥${mealOrderItem.itemPrice}</span></p>
		    			<p class="js_items" data-index="${vs.index}"><input type="hidden" name="items[${vs.index}].name" value="${mealOrderItem.name}"/>
							<input type="hidden" name="items[${vs.index}].itemPrice" value="${mealOrderItem.itemPrice}"/>
							<input type="hidden" name="items[${vs.index}].itemQuantity" value="${mealOrderItem.itemQuantity}"/>
							<input type="hidden" name="items[${vs.index}].itemAmount" value="${mealOrderItem.itemAmount}" id="js_itemAmount${vs.index}"/>
							<input type="hidden" name="items[${vs.index}].itemCode" value="${mealOrderItem.itemCode}"/>
							<input type="hidden" name="items[${vs.index}].unit" value="${mealOrderItem.unit}"/>
							<input type="hidden" name="items[${vs.index}].dishesId" value="${mealOrderItem.dishesId}"/></p>
		    		</c:forEach>
		    		<c:if test="${mealOrder.serviceFee != null && mealOrder.serviceFee>0}">
			    		<p class="ordcdmxlist clorshowH"><span class="fr" style="color:#000;">￥${mealOrder.serviceFee}</span><span>服务费</span></p>
		    			<input type="hidden" name="serviceFee" id="js_serviceFee" value="${mealOrder.serviceFee}">
		    		</c:if>
		      		<p class="ordcdmxlist clorshowH"><span class="clorshowY fr" id="js_dishesTotalFee">￥0.00</span><span>小计</span></p>
		    	</li>
	  		</ul>
		</div>
	</c:if>
	<%--<li class="place">服务费<i class="color-gray fright">
			¥<span id="js_serviceFeeShow">${mealOrder.serviceFee}</span>
			<input type="hidden" name="serviceFee" id="js_serviceFee" value="${mealOrder.serviceFee}">
			<input type="hidden" id="js_serviceFeeRate" value="${mealOrder.restaurant.serviceFee}">
		</i></li>--%>
	
	<div class="infowrite_listbox mrg-t5">
	  	<ul class="infowrite_list">
	    	<li>
	    		<c:choose>
	    			<c:when test="${memberCoupons!=null && memberCoupons.size()>0}">
	    				<a id="js-selectCouponOpt" href="#" class="fr">有可用的优惠券<i class=" more_icon_12"></i></a>
			    		<span id="coupon">
							<input type="hidden" name="couponSeqid" value="${mealOrder.couponSeqid}">
							<input type="hidden" name="couponCode" value="${mealOrder.couponCode}">
							<input type="hidden" name="chargeamtmodel" value="${mealOrder.chargeamtmodel}">
							<input type="hidden" name="chargeamt" value="${mealOrder.chargeamt}">
						</span>
	    			</c:when>
	    			<c:otherwise>
	    				<a href="javascript:" class="fr">无可用的优惠券</a>
	    			</c:otherwise>
	    		</c:choose>
	    		<span class="clorshowH">优惠券</span>
	    	</li>
	    	<li id="js-showCouponOpt" <c:if test="${mealOrder.chargeamt == null}">style="display: none;"</c:if>>
	    		<span class="clorshowY fr">-￥${mealOrder.chargeamt}</span><span class="clorshowH">${mealOrder.chargeamt}元优惠券</span>
	    	</li>
	    	<li><input type="text" name="remark" placeholder="填写备注" class="inputinfofillstyle_B fr ct"/><span class="clorshowH">备注</span></li>
	  	</ul>
	</div>
	
	<div class="footerblcok">
		<div class="fr">
    		<button class="footer_spcartbut" onclick="submitForm()">提交</button>
  		</div>
  		<div class="total_j">
  			<span class="price clorshowY">￥<b id="js_totalFeeShow">${mealOrder.totalFee}</b></span>
  			<small class="clorshowH"></small>
  		</div>
	</div>
</form>

<!-- 优惠劵modal -->
<c:if test="${memberCoupons!=null&&memberCoupons.size()>0}">
	<div class="am-popup" id="js-coupon-popup">
		<div class="am-popup-inner">
			<div class="am-popup-hd">
				<h4 class="am-popup-title" style="font-size: 18px">选择优惠券</h4>
				<span data-am-modal-close class="am-close">&times;</span>
			</div>
			<div class="am-popup-bd">
				<div class="blockbg">
					<ul class="coupon_listsl">
						<c:forEach items="${memberCoupons}" var="coupon">
							<li class="block_pd" data-chargeamtmodel="${coupon.chargeamtmodel}" data-seqId="${coupon.seqid}" data-code="${coupon.code}"
								data-amount="<fmt:formatNumber value="${coupon.amount}" type="currency" pattern="0.00"/>">
								<a href="javascript:" class="js-select-btn">
									<span class="fr">未使用<i class="more_icon_12"></i></span>
									<span class="sum colorking">¥<b><fmt:formatNumber value="${coupon.amount}" type="currency" pattern="#######.##" /></b></span>
									<span class="date"><b>${coupon.ticketTypeCname}</b><br/>有效期至<fmt:formatDate value="${coupon.limitdate}" pattern="yyyy/MM/dd" /></span>
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
</c:if>

<!-- 弹出框modal -->
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
$(function() {
	updatePrice();
	showShuffle();
	
	if($("#js-kalendae").length > 0){
		var kal = new Kalendae.Input('js-kalendae', {
			//months:1,
			mode:'single',
			direction:'today-future',
			format: 'YYYY-MM-DD',
			selectEndDate:$("#js_endDate").val(),
			selectStartDate:$("#js_startDate").val()
		});
		
		kal.subscribe('change', function (date) {
			$("#js-kalendae").blur();
			kal.hide();
			
			showShuffle();
		});
	}
	
	
	//使用优惠劵
	$("#js-selectCouponOpt").click(function() {
		//$(this).find(".coupon-radio").attr("checked","checked");
		$("#js-coupon-popup").modal();
	});
	
	$(".js-select-btn").click(function() {
		var _this = $(this);
		var liObj = _this.closest("li");
		var amount = liObj.attr("data-amount");
		var seqId = liObj.attr("data-seqId");
		var code = liObj.attr("data-code");
		var chargeamtmodel = liObj.attr("data-chargeamtmodel");
		
		$("#coupon").find("input[name=chargeamt]").val(amount);
		$("#coupon").find("input[name=couponSeqid]").val(seqId);
		$("#coupon").find("input[name=couponCode]").val(code);
		$("#coupon").find("input[name=chargeamtmodel]").val(chargeamtmodel);
		
		$("#js-showCouponOpt").find(".clorshowY").text('-￥'+amount);
		$("#js-showCouponOpt").find(".clorshowH").text(amount+'元优惠券');
		$("#js-showCouponOpt").show();
		$(".total_j").find("small").text('(已优惠￥'+amount+')');
		
		$("#js-coupon-popup").modal('close');
		updatePrice();
	});
	
	$(".js_delete").click(function(){
		var tr = $(this).parent().parent("tr");
		tr.remove();
		updatePrice();
	});
});
	
//修改价格
function updatePrice(){
	var deposit = $("#js_deposit").val();
	var serviceFee = $("#js_serviceFee").val();//服务费
	var totalFee = 0.0;
	$(".js_items").each(function(){
		var index = $(this).attr("data-index");
		var itemAmount = parseFloat($("#js_itemAmount"+index).val());
		totalFee += itemAmount;
	});
	$("#js_dishesTotalFee").text('￥'+totalFee.toFixed(2));
	
	if(serviceFee != "" && serviceFee != undefined){
		totalFee += parseFloat(serviceFee);
	}
	
	if(deposit != ""){
		totalFee += parseFloat(deposit);
	}
	
	var chargeamt = $("#coupon").find("input[name='chargeamt']").val();
	if(chargeamt != "" && chargeamt != undefined){
		totalFee = totalFee - parseFloat(chargeamt);
	}
	$("#js_totalFeeShow").text(totalFee.toFixed(2));
}

function checkInput(){
	if($("#js_submitForm").attr("action") == "/toMealCashierDesk.do"){
		var contactName = $("input[name='contactName']").val();
		if(contactName.length==0){
			showMessage("请输入预订人！！！");
			return false;
		}
		
		var contactMobile = $("input[name='contactMobile']").val();
		if(contactMobile.length==0){
			showMessage("请输入联系人手机号！！！");
			return false;
		}
		if(contactMobile.length != 11){
			showMessage("请输入正确的手机号！！！");
			return false;
		}
		
		return bo;
	}
	return true;
}

function mealTabList(comid, restaurantId){
	$("#js_submitForm").attr("action","/oauth/meal/mealTabList.do?comid="+comid+"&restaurantId="+restaurantId);
	$("#js_submitForm").submit();
	//document.location="/oauth/meal/mealTabList.do?comid="+comid+"&restaurantId="+restaurantId;
}

function submitForm(){
	$("#js_submitForm").attr("action","/toMealCashierDesk.do");
	$("#js_submitForm").submit();
}

function showMessage(message) {
	$("#alertMsg").html(message);
	$("#alertTip").modal();
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

function ajaxListArriveTime(){
	var _select = $(".js_shuffle");
	var shuffleName = _select.find("option:selected").text();
	if(shuffleName.length > 0){
		$("#js_shuffleName").val(shuffleName);
		_select = _select.find("option:selected");
	}
	var arriveTime = $("#js_arriveTimeValue").val();
	
	var startTime = _select.attr("data-start");
	var start = parseInt(startTime.substring(0,2));
	
	var nowFormat = (new Date()).Format("yyyy-MM-dd");
	var arrDate = $("input[name=arrDate]").val();
	if(nowFormat == arrDate){
		var nowTime = (new Date()).Format("hh:mm");
		var now = parseInt(nowTime.replace(":",""));
		var startTime1 = parseInt(_select.attr("data-start").replace(":",""));
		if(now > startTime1){
			startTime = nowTime;
			start = parseInt(nowTime.substring(0,2));
		}
	}
	
	
	var endTime = _select.attr("data-end");
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

function showShuffle(){
	if($("#js_shuffle").length>0){
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
	ajaxListArriveTime();
}
//方法 增添dayNumber天（整形），date：如果没传就使用今天（日期型）
function addDay(dayNumber) {
    var date = new Date();
    var ms = dayNumber * (1000 * 60 * 60 * 24)
    var newDate = new Date(date.getTime() + ms);
    return newDate;
}
</script>
</body>
</html>
