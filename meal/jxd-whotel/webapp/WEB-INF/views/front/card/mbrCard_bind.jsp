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
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common${THEME}.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain${THEME}.css?v=${version}">
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
</head>
<body>
<c:set var="headerTitle" value="绑定会员卡" scope="request"/>
<jsp:include page="card_header.jsp" />

<form action="" method="post" id="">

<div class="belinkedbox">
	<ul class="belinkedform  pd20">
		<li class="hm clearfix">
			<input type="text" placeholder="输入会员卡号" value="" class="mbrCardBind" id="mbrCardNo"/>
		</li>
	</ul>
	<div class="pd20">
		<input type="button" value="绑定" class="butstyleB" id="js_bindMbrCard"/>
	</div>
</div>
</form>

<div class="am-modal am-modal-alert" tabindex="-1" id="alertTip">
	<div class="am-modal-dialog">
		<div class="am-modal-bd" id="alertMsg"></div>
		<div class="am-modal-footer">
			<span class="am-modal-btn">确定</span>
		</div>
	</div>
</div>
<div class="loading" id="loading"></div>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
$(function() {
	$("#js_bindMbrCard").click(function() {
		var mbrCardNo = $("#mbrCardNo").val();
		if($.trim(mbrCardNo) == "") {
			$("#alertMsg").html("请输卡号");
			$("#alertTip").modal();
		}else{
			var $loading = $("#loading");
			$loading.show();
			$.ajax({
				type:"POST",
				url:"/oauth/member/ajaxMbrCardBinding.do",
				data:{"mbrCardNo": mbrCardNo},
				beforeSend: function(){
				},
				success: function(data){
					$loading.hide();
					if(data=="success"){
						window.location.href="/oauth/member/listMbrCard.do";
					}else{
						$("#alertMsg").html(data);
						$("#alertTip").modal();
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					$loading.hide();
					$("#alertMsg").html("绑定失败！");
					$("#alertTip").modal();
		        }
			});
		}
	});
});
</script>
</body>
</html>