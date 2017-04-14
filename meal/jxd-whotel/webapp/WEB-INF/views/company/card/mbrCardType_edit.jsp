<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 券类型信息编辑</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
</head>
<c:set var="cur" value="sel-webcard" scope="request"/>
<c:set var="cur_sub" value="sel-webcard-mbrCardType" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>酒店信息编辑</li>
				</ul>
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateMbrCardType.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${mbrCardType.id}"/>
				<input type="hidden" name="code" value="${mbrCardType.code}"/>
				<input type="hidden" name="name" value="${mbrCardType.name}"/>
				<div class="form-body edit-Pictorial">
					<div class="form-group first">
						<label class="col-md-3 control-label">代码</label>
						<div class="col-md-4">
						 <p class="form-control-static">
						 	${mbrCardType.code}
						 </p>
						</div>
					</div>
					
					<div class="form-group">
					    <label class="col-md-3 control-label">名称</label>
						<div class="col-md-4">
						 <p class="form-control-static">
						 	${mbrCardType.name}
						 </p>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">图片</label>
						<div class="col-md-4">
						  <div id="imagePreview">
			                <c:if test="${mbrCardType.pic != null && mbrCardType.pic != ''}">
			                  <input type="hidden" name="pic" value="${mbrCardType.pic}">
			                  <img src="${mbrCardType.picUrl}"/>
			                  <%-- <c:choose>
			                  	<c:when test="${sysParamConfig.mbrCardTheme == 1}">
			                  		<img src="${mbrCardType.picUrl}" width="230" height="270"/>
			                  	</c:when>
			                  	<c:otherwise>
			                  		<img src="${mbrCardType.picUrl}" width="370" height="160"/>
			                  	</c:otherwise>
			                  </c:choose> --%>
			                </c:if>
			               </div>
			               <div id="progressbar" style="width: 76px;height:6px;display:inline-block;"></div><br/>
			               <div><span class="fm-uploadPic"><input type="file" class="js-uploadFile"/><b>上传图片</b></span>
			               	  <c:choose>
			                  	<c:when test="${sysParamConfig.mbrCardTheme == 1}">
			                  		(*最佳尺寸230*270像素，大小不能超过20KB)
			                  	</c:when>
			                  	<c:otherwise>
			                  		(*最佳尺寸370*160像素，大小不能超过20KB)
			                  	</c:otherwise>
			                  </c:choose>
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
<input type="hidden" id="js_mbrCardTheme" value="${sysParamConfig.mbrCardTheme}"/>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/qiniu_upload.jsp" />
<script>

	$(function() {
		
		$("body").on("click", ".js-uploadFile", function() {
			var _this = $(this);
			setUploadToken();
			//uploadFile(_this.get(), null, imageCallback);
			if($("#js_mbrCardTheme").val() == "1"){
				uploadImage(_this.get(), "progressbar", imageCallback,230,270,20);
			}else{
				uploadImage(_this.get(), "progressbar", imageCallback,370,160,20);
			}
		});
		
	});
	
	function imageCallback() {
		var htmlContent = "<input name='pic' type='hidden' value='"+$("#res_key").val()+"'>";
		if($("#js_mbrCardTheme").val() == "1"){
			htmlContent += "<img src='"+$("#res_url").val()+"' width='230' height='270'/>";
		}else{
			htmlContent += "<img src='"+$("#res_url").val()+"' width='370' height='160'/>";
		}
		$("#imagePreview").html(htmlContent);
	}
</script>

<jsp:include page="/common/bootbox.jsp" />
