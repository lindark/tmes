
$(function(){
	var $userAddBtn = $("#userAddBtn");//添加用户
	var $userDeleteBtn = $("#userDeleteBtn");//删除用户
	
	/**
	 * 添加按钮点击
	 */
	$userAddBtn.click(function(){
		var zTree = $.fn.zTree.getZTreeObj("ingageTree");
		var nodes = zTree.getSelectedNodes();
		var departid = nodes[0].id;//获取ztree 选择节点的id
		var url = "admin!add.action?departid="+departid;
		var $dom = $("#dialog-message");
		var flag = jiuyi.admin.browser.inputdata(url,$dom);
		var title = "新增员工";
		if(flag)
		jiuyi.admin.browser.dialog($dom,title,function(){
			var $inputForm = $("#inputForm");
			$inputForm.submit();
//			$.ajax({	
//				url: $departform.attr("action"),
//				data: $departform.serialize(),
//				dataType: "json",
//				async: false,
//				beforeSend: function(data) {
//					$(this).attr("disabled", true);
//				},
//				success: function(data) {
////					$.tip(data.status, data.message);
////					var zTree = $.fn.zTree.getZTreeObj("ingageTree");
////					zTree.cancelEditName(data.deptName);
//				}
//			});
		});
	});
})
