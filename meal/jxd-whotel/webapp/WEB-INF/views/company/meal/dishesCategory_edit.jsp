<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 编辑菜肴</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-meal-dishesCategory" scope="request"/>
<c:set var="cur_sub_leaf" value="sel-dishes-manage-leaf" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>编辑分类</li>
				</ul>
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="saveDishesCategory.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${dishesCategory.id}"/>					
					<div class="form-group first">
						<label class="col-md-3 control-label"><span class="jxd_required">*</span>所属餐厅</label>
						<div class="col-md-4">
							<input type="text" readonly="readonly" value="${dishesCategory.restaurant.name}"  class="form-control">
						</div>
					</div>
					
				
					<div class="form-group first">
						<label class="col-md-3 control-label"><span class="jxd_required">*</span>分类名称</label>
						<div class="col-md-4">
							<input type="text" name="dishName" readonly="readonly" value="${dishesCategory.dishName}"  class="form-control">
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label"><span class="jxd_required">*</span>分类编码</label>
						<div class="col-md-4">
							<input type="text" name="dishNo" readonly="readonly" value="${dishesCategory.dishNo}"  class="form-control">
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">排序</label>
						<div class="col-md-4">
						 <input name="displayOrder" class="form-control" placeholder="排序" maxlength="50" value="${dishesCategory.displayOrder}"/>
						</div>
					</div>
					
				   <div class="form-group">
					    <label class="col-md-3 control-label">分类说明</label>
						<div class="col-md-4">
						<textarea name="remark" class="editor">${dishesCategory.remark}</textarea>
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
<script src="/static/company/js/dishesCategory.js?v=${version}" type="text/javascript"></script>
<script type="text/javascript" src="/static/common/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="/static/common/js/ueditor/ueditor.all.js"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/qiniu_upload.jsp" />
<script>
$(function() {
	Module.init();
		
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
	
	$("button[type='submit']").click(function(){
		return check();
	});
});

function check() {
	//验证排序是否重复
	var displayOrder = $("input[name='displayOrder']").val();
	var reg = new RegExp("^[0-9]*$");
	var id = $("input[name='id']").val();
	var sign = true;
	if(reg.test(displayOrder)){  
	    //排序文本框 输入的 是数字， 发送ajax请求 验证是否之前添加过改数字
	    $.ajax({
	    	url : "checkDisplayOrder.do",
	    	type : "post",
	    	async : false,
	    	data : {displayOrder : displayOrder , id : id},
	    	dataType : "json",
	    	success : function(data) {
	    		if(data) {
	    			sign = false;
	    			alert("排序已存在， 请重新输入！");
	    		}
	    	}
	    });
	} 
	return sign;
}
</script>

<jsp:include page="/common/bootbox.jsp" />
