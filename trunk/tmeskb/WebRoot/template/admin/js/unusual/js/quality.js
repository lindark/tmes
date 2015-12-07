$(function() {
	
	$("#completeQuality").click(function(){		
    	$("#inputForm").submit();  		    		
	});
	
	
	$("#checkQuality").click(function(){
		$("#inputForm").attr("action", "quality!check.action");
	    $("#inputForm").submit(); 			
	});
	
	$("#confirmQuality").click(function(){
		$("#inputForm").attr("action", "quality!confirm.action");
	    $("#inputForm").submit(); 			
	});
	
	$("#closeQuality").click(function(){
		$("#inputForm").attr("action", "quality!close.action");
		$("#inputForm").submit(); 			
	});
	
	
	$("#returnQuality").click(function(){
		window.history.back();
	});
	
	
	
	// 增加选项内容输入框
	$("#addImage").click( function() {
		addAttributeOptionTr();
	})
	
	function addAttributeOptionTr() {
		var size=$(".zg").length;
		var attributeOptionTrHtml = '<tr class="zg"><td><input type="hidden" name="flowingId" value=""/><textarea name="flowingRectify.content"	style="width:600px;" class="text"></textarea>&nbsp;&nbsp;&nbsp;<a class="save" style="cursor:pointer">保存</a>&nbsp;&nbsp;<a style="cursor:pointer" class="edit">编辑</a>&nbsp;&nbsp;<a class="deleteButton" style="cursor:pointer">删除</a></div></td></tr>';
		alert(attributeOptionTrHtml);
		alert($(".zg").length);
		if($(".zg").length >= 0) {
			alert(3);
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
	
	
	// 产品弹出框
	$("#productId").click( function() {
		showProduct();
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
		//$("#productName1").val(id[0]);//产品名称
		$("#productName1").html(id[0]);
		$("#productNa").val(id[1]);//产品id
		

		url="process!getProcessList.action?id="+id[1];
		  $.ajax({
				url: url,
				//data: ids,
				dataType: "json",		
				success: function(data) {
					$(data).each(function(n){
			            $("<option/>").html(this.name).val(this.id)
			            .appendTo("#processName");
			        });
				}
			});	
		  
	
		layer.close(index); 
	});
}
	
	
	
	// 人员弹出框
	$("#receive").click( function() {
		showReceive();
	})
	
	
	function showReceive()
{
	var title = "选择人员";
	var width="800px";
	var height="632px";
	var content="quality!receive.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#receiveName1").text(id[0]);
		$("#receiveNa").val(id[1]);//
		layer.close(index); 
	});
}
	

})


