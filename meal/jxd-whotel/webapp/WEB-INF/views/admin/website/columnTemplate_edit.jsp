<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 栏目模板</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
<style>
	  .colum-tab-td td{vertical-align:middle !important;text-align:left;}
	  .temp-tempForm  .colum-tab-td label{width:auto !important;display:block;}
	  .colum-tab-td label input{margin-right:5px;vertcal-align:middle;}
</style>
</head>
<c:set var="cur" value="sel-website-template" scope="request"/>
<c:set var="cur_sub" value="sel-column-list" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>栏目模板</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateColumnTemplate.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${columnTemplate.id}"/>
				<div id="js-template" class="temp-tempForm">
				<div class="form-body">
					<div class="form-group first">
						<label class="col-md-3 control-label">名称</label>
						<div class="col-md-4">
						   <input name="name" class="form-control" placeholder="名称" maxlength="50" value="${columnTemplate.name}"/>
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">模板文件</label>
						<div class="col-md-4">
						   <input name="template" class="form-control" placeholder="模板文件" maxlength="50" value="${columnTemplate.template}"/>
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">图片格式</label>
						<div class="col-md-4">
						   <input name="widthHeight" class="form-control" placeholder="宽*高" maxlength="50" value="${columnTemplate.widthHeight}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">css文件</label>
						<div class="col-md-4">
				              <div id="cssPreview">
				                ${columnTemplate.cssPathUrl}
				              </div>
				             <div><span class="fm-uploadPic"> <input type="file" class="uploadFile" id="cssFile" /><b>上传css</b></span></div>
				        </div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">js文件</label>
						<div class="col-md-4">
				              <div id="jsPreview">
				                ${columnTemplate.jsPathUrl}
				              </div>
				             <div><span class="fm-uploadPic"><input type="file" class="uploadFile" id="jsFile" /><b>上传js</b></span></div>
				        </div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">预览图</label>
						<div class="col-md-4">
				              <div id="thumbnailPreview">
				                <c:if test="${columnTemplate.thumbnail != null && columnTemplate.thumbnail != ''}">
				                   <input type="hidden" name="thumbnail" value="${columnTemplate.thumbnail}">
                    			   <img src="${columnTemplate.thumbnailUrl}" width="60" height="120" />
								</c:if>
				              </div>
				             <div><span class="fm-uploadPic"><input type="file" class="uploadFile" id="thumbnailFile" /><b>上传预览图</b></span></div>
				        </div>
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
<input type="hidden" id="fieldIndex">
<style>
.temp-tempForm label{width:250px !important;}
.tem-iconSize,.tem-columnName{height:35px;line-height:35px;border:1px solid #ddd;vertical-align:middle;margin:0 3px;padding:0 3px;}
.tem-iconSize{width:50px;}
.tem-table,.tem-table th{text-align:center;}
.tem-table *{color:#444}
.tem-uploadPic{display:inline-block;vertical-align:middle;}
</style>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script src="/static/admin/js/template.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/qiniu_upload.jsp" />
<script>

	$(function() {
		
		Template.init();

		$("body").on("click", "#cssFile", function() {
			var _this = $(this);
			var t = new Date().getTime();
			setUploadToken(null, null, t+".css");
			uploadFile(_this.get(), null, cssCallback);
		});
		
		$("body").on("click", "#jsFile", function() {
			var _this = $(this);
			var t = new Date().getTime();
			setUploadToken(null, null, t+".js");
			uploadFile(_this.get(), null, jsCallback);
		});
		
		$("body").on("click", "#thumbnailFile", function() {
			var _this = $(this);
			setUploadToken();
			uploadFile(_this.get(), null, thumbnailCallback);
		});
	});
	
	function cssCallback() {
		 var filePreviewObj = $("#cssPreview");
	     var htmlContent = "<input name='cssPath' type='hidden' value='"+$("#res_key").val()+"'>"
	                     +$("#res_url").val();
	     filePreviewObj.html(htmlContent);
	}
	
	function jsCallback() {
		 var filePreviewObj = $("#jsPreview");
	     var htmlContent = "<input name='jsPath' type='hidden' value='"+$("#res_key").val()+"'>"
	                     +$("#res_url").val();
	     filePreviewObj.html(htmlContent);
	}
	
	function thumbnailCallback() {
		 var filePreviewObj = $("#thumbnailPreview");
	     var htmlContent = "<input name='thumbnail' type='hidden' value='"+$("#res_key").val()+"'>"
	                     + "<img src='"+$("#res_url").val()+"' width='60' height='120'/>";
	     filePreviewObj.html(htmlContent);
	}
</script>

<jsp:include page="/common/bootbox.jsp" />