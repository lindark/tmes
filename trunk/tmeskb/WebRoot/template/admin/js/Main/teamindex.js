$(function(){
	var $storage = $("#storage");//入库
	/**
	 * 入库按钮点击
	 */
	$storage.click(function(){
		var istrue = ckboxChick();
		if(istrue)
			window.location.href="enteringware_house!list.action";
	});
})

/**
 * 检查checkbox 勾选的数量,如果是1 返回 true,如果是0,alert 请勾选随工单 返回false, 如果大于1 alert 请勾选一项 返回false
 */
function ckboxChick(){
	var ckLen = $(".ckbox:checked").length;
	if(ckLen <=0){
		alert("请选择随工单");
		return false;
	}else if(ckLen>1){
		alert("请选择一条随工单");
		return false;
	}else{
		return true;
	}
}