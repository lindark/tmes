$(function() {	
	//长期预防措施
	$("#longPrevent").click( function() {
		showPrevent();
	})
	
	
	function showPrevent(){
	var title = "选择长期预防措施";
	var width="600px";
	var height="160px";
	var content="model!prevent.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		var size=$(".deleteImage2").length;

		$(".deleteImage2").live("click",function(){
			$(this).parent().remove();
		})
		
        var html="<div><span>"+id[0]+"</span>&nbsp;&nbsp;<img src='/template/admin/images/input_delete_icon.gif' style='cursor: pointer;' class='deleteImage2' alt='删除'>";
            html+="<input type='hidden' name='longSet["+size+"].id' value='"+id[1]+"'/>";
            html +="</div>";
        $("#prevent").append(html);
		layer.close(index); 
	});
}
	
	
	//处理方法与结果
	$("#handleResult").click( function() {
		showResult();
	})
	
	
	function showResult()
{
	var title = "选择处理方法";
	var width="480px";
	var height="160px";
	var content="model!handle.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		var size=$(".deleteImage1").length;
		$(".deleteImage1").live("click",function(){
			$(this).parent().remove();
		})
		
        var html="<div><span>"+id[0]+"</span>&nbsp;&nbsp;<img src='/template/admin/images/input_delete_icon.gif' style='cursor: pointer;' class='deleteImage1' alt='删除'>";
            html+="<input type='hidden' name='handleSet["+size+"].id' value='"+id[1]+"'/>";
            html +="</div>";
        $("#means").append(html);
		layer.close(index); 
	});
}

	
	// 故障原因
	$("#faultReason").click( function() {
		showReason();
	})
	
	
	function showReason()
{
	var title = "选择故障原因";
	var width="420px";
	var height="160px";
	var content="model!reason.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		var size=$(".deleteImage").length;

		$(".deleteImage").live("click",function(){
			$(this).parent().remove();
		})

        var html="<div><span>"+id[0]+"</span>&nbsp;&nbsp;<img src='/template/admin/images/input_delete_icon.gif' class='deleteImage' style='cursor: pointer;' alt='删除'>";
            html+="<input type='hidden' name='faultReasonSet["+size+"].id' value='"+id[1]+"'/>";
            html +="</div>";
        $("#reason").append(html);
		layer.close(index); 
	});
}
	
	
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
		layer.close(index); 
	});
}
	
	
	// 人员弹出框
	$("#repairId").click( function() {
		showRepair();
	})
	
	
	function showRepair()
{
	var title = "选择人员";
	var width="800px";
	var height="632px";
	var content="model!repair.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#repairName1").text(id[0]);
		$("#repairNa").val(id[1]);//名称*/
		layer.close(index); 
	});
}
	
	
	// 人员弹出框
	$("#insepectorId").click( function() {
		showInsepector();
	})
	
	
	function showInsepector()
{
	var title = "选择人员";
	var width="800px";
	var height="632px";
	var content="model!insepector.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#insepectorName1").text(id[0]);
		$("#insepectorNa").val(id[1]);//名称*/
		layer.close(index); 
	});
}
	

})


