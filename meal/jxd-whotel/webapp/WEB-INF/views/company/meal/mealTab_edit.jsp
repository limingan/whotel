<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 包间管理</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
<style type="text/css">
input[type='checkbox']{width: 17px;height: 17px;}
</style>
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-meal-mealTab" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>编辑包间</li>
				</ul>
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="saveMealTab.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${mealTab.id}"/>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">预订状态</label>
						<div class="col-md-4">
							<input type="text" readonly="readonly" value="${mealTab.isEnable?'可预订':'不可预订'}" class="form-control">
						</div>
					</div>
				
					<div class="form-group first">
						<label class="col-md-3 control-label">包间名称</label>
						<div class="col-md-4">
							<input type="text" readonly="readonly" value="${mealTab.tabName}" class="form-control">
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">包间编码</label>
						<div class="col-md-4">
							<input type="text" readonly="readonly" value="${mealTab.tabNo}" class="form-control">
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">所属餐厅</label>
						<div class="col-md-4">
							<input type="text" readonly="readonly" value="${mealTab.restaurant.name}" class="form-control">
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">容纳人数</label>
						<div class="col-md-4">
							<input type="text" name="seats" value="${mealTab.seats}" class="form-control">
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">最低消费</label>
						<div class="col-md-4">
							<input type="text" name="minimums" value="${mealTab.minimums}"  class="form-control">
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">预订定金</label>
						<div class="col-md-4">
							<input type="text" name="deposit" value="${mealTab.deposit}"  class="form-control">
						</div>
					</div>
					
					<div class="form-group first">
						<label class="col-md-3 control-label">显示顺序</label>
						<div class="col-md-4">
							<input type="text" name="orderIndex" value="${mealTab.orderIndex}"  class="form-control">
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">包间图片</label>
						<div class="col-md-4">
				              <div id="miniaturePreview">
				                <c:if test="${mealTab.miniature != null && mealTab.miniature != ''}">
				                  <img src="${mealTab.miniatureUrl}" width="80" height="75"/>
				                </c:if>
				              </div>
				              <div id="progressbar1" style="width: 76px;height:6px;display:inline-block;"></div><br/>
				             <div><span class="fm-uploadPic"> <input name="file" type="file" class="uploadFile" id="miniatureFile" /><b>上传包间图片</b></span>(*最佳尺寸180*150像素，大小不能超过20KB)</div>
				        </div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">包间导图</label>
						<div class="col-md-4">
							<div id="bannerPreview">
								<c:if test="${fn:length(mealTab.bannerUrls) > 0}">
								  <c:forEach items="${mealTab.bannerUrls}" var="bannerUrl" varStatus="vs">
								  <span class="del-icon">
								   <input type="hidden" name="banner" value="${mealTab.banners[vs.index]}">
                    			   <img src="${bannerUrl}" width="100" height="90" />
                    			   <i></i>
                    			   </span>
								  </c:forEach>
								</c:if>
								<div id="progressbar" style="width: 76px;height:6px;display:inline-block;"></div><br/>
							</div>
							<span class="fm-uploadPic"><input type="file" class="uploadFile"  id="bannerFile"/><b>上传banner</b></span>(*最佳尺寸300*200像素，大小不能超过20KB，*可上传多个Banner)
						</div>
					</div>
					
					<div class="form-group">
					    <label class="col-md-3 control-label">餐台说明</label>
						<div class="col-md-4">
						<textarea readonly="readonly" rows="5" cols="60">${mealTab.remark}</textarea><!--  class="editor" -->
						</div>
					</div>
					
					<div class="form-group">
					    <label class="col-md-3 control-label">预订须知</label>
						<div class="col-md-4">
						<textarea name="notes" class="editor">${mealTab.notes}</textarea>
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
			uploadImage(_this.get(), "progressbar1", miniatureCallback,180,150,20);//200*146
		});
		$("body").on("click", "#bannerFile", function() {
			var _this = $(this);
			setUploadToken();
			//uploadFile(_this.get(), null, bannerCallback);
			uploadImage(_this.get(), "progressbar", bannerCallback,300,200,20);//290*210
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
