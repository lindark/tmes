$(function() {	
	/*
$("form.validatecredit").validate({
		
		errorClass: "validateError",
		ignore: ".ignoreValidate",
		onkeyup:false,
		errorPlacement: function(error, element) {
			var messagePosition = element.metadata().messagePosition;
			if("undefined" != typeof messagePosition && messagePosition != "") {
				var $messagePosition = $(messagePosition);
				if ($messagePosition.size() > 0) {
					error.insertAfter($messagePosition).fadeOut(300).fadeIn(300);
				} else {
					error.insertAfter(element).fadeOut(300).fadeIn(300);
				}
			} else {
				error.insertAfter(element).fadeOut(300).fadeIn(300);
			}
		},
		submitHandler: function(form) {
			var url = $(form).attr("action");
			var dt = $(form).serialize();
			credit.creditCard(url,function(data){
				$.message(data.status,data.message);
				window.location.href = "abnormal!list.action";
			},dt)
			
		}
	});*/
	
	$("#completeCraft").click(function(){		
    	$("#inputForm").submit();  		    		
	});
	
	
	$("#checkCraft").click(function(){
	//	$("#inputForm").attr("action", "craft!check.action");
	 //   $("#inputForm").submit(); 	
		var dt = $("#inputForm").serialize();
		var url = "craft!creditreply.action";		
		credit.creditCard(url,function(data){
			$.message(data.status,data.message);
			window.location.href = "craft!list.action";
		},dt)
	});
	
	$("#closeCraft").click(function(){
		//$("#inputForm").attr("action", "craft!close.action");
		//$("#inputForm").submit(); 		
		 var dt = $("#inputForm").serialize();
			var url = "craft!creditclose.action";		
			credit.creditCard(url,function(data){
				$.message(data.status,data.message);
				window.location.href = "craft!list.action";
			},dt)
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
	
	
	
	// 人员弹出框
	$("#repair").click( function() {
		showRepair();
	})
	
	
	function showRepair()
{
	var title = "选择人员";
	var width="800px";
	var height="632px";
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


