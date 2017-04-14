<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${marketingSignIn.name}</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"   content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link rel="stylesheet" type="text/css" href="/static/common/css/signInDate.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/common/js/kalendae/kalendae.css">
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common${THEME}.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain${THEME}.css?v=${version}">
<style type="text/css">

</style>
</head>
<body>
<form action="/oauth/member/toMarketingSignIn.do?comid=${marketingSignIn.companyId}&signInId=${marketingSignIn.id}" method="post" id="formSubmit"></form>
<input id="js_shareIconUrl" type="hidden" value="${marketingSignIn.shareIconUrl}"/>
<input id="js_shareTitle" type="hidden" value="${marketingSignIn.shareTitle}"/>
<input id="js_shareDescription" type="hidden" value="${marketingSignIn.shareDescription}"/>
<input id="js_signInId" type="hidden" value="${marketingSignIn.id}"/>
<input id="js_isActivityTime" type="hidden" value="${marketingSignIn.isActivityTime}"/>
<div class="signInAll">
	<div class="inCircles">
		<img id="js_clockwise" class="q1" src="/static/front/images/q1.png">
		<img id="js_anticlockwise" class="q2" src="/static/front/images/q2.png">
		<img id="js_clockwise" class="q3" src="/static/front/images/q3.png">
		<div class="signedIn" <c:if test="${signInRecord == null}">style="display: none;"</c:if>>
			<span class="point">
				<span style="font-size: 26px;margin-left: 8px;" id="js_validScore">
					<fmt:formatNumber value="${memberVO.validScore==null?0:memberVO.validScore}" type="currency" pattern="#"/>
				</span><br/>积分</span>
		</div>
		<div class="notSignIn" <c:if test="${signInRecord != null}">style="display: none;"</c:if>>
			<a href="javascript:<c:if test="${marketingSignIn.isActivityTime==true}">ajax</c:if>signIn();" style="color: #fff;">签到</a>
		</div>
	</div>
	<div class="continuous">最近连续签到<span id="js_signInNum">${member.signInNum==null?0:member.signInNum}</span>天</div>
</div>

<div class="signInDetails">
	<p class="signInExplain" onclick="alertSignInExplain()">签到说明>></p>
	<div class="pointsFor">
		<%-- <c:set var="pointUrl" value="/oauth/member/toCreditExchange.do?comid=${WEIXINFAN_LOGIN_COMPANYID}"/> --%>
		<a href="${(marketingSignIn.pointsForUrl==''||marketingSignIn.pointsForUrl==null)?'javascript:':marketingSignIn.pointsForUrl}">
			<img src="${(marketingSignIn.pointsForImgUrl==''||marketingSignIn.pointsForImgUrl==null)?'/static/front/images/jfdh.png':marketingSignIn.pointsForImgUrl}" height="93px" style="margin-top: 6px;border-radius: 5px;" width="100%">
		</a>
	</div>
	<div class="addUpSignIn">
		<b style="font-size: 16px"><span style="font-size: 30px" id="js_allDay">0</span>天</b><br/>累计共签到
	</div>
</div>

<div class="signInDate" id="js_signInDate"></div>

<div class="am-modal am-modal-no-btn" tabindex="-1" id="alertSign">
  <div class="am-modal-dialog">
    <div class="am-modal-hd" style="font-size: 20px;font-weight: bold;">签到说明
      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
      ${marketingSignIn.remark}
    </div>
  </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="my-alert">
  <div class="am-modal-dialog">
    <div class="am-modal-bd" id="textContent">不在签到活动时间内！</div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>确定</span>
    </div>
  </div>
</div>

<div class="am-modal am-modal-confirm" tabindex="-1" id="alertTip">
  <div class="am-modal-dialog">
    <div class="am-modal-bd" id="alertMsg">签到成功</div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
    </div>
  </div>
</div>

<!-- <div id="rect">
    <div id="dire"></div>
    <div id="start">ss</div>
    <div id="move">ddd</div>
</div> -->

<script src="/static/common/js/jquery-1.11.2.js"></script>
<script src="/static/common/js/signInDate.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script src="/static/common/js/jQueryRotate.2.2.js"></script>
<script src="/static/common/js/kalendae/kalendae.standalone.js"></script>
<script src="/static/common/js/dateUtil.js?v=${version}"></script>
<script type="text/javascript" src="/static/common/js/wx-share.js?v=${version}"></script>
<script type="text/javascript">
$(function() {
	var signInId = $("#js_signInId").val();
	$.ajax({
		url:"/oauth/member/ajaxFindSignInRecords.do",
		data:{signInId:signInId},
		dataType:"json",
		success:function(data) {
			//var data = [{"createDate": "2016-09-19","openid": "158"},{"createDate": "2016-09-20","openid": "158"}];
			$("#js_allDay").text(data.length);
			pickerEvent.setPriceArr(data);
			pickerEvent.callback(_this,callback,new Date());
			pickerEvent.Init("#js_signInDate");
		}
	});
	
	var isActivityTime = $("#js_isActivityTime").val();
	if(isActivityTime != "true"){
		signIn();
	}
	
	slidingAround();
	
	var wxShareDate = {
			"title" : $("#js_shareTitle").val(),
			"content" : $("#js_shareDescription").val(),
			"imgUrl" : $("#js_shareIconUrl").val(),
			"shareUrl" : window.location.href
		};
	//微信新版分享
	WXJSSDK.load({
		ready: function(){
			WXAPI.shares({
			    title: wxShareDate.title, // 分享标题
			    desc: wxShareDate.content, // 分享描述
			    link: wxShareDate.shareUrl, // 其他分享链接
			    imgUrl: wxShareDate.imgUrl, // 分享图标
			    success: function(res){
			    }
			});
		}
	});
});

function alertSignInExplain(){
	$("#alertSign").modal();
}

function signIn(){
	$("#textContent").text("不在签到活动时间内！");
	$("#my-alert").modal();
}

var qqNum = 1;
var interval;
var isClick = true;
function ajaxsignIn(){
	if(isClick){
		isClick = false;
		$("#js_clockwise").fadeIn();
		$("#js_anticlockwise").fadeIn();
		qqNum = 1;
		interval = setInterval("inCircles()",5);
		var id = $("#js_signInId").val();
		$.ajax({
			url:"/oauth/member/ajaxSignIn.do",
			data:{id:id},
			dataType:"json",
			success:function(data) {
				$("#alertMsg").text(data[1]);
				$('#alertTip').modal({
			        relatedTarget: this,
			        closeViaDimmer: 0,
			        onConfirm: function(options) {
			        	if(data[0] == "true"){//签到成功
			        		/* $(".notSignIn").css("display","none");
			        		$(".signedIn").css("display","block");
			        		var validScore = parseInt($("#js_validScore").text())+parseInt(data[3]);
			        		$("#js_validScore").text(validScore);
			        		var allDay = parseInt($("#js_allDay").text())+1;
			        		$("#js_allDay").text(allDay);
			        		$("#js_signInNum").text(data[2]);
			        		$(".today").find(".today_span").addClass("signIn").removeClass("today_span");
			        		$(".today").find(".team").html('<img src="/static/front/images/jy.png">'); */
			        		$("#formSubmit").submit();
			        	}else if(data[0] == "refresh"){
			        		$("#formSubmit").submit();
			        	}
			        	$("#js_clockwise").fadeOut();
			    		$("#js_anticlockwise").fadeOut();
			    		window.clearInterval(interval);
			        }
			    });
			}
		});
	}
}
//var interval = setInterval("inCircles(qqNum)",300);
function inCircles(){
	$("#js_clockwise").rotate(qqNum);
	$("#js_anticlockwise").rotate(-qqNum);
	qqNum++;
}


function slidingAround(){
	var LSwiperMaker = function(o){ 
		 
        var that = this;
        this.config = o;
        this.control = false;
        this.sPos = {};
        this.mPos = {};
        this.dire;
 
        // this.config.bind.addEventListener('touchstart', function(){ return that.start(); } ,false);
        // 这样不对的，event对象只在事件发生的过程中才有效;
        this.config.bind.addEventListener('touchstart', function(e){ return that.start(e); } ,false);
        this.config.bind.addEventListener('touchmove', function(e){ return that.move(e); } ,false);
        this.config.bind.addEventListener('touchend', function(e){ return that.end(e); } ,false);

    }

    LSwiperMaker.prototype.start = function(e){
         
         var point = e.touches ? e.touches[0] : e;
         this.sPos.x = point.screenX;
         this.sPos.y = point.screenY;
         //document.getElementById("start").innerHTML = "开始位置是:"+this.sPos.x +" "+ this.sPos.y ;

    }
    LSwiperMaker.prototype.move = function(e){  

        var point = e.touches ? e.touches[0] : e;
        this.control = true;
        this.mPos.x = point.screenX;
        this.mPos.y = point.screenY;
        //document.getElementById("move").innerHTML = "您的位置是："+this.mPos.x +" "+ this.mPos.y ;

    }

    LSwiperMaker.prototype.end = function(e){
    	this.dire = null;
		if(this.config.dire_h && this.control){
			var posCha = this.mPos.x-this.sPos.x;
			if(posCha<-40){
				this.dire='左';
			}else if(posCha>40){
				this.dire='右';
			}
    	}
        //this.config.dire_h  && (!this.control?this.dire=null:this.mPos.x>this.sPos.x?this.dire='右':this.dire='左')
        //this.config.dire_h  || (!this.control ? this.dire = null : this.mPos.y > this.sPos.y ? this.dire = '下' : this.dire = '上')

        this.control = false;
        this.config.backfn(this);

    }

    window.LSwiperMaker = LSwiperMaker;
    //document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);// 禁止微信touchmove冲突
    
	var a = new LSwiperMaker({
	    bind:document.getElementById("js_signInDate"),  // 绑定的DOM对象
	    dire_h:true,     //true 判断左右， false 判断上下
	    backfn:function(o){    //回调事件
	    	if(o.dire == '右'){
	    		pickerEvent.getLast();
	    	}else if(o.dire == '左'){
	    		pickerEvent.getNext();
	    	}
	        //document.getElementById("dire").innerHTML = "向"+ o.dire + "滑";  
	    }
	});
}
</script>
</body>
</html>