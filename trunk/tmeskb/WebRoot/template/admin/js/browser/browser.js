$register("jiuyi.admin.browser");
$(function(){

})


/*
 * 生成html 给 弹出层内容
 */
jiuyi.admin.browser.inputdata = function(url,$dom){
	var flag = false;
	var url=url;
	$.ajax({
		url: url,
		type:"post",
		async: false,
//		beforeSend: function(data) {
//			//$dom.attr("href", "#");
//		},
		success: function(data) {
			$dom.html(data);
			flag = true;
		},error:function(data){
			alert("加载数据失败");
			
		}
	});
	
	return flag;
}


/**
 * dialog 弹出层
 */
jiuyi.admin.browser.dialog = function($dom,title,callback){
	$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
		_title: function(title) {
			var $title = this.options.title || '&nbsp;'
			if( ("title_html" in this.options) && this.options.title_html == true )
				title.html($title);
			else title.text($title);
		}
	}));
	var dialog = $dom.removeClass('hide').dialog({
		modal: true,
		width:"650",
		height:"auto",
		title: "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-check'></i>"+title+"</h4></div>",
		title_html: true,
		buttons: [ 
			{
				text: "取消",
				"class" : "btn btn-xs",
				click: function() {
					$( this ).dialog( "close" ); 
				} 
			},
			{
				text: "保存",
				"class" : "btn btn-primary btn-xs",
				click: function() {
					callback();
				} 
			}
		]
	});
	
}
