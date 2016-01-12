$(function() {	
	
	$("#completeCraft").click(function(){		
    	$("#inputForm").submit();  		    		
	});
	
	
	$("#checkCraft").click(function(){	
		var dt = $("#inputForm").serialize();
		var url = "craft!creditreply.action";		
	    
	    credit.creditCard(url,function(data){
			if(data.status=="success"){
				layer.alert(data.message, {icon: 6},function(){
					window.location.href="craft!list.action";
				}); 
			}else if(data.status=="error"){
				layer.alert(data.message, {
			        closeBtn: 0,
			        icon:5,
			        skin:'error'
			   });
			}		
		},dt)
	});
	
	$("#closeCraft").click(function(){		
		 var dt = $("#inputForm").serialize();
			var url = "craft!creditclose.action";		
			
			credit.creditCard(url,function(data){
			if(data.status=="success"){
				layer.alert(data.message, {icon: 6},function(){
					window.location.href="craft!list.action";
				}); 
			}else if(data.status=="error"){
				layer.alert(data.message, {
			        closeBtn: 0,
			        icon:5,
			        skin:'error'
			   });
			}		
		},dt)
	});
	
	
	$("#returnCraft").click(function(){
		window.history.back();
	});
	
	
	
	
	/*// 产品弹出框
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
	
	*/
	
	// 人员弹出框
	$("#repair").click( function() {
		showRepair();
	})
	
	
	function showRepair()
{
	var title = "选择人员";
	var width="800px";
	var height="500px";
	var content="craft!repair.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#repairName1").text(id[0]);
		$("#repairNa").val(id[1]);//
		layer.close(index); 
	});
}

})


