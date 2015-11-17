$(function() {
	
	var $callRing = $("#callRing");// 呼叫
	$callRing.click(function() {
		alert("in");
			window.location.href = "abnormal!add.action";		
    });
	
	
	var $modelrep = $("#modelrep");// 工模
	$modelrep.click(function() {		
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
	
	var $craftrep = $("#craftrep");// 工艺
	$craftrep.click(function() {		
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');		
		if(i.length<=0){
			alert("请选择一个异常");
			return false;
		}else if(i.length>1){
			alert("请选择一个异常");
			return false;
		}else{
			window.location.href = "craft!add.action?aid="+i;
		}
		
    });
	
	var $responsecar = $("#responsecar");// 响应刷卡cancelcal
	$responsecar.click(function() {
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
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');
		if(i.length<=0){
			alert("请至少选择一个异常");
			return false;
		}else{
			window.location.href = "abnormal!update.action?cancelId="+i;
		}		
    });
	
	var $closeAbn = $("#closeAbn");// 关闭异常
	$closeAbn.click(function() {
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');
		if(i.length<=0){
			alert("请至少选择一个异常");
			return false;
		}else{
			var rowData = $("#grid-table1").jqGrid('getRowData',i);
			var rowName=rowData.workShopName;
			window.location.href = "abnormal!update.action?closeId="+i;
		}		
    });
})