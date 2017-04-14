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
<link rel="stylesheet" type="text/css" href="/static/front/css/loading.css"> -->
<link rel="stylesheet" type="text/css" href="/static/front/css/column_news.css">


<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common${THEME}.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain${THEME}.css">
</head>

<body>
	<div>
		<div class="header">
			<div class="header_left">
				<a href="javascript:window.history.go(-1);" class="header_icon_back"></a>
			</div>
			<h1 class="header_title">${columnNews.name}</h1>
			<div class="header_right">
				<a href="/oauth/website.do?comid=${columnNews.companyId}" class="header_icon_bar"></a>
			</div>
		</div>


		<div class="graphiclistbox">
			<ul class="graphiclist_h">
			</ul>
		</div>
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
				var brief = r[i].brief;
				if(!brief) {
					brief = "";
				}
				htmls.push('<li>'
						  +'	<a href="'+url+'">'
						  +'		<p class="pic_L"><img src="'+r[i].thumbnailUrl+'"/></p>'
						  +'		<h3 class="title">'+r[i].title+'</h3>'
						  +'		<div class="summary" style="overflow: hidden;text-overflow: ellipsis;display: -webkit-box;-webkit-line-clamp: 2;-webkit-box-orient: vertical;line-height:1.6em;">'+brief+'</div>'
						  +'		<span class="time">'+zdate+'</span>'
						  +'	</a>'
						  +'</li>');
				
				/* htmls.push('<li>'
			            + '<dl>'
			            + ' <a href="'+url+'">'
			            + ' <dt>'
			            + '    <img src="'+r[i].thumbnailUrl+'">'
			            + '    </dt>'
			            + '      <dd>'+r[i].title+'</dd>'
			            + '     </a>'
			            + ' </dl>'  
			            + '</li>'); */
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