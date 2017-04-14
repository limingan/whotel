<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="Navfooter">
	<ul>
	<c:choose>
		<c:when test="${COMPANY_THEME!=null}">
			<c:forEach items="${COMPANY_THEME.columns}" var="column">
				<li class="nav_${vs.count}"><a href="${column.target}" ><i style="background:url(${column.iconUrl}) no-repeat;background-size:24px 24px;"></i>${column.name}</a></li>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<li class="nav_1"><a href="/oauth/website.do?comid=${WEIXINFAN_LOGIN_COMPANYID}" ><i></i>预订</a></li>
			<li class="nav_2"><a href="javascript: void(0)"><i></i>优惠</a></li>
			<li class="nav_3"><a href="/oauth/member/memberCoupon.do?comid=${WEIXINFAN_LOGIN_COMPANYID}"><i></i>卡券</a></li>
			<li class="nav_4"><a href="/oauth/member/index.do?comid=${WEIXINFAN_LOGIN_COMPANYID}" class="hover"><i></i>我的</a></li>
		</c:otherwise>
	</c:choose>
	</ul>
</div>