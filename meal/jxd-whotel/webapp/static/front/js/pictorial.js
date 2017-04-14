
$(function() {
	var pages=$(".pages");
	var arrow=$(".arrow");
	var decreaseBtn=$(".decreaseBtn");
	var increaseBtn=$(".increaseBtn");
	var lenNum=pages.children("li").length;
	var i=0;
	function pageDecrease() {
	  if(i<lenNum&&i>0) {
		  pages.children("li").eq(i).animate({top:"100%"});
		  i--;
	   } 
	}

	function pageIncrease() {
	  if(i<lenNum-1) {
		  i++;
	  } else {
		  i = 0;
		  pages.children("li").css({top:"100%"}); 
	  }
	  pages.children("li").eq(i).animate({top:0});
	}

	decreaseBtn.click(function(){
		pageDecrease();
	});
	increaseBtn.click(function(){
		pageIncrease();
	});

	/*
	var  evY;	
	pages.mousedown(function(e1){
		e1.preventDefault();
		evY=e1.pageY;

	});
		
	pages.mouseup(function(e){
	   if(e.pageY-evY>50) {
			//alert("上")
			PageDecrease();
		} else if(e.pageY-evY<-50){
			//alert("下")
			PageIncrease();
		}
	});	
	*/
	pages.swipe({
	    swipe:function(event, direction, distance, duration, fingerCount) {
	        //$(this).text("你用"+fingerCount+"个手指以"+duration+"秒的速度向" + direction + "滑动了" +distance+ "像素 " );
			if(direction=="up") {
				pageIncrease();
			} else if(direction=="down"){
				pageDecrease();
			}
	     }
	});  
		
	function shinningArro(){
		arrow.animate({bottom:10,opacity:0.5})
			 .animate({bottom:15,opacity:1})
			 .animate({bottom:18,opacity:0.5})
			 .animate({bottom:25,opacity:0});
	}
	setInterval(shinningArro,1000);
});