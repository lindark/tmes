$(function() {	
	
	/*
	// 故障原因
	$("#faultReason").click( function() {
		showProduct();
	})
	
	
	function showProduct()
{
	var title = "选择产品";
	var width="800px";
	var height="632px";
	var content="model!browser.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		//$("#productName1").val(id[0]);//产品id
		alert("2");
		$("#productName1").html(id[0]);
		$("#productNa").val(id[1]);//产品名称
		layer.close(index); 
	});
}
	*/
	
	
	
	$("#completeModel").click(function(){		
    	$("#inputForm").submit();  		    		
	});
	
	
	$("#checkModel").click(function(){
		$("#inputForm").attr("action", "model!check.action");
	    $("#inputForm").submit(); 			
	});
	
	$("#confirmModel").click(function(){
		$("#inputForm").attr("action", "model!confirm.action");
	    $("#inputForm").submit(); 			
	});
	
	$("#closeModel").click(function(){
		$("#inputForm").attr("action", "model!close.action");
		$("#inputForm").submit(); 			
	});
	
	
	$("#returnModel").click(function(){
		window.history.back();
	});
	
	
	
	
	// 产品弹出框
	$("#productId").click( function() {
		showProduct();
	})
	
	
	function showProduct()
{
	var title = "选择产品";
	var width="800px";
	var height="632px";
	var content="model!browser.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#productName1").text(id[0]);//产品id productName1
		$("#productNa").val(id[1]);//产品名称
		alert(work);
		layer.close(index); 
	});
}
	

})


