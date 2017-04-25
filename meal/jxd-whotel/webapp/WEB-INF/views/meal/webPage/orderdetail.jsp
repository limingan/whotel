<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html ng-app="diandanbao" class="ng-scope">
<head>
    <meta charset="utf-8">
    <style type="text/css">@charset "UTF-8";
    [ng\:cloak], [ng-cloak], [data-ng-cloak], [x-ng-cloak], .ng-cloak, .x-ng-cloak, .ng-hide:not(.ng-hide-animate) {
        display: none !important;
    }
    ng\:form {
        display: block;
    }</style>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta content="telephone=no" name="format-detection">
    <link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">
    <title>我的订单</title>
    <link data-turbolinks-track="true" href="/static/meal/css/weixin.css?v=1" media="all"
          rel="stylesheet">
    <style type="text/css">@media screen {
        .smnoscreen {
            display: none
        }
    }

    @media print {
        .smnoprint {
            display: none
        }
    }
	.total-info .btn_add {

	margin-left: 30px;
	margin-top: 0px;
	padding: 5px 8px;
	border: 0;
	border-radius: 2px;
	cursor: pointer;
	background-color: #2ec366;
	background-color: #4dbfd0;
	background-color: #F03C03;
	color: #fff
}
	</style>
</head>
<body>
<div ng-view="" style="height: 100%;" class="ng-scope">
    <div class="ddb-nav-header ng-scope" common-header="">
        <div class="nav-left-item" onclick="javascript :history.back(-1);"><i class="fa fa-angle-left"></i></div>
        <div class="header-title ng-binding">我的订单</div>
    </div>
    <!-- ngInclude:  -->
    <div class="ddb-nav-footer ng-scope" style="text-align:center;">
        <span class="button border-green {if $order['paytype']!=0}ng-hide{/if}" onclick="confirmorder()">确认</span>

        <span class="button border-blue ng-hide" ng-show="can_complete()" ng-click="complete()">完成</span>
        <span class="button border-red ng-hide" ng-show="can_pay_online()" ng-click="pay_online()">支付</span>
        <span class="button border-blue ng-hide" ng-show="can_append_itemable()" ng-click="go_append_itemable()">追加商品</span>
        <span class="button ng-hide" ng-show="order &amp;&amp; order.pay_item_state=='paid'">已支付</span>
        <span class="button border-green ng-hide" ng-show="can_exchange_code()" ng-click="go_exchange_code()">兑换码</span>
        <span class="button border-red ng-binding  ng-hide" ng-click="hasten()" >催单</span>
        <span class="button border-green ng-binding disable ng-hide">呼叫服务员</span>
        <div class="ng-hide">
        <!--<div>-->
            <hr>
            <span
                class="button border-green ng-binding ng-scope" ng-click="call_waiter(waiter_service_item.name)"
                ng-repeat="waiter_service_item in branch.waiter_service_items">水</span><!-- end ngRepeat: waiter_service_item in branch.waiter_service_items --><span
                class="button border-green ng-binding ng-scope" ng-click="call_waiter(waiter_service_item.name)"
                ng-repeat="waiter_service_item in branch.waiter_service_items">碗筷</span><!-- end ngRepeat: waiter_service_item in branch.waiter_service_items --><span
                class="button border-green ng-binding ng-scope" ng-click="call_waiter(waiter_service_item.name)"
                ng-repeat="waiter_service_item in branch.waiter_service_items">椅子</span><!-- end ngRepeat: waiter_service_item in branch.waiter_service_items -->
            <span class="button border-green" ng-click="call_waiter('其他服务')">其他服务</span>
            <span class="button border-red" ng-click="show_options(false)">取消</span>
        </div>
    </div>
    <div class="main-view order-show ng-scope" id="delivery-order-show">
        <div class="section">
            <a class="list-item arrow-right ng-binding" href="{php echo $this->createMobileUrl('detail', array('id' => $store['id']), true)}">
                <i class="fa fa-bookmark-o"></i> 甲乙丙丁
            </a>
            <a class="list-item arrow-right ng-binding" href="tel:{$store['tel']}">
                <i class="fa fa-phone"></i> 商家客服：8698888
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
            
            <a class="list-item ng-scope" onclick="cancelorder()">
                <div class="service-button">
                    <i class="red fa fa-bell"></i> 取消订单
                </div>
            </a>
           
        </div>
        <div class="space-8"></div>
        
        <div class="order-state-section ng-scope">
            <div class="order-state ng-isolate-scope active">
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
            <div class="order-state ng-isolate-scope active ">
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
            <!--<div class="order-state ng-isolate-scope" >-->
                <!--<div class="order-state-header">-->
                    <!--<div class="square">-->
                        <!--<div class="line-through" ng-hide="hide_left"></div>-->
                    <!--</div>-->
                    <!--<i class="fa fa-check-circle"></i>-->

                    <!--<div class="square">-->
                        <!--<div class="line-through" ng-hide="hide_right"></div>-->
                    <!--</div>-->
                <!--</div>-->
                <!--<div class="order-state-body ng-binding">配送中</div>-->
            <!--</div>-->
            <div class="order-state ng-isolate-scope active">
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
        </div>
        
        <div class="order-state-section ng-scope">
            <div class="order-state ng-isolate-scope active">
                <div class="order-state-header">
                    <div class="square">
                        <div class="line-through" ng-hide="hide_left"></div>
                    </div>
                    <i class="fa fa-check-circle"></i>

                    <div class="square">
                        <div class="line-through" ng-hide="hide_right"></div>
                    </div>
                </div>
                <div class="order-state-body ng-binding">已取消</div>
            </div>
        </div>
        
        <div class="space-8"></div>
        <div class="section no-bottom-border">
            <div class="list-item time">
                <span class="ng-binding">类型：外卖</span>&nbsp;&nbsp;<span class="ng-binding">订单号：201231232222</span><br>
                <span class="ng-binding">下单时间：2014-01-01 12:00:00</span>

            </div>
			<div class="list-item ng-scope">
                <div class="name ng-binding">
                    糖醋鱼
                </div>
                <div class="quantity-info">
                    <span class="quantity ng-binding">1份</span>
                    ×
                    <span class="price ng-binding">￥23.5</span>
                </div>
                <div class="total-info">
                    <button class="btn_add"  onclick="location.href=''">编辑</button>
                </div>
            </div>
			<div class="list-item ng-scope">
                <div class="name ng-binding">
                    糖醋鱼
                </div>
                <div class="quantity-info">
                    <span class="quantity ng-binding">1份</span>
                    ×
                    <span class="price ng-binding">￥23.5</span>
                </div>
                <div class="total-info">
                    <button class="btn_add"  onclick="location.href='' ">编辑</button>
                </div>
            </div>
            <div class="list-item">合计：<span class="red ng-binding">2份</span>，<strong
                    class="red ng-binding">￥23.45</strong>
            
                    
                    <span class="ng-scope">（含4元配送费）</span>
              
                  

                   
            </div>
     
            
         
            <div class="list-item">
                
                <div class="order-item ng-binding ng-scope">
                    支付类型: 未选择
                </div>
           
                          
                <div class="order-item ng-binding ng-scope">
                    姓名: 张三

                </div>
                <div class="order-item ng-binding ng-scope">
                    电话: 13322221111
                </div>
                <div class="order-item ng-binding ng-scope">
                    地址: XXXXXXXXXXXXX
                </div>
             

                <div class="order-item ng-binding ng-scope">
                    备注: 备注备注备注备注
                </div>
               
        </div>
        <div class="juchi"></div>
        </div>
    </div>
</div
<jsp:include page="footer.jsp"/>
<script src="/static/meal/js/jquery-1.11.3.min.js"></script>
<script>
    function confirmorder() {
            var url = "{php echo $this->createMobileUrl('pay', array('orderid' => $order['id']), true)}";
            window.location.href = url;

    }

    function cancelorder() {
        var url = "{php echo $this->createMobileUrl('cancelorder', array('id' => $order['id']), true)}";
        if (confirm("确认取消吗?")) {
            $.ajax({
                url: url, type: "post", dataType: "json", timeout: "10000",
                data: {
                },
                success: function (data) {
                    if (data.status == 1) {
                        location.href='{php echo $this->createMobileUrl("order", array(), true)}';
                    } else {
                        alert(data.msg);
                    }
                },error: function () {
                    alert("网络不稳定，请重新尝试!");
                }
            });
        }
    }
</script>
</body>
</html>