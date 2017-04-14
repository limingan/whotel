$(function() {
	
	$("body").on("click", ".js-addIcon", function() {
		var _this = $(this);
		var dlObj = _this.closest("dl");
		var tip, title, len, level;
		
		var words = 10;
		
		if(dlObj && dlObj.length > 0) {
			len = _this.closest("dl").find("dd").length;
			if(len >= 5) {
				boxAlert("同一个一级菜单下二级菜单不能超过5个");
				return false;
			}
			title = "添加二级菜单";
			tip = "还能添加"+(5-len)+"个二级菜单，请输入名称（8个汉字或16个字母以内）";
			level = "sub";
			words = 16;
		} else {
			len = $(".sortable").find("li").length;
			if(len >= 3) {
				boxAlert("一级菜单不能超过3个");
				return false;
			}
			title = "添加一级菜单";
			tip = "还能添加"+(3-len)+"个一级菜单，请输入名称（5个汉字或10个字母以内）";
			level = "top";
		}
		
		bootbox.dialog({
            message:'<div class="js-tip">'
             + tip
             +'</div>'
             +'<div class="input">'
	    	 +'<input type="text" name="name" id="menuName">'
			 +'</div>',
             title: title,
             buttons:{
            	 cancel: {
            		 label:"取消"
            	 },
            	 success: {
            		 label:"完成",
            		 callback:function(){
            			 $(".error").remove();
            			 var inputObj = $("#menuName");
            			 var menuName = inputObj.val();
            			 if($.trim(menuName) == "") {
            				 inputObj.closest("div").after("<div class='error'>菜单名称不能为空！</div>");
            				 return false;
            			 }
            			 
            			 if(chkstrlen($.trim(menuName))>words) {
            				 inputObj.closest("div").after("<div class='error'>菜单名称必须"+(words/2)+"个汉字或"+words+"个字母以内！</div>");
            				 return false;
            			 }
            			 
            			 var parentId;
            			 
            			 if(level == "sub") {
            				 parentId = _this.closest("dt").attr("data-id");
            			 }
            			 
            			 $.ajax({
            				url:"/company/publicno/ajaxUpdateMenuItem.do",
            				type:"post",
            				data:{name:menuName, parentId:parentId},
            				dataType:"json",
            				success:function(data) {
            					if(level == "top") {
	            					var html = '<li class="ui-state-default">'
	            	                    	   +'<dl>'
	            	                    	   +'<dt data-id="'+data.id+'" class="js-menuItem"><a href="javascript:;">'+data.name+'<i class="glyphicon glyphicon-plus js-addIcon"></i></a></dt>'
	            	                    	   +'</dl>'
	            	                 		   +'</li>';
	            					$(".sortable").append(html);	
            					} else if(level == "sub") {
            						var html = '<dd class="ui-state-default2 js-menuItem" data-id="'+data.id+'">'
		            						+'<a href="javascript:;">'
		            						+'<em class="glyphicon glyphicon-stop"></em>'
		            						+data.name
		            						+'</a>'
		            						+'<i class="glyphicon glyphicon-align-justify"></i>'
		            						+'</dd>';
            						dlObj.append(html);
            					}
            				}
            			 });
            		 }
            	 }
		   }
		});
	});
	
	$("body").on("click", ".js-menuItem", function() {
		var _this = $(this);
		
	    $(".js-menuItem").removeClass("mn-click");
	    _this.addClass("mn-click");
		var id = _this.attr("data-id");
		$("#js-curMenuItem").val(id);
		
		$.ajax({
			url:"/company/publicno/ajaxGetMenuItem.do",
			data:{id:id},
			dataType:"json",
			success:function(data) {
				showMenuResponse(data);
			}
		});
	});
	
	$(".js-subIcon").click(function(){
		 $(".js-finishBtn,.js-cancleBtn").show();
		 $(".js-subIcon,.js-addIcon").hide();
		 $(".js-menuSlide").find("dl").children().find("i").show().attr("class","glyphicon glyphicon-align-justify");
		
		 $( ".sortable" ).sortable({
			  cursor: "move",
			  items :"li",                        //只是li可以拖动
			  opacity: 0.6,                       //拖动时，透明度为0.6
			  revert: false                       //释放时，增加动画	  
		 });


		$( ".sortable2" ).sortable({
			  cursor: "move",
			  items :"dd",                        //只是dd可以拖动
			  opacity: 0.6,                       //拖动时，透明度为0.6
			  revert: false                       //释放时，增加动画	  
		});	
		
		 $(".sortable,.sortable2").sortable('enable');
		 
	});
	 
	$(".js-finishBtn").click(function(){
		$(".sortable,.sortable2").sortable('disable');
		$(".js-finishBtn,.js-cancleBtn").hide();
		$(".js-subIcon,.js-addIcon").show();
		$(".js-menuSlide").find("dl").children("dt").find("i").attr("class","glyphicon glyphicon-plus js-addIcon");
		$(".js-menuSlide").find("dl").children("dd").find("i").hide();
		
		var liObj = $(".sortable").find("li");
		var idOrders = [];
		liObj.each(function(index) {
			var _this = $(this);
			var dataId = _this.find("dt").attr("data-id");
			idOrders.push(dataId+","+index);
			_this.find("dd").each(function(dIndex) {
				var ddThis = $(this);
				var ddId = ddThis.attr("data-id");
				idOrders.push(ddId+","+dIndex);
			});
		});
		
		$.ajax({
			url:"/company/publicno/ajaxUpdateMenuOrders.do",
			type:"post",
			data:{idOrders:idOrders},
			success:function(data) {
				
			}
		});
	});
	
	$(".js-cancleBtn").click(function(){
		$(".sortable,.sortable2").sortable('cancel');
		$(".sortable,.sortable2").sortable('disable');
		$(".js-finishBtn,.js-cancleBtn").hide();
		$(".js-subIcon,.js-addIcon").show();
		$(".js-menuSlide").find("dl").children("dt").find("i").attr("class","glyphicon glyphicon-plus");
		$(".js-menuSlide").find("dl").children("dd").find("i").hide();
	});
	
	$(".js-modifyMenuName").click(function() {
		var level = $(".js-menuLevel").html();
		var name = $(".js-menuTitle").html();
		var id = $("#js-curMenuItem").val();
		var tip, curMenu;
		if(level == "一级菜单") {
			tip = "请输入名称（5个汉字或10个字母以内）";
			curMenu = $("dt[data-id='"+id+"']");
		} else {
			tip = "请输入名称（8个汉字或16个字母以内）";
			curMenu = $("dd[data-id='"+id+"']");
		}
		bootbox.dialog({
            message:'<div class="js-tip">'
             + tip
             +'</div>'
             +'<div class="input">'
	    	 +'<input type="text" name="name" value="'+name+'" id="menuName">'
			 +'</div>',
             title: "修改"+level,
             buttons:{
            	 cancel: {
            		 label:"取消"
            	 },
            	 success: {
            		 label:"完成",
            		 callback:function(){
            			 $(".error").remove();
            			 var inputObj = $("#menuName");
            			 var menuName = inputObj.val();
            			 if($.trim(menuName) == "") {
            				 inputObj.closest("div").after("<div class='error'>菜单名称不能为空！</div>");
            				 return false;
            			 }
            			 if(level == "一级菜单"){
            				 if(chkstrlen($.trim(menuName))>10){
            					 inputObj.closest("div").after("<div class='error'>菜单名称5个汉字或10个字母以内！</div>");
                				 return false;
            				 }
            			 }else{
            				 if(chkstrlen($.trim(menuName))>16){
            					 inputObj.closest("div").after("<div class='error'>菜单名称8个汉字或16个字母以内！</div>");
                				 return false;
            				 }
            			 }
            			 
            			 $.ajax({
            				url:"/company/publicno/ajaxUpdateMenuItem.do",
            				type:"post",
            				data:{id:id, name:menuName},
            				dataType:"json",
            				success:function(data) {
            					curMenu.find(".js-name").text(menuName);
            					$(".js-menuTitle").html(menuName);
            				}
            			 });
            		 }
            	 }
		   }
		});
	});
	
	$(".js-deleteMenu").click(function() {
		boxConfirm("真的要删除吗？", function(val) {
			if(val == true) {
				var id = $("#js-curMenuItem").val();
				$.ajax({
					url:"/company/publicno/ajaxDeleteMenuItem.do",
					data:{ids:id},
					dataType:"json",
					success:function(data) {
						if(data == true) {
							boxAlert("删除成功！");
							location.reload();
						}
					}
				});
			}
		});
	});
	
	$(".js-saveMenuBtn a").click(function() {
		handleInitData();
		var id = $("#js-curMenuItem").val();
		var url = $("#js-menuUrl").val();
		
		if($.trim(url) != "" && !checkUrl(url)) {
			$("#js-menuUrl").after("<span class='error'>请填写正确的链接</span>");
			return false;
		}
		
		var text = $("#textMsg").val();
		var newsId = $("#newsMsg").val();
		var pic = $("#picMsg").val();
		var unlock = $("#unlock").val();
		var scancode = $("#scancode").val();
		$.ajax({
			type:"post",
			url:"/company/publicno/ajaxUpdateMenuItem.do",
			data:{id:id, 'responseMsg.text':text, 'responseMsg.pic':pic, 'responseMsg.newsId':newsId, 'responseMsg.url':url,'responseMsg.unlock':unlock,'responseMsg.scancode':scancode},
			success:function(data) {
				boxAlert("设置成功！");
				
				showMenuResponse(data);
			}
		});
	});
	
	$(".js-modifyMenuBtn a").click(function() {
		clearMenuEvent();
		showMenuEvent();
	});
	
	$(".js-publishMenu").click(function() {
		$.ajax({
			url:"/company/publicno/ajaxPublishMenu.do",
			dataType:"json",
			success:function(data) {
				if(data == true) {
					boxAlert("菜单发布成功！");
				} else {
					boxAlert("菜单发布失败！");
				}
			}
		});
	});
});

function showMenuResponse(data) {
	var parentId = data.parentId;
	
	$(".js-menuTitle").html(data.name);
	
	clearMenuEvent();
	$(".js-menuTip").hide();
	$(".js-menuEvent").show();
	
	if(parentId) {
		$(".js-menuLevel").html("二级菜单");
	} else {
		$(".js-menuLevel").html("一级菜单");
		var curMenu = $("dt[data-id='"+data.id+"']");
		var sibling = curMenu.siblings();
		if(sibling.length > 0) {
			var menuTip = $(".js-menuTop");
			menuTip.find(".mn-tips").html("您还可添加"+(5-sibling.length)+"个二级菜单");
			showMenuEvent("TOP");
			return false;
		}
	}
	
	var responseMsg = data.responseMsg;
	
	if(responseMsg) {
		var type = responseMsg.responseType;
		if(type == "LINK") {
			$("#js-menuUrl").val(responseMsg.url);
		} else if(type == "TEXT") {
			addTextToContain(responseMsg.text);
		} else if(type == "PIC") {
			addPicToContain(responseMsg.pic);
		} else if(type == "NEWS") {
			var news = responseMsg.newsResource;
			addTwToContain(news.multiple, news.id, $(genTwMsg(news)).html());
		} else if(type == "UNLOCK"){
			addUnlockToContain();
		} else if(type == "SCANCODE"){
			addScancodeToContain();
		}
		showMenuEvent(type);
		$(".js-saveMenuBtn").hide();
		$(".js-modifyMenuBtn").show();
	} else {
		showMenuEvent();
	}
}

function clearMenuEvent() {
	$(".error").remove();
	clearMsgContain();
	$("#js-menuUrl").val("");	
}

function showMenuEvent(type) {
	var menuType = $(".js-menuType");
	var menuMsg = $(".js-menuMsg");
	var menuUrl = $(".js-menuUrl");
	var menuTop = $(".js-menuTop");
	
	$(".js-saveMenuBtn").hide();
	$(".js-modifyMenuBtn").hide();
	menuType.hide();
	menuMsg.hide();
	menuUrl.hide();
	menuTop.hide();
	if(type) {
		if(type == "LINK") {
			menuUrl.show();
			$("#js-curMenuEvent").val("URL");
		} else if(type == "NEWS") {
			menuMsg.show();
			$("#js-curMenuEvent").val("NEWS");
		} else if(type == "PIC") {
			menuMsg.show();
			$("#js-curMenuEvent").val("NEWS");
		} else if(type == "TEXT") {
			menuMsg.show();
			$("#js-curMenuEvent").val("NEWS");
		} else if(type == "TOP") {
			menuTop.show();
		} else if(type == "UNLOCK"){
			menuMsg.show();
			$("#js-curMenuEvent").val("NEWS");
		} else if(type == "SCANCODE"){
			menuMsg.show();
			$("#js-curMenuEvent").val("NEWS");
		}
		if(type != "TOP") {
			$(".js-saveMenuBtn").show();
		}
	} else {
		menuType.show();
	}
}

function handleInitData() {
	var event = $("#js-curMenuEvent").val();
	if(event == "NEWS") {
		$("#js-menuUrl").val("");
		handleData();
	} else {
		$("#textMsg").val("");
		$("#picMsg").val("");
		$("#newsMsg").val("");
		$("#unlock").val("");
		$("#scancode").val("");
	}
}
function chkstrlen(str){
	var strlen = 0;
	for(var i = 0;i < str.length; i++){
		if(str.charCodeAt(i) > 255){// 如果是汉字，则字符串长度加2
			strlen += 2;
		}else{
			strlen++;
		}
	}
	return strlen;
}