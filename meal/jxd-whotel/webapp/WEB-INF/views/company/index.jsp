<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微信商户平台 | 首页</title>
<style type="text/css">
.title_bar {
    padding: 0 20px;
    background-color: #f4f5f9;
    border-bottom: 1px solid #e7e7eb;
    line-height: 40px;
}
.notices_box {
    line-height: 40px;
    border-radius: 0;
    -moz-border-radius: 0;
    -webkit-border-radius: 0;
    background-color: #fff;
    border: 1px solid #e7e7eb;
    padding: 0px;
}
.title_bar h3 {
    font-size: 14px;
    font-weight: 400;
    font-style: normal;
}
.mp_news_item:first-child {
    border-top-width: 0;
}
.mp_news_item {
    position: relative;
    border-top: 1px solid #e7e7eb;
    font-size: 14px;
}
.mp_news_item a strong {
    display: block;
    font-weight: 400;
    font-style: normal;
    width: 52em;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
    color: #000;
}
.mp_news_item a:hover strong {
    display: block;
    font-weight: 400;
    font-style: normal;
    width: 52em;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-wrap: normal;
   	color: #459ae9;
    text-decoration:none;
}
.mp_news_item a:hover{
    text-decoration:none;
}
.icon_common.new {
    margin-left: 6px;
    background: url("/mpres/zh_CN/htmledition/comm_htmledition/style/base/base_z2b638f.png") 0 -3428px no-repeat;
    width: 18px;
    height: 12px;
    vertical-align: middle;
    display: inline-block;
    line-height: 100px;
    overflow: hidden;
}
.mp_news_item .read_more {
    position: absolute;
    right: 20px;
    top: 0;
    color: #8d8d8d;
    text-decoration: none;
}



/* .index_tap_item a {
    display: block;
    color: #fff;
    text-decoration: none;
}
.index_tap_item {
    float: left;
}
.size1of2 {
    width: 50%;
}
.index_tap.added .inner {
    background-color: #7cbae5;
    border: 1px solid #6eb0dd;
    height: 125px;
}

.index_tap .tap_inner {
    display: block;
}

.index_tap .left_border{
    border-left: 1px solid #6fa7ce;
}

.index_tap a {
    padding-top: 26px;
    padding-bottom: 26px;
}

.index_tap .inner {
    overflow: hidden;
}
.index_tap.added {
    width: 65%;
}
.index_tap {
    display: inline-block;
    vertical-align: top;
    text-align: center;
}
.index_show_area {
    padding: 20px 15px 16px;
    text-align: justify;
    text-justify: distribute-all-lines;
    font-size: 0;
}

.index_tap.total {
    width: 34%;
}
.index_tap {
    display: inline-block;
    vertical-align: top;
    text-align: center;
}
.index_tap.total .inner {
    background-color: #60d295;
    border: 1px solid #57c78b;
    height: 125px;
}
.index_tap .inner {
    overflow: hidden;
}
.index_tap.total .index_tap_item {
    width: 100%;
}

.index_tap_item .title {
    display: block;
    font-weight: 400;
    font-style: normal;
    font-size: 16px;
    letter-spacing: 2px;
    margin-top: -10px;
}

.index_tap_item .number {
    margin-left: 5px;
    font-weight: 400;
    font-style: normal;
    vertical-align: middle;
    font-size: 35px;
}

.index_show_area:after {
    display: inline-block;
    width: 100%;
    height: 0;
    font-size: 0;
    margin: 0;
    padding: 0;
    overflow: hidden;
    content: ".";
} */
</style>
</head>
<c:set var="cur" value="sel-index" scope="request"/>
<div class="page-content-wrapper"><div class="page-content">
	<form action="/company/index.do" id="pageForm" method="post">
   		<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
	</form>
	<!-- <div class="index_show_area">
		<div class="index_tap added">
			<ul class="inner">
				<li class="grid_item size1of2 index_tap_item added_message">
					<a href="">
						<span class="tap_inner"> <img src="/static/company/images/people.png"/> 
						<em class="number">0</em> <strong class="title">新增会员</strong>
					</span>
				</a>
				</li>
				<li class="grid_item size1of2 no_extra index_tap_item added_fans">
					<a href="">
						<span class="tap_inner no_extra left_border"> <i class="icon_index_tap"></i> <img src="/static/company/images/people.png"/>
						<em class="number">0</em> <strong class="title">新增粉丝</strong>
					</span>
				</a>
				</li>
			</ul>
		</div>&nbsp;
		<div class="index_tap total">
			<ul class="inner">
				<li class="index_tap_item total_fans extra"><a href="">
					<span class="tap_inner"><img src="/static/company/images/peoples.png"/>
						<em class="number">357</em> <strong class="title">总用户数</strong>
					</span>
				</a></li>
			</ul>
		</div>
	</div> -->
	
	<div class="notices_box">
		<div class="title_bar"><h3>系统公告</h3></div>
		<ul class="feeds" style="padding: 0 20px;">
		   <c:forEach items="${page.result}" var="notice" varStatus="vs">
		   		<li class="mp_news_item"><a href="/company/indexNoticeDetails.do?id=${notice.id}">
		   			<strong>${notice.title}<c:if test="${vs.index == 0}"><img src="/static/company/images/new.png"/></c:if></strong>
		   			<span class="read_more" style="font-style: italic;">
		   				阅读量：${notice.readQty==null?0:notice.readQty}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		   				<fmt:formatDate value="${notice.createTime}" pattern="yyyy-MM-dd HH:mm"/>
		   			</span>
		   		</a></li>
		   </c:forEach>
		</ul>
	</div>
	<div class="row">${page.pageNavigation}</div>
</div></div>
<script src="/static/common/js/paging.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/bootbox.jsp"></jsp:include>