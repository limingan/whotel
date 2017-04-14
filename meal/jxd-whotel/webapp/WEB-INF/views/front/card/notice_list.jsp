<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${WEIXINFAN_LOGIN_COMPANYNAME}</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"   content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/css/loading.css?v=${version}">
</head>
<body>
<c:set var="headerTitle" value="公告消息" scope="request"/>
<jsp:include page="card_header.jsp" />

<div class="news_listbox">
<ul class="news_list dataContainer">
<c:forEach items="${companyNotices}" var="companyNotice">
<li class="link"><i class=" more_icon_12 fr"></i>
<a href="/oauth/member/notice.do?id=${companyNotice.id}&comid=${WEIXINFAN_LOGIN_COMPANYID}">
${companyNotice.title}
<span><fmt:formatDate value="${companyNotice.createTime}" pattern="yyyy-MM-dd"/></span></a>
</li>
</c:forEach>
</ul>
</div>
<div class="loading" id="loading"></div>
<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript" src="/static/common/js/dateUtil.js?v=${version}"></script>
<script type="text/javascript" src="/static/common/js/scrollLoad.js?v=${version}"></script>
<script type="text/javascript">
$(function(){
	var $loading = $("#loading");
	var $dataContainer = $(".dataContainer");
	
	$dataContainer.scrollLoad({
		"url": "/member/loadCompanyNotices.do",
		"fromPage": 1, //从第几页开始
		"scrollWrap": $(document), //滚动的对象
		"pageSize": 18, //每页加载个数
		//"click":"moreLoad",
		"htmlTemp": function (data){ //func 数据的html结构 接受了返回的data
			//$("#totalCount").text(data.totalCount);
			var r = data.result;
			var l = r.length;
			var htmls = [];
			if (l > 0) {
				for ( var i=0; i < l; i++) {
				var zdate = "";
				
				if(r[i].createTime) {
					zdate = new Date(r[i].createTime).Format("yyyy-MM-dd");
				}
				
				htmls.push('<li class="link">'
						+ '<i class=" more_icon_12 fr"></i>'
						+ '<a href="/oauth/member/notice.do?id='+r[i].id+'&comid='+r[i].companyId+'">'
						+ r[i].title
						+ '<span>' + zdate + '</span></a>'
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