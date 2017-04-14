<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 商户员工</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<c:set var="cur_sub" value="sel-company-admin" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>商户员工</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateCompanyAdmin.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${companyAdmin.id}"/>
				<input type="hidden" name="oldPassword" value="${companyAdmin.password}"/> 
				<input type="hidden" name="admin" value="${companyAdmin.admin==true}"/>
				<div class="form-body">
					<div class="form-group first">
						<label class="col-md-3 control-label"><span class="jxd_required">*</span>账号</label>
						<div class="col-md-4">
						   <input name="account" id="account" class="form-control js_trim" placeholder="账号" maxlength="50" value="${companyAdmin.account}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">密码</label>
						<div class="col-md-4">
						<input name="password" type="password" class="form-control js_trim" placeholder="密码" maxlength="50" id="password" maxlength="50"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">确认密码</label>
						<div class="col-md-4">
						<input class="form-control js_trim" type="password" placeholder="确认密码" name="repassword"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label"><span class="jxd_required">*</span>姓名</label>
						<div class="col-md-4">
						<input name="name" class="form-control" placeholder="姓名" maxlength="50" value="${companyAdmin.name}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label"><span class="jxd_required">*</span>邮箱</label>
						<div class="col-md-4">
						<input name="email" id="email" class="form-control" placeholder="邮箱" maxlength="50" value="${companyAdmin.email}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label"><span class="jxd_required">*</span>手机</label>
						<div class="col-md-4">
						<input name="mobile" class="form-control" placeholder="手机" maxlength="50" value="${companyAdmin.mobile}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">角色</label>
						<div class="col-md-4">
							<select class="form-control" name="roleId">
								<option value="">请选择角色</option>
								<c:forEach items="${roles}" var="role">
									<option value="${role.id}" <c:if test="${companyAdmin.roleId == role.id}">selected</c:if>>${role.name}</option> 
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">酒店</label>
						<div class="col-md-4">
							<%-- <select class="form-control" name="hotelCode">
								<c:forEach items="${hotelBranchs}" var="hotelBranch">
									<option value="${hotelBranch.code}" <c:if test="${companyAdmin.hotelCode == hotelBranch.code}">selected</c:if>>${hotelBranch.cname}</option> 
								</c:forEach>
							</select> --%>
							<c:forEach items="${hotelBranchs}" var="hotelBranch">							
							        <label class="checkbox-inline">
									<div class="checker"><span><input name="hotelCode" type="checkbox" value="${hotelBranch.code}" <c:forEach items="${hotelCodes}" var="hotelCode"><c:if test="${hotelCode == hotelBranch.code}">checked</c:if></c:forEach>></span></div>${hotelBranch.cname}</label>				   		 
						    </c:forEach>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">微信id</label>
						<div class="col-md-4">
						<input name="openId" class="form-control" placeholder="微信id" value="${companyAdmin.openId}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">权限集</label>
						<div class="col-md-7">
						        &nbsp;&nbsp;
						        <c:forEach items="${messageTypes}" var="messageType" varStatus="vs">
						        	<label class="checkbox-inline">
									<div class="checker"><span>
										<input name="messageTypes[${vs.index}]" id="js_${messageType}" type="checkbox" value="${messageType}" <c:if test="${messageType.checked}">checked</c:if>>
									</span></div> ${messageType.name}</label>
						        </c:forEach>
						</div>
					</div>
					<div class="form-group" id="js_cusTime" style="display: none;">
						<label class="col-md-3 control-label">客服时间</label>
						<div class="col-md-4"><!--  class="col-md-4"  -->
							<select class="form-control js_hours" style="width: 114px;display: inline;"></select>&nbsp; :&nbsp;
							<select class="form-control js_minute" style="width: 114px;display: inline;"></select>&nbsp;至&nbsp;
							<select class="form-control js_hours" style="width: 114px;display: inline;"></select>&nbsp; :&nbsp;
							<select class="form-control js_minute" style="width: 114px;display: inline;"></select>
							<input type="hidden" name="startTime" value="${companyAdmin.startTime}" id="js_startTime"/>
							<input type="hidden" name="endTime" value="${companyAdmin.endTime}" id="js_endTime"/>
							<br/>&nbsp;<br/><span style="color: red;">（请注意！必须在公众号中设置微信号才能显示客服昵称）</span>
						</div>
					</div>
				</div>
				<div class="form-actions fluid">
					<div class="col-md-offset-3 col-md-9">
						<button type="submit" class="btn blue">提交</button>
						<button type="button" class="btn default goback">取消</button>
					</div>
				</div>
				
			</form>
			<!-- END FORM-->
		</div>
	</div>
</div>

<script src="/static/company/js/companyAdmin.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<%--内容区域 结束--%>
<script>
	$(function() {
		CompanyAdmin.init();
		
		customerMessageTime();
		
		$("#submitForm").submit(function(){
			$(".js_trim").each(function(){
				$(this).val($(this).val().trim());
			});
		});
		
		$("#js_CUSTOMERSERVICE").change(function(){
			if($("#js_CUSTOMERSERVICE").is(':checked')){
				$("#js_cusTime").show();
			}else{
				$("#js_cusTime").hide();
			}
			//customerMessageTime();
		});
		
		$("#js_cusTime").find("select").change(function(){
			var startTime = $(".js_hours").eq(0).val()+":"+$(".js_minute").eq(0).val();
			var endTime = $(".js_hours").eq(1).val()+":"+$(".js_minute").eq(1).val();
			$("#js_startTime").val(startTime);
			$("#js_endTime").val(endTime);
		});
	});
	
	function customerMessageTime(){
		$("#js_cusTime").hide();
		//$(".js_hours").empty();
		//$(".js_minute").empty();
		var html = '';
		for (var i = 0; i < 24; i++) {
			if(i<10){
				html += '<option value="0'+i+'">0'+i+'</option>';
			}else{
				html += '<option value="'+i+'">'+i+'</option>';
			}
		}
		$(".js_hours").html(html);
		
		for (var i = 24; i < 60; i++) {
			html += '<option value="'+i+'">'+i+'</option>';
		}
		$(".js_minute").html(html);
		
		var startTime = $("#js_startTime").val();
		var endTime = $("#js_endTime").val();
		if(startTime.length>0 && endTime.length>0){
			var sh = startTime.substring(0,startTime.indexOf(":"));
			var sm = startTime.substring(startTime.indexOf(":")+1,startTime.length);
			var eh = endTime.substring(0,endTime.indexOf(":"));
			var em = endTime.substring(endTime.indexOf(":")+1,endTime.length);
			$(".js_hours").eq(0).val(sh);
			$(".js_minute").eq(0).val(sm);
			$(".js_hours").eq(1).val(eh);
			$(".js_minute").eq(1).val(em);
		}else{
			$("#js_startTime").val("00:00");
			$("#js_endTime").val("00:00");
		}
		
		if($("#js_CUSTOMERSERVICE").is(':checked')){
			$("#js_cusTime").show();
		}
		//}
	}
</script>

<jsp:include page="/common/bootbox.jsp" />