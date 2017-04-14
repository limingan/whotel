<script src="/static/common/js/uploadApi.js" type="text/javascript"></script>
<input name="token" type="hidden" value="${token}" id="token">
<input name="key" type="hidden" value="${key}" id="res_key">
<input name="res_url" type="hidden" value="${res_url}" id="res_url">
<script>
	function setUploadToken(dir, resType, fileName) {
		var params = {};
		if(dir) {
			params.dir = dir;
		}
		
		if(resType) {
			params.resType = resType;
		}
		
		if(fileName) {
			params.fileName = fileName;
		}
		$.ajax({
			url : "/uploadToken.json",
			type : "get",
			async : false,
			data:params,
			dataType : "json",
			success : function(data) {
				if(data) {
					$("#token").val(data.token);
					$("#res_key").val(data.key);
					$("#res_url").val(data.res_url);
				}
			}
		});
	}
	
	$(function() {
		$("body").on("click", ".del-icon i", function() {
			var $this = $(this).parent("span");
			$this.remove();
		});
	});
</script>