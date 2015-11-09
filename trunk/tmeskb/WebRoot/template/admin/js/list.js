

$().ready( function() {
	var $searchButton = $("#searchButton");//搜索
	var $searchform= $("#searchform");//搜索表单
	var $confirmsButton = $("#confirmsButton");//刷卡确认
	
	// 滑动提示框
	$("body").prepend('<div id="tipWindow" class="tipWindow"><span class="icon"> </span><span class="messageText"></span></div>');
	
	/**
	 * 搜索按钮
	 */
	$searchButton.click(function(){
		
		var rules = "";
		var ishead= 0;
		$searchform.find(":input").each(function(i){
			if($(this).val()){
				if(ishead==1)
					rules +=",";
				rules += '"' + $(this).attr("name") + '":"' + $(this).val() + '"';
				ishead=1;
			}
			
		});
		ParamJson = '{' + rules + '}';
		var postData = $("#grid-table").jqGrid("getGridParam", "postData");
        $.extend(postData, { Param: ParamJson });
        $("#grid-table").jqGrid("setGridParam", { search: true }).trigger("reloadGrid", [{ page: 1}]);  //重载JQGrid
 
		
		return false;
	});
	/**
	 * 批量刷卡确认
	 */
	$confirmsButton.click(function(){
		var id = "";
		id=$("#grid-table").jqGrid('getGridParam','selarrrow');
		if(id==""){
			alert("请选择至少一条转储记录！");
		}else{
			window.location.href="dump!confirms.action?id="+id;			
		}
	});
	/**
	 * 时间选择控件
	 */
	
	 $("input.datePicker").datepicker({
		 format: 'yyyy-mm-dd',
		 language:'zh-CN',
		 autoclose: true,
		 todayBtn:'linked'});
	
	// 重新绑定日期选择框
	$.bindDatePicker = function () {
		$("input.datePicker").datepicker({format: 'yyyy-mm-dd'});
	}
	
	
	// 滑动提示框
	$.tip = function () {
		var $tipWindow = $("#tipWindow");
		var $icon = $("#tipWindow .icon");
		var $messageText = $("#tipWindow .messageText");
		var messageType;
		var messageText;
		if (arguments.length == 1) {
			messageType = "warn";
			messageText = arguments[0];
		} else {
			messageType = arguments[0];
			messageText = arguments[1];
		}
		if (messageType == "success") {
			$icon.removeClass("warn").removeClass("error").addClass("success");
		} else if (messageType == "error") {
			$icon.removeClass("warn").removeClass("success").addClass("error");
		} else {
			$icon.removeClass("success").removeClass("error").addClass("warn");
		}
		$messageText.html(messageText);
		$tipWindow.css({"margin-left": "-" + parseInt($tipWindow.width() / 2) + "px", "left": "50%"});
		setTimeout(function() {
			$tipWindow.animate({left: 0, opacity: "hide"}, "slow");
		}, 1000);
		$tipWindow.show();
	}
});