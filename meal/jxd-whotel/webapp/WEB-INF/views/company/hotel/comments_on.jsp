<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 酒店点评回复</title>
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
					<li>酒店点评回复</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="saveHotelComment.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="replyId" value="${hotelComment.id}"/>
				<input type="hidden" name="hotelCode" value="${hotelComment.hotelCode}"/>
				<%-- <input type="hidden" name="name" value="${hotelBranchVO.cname}"/> --%>
				<div class="form-group first">
					<label class="col-md-3 control-label">${hotelComment.weixinFan.nickName}</label>
					<div class="col-md-4">
					 <p class="form-control-static">
					 	${hotelComment.content}
					 </p>&nbsp;<br/>
					 <c:if test="${fn:length(hotelComment.imagesUrls) > 0}">
						<c:forEach items="${hotelComment.imagesUrls}" var="imagesUrl" varStatus="vs">
							<img src="${imagesUrl}" width="80" height="80" />
						</c:forEach>
					</c:if>
					</div>
				</div>
					
				<div class="form-group">
				    <label class="col-md-3 control-label">回复内容</label>
					<div class="col-md-4">
					<textarea name="content" rows="8" cols="70"></textarea>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-3 control-label">图片</label>
					<div class="col-md-4">
						<div id="bannerPreview"></div>
						<span class="fm-uploadPic"><input type="file" class="uploadFile" id="bannerFile"/><b>上传图片</b></span> (* 可上传多个图片)
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
	$(".uploadFile").click(function() {
		setUploadToken();
	});
	uploadFile("#bannerFile", null, shareIconCallback);
});

function shareIconCallback() {
	var htmlContent = "<span class='del-icon'><input name='images' type='hidden' value='"+$("#res_key").val()+"'>"
                   + "<img src='"+$("#res_url").val()+"' width='80' height='80'/><i></i></span>";
    $("#bannerPreview").append(htmlContent);
}
</script>

<jsp:include page="/common/bootbox.jsp" />
