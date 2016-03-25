var iscansub="Y";
$(function(){
	//编码change事件
	$("#input_code").change(function(){
		code_event();
	});
	
	//提交事件
	$("#btn_sub").click(function(){
		sub_event();
	});
});

//编码change事件
function code_event()
{
	var stationcode=$("#input_code").val();
	stationcode=stationcode.replace(/\s+/g,"");
	$("#input_code").val(stationcode);
	//检验编码是否已存在
	if(stationcode!=null&&stationcode!="")
	{
		$.post("station!ckcode.action?id="+$("#stationid").val()+"&stationcode="+stationcode,function(data){
			if(data.status=="success")
			{
				$("#span_code").text("");
				iscansub="Y";
			}
			else if(data.status=="error")
			{
				$("#span_code").text("工位编码已存在!");
				iscansub="N";
			}
		},"json");
	}
}

//提交事件
function sub_event()
{
	var stationcode=$("#input_code").val();
	if(stationcode!=null&&stationcode!="")
	{
		if(iscansub=="Y")
		{
			$("#inputForm").submit();
		}
	}
	else
	{
		$("#inputForm").submit();
	}
}
