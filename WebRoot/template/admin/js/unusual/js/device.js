$(function() {	
	
	$("#completeDevice").click(function(){		
    	$("#inputForm").submit();  		    		
	});
	
	
	$("#checkDevice").click(function(){
		var dt = $("#inputForm").serialize();
		var url = "device!creditreply.action";		
		
	    credit.creditCard(url,function(data){
			if(data.status=="success"){
				layer.alert(data.message, {icon: 6},function(){
					window.location.href="device!list.action";
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
	
	$("#closeDevice").click(function(){ 		
		var dt = $("#inputForm").serialize();
		var url = "device!creditclose.action";		
		
		credit.creditCard(url,function(data){
			if(data.status=="success"){
				layer.alert(data.message, {icon: 6},function(){
					window.location.href="device!list.action";
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
		$("#equipNo").val(id[2]);
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
	var height="500px";
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
		var html="<div style='padding:3px;'><span style='padding:3px;'>"+(size+1)+"."+"</span><input name='deviceStepSet["+size+"].vornr' style='width:300px;' class='input input-sm'/><img src='/template/admin/images/input_delete_icon.gif' class='deleteImage' style='cursor: pointer;padding:6px;' alt='删除'>";
        html +="</div>";
        
        $(".deleteImage").live("click",function(){
    		$(this).parent().remove();
    	})  
    	
        $("#process").append(html);
	}
	
	

})


