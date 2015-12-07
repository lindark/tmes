
$(function() {
	var $storage = $("#storage");// 入库
	var $dump = $("#dump");// 转储
	var $repair = $("#repair");// 返修
	var $repairin = $("#repairin");// 返修收货
	var $pick = $("#pick");// 领退料
	var $qResponse = $("#qResponse");// 快速响应
	var $handoverprocess = $("#handoverprocess");// 交接
	var $dailywork = $("#dailywork");// 报工
	var $carton = $("#carton");// 纸箱收货
	var $rework = $("#rework");// 返工
	var $sample=$("#sample");//抽检
	var $halfinspection=$("#halfinspection");//半成品巡检
	var $pollingtest = $("#pollingtest");//巡检
	var $scrap=$("#scrap");//报废
	var $ckbox = $(".ckbox");//checkbox
	
	var init = {
			"isCheck":function(){//需要有选中来改变按钮的属性的
				var cklength = $(".ckbox:checked").length;
				if(cklength > 1){
					layer.alert('请选择一条随工单', {
					    skin: 'layui-layer-molv' //样式类名
					    ,closeBtn: 0
					});
					return false;
				}else if(cklength < 1){
					layer.alert('请选择一条随工单', {
					    skin: 'layui-layer-molv' //样式类名
					    ,closeBtn: 0
					});
					return false;
				}else{
					return true;
				}
				
				
			},
			"notCheck":function(){//不需要选中就可以使用
				return true;
			}
	}
	
	/**
	 * checkbox 选中
	 */
	$ckbox.click(function(){
		var flag = $ckbox.is(":checked");
		if(flag){//有选中的
			$storage.removeClass("disabled");
			$repair.removeClass("disabled");
			$repairin.removeClass("disabled");
			$pick.removeClass("disabled");
			$dailywork.removeClass("disabled");
			$carton.removeClass("disabled");
			$rework.removeClass("disabled");
			$sample.removeClass("disabled");
			$halfinspection.removeClass("disabled");
			$pollingtest.removeClass("disabled");
			$scrap.removeClass("disabled");
		}else{//未选中
			$storage.addClass("disabled");
			$repair.addClass("disabled");
			$repairin.addClass("disabled");
			$pick.addClass("disabled");
			$dailywork.addClass("disabled");
			$carton.addClass("disabled");
			$rework.addClass("disabled");
			$sample.addClass("disabled");
			$halfinspection.addClass("disabled");
			$pollingtest.addClass("disabled");
			$scrap.addClass("disabled");
		}
	});
	

	/**
	 * 入库按钮点击
	 */
	$storage
			.click(function() {
				var istrue = init.isCheck();
				if (istrue) {
					var id = getCKboxById();
					window.location.href = "enteringware_house!list.action?workingBillId="
							+ id;
				}

			});

	/**
	 * 领/退料按钮点击
	 */
	$pick.click(function() {
		var istrue = init.isCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "pick!list.action?workingBillId=" + id;
		}

	});
	
	
	/**
	 * 半成品巡检按钮点击
	 */
	$halfinspection.click(function() {
		var istrue = init.isCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "itermediate_test!list.action?workingBillId=" + id;
		}

	});
	
	/**
	 * 领/退料按钮点击
	 */
	$rework.click(function() {
		var istrue = init.isCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "rework!list.action?workingBillId=" + id;
		}

	});

	/**
	 * 交接按钮点击
	 */
	$handoverprocess.click(function() {
		var istrue = init.notCheck();
		if (istrue) {
			window.location.href = "hand_over_process!list.action";
		}
	});

	/**
	 * 转储按钮点击
	 */
	$dump.click(function() {
		var istrue = init.notCheck();
		if (istrue) {
			window.location.href = "dump!list.action";
		}
	});
	/**
	 * 纸箱收货按钮点击
	 */
	$carton.click(function() {
		var istrue = init.isCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "carton!list.action?workingBillId=" + id;
		}

	});
	/**
	 * 报工按钮点击
	 */
	$dailywork.click(function() {
		var istrue = init.isCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "daily_work!list.action?workingBillId=" + id;
		}

	});
	/**
	 * 返修按钮点击
	 */
	$repair.click(function() {
		var istrue = init.isCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "repair!list.action?workingBillId=" + id;
		}
	});
	/**
	 * 返修收货按钮点击
	 */
	$repairin.click(function() {
		var istrue = init.isCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "repairin!list.action?workingBillId=" + id;
		}
	});
	
	/**
	 * 巡检按钮点击
	 */
	$pollingtest.click(function() {
		var istrue = init.isCheck();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "pollingtest!list.action?workingBillId=" + id;
		}
	});

	/**
	 * 快速响应按钮点击
	 */
	$qResponse.click(function() {
		var istrue = init.notCheck();
		if (istrue) {
			window.location.href = "abnormal!list.action";
		}
	});

	/**
	 * 抽检
	 */
	$sample.click(function(){
		var istrue=init.isCheck();
		if(istrue)
		{
			var id = getCKboxById();
			window.location.href = "sample!list.action?wbId=" + id;
		}
	});
	
	/**
	 * 报废
	 */
	$scrap.click(function(){
		var istrue=init.isCheck();
		if(istrue)
		{
			var id = getCKboxById();
			window.location.href = "scrap!list.action?wbId=" + id;
		} 
	});
})

/**
 * 获取checkbox的ID值
 */
function getCKboxById() {
	var id = "";
	var ishead = 0;
	$(".ckbox:checked").each(function() {
		var sId = $(this).val();
		if (ishead == 1)
			id += "," + sId;
		else
			id = sId;
		ishead = 1;
	});
	return id;
}
