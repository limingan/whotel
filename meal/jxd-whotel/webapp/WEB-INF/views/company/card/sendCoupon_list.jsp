<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 派劵记录</title>
</head>
<c:set var="cur" value="sel-webcard" scope="request"/>
<c:set var="cur_sub" value="sel-webcard-couponType" scope="request"/>
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
							<a href="/company/webcard/sendCouponRecord.do">派劵记录</a>
						</li>
					</ul>
					<!-- END PAGE TITLE & BREADCRUMB-->
				</div>
			</div>
			
			<div class="portlet-body">
				<form action="/company/webcard/sendCouponRecord.do" id="pageForm" method="post">
	    			<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">		
	    			<div class="input-group">
						<label>姓名</label>
						<input name="params[name]" class="form-control input-medium" maxlength="50" value="${queryParam.params.name}"/>
						<span>&nbsp;&nbsp;&nbsp;</span>
						<label>手机</label>
						<input name="params[mobile]" class="form-control input-medium" maxlength="50" value="${queryParam.params.mobile}"/>
						<span>&nbsp;&nbsp;&nbsp;</span>
						<label>劵类型</label>
						<select name="params[couponCode]" class="form-control input-medium">
							<option value="">全部</option>
							<c:forEach items="${couponTypes}" var="couponType">
								<option <c:if test="${couponType.code==queryParam.params.couponCode}">selected="selected"</c:if> value="${couponType.code}">${couponType.name}</option>
							</c:forEach>
						</select>
						<span>&nbsp;&nbsp;&nbsp;</span>
						<button type="submit" class="btn btn-sm blue">查询<i class="fa fa-search"></i></button>
					</div>
	    		</form>	
				<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr role="row">
				    <th width="6%">序号</th>       
				    <th width="7%">姓名</th>
					<th width="8%">手机号码</th>
					<th width="8%">券类型</th>
					<th width="7%">券编码</th>
					<th width="7%">数量</th>
					<th width="15%">派劵原因</th>
					<th width="7%">同步状态</th>
					<th width="15%">失败消息</th>
					<th width="10%">时间</th>
					<th width="10%">操作</th>
				</tr>
				</thead>
				<tbody> 
				   <c:forEach items="${page.result}" var="coupon" varStatus="vs">
				   	  <tr class="odd gradeX">
				   	    <td>${vs.count}</td>
						<td>${coupon.name}</td>
						<td>${coupon.mobile}</td>
						<td>${coupon.couponName}</td>
						<td>${coupon.couponCode}</td>
						<td>${coupon.quantity}</td>
						<td>${coupon.reason}</td>
						<td>${coupon.synchronizeState}</td>
						<td>${coupon.errorMsg}</td>
						<td><fmt:formatDate value="${coupon.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<a href="/company/webcard/replaceSendMemberCoupon.do?recordId=${coupon.id}" class="btn btn-sm purple" <c:if test="${coupon.synchronizeState == '成功'}">disabled="disabled"</c:if>><i class="fa fa-edit"></i>重发</a>
					    </td>
					  </tr>
				   </c:forEach>
				</tbody>
				</table>
				</div><div class="row">${page.pageNavigation}</div>
		</div>
</div>
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/bootbox.jsp" />
<script type="text/javascript">
</script>