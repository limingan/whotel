<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html lang="zh-CN"><head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no">
<title>全部商品</title>
<link data-turbolinks-track="true" href="http://tiantianwutuo.top/addons/weisrc_dish/template//mobile/style1/assets/diandanbao/weixin.css?v=1 " media="all" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="http://tiantianwutuo.top/webPage/assets/diandanbao/wei_canyin_v1.8.4.css?v=1.1.1" media="all">
<link rel="stylesheet" type="text/css" href="http://tiantianwutuo.top/addons/weisrc_dish/template/css/1/wei_dialog_v1.2.1.css?v=1.1" media="all">
<link data-turbolinks-track="true" href="http://tiantianwutuo.top/addons/weisrc_dish/template/mobile/style1/assets/diandanbao/font.css?v=1" media="all" rel="stylesheet">
<style>
    /*解决右边背景总是为灰色的bug*/
    #page_allMenu section article, #pInfo {
        min-height: 100%;
    }
</style>
<style>abbr,article,aside,audio,canvas,datalist,details,dialog,eventsource,fieldset,figure,figcaption,footer,header,hgroup,mark,menu,meter,nav,output,progress,section,small,time,video,legend{display:block;}</style>
<script type="text/javascript" src="http://tiantianwutuo.top/addons/weisrc_dish/template/js/1/wei_webapp_v2_common_v1.9.4.js"></script>
<script type="text/javascript" src="http://tiantianwutuo.top/addons/weisrc_dish/template/js/1/wei_dialog_v1.9.9.js"></script>
</head>
<body id="page_allMenu" style="background-color: #fff;">
<style>

    .ddb-nav-header {
        opacity: 0.95;
        position: fixed;
        top: 0;
        width: 100%;
        height: 45px;
        line-height: 44px;
        z-index: 1999;
        border-bottom: 1px solid #eeeeee;
        background-color: white;
        color: #555555
    }

    .ddb-nav-header a {
        color: #555555
    }

    .ddb-nav-header .header-title {
        width: 50%;
        white-space: nowrap;
        text-overflow: ellipsis;
        text-align: center;
        overflow: hidden;
        font-size: 17px;
        font-weight: bold;
        display: inline-block;
        margin-left: 25%
    }

    .ddb-nav-header .nav-left-item, .ddb-nav-header .nav-right-item {
        display: inline-block;
        height: 100%
    }

    .ddb-nav-header .nav-left-item i, .ddb-nav-header .nav-right-item i {
        width: 20px;
        font-size: 18px;
        line-height: inherit;
    }

    .ddb-nav-header .nav-left-item {
        width: 25%;
        float: left;
        padding-left: 10px
    }

    .ddb-nav-header .nav-right-item {
        width: 10%;
        float: right;
        text-align: right
    }

    .ddb-nav-header .nav-right-item .operation-button {
        font-size: 16px;
        text-align: right;
        color: #555555;
        padding-right: 10px;
        font-weight: bold
    }

    .ddb-nav-header .nav-left-item ~ .header-title {
        margin-left: 0
    }

    .ddb-nav-header.label-red {
        border-bottom: 0
    }
    .notification-section {
        opacity: 0.9;
        top: 45px;
        margin-top: 45px;
        background-color: #fff9df;
        padding: 0 10px;
        border-bottom: 0px solid #dbdbdb;
        z-index: 1999;
    }

    .notification-section .notice {
        border-top: 1px dotted #eeeeee;
        padding: 8px 0;
        padding-left: 30px;
        line-height: 20px
    }

    .notification-section .notice i {
        display: block;
        float: left;
        margin-left: -30px;
        text-align: center;
        width: 30px
    }
    .product-notice-section {
        opacity: 0.9;
        top: 45px;
        margin-top: 45px;

        background-color: #fff9df;
        padding: 8px 20px;
        font-size: 14px;
        line-height: 20px;
        z-index: 1999
    }
	.ddb-tab-bar .active{
	padding-bottom:0px;
	line-height:5px;
	border-bottom:2px solid #ff0000
	}
	.dn{
	display:none
	}
	.shopInfoList{
	 line-height:30px
	}
	.shopInfoList img {
	 width:13px;height:13px;margin-left:20px
	}
	.shopInfoList span{
	 margin-left:20px
	}
	.shopInfoList i{
	 margin-left:10px
	}
	.dishSecondTitle{
	 line-height:8px;
	}
</style>
<div class="ddb-nav-header ng-scope">
    <a class="nav-left-item" href="javascript:void(0)" onclick="history.go(-1)"><i class="fa fa-angle-left"></i></a>
    <div class="header-title ng-binding">餐厅1</div>
    
</div>

<div class="notification-section">
    <div class="">
       <img style="width:100%;height:200px" src="http://tiantianwutuo.top/attachment/images/1/2017/04/SanMzlI2yXTxyqhYt1DtILUxnY1VU2.jpg"/>
    </div>
</div>
         <div class="">
         <div class="ddb-tab-bar ">
            <div class="ddb-tab-item ng-scope active">
                <a href="javascript:;" class="" id="store_classify">点餐</a>
               
            </div>
            <div class="ddb-tab-item ng-scope">
                <a href="javascript:;" class="ng-binding">评论</a>
            </div>
            <div class="ddb-tab-item ng-scope" ng-repeat="pane in panes" ng-class="{active:pane.selected}"
                 ng-click="toggle(pane)">
                <a href="javascript:;" class="ng-binding">商家</a>
            </div>
        </div>
		</div>
<div class="" >
    <nav id="navBar">
        <dl>
            <c:forEach items="${cateList}" var="cate" varStatus="status">

                <c:if test="${status.index ==0}">
                    <dd categoryid="${cate.id}" class="active">
                    <%--FIXME limingan 分类暂时无图,这个图片去掉--%>
                    <%--<img style="width:15px;height:15px;border-top:10px" src="http://tiantianwutuo.top/attachment/images/1/2017/04/SanMzlI2yXTxyqhYt1DtILUxnY1VU2.jpg" alt="" title="">--%>
                    ${cate.dishName}
                    <span style="display:inline-block;">1</span>
                </c:if>

                <c:if test="${status.index !=0}">
                    <dd categoryid="${cate.id}">
                    ${cate.dishName}
                    <span style="display:none;"></span>
                </c:if>
                </dd>
            </c:forEach>
        </dl>
        <div style="margin-top: 100px;"></div>
    </nav>
    <section id="infoSection">
        <article style="border: 0px">
            <div id="pInfo">
                <div style="margin-top: 100px;"></div>
            </div>
        </article>
    </section>
</div>

<div class="shopInfoList dn">
 <div><img src="http://tiantianwutuo.top/attachment/images/1/2017/04/SanMzlI2yXTxyqhYt1DtILUxnY1VU2.jpg"/><span >月售 811 份</span></div>
 <div><img src="http://tiantianwutuo.top/attachment/images/1/2017/04/SanMzlI2yXTxyqhYt1DtILUxnY1VU2.jpg"/><span >09:30-21:00</span></div>
 <div><img src="http://tiantianwutuo.top/attachment/images/1/2017/04/SanMzlI2yXTxyqhYt1DtILUxnY1VU2.jpg"/><span >深圳市南山区</span></div>
 <div><img src="http://tiantianwutuo.top/attachment/images/1/2017/04/SanMzlI2yXTxyqhYt1DtILUxnY1VU2.jpg"/><span >0755-33331111</span><a href="javascript:void(0);"><i class="fa fa-angle-right"></i></a></div>
</div>
{php include $this->template($this->cur_tpl.'/_header');}
<script type="text/javascript">
    var view_const_dish_SPECIAL_PRICE_YES = '2';
    var view_const_dish_SPECIAL_PRICE_VIP = '3';
    var view_const_dish_HOT_YES = '2';
    function setHeight(){
        var  cHeight;
        cHeight = document.documentElement.clientHeight;
        cHeight = cHeight +"px"
        document.getElementById("navBar").style.height =  cHeight;
        document.getElementById("infoSection").style.height =  cHeight;
    }
    //ajax处理
    //配合_doAjax方法使用
    function doSelect(){
        var dds = _qAll('#navBar dd');
        var aa=0, bb;
        var article = _q("#infoSection article");
        _forEach(dds, function(ele, idx, dds) {
            dds[idx].onclick = function(){
                _q('.active').className = null;
                this.className = "active";
                var div = document.getElementById("pInfo");

                div.innerHTML = '';
                var params = {
                    'cateId' : this.getAttribute('categoryId')
                };

//                var url = "{php echo $this->createMobileUrl('GetDishList', array('storeid' => $storeid, 'from_user' => $from_user), true)}";

                var url = "/oauth/meal/dishList.do";
                _doAjax(url, 'GET', params, function(ret) {
                    //alert(ret['message']['debug']);
                    var dishList = ret['data']['list'];
                    var categoryId = ret['data']['categoryId'];

                    var str = '';
                    var rnd = Math.random();
                    for(key in dishList) {
                        var dish = dishList[key];


                        if (dish['marketPrice'] != '') {
                            var priceHtml = "<em class='sale'>"+dish['price']+"元/"+dish['unit']+"<br/><del>"+dish['marketPrice']+"元/"+dish['unitname']+"</del></em>";
                        } else {
                            var priceHtml = "<em>"+dish['price']+"元"+dish['unit']+"</em>";
                        }
                        if (dish.dIsHot == view_const_dish_HOT_YES) {
                            var hotHtml = '<span></span>';
                        } else {
                            var hotHtml = '';
                        }

                        if (dish['credit'] > 0) {
                            var dSubCountHtml = '赠送<f style="color: #f00;">' + dish['credit'] + '</f>积分';
                        } else {
                            var dSubCountHtml = '';
                        }

                        if (dish['thumb'] == '') {
                            var dlstyle = ' style="padding-left:0px;" ';
                        }

                        var attrList = " dUnitName='"+dish['unit']+"' dSubCount='"+dish['dSubCount']+"' dishid='"+dish['id']+"' dName='"+dish['dishName']+"' dTaste='"+dish['dTaste']+"' dDescribe='"+dish['dDescribe']+"' dPrice='"+dish['price']+"' dIsHot='"+dish['dIsHot']+"' dSpecialPrice='"+dish['dSpecialPrice']+"' dIsSpecial='"+dish['dIsSpecial']+"' ";
                        str += "<dl shopInfo='' "+attrList+"' "+dlstyle+"><dt><h3>"+dish['dishName']+"</h3></dt><dd><a href='javascript:void(0)' class='dataIn'><img src='" + dish['miniatureUrl'] + "?rnd="+rnd+"' alt='' title='' />"+hotHtml+"</a></dd><dd>"+priceHtml+"</dd><dd class='dpNum'>"+dSubCountHtml+"</dd><dd class='btn'><button class='minus'><strong></strong></button><i>"+dish['total']+"</i><button class='add'><strong></strong></button><em class='fixBig fake'></em></dd></dl>";
                    }
                    str += '<div style="margin-top: 60px;"></div>';

                    if (_q('.active').getAttribute('categoryId') == categoryId) {
                        div.innerHTML = str;
                        _q('#infoSection').scrollTop = 0;
                        doSelectBtn();
                        showPicInfo();
                    }
                });
            }
        });
    }

    //选择商品按钮样式
    function doSelectBtn(){
        var btn = _qAll("article dl .btn");
        var btnIndex = 0,btnLength = btn.length;

        // countDish();
        for(btnIndex;btnIndex<btnLength;btnIndex++){
            var countNumText=parseInt(btn[btnIndex].children[1].innerHTML),
                btnAdd=btn[btnIndex].children[2],
                btnMin=btn[btnIndex].children[0];

            btnShowHide(countNumText,btn[btnIndex], false);

            var iTimeout,iInterval, originalNum,
                beforeRemoveDish=false,
                beforeAddDish=false,
                needRemoveNotify=false; //是否需要删除提醒

            btnAdd.addEventListener(_movestartEvt,function(){
                beforeRemoveDish = false;
                var _self = this;
                originalNum = parseInt(_self.parentNode.children[1].innerHTML, 10);
                countNumText =  originalNum +1;
                var shopInfo =_self.parentNode.parentNode.getAttribute('shopInfo');
                ///debug
                if (countNumText == 1) {
                    if (shopInfo) {
                        _self.parentNode.children[1].innerHTML = 0;
                        beforeAddDish = true;
                        return;
                    } else {
                        _self.parentNode.children[1].innerHTML = 1;
                        btnShowHide(1, _self.parentNode);
                    }
                } else {
                    _self.parentNode.children[1].innerHTML = countNumText;
                    btnShowHide(countNumText,_self.parentNode);
                    iTimeout = setTimeout(function(){
                        // console.log(_self);
                        iInterval = setInterval(function(){
                            countNumText++;
                            _self.parentNode.children[1].innerHTML = countNumText;
                            // 变化打数字
                            _removeClass(_self.parentNode.children[3],'fake');
                            _self.parentNode.children[3].innerHTML = countNumText
                        },100)
                    },1000)
                }
            })

            btnAdd.addEventListener(_moveendEvt,function(){//add
                clearTimeout(iTimeout);
                clearInterval(iInterval);
                hideBigFont();
                var _self = this;
                var countNumText =  parseInt(_self.parentNode.children[1].innerHTML, 10);
                var dishid = _self.parentNode.parentNode.getAttribute('dishid');
                var shopInfo =_self.parentNode.parentNode.getAttribute('shopInfo');

                var price=_self.parentNode.parentNode.getAttribute('dspecialprice');
                if(price==""){
                    var dprice=_self.parentNode.parentNode.getAttribute('dprice');
                    price= dprice;
                }

                if (beforeAddDish) {
                    //alert('debug');
                    setTimeout(function(){
                        MDialog.confirm(
                            '', shopInfo, null,
                            '确定', function(){
                                _self.parentNode.children[1].innerHTML = 1;
                                btnShowHide(1, _self.parentNode);
                                ajaxDishReset(dishid, 1, function(){}, function() {
                                    _self.parentNode.children[1].innerHTML = originalNum;                                                                 btnShowHide(originalNum, _self.parentNode);
                                });
                        }, null,
                            '取消', function(){}, null,
                            null, true, true
                        );
                    }, 500);
                    beforeAddDish = false;
                } else {
//                    var totalprice = parseFloat(_q("#totalprice").value)+parseFloat(price);
//                    var totalcount = parseInt(_q("#totalcount").value)+1;
//                    totalprice = totalprice.toFixed(2);
//
//                    _q("#totalprice").value = totalprice;
//                    _q("#totalpriceshow").innerHTML = totalprice;
//                    _q("#totalcount").value = totalcount;
//                    _q("#totalcountshow").innerHTML = totalcount;

                    ajaxDishReset(dishid, countNumText, function(){}, function() {
                        _self.parentNode.children[1].innerHTML = originalNum;
                        btnShowHide(originalNum, _self.parentNode);
                    });
                }
            })

            btnMin.addEventListener(_movestartEvt,function(){
                var _self = this;
                originalNum = parseInt(_self.parentNode.children[1].innerHTML, 10);
                countNumText =  originalNum -1;
                if(countNumText <= 0 ){
                    _self.parentNode.children[1].innerHTML = 1;
                    beforeRemoveDish = true;
                    return;
                } else {
                    _self.parentNode.children[1].innerHTML = countNumText;
                    iTimeout = setTimeout(function(){
                        iInterval = setInterval(function(){
                            countNumText--;
                            if(countNumText <= 0){
                                clearInterval(iInterval);
                                _self.parentNode.children[1].innerHTML = 1;
                                beforeRemoveDish = true;
                                return;
                            } else {
                                _self.parentNode.children[1].innerHTML = countNumText;
                                btnShowHide(countNumText,_self.parentNode);
                            }
                            // 字体放大显示
                            _removeClass(_self.parentNode.children[3],'fake');
                            _self.parentNode.children[3].innerHTML = countNumText
                        },100)
                    },1000)
                }
            })

            btnMin.addEventListener(_moveendEvt,function(){
                clearTimeout(iTimeout);
                clearInterval(iInterval);
                hideBigFont();
                var _self = this;
                var countNumText =  parseInt(_self.parentNode.children[1].innerHTML, 10);
                var dishid = _self.parentNode.parentNode.getAttribute('dishid');
                var dName = _self.parentNode.parentNode.getAttribute('dName');

                var price = _self.parentNode.parentNode.getAttribute('dspecialprice');
                if(price == ""){
                    var dspecialprice=_self.parentNode.parentNode.getAttribute('dprice');
                    price= dspecialprice;
                }

//                var totalprice = parseFloat(_q("#totalprice").value)-parseFloat(price);
//                var totalcount = parseInt(_q("#totalcount").value)-1;
//                totalprice = totalprice.toFixed(2);
//
//                _q("#totalprice").value = totalprice;
//                _q("#totalpriceshow").innerHTML = totalprice;
//                _q("#totalcount").value = totalcount;
//                _q("#totalcountshow").innerHTML = totalcount;

                if (beforeRemoveDish) {
                    if (needRemoveNotify) {
                        setTimeout(function(){
                            MDialog.confirm(
                                '', '是否删除'+dName+'？', null,
                                '确定', function(){
                                    _self.parentNode.children[1].innerHTML = 0;
                                    btnShowHide(0, _self.parentNode);
                                    //alert('needRemoveNotify');//debug
                                    ajaxDishRemove(dishid, function(){}, function() {
                                        _self.parentNode.children[1].innerHTML = originalNum;
                                        btnShowHide(originalNum, _self.parentNode);
                                    });
                                }, null,
                                '取消', function(){
                                    _self.parentNode.children[1].innerHTML = originalNum;
                                    btnShowHide(originalNum, _self.parentNode);
                                }, null,
                                null, true, true
                            );
                        }, 500);
                        beforeRemoveDish = false;
                    } else {
                        //alert('not_needRemoveNotify');//debug
                        _self.parentNode.children[1].innerHTML = 0;
                        btnShowHide(0, _self.parentNode);
                        ajaxDishRemove(dishid, function(){}, function() {
                            _self.parentNode.children[1].innerHTML = originalNum;
                            btnShowHide(originalNum, _self.parentNode);
                        });
                        beforeRemoveDish = false;
                    }
                } else {
                    ajaxDishReset(dishid, countNumText, function(){}, function() {
                        _self.parentNode.children[1].innerHTML = originalNum;
                        btnShowHide(originalNum, _self.parentNode);
                    });
                }
            }) // btnMin.addEventListener
        }

        //更新分类商品数量
        function ajaxDishReset(dishid, o2uNum, successCallback, errorCallback) {
            var params = {
                'dishid' : dishid,
                'o2uNum' : o2uNum
            };
            //alert('dishid:'+dishid+';o2uNum:'+o2uNum);
            //debug
            var url = "{php echo $this->createMobileUrl('UpdateDishNumOfCategory', array('storeid' => $storeid, 'from_user' => $from_user), true)}";

            //successCallback();
            _doAjax(url, 'POST', params, function(ret) {
                //alert(ret['message']['msg']);return;

                if (ret['message']['code'] != 0) {
                    errorCallback();
                    alert(ret['message']['msg']);
                    return;
                } else {
                    totalprice = ret['message']['totalprice'];
                    totalcount = ret['message']['totalcount'];
                    _q("#totalprice").value = totalprice;
                    _q("#totalpriceshow").innerHTML = totalprice;
                    _q("#totalcount").value = totalcount;
                    _q("#totalcountshow").innerHTML = totalcount;
                    changeBtnSelect();
                    successCallback();
                }
                successCallback();
            });
        } // ajaxDishReset

        function ajaxDishRemove(dishid, successCallback, errorCallback) {
            var params = {
                'dishid' : dishid,
                'action' : 'remove'
            };

            var url = "{php echo $this->createMobileUrl('RemoveDishNumOfCategory', array('storeid' => $storeid, 'from_user' => $from_user), true)}";

            _doAjax(url, 'POST', params, function(ret) {
                if (ret['message']['code']!= 0) {
                    errorCallback();
                    alert(ret['message']['msg']);
                    return;
                } else {
                    totalprice = ret['message']['totalprice'];
                    totalcount = ret['message']['totalcount'];
                    _q("#totalprice").value = totalprice;
                    _q("#totalpriceshow").innerHTML = totalprice;
                    _q("#totalcount").value = totalcount;
                    _q("#totalcountshow").innerHTML = totalcount;
                    changeBtnSelect();
                    successCallback();
                }
            });
        } // ajaxDishRemove
    } // doSelectBtn

    function hideBigFont(){
        var _arr = _qAll(".fixBig");
        _forEach(_arr,function(ele,idx,_arr){
            _addClass(ele,'fake');
        })
    }

    function btnShowHide(num,btns, isCountDish){

        if (isCountDish !== false) {
            countDish();
        }

        if (num <= 0) {
            btns.children[0].style.display ="none";
            btns.children[1].style.display ="none";
        } else {
            btns.children[0].style.display ="inline-block";
            btns.children[1].style.display ="inline-block";
        };
    }

    function countDish(){
        var countTotle = 0,countdish;
        var dishNum = _qAll("#page_allMenu section article dl .btn i");
        _forEach(dishNum,function(ele,idx,dishNum){
            countdish = parseInt(ele.innerHTML);
            if(countdish>0){
                countTotle++;
            }
        });

        if(countTotle != 0){
            _q("#page_allMenu nav dl dd.active span").innerHTML = countTotle;
            _q("#page_allMenu nav dl dd.active span").style.display='block';
        }else{
            _q("#page_allMenu nav dl dd.active span").style.display='none';
        }
    }

    //点击促发弹层事件
    function showPicInfo(){
        var links = _qAll(".dataIn"), i=0;
        for(i;i<links.length;i++){
            links[i].onclick=function(event){
                event.stopPropagation();
                // dl
                var parentDl = this.parentNode.parentNode;
                var childImg = this.childNodes[0]
                if(childImg.nodeType == 3){
                    childImg = this.childNodes[1];
                }

                var dSpecialPrice = parentDl.getAttribute('dSpecialPrice');

                if (dSpecialPrice != '') {
                    dSpecialPrice = parentDl.getAttribute('dSpecialPrice') + '元/' + parentDl.getAttribute('dunitName');
                }

                popPic(childImg.src,
                        parentDl.getAttribute('dname'),
                        parentDl.getAttribute('dprice') + '元/' + parentDl.getAttribute('dunitName'),
                        parentDl.getAttribute('dIsSpecial'),
                        dSpecialPrice,
                        parentDl.getAttribute('dsubCount'),
                        parentDl.getAttribute('dtaste'),
                        parentDl.getAttribute('ddescribe'),
                        parentDl.getAttribute('dishot')
                );
            }
        }
    }
    //后台可自行扩展参数
    //调用自定义弹层
    function popPic(imgUrl,title,price, isSpecial, specialPrice, people,teast,assess,isHot){
        var _title = title,
            _price = price,
            _people = people,
            _teast = teast,
            _assess = assess;

        var hotHtml = '';
        if (isHot == view_const_dish_HOT_YES) {
            hotHtml = '<b></b>';
        }
            _tmpHtml = "<div class='content'>"+hotHtml+"<img src='"+imgUrl+"' alt='' title=''><h2>"+_title;


             if (specialPrice != '') {
                 _tmpHtml += "<i>"+specialPrice+"</i><del>"+_price+"</del>";
             } else {
                 _tmpHtml += "<i>"+_price+"</i>";
             }

            if (_people) {
                _tmpHtml += "<span>"+_people+"人点过</span>";
            }
            _tmpHtml += "</h2>";

            if (_teast) {
                _tmpHtml += "<h3>口味："+_teast+"</h3>";
            }

            if (_assess) {
                _tmpHtml += "<p>"+_assess+"</p>";
            }

            _tmpHtml += '</div>';
            MDialog.popupCustom(_tmpHtml,true, function(){}, true);
    }

    // 获取各个分类被选中商品的数量
    function getDishNumOfCategory() {
        var params = {
        };
        _doAjax("{php echo $this->createMobileUrl('GetDishNumOfCategory', array('storeid' => $storeid, 'from_user' => $from_user), true)}", 'POST', params, function(ret) {
            for(var i in ret.message.data) {
                var val = ret['message']['data'][i];
                if (val > 0) {
                    //debug
                    _q('[categoryId="'+i+'"] span').innerHTML = val;
                    _q('[categoryId="'+i+'"] span').style.display='block';
                } else {
                    _q('[categoryId="'+i+'"] span').style.display='none';
                }
            }
        });
    }

    _onPageLoaded(function(){
//        changeBtnSelect();
        setHeight();
        doSelect();
        doSelectBtn();
        showPicInfo();
        getDishNumOfCategory();
        if(_isIOS){
            _q("#page_allMenu section article").style.overflowY ="scroll";
            _q("#page_allMenu section article").style.minHeight ="85%";
            _q("#page_allMenu section article").style.marginBottom="15px";
        }
    })
    window.addEventListener('orientationchange', function(){
        setHeight();
    })
</script>
</body>
</html>