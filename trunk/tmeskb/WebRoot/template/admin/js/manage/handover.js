$(function(){
	var $maclick = $(".maclick"); //随工单
	var $btnsubmit = $(".btnsubmit");//刷卡提交
	var $btnapproval = $(".btnapproval");//刷卡确认
	
	
	$maclick.click(function(){
		var shift = $("#sl_sh").val();
		if(shift==""){
			layer.alert("请选择班次");
			return false;
		}
		var materialCode = $(this).find(".materialCode").text();
		var materialName = $(this).find(".materialName").text();
		var active = $(".step-jump.active").find(".process").val();
		var nowdate = $("#productDate").val();
		
		var title = "工序交接";
		//var widths = $(window).width();
		//var heights = $(window).height();
		//var width=widths-189+"px";
		//var height=heights-86+"px";
		var width="950px";
		var height="500px";
		var content="hand_over_process!add.action?materialCode="+materialCode+"&materialName="+materialName+"&processid="+active+"&nowDate="+nowdate+"&shift="+shift;
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
	        btn:['刷卡提交',"测试",'刷卡确认','取消'],
	        area: [width, height],//弹出框的高度，宽度
	        content:content,
//	        yes:function(index,layero){//刷卡保存
//	        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
//	        	var docu = iframeWin.document;//获取document 对象
//	        	var url = "hand_over_process!creditsave.action";
//	        	var dt = $(docu).find("#inputForm").serialize();
//	        	credit.creditCard(url,function(data){
//	    			$.message(data.status,data.message);
//	    			
//	    		},dt)
//	        	return false;
//	        },
	        yes:function(index,layero){//刷卡提交
	        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
	        	var docu = iframeWin.document;//获取document 对象
	        	var $afterworkingBillCode = $(docu).find(".afterworkingBillCode");
	        	var flag = true;
	        	for(var i=0;i<$afterworkingBillCode.length;i++){
					if($afterworkingBillCode.eq(i).val()==""){
						alert("数据错误,无法查询到下一班随工单,或手动填写随工单");
						flag = false;
						return false;
					}
				}
	        	
	        	if(flag){
	        		for(var i=0;i<$afterworkingBillCode.length;i++){
		        		var workingCode  = $afterworkingBillCode.eq(i).parent().prev().prev().prev().text();
		        		if(workingCode == $afterworkingBillCode.eq(i).val()){
		        			alert("数据错误,下一班随工单不允许与上班随工单一致");
		        			flag = false;
		        			return false;
		        		}
		        	}
	        	}
	        	if(flag){
	        		var url = "hand_over_process!creditsubmit.action";
		        	var dt = $(docu).find("#inputForm").serialize();
		        	credit.creditCard(url,function(data){
		    			$.message(data.status,data.message);
		    			iframeWin.reloadwin();
		    			//iframeWin.location.reload();
		    		},dt)
	        	}
	        	return false;
	        },
	        btn2:function(index,layero){
	        	return false;
	        },
	        btn3:function(index,layero){//刷卡确认
	        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
	        	var docu = iframeWin.document;//获取document 对象
	        	var $afterworkingBillCode = $(docu).find(".afterworkingBillCode");
	        	var flag = true;
	        	
	        	for(var i=0;i<$afterworkingBillCode.length;i++){
					if($afterworkingBillCode.eq(i).val()==""){
						alert("数据错误,无法查询到下一班随工单,或手动填写随工单");
						flag = false;
						return false;
					}
				}
	        	if(flag){
	        		for(var i=0;i<$afterworkingBillCode.length;i++){
		        		var workingCode  = $afterworkingBillCode.eq(i).parent().prev().prev().prev().text();
		        		if(workingCode == $afterworkingBillCode.eq(i).val()){
		        			alert("数据错误,下一班随工单不允许与上班随工单一致");
		        			flag = false;
		        			return false;
		        		}
		        	}
	        	}
	        	if(flag){
	        		var url = "hand_over_process!creditapproval.action";
		        	var dt = $(docu).find("#inputForm").serialize();
		        	credit.creditCard(url,function(data){
		    			$.message(data.status,data.message);
		    			iframeWin.location.reload();
		    		},dt)
	        	}
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