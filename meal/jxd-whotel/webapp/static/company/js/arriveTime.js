var ArriveTime = function () {

    var handleValidation = function() {
    	
            var submitForm = $('#submitForm');
            var error = $('.alert-danger', submitForm);
            var success = $('.alert-success', submitForm);

            $.validator.addMethod("time", function(value, element) {   
  				var reg = /^(([0-1][0-9])|([2][0-3])):([0-6][0-9])$/;
  	    	    return reg.test(value);
  	    	}, "请正确填写到店时间");
               
           $.validator.addMethod("arriveTimeExist", function(value, element) {  
        	   var rs = false;
	           	$.ajax({
	           		url:"/company/ajaxArriveTimeExist.do",
	           		data:{time:value},
	           		async:false,
	           		dataType:"json",
	           		beforeSend:function(XMLHttpRequest) {
	           			var oldDeveloperCode = document.getElementById('time').defaultValue;
	           			if($("#time").val()==oldDeveloperCode){
	           				return false;
	           			}
	           		},
	           		success:function(data) {
	           			rs = data;
	           		}
	           	});
     	        return !rs;
     	    }, "时间已经存在");
            
            
            submitForm.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {
                    'arriveTime': {
                    	required: true,
                    	time:true,
                    	arriveTimeExist:true
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