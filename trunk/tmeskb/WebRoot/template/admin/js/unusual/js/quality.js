$(function() {
	// 增加选项内容输入框
	$("#addImage").click( function() {
		addAttributeOptionTr();
	})
	
	function addAttributeOptionTr() {
		var size=$(".zg").length;
		var attributeOptionTrHtml = '<tr class="zg"><td><textarea name="flowingRectifys['+size+'].content"	style="width:600px;"></textarea></td></tr>';
		if($(".zg").length > 0) {
			$(".zg:last").after(attributeOptionTrHtml);
		} 
	}
	
	
	
	
	// 减少选项内容输入框
	$("#removeImage").click( function() {
		removeAttributeOptionTr();
	})
	
	function removeAttributeOptionTr() {
		if($(".zg").length > 1) {
			$(".zg:last").remove();
		} else {
			alert("请至少保留一个选项!");
		}
	}

})