<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 编辑菜肴</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
<style type="text/css">
input[type='checkbox']{width: 17px;height: 17px;}
</style>
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-meal-dishes" scope="request"/>
<c:set var="cur_sub_leaf" value="sel-dishes-manage-leaf" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">
		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>编辑菜肴</li>
				</ul>
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="saveDishes.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${dishes.id}"/>
				<div class="form-group first">
					<label class="col-md-3 control-label">菜肴名称</label>
					<div class="col-md-4">
						<input type="text" name="dishName" value="${dishes.dishName}"  class="form-control">
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">菜肴编码</label>
					<div class="col-md-4">
						<input type="text" readonly="readonly" value="${dishes.dishNo}"  class="form-control">
					</div>
				</div>

				<div class="form-group first">
					<label class="col-md-3 control-label">市场价</label>
					<div class="col-md-4">
						<input type="text" name="marketPrice" value="${dishes.marketPrice}"  class="form-control">
					</div>
				</div>				
				
				<div class="form-group first">
					<label class="col-md-3 control-label">优惠价</label>
					<div class="col-md-4">
						<input type="text" name="price" value="${dishes.price}"  class="form-control">
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">显示顺序</label>
					<div class="col-md-4">
						<input type="text" name="orderIndex" value="${dishes.orderIndex}"  class="form-control">
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">菜肴单位</label>
					<div class="col-md-4">
						<input type="text" name="unit" value="${dishes.unit}"  class="form-control">
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">所属分店</label>
					<div class="col-md-4">
						<input type="text" readonly="readonly" value="${dishes.restaurant.hotelName}"  class="form-control">
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">所属餐厅</label>
					<div class="col-md-4">
						<input type="text" readonly="readonly" value="${dishes.restaurant.name}"  class="form-control">
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">菜肴分类</label>
					<div class="col-md-4">
						<input type="text" readonly="readonly" value="${dishes.dishType}" class="form-control">
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">所属餐市</label>
					<div class="col-md-4">
						<input type="text" readonly="readonly" value="${dishes.shuffleName}" class="form-control">
						<%-- <input type="checkbox" name="mealType" <c:if test="${dishes.isHasMealType('BREAKFAST')}">checked="checked"</c:if> value="BREAKFAST">早餐
						<input type="checkbox" name="mealType" <c:if test="${dishes.isHasMealType('LUNCH')}">checked="checked"</c:if> value="LUNCH">午餐
						<input type="checkbox" name="mealType" <c:if test="${dishes.isHasMealType('AFTERNOONTEA')}">checked="checked"</c:if> value="AFTERNOONTEA">下午茶
						<input type="checkbox" name="mealType" <c:if test="${dishes.isHasMealType('DINNER')}">checked="checked"</c:if> value="DINNER">晚餐 --%>
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">是否上架</label>
					<div class="col-md-4">
						<input type="checkbox" name="isEnable" <c:if test="${dishes.isEnable}">checked="checked"</c:if> >
					</div>
				</div>
				
				<%-- <div class="form-group first">
					<label class="col-md-3 control-label">点餐方式</label>
					<div class="col-md-4">
						<select name="orderWay" class="form-control">
							<option value="">请选择</option>
							<option value="0" <c:if test="${dishes.orderWay == 0}">selected="selected"</c:if>>单点</option>
							<option value="1" <c:if test="${dishes.orderWay == 1}">selected="selected"</c:if>>套餐</option>
						</select>
					</div>
				</div> --%>
				
				<div class="form-group">
					<label class="col-md-3 control-label">菜肴图片</label>
					<div class="col-md-4">
		                <div id="miniaturePreview">
		                	<c:if test="${dishes.miniature != null && dishes.miniature != ''}">
		                		<img src="${dishes.miniatureUrl}" width="80" height="75"/>
		                	</c:if>
		              	</div>
		              	<div id="progressbar1" style="width: 76px;height:6px;display:inline-block;"></div><br/>
		             	<div><span class="fm-uploadPic"><input name="file" type="file" class="uploadFile" id="miniatureFile" /><b>上传菜肴</b></span>(*最佳尺寸220*180像素，大小不能超过20KB)</div>
			        </div>
				</div>
				
				<div class="form-group">
					<label class="col-md-3 control-label">菜肴导图</label>
					<div class="col-md-4">
						<div id="bannerPreview">
							<c:if test="${fn:length(dishes.bannerUrls) > 0}">
							  	<c:forEach items="${dishes.bannerUrls}" var="bannerUrl" varStatus="vs">
							  		<span class="del-icon">
							   			<input type="hidden" name="banner" value="${dishes.banners[vs.index]}">
                   			   			<img src="${bannerUrl}" width="100" height="90" />
                   			   			<i></i>
                   			   		</span>
							  	</c:forEach>
							</c:if>
							<div id="progressbar" style="width: 76px;height:6px;display:inline-block;"></div><br/>
						</div>
						<span class="fm-uploadPic"><input type="file" class="uploadFile"  id="bannerFile"/><b>上传banner</b></span>(*最佳尺寸400*300像素，大小不能超过20KB，*可上传多个Banner)
					</div>
				</div>
				
				<div class="form-group">
				    <label class="col-md-3 control-label">预订须知</label>
					<div class="col-md-4">
						<textarea name="notes" class="editor">${dishes.notes}</textarea>
					</div>
				</div>
			
			   	<div class="form-group">
			   		<label class="col-md-3 control-label">菜肴简介</label>
					<div class="col-md-4">
						<textarea name="brief" class="editor">${dishes.brief}</textarea>
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
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/qiniu_upload.jsp" />
<script>
$(function() {
	
	initUeditor();
	
	$("body").on("click", "#miniatureFile", function() {
		var _this = $(this);
		setUploadToken();
		//uploadFile(_this.get(), null, miniatureCallback);
		uploadImage(_this.get(), "progressbar1", miniatureCallback,220,180,20);//238*182
	});
	$("body").on("click", "#bannerFile", function() {
		var _this = $(this);
		setUploadToken();
		//uploadFile(_this.get(), null, bannerCallback);
		uploadImage(_this.get(), "progressbar", bannerCallback,400,300,20);//267*160
	});
});

function miniatureCallback() {
	var htmlContent = "<span class='del-icon'><input name='miniature' type='hidden' value='"+$("#res_key").val()+"'>"
	+ "<img src='"+$("#res_url").val()+"?imageView2/2/w/80/h/80' width='120' height='80'/><i></i></span>";
	$("#miniaturePreview").html(htmlContent);
}

function bannerCallback() {
	var htmlContent = "<span class='del-icon'><input name='banner' type='hidden' value='"+$("#res_key").val()+"'>"
	+ "<img src='"+$("#res_url").val()+"?imageView2/2/w/100/h/90' width='120' height='80'/><i></i></span>";
	$("#progressbar").before(htmlContent);
}

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
