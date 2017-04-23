<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html lang="zh-CN">
<head>
    <style type="text/css">@charset "UTF-8";
    [ng\:cloak], [ng-cloak], [data-ng-cloak], [x-ng-cloak], .ng-cloak, .x-ng-cloak, .ng-hide:not(.ng-hide-animate) {
        display: none !important;
    }

    ng\:form {
        display: block;
    }</style>
    <style type="text/css">@charset "UTF-8";
    [ng\:cloak], [ng-cloak], [data-ng-cloak], [x-ng-cloak], .ng-cloak, .x-ng-cloak, .ng-hide:not(.ng-hide-animate) {
        display: none !important;
    }
    ng\:form {
        display: block;
    }

    </style>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta content="telephone=no" name="format-detection">
    <title>分厅列表</title>
    <link data-turbolinks-track="true" href="/static/meal/css/weixin.css?v=1" media="all" rel="stylesheet">
    <style type="text/css">
        @media screen {
        .smnoscreen {
            display: none
        }
    }
    @media print {
        .smnoprint {
            display: none
        }
    }</style>
    <script type="text/javascript" src="/static/meal/js/jQuery.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=5PESLgvMcSbSUbPjmDKgvGZ3"></script>
    <script type="text/javascript" src="/static/meal/js/postion.js?v=3"></script>
</head>
<body>
<div style="height: 100%;" class="ng-scope">
    <div class="ddb-nav-header ng-scope">
        <a class="nav-left-item" href="javascript:history.back(-1);"><i class="fa fa-angle-left"></i></a>
        <div class="header-title ng-binding">餐厅列表</div>

    </div>

    <div id="ddb-delivery-branch-index" class="main-view ng-scope">
       <c:forEach items="${restList}" var="rest">
           <div class="morelist branch-item ng-scope " >
               <input id="showlan" type="hidden" value="100,100"/>
               <a class="branch-info " href="/oauth/meal/dishCatList.do?restaurantId=${rest.id}">
                   <div class="branch-image">
                       <img src="http://tiantianwutuo.top/attachment/images/1/2017/04/SanMzlI2yXTxyqhYt1DtILUxnY1VU2.jpg">
                   </div>
                   <div class="delivery-info">
                       <div class="first-line">
                           <div class="name ng-binding">
                               ${rest.name}
                           </div>

                       </div>

                       <div class="third-line">

                           <div class="fee ng-binding">

                            <span class="ng-binding ng-scope" style="display:inline-block;">营业时间 <span style="color:red">${rest.businessTime}</span>

							</span>
                            <span onclick="javascript:void(0)" class="ng-binding ng-scope"  style="text-align:center;float:right;margin-right:20px;background-color:red;color:white;border-radius:25px;width:50px;height:20px">订餐</span>

                           </div>
                           <div class="address ng-binding">${rest.cuisine}</div>
                       </div>
                   </div>
               </a>



           </div>
       </c:forEach>
    </div>
    <input type="hidden" id="curlat" name="curlat" value="0"/>
    <input type="hidden" id="curlng" name="curlng" value="0"/>

    <!--input type="hidden" id="cururl" name="cururl" value="{php echo $this->createMobileurl('waprestlist', array(), true)}" /-->
</div>
</div>
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
    //区域
    $('.areatype > li').click(function () {
        var curlat = $('#curlat').val();
        var curlng = $('#curlng').val();
        var id = $(this).attr("data-id");
        //window.location.href = "{php echo $this->createMobileurl('waprestlist', array('storeid' => $storeid, 'sortid' => $sortid, 'typeid' => $typeid), true)}" + '&areaid=' + id + '&lat=' + curlat + '&lng=' + curlng;
    });
    //商家类型
    $('.shoptype > li').click(function () {
        var curlat = $('#curlat').val();
        var curlng = $('#curlng').val();
        var id = $(this).attr("data-id");
        //window.location.href = "{php echo $this->createMobileurl('waprestlist', array('storeid' => $storeid, 'sortid' => $sortid, 'areaid' => $areaid), true)}" + '&typeid=' + id + '&lat=' + curlat + '&lng=' + curlng;
    });
    //排序
    $('.shopsort > li').click(function () {
        var curlat = $('#curlat').val();
        var curlng = $('#curlng').val();

        var id = $(this).attr("data-id");
        //window.location.href = "{php echo $this->createMobileurl('waprestlist', array('storeid' => $storeid, 'typeid' => $typeid, 'areaid' => $areaid), true)}" + '&sortid=' + id + '&lat=' + curlat + '&lng=' + curlng;
    });
</script>
</body>
</html>