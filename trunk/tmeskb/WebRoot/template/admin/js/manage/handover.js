$(function(){
	var $maclick = $(".maclick"); //随工单
	
	
	
	$maclick.click(function(){
		var materialCode = $(this).find(".materialCode").text();
		var title = "工序交接";
		var width="800px";
		var height="400px";
		var content="hand_over_process!add.action?matnr="+materialCode;
		var wizard = $('#fuelux-wizard').data('wizard');
		//alert(wizard.stepclicked());
		layer.open({
	        type: 2,
	        //skin: 'layui-layer-lan',
	        title: title,
	        fix: false,
	        shadeClose: false,
	        maxmin: true,
	        scrollbar: false,
	        btn:['保存','取消'],
	        area: [width, height],//弹出框的高度，宽度
	        content:content,
	        yes:function(index,layero){
	        	alert("??");
	        }

	    });
		
	});
})