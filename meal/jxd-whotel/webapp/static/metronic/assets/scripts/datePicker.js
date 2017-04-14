var DatePicker = function () {
    var handleDatePickers = function () {

        if (jQuery().datepicker) {
            $('.date-picker').datepicker({
                rtl: App.isRTL(),
                format: 'yyyy-mm-dd',
                autoclose: true,
                language: 'zh-CN',
            });
            $('body').removeClass("modal-open"); // fix bug when inline picker is used in modal
        }
    }
    
    return {
        //main function to initiate the module
        init: function () {
        	handleDatePickers();
        }
    };

}();