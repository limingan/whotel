var CompanyRegister = function () {

    var handleValidation = function() {
    	
            var submitForm = $('#submitForm');
            var error = $('.alert-danger', submitForm);
            var success = $('.alert-success', submitForm);

            $.validator.addMethod("codeExist", function(value, element) {  
            	var rs = false;
            	$.ajax({
            		url:"/admin/company/ajaxCodeExist.do",
            		data:{code:value},
            		async:false,
            		dataType:"json",
            		success:function(data) {
            			rs = data;
            		}
            	});
      	        return !rs;
      	    }, "商户编码已经存在");   
            
            
            submitForm.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {
                	companyName: {
   	                    required: true,
   	                    maxlength: 20
   	                },
   	                code: {
   	                    required: true,
   	                    maxlength: 10,
   	                    codeExist:true
   	                },
   	                account: {
   	                    required: true,
   	                    maxlength: 20
   	                },
   	                password: {
   	                	required: true
   	                },
   	                repassword: {
   	                	required: true,
   	                	equalTo:"#password"
   	                },
   	                email:{
   	                	email:true
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