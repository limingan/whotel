/**
 * 发送
*/
function sendMsm(btnObj, checkRsCallback) {
		var element = $("#mobile");
		var mobileVal = $.trim(element.val());
		if(mobileVal == "") {
			if(typeof(checkRsCallback) != "undefined" && typeof(checkRsCallback) == "function") {
				checkRsCallback("请输入手机号");
			}
			return false;
		}
		
		if (!/^\d{11}$/.test(mobileVal)){			
			if(typeof(checkRsCallback) != "undefined" && typeof(checkRsCallback) == "function") {
				checkRsCallback("请输入真实手机号");
			}
			return false;
		}
		
		//倒计时60秒
		var wait=60;
		function counTime(btnObj) {
		if (wait == 0) {
			btnObj.prop("disabled",false);
			btnObj.removeClass("over");
			btnObj.val("获取验证码");
			wait = 60;
		} 
		else {
			btnObj.prop("disabled", true);
			btnObj.val("重新发送(" + wait + ")");
			btnObj.addClass("over");
			wait--;
			setTimeout(function() {counTime(btnObj);},1000);
		}
		}

		//send
		$.ajax({
			type:"POST",
			url:"/sendSmsCode.do",
			data:{"mobile": mobileVal},
			cache:false,
			beforeSend: function(){
			},
			success: function(data){
				counTime(btnObj);
			}
		});
}

function checkSmsCode(successCallback, failureCallback) {
	var mobile = $("#mobile").val();
	var verifyCode = $("#verifyCode").val();
	//send
	$.ajax({
		type:"POST",
		url:"/checkSmsCode.do",
		data:{"mobile": mobile, "verifyCode":verifyCode},
		cache:false,
		dataType:"json",
		success: function(data){
			if(data == true) {
				if(typeof(successCallback) != "undefined" && typeof(successCallback) == "function") {
					successCallback();
				}
			} else if(data == false) {
				if(typeof(failureCallback) != "undefined" && typeof(failureCallback) == "function") {
					failureCallback();
				}
			}
		}
	});
}