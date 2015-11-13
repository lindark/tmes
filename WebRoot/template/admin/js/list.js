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
	
//	 $("input.datePicker").datepicker({
//		 format: 'yyyy-mm-dd',
//		 language:'zh-CN',
//		 autoclose: true,
//		 todayBtn:'linked'
//	 });
	
	$("input.datePicker").datepicker({
    	clearText: '清除',   
        clearStatus: '清除已选日期',   
        closeText: '关闭',   
        closeStatus: '不改变当前选择',   
        prevText: '<上月',   
        prevStatus: '显示上月',   
        prevBigText: '<<',   
        prevBigStatus: '显示上一年',   
        nextText: '下月>',   
        nextStatus: '显示下月',   
        nextBigText: '>>',   
        nextBigStatus: '显示下一年',   
        currentText: '今天',   
        currentStatus: '显示本月',   
        monthNames: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],   
        monthNamesShort: ['一','二','三','四','五','六', '七','八','九','十','十一','十二'],   
        monthStatus: '选择月份',   
        yearStatus: '选择年份',   
        weekHeader: '周',   
        weekStatus: '年内周次',   
        dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],   
        dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],   
        dayNamesMin: ['日','一','二','三','四','五','六'],   
        dayStatus: '设置 DD 为一周起始',   
        dateStatus: '选择 m月 d日, DD',   
        dateFormat: 'yy-mm-dd',   
        firstDay: 1,   
        initStatus: '请选择日期',   
		changeMonth : true,// 设置允许通过下拉框列表选取月份。
		changeYear : true,// 允许通过下拉框列表选取年份
		showMonthAfterYear : true,//是否把月放在年的后面
		showOtherMonths: true,
		selectOtherMonths: true});
	
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