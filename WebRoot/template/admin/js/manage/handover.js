$(function(){
	var $maclick = $(".maclick"); //随工单
	var $btnsubmit = $(".btnsubmit");//刷卡提交
	var $btnapproval = $(".btnapproval");//刷卡确认
	
	
	$maclick.click(function(){
		var materialCode = $(this).find(".materialCode").text();
		var active = $(".step-jump.active").find(".process").val();
		var title = "工序交接";
		var width="800px";
		var height="400px";
		var content="hand_over_process!add.action?materialCode="+materialCode+"&processid="+active;
		layer.open({
	        type: 2,
	        skin: 'layui-layer-lan',
	        title: title,
	        fix: false,
	        shadeClose: false,
	        maxmin: true,
	        scrollbar: false,
	        btn:['刷卡保存','刷卡提交','刷卡确认','取消'],
	        area: [width, height],//弹出框的高度，宽度
	        content:content,
	        yes:function(index,layero){//刷卡保存
	        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
	        	var docu = iframeWin.document;//获取document 对象
	        	$(docu).find("#submit_btn").trigger("click");//点击ifream里面的提交按钮
	        },
	        cancel:function(){//刷卡提交
//	        	layer.msg("请刷卡",{
//	        		icon:16,
//	        		shade:0.3,
//	        		time:60000 //60秒
//	        	},function(){
//	        		alert("60秒未操作，关闭");
//	        	});
	        	return false;
	        },
	        btn3:function(){//刷卡确认
	        	return false;
	        }

	    });
		
	});
	
	$btnsubmit.click(function(){//刷卡提交
		window.location.href="hand_over_process!submit.action";
		
	})
})