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
<c:set var="headerTitle" value="会员信息" scope="request"/>
<jsp:include page="card_header.jsp" />

<div class="memberID"><i class="bg40_icon_card"></i><p class="hf">会员号<b>${memberVO.mbrCardNo}</b></p></div>
<div class="member_djbox">
<ul class="listinfofill">
<li><a href="/oauth/member/listMbrCardUpgrade.do" class="block"><p class="fr"><i class="sm20_icon_ph "></i><i class="more_icon_12" ></i>${memberVO.mbrCardTypeName}</p>会员等级</a></li>
<li><p class="fr" style="color: #999;">${memberVO.mbrExpired}</p>会员卡有效期</li>
</ul>
</div>

<form action="bindMember.do" method="post" id="bindForm">
<input type="hidden" name="id" value="${member.id}">
<input type="hidden" name="action" value="save">
<div class="plinfobox mrg-t10">
<ul class="listinfofill blockbg">
<c:if test="${memberPolicy.score>0 && (member.memberPolicyScore==null || member.memberPolicyScore==0)}"><li>完善个人信息赠送${memberPolicy.score}积分</li></c:if>
<li><p class="fr wd50"><input name="name" type="text" class="inputinfofillstyle_A required" placeholder="姓名" value="${member.name}"/></p><i class="sm20_icon_tx"></i>姓名</li>
<li>
<p class="fr wd50">
<select name="gender" class="selectstyleA">
<option value="MALE" <c:if test="${member.gender == 'MALE'}">selected</c:if>>男</option>
<option value="FEMALE" <c:if test="${member.gender == 'FEMALE'}">selected</c:if>>女</option>
</select>
</p>
<i class="sm20_icon_nv"></i>性别
</li>
<li><p class="fr wd50"><input name="mobile" type="tel" class="inputinfofillstyle_A required" placeholder="手机号码" value="${member.mobile}" readonly="readonly"/></p><i class="sm20_icon_mb"></i>手机</li>
<li><p class="fr wd50"><input name="IDCard" id="IDCard" type="text" class="inputinfofillstyle_A" placeholder="身份证" value="${member.IDCard}"/></p><i class="sm20_icon_idcard"></i>身份证</li>
<li><p class="fr wd50"><input name="email" type="text" class="inputinfofillstyle_A" placeholder="邮箱" value="${member.email}"/></p><i class="sm20_icon_el"></i>邮箱</li>
<li><p class="fr wd50"><input name="addr" type="text" class="inputinfofillstyle_A" placeholder="地址" value="${member.addr}"/></p><i class="sm20_icon_ad"></i>地址</li>
<li><p class="fr wd50">
	<input name="isSendMsg" type="radio" class="cb" value="true" <c:if test="${member.isSendMsg==null || member.isSendMsg==true}">checked</c:if>/>是&nbsp&nbsp&nbsp&nbsp
	<input name="isSendMsg" type="radio" class="cb" value="false" <c:if test="${member.isSendMsg==false}">checked</c:if>/>否</p><i class="sm12_icon_notice"></i>接收消费通知</li>
</ul>
</div>
</form>
<div class="mrgtd10 ">
    <ul class="listinfofill blockbg">
      <li><a href="/oauth/member/toSetPayPwd.do" class="block"><i class="more_icon_12 fr mrg-t10"></i>修改密码</a></li>
    </ul>
</div>
<div class="pd20"><input type="button" value="保存资料" class="butstyleB js-bindMemberBtn"/></div>

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
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">

$(function() {
	
	var message = '${param.message}';
	if(message != '') {
		$("#alertMsg").html(message);
		$("#alertTip").modal();
	}
	
	$(".js-bindMemberBtn").click(function() {
		bindMember();
	});
});

function checkMemberInfo() {
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
	
	if($("#IDCard").val()!=null && $("#IDCard").val().length>0){
		var regIdCard = $("#IDCard").val();
		if(!/^\d{17}(\d|x)$/i.test(regIdCard)){
			$("#alertMsg").html("请输入正确的身份证号!");
			$("#alertTip").modal();
			rs = false;
			return rs;
		}
	}
	
	return rs;
}

function bindMember() {
	if(checkMemberInfo()) {
		$("#bindForm").submit();
	}
}

</script>

</body>
</html>