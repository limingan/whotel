// JavaScript Document
//
(function(){
	$.fn.waterfall=function(options){
		var defaults={
			list:".li",
            marginB:10
			
		 };
	
	  var opts=$.extend(defaults,options);
	  //opts为参数集合
	  // alert(options)
	  return this.each(function () {
		 var obj=$(this);
		 var child=$(opts.list);
		 var ix;
		 var objWidth=obj.parent().width()-parseInt(obj.css("padding-left"))-parseInt(obj.css("padding-right"))-parseInt(obj.css("margin-left"))-parseInt(obj.css("margin-right"));//obj的宽度
		 var childConwidth=child.width()+parseInt(child.css("padding-left"))+parseInt(child.css("padding-right"))+parseInt(child.css("margin-left"))+parseInt(child.css("margin-right"));//child的宽度公式
		 var arr=new Array(),arrS=new Array();
			
			  child.each(function(index){
				 var _this=$(this);
				 var horNum=Math.floor(objWidth/childConwidth);
				 //horNum为每横排元素的个数
				 var thisConheight=_this.height()+parseInt(_this.css("padding-top"))+parseInt(_this.css("padding-bottom"));//child的高度的宽度公式
				 
				 
				 
				  if(index<horNum)
					{
					   _this.css({
						 "left":(objWidth%childConwidth)/2+parseInt(obj.css("padding-left"))+parseInt(obj.css("margin-left"))+childConwidth*index,
						 "top":parseInt(obj.css("padding-top"))+parseInt(obj.css("margin-top"))
						 });
						 _this.queue(function(){
							arrS.push(thisConheight+parseInt(_this.offset().top)-parseInt(obj.offset().top)+opts.marginB);
							arr.push(thisConheight+parseInt(_this.offset().top)-parseInt(obj.offset().top)+opts.marginB);
						    arrS.sort(function(a, b){ return a - b;});
							
							ix=function(){	
									 for(var i=0;i<horNum;i++)
									 {
										 var theHeight=child.eq(i).height()+
													   parseInt(child.eq(i).css("padding-top"))+
													   parseInt(child.eq(i).css("padding-bottom"))+
													   parseInt(child.eq(i).offset().top)-
													   parseInt(obj.offset().top)+
													   opts.marginB;
										  
										 if(theHeight==arrS[0])
										 {  
											
											 return i; 
											 
										 }
									 }
								 }();//获取高度最小的child元素的index值的函数minHeightindex   
							 _this.dequeue();
							
							
						  });	 
						
					} 
				    else{
					 
					  _this.css({
						 "left":parseInt(child.eq(ix).css("left")),
						 "top":arr[ix]						   
						 });
						 
						 _this.queue(function(){ 
						     arrS[0]=thisConheight+parseInt(_this.offset().top)-parseInt(obj.offset().top)+opts.marginB;
							 arr[ix]=thisConheight+parseInt(_this.offset().top)-parseInt(obj.offset().top)+opts.marginB;
							 arrS.sort(function(a, b){ return a - b;});
							 
							  ix=function(){	
							        
									 for(var i=0;i<horNum;i++)
									 {
									   
									    if(arr[i]==arrS[0])
										{
										  return i; 
										}
									
									
									 }
									 
								 }()//获取高度最小的child元素的index值的函数minHeightindex 	 
						       _this.dequeue();
						   
						 
                         })
					 }
					
					
				})//遍历子元素
	   })
    }	
})(jQuery)