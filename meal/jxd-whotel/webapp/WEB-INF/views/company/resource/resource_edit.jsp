<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 图文素材编辑</title>
<link rel="stylesheet" href="/static/common/css/upload.css" />
<link rel="stylesheet" href="/static/company/css/resource.css" />
<link type="text/css" href="/static/common/js/ueditor/themes/default/css/ueditor.css" rel="stylesheet" />
<style type="text/css">
	.error {
		color:red;
	}
</style>
</head>
<c:set var="cur" value="sel-resource" scope="request"/>
<c:set var="cur_sub" value="sel-resource-news" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>图文素材编辑</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
			<div class="editBox">
			 <ul class="editObj js-preview">
			 <c:choose>
			   	<c:when test="${newsResource != null}">
			   		<c:forEach items="${newsResource.news}" var="item" varStatus="vs">
			   			<c:choose>
			   				<c:when test="${vs.first == true}">
			   					<li class="editFirst">        
						            <div class="text-center">
						               <p class="ediPicTXT"><span class="js-coverPreview"><img alt="封面图片" src="${item.coverUrl}"></span><i class="glyphicon glyphicon-pencil"></i></p>
						               <a href="javascript:;" class="glyphicon glyphicon-pencil pencil JS_pencil" ></a>
						               <h2 class="text-left js-title">${item.title}</h2>
						            </div>
						        </li> 
			   				</c:when>
			   				<c:otherwise>
			   				    <li class="editCur">
						           <h2 class="text-left js-title">${item.title}</h2>
						           <em class="js-coverPreview"><img alt="缩略图" src="${item.coverUrl}"></em>
						           <a  href="javascript:;" class="glyphicon glyphicon-pencil JS_pencil" ></a>
						           <a  href="javascript:;" class="glyphicon glyphicon-trash JS_delete" ></a>
						       </li>
			   				</c:otherwise>
			   			</c:choose>
			   		</c:forEach>
			   	</c:when>
			   	<c:otherwise>
			   		<li class="editFirst">        
			            <div class="text-center">
			               <p class="ediPicTXT"><span class="js-coverPreview">封面图片</span><i class="glyphicon glyphicon-pencil"></i></p>
			               <a href="javascript:;" class="glyphicon glyphicon-pencil pencil JS_pencil" ></a>
			               <h2 class="text-left js-title">标题</h2>
			            </div>
			        </li> 
			   	</c:otherwise>
		   		</c:choose>
		        <li class="raise">
		          <a href="javascript:;" class="glyphicon glyphicon-plus text-center js-addItem"></a>
		       </li>
		   </ul>
		   
   <form action="updateNewsResource.do" method="post" id="submitForm" class="formClass">
   <input type="hidden" name="id" value="${newsResource.id}"/>
   <input type="hidden" name="mediaId" value="${newsResource.mediaId}"/>
   <div id="areaContain">
   <c:choose>
   	<c:when test="${newsResource != null}">
   		<c:forEach items="${newsResource.news}" var="item" varStatus="vs">
   		   <div class="editArea <c:if test="${vs.first}">cur</c:if>">
		       <i class="arrowEdt"><img src="/static/company/images/arrowEdt.png"></i>
		       <div>
		          <ul>
		             <li>
		                <dl>
		                    <dt>标题</dt>
		                    <dd class="editInput">
		                      <input type="text" name="news[${vs.index}].title" value="${item.title}" class="JS_textSize js-titCon"><em  class="JS_textNum"><i>0</i>/<abbr>64</abbr></em>
		                    </dd>
		                 </dl>    
		              </li>
		              <li>
		                <dl>
		                    <dt>作者<abbr>（选填）</abbr></dt>
		                    <dd class="editInput">
		                      <input type="text" name="news[${vs.index}].author" value="${item.author}" class="JS_textSize"><em  class="JS_textNum"><i>0</i>/<abbr>8</abbr></em>
		                    </dd>
		                 </dl>    
		              </li>
		               <li>
		                <dl>
		                    <dt>封面<abbr>（大图片建议尺寸：900像素 * 500像素）</abbr></dt>
		                    <dd>
		                      <span class="fm-uploadPic"><input type="file" name="file" class="uploadFile"><b>上传</b></span>
		                      <div class="imagePreview">
		                      <input type="hidden" name="news[${vs.index}].cover" value="${item.cover}" class="js-coverHidden"/>
		                      <img src="${item.coverUrl}" width="150" height="120"/>
		                      </div>
		                    </dd>
		                    <dd class="frontImgcheck">
		                       <input type="checkbox" name="news[${vs.index}].showCover" value="true" <c:if test="${item.showCover}">checked</c:if>><span>封面图片显示在正文中</span>
		                    </dd> 
		                 </dl>    
		              </li>
		               <li>
		                <dl>
		                    <dt>摘要<abbr>（选填，该摘要只在发送图文消息为单条时显示）</abbr></dt>
		                    <dd class="summry">
		                      <textarea name="news[${vs.index}].brief" class="JS_textSize">${item.brief}</textarea>
		                      <em  class="JS_textNum"><i>0</i>/<abbr>120</abbr></em>
		                    </dd>
		                 </dl>    
		              </li>
		               <li>
		                <dl>
		                    <dt>正文<abbr></abbr></dt>
		                    <dd class="bodyText" >
		                      <textarea class="editor" name="news[${vs.index}].content">${item.content}</textarea>
		                    </dd>
		                 </dl>    
		              </li>
		               <li>
		                <dl>
		                    <dt>原文链接<abbr>（选填）</abbr></dt>
		                    <dd class="editInput link">
		                     <input type="text" name="news[${vs.index}].url" value="${item.url}" class="js-url">
		                    </dd> 
		                 </dl>    
		              </li>
		           </ul>
		       </div>
		   </div>
   		</c:forEach>
   	</c:when>
   	<c:otherwise>
   		<div class="editArea cur">
		       <i class="arrowEdt"><img src="/static/company/images/arrowEdt.png"></i>
		       <div>
		          <ul>
		             <li>
		                <dl>
		                    <dt>标题</dt>
		                    <dd class="editInput">
		                      <input type="text" name="news[0].title"  class="JS_textSize js-titCon"><em  class="JS_textNum"><i>0</i>/<abbr>64</abbr></em>
		                    </dd>
		                 </dl>    
		              </li>
		              <li>
		                <dl>
		                    <dt>作者<abbr>（选填）</abbr></dt>
		                    <dd class="editInput">
		                      <input type="text" name="news[0].author" value="${item.author}" class="JS_textSize"><em  class="JS_textNum"><i>0</i>/<abbr>8</abbr></em>
		                    </dd>
		                 </dl>    
		              </li>
		               <li>
		                <dl>
		                    <dt>封面<abbr>（大图片建议尺寸：900像素 * 500像素）</abbr></dt>
		                    <dd>
		                      <span class="fm-uploadPic"><input type="file" name="file" class="uploadFile"><b>上传</b></span>
		                      <div class="imagePreview">
		                      <input type="hidden" name="news[0].cover" class="js-coverHidden"/>
		                      </div>
		                    </dd>
		                    <dd class="frontImgcheck">
		                       <input type="checkbox" name="news[0].showCover" value="true"><span>封面图片显示在正文中</span>
		                    </dd> 
		                 </dl>    
		              </li>
		               <li>
		                <dl>
		                    <dt>摘要<abbr>（选填，该摘要只在发送图文消息为单条时显示）</abbr></dt>
		                    <dd class="summry">
		                      <textarea name="news[0].brief" class="JS_textSize"></textarea>
		                      <em class="JS_textNum"><i>0</i>/<abbr>120</abbr></em>
		                    </dd>
		                 </dl>    
		              </li>
		               <li>
		                <dl>
		                    <dt>正文<abbr></abbr></dt>
		                    <dd class="bodyText" >
		                      <textarea class="editor" name="news[0].content"></textarea>
		                    </dd>
		                 </dl>    
		              </li>
		               <li>
		                <dl>
		                    <dt>原文链接<abbr>（选填）</abbr></dt>
		                    <dd class="editInput link">
		                     <input type="text" name="news[0].url" class="js-url">
		                    </dd> 
		                 </dl>    
		              </li>
		           </ul>
		       </div>
		   </div>
   	</c:otherwise>
   </c:choose>
   </div>
   <p class="editTip"></p>
   <div class="saveBtn">
   <a href="javascript:updateNewsResource()"  class=" save">保存</a>
   <a href="javascript:" class="goback">取消</a>
   </div>
   </form>
</div>
	</div>
</div>
<jsp:include page="/common/qiniu_upload.jsp" />
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/validate.js?v=${version}" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="/static/common/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/static/common/js/ueditor/ueditor.all.js"></script>
<script src="/static/company/js/resource.js?v=${version}" type="text/javascript"></script>

<jsp:include page="/common/bootbox.jsp" />
