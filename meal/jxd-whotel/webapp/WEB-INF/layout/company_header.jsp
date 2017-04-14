<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!-- BEGIN HEADER -->
<div class="header navbar navbar-inverse navbar-fixed-top">
	<!-- BEGIN TOP NAVIGATION BAR -->
	<div class="header-inner">
		<!-- BEGIN LOGO -->
	  	<a class="navbar-brand" href="/" style="padding:10px; font-weight:bold; font-size:18px">
	 	捷信达微信商户平台
		</a> 
		<!-- END LOGO -->
		<!-- BEGIN RESPONSIVE MENU TOGGLER -->
		<a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
		</a>
		<!-- END RESPONSIVE MENU TOGGLER -->
		<!-- BEGIN TOP NAVIGATION MENU -->
		<ul class="nav navbar-nav pull-right">
			<!-- BEGIN USER LOGIN DROPDOWN -->
			<li class="dropdown user">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
				
				<c:choose>
					<c:when test="${COMPANY_ADMIN_LOGIN_AVATAR != null &&  COMPANY_ADMIN_LOGIN_AVATAR !=''}">
						<img alt="" src="${COMPANY_ADMIN_LOGIN_AVATAR}" width="29" height="29"/>
					</c:when>
					<c:otherwise>
						<img alt="" src="/static/metronic/assets/img/avatar.png" width="29" height="29"/>
					</c:otherwise>
				</c:choose>
				<span class="username">
					${COMPANY_ADMIN_LOGIN_COMPANY}|${COMPANY_ADMIN_LOGIN_KEY}
				</span>
				<i class="fa fa-angle-down"></i>
				</a>
				<ul class="dropdown-menu">
					<li>
						<a href="/company/logout.do"><i class="fa fa-key"></i> 安全退出</a>
					</li>
				</ul>
			</li>
			
			<li>
			<div class="theme-panel hidden-xs hidden-sm">
				<div class="toggler js-sidebar-toggler" title="缩放菜单" style="display: block;">
				</div>
			</div>
			</li>
		</ul>
		<!-- END TOP NAVIGATION MENU -->
	</div>
	<!-- END TOP NAVIGATION BAR -->
</div>
<!-- END HEADER -->