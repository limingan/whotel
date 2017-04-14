<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 商户信息</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<c:set var="cur_sub" value="sel-company-info" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>商户信息</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateCompany.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${company.id}"/>
				<input type="hidden" name="regionId" value="${company.regionId}" id="regionId"/>
				<div class="form-body">
					<div class="form-group first">
						<label class="col-md-3 control-label">编码</label>
						<div class="col-md-4">
						  <p class="form-control-static">${company.code}</p>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label"><span class="jxd_required">*</span>名称</label>
						<div class="col-md-4">
						   <input name="name" class="form-control" placeholder="名称" maxlength="50" value="${company.name}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">英文名称</label>
						<div class="col-md-4">
						   <input name="englishName" class="form-control" id="englishName" placeholder="英文名称" maxlength="50" value="${company.englishName}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">
						<c:if test="${company.group== true}">400</c:if>电话</label>
						<div class="col-md-4">
						<input name="tel" class="form-control" <c:if test="${company.group== true}">placeholder="全国400统一电话"</c:if> 
						 <c:if test="${!company.group}">placeholder="电话"</c:if>
						 maxlength="50" value="${company.tel}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">传真</label>
						<div class="col-md-4">
						<input name="fax" class="form-control" placeholder="传真" maxlength="50" value="${company.fax}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">邮政编码</label>
						<div class="col-md-4">
						<input name="zipcode" class="form-control" placeholder="邮政编码" maxlength="50" value="${company.zipcode}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">公司邮箱</label>
						<div class="col-md-4">
						<input name="email" class="form-control" placeholder="公司邮箱" maxlength="50" value="${company.email}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">公司官网</label>
						<div class="col-md-4">
						<input name="website" class="form-control" placeholder="公司官网" maxlength="50" value="${company.website}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">地区</label>
						<div class="col-md-4">
						<div class="input-group input-xlarge"> 
						<select id="provinceId" class="form-control input-small" style="display:none"></select>
						<span>&nbsp;</span>
						<select id="cityId" class="form-control input-small" style="display:none"></select>
						<span>&nbsp;</span>
						<select class="form-control input-small" id="districtId" style="display:none"></select>
						</div>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">公司地址</label>
						<div class="col-md-4">
						<input name="addr" class="form-control" placeholder="公司地址" maxlength="50" value="${company.addr}" id="addr"/>
						</div>
					</div>
					
					<div class="form-group">
					    <label class="col-md-3 control-label">坐标</label>
						<div class="col-md-4">
						 <input name="coord" id="coord" type="text" value="${company.coord}" readonly="readonly" class="form-control">
								<div>
								<div id="baidumap" style="border: 1px solid #666; width: 500px; height: 300px; margin:5px auto"></div>
								<div class="ma_line	fl mt5">
									<button type="button" class="btn" onclick="javascript:returnOrigin()">重置标注点</button>
								</div>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">logo</label>
						<div class="col-md-4">
				              <div id="logoPreview">
				                <c:if test="${company.logo != null && company.logo != ''}">
				                  <img src="${company.logoUrl}" width="120" height="80"/>
				                </c:if>
				              </div>
				             <div><span class="fm-uploadPic"><input name="file" type="file" class="uploadFile" id="logoFile" /><b>上传Logo</b></span></div>
				        </div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">二维码</label>
						<div class="col-md-4">
				              <div id="qrcodePreview">
				                <c:if test="${company.qrcode != null && company.qrcode != ''}">
				                  <img src="${company.qrcodeUrl}" width="120" height="80"/>
				                </c:if>
				              </div>
				             <div><span class="fm-uploadPic"> <input name="file" type="file" class="uploadFile" id="qrcodeFile" /><b>上传二维码</b></span></div>
				        </div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">公司介绍</label>
						<div class="col-md-7">
						<textarea name="descr" rows="10" cols="150" class="form-control">${company.descr}</textarea>
						</div>
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

<input type="hidden" id="provinceValue" value="${company.region.regionIds[0]}"/>
<input type="hidden" id="cityValue" value="${company.region.regionIds[1]}"/>
<input type="hidden" id="districtValue" value="${company.region.regionIds[2]}"/>

<script src="/static/common/js/region.js?v=${version}" type="text/javascript"></script>
<script src="/static/company/js/company.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
<%--内容区域 结束--%>
<jsp:include page="/common/qiniu_upload.jsp" />
<script>
	$(function() {
		
		Company.init();
		
		initMap();
		
		$(".uploadFile").click(function() {
			setUploadToken();
		});

		uploadFile("#logoFile", null, logoCallback);
		
		uploadFile("#qrcodeFile", null, qrcodeCallback);
		
		$("body").on("click", ".del-icon i", function() {
			var $this = $(this).parent("span");
			$this.remove();
		});
		
		$("#cityId").change(function() {
			showMapByAddr("city");
		});
	});
	
	function logoCallback() {
		 var filePreviewObj = $("#logoPreview");
	     var htmlContent = "<input name='logo' type='hidden' value='"+$("#res_key").val()+"'>"
	                     + "<img src='"+$("#res_url").val()+"' width='120' height='80'/>";
	     filePreviewObj.html(htmlContent);
	}
	
	function qrcodeCallback() {
		 var filePreviewObj = $("#qrcodePreview");
	     var htmlContent = "<input name='qrcode' type='hidden' value='"+$("#res_key").val()+"'>"
	                     + "<img src='"+$("#res_url").val()+"' width='120' height='80'/>";
	     filePreviewObj.html(htmlContent);
	}
	
	var map;
	var point;
	var marker;
	function initMap() {
		var mapXY = $("#coord").val();
	    var addr = $("#addr").val();
	    var city = $("#cityId option:selected").text();
		var mapX = "";
		var mapY = "";
		if(mapXY != "") {
			var xys = mapXY.split(",");
			if(xys.length == 2) {
				mapX = $.trim(xys[0]);
				mapY = $.trim(xys[1]);
			}
		}
			
		var zoom = 16;
		
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
		var cityName = $("#cityId option:selected").text();
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
		var regionText = $("#cityId option:selected").text();
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