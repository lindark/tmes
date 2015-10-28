

$().ready( function() {
	var $searchButton = $("#searchButton");//搜索
	var $searchform= $("#searchform");//搜索表单
	
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
		alert(ParamJson);
		var postData = $("#grid-table").jqGrid("getGridParam", "postData");
        $.extend(postData, { Param: ParamJson });
        $("#grid-table").jqGrid("setGridParam", { search: true }).trigger("reloadGrid", [{ page: 1}]);  //重载JQGrid
 
		
		return false;
	});
	
	/**
	 * 时间选择控件
	 */
	
	 $("input.datePicker").datepicker({format: 'yyyy-mm-dd',language:'zh-CN'});
	
	// 重新绑定日期选择框
	$.bindDatePicker = function () {
		$("input.datePicker").datepicker({format: 'yyyy-mm-dd'});
	}
});