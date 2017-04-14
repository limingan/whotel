<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 公众号菜单</title>
<link rel="stylesheet" href="/static/company/css/resource.css" />

</head>
<c:set var="cur" value="sel-publicno" scope="request"/>
<c:set var="cur_sub" value="sel-menu" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">
		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>公众号菜单</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
		<div id="menuManage">
	    <form>
	     <p class="editTip">可创建最多3个一级菜单，每个一级菜单下可创建最多5个二级菜单。</p>
	     <div class="menuManage">
	         <div class="mn-menuSlide js-menuSlide">
	            <h2>菜单管理<i class="js-subIcon subIcon" title="拖动菜单编辑菜单顺序"></i><i class="addIcon js-addIcon"></i><em class="mn-finishBtn js-finishBtn">完成</em><em class="mn-cancleBtn js-cancleBtn">取消</em></h2>
	             <ul class="sortable">
	               <c:forEach items="${menuItemsTree}" var="menuItem">
	                 	<li class="ui-state-default">
		                    <dl class="sortable2">
		                    <dt data-id="${menuItem.id}" class="js-menuItem "><a href="javascript:;"><span class="js-name">${menuItem.name}</span><i class="glyphicon glyphicon-plus js-addIcon"></i></a></dt>
		                    
		                    <c:forEach items="${menuItem.children}" var="subMenuItem">
		                    	<dd class="ui-state-default2 js-menuItem" data-id="${subMenuItem.id}">
		                    	<a href="javascript:;"><em class="glyphicon glyphicon-stop"></em><span class="js-name">${subMenuItem.name}</span></a>
		                    	<i class="glyphicon glyphicon-align-justify"></i>
		                    	</dd>
		                    </c:forEach>
		                    </dl>
	                 	</li>
	               </c:forEach>
	             </ul>
	         </div> 
	         <div class="mn-menuCon js-menuEvent" style="display:none">
	           <h2><span class="js-menuLevel"></span>：<span class="js-menuTitle"></span><a href="javascript:;" class="js-modifyMenuName">重命名</a><a href="javascript:;" class="js-deleteMenu">删除</a></h2>       
	           <div class="js-menuType mu-menuType">
	               <a href="javascript:showMenuEvent('NEWS')"><i>...</i>消息</a>
	               <a href="javascript:showMenuEvent('LINK')"><i class="glyphicon glyphicon-share-alt"></i>链接</a>
	            </div>
	            <form id="submitForm" method="post">
	            <input type="hidden" name="id"  id="js-curMenuItem" />
	         	<input type="hidden" id="js-curMenuEvent" />
	            <div class="js-menuMsg">
	               <p class="mn-tips">订阅者点击该子菜单会收到以下消息</p>
	               <jsp:include page="/WEB-INF/views/company/publicno/news_select.jsp" />
	            </div>
	            
	             <div class="js-menuUrl mu-menuUrl">
	               <p class="mn-tips">订阅者点击该子菜单会跳转到以下链接</p>
	               <input name="responseMsg.url" id="js-menuUrl" class="mu-menuInput"/>
	             </div>
	             
	             <div class="js-menuTop">
	               <p class="mn-tips">添加二级菜单</p>
	             </div>
	            <span class="mu-menuSaveBtn js-saveMenuBtn" style="display:none"><a href="javascript:"  class="save">保存</a></span>
	            <span  class="mu-menumodifyBtn js-modifyMenuBtn" style="display:none"><a href="javascript:"  class="save">修改</a></span>
	            </form>
	         </div>
	         
	         <div class="mn-menuCon js-menuTip">
	            <h2>菜单事件设置</h2>       
	            <div>
	               <p class="mn-tips">您可以添加菜单，并设置菜单响应动作</p>
	            </div>
	         </div>      
	    </div> 
	   <p class="editTip mu-editTip">编辑中：点击下方“保存并发布”，才能更新到手机上</p>
	   <div class="saveBtn"><a href="javascript:"  class="save js-publishMenu">保存并发布</a></div>
	   </form> 
		</div>
		</div>
	</div>
</div>

<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/validate.js?v=${version}" type="text/javascript"></script>
<script src="/static/company/js/menu.js?v=${version}" type="text/javascript"></script>

<jsp:include page="/common/bootbox.jsp" />