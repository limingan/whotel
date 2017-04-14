<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.modal-content {
	margin-top:18%;
}
</style>
<script src="/static/metronic/assets/plugins/bootbox/bootbox.min.js" type="text/javascript" ></script>
<script type="text/javascript">
<!--
$(function() {
	var msg = "${message}";
	
	var paramMsg = "${param.message}";
	if(paramMsg != "") {
		msg = paramMsg;
	}
	
	if (msg != "") {
		boxAlert(msg);
	}
});

function boxAlert(msg) {
	bootbox.alert({  
        buttons: {  
            ok: {  
                label: '确定'
            }
        },  
        message: msg	
	});
}

function boxConfirm(msg, callback) {
	// bootbox.confirm(msg, callback);
	bootbox.confirm(
			{  
		        buttons: {  
		            confirm: {  
		                label: '确定'
		            },  
		            cancel: {  
		                label: '取消'
		            }  
		        },  
		        message: msg,  
		        callback: callback
		        //title: "bootbox confirm也可以添加标题哦",		
			});
}

function confirmDeleteData(msg, url) {
	boxConfirm(msg, function(val) {
		if(val == true) {
			document.location=url;
		}
	});
}

function confirmCallback(msg, callback) {
	boxConfirm(msg, function(val) {
		if(val == true) {
			callback();
		}
	});
}

function confirmResult(msg, confirmLabe, cancelLabe, url) {
	bootbox.confirm(
			{  
		        buttons: {  
		            confirm: {  
		                label: confirmLabe
		            },  
		            cancel: {  
		                label: cancelLabe
		            }  
		        },  
		        message: msg,
		        callback: function(val) {
		    		if(val == true) {
		    			document.location=url;
		    		}
		    	}
			});
}
//-->
</script>
