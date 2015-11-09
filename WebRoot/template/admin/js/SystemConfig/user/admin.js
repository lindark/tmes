
$(function(){
	var $userAddBtn = $("#userAddBtn");//添加用户
	var $userDeleteBtn = $("#userDeleteBtn");//删除用户
	var $userEditBtn = $("#userEditBtn");//编辑用户
	
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
			var zTree = $.fn.zTree.getZTreeObj("ingageTree");
			var nodes = zTree.getSelectedNodes();
			$("#grid-table").jqGrid('setGridParam',{
				url:"admin!ajlist.action?departid="+nodes[0].id,
				datatype:"json",
				page:1
			}).trigger("reloadGrid");
		});
	});
	
	
	/**
	 * 删除按钮点击
	 */
	$userDeleteBtn.click(function(){
		var flag = confirm("是否确认删除？");
		if(flag == false) return false;
		var ids=$("#grid-table").jqGrid('getGridParam','selarrrow');
		var url="admin!delete.action?id="+ids;
		$.ajax({	
			url: url,
			//data: "{\"id\":\""+ids+"\"}",
			dataType: "json",
			async: false,
			beforeSend: function(data) {
				$(this).attr("disabled", true);
			},
			success: function(data) {
				$.tip(data.status, data.message);
				var zTree = $.fn.zTree.getZTreeObj("ingageTree");
				var nodes = zTree.getSelectedNodes();
				$("#grid-table").jqGrid('setGridParam',{
					url:"admin!ajlist.action?departid="+nodes[0].id,
					datatype:"json",
					page:1
				}).trigger("reloadGrid");
			}
		});
	});
	
	/**
	 * 编辑按钮点击
	 */
	$userEditBtn.click(function(){
		var ids=$("#grid-table").jqGrid('getGridParam','selarrrow');
		if(ids.length <=0){
			alert("请选择记录");
			return false;
		}
		if(ids.length >1){
			alert("请选择一条记录");
			return false;
		}
		var url = "admin!edit.action?id="+ids;
		var $dom = $("#dialog-message");
		var flag = jiuyi.admin.browser.inputdata(url,$dom);
		var title = "编辑员工";
		if(flag)
		jiuyi.admin.browser.dialog($dom,title,function(){
			var $inputForm = $("#inputForm");
			$inputForm.submit();
			var zTree = $.fn.zTree.getZTreeObj("ingageTree");
			var nodes = zTree.getSelectedNodes();
			$("#grid-table").jqGrid('setGridParam',{
				url:"admin!ajlist.action?departid="+nodes[0].id,
				datatype:"json",
				page:1
			}).trigger("reloadGrid");
		});
		
	});
	
})
