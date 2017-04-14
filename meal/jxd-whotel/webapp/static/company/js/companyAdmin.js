var CompanyAdmin = function () {

    var handleValidation = function() {
    	
            var submitForm = $('#submitForm');
            var error = $('.alert-danger', submitForm);
            var success = $('.alert-success', submitForm);
            
            $.validator.addMethod("tel", function(value, element) {   
   			 var telOrMobile = /^[0-9]{11}$/;
   	    	    return this.optional(element) || (telOrMobile.test(value));
   	    	}, "请正确填写电话号码");   
            
            $.validator.addMethod("emailExist", function(value, element) {  
            	var rs = false;
            	$.ajax({
            		url:"/company/ajaxCompanyAdminEmailExist.do",
            		data:{email:value},
            		async:false,
            		dataType:"json",
            		beforeSend:function(XMLHttpRequest) {
            			var oldDeveloperCode = document.getElementById('email').defaultValue;
            			if($("#email").val()==oldDeveloperCode){
            				return false;
            			}
            		},
            		success:function(data) {
            			rs = data;
            		}
            	});
      	        return !rs;
      	    }, "邮箱已经存在");
            
            $.validator.addMethod("accountExist", function(value, element) {  
            	var rs = false;
            	value = value.trim();
            	$.ajax({
            		url:"/company/ajaxCompanyAdminAccountExist.do",
            		data:{account:value},
            		async:false,
            		dataType:"json",
            		beforeSend:function(XMLHttpRequest) {
            			var oldAccount = document.getElementById('account').defaultValue;
            			if($("#account").val().trim()==oldAccount){
            				return false;
            			}
            		},
            		success:function(data) {
            			rs = data;
            		}
            	});
      	        return !rs;
      	    }, "账号已经存在");
            
            
            submitForm.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {
                    'account': {
                    	maxlength: 30,
                        required: true,
                        accountExist:true
                    },
                    'name': {
                    	required: true
                    },
                    'repassword': {
   	                	equalTo:"#password"
   	                },
                    'email': {
                    	required: true,
                    	email: true,
                    	emailExist:true
                    },
                    'mobile': {
                    	required: true,
                    	tel:true
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
                    
                    var bo = true;
                    if($("#js_CUSTOMERSERVICE").is(':checked')){
                    	var startTime = parseInt($("#js_startTime").val().replace(":",""));
                		var endTime = parseInt($("#js_endTime").val().replace(":",""));
                		if(startTime>endTime){
                			alert("开始时间不能大于结束时间");
                			bo = false;
                		}
                    }
                    
                    if(bo == true){
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