<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${newsItem.title}</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css" href="/static/front/css/news_page.css">
</head>

<body id="activity-detail">
	<div class="page-bizinfo">
		<div class="header">
			<h1 id="activity-name">${newsItem.title}</h1>
			<p class="activity-info">
				<span class="activity-meta"><fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd"/></span>
				<a href="${publicNo.attentionUrl}" id="post-user" class="activity-meta"><span class="text-ellipsis">${publicNo.name}</span><i class="icon_link_arrow"></i></a>
			</p>
		</div>
	</div>

	<div class="page-content">
		<div class="media" id="media">
			<c:if test="${newsItem.showCover}">
				<img src="${newsItem.coverUrl}">
			</c:if>
		</div>
		<div class="text">${newsItem.content}</div>
		<p class="page-toolbar">
			<c:if test="${not empty newsItem.url}">
				<a href="${newsItem.url}" class="page-url">阅读原文</a>
			</c:if>
		</p>
	</div>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script>
$(function(){
	var srcList = [];
	var imgs = $.find('img');
	for (i = 0; i < imgs.length; i++) {
		srcList[i] = $(imgs[i]).attr("src");
		$(imgs[i]).attr("data-index",i);
	}
	$('img').click(function() {
		var index = $(this).attr("data-index");
		imagePreview(srcList[index], srcList);
	});
});
function imagePreview(curSrc, srcList) {
	//这个检测是否参数为空
	if (!curSrc || !srcList || srcList.length == 0) {
		return;
	}
	//这个使用了微信浏览器提供的JsAPI 调用微信图片浏览器
	WeixinJSBridge.invoke('imagePreview', {
		'current' : curSrc,
		'urls' : srcList
	});
};
</script>
</body>
</html>