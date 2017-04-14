<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- BEGIN SIDEBAR -->
<div class="page-sidebar-wrapper">
  <div class="page-sidebar navbar-collapse collapse">
    <!-- BEGIN SIDEBAR MENU -->
    <ul class="page-sidebar-menu">
     <li class="start <c:if test="${cur=='sel-index'}">active</c:if>">
        <a href="/admin/index.do"><i class="fa fa-home"></i><span class="title">首页</span><span class="selected"></span></a>
      </li>
       <li <c:if test="${cur=='sel-company'}">class="active"</c:if>>
        <a href="/admin/company/listCompany.do"><i class="fa fa-table"></i><span class="title">商户管理</span><span class="selected"></span></a>
      </li>
      <%-- <li <c:if test="${cur=='sel-website-template'}">class="active"</c:if>>
        <a href="/admin/website/listWebsiteTemplate.do"><i class="fa fa-asterisk"></i><span class="title">模板管理 </span><span class="selected"></span></a>
      </li> --%>
      <li <c:if test="${cur=='sel-website-template'}">class="active"</c:if>>
		<a href="javascript:;">
		<i class="fa fa-asterisk"></i>
		<span class="title">模板管理</span>
		<span class="arrow">
		</span>
		</a>
		<ul class="sub-menu">
			<li <c:if test="${cur_sub=='sel-website-list'}">class="active"</c:if>>
				<a href="/admin/website/listWebsiteTemplate.do">
				微网站模板
				</a>
			</li>
			<li <c:if test="${cur_sub=='sel-column-list'}">class="active"</c:if>>
				<a href="/admin/website/listColumnTemplate.do">
				栏目模板
				</a>
			</li>
			<li <c:if test="${cur_sub=='sel-theme-list'}">class="active"</c:if>>
				<a href="/admin/website/listThemeTemplate.do">
				主题
				</a>
			</li>
		</ul>
	 </li>
      
      <li <c:if test="${cur=='sel-columnLink'}">class="active"</c:if>>
        <a href="/admin/website/listColumnLink.do"><i class="fa fa-link"></i><span class="title">栏目链接 </span><span class="selected"></span></a>
      </li>
      <li <c:if test="${cur=='sel-notice'}">class="active"</c:if>>
        <a href="/admin/notice/listSysNotice.do"><i class="fa fa-bell"></i><span class="title">平台公告</span><span class="selected"></span></a>
      </li>
      
      <li <c:if test="${cur=='sel-sys'}">class="active"</c:if>>
		<a href="javascript:;">
		<i class="fa fa-cogs"></i>
		<span class="title">系统管理</span>
		<span class="arrow">
		</span>
		</a>
		<ul class="sub-menu">
			<li <c:if test="${cur_sub=='sel-role'}">class="active"</c:if>>
				<a href="/admin/sys/listSysRole.do">
				系统角色
				</a>
			</li>
			<li <c:if test="${cur_sub=='sel-module'}">class="active"</c:if>>
				<a href="/admin/sys/listSysModule.do">
				运营平台模块管理
				</a>
			</li>
			<li <c:if test="${cur_sub=='sel-companyModule'}">class="active"</c:if>>
				<a href="/admin/sys/listCompanyModule.do">
				商户平台模块管理
				</a>
			</li>
			<li <c:if test="${cur_sub=='sel-admin'}">class="active"</c:if>>
				<a href="/admin/sys/listSysAdmin.do">
				系统用户
				</a>
			</li>
		</ul>
	  </li>
      
      <li class="<c:if test="${cur=='sel-pwd'}">active</c:if>">
        <a href="/admin/auth/modifyPassword.do"><i class="fa fa-cog"></i><span class="title">修改密码</span><span class="selected"></span></a>
      </li>
    </ul>
    <!-- END SIDEBAR MENU -->
  </div>
</div>
<!-- END SIDEBAR -->