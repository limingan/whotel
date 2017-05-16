<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title></title>
    <meta name="format-detection" content="telephone=no, address=no">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <!-- apple devices fullscreen -->
    <meta name="apple-touch-fullscreen" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent"/>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <link rel="shortcut icon" href=""/>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
    <script type="text/javascript" src="/static/meal/js/util.js"></script>
    <script src="/static/meal/js/require.js"></script>
    <script type="text/javascript" src="/static/meal/js/jquery-1.11.3.min.js?v=20160906"></script>
    <script type="text/javascript" src="/static/meal/js/mui.min.js?v=20160906"></script>
    <script type="text/javascript" src="/static/meal/js/common.js?v=20160906"></script>
    <link type="text/css" rel="stylesheet" href="/static/meal/css/zdialog.css"/>
    <script type="text/javascript" src="/static/meal/js/zdialog.js"></script>
    <link href="/static/meal/css/bootstrap.min.css?v=20160906" rel="stylesheet">
    <link href="/static/meal/css/common.min.css?v=20160906" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/static/front/css/loading.css?v=${version}"/>

    <style>
        .pwd-box {
            width: 310px;
            padding-left: 1px;
            position: relative;
            border: 1px solid #9f9fa0;
            border-radius: 3px;
            over-flow: hidden
        }

        .pwd-box input[type="tel"] {
            width: 99%;
            height: 45px;
            color: transparent;
            position: absolute;
            top: 0;
            left: 0;
            border: none;
            font-size: 18px;
            opacity: 0;
            z-index: 1;
            letter-spacing: 35px;
        }

        .fake-box input {
            width: 44px;
            height: 48px;
            border: none;
            border-right: 1px solid #e5e5e5;
            text-align: center;
            font-size: 30px;
        }

        .fake-box input:nth-last-child(1) {
            border: none;
        }
    </style>
    <script type="text/javascript">
        if (navigator.appName == 'Microsoft Internet Explorer') {
            if (navigator.userAgent.indexOf("MSIE 5.0") > 0 || navigator.userAgent.indexOf("MSIE 6.0") > 0 || navigator.userAgent.indexOf("MSIE 7.0") > 0) {
                alert('您使用的 IE 浏览器版本过低, 推荐使用 Chrome 浏览器或 IE8 及以上版本浏览器.');
            }
        }
        window.sysinfo = {};

    </script>
</head>
<>
< class="container container-fill">

<div class="mui-content pay-method">
    <input type="hidden" value="${order.id}" id="orderId">
    <h5 class="mui-desc-title mui-pl10">订单详情</h5>
    <ul class="mui-table-view">

        <li class="mui-table-view-cell">
            订单编号<span class="mui-pull-right mui-text-muted">${order.orderSn}</span>
        </li>
        <li class="mui-table-view-cell">
            商家名称<span class="mui-pull-right mui-text-muted">${rest.name}</span>
        </li>
		<li class="mui-table-view-cell">
			优惠券<select class="couponSelect mui-pull-right mui-text-muted" style="margin-top:-6px;width:70px;height:33px;padding:0;margin-bottom:-20px">
				          <option prizeId="0" prizeValue="0">请选择</option>
                      <c:forEach items="${prizeList}" var="prize">
                          <option prizeId="${prize.id}" prizeValue="${prize.prizeValue}">${prize.prizeName}</option>
                      </c:forEach>
			       </select>
		</li>
        <li class="mui-table-view-cell">
            您需要支付<span class="mui-pull-right mui-text-success mui-big mui-rmb">${order.totalFee} 元</span>
        </li>
    </ul>

    <h5 class="mui-desc-title mui-pl10">选择支付方式</h5>
    <ul class="mui-table-view mui-table-view-chevron">


        <li class="mui-table-view-cell mui-disabled js-wechat-pay">
            <a class="mui-navigate-right mui-media" href="javascript:;" id="order">
                <img src="/static/meal/images/wx-icon.png" alt="" class="mui-media-object mui-pull-left"/>
				<span class="mui-media-body mui-block">
					<span id="wetitle">微信支付(必须使用微信内置浏览器)</span>
					<span class="mui-block mui-text-muted mui-mt5">微信支付,安全快捷</span>
				</span>
            </a>
        </li>


        <li class="mui-table-view-cell">
            <a class="mui-navigate-right mui-media js-pay" href="javascript:;" id="creditPay">

                <img src="/static/meal/images/icon_membercard.png" alt="" class="mui-media-object mui-pull-left"/>
				<span class="mui-media-body mui-block">
					会员支付
					<span class="mui-block mui-text-muted mui-mt5">会员卡支付服务</span>
				</span>
            </a>
        </li>


    </ul>
</div>

<div id="pay-select-coupon-modal" class="mui-modal">
    <header class="mui-bar mui-bar-nav">
        <a class="mui-icon mui-icon-close mui-pull-right" href="#pay-select-coupon-modal"></a>

        <h1 class="mui-title">请选择卡券</h1>
    </header>
    <nav class="mui-bar mui-bar-footer">
        <div class="js-coupon-submit">
            <input type="hidden" name="couponid" value="">
            <button class="mui-btn mui-btn-success mui-btn-block js-submit">确定</button>
        </div>
    </nav>
    <div class="mui-content">
        <div class="pay-select-coupon">
            <div class="js-coupon-show">
                <c:forEach items="${prizeList}" var="prize">
                    <div class="mui-input-row mui-radio">
                        <label>
                            <div class="coupon-panel">
                                <div class="mui-row">
                                    <div class="mui-col-xs-4 mui-text-center">
                                        <div class="coupon-panel-left">
                                                ${prize.pic}
                                        </div>
                                    </div>
                                    <div class="mui-col-xs-8">
                                        <div class="store-title mui-ellipsis">${prize.prizeName}</div>
                                        <div class="date">${prize.date}</div>
                                    </div>
                                </div>
                            </div>
                            <input type="radio" name="coupon" value="${prize.id}"/>
                        </label>
                        <ol class="coupon-rules" style="display:none;">
                            <c:if test="${empty prize.remark}">
                                暂无说明
                            </c:if>
                            <c:if test="${!empty prize.remark}">
                                ${prize.remark}
                            </c:if>
                        </ol>
                        <div class="scan-rules js-scan-rules">折扣券使用规则<span class="fa fa-angle-up"></span></div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<div id="tradeForm" style="display: none">
</div>
<div class="loading" id="loading"></div>

<script type="text/javascript">
    $(document).on('click', '.js-scan-rules', function () {
        $(this).prev().toggle();
        $(this).find('span').toggleClass('fa-angle-up');
        $(this).find('span').toggleClass('fa-angle-down');
    });
    $(document).on('click', 'input[type="radio"]', function () {
        hidden_couponid = $('input[name="couponid"]').val();
        var couponid = $(this).val();
        if (!hidden_couponid) {
            $('input[type="radio"]').prop('checked', false);
            $(this).prop('checked', true);
            $('input[name="couponid"]').val(couponid);
        } else {
            if (hidden_couponid == couponid) {
                $(this).prop('checked', false);
                $('input[name="couponid"]').val('');
            } else {
                $('input[type="radio"]').prop('checked', false);
                $(this).prop('checked', true);
                $('input[name="couponid"]').val(couponid);
            }
        }
    });
    var cards_str = '';
    if (cards_str) {
        cards_str = $.parseJSON(cards_str);
    } else {
        cards_str = {};
    }
    $(document).on('click', '.js-submit', function () {
        var checked_card = $('input[name="couponid"]').val();
        if (checked_card && cards_str[checked_card]) {
            if (cards_str[checked_card].type == '1') {
                $('.js-coupon .js-card-info').html('已抵用' + cards_str[checked_card].discount_cn + '元');
                $('.js-token .js-card-info').html('未使用');
            }
            ;
            if (cards_str[checked_card].type == '2') {
                $('.js-token .js-card-info').html('已抵用' + cards_str[checked_card].discount_cn + '元');
                $('.js-coupon .js-card-info').html('未使用');
            }
            ;
            $('.js-pay input[name="coupon_id"]').val(checked_card);
            $('.js-pay input[name="code"]').val(cards_str[checked_card]['code']);
        } else {
            $('.js-token .js-card-info').html('未使用');
            $('.js-coupon .js-card-info').html('未使用');
            $('.js-pay input[name="coupon_id"]').val(0);
        }
        $('#pay-select-coupon-modal').removeClass('mui-active');
        $('#pay-select-token-modal').removeClass('mui-active');
        $('.mui-backdrop').remove('.mui-backdrop');
        $('.pay-method').removeAttr('style');
    })
</script>
{/if}
<script type="text/javascript">

    function callpay() {
        if (typeof WeixinJSBridge == "undefined") {
            if (document.addEventListener) {
                document.addEventListener('WeixinJSBridgeReady', jsApiCall, false);
            } else if (document.attachEvent) {
                document.attachEvent('WeixinJSBridgeReady', jsApiCall);
                document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
            }
        } else {
            jsApiCall();
        }
    }
    $(document).on('click', '.js-pay', function () {
        $(this).find('form').submit();
    })

    $('#order').click(function () {
        wxPay();
    });


    var $loading = $("#loading");
    function wxPay() {
        $loading.show();
        $.ajax({
            type: "POST",
            url: "/oauth/meal/getWxPayData.do",
            data: {orderId: $("#orderId").val()},
            cache: false,
            dataType: 'html',
            success: function (rs) {
                $loading.hide();
                if (rs && rs != "") {
                    $("#tradeForm").html(rs);
                } else {
                    // document.referrer
                    $("#alertMsg").html("支付失败！");
                    $("#alertTip").modal();
                }
            },
            error: function (request) {
                $loading.hide();
                $("#alertMsg").html("网络异常，支付失败！");
                $("#alertTip").modal();
            }
        });
    }


    $("#creditPay").click(function () {
        $.DialogByZ.Confirm({
            Title: "请输入密码",
            Content: "卡号:<input id='cardNo' style='margin-top:10px' type='text' />" +
            "密码:<input id='password' style='margin-top:10px' type='password' />",
            FunL: confirmL,
            FunR: Immediate
        })
    })
    function confirmL() {
        var password = $('#password').val();
        var mbrCardNo = $('#cardNo').val();
        var orderId = $('#orderId').val();
        if (password == '' || mbrCardNo == '') {
            $.DialogByZ.Alert({
                Title: "提示", Content: "请输入有效卡号密码", BtnL: "确定", FunL: function () {
                    $.DialogByZ.Close();
                }
            });
        }
        else {

            $loading.show();
            $.ajax({
                url: "/oauth/meal/memberPay.do",
                cache: false,
                data: {
                    password: password,
                    mbrCardNo: mbrCardNo,
                    orderId: orderId
                },
                dataType: "text",
                success: function (data) {
                    if (data == "true") {
                        document.location = "/meal/showMealBookRs.do?orderSn=" + rs;
                    } else {
                        $.DialogByZ.Alert({
                            Title: "提示", Content: data, FunL: function () {
                                $.DialogByZ.Close();
                            }
                        });
                    }
                },
                complete: function (xhr, status) {
                    $loading.hide();
                    isComplete = true;
                }
            });
            $.DialogByZ.Close();
        }

    }
    function Immediate() {
        $.DialogByZ.Close();
    }

    function wxPayCallBack(orderSn,res) {
        if(res.err_msg=='get_brand_wcpay_request:ok'){
            document.location = "/oauth/meal/orderDetail.do?orderId="+$("#orderId").val();
        }else{
            $.DialogByZ.Alert({
                Title: "提示", Content: "支付失败", BtnL: "关闭", FunL: function () {
                    $.DialogByZ.Close();
                }
            });
        }
    }

</script>
</body>
</html>