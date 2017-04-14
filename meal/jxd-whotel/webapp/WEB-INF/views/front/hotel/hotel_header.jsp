<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<header class="header">
  <div class="header_left">
  <c:if test="${backUrl != null && backUrl != ''}">
  <a href="${backUrl}" class="header_icon_back"></a>
  </c:if>
  <c:if test="${backUrl == null || backUrl == ''}">
  <a href="javascript:history.go(-1)" class="header_icon_back"></a>
  </c:if>
  </div>
  <h1 class="header_title">${headerTitle}</h1>
  <div class="header_right">
    <nav data-am-widget="menu" class="am-menu  am-menu-dropdown1"  
     data-am-menu-collapse  style="display:block" > <a href="javascript: void(0)" class="header_icon_bar am-menu-toggle"> </a>
      <ul class="am-menu-nav am-avg-sm-1 am-collapse">
        <c:choose>
			<c:when test="${COMPANY_THEME!=null}">
				<c:forEach items="${COMPANY_THEME.columns}" var="column">
					<li><a href="${column.target}">${column.name}</a></li>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<li><a href="/oauth/website.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">首页</a></li>
			   	<li><a href="/oauth/hotel/hotelSearch.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">酒店预订</a></li>
			   	<li><a href="/oauth/member/index.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">会员中心</a></li>
			   	<li><a href="/oauth/hotel/listHotelOrder.do?comid=${WEIXINFAN_LOGIN_COMPANYID}">我的订单</a></li>
			</c:otherwise>
		</c:choose>
      </ul>
    </nav>
  </div>
</header>