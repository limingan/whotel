<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1">
<title>请求失败</title>
<style type="text/css">
body {
	background: #f8f8f8;
}
.notice {
	font-size: 24px;
	margin: 30% 10px;
	text-align: center;
}
p {
	padding: 10px 0;
	border-radius: 6px;
}
</style>
</head>
<body>
	<div class="notice"><span class="cryface"><img src="/static/front/images/bc500.png" style="width: 200px;height: 200px;"/></span>
	  <p>访问人数过多,请稍后再试...</p>
	</div>
	<div class="error-page" style="display:none">
		<div class="error-msg">
			<i class="icon-error"></i>
			<div class="error-msg-tip"><div class="error-code">500</div><div class="error-cont">操作错误，请重试！</div></div>
		</div>
		<div class="error-butn"><a href="javascript:history.back()" class="butn">点击返回</a></div>
		
		<div style="display:none">
		  错误码：<%=exception%><br>
			     异常： <%=request.getAttribute("javax.servlet.error.exception_type")%> <br>  
		<%
			Throwable ex = null;
			if (exception != null)
				ex = exception;
			if (request.getAttribute("javax.servlet.error.exception") != null)
				ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
		
			//记录日志
			Logger logger = LoggerFactory.getLogger("500.jsp");
			if(ex!=null){
				logger.error(ex.getMessage(), ex);
			}else{
				logger.error("500异常");
			}
		%>
		</div>
	</div>
</body>
</html>