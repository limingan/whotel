<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 图文文章编辑</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
</head>
<c:set var="cur" value="sel-website" scope="request"/>
<c:set var="cur_sub" value="sel-column-news" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/admin/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>图文文章编辑</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateNewsArticle.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${newsArticle.id}"/>
				<input type="hidden" name="newsId" value="${param.newsId}"/>
				<div class="form-body">
					<div class="form-group first">
						<label class="col-md-3 control-label"><span class="jxd_required">*</span>标题</label>
						<div class="col-md-4">
						   <input name="title" class="form-control" placeholder="标题" maxlength="50" value="${newsArticle.title}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">封面</label>
						<div class="col-md-4">
		                      <div id="thumbnailPreview">
		                      <c:if test="${newsArticle.thumbnail != null && newsArticle.thumbnail != ''}">
		                      	<input type="hidden" name="thumbnail" value="${newsArticle.thumbnail}"/>
		                      	<img alt="封面" src="${newsArticle.thumbnailUrl}" width="120" height="120">
		                      </c:if>
		                      </div>
		                      <div id="progressbar" style="width: 76px;height:6px;display:inline-block;"></div><br/>
		                      <span class="fm-uploadPic">
								<input type="file" class="uploadFile" id="thumbnailFile"/><b>上传封面</b>
		                      </span>
		                      <c:set var="widthHeight" value="${columnNews.template.widthHeight}"/>
		                      <p>（最佳尺寸<span id="js_widthHeight">${(widthHeight==null || widthHeight=='')?'200*200':widthHeight}</span>，大小不超过140kb）</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">外链</label>
						<div class="col-md-4">
						   <input name="url" class="form-control" placeholder="外链" value="${newsArticle.url}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">排序</label>
						<div class="col-md-4">
						   <input name="orderIndex" class="form-control" placeholder="排序" value="${newsArticle.orderIndex}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label"><span class="jxd_required">*</span>摘要</label>
						<div class="col-md-4">
						<textarea name="brief" rows="4" cols="80" class="form-control">${newsArticle.brief}</textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">正文</label>
						<div class="col-md-9">
							 <textarea id="editor" name="content">${newsArticle.content}</textarea>
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
<jsp:include page="/common/qiniu_upload.jsp" />
<jsp:include page="/common/bootbox.jsp" />
<script type="text/javascript" src="/static/common/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="/static/common/js/ueditor/ueditor.all.js"></script>
<script src="/static/company/js/newsArticle.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	NewsArticle.init();
	initUeditor();
	
	$("body").on("click", "#thumbnailFile", function() {
		var _this = $(this);
		setUploadToken();
		var widthHeight = $("#js_widthHeight").text();
		var index = widthHeight.indexOf('*');
		uploadImage(_this.get(), "progressbar", thumbnailCallback,widthHeight.substring(0,index)
				,widthHeight.substring(index+1,widthHeight.length),140);
		//uploadFile(_this.get(), null, thumbnailCallback);
	});
});

function initUeditor() {
	//初始化文本编辑器
	var option = {
			initialFrameWidth:550,  //初始化编辑器宽度,默认430
	        initialFrameHeight:400,  //初始化编辑器高度,默认150
		    //关闭字数统计
		    wordCount:false,
		    //关闭elementPath
		    elementPathEnabled:false
		    //focus:true
		    //readonly:true
		   /* toolbars: [[
                  'source', 'bold', 'italic', 'underline', 'justifyleft','justifycenter','justifyright','|', 'insertorderedlist', 'insertunorderedlist', '|', 'insertimage', '|', 
                     		'removeformat', 'fontfamily', 'fontsize', 'forecolor', 'backcolor', 'music','insertvideo','link','unlink','inserttable','deletetable','emotion','map'
              ]] */
	}; 
	
	var ue = UE.getEditor("editor", option);
}

function thumbnailCallback() {
	var htmlContent = "<input name='thumbnail' type='hidden' value='"+$("#res_key").val()+"'>"
	+ "<img src='"+$("#res_url").val()+"' width='120' height='80'/>";
	$("#thumbnailPreview").html(htmlContent);
}
</script>
