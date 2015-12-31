$(function(){
	var $maclick = $(".maclick"); //随工单
	var $btnsubmit = $(".btnsubmit");//刷卡提交
	var $btnapproval = $(".btnapproval");//刷卡确认
	
	
	$maclick.click(function(){
		var materialCode = $(this).find(".materialCode").text();
		var materialName = $(this).find(".materialName").text();
		var active = $(".step-jump.active").find(".process").val();
		var title = "工序交接";
		//var widths = $(window).width();
		//var heights = $(window).height();
		//var width=widths-189+"px";
		//var height=heights-86+"px";
		var width="800px";
		var height="400px";
		var content="hand_over_process!add.action?materialCode="+materialCode+"&materialName="+materialName+"&processid="+active;
		layer.open({
	        type: 2,
	        skin: 'layui-layer-lan',
	        shift:2,
	        //offset:["86px","189px"],
	        title: title,
	        fix: false,
	        shadeClose: true,
	        maxmin: true,
	        scrollbar: false,
	        btn:['刷卡保存','刷卡提交','刷卡确认','取消'],
	        area: [width, height],//弹出框的高度，宽度
	        content:content,
	        yes:function(index,layero){//刷卡保存
	        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
	        	var docu = iframeWin.document;//获取document 对象
	        	var url = "hand_over_process!creditsave.action";
	        	var dt = $(docu).find("#inputForm").serialize();
	        	credit.creditCard(url,function(data){
	    			$.message(data.status,data.message);
	    			
	    		},dt)
	        	return false;
	        },
	        btn2:function(index,layero){//刷卡提交
	        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
	        	var docu = iframeWin.document;//获取document 对象
	        	var url = "hand_over_process!creditsubmit.action";
	        	var dt = $(docu).find("#inputForm").serialize();
	        	credit.creditCard(url,function(data){
	    			$.message(data.status,data.message);
	    			
	    		},dt)
	        	return false;
	        },
	        btn3:function(index,layero){//刷卡确认
	        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
	        	var docu = iframeWin.document;//获取document 对象
	        	var url = "hand_over_process!creditapproval.action";
	        	var dt = $(docu).find("#inputForm").serialize();
	        	credit.creditCard(url,function(data){
	    			$.message(data.status,data.message);
	    			
	    		},dt)
	        	return false;
	        }

	    });
		
	});
	/*
	$btnsubmit.click(function(){//刷卡提交
		window.location.href="hand_over_process!submit.action";
		
	})
	*/
})