<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="/static/common/js/uploadify/uploadify.css" />
<script language="javascript" type="text/javascript">  
    //防止客户端缓存文件，造成uploadify.js不更新，而引起的“喔唷，崩溃啦”  
    document.write("<script type='text/javascript' "  
            + "src='/static/common/js/uploadify/jquery.uploadify.js?" + new Date()  
            + "'></s" + "cript>");  
</script> 
<script type="text/javascript" src="/static/common/js/uploadify/uploadSetting.js"></script>