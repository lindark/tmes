$(function(){
	//页面右上角点击登录人事件
	login_event();
	
	//添加人员
	$("#img_emp").click(function(){
		addemp_event();
	});
	//返回
	$("#btn_return").click(function(){
		return_event();
	});
});

//添加人员
function addemp_event()
{
	layer.open({
        type: 2,
        skin: 'layui-layer-lan',
        shift:2,
        title: "选择班组",
        fix: false,
        shade: 0.5,
        shadeClose: true,
        maxmin: true,
        scrollbar: false,
        btn:['确认','取消'],
        area: ["80%", "80%"],//弹出框的高度，宽度
        content:"admin!beforegetempqxn.action",
        yes:function(index,layero){//确定
        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        	var info = iframeWin.getName();
        	$("#input_emp").val(info.empid);
        	$("#span_emp").text(info.name);
        	layer.close(index);
        	return false;
        },
        no:function(index)
        {
        	layer.close(index);
        	return false;
        }
    });
	return false;
}

//页面右上角点击登录人事件
function login_event()
{
	var ishead=0;
	$("#ace-settings-btn").click(function(){
		if(ishead==0){
			ishead=1;
			$("#ace-settings-box").addClass("open");
		}else{
			ishead=0;
			$("#ace-settings-box").removeClass("open");
		}
	});
	$(".btn-colorpicker").click(function(){
			$(".dropdown-colorpicker").addClass("open");
	});
	
	var ishead2=0;
	$(".light-blue").click(function(){
		if(ishead2==0){
			ishead2=1;
			$(this).addClass("open");
		}else{
			ishead2=0;
			$(this).removeClass("open");
		}
		
	});
}

//提交
function sub_event()
{
	var worknum=$("#input_worknum").val();//工号
	var cardnum=$("#input_cardnum");//卡号
	if(worknum!=null&&worknum!=""&&cardnum!=null&&cardnum!="")
	{
		var url="admin!checknum.action?worknumber="+worknum+"&cardnumber="+cardnum;
		$.post(url,function(data){
			if(data.message=="w")
			{
				//只有工号重复
				$("#span_worknum").text("工号已存在!");
			}
			else if(data.message=="c")
			{
				//只有卡号重复
				$("#span_cardnum").text("卡号已存在!");
			}
			else if(data.message=="wc")
			{
				//工号卡号都重复
				$("#span_worknum").text("工号已存在!");
				$("#span_cardnum").text("卡号已存在!");
			}
			else if(data.message=="success")
			{
				//工号卡号都没有重复,可以提交
				$("#xform").submit();
			}
			else
			{
				//系统出现异常
				layer.alert("系统出现异常!",{icon:5, skin:"error"});
			}
		},"json");
	}
	else
	{
		$("#xform").submit();
	}
}

//返回
function return_event()
{
	window.location.href="admin!alllistqx.action";
}