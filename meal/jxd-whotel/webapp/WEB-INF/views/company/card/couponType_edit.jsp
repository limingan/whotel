<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 券类型信息编辑</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
</head>
<c:set var="cur" value="sel-webcard" scope="request"/>
<c:set var="cur_sub" value="sel-webcard-couponType" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>酒店信息编辑</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateCouponType.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${couponType.id}"/>
				<input type="hidden" name="code" value="${couponType.code}"/>
				<input type="hidden" name="name" value="${couponType.name}"/>
				<div class="form-body edit-Pictorial">
					<div class="form-group first">
						<label class="col-md-3 control-label">代码</label>
						<div class="col-md-4">
						 <p class="form-control-static">
						 	${couponType.code}
						 </p>
						</div>
					</div>
					
					<div class="form-group">
					    <label class="col-md-3 control-label">名称</label>
						<div class="col-md-4">
						 <p class="form-control-static">
						 	${couponType.name}
						 </p>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">权限集</label>
						<div class="col-md-4" style="margin-top: 8px;">
							<c:forEach items="${moduleTypes}" var="moduleType" varStatus="vs">
								<c:if test="${moduleType != null}">
									<input type="checkbox" name="moduleTypes" value="${moduleType}" <c:if test="${couponType.hasModuleTypes(moduleType)}">checked="checked"</c:if>/>${moduleType.label}&nbsp;&nbsp;
								</c:if>
							</c:forEach>
						</div>
					</div>
					
					<div class="form-group">
					    <label class="col-md-3 control-label">满金额使用优惠券</label>
						<div class="col-md-4">
						 <p class="form-control-static">
						 	<input type="number" name="useMoney" value="${couponType.useMoney}"/>
							 <span style="color: #999999;font-size: 12px;">(说明：订单金额满多少才能使用)</span>
						 </p>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">图片</label>
						<div class="col-md-4">
						  <div id="imagePreview">
			                <c:if test="${couponType.pic != null && couponType.pic != ''}">
			                  <input type="hidden" name="pic" value="${couponType.pic}">
			                  <img src="${couponType.picUrl}" width="80" height="80"/>
			                </c:if>
			               </div>
			               <div id="progressbar" style="width: 76px;height:6px;display:inline-block;"></div><br/>
			               <div><span class="fm-uploadPic"><input type="file" class="js-uploadFile"/><b>上传图片</b></span>(*最佳尺寸400*300像素，大小不能超过20KB)</div>
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
