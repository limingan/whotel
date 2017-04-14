var CompanyMain = function () {

    var removeMenu = function() {
    	var arr=new Array("#company","#resource","#publicno","#webcard","#website","#whotel","#tickettype","#marketingEmp","#marketingActivity","#marketing","#wmallGood","#wmallReport","#wmall","#order","#dishes_manage","#wmeal");
    	for(var i=0;i<arr.length;i++){
    		var company = $(arr[i]).find("li");
    		if(company.length==0){
    			$(arr[i]).remove();
    		}
    	}
    };

    return {
        init: function () {
        	removeMenu();
        }
    };
}();