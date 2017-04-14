$(function() {
	$(".goback").click(function() {
		if(window.history.length > 1){
			window.history.go(-1);
		}else{//如果没有历史记录则跳转到指定的URL
			window.close();
		}
	});
});