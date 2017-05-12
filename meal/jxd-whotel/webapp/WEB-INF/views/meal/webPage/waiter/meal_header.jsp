<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:choose>
	<c:when test="${headerTitle != null && headerTitle != ''}">
		<header class="header_cy">
		  	<h1 class="header_title">${headerTitle}</h1>
		  	<div class="header_left">
			  		<a href="javascript:closeOrGoback()" class="header_icon_back"></a>
		  	</div>
		</header>
	</c:when>
	<c:otherwise>
		<header class="header_cy_fh">
		  		<a href="javascript:javascript:closeOrGoback()" class="header_icon_back"></a>
		</header>
	</c:otherwise>
</c:choose>
<script type="text/javascript">
	function closeOrGoback() {
		if(document.referrer === '') {
			WeixinJSBridge.call('closeWindow');
		} else {
			window.history.go(-1);
		}
	}
</script>