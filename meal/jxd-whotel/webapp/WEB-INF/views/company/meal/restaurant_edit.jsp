<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 餐厅管理</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
<style type="text/css">
input[type='checkbox']{width: 17px;height: 17px;}
</style>
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-meal-restaurant" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>编辑餐厅</li>
				</ul>
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="saveRestaurant.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${restaurant.id}"/>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">能否预订</label>
					<div class="col-md-4">
						<input type="checkbox" name="isEnable" <c:if test="${restaurant.isEnable}">checked="checked"</c:if>>
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">餐厅名称</label>
					<div class="col-md-4">
						<input type="text" name="name" value="${restaurant.name}"  class="form-control">
					</div>
				</div>
				
				
				<div class="form-group first">
					<label class="col-md-3 control-label">营业时间</label>
					<div class="col-md-4">
						<input type="text" name="businessTime" value="${restaurant.businessTime}" placeholder="7:00AM-6:30PM" class="form-control">
					</div>
				</div>
					
				<div class="form-group first">
					<label class="col-md-3 control-label">楼层</label>
					<div class="col-md-4">
						<input type="text" name="floor" value="${restaurant.floor}"  class="form-control">
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">服务费率</label>
					<div class="col-md-4">
						<input type="text" style="display: inline;width: 97%;" name="serviceFee" value="${restaurant.serviceFee}"  class="form-control">%
					</div>
				</div>
					
				<div class="form-group first">
					<label class="col-md-3 control-label">所属餐市</label>
					<div class="col-md-4">
						<input type="text" readonly="readonly" value="<c:forEach items="${shuffles}" var="shuffle" varStatus="vs"><c:if test="${vs.index != 0}">,</c:if>${shuffle.shuffleName}</c:forEach>"  class="form-control">
						<%-- <input type="checkbox" name="mealType" style="zoom: 130%;" <c:if test="${restaurant.isHasMealType('BREAKFAST')}">checked="checked"</c:if> value="BREAKFAST">早餐
						<input type="checkbox" name="mealType" style="zoom: 130%;" <c:if test="${restaurant.isHasMealType('LUNCH')}">checked="checked"</c:if> value="LUNCH">午餐
						<input type="checkbox" name="mealType" style="zoom: 130%;" <c:if test="${restaurant.isHasMealType('AFTERNOONTEA')}">checked="checked"</c:if> value="AFTERNOONTEA">下午茶
						<input type="checkbox" name="mealType" style="zoom: 130%;" <c:if test="${restaurant.isHasMealType('DINNER')}">checked="checked"</c:if> value="DINNER">晚餐 --%>
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">菜系</label>
					<div class="col-md-4">
						<input type="text" name="cuisine" value="${restaurant.cuisine}"  class="form-control">
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">订位方式</label>
					<div class="col-md-4">
						<select name="reservationWay" class="form-control">
							<option value="0" <c:if test="${restaurant.reservationWay == 0}">selected="selected"</c:if>>电话预定</option>
							<option value="1" <c:if test="${restaurant.reservationWay == 1}">selected="selected"</c:if>>在线预定</option>
						</select>
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">预定电话</label>
					<div class="col-md-4">
						<input type="text" name="tel" value="${restaurant.tel}"  class="form-control">
					</div>
				</div>
					
				<div class="form-group first">
					<label class="col-md-3 control-label">餐厅排序</label>
					<div class="col-md-4">
						<input type="text" name="orderIndex" value="${restaurant.orderIndex}"  class="form-control">
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">是否参与返现</label>
					<div class="col-md-4">
						<input type="checkbox" name="isCashBack" id="js_cashBack" <c:if test="${restaurant.isCashBack}">checked="checked"</c:if>>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span id="js_useShow" <c:if test="${!restaurant.isCashBack}">style="display: none;"</c:if>>使用方式
							<select name="useWay">	
								<option value="0" <c:if test="${restaurant.useWay == 0}">selected="selected"</c:if>>固定金额</option>
								<option value="1" <c:if test="${restaurant.useWay == 1}">selected="selected"</c:if>>百分比</option>
							</select>
							<input type="text" name="moneyPercentage" value="${restaurant.moneyPercentage}">
						</span>
					</div>
				</div>
				
				<div class="form-group first">
					<label class="col-md-3 control-label">支付方式</label>
					<div class="col-md-4">
						<input type="checkbox" name="payMents" <c:if test="${restaurant.isHasPayMent('WXPAY')}">checked="checked"</c:if> value="WXPAY">微信支付
						<input type="checkbox" name="payMents" <c:if test="${restaurant.isHasPayMent('BALANCEPAY')}">checked="checked"</c:if> value="BALANCEPAY">储值支付
						<input type="checkbox" name="payMents" <c:if test="${restaurant.isHasPayMent('OFFLINEPAY')}">checked="checked"</c:if> value="OFFLINEPAY">到店支付
					</div>
				</div>
					
				<div class="form-group">
					<label class="col-md-3 control-label">餐厅图片</label>
					<div class="col-md-4">
			              <div id="miniaturePreview">
			                <c:if test="${restaurant.miniature != null && restaurant.miniature != ''}">
			                  <img src="${restaurant.miniatureUrl}" width="80" height="75"/>
			                </c:if>
			              </div>
			              <div id="progressbar1" style="width: 76px;height:6px;display:inline-block;"></div><br/>
			             <div><span class="fm-uploadPic"> <input name="file" type="file" class="uploadFile" id="miniatureFile" /><b>上传图片</b></span>(*最佳尺寸200*200像素，大小不能超过20KB)</div>
			        </div>
				</div>
				
				<div class="form-group">
					<label class="col-md-3 control-label">餐厅导图</label>
					<div class="col-md-4">
						<div id="bannerPreview">
							<c:if test="${fn:length(restaurant.bannerUrls) > 0}">
							  <c:forEach items="${restaurant.bannerUrls}" var="bannerUrl" varStatus="vs">
							  <span class="del-icon">
							   <input type="hidden" name="banner" value="${restaurant.banners[vs.index]}">
                   			   <img src="${bannerUrl}" width="100" height="90" />
                   			   <i></i>
                   			   </span>
							  </c:forEach>
							</c:if>
							<div id="progressbar" style="width: 76px;height:6px;display:inline-block;"></div><br/>
						</div>
						<span class="fm-uploadPic"><input type="file" class="uploadFile"  id="bannerFile"/><b>上传banner</b></span> (*最佳尺寸400*300像素，大小不能超过20KB，*可上传多个Banner)
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-3 control-label">地址</label>
					<div class="col-md-4">
						<%-- <input type="hidden" value="${hotelBranchVO.city}" id="city"> --%>
						<input type="text" name="address" value="${restaurant.address}" class="form-control" id="addr">
					</div>
				</div>
				
				<div class="form-group">
					    <label class="col-md-3 control-label">坐标</label>
						<div class="col-md-4">
						 <input name="coord" id="coord" type="text" value="${restaurant.coord}" readonly="readonly" class="form-control">
								<div>
								<div id="baidumap" style="border: 1px solid #666; width: 500px; height: 300px; margin:5px auto"></div>
								<div class="ma_line	fl mt5">
									<button type="button" class="btn" onclick="javascript:returnOrigin()">重置标注点</button>
								</div>
							</div>
						</div>
					</div>
					
				<div class="form-group">
				    <label class="col-md-3 control-label">预订须知</label>
					<div class="col-md-4">
					<textarea name="notes" class="editor">${restaurant.notes}</textarea>
					</div>
				</div>
			
			   <div class="form-group">
				    <label class="col-md-3 control-label">简介</label>
					<div class="col-md-4">
					<textarea name="brief" class="editor">${restaurant.brief}</textarea>
					</div>
				</div>
				<div class="form-actions fluid">
					<div class="col-md-offset-3 col-md-9">
						<button type="submit" class="btn blue">提交</button>
						<button type="button" class="btn default goback">取消</button>
					</div>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
</div>
<script type="text/javascript" src="/static/common/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="/static/common/js/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/qiniu_upload.jsp" />
<script>
	$(function() {
		
		initUeditor();
		
		initMap();
		
		$("body").on("click", "#miniatureFile", function() {
			var _this = $(this);
			setUploadToken();
			//uploadFile(_this.get(), null, miniatureCallback);
			uploadImage(_this.get(), "progressbar1", miniatureCallback,200,200,20);//200*170
		});
		$("body").on("click", "#bannerFile", function() {
			var _this = $(this);
			setUploadToken();
			//uploadFile(_this.get(), null, bannerCallback);
			uploadImage(_this.get(), "progressbar", bannerCallback,400,300,20);//267*160
		});
		
		$("body").on("change", "#js_cashBack", function() {
			var _this = $(this);
			if(_this.is(':checked')){
				$("#js_useShow").show();
			}else{
				$("#js_useShow").hide();
			}
		});
	});
	
	function miniatureCallback() {
		var htmlContent = "<span class='del-icon'><input name='miniature' type='hidden' value='"+$("#res_key").val()+"'>"
		+ "<img src='"+$("#res_url").val()+"?imageView2/2/w/80/h/80' width='120' height='80'/><i></i></span>";
		$("#miniaturePreview").html(htmlContent);
	}
	
	function bannerCallback() {
		var htmlContent = "<span class='del-icon'><input name='banner' type='hidden' value='"+$("#res_key").val()+"'>"
		+ "<img src='"+$("#res_url").val()+"?imageView2/2/w/100/h/90' width='120' height='80'/><i></i></span>";
		$("#progressbar").before(htmlContent);
	}
	
	function initUeditor() {
		//初始化文本编辑器
		var option = {
				initialFrameWidth:430,  //初始化编辑器宽度,默认430
		        initialFrameHeight:150,  //初始化编辑器高度,默认150
			    //关闭字数统计
			    wordCount:false,
			    //关闭elementPath
			    elementPathEnabled:false,
			    //focus:true
			    //readonly:true
			    toolbars: [[
			                  'source', 'bold', 'italic', 'underline', 'justifyleft','justifycenter','justifyright','|', 'insertorderedlist','insertunorderedlist', '|', 'insertimage', '|', 
			                     		'removeformat','fontfamily', 'fontsize', 'forecolor', 'backcolor', 'music','insertvideo','link','unlink','inserttable','deletetable','emotion','map'
			    ]]
		}; 
		var editors = $(".editor");
		if(editors && editors.length > 0) {
			editors.each(function(i) {
				var _this = $(this);
				if(!_this.hasClass("edui-default")) {
					var editor = new baidu.editor.ui.Editor(option);
					editor.render(editors.get(i));
				}
			});
		}
	}
	
	var map;
	var point;
	var marker;
	function initMap() {
		var mapXY = $("#coord").val();
	    var addr = $("#addr").val();
	    var city = $("#city").val();
		var mapX = "";
		var mapY = "";
		if(mapXY != "") {
			var xys = mapXY.split(",");
			if(xys.length == 2) {
				mapX = $.trim(xys[0]);
				mapY = $.trim(xys[1]);
			}
		}
			
		var zoom = 12;
		
		map = new BMap.Map("baidumap");
		map.addControl(new BMap.NavigationControl({
			type : BMAP_NAVIGATION_CONTROL_LARGE
		})); 
		if (mapX != "" && mapY != "") {
			var x = parseFloat(mapX);
			var y = parseFloat(mapY);
			point = new BMap.Point(x,y);
			marker = new BMap.Marker(point);
			marker.enableDragging();
			map.centerAndZoom(point, zoom);
			map.addOverlay(marker);
			showMapByAddr('addr');
		} else if(city != "" && addr != "") {
			initResult();
			point = new BMap.Point(114.066164,
					22.549392);
			marker = new BMap.Marker(point);
			marker.enableDragging();
			map.centerAndZoom(point, zoom);
			map.addOverlay(marker);
			showMapByAddr('addr');
		} else {
			point = new BMap.Point(114.066164,
					22.549392);
			marker = new BMap.Marker(point);
			marker.enableDragging();
			map.centerAndZoom(point, zoom);
			showMapByAddr('city');
		}
		map.enableScrollWheelZoom();
		map.enableKeyboard();
		map.enableContinuousZoom();
		map.enableInertialDragging();
		
		// 事件
		marker.addEventListener("dragend",
				function(e) {
					setMarker(this, e.point);
				});
		map.addEventListener("click", function(e) {
			if (marker) {
				setMarker(marker, e.point);
			}
		});
	}
	
	function setMarker(marker, newPoint) {
		map.removeOverlay(marker);
		marker.point.lng = newPoint.lng;
		marker.point.lat = newPoint.lat;
		map.addOverlay(marker);
		map.panTo(newPoint);
		$("#coord").val(newPoint.lng+ "," +newPoint.lat);
	}
	function showMapByAddr(type) {
		var cityName = $(
				"form select[name=cityId]")
				.find('option:selected').text();
		var target = "";
		if (type == 'name') {
			target = $("#name").val();
		} else if (type == 'addr') {
			target = $("#address").val();
		} else if (type == 'city') {
			target = cityName;
		}
		var myGeo = new BMap.Geocoder();
		// alert(cityName);
		myGeo.getPoint(target, function(point) {
			if (point) {
				//alert("找到了指定地址：" + address + "【" + point.lat + "," + point.lng + "】");
				//map.centerAndZoom(point, parseInt(mapZ.value));
				map.panTo(point);
				setMarker(marker, point);
			} else {
				alert("没找到指定位置【" + target
						+ "】，请确认条件足够详细.");
			}
		}, cityName);
	}
	function returnOrigin() {
		//map.reset();
		initResult();
	}
	
	function initResult() {
		//设置 searchRequest
		var poiText = $("#addr").val();
		var regionText = $("#city").val();
		if (!regionText) {
			return;
		}

		//map.centerAndZoom(regionText, 10);
		var local = new BMap.LocalSearch(regionText, {
			  renderOptions:{map: map}
			});
		local.setPageCapacity(7);
		local.search(poiText);
	
		
		//var local = new map.LocalSearch(regionText);
		//local.search("景点");
		//LocalSearch ls = new BMap.LocalSearch(regionText);
		//ls.setPageCapacity(7);
		//ls.search(poiText);
	}
</script>

<jsp:include page="/common/bootbox.jsp" />
