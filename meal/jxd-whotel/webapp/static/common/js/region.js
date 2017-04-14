/**
 * 省市联动
 */
function ajaxListProvince(selectId, head, callBack) {
	var selectObj = $("#" + selectId);
	selectObj.empty();
	if(head) {
		selectObj.append("<option value=''>"+head+"</option>");
	}
	$.ajax({
		async : false,
		type : "POST",
		url : "/ajaxGetProvince.do",
		dataType : "json",
		success : function(json) {
			if (json && json != null && json.length > 0) {
				for ( var i = 0, len = json.length; i < len; i++) {
					var region = json[i];
					if(region && typeof(region) != "undefined") {
						selectObj.append("<option value='" + region.regionId + "'>" + region.name + "</option>");
					}
				}
				selectObj.show();
			}
			
			if(typeof(callBack) != "undefined" && typeof(callBack) == "function") {
				callBack();
			}
		}
	});
}

function ajaxListSubRegion(selectId, id, head, callBack) {
	var selectObj = $("#" + selectId);
	selectObj.empty();
	if(head) {
		selectObj.append("<option value=''>"+head+"</option>");
	}
	if (id != "") {
		$.ajax({
			async : false,
			type : "POST",
			url : "/ajaxGetSubRegion.do",
			data : {
				'parentId' : id
			},
			dataType : "json",
			success : function(json) {
				if (json && json != null && json.length > 0) {
					for ( var i = 0, len = json.length; i < len; i++) {
						var region = json[i];
						if(region && typeof(region) != "undefined") {
							selectObj.append("<option value='" + region.regionId + "'>" + region.name + "</option>");
						}
					}
					selectObj.show();
				}
				
				if(typeof(callBack) != "undefined" && typeof(callBack) == "function") {
					callBack();
				}
			}
		});
	}
}

$(function() {
	$("#provinceId").change(function() {
		$this = $(this);
		$("#cityId").hide();
		$("#districtId").hide();
		var regionId = $this.find("option:selected").val();
		ajaxListSubRegion("cityId", regionId, "请选择");
		
		$("#regionId").val(regionId);
	});
	
	$("#cityId").change(function() {
		$this = $(this);
		$("#districtId").hide();
		var regionId = $this.find("option:selected").val();
		ajaxListSubRegion("districtId", regionId, "请选择");
		$("#regionId").val(regionId);
	});
	
	$("#districtId").change(function() {
		$this = $(this);
		var regionId = $this.find("option:selected").val();
		$("#regionId").val(regionId);
	});
	
	initalRegion();
});

function initalRegion() {
	var provinceValue = $("#provinceValue").val();
	var cityValue = $("#cityValue").val();
	var districtValue = $("#districtValue").val();
	ajaxListProvince("provinceId", "请选择", function() {
		if(provinceValue != "") {
			$("#provinceId option[value="+provinceValue+"]").prop("selected", "selected");
			ajaxListSubRegion("cityId", provinceValue, "请选择", function() {
				if(cityValue != "") {
					$("#cityId option[value="+cityValue+"]").prop("selected", "selected");
					ajaxListSubRegion("districtId", cityValue, "请选择", function() {
						if(districtValue != "") {
							$("#districtId option[value="+districtValue+"]").prop("selected", "selected");
						}
					});
				}
			});
		}
	});
}