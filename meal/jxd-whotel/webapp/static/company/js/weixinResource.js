function synchronizeMPResource(type) {
	
	confirmCallback("真的要同步公众平台的素材吗？", function() {
		var $loading = $("#loading");
		$loading.show();
		$.ajax({
			url:"/company/resource/synchronizeMPResource.do",
			data:{type:type},
			type:"post",
			success:function(data) {
				$loading.hide();
				window.location.reload();
			}
		});
	});
}