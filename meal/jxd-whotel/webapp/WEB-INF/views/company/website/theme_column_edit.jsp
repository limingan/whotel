<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 主题编辑</title>
<link rel="stylesheet" href="/static/metronic/assets/plugins/bootstrap-colorpicker/css/colorpicker.css" />
<link rel="stylesheet" href="/static/common/css/upload.css" />
<link rel="stylesheet" href="/static/company/css/website.css" />
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/home10.css">
</head>
<c:set var="cur" value="sel-website" scope="request"/>
<c:set var="cur_sub" value="sel-theme-list" scope="request"/>
<div class="page-content-wrapper">
	<div class="page-content">
		
		<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				<!-- 	<h3 class="page-title">
					Managed Datatables <small>managed datatable samples</small>
					</h3>  -->
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i>
							<a href="/company/index.do">首页</a>
							<i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="/company/website/listColumnLink.do">主题编辑</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			
			<c:forEach items="${template.bannerUrls}" var="bannerUrl" varStatus="vs">
			   <input type="hidden" value="${bannerUrl}" class="js-defaultBanner">
			 </c:forEach>
								  
			<!--选择编辑模板-->
			<div id="tm-templet">
			    <div class="tm-tempTit js-tempTit">
			      <h1>
			    <c:choose>
			     	<c:when test="${theme.id != null && theme.id != ''}">
				     	 <span class="over" id="js-selectTemplate">选择主题</span>
					     <span class="cur" id="js-editTemplate">编辑主题</span>
					     <span id="js-editTheme">设置主题名称</span>
					     <span class="last">完成</span>
			     	</c:when>
			     	<c:otherwise>
			     		 <span class="over" id="js-selectTemplate">选择主题</span>
					     <span class="cur" id="js-editTemplate">编辑主题</span>
					     <span>设置主题名称</span>
					     <span class="last">完成</span>
			     	</c:otherwise>
			     </c:choose>
			    </h1>
			    </div>  
			    <div class="tm-templet">
			        <div class="tm-tempediter">
			           <div class="tm-tempColumn">
			               <h2>
			                  <c:forEach items="${theme.columns}" var="column" varStatus="vs">
			            	 	<span class="js-columnBar"><p>${column.name}</p>
			            	 	<c:if test="${column.orderEdit == true}">
			            	 	<i></i>
			            	 	</c:if>
			            	 	</span>
			            	   </c:forEach>
			               </h2>
			            </div>
			            <form action="updateTheme.do" method="post" id="submitForm">
			            
			            <div class="tm-web">
			                <span><img src="/static/company/images/iframe_top.jpg"></span>
							<div class="homeAD">
								<div data-am-widget="slider" class="am-slider am-slider-default" data-am-slider='{playAfterPaused: 8000}'>
									<ul class="am-slides">
										<c:forEach items="${theme.bannerUrls}" var="bannerUrl" varStatus="vs">
											<input type="hidden" name="banner" value="${theme.banners[vs.index]}">
											<li><img src="${bannerUrl}"/></li>
										</c:forEach>
									</ul>
							 	</div>
							</div>
			            </div>
			           
			            <input type="hidden" name="id" value="${theme.id}" id="themeId"/>
			            <input type="hidden" name="templateId" value="${template.id}"/>
			            <input type="hidden" name="name" value="${theme.name}"/>
			           
			           <div>
			            <c:forEach items="${theme.columns}" var="column" varStatus="vs">
			            	<div class="tm-columnEditer js-columnEdit js-columns" style="display:none">
			                	<input name="columns[${vs.index}].iconEdit" class="js-columnIconEdit" type="hidden" value="${column.iconEdit}">
			                	<input name="columns[${vs.index}].bgEdit" class="js-columnBgEdit" type="hidden" value="${column.bgEdit}">
			                	<input name="columns[${vs.index}].bgColorEdit" class="js-columnBgColorEdit" type="hidden" value="${column.bgColorEdit}">
			                	<input name="columns[${vs.index}].orderEdit" class="js-columnOrderEdit" type="hidden" value="${column.orderEdit}">
			                	<input name="columns[${vs.index}].remarksEdit" class="js-columnRemarksEdit" type="hidden" value="${column.remarksEdit}">
				                <ul>
				                   <li>
				                   <span>栏目名称</span>
				                   <input name="columns[${vs.index}].name" class="js-columnName" type="text" value="${column.name}" maxlength="20">
				                   </li>
				                   <li>
				                   <span>栏目类型</span>
					                   <select name="columns[${vs.index}].type" class="js-columnType"> 
						                   <option value="LINK" <c:if test="${column.type == 'LINK'}">selected</c:if>>链接</option>
						                   <option value="NEWS" <c:if test="${column.type == 'NEWS'}">selected</c:if>>图文</option>
						                   <option value="CONTACT" <c:if test="${column.type == 'CONTACT'}">selected</c:if>>联系方式</option>
		                               	   <option value="NAVIGATION" <c:if test="${column.type == 'NAVIGATION'}">selected</c:if>>导航</option>
		                               	   <option value="SYSTEM" <c:if test="${column.type == 'SYSTEM'}">selected</c:if>>系统连接</option>
					                   </select>
				                   </li>
				                   
				                   <c:if test="${column.iconEdit == true || (column.icon != null && column.icon != '')}">
				                   <li>
				                    <span>栏目icon</span>
				                    <c:if test="${column.iconEdit == true}">
									<span class="fm-uploadPic">
									<input type="file" class="uploadFile js-uploadIcon"/><b>上传icon</b></span>
									</c:if>
									<span class="imagePreview">
									<c:if test="${column.icon != null && column.icon != ''}">
									<input name="columns[${vs.index}].icon" class="js-columnIcon" type="hidden" value="${column.icon}">
									<img src="${column.iconUrl}" width="30" height="30"/>
									</c:if>
									</span>
				                   </li>
				                   </c:if>
				                   
				                   <li class="js-columnTarget"  data-defaultLink="${column.defaultLink}?comid=${companyId}" data-value="${column.target}" data-index="${vs.index}">
				                   </li>
				                </ul>
					        </div>
			            </c:forEach>
			            </div>
			            </form>
			        </div>
			    </div>
			    
			    <div class="form-actions fluid">
					<div class="col-md-offset-3 col-md-9">
						<button class="btn blue" onclick="generateTheme()">下一步</button>
					</div>
				</div>  
			</div>
			</div>
		</div>
</div>

<div class="js-columnNews" style="display:none">
<select>
    <option value="">请选择图文</option>
    <c:forEach items="${columnNewss}" var="columnNews">
    	<option value="${DOMAIN}/front/listNewsArticle.do?newsId=${columnNews.id}">${columnNews.name}</option>
    </c:forEach>
</select>
</div>
<div class="js-systemLink" style="display:none">
<select>
    <option value="">请选择连接</option>
    <c:forEach items="${columnLinks}" var="columnLink">
    	<option value="${columnLink.url}">${columnLink.name}</option>
    </c:forEach>
</select>
</div>
<jsp:include page="/common/bootbox.jsp" />
<jsp:include page="/common/qiniu_upload.jsp" />
<script src="/static/metronic/assets/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script>
$(document).ready(function(){  
	
    $('.colorpicker-default').colorpicker({
        format: 'hex'
    });
	
	initColumnTarget();
	
	$("body").on("click", "#bannerFile", function() {
		var _this = $(this);
		setUploadToken();
		uploadFile(_this.get(), null, bannerCallback);
	});
	
	$("body").on("click", ".js-uploadIcon", function() {
		var _this = $(this);
		setUploadToken();
		uploadFile(_this.get(), null, iconCallback);
	});
	
	$("body").on("click", ".js-uploadBg", function() {
		var _this = $(this);
		setUploadToken();
		uploadFile(_this.get(), null, bgCallback);
	});
	
	$("body").on("click", ".js-columnBar", function() {
		var _this = $(this);
		
		showColumn(_this);
	});
	
	$("body").on("keyup", ".js-columnName", function() {
		var _this = $(this);
		var columnName = _this.val();
		var index = _this.closest(".js-columnEdit").index();
		$(".js-columnBar").eq(index).find("p").html(columnName);
		
	});
	
	$("body").on("click", ".js-columnBar i", function() {
		var _this = $(this);
		var bar = _this.closest(".js-columnBar");
		var index = bar.index();
		
		if(index > 1) {
			bar.insertBefore(bar.prev());
			
			var columnObj = $(".js-columnEdit").eq(index);
			columnObj.insertBefore(columnObj.prev());
			
			changeColumnIndex();
			
			showColumn(bar);
		}
	});
	
	$("body").on("change", ".js-columnType", function() {
		var _this = $(this);
		changeColumnTarget(_this);
	});
	
	$("#js-selectTemplate").click(function() {
		   var themeId = $("#themeId").val();
		   document.location = "/company/website/selectThemeTemplate.do?themeId="+themeId;
	   });
	   
   $("#js-editTemplate").click(function() {
	   editTemplate();
   });
   
   $("#js-editTheme").click(function() {
	   var themeId = $("#themeId").val();
	   document.location = "/company/website/editThemeName.do?themeId="+themeId;
   });
});

function iconCallback(file) {
	var liObj = $(file).closest("li");
	var index = liObj.closest(".js-columns").index();
	
	var html = "<input name='columns["+index+"].icon' type='hidden' value='"+$("#res_key").val()+"' class='js-iconHidden'>"
    			+ "<img src='"+$("#res_url").val()+"' width='30' height='30'/>";
    liObj.find(".imagePreview").html(html);
}

function bgCallback(file) {
	var liObj = $(file).closest("li");
	var index = liObj.closest(".js-columns").index();
	var html = "<input name='columns["+index+"].bg' type='hidden' value='"+$("#res_key").val()+"' class='js-bgHidden'>"
    			+ "<img src='"+$("#res_url").val()+"' width='120' height='80'/>";
    liObj.find(".imagePreview").html(html);
}

function changeColumnIndex() {
	var columns = $(".js-columns");
	columns.each(function(index) {
		var _this = $(this);
		
		_this.find(".js-columnIconEdit").attr("name", "columns["+index+"].iconEdit");
		_this.find(".js-columnBgEdit").attr("name", "columns["+index+"].bgEdit");
		_this.find(".js-columnBgColorEdit").attr("name", "columns["+index+"].bgColorEdit");
		_this.find(".js-columnOrderEdit").attr("name", "columns["+index+"].orderEdit");
		_this.find(".js-columnIcon").attr("name", "columns["+index+"].icon");
		_this.find(".js-columnBg").attr("name", "columns["+index+"].bg");
		_this.find(".js-columnName").attr("name", "columns["+index+"].name");
		_this.find(".js-columnType").attr("name", "columns["+index+"].type");
		_this.find(".js-columnTarget").attr("data-index", index);
	});
}

function showColumn(obj) {
	var index = obj.index();
	
	var columnEditDiv = $(".js-columnEdit");
	var columnBar = $(".js-columnBar");
	columnEditDiv.hide();
	columnBar.removeClass("cur");
	
	obj.addClass("cur");
	
	columnEditDiv.eq(index).show();
}

function bannerCallback() {
	var htmlContent = "<span class='del-icon'><input name='banner' type='hidden' value='"+$("#res_key").val()+"'>"
	+ "<img src='"+$("#res_url").val()+"' width='120' height='80'/><i></i></span>";
	$("#bannerPreview").append(htmlContent);
}

function initColumnTarget() {
	var columnTypes = $(".js-columnType");
	columnTypes.each(function(i) {
		var _this = $(this);
		changeColumnTarget(_this);
	});
}

function changeColumnTarget(_this) {
	var optionObj = _this.find("option:selected");
	if(optionObj.length == 0) {
		return;
	}
	var type = optionObj.val();
	var columnTarget = _this.closest("ul").find(".js-columnTarget");
	var target = columnTarget.attr("data-value");
	var index = columnTarget.attr("data-index");
	var defaultLink = columnTarget.attr("data-defaultLink");
	var targetName = "columns["+index+"].target";
	if(defaultLink != "" && (target=="" || target =="javascript:")){
		target = defaultLink;
	}
	
	columnTarget.show();
	if(type == "LINK") {
		columnTarget.html("<span>栏目链接</span> <textarea name='"+targetName+"' rows=4 cols=60>"+target+"</textarea>");
	} else if(type == "NEWS") {
		var selectObj = $(".js-columnNews").find("select");
		var select = selectObj.clone();
		select.attr("name", targetName);
		select.find("option[value='"+target+"']").attr("selected", "selected");
		columnTarget.html("<span>栏目链接</span> "+select.prop("outerHTML"));
	} else if(type == "SYSTEM"){
		var selectObj = $(".js-systemLink").find("select");
		var select = selectObj.clone();
		select.attr("name", targetName);
		select.find("option[value='"+target+"']").attr("selected", "selected");
		columnTarget.html("<span>系统链接</span> "+select.prop("outerHTML"));
	} else{
		columnTarget.html("");
		columnTarget.hide();
	}
}

function checkField() {
	var rs = true;
	var columnNames = $(".js-columnName");
	var showError = false;
	columnNames.each(function() {
		var _this = $(this);
		var columnName = _this.val();
		_this.siblings(".error").remove();
		if(columnName == "") {
			_this.after("<span class='error'>栏目名不能为空！</span>");
			rs = false;
		} else if(columnName.length > 20) {
			_this.after("<span class='error'>栏目名不能超过20个字！</span>");
			rs = false;
		}
		
		if(!rs && !showError) {
			var index = _this.closest(".js-columnEdit").index();
			
			var columnBars = $(".js-columnBar");
			showColumn(columnBars.eq(index-1));
			showError = true;
		}
	});
	return rs;
}

function generateTheme() {
	var from = $("#submitForm");
	if(checkField()) {
		from.attr("action", "updateTheme.do");
		from.removeAttr("target");
		from.submit();
	}
}

function previewTheme() {
	var from = $("#submitForm");
	if(checkField()) {
		from.attr("action", "/previewTheme.do?edit=true");
		from.attr("target", "previewIframe");
		from.submit();
	}
}
</script>
