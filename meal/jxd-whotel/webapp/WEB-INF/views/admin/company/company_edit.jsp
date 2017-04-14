<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 商户信息编辑</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
</head>
<c:set var="cur" value="sel-company" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/admin/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>商户信息编辑</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="updateCompany.do" class="form-horizontal" method="post" id="submitForm">
				<input type="hidden" name="id" value="${company.id}"/>
				<div class="form-body">
					<div class="form-group first">
						<label class="col-md-3 control-label">编码</label>
						<div class="col-md-4">
						  <p class="form-control-static">${company.code}</p>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">名称</label>
						<div class="col-md-4">
						   <input name="name" class="form-control" placeholder="名称" maxlength="50" value="${company.name}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">管理员账号</label>
						<div class="col-md-4">
						  <p class="form-control-static">${company.adminAccount}</p>
						  <input type="hidden" name="adminAccount" class="form-control" placeholder="管理员账号" maxlength="50" value="${company.adminAccount}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">管理员密码</label>
						<div class="col-md-4">
						   <input name="adminPwd" class="form-control" placeholder="管理员密码" value="${company.adminPwd}"/>
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
						<label class="col-md-3 control-label">公司地址</label>
						<div class="col-md-4">
						<input name="addr" class="form-control" placeholder="公司地址" maxlength="50" value="${company.addr}"/>
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
					
					<div class="form-group">
						<label class="col-md-3 control-label">有效时间</label>
						<div class="col-md-4">
							<input type="text" placeholder="有效时间" class="form-control date-picker input-medium" name="validTime" value='<fmt:formatDate value="${company.validTime}" pattern="yyyy-MM-dd"/>' >
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">集团</label>
						<div class="col-md-4">
						<label class="radio-inline">
						<span><input type="radio" name="group" value="false" <c:if test="${company.group==null || company.group==false}">checked</c:if>></span> 否</label>
						<label class="radio-inline">
						<input type="radio" name="group" value="true" <c:if test="${company.group==true}">checked</c:if>> 是</label>
						 </div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">允许多会员</label>
						<div class="col-md-4">
						<label class="radio-inline">
						<span><input type="radio" name="multipleMbr" value="false" <c:if test="${company.multipleMbr==null || company.multipleMbr==false}">checked</c:if>></span> 否</label>
						<label class="radio-inline">
						<input type="radio" name="multipleMbr" value="true" <c:if test="${company.multipleMbr==true}">checked</c:if>> 是</label>
						 </div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label">状态</label>
						<div class="col-md-4">
						<label class="radio-inline">
						<span><input type="radio" name="valid" value="false" <c:if test="${company.valid==null || company.valid==false}">checked</c:if>></span> 禁用</label>
						<label class="radio-inline">
						<input type="radio" name="valid" value="true" <c:if test="${company.valid==true}">checked</c:if>> 有效 </label>
						 </div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">权限集</label>
						<div class="col-md-4">
							<c:forEach items="${moduleTypes}" var="moduleType" varStatus="vs">
								<input type="checkbox" class="js_setModules" name="moduleTypes[${vs.index}]" value="${moduleType}" <c:if test="${moduleType.checked}">checked</c:if>/>${moduleType.label}&nbsp;&nbsp;
							</c:forEach>
						<!-- <input type="checkbox" class="js_setModules" value="HOTEL"/>酒店预订&nbsp;&nbsp;<input class="js_setModules" type="checkbox" value="TICKET"/>门票&nbsp;&nbsp;<input class="js_setModules" type="checkbox" value="MALL"/>商城 -->
						</div>
						<div class="col-md-7" id="js_modules">
						        &nbsp;&nbsp;
						        <c:forEach items="${modules}" var="module" varStatus="vs">
						        	<label class="checkbox-inline">
									<div class="checker"><span><input name="modules[${vs.index}].id" type="checkbox" data-moduleType="${module.moduleType}" value="${module.id}" <c:if test="${module.checked}">checked</c:if>></span></div> ${module.name} </label>
						        </c:forEach>
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

<script src="/static/admin/js/company.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script src="/static/metronic/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript" ></script>
<script src="/static/metronic/assets/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js" type="text/javascript" ></script>
<%--内容区域 结束--%>
<jsp:include page="/common/qiniu_upload.jsp" />
<script>
	$(function() {
		
		Company.init();
		
		$(".uploadFile").click(function() {
			setUploadToken();
		});

		uploadFile("#logoFile", null, logoCallback);
		
		uploadFile("#qrcodeFile", null, qrcodeCallback);
		
		$("body").on("click", ".del-icon i", function() {
			var $this = $(this).parent("span");
			$this.remove();
		});
		
		
		if (jQuery().datepicker) {
            $('.date-picker').datepicker({
                rtl: App.isRTL(),
                format: 'yyyy-mm-dd',
                autoclose: true,
                language: 'zh-CN',
            });
            $('body').removeClass("modal-open"); // fix bug when inline picker is used in modal
        }
		
		$(".js_setModules").click(function() {
			var _this = $(this);
			var moduleType = _this.val();
			var isChecked = _this.is(':checked');
			$("#js_modules").find("input[data-moduleType='"+moduleType+"']").each(function(){
				if(isChecked){
					if(!$(this).is(':checked')){
						$(this).click();
					}
				}else{
					if($(this).is(':checked')){
						$(this).click();
					}
				}
			});
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
</script>

<jsp:include page="/common/bootbox.jsp" />