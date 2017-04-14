
var obj = { date: new Date(), year: -1, month: -1, priceArr: [] };
var htmlObj = { header: "", left: "", right: "" };
var elemId = null;
var callback;
var _this;
var _appointDate;
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
        for (var item in pickerHtml) {
            pickerHtml[item]();
        }
        var p = document.getElementById("calendar_choose");
        if (p != null) {
        	$("body .datePrice").remove();
        }
        
        var html = '<div class="datePrice">';
        html += '		<div class="am-modal am-modal-alert" tabindex="-1" id="js_dailypriceAlert">';
        html += '		<div class="am-modal-dialog">';
        html += '		<div class="am-modal-bd" id="js_dailyprice">';
        html += '			<div id="calendar_choose" class="calendar" style="display: block; position: absolute;">'
        html += 			htmlObj.left;
        html += 			htmlObj.header;
        html += 			'<div class="basefix" id="bigCalendar" style="display: block;">';
        html += 			htmlObj.right;
        html += 			'<div style="clear: both;"></div>';
        html += 			"</div></div>";
        html += '</div></div></div></div>';
        elemId=elemid;
        var elemObj = document.getElementById(elemid);
        $(document.body).append(html);
        pickerEvent.bindingEvents();
        $("#js_dailypriceAlert").modal();
        //return html;
        //return elemObj;
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
        for (var item in pickerHtml) {
            pickerHtml[item]();
        }
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
        document.getElementById("picker_last").onclick = pickerEvent.getLast;
        document.getElementById("picker_next").onclick = pickerEvent.getNext;
		document.getElementById("picker_today").onclick = pickerEvent.getToday;
        document.getElementById("calendar_choose").style.left = "0px";
        document.getElementById("calendar_choose").style.top  = "0px";
        document.getElementById("calendar_choose").style.zIndex = 1000;
        var tds = document.getElementById("calendar_tab").getElementsByTagName("td");
        for (var i = 0; i < tds.length; i++) {
            if (tds[i].getAttribute("date") != null && tds[i].getAttribute("date") != "" && tds[i].getAttribute("price") != "-1") {
                tds[i].onclick = function () {
                    commonUtil.chooseClick(this)
                };
            }
        }
    },
    callback:function(dp,_callback,appointDate){
    	_this = dp;
    	callback = _callback;
    	_appointDate=appointDate;
    }
}

var pickerHtml = {
    getHead: function () {
        var head = '<ul class="calendar_num basefix"><li class="bold">六</li><li>五</li><li>四</li><li>三</li><li>二</li><li>一</li><li class="bold">日</li><li class="picker_today bold" style="display: none;" id="picker_today">回到今天</li></ul>';
        htmlObj.header = head;
    },
    getLeft: function () {
        var left = '<div class="calendar_left pkg_double_month"><p class="date_text">' + obj.year + '年' + obj.month + '月</p><a href="javascript:void(0)" title="上一月" id="picker_last" class="pkg_circle_top">上一月</a><a href="javascript:void(0)" title="下一月" id="picker_next" class="pkg_circle_bottom">下一月</a></div>';
        htmlObj.left = left;
    },
    getRight: function () {
        var days = dateUtil.getLastDay();
        var week = dateUtil.getWeek();
        var html = '<table id="calendar_tab" class="calendar_right"><tbody>';
        var index = 0;
        for (var i = 1; i <= 42; i++) {
            if (index == 0) {
                html += "<tr>";
            }
            var c = week > 0 ? week : 0;
            if ((i - 1) >= week && (i - c) <= days) {
                var price = commonUtil.getPrice((i - c));
                var priceStr = "";
                var classStyle = "";
                if (price != -1) {
                    priceStr = "<dfn>¥</dfn>" + price;
                    classStyle = "class='on'";
                }
				if (price != -1&&obj.year==new Date().getFullYear()&&obj.month==new Date().getMonth()+1&&i-c==new Date().getDate()) {
                    classStyle = "class='on today'";
                }
				//判断今天
				if(obj.year==new Date().getFullYear()&&obj.month==new Date().getMonth()+1&&i-c==new Date().getDate()){
					html += '<td  ' + classStyle + ' date="' + obj.year + "-" + obj.month + "-" + (i - c) + '" price="' + price + '"><a><span class="date basefix">今天</span><span class="team basefix" style="display: none;">&nbsp;</span><span class="calendar_price01">' + priceStr + '</span></a></td>';
				}
				else{
                	html += '<td  ' + classStyle + ' date="' + obj.year + "-" + obj.month + "-" + (i - c) + '" price="' + price + '"><a><span class="date basefix">' + (i - c) + '</span><span class="team basefix" style="display: none;">&nbsp;</span><span class="calendar_price01">' + priceStr + '</span></a></td>';
				}
                if (index == 6) {

                    html += '</tr>';
                    index = -1;
                }
            }
            else {
                html += "<td></td>";
                if (index == 6) {
                    html += "</tr>";
                    index = -1;
                }
            }
            index++;
        }
        html += "</tbody></table>";
        htmlObj.right = html;
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
    },
    chooseClick: function (sender) {
        var date = sender.getAttribute("date");
        var price = sender.getAttribute("price");
        callback(date,_this);
		//alert("日期是："+date);
		//alert("价格是：￥"+price);
        pickerEvent.remove();
    }
}

