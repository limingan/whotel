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
</head>
<body>
<c:set var="headerTitle" value="联系人资料" scope="request"/>
<jsp:include page="card_header.jsp" />

<form action="updateGuest.do" method="post" id="guestForm">
<input type="hidden" name="id" value="${guest.id}">
<div class="plinfobox">
<ul class="listinfofill blockbg">
<li><p class="fr wd50"><input name="name" type="text" class="inputinfofillstyle_A required" placeholder="姓名" value="${guest.name}"/></p>姓名</li>
<li>
<p class="fr wd50">
<select name="gender" class="selectstyleA">
<option value="MALE" <c:if test="${guest.gender == 'MALE'}">selected</c:if>>男</option>
<option value="FEMALE" <c:if test="${guest.gender == 'FEMALE'}">selected</c:if>>女</option>
</select>
</p>
性别
</li>
<li><p class="fr wd50"><input name="mobile" type="tel" class="inputinfofillstyle_A required" placeholder="手机号码" value="${guest.mobile}"/></p>手机</li>
</ul>
</div>
</form>
<div class="pd20">
	<input type="button" value="保存资料" class="butstyleB js-bindMemberBtn" onclick="updateGuest();"/>
	<c:if test="${guest.id != null && guest.id != ''}">
	<input type="button" value="删除资料" class="butstyleC js-bindMemberBtn mrgtd10" onclick="deleteGuest('${guest.id}');"/>
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

<div class="am-modal am-modal-confirm" tabindex="-1" id="confirmTip">
  <div class="am-modal-dialog">
    <div class="am-modal-bd">
      你，确定要删除吗？
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
    </div>
  </div>
</div>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">

function checkGuest() {
	var requiredObj = $(".required");
	var rs = true;
	requiredObj.each(function() {
		var _this = $(this);
		var inputValue = _this.val();
		if(inputValue == "") {
			$("#alertMsg").html(_this.attr("placeholder")+"必须填写");
			$("#alertTip").modal();
			rs = false;
			return rs;
		}
	});
	
	return rs;
}

function updateGuest() {
	if(checkGuest()) {
		$("#guestForm").submit();
	}
}
function deleteGuest(id) {
	$('#confirmTip').modal({
        relatedTarget: this,
        onConfirm: function(options) {
        	var url = "/oauth/member/deleteGuest.do?id="+id;
        	document.location=url;
        },
        // closeOnConfirm: false,
        onCancel: function() {
         
        }
   });
}
</script>

</body>
</html>