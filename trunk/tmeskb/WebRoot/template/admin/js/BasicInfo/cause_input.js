$(function(){
	type_event();
	//代码类型选择事件
	$("#select_type").change(function(){
		type_event();
	});
	
	//责任类型事件
	$("#select_duty").change(function(){
		duty_event();
	});
	
	//提交事件
	$("#btn_save").click(function(){
		save_event();
	});
});
//代码类型选择事件
function type_event()
{
	var typeval=$("#select_type").val();
	if(typeval=="4")
	{
		$("#div_duty").show();
		$("#select_duty").val("baga");
		$("#span_duty").text("");
	}
	else
	{
		$("#div_duty").hide();
		$("#select_duty").val("baga");
		$("#span_duty").text("");
	}
}

//责任类型事件
function duty_event()
{
	$("#span_duty").text("");
}

//提交事件
function save_event()
{
	var typeval=$("#select_type").val();
	var dutyval=$("#select_duty").val();
	if(typeval=="4")
	{
		if(dutyval==null||dutyval==""||dutyval=="baga")
		{
			//代码类型为报废时,责任类型必选
			$("#span_duty").text("代码类型为报废时,责任类型必选 !");
		}
		else
		{
			$("#inputForm").submit();
		}
	}
	else
	{
		$("#inputForm").submit();
	}
}