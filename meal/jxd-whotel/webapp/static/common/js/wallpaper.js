(function($){
	/**
	 * 确认弹窗 vConfirm
	 */
	$.vConfirm = function(options,callback){
		
		var setting = {
			"msg": null,
			"tit": "提示",
			"titLine": false,
			"okBtnText": "确定",
			"closeBtnText": "取消",
			"ok": {
				"callback": null,
				"isClose": false
			},
			"width": "300px"
		};
		
		var settings = null;
		if(typeof options === "string"){
			settings = $.extend(setting,{"msg":options});
			if(typeof callback === "function"){
				settings = $.extend(setting,{"ok":callback});
			}
		}else{
			settings = $.extend(setting,options);
		}
		
		var $temp = $("<div class='vpopup vpopup-confirm'>"+
						"<div class='vpopup-inner' style='width:"+settings.width+"'>"+
							"<div class='vpopup-title'>"+settings.tit+"</div>"+
								"<div class='vpopup-content'></div>"+
								"<div class='vpopup-footer'>"+
									"<span class='vpopup-btn vpopup-close grid-two'>"+settings.closeBtnText+"</span>"+
									"<span class='vpopup-btn vpopup-ok grid-two'>"+settings.okBtnText+"</span>"+
								"</div>"+
							"</div>"+
							"<div class='vpopup-mask'></div>"+
						"</div>");
		
		$temp.find(".vpopup-content").append(settings.msg);
		$("body").append($temp);
		
		var vpopupInner = $temp.find(".vpopup-inner");
		vpopupInner.css({
			"margin-top": -vpopupInner.height()/2
		});
		if(settings.titLine === true){
			vpopupInner.find(".vpopup-title").addClass("vpopup-title-line");
		}
		vpopupInner.find(" .vpopup-ok").click(function(){
			if($("#wallPaPers").find(".tag-pink").size() < 1) {
				alert("请选择背景图片");
				return ;
			}
			if(typeof settings.ok === "function"){
				settings.ok();
			}else if(typeof settings.ok === "object"){
				if(typeof settings.ok.callback === "function"){
					settings.ok.callback();
				}
				if(settings.ok.isClose === true){
					$temp.remove();
				}
			}
		});
		vpopupInner.find(" .vpopup-close").click(function(){
			$temp.remove();
		});
		
	};
	
	/**
	 * 选择背景图
	 */
	$.fn.wallPaper = function (backgroundImgFileId, backgroundImgId, options) {
		
		var defaults = {
				"title":"选择背景图片",
				"width":"720px",
				"img":"#backgroundImgPreview",
				"path":"#bgImgPath",
		};
		
		var settings = $.extend(defaults,options);
		
		function pics(){
			var titleArr = ["炫目极光","家乡忆像","斑斓星云","柠檬心情"];
			var piclists = "";
			var piclist = "";
			for(var i=1; i<=4; i++){
				piclist += "<td>"+titleArr[i-1]+"<div class='m5'><input type='hidden' name='backgroundImg' value='${marketingRule.backgroundImg}'><img name='backgroundImg' src='/static/company/images/"+i+".jpg' width='130' height='100'></div><a href='javascript:;'><span class='tag'><i class='icon-ok icon-white'  width: 14px height: 14px></i>使用</span></a></td>";
				if(i%4 === 0){
					piclists += "<tr>"+piclist+"</tr>";
					piclist = "";
				}
			}
			return piclists;
		}
		
		function selWallPaper(){
			$.vConfirm({
				"tit":settings.title,
				"titLine": true,
				"msg":"<div style='height:500px;overflow-y: auto;'><table width='100%' id='wallPaPers'>"+pics()+"</table></div>",
				"width":settings.width,
				"ok":{
					"callback":function(){
						var imgurl = $("#wallPaPers").find("td.seli img").attr("src");
						$("#" + backgroundImgId).val(imgurl);
						$(settings.img).html("<input type='hidden' name='backgroundImg' value='"+imgurl+"'><input type='hidden' name='bg' value='"+imgurl+"'>" +
								"<img src='"+imgurl+"' height='120' width='80'>");
						$(settings.path).val(imgurl);
						if(typeof(backgroundImgFileId) != "undefined") {
							var file = $("#" + backgroundImgFileId); 
							file[0].value="";
						}
					},
					"isClose":true
				}
			});
			
			var $wallPaPers = $("#wallPaPers");
			$wallPaPers.find("td").click(function(){
				var $this = $(this);
				$wallPaPers.find(".tag").removeClass("tag-pink");
				$wallPaPers.find("td").removeClass("seli");
				$this.addClass("seli").find(".tag").addClass("tag-pink");
			});
			
			//点进去时， 在默认的背景图片下打钩
			if(typeof(backgroundImgId) != "undefined") {
				var backgroundImg = $("#" + backgroundImgId).val();
				if(backgroundImg != undefined && backgroundImg.indexOf("static/company/images/") >= 0) {
					$("#wallPaPers td img").each(function(){
						if($(this).attr("src") == backgroundImg) {
							$(this).parent().parent().addClass("seli").find(".tag").addClass("tag-pink");
						}
					});
				} 
			}
		}
		
		return this.each(function(){
			var $this = $(this);
			$this.click(selWallPaper);
		});
	};
	
		
})(jQuery);

