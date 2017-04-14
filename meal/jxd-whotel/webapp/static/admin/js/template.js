var Template = function () {

    var handleValidation = function() {
    	
            var submitForm = $('#submitForm');
            var error = $('.alert-danger', submitForm);
            var success = $('.alert-success', submitForm);

            submitForm.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {
                    'name': {
                        required: true
                    },
                    'template': {
                        required: true
                    },
                    'bannerSize': {
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
                    $(".has-error").remove();
                    var check = true;
                    $(".js-columnName").each(function(index) {
                    	var _this = $(this);
                    	var labelValue = _this.val();
                    	if($.trim(labelValue) == "") {
                    		$(".js-addColumn").closest("div").append("<div class='has-error'>栏目名称不能为空！</div>");
                    		check = false;
                    		return false;
                    	}
                    });
                    
                    $(".js-iconSize").each(function(index) {
                    	var _this = $(this);
                    	var labelValue = _this.val();
                    	var reg = /^-?(?:\d+|\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/;
                    	if($.trim(labelValue) != "" && !reg.test(labelValue)) {
                    		$(".js-addColumn").closest("div").append("<div class='has-error'>icon宽高必须为数值类型！</div>");
                    		check = false;
                    		return false;
                    	}
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