<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>捷信达微信商户平台 | 密码找回</title>
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
<link href="/static/metronic/assets/css/pages/login-soft.css" rel="stylesheet" type="text/css" />
<link href="/static/metronic/assets/css/custom.css" rel="stylesheet">
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- BEGIN BODY -->
<body class="login">
	<!-- BEGIN LOGO -->
	<div class="logo">
		<!--    <img src="/static/metronic/assets/img/logo-big.png" alt="" />  -->
	</div>
	<!-- END LOGO -->
	<!-- BEGIN RESETPWD -->
	<div class="content">
		<!-- BEGIN RESETPWD FORM -->
		<form class="resetPwd-form" action="/company/resetPwd.do" method="post">
			<input type="hidden" name="email" value="${email}"/>
			<input type="hidden" name="code" value="${code}"/>
			<h3 class="form-title">请重新设置密码</h3>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">新密码</label>
				<div class="input-icon">
					<i class="fa fa-lock"></i> 
					<input class="form-control placeholder-no-fix" type="password" placeholder="新密码" name="password" id="password" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">确认密码</label>
				<div class="input-icon">
					<i class="fa fa-check"></i>
					<input class="form-control placeholder-no-fix" type="password" placeholder="确认密码" name="repassword" />
				</div>
			</div>
			<div class="form-actions">
				<button type="submit" class="btn green pull-right">
					确认 <i class="m-icon-swapright m-icon-white"></i>
				</button>
			</div>
		</form>
		<!-- END RESETPWD FORM -->
	</div>
	<!-- END RESETPWD -->
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
	<script src="/static/metronic/assets/plugins/jquery-validation/lib/jquery.validate.min.js" type="text/javascript"></script>
	<script src="/static/metronic/assets/plugins/jquery-validation/localization/messages_zh.js"></script>
	<script src="/static/metronic/assets/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="/static/company/js/resetPwd.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script>
    jQuery(document).ready(function() {
      ResetPwd.init();
    });
  </script>
	<!-- END JAVASCRIPTS -->

	<jsp:include page="/common/bootbox.jsp"></jsp:include>
</body>
<!-- END BODY -->
</html>