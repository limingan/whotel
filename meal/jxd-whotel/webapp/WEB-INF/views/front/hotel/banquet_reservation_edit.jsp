<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${WEIXINFAN_LOGIN_COMPANYNAME}</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/common/js/kalendae/kalendae.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common${THEME}.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain${THEME}.css?v=${version}">
</head>

<body>
<c:set var="headerTitle" value="宴会预订" scope="request"/>
<jsp:include page="hotel_header.jsp" />

<form id="orderForm">
	<div class="listinfofillbox blockbg">
		<ul class="listinfofill">
			<li>预订日期<div class="inputfill fr"><input name="arriveDate" placeholder="预订日期" class="inputinfofillstyle_A" id="js-kalendae" readonly="readonly" /></div></li>
			<li>预订人<div class="inputfill fr"><input name="guestName" id="js-guestName" placeholder="姓名" class="inputinfofillstyle_A" /></div></li>
			<li>手机<div class="inputfill fr"><input name="contactMobile" id="js-contactMobile" placeholder="手机号" class="inputinfofillstyle_A" /></div></li>
			<li>固定电话<div class="inputfill fr"><input name="contactTel" placeholder="0755-88888888" class="inputinfofillstyle_A" /></div></li>
			<li>邮箱<div class="inputfill fr"><input name="contactEmail" type="email" placeholder="XX@X.com" class="inputinfofillstyle_A" /></div></li>
		</ul>
	</div>
</form>

<div class="block_pd mrgtd10">其它要求<textarea name="guestDemand" cols="" rows="" class="wd100" placeholder="请这里输入特别要求和说明"></textarea></div>
<div class="pd20"><button class="butstyleB" onclick="return submit()">提交</button></div>

<div class="am-modal am-modal-alert" tabindex="-1" id="alertMsg">
	<div class="am-modal-dialog">
		<div class="am-modal-bd" id="msg"></div>
		<div class="am-modal-footer">
			<span class="am-modal-btn">确定</span>
		</div>
	</div>
</div>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script src="/static/common/js/kalendae/kalendae.standalone.js?v=${version}"></script>
<script type="text/javascript">
$(function() {
	var kal = new Kalendae.Input('js-kalendae', {
		months:1,
		direction:'future',
		format: 'YYYY-MM-DD'
	});
})
function submit() {
	if(checkInfo()) {
		$.ajax({
    		url:"/oauth/hotel/saveBanquetReservation.do",
    		data:$('#orderForm').serialize(),
    		async:false,
    		type: "POST",
    		success:function(data) {
    			$("#msg").html(data==true?"保存成功":"保存失败");
    			$("#alertMsg").modal();
    		}
    	});
		return true;
	} else {
		return false;
	}
}
function checkInfo(){
	var isOk = true;
	var msg = "";
	if($("#js-kalendae").val()==""){
		isOk=false;
		msg = "请选择日期";
	}else if($("#js-guestName").val()==""){
		isOk=false;
		msg = "请输入姓名";
	}else if($("#js-contactMobile").val()==""){
		isOk=false;
		msg = "请输入手机";
	}
	if(isOk==false){
		$("#msg").html(msg);
		$("#alertMsg").modal();
	}
	return isOk;
}

</script>
</body>
</html>
