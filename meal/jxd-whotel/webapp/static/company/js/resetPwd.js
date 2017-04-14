var ResetPwd = function () {

	var handleResetPwd = function() {
		 $('.resetPwd-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	            rules: {
	                password: {
	                	required: true
	                },
	                repassword: {
	                	required: true,
	                	equalTo:"#password"
	                }
	            },

	            messages: { // custom messages for radio buttons and checkboxes
	                password: {
	                	required: "登录密码不能为空"
	                },
	                repassword: {
	                	required: "确认密码不能为空"
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   

	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.form-group').addClass('has-error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                if (element.closest('.input-icon').size() === 1) {
	                    error.insertAfter(element.closest('.input-icon'));
	                } else {
	                	error.insertAfter(element.parent());
	                }
	            },

	            submitHandler: function (form) {
            		form.submit();
	            }
	        });

			$('.resetPwd-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.resetPwd-form').validate().form()) {
	                    $('.resetPwd-form').submit();
	                }
	                return false;
	            }
	        });
	};
    
    return {
        //main function to initiate the module
        init: function () {
        	jQuery('.resetPwd-form').show();
            handleResetPwd();
	       	$.backstretch([
		        "/static/metronic/assets/img/bg/1.jpg",
		        "/static/metronic/assets/img/bg/2.jpg",
		        "/static/metronic/assets/img/bg/3.jpg",
		        "/static/metronic/assets/img/bg/4.jpg"
		        ], {
		          fade: 1000,
		          duration: 8000
		    });
        }

    };

}();