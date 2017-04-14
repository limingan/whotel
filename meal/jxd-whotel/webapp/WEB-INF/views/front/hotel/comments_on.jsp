<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html <c:if test="${voteActivity.listBgUrl != null}">style="background:url(${voteActivity.listBgUrl}?imageView2/2/w/640);background-attachment:fixed;
    background-size:100% 100%;"</c:if>>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>酒店点评</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"   content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link rel="stylesheet" type="text/css" href="/static/front/css/zj/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common${THEME}.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain${THEME}.css">
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
<link rel="stylesheet" href="/static/common/css/upload.css"/>
<style>
.voetinae {margin-bottom:30px}
.voetinae [type*="text"],.voetinae textarea{-webkit-appearance: none!important;
	-moz-appearance: none!important;
	-webkit-border-radius: 0;}
.voetinae li {
	font-size: 14px;
	margin: 10px 0
}
.voetinae .wby {
	padding: 10px ;text-indent: 0.5em; border:1px solid #CCC;border-radius: 6px;
}
.voetinae .inputstyle_A, .voetinae .wby {
	width: 100%
}
.voetinae .inputupimgbut {
	display: block; 
	float:right;
	width: 100px;
	height: 40px;
	background: #26b3ee;
	position: relative;
	overflow: hidden;
	line-height: 40px;
	text-align: center;
	color: #fff;
	font-size: 12px;
	border-radius: 2px; 
}
.voetinae li:after{ content:""; clear:both; display:table}
.voetinae .inputupimgbut:hover {
	background:#2DCEFD;
}
.voetinae .inputupimgbut input {
    width: 100px;
    height: 40px;
	position: absolute;
	right: 0;
	top: 0;
	font-size: 10px;
	opacity: 0;
	filter: alpha(opacity=0);
}
.js_commentStar{width: 18px;height: 16px;}
.js_commentStar-1{width: 18px;height: 16px;}
/* .voetinae .picsl { float:left ; line-height:40px;} */
.voetinae .picsl {float: left;border:2px dashed #dbdbdb; color:#dbdbdb; font-size:36px; padding:2px 22px;}




.voetinae .picsl img{ height:40px; width:auto;}

.careful-f{ font-size:0.875em; color:#666; padding:40px 0 10px; }
.careful{font-size:0.875em;color:#666;}
</style>
</head>

<body>
<header class="header">
  <div class="header_left"> <a href="javascript:history.go(-1);" class="header_icon_back"></a></div>
  <h1 class="header_title">我的评论</h1>
</header>
<div class="voetinaebox pd10">
	<form action="/oauth/hotelMsg/saveHotelComment.do?comid=${WEIXINFAN_LOGIN_COMPANYID}" id="submitForm" method="post">
		<input type="hidden" name="id" value="${hotelComment.id}"/>
		<input type="hidden" name="replyId" value="${replyId}">
		<input type="hidden" name="hotelCode" value="${hotelOrder.hotelCode}"/>
		<input type="hidden" name="buyName" value="${hotelOrder.name}"/>
		<input type="hidden" name="orderSn" value="${hotelOrder.orderSn}"/>
		<c:set var="commentStarNum" value="${hotelComment.commentStar==null?0:hotelComment.commentStar}"/>
		<input type="hidden" name="commentStar" value="${commentStarNum}" id="js_commentStar"/>
  		<ul class="voetinae">
  			<li>
  				<div style="height: 70px;float: left;"><img src="${weixinFan.avatar}" width="70px" style="border-radius: 10px;"></div>
  				<div style="height: 70px;float: left;font-size: 16px;margin: 10px 0px 0px 10px;">
  					<p>${weixinFan.nickName}</p>
  					<p><c:forEach begin="1" end="5" varStatus="vs">
  						<img src="/static/front/hotel/images/xin<c:if test="${commentStarNum<vs.index}">-1</c:if>.png" class="js_commentStar<c:if test="${hotelComment.checkStatus == true}">-1</c:if>" data-value="${vs.index}">
  					</c:forEach></p>
  				</div>
  				<div style="height: 70px;float: right;font-size: 12px;margin-top:15px;">
  					<p style="background: #EF870A;color: #f5f5f5;padding: 1px 9px;border-radius: 5px;">${hotelOrder.name}</p>
  					<p style="text-align: right;color: #8C8E90;margin-top: 5px;"><fmt:formatDate value="${hotelOrder.createTime}" pattern="yyyy-MM-dd"/></p>
  				</div>
  			</li>
    		<li><textarea id="js_content" name="content" cols="" rows="7" placeholder="点评内容" data-pla="点评内容" class="wby js_text">${hotelComment.content}</textarea></li>
    		<li>
    			<c:if test="${hotelComment.checkStatus != true}"><p class="inputupimgbut"><input type="file" capture="camera" accept="image/*" id="weixinpic">上传图片</p></c:if>
		    	<div id="bannerPreview">
		    		<c:if test="${fn:length(hotelComment.imagesUrls) > 0}">
						<c:forEach items="${hotelComment.imagesUrls}" var="imagesUrl" varStatus="vs">
							<div style="padding: 5px;display: inline-block;" <c:if test="${hotelComment.checkStatus != true}">class="del-icon"</c:if>>
								<input type="hidden" name="images" value="${hotelComment.imagesUrls[vs.index]}">
								<img src="${imagesUrl}" width="70px" height="60px" /><i></i>
							</div>
						</c:forEach>
					</c:if>
		    	</div>
		    </li> 
  		</ul>
  	</form>
  	<a href="/oauth/hotelMsg/listHotelComment.do?hotelCode=${hotelOrder.hotelCode}&comid=${WEIXINFAN_LOGIN_COMPANYID}">
  		<button class="butstyleC">查看评论</button>
  	</a>
  	<p>&nbsp;</p>
  	<c:if test="${hotelOrder.orderSn!=null}">
	  	<c:if test="${hotelComment.checkStatus == true}">
	  		<p class="careful-f">您的评论已通过审核！</p>
	  	</c:if>
	  	<c:if test="${hotelComment.checkStatus == false}">
	  		<div><button class="butstyleB">提交评论</button></div>
	  		<p class="careful-f">您的评论未通过审核，请重新填写，谢谢！</p>
	  	</c:if>
	    <c:if test="${hotelComment.checkStatus == null }">
	    	<div><button class="butstyleB">提交评论</button></div>
	    	<p class="careful-f">* 编辑评论提交后，将提交后台审核，审核通过后不能更改评论，请谨慎操作，谢谢！</p>
	    	<p class="careful">* 您的评论处于待审核状态，审核通过后方可在首页中展示，请耐心等待！</p>
	  	</c:if>
  	</c:if>
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

<script src="/static/common/js/jquery-1.11.2.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<jsp:include page="/common/qiniu_upload.jsp" />
<script type="text/javascript">
	var $loading = $("#loading");
	$(function(){
		
		$(".js_text").focus(function(){
			$(this).attr("placeholder","");
		});
		$(".js_text").blur(function(){
			$(this).attr("placeholder",$(this).attr("data-pla"));
		});
		
		$("body").on("click","#weixinpic",function(){
			var _this = $(this);
			setUploadToken();
			$loading.show();
			uploadFile(_this.get(), null, uploadCallback);
		});
		
		$("body").on("click",".butstyleB",function(){
			/*校验基本信息是否为空*/
			var content = $("#js_content");
			if(content==null||content.val()==""){
				showMessage("请输入点评内容！");
				content.focus();
				return false;
			}
			$("#submitForm").submit();
		});
		
		$(".js_commentStar").click(function(){
			var index = parseInt($(this).attr("data-value"));
			$(".js_commentStar").attr("src","/static/front/hotel/images/xin-1.png");
			$(".js_commentStar").each(function(i){
				if(i<index){
					$(this).attr("src","/static/front/hotel/images/xin.png");
				}
			});
			$("#js_commentStar").val(index);
		});
	});
	
	function uploadCallback(file){
		var html = "<span class='del-icon'><img src='"+$("#res_url").val()+"?imageView2/2/w/90/h/90' width='100' height='90'/><i></i>"
		+ "<input name='images' type='hidden' value='"+$("#res_key").val()+"'></span>";
		$("#bannerPreview").append(html);
		$loading.hide();
	}
	
	function showMessage(message) {
		$("#alertMsg").html(message);
		$("#alertTip").modal();
	}
</script>
</body>
</html>