
$(function(){
	var $userAddBtn = $("#userAddBtn");//添加用户

	/**
	 * 添加按钮点击
	 */
	$userAddBtn.click(function(){
		
		var title = "车间";
		var width="800px";
		var height="600px";
		var content="work_shop!browser.action";
		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
			
        var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        var docu = iframeWin.document;//获取document 对象
        //var ids=$('#grid-table').jqGrid('getGridParam','selarrrow’);
        var ids =  $(docu).find("#grid-table").jqGrid('getGridParam','selarrrow');
        	//var ids=$("#grid-table").jqGrid('getGridParam','selarrrow');
       //    alert(ids); 
//           if(ids.length >1){
//				alert("请选择一条记录");
//				return false;
//			}
//        	$("#workShopId").text(ids);
        	/*var docu = iframeWin.document;//获取document 对象
        	$(docu).find("#submit_btn").trigger("click");//点击ifream里面的提交按钮*/
        	          	     	
		});
		
		
	});
	
	
	
})
