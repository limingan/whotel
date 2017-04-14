var PublicNo = function () {

    var handleValidation = function() {
    	
            var submitForm = $('#submitForm');
            var error = $('.alert-danger', submitForm);
            var success = $('.alert-success', submitForm);

            $.validator.addMethod("developerCodeExist", function(value, element) {  
            	var rs = false;
            	$.ajax({
            		url:"/admin/company/ajaxDeveloperCodeExist.do",
            		data:{developerCode:value},
            		async:false,
            		dataType:"json",
            		beforeSend:function(XMLHttpRequest) {
            			var oldDeveloperCode = document.getElementById('developerCode').defaultValue;
            			if($("#publicNoId").val().length>0 && $("#developerCode").val()==oldDeveloperCode){
            				return false;
            			}
            		},
            		success:function(data) {
            			rs = data;
            		}
            	});
      	        return !rs;
      	    }, "原始ID已经存在");
            
            $.validator.addMethod("accountExist", function(value, element) {  
            	var rs = false;
            	$.ajax({
            		url:"/admin/company/ajaxAccountExist.do",
            		data:{account:value},
            		async:false,
            		dataType:"json",
            		beforeSend:function(XMLHttpRequest) {
            			var oldAccount = document.getElementById('account').defaultValue;
            			if($("#publicNoId").val().length>0 && $("#account").val()==oldAccount){
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
                	'name': {
                        required: true
                    },
                    'developerCode': {
                        required: true,
                        developerCodeExist:true
                    },
                    'account': {
                    	required: true,
                    	accountExist:true
                    },
                    'pwd': {
                    	required: true
                    },
                    'appId': {
                    	required: true
                    },
                    'appSecret': {
                    	required: true
                    },
                    'authToken': {
                    	required: true
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
                    form.submit();
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