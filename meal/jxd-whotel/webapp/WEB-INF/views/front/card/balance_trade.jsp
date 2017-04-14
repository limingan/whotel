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
<c:set var="headerTitle" value="储值帐户" scope="request"/>
<jsp:include page="card_header.jsp" />

<div class="uesrtop_box pd30">
  <div class="uesrtop_ye"> <a href="/pay/toCardCharge.do?showwxpaytitle=1" class="uesrtop_link">充值<i class="Wmore_icon"></i></a>
    <div class="uesrtop_ye_info">
    	<c:choose>
			<c:when test="${tradeType == '' || tradeType==null}">
		      	<p>帐户余额</p>
		      	<h1><b>￥</b><fmt:formatNumber value="${memberVO.balance}" type="currency" pattern="#######.##"/></h1>
			</c:when>
			<c:when test="${tradeType == 'CHARGE'}">
		      	<p>充值总额</p>
		      	<h1><b>￥</b><fmt:formatNumber value="${memberVO.incamt}" type="currency" pattern="#######.##"/></h1>
			</c:when>
			<c:when test="${tradeType == 'DEDUCT'}">
		      	<p>消费总额</p>
		      	<h1><b>￥</b><fmt:formatNumber value="${memberVO.deductamt}" type="currency" pattern="#######.##"/></h1>
			</c:when>
		</c:choose>
    </div>
  </div>
</div>
<%-- <div class="transactionDL">
<div class="transactionDL_title"><p class="hover"><a href="javascript:" id="allTrade">全部交易</a></p>
  <p>
	<select data-am-selected="{placeholder: '交易类型'}" id="tradeType">
	  <option value="">交易类型</option>
	  <option value="CHARGE" <c:if test="${tradeType == 'CHARGE'}">selected</c:if>>充值</option>
	  <option value="DEDUCT" <c:if test="${tradeType == 'DEDUCT'}">selected</c:if>>消费</option>
	</select>
   </p>
  </div>
  <ul class="transactionDL_list">
    
    <c:choose>
    	<c:when test="${tradeVOs != null && tradeVOs.size() > 0}">
    		<c:forEach items="${tradeVOs}" var="tradeVO">
		    	<li>
			      <div class="money fr">
			      <c:if test="${tradeVO.amount > 0}">
			      <span class="colored">
			      <fmt:formatNumber value="${tradeVO.amount}" type="currency" pattern="#######.##"/>
			      </span> 
			      </c:if>
			      <c:if test="${tradeVO.amount < 0}">
			      <span class="colorgreen">
			      <fmt:formatNumber value="${tradeVO.amount}" type="currency" pattern="#######.##"/>
			      </span> 
			      </c:if>
			                  交易成功
			      </div>
			      <div class="transactionDL_list_title">
			        <p class="transactionDL_list_pic">
			        <c:if test="${tradeVO.amount > 0}">
			         <img src="/static/front/hotel/images/jl-cah-icon.png"/>
			        </c:if>
			        <c:if test="${tradeVO.amount < 0}">
			         <img src="/static/front/hotel/images/jl-zz-icon.png"/>
			        </c:if>
			        </p>
			        <h3>${tradeVO.remark}</h3>
			        <span>
			        <fmt:formatDate value="${tradeVO.createDate}" pattern="yyyy-MM-dd"/>
			        </span>
			      </div>
			    </li>
		    </c:forEach>
    	</c:when>
    	<c:otherwise>
    		<li><p class="align_C colorhui">暂无记录</p></li>
    	</c:otherwise>
    </c:choose>
  </ul>
</div> --%>



<div class="transactionDL">
	<div class="am-tabs" data-am-tabs>
		<ul class="am-tabs-nav am-nav am-nav-tabs transactionDL_title">
			<li class="tradeType <c:if test="${tradeType == '' || tradeType==null}">am-active</c:if>" value=""><a href="#tab1">全部交易</a></li>
			<li class="tradeType <c:if test="${tradeType == 'CHARGE'}">am-active</c:if>" value="CHARGE"><a href="#tab2">充值记录</a></li>
			<li class="tradeType <c:if test="${tradeType == 'DEDUCT'}">am-active</c:if>" value="DEDUCT" ><a href="#tab3">消费记录</a></li>
		</ul>
		<div class="am-tabs-bd">
			<div class="am-tab-panel am-fade <c:if test="${tradeType == '' || tradeType==null}">am-in am-active</c:if>" id="tab1">
				<ul class="transactionDL_list">
					<c:choose>
						<c:when test="${tradeVOs != null && tradeVOs.size() > 0}">
							<c:forEach items="${tradeVOs}" var="tradeVO">
								<li>
									<div class="money fr">
										<span class="colorgreen">
											<fmt:formatNumber value="${tradeVO.amount}" type="currency" pattern="#######.##" />
										</span> 交易成功
									</div>
									<div class="transactionDL_list_title">
										<p class="transactionDL_list_pic">
											<c:choose>
												<c:when test="${tradeVO.amount>0}">
													<img src="/static/front/hotel/images/jl-zz-icon.png" />
												</c:when>
												<c:otherwise>
													<img src="/static/front/hotel/images/jl-cah-icon.png" />
												</c:otherwise>
											</c:choose>
										</p>
										<c:choose>
											<c:when test="${tradeVO.amount>0}">
												<h3>充值</h3>
											</c:when>
											<c:otherwise>
												<h3>消费</h3>
											</c:otherwise>
										</c:choose>
										<span style="width: 70%;display: inline-block;">
											${tradeVO.remark}<br/>
											<fmt:formatDate value="${tradeVO.createDate}" pattern="yyyy-MM-dd" />
										</span>
										<%-- <span><fmt:formatDate value="${tradeVO.createDate}" pattern="yyyy-MM-dd" /></span> --%>
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
			<div class="am-tab-panel am-fade <c:if test="${tradeType == 'CHARGE'}">am-in am-active</c:if>" id="tab2">
				<ul class="transactionDL_list">
					<c:choose>
						<c:when test="${tradeVOs != null && tradeVOs.size() > 0}">
							<c:forEach items="${tradeVOs}" var="tradeVO">
								<li>
									<div class="money fr">
										<span class="colored">
											<fmt:formatNumber value="${tradeVO.amount}" type="currency" pattern="#######.##" />
										</span> 交易成功
									</div>
									<div class="transactionDL_list_title">
										<p class="transactionDL_list_pic">
											<img src="/static/front/hotel/images/jl-zz-icon.png" />
										</p>
										<h3>充值</h3>
										<span><fmt:formatDate value="${tradeVO.createDate}" pattern="yyyy-MM-dd" /></span>
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
			<div class="am-tab-panel am-fade <c:if test="${tradeType == 'DEDUCT'}">am-in am-active</c:if>" id="tab3">
				<ul class="transactionDL_list">
				    <c:choose>
						<c:when test="${tradeVOs != null && tradeVOs.size() > 0}">
							<c:forEach items="${tradeVOs}" var="tradeVO">
								<li>
									<div class="money fr">
										<span>
											<fmt:formatNumber value="${tradeVO.amount}" type="currency" pattern="#######.##" />
										</span> 交易成功
									</div>
									<div class="transactionDL_list_title">
										<p class="transactionDL_list_pic">
											<img src="/static/front/hotel/images/jl-cah-icon.png" />
										</p>
										<h3>消费</h3>
										<span style="width: 70%;display: inline-block;">
											${tradeVO.remark}<br/>
											<fmt:formatDate value="${tradeVO.createDate}" pattern="yyyy-MM-dd" />
										</span>
										<%-- <span><fmt:formatDate value="${tradeVO.createDate}" pattern="yyyy-MM-dd" /></span> --%>
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
	$(function() {
		$(".tradeType").click(function() {
			var tradeType = $(this).attr("value");
			if(tradeType==""){
				document.location = "/oauth/member/balanceTrade.do";
			}else{
				document.location = "/oauth/member/balanceTrade.do?tradeType="+tradeType;
			}
		});
	});
</script>
</body>
</html>