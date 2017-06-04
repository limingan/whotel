<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html lang="zh-CN"><head>
<%
response.setHeader("Cache-Control","no-cache,must-revalidate,no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);
%>
<script>
 function delCookie(name){//为cookie name
   var date = new Date();
   date.setTime(date.getTime() - 10000);
   document.cookie = name + "=a; expires=" + date.toGMTString();
 }
<c:if test="${clearCookieFlag == 1}">
 delCookie('totalPrice');
 delCookie('totalCount');
 delCookie('dishList');
 delCookie('categoryList');
 delCookie('guestNum');
 delCookie('remark');
</c:if>
</script>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="format-detection" content="telephone=no">
<title>全部菜肴</title>
<link data-turbolinks-track="true" href="/static/meal/css/weixin.css?v=1" media="all" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/static/meal/css/wei_canyin_v1.8.4.css?v=1.1.1" media="all">
<link rel="stylesheet" type="text/css" href="/static/meal/css/wei_dialog_v1.2.1.css?v=1.1" media="all">
<link data-turbolinks-track="true" href="/static/meal/css/font.css?v=1" media="all" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="/static/meal/css/list.css">


    <link rel="stylesheet" type="text/css" href="/static/meal/css/banner.css?v=1.1.1" media="all">

    <style>
    /*解决右边背景总是为灰色的bug*/
    #page_allMenu section article, #pInfo {
        min-height: 100%;
    }
	.point-outer{
    position:absolute; 
    z-index:20;
    -webkit-transition:all 0.5s cubic-bezier(0.39,-0.4,0.83,0.23) 0s;
}
.point-inner{
    width:10px;
    height:10px; 
    border-radius:50%;
    background-color:#ff6326; 
	z-index:20;
	transition-duration: 0.5s;
    -moz-transition-duration: 0.5s; /* Firefox 4 */
    -webkit-transition-duration: 0.5s; /* Safari 和 Chrome */
    -o-transition-duration: 0.5s; /* Opera */
    -webkit-transition:all 0.5s linear 0s;
}
.point-outer.point-pre{
    display:none;
}
.ui-loader{
	display:none !important;
}

</style>

<script type="text/javascript" src="/static/meal/js/wei_webapp_v2_common_v1.9.4.js"></script>
<script type="text/javascript" src="/static/meal/js/wei_dialog_v1.9.9.js"></script>
<script type="text/javascript" src="/static/meal/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/static/meal/js/unslider.min.js"></script>
<script src="/static/meal/js/jquery.fly.min.js"></script>
</head>
<body id="page_allMenu" style="background-color: #fff;height:100%">

<div class="ddb-nav-header ng-scope">
    <a class="nav-left-item" href="javascript:void(0)" onclick="clearAndGoBack()"><i class="fa fa-angle-left"></i></a>
    <div class="header-title ng-binding">${rest.name}</div>
    
</div>



        <div class="">
         <div class="ddb-tab-bar ">
            <div class="ddb-tab-item ng-scope active">
                <a style="font-size:16px;color:black;line-height:40px" href="javascript:;" class="" id="store_classify">点餐</a>
               
            </div>
            <div class="ddb-tab-item ng-scope">
                <a style="font-size:16px;color:black;line-height:40px"  href="javascript:;" class="ng-binding">评论</a>
            </div>
            <div class="ddb-tab-item ng-scope" ng-repeat="pane in panes" ng-class="{active:pane.selected}"
                 ng-click="toggle(pane)">
                <a  style="font-size:16px;color:black;line-height:40px" href="javascript:;" class="ng-binding">商家</a>
            </div>
        </div>
		</div>
<div class="main" >
    <nav id="navBar">
        <dl>


            <c:forEach items="${cateList}" var="cate" varStatus="status">

                <c:if test="${status.index ==0}">
                    <div class="navOption" href="#${cate.dishNo}">
                        <dd categoryid="${cate.id}" class="active" style="color:black;">
                            <%--fixme limingan 分类无图--%>
                            <%--<img style="width:15px;height:15px;border-top:10px" src="http://tiantianwutuo.top/attachment/images/1/2017/04/SanMzlI2yXTxyqhYt1DtILUxnY1VU2.jpg" alt="" title="">--%>
                            <p>${cate.dishName}</p>
                            <span style="display:none;"></span>
                        </dd>
                    </div>
                </c:if>

                <c:if test="${status.index !=0}">
                    <div class="navOption" href="#${cate.dishNo}">
                        <dd categoryid="${cate.id}" >
                                <p>${cate.dishName}</p>
                            <span style="display:none;"></span>
                        </dd>
                    </div>
                </c:if>
            </c:forEach>
        </dl>
        <div style="margin-top: 100px;"></div>
    </nav>
    <section id="infoSection" onscroll="dishScroll()">
        <article style="border: 0px">
            <div id="pInfo">
                <c:forEach items="${cateList}" var="cate">
                    <div class="pTitle" id="${cate.dishNo}">${cate.dishName}</div>
                    <c:forEach items="${cate.dishesList}" var="dish">
                        <dl class="pContent" isMultiStyle="${dish.isMultiStyle}" multiStyle='${dish.multiStyle}' isSet='${dish.isSuite}' setData='${dish.suiteData}' dunitname="${dish.unit}" dsubcount="${dish.openIdCount}" dishid="${dish.id}" dname="${dish.dishName}" dtaste="口味" ddescribe="${dish.brief}" dmarkNum="45" dprice="${dish.price}" dishot="2" dspecialprice="${dish.price}" disspecial="是否特价" shopinfo="" style="padding-left:60px;">
                            <dt><h3>${dish.dishName}</h3></dt>

                            <dd>
                                <a href="javascript:void(0)" class="dataIn">
                                    <img src="${dish.miniatureUrl}" alt="" title="">

                                    <span></span>

                                </a>
                            </dd>
                            <span class="dishSecondTitle">月售 ${dish.monthSale} 份 | 好评率 100%</span>
                            <dd class="dishDetailInfo">
                                <em class="sale">￥${dish.price}</em><del>￥${dish.marketPrice}</del>  

                            </dd>
                            <dd class="dpNum">

                            </dd>
                            <dd class="btn">
							    <c:choose>
                                  <c:when test="${dish.isMultiStyle== 1 || dish.isSuite== 1 }">  
							        <a class="dishstyle" href="javascript:void(0);">可选规格</a>
                                    <numsmall style="display:none">0</numsmall>       
								  </c:when>
                                  <c:otherwise> 
                                    <img src="/static/meal/images/minusdish.png" class="minusDish" alt="" style="display:none">
					                <num style="display:none">0</num>								
                                    <img src="/static/meal/images/plusdish.png" class="addDish" alt="">
 								  </c:otherwise>
                                </c:choose>
                            </dd>
                        </dl>
                    </c:forEach>
                </c:forEach>
                <div class="botLine"></div>
            </div>
        </article>
    </section>
</div>

<div class="shopInfoList dn">
<div class="notification-section" style="width:100% !important;padding:0 0px;">
    <div class="banner" style="padding:0 0px !important">
        <ul>
            <c:forEach items="${bannerList}" var="baner">
                <li><img style="width:100%;" src="${baner}"></li>
            </c:forEach>
        </ul>
    </div>
</div>
 <div><img src="/static/meal/images/sales.png"/><span >&nbsp;月售 ${monthSale} 单</span></div>
 <div><img src="/static/meal/images/timer.png"/><span >&nbsp;${rest.businessTime}</span></div>
 <div><img src="/static/meal/images/position.png"/><span >&nbsp;${rest.address}</span></div>
 <div><img src="/static/meal/images/tel.png"/><span >&nbsp;${rest.tel}</span><a href="tel://${rest.tel}"><i class="fa fa-angle-right"></i></a></div>
</div>
<div class="mModal1" style="position:fixed;width:100%;z-index: 901;height:100%;display:none;top:0;background-color:rgba(0, 0, 0, .5)"><a href="javascript:void(0)" style="height: 736px;"></a></div>
<div class="popupWindow" style="z-index:9999;display:none">
 <img class="close" src="/static/meal/images/close.png" />
 <div id="popContent"></div>
</div>
<jsp:include page="_header.jsp"/>
<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript">
   var isUserChangeCateogry = 0;
   var offset = $("#cartN").offset(); 
   var totalPrice= 0;
   var totalCount = 0;
   var allDishObject = Array();
   var allDishCategoryList = {};
   
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
   $('.ddb-tab-item').eq(0).click(function(){
     $('.ddb-tab-item').removeClass('active');
	 $(this).addClass('active');
     $('.main').removeClass('dn');
	 $('.shopInfoList').addClass('dn');
   });
   var isBannnerInit = 0;
   $('.ddb-tab-item').eq(2).click(function(){
     $('.ddb-tab-item').removeClass('active');
	 $(this).addClass('active');
     $('.main').addClass('dn');
	 $('.shopInfoList').removeClass('dn');
	 if(0 == isBannnerInit)
	 $('.banner').unslider({			
				keys: true,
				dots: true
			});
	isBannnerInit = 1;		
	 
   });
   $(".navOption").click(function() {
	  isUserChangeCateogry = 1; 
      $('.navOption dd').removeClass("active");
	  $(this).children('dd').addClass('active');
      $("#infoSection").animate({
        scrollTop: $($(this).attr("href")).offset().top - $("#" + $('.pTitle').eq(0).attr('id')).offset().top +"px"
      }, {
        duration: 300,
        easing: "swing"
      });
      return false;
    });
	$('.dishstyle').click(function(){
	  $(this).parent().parent().children('.btn').children('.addDish').click();
	});
    var addBtn = _qAll('.addDish,.dishstyle');
	var minusBtn = _qAll('.minusDish');
	var addBtnLength = addBtn.length;
	var minusLength = minusBtn.length;
	function addMinusNormalDish(dishId,sign)
    {
     var dishInfo = eval('('+ localStorage.getItem(dishId) + ')');
	 var currentDishCount ;
     for(var i in allDishObject)
     {
      if(Object.keys(allDishObject[i])[0]==dishId )
      {
	  if(allDishObject[i][dishId]  == 0 || allDishObject[i][dishId]==undefined)
	   return 0;
       if(dishInfo['style'] == 'normal')
       {
       allDishObject[i][dishId] += sign;
	   currentDishCount = allDishObject[i][dishId];
       if(allDishObject[i][dishId]  == 0)
        allDishObject.splice(i, 1);
       }
       else
        allDishObject.splice(i, 1);
      }
     }
	 var dishCategory = dishInfo.category;
     allDishCategoryList[dishCategory] += sign;
     if(allDishCategoryList[dishCategory]  == 0)
       delete allDishCategoryList[dishCategory];
     totalCount+=sign;
     totalPrice += sign*(dishInfo['price']);
     refreshCategoryPrice(allDishCategoryList , allDishObject,totalCount ,totalPrice);
	 return currentDishCount;
   }

	$('.addDish,.dishstyle').click(function(event){
	
	 //load dish style
	 var parentDl = this.parentNode.parentNode;
	 var dPrice = parentDl.getAttribute('dPrice');
	 var isMultiStyle = parentDl.getAttribute('isMultiStyle'); //是否多规格
	 var isSet = parentDl.getAttribute('isSet');               //是否套餐
	 var dishName = parentDl.getAttribute('dname'); 
	 var dishUnit = parentDl.getAttribute('dunitname'); 
	 var dishId = parentDl.getAttribute('dishid');
	 var dishCategoryPage = $(this).parent().parent().prevAll('div').attr('id')
	 var dishCategory = $('#navBar>dl').find('div[href="#'+dishCategoryPage+'"]').children('dd').attr('categoryId');
	 var html='';
	 var localStorageObj = {};
	 localStorageObj['id'] = dishId;
	 localStorageObj['price'] = dPrice;
	 localStorageObj['unit'] = dishUnit;
	 localStorageObj['name'] = dishName;
	 localStorageObj['category'] = dishCategory;
	 if(1 == isSet)
	 {
	   localStorageObj['style'] = 'set';
	   localStorageObj['data'] = parentDl.getAttribute('setData').replace(/[\r\n]/g,"");
	   var setData = eval('('+ parentDl.getAttribute('setData') + ')');
	   var setDishLength = setData.length;
	   var setTemplate = '<div class="setStyle"><img class="leftArrow" src="/static/meal/images/right_left_arrow.png"><span class="settitle">{0}</span><img class="rightArrow" src="/static/meal/images/right_right_arrow.png">{1}<div style="width:100%;height:40px;top:50%;"><img class="leftArrow middle" src="/static/meal/images/right_left_arrow.png"><img class="rightArrow middle" src="/static/meal/images/right_right_arrow.png"></div></div>';
	   
	   $(setData).each(function(i,n){
	      var baseHtml = '';
		  var liLength = $(n).length;
		  var className = '';
		  if(liLength == 1)
			 className = 'redborder';
		  
   	      $(n).each(function(ii,nn){
		    if(typeof(nn.style) !='undefined')
		    baseHtml += '<li class="{4}" attrid="{0}" grade="{3}" styleData=\'{2}\'>{1}</li>'.format(nn.id,nn.name,JSON.stringify(nn.style),nn.grade,className);
			else
			baseHtml += '<li class="{3}" attrid="{0}" grade="{2}">{1}</li>'.format(nn.id,nn.name,nn.grade,className);
		 });
		 baseHtml = '<ul>{0}</ul>'.format(baseHtml);
		 
		 html += setTemplate.format('第'+parseInt(i+1)+'道菜', baseHtml);
	   });
	   html += '<img class="bottom-rmb" src="/static/meal/images/rmb.png"/>';
	   html += '<span class="bottom-price">{0}</span>'.format(dPrice);
	   html += '<img class="bottomright-button addToList" src="/static/meal/images/dish_addMenu.png"/>';
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
		  totalPrice += parseFloat(dPrice);
		  totalCount += 1;
		  allDishCategoryList[dishCategory] = undefined != allDishCategoryList[dishCategory]?allDishCategoryList[dishCategory]+1:1;
		  var obj = {};
		  obj[dishId] = setDishList;
		  allDishObject.push(obj);
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
		   var html = '';
	       $(multiStyle).each(function(i,n){
	       var baseHtml = '';
		  
   	         $(n.data).each(function(ii,nn){
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
		   $('#page_allMenu').append(stylePopup);
		   
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
		 {
		  alert('已经是第1道菜了');
		  return;
		 }
	    $('.setStyle').addClass('dn');
		$('.setStyle').eq(index-1).removeClass('dn');
	   });
	   $('.setStyle .rightArrow').click(function(){
	    var index = getCurrentSetIndex()
		if(index == $('.setStyle').length-1)
		 {
		 alert('已经是最后一道菜了');
		 return;
		 }
	    $('.setStyle').addClass('dn');
		$('.setStyle').eq(index+1).removeClass('dn');
	   });
	   var modal = document.getElementByClass('mModal1')[0]; // 弹窗dom对象
       modal.addEventListener('touchmove', function(e) {
             e.preventDefault();
            }, false);
	 }
	 else if(1 == isMultiStyle)
	 {
	   localStorageObj['style'] = 'multi';
	   localStorageObj['data'] = parentDl.getAttribute('multiStyle').replace(/[\r\n]/g,"");
	   var multiStyleTemplate = '<div class="multiStyle"><div class="stitle"><span>{0}</span></div>{1}</div>';
	   var multiStyleBaseTemplate = '<div class="subtitle" attrid="{2}">{0}</div>{1}';
	   var multiStyle = eval('('+ parentDl.getAttribute('multiStyle') + ')');
	   $(multiStyle).each(function(i,n){
	      var baseHtml = '';
		  var className = '';
		  var liLength = $(n.data).length;
		  if(liLength == 1)
			 className = 'redborder';
   	      $(n.data).each(function(ii,nn){
		    baseHtml += '<li class="{2}"attrid="{0}"  >{1}</li>'.format(nn.id,nn.name,className);
		 });
		 baseHtml = '<ul>{0}</ul>'.format(baseHtml);
		 
		 html += multiStyleBaseTemplate.format(n.name, baseHtml,n.id);
	   });
	   html += '<img class="bottom-rmb" src="/static/meal/images/rmb.png"/>';
	   html += '<span class="bottom-price">{0}</span>'.format(dPrice);
	   html += '<img class="bottomright-button addToList" src="/static/meal/images/dish_addMenu.png"/>';
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
		   totalCount += 1;
		   totalPrice += parseFloat(dPrice);
		   allDishCategoryList[dishCategory] = undefined != allDishCategoryList[dishCategory]?allDishCategoryList[dishCategory]+1:1;
		   
		   var obj = {};
		   obj[dishId] = dishStyleList;
		   allDishObject.push(obj);
		   
		   refreshCategoryPrice(allDishCategoryList , allDishObject,totalCount ,totalPrice);
		   $('.popupWindow .close').click();
	   })
	   //TODO:addListener
	   $('.mModal1,.popupWindow').show();
	   $('.multiStyle ul li').click(function(){
	    $(this).parent().children('li').removeClass('redborder');
		$(this).addClass('redborder');
	   });
	   var modal = document.getElementByClass('mModal1')[0]; // 弹窗dom对象
       modal.addEventListener('touchmove', function(e) {
             e.preventDefault();
            }, false);
	 }
	 else //normal dish
	 {
	 
	   localStorageObj['style'] = 'normal';
	   totalPrice += parseFloat(dPrice);
	   totalCount += 1;
	   var isFindDish = false;
	   for(var i=0;i<allDishObject.length;i++)
	   {
	    if(Object.keys(allDishObject[i])[0] == dishId)
	    {
	     isFindDish = true;
	     allDishObject[i][dishId] ++;
	     break;
	    }
	   }
	   if(false == isFindDish)
	   {
	   var obj = {};
	   obj[dishId] = 1;
	   allDishObject.push(obj);
	   }
	   
	   allDishCategoryList[dishCategory] = undefined != allDishCategoryList[dishCategory]?allDishCategoryList[dishCategory]+1:1;
	   localStorage.setItem(dishId,JSON.stringify(localStorageObj));
	   refreshCategoryPrice(allDishCategoryList , allDishObject,totalCount ,totalPrice);
	   var startOffset = $(this).offset();
		//获取结束点坐标
		var endTop = $('#cartN').offset().top, endLeft = $('#cartN').offset().left + 35,left = startOffset.left+10,top = startOffset.top+10;
		var outer = $('#pointDivs .point-pre').first().removeClass("point-pre").css({left:left+'px',top:top+'px'});
		var inner = outer.find(".point-inner");
		setTimeout(function(){
			outer[0].style.webkitTransform = 'translate3d(0,'+(endTop - top)+'px,0)';
			inner[0].style.webkitTransform = 'translate3d('+(endLeft - left)+'px,0,0)';
			setTimeout(function(){
				outer.removeAttr("style").addClass("point-pre");
				inner.removeAttr("style");
			},600);
		},1);
	 }
	 localStorage.setItem(dishId,JSON.stringify(localStorageObj));
	})
	
	
	var canMinus = true;
	for(var i=0;i<minusLength;i++)
	{
	 minusBtn[i].addEventListener(_moveendEvt,function(){
	 if(!canMinus)
	  return;
	 canMinus = false;
	 var parentDl = this.parentNode.parentNode;
	 var dPrice = parentDl.getAttribute('dPrice');
	 var dishId = parentDl.getAttribute('dishid');
	 var dishCategoryPage = $(this).parent().parent().prevAll('div').attr('id')
	 var dishCategory = $('#navBar>dl').find('div[href="#'+dishCategoryPage+'"]').children('dd').attr('categoryId');
	 var currentDishCount = addMinusNormalDish(dishId,-1);
	 if(0 == currentDishCount)
	 {
	  $(this).fadeOut(0);
	  $(this).siblings('num').fadeOut(0);
	 }
	 canMinus = true;
	})
	}
	function refreshCategoryPrice(categoryList,dishList,totalcount,totalprice)
	{
	 $('.navOption').find('dd').children('span').text('').css('display','none');
	 for(var i in categoryList) {
	  $('.navOption').find('dd[categoryid='+i+']').children('span').css('display','inline-block').text(categoryList[i]);
	 }
	 // set cookies
	 var styleSetDishCount = {};
	 $(dishList).each(function(i,n){
		 var dishId = Object.keys(n)[0];
		 var dishInfo = eval('(' + localStorage.getItem(dishId) + ')');
		 var obj = $('#pInfo').find('dl[dishid='+dishId+']');
		 switch(dishInfo['style'])
		 {
		  case 'normal':		           
				   obj.children('.btn').children('num').text(n[dishId]);
		           obj.children('.btn').children('num').fadeIn(0);
				   obj.children('.btn').children('.minusDish').fadeIn(0);
				   break;
		  case 'multi':
		  case 'set':
		           styleSetDishCount[dishId] = (undefined == styleSetDishCount[dishId])?1:1+styleSetDishCount[dishId];
				   obj.children('.btn').children('numsmall').text(styleSetDishCount[dishId] );
		           obj.children('.btn').children('numsmall').fadeIn(0);
				   break;
				   
                   		  
		 }
	 });
	 document.cookie = "categoryList="+JSON.stringify(categoryList);
	 document.cookie = "dishList="+JSON.stringify(dishList);
	 document.cookie = "totalCount="+totalcount;
	 document.cookie = "totalPrice="+totalprice;
     totalprice = totalprice.toFixed(2);
     try{
	 _q("#totalprice").value = totalprice;
	 _q("#totalpriceshow").innerHTML = totalprice;
	 _q("#totalcount").value = totalcount;
	 _q("#totalcountshow").innerHTML = totalcount;
	 changeBtnSelect();
	 }
	 catch(e){;}
	 
	}
	
	
	$('.popupWindow .close').click(function(){
	 $('#popContent').html('');
	 $('.mModal1,.popupWindow').hide();
	})
    var view_const_dish_SPECIAL_PRICE_YES = '2';
    var view_const_dish_SPECIAL_PRICE_VIP = '3';
    var view_const_dish_HOT_YES = '2';
    function setHeight(){
        var  cHeight;
        cHeight = document.documentElement.clientHeight;
        cHeight = cHeight  -45 -$('.ddb-nav-header').height()+"px";
        document.getElementById("navBar").style.height =  cHeight;
        document.getElementById("infoSection").style.height =  cHeight;
		$('.shopInfoList').height(cHeight);
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
                        parentDl.getAttribute('dishot'),
						parentDl.getAttribute('dmarkNum')
                );
            }
        }
    }
    //后台可自行扩展参数
    //调用自定义弹层
	document.getElementByClass = function(n) { 
            var el = [],
                _el = document.getElementsByTagName('*');
            for (var i=0; i<_el.length; i++ ) {
 
                if (_el[i].className == n ) {
                    el[el.length] = _el[i];
                }
            }
            return el;
        }
    function popPic(imgUrl,title,price, isSpecial, specialPrice, people,teast,assess,isHot,dmarkNum){
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
                 _tmpHtml += "<i>"+specialPrice+"</i>";
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
            
			if(dmarkNum){
			_tmpHtml += "<h2>查看所有评论("+dmarkNum+")</h2>";
			}
            _tmpHtml += '</div>';
            MDialog.popupCustom(_tmpHtml,true, function(){}, true);
			var modal = document.getElementByClass('mModal')[0]; // 弹窗dom对象
            modal.addEventListener('touchmove', function(e) {
             e.preventDefault();
            }, false);
		    modal = document.getElementByClass('mDialog freeSet')[0]; // 弹窗dom对象
            modal.addEventListener('touchmove', function(e) {
             e.preventDefault();
            }, false);
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
		
		
    _onPageLoaded(function(){
        //changeBtnSelect();
        setHeight();
        //doSelect();
        //doSelectBtn();
        showPicInfo();
        //getDishNumOfCategory();
        if(_isIOS){
            _q("#page_allMenu section article").style.overflowY ="scroll";
            _q("#page_allMenu section article").style.minHeight ="85%";
            _q("#page_allMenu section article").style.marginBottom="20px";
        }
		var slidey=$('.banner').unslider({			
				keys: true,
				dots: true
			});
		data = slidey.data('unslider');
	    (function()
        {
         
		  if(get_cookie('totalPrice') != ''){
           totalPrice = parseFloat(get_cookie('totalPrice'));
	       totalCount = parseFloat(get_cookie('totalCount'));
	       allDishObject = eval('('+ get_cookie('dishList')+')');
	       allDishCategoryList = eval('('+ get_cookie('categoryList')+')');
	       refreshCategoryPrice(allDishCategoryList , allDishObject, totalCount ,totalPrice);
		   }
       })();		
    })
    window.addEventListener('orientationchange', function(){
        setHeight();
    })
	function clearAndGoBack()
	{
	if(parseInt(get_cookie('totalCount')) != 0 && !isNaN(parseInt(get_cookie('totalCount'))))
	{
	 MDialog.confirm(
                        '', '                       确定返回上一页？购物车将被清空。' , null,
                        '  确定  ', function(){
                           delCookie('totalPrice');
						   delCookie('totalCount');
						   delCookie('dishList');
						   delCookie('categoryList');
						   delCookie('guestNum');
				           delCookie('remark');
						   window.location.href='/oauth/meal/restaurant.do?hotelCode='+$('#hotelCode').val();
                        }, null,
                        '  取消  ', null, null,null, true, true
                    );
	  $('.mDialog').attr('style',$('.mDialog').attr('style') + '-webkit-box-sizing:initial;' );
      var left = $('.mDialog').offset().left - 25;	  
      $('.mDialog').attr('style',$('.mDialog').attr('style') + 'left:'+ left+ 'px' );		  
	}
	else
     window.location.href='/oauth/meal/restaurant.do?hotelCode='+$('#hotelCode').val();
	}
	function dishScroll()
	{
	//get dock point
     if(1 == isUserChangeCateogry)
	 {	 
	  return;
	 }
	 var currentScrollTop = $('#infoSection').scrollTop();
	 var topList=Array();
	 $('#pInfo').children('.pTitle').each(function(i,n){
	  if(i==0)
	  topList.push($(n).offset().top);
	  else
	  topList.push($(n).offset().top-topList[0]);
	 });
	 topList[0] = 0;
	 for(var i=topList.length;i>=0;i--)
	 {
	   if(currentScrollTop >= topList[i])
	    {
		 $('.navOption dd').removeClass("active");
	     $('.navOption dd').eq(i).addClass('active');
		 break;
		}
	 }
	 
	}
	$('#infoSection').bind('scrollstop', function(event) {
       if(1 == isUserChangeCateogry)
	    {
	   	 isUserChangeCateogry = 0;	   	
	    }	
    });
	var $pointDiv = $('<div id="pointDivs">').appendTo('body');
	for(var i = 0;i<5;i++){
		$('<div class="point-outer point-pre"><div class="point-inner"/></div>').appendTo($pointDiv);
	}
	window.alert = function(name){
      var iframe = document.createElement("IFRAME");
      iframe.style.display="none";
      iframe.setAttribute("src", 'data:text/plain,');
      document.documentElement.appendChild(iframe);
      window.frames[0].window.alert(name);
      iframe.parentNode.removeChild(iframe);
     };
</script>
	
</script>
</body>
</html>