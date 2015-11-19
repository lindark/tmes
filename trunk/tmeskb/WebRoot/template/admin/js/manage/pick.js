$(function(){
	var $pickBtn = $("#pickBtn");//创建领料单
	
	
	/**
	 * 创建领料单点击事件
	 */
	$pickBtn.click(function(){
		window.location.href="pick_detail!list.action?matnr="+productCode;
		
	});
})