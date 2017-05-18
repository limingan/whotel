<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no">
    <title>地址列表</title>
    <link rel="stylesheet" type="text/css" href="/static/meal/css/wei_canyin_v1.8.4.css?v=1.1.2" media="all">
    <link rel="stylesheet" type="text/css" href="/static/meal/css/mobiscroll.custom-2.6.2.min.css" media="all">
    <link data-turbolinks-track="true" href="/static/meal/css/font.css?v=1" media="all" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/static/meal/css/wei_canyin_v1.8.4.css?v=1.1.2" media="all">
    <link rel="stylesheet" type="text/css" href="/static/meal/css/wei_dialog_v1.2.1.css?v=1.0" media="all">
    <script type="text/javascript" src="/static/meal/js/wei_webapp_v2_common_v1.9.4.js"></script>
    <script type="text/javascript" src="/static/meal/js/wei_dialog_v1.9.9.js?v=1"></script>
    <script src="/static/meal/js/jquery-1.11.3.min.js"></script>

    <!-- 页面样式 -->
    <link rel="stylesheet" type="text/css" href="/static/meal/css/addressList.css">
</head>
<body id="page_intelOrder" class="myOrderCon">
<div class="ddb-nav-header ng-scope">
    <a class="nav-left-item" href="/oauth/meal/menu.do"><i class="fa fa-angle-left"></i></a>

    <div class="header-title ng-binding">地址列表</div>
    <a class="nav-right-item" href="#"></a>
</div>
<div class="center container">

    <section>
        <article>
            <ul class="myorder" id="list">

                <c:forEach items="${list}" var="guest">
                    <li class="list-item">

                        <img src="/static/meal/images/icon_add.png" class="item-img" />
                        <span class="item-txt">${guest.address}</span>
                        <c:if test="${guest.isDefault == 1}"><span style="color:orange">（默认）</span></c:if>
                        <div class="item-detail">
                            <span>${guest.name}</span>
                            <span>${guest.mobile}</span>
                        </div>

                        <c:if test="${guest.isDefault != 1}">
                            <h2>
                                <button guestId="${guest.id}" class="btn_add btn_setdefault btn-to-default"
                                        onclick="location.href='">设为默认
                                </button>
                            </h2>
                        </c:if>
                        <h2>
                            <button guestId="${guest.id}" class="btn_add btn-to-edit"
                                    onclick="location.href='/oauth/meal/editAddr.do?id=${guest.id}'">编辑
                            </button>
                        </h2>
                        <h2>
                            <button guestId="${guest.id}" class="btn_add delete-btn btn-to-delete"
                                    onclick="location.href='">删除
                            </button>
                        </h2>

                    </li>
                </c:forEach>
            </ul>
        </article>
    </section>
	<div class="btn-box">
	   <a href="/oauth/meal/editAddr.do" style="color:white;">新增</a>
	</div>
</div>

<script>
    $(function () {
        function prevent_default(e) {
            e.preventDefault();
        }

        function disable_scroll() {
            $(document).on('touchmove', prevent_default);
        }

        function enable_scroll() {
            $(document).unbind('touchmove', prevent_default)
        }

        var x;
        $('#list li > a')
                .on('touchstart', function (e) {
                    console.log(e.originalEvent.pageX)
                    $('.swipe-delete li > a.open').css('left', '0px').removeClass('open') // close em all
                    $(e.currentTarget).addClass('open')
                    x = e.originalEvent.targetTouches[0].pageX // anchor point
                })
                .on('touchmove', function (e) {
                    var change = e.originalEvent.targetTouches[0].pageX - x
                    change = Math.min(Math.max(-100, change), 100) // restrict to -100px left, 0px right
                    e.currentTarget.style.left = change + 'px'
                    if (change < -10) disable_scroll() // disable scroll once we hit 10px horizontal slide
                })
                .on('touchend', function (e) {
                    var left = parseInt(e.currentTarget.style.left)
                    var new_left;
                    if (left < -35) {
                        new_left = '-100px'
                    } else if (left > 35) {
                        new_left = '100px'
                    } else {
                        new_left = '0px'
                    }
                    // e.currentTarget.style.left = new_left
                    $(e.currentTarget).animate({left: new_left}, 200)
                    enable_scroll()
                });

        $('.delete-btn').click(function () {
            var obj = $(this);
            var guestId = $(this).attr("guestId");
            MDialog.confirm('', '是否删除该地址？', null, '确定', function () {
                var url = "/oauth/meal/deleteAddr.do?id=" + guestId;
                $.ajax({
                    url: url, type: "get", dataType: "json", timeout: "10000",
                    success: function (data) {
                        if (data.code == 200) {
                            location.href = '/oauth/meal/getAddrList.do';
                        } else {
                            alert(data.message);
                        }
                    }, error: function () {
                        alert("网络不稳定，请重新尝试!");
                    }
                });
            })
        });

        $('.btn_setdefault').click(function () {
            var obj = $(this);
            var guestId = $(this).attr("guestId");
            MDialog.confirm('', '是否设置该地址为默认？', null, '确定', function () {
                var url = "/oauth/meal/setDefaultAddr.do?id=" + guestId;
                $.ajax({
                    url: url, type: "get", dataType: "json", timeout: "10000",
                    success: function (data) {
                        if (data.code == 200) {
                            location.href = '/oauth/meal/getAddrList.do';
                        } else {
                            alert(data.message);
                        }
                    }, error: function () {
                        alert("网络不稳定，请重新尝试!");
                    }
                });
            })
        });
    });
</script>
</body>
</html>