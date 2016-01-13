$(function() {			   
	
	$("#completeQuality").click(function(){		
		var choice = $("#productChoice").val();
		var bom = $("#productNa").val();
		if(choice.length>0 && bom.length>0){
			alert("产品与产品Bom只能选择一个");
			return false;
		}
    	$("#inputForm").submit();  	
	});	
	
	
	
	$("#checkQuality").click(function(){
		/*$("#inputForm").attr("action", "quality!creditreply.action");
	   $("#inputForm").submit(); 	*/
	   var dt = $("#inputForm").serialize();
		var url = "quality!creditreply.action";				
		credit.creditCard(url,function(data){
					if(data.status=="success"){
						layer.alert(data.message, {icon: 6},function(){
							window.location.href="quality!list.action";
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
	
	$("#closeQuality").click(function(){
		//$("#inputForm").attr("action", "quality!close.action");
		//$("#inputForm").submit(); 
		 var dt = $("#inputForm").serialize();
			var url = "quality!creditclose.action";		
			credit.creditCard(url,function(data){
					if(data.status=="success"){
						layer.alert(data.message, {icon: 6},function(){
							window.location.href="quality!list.action";
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
	
	
	$("#returnQuality").click(function(){
		window.history.back();
	});
	
	
	
	// 增加选项内容输入框
	$("#addImage").click( function() {
		addAttributeOptionTr();
	})
	
	function addAttributeOptionTr() {
		var size=$(".zg").length;
		var attributeOptionTrHtml = '<tr class="zg"><td><input type="hidden" name="flowingId" value=""/><textarea name="flowingRectify.content"	style="width:600px;" class="text"></textarea>&nbsp;&nbsp;&nbsp;<a class="save" style="cursor:pointer">刷卡保存</a>&nbsp;&nbsp;<a style="cursor:pointer" class="edit">编辑</a>&nbsp;&nbsp;<a class="deleteButton" style="cursor:pointer">删除</a></div></td></tr>';
		if($(".zg").length >= 0) {
			$(".zg:last").after(attributeOptionTrHtml);
		} 
	}
	
			
	// 减少选项内容输入框
	$("#removeImage").click( function() {
		removeAttributeOptionTr();
	})
	
	function removeAttributeOptionTr() {
		if($(".zg").length > 1) {
			$(".zg:last").remove();
		} else {
			alert("请至少保留一个选项!");
		}
	}	
	
	
	// 产品弹出框
	$("#productId").click( function() {
		showProduct();
	})
	
	
	function showProduct()
{
	var title = "选择产品";
	var width="800px";
	var height="632px";
	var content="quality!browser.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		//$("#productName1").val(id[0]);产品名称
		$("#productName1").html(id[0]);//组件名称
		$("#productNa").val(id[1]);//组件编码

		/*url="process_route!getProcessList.action?id="+id[2];
		  $.ajax({
				url: url,
				//data: ids,
				dataType: "json",		
				success: function(data) {
					$("#processName").html("");
					$(data).each(function(n){
			            $("<option/>").html(this.name).val(this.id)
			            .appendTo("#processName");
			        });
				}
			});	*/
		  
	
		layer.close(index); 
	});
}
	
	
	
	// 人员弹出框1
	$("#receive").click( function() {
		showReceive();
	})
	
	
	function showReceive()
{
	var title = "选择人员";
	var width="800px";
	var height="500px";
	var content="quality!receive.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#receiveName1").text(id[0]);
		$("#receiveNa").val(id[1]);//
		layer.close(index); 
	});
}
	
	
	
	// 人员弹出框2
	$("#receive1").click( function() {
		showReceive1();
	})
	
	
	function showReceive1()
{
	var title = "选择人员";
	var width="800px";
	var height="500px";
	var content="quality!engineer.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#receiveName2").text(id[0]);
		$("#receiveNa1").val(id[1]);//
		layer.close(index); 
	});
}

})


