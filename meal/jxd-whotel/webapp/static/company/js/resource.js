$(function() {
		
		initUeditor();
		
		initEditItemEvent();
		
		$("body").on("click", ".uploadFile", function() {
			var _this = $(this);
			setUploadToken();
			uploadFile(_this.get(), null, coverCallback);
		});
		
		$("body").on("click", ".del-icon i", function() {
			var $this = $(this).parent("span");
			$this.remove();
		});
		
		$("body").on("mouseover", ".editFirst", function() {
			 $(".pencil").css({"display":"block","opacity":"0.7"});
		});
		
		$("body").on("mouseleave", ".editFirst", function() {
			 $(".pencil").css("display","none");
		});
		
		$("body").on("click", ".js-addItem", function() {
			var _this = $(this);
			var liObjs = _this.closest("ul").find("li");
			if(liObjs.length > 8) {
				boxAlert("您最多只能添加8条图文");
				return false;
			}
			
			_this.closest("li").before('<li class="editCur">'
			            + '<h2 class="text-left js-title">标题</h2>'
			            + '<em class="js-coverPreview">缩略图</em>'
			            + '<a href="javascript:;" class="glyphicon glyphicon-pencil JS_pencil" ></a>'
			            + '<a href="javascript:;" class="glyphicon glyphicon-trash JS_delete" ></a>'
			       		+ '</li>');
			addEditItem();
			initUeditor();
			initEditItemEvent();
		});
		
		$("body").on("mouseleave", ".editCur", function() {
			$(this).removeClass("hover"); 
		});
		
		$("body").on("mouseover", ".editCur", function() {
			$(this).addClass("hover");
		});

		$("body").on("click", ".JS_pencil", function() {
			var index = $(this).closest("li").index();
			showEditArea(index);
		}); 
		
		$("body").on("click", ".JS_delete", function() {
			var curLi = $(this).closest("li");
			var index = curLi.index();
			var editArea=$(".editArea");
			editArea.eq(index).remove();
			curLi.remove();
			showEditArea(index - 1);
		}); 
		
	});
	
	function showEditArea(index) {
		var editArea=$(".editArea");
		editArea.eq(index).siblings().removeClass("cur");
		editArea.eq(index).addClass("cur").css("margin-top",$(".editObj").children("li").eq(index).offset().top-$(".editObj").offset().top);
	}
	
	function coverCallback(file) {
		var index = $(file).closest(".editArea").index();
		var html = "<input name='news["+index+"].cover' type='hidden' value='"+$("#res_key").val()+"' class='js-coverHidden'>"
        			+ "<img src='"+$("#res_url").val()+"' width='120' height='80'/>";
		$(file).closest("dd").find(".imagePreview").html(html);
		
		$(".js-coverPreview").eq(index).html("<img src='"+$("#res_url").val()+"'/>");
	}
	
	var ueditors = [];
	function initUeditor() {
		//初始化文本编辑器
		var option = {
				initialFrameWidth:430,  //初始化编辑器宽度,默认430
		        initialFrameHeight:250,  //初始化编辑器高度,默认150
			    //关闭字数统计
			    wordCount:false,
			    //关闭elementPath
			    elementPathEnabled:false,
			    //focus:true
			    //readonly:true
			    toolbars: [[
			                  'source', 'bold', 'italic', 'underline', 'justifyleft','justifycenter','justifyright','|', 'insertorderedlist','insertunorderedlist', '|', 'insertimage', '|', 
			                     		'removeformat','fontfamily', 'fontsize', 'forecolor', 'backcolor', 'music','insertvideo','link','unlink','inserttable','deletetable','emotion','map'
			    ]]
		}; 
		var editors = $(".editor");
		if(editors && editors.length > 0) {
			editors.each(function(i) {
				var _this = $(this);
				if(!_this.hasClass("edui-default")) {
					var editor = new baidu.editor.ui.Editor(option);
					editor.render(editors.get(i));
					ueditors.push(editor);
				}
			});
		}
	}
	
	function initEditItemEvent() {
		var  textSize=$(".JS_textSize");
		var  textNum=$(".JS_textNum");
		
		var titleObj = $(".js-preview").find(".js-title");//h2
		//var titleCon=editArea.find(".js-titCon");//标题
		textSize.each(function(index){
			var _this=$(this);
			textNum.eq(index).find("i").text(_this.val().length);
			
			if(_this.hasClass("js-titCon"))
			 {
				var i = _this.closest(".editArea").index();
				
				if(_this.val() != "") {
					titleObj.eq(i).text(_this.val());
				} else {
					titleObj.eq(i).text("标题");
				}
			}
			_this.keyup(function(){
				var totalNum = parseInt(textNum.eq(index).find("abbr").text());
				var numI = textNum.eq(index).find("i");
				
				if(_this.val().length<=totalNum)
				{
					numI.text(_this.val().length);
					if(_this.hasClass("js-titCon"))
					 {
						var i = _this.closest(".editArea").index();
						titleObj.eq(i).text(_this.val()); 
					 }
				}
				else{
					numI.text(totalNum);
					var valText=_this.val();
					_this.val(valText.slice(0,totalNum));
					return;
				}
			});
		});
	}

	function addEditItem(){
		var editArea = $(".editArea");
		var i = editArea.length;
		
		var temp = '<div class="editArea">'
		       +'<i class="arrowEdt"><img src="/static/company/images/arrowEdt.png"></i>'
		       +'<div>'
		       +'   <ul>'
		       +'      <li>'
		       +'         <dl>'
		       +'             <dt>标题</dt>'
		       +'             <dd class="editInput">'
		       +'               <input type="text" name="news['+i+'].title" value="" class="JS_textSize js-titCon"><em  class="JS_textNum"><i>0</i>/<abbr>64</abbr></em>'
		       +'             </dd>'
		       +'          </dl>'    
		       +'       </li>'
		       +'       <li>'
		       +'         <dl>'
		       +'             <dt>作者<abbr>（选填）</abbr></dt>'
		       +'             <dd class="editInput">'
		       +'               <input type="text" name="news['+i+'].author" value="" class="JS_textSize"><em  class="JS_textNum"><i>0</i>/<abbr>8</abbr></em>'
		       +'             </dd>'
		       +'          </dl>'    
		       +'       </li>'
		       +'        <li>'
		       +'         <dl>'
		       +'             <dt>封面<abbr>（大图片建议尺寸：900像素 * 500像素）</abbr></dt>'
		       +'            <dd>'
		       +'               <span class="fm-uploadPic"><input type="file" name="file" class="uploadFile"><b>上传</b></span>'
		       +'               <div class="imagePreview">'
		       +'				<input type="hidden" name="news['+i+'].cover" class="js-coverHidden"/>'
		       +'               </div>'
		       +'             </dd>'
		       +'            <dd class="frontImgcheck">'
		       +'               <input type="checkbox" name="news['+i+'].showCover" value="true"><span>封面图片显示在正文中</span>'
		       +'             </dd>'
		       +'         </dl>'    
		       +'      </li>'
		       +'        <li>'
		       +'         <dl>'
		       +'            <dt>摘要<abbr>（选填，该摘要只在发送图文消息为单条时显示）</abbr></dt>'
		       +'            <dd class="summry">'
		       +'               <textarea name="news['+i+'].brief" class="JS_textSize"></textarea>'
		       +'               <em  class="JS_textNum"><i>0</i>/<abbr>120</abbr></em>'
		       +'            </dd>'
		       +'        </dl>'    
		       +'      </li>'
		       +'       <li>'
		       +'         <dl>'
		       +'             <dt>正文<abbr></abbr></dt>'
		       +'            <dd class="bodyText" >'
		       +'			 <textarea class="editor" name="news['+i+'].content"> </textarea>'
		       +'            </dd>'
		       +'          </dl>'    
		       +'       </li>'
		       +'        <li>'
		       +'         <dl>'
		       +'             <dt>原文链接<abbr>（选填）</abbr></dt>'
		       +'             <dd class="editInput link">'
		       +'             <input type="text" name="news['+i+'].url" value="" class="js-url">'
		       +'            </dd>' 
		       +'         </dl>'    
		       +'      </li>'
		       +'  </ul>'
		      +' </div>'
		   +'</div>';
		   $("#areaContain").append(temp);
	}
	
	function updateNewsResource() {
		var rs = true;
		$(".error").remove();
		$(".js-titCon").each(function() {
			var _this = $(this);
			if($.trim(_this.val()) == "") {
				rs = false;
				var i = _this.closest(".editArea").index();
				showEditArea(i);
				_this.closest("dd").after("<dd class='error'>标题不能为空</dd>");
				return false;
			}
		});
		
		$(".js-coverHidden").each(function() {
			var _this = $(this);
			if($.trim(_this.val()) == "") {
				rs = false;
				var i = _this.closest(".editArea").index();
				showEditArea(i);
				_this.closest("dd").after("<dd class='error'>封面图片必须上传</dd>");
				return false;
			}
		});
		
	/*	$(".editor").each(function() {
			var _this = $(this);
			var i = _this.closest(".editArea").index();
			var content = ueditors[i].getContent(); 
			if($.trim(content) == "") {
				rs = false;
				showEditArea(i);
				_this.closest("dd").after("<dd class='error'>正文信息必须填写</dd>");
				return false;
			}
		}); */
		
		$(".js-url").each(function() {
			var _this = $(this);
			if($.trim(_this.val()) != "" && !checkUrl(_this.val())) {
				rs = false;
				var i = _this.closest(".editArea").index();
				showEditArea(i);
				_this.closest("dd").after("<dd class='error'>请填写正确的链接</dd>");
				return false;
			}
		});
		
		if(rs) {
			$('#submitForm').submit();
		}
	}