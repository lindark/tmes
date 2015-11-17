$(function(){
	var $userAddBtn = $("#userAddBtn");//添加用户
	 var $productId=$("#productId");
	 var $productName=$("#productName");

	/**
	 * 添加按钮点击
	 */
	$userAddBtn.click(function(){
		
		var title = "选择产品";
		var width="800px";
		var height="600px";
		var content="products!browser.action";
		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
			
        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        	//alert(iframeWin);
        	 var work = iframeWin.getGridId();
             var id=work.split(",");
             alert(id);
             $productId.val(id[1]);
             $productName.text(id[0]);
             layer.close(index);            	          	     	
             
//        	var docu = iframeWin.document;//获取document 对象
//        	$(docu).find("#submit_btn").trigger("click");//点击ifream里面的提交按钮
//        	var ids=$("#grid-table").jqGrid('getGridParam','selarrrow');
//        	alert(ids);
//        	alert("into");	
	 });
	
	});
	
})
