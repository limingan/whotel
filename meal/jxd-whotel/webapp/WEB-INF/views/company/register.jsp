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
<title>捷信达微信商户平台 | 注册</title>
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
<link href="/static/metronic/assets/css/themes/default.css" rel="stylesheet" id="style_color">
<link href="/static/metronic/assets/css/pages/login-soft.css" rel="stylesheet" type="text/css"/>
<link href="/static/metronic/assets/css/custom.css" rel="stylesheet">
<!-- END THEME STYLES -->
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
    <!-- BEGIN REGISTRATION FORM -->
	<form class="register-form" action="/company/register.do" method="post">
		<h3>商户注册</h3>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">商户全称</label>
			<div class="input-icon">
				<i class="fa fa-font"></i>
				<input class="form-control placeholder-no-fix" type="text" placeholder="商户全称" name="companyName"/>
			</div>
		</div>
		<div class="form-group">
			<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
			<label class="control-label visible-ie8 visible-ie9">商户编码</label>
			<div class="input-icon">
				<i class="fa fa-filter"></i>
				<input class="form-control placeholder-no-fix" type="text" placeholder="商户编码" name="code"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">登录账号</label>
			<div class="input-icon">
				<i class="fa fa-user"></i>
				<input class="form-control placeholder-no-fix" type="text" placeholder="登录账号" name="account"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">密码</label>
			<div class="input-icon">
				<i class="fa fa-lock"></i>
				<input class="form-control placeholder-no-fix" type="password" placeholder="密码" name="password" id="password"/>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">确认密码</label>
			<div class="input-icon">
				<i class="fa fa-check"></i>
				<input class="form-control placeholder-no-fix" type="password" placeholder="确认密码" name="repassword"/>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-md-3 control-label">集团</label>
			<div class="col-md-4">
			<label class="radio-inline">
			<span><input type="radio" name="group" value="false" checked></span> 否</label>
			<label class="radio-inline">
			<input type="radio" name="group" value="true"> 是</label>
			 </div>
		</div>
		
		<div class="form-actions">
		    <button id="register-back-btn" type="button" class="btn" onclick="document.location='/company/toLogin.do'">
			<i class="m-icon-swapleft"></i> 登录 </button>
			<button type="submit" id="register-submit-btn" class="btn green pull-right">
			确认注册 <i class="m-icon-swapright m-icon-white"></i>
			</button>
		</div>
	</form>
	<!-- END REGISTRATION FORM -->
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
  <script src="/static/metronic/assets/plugins/jquery-validation/localization/messages_zh.js"></script>
  <script src="/static/metronic/assets/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
  <!-- END PAGE LEVEL PLUGINS -->
  <!-- BEGIN PAGE LEVEL SCRIPTS -->
  <script src="/static/company/js/register.js" type="text/javascript"></script>
  <!-- END PAGE LEVEL SCRIPTS -->
  <script>
    jQuery(document).ready(function() {
      Register.init();
    });
  </script>
  <!-- END JAVASCRIPTS -->
  
  <jsp:include page="/common/bootbox.jsp"></jsp:include>
</body>
<!-- END BODY -->
</html>