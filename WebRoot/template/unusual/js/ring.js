$(function(){
	var $call = $(".call");//呼叫
	$call.click(function(){
		var url = "ring!list.action";
		var $dom = $("#dialog-message");
		var flag = jiuyi.admin.browser.inputdata(url,$dom);
		var title = "呼叫人员";
		if(flag)
		jiuyi.admin.browser.dialog($dom,title,function(){			
			var $ringForm = $("#ringForm");
			alert("ok");
			var ps=$("input[name='ids']:checked").parent().next().text();
			$(".person").text(ps);
			$.ajax({
				url: $ringForm.attr("action"),
				data: $ringForm.serialize(),
				dataType: "json",
				async: false,	
				success: function(data) {
					alert("yes");
					window.location.href="abnormal!list.action";
				}			
			});
			
			
		});		

	});
})