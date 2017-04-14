<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 主题</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
<style>
	  .colum-tab-td td{vertical-align:middle !important;text-align:left;}
	  .temp-tempForm  .colum-tab-td label{width:auto !important;display:block;}
	  .colum-tab-td label input{margin-right:5px;vertcal-align:middle;}
	  
	  .temp-tempForm label{width:250px !important;}
	  .tem-iconSize,.tem-columnName{height:35px;line-height:35px;border:1px solid #ddd;vertical-align:middle;margin:0 3px;padding:0 3px;}
	  .tem-iconSize{width:50px;}
	  .tem-table,.tem-table th{text-align:center;}
	  .tem-table *{color:#444}
	  .tem-uploadPic{display:inline-block;vertical-align:middle;}
</style>
</head>
<c:set var="cur" value="sel-website-template" scope="request"/>
<c:set var="cur_sub" value="sel-theme-list" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>主题</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateThemeTemplate.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${themeTemplate.id}"/>
				<div id="js-template" class="temp-tempForm">
				<div class="form-body">
					<div class="form-group first">
						<label class="col-md-3 control-label">名称</label>
						<div class="col-md-4">
						   <input name="name" class="form-control" placeholder="名称" maxlength="50" value="${themeTemplate.name}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">模板</label>
						<div class="col-md-4">
							<input name="template" class="form-control" placeholder="模板文件名" maxlength="50" value="${themeTemplate.template}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">banner</label>
						<div class="col-md-4">
							<div id="bannerPreview">
								<c:if test="${fn:length(themeTemplate.bannerUrls) > 0}">
								  <c:forEach items="${themeTemplate.bannerUrls}" var="bannerUrl" varStatus="vs">
								  <span class="del-icon">
								   <input type="hidden" name="banner" value="${themeTemplate.banners[vs.index]}">
                    			   <img src="${bannerUrl}" width="60" height="120" />
                    			   <i></i>
                    			   </span>
								  </c:forEach>
								</c:if>
							</div>
							<span class="fm-uploadPic"><input type="file" class="uploadFile"  id="bannerFile"/><b>上传banner</b></span> (* 可上传多个Banner)
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">预览图</label>
						<div class="col-md-4">
				              <div id="thumbnailPreview">
				                <c:if test="${themeTemplate.thumbnail != null && themeTemplate.thumbnail != ''}">
				                   <input type="hidden" name="thumbnail" value="${themeTemplate.thumbnail}">
                    			   <img src="${themeTemplate.thumbnailUrl}" width="60" height="120" />
								</c:if>
				              </div>
				             <div><span class="fm-uploadPic"><input type="file" class="uploadFile" id="thumbnailFile" /><b>上传预览图</b></span></div>
				        </div>
					</div>
					</div>
					
					<div class="form-group">
					    <label class="col-md-3 control-label"></label>
						<div class="col-md-4">
						<a href="javascript:" class="btn btn-sm green js-addColumn"><i class="fa fa-plus"></i>新增栏目</a>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label"></label>
						<div class="col-md-9">
						<table class="table table-striped table-bordered table-hover dataTable tem-table">
							<thead>
							<tr style="width:100%">
								   <th style="width:15%;">名称</th>
		                           <th style="width:20%;">图标</th>
		                           <th style="width:15%;">编辑权限</th>
		                           <th style="width:10%;">默认链接</th>
		                           <th style="width:10%;">操作</th>
							</tr>
							</thead>
							<tbody id="columnTr" class="colum-tab-td">
							<c:forEach items="${themeTemplate.columns}" var="column" varStatus="vs">
								   <tr>
			                           <td> 
			                           	<input name="columns[${vs.index}].name" type="text" class="js-columnName tem-columnName" value="${column.name}">
			                           </td>
			                           <td>
							           <span class="fm-uploadPic tem-uploadPic"><input type="file" class="uploadFile js-uploadIcon"/><b>上传icon</b></span>
							           <span class="imagePreview">
							                <c:if test="${column.iconUrl != null && column.iconUrl != ''}">
			                    			   <input type="hidden" name="columns[${vs.index}].icon" value="${column.icon}">
			                    			   <img src="${column.iconUrl}" width="30" height="30" />
											</c:if>
							              </span>
			                           </td>
			                           <td>
				                           <label>
				                           <input name="columns[${vs.index}].iconEdit" type="checkbox" value="true" <c:if test="${column.iconEdit == true}">checked</c:if>>icon
				                           </label>
				                           <label>
				                           <input name="columns[${vs.index}].bgColorEdit" type="checkbox" value="true" <c:if test="${column.bgColorEdit == true}">checked</c:if>>颜色
				                           </label>
			                           </td>
			                           <td> 
			                               <select id="js_defaultLink${vs.index}" name="columns[${vs.index}].defaultLink">
			                               	<option value="">请选择</option>
			                               	<c:forEach items="${columnLinks}" var="columnLink">
				                               	<option value="${columnLink.url}" <c:if test="${column.defaultLink == columnLink.url}">selected</c:if>>${columnLink.name}</option>
			                               	</c:forEach>
			                               </select>
			                           </td>
			                           <td >
			                           <a href="javascript:" class="btn btn-sm default js-deleteColumn"><i class="fa fa-times"></i> 删除</a>
			                           </td>
			                       </tr>
							</c:forEach>
							</tbody>
							</table>
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
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script src="/static/admin/js/template.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/qiniu_upload.jsp" />
<script>

	$(function() {
		
		Template.init();
		
		$("body").on("click", "#bannerFile", function() {
			var _this = $(this);
			setUploadToken();
			uploadFile(_this.get(), null, bannerCallback);
		});

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
		
		$(".js-addColumn").click(function() {
			addColumn();
		});
		
		$("body").on("click", ".js-deleteColumn", function() {
			var _this = $(this);
			_this.closest("tr").remove();
		});
	});
	
	function bannerCallback() {
		var htmlContent = "<span class='del-icon'><input name='banner' type='hidden' value='"+$("#res_key").val()+"'>"
		+ "<img src='"+$("#res_url").val()+"' width='100' height='90'/><i></i></span>";
		$("#bannerPreview").append(htmlContent);
	}
	
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
	
	function iconCallback(file) {
		var tdObj = $(file).closest("td");
		var index = tdObj.closest("tr").index();
		var html = "<input name='columns["+index+"].icon' type='hidden' value='"+$("#res_key").val()+"' class='js-iconHidden'>"
        			+ "<img src='"+$("#res_url").val()+"' width='30' height='30'/>";
        tdObj.find(".imagePreview").html(html);
	}
	
	function bgCallback(file) {
		var tdObj = $(file).closest("td");
		var index = tdObj.closest("tr").index();
		var html = "<input name='columns["+index+"].bg' type='hidden' value='"+$("#res_key").val()+"' class='js-bgHidden'>"
        			+ "<img src='"+$("#res_url").val()+"' width='120' height='80'/>";
        tdObj.find(".imagePreview").html(html);
	}
	
	function addColumn() {
		
		var columnTr = $("#columnTr");
		var trObjs = columnTr.find("tr");
		
		var fieldIndexObj = $("#fieldIndex");
		var index = fieldIndexObj.val();
		if(index == "") {
			index = trObjs.length;
		}
	 	fieldIndexObj.val(parseInt(index) + 1);
	 	
	 	var columnLinks = null;
	 	var link = '<select id="js_defaultLink'+index+'" name="columns['+index+'].defaultLink">'
	 			 + '<option value="">请选择</option>';
       
    	$.ajax({
    		url:"/admin/website/ajaxfindAllColumnLinks.do",
    		async:false,
    		dataType:"json",
    		success:function(data) {
    			columnLinks = data;
    		}
    	});
    	if(columnLinks!=null){
	    	for (var i = 0; i < columnLinks.length; i++) {
	    		link +='<option value="'+columnLinks[i].url+'">'+columnLinks[i].name+'</option>';
			}
    	}
    	link+='</select>';
		
		var html = '<tr>'
        		 + '<td>' 
       			 + '<input name="columns['+index+'].name" type="text" class="js-columnName tem-columnName">'
       			 + '</td>'
       			 + '<td>'
       			 + '<span class="fm-uploadPic tem-uploadPic"><input type="file" class="uploadFile js-uploadIcon"/><b>上传icon</b></span>'
       			 + '<span class="imagePreview">'
	             + '</span>'
       			 + '</td>'
       			 + '<td>' 
       			 + '<label><input name="columns['+index+'].iconEdit" type="checkbox" value="true">icon</label>'
                 + '<label><input name="columns['+index+'].bgColorEdit" type="checkbox" value="true">颜色</label>'
       			 + '</td>'
          		 + '<td>'
          		 + link
       			 + '</td>'
          		 + '<td>'
       			 + '<a href="javascript:" class="btn btn-sm default js-deleteColumn"><i class="fa fa-times"></i> 删除</a>'
       			 + '</td>';
    	columnTr.append(html);
	}
</script>

<jsp:include page="/common/bootbox.jsp" />
