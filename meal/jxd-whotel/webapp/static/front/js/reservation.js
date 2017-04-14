
$(function() {
	var pages=$(".pages");
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
});