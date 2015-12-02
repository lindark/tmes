$(function() {	
	
	$("#completeCraft").click(function(){		
    	$("#inputForm").submit();  		    		
	});
	
	
	$("#checkCraft").click(function(){
		$("#inputForm").attr("action", "craft!check.action");
	    $("#inputForm").submit(); 			
	});
	
	$("#closeCraft").click(function(){
		$("#inputForm").attr("action", "craft!close.action");
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
	var content="craft!browser.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#productName1").text(id[0]);
		$("#productNa").val(id[1]);//产品名称
		$("#productNo").text(id[2]);//产品编码
		layer.close(index); 
	});
}
	

})


