<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html ng-app="diandanbao" class="ng-scope"><head><style type="text/css">@charset "UTF-8";[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide:not(.ng-hide-animate){display:none !important;}ng\:form{display:block;}</style>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta content="telephone=no" name="format-detection">
    <link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">
    <meta content="production" name="env">
    <title>我的订单</title>
    <link data-turbolinks-track="true" href="/static/meal/css/weixin.css?v=1" media="all" rel="stylesheet">
    <style type="text/css">@media screen{.smnoscreen {display:none}} @media print{.smnoprint{display:none}}</style></head>
<body>

<div style="height: 100%;" class="ng-scope">
    <div class="ddb-nav-header ng-scope" common-header="">
    <div class="nav-left-item"  onclick="javascript :history.back(-1);"><i class="fa fa-angle-left"></i></div>
    <div class="header-title ng-binding">我的订单</div>
        
    </div>

    <div class="orders-index-page main-view ng-scope" id="delivery-orders-index" style="padding-bottom:45px">
        
        <div class="space-12"></div>
        <c:forEach items="${orderPage.result}" var="order">
            <div class="order-item section ng-scope" onclick="window.location.href='/oauth/meal/orderDetail.do?orderId=${order.id}'">
                <a class="list-item">
                    <div class="time ng-binding">下单时间：<fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></div>
                </a>
                <a class="list-item">
                    <div class="name ng-binding">
                        <c:if test="${order.mealOrderType eq 'OUT'}">
                            [外卖]
                        </c:if>
                        ${order.name}</div>
                    <div class="variants overflow-ellipsis ng-binding">
                        共计 ${fn:length(order.items)} 件商品
                    </div>
				<span style="float:right;margin-top:-40px;margin-right:10px">
                        ${order.tradeStatus.label}
				</span>
                </a>
                <div class="list-item">
                    <div class="total-amount ng-binding"><span class="amount-label">金额：</span>￥<script> var fee = ${order.totalFee};document.write(fee.toFixed(2));</script></div>
                    <div class="operation">
                        <span class="button ng-binding ng-scope" style="">${order.status.label}</span>
                    </div>
                    <div class="space"></div>
                </div>
            </div>
        </c:forEach>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
<script>
    function jump(status) {
        window.location.href = "{php echo $this->createMobileUrl('order', array(), true)}" + "&status=" + status;
    }

    function go_detail(id) {
        window.location.href = "/oauth/meal/orderDetail.do?orderId=" + id;
    }
</script>

</body>
</html>