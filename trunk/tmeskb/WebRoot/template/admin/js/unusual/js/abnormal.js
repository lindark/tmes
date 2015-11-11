$(function() {
	var $modelrep = $("#modelrep");// 工模
	$modelrep.click(function() {		
		alert("y");
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');		
		if(i.length<=0){
			alert("请选择一个异常");
			return false;
		}else if(i.length>1){
			alert("请选择一个异常");
			return false;
		}else{
			window.location.href = "model!add.action?aid="+i;
		}
		
    });
	
	var $qualityque = $("#qualityque");// 质量
	$qualityque.click(function() {
		alert("e");
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');
		
		if(i.length<=0){
			alert("请选择一个异常");
			return false;
		}else if(i.length>1){
			alert("请选择一个异常");
			return false;
		}else{
			window.location.href = "quality!add.action?aid="+i;
		}
		
    });
	
	var $responsecar = $("#responsecar");// 响应刷卡cancelcal
	$responsecar.click(function() {
		alert("f");
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');
		if(i.length<=0){
			alert("请至少选择一个异常");
			return false;
		}else{
			window.location.href = "abnormal!update.action?aid="+i;
		}
		
    });
	
	var $cancelcal = $("#cancelcal");// 撤销呼叫
	$cancelcal.click(function() {
		alert("o");
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');
		if(i.length<=0){
			alert("请至少选择一个异常");
			return false;
		}else{
			window.location.href = "abnormal!update.action?cancelId="+i;
		}		
    });
})