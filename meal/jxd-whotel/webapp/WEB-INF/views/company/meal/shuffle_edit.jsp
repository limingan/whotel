<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 编辑菜肴</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
<!-- <link rel="stylesheet" type="text/css" href="/static/metronic/assets/plugins/datetimepicker/jquery.datetimepicker.css"/> -->
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-meal-shuffle" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>编辑市别</li>
				</ul>
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="saveShuffle.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${shuffle.id}"/>
					<div class="form-group first">
						<label class="col-md-3 control-label">市别名称</label>
						<div class="col-md-4">
							<input type="text" name="shuffleName" value="${shuffle.shuffleName}"  class="form-control">
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">市别编码</label>
						<div class="col-md-4">
							<input type="text" name="shuffleNo" value="${shuffle.shuffleNo}"  class="form-control">
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">开始时间</label>
						<div class="col-md-4">
							<input type="text" name="startTime" value="${shuffle.startTime}" class="form-control"><!-- datetimepicker  -->
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">结束时间</label>
						<div class="col-md-4">
							<input type="text" name="endTime" value="${shuffle.endTime}" class="form-control"><!-- datetimepicker  -->
						</div>
					</div>
					
				   <div class="form-group">
					    <label class="col-md-3 control-label">市别说明</label>
						<div class="col-md-4">
						<textarea name="remark" class="editor">${shuffle.remark}</textarea>
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
<!-- <script src="/static/metronic/assets/plugins/datetimepicker/jquery.datetimepicker.js" type="text/javascript" ></script> -->
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/qiniu_upload.jsp" />
<script>
$(function() {
		
	initUeditor();
		
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
	
	/* $('.datetimepicker').datetimepicker({
		lang:'ch',
		step:1,
		format:'Y-m-d H:i:00'
	}); */
});
</script>

<jsp:include page="/common/bootbox.jsp" />
