<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<html ng-app="diandanbao" class="ng-scope">
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta content="telephone=no" name="format-detection">
    <link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">
    <title>我的订单</title>
    <link data-turbolinks-track="true" href="/static/meal/css/weixin.css?v=1" media="all"
          rel="stylesheet">
    <!-- 新添加样式 -->
    <link rel="stylesheet" type="text/css" href="/static/meal/css/orderdetail.css">

    <style type="text/css">
        @media screen {
            .smnoscreen {
                display: none
            }
        }
    </style>
</head>
<body>
<div class="mModal1"
     style="position:fixed;width:100%;z-index: 901;height:100%;display:none;top:0;background-color:rgba(0, 0, 0, .5)"><a
        href="javascript:void(0)" style="height: 736px;"></a></div>
<div class="popupWindow" style="z-index:9999;display:none">
    <img class="close" src="/static/meal/images/close.png"/>

    <div id="popContent"></div>
</div>


<div ng-view="" style="height: 100%;" class="ng-scope">
    <input type="hidden" id="orderId" name="orderId" value="${order.id}"/>

    <div class="ddb-nav-header ng-scope" common-header="">
        <div class="nav-left-item" onclick="javascript :history.back(-1);"><i class="fa fa-angle-left"></i></div>
        <div class="header-title ng-binding">我的订单</div>
    </div>
    <!-- ngInclude:  -->
    <div class="ddb-nav-footer ng-scope" style="text-align:center;">
        <span class="button border-green <c:if test="${ order.tradeStatus != 'WAIT_PAY'}">ng-hide</c:if>"
              onclick="confirmorder()">确认</span>

        <span class="button border-blue ng-hide" ng-show="can_complete()" ng-click="complete()">完成</span>
        <span class="button border-red ng-hide" ng-show="can_pay_online()" ng-click="pay_online()">支付</span>
        <span class="button border-blue ng-hide" ng-show="can_append_itemable()"
              ng-click="go_append_itemable()">追加商品</span>
        <!--<span class="button ng-hide" ng-show="order &amp;&amp; order.pay_item_state=='paid'">已支付</span>
        <span class="button border-green ng-hide" ng-show="can_exchange_code()" ng-click="go_exchange_code()">兑换码</span>
        <span class="button border-red ng-binding  ng-hide" ng-click="hasten()" >催单</span>
        <span class="button border-green ng-binding disable ng-hide">呼叫服务员</span>-->
        <div class="ng-hide">
            <!--<div>-->
            <hr>
            <!-- <span
                 class="button border-green ng-binding ng-scope" ng-click="call_waiter(waiter_service_item.name)"
              <!--    ng-repeat="waiter_service_item in branch.waiter_service_items">水</span><!-- end ngRepeat: waiter_service_item in branch.waiter_service_items --><span
            <!--    class="button border-green ng-binding ng-scope" ng-click="call_waiter(waiter_service_item.name)"
              <!--   ng-repeat="waiter_service_item in branch.waiter_service_items">碗筷</span><!-- end ngRepeat: waiter_service_item in branch.waiter_service_items --><span
            <!--     class="button border-green ng-binding ng-scope" ng-click="call_waiter(waiter_service_item.name)"
                 ng-repeat="waiter_service_item in branch.waiter_service_items">椅子</span><!-- end ngRepeat: waiter_service_item in branch.waiter_service_items -->
            <!--   <span class="button border-green" ng-click="call_waiter('其他服务')">其他服务</span>
               <span class="button border-red" ng-click="show_options(false)">取消</span>!-->
        </div>
    </div>
    <div class="main-view order-show ng-scope" id="delivery-order-show">
        <div class="section">
            <a class="list-item arrow-right ng-binding" href="/oauth/meal/dishCatList.do?restaurantId=${rest.id}">
                <i class="fa fa-bookmark-o"></i> ${order.name}
            </a>
            <a class="list-item arrow-right ng-binding" href="tel:{$store['tel']}">
                <i class="fa fa-phone"></i> 商家客服：${rest.tel}
            </a>
            <!--<a class="list-item ng-scope" ng-click="deliveryman_location()" ng-if="can_track_deliveryman()">-->
            <!--<div class="service-button">-->
            <!--<i class="red fa fa-map-marker"></i> 配送员在哪儿-->
            <!--</div>-->
            <!--</a>-->
            <!--<a class="list-item ng-scope" ng-click="ask_for_invoice()"-->
            <!--ng-if="can_ask_for_invoice()">-->
            <!--<div class="service-button">-->
            <!--<i class="red fa fa-ticket"></i> 索要发票-->
            <!--</div>-->
            <!--</a>-->
            <c:if test="${ order.tradeStatus == 'WAIT_PAY'}">
                <a class="list-item ng-scope" onclick="cancelorder()">
                    <div class="service-button">
                        <i class="red fa fa-bell"></i> 取消订单
                    </div>
                </a>
            </c:if>
        </div>
        <div class="space-8"></div>

        <div class="order-state-section ng-scope <c:if test="${ order.tradeStatus == 'CLOSED' || order.tradeStatus == 'CANCELED'}">ng-hide</c:if>">
            <%--外卖订单 支付——>等待处理——>已确认——>已完成--%>
            <c:if test="${order.mealOrderType eq 'OUT'}">
                <div class="order-state ng-isolate-scope <c:if test="${order.tradeStatus == 'FINISHED' || order.tradeStatus == 'CONFIRM' || order.tradeStatus == 'SUCCESS'}">active</c:if>">
                    <div class="order-state-header">
                        <div class="square">
                            <div class="line-through ng-hide" ng-hide="hide_left"></div>
                        </div>
                        <i class="fa fa-check-circle"></i>

                        <div class="square">
                            <div class="line-through" ng-hide="hide_right"></div>
                        </div>
                    </div>
                    <div class="order-state-body ng-binding">等待处理</div>
                </div>
                <div class="order-state ng-isolate-scope <c:if test="${ order.tradeStatus == 'CONFIRM' || order.tradeStatus == 'SUCCESS'}">active</c:if> ">
                    <div class="order-state-header">
                        <div class="square">
                            <div class="line-through" ng-hide="hide_left"></div>
                        </div>
                        <i class="fa fa-check-circle"></i>

                        <div class="square">
                            <div class="line-through" ng-hide="hide_right"></div>
                        </div>
                    </div>
                    <div class="order-state-body ng-binding">已确认</div>
                </div>
                <div class="order-state ng-isolate-scope <c:if test="${ order.tradeStatus == 'SUCCESS'}">active</c:if> ">
                    <div class="order-state-header">
                        <div class="square">
                            <div class="line-through" ng-hide="hide_left"></div>
                        </div>
                        <i class="fa fa-check-circle"></i>

                        <div class="square">
                            <div class="line-through ng-hide" ng-hide="hide_right"></div>
                        </div>
                    </div>
                    <div class="order-state-body ng-binding">已完成</div>
                </div>
            </c:if>
            <%--点餐状态为 等待处理——>已确认——>已完成——>支付--%>
            <c:if test="${order.mealOrderType eq 'IN'}">
                <div class="order-state ng-isolate-scope <c:if test="${ order.tradeStatus == 'PENDING' || order.tradeStatus == 'CONFIRM' || order.tradeStatus == 'FINISHED' || order.tradeStatus == 'SUCCESS'}">active</c:if>">
                    <div class="order-state-header">
                        <div class="square">
                            <div class="line-through ng-hide" ng-hide="hide_left"></div>
                        </div>
                        <i class="fa fa-check-circle"></i>

                        <div class="square">
                            <div class="line-through" ng-hide="hide_right"></div>
                        </div>
                    </div>
                    <div class="order-state-body ng-binding">等待处理</div>
                </div>
                <div class="order-state ng-isolate-scope <c:if test="${ order.tradeStatus == 'CONFIRM' || order.tradeStatus == 'FINISHED' || order.tradeStatus == 'SUCCESS'}">active</c:if> ">
                    <div class="order-state-header">
                        <div class="square">
                            <div class="line-through" ng-hide="hide_left"></div>
                        </div>
                        <i class="fa fa-check-circle"></i>

                        <div class="square">
                            <div class="line-through" ng-hide="hide_right"></div>
                        </div>
                    </div>
                    <div class="order-state-body ng-binding">已确认</div>
                </div>
                <div class="order-state ng-isolate-scope <c:if test="${ order.tradeStatus == 'FINISHED' || order.tradeStatus == 'SUCCESS'}">active</c:if> ">
                    <div class="order-state-header">
                        <div class="square">
                            <div class="line-through" ng-hide="hide_left"></div>
                        </div>
                        <i class="fa fa-check-circle"></i>

                        <div class="square">
                            <div class="line-through ng-hide" ng-hide="hide_right"></div>
                        </div>
                    </div>
                    <div class="order-state-body ng-binding">已完成</div>
                </div>
            </c:if>

        </div>

        <c:if test="${ order.tradeStatus == 'CLOSED' || order.tradeStatus == 'CANCELED'}">
            <div class="order-state-section ng-scope">
                <div class="order-state ng-isolate-scope active">
                    <div class="order-state-header">
                        <div class="square">
                            <div class="line-through ng-hide" ng-hide="hide_left"></div>
                        </div>
                        <i class="fa fa-check-circle"></i>

                        <div class="square">
                            <div class="line-through ng-hide" ng-hide="hide_right"></div>
                        </div>
                    </div>
                    <div class="order-state-body ng-binding">已取消</div>
                </div>
            </div>
        </c:if>

        <div class="space-8"></div>
        <div class="section no-bottom-border">
            <div class="list-item time">
                <span class="ng-binding">类型：
                    <c:choose>
                        <c:when test="${order.mealOrderType eq 'OUT'}">外卖</c:when>
                        <c:otherwise>堂食</c:otherwise>
                    </c:choose>
                </span>&nbsp;&nbsp;<span class="ng-binding">订单号：${order.orderSn}</span><br>
                <span class="ng-binding">下单时间：<fmt:formatDate value="${order.createDate}"
                                                              pattern="yyyy-MM-dd HH:mm:ss"/></span>

            </div>
            <c:forEach items="${order.items}" var="item">
                <div class="list-item ng-scope">
                    <div class="name ng-binding">
                            ${item.name}
                    </div>
                    <div class="quantity-info">
                        <span class="quantity ng-binding">${item.itemQuantity}${item.unit}</span>
                        ×
                        <span class="price ng-binding">￥${item.itemPrice}</span>
                    </div>
                    <div class="total-info">
                        <button class="btn_add edit_button">编辑</button>
                    </div>
                </div>
            </c:forEach>

            <div class="list-item">合计：<span class="red ng-binding">${fn:length(order.items)}份</span>，<strong
                    class="red ng-binding">￥${order.totalFee}</strong>

                <c:if test="${order.mealOrderType eq 'OUT'}">
                         <span class="ng-scope">
                        （含
                        <c:if test="${empty hotel.deliverPrice}">0</c:if>
                        <c:if test="${!empty hotel.deliverPrice}">${hotel.deliverPrice}</c:if>
                        元配送费）
                    </span>
                </c:if>
                <c:if test="${order.mealOrderType eq 'IN'}">
                        <span class="ng-scope">
                        （含
                        <c:if test="${empty hotel.teaFee}">0 * ${order.guestNum}</c:if>
                        <c:if test="${!empty hotel.teaFee}">${hotel.teaFee} * ${order.guestNum}</c:if>
                        元茶位费）
                    </span>
                </c:if>

            </div>


            <div class="list-item">

                <div class="order-item ng-binding ng-scope">
                    支付类型: ${order.payMent.label}
                </div>

                <c:if test="${order.mealOrderType eq 'OUT'}">
                <div class="order-item ng-binding ng-scope">
                    姓名: ${order.contactName}

                </div>
                <div class="order-item ng-binding ng-scope">
                    电话: ${order.contactMobile}
                </div>
                <div class="order-item ng-binding ng-scope">
                    地址: ${order.addr}
                </div>

                </c:if>
                <div class="order-item ng-binding ng-scope">
                    备注: ${order.remark}
                </div>

            </div>
            <div class="juchi"></div>
        </div>
    </div>
</div>
<script src="/static/meal/js/jquery-1.11.3.min.js"></script>
<script>
    function confirmorder() {
        var url = "/oauth/meal/payCenter.do?orderId=" + $('#orderId').val();
        window.location.href = url;

    }

    function cancelorder() {
        var url = "/oauth/meal/cancelOrder.do?orderId=" + $('#orderId').val();
        if (confirm("确认取消吗?")) {
            $.ajax({
                url: url, type: "post", dataType: "json", timeout: "10000",
                data: {},
                success: function (data) {
                    if (data.code == 200) {
                        location.href = '/oauth/meal/orderList.do';
                    } else {
                        alert(data.message);
                    }
                }, error: function () {
                    alert("网络不稳定，请重新尝试!");
                }
            });
        }
    }
    $(function () {
        $('.edit_button').click(function () {
            $('.mModal1,.popupWindow').show();
        });
    });
</script>
</body>
</html>