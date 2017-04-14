<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 栏目链接</title>
<style type="text/css">
.whotel-modal .modal-content{
	width:230px;
	text-algin:center;
	margin-left:30%;
}
</style>
</head>
<c:set var="cur" value="sel-website" scope="request"/>
<c:set var="cur_sub" value="sel-column-link" scope="request"/>
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
							<a href="/company/website/listColumnLink.do">栏目链接</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
			
			 <form action="/company/website/listColumnLink.do" id="pageForm" method="post">
		    	<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
			</form>
			
			<ul class="feeds">
			   <c:forEach items="${page.result}" var="columnLink" varStatus="vs">
			   		<li style="background-color: #EEEEEE;">
						<div class="col1" style="width:90%;float:left">
							<div class="cont">
								<div class="cont-col1">
									<div class="label label-sm label-success">
										<i class="fa fa-cloud"></i>
									</div>
								</div>
								<div class="cont-col2">
									<div class="desc">
									     ${columnLink.name}
									</div>
								</div>
							</div>
						</div>
						<div class="col2" style="width:10%;text-align:right;float:right">
							<div class="date" style="font-style: normal;">
								<a href="javascript:" class="copy_btn" data-url="${columnLink.url}?comid=${companyId}">复制</a> |
								<a href="javascript:" class="ewm_btn" data-url="${columnLink.url}?comid=${companyId}">二维码</a>
							</div>
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
<jsp:include page="/common/bootbox.jsp" />
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/zclip/jquery.zclip.min.js"></script>
<script src="/static/common/js/jquery.qrcode.min.js"></script>
<script src="/static/common/js/copy.js"></script>
<script src="/static/common/js/qrapi.js"></script>
<script type="text/javascript">

$(function() {
	
	$("body").on("click", ".ewm_btn",function() {
		bootbox.dialog({
            message: '<div class="portlet-body" id="js-qr">'
			 +'</div>',
             title: "二维码",
             className:"whotel-modal"
		});
		
		var $this = $(this);
		var dataUrl = $this.attr("data-url");
		showQr("js-qr", 180, 180, dataUrl);
	});
	
	
});
</script>
