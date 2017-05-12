<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>	
<head>
<title>Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<meta name="keywords" content="Flat Dark Web Login Form Responsive Templates, Iphone Widget Template, Smartphone login forms,Login form, Widget Template, Responsive Templates, a Ipad 404 Templates, Flat Responsive Templates" />
<link href="/static/meal/css/login.css" rel='stylesheet' type='text/css' />
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css?v=${version}">
<!--webfonts-->
<!--//webfonts-->
<script src="/static/meal/js/jquery-1.11.3.min.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
</head>
<style>
body{
font-family: 'Microsoft Yahei', '微软雅黑';
 
}
</style>
<body>

 <!--SIGN UP-->
 <h1>服务员登录</h1>
<div class="login-form">
	<div class="clear"> </div>
	<div class="avtar">
		<img src="/static/meal/images/avtar.png" />
	</div>
	<form action="/oauth/waiter/login.do" method="post">
		<input type="text" class="text" value="Username" name="userName" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Username';}" >
		<div class="key">
		<input type="password" value="Password" name="password" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Password';}">
		</div>
		<div class="signin">
			<input type="submit" value="登录" >
		</div>
	</form>
</div>
 <div class="copy-rights">
	<p>Copyright 2017</p>
</div>

<div class="am-modal am-modal-alert" tabindex="-1" id="alertTip">
  <div class="am-modal-dialog">
    <div class="am-modal-bd" id="alertMsg">
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn">确定</span>
    </div>
  </div>
</div>

</body>
</html>
<script type="text/javascript">
	$(function(){
		var message = '${message}';
		if(message != null && message != '') {
			$("#alertMsg").html(message);
			$("#alertTip").modal();
		}
	});
</script>
