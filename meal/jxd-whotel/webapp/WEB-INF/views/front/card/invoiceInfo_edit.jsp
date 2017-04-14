<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
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
<c:set var="headerTitle" value="发票编辑" scope="request"/>
<jsp:include page="card_header.jsp" />

<form action="updateInvoiceInfo.do" method="post" id="js_invoiceInfo">
<input type="hidden" name="id" value="${invoiceInfo.id}">
<div class="plinfobox">
	<ul class="listinfofill blockbg" style="<c:if test="${invoiceInfo.id!=null}">display: none;</c:if>">
		<li><p class="fr wd70">
			<input type="radio" name="ticketType" id="js_special" style="margin-right: 5px" value="true" <c:if test="${invoiceInfo.ticketType}">checked</c:if>>专票
			<input type="radio" name="ticketType" id="js_definitive" style="margin-left: 30px;margin-right: 5px" value="false" <c:if test="${!invoiceInfo.ticketType}">checked</c:if>>普票
		</p>发票类型</li>
		<li id="js_isShow"><p class="fr wd70">
			<input type="radio" name="type" id="js_company" style="margin-right: 5px" value="false" <c:if test="${invoiceInfo.type==null || invoiceInfo.type==false}">checked</c:if>>单位
			<input type="radio" name="type" id="js_personal" style="margin-left: 30px;margin-right: 5px" value="true" <c:if test="${invoiceInfo.type==true}">checked</c:if>>个人
		</p>&nbsp;</li>
	</ul>
	<%-- <div class="mrg-l10" style="<c:if test="${invoiceInfo.id!=null}">display: none;</c:if>">
		<input type="radio" name="type" id="js_company" style="margin-right: 5px" value="false" <c:if test="${invoiceInfo.type==null || invoiceInfo.type==false}">checked</c:if>>单位
		<input type="radio" name="type" id="js_personal" style="margin-left: 30px;margin-right: 5px" value="true"  <c:if test="${invoiceInfo.type==true}">checked</c:if>>个人
	</div> --%>
	<ul class="listinfofill blockbg">
		<li><p class="fr wd70"><input name="name" type="text" class="inputinfofillstyle_A" required="required" placeholder="抬头" value="${invoiceInfo.name}"/></p>抬头</li>
		<li class="pd5">
			<p class="ofbutstyleA mgr_t10 fr" style="height: 20px;margin: 5px;">
				<input type="checkbox" class="butThree" id="invoice" name="def" <c:if test="${invoiceInfo.def==true}">checked</c:if> />
				<label class="butskin" for="invoice"></label>
			</p>默认抬头
		</li>
	</ul>
	<ul class="listinfofill blockbg" id="js_personal1" style="<c:if test="${invoiceInfo.type==null || invoiceInfo.type==false}">display: none;</c:if>">
		<li><p class="fr wd70"><input name="mobile" type="tel" class="inputinfofillstyle_A required" placeholder="电话号码" value="${invoiceInfo.mobile}"/></p>电话</li>
		<li><p class="fr wd70"><input name="email" type="email" class="inputinfofillstyle_A" placeholder="邮箱" value="${invoiceInfo.email}"/></p>邮箱</li>
	</ul>
	<ul class="listinfofill blockbg" id="js_company1" style="<c:if test="${invoiceInfo.type==true}">display: none;</c:if>">
		<li><p class="fr wd70"><input name="identifyNo" type="text" class="inputinfofillstyle_A" placeholder="税号" value="${invoiceInfo.identifyNo}"/></p>税号</li>
		<li><p class="fr wd70"><input name="address" type="text" class="inputinfofillstyle_A" placeholder="单位地址" value="${invoiceInfo.address}"/></p>单位地址</li>
		<li><p class="fr wd70"><input name="phone" type="tel" class="inputinfofillstyle_A" placeholder="电话号码" value="${invoiceInfo.phone}"/></p>电话号码</li>
		<li><p class="fr wd70"><input name="bankName" type="text" class="inputinfofillstyle_A" placeholder="开户银行" value="${invoiceInfo.bankName}"/></p>开户银行</li>
		<li><p class="fr wd70"><input name="account" type="text" class="inputinfofillstyle_A" placeholder="银行账户" value="${invoiceInfo.account}"/></p>银行账户</li>
	</ul>
</div>
<div class="pd20">
	<input type="button" value="保存" class="butstyleB" onclick="submitInvoiceInfo()"/>
	<c:if test="${invoiceInfo.id != null && invoiceInfo.id != ''}">
	<input type="button" value="删除" class="butstyleC mrgtd10" onclick="deleteInvoiceInfo('${invoiceInfo.id}');"/>
	</c:if>
</div>
</form>

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
<input type="hidden" value="${invoiceInfo.def}" id="js_def">
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#js_company").click(function(){
		$("#js_company1").show();
		$("#js_personal1").hide();
	});
	$("#js_personal").click(function(){
		$("#js_personal1").show();
		$("#js_company1").hide();
	});
	$("#js_special").click(function(){
		$("#js_company").click();
		$("#js_isShow").hide();
	});
	$("#js_definitive").click(function(){
		$("#js_isShow").show();
		$("#js_company1").show();
		$("#js_personal1").hide();
	});
});

function deleteInvoiceInfo(id) {
	$('#confirmTip').modal({
        relatedTarget: this,
        onConfirm: function(options) {
        	document.location="/oauth/invoice/deleteInvoiceInfo.do?id="+id;
        },
        onCancel: function() {
        }
   });
}

function checkInvoiceInfo(){
	var bo = true;
	if($("input[name='name']").val().length==0){
		alertMsg("抬头不能为空！");
		bo = false;
		return bo;
	}
	if($("input[name='ticketType']:checked").val() == 'true'){
		$("#js_company1").find("input[type='text']").each(function(){
			var _this = $(this);
			if(_this.val().length==0){
				alertMsg(_this.attr("placeholder")+"不能为空！");
				bo = false;
				return bo;
			}
		});
	}
	
	if(bo){
		var mobile = $("input[name='phone']").val();
		var identifyNo = $("input[name='identifyNo']").val();
		if($("input[name='type']:checked").val() == 'true'){
			mobile =$("input[name='mobile']").val();
			
			var email = $("input[name='email']").val();
			if(email.length>0 && !email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
				alertMsg("请输入正确的邮箱！");
				bo = false;
				return bo;
			}
		}
		if(mobile.length==0){
			alertMsg("电话号码不能为空！");
			bo = false;
			return bo;
		}else if(mobile.length!=11){
			alertMsg("请填写正确电话号码！");
			bo = false;
			return bo;
		} 
		if(identifyNo.length==0){
			alertMsg("税号不能为空！");
			bo = false;
			return bo;
		}else if(identifyNo.length!=15&&identifyNo.length!=18&&identifyNo.length!=20){
			alertMsg("请填写正确的税号！");
			bo = false;
			return bo;
		}
	}
	return bo;
}

function submitInvoiceInfo(){
	if(checkInvoiceInfo()){
		$("#js_invoiceInfo").submit();
	}
}

function alertMsg(msg){
	$("#alertMsg").text(msg);
	$("#alertTip").modal();
}

</script>

</body>
</html>