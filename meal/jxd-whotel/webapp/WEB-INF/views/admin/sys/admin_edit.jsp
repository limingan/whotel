<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台  | 管理员编辑</title>
</head>
<c:set var="cur" value="sel-sys" scope="request"/>
<c:set var="cur_sub" value="sel-admin" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/admin/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>管理员编辑</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateSysAdmin.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${admin.id}"/>
				<input type="hidden" name="oldPassword" value="${admin.password}"/>
				<div class="form-body">
					<div class="form-group first">
						<label class="col-md-3 control-label">账号</label>
						<div class="col-md-4">
						   <input name="userName" class="form-control" placeholder="账号" maxlength="50" value="${admin.userName}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">密码</label>
						<div class="col-md-4">
						<input name="password" class="form-control" placeholder="密码" maxlength="50" type="password"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">角色</label>
						<div class="col-md-4">
							<select class="form-control" name="roleId">
								<option value="">请选择角色</option>
								<c:forEach items="${roles}" var="role">
									<option value="${role.id}" <c:if test="${admin.roleId == role.id}">selected</c:if>>${role.name}</option> 
								</c:forEach>
							</select>
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

<script src="/static/admin/js/admin.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script>
	$(function() {
		Admin.init();
	});
</script>
<jsp:include page="/common/bootbox.jsp" />