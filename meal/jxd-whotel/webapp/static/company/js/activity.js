var Activity = function () {

    var handleValidation = function() {
    	
            var submitForm = $('#submitForm');
            var error = $('.alert-danger', submitForm);
            var success = $('.alert-success', submitForm);
            
            $.validator.addMethod("winningNumber", function(value, element) {
            	var everyDayNumber = $("#everyDayNumber").val();
            	if(everyDayNumber==''){
            		return true;
            	}
            	return value<=parseInt(everyDayNumber);
   	    	}, "中奖次数不可大于每天每人可抽奖次数");
            $.validator.addMethod("totalWinningNumber", function(value, element) {
            	var winningNumber = $("#winningNumber").val();
            	if(winningNumber=='' || value=='' || value == undefined){
            		return true;
            	}
            	return value>=parseInt(winningNumber);
   	    	}, "总中奖次数不可小于每天中奖次数");
            $.validator.addMethod("everyDayNumber", function(value, element) {
            	var totalNumber = $("#totalNumber").val();
            	if(totalNumber==''){
            		return true;
            	}
            	return value<=parseInt(totalNumber);
   	    	}, "每人每天可抽奖次数不可以大于可抽奖总数");
            $.validator.addMethod("focusUrl", function(value, element) {
            	var totalNumber = $("#focusUrl").val();
            	var activityType = $("#js_activityType").val();
            	if(totalNumber=='' && activityType=='TURNTABLE'){
            		return false;
            	}
            	return true;
   	    	}, "未关注用户，需要先跳转到此链接进行关注");
            $.validator.addMethod("keyword", function(value, element) {
            	var totalNumber = $("#keyword").val();
            	var activityType = $("#js_activityType").val();
            	if(totalNumber=='' && activityType=='VOICE'){
            		return false;
            	}
            	return true;
   	    	}, "语音关键字必须填写");
            submitForm.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {
                    'name': {
                        required: true,
                    },
                    'startTime': {
                    	required: true
                    },
                    'endTime': {
                    	required: true,
                    },
                    'winningNumber': {
                    	winningNumber:true
                    },
                    'totalWinningNumber':{
                    	totalWinningNumber:true
                    },
                    'totalNumber': {
                    	required: true,
                    },
                    'everyDayNumber': {
                    	required: true,
                    	everyDayNumber:true
                    },
                    'focusUrl': {
                    	focusUrl: true,
                    },
                    'keyword': {
                    	keyword: true,
                    }
                },
                
                invalidHandler: function (event, validator) { //display error alert on form submit              
                    success.hide();
                    error.show();
                    App.scrollTo(error, -200);
                },

                errorPlacement: function (error, element) { // render error placement for each input type
                    error.insertAfter(element.parent("div"));
                },

                highlight: function (element) { // hightlight error inputs
                    $(element).closest('.form-group').addClass('has-error'); // set error class to the control group   
                },

                unhighlight: function (element) { // revert the change done by hightlight
                    
                },

                success: function (label, element) {
                    $(element).closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
                },

                submitHandler: function (form) {
                    success.show();
                    error.hide();
                    $(".has-error").remove();
                    var check = true;
                    var map = {};
                    
                    var input = $("#prizeTr").find(".js_serialNumber");
            		if(input.length>6 && $("#js_activityType").val()=='TURNTABLE'){
            			$(".js-addPrize").closest("div").append("<div class='has-error'><font color='red'>大转盘的礼品数量不能超过6！</font></div>");
            			check = false;
            			return false;
            		}
                    
                    $(".js_serialNumber").each(function(index) {
                    	var _this = $(this);
                    	var labelValue = _this.val();
                    	if(map[labelValue]!=null){
                    		$(".js-addPrize").closest("div").append("<div class='has-error'><font color='red'>礼品序号不能重复！</font></div>");
                    		check = false;
                    		return false;
                    	}else{
                    		map[labelValue] = labelValue;
                    	}
                    	if(Number(labelValue)!=100){
                    		all = false;
                    	}
                    });
                    var prizeNumber = 0;
                    $(".js_prizeNumber").each(function(index) {
                    	var _this = $(this);
                    	prizeNumber +=Number(_this.val());
                    });
                    var totalNumber = parseInt($("#totalNumber").val());
                    if(totalNumber<prizeNumber){
                    	check = false;
                    	$(".js-addPrize").closest("div").append("<div class='has-error'><font color='red'>抽奖总次数不可以小于奖品数量</font></div>");
                    }
                    if($("#js_activityType").val()=='TURNTABLE'){//大转盘
                    	var input = $("#prizeTr").find(".js_serialNumber");
                    	if(input.length==6 && totalNumber!=prizeNumber){
                    		check = false;
                    		$(".js-addPrize").closest("div").append("<div class='has-error'><font color='red'>奖品数量为6个的时候，抽奖总数必须等于奖品数量</font></div>");
                    	}
                    }
                    
                    if($(".js_codeInput").length<=0){
                    	alert("您还没有礼品，不能提交");
                    	check = false;
                    }
                    
                    if(check) {
                    	form.submit();
                    }
                }
            });
    };

    return {
        //main function to initiate the module
        init: function () {
        	handleValidation();
        }
    };
}();


var SendCoupon = function () {
	
	var handleValidation = function() {
    	
        var submitForm = $('#submitForm');
        var error = $('.alert-danger', submitForm);
        var success = $('.alert-success', submitForm);
        
        $.validator.addMethod("everyDayNumber", function(value, element) {
        	var totalNumber = $("#totalNumber").val();
        	if(totalNumber==''){
        		return true;
        	}
        	return value<=parseInt(totalNumber);
	    	}, "每人每天可领取次数不可以大于每人可领取总数");
        $.validator.addMethod("focusUrl", function(value, element) {
        	var totalNumber = $("#focusUrl").val();
        	if(totalNumber==''){
        		return false;
        	}
        	return true;
	    	}, "未关注用户，需要先跳转到此链接进行关注");
        submitForm.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                'name': {
                    required: true,
                },
                'startTime': {
                	required: true
                },
                'endTime': {
                	required: true,
                },
                'everyDayNumber': {
                	required: true,
                	everyDayNumber:true
                },
                'focusUrl': {
                	focusUrl: true,
                }
            },
            
            invalidHandler: function (event, validator) { //display error alert on form submit              
                success.hide();
                error.show();
                App.scrollTo(error, -200);
            },

            errorPlacement: function (error, element) { // render error placement for each input type
                error.insertAfter(element.parent("div"));
            },

            highlight: function (element) { // hightlight error inputs
                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group   
            },

            unhighlight: function (element) { // revert the change done by hightlight
                
            },

            success: function (label, element) {
                $(element).closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
            },

            submitHandler: function (form) {
                success.show();
                error.hide();
                $(".has-error").remove();
                var check = true;
                var map = {};
                
                $(".js_serialNumber").each(function(index) {
                	var _this = $(this);
                	var labelValue = _this.val();
                	
                	if(map[labelValue]!=null){
                		$(".js-addPrize").closest("div").append("<div class='has-error'><font color='red'>礼品序号不能重复！</font></div>");
                		check = false;
                		return false;
                	}else{
                		map[labelValue] = labelValue;
                	}
                	if(Number(labelValue)!=100){
                		all = false;
                	}
                });
                var prizeNumber = 0;
                $(".js_prizeNumber").each(function(index) {
                	var _this = $(this);
                	prizeNumber +=Number(_this.val());
                });
                if(check) {
                	form.submit();
                }
            }
        });
};

return {
    //main function to initiate the module
    init: function () {
    	handleValidation();
    }
};
}();

var MarketingGame = function () {

    var handleValidation = function() {
    	
            var submitForm = $('#submitForm');
            var error = $('.alert-danger', submitForm);
            var success = $('.alert-success', submitForm);
            
            $.validator.addMethod("gameTime", function(value, element) {
            	var ex = /^\d+$/;
            	var gameTime = $("#gameTime").val();
        	    if (gameTime.length>0 && !ex.test(gameTime)) {
        	    	return false;
        	    }
        	    return true;
    	    	}, "游戏时长必须为正整数");
            $.validator.addMethod("focusUrl", function(value, element) {
            	var totalNumber = $("#focusUrl").val();
            	if(totalNumber==''){
            		return false;
            	}
            	return true;
   	    	}, "未关注用户，需要先跳转到此链接进行关注");
            submitForm.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {
                    'name': {
                        required: true,
                    },
                    'focusUrl': {
                    	focusUrl: true,
                    },
                    'gameTime': {
                    	gameTime: true,
                    }
                },
                
                invalidHandler: function (event, validator) { //display error alert on form submit              
                    success.hide();
                    error.show();
                    App.scrollTo(error, -200);
                },

                errorPlacement: function (error, element) { // render error placement for each input type
                    error.insertAfter(element.parent("div"));
                },

                highlight: function (element) { // hightlight error inputs
                    $(element).closest('.form-group').addClass('has-error'); // set error class to the control group   
                },

                unhighlight: function (element) { // revert the change done by hightlight
                    
                },

                success: function (label, element) {
                    $(element).closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
                },

                submitHandler: function (form) {
                    success.show();
                    error.hide();
                    $(".has-error").remove();
                    var check = true;
                    var map = {};
                    
                    var input = $("#prizeTr").find(".js_seqNumber");
            		if(input.length == 0){
            			$(".js-addPrize").closest("div").append("<div class='has-error'><font color='red'>请至少设置一关！</font></div>");
            			check = false;
            			return false;
            		}
                	if($("#js_prizeCode").val().length==0){
            			$("#js_prizeCode").closest("div").append("<div class='has-error'><font color='red'>请选择奖品！</font></div>");
                		check = false;
                		return false;
                	}
                    if(check) {
                    	form.submit();
                    }
                }
            });
    };

    return {
        //main function to initiate the module
        init: function () {
        	handleValidation();
        }
    };
}();