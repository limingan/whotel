<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 系统配置</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<c:set var="cur_sub" value="sel-sysConfig" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>系统配置</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="editSysParamConfig.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${sysParamConfig.id}"/>
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-3 control-label">充值功能</label>
						<div class="col-md-4">
							<label class="radio-inline"><span><input type="radio" name="isRecharge" <c:if test="${sysParamConfig.isRecharge != false}">checked="checked"</c:if> value="true"/></span>启用</label>
							<label class="radio-inline"><input type="radio" name="isRecharge" <c:if test="${sysParamConfig.isRecharge == false}">checked="checked"</c:if> value="false"/>禁用</label>
							<span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="radio-inline" style="color: #999999;">(说明：用于控制微信充值的功能)</span>
						</div>
					</div>
				</div>
				
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-3 control-label">酒店及门票会员注册功能</label>
						<div class="col-md-4">
							<label class="radio-inline"><span><input type="radio" name="isHotelRegister" <c:if test="${sysParamConfig.isHotelRegister != false}">checked="checked"</c:if> value="true"/></span>启用</label>
							<label class="radio-inline"><input type="radio" name="isHotelRegister" <c:if test="${sysParamConfig.isHotelRegister == false}">checked="checked"</c:if> value="false"/>禁用</label>
							<span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="radio-inline" style="color: #999999;">(说明：用于控制预订酒店客房时注册会员的功能)</span>
						</div>
					</div>
				</div>
				
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-3 control-label">积分兑换</label>
						<div class="col-md-4">
							<label class="radio-inline"><span><input type="radio" name="isIntegralConvert" <c:if test="${sysParamConfig.isIntegralConvert != false}">checked="checked"</c:if> value="true"/></span>启用</label>
							<label class="radio-inline"><input type="radio" name="isIntegralConvert" <c:if test="${sysParamConfig.isIntegralConvert == false}">checked="checked"</c:if> value="false"/>禁用</label>
							<span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="radio-inline" style="color: #999999;">(说明：用于控制是否启用积分兑换)</span>
						</div>
					</div>
				</div>
				
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-3 control-label">会员卡主题</label>
						<div class="col-md-8">
							<label class="radio-inline"><span><input type="radio" name="mbrCardTheme" checked="checked" value=""/></span>列表</label>
							<label class="radio-inline"><input type="radio" name="mbrCardTheme" <c:if test="${sysParamConfig.mbrCardTheme == '1'}">checked="checked"</c:if> value="1"/>滑动</label>
							<span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="radio-inline" style="color: #999999;">(说明：用于控制会员卡界面展示的功能)</span>
						</div>
					</div>
				</div>
				
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-3 control-label">已支付订单取消</label>
						<div class="col-md-8">
							<label class="radio-inline"><span><input type="radio" name="isRefund" <c:if test="${sysParamConfig.isRefund==true}">checked="checked"</c:if> value="true"/></span>启用</label>
							<label class="radio-inline"><input type="radio" name="isRefund" <c:if test="${sysParamConfig.isRefund==null || sysParamConfig.isRefund==false}">checked="checked"</c:if> value="false"/>禁用</label>
							<span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="radio-inline" style="color: #999999;">(说明：用于控制用户能否取消已支付订单)</span>
						</div>
					</div>
				</div>
				
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-3 control-label">显示会员二维码的时间</label>
						<div class="col-md-8">
							<input type="number" name="showMbrQrTime" value="${sysParamConfig.showMbrQrTime}" style="width: 110px;">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="radio-inline" style="color: #999999;">(说明：用于控制会员中心的二维码显示时间，以秒为单位)</span>
						</div>
					</div>
				</div>
				
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-3 control-label">充值手动输入金额</label>
						<div class="col-md-4">
							<label class="radio-inline"><span><input type="radio" name="isChargeConvert" <c:if test="${sysParamConfig.isChargeConvert != false}">checked="checked"</c:if> value="true"/></span>启用</label>
							<label class="radio-inline"><input type="radio" name="isChargeConvert" <c:if test="${sysParamConfig.isChargeConvert == false}">checked="checked"</c:if> value="false"/>禁用</label>
							<span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="radio-inline" style="color: #999999;">(说明：用于控制是否启用手动输入金额功能)</span>
						</div>
					</div>
				</div>
				<div class="form-actions fluid">
					<div class="col-md-offset-3 col-md-9">
						<button type="submit" class="btn blue">提交</button>
					</div>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
</div>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<%--内容区域 结束--%>
<script>
$(function() {
	
});
</script>
<jsp:include page="/common/bootbox.jsp" />