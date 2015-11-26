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
	var $halfinspection=$("#halfinspection");//半成品巡检
	var $pollingtest = $("#pollingtest");//巡检

	/**
	 * 入库按钮点击
	 */
	$storage
			.click(function() {
				var istrue = ckboxChick();
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
		var istrue = ckboxChick();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "pick!list.action?workingBillId=" + id;
		}

	});
	
	
	/**
	 * 半成品巡检按钮点击
	 */
	$halfinspection.click(function() {
		var istrue = ckboxChick();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "itermediate_test!list.action?workingBillId=" + id;
		}

	});
	
	/**
	 * 领/退料按钮点击
	 */
	$rework.click(function() {
		var istrue = ckboxChick();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "rework!list.action?workingBillId=" + id;
		}

	});

	/**
	 * 交接按钮点击
	 */
	$handoverprocess.click(function() {
		window.location.href = "hand_over_process!list.action";
	});

	/**
	 * 转储按钮点击
	 */
	$dump.click(function() {
		window.location.href = "dump!list.action";
	});
	/**
	 * 纸箱收货按钮点击
	 */
	$carton.click(function() {
		var istrue = ckboxChick();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "carton!list.action?workingBillId=" + id;
		}

	});
	/**
	 * 报工按钮点击
	 */
	$dailywork.click(function() {
		var istrue = ckboxChick();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "daily_work!list.action?workingBillId=" + id;
		}

	});
	/**
	 * 返修按钮点击
	 */
	$repair.click(function() {
		var istrue = ckboxChick();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "repair!list.action?workingBillId=" + id;
		}
	});
	/**
	 * 返修收货按钮点击
	 */
	$repairin.click(function() {
		var istrue = ckboxChick();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "repairin!list.action?workingBillId=" + id;
		}
	});
]	
	/**
	 * 巡检按钮点击
	 */
	$pollingtest.click(function() {
		var istrue = ckboxChick();
		if (istrue) {
			var id = getCKboxById();
			window.location.href = "pollingtest!list.action?workingBillId=" + id;
		}
	});
	
	/**
	 * 快速响应按钮点击
	 */
	$qResponse.click(function() {
		window.location.href = "abnormal!list.action";
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
	alert(id);
	return id;
}

/**
 * 检查checkbox 勾选的数量,如果是1 返回 true,如果是0,alert 请勾选随工单 返回false, 如果大于1 alert 请勾选一项
 * 返回false
 */
function ckboxChick() {
	var ckLen = $(".ckbox:checked").length;
	if (ckLen <= 0) {
		alert("请选择随工单");
		return false;
	} else if (ckLen > 1) {
		alert("请选择一条随工单");
		return false;
	} else {
		return true;
	}
}