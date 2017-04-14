$(function() {
	$("body").on("click", "#jsTwBtn",function() {
			bootbox.dialog({
	            message: '<div class="portlet-body">'
		    	 +'<input type="hidden" name="page" id="bootPage">'
		    	 +'<ul class="news_resource" id="dataContain">'
				 +'</ul>'
				 +'</div>'
				 +'<div class="row" id="pageNavigation">'
				 +'</div>',
	             title: "选择图文",
	             className:"whotel-modal",
	             buttons:{
			    	 cancel: {
			    		 label:"取消"
			    	 },
	            	 success: {
	            		 label:"完成",
	            		 callback:function(){
	            			 var iObj = $(".news_thumb").find("i");
	            			 if(iObj.length <= 0) {
	            				 boxAlert("您没有选择任何素材!");
	            			 } else {
	            				 var newsId = iObj.closest(".news_thumb").attr("data-id");
	            				
	            				 var liObj = iObj.closest(".li");
	            				 var olObj = liObj.find("ol");
	            				 
	            				 var multiple;
	            				 if(olObj.length > 0) {
	            					 multiple = true;
	            				 } else {
	            					 multiple = false;
	            				 }
	            				 addTwToContain(multiple, newsId, liObj.html());
	            			 }
	            		 }
			   }
			}
			});
			loadNewsData(1);
		});
	
	$("body").on("click", "#jsPicBtn",function() {
		bootbox.dialog({
            message: '<div class="portlet-body">'
	    	 +'<input type="hidden" name="page" id="bootPage">'
	    	 +'<ul class="news_resource" id="dataContain">'
			 +'</ul>'
			 +'</div>'
			 +'<div class="row" id="pageNavigation">'
			 +'</div>',
             title: "选择图片",
             className:"whotel-modal",
             buttons:{
		    	 cancel: {
		    		 label:"取消"
		    	 },
            	 success: {
            		 label:"完成",
            		 callback:function(){
            			 var iObj = $(".news_thumb").find("i");
            			 if(iObj.length <= 0) {
            				 boxAlert("您没有选择任何素材!");
            			 } else {
            				 var pic = iObj.closest(".news_thumb").attr("data-pic");
            				 addPicToContain(pic);
            			 }
            		 }
		   }
		}
		});
			loadPicData(1);
		});
		
		$("body").on("click", ".whotel-modal .bootbox-close-button", function() {
			//window.location.reload();
		});
		
		$("body").on("click", "#jsTwDelete", function() {
	    	var _this = $(this);
	    	var liObj = _this.closest("li");
	    	$("#newsMsg").val("");
	    	liObj.html('<a href="javascript:;" id="jsTwBtn" class="twaddBtn">从图文库中选择</a>');
	    });
		
		$("body").on("click", "#jsPicDelete", function() {
	    	var _this = $(this);
	    	var liObj = _this.closest("li");
	    	$("#picMsg").val("");
	    	liObj.html('<a href="javascript:;" id="jsPicBtn" class="twaddBtn">从图片库中选择</a>');
	    });
		
		$("body").on("click", ".news_thumb", function() {
	    	var _this = $(this);
	    	_this.unbind("mouseleave");
	    	$(".news_thumb").find("i").remove();
			_this.append("<i></i>");
	    });
		
		$("body").on("mouseover", ".news_thumb", function() {
	    	var _this = $(this);
	    	if(_this.find("i").length<=0) {
	    		_this.append("<i></i>");
			}
	    });
		
		$(window).resize(function(){
			waterfullData();
	    });
		
		var mnInform=$(".mn-inform");
	    mnInform.children("ol").children("li").each(function(index){
	      var _this=$(this);
	        _this.click(function(){
	            _this.addClass("cur").siblings().removeClass("cur");
	           mnInform.children("ul").children("li").eq(index).show().siblings().hide();
	        });
	    });
	});

    function waterfullData() {
    	var resourceObj = $(".news_resource");
		resourceObj.waterfall({});
	
		var arrHeight=[],listHeight;
	    resourceObj.find(".li").each(function(index){
	    	var _this=$(this);
	    	arrHeight[index]=parseFloat(_this.css("top"))+_this.height()+
	    	parseFloat(_this.css("padding-top"))+parseFloat(_this.css("padding-bottom"));
	    });

	    
	    var arr1=arrHeight.sort(function(a, b){ return a - b;});
	    
	    listHeight=arr1[arr1.length-1]+10;
	    resourceObj.height(listHeight);
    }
	
	function loadNewsData(page) {
		$.ajax({
			url:"/company/resource/ajaxLoadNewsResource.do",
			data:{pageNo:page, pageSize:8},
			dataType:"json",
			cache:false,
			success:function(data) {
				var rs = data.result;
				var html = "";
				if(rs) {
					for(var i=0; i<rs.length; i++) {
						var newsResource = rs[i];
						html += genTwMsg(newsResource);
					}
				}
				$("#dataContain").html(html);
				$("#bootPage").val(data.pageNo);
				$("#pageNavigation").html(genPageNavigation(data.pageNo, data.totalPages, data.totalCount, data.start, data.end, "loadNewsData"));
				setTimeout(waterfullData, 200);
				
				$(".news_thumb").bind("mouseleave",function(){
						var _this = $(this);
						_this.find("i").remove();
				});
			}
		});
	}
	
	function loadPicData(page) {
		$.ajax({
			url:"/company/resource/ajaxLoadPicResource.do",
			data:{pageNo:page, pageSize:8},
			dataType:"json",
			cache:false,
			success:function(data) {
				var rs = data.result;
				var html = "";
				if(rs) {
					for(var i=0; i<rs.length; i++) {
						var newsResource = rs[i];
						html += genPicMsg(newsResource);
					}
				}
				$("#dataContain").html(html);
				$("#bootPage").val(data.pageNo);
				$("#pageNavigation").html(genPageNavigation(data.pageNo, data.totalPages, data.totalCount, data.start, data.end, "loadPicData"));
				setTimeout(waterfullData, 200);
				
				$(".news_thumb").bind("mouseleave",function(){
						var _this = $(this);
						_this.find("i").remove();
				});
			}
		});
	}
	
	function genTwMsg(newsResource) {
		
		var createTime = "";
		if(newsResource.createTime) {
			createTime = new Date(newsResource.createTime.replace(/-/g,"/")).Format("MM月dd日");
		}
		
		var items = newsResource.news;
		var newsContent = "";
		if(newsResource.multiple == true) {
			newsContent = '<ol>';
			
			for(var j=0; j<items.length; j++) {
				var item = items[j];
				if(j == 0) {
					newsContent += '<li class="xFirst">'
						        + '<b>'+createTime+'</b>'
						        + '<dl>'
						        + '<a href="javascript:;">'
						        + '<dt class="news_thumb" data-id="'+newsResource.id+'">'
						        + '<img src="'+item.coverUrl+'" />'
						        + '</dt>'
						        + '<dd>'+item.title+'</dd>'
						        + '</a>'
						        + '</dl>'
						        + '</li>';
				} else {
					newsContent += '<li class="nList">'
								+ '<a href="javascript:;">'
								+ '<img src="'+item.coverUrl+'" />'
								+ '<h4>'+item.title+'</h4>'
								+ '</a>'
								+ '</li>';
				}
			}
			newsContent += '</ol>';
		} else {
			var item = items[0];
			
			newsContent = '<h4><a href="javascript:;">'+item.title+'</a></h4>'
	          + '<i>'+createTime+'</i>'
	          + '<div class="news_thumb" data-id="'+newsResource.id+'">'
	          + '<img src="'+item.coverUrl+'">'
	          + '</div>'
	          + '<p>'+item.brief+'</p>';
		}
		return '<li class="li">' +	newsContent + '</li>';
	}
	
	function genPicMsg(picResource) {
		var picContent =  '<div class="news_thumb" data-pic="'+picResource.picUrl+'">'
					        + '<img src="'+picResource.picUrl+'">'
					        + '</div>';
		return '<li class="li">' +	picContent + '</li>';
	}
	
	function clearMsgContain() {
		$("#newsMsg").val("");
		$("#picMsg").val("");
		$("#textMsg").val("");
		$("#unlock").val("");
		$("#scancode").val("");
		$("#js-tw").html('<a href="javascript:;" id="jsTwBtn" class="twaddBtn">从图文库中选择</a>');
		$("#js-pic").html('<a href="javascript:;" id="jsPicBtn" class="twaddBtn">从图片库中选择</a>');
	}
	
	function addTwToContain(multiple, twId, twMsg) {
		$("#newsMsg").val(twId);
		 var twLi = $("#js-tw");
		 var twHtml = "";
		 if(multiple) {
			 twHtml = '<div class="mn-tuwen-con mn-Dtuwen-con">' + twMsg + '</div>';
		 } else {
			 twHtml = '<div class="mn-tuwen-con">' + twMsg + '</div>';
		 }
		 twHtml += '<a href="javascript:;" id="jsTwDelete">删除</a >';
		 $("#jsTwBtn").remove();
		 twLi.html(twHtml);
		 
		var mnInform=$(".mn-inform");
	    mnInform.children("ol").children("li").eq(0).addClass("cur").siblings().removeClass("cur");
        mnInform.children("ul").children("li").eq(0).show().siblings().hide();
	}
	
	function addPicToContain(pic) {
		 var picLi = $("#js-pic");
		 var picHtml = '<div class="mn-tuwen-con"><div class="news_thumb"><img src="'+pic+'" /></div></div>';
		 picHtml += '<a href="javascript:;" id="jsPicDelete">删除</a >';
		 $("#jsPicBtn").remove();
		 picLi.html(picHtml);
		 
		 $("#picMsg").val(pic);
		 
		 var mnInform=$(".mn-inform");
	     mnInform.children("ol").children("li").eq(1).addClass("cur").siblings().removeClass("cur");
         mnInform.children("ul").children("li").eq(1).show().siblings().hide();
	}
	
	function addTextToContain(text) {
		$("#textMsg").val(text);
		var mnInform=$(".mn-inform");
	    mnInform.children("ol").children("li").eq(2).addClass("cur").siblings().removeClass("cur");
        mnInform.children("ul").children("li").eq(2).show().siblings().hide();
	}
	
	function addUnlockToContain() {
		var mnInform=$(".mn-inform");
	    mnInform.children("ol").children("li").eq(3).addClass("cur").siblings().removeClass("cur");
        mnInform.children("ul").children("li").eq(3).show().siblings().hide();
	}
	
	function addScancodeToContain() {
		var mnInform=$(".mn-inform");
	    mnInform.children("ol").children("li").eq(4).addClass("cur").siblings().removeClass("cur");
        mnInform.children("ul").children("li").eq(4).show().siblings().hide();
	}
	
	function genPageNavigation(page, pageCount, rowCount, start, end, dataMethod) {
		var nav = "<div class='col-md-5 col-sm-12'>"
				+"<div class='dataTables_info'>";
		if (rowCount == 0) {
			nav += "总共" + rowCount + "条";
		} else {
			nav += "显示  " + (start + 1) + " to " + end + " 总共" + rowCount + "条";
		}
		nav += "</div></div>";
		if (rowCount > 0) {
			nav += "<div class='col-md-7 col-sm-12'>";
			nav += "<div class='dataTables_paginate paging_bootstrap'>";
			nav += "<div class='pagination' style='visibility: visible;'>";
			if (page == 1) {
				nav += "<li class='prev disabled'>";
			} else {
				nav += "<li class='prev'>";
			}
			nav += "<a href=\"javascript:"+dataMethod+"('" + ((page - 1) < 1 ? 1:(page-1)) + "');\" title='Prev'>";
			nav += "<i class='fa fa-angle-left'></i></a></li>";

			var sp = 1;
			var ep = pageCount > 10 ? 10 : pageCount;

			if (page > 6) {
				sp = page - 5;
				ep = sp + 9;
				if (ep > pageCount) {
					ep = pageCount;
				}
			}
			for (var i = sp; i <= ep; i++) {
				if (i == page) {
					nav += "<li class='active'>";
				} else {
					nav += "<li>";
				}
				nav += "<a href=\"javascript:"+dataMethod+"('" + i + "');\">" + i + "</a></li>";
			}

			if (page == pageCount) {
				nav += "<li class='next disabled'>";
			} else {
				nav += "<li class='next'>";
			}
			nav += "<a href=\"javascript:"+dataMethod+"('" + ((page+1) > pageCount ? pageCount:(page+1)) + "')\" title='Next'>";
			nav += "<i class='fa fa-angle-right'></i></a></li>";
			nav += "</ul></div></div>";
		}
		return nav;
}

	
function handleData() {
	var iObj = $(".js-nav-icon").find(".cur").find("i");
	if(iObj.hasClass("js-tuwenType")) {
		$("#textMsg").val("");
		$("#picMsg").val("");
		$("#unlock").val("");
		$("#scancode").val("");
	} else if(iObj.hasClass("js-picType")) {
		$("#textMsg").val("");
		$("#newsMsg").val("");
		$("#unlock").val("");
		$("#scancode").val("");
	} else if(iObj.hasClass("js-unlock")){
		$("#newsMsg").val("");
		$("#picMsg").val("");
		$("#scancode").val("");
		$("#unlock").val("unlock");
	}else if(iObj.hasClass("js-scancode")){
		$("#textMsg").val("");
		$("#newsMsg").val("");
		$("#picMsg").val("");
		$("#unlock").val("");
		$("#scancode").val("scancode");
	}else {
		$("#picMsg").val("");
		$("#newsMsg").val("");
		$("#unlock").val("");
		$("#scancode").val("");
	}
}