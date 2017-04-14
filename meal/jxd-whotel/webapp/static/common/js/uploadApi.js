//普通上传
var Qiniu_upload = function(fileObj, token, key, callback,progress) {
	var f = fileObj[0].files[0];
	var Qiniu_UploadUrl = "http://upload.qiniu.com";
    var xhr = new XMLHttpRequest();
    xhr.open('POST', Qiniu_UploadUrl, true);
    var formData, startDate=0;
    formData = new FormData();
    formData.append('key', key);
    formData.append('token', token);
    formData.append('file', f);
    var taking;
    xhr.upload.addEventListener("progress", function(evt) {
        if (evt.lengthComputable) {
            var nowDate = new Date().getTime();
            taking = nowDate - startDate;
            var x = (evt.loaded) / 1024;
            var y = taking / 1000;
            var uploadSpeed = (x / y);
            var formatSpeed = "";
            if (uploadSpeed > 1024) {
                formatSpeed = (uploadSpeed / 1024).toFixed(2) + "Mb\/s";
            } else {
                formatSpeed = uploadSpeed.toFixed(2) + "Kb\/s";
            }
            var percentComplete = Math.round(evt.loaded * 100 / evt.total);
            
            if(percentComplete != undefined){
            	$("#"+progress).progressbar({
            		value: percentComplete
            	});
            }
            
            console && console.log(percentComplete, ",", formatSpeed);
        }
    }, false);

    xhr.onreadystatechange = function(response) {
        if (xhr.readyState == 4 && xhr.status == 200 && xhr.responseText != "") {
            var blkRet = JSON.parse(xhr.responseText);
            console && console.log(blkRet);
            if(typeof callback == 'function') {
            	callback(fileObj);
            	$(".uploadFile").removeAttr("disabled");
            }
        } else if (xhr.status != 200 && xhr.responseText) {

        }
    };
    startDate = new Date().getTime();
    xhr.send(formData);
    $(".uploadFile").attr({"disabled":"disabled"});
};
	        
function uploadFile(file, progress, callback) {
    var fileObj = $(file);
    fileObj.unbind("change");
    fileObj.change(function() {
    	var token = $("#token").val();
    	var key = $("#res_key").val();
    	console && console.log(token+","+key+","+fileObj[0].files.length);
        if (fileObj[0].files.length > 0 && token != "" && key != "") {
        	
        	if(progress) {
        		var progressbar = $("#"+progress);
        		progressbar.show();
        	    progressbar.progressbar({
        	        value: false,
        	        change: function() {
        	        	//var val = progressbar.progressbar( "value" ) || 0;
        	        },
        	        complete: function() {
        	        	progressbar.hide();
        	        }
        	    });
        	}
            Qiniu_upload(fileObj, token, key, callback,progress);
        } else {
            console && console.log("form input error");
        }
    });
}

function uploadImage(file, progress, callback, width, height, size){
    var fileObj = $(file);
    fileObj.unbind("change");
    fileObj.change(function() {
    	var file = fileObj[0].files[0];
    	if(file != undefined && width>0 && height>0 && size>0){
    		
    		if(!/\.(gif|jpg|jpeg|png|gif)$/.test(file.name)){
    	    	alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
    	    	return false;
    		}
    		
    		if($("#js_getId").length > 0){
    			$("#js_getId").val(progress);
    		}
    		
    		var img = new Image();
        	img.src = window.URL.createObjectURL(file);
        	img.onload=function(){
        		if(width==img.width && height==img.height && file.size<(size*1024)){
        			var token = $("#token").val();
        	    	var key = $("#res_key").val();
        	    	console && console.log(token+","+key+","+fileObj[0].files.length);
        	        if (fileObj[0].files.length > 0 && token != "" && key != "") {
        	        	
        	        	if(progress) {
        	        		var progressbar = $("#"+progress);
        	        		progressbar.show();
        	        	    progressbar.progressbar({
        	        	        value: false,
        	        	        change: function() {
        	        	        	//var val = progressbar.progressbar( "value" ) || 0;
        	        	        },
        	        	        complete: function() {
        	        	        	progressbar.hide();
        	        	        }
        	        	    });
        	        	}
        	        	
        	            Qiniu_upload(fileObj, token, key, callback,progress);
        	        } else {
        	            console && console.log("form input error");
        	        }
        		}else{
        			alert("图片宽度必须是"+width+"高度是"+height + "且大小必须小于"+size+"KB");
        			return false;
        		}
        	}
    	}
    	
    });
 }
