<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 模板栏目编辑</title>
<link rel="stylesheet" href="/static/metronic/assets/plugins/bootstrap-colorpicker/css/colorpicker.css" />
<link rel="stylesheet" href="/static/common/css/upload.css" />
<link rel="stylesheet" href="/static/company/css/website.css" />
</head>
<c:set var="cur" value="sel-website" scope="request"/>
<c:set var="cur_sub" value="sel-website-list" scope="request"/>
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
							<a href="/company/website/listColumnLink.do">模板栏目编辑</a>
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
			     	<c:when test="${website.id != null && website.id != ''}">
				     	 <span class="over" id="js-selectTemplate">选择模板</span>
					     <span class="cur" id="js-editTemplate">编辑模板</span>
					     <span id="js-editWebsite">编辑微网站</span>
					     <span class="last">完成</span>
			     	</c:when>
			     	<c:otherwise>
			     		 <span class="over" id="js-selectTemplate">选择模板</span>
					     <span class="cur" id="js-editTemplate">编辑模板</span>
					     <span>编辑微网站</span>
					     <span class="last">完成</span>
			     	</c:otherwise>
			     </c:choose>
			    </h1>
			    </div>  
			    <div class="tm-templet">
			        <div class="tm-tempediter">
			           <div class="tm-tempColumn">
			               <h2>
			                   <span class="cur js-columnBar">焦点图</span>
			                  <c:forEach items="${website.columns}" var="column" varStatus="vs">
			            	 	<span class="js-columnBar"><p>${column.name}</p>
			            	 	<c:if test="${column.orderEdit == true}">
			            	 	<i></i>
			            	 	</c:if>
			            	 	</span>
			            	   </c:forEach>
			               </h2>
			            </div>
			            <div class="tm-web">
			                <span><img src="/static/company/images/iframe_top.jpg"></span>
			                <iframe src="/website.do?tempId=${template.id}&siteId=${website.id}&edit=true&code=${COMPANY_ADMIN_LOGIN_CODE}" name="previewIframe">
			                </iframe>
			            </div>
			           
			            <form action="updateWebsite.do" method="post" id="submitForm">
			            <input type="hidden" name="id" value="${website.id}" id="websiteId"/>
			            <input type="hidden" name="templateId" value="${template.id}"/>
			            <input type="hidden" name="name" value="${website.name}"/>
			            <div class="tm-columnEditer js-columnEdit">
				            <div id="bannerPreview">
								<c:if test="${fn:length(website.bannerUrls) > 0}">
								  <c:forEach items="${website.bannerUrls}" var="bannerUrl" varStatus="vs">
								  <span class="del-icon">
								   <input type="hidden" name="banner" value="${website.banners[vs.index]}">
                    			   <img src="${bannerUrl}" width="100" height="90" />
                    			   <i></i>
                    			   </span>
								  </c:forEach>
								</c:if>
							</div>
							<c:if test="${fn:length(website.bannerUrls) > 1}">
							<div id="js_bannerLink">
							  <c:forEach items="${website.bannerUrls}" var="bannerUrl" varStatus="vs">
							  	<br/>
							  	图片点击链接${vs.count}：<input type="text" name="bannerLink" value="${website.bannerLinks[vs.index]}">
							  </c:forEach>
							</div>
							</c:if>
							<span class="fm-uploadPic"><input type="file" class="uploadFile"  id="bannerFile"/><b>上传banner</b></span> (${template.bannerRemark})
					    </div>
			           
			           <div>
			            <c:forEach items="${website.columns}" var="column" varStatus="vs">
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
				                   <c:if test="${column.remarksEdit == true}">
					                   <li>
						                   <span>栏目说明</span>
						                   <textarea name="columns[${vs.index}].remarks" class="js-columnRemarks" rows=4 cols=60>${column.remarks}</textarea>
					                   </li>
				                   </c:if>
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
				                   
				                   <c:if test="${column.bgEdit == true || (column.bg != null && column.bg != '')}">
				                   <li>
									<span>背景图片</span>
									<c:if test="${column.bgEdit == true}">
									<span class="fm-uploadPic">
									<input type="file" class="uploadFile js-uploadBg" /><b>上传背景图</b></span><c:if test="${column.bgSize!=null&&column.bgSize!=''}">(${column.bgSize})</c:if>
									</c:if>
									<span class="imagePreview">
									<c:if test="${column.bg != null && column.bg != ''}">
									<input name="columns[${vs.index}].bg" class="js-columnBg" type="hidden" value="${column.bg}">
									<img src="${column.bgUrl}" width="120" height="80"/>
									</c:if>
									</span>
				                   </li>
				                   </c:if>
				                   
				                   <c:if test="${column.bgColorEdit == true}">
				                   <li>
		                   		   <div class="color colorpicker-default" data-color="#3865a8" data-color-format="rgba">
										<span>背景颜色</span>
										<input name="columns[${vs.index}].bgColor" class="js-columnBgColor" type="text" value="${column.bgColor}" >
										<button class="input-group-btn btn default" type="button" style="overflow:hidden;position:relative;height:30px">
										<i style="background-color:${column.bgColor};display:block;width:80%;height:80%;position:absolute;left:10%;top:10%" ></i>
										</button>
									</div>				                 
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
						<button class="btn blue" onclick="generateWebsite()">下一步</button>
						<button class="btn blue" onclick="previewWebsite()">预览</button>
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
    	<option value="${columnLink.url}?comid=${companyId}">${columnLink.name}</option>
    </c:forEach>
</select>
</div>
<jsp:include page="/common/bootbox.jsp" />
<jsp:include page="/common/qiniu_upload.jsp" />
<script src="/static/metronic/assets/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
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
		$(".js-columnBar").eq(index+1).find("p").html(columnName);
		
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
		   var websiteId = $("#websiteId").val();
		   document.location = "/company/website/selectTemplate.do?siteId="+websiteId;
	   });
	   
   $("#js-editTemplate").click(function() {
	   editTemplate();
   });
   
   $("#js-editWebsite").click(function() {
	   var websiteId = $("#websiteId").val();
	   document.location = "/company/website/editWebsiteName.do?siteId="+websiteId;
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
		_this.find(".js-columnRemarksEdit").attr("name", "columns["+index+"].remarksEdit");
		_this.find(".js-columnRemarks").attr("name", "columns["+index+"].remarks");
		_this.find(".js-columnIcon").attr("name", "columns["+index+"].icon");
		_this.find(".js-columnBg").attr("name", "columns["+index+"].bg");
		_this.find(".js-columnBgColor").attr("name", "columns["+index+"].bgColor");
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
	var size = $("#js_bannerLink").find("input").length;
	var htmlLink = '<br/>图片点击链接'+(size+1)+'：<input type="text" name="bannerLink">';
	$("#js_bannerLink").append(htmlLink);
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
	if(target==""){
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

function generateWebsite() {
	var from = $("#submitForm");
	if(checkField()) {
		from.attr("action", "updateWebsite.do");
		from.removeAttr("target");
		from.submit();
	}
}

function previewWebsite() {
	var from = $("#submitForm");
	if(checkField()) {
		from.attr("action", "/previewWebsite.do?edit=true");
		from.attr("target", "previewIframe");
		from.submit();
	}
}
</script>
