<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no">
    <title>我的商品</title>
    <link rel="stylesheet" type="text/css" href="/static/meal/css/wei_canyin_v1.8.4.css?v=1.1.2" media="all">
    <link rel="stylesheet" type="text/css" href="/static/meal/css/wei_dialog_v1.2.1.css?v=1.0" media="all">
    <script type="text/javascript" src="/static/meal/js/wei_webapp_v2_common_v1.9.4.js"></script>
    <script type="text/javascript" src="/static/meal/js/wei_dialog_v1.9.9.js?v=1"></script>
    <script type="text/javascript" src="/static/meal/js/jQuery.js"></script>
    <link rel="stylesheet" type="text/css" href="/static/meal/css/mobiscroll.custom-2.6.2.min.css" media="all">
    <link data-turbolinks-track="true" href="/static/meal/css/font.css?v=1" media="all" rel="stylesheet">
    <script type="text/javascript" src="/static/meal/js/mobiscroll.custom-2.6.2.min.js"></script>
    <script type="text/javascript" src="/static/meal/js/json2.js"></script>
    <!-- 新添加css -->
    <link rel="stylesheet" type="text/css" href="/static/meal/css/menu.css">

    <script type="text/javascript">
        $(function () {
            var dat = new Date();
            var curr = dat.getFullYear();
            var currM = dat.getMonth();
            var currD = dat.getDate();
            var opt = {}
            opt.date = {
                preset: 'date',
                minDate: new Date(curr, currM, currD, 9, 22),
                maxDate: new Date(curr, currM + 1, currD, 15, 44),
                stepMinute: 5
            };
            opt.datetime = {
                preset: 'datetime',
                minDate: new Date(2012, 3, 10, 9, 22),
                maxDate: new Date(2014, 7, 30, 15, 44),
                stepMinute: 5
            };
            opt.time = {preset: 'time'};
            $('#sdate').scroller('destroy').scroller($.extend(opt['date'], {
                theme: 'ios',
                mode: 'scroller',
                display: 'bottom',
                lang: 'zh',
                dateOrder: 'yymmdd',
                dateFormat: 'yy-mm-dd'
            }));
        });
    </script>
    
</head>
<body id="page_intelOrder" class="myOrderCon">
<div class="ddb-nav-header ng-scope">
    <a class="nav-left-item" href="javascript:void(0)" onclick="history.go(-1)"><i class="fa fa-angle-left"></i></a>

    <div class="header-title ng-binding">我的菜单</div>
    <a class="nav-right-item" href="#"></a>
</div>
<div class="center marTop"> 
    <!--<header>-->
    <!--<span class="pCount">-->
    <!--{if $store['is_delivery'] == 1}-->
    <!--{if !empty($store['sendingprice'])}-->
    <!--本店外卖<font color="red">{$store['sendingprice']}</font>元起送-->
    <!--{else}-->
    <!--美味即将送到-->
    <!--{/if}-->
    <!--{else}-->
    <!--请叫服务员下单-->
    <!--{/if}-->
    <!--</span>-->
    <!--<label><i>商品共计：</i><b class="duiqi" id="total">0</b><b class="duiqi">元</b></label>-->
    <!--</header>-->

    <section style="margin-bottom: 10px;">
        <article>
            <div>
                <ul class="myorder wlInfo" id="addressInfo">

                    <li class="wlItem">
                        <input type="hidden" id="addressId" value="${guest.id}">
                        <img src="/static/meal/images/icon_add.png" class="itemImg"/>
                        <span class="itemTxt">${guest.address}</span>

                        <div class="secondInfo">
                            <span>${guest.name}</span>
                            <span>${guest.mobile}</span>
                        </div>

                        <a class="nav-right-item" href="/oauth/meal/getAddrList.do"><i
                                style="position:absolute;font-size:26px;right:5px;bottom:20px;color:#999"
                                class="fa fa-angle-right"></i></a>

                    </li>
                    <li class="wlItem">
                        <img src="/static/meal/images/icon_timer.png" class="itemImg"/>
                        <span class="itemTxt">立即配送</span><span
                            style="margin-left:5px;color:red;font-size:14px">约${time}送达</span>

                        <div class="secondInfo">
                            <span>商家自配送</span>
                        </div>

                        <a class="nav-right-item" href="javascript:void(0)" onclick=";"><i
                                style="position:absolute;font-size:26px;right:5px;bottom:20px;color:#999"
                                class="fa fa-angle-right"></i></a>
                    </li>

                </ul>
            </div>
            <div id="form_dish" class="peopleInfo" target="hide" {if $mode== 1}style="border-top: 1px #e7eaeb solid;margin-top: 10px;">
                <table class="table_book">
                    <tbody>
                    <tr>
                        <td style="width: 80px;">所需餐具：</td>
                        <td colspan="2">
                           <input type="tel" id="guentNum" name="guentNum" value="" maxlength="3" class="tdInp" placeholder="请填写用餐人数"> 人
                        </td>
                    </tr>
                  <tr>
			       <td>优惠券</td><td><select class="" style="width:80px;">
                      <c:forEach items="${prizeList}" var="prize">
                          <option prizeId="${prize.id}" prizeValue="${prize.prizeValue}">${prize.prizeName}</option>
                      </c:forEach>
			       </select></td>
		          </tr>
                    <tr>
                        <td style="width: 80px; vertical-align: top; line-height: 25px;">备注说明：</td>
                        <td colspan="2">
                        <textarea name="remark" id="remark" style="height: 60px;" maxlength="100"
                                  placeholder="请输入备注(可不填)"></textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </article>
    </section>

    <section style="margin-bottom: 10px;">
        <article>
            <h2>菜单列表
                <button class="btn_add emptyIt" id="clearBtn">清空</button>
                <button class="btn_add" onclick="location.href='#'">+继续点菜</button>
            </h2>
            <ul class="myorder" id="myorder">

               <script>
				String.prototype.format = function (args) {
                var result = this;
                if (arguments.length > 0) {
                    if (arguments.length == 1 && typeof (args) == "object") {
                        for (var key in args) {
                            if (args[key] != undefined) {
                                var reg = new RegExp("({" + key + "})", "g");
                                result = result.replace(reg, args[key]);
                            }
                        }
                    } else {
                        for (var i = 0; i < arguments.length; i++) {
                            if (arguments[i] != undefined) {
                                var reg = new RegExp("({)" + i + "(})", "g");
                                result = result.replace(reg, arguments[i]);
                            }
                        }
                    }
                }
                return result;
            }
               	function get_cookie(Name) {
                  var search = Name + "="//查询检索的值
                  var returnvalue = "";//返回值
                  if (document.cookie.length > 0) {
                    sd = document.cookie.indexOf(search);
                    if (sd!= -1) {
                       sd += search.length;
                       end = document.cookie.indexOf(";", sd);
                       if (end == -1)
                        end = document.cookie.length;
                        //unescape() 函数可对通过 escape() 编码的字符串进行解码。
                       returnvalue=unescape(document.cookie.substring(sd, end))
                     }
                  } 
                  return returnvalue;
                 }
				 function refreshCategoryPrice(categoryList,dishList,totalcount,totalprice)
	              {
	              
	              // set cookies
	              
	              document.cookie = "categoryList="+JSON.stringify(categoryList);
	              document.cookie = "dishList="+JSON.stringify(dishList);
	              document.cookie = "totalCount="+totalcount;
	              document.cookie = "totalPrice="+totalprice;
	              
	             }
				 var totalPrice=0;
                 var totalCount = 0;
                 var allDishObject = Array();
                 var allDishCategoryList = {};
				 totalPrice = parseFloat(get_cookie('totalPrice'));
	             totalCount = parseInt(get_cookie('totalCount'));
	             allDishObject = eval('('+ get_cookie('dishList')+')');
	             allDishCategoryList = eval('('+ get_cookie('categoryList')+')');
	             refreshCategoryPrice(allDishCategoryList , allDishObject, totalCount ,totalPrice);
				  var dishList = get_cookie('dishList');
				  dishList = eval('('+dishList+')');
				  for(var i=0;i<dishList.length;i++)
				  {
				   var dishId = Object.keys(dishList[i])[0];
				   var dishInfo = eval('('+ localStorage.getItem(dishId) + ')');
				   document.write('<li>');
				   
				   
				   
				   switch(dishInfo['style'])
				   {
				     case 'set':document.write('<section class="bbox" style="left:-40px;top:10px" dishid="{0}" dishname="{1}">'.format(dishId,dishInfo['name']));
					            document.write('<input class="btn-reduce" style="margin-top:5px" type="button" value="-">');
					            document.write('<input class="numBox" name="numBox" type="hidden" value="1" price="{0}" disabled="disabled">'.format(dishInfo['price']));
								document.write('<input class="setData" type="hidden" value="{0}" />'.format(dishInfo['data']));
								document.write('</section>');
								document.write('<div style="display:inline;position:absolute;left:50px">');
								document.write('<span class="dishName">{0}</span>'.format(dishInfo['name']));
								document.write('<div><i>{0}元/{1}</i></div></div>'.format(dishInfo['price'],dishInfo['unit']));
								document.write('<h2><button class="btn_add editStyle" style="position:absolute;right:10%;top:30%" onclick=";">编辑</button></h2>');
					            break;
					 case 'multi':document.write('<section class="bbox" style="left:-40px;top:10px" dishid="{0}" dishname="{1}">'.format(dishId,dishInfo['name']));
					              document.write('<input class="btn-reduce" style="margin-top:5px" type="button" value="-">');
					              document.write('<input class="numBox" name="numBox" type="hidden" value="1" price="{0}" disabled="disabled">'.format(dishInfo['price']));
								  document.write('<input class="styleData" type="hidden" value="{0}" />'.format(dishInfo['data']));
								  document.write('</section>');
								  document.write('<div style="display:inline;position:absolute;left:50px">');
								  document.write('<span class="dishName">{0}</span>'.format(dishInfo['name']));
								  document.write('<div><i>{0}元/{1}</i></div></div>'.format(dishInfo['price'],dishInfo['unit']));
								  document.write('<h2><button class="btn_add editStyle" style="position:absolute;right:10%;top:30%"onclick=";">编辑</button></h2>');
					              break;
					 case 'normal':
					              document.write('<span class="dishName">{0}</span>'.format(dishInfo['name']));
								  document.write('<i>{0}元/{1}</i>'.format(dishInfo['price'],dishInfo['unit']));
								  document.write('<section class="bbox"  dishid="{0}" dishname="{1}">'.format(dishId,dishInfo['name']));
					              document.write('<input class="btn-reduce"  type="button" value="-">');
					              document.write('<input class="numBox" type="text" name="numBox" value="{1}" price="{0}" disabled="disabled">'.format(dishInfo['price'],dishList[i][dishId]));
								  document.write('<input type="button" class="btn-plus" value="+"></section>');
								  break;
								  
				   }
				   document.write('</li>');
				   }
				  
				</script>
            </ul>
        </article>
    </section>


    <input type="hidden" id="mode" value="{$mode}" name="mode">
    <input type="hidden" id="tables" value="{$tablesid}" name="tables">
    <section style="margin-bottom: 0px;">
        <article class="otherInfo">

            <%--<div style="width: 120px;">打包费用:￥<span id="">5.3</span></span>元--%>
            <%--</div>--%>
            <c:if test="${!empty hotel.deliverPrice}">
                <div style="width: 120px;">配送费:￥<span id="">${hotel.deliverPrice}</span></span>元</div>
            </c:if>

        </article>
    </section>

    <div class="header">
        <c:if test="${!empty hotel.deliverPrice}">
            <input type="hidden" id="deliverPrice" value="${hotel.deliverPrice}" name="deliverPrice">
        </c:if>
        <c:if test="${empty hotel.deliverPrice}">
            <input type="hidden" id="deliverPrice" value="0.0" name="deliverPrice">
        </c:if>
        <input type="hidden" id="packprice" value="0.0" name="packprice">
        <input type="hidden" id="discount" value="0.0" name="discount">
		<input type="hidden" id="coupon" value="0" name="coupon">
        <input type="hidden" id="totalprice" value="{$totalPrice}" name="totalprice">
        <input type="hidden" id="totalcount" value="{$totalcount}" name="totalcount">
        <input type="hidden" id="limitprice" value="{$limitprice}" name="limitprice">
        <input type="hidden" id="over_radius" value="{$over_radius}" name="over_radius">

        <input type="hidden" id="btnstatus" value="0" name="btnstatus">

        <div class="left">还需支付：￥<span id="totalpriceshow">${totalPrice}</span></span>元</div>
        <div class="right"><a id="btnselect" class="xhlbtn" href="javascript:void(0)">提交</a></div>
    </div>

    <div style="padding-top: 20px;"></div>
</div>
<div class="mModal1" style="position:fixed;width:100%;z-index: 901;height:100%;display:none;top:0;background-color:rgba(0, 0, 0, .5)"><a href="javascript:void(0)" style="height: 736px;"></a></div>
<div class="popupWindow" style="z-index:9999;display:none">
 <img class="close" src="/static/meal/images/close.png" style="z-index:901"/>
 <div id="popContent"></div>
</div>
<script>
    $('.couponSelect').change(function(){
	 $('#coupon').val($('.couponSelect option:selected').attr('prizeValue'));
	 tototal();
	})
    function changeMealDate(obj) {
        var time1 = '{$select_mealtime}';
        var time2 = '{$select_mealtime2}';
        var curdate = "{$cur_date}";
        if (obj.value == curdate) {
            $("#meal_time").html(time1);
        } else {
            $("#meal_time").html(time2);
        }
    }

    function changeBtnSelect() {
        var limitprice = parseFloat(_q("#limitprice").value);
        var totalprice = parseFloat(_q("#totalprice").value);
        if (limitprice > 0) {
            if (totalprice > limitprice) {
                _removeClass(_q("#btnselect"),'disable');
                _q("#btnstatus").value = '1';
            } else {
                _addClass(_q("#btnselect"),'disable');
                _q("#btnstatus").value = '0';
            }
        } else {
            if (totalprice > 0) {
                _q("#btnstatus").value = '1';
                _removeClass(_q("#btnselect"),'disable');
            } else {
                _q("#btnstatus").value = '0';
                _addClass(_q("#btnselect"),'disable');
            }
        }
    }
</script>
<script>var pageName = 'menuFilled';</script>
<script type="text/javascript">
var reduce = _qAll('.btn-reduce');
var plus = _qAll('.btn-plus');
var share = _qAll('.shareBtn');
//金额累加操作
"{if $flag != true}"

function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=get_cookie(name);
    if(cval!=null)
    document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}
function tototal(){
    var total = 0;
    var nums = _qAll('.numBox');
	var packprice = _qAll('#packprice')[0].value;
	var discount = parseFloat(_qAll('#discount')[0].value) + parseFloat(_qAll('#coupon')[0].value);
    for( var j = 0; j < nums.length; j++){
        total = total + nums[j].value * nums[j].getAttribute('price');
    }
    endTotal = parseFloat(total).toFixed(2) * 100/100 + parseFloat(packprice - discount);
    // endTotal = endTotal == parseInt(endTotal) ? parseInt(endTotal) : endTotal;
    _q('#totalprice').value = endTotal;
    _q("#totalpriceshow").innerHTML = endTotal;

    changeBtnSelect();
    return endTotal;
}
tototal();//初始化金额
function addMinusNormalDish(dishId,sign)
{
 for(var i in allDishObject)
 {
  if(Object.keys(allDishObject[i])[0]==dishId )
  {
   allDishObject[i][dishId] += sign;
   if(allDishObject[i][dishId]  == 0)
    allDishObject.splice(i, 1);
  }
 }
 //refresh categorycount
  var dishInfo = eval('('+ localStorage.getItem(dishId) + ')');
  var dishCategory = dishInfo.category;
  allDishCategoryList[dishCategory] += sign;
  if(allDishCategoryList[dishCategory]  == 0)
    delete allDishCategoryList[dishCategory];
  totalCount+=sign;
  totalPrice += sign*(dishInfo['price']);
  refreshCategoryPrice(allDishCategoryList , allDishObject,totalCount ,totalPrice);
}
function doSelectBtn(){
    var btn = _qAll("article ul li .bbox");
        var btnIndex = 0,btnLength = btn.length;
    for(btnIndex;btnIndex<btnLength;btnIndex++){
        var countNumText=parseInt(btn[btnIndex].children[1].value),
            btnAdd=btn[btnIndex].children[2],
            btnMin=btn[btnIndex].children[0];
        var iTimeout,iInterval,originalNum,
            beforeRemoveDish = false;
        btnAdd.addEventListener(_movestartEvt,function(){
            var _self = this;
            originalNum = parseInt(_self.parentNode.children[1].value, 10);
            countNumText =  originalNum +1;
            _self.parentNode.children[1].value = countNumText;
           
        })
        btnAdd.addEventListener(_moveendEvt,function(){
            //alert(countNumText)
            //_doAjax()...
            clearTimeout(iTimeout);
            clearInterval(iInterval);
          
            var _self = this;
            var countNumText =  parseInt(_self.parentNode.children[1].value, 10);
            var dishid = _self.parentNode.getAttribute('dishid');
			addMinusNormalDish(dishid,1);
            tototal();
                
            // countNumText = 0;
        })

        btnMin.addEventListener(_movestartEvt,function(){
            var _self = this;
            originalNum = parseInt(_self.parentNode.children[1].value, 10);
            countNumText =  originalNum -1;
            if(countNumText <= 0){
                beforeRemoveDish = true;
            }else{
                _self.parentNode.children[1].value = countNumText;
                iTimeout = setTimeout(function(){
                    iInterval = setInterval(function(){
                        if(countNumText<=0){
                            beforeRemoveDish = true;
                            _self.parentNode.children[1].value = originalNum;
                            clearInterval(iInterval);
                            return;
                        }
                        countNumText--;
                        _self.parentNode.children[1].value = countNumText;
                    },100)
                },1000)
            }
        })

        btnMin.addEventListener(_moveendEvt,function(){
            clearTimeout(iTimeout);
            clearInterval(iInterval);
            _self = this;

            var dishid = _self.parentNode.getAttribute('dishid');
            var dishName = _self.parentNode.getAttribute('dishName');
            var countNumText =  parseInt(_self.parentNode.children[1].value, 10);

            if(beforeRemoveDish){
                setTimeout(function(){
                    MDialog.confirm(
                        '', '是否删除' + dishName +'？', null,
                        '确定', function(){
                           addMinusNormalDish(dishid,-1);
						   var li = _self.parentNode.parentNode;
                           li.parentNode.removeChild(li);
						   tototal();
                        }, null,
                        '取消', null, null,null, true, true
                    );
                },200)
                beforeRemoveDish = false;
            } else {
                tototal();
                addMinusNormalDish(dishid,-1);
            }
        })
    } // for

   

    function ajaxDishRemove(dishid, successCallback, errorCallback) {
       // delete dishes in cookie
		// 1.delete dish 2.minus category 3.minus price and totalcount
        successCallback();
    } // ajaxDishRemove
} // doSelectBtn
"{/if}"
_onPageLoaded(function(){
    var reduce = _qAll('.btn-reduce');
    var plus = _qAll('.btn-plus');
    "{if $flag != true}"
    tototal();//初始化金额
    doSelectBtn();
    "{/if}"

    var url = "{php echo $this->createMobileUrl('clearmenu', array('storeid' => $storeid, 'from_user' => $from_user), true)}";
    "{if $flag != true}"
    _q('#clearBtn').onclick = function() {
        MDialog.confirm(
            '', '是否清空菜单？', null,
            '确定', function(){
			    //clear cookie
				delCookie('categoryList');
				delCookie('dishList');
				delCookie('totalCount');
				delCookie('totalPrice');
                history.go(-1)
            }, null,
            '取消', null, null,
            null, true, true
        );
    };
    "{/if}"

    $("#btnselect").click(function () {
        var bool = checkItem();
        if (bool) {
            MDialog.confirm(
                '', '确认提交吗？', null,
                '确定', function(){
                    postmain();
                }, null, '取消', null, null,null, true, true
            );
        }
    });

    $("#submit_form2").click(function () {
        var mobile = $("#entry_mobile").val();
        var checkcode = $("#entry_checkcode").val();
        if (mobile == "") {
            alert('请输入手机号码!');
            return false;
        }
        if (checkcode == "") {
            alert('请输入验证码!');
            return false;
        }
        MDialog.confirm(
            '', '确认提交吗？', null,
            '确定', function(){
                postmain2();
            }, null, '取消', null, null,null, true, true
        );
    });
});

function checkItem() {
        var ordertype = $("#mode").val();
        var meal_time = $("#meal_time").val();

        if ($("#tel").val() == "" || $("#tel").val() == "(必填*)请输入您的手机号码") {
            alert("请输入您的电话号码！");
            return false;
        }
//    if (!/^1[3|4|5|8][0-9]\d{8}$/.test($("#tel").val())) { alert("请输入正确的电话号码！"); return false; }
        if ($("#name").val() == "" || $("#name").val() == "(必填*)请输入您的真实姓名") {
            alert("请输入您的真实姓名！");
            return false;
        }
        totalprice = parseFloat(_q("#totalprice").value);

        if (totalprice <= 0) {
            alert("金额为0，请选择菜品！");
            return false;
        }

        if (ordertype == 1) { //店内
            if ($("#counts").val() == "") {
                alert("请输入用餐人数！");
                return false;
            }
            if (!new RegExp("^[0-9]*$").test($("#counts").val())) {
                alert("用餐人数只能为数字！");
                return false;
            }
            if ($("#tables").val() == "") {
                alert("请输入桌号！");
                return false;
            }
        } else if (ordertype == 2) {//外卖
            if ($("#address").val() == "" || $("#address").val() == "(必填*)请输入您的联系地址！") {
                alert("请输入您的联系地址！");
                return false;
            }
            if (meal_time == '休息中') {
                alert('未在配送时间！');
                return false;
            }
            var over_radius = $("#over_radius").val();
            if (over_radius == 1) {
                alert('超出配送范围，不允许下单！');
                return false;
            }
        } else if (ordertype == 4) {//快餐

        }
        return true;
    }

    function outCheck(){
        if ($("#addressId").val() == "") {
            alert("请输入您的联系地址！");
            return false;
        }
        if ($("#guestNum").val() == "") {
            alert("请输入您的用餐人数！");
            return false;
        }
    }

function postmain() {
        var status = _q("#btnstatus").value;
        outCheck();
        var ordertype = $("#mode").val();
        var mealtime = $("#meal_date").val() + ' ' + $("#meal_time").val();
        if ($("#meal_date").val() == undefined) {
            if ($("#meal_time").val() == undefined) {
                mealtime = '';
            } else {
                mealtime = $("#meal_time").val();
            }
        }

        if (status == 0) {
            return false;
        }

        $("#btnselect").hide();
        if (true) {
		    var length = allDishObject.length;
			var orderData = {};
			orderData['addressId'] = $("#addressId").val();
			orderData['remark'] = $("#remark").val();
			orderData['guestNum'] = $("#guestNum").val();
			orderData['mealOrderType'] = 'OUT';	
			if($('#couponSelect').val() !=0)
			 orderData['prizeId'] = $('#couponSelect').val();	
			 orderData['list'] = Array();
			for(var i=0;i<length;i++)
			{
			  var dishId = Object.keys(allDishObject[i])[0];
			  var dishInfo = eval('('+ localStorage.getItem(dishId) + ')');
			  var obj =  {};
			  obj['dishesId'] = dishId;
			  switch(dishInfo['style'])
			  {
			   case 'normal':			         
					 obj['itemQuantity'] = allDishObject[i][dishId];			         
					 break;
			   case 'multi':
                     obj['itemQuantity'] = 1;
					 obj['propList'] = Array();
					 var objLength = Object.keys(allDishObject[i][dishId]).length;
					 for (var key in allDishObject[i][dishId])
					 {
					   var innerObj = {};
					   innerObj['propId'] = key;
					   innerObj['valueList'] = Array();
					   innerObj['valueList'].push(allDishObject[i][dishId][key]);
					   obj['propList'].push(innerObj);
					 }
					break;
                case 'set':
            		  obj['itemQuantity'] = 1;	
					  obj['itemList'] = Array();
					  var itemLength = allDishObject[i][dishId].length;
					  for(var j=0;j<itemLength;j++)
					  {
					    var item = {};
						item['grade'] = allDishObject[i][dishId][j].grade;
						item['dishNo'] = allDishObject[i][dishId][j].id;
						if(allDishObject[i][dishId][j].style != undefined)
						{
						 var propList = Array();
						 
						 var styleData = eval('(' + allDishObject[i][dishId][j].style + ')');
						 for(var key in styleData)
						 {
						  var propObj = {};
						  propObj['propId'] = key;
						  propObj['valueList'] = Array();
					      propObj['valueList'].push(styleData[key]);
						  propList.push(propObj);
						 }
						 
						}
						obj['itemList'].push(item);
					  }
					  break;
				default:break;	  
					  
			  }
			  orderData['list'].push(obj);
			}
            
            $.ajax({
                url: "/oauth/meal/createOrder.do", type: "post", dataType: "json", timeout: "10000",
                data: {
                    str: JSON.stringify(orderData)
                },
                success: function (data) {
                    if (data.code == 200) {
                        var orderId = data.data;
                        location.href = '/oauth/meal/orderDetail.do?orderId='+orderId;
                    } else {
                        alert(data.message);
                    }
                }, error: function () {
                    alert("网络不稳定，请重新尝试!");
                }
            });
        } else {
            $("#btnselect").show();
        }
    }
//edit dish set and style
    $('.popupWindow .close').click(function(){
	 $('#popContent').html('');
	 $('.mModal1,.popupWindow').hide();
	})
    var editBtn = _qAll('.editStyle');
	var addBtnLength = editBtn.length;
	for(var i=0;i<addBtnLength;i++)
	editBtn[i].addEventListener(_moveendEvt,function(){
	
	 //load dish style
	 var liId = 0;
	 var obj = $(this).parent().parent().prev('li');
	 while(obj.length!=0){liId++;obj = obj.prev('li');}
	 var dishId  = $(this.parentNode.parentNode).children('section').attr('dishId');
	 var dishInfo = eval( '(' + localStorage.getItem(dishId) + ')');
	 var dPrice = dishInfo.price;
	 var isMultiStyle = dishInfo.style == 'multi'; //是否多规格
	 var isSet = dishInfo.style == 'set';               //是否套餐
	 var dishName = dishInfo.name; 
	 var dishUnit = dishInfo.unit; 
	 var dishCategory = dishInfo.category;
	 var html='';
	 
	 if(1 == isSet)
	 {
	   
	   var setData = eval('(' + dishInfo.data + ')');
	   var setDishLength = setData.length;
	   var setTemplate = '<div class="setStyle"><img class="leftArrow" src="./images/right_left_arrow.png"><span class="settitle">{0}</span><img class="rightArrow" src="./images/right_right_arrow.png">{1}</div>';
	   var dishSelectedData = allDishObject[liId][dishId];
	   $(setData).each(function(i,n){
	      var baseHtml = '';
		  var subId = i;
   	      $(n).each(function(ii,nn){
		    if(typeof(nn.style) !='undefined')
			{
			 if(undefined == dishSelectedData[subId].style)
			  dishSelectedData[subId].style = '';
			 if(dishSelectedData[subId].id != nn.id)
		     baseHtml += '<li attrid="{0}" grade="{3}" selectedStyle=\'{4}\' styleData=\'{2}\'>{1}</li>'.format(nn.id,nn.name,JSON.stringify(nn.style),nn.grade,dishSelectedData[subId].style);
			 else
			 baseHtml += '<li class="redborder" attrid="{0}" grade="{3}" selectedStyle=\'{4}\' styleData=\'{2}\'>{1}</li>'.format(nn.id,nn.name,JSON.stringify(nn.style),nn.grade,dishSelectedData[subId].style);
			}
			else
			{
			 if(dishSelectedData[subId].id != nn.id)
			  baseHtml += '<li attrid="{0}" grade="{2}">{1}</li>'.format(nn.id,nn.name,nn.grade);
			 else
			  baseHtml += '<li class="redborder" attrid="{0}" grade="{2}">{1}</li>'.format(nn.id,nn.name,nn.grade);
			}
		 });
		 baseHtml = '<ul>{0}</ul>'.format(baseHtml);
		 
		 html += setTemplate.format('第'+parseInt(i+1)+'道菜', baseHtml);
	   });
	   html += '<img class="bottom-rmb" src="./images/rmb.png"/>';
	   html += '<span class="bottom-price">{0}</span>'.format(dPrice);
	   html += '<img class="bottomright-button addToList" src="./images/dish_addMenu.png"/>';
	   html += '<input id="dishId" type="hidden" value="{0}" />'.format(dishId);
	   html += '<input id="dishCategory" type="hidden" value="{0}" />'.format(dishCategory);
	   $('#popContent').html(html);
	   $('.addToList').click(function(){
	      //alert('ok');
		  var setDishList = Array();
		  var ulLength = $(this).parent().children('.setStyle').length;
		  var parentNode = $(this).parent();
		  for(var i=0;i<ulLength ;i++)
		  {
		    if(parentNode.children('.setStyle').children('ul').eq(i).children('.redborder').length == 0)
			 {
			  var dishName = parentNode.children('.setStyle').children('.settitle').eq(i).text();
			  alert(dishName + '未选择，请选择')
			  return;
			 }
			else
             {
			  var selectedDish = parentNode.children('.setStyle').children('ul').eq(i).children('.redborder');
			  var subDishId = selectedDish.attr('attrid');
			  var grade = selectedDish.attr('grade');
			  var obj = {id:subDishId,grade:grade};
			  if(selectedDish.attr('selectStyle') != undefined)
			  {
			   obj.style = selectedDish.attr('selectStyle')
			  }
			  setDishList.push(obj);
			 }			
		  }
		  //save set id setdishes id
		  var dishId = $(this).parent().children('#dishId').val();
          var obj = {};
		  obj[dishId] = setDishList;
		  allDishObject[liId] = obj;
		  refreshCategoryPrice(allDishCategoryList , allDishObject, totalCount ,totalPrice);
		  $('.popupWindow .close').click();
	   })
	   
	   $('.setStyle').addClass('dn');
	   $('.setStyle').eq(0).removeClass('dn');
	   $('.mModal1,.popupWindow').show();
	   $('.setStyle ul li').click(function(){
	    $(this).parent().children('li').removeClass('redborder');
		if($(this).attr('styledata') == undefined)
		 $(this).addClass('redborder');
		else
         {
		   //show style selection dialog
		   var stylePopup = $('.popupWindow').clone();
		   stylePopup.children('#popContent').html('');
		   
		   // add style data
		   var multiStyleTemplate = '<div class="multiStyle"><div class="stitle"><span>{0}</span></div>{1}</div>';
	       var multiStyleBaseTemplate = '<div class="subtitle" attrid="{2}">{0}</div>{1}';
	       var multiStyle = eval('('+ $(this).attr('styledata') + ')');
		   var selectedStyle = '';
		   if('' != $(this).attr('selectedStyle'))		   
		    selectedStyle = eval( '(' + $(this).attr('selectedStyle') + ')');
		   var html = '';
	       $(multiStyle).each(function(i,n){
	       var baseHtml = '';
		  
   	         $(n.data).each(function(ii,nn){
			   if(selectedStyle[1] == nn.id)
		        baseHtml += '<li class="redborder" attrid="{0}"  >{1}</li>'.format(nn.id,nn.name);
			   else
			    baseHtml += '<li attrid="{0}"  >{1}</li>'.format(nn.id,nn.name);
		     });
		   baseHtml = '<ul>{0}</ul>'.format(baseHtml);		 
		   html += multiStyleBaseTemplate.format(n.name, baseHtml,n.id);
	      });
		   html += '<button class="bottomright-button confirmBtn">确定</button>';
		   stylePopup.children('#popContent').html(multiStyleTemplate.format('',html));
		   //bind event
		   stylePopup.children('.close').click(function(){
		     $(this).parent().remove();
		   });
		   stylePopup.find('li').click(function(){
		     $(this).parent().children('li').removeClass('redborder');
		     $(this).addClass('redborder');
		   });
		   var currentDish = this;
		   stylePopup.find('.confirmBtn').click(function(){
		     var ulLength = $(this).parent().children('ul').length;
		     var parentNode = $(this).parent();
		     var dishStyleList = {};
			 var dishStyleAllSelected = true;
		     for(var i=0;i<ulLength ;i++)
		     {
		      if(parentNode.children('ul').eq(i).children('.redborder').length == 0)
			   {
			    var dishName = parentNode.children('.subtitle').eq(i).text();
			    alert(dishName + '未选择，请选择')
			    return;
			   }
			   else
			   {
			    var subStyle = parentNode.children('ul').eq(i).children('.redborder').attr('attrid');
		  	    //var styleId = parentNode.children('.subtitle').eq(i).attr('attrid');
			    dishStyleList[1] = subStyle;
			   }
		     }
			 //add style list to upper li
			 $(currentDish).attr('selectStyle',JSON.stringify(dishStyleList));
			 $(currentDish).parent().children('li').removeClass('redborder');		         
	   	     $(currentDish).addClass('redborder');			    
		     $(this).parent().parent().parent().remove();
		   });
		   $('#page_intelOrder').append(stylePopup);
		   
		 }		
	   });
	   function getCurrentSetIndex()
	   {
	    var currentIndex = -1;
		
		$('.setStyle').each(function(i,n){
		  if(!$(n).hasClass('dn'))
		   {
		    currentIndex = i;
			return false;
		   }
		});
		return currentIndex;
	   }
	   $('.setStyle .leftArrow').click(function(){
		var index = getCurrentSetIndex()
		if(index == 0)
		 return;
	    $('.setStyle').addClass('dn');
		$('.setStyle').eq(index-1).removeClass('dn');
	   });
	   $('.setStyle .rightArrow').click(function(){
	    var index = getCurrentSetIndex()
		if(index == $('.setStyle').length-1)
		 return;
	    $('.setStyle').addClass('dn');
		$('.setStyle').eq(index+1).removeClass('dn');
	   });
	 }
	 else if(1 == isMultiStyle)
	 {
	   
	   var multiStyleTemplate = '<div class="multiStyle"><div class="stitle"><span>{0}</span></div>{1}</div>';
	   var multiStyleBaseTemplate = '<div class="subtitle" attrid="{2}">{0}</div>{1}';
	   var multiStyle = eval('('+ dishInfo.data + ')');
	   var selectedStyle =  allDishObject[liId][dishId];
	   $(multiStyle).each(function(i,n){
	      var baseHtml = '';
		  var currentStyle = i;
		  var selectedStyleId = selectedStyle[n.id];
		  
   	      $(n.data).each(function(ii,nn){
		    if(nn.id == selectedStyleId)
		    baseHtml += '<li class="redborder" attrid="{0}"  >{1}</li>'.format(nn.id,nn.name);
			else
			baseHtml += '<li attrid="{0}"  >{1}</li>'.format(nn.id,nn.name);
		 });
		 baseHtml = '<ul>{0}</ul>'.format(baseHtml);
		 
		 html += multiStyleBaseTemplate.format(n.name, baseHtml,n.id);
	   });
	   html += '<img class="bottom-rmb" src="./images/rmb.png"/>';
	   html += '<span class="bottom-price">{0}</span>'.format(dPrice);
	   html += '<img class="bottomright-button addToList" src="./images/dish_addMenu.png"/>';
	   html += '<input id="dishId" type="hidden" value="{0}" />'.format(dishId);
	   html += '<input id="dishCategory" type="hidden" value="{0}" />'.format(dishCategory);
	   $('#popContent').html(multiStyleTemplate.format(dishName,html))
	    $('.addToList').click(function(){
	       var ulLength = $(this).parent().children('ul').length;
		   var parentNode = $(this).parent();
		   var dishStyleList = {};
		   for(var i=0;i<ulLength ;i++)
		   {
		    if(parentNode.children('ul').eq(i).children('.redborder').length == 0)
			 {
			  var dishName = parentNode.children('.subtitle').eq(i).text();
			  alert(dishName + '未选择，请选择')
			  return;
			 }
			 else
			 {
			  var subStyle = parentNode.children('ul').eq(i).children('.redborder').attr('attrid');
			  var styleId = parentNode.children('.subtitle').eq(i).attr('attrid');
			  dishStyleList[styleId] = subStyle;
			 }
		   }
		   var dishId = $(this).parent().children('#dishId').val();
		   var obj = {};
		   obj[dishId] = dishStyleList;
		   allDishObject[liId] = obj;
		   
		   refreshCategoryPrice(allDishCategoryList , allDishObject,totalCount ,totalPrice);
		   $('.popupWindow .close').click();
	   })
	   //TODO:addListener
	   $('.mModal1,.popupWindow').show();
	   $('.multiStyle ul li').click(function(){
	    $(this).parent().children('li').removeClass('redborder');
		$(this).addClass('redborder');
	   });
	   
	 }
	 
	})
	

</script>
</body>
</html>