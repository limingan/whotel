<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 服务员管理</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
<style type="text/css">
input[type='checkbox']{width: 17px;height: 17px;}
</style>
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-meal-mealTab" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>编辑服务员</li>
				</ul>
			</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="saveWaiter.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${waiter.id}"/>
					<div class="form-group first">
						<label class="col-md-3 control-label">卡号</label>
						<div class="col-md-4">
							<input type="text" value="${waiter.iCCard}" disabled="disabled" class="form-control">
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">所属分店</label>
						<div class="col-md-4">
							<input type="text" disabled="disabled" value="${waiter.hotelName}" class="form-control">
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">所属餐厅</label>
						<div class="col-md-4">
							<select name="restaurantId" class="form-control">
								<option value="" <c:if test="${empty waiter.restaurantId}">selected="selected"</c:if>>全部</option>
								<c:forEach items="${allRestaurant}" var="r">
									<option value="${r.id}" <c:if test="${r.id == waiter.restaurantId}">selected="selected"</c:if>>${r.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">服务员名称</label>
						<div class="col-md-4">
							<input type="text" name="userName" value="${waiter.userName}" class="form-control">
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">登录用户名</label>
						<div class="col-md-4">
							<input type="text" name="loginUserName" value="${waiter.loginUserName}" placeholder="用于登录系统的账号" class="form-control">
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">密码</label>
						<div class="col-md-4">
							<input type="password" name="pwd" placeholder="不填表示不修改密码" class="form-control">
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">状态</label>
						<div class="col-md-4">
							<select name="status" class="form-control">
								<option value="0" <c:if test="${waiter.status == 0}">selected="selected"</c:if>>可用</option>
								<option value="1" <c:if test="${waiter.status == 1}">selected="selected"</c:if>>停用</option>
							</select>
						</div>
					</div>
					
				<div class="form-actions fluid">
					<div class="col-md-offset-3 col-md-9">
						<button type="submit" class="btn blue">提交</button>
						<button type="button" class="btn default js-goback">取消</button>
					</div>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
</div>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$(".js-goback").click(function(){
		location.href = "/company/waiter/listWaiter.do";
		return false;
	});
	
	var message = $("message");
	if(message != null && message.length > 0) {
		alert(message);
	}
});
</script>
<jsp:include page="/common/bootbox.jsp" />
