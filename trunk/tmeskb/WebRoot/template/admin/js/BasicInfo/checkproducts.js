
$(function(){
	var $productsAddBtn = $("#userAddBtn");//选择产品
	//var $userDeleteBtn = $("#userDeleteBtn");//删除用户
	//var $userEditBtn = $("#userEditBtn");//编辑用户
	
	


	
	/**
	 * 添加按钮点击
	 */
	$productsAddBtn.click(function(){
		//var zTree = $.fn.zTree.getZTreeObj("ingageTree");
		var nodes = zTree.getSelectedNodes();
		var departid = nodes[0].id;//获取ztree 选择节点的id
		var title = "选择产品";
		var width="1000px";
		var height="500px";
		var content="material!add.action?productsid="+productsid;
		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
			
        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        	var docu = iframeWin.document;//获取document 对象
        	$(docu).find("#submit_btn").trigger("click");//点击ifream里面的提交按钮
        	//$(docu).find("#inputForm").submit(); //有问题，没解决
			var zTree = $.fn.zTree.getZTreeObj("ingageTree");
			var nodes = zTree.getSelectedNodes();
			$("#grid-table").jqGrid('setGridParam',{
				url:"material!ajlist.action?productsid="+nodes[0].id,
				datatype:"json",
				page:1
			}).trigger("reloadGrid");
		});
		
		
	});
	
	
	/**
	 * 删除按钮点击
	 */
//	$userDeleteBtn.click(function(){
//		var flag = confirm("您确定要删除吗?");
//		if(flag == false) return false;
//		var ids=$("#grid-table").jqGrid('getGridParam','selarrrow');
//		var url="admin!delete.action?id="+ids;
//		$.ajax({	
//			url: url,
//			//data: "{\"id\":\""+ids+"\"}",
//			dataType: "json",
//			async: false,
//			beforeSend: function(data) {
//				$(this).attr("disabled", true);
//			},
//			success: function(data) {
//				layer.msg(data.message);
//				var zTree = $.fn.zTree.getZTreeObj("ingageTree");
//				var nodes = zTree.getSelectedNodes();
//				$("#grid-table").jqGrid('setGridParam',{
//					url:"admin!ajlist.action?departid="+nodes[0].id,
//					datatype:"json",
//					page:1
//				}).trigger("reloadGrid");
//			}
//		});
//		
//	});
	
	/**
	 * 编辑按钮点击
	 */
//	$userEditBtn.click(function(){
//		var ids=$("#grid-table").jqGrid('getGridParam','selarrrow');
//		if(ids.length <=0){
//			alert("请选择记录");
//			return false;
//		}
//		if(ids.length >1){
//			alert("请选择一条记录");
//			return false;
//		}
//		var title = "编辑员工";
//		var width="1000px";
//		var height="500px";
//		var content="admin!edit.action?id="+ids;
//		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
//        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
//        	var docu = iframeWin.document;//获取document 对象
//        	$(docu).find("#submit_btn").trigger("click");//点击ifream里面的提交按钮
//			var zTree = $.fn.zTree.getZTreeObj("ingageTree");
//			var nodes = zTree.getSelectedNodes();
//			$("#grid-table").jqGrid('setGridParam',{
//				url:"admin!ajlist.action?departid="+nodes[0].id,
//				datatype:"json",
//				page:1
//			}).trigger("reloadGrid");
//		});
//		
//	});
//	
})
