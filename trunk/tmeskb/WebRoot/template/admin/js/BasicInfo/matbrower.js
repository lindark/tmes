
$(function(){
	var $userAddBtn = $("#userAddBtn");//添加用户

	/**
	 * 添加按钮点击
	 */
	$userAddBtn.click(function(){
		
		var title = "产品";
		var width="800px";
		var height="650px";
		var content="products!browser.action";
		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
			
        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        	var docu = iframeWin.document;//获取document 对象
        	$(docu).find("#submit_btn").trigger("click");//点击ifream里面的提交按钮
        	var ids=$("#grid-table").jqGrid('getGridParam','selarrrow');
        	alert(ids);
        	alert("into");	
	 });
	
	});
	
})
