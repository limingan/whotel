<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 酒店信息编辑</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
<style type="text/css">
.pc_icon_hotwater{background: url(/static/front/hotel/images/hotel_icon_rs.png) no-repeat;}
.hotelfacility{width:100%;margin-bottom: 15px;}
.hotelfacility tr{margin:10px;}
.hotelfacility td{margin:5px 0px 0px 20px;padding:0px; }
.hotelfacility td i{margin:0px 0px 0px 20px;padding:0px;}
.hotelfacility td span{margin:20px 0px 0px 5px;position:relative;top:2px;font-size:8px;}
.ht_facility p{font-size:13px;background-color: #fff;margin: 5px 0px 5px 15px;font-weight: 600;}
</style>
</head>
<c:set var="cur" value="sel-whotel" scope="request"/>
<c:set var="cur_sub" value="sel-hotel-list" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>酒店信息编辑</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateHotel.do" class="form-horizontal" method="post" id="hotelSubmitForm">
				<input type="hidden" name="id" value="${hotel.id}"/>
				<input type="hidden" name="code" id="hotelCode" value="${hotel.code}"/>
				<input type="hidden" name="name" value="${hotel.name}"/>
				<input type="hidden" name="tel" value="${hotel.tel}">
				<%-- <input type="hidden" name="facilitys" value="${fn:length(hotel.facilitys) }" id="facilitysLength"/> --%>
				<div class="form-body edit-Pictorial">
					<div class="form-group first">
						<label class="col-md-3 control-label">名称</label>
						<div class="col-md-4">
						 <p class="form-control-static">
						 	${hotel.name}
						 </p>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">酒店缩图</label>
						<div class="col-md-4">
				              <div id="miniaturePreview">
				                <c:if test="${hotel.miniature != null && hotel.miniature != ''}">
				                  <img src="${hotel.miniatureUrl}" width="80" height="75"/>
				                  <input type="hidden" name="miniature" value="${hotel.miniature}">
				                </c:if>
				              </div>
				              <div id="progressbar1" style="width: 76px;height:6px;display:inline-block;"></div><br/>
				             <div><span class="fm-uploadPic"><input name="file" type="file" class="uploadFile" id="miniatureFile" /><b>上传图片</b></span></div>
				        	(*最佳尺寸200*200像素，大小不能超过140KB)
				        </div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">酒店banner</label>
						<div class="col-md-4">
							<div id="bannerPreview">
								<c:if test="${fn:length(hotel.bannerUrls) > 0}">
								  <c:forEach items="${hotel.bannerUrls}" var="bannerUrl" varStatus="vs">
								  <span class="del-icon">
								   <input type="hidden" name="banner" value="${hotel.banners[vs.index]}">
                    			   <img src="${bannerUrl}" width="100" height="90" />
                    			   <i></i>
                    			   </span>
								  </c:forEach>
								</c:if>
								<div id="progressbar" style="width: 76px;height:6px;display:inline-block;"></div><br/>
							</div>
							<span class="fm-uploadPic"><input type="file" class="uploadFile"  id="bannerFile"/><b>上传banner</b></span> (*最佳尺寸400*270像素，大小不能超过140KB，*可上传多个Banner)
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">门票温泉电话</label>
						<div class="col-md-4">
							<input type="text" name="ticketTel" value="${hotel.ticketTel}" />
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">温泉banner</label>
						<div class="col-md-4">
							<div id="ticketBannerPreview">
								<c:if test="${fn:length(hotel.ticketBannerUrls) > 0}">
								  <c:forEach items="${hotel.ticketBannerUrls}" var="ticketBannerUrl" varStatus="vs">
								  <span class="del-icon">
								   <input type="hidden" name="ticketBanner" value="${hotel.ticketBanners[vs.index]}">
                    			   <img src="${ticketBannerUrl}" width="100" height="90" />
                    			   <i></i>
                    			   </span>
								  </c:forEach>
								</c:if>
								<div id="progressbar2" style="width: 76px;height:6px;display:inline-block;"></div><br/>
							</div>
							<span class="fm-uploadPic"><input type="file" class="uploadFile" id="ticketBannerFile"/><b>上传banner</b></span> (*最佳尺寸400*270像素，大小不能超过140KB，*不填默认酒店banner)
						</div>
					</div>
					
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-3 control-label">是否启用酒店设施</label>
						<div class="col-md-4">
							<label class="radio-inline"><span><input type="radio" name="isHotelFacilityConvert" id="enable" <c:if test="${hotel.isHotelFacilityConvert == true}">checked="checked"</c:if> value="true"/></span>启用</label>
							<label class="radio-inline"><input type="radio" name="isHotelFacilityConvert" id="disable" <c:if test="${hotel.isHotelFacilityConvert != true}">checked="checked"</c:if> value="false"/>禁用</label>
							<span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="radio-inline" style="color: #999999;">(说明：用于控制在移动端显示酒店设施)</span>
						</div>
					</div>
				</div>
				
				
				<div id="hotelFacilityState">
					<div class="form-group">
					    <label class="col-md-3 control-label"></label>
						<div class="col-md-4">
						<a href="javascript:" class="btn btn-sm green js-addColumn"><i class="fa fa-plus"></i>新增栏目</a>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label"></label>
						<div class="col-md-5">
						<table class="table table-striped table-bordered table-hover dataTable tem-table">
							<thead>
							<tr style="width:100%">
								   <th style="width:25%;"><input id="checkAll" name="isAllChecked" type="checkbox" <c:if test="${hotel.isAllChecked}">checked</c:if> />是否显示</th>
								   <th style="width:40%;">名称</th>
		                           <th style="width:20%;">图标</th>
		                           <th style="width:15%;">操作</th>
							</tr>
							</thead>
							<tbody id="columnTr" class="colum-tab-td">
							<c:forEach items="${hotel.facilitys}" var="facility" varStatus="vs">
								   <tr class="js-column">
								       <td><input type="checkbox" name="facilitys[${vs.index}].isChecked" class="js-check" <c:if test="${facility.isChecked}">checked</c:if> /></td>
			                           <td> 
			                           	 <input name="facilitys[${vs.index}].hotelFacilitieNames" type="text" class="js-columnName tem-columnName" value="${facility.hotelFacilitieNames}" maxlength="10" >
			                           </td> 
			                           <td>
							           <span class="fm-uploadPic tem-uploadPic"><input type="file" class="uploadFile js-uploadIcon"/><b>上传icon</b></span>
							           <span class="imagePreview">
								
								<c:if test="${facility.hotelFacilitieUrl != null && facility.hotelFacilitieUrl != ''}">
										<input type="hidden" value="${vs.index }" id="curIndex" />
			                    		<input type="hidden" name="facilitys[${vs.index}].hotelFacilitieUrls" value="${facility.hotelFacilitieUrls}">
			                    		<img src="${facility.hotelFacilitieUrl}" width="30" height="30" />
								</c:if>
							              </span>
			                           </td>
			                           <td >
			                           <a href="javascript:" class="btn btn-sm default js-deleteColumn"><i class="fa fa-times"></i> 删除</a>
			                           </td>
			                       </tr>
							</c:forEach>
							</tbody>
							</table>
						</div>
					</div>	
				</div>
				
					
					<div class="form-group">
					    <label class="col-md-3 control-label">餐饮接口地址</label>
						<div class="col-md-4">
						 <input type="text" name="mealUrl" value="${hotel.mealUrl}" class="form-control">
						</div>
					</div>
					<div class="form-group">
					    <label class="col-md-3 control-label">票券接口地址</label>
						<div class="col-md-4">
						 <input type="text" name="ticketUrl" value="${hotel.ticketUrl}" class="form-control">
						</div>
					</div>
					<c:if test="${companyId=='56287a74cb0d741a75d3d633'}">
					
						<div class="form-group">
						    <label class="col-md-3 control-label">秘钥</label>
							<div class="col-md-4">
							 <input type="text" name="key" value="${hotel.key}" class="form-control">
							</div>
						</div>
						
						<div class="form-group">
						    <label class="col-md-3 control-label">账号</label>
							<div class="col-md-4">
							 <input type="text" name="userName" value="${hotel.userName}" class="form-control">
							</div>
						</div>
						
						<div class="form-group">
						    <label class="col-md-3 control-label">企业码</label>
							<div class="col-md-4">
							 <input type="text" name="corpCode" value="${hotel.corpCode}" class="form-control">
							</div>
						</div>
					</c:if>
					<div class="form-group">
					    <label class="col-md-3 control-label">地址</label>
						<div class="col-md-4">
						 <input type="hidden" name="city" value="${hotel.city}" id="city">
						 <input type="text" name="address" value="${hotel.address}" readonly="readonly" class="form-control" id="addr">
						</div>
					</div>
					<div class="form-group">
					    <label class="col-md-3 control-label">坐标</label>
						<div class="col-md-4">
						 <input name="coord" id="coord" type="text" value="${hotel.coord}" readonly="readonly" class="form-control">
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
						<textarea name="descr" class="editor">${hotel.descr}</textarea>
						</div>
					</div>
				
				   <div class="form-group">
					    <label class="col-md-3 control-label">设施</label>
						<div class="col-md-4">
						<textarea name="facility" class="editor">${hotel.facility}</textarea>
						</div>
					</div>
					
					<div class="form-group">
					    <label class="col-md-3 control-label">周边</label>
						<div class="col-md-4">
						<textarea name="around" class="editor">${hotel.around}</textarea>
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
<input type="hidden" id="fieldIndex">
<script type="text/javascript" src="/static/common/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="/static/common/js/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script src="/static/admin/js/template.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/qiniu_upload.jsp" />
<script>
		

	$(function() {
		
		initUeditor();
		
		initMap();
		
		var hotelCode = $("#hotelCode").val();
		
		$.ajax({
			data:{hotelCode:hotelCode},
    		url:"/company/hotel/ajaxFindHConvert.do",
    		async:false,
    		dataType:"json",
    		success:function(data) {
    			if(data.isHotelFacilityConvert){
					$("#hotelFacilityState").show();
				}else{
					$("#hotelFacilityState").hide();
				}
    		}
    	});
		
		$("#checkAll").click(function(){
			if($(this).is(":checked")){
				$("#columnTr [class='js-check']").prop("checked",true);
			}else{
				$("#columnTr [class='js-check']").prop("checked",false);
			}	
		})
		
		$("body").on("click", "#miniatureFile", function() {
			var _this = $(this);
			setUploadToken();
			uploadImage(_this.get(), "progressbar1", miniatureCallback,200,200,140);//150*150
			//uploadFile(_this.get(), null, miniatureCallback);
		});
		$("body").on("click", "#bannerFile", function() {
			var _this = $(this);
			setUploadToken();
			uploadImage(_this.get(), "progressbar", bannerCallback,400,270,140);//500*340
			//uploadFile(_this.get(), null, bannerCallback,150,139,34);
		});
		$("body").on("click", "#ticketBannerFile", function() {
			var _this = $(this);
			setUploadToken();
			uploadImage(_this.get(), "progressbar2", ticketBannerCallback,400,270,140);//500*340
			//uploadFile(_this.get(), null, bannerCallback,150,139,34);
		});
		$("body").on("click", "#facilitiesFile", function() {
			var _this = $(this);
			setUploadToken();
			uploadImage(_this.get(), "progressbar3", facilitiesCallback,80,80,70);//500*340
			//uploadFile(_this.get(), null, bannerCallback,150,139,34);
		});
		
		Template.init();
		$(".js-addColumn").click(function() {
			addColumn();
		});
		
		$("body").on("click", ".js-uploadIcon", function() {
			var _this = $(this);
			setUploadToken();
			uploadFile(_this.get(), null, iconCallback);
		});
		
		$("body").on("click", ".js-deleteColumn", function() {
			var _this = $(this);
			_this.closest("tr").remove();
		});
		
		 $("#enable").click(function(){
			//$("#hotelFacilityState").show("slow");
			
			$("#hotelFacilityState").slideDown();
			
		}) 
		
		 $("#disable").click(function(){
			//$("#hotelFacilityState").hide("slow");
			$("#hotelFacilityState").slideUp();
		}) 
		
		$("form").submit(function(){
			var marketStatus = true;
			$(".js-column").each(function(obj){
					 var objLength1=$(this).val().length;
					 var marketPri  = $(this).find(".js-columnName").val();
					 var strLength = chkstrlen(marketPri);
					 if(strLength>10){
						 alert("酒店设施名称不能大于五个汉字，十个字母，请修改!")
						 marketStatus = false;
						 return false;
					 }
			});
			return marketStatus;
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
		//$("#bannerPreview").append(htmlContent);
		$("#progressbar").before(htmlContent);
	}
	function ticketBannerCallback() {
		var htmlContent = "<span class='del-icon'><input name='ticketBanner' type='hidden' value='"+$("#res_key").val()+"'>"
		+ "<img src='"+$("#res_url").val()+"?imageView2/2/w/100/h/90' width='120' height='80'/><i></i></span>";
		//$("#bannerPreview").append(htmlContent);
		$("#progressbar2").before(htmlContent);
	}
	function facilitiesCallback() {
		var htmlContent = "<span class='del-icon'><input name='facilities' type='hidden' value='"+$("#res_key").val()+"'>"
		+ "<img src='"+$("#res_url").val()+"?imageView2/2/w/100/h/90' width='120' height='80'/><i></i></span>";
		//$("#bannerPreview").append(htmlContent);
		$("#progressbar3").before(htmlContent);
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
	
	
	function iconCallback(file) {        
        var tdObj = $(file).closest("td");
		var index = tdObj.closest("tr").index();
		var html = "<input name='facilitys["+index+"].hotelFacilitieUrls' type='hidden' value='"+$("#res_key").val()+"' class='js-iconHidden'>"
        			+ "<img src='"+$("#res_url").val()+"' width='30' height='30'/>";
        tdObj.find(".imagePreview").html(html);
	}
	
	function addColumn() {
		
		var columnTr = $("#columnTr");
		var trObjs = columnTr.find("tr");
		
		var fieldIndexObj = $("#fieldIndex");
		var index = fieldIndexObj.val();
		if(index == "") {
			index = trObjs.length;
		}
	 	fieldIndexObj.val(parseInt(index) + 1);
	 	
		
		var html = '<tr class="js-column">'
				 + '<td>' 
  			 	 + '<input name="facilitys['+index+'].isChecked" type="checkbox" class="js-check" />'
  			 	 + '</td>'
        		 + '<td>' 
       			 + '<input name="facilitys['+index+'].hotelFacilitieNames" type="text" class="js-columnName tem-columnName" maxlength="10" >'
       			 + '</td>'
       			 + '<td>'
       			 + '<span class="fm-uploadPic tem-uploadPic"><input type="file" class="uploadFile js-uploadIcon"/><b>上传icon</b></span>'
       			 + '<span class="imagePreview">'
	             + '</span>'
       			 + '</td>'
       			 
          		 + '<td>'
       			 + '<a href="javascript:" class="btn btn-sm default js-deleteColumn"><i class="fa fa-times"></i> 删除</a>'
       			 + '</td>';
    	columnTr.append(html);
	}
	
	//统计输入字符串的长度  汉+2
	function chkstrlen(str){
		var strlen = 0;
		for(var i = 0;i < str.length; i++)
		{
			if(str.charCodeAt(i) > 255) //如果是汉字，则字符串长度加2
				strlen += 2;
			else
				strlen++;
			}
		return   strlen;
	}
	
</script>

<jsp:include page="/common/bootbox.jsp" />
