
$(function(){
	var $userAddBtn = $("#userAddBtn");//添加用户
	 var $workShopId=$("#workShopId1");
	 var $workShopId2=$("#workShopId2");
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
        var work = iframeWin.getGridId();
        var id=work.split(",");
        $workShopId.val(id[0]);
        $workShopId2.val(id[1]);
        layer.close(index);            	          	     	
		});
		
		
	});
	
	
	
})
