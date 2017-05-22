<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新增地址</title>
    <meta http-equiv="Content-Type" content="text/html" ; charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0 user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" type="text/css" href="/static/meal/css/wei_canyin_v1.8.4.css?v=1.1.2" media="all">


    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css"/>
    <!-- 页面样式 -->
    <link rel="stylesheet" type="text/css" href="/static/meal/css/addAddress.css">
    <script src="/static/meal/js/jquery-1.11.3.min.js"></script>
<body>

<div id="container">
    <div class="page_topbar">
        <a href="javascript:;" class="back" onclick="window.location.href='/oauth/meal/getAddrList.do'"><i class="fa fa-angle-left"></i></a>

        <div class="title">
		<c:choose>
		<c:when test="${guest.id != ''}">
		     编辑地址
		 </c:when>
		 <c:otherwise>
             新增地址
         </c:otherwise>
		</c:choose>
		</div>
    </div>

    <div id="container">
        <div class="address_main">
            <input type="hidden" id="guestId" value="${guest.id}">
            <input type="hidden" id="spuid" value="3074">

            <div class="line"><input type="text" placeholder="姓名" id="name" value="${guest.name}"></div>
            <div class="line"><input type="text" placeholder="联系电话" id="mobile" value="${guest.mobile}"></div>

            <script type="text/javascript" src="/static/meal/js/area.js"></script>
            <script type="text/javascript">_init_area();</script>

            <div class="line"><input type="text" placeholder="地址" id="address" value="${guest.address}"></div>
        </div>

        <div class="address_sub1" onclick="copyText()">确认添加</div>
        <div class="address_sub2" onclick="history.go(-1)">取消</div>
    </div>
    <br>

    <div id="toastId2" class="toasttj2" style="display: none; opacity: 0;"></div>
    <div id="BgDiv1"></div>
</div>
</body>

<!-- <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script> -->
<script language="javascript" src="/static/meal/js/jquery.gcjs.js"></script>
<script language="javascript">
    function copyText() {
        var guestId = $("#guestId").val();
        var mobile = $("#mobile").val();
        var name = $("#name").val();
        var address = $("#address").val();
        if (mobile == "") {
            alert('请输入手机号码!');
            return false;
        }
        if (name == "") {
            alert('请输入姓名!');
            return false;
        }
        if (address == "") {
            alert('请输入地址!');
            return false;
        }

        $.ajax({
            url: "/oauth/meal/saveAddr.do", type: "post", dataType: "json", timeout: "10000",
            data: {
                id: guestId,
                name: name,
                mobile: mobile,
                address: address
            },
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
    }
</script>
</html>