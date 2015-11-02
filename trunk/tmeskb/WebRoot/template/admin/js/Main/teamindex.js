$(function(){
	var $storage = $("#storage");//入库
	
	/**
	 * 入库按钮点击
	 */
	$storage.click(function(){
		var istrue = ckboxChick();
		if(istrue){
			var id = getCKboxById();
			window.location.href="enteringware_house!list.action?workingBillId="+id;
		}
			
	});
})


/**
 * 获取checkbox的ID值
 */
function getCKboxById(){
	var id = "";
	var ishead=0;
	$(".ckbox:checked").each(function(){
		var sId = $(this).val();
		if(ishead==1)
			id+=","+sId;
		else
			id=sId;
		ishead=1;
	});
	alert(id);
	return id;
}

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