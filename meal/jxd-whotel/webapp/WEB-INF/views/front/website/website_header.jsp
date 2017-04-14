<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<header class="header">
  <div class="header_left"><a href="javascript:history.go(-1)" class="header_icon_back"></a></div>
  <h1 class="header_title">${headerTitle}</h1>
  <div class="header_right">
    <nav data-am-widget="menu" class="am-menu  am-menu-dropdown1"  
     data-am-menu-collapse  style="display:block" > <a href="javascript: void(0)" class="header_icon_bar am-menu-toggle"> </a>
      <ul class="am-menu-nav am-avg-sm-1 am-collapse">
        <li><a href="/oauth/website.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">首页</a></li>
    	<li><a href="/oauth/hotel/hotelSearch.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">酒店预订</a></li>
    	<li><a href="/oauth/member/index.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">会员中心</a></li>
    	<li><a href="/oauth/hotel/listHotelOrder.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">我的订单</a></li>
      </ul>
    </nav>
  </div>
</header>