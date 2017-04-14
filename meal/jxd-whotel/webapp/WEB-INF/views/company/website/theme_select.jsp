<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 模板选择</title>
<link rel="stylesheet" href="/static/company/css/website.css" />
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
							<a href="/company/website/listColumnLink.do">主题选择</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			<input type="hidden" value="${theme.id}" id="themeId"/>
			<input type="hidden" value="${theme.templateId}" id="templateId"/>
			<!--选择编辑模板-->
			<div id="tm-templet">
			     <div class="tm-tempTit js-tempTit">
			     <h1>
			     <c:choose>
			     	<c:when test="${theme.id != null && theme.id != ''}">
			     	 <span class="cur" id="js-selectThemeTemplate">选择主题</span>
				     <span id="js-editThemeTemplate">编辑主题</span>
				     <span id="js-editTheme">设置主题名称</span>
				     <span class="last">完成</span>
			     	</c:when>
			     	<c:otherwise>
			     		<span class="cur" id="js-selectThemeTemplate">选择主题</span>
				     	<span id="js-editThemeTemplate">编辑主题</span>
				     	<span>设置主题名称</span>
				     	<span class="last">完成</span>
			     	</c:otherwise>
			     </c:choose>
			     </h1>
			    </div>  
			    <div class="tm-templet">
			        <div class="tm-tempList js-tempList">
			           <ul>
			            <c:forEach items="${templates}" var="template">
			            	<li data-id="${template.id}" <c:if test="${theme.templateId == template.id}">class="check"</c:if>>
			            	  <img src="${template.thumbnailUrl}">
			                  <span><i></i></span>
			                  <p>${template.name}</p>
			               </li>
			            </c:forEach>
			           </ul>
			        </div>
			    </div>
			</div>
			
			<div class="form-actions fluid">
				<div class="col-md-offset-3 col-md-9">
					<button class="btn blue" onclick="editTemplate()">下一步</button>
				</div>
			</div>   
			</div>
		</div>
</div>
<jsp:include page="/common/bootbox.jsp" />
<script>
$(document).ready(function(){  
   $("body").on("click",'.js-tempList li',function(){
         var _this=$(this);
        _this.addClass("check").siblings().removeClass("check");
    });
   
   $("#js-selectThemeTemplate").click(function() {
	   var themeId = $("#themeId").val();
	   document.location = "/company/website/selectThemeTemplate.do?themeId="+themeId;
   });
   
   $("#js-editThemeTemplate").click(function() {
	   editTemplate();
   });
   
   $("#js-editTheme").click(function() {
	   var themeId = $("#themeId").val();
	   document.location = "/company/website/ediThemeName.do?themeId="+themeId;
   });
});

function editTemplate() {
	var liObj = $(".js-tempList").find("li.check");
	
	if(liObj && liObj.length > 0) {
		var id = liObj.attr("data-id");
		
		var themeId = $("#themeId").val();
		var templateId = $("#templateId").val();
		
		var query = "tempId="+id;
		if(themeId != "") {
			query += "&themeId="+themeId;
		}
		url = "/company/website/editThemeTemplateColumn.do?"+query;
		
		if(templateId != "" && id != templateId) {
			confirmResult("更换模板将会清空微网站栏目数据， 您真的需要更换吗？", "确定", "取消", url);
			return;
		}
		
		document.location = url;
	} else {
		boxAlert("请选择模板！");
	}
}
</script>



