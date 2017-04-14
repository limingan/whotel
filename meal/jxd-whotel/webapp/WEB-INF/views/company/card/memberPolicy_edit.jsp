<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 会员政策</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
</head>
<c:set var="cur" value="sel-webcard" scope="request"/>
<c:set var="cur_sub" value="sel-webcard-memberPolicy" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>会员政策</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateMemberPolicy.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${memberPolicy.id}"/>
				<div class="form-body edit-Pictorial">
					
					<div class="form-group">
					    <label class="col-md-3 control-label">完善会员信息送积分</label>
						<div class="col-md-4">
						 <p class="form-control-static">
						 	<input type="number" name="score" value="${memberPolicy.score}"/>
							 <span style="color: #999999;font-size: 12px;">(说明：会员中心，完善个人会员信息送积分)</span>
						 </p>
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
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/qiniu_upload.jsp" />
<script>

	$(function() {
		
		$("body").on("click", ".js-uploadFile", function() {
			var _this = $(this);
			setUploadToken();
			//uploadFile(_this.get(), null, imageCallback);
			uploadImage(_this.get(), "progressbar", imageCallback,400,300,20);
		});
		
	});
	
	function imageCallback() {
		var htmlContent = "<input name='pic' type='hidden' value='"+$("#res_key").val()+"'>"
		+ "<img src='"+$("#res_url").val()+"' width='80' height='80'/>";
		$("#imagePreview").html(htmlContent);
	}
</script>

<jsp:include page="/common/bootbox.jsp" />
