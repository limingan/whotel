var checkTools = function(options) {
	
	var setting = {
			all:"js-allCheckbox",
			list:"js-listCheckbox"
	};
	
	var settings = setting;
	if(options != null) {
		settings = $.extend(setting, options);
	}
	
	return {
        //main function to initiate the module
		init: function() {
			$("body").on("click", "."+settings.all, function() {
				var $this = $(this);
				var listCheckBox = $("."+settings.list);
				if($this.prop("checked")) {
					listCheckBox.not(":disabled").prop("checked", true);
				} else {
					listCheckBox.not(":disabled").prop("checked", false);
				}
			});
			
			$("body").on("click", "."+settings.list, function() {
				var listCheckBox = $("."+settings.list);
				var allCheckBox = $("."+settings.all);
				var isAll = true;
				listCheckBox.each(function(i) {
					var $this = $(this);
					if(!$this.prop("checked")) {
						isAll = false;
						return false;
					}
				});
				if(isAll) {
					allCheckBox.prop("checked", true);
				} else {
					allCheckBox.prop("checked", false);
				}
			});
		},
		getAllCheckObj: function () {
			var ids = [];
			$("."+settings.list).not(":disabled").each(function(i) {
				var $this = $(this);
				if($this.prop("checked")) {
					ids.push($this);
				}
			});
			return ids;
        },
		getAllCheckValues: function () {
			var ids = [];
			$("."+settings.list).not(":disabled").each(function(i) {
				var $this = $(this);
				if($this.prop("checked")) {
					ids.push($this.val());
				}
			});
			return ids.join(",");
        }
    };
};