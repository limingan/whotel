<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-cn" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="UTF-8">
<title>捷信达运营后台 | 登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="/static/metronic/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link href="http://cdn.bootcss.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
<link href="/static/metronic/assets/plugins/uniform/css/uniform.default.min.css" rel="stylesheet">
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME STYLES -->
<link href="/static/metronic/assets/css/style-metronic.css" rel="stylesheet">
<link href="/static/metronic/assets/css/style.css" rel="stylesheet">
<link href="/static/metronic/assets/css/style-responsive.css" rel="stylesheet">
<link href="/static/metronic/assets/css/plugins.css" rel="stylesheet">
<link href="/static/metronic/assets/css/themes/blue.css" rel="stylesheet" id="style_color">
<link href="/static/metronic/assets/css/pages/login.css" rel="stylesheet" type="text/css"/>
<link href="/static/metronic/assets/css/custom.css" rel="stylesheet">
</head>
<!-- BEGIN BODY -->
<body class="login">
  <!-- BEGIN LOGO -->
  <div class="logo">
  <!--    <img src="/static/metronic/assets/img/logo-big.png" alt="" />  -->
  </div>
  <!-- END LOGO -->
  <!-- BEGIN LOGIN -->
  <div class="content">
    <!-- BEGIN LOGIN FORM -->
    <form class="login-form" action="/admin/login.do" method="post">
      <h3 class="form-title">管理员登录</h3>
      <div class="form-group">
        <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
        <label class="control-label visible-ie8 visible-ie9">账号登录</label>
        <div class="input-icon">
          <i class="fa fa-user"></i> <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="用户名" name="userName" value="${user.username}" />
        </div>
      </div>
      <div class="form-group">
        <label class="control-label visible-ie8 visible-ie9">密码</label>
        <div class="input-icon">
          <i class="fa fa-lock"></i> <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="密码" name="password" />
        </div>
      </div>
     <div class="form-group">
        <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
        <label class="control-label visible-ie8 visible-ie9">验证码</label>
        <div class="input-icon">
          <i class="fa fa-eye "></i> 
          <input class="form-control placeholder-no-fix" style="display:inline-block;vertical-align:middle; width:150px" type="text" autocomplete="off" placeholder="验证码" name="captcha"/>
          <img src="" width="100" height="33" id="codeImg" onclick="changeCode();" title="单击图片更换验证码" />
        </div>
      </div>
      <div class="form-actions">
        <button type="submit" class="btn green pull-left">
                         登录 <i class="m-icon-swapright m-icon-white"></i>
        </button>
      </div>
    <!--    <div class="forget-password">
        <p>
          <a href="#" id="forget-password">忘记密码</a>
        </p>
      </div>
      <div class="create-account">
        <p>
          <a href="#" id="register-btn">注册用户</a>
        </p>
      </div>   -->
    </form>
    <!-- END LOGIN FORM -->
  </div>
  <!-- END LOGIN -->
  <!-- BEGIN COPYRIGHT -->
  <div class="copyright">2015 &copy; 捷信达 - 版权所有.</div>
  <!-- END COPYRIGHT -->
  <!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
  <!-- BEGIN CORE PLUGINS -->
  <!--[if lt IE 9]>
	<script src="/static/metronic/assets/plugins/respond.min.js"></script>
	<script src="/static/metronic/assets/plugins/excanvas.min.js"></script> 
	<![endif]-->
  <script src="/static/metronic/assets/plugins/jquery-1.10.2.min.js"></script>
  <script src="/static/metronic/assets/plugins/bootstrap/js/bootstrap.min.js"></script>
  <!-- END CORE PLUGINS -->
  <!-- BEGIN PAGE LEVEL PLUGINS -->
  <script src="/static/metronic/assets/plugins/jquery-validation/lib/jquery.validate.min.js"></script>
  <!-- END PAGE LEVEL PLUGINS -->
  <!-- BEGIN PAGE LEVEL SCRIPTS -->
  <script src="/static/admin/js/login.js" type="text/javascript"></script>
  <!-- END PAGE LEVEL SCRIPTS -->
  <script>
    jQuery(document).ready(function() {
      Login.init();
      changeCode();
	});
	function changeCode() {
		$("#codeImg").attr("src", "/kaptcha.jpg?t=" + new Date().getTime());
	}
  </script>
  <!-- END JAVASCRIPTS -->
  
  <jsp:include page="/common/bootbox.jsp"></jsp:include>
</body>
<!-- END BODY -->
</html>