<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 平台公告编辑</title>
</head>
<c:set var="cur" value="sel-notice" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/admin/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>平台公告编辑</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateSysNotice.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${notice.id}"/>
				<%-- <input type="hidden" name="createTime"  value="<fmt:formatDate value="${notice.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/>" /> --%>
				<input type="hidden" name="readQty" value="${notice.readQty }" />
				<div class="form-body">
					<div class="form-group first">
						<label class="col-md-3 control-label">标题</label>
						<div class="col-md-4">
						   <input name="title" class="form-control" placeholder="标题" maxlength="50" value="${notice.title}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">公告内容</label>
						<div class="col-md-4">
						<textarea name="content" class="editor">${notice.content}</textarea>
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
<script type="text/javascript" src="/static/common/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="/static/common/js/ueditor/ueditor.all.js"></script>
<script src="/static/admin/js/notice.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	Notice.init();
	
	initUeditor();
});

function initUeditor() {
	//初始化文本编辑器
	var option = {
			initialFrameWidth:430,  //初始化编辑器宽度,默认430
	        initialFrameHeight:150,  //初始化编辑器高度,默认150
		    //关闭字数统计
		    wordCount:false,
		    //关闭elementPath
		    elementPathEnabled:false,
		    //focus:true
		    //readonly:true
		    toolbars: [[
		                  'source', 'bold', 'italic', 'underline', 'justifyleft','justifycenter','justifyright','|', 'insertorderedlist','insertunorderedlist', '|', 'insertimage', '|', 
		                     		'removeformat','fontfamily', 'fontsize', 'forecolor', 'backcolor', 'music','insertvideo','link','unlink','inserttable','deletetable','emotion','map'
		    ]]
	}; 
	var editors = $(".editor");
	if(editors && editors.length > 0) {
		editors.each(function(i) {
			var _this = $(this);
			if(!_this.hasClass("edui-default")) {
				var editor = new baidu.editor.ui.Editor(option);
				editor.render(editors.get(i));
			}
		});
	}
}
</script>
<jsp:include page="/common/bootbox.jsp" />