<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html lang="zh-CN">
<head>  
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta content="telephone=no" name="format-detection">
    <title>门店列表</title>
    <link data-turbolinks-track="true" href="/static/meal/css/weixin.css?v=1 " media="all" rel="stylesheet">
    <!-- 新添加样式 -->
    <link rel="stylesheet" type="text/css" href="/static/meal/css/restlist.css">
    
    <script type="text/javascript" src="/static/meal/js/jQuery.js"></script>
    <script type="text/javascript" src="/static/meal/js/postion.js?v=3"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=5PESLgvMcSbSUbPjmDKgvGZ3"></script>
</head>
<body>
<div style="height: 100%;" class="ng-scope">
    <div class="ddb-nav-header ng-scope">
        <%--<a class="nav-left-item" href="javascript:history.back(-1);"><i class="fa fa-angle-left"></i></a>--%>
        <div class="header-title ng-binding">门店列表</div>
        <a class="nav-right-item" href="/oauth/meal/search.do">
            <div class="operation-button gray"><i class="fa fa-search"></i>
            </div>
        </a>
    </div>
    <div class="ddb-secondary-nav-header ng-isolate-scope" on-pickup="onPickupFilter">
        <div class="ddb-tab-bar">
            <div class="ddb-tab-item ng-scope">
                <a href="javascript:;" class="ng-binding" id="store_classify"></a>
                <i class="fa fa-caret-down"></i>
            </div>
            <div class="ddb-tab-item ng-scope">
                <a href="javascript:;" class="ng-binding" id="current_area"></a>
                <i class="fa fa-caret-down"></i>
            </div>
            <div class="ddb-tab-item ng-scope" ng-repeat="pane in panes" ng-class="{active:pane.selected}"
                 ng-click="toggle(pane)">
                <a href="javascript:;" class="ng-binding" id="current_sort"></a>
                <i class="fa fa-caret-down"></i>
            </div>
        </div>
        <div class="ddb-box filter-nav-box ng-hide" ng-show="mask" ng-click="select()">
            <div class="box-mask"></div>
        </div>
        <div class="filter-nav-menu" ng-transclude="">
            <div class="ddb-nav-pane ng-isolate-scope ng-hide">
                <div class="sub-pane cur-sub-pane ng-scope ng-isolate-scope">
                    <ul class="shoptype ng-scope">
                        <li class="sub-item" data-id="0">
                            <div class="name ng-binding">
                                全部
                            </div>
                        </li>
                        
                        <li class="sub-item ng-scope" data-id="1">
                            <div class="name ng-binding">
                                外卖
                            </div>
                        </li>
                        <li class="sub-item ng-scope" data-id="2">
                            <div class="name ng-binding">
                                堂食
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="ddb-nav-pane ng-isolate-scope ng-hide">
                <div class="sub-pane cur-sub-pane ng-scope ng-isolate-scope" >
                    <ul class="areatype ng-scope">
                        <li class="sub-item active" data-id="0">
                            <div class="name ng-binding">
                                全城  
                            </div>
                        </li>

                        <c:forEach items="${cityList}" var="c">
                            <li class="sub-item ng-scope" data-id="${c.city}">
                                <div class="name ng-binding">
                                    ${c.city}
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="ddb-nav-pane ng-isolate-scope ng-hide">
                <div class="sub-pane cur-sub-pane ng-scope ng-isolate-scope" >
                    <ul class="shopsort ng-scope">
                        <li class="sub-item active" data-id="0">
                            <div class="name ng-binding">
                                综合排序
                                
                            </div>
                        </li>
                        <li class="sub-item ng-scope" data-id="1">
                            <div class="name ng-binding">
                                正在营业
                            </div>
                        </li>
                        <li class="sub-item ng-scope" data-id="2">
                            <div class="name ng-binding">
                                距离优先
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!--footer-->
    <div id="ddb-delivery-branch-index" class="main-view ng-scope">

        <c:forEach items="${hotelList}" var="hotel">
            <c:choose>
                <c:when test="${hotel.isClose}">
                    <div class="morelist branch-item ng-scope closed" >
                </c:when>
                <c:otherwise>
                    <div class="morelist branch-item ng-scope" >
                </c:otherwise>
            </c:choose>
                    <input id="showlan" type="hidden" value="${hotel.lng},${hotel.lat}"/>
                    <a class="branch-info " href="/oauth/meal/restaurant.do?hotelCode=${hotel.code}">
                        <div class="branch-image">
                            <img src="${hotel.miniatureUrl}">
                        </div>
                        <div class="delivery-info">
                            <div class="first-line">
                                <div class="name ng-binding">
                                        ${hotel.name}
                                </div>								
                                <!--div class="distance right ng-binding" >${hotel.distance}</div-->
                                <c:choose>
                                <c:when test="${hotel.isClose}">
                                    <div class="tag label-green ng-scope">休息中</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="tag label-green ng-scope">营业中</div>
                                        </c:otherwise>
                                        </c:choose>


                                <c:if test="${hotel.isInner}">
                                    <div class="tag label-red ng-scope">店</div>
                                </c:if>
                                <c:if test="${hotel.isTakeOut}">
                                    <div class="tag label-blue ng-scope">外</div>
                                </c:if>
                                <div class="distance right ng-binding" id="shopspostion">${hotel.distance}</div>
                            </div>
                            <div class="second-line">
                                <div class="comment-level red">
                                    <div class="ng-isolate-scope">
                                        <i class="fa fa-star-o ng-scope"></i>
                                        <i class="fa fa-star-o ng-scope"></i>
                                        <i class="fa fa-star-o ng-scope"></i>
                                        <i class="fa fa-star-o ng-scope"></i>
                                        <i class="fa fa-star-o ng-scope"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="third-line">
                                <c:if test="${hotel.isTakeOut}">
                                <div class="time ng-show" ng-show="branch.delivery_times.length &gt; 0">
                                    <i class="fa fa-clock-o"></i>
                                    配送时间${hotel.deliverTime}
                                </div>
                                <div class="fee ng-binding">

                                    <span class="ng-binding ng-scope">${hotel.startPrice}起送</span>
                                    <span class="spliter"></span>


                                    <span class="ng-binding ng-scope">配送费${hotel.deliverPrice}</span>
                                    <span class="spliter"></span>

                                </div>
                                </c:if>
								
                                <div class="address ng-binding">${hotel.address}</div>
                            </div>
                        </div>
                    </a>

                    <div class="top-sales ng-binding ng-scope">
                            ${hotel.descr}
                    </div>

                    </div>
        </c:forEach>
    </div>
    <input type="hidden" id="curlat" name="curlat" value="0"/>
    <input type="hidden" id="curlng" name="curlng" value="0"/>

</div>
</div>
<jsp:include page="footer.jsp"/>
<script src="/static/meal/js/jquery-1.11.3.min.js"></script>
<script language="javascript">
    $('.ddb-tab-bar .ddb-tab-item').click(function () {
        $(".filter-nav-menu > .ddb-nav-pane").addClass('ng-hide').eq($('.ddb-tab-bar .ddb-tab-item').index(this)).removeClass('ng-hide');
        $(".ddb-box").removeClass('ng-hide');
    });

    $('.ddb-box').click(function () {
        $(".filter-nav-menu > .ddb-nav-pane").addClass('ng-hide').eq($('.ddb-tab-bar .ddb-tab-item').index(this)).addClass('ng-hide');
        $(".ddb-box").addClass('ng-hide');
    });
	function queryRedirect()
	{
		var curlat = $('#curlat').val();
        var curlng = $('#curlng').val();
		var shopType = $('.shoptype .active').eq(0).attr('data-id');
		var city = $('.areatype .active').eq(0).attr('data-id');
		var shopSort = $('.shopsort .active').eq(0).attr('data-id');
		window.location.href = encodeURI(encodeURI("/oauth/meal/list.do?city="+city+"&type="+shopType+"&sortType="+shopSort+"&lat=" + curlat + "&lng="+curlng));
	}
    //区域
    $('.shoptype>li,.areatype>li,.shopsort>li').click(function () {
       $(this).parent().children('li').removeClass('active');
       $(this).addClass('active');	   
       queryRedirect()
	});
	function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }
	/*if(getQueryString('lat') == null)
	{
		var curlat = $('#curlat').val();
        var curlng = $('#curlng').val();
		window.location.href = window.location.href+ "?lat=" + curlat + "&lng="+curlng;
	}*/
	var initArray = Array(
	{
		'urlpara':'type',
		'class':'shoptype',
		'findAttr':'data-id',
		'menuId':'store_classify'
	},
	{
		'urlpara':'city',
		'class':'areatype',
		'findAttr':'data-id',
		'menuId':'current_area'
	},
	{
		'urlpara':'sortType',
		'class':'shopsort',
		'findAttr':'data-id',
		'menuId':'current_sort'
	}
	);
	$(initArray).each(function(i,n){
		var para = getQueryString(n.urlpara);
	    $('.'+n.class+' > li').removeClass('active');
		var activeli;
		if(null != para)
	    activeli = $('.'+n.class).find('li['+n.findAttr+'='+ decodeURI(para) + ']');
	    else
		activeli = $('.'+n.class).find('li').eq(0);	
	    activeli.addClass('active');
	    var text = activeli.children('div').html();
	    activeli.children('div').append('<i class="fa fa-check-circle pull-right ng-scope"></i>');
	    $('#'+n.menuId).html(text);
		
	})
	
	
	
</script>
</body>
</html>