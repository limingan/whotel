<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
    <meta charset="gbk">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta content="telephone=no" name="format-detection">
    <title>餐台列表</title>
    <link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common2.css?v=${version}">
	<link rel="stylesheet" type="text/css" href="/static/front/meal/css/main.css?v=${version}">
    <link rel="stylesheet" type="text/css" href="/static/meal/css/table_list.css?v=${version}">
    <link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
</head>
<body>
<div style="height: 100%;" class="ng-scope">
   <c:set var="headerTitle" value="${restaurant.name}" scope="request"/>
	<jsp:include page="meal_header.jsp"/>

    <div id="ddb-delivery-branch-index" class="main-view ng-scope multiStyle">
       <ul id="main">
	    
	   </ul>
        
    </div>
</div>
</div>
<div class="loading" id="loading"></div>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script type="text/javascript" src="/static/common/js/scrollLoad.js?v=${version}"></script>
<script language="javascript">
$(function(){
	$("body").on("click", ".js-tab", function() {
		var _this = $(this);
		var tabId = _this.attr('data-tabid');
		location.href = '/oauth/meal/dishCatList.do?tabId=' + tabId;
	});
	
	var $main = $("#main");
	var $loading=$("#loading");
	var restaurantId = '${restaurant.id}';
	
	$main.scrollLoad({
		"url": "/oauth/waiter/ajaxGetMealTabPage.do",
		"fromPage": 1, //从第几页开始
		"scrollWrap": $(document), //滚动的对象
		"params":{'restaurantId' : restaurantId},
		"pageSize": 15, //每页加载个数
		"htmlTemp": function (data){ //func 数据的html结构 接受了返回的data
			var r = data.result;
			var l = r.length;
			var html_rank = "";
			
			if(l > 0) {
				for(var i=0; i<l; i++) {
					var item = r[i];
					var status = item.status;
					var liClass = "deactive";
					var hint = "不可用";
					if(status == 1) {
						liClass = "used";
						hint = "正在使用";
					} else if(status == 2) {
						liClass = "available";
						hint = "可用";
					}
					
					html_rank += '<li class="' + liClass + ' js-tab" data-tabid="' + item.id + '"> '
							  + '<span>' + item. tabName + '</span>'
							  + '<br/>'
							  + '<div style="display:inline-block">'
							  + '<span style="float:left;">(' + hint + ')</span>'
							  + '</div>'
							  + '</li>';
				}
			} else {
				html_rank = '<p style="color:red; text-align:center; padding:10px 10px;">此条件下没有可预订桌台！</p>';
			}
			$("#main").append(html_rank);
			return "";
		},
		"bsCallback": function(){
			$loading.show();
		},
		"callback": function(){//func 加载成功后的回调
			$loading.hide();
		},
		"error":function(){
			html_rank = '<p style="color:red; text-align:center; padding:10px 10px;">系统繁忙，请稍后重试</p>';
			$("#main").append(html_rank);
			$("#total").parent().remove();
		}
	});
	
});


</script>
</body>


























</html>