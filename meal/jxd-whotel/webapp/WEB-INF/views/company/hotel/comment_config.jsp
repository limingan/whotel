<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 参数配置</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
</head>
<c:set var="cur" value="sel-whotel" scope="request"/>
<c:set var="cur_sub" value="sel-hotelComment-list" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>参数配置</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateCommentConfig.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${commentConfig.id}"/>
				
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-3 control-label">是否允许用户点评</label>
						<div class="col-md-4">
							<label class="radio-inline"><span><input type="radio" name="isHotelComment" <c:if test="${commentConfig.isHotelComment}">checked="checked"</c:if> value="true"/></span>启用</label>
							<label class="radio-inline"><input type="radio" name="isHotelComment" <c:if test="${!commentConfig.isHotelComment}">checked="checked"</c:if> value="false"/>禁用</label>
							<span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="radio-inline" style="color: #999999;">(说明：用于控制用户在订单详情界面对酒店点评)</span>
						</div>
					</div>
				</div>
				
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-3 control-label">背景图片</label>
						<div class="col-md-4">
							<div id="thumbPreview">
								<c:if test="${commentConfig.backgroundImg != null && commentConfig.backgroundImg != ''}">
									<span class="del-icon">
										<input type="hidden" name="backgroundImg" value="${commentConfig.backgroundImg}">
										<img src="${commentConfig.backgroundImgUrl}" width="80" height="80"/>
									<i></i></span>
								</c:if>
							</div>
							<div id="progressbar" style="width: 76px;height:6px;display:inline-block;"></div><br/>
							<div><span class="fm-uploadPic"><input type="file" class="uploadFile" id="thumbFile" /><b>上传背景图片</b></span><br/>(*最佳尺寸400*200像素，大小不能超过140KB)</div>
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

<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/qiniu_upload.jsp" />
<%--内容区域 结束--%>
<script>
$(function(){
    $(".uploadFile").click(function() {
    	setUploadToken();
    });
    uploadImage("#thumbFile", "progressbar", bannerCallback,400,200,140);
});
function bannerCallback() {
	var htmlContent = "<span class='del-icon'><input name='backgroundImg' type='hidden' value='"+$("#res_key").val()+"'>"
    	+ "<img src='"+$("#res_url").val()+"?imageView2/2/w/80/h/80' width='80' height='80'/><i></i></span>";
	$("#thumbPreview").html(htmlContent);
}
</script>

<jsp:include page="/common/bootbox.jsp" />