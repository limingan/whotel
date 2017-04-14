<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="newsResource" value="${responseMsg.newsResource}" />
<input type="hidden" name="responseMsg.newsId" value="${responseMsg.newsId}" id="newsMsg"/>
<input type="hidden" name="responseMsg.pic" value="${responseMsg.pic}" id="picMsg"/>
<div class="mn-inform">
     <ol class="js-nav-icon">
        <li <c:if test="${responseMsg == null || responseMsg.responseType=='NEWS'}">class="cur"</c:if>><a href="javascript:;"><i class="mn-tuwen js-tuwenType"></i>图文</a></li>
        <li <c:if test="${responseMsg.responseType=='PIC'}">class="cur"</c:if>><a href="javascript:;"><i class="mn-tupian js-picType"></i>图片</a></li>
        <li <c:if test="${responseMsg.responseType=='TEXT'}">class="cur"</c:if>><a href="javascript:;"><i class="mn-text js-textType"></i>文字</a></li>
        <li <c:if test="${responseMsg.responseType=='UNLOCK'}">class="cur"</c:if>><a href="javascript:;"><i class="fa fa-unlock js-unlock"></i>门锁</a></li>
        <li <c:if test="${responseMsg.responseType=='SCANCODE'}">class="cur"</c:if>><a href="javascript:;"><i class="fa fa-qrcode js-scancode"></i>扫码</a></li>
     </ol>
<ul>
    <li <c:if test="${responseMsg == null || responseMsg.responseType=='NEWS'}">class="cur"</c:if> id="js-tw">
     <c:if test="${newsResource != null}">
		<c:choose>
		<c:when test="${newsResource.multiple == true}">
		 <div  class="mn-tuwen-con mn-Dtuwen-con">
		<ol>
		  <c:forEach items="${newsResource.news}" var="item" varStatus="vs">
		  <c:choose>
			<c:when test="${vs.first}">
			<li class="xFirst">
			   <b><fmt:formatDate value="${newsResource.createTime}" pattern="MM月dd日"/></b>
			<dl>
			   <a href="/front/showNewsItem.do?idKey=${newsResource.id},${item.key}">
			   <dt class="news_thumb">
			       <img src="${item.coverUrl}" />
			   </dt>
			   <dd>${item.title}</dd>
			      </a>
			   </dl>
			</li> 
			</c:when>
			<c:otherwise>
			<li class="nList">
			     <a href="/front/showNewsItem.do?idKey=${newsResource.id},${item.key}">
			      <img src="${item.coverUrl}" />
			      <h4>${item.title}</h4>
			     </a>
			</li>
			</c:otherwise>
			</c:choose>
			</c:forEach>
		</ol>
		</div>
		</c:when>
		<c:otherwise>
			<div class="mn-tuwen-con">
			<c:set var="item" value="${newsResource.news[0]}"/>
			<h4><a href="/front/showNewsItem.do?idKey=${newsResource.id},${item.key}">${item.title}</a></h4>
			<i><fmt:formatDate value="${newsResource.createTime}" pattern="MM月dd日"/></i>
			<div class="news_thumb">
			<img src="${item.coverUrl}">
			</div>
			<p>${item.brief}</p>
			</div>
		</c:otherwise>
		</c:choose>
        <a href="javascript:;" id="jsTwDelete">删除</a >
      </c:if>
      <c:if test="${newsResource == null}">
      	<a href="javascript:;" id="jsTwBtn" class="twaddBtn">从图文库中选择</a>
      </c:if>
   	</li>
   	
   	<li <c:if test="${responseMsg.responseType=='PIC'}">class="cur"</c:if> id="js-pic">
       <c:if test="${responseMsg.pic != null && responseMsg.pic != ''}">
			<div class="mn-tuwen-con">
			<div class="news_thumb">
			<img src="${responseMsg.pic}">
			</div>
			</div>
        	<a href="javascript:;" id="jsPicDelete">删除</a >
       </c:if>
      <c:if test="${responseMsg.pic == null || responseMsg.pic == ''}">
      	<a href="javascript:;" id="jsPicBtn" class="twaddBtn">从图片库中选择</a>
      </c:if>
   	</li>
   	
    <li <c:if test="${responseMsg.responseType=='TEXT'}">class="cur"</c:if>>
     	<div class="mn-text-con">
      		<textarea name="responseMsg.text" id="textMsg">${responseMsg.text}</textarea>
       	</div>
       	<div class="mn-motionTip">
           <!-- <em>还可以输入<i>594</i>字</em>  --> 
        </div>
    </li>
    <li <c:if test="${responseMsg.responseType=='UNLOCK'}">class="cur"</c:if>>
     	<div class="mn-text-con">
      		<input name="responseMsg.unlock" id="unlock" type="hidden" value="unlock"/>
       	</div>
       	<div class="mn-motionTip">
           <em>点击开锁，提示正在开锁中，请稍后...</em>  
        </div>
    </li>
     <li <c:if test="${responseMsg.responseType=='SCANCODE'}">class="cur"</c:if>>
     	<input name="responseMsg.scancode" id="scancode" type="hidden" value="scancode"/>
       	<div class="mn-motionTip">
           <em>点击扫码，打开微信扫码工具</em>
        </div>
    </li>
</ul>
</div>
<script src="/static/common/js/dateUtil.js?v=${version}" type="text/javascript"></script>
<script src="/static/common/js/waterfall.js?v=${version}" type="text/javascript"></script>
<script src="/static/company/js/news_select.js?v=${version}" type="text/javascript"></script>