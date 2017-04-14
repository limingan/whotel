<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${WEIXINFAN_LOGIN_COMPANYNAME}</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"   content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common${THEME}.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain${THEME}.css?v=${version}">
</head>
<body>
<c:set var="headerTitle" value="${tradeType=='DEDUCT'?'兑换记录':'积分明细'}" scope="request"/>
<jsp:include page="card_header.jsp" />

<div class="transactionDL">
	<div class="am-tabs" data-am-tabs>
		<div class="am-tabs-bd">
			<div class="am-tab-panel am-fade <c:if test="${tradeType == '' || tradeType==null}">am-in am-active</c:if>" id="tab1">
				<ul class="transactionDL_list">
					<c:choose>
						<c:when test="${pointVOs != null && pointVOs.size() > 0}">
							<c:forEach items="${pointVOs}" var="pointVO">
								<li>
									<div class="money fr">
										<span <c:choose><c:when test="${pointVO.tradeType == 'CHARGE'}">class="colored"</c:when>
											<c:when test="${pointVO.tradeType == 'DEDUCT'}"></c:when>
											<c:otherwise>class="colorgreen"</c:otherwise></c:choose>>
											<fmt:formatNumber value="${pointVO.amount}" type="currency" pattern="#######.##" />
										</span>
										<c:choose>
											<c:when test="${pointVO.status=='A'}">
												未审核
											</c:when>
											<c:when test="${pointVO.status=='I'}">
												兑换中
											</c:when>
											<c:when test="${pointVO.status=='C'}">
												已发放
											</c:when>
											<c:when test="${pointVO.status=='X'}">
												已撤销
											</c:when>
											<c:otherwise>
												交易成功
											</c:otherwise>
										</c:choose>
									</div>
									<div class="transactionDL_list_title">
										<%-- <p class="transactionDL_list_pic">
											<c:if test="${pointVO.tradeType == 'CHARGE'}">
												<c:if test="${pointVO.amount > 0}">
										        	<img src="/static/front/hotel/images/jl-zz-icon.png"/>
										        </c:if>
										        <c:if test="${pointVO.amount < 0}">
										        	<img src="/static/front/hotel/images/jl-cah-icon.png"/>
										        </c:if>
									        </c:if>
									        <c:if test="${pointVO.tradeType == 'DEDUCT'}">
									        	<img src="${pointVO.imgUrl}"/>
									        </c:if>
										</p> --%>
										<h3><c:if test="${pointVO.remark==null||pointVO.remark==''}">&nbsp</c:if>${pointVO.remark}</h3>
										<span><fmt:formatDate value="${pointVO.createDate}" pattern="yyyy-MM-dd" /></span>
									</div>
								</li>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<li><p class="align_C colorhui">暂无记录</p></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
			<div class="am-tab-panel am-fade <c:if test="${tradeType == 'DEDUCT'}">am-in am-active</c:if>" id="tab2">
				<ul class="transactionDL_list">
				    <c:choose>
						<c:when test="${pointVOs != null && pointVOs.size() > 0}">
							<c:forEach items="${pointVOs}" var="pointVO">
								<li>
									<div class="money fr">
										<span>
											<fmt:formatNumber value="${pointVO.amount}" type="currency" pattern="#######.##" />
										</span>
											<c:choose>
												<c:when test="${pointVO.status=='A'}">
													未审核
												</c:when>
												<c:when test="${pointVO.status=='I'}">
													兑换中
												</c:when>
												<c:when test="${pointVO.status=='C'}">
													已发放
												</c:when>
												<c:when test="${pointVO.status=='X'}">
													已撤销
												</c:when>
												<c:otherwise>
													交易成功
												</c:otherwise>
											</c:choose>
									</div>
									<div class="transactionDL_list_title">
										<p class="transactionDL_list_pic">
											<img src="${pointVO.imgUrl}" />
										</p>
										<h3><c:if test="${pointVO.remark==null||pointVO.remark==''}">&nbsp</c:if>${pointVO.remark}</h3>
										<span><fmt:formatDate value="${pointVO.createDate}" pattern="yyyy-MM-dd" /></span>
									</div>
								</li>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<li><p class="align_C colorhui">暂无记录</p></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</div>
</div>

<script src="/static/common/js/jquery-1.11.2.js"></script> 
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script type="text/javascript">
</script>
</body>
</html>