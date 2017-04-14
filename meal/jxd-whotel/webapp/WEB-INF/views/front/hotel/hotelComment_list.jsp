<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${WEIXINFAN_LOGIN_COMPANYNAME}</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common${THEME}.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain${THEME}.css?v=${version}">
<style type="text/css">
.myorderlist li {
    padding: 10px 15px;
    border-bottom: 3px solid #ccc;
}
.commentStar{width: 14px;height: 12px;}
.js_thumbUp{border-top: 1px solid #E6E6E6;text-align: right;color: #7C7C7C;font-size: 12px;padding-top: 5px;}
</style>
</head>
<body>
<c:set var="headerTitle" value="酒店点评" scope="request"/>
<jsp:include page="hotel_header.jsp" />
<div style="height: 200px;background: url(${commentConfig.backgroundImg!=null?commentConfig.backgroundImgUrl:'/static/front/hotel/images/bj.png'}) no-repeat;background-size: 100% 100%;">
	<div style="padding-top: 50px;">
		<div style="background: #fff;width: 85px;border-radius: 14px;margin: 0 auto;">
			<img src="${company.logoUrl}" width="85px" height="85px" style="border-radius: 14px;">
		</div>
		<p style="color: #fff;text-align: center;margin-top: 5px;font-size: 15px;">${company.name}</p>
	</div>
</div>

<div class="blockbg">
  <ul class="myorderlist">
    <c:if test="${hotelComments == null || hotelComments.size() == 0}">
    	<p style="color:red; text-align:center; padding:10px 10px;">此酒店还没有任何点评！</p>
	</c:if>
    <c:forEach items="${hotelComments}" var="hotelComment">
	    <li id="js_${hotelComment.id}"><%-- <a href="/oauth/hotelMsg/toHotelComment.do?comid=${WEIXINFAN_LOGIN_COMPANYID}&hotelCode=${hotelCode}&replyId=${hotelComment.id}"> --%>
		      <%-- <img src="${hotelComment.weixinFan.avatar}" width="50px" style="border-radius: 8px;">
		      	<b class="pe colorking">${hotelComment.weixinFan.nickName}</b> --%>
		      <div style="margin-bottom: 5px;">
		      	<div style="height: 50px;float: left;"><img src="${hotelComment.weixinFan.avatar}" width="50px" style="border-radius: 8px;"></div>
  				<div style="height: 50px;float: left;font-size: 14px;margin-left: 10px;">
  					<p>${hotelComment.weixinFan.nickName}</p>
  					<p><c:forEach begin="1" end="5" varStatus="vs">
  						<img src="/static/front/hotel/images/xin<c:if test="${hotelComment.commentStar<vs.index}">-1</c:if>.png" class="commentStar" data-value="${vs.index}">
  					</c:forEach></p>
  				</div>
  				<div style="height: 50px;float: right;font-size: 12px;">
  					<p style="background: #EF870A;color: #f5f5f5;padding: 1px 9px;border-radius: 5px;">${hotelComment.buyName}</p>
  					<p style="text-align: right;color: #8C8E90;margin-top: 5px;"><fmt:formatDate value="${hotelComment.createTime}" pattern="yyyy-MM-dd"/></p>
  				</div>
  			  </div>
		      <div style="clear:both;padding-top: 10px;">
		      	 <%-- <p style="line-height: 30px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;width: 74%;font-weight: bold;">${hotelComment.content}</p> --%>
				 <!-- <div style="position: relative;"> -->
					 <p style="overflow: hidden;font-size: 14px;">
					 	&nbsp;&nbsp;&nbsp;&nbsp;${hotelComment.content}
					 </p>
					 <!-- <div style="position: absolute;right: 0px;top: -5px;"><img src="/static/front/hotel/images/zk.png" width="15px" height="15px"/></div>
				 </div> -->
				 <p style="margin: 10px 0px;"><c:forEach items="${hotelComment.imagesUrls}" var="imagesUrl" varStatus="vs">
					<img src="${imagesUrl}" width="80" height="65"/><i></i>
				 </c:forEach></p>
				 <p class="js_thumbUp"><a style="color: #7C7C7C;" href="javascript:<c:if test="${recordsMap[hotelComment.id]==null}">ajaxThumbUp('${hotelComment.id}')</c:if>">
				 	<img src="/static/front/hotel/images/zan<c:if test="${recordsMap[hotelComment.id]!=null}">-1</c:if>.png" style="width: 14px;height: 12px;">&nbsp;点赞&nbsp;
				 	<span>${hotelComment.thumbUpCount==null?0:hotelComment.thumbUpCount}</span>
				 </a></p>
		      </div>
	    </li>
    </c:forEach>
  </ul>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="js_alert">
  <div class="am-modal-dialog">
    <div class="am-modal-hd"></div>
    <div class="am-modal-bd">
    	<span id="js_message">${message}</span>
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>确定</span>
    </div>
  </div>
</div>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
	$(function(){
		var msg = $("#js_message").text();
		if(msg.length > 0){
			$("#js_alert").modal();
		}
		
		/* $(".js_showReply").click(function(){
			var _div = $(this).prev();
			if(_div.is(':hidden')){//如果_div是隐藏的则显示node元素，否则隐藏
				_div.show();
				$(this).text("收起回复");
			}else{
				_div.hide();
				$(this).text("查看回复");
			}
		}); */
	});
	
	function ajaxThumbUp(id){
		var _this = $("#js_"+id).find(".js_thumbUp");
		$.ajax({
			url:"/oauth/hotelMsg/ajaxThumbUp.do",
			type : 'post',
			async: false,
			data:{"id":id},
			success:function(data) {
				if(data>0){
					_this.find("img").attr("src","/static/front/hotel/images/zan-1.png");
					_this.find("span").text(data);
					_this.find("a").attr("href","javascript:");
				}
			}
		});
	}
</script>
</body>
</html>
