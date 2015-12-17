$(function() {	
	
	$("#completeDevice").click(function(){		
    	$("#inputForm").submit();  		    		
	});
	
	
	$("#checkDevice").click(function(){
		//$("#inputForm").attr("action", "device!check.action");
	   // $("#inputForm").submit(); 
		var dt = $("#inputForm").serialize();
		var url = "device!creditreply.action";		
		credit.creditCard(url,function(data){
			$.message(data.status,data.message);
			window.location.href = "device!list.action";
		},dt)
	});
	
	$("#closeDevice").click(function(){
		//$("#inputForm").attr("action", "device!close.action");
		//$("#inputForm").submit(); 		
		var dt = $("#inputForm").serialize();
		var url = "device!creditclose.action";		
		credit.creditCard(url,function(data){
			$.message(data.status,data.message);
			window.location.href = "device!list.action";
		},dt)
	});
	
	
	$("#returnDevice").click(function(){
		window.history.back();
	});
	
	
	
	
	// 设备弹出框
	$("#deviceId").click( function() {
		showDevice();
	})
	
	
	function showDevice()
{
	var title = "选择设备";
	var width="800px";
	var height="632px";
	var content="device!browser.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		//$("#productName1").val(id[0]);//产品id
		$("#deviceName1").text(id[0]);
		$("#deviceNa").val(id[1]);//产品名称
		layer.close(index); 
	});
}
	
	
	
	// 车间弹出框
	$("#workShopId").click( function() {
		showWorkShop();
	})
	
	
	function showWorkShop()
{
	var title = "选择车间";
	var width="800px";
	var height="632px";
	var content="device!workshop.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#workShopName1").text(id[0]);
		$("#workShopNa").val(id[1]);//产品名称
		layer.close(index); 
	});
}
	
	
	// 人员弹出框
	$("#adminId").click( function() {
		showPerson();
	})
	
	
	function showPerson()
{
	var title = "选择人员";
	var width="800px";
	var height="632px";
	var content="device!person.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#adminName1").text(id[0]);
		$("#adminNa").val(id[1]);//产品名称*/
		layer.close(index); 
	});
}
	
	
	// 增加选项内容输入框
	$("#faultProcess").click( function() {
		addAttributeOptionTr();
	})
	
	function addAttributeOptionTr() {
		var size=$(".deleteImage").length;
		//var attributeOptionTrHtml = '<tr class="zg"><td><input type="hidden" name="flowingId" value=""/><textarea name="flowingRectify.content"	style="width:600px;" class="text"></textarea>&nbsp;&nbsp;&nbsp;<a class="save" style="cursor:pointer">保存</a>&nbsp;&nbsp;<a style="cursor:pointer" class="edit">编辑</a>&nbsp;&nbsp;<a class="deleteButton" style="cursor:pointer">删除</a></div></td></tr>';
		var html="<div><span>"+size+"</span>&nbsp;&nbsp;<img src='/template/admin/images/input_delete_icon.gif' class='deleteImage' style='cursor: pointer;' alt='删除'>";
        html+="<input type='hidden' name='deviceProcessSet["+size+"].content' value=''/>";
        html +="</div>";
        $("#process").append(html);
		/*if($(".zg").length >= 0) {
			$(".zg:last").after(attributeOptionTrHtml);
		} */
	}
	
	$(".deleteImage").live("click",function(){
		$(this).parent().remove();
	})  
	
	/*
	// 增加选项内容输入框
	$("#addImage").click( function() {
		addAttributeOptionTr();
	})
	
	function addAttributeOptionTr() {
		var size=$(".zg").length;
		var attributeOptionTrHtml = '<tr class="zg"><td><input type="hidden" name="flowingId" value=""/><textarea name="flowingRectify.content"	style="width:600px;" class="text"></textarea>&nbsp;&nbsp;&nbsp;<a class="save" style="cursor:pointer">保存</a>&nbsp;&nbsp;<a style="cursor:pointer" class="edit">编辑</a>&nbsp;&nbsp;<a class="deleteButton" style="cursor:pointer">删除</a></div></td></tr>';
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
	
	*/

})


