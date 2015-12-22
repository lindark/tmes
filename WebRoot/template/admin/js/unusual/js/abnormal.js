$(function() {
	
	
	var $callRing = $("#callRing");// 呼叫
	$callRing.click(function() {
			window.location.href = "abnormal!add.action";		
    });
	
	
	var $modelrep = $("#modelrep");// 工模
	$modelrep.click(function() {		

		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');		
		var rowData = $("#grid-table1").jqGrid('getRowData',i);
		if(i.length<=0){
			alert("请选择一个异常");
			return false;
		}else if(i.length>1){
			alert("请选择一个异常");
			return false;
		}else{
			
		    if(rowData.state!=3 & rowData.state!=4){
			  window.location.href = "model!add.action?aid="+i;
			}else{
				alert("当前状态不能开单");
			}
		}
		
    });
	
	var $qualityque = $("#qualityque");// 质量
	$qualityque.click(function() {
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');
		var rowData = $("#grid-table1").jqGrid('getRowData',i);
		
		if(i.length<=0){
			alert("请选择一个异常");
			return false;
		}else if(i.length>1){
			alert("请选择一个异常");
			return false;
		}else{
			if(rowData.state!=3 & rowData.state!=4){
				window.location.href = "quality!add.action?aid="+i;
			}else{
				alert("当前状态不能开单");
			}			
		}
		
    });
	
	var $craftrep = $("#craftrep");// 工艺
	$craftrep.click(function() {		
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');		
		var rowData = $("#grid-table1").jqGrid('getRowData',i);
		if(i.length<=0){
			alert("请选择一个异常");
			return false;
		}else if(i.length>1){
			alert("请选择一个异常");
			return false;
		}else{
			if(rowData.state!=3 & rowData.state!=4){
			window.location.href = "craft!add.action?aid="+i;
			}else{
				alert("当前状态不能开单");
			}
		}
		
    });
	
	
	var $devicerep = $("#devicerep");//设备
	$devicerep.click(function() {		
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');		
		var rowData = $("#grid-table1").jqGrid('getRowData',i);
		if(i.length<=0){
			alert("请选择一个异常");
			return false;
		}else if(i.length>1){
			alert("请选择一个异常");
			return false;
		}else{
			if(rowData.state!=3 & rowData.state!=4){
			window.location.href = "device!add.action?aid="+i;
			}else{
				alert("当前状态不能开单");
			}
		}
		
    });
	
	var $responsecar = $("#responsecar");// 响应刷卡cancelcal
	$responsecar.click(function() {
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');
		if(i.length<=0){
			alert("请至少选择一个异常");
			return false;
		}else{
			for (var a=0;a<i.length;a++){
				var rowData = $("#grid-table1").jqGrid('getRowData',i[a]);
				if(rowData.state!=0 & rowData.state!=1){				
					alert("异常不能响应");
					return false;
				}
			}
			
			//var dt = $("#inputForm").serialize();
			var ids=i;
			/*var url = "abnormal!creditresponse1.action";		
			credit.creditCard(url,function(data){
				$.message(data.status,data.message);
				window.location.href = "abnormal!list.action";
			},ids)*/
			window.location.href = "abnormal!creditresponse1.action?ids="+i;
		}
		
    });
	
	var $cancelcal = $("#cancelcal");// 撤销呼叫
	$cancelcal.click(function() {
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');
		if(i.length<=0){
			alert("请至少选择一个异常");
			return false;
		}else{
			for (var a=0;a<i.length;a++){
				var rowData = $("#grid-table1").jqGrid('getRowData',i[a]);
				if(rowData.state==3 || rowData.state==4 || rowData.state==2){				
					alert("异常已关闭/撤销/正处理");
					return false;
				}
			}
			var cancelIds=i;
			var url = "abnormal!creditundo.action";		
			credit.creditCard(url,function(data){
				$.message(data.status,data.message);
				window.location.href = "abnormal!list.action";
			},cancelIds)
			//window.location.href = "abnormal!creditundo.action?cancelIds="+i;
		}
				
    });
	
	var $closeAbn = $("#closeAbn");// 关闭异常
	$closeAbn.click(function() {
		var i=$("#grid-table1").jqGrid('getGridParam','selarrrow');
		if(i.length<=0){
			alert("请至少选择一个异常");
			return false;
		}else{
			for (var a=0;a<i.length;a++){
				var rowData = $("#grid-table1").jqGrid('getRowData',i[a]);
				if(rowData.state==3 || rowData.state==4){				
					alert("异常已关闭/撤销");
					return false;
				}
			}
			var closeIds=i;
			var url = "abnormal!creditclose.action";		
			credit.creditCard(url,function(data){
				$.message(data.status,data.message);
				window.location.href = "abnormal!list.action";
			},closeIds)
			//window.location.href = "abnormal!creditclose.action?closeIds="+i;
		}
    });
	
})