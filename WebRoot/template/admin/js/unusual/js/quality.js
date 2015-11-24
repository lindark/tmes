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
	
	
	// 减少选项内容输入框
	$("#productId").click( function() {
		showProduct();
	})
	
	
	

})


function showProduct()
{
	var title = "选择产品";
	var width="800px";
	var height="632px";
	var content="quality!browser.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#xfuid").val(id[0]);//单元id
		$("#label_xfuname").text(id[1]);//单元名称
		layer.close(index); 
	});
}