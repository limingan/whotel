<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>捷信达微酒店平台 | 关键词规则</title>
<link rel="stylesheet" href="/static/company/css/resource.css" />
</head>
<c:set var="cur" value="sel-publicno" scope="request"/>
<c:set var="cur_sub" value="sel-keywords" scope="request"/>
<div class="page-content-wrapper">
	<div class="portlet page-content">

		<div class="row">
			<div class="col-md-12">
				<ul class="page-breadcrumb breadcrumb">
					<li><i class="fa fa-home"></i> <a href="/company/index.do">首页</a> <i class="fa fa-angle-right"></i></li>
					<li>关键词规则</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>

		<div class="portlet-body form">
		<div id="creatRule">
		        <form action="updateKeywordRule.do" method="post" id="submitForm">
		        <input name="id" type="hidden" value="${keywordRule.id}"/>
			    <div class="cr-ruleTit">
			       <span>规则名：</span><input name="name" type="text" value="${keywordRule.name}" class="js-ruleName"><i>规则名最多60个字符</i>
			    </div>
			    
			     <div class="menuManage">
			       <c:if test="${!keywordRule.def}">
			         <div class="cr-keywords cr-height">
			             <h2><input type="checkbox" class="group-checkable js-allCheckbox">关键字</h2>     
			             <div class="cr-keyword-td">
			                <table class="js-keyword-table">
			                   <c:forEach items="${keywordRule.keywords}" var="keyword" varStatus="vs">
			                        <tr>
			                          <input name="keywords[${vs.index}].keyword" type="hidden" value="${keyword.keyword}" class="js-keyword"/>
			                          <input name="keywords[${vs.index}].type" type="hidden" value="${keyword.type}"  class="js-keywordType"/>
			                          <input name="keywords[${vs.index}].count" type="hidden" value="${keyword.count}" class="js-keywordCount"/>
				                      <td class="cr-td1"><input type="checkbox" class="checkboxes js-listCheckbox"><span>${keyword.keyword}</span></td>
				                      <td class="cr-td2"><span>${keyword.type.label}</span></td>
				                      <td class="cr-td3"><a href="javascript:;" class="btn btn-sm blue js-keywordEdit"><i class="fa fa-edit"></i>编辑</a></td>
				                    </tr>
			                   </c:forEach>
			                </table>
			             </div>
			              <div class="cr-removeBtn"><a href="javascript:;" class="cr-remove js-deleteKeyword">删除选中</a><a href="javascript:;" class="cr-addkeyword js-addKeyword">添加关键字</a></div>
			         </div>
			         </c:if>
			         <div class="mn-menuCon cr-height">
			           <h2>回复</h2>       
			           <div>
			               <jsp:include page="/WEB-INF/views/company/publicno/news_select.jsp" />
			            </div>
			         </div> 
			
			    </div> 
			   <div class="saveBtn"><a href="javascript:handleCheckData()" class="save">保存</a><a href="javascript:" class="goback">取消</a></div>
			   </form>   
			</div>
		</div>
	</div>
</div>
<input type="hidden" id="fieldIndex">
<script src="/static/common/js/checkAll.js" type="text/javascript"></script>
<script src="/static/company/js/keyword.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/goback.js?v=${version}" type="text/javascript"></script>
<jsp:include page="/common/bootbox.jsp" />