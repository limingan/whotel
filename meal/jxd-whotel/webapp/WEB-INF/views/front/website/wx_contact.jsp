<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>${WEIXINFAN_LOGIN_COMPANYNAME}</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"   content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain.css">
</head>

<body>
<c:set var="headerTitle" value="微信联系" scope="request"/>
<jsp:include page="website_header.jsp" />
<div class="wxcontact block_pd">
  <h3 class="align_C pd10">关注我们，获得更多讯息。<br />
  公众号：${publicNo.name}</h3><ul class="wxcontact_sb">
    <li class="ewm"><p class="ewm_c"><span ><img src="${company.qrcodeUrl}" alt="二维码"/></span> </p></li>
    <li class="sbw" style="position:relative"><p class="sbwbg"><span style="position:absolute;left:0;top:0;"><img src="${company.qrcodeUrl}" alt="二维码" style="opacity:0;width:140px;"/></span>长按指纹识别二维码</p></li>
  </ul>
  <p class="align_C pd15">
或通过查找，添加公众号关注我们</p>
</div>

<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
</body>
</html>
