<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="modal-backdrop fade in" style="display: none"></div>
  <div id="file-dalig" style="position: absolute;z-index: 99999999999999; height:1000px; background-color:#FFF;display: none">
	<div class="screenshot" style="width: 540px; height: 960px; display: inline-block;">
			<div class="img_content" style="display: inline-block;overflow: hidden;"></div>
			<div class="img_select" style="position: position:absolute; left:0px; top:0px;"></div>
     </div>
     <div>
     <span class="fm-uploadPic"><input type="button" id="img_shear" /><b>剪切</b></span>
     <span class="fm-uploadPic"><input type="button" id="img_compress" /><b>压缩</b></span>
     <span class="fm-uploadPic"><input type="button" id="img_close" onclick="$('#file-dalig').hide();$('.modal-backdrop').hide()" /><b>关闭</b></span>
     </div>
	 <div id="finish_pic">
        <canvas id="cvs-id" style="display:none;"></canvas>
      </div>
  </div>
  
  <script type="text/javascript">
  function strToJson(str){
	   var json = eval('(' + str + ')'); 
	   return json; 
	} 

	function fileSize(str){
	  var fileSize;
	   if(str.indexOf('=')>0){
	      var indexOf=str.indexOf('=');
	      str=str.substring(0,indexOf);
	   }
	    return fileSize;
	}

	 function putb64(picBase,callback){
	  var token = $("#token").val();
	  var key = $("#res_key").val();
	  picBase=picBase.substring(22);
	  var url = "http://up.qiniu.com/putb64/-1"; 
	  var xhr = new XMLHttpRequest();
	  xhr.onreadystatechange=function()
	  {
	     if(xhr.readyState==4){
	          var keyText=xhr.responseText;
	          keyText=strToJson(keyText);
	          $(".uploadFile").removeAttr("disabled");
	          $("#res_key").val('http://7xljym.com1.z0.glb.clouddn.com/'+keyText.key)
	          picCallback();
	      }
	  }
	  xhr.open("POST", url, true); 
	  xhr.setRequestHeader("Content-Type", "application/octet-stream"); 
	  xhr.setRequestHeader("Authorization", "UpToken "+token);
	  xhr.send(picBase);
	 }
	 
	 var imgContent = $(".img_content")
	 var imgSelect = $(".img_select")
	 var fileObj = $(".uploadFile");
	 var limitWidth = 300;
	 var limitHeight =300;

	 fileObj.change(function(){
	 	var img = new Image(); 
	 	img.src = window.URL.createObjectURL(fileObj[0].files[0]); 
	 	
	 	img.onload=function(){ 
	 		imgSelect.width(limitWidth-2);
	 		imgSelect.height(limitHeight-2); 

	 		imgContent.append(img); 
	 		$("#file-dalig").show();
	 		$(".modal-backdrop").show();
	 	} 
	 	window.setTimeout(function(){
	 		$(".screenshot").width($(img).width());
	 		$(".screenshot").height($(img).height());
	 		window.clearInterval();
	 	}, 500) ;
	 });




	 imgSelect.mousedown(function (event) {
	 	var isMove = true;
	 	var contentLeft = imgContent.offset().left;
	 	var contentTop = imgContent.offset().top;
	 	
	 	var imgSelectLeft = imgSelect.offset().left;
	 	var imgSelectTop = imgSelect.offset().top;
	 	
	 	var width = imgContent.width() - imgSelect.width() - 2;
	 	var height = imgContent.height() - imgSelect.height() - 2;
	 	$(document).mousemove(function (e) {
	 		if (isMove) {

	 			var left = e.pageX - event.pageX ;//x偏移量
	 			var top = e.pageY - event.pageY ;//y偏移量
	   
	 			if(left+imgSelect.width() <= imgContent.width()-1 && left>=0)
	 				imgSelect.css({'left': left});
	 			if(top+imgSelect.height() <= imgContent.height()-1 && top>=0)
	 			 	imgSelect.css({'top': top});
	 			
	 		}
	 	}).mouseup(function () {
	 		isMove = false; 
	 	}); 
	 }); 

	 $("#img_shear").click(function(){
	 	var cvs = document.getElementById("cvs-id");
	 	$("#finish_pic").append(cvs);
	 	cvs.display='inline-block';
	 	cvs.width = limitWidth;
	 	cvs.height = limitHeight;
	 	var ctx = cvs.getContext("2d");
	 	var img = new Image();
	 	img.crossOrigin = '';
	 	img.src = window.URL.createObjectURL(fileObj[0].files[0]);
	 	img.onload=function(){
	 		var ctx = cvs.getContext("2d");
	 		//图片对象，左右移动，上下移动，原图宽，原图高，内左边距，内上边距，压缩后宽，压缩后高
	 		ctx.drawImage(img, (imgSelect.offset().left-imgContent.offset().left)*(img.width/imgContent.width()), (imgSelect.offset().top-imgContent.offset().top)*(img.height/imgContent.height()), img.width, img.height, 0, 0, imgContent.width(), imgContent.height());
	 		var  dataUrl=cvs.toDataURL("image/png");
	 		putb64(dataUrl);
	 	}

	 });

	 $("#img_compress").click(function(){
	 	var cvs = document.createElement('canvas');
	 	$("#finish_pic").append(cvs);
	 	cvs.display='inline-block';
	 	cvs.width = limitWidth;
	 	cvs.height = limitHeight;
	 	var ctx = cvs.getContext("2d");
	 	var img = new Image();
	 	img.crossOrigin = '';
	 	img.src = window.URL.createObjectURL(fileObj[0].files[0]);
	 	img.onload=function(){
	 		var ctx = cvs.getContext("2d");
	 		ctx.drawImage(img, 0, 0, img.width, img.height, 0, 0, limitWidth, limitHeight);
	 		var  dataUrl=cvs.toDataURL("image/png");
	 		putb64(dataUrl);
	 	}

	 });
  
  </script>