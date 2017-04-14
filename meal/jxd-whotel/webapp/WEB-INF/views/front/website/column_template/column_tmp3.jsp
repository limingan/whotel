<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${columnNews.name}</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<!-- <link rel="stylesheet" type="text/css" href="/static/metronic/assets/plugins/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/static/front/css/column_news.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common${THEME}.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain${THEME}.css">
<link rel="stylesheet" type="text/css" href="/static/front/css/loading.css"> -->

<style type="text/css">
*html{margin:0; padding:0;}
body{ margin:0; padding:0; font-family:"Microsoft YaHei",Arial, Helvetica, sans-serif; font-size:0.875em;}
.header{ height:45px; width:100%;background:#26b3ee;  position:fixed; z-index:99;}
.cl-bug{ height:45px;}
.top{ position:relative;}
.banner ul{ padding:0; margin:0;}
.banner li {list-style: none; margin-top:6px; position:relative; width:100%;}
.pic{ width:100%; display:block;margin-bottom: -4px;}
.banner li img{ width:100%; height:100%;}
.header_icon_back{background:url(/static/front/hotel/images/header_back_wh.png) no-repeat;background-size:25px; width:25px; height:25px; display:inline-block; position:absolute; top:10px; left:10px;}
.theme{ line-height:45px; height:45px;color:#fff;margin: 0;width: 100%; font-size:1.125em; text-align:center;position:absolute;font-weight:normal;}
.transparent-bg {position: absolute;background: #000;height: 30px;margin: 0;bottom: 4px;left: 0;opacity: 0.7;width: 100%;}
.text {position: absolute;line-height: 30px;margin: 0;bottom: 0;left: 10px;color: #fff;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;}
</style>
</head>

<body>
<header class="header"><div class="top"><a href="javascript:window.history.go(-1);" class="header_icon_back"></a><h1 class="theme">${columnNews.name}</h1></div></header>

<div class="cl-bug"></div>
<div class="banner">
	<ul class="graphiclist_h">
	</ul>
</div>

<div class="loading" id="loading"></div>
<input type="hidden" value="${columnNews.id}" id="columnNewsId"/>
<script type="text/javascript" src="/static/common/js/jquery-1.11.2.js"></script>
<script type="text/javascript" src="/static/common/js/dateUtil.js?v=${version}"></script>
<script type="text/javascript" src="/static/common/js/scrollLoad.js?v=${version}"></script>
<script type="text/javascript">
$(function(){
	var $loading = $("#loading");
	var $dataContainer = $(".graphiclist_h");
	var params = {"newsId":$("#columnNewsId").val()};
	
	$dataContainer.scrollLoad({
		"url": "/front/loadNewsArticle.do",
		"fromPage": 1, //从第几页开始
		"scrollWrap": $(document), //滚动的对象
		"pageSize": 18, //每页加载个数
		//"click":"moreLoad",
		"params":params,
		"htmlTemp": function (data){ //func 数据的html结构 接受了返回的data
			//$("#totalCount").text(data.totalCount);
			var r = data.result;
			var l = r.length;
			var htmls = [];
			if (l > 0) {
				for ( var i=0; i < l; i++) {
				var zdate = new Date(r[i].createTime.replace("-", "/").replace("-", "/")).Format("yyyy/MM/dd");
				
				var url = r[i].url;
				if(!url || url == "") {
					url = "/front/showNewsArticle.do?id="+r[i].id;
				}
				htmls.push('<li><a class="pic" href="'+url+'"><img src="'+r[i].thumbnailUrl+'" width="800" height="340" /></a>'
						  +'<div class="transparent-bg"></div>'
					      +'<p class="text">'+r[i].title+'</p>'
					   	  +'</li>');
				}
			}
			return htmls.join("");
		},
		"bsCallback": function(){
			$loading.show();
		},
		"callback": function(){//func 加载成功后的回调
			$loading.hide();
		} 
	});
});
</script>
</body>
</html>