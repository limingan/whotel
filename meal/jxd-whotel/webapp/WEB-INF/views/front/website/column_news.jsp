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
<link rel="stylesheet" type="text/css" href="/static/front/css/column_news.css">
<link rel="stylesheet" type="text/css" href="/static/front/css/loading.css">
</head>

<body>
   <div>
     <div class="hList-tit">
         <a class="glyphicon glyphicon-arrow-left"  href="javascript:window.history.go(-1);"></a>
         <h2 class="text-center">${columnNews.name}</h2>
         <a class="glyphicon glyphicon-home" href="/oauth/website.do?comid=${columnNews.companyId}"></a>
      </div>
      
      <ul class="dataContainer">
       </ul>
   </div> 
   <footer class="text-center">捷信达技术支持</footer>
    
<div class="loading" id="loading"></div>
<input type="hidden" value="${columnNews.id}" id="columnNewsId"/>
<input type="hidden" value="${columnNews.type}" id="columnNewsType"/>
<script type="text/javascript" src="/static/common/js/jquery-1.11.2.js"></script>
<script type="text/javascript" src="/static/common/js/dateUtil.js?v=${version}"></script>
<script type="text/javascript" src="/static/common/js/scrollLoad.js?v=${version}"></script>
<script type="text/javascript">
$(function(){
	var $loading = $("#loading");
	var $dataContainer = $(".dataContainer");
	var params = {"newsId":$("#columnNewsId").val()};
	var columnNewsType = $("#columnNewsType").val();
	
	if(columnNewsType == "VERTICAL") {
		$dataContainer.attr("id", "vertilist");
	} else if(columnNewsType == "HORIZONTAL") {
		$dataContainer.attr("id", "horizlist");
	}
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
				var zdate = new Date(r[i].createTime).Format("yyyy/MM/dd");
				
				var url = r[i].url;
				if(!url || url == "") {
					url = "/front/showNewsArticle.do?id="+r[i].id;
				}
				
				htmls.push('<li>'
			            + '<dl>'
			            + ' <a href="'+url+'">'
			            + ' <dt>'
			            + '    <img src="'+r[i].thumbnailUrl+'">'
			            + '    </dt>'
			            + '      <dd>'+r[i].title+'</dd>'
			            + '     </a>'
			            + ' </dl>'  
			            + '</li>');
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