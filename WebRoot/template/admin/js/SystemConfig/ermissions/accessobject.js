//$register("jiuyi.admin.ermissions");
$(function(){
	var $addButton = $("#addButton");//添加按钮
	var $editButton = $("#editButton");//修改按钮
	var $deleteButton = $("#deleteButton");//删除按钮
	
	
	/**
	 * 添加按钮点击
	 */
	$addButton.click(function(){
		window.location.href="access_object!add.action";
	})
	
	/**
	 * 修改按钮点击
	 */
	$editButton.click(function(){
		var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');
		if(ids.length > 1){
			alert("请选择一行记录");
			return false;
		}
		
		window.location.href="access_object!edit.action?id="+ids;
	})
	
	
	$deleteButton.click(function(){
		var ids = $('#grid-table').jqGrid('getGridParam','selarrrow');
		if(ids.length <=0){
			alert("请选择一行记录");
			return false;
		}
		var flag = confirm("你确定要删除吗？");
		if(flag==false) return false;
		window.location.href="access_object!delete.action?ids="+ids;
	})
	
})

