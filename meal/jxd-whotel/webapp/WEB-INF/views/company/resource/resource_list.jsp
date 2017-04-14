<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 图文素材管理</title>
<link rel="stylesheet" href="/static/company/css/resource.css" />
<link rel="stylesheet" type="text/css" href="/static/front/css/loading.css">
</head>
<c:set var="cur" value="sel-resource" scope="request"/>
<c:set var="cur_sub" value="sel-resource-news" scope="request"/>
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
							<a href="/company/resource/listNewsResource.do">图文素材管理</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
				
				<form action="/company/resource/listNewsResource.do" id="pageForm" method="post">
		    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
		    	
		    	<div class="table-toolbar">
					<div class="input-group"> 
						<label>标题:</label>
						<input name="params[name]" class="form-control input-medium" style="height:28px" type="text" value="${queryParam.params.name}">
						<span>&nbsp;</span>
						<button type="submit" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
					</div>
				</div>
				</form>
				
				<div class="table-toolbar">
					<a href="/company/resource/toEditNewsResource.do" class="btn btn-sm green"><i class="fa fa-plus"></i>新建素材</a>
					<!-- <a href="javascript:synchronizeMPResource('news')" class="btn btn-sm yellow"><i class="fa fa-exchange"></i>同步公众平台素材</a> -->
					<hr>
				</div>
				<div>
						<ul class="news_resource">
						    <c:forEach items="${page.result}" var="newsResource">
						   				<li class="li">
						   				  <c:choose>
						   				  <c:when test="${newsResource.multiple}">
									      <ol>
									        <c:forEach items="${newsResource.news}" var="item" varStatus="vs">
									        	<c:choose>
									        		<c:when test="${vs.first}">
									        			<li class="xFirst">
											            <b><fmt:formatDate value="${newsResource.createTime}" pattern="MM月dd日"/></b>
											            <dl>
											               <a href="/front/showNewsItem.do?idKey=${newsResource.id},${item.key}">
											               <dt class="news_thumb">
											                   <img src="${item.coverUrl}?imageView2/2/w/267/h/160" />
											               </dt>
											               <dd>${item.title}</dd>
											               </a>
											            </dl>
											         </li> 
									        		</c:when>
									        		<c:otherwise>
									        		  <li class="nList">
											               <a href="/front/showNewsItem.do?idKey=${newsResource.id},${item.key}">
											                  <img src="${item.coverUrl}?imageView2/2/w/78/h/78" />
											               <h4>${item.title}</h4>
											               </a>
											        	</li>
									        		</c:otherwise>
									        	</c:choose>
									        </c:forEach>
									     </ol>
									     </c:when>
							   			<c:otherwise>
							   			     <c:set var="item" value="${newsResource.news[0]}"/>
							   				 <h4><a href="/front/showNewsItem.do?idKey=${newsResource.id},${item.key}">${item.title}</a></h4>
									          <i><fmt:formatDate value="${newsResource.createTime}" pattern="MM月dd日"/></i>
									          <div class="news_thumb">
									          <img src="${item.coverUrl}?imageView2/2/w/267/h/160">
									          </div>
									          <p>${item.brief}</p>
							   			</c:otherwise>
							   			</c:choose>
								         <div class="text-center changeIcon">
								            <span style="border-right:1px solid #ddd" ><a href="/company/resource/toEditNewsResource.do?id=${newsResource.id}"><i  class="glyphicon glyphicon-pencil" ></i></a></span>
								            <span><a  href="javascript:deleteNewsResource('${newsResource.id}')"><i class="glyphicon glyphicon-trash"></i></a></span>
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

<div class="loading" id="loading"></div>
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/waterfall.js?v=${version}" type="text/javascript"></script>
<script src="/static/company/js/weixinResource.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/bootbox.jsp" />
<script>
function deleteNewsResource(id) {
	var url = "/company/resource/deleteNewsResource.do?ids="+id;
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

$(function() {
	setTimeout(waterfullData, 10);
    $(window).resize(function(){
    	waterfullData();
    });
    
    $("body").on("click", ".js-sidebar-toggler", function() {
    	setTimeout(waterfullData, 10);
    });
});
</script>