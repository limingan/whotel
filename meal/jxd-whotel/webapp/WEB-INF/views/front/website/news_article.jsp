<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${WEIXINFAN_LOGIN_COMPANYNAME}</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css" href="/static/metronic/assets/plugins/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/static/front/css/column_news.css?v=${version}">
</head>

<body>
   <section id="actical-detail">
       <h3>${newsArticle.title}</h3>
       <time>发布于：<i><fmt:formatDate value="${newsArticle.createTime}" pattern="yyyy-MM-dd"/></i></time>
       <artical>
       		${newsArticle.content}
       </artical>
    </section>
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