$(function() {
	
	var $callRing = $("#callRing");// 呼叫
	$callRing.click(function() {
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
	
	
	var $devicerep = $("#devicerep");//设备
	$devicerep.click(function() {		
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');		
		if(i.length<=0){
			alert("请选择一个异常");
			return false;
		}else if(i.length>1){
			alert("请选择一个异常");
			return false;
		}else{
			window.location.href = "device!add.action?aid="+i;
		}
		
    });
	
	var $responsecar = $("#responsecar");// 响应刷卡cancelcal
	$responsecar.click(function() {
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');
		if(i.length<=0){
			alert("请至少选择一个异常");
			return false;
		}else if(i.length==1){	
			var rowData = $("#grid-table1").jqGrid('getRowData',i);
			if(rowData.state==0 || rowData.state==1){				
				window.location.href = "abnormal!update.action?aid="+i;
			}else{
				alert("异常不能响应");
				return false;
			}
			
		}else if(i.length>=2){
			for (var a=0;a<i.length;a++){
				var rowData = $("#grid-table1").jqGrid('getRowData',i[a]);
				if(rowData.state!=0){				
					alert("异常不能响应");
					return false;
				}
			}
			window.location.href = "abnormal!update.action?aids="+i;
		}
		
    });
	
	var $cancelcal = $("#cancelcal");// 撤销呼叫
	$cancelcal.click(function() {
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');
		if(i.length<=0){
			alert("请至少选择一个异常");
			return false;
		}else if(i.length==1){	
			var rowData = $("#grid-table1").jqGrid('getRowData',i);
			if(rowData.state==0 || rowData.state==1){
			  window.location.href = "abnormal!update.action?cancelId="+i;
			}else{
				alert("异常不能撤销");
			}
		}else if(i.length>=2){
			for (var a=0;a<i.length;a++){
				var rowData = $("#grid-table1").jqGrid('getRowData',i[a]);
				if(rowData.state!=0){				
					alert("异常不能撤销");
					return false;
				}
			}
			window.location.href = "abnormal!update.action?cancelIds="+i;
		}		
    });
	
	var $closeAbn = $("#closeAbn");// 关闭异常
	$closeAbn.click(function() {
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');
		if(i.length<=0){
			alert("请至少选择一个异常");
			return false;
		}else if(i.length==1){
			var rowData = $("#grid-table1").jqGrid('getRowData',i);
			if(rowData.state!=3 && rowData.state!=4){
				window.location.href = "abnormal!update.action?closeId="+i;
			}else{
				alert("异常已关闭/撤销");
			}
			
		}else if(i.length>=2){
			for (var a=0;a<i.length;a++){
				var rowData = $("#grid-table1").jqGrid('getRowData',i[a]);
				if(rowData.state==3 || rowData.state==4){				
					alert("异常已关闭/撤销");
					return false;
				}
			}
			window.location.href = "abnormal!update.action?closeIds="+i;
		}		
    });
})