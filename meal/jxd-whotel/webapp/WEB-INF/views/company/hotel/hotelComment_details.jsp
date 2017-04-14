<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 酒店点评详情</title>
</head>
<c:set var="cur" value="sel-whotel" scope="request"/>
<c:set var="cur_sub" value="sel-hotelComment-list" scope="request"/>

<div class="page-content-wrapper"><div class="portlet page-content">
	<div class="row"><div class="col-md-12">
		<ul class="page-breadcrumb breadcrumb">
			<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
			<li>酒店点评详情</li>
		</ul>
	</div></div>
	
	<div class="portlet-body form"><div class="form-horizontal">
		<div class="form-body">
			<div class="form-group">
				<label class="col-md-3 control-label">微信昵称</label>
				<div class="col-md-4">
					<label class="radio-inline">${hotelComment.weixinFan.nickName}</label>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-3 control-label">订单号</label>
				<div class="col-md-4">
					<label class="radio-inline">${hotelComment.orderSn}</label>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-3 control-label">点评商品</label>
				<div class="col-md-4">
					<label class="radio-inline">${hotelComment.buyName}</label>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-3 control-label">点评星级</label>
				<div class="col-md-4">
					<label class="radio-inline">${hotelComment.commentStar}</label>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-3 control-label">点评内容</label>
				<div class="col-md-4">
					<label class="radio-inline">${hotelComment.content}</label>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-3 control-label">点评图片</label>
				<div class="col-md-4"><label class="radio-inline">
					<c:forEach items="${hotelComment.imagesUrls}" var="imagesUrl" varStatus="vs">
						<img src="${imagesUrl}" width="80" height="80" />
					</c:forEach>
				</label></div>
			</div>
		</div>
				
		<div class="form-actions fluid">
			<div class="col-md-offset-3 col-md-9">
				<button type="button" class="btn default goback">返回</button>
			</div>
		</div>
	</div></div>
</div></div>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/bootbox.jsp" />
