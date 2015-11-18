$(function(){
	var $userAddBtn = $("#userAddBtn");//添加用户
	 var $name=$("#name");
	 var $nameId=$("#nameId");
	/**
	 * 添加按钮点击
	 */
	$userAddBtn.click(function(){
		alert(${callReasonList});
		var title = "人员";
		var width="800px";
		var height="600px";
		var content="abnormal!browser.action";
		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
			
        var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        var work = iframeWin.getGridId();
        var html = '<tr><td><input type="text" name="abnormal.admin.id" id="name"　value="" class="{required: true}" readonly="readonly"/><input type="hidden" name="nameId" id="nameId"　value=""/></td><td><select name="callReasonId" class="{required: true}" style="width:200px;"><option value="">请选择...</option> </select></td></tr>';
        var id=work.split(",");
        $name.val(id[0]);
        $nameId.val(id[1]);
        if($(".person").length > 0) {
			$(".person:last").after(html);
		} 
        layer.close(index);            	          	     	
		});
		
		
	});
	
	
	
})


