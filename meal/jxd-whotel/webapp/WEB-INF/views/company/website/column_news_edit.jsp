<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 栏目图文编辑</title>
<style>
.form-list-column {overflow：hidden;width:50%}
.form-list-column img{width:180px;display:block;margin-bottom:5px;}

li,ul,dt,dl,dd,ol{list-style:none;}

.news_template{padding:0;position:relative;margin:0 auto;}
.news_template>li{position:absolute;width:22%;padding:1% 0 0;background-color:#fff;margin:0 0.6%;border:1px solid #ddd;line-height:1.6em;}
.news_template>li>*{padding:0 1% 0;}
.news_template li img{width:100%;margin:5px 0;}
.news_template li h2{margin-top:2px; font-size:16px;line-height:1.6em;}
.news_template li h2 a{color:#444;}
.news_template .template_thumb{
	height: 300px;
  	overflow: hidden;
}
.news_template li>p{font-size:13px;color:#666;padding:10px 0;margin:0}
.news_template li>i{font-style:normal;color:#999;font-size:12px;}
.news_template>li li{border-bottom:1px solid #ddd;}
.news_template>li li:last-child{border:0 none;}
.news_template dl{width:100%}
.news_template dd{padding:1em 0.8em;line-height:1.7em;}
.whotel-modal .modal-dialog{
	width:65%;
	padding-bottom:50px;
}
.whotel-modal .modal-body{
   overflow-y:scroll;
   height:400px;
}
.whotel-modal .modal-dialog .template_thumb{
  position:relative;
}
.modal-dialog .template_thumb i{position:absolute;display:block;width:100%;height:100%;left:0;top:0;z-index:200;background:url(/static/company/images/bgIcon.png) center 50px no-repeat rgba(0,0,0,0.5);}
</style>
</head>
<c:set var="cur" value="sel-website" scope="request"/>
<c:set var="cur_sub" value="sel-column-news" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/admin/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>栏目图文编辑</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateColumnNews.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${columnNews.id}"/>
				<div class="form-body">
					<div class="form-group first">
						<label class="col-md-3 control-label"><span class="jxd_required">*</span>名称</label>
						<div class="col-md-4">
						   <input name="name" class="form-control" placeholder="名称" maxlength="50" value="${columnNews.name}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">模板</label>
						<div class="col-md-4 form-list-column">
						    <div class="previewThumbnail">
						    <c:set var="template" value="${columnNews.template}"/>
						    <c:if test="${template != null && template.thumbnail != null && template.thumbnail != ''}">
						    <input type="hidden" name="templateId" value="${columnNews.templateId}"/>
						    <img src="${template.thumbnailUrl}" />
						    </c:if>
						    </div>
						    <div class="selectTemplate">
						        
						    <!--  	<c:if test="${template != null && template.thumbnail != null && template.thumbnail != ''}"> -->
						    <!--  	</c:if> -->
						    <a href="javascript:" id="selectColumnTemplate">选择图文模板</a>
						    </div>
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

<script src="/static/company/js/columnNews.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/waterfall.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	ColumnNews.init();
	
	$("body").on("click", "#selectColumnTemplate",function() {
		bootbox.dialog({
            message: '<div class="portlet-body">'
	    	 +'<input type="hidden" name="page" id="bootPage">'
	    	 +'<ul class="news_template" id="dataContain">'
			 +'</ul>'
			 +'</div>'
			 +'<div class="row" id="pageNavigation">'
			 +'</div>',
             title: "选择模板",
             className:"whotel-modal",
             buttons:{
		    	 cancel: {
		    		 label:"取消"
		    	 },
            	 success: {
            		 label:"完成",
            		 callback:function(){
            			 var iObj = $(".template_thumb").find("i");
            			 if(iObj.length <= 0) {
            				 boxAlert("您没有选择任何模板!");
            			 } else {
            				 var templateObj = iObj.closest(".template_thumb");
            				 var templateId = templateObj.attr("data-id");
            				
            				 var imageUrl = templateObj.find("img").attr("src");
            				 
            				 var html = '<input type="hidden" name="templateId" value="'+templateId+'"/>'
 						    		  + '<img src="'+imageUrl+'" />';
            				 $(".previewThumbnail").html(html);
            			 }
            		 }
		   }
		}
		});
		loadData(1);
	});
	
	$("body").on("click", ".template_thumb", function() {
    	var _this = $(this);
    	_this.unbind("mouseleave");
    	$(".template_thumb").find("i").remove();
		_this.append("<i></i>");
    });
	
	$("body").on("mouseover", ".template_thumb", function() {
    	var _this = $(this);
    	if(_this.find("i").length<=0) {
    		_this.append("<i></i>");
		}
    });
});

function waterfullData() {
	var resourceObj = $(".news_template");
	resourceObj.waterfall({});

	var arrHeight=[],listHeight;
    resourceObj.find(".li").each(function(index){
    	var _this=$(this);
    	arrHeight[index]=parseFloat(_this.css("top"))+_this.height()+
    	parseFloat(_this.css("padding-top"))+parseFloat(_this.css("padding-bottom"));
    });

    
    var arr1=arrHeight.sort(function(a, b){ return a - b;});
    
    listHeight=arr1[arr1.length-1]+10;
    resourceObj.height(listHeight);
}

function loadData(page) {
	$.ajax({
		url:"/company/website/ajaxLoadColumnTemplate.do",
		data:{page:page, limit:5},
		dataType:"json",
		cache:false,
		success:function(data) {
			var rs = data.result;
			var html = "";
			if(rs) {
				for(var i=0; i<rs.length; i++) {
					var template = rs[i];
					html += genColumnTemplate(template);
				}
			}
			$("#dataContain").html(html);
			$("#bootPage").val(data.pageNo);
			$("#pageNavigation").html(genPageNavigation(data.pageNo, data.totalPages, data.totalCount, data.start, data.end));
			setTimeout(waterfullData, 200);
			
			$(".template_thumb").bind("mouseleave",function(){
				var _this = $(this);
				_this.find("i").remove();
			});
		}
	});
}

function genColumnTemplate(template) {
	return '<li class="li"><div class="template_thumb" data-id="'+template.id+'"><img src="'+template.thumbnailUrl+'"></div></li>';
}
</script>
<jsp:include page="/common/bootbox.jsp" />
