/* this is a 例子
var uploadParams = {
		"limitWidth":640,
		"widthCriterion":"equal",
		"limitHeight":480,
		"heightCriterion":"equal",
	    "sizeRange":"320x240",
	    "fileSizeLimit":"140KB",
	    "callBack":function(obj){};
};
*/

function upload3(param) {
	param.callBack=function(obj){
		if (obj.state != 'FAILURE') {
    		$("#picPath").val(obj.path);//重新设置提交图片的INPUT值
    		$("#showPic").attr("src",obj.url);//刷新显示新上传图片
    	}else{
    		alert("请上传符合比例的图片!");
    	}
	}
	upload2("uploadFileButton",param);//uploadFileButton上传图片的按钮ID
}

function upload2(inputFileId,uploadParams) {
	if(!uploadParams.fileType){
		uploadParams.fileType="resource";
	}
	if(!uploadParams.fileSizeLimit){
		uploadParams.fileSizeLimit="140KB";
	}
	upload(inputFileId,"","",uploadParams.fileType,"imageQueueId",uploadParams.fileSizeLimit,uploadParams.callBack,uploadParams);
}
//检测图片的宽度大小、高度大小,宽高比例,并上传图片
function upload(inputFileId,inputName,preViewId,fileType,queueID,fileSizeLimit,callBack,uploadParams) {
	
	uploadParams = uploadParams || {};
	
	var action = "/ajaxUploadFile.do?fileType=" + fileType;
	if(uploadParams) {
		if(uploadParams.sizeRange) {//检测图片的宽高比例
			action += "&sizeRange=" + uploadParams.sizeRange;
		}
		if(uploadParams.limitWidth && uploadParams.widthCriterion) {//检测图片的宽度大小
			action += "&limitWidth=" + uploadParams.limitWidth + "&widthCriterion=" + uploadParams.widthCriterion;
		}
		if(uploadParams.limitHeight && uploadParams.heightCriterion) {//检测图片的高度大小,并上传图片
			action += "&limitHeight=" + uploadParams.limitHeight + "&heightCriterion=" + uploadParams.heightCriterion;
		}
	}
	var btnText = "上传新图片";
	if(uploadParams.buttonText){
		btnText = uploadParams.buttonText;
	}
	var btnWidth = 80;
	if(uploadParams.width){
		btnWidth = uploadParams.width;
	}
	var btnHeight = 25;
	if(uploadParams.height){
		btnHeight = uploadParams.height;
	}
	var fileTypeDesc = "支持格式:jpg/gif/jpeg/png/bmp.";
	if(uploadParams.fileTypeDesc){
		fileTypeDesc = uploadParams.fileTypeDesc;
	}
	var fileTypeExts = "*.jpg;*.gif;*.jpeg;*.png;*.bmp";
	if(uploadParams.fileTypeExts){
		fileTypeExts = uploadParams.fileTypeExts;
	}
	var multi = false;
	if(uploadParams.multi!=null){
		multi = uploadParams.multi;
	}
	
	//alert(action);
	$("#"+inputFileId).uploadify({
		'swf': '/app_static/common/js/uploadify/uploadify.swf',  
		'uploader': action,
		'fileObjName': 'fileInput', //必须，和以下input的name属性一致                   
		'auto'  : true, //是否自动开始  
		'multi': multi, //是否支持多文件上传  
		  //'queueID': queueID,
		  'buttonText' : btnText,//自定义上传提示文字 victor 2014/3/22 增加
		  'width' : btnWidth,
		  'height' : btnHeight,
		'fileSizeLimit': fileSizeLimit, //设置单个文件大小限制
		'fileTypeDesc': fileTypeDesc, //如果配置了以下的'fileExt'属性，那么这个属性是必须的  
		'fileTypeExts': fileTypeExts,//允许的格式
		  'overrideEvents': ['onSelectError', 'onDialogClose'],
		  'onUploadStart' : function(file) {
	      //	this.addPostParam("fileNames",encodeURI(file.name));//改变文件名的编码
	      },
	      'onSelectError' : function(file, errorCode, errorMsg) {
	        	 alert("文件大小不能超过" + fileSizeLimit);
	        	 return false;
	        },
	      'onUploadError' : function(file, errorCode, errorMsg, errorString) {
	        	 alert("文件上传失败，请重试。");
	        	 return false;
	        },
	      'onUploadSuccess' : function(file, data, response) {
	     	  var obj = JSON.parse(data);
	          //var obj = eval('('+data+')');
	          if(obj.url && obj.url != "") {
		          callBack(obj,inputName,preViewId);
			  } else {
				  alert(obj.message);
			  }
	        }
	});
}