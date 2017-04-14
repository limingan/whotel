<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 商户微信支付配置</title>
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<c:set var="cur_sub" value="sel-pay-config" scope="request"/>

<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>支付配置</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updatePayConfig.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${payConfig.id}"/>
				<input type="hidden" id="js_logoUrl" value="${company.logoUrl}"/>
				<div class="form-body">
				
					<div class="form-group">
						<label class="col-md-3 control-label">支付类型</label>
						<div class="col-md-4">
							<select class="form-control" name="payType" id="js_payType">
								<option value="">请选择支付类型</option>
								<c:forEach items="${payTypes}" var="pt">
									<option value="${pt}" <c:if test="${pt == payConfig.payType}">selected</c:if>>${pt.label}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<c:if test="${isGroup}">
						<div class="form-group" id="js_hotel" <c:if test="${payConfig.payType=='WX'}">style="display: none;"</c:if>>
							<label class="col-md-3 control-label">酒店</label>
							<div class="col-md-4">
								<select class="form-control" name="hotelCode" id="js_hotelCode" onchange="refreshQr()">
									<option value="">请选择</option>
									<c:forEach items="${hotelBranchs}" var="hotelBranch">
										<option value="${hotelBranch.code}" <c:if test="${payConfig.hotelCode == hotelBranch.code}">selected</c:if>>${hotelBranch.cname}</option>						
							    	</c:forEach>
						    	</select>
							</div>
						</div>
					</c:if>
					
					<div class="form-group first">
						<label class="col-md-3 control-label"><span class="jxd_required">*</span>商户ID</label>
						<div class="col-md-4">
						   <input name="mchId" class="form-control js_trim" placeholder="商户ID" maxlength="50" value="${payConfig.mchId}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label"><span class="jxd_required">*</span>应用ID</label>
						<div class="col-md-4">
						<input name="appId" class="form-control js_trim" placeholder="应用ID" maxlength="50" value="${payConfig.appId}"/>
						</div>
					</div>
					
					<div class="form-group" id="js_apiKey" <c:if test="${payConfig.payType=='WX_PROVIDER'}">style="display: none;"</c:if>>
						<label class="col-md-3 control-label">应用KEY</label>
						<div class="col-md-4">
						<input name="apiKey" class="form-control js_trim" placeholder="应用KEY" value="${payConfig.apiKey}"/>
						</div>
					</div>
					
					<div class="form-group" id="js_cert" <c:if test="${payConfig.payType=='WX_PROVIDER'}">style="display: none;"</c:if>>
						<label class="col-md-3 control-label">证书</label>
						<div class="col-md-4">
				              <div id="certPreview">
				                <c:if test="${payConfig.cert != null && payConfig.cert != ''}">
				                 ${payConfig.certUrl}
				                 <input type="hidden" name="cert" value="${payConfig.cert}"/>
				                </c:if>
				              </div>
				              <div id="certProgressbar" style="display: none">
				                <div class="progress-label"></div>
				              </div>
				             <div> <input name="file" type="file" class="uploadFile" id="certFile" /></div>
				        </div>
					</div>
					
					<div class="form-group" id="js_pay" <c:if test="${payConfig.payType=='WX'}">style="display: none;"</c:if>>
						<label class="col-md-3 control-label">扫码支付</label>
						<div class="col-md-4">
							<div>
			                   	<a href="javascript:convertCanvasToImage()" id="js-qr" class="erm"></a>
			                   	<br/>（点击图片下载二维码）
			              	</div>
			               	<div style="display: none;">
			                   	<a href="javascript:" id="js-qr-hidden" class="erm"></a>
			              	</div>
				        </div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">状态</label>
						<div class="col-md-4">
						<label class="radio-inline"><span><input type="radio" name="valid" value="false" <c:if test="${payConfig.valid==false}">checked</c:if>></span> 禁用</label>
						<label class="radio-inline"><input type="radio" name="valid" value="true" <c:if test="${payConfig.valid!=false}">checked</c:if>> 有效 </label>
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
			<a href="javascript:" id="canvas1" class="erm"></a>
			<!-- END FORM-->
		</div>
	</div>
</div>
<input type="hidden" value="${payConfig.companyId}" id="js_companyId">
<script src="/static/company/js/payconfig.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/jquery.qrcode.min.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script src="/static/common/js/qrapi.js"></script>
<%--内容区域 结束--%>
<jsp:include page="/common/qiniu_upload.jsp" />
<script>
	$(function() {
		
		$("#submitForm").submit(function(){
			if($("#js_payType").val().length == 0){
				alert("请选择支付类型");
				return false;
			}
			
			$(".js_trim").each(function(){
				$(this).val($(this).val().trim());
			});
		});
		
		PayConfig.init();
		
		refreshQr();
		
		$("body").on("click", "#certFile", function() {
			var _this = $(this);
			var t = new Date().getTime();
			setUploadToken(null, null, t+".p12");
			uploadFile(_this.get(), null, certCallback);
		});
		
	});
	
	function refreshQr(){
		$("#js-qr").empty();
		$("#js-qr-hidden").empty();
		
		if($("#js_payType").val() == "WX_PROVIDER"){
			var path = "http://"+window.location.host+"/oauth/pay/scanCodePay.do?comid="+$("#js_companyId").val();
			if($("#js_hotelCode").length>0){
				var hotelCode = $("#js_hotelCode").val();
				if(hotelCode != ""){
					path += "&hotelCode="+hotelCode;
				}
			}
			showQr("js-qr", 150, 150, path);
			showQr("js-qr-hidden", 340, 340, path);
			
			var canvas0 = $("#js-qr-hidden").find("canvas")[0];
			var canvas1 = $("#js-qr").find("canvas")[0];
			var ctx0 = canvas0.getContext("2d");
			var ctx1 = canvas1.getContext("2d");
			
			var img = new Image();
			img.crossOrigin = "*";
			img.src = $("#js_logoUrl").val();
			img.onload = function() {
				ctx0.drawImage(img, 120, 120, 100, 100);
				ctx1.drawImage(img, 50, 50, 50, 50);
			    ctx0.canvas.toDataURL("image/jpeg", 1);
			}
			
		}else{
			$("#js_pay").hide();
		}
	}
	
	function certCallback() {
	     var filePreviewObj = $("#certPreview");
	     var htmlContent = "<input name='cert' type='hidden' value='"+$("#res_key").val()+"'>"
	                     +$("#res_url").val();
	     filePreviewObj.html(htmlContent);
	}
	
	$("#js_payType").change(function(){
		var payType = $(this).val();
		if(payType=="WX"){
			$("#js_apiKey").show();
			$("#js_cert").show();
			$("#js_pay").hide();
			$("#js_hotel").hide();
		}else{
			$("#js_apiKey").hide();
			$("#js_cert").hide();
			$("#js_pay").show();
			$("#js_hotel").show();
		}
		refreshQr();
	});
	
	function convertCanvasToImage() {
		var canvas = $("#js-qr-hidden").find("canvas")[0];
		var img = new Image();
		img = canvas.toDataURL("image/jpg");
		
		var filename = 'qr_' + (new Date()).getTime() + '.png';
		saveFile(img,filename);
	}
	
	function saveFile(data, filename){
	    var save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a');
	    save_link.href = data;
	    save_link.download = filename;
	   
	    var event = document.createEvent('MouseEvents');
	    event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
	    save_link.dispatchEvent(event);
	};
</script>

<jsp:include page="/common/bootbox.jsp" />