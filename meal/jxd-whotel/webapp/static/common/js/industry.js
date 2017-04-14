/**
 * 顶级行业
 */
function ajaxListTopIndustry(selectId, head, callBack) {
	var selectObj = $("#" + selectId);
	selectObj.empty();
	if(head) {
		selectObj.append("<option value=''>"+head+"</option>");
	}
	$.ajax({
		async : false,
		type : "POST",
		url : "/ajaxGetTopIndustry.do",
		dataType : "json",
		success : function(json) {
			if (json && json != null && json.length > 0) {
				for ( var i = 0, len = json.length; i < len; i++) {
					var industry = json[i];
					if(industry && typeof(industry) != "undefined") {
						selectObj.append("<option value='" + industry.id + "'>" + industry.name + "</option>");
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

function ajaxListSubIndustry(selectId, id, head, callBack) {
	var selectObj = $("#" + selectId);
	selectObj.empty();
	if(head) {
		selectObj.append("<option value=''>"+head+"</option>");
	}
	if (id != "") {
		$.ajax({
			async : false,
			type : "POST",
			url : "/ajaxGetSubIndustry.do",
			data : {
				'parentId' : id
			},
			dataType : "json",
			success : function(json) {
				if (json && json != null && json.length > 0) {
					for ( var i = 0, len = json.length; i < len; i++) {
						var industry = json[i];
						if(industry && typeof(industry) != "undefined") {
							selectObj.append("<option value='" + industry.id + "'>" + industry.name + "</option>");
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
	$("#topIndustryId").change(function() {
		$this = $(this);
		$("#secondIndustryId").hide();
		$("#thirdIndustryId").hide();
		var industryId = $this.find("option:selected").val();
		ajaxListSubIndustry("secondIndustryId", industryId, "请选择");
		$("#industryId").val(industryId);
	});
	
	$("#secondIndustryId").change(function() {
		$this = $(this);
		$("#thirdIndustryId").hide();
		var industryId = $this.find("option:selected").val();
		ajaxListSubIndustry("thirdIndustryId", industryId, "请选择");
		$("#industryId").val(industryId);
	});
	
	$("#thirdIndustryId").change(function() {
		$this = $(this);
		var industryId = $this.find("option:selected").val();
		$("#industryId").val(industryId);
	});
	
	initalIndustry();
});

function initalIndustry() {
	var topIndustryValue = $("#topIndustryValue").val();
	var secondIndustryValue = $("#secondIndustryValue").val();
	var thirdIndustryValue = $("#thirdIndustryValue").val();
	ajaxListTopIndustry("topIndustryId", "请选择", function() {
		if(topIndustryValue != "") {
			$("#topIndustryId option[value="+topIndustryValue+"]").prop("selected", "selected");
			ajaxListSubIndustry("secondIndustryId", topIndustryValue, "请选择", function() {
				if(secondIndustryValue != "") {
					$("#secondIndustryId option[value="+secondIndustryValue+"]").prop("selected", "selected");
					ajaxListSubIndustry("thirdIndustryId", secondIndustryValue, "请选择", function() {
						if(thirdIndustryValue != "") {
							$("#thirdIndustryId option[value="+thirdIndustryValue+"]").prop("selected", "selected");
						}
					});
				}
			});
		}
	});
}
