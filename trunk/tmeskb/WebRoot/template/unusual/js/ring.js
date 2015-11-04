$(function(){
	//add();
	var $call = $(".call");//呼叫
	$call.click(function(){
		var url = "ring!add.action";
		var $dom = $("#dialog-message");
		var flag = jiuyi.admin.browser.inputdata(url,$dom);
		var title = "呼叫人员";
		if(flag)
		jiuyi.admin.browser.dialog($dom,title,function(){
			var $departform = $("#ringform");
			$.ajax({
				url: $departform.attr("action"),
				data: $departform.serialize(),
				dataType: "json",
				async: false,
				beforeSend: function(data) {
					$(this).attr("disabled", true);
				},
				success: function(data) {
					alert(data.status);					
				}
			});
		});
		
		
		return false;
	})
})