var init={
	"add":function(){
		window.location.href="resource!add.action";
	},
	"edit":function(){
		var ids=this.jQgrid.jqGrid('getGridParam','selarrrow');
		if(ids.length>1 || ids.length<1){
			$.message("error","请选择一行记录");
			return false;
		}
		window.location.href="resource!edit.action?id="+ids;
	},
	"delete":function(){
		var index;
		var ids=this.jQgrid.jqGrid('getGridParam','selarrrow');
		$.ajax({	
			url: "resource!delete.action",
			data:"ids="+ids,
			dataType: "json",
			async: false,
			beforeSend: function(data) {
				init.deleteButton.attr("disabled", true);
				index = layer.load();
			},
			success: function(data) {
				init.deleteButton.attr("disabled", false);
				layer.close(index);
				$.message(data.status,data.message);
				init.jQgrid.trigger("reloadGrid");
			},error:function(data){
				init.deleteButton.attr("disabled", false);
				layer.close(index);
				$.message("error","系统出现问题，请联系系统管理员");
			}
		});
	}
		
}

$(function(){
	init.jQgrid=$("#grid-table");
	init.addButton = $("#addButton");
	init.editButton=$("#editButton");
	init.deleteButton=$("#deleteButton");
	
	/**
	 * 添加
	 */
	init.addButton.click(function(){
		init.add();
	});
	
	/**
	 * 修改
	 */
	init.editButton.click(function(){
		init.edit();
	})
	
	/**
	 * 删除
	 */
	init.deleteButton.click(function(){
		init.delete();
	});
})