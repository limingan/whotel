
var obj = { date:new Date(), year: -1, month: -1, priceArr: [] };
var elemId = null;
var callback;
var _this;
var _appointDate;
var  startdate='';
var enddate='';
var flag=true;
var  cdate=new Date();
//获取控件宽度
function getElementWidth(objectId) {
    x = document.getElementById(objectId);
    return x.clientHeight;
}
var pickerEvent = {
    Init: function (elemid) {
        if (obj.year == -1) {
            dateUtil.getAppointDate();
        }
        
        var p = document.getElementById("calendar_choose");
        if (p != null) {
        	$("body .datePrice").remove();
        }
        
        var html = '<div class="datePrice" style="display:inline-block;overflow:hidden;">';
        html += '		<div>';
        html += '		<div class="am-modal-dialog">';
        html += '		<div class="am-modal-bd" id="js_dailyprice">';
        html += '		<div id="calendar_choose" class="calendar" style="font-size:16px;font-family:方正大黑简体;background: #f0f0f0;border:none;display:block;position:fixed;height:100%;overflow:auto;">'
        html+='<div style="border-bottom: 3px solid rgb(211, 143, 92);font-size:18px;padding:2px;color: rgb(211, 143, 92);"><a href="javascript:commonUtil.close();" title="返回" style="display: inline-block;position: absolute;width: 25px;left:10px;color: rgb(211, 143, 92);font-size: 16px;font-family: 黑体;"><</a>请选入店日期</div>';
        for(var i=0;i<4;i++){
        if(i==1){dateUtil.getNexDate()}else if(i==2){dateUtil.getNexDate()}else if(i==3){dateUtil.getNexDate()}
        html += 			pickerHtml.getLeft();
        html += 			pickerHtml.getHead();
        html += 			'<div class="basefix" style="display: block;">';
        html += 			pickerHtml.getRight();
        }
        html += 			'<div style="clear: both;"></div>';
        html += 			"</div></div>";
        html += '</div></div></div></div>';
        elemId=elemid;
        $(document.body).append(html);
        pickerEvent.bindingEvents();
        $("#js_dailypriceAlert").modal();
    },
    getLast: function () {
        dateUtil.getLastDate();
        for (var item in pickerHtml) {
            pickerHtml[item]();
        }
        $(".pkg_double_month").find(".date_text").html(obj.year + '年' + obj.month + '月');
        $("#bigCalendar").empty();
        $("#bigCalendar").append(htmlObj.right);
        pickerEvent.bindingEvents();
    },
    getNext: function () {
        dateUtil.getNexDate();
        $(".pkg_double_month").find(".date_text").html(obj.year + '年' + obj.month + '月');
        $("#bigCalendar").empty();
        $("#bigCalendar").append(htmlObj.right);
        pickerEvent.bindingEvents();
    },
	getToday:function(){
		dateUtil.getCurrent();
		pickerEvent.Init(elemId);
	},
    setPriceArr: function (arr) {
        obj.priceArr = arr;
    },
    remove: function () {
        $("#js_dailypriceAlert").modal('close');
    },
    isShow: function () {
        var p = document.getElementById("calendar_choose");
        if (p != null) {
            return true;
        }
        else {
            return false;
        }
    },
    bindingEvents:function(){
	 document.getElementById("picker_today").onclick = pickerEvent.getToday;
     document.getElementById("calendar_choose").style.left = "0px";
     document.getElementById("calendar_choose").style.top  = "0px";
     document.getElementById("calendar_choose").style.zIndex = 1000;
     commonUtil.getRefresh(pickerEvent.r);
    },
    callback:function(dp,_callback,appointDate){
	 _this = dp;
	 callback = _callback;
	 _appointDate=appointDate;
    },r:function(td,i){
	 td.onclick = function () {
       commonUtil.chooseClick(this)
     };
    }
}

var pickerHtml = {
    getHead: function () {
        var head = '<ul class="calendar_num basefix" style="background: #f0f0f0;"><li class="bold" style="color:rgb(211, 143, 92);">六</li><li class="bold" style="color:#000;">五</li><li class="bold" style="color:#000;">四</li><li class="bold" style="color:#000;">三</li><li class="bold" style="color:#000;">二</li><li class="bold" style="color:#000;">一</li><li class="bold" style="color:rgb(211, 143, 92);">日</li><li class="picker_today bold" style="display: none;" id="picker_today">回到今天</li></ul>';
        return head;
    },
    getLeft: function () {
        var left = '<div class="calendar_left pkg_double_month"  ><p class="date_text" style="color: #000;">' + obj.year + '年' + obj.month + '月</p></div>';
        return left;
    },
    getRight: function () {
        var days = dateUtil.getLastDay();
        var week = dateUtil.getWeek();
        console.log(week);
        var html = '<table class="calendar_right" style="background: #fff;"><tbody>';
        var index = 0;
        for (var i = 1; i <42; i++) {
            if (index == 0) {
                html += "<tr>";
            }
            var c = week > 0 ? week : 0;
            if ((i - 1) >= week && (i - c) <= days) {
                var price = commonUtil.getPrice((i - c));
                var priceStr = "";
                var classStyle = "";
                
                
                //alert(obj.year + "-" + obj.month + "-" + (i - c));
                
				if (dateUtil.datedaxiao(obj.year + "-" + obj.month + "-" + (i - c),cdate.getFullYear()+"-"+(cdate.getMonth() + 1)+"-"+cdate.getDate())) {//price != -1&&
                    classStyle = "class='m-day'";
                }
				//判断今天
				if(dateUtil.datedaxiao(obj.year + "-" + obj.month + "-" + (i - c),cdate.getFullYear()+"-"+(cdate.getMonth() + 1)+"-"+cdate.getDate())){
				  var day=new Date(obj.year + "-" + obj.month + "-" + (i - c)).getDay();
				  if(day==0||day==6){
				    html += '<td  ' + classStyle + ' date="' + obj.year + "-" + obj.month + "-" + (i - c) + '" price="' + price + '"><a style="width: 40px;text-align: center;display: table-cell;vertical-align: middle;"><span class="date basefix" style="color:rgb(211, 143, 92)">' + (i - c) + '</span></a></td>';
				  }else{
					html += '<td  ' + classStyle + ' date="' + obj.year + "-" + obj.month + "-" + (i - c) + '" price="' + price + '"><a style="width: 40px;text-align: center;display: table-cell;vertical-align: middle;"><span class="date basefix" style="color:#000">' + (i - c) + '</span></a></td>';
				  }
				}else{
                	html += '<td  ' + classStyle + ' date="' + obj.year + "-" + obj.month + "-" + (i - c) + '" price="' + price + '"><a style="width: 40px;text-align: center;display: table-cell;vertical-align: middle;"><span class="date basefix">' + (i - c) + '</span></a></td>';
				}
                if (index == 6) {

                    html += '</tr>';
                    index = -1;
                }
            }else {
                html += "<td></td>";
                if (index == 6) {
                    html += "</tr>";
                    index = -1;
                }
            }
            index++;
        }
        html += "</tbody></table>";
        return html;
    }
}
var dateUtil = {
    //根据日期得到星期
    getWeek: function () {
        var d = new Date(obj.year, obj.month - 1, 1);
        return d.getDay();
    },
    //得到一个月的天数
    getLastDay: function () {
        var new_year = obj.year;//取当前的年份        
        var new_month = obj.month;//取下一个月的第一天，方便计算（最后一不固定）        
        var new_date = new Date(new_year, new_month, 1);                //取当年当月中的第一天        
        return (new Date(new_date.getTime() - 1000 * 60 * 60 * 24)).getDate();//获取当月最后一天日期        
    },
    getCurrent: function () {
        var dt = obj.date;
        obj.year = dt.getFullYear();
        obj.month = dt.getMonth() + 1;
		obj.day = dt.getDate();
    },
    getLastDate: function () {
        if (obj.year == -1) {
            var dt = new Date(obj.date);
            obj.year = dt.getFullYear();
            obj.month = dt.getMonth() + 1;
        }
        else {
            var newMonth = obj.month - 1;
            if (newMonth <= 0) {
                obj.year -= 1;
                obj.month = 12;
            }
            else {
                obj.month -= 1;
            }
        }
    },
    getNexDate: function () {
        if (obj.year == -1) {
            var dt = new Date(obj.date);
            obj.year = dt.getFullYear();
            obj.month = dt.getMonth() + 1;
        }
        else {
            var newMonth = obj.month + 1;
            if (newMonth > 12) {
                obj.year += 1;
                obj.month = 1;
            }
            else {
                obj.month += 1;
            }
        }
    },
    getAppointDate:function(){
    	var dt = obj.date;
    	if(_appointDate && typeof _appointDate != "undefined"){
    		dt = _appointDate;
    	}
        obj.year = dt.getFullYear();
        obj.month = dt.getMonth() + 1;
		obj.day = dt.getDate();
    },datedaxiao:function (a, b) {
        var arr = a.split("-");
        var starttime = new Date(arr[0], arr[1], arr[2]);
        var starttimes = starttime.getTime();
        var arrs = b.split("-");
        var lktime = new Date(arrs[0], arrs[1], arrs[2]);
        var lktimes = lktime.getTime();
        if (starttimes >= lktimes) {
            return true;
        }
        else
            return false;

    }
}
var commonUtil = {
    getPrice: function (day) {
        var dt = obj.year + "-";
        if (obj.month < 10)
        {
            dt += "0"+obj.month;
        }
        else
        {
            dt+=obj.month;
        }
        if (day < 10) {
            dt += "-0" + day;
        }
        else {
            dt += "-" + day;
        }
        
        for (var i = 0; i < obj.priceArr.length; i++) {
            if (obj.priceArr[i].priceDate == dt) {
                return obj.priceArr[i].price;
            }
        }
        return -1;
    },close:function(){
    	$('#calendar_choose').remove();
    	obj = { date: new Date(), year: -1, month: -1, priceArr: [] }	
    },
    getRDian:function(date){
     return "<div><span class='date-txt' style='font-size:13px;border:1px solid rgb(211, 143, 92);overflow:hidden;color:#FFFFFF;height: 23px;border-radius: 50%; width: 23px;display:inline-block;background: rgb(211, 143, 92);text-align: center;vertical-align: middle;'>"+date+"</span><span style='font-size:10px;display:block;color: rgb(211, 143, 92);'>入店</span></div>";
    },
    getLDian:function(date){
        return "<div><span class='date-txt' style='font-size:13px;overflow:hidden;color:#FFFFFF;height: 23px;border-radius: 50%; width: 23px;display:inline-block;background:green;text-align: center;vertical-align: middle;'>"+date+"</span><span style='font-size:10px;display:block;color:green;'>离店</span></div>";
       },
       getRefresh:function(call){
    	   var tds = document.getElementById("calendar_choose").getElementsByTagName("td");
           for (var i = 0; i < tds.length; i++) {
               if (tds[i].getAttribute("date") != null && tds[i].getAttribute("date") != ""&&$(tds[i]).attr("class")=='m-day') {
                   call(tds[i],i);
               }
           }
       },r:function(td,i){
    	  var div=$(td).find("div");
    	  var value=$(div).find("span[class='date-txt']").html();
    	  var type=$(div).find("span").eq(1).html();
    	  if(undefined!=value&&flag&&type=='入店'){
    		  $(td).html("<a style='font-size:10px;width: 40px;text-align: center;display: table-cell;vertical-align: middle;'><span class=\"date basefix\" style=\"color:#000\">"+value+"</span></a>")
    	  }else if(undefined!=value&&!flag&&type=='离店'){
    		  $(td).html("<a style='font-size:10px;width: 40px;text-align: center;display: table-cell;vertical-align: middle;'><span class=\"date basefix\" style=\"color:#000\">"+value+"</span></a>")
    	  }
    	  
       },chooseClick: function (sender) {
    	commonUtil.getRefresh(commonUtil.r);
        var date = sender.getAttribute("date");
        if(startdate==''&&flag){
        $(sender).html(commonUtil.getRDian(date.split("-")[2]));
         startdate=date;
         flag=false;
        }else if(enddate!=''&&flag&&!dateUtil.datedaxiao(date,cdate.getFullYear()+"-"+(cdate.getMonth() + 1)+"-"+cdate.getDate())){
         $(sender).html(commonUtil.getRDian(date.split("-")[2]));
         startdate=date;
         flag=false;
        }else if(startdate!=''&&!flag&&date==startdate){
          $(sender).html("<a style='width: 40px;text-align: center;display: table-cell;vertical-align: middle;'><span class=\"date basefix\" style=\"color:#000\">"+date.split("-")[2]+"</span></a");
          startdate='';
          flag=true;
        }else if(startdate!=''&&!flag&&dateUtil.datedaxiao(date,cdate.getFullYear()+"-"+(cdate.getMonth() + 1)+"-"+cdate.getDate())){
         $(sender).html(commonUtil.getLDian(date.split("-")[2]));
         enddate=date;
         flag=true;
        }
        if(startdate!=undefined&&enddate!=undefined&&startdate!=''&&enddate!=''){
         callback(this,startdate,enddate);
         startdate='';
         enddate='';
         //commonUtil.close();
        }
    }
}

