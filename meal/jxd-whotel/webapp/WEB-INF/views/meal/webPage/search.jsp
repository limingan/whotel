<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html ng-app="diandanbao" class="ng-scope">
<meta charset="utf-8">
<head>
   
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta content="telephone=no" name="format-detection">
    <title>门店搜索</title>
    <link data-turbolinks-track="true" href="/static/meal/css/weixin.css?v=1" media="all" rel="stylesheet">
    <!-- 新添加样式 -->
    <link rel="stylesheet" type="text/css" href="/static/meal/css/search.css">
    
</head>
<body>
<!-- ngView:  -->
<div ng-view="" style="height: 100%;" class="ng-scope">
    <div id="search-word-page" class="ng-scope">
        <div class="ddb-nav-header search-header">
            <div class="search-input">
                <span class="fa fa-search green voice-input ng-scope" ng-click="startRecord()" ng-if="!is_recording"></span>
                <input type="text" placeholder="输入门店名称" name="word" id="word" class="query-word ng-pristine ng-untouched ng-valid">
            </div>
            <div class="operation-button green search-cancel" onclick="search()">搜索</div>
        </div>
        <div class="main-view">
  
            <div class="keywords-section">
                <c:forEach items="${keywordList}" var="keyword" varStatus="status">
                    <c:if test="${status.index%5 == 0}">
                    <div class="keyword ng-binding ng-scope label-green'" onclick="searchword('${keyword}');">
                        ${keyword}
                    </div>
                    </c:if>
                    <c:if test="${status.index%5 == 1}">
                        <div class="keyword ng-binding ng-scope label-red'" onclick="searchword('${keyword}');">
                            ${keyword}
                        </div>
                    </c:if>
                    <c:if test="${status.index%5 == 2}">
                        <div class="keyword ng-binding ng-scope label-orange'" onclick="searchword('${keyword}');">
                            ${keyword}
                        </div>
                    </c:if>
                    <c:if test="${status.index%5 == 3}">
                        <div class="keyword ng-binding ng-scope label-blue'" onclick="searchword('${keyword}');">
                            ${keyword}
                        </div>
                    </c:if>
                    <c:if test="${status.index%5 == 4}">
                        <div class="keyword ng-binding ng-scope label-pink'" onclick="searchword('${keyword}');">
                            ${keyword}
                        </div>
                    </c:if>
                </c:forEach>
                <div class="space"></div>
            </div>

            <div class="search-result">
                <c:forEach items="${list}" var="hotel">
                    <a class="list-item ng-binding ng-scope" href="/oauth/meal/restaurant.do?hotelCode=${hotel.code}">
                        ${hotel.name}
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
<script src="/static/meal/js/jquery-1.11.3.min.js"></script>
<script>
    function searchword(search) {
        var keyword = encodeURI(encodeURI(search));
        window.location.href = "/oauth/meal/search.do?keyword=" + keyword;
    }

    function search() {
        var word = $('#word').val();
        if (word == '') {
            alert('请输入搜索关键字！');
            return false;
        }
        var keyword = encodeURI(encodeURI(word));
        window.location.href = "/oauth/meal/search.do?keyword=" + keyword;
    }
</script>
</body>
</html>