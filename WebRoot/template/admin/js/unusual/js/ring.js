$(function(){
	var $call = $(".call");//呼叫
	$call.click(function(){
		var url = "ring!list.action";
		var $dom = $("#dialog-message");
		var flag = jiuyi.admin.browser.inputdata(url,$dom);
		var title = "呼叫人员";
		if(flag)
		jiuyi.admin.browser.dialog($dom,title,function(){
			alert("o");
			var i=$("#grid-table2").jqGrid('getGridParam','selarrrow');	
			alert(i);
			/*
			var $ringForm = $("#ringForm");
			alert("ok");
			var ps=$("input[name='ids']:checked").parent().next().text();
			var id=$("input[name='ids']:checked").val();
			alert(id);
			$(".person").text(ps);
			$.ajax({
				url: $ringForm.attr("action"),
				data: $ringForm.serialize(),
				//data: {"id": id},
				dataType: "json",
				async: false,	
				success: function(data) {
					alert("ye");
					//window.location.href="abnormal!list.action";
				}			
			});*/
			
			
		});		

	});
})