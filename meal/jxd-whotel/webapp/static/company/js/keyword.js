var checkTool = new checkTools();
$(function() {
	
	checkTool.init();
	
	$("body").on("click", ".js-keywordEdit", function() {
		var _this = $(this);
		var trObj = _this.closest("tr");
		var keyword = trObj.find(".js-keyword").val();
		var type = trObj.find(".js-keywordType").val();
		var count = trObj.find(".js-keywordCount").val();
		var partChecked = "";
		var allChecked = "";
		if(type == "PART") {
			partChecked = "checked";
		} else if(type == "ALL") {
			allChecked = "checked";
		}
		bootbox.dialog({
		    message:'<div class="js-tip">'
		     +'</div>'
		     +'<div class="input">'
			 +'关键字：<input type="text" name="keyword" id="keyword" value="'+keyword+'">'
			 +'</div>'
			 +'<div class="input">'
			 +'匹配规则：<label><input type="radio" name="type" value="PART" '+partChecked+'>模糊匹配</label><label><input type="radio" name="type" value="ALL" '+allChecked+'>全匹配</label>'
			 +'</div>',
		     title: '修改关键字',
		     buttons:{
		    	 cancel: {
		    		 label:"取消"
		    	 },
		    	 success: {
		    		 label:"完成",
		    		 callback:function(){
		    			 $(".error").remove();
		    			 var inputObj = $("input[name=keyword]");
		    			 var keyword = inputObj.val();
		    			 if($.trim(keyword) == "") {
		    				 inputObj.closest("div").after("<div class='error'>关键字不能为空！</div>");
		    				 return false;
		    			 }
		    			 
		    			 var radioObj = $("input[name=type]:radio:checked");
		    			 var type = radioObj.val();
		    			 var typeLabel = radioObj.closest("label").text();
		    			 var index = trObj.index();
		    			 var html = '<tr>'
		                   		+'<input name="keywords['+index+'].keyword" type="hidden" value="'+keyword+'" class="js-keyword"/>'
		                        +'<input name="keywords['+index+'].type" type="hidden" value="'+type+'"  class="js-keywordType"/>'
		                        +'<input name="keywords['+index+'].count" type="hidden" value="'+count+'"  class="js-keywordCount"/>'
			                    +'<td class="cr-td1"><input type="checkbox" class="checkboxes js-listCheckbox"><span>'+keyword+'</span></td>'
			                    +'<td class="cr-td2"><span>'+typeLabel+'</span></td>'
			                    +'<td class="cr-td3"><a href="javascript:;" class="btn btn-sm blue js-keywordEdit"><i class="fa fa-edit"></i>编辑</a></td>'
			                    +'</tr>';
		    			 trObj.replaceWith(html);
		    		 }
		    	 }
		   }
		});
	});
	
	$(".js-addKeyword").click(function() {
		bootbox.dialog({
		    message:'<div class="js-tip">'
		     +'</div>'
		     +'<div class="input">'
			 +'关键字：<input type="text" name="keyword" id="keyword">'
			 +'</div>'
			 +'<div class="input">'
			 +'匹配规则：<label><input type="radio" name="type" value="PART" checked>模糊匹配</label><label><input type="radio" name="type" value="ALL">全匹配</label>'
			 +'</div>',
		     title: '添加关键字',
		     buttons:{
		    	 cancel: {
		    		 label:"取消"
		    	 },
		    	 success: {
		    		 label:"完成",
		    		 callback:function(){
		    			 $(".error").remove();
		    			 var inputObj = $("input[name=keyword]");
		    			 var keyword = inputObj.val();
		    			 if($.trim(keyword) == "") {
		    				 inputObj.closest("div").after("<div class='error'>关键字不能为空！</div>");
		    				 return false;
		    			 }
		    			 
		    			 var radioObj = $("input[name=type]:radio:checked");
		    			 var type = radioObj.val();
		    			 var typeLabel = radioObj.closest("label").text();
		    			 var table = $(".js-keyword-table");
		    			 var trObjs = table.find("tr");
		    			 var fieldIndexObj = $("#fieldIndex");
		    			 var index = fieldIndexObj.val();
		    			 if(index == "") {
		    				 index = trObjs.length;
		    			 } 
	    				 fieldIndexObj.val(parseInt(index) + 1);
		    			 var html = '<tr>'
		                   		+'<input name="keywords['+index+'].keyword" type="hidden" value="'+keyword+'" class="js-keyword"/>'
		                        +'<input name="keywords['+index+'].type" type="hidden" value="'+type+'"  class="js-keywordType"/>'
			                    +'<td class="cr-td1"><input type="checkbox" class="checkboxes js-listCheckbox"><span>'+keyword+'</span></td>'
			                    +'<td class="cr-td2"><span>'+typeLabel+'</span></td>'
			                    +'<td class="cr-td3"><a href="javascript:;" class="btn btn-sm blue js-keywordEdit"><i class="fa fa-edit"></i>编辑</a></td>'
			                    +'</tr>';
			             $(".js-allCheckbox").prop("checked", false);
		    			 table.append(html);
		    		 }
		    	 }
		   }
		});
	});
	
	$(".js-deleteKeyword").click(function() {
		var checkObjs = checkTool.getAllCheckObj();
		if(checkObjs.length == 0) {
			boxAlert("您没有选择任何需要删除的关键词！");
			return false;
		}
		
		boxConfirm("真的需要删除吗？", function(val) {
			if(val == true) {
				$(checkObjs).each(function(i) {
					var _this = $(this);
					_this.closest("tr").remove();
				});
			}
		});
	});
});

function handleCheckData() {
	var rs = true;
	var ruleNameObj = $(".js-ruleName");
	
	var ruleName = ruleNameObj.val();
	if($.trim(ruleName) == "") {
		ruleNameObj.siblings("i").addClass("error").html("规则名必须填写！");
		rs = false;
	}
	if(ruleName.length > 60) {
		ruleNameObj.siblings("i").addClass("error").html("规则名不能超过60个字符！");
		rs = false;
	}
	
	var table = $(".js-keyword-table");
	
	if(table.length > 0) {
	    var trObjs = table.find("tr");
	    if(trObjs.length == 0) {
	    	table.before("<p class='error'>没有添加任何关键词！</p>");
	    	rs = false;
	    }
	}
	if(rs) {
		handleData();
		$("#submitForm").submit();
	}
}