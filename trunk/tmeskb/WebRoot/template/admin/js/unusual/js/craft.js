$(function() {	
	
	
	$("#completeCraft").click(function(){		
    	$("#inputForm").submit();  		    		
	});
	
	
	$("#checkCraft").click(function(){
	    document.inputForm.action="craft!check.action";
	    $("#inputForm").submit(); 			
	});
	
	$("#closeCraft").click(function(){
		document.inputForm.action="craft!close.action";
		$("#inputForm").submit(); 			
	});
	
	
	$("#returnCraft").click(function(){
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
		$("#productName1").val(id[0]);//产品id
		$("#productNa").val(id[1]);//产品名称
		$("#productNo").val(id[2]);//产品编码
		layer.close(index); 
	});
}
	

})


