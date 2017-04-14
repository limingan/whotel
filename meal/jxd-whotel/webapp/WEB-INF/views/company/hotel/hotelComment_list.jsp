<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 酒店点评列表</title>
</head>
<c:set var="cur" value="sel-whotel" scope="request"/>
<c:set var="cur_sub" value="sel-hotelComment-list" scope="request"/>
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
							<a href="/company/hotel/listHotels.do">酒店点评列表</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
				<form action="/company/hotel/listHotelComment.do" id="pageForm" method="post">
		    		<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
		    		<div class="input-group">
			    		<label>酒店编码</label>
						<input name="params[hotelCode]" class="form-control input-medium" maxlength="50" value="${queryParam.params.hotelCode}"/>
						
						<span>&nbsp;&nbsp;&nbsp;</span>
						<label>审核状态</label>
						<select class="form-control input-medium" name="params[checkStatus]">
							<option value="">全部</option>
							<option value="0" <c:if test="${queryParam.params.checkStatus == '0'}">selected</c:if>>未审核</option> 
							<option value="1" <c:if test="${queryParam.params.checkStatus == '1'}">selected</c:if>>未通过</option> 
							<option value="2" <c:if test="${queryParam.params.checkStatus == '2'}">selected</c:if>>已通过</option> 
						</select>
						<span>&nbsp;&nbsp;&nbsp;</span>
						<button onclick="pageFormSubmit()" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
						<span>&nbsp;&nbsp;&nbsp;</span>
						<a href="/company/hotel/toCommentConfig.do" class="btn btn-sm green"><i class="fa fa-edit"></i>参数配置</a>
					</div>
				</form>
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr role="row">
				    <th width="5%">序号</th>
				    <th width="10%">酒店编码</th>
					<th width="10%">点评人</th>
					<th width="35%">点评内容</th>
					<th width="10%">审核状态</th>
					<th width="15%">点评时间</th>
					<th width="15%">操作</th>
				</tr>
				</thead>
				<tbody> 
				   <c:forEach items="${page.result}" var="hotelComment" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
				   	    <td>${hotelComment.hotelCode}</td>
				   	    <td>${hotelComment.weixinFan.nickName}</td>
				   	    <td>${hotelComment.content}</td>
				   	    <%-- <td><c:forEach items="${hotelComment.imagesUrls}" var="imagesUrl">
							<img src="${imagesUrl}" width="100" height="90" /><i></i>
						</c:forEach></td> --%>
				   	    <td>
				   	    	<c:if test="${hotelComment.checkStatus==true}">已通过</c:if>
				   	    	<c:if test="${hotelComment.checkStatus==false}">未通过</c:if>
				   	    	<c:if test="${hotelComment.checkStatus==null}">未审核</c:if>
				   	    </td>
				   	    <td><fmt:formatDate value="${hotelComment.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
						<td>
							<a href="showHotelComment.do?id=${hotelComment.id}" class="btn btn-sm purple"><i class="fa fa-search"></i>查看</a>
							<%-- <a href="toHotelComment.do?id=${hotelComment.id}" class="btn btn-sm purple"><i class="fa fa-edit"></i>回复</a> --%>
							<c:if test="${hotelComment.checkStatus!=true}"><a href="javascript:auditHotelComment('${hotelComment.id}')" class="btn btn-sm yellow"><i class="fa fa-file-o"></i>审核</a></c:if>
							<a href="javascript:deleteNotice('${hotelComment.id}')" class="btn btn-sm default"><i class="fa fa-times"></i>删除</a>
					    </td>
					  </tr>
				   </c:forEach>
				</tbody>
				</table>
				</div>
				<div class="row">
					${page.pageNavigation}
				</div>
		</div>
</div>
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/bootbox.jsp" />
<script>
function auditHotelComment(id){
	bootbox.dialog({
		message:"审核是否通过？",
		title : "",
		buttons : {
			Cancel : {
				label:"拒绝",
				className:"but-default",
				callback: function(){
					window.location.href = "/company/hotel/auditHotelComment.do?id="+id+"&checkStatus="+false;
				}
			},
			OK : {
				label:"通过",
				className:"btn-primary",
				callback: function(){
					window.location.href = "/company/hotel/auditHotelComment.do?id="+id+"&checkStatus="+true;
				}
			}
		}
	})
}

function deleteNotice(ids) {
	var url = "/company/hotel/deleteHotelComment.do?ids="+ids;
	confirmDeleteData("真的需要删除吗？", url);
}
</script>