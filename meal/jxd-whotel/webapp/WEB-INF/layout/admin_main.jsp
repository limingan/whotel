<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title><decorator:title default="捷信达| 运营中心" /></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta content="" name="description"/>
<meta content="" name="author"/>
<meta name="MobileOptimized" content="320">
<link rel="icon" href="/static/company/images/favicon.ico" mce_href="/static/company/images/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="/static/company/images/favicon.ico" mce_href="/static/company/images/favicon.ico" type="image/x-icon">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="/static/metronic/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="/static/metronic/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="/static/metronic/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->

<link rel="stylesheet" href="/static/metronic/assets/plugins/select2/select2_metro.css" type="text/css"/>
<link rel="stylesheet" href="/static/metronic/assets/plugins/data-tables/DT_bootstrap.css" type="text/css"/>


<!-- BEGIN THEME STYLES -->
<link href="/static/metronic/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
<link href="/static/metronic/assets/css/style.css" rel="stylesheet" type="text/css"/>
<link href="/static/metronic/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
<link href="/static/metronic/assets/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="/static/metronic/assets/css/themes/default.css" rel="stylesheet" theme-dir="/static/metronic/assets/css/themes/" type="text/css" id="style_color"/>
<link href="/static/metronic/assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->
<script src="/static/metronic/assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="/static/metronic/assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="/static/metronic/assets/plugins/jquery-validation/lib/jquery.validate.min.js" type="text/javascript"></script>
<script src="/static/metronic/assets/plugins/jquery-validation/lib/additional-methods.min.js" type="text/javascript" ></script>
<script src="/static/metronic/assets/plugins/jquery-validation/localization/messages_zh.js" type="text/javascript" ></script>
<decorator:head />
<style>
.page-content{padding-bottom:0 !important;}
.portlet{margin-bottom:0;}
</style>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">

<jsp:include page="admin_header.jsp" />
<div class="clearfix">
</div>


<!-- BEGIN CONTAINER -->
<div class="page-container">
	<jsp:include page="admin_left.jsp" />
	<!-- BEGIN CONTENT -->
	<decorator:body />
	<!-- END CONTENT -->
</div>
<!-- END CONTAINER -->

<jsp:include page="admin_footer.jsp" />
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="/static/metronic/assets/plugins/respond.min.js"></script>
<script src="/static/metronic/assets/plugins/excanvas.min.js"></script> 
<![endif]-->

<script src="/static/metronic/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="/static/metronic/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="/static/metronic/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="/static/metronic/assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->

<!-- BEGIN PAGE LEVEL PLUGINS -->
<script type="text/javascript" src="/static/metronic/assets/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="/static/metronic/assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="/static/metronic/assets/plugins/data-tables/DT_bootstrap.js"></script>
<!-- END PAGE LEVEL PLUGINS -->

<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="/static/metronic/assets/scripts/admin_app.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script>
jQuery(document).ready(function() {    
   App.init(); // initlayout and core plugins
});
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>