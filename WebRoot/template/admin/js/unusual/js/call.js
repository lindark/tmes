
$(function(){
	var $userAddBtn = $("#userAddBtn");//添加用户
	 var $name=$("#name");
	 var $nameId=$("#nameId");
	/**
	 * 添加按钮点击
	 */
	$userAddBtn.click(function(){
		
		var title = "人员";
		var width="800px";
		var height="600px";
		var content="abnormal!browser.action";
		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
			
        var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        var work = iframeWin.getGridId();
        var id=work.split(",");
        $name.val(id[0]);
        $nameId.val(id[1]);
        layer.close(index);            	          	     	
		});
		
		
	});
	
	
	
})
