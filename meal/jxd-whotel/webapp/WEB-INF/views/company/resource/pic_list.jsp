<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 图片素材管理</title>
<link rel="stylesheet" href="/static/company/css/resource.css" />
<link rel="stylesheet" href="/static/common/css/upload.css" />
<link rel="stylesheet" type="text/css" href="/static/front/css/loading.css">
</head>
<c:set var="cur" value="sel-resource" scope="request"/>
<c:set var="cur_sub" value="sel-resource-pic" scope="request"/>
<div class="page-content-wrapper">
	<div class="page-content">
		
		<div class="row">
				<div class="col-md-12">
					<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				<!-- <h3 class="page-title">
					Managed Datatables <small>managed datatable samples</small>
					</h3>  -->
					<ul class="page-breadcrumb breadcrumb">
						<li>
							<i class="fa fa-home"></i>
							<a href="/company/index.do">首页</a>
							<i class="fa fa-angle-right"></i>
						</li>
						<li>
							<a href="/company/resource/listNewsResource.do">图片素材管理</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
				
				<form action="/company/resource/listPicResource.do" id="pageForm" method="post">
		    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
				</form>
				
				<div class="table-toolbar">
					<div>
					<span class="fm-uploadPic"> <input name="file" type="file" class="uploadFile" id="picFile" /><b>上传图片</b></span>
					<a href="javascript:synchronizeMPResource('image')" class="btn btn-sm yellow"><i class="fa fa-exchange"></i>同步公众平台素材</a>
					</div>
					
				</div>
				<div>
					<ul class="news_resource">
					    <c:forEach items="${page.result}" var="picResource">
			   				<li class="li">
			   				  <div class="news_thumb">
								<img src="${picResource.picUrl}?imageView2/2/w/267/h/160" />
							  </div>	             
					         <div class="text-center changeIcon">
					           <a href="javascript:deletePicResource('${picResource.id}')"><i class="glyphicon glyphicon-trash"></i></a></span>
					         </div>
						   </li>
					   </c:forEach>
				     </ul>
				</div>
				<div class="row">
					${page.pageNavigation}
				</div>
		</div>
</div>

<form action="/company/resource/updatePicResource.do" method="post" id="submitForm">
	<input name="pic" id="pic" type="hidden">
</form>

<div class="loading" id="loading"></div>
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/waterfall.js?v=${version}" type="text/javascript"></script>
<script src="/static/company/js/weixinResource.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/bootbox.jsp" />
<jsp:include page="/common/qiniu_upload.jsp" />
<script>
	$(function() {
		
		setTimeout(waterfullData, 10);
	    $(window).resize(function(){
	    	waterfullData();
	    });
	    
	    $("body").on("click", ".js-sidebar-toggler", function() {
	    	setTimeout(waterfullData, 10);
	    });
		
		$(".uploadFile").click(function() {
			setUploadToken();
		});

		uploadFile("#picFile", null, picCallback);
		
	});
	
	function picCallback() {
	     $("#pic").val($("#res_key").val());
	     $("#submitForm").submit();
	}
	
function deletePicResource(id) {
	var url = "/company/resource/deletePicResource.do?ids="+id;
	confirmDeleteData("真的需要删除吗？", url);
}

function waterfullData() {
	var resourceObj = $(".news_resource");
	resourceObj.waterfall({});
    var arrHeight=[],listHeight;
    resourceObj.find(".li").each(function(index){
    	var _this=$(this);
    	arrHeight[index]=parseFloat(_this.css("top"))+_this.height()+
    	parseFloat(_this.css("padding-top"))+parseFloat(_this.css("padding-bottom"));
    });

    
    var arr1=arrHeight.sort(function(a, b){ return a - b});
    
    listHeight=arr1[arr1.length-1]+10;
    resourceObj.height(listHeight);
}

</script>