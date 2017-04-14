<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 分店信息编辑</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
</head>
<c:set var="cur" value="sel-meal" scope="request"/>
<c:set var="cur_sub" value="sel-meal-mealBranch" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">
		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>分店信息编辑</li>
				</ul>
			</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="saveMealBranch.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${mealBranch.id}"/>
				<input type="hidden" name="code" value="${mealBranch.code}"/>
				<div class="form-body edit-Pictorial">
					<div class="form-group first">
						<label class="col-md-3 control-label">名称</label>
						<div class="col-md-4">
						 	<p class="form-control-static">${mealBranch.cname}</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">banner</label>
						<div class="col-md-4">
							<div id="bannerPreview">
								<c:if test="${fn:length(mealBranch.bannerUrls) > 0}">
								  	<c:forEach items="${mealBranch.bannerUrls}" var="bannerUrl" varStatus="vs">
								  		<span class="del-icon">
								   			<input type="hidden" name="banner" value="${mealBranch.banners[vs.index]}">
                    			   			<img src="${bannerUrl}" width="100" height="90" />
                    			   			<i></i>
                    			   		</span>
								  	</c:forEach>
								</c:if>
							</div>
							<span class="fm-uploadPic"><input type="file" class="uploadFile"  id="bannerFile"/><b>上传banner</b></span> (* 可上传多个Banner)
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">地址</label>
						<div class="col-md-4">
						 	<input type="hidden" value="${mealBranch.city}" id="city">
						 	<input type="text" value="${mealBranch.address}" readonly="readonly" class="form-control" id="addr">
						</div>
					</div>
					<div class="form-group">
					    <label class="col-md-3 control-label">坐标</label>
						<div class="col-md-4">
							<input name="coord" id="coord" type="text" value="${mealBranch.coord}" readonly="readonly" class="form-control">
							<div>
								<div id="baidumap" style="border: 1px solid #666; width: 500px; height: 300px; margin:5px auto"></div>
								<div class="ma_line	fl mt5">
									<button type="button" class="btn" onclick="javascript:returnOrigin()">重置标注点</button>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
					    <label class="col-md-3 control-label">介绍</label>
						<div class="col-md-4">
							<textarea name="descr" class="editor">${mealBranch.descr}</textarea>
						</div>
					</div>
				   	<div class="form-group">
					   	<label class="col-md-3 control-label">设施</label>
						<div class="col-md-4">
							<textarea name="facility" class="editor">${mealBranch.facility}</textarea>
						</div>
					</div>
					<div class="form-group">
					    <label class="col-md-3 control-label">周边</label>
						<div class="col-md-4">
							<textarea name="around" class="editor">${mealBranch.around}</textarea>
						</div>
					</div>
					<div class="form-actions fluid">
						<div class="col-md-offset-3 col-md-9">
							<button type="submit" class="btn blue">提交</button>
							<button type="button" class="btn default goback">取消</button>
						</div>
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
		
		$("body").on("click", "#bannerFile", function() {
			var _this = $(this);
			setUploadToken();
			uploadFile(_this.get(), null, bannerCallback);
		});
		
	});
	
	function bannerCallback() {
		var htmlContent = "<span class='del-icon'><input name='banner' type='hidden' value='"+$("#res_key").val()+"'>"
		+ "<img src='"+$("#res_url").val()+"' width='120' height='80'/><i></i></span>";
		$("#bannerPreview").append(htmlContent);
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
