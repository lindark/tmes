var iscansub="N";
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
	
	//用户名change事件
	$("#input_username").change(function(){
		username_event();
	});
	
	//提交事件
	$("#btn_submit").click(function(){
		sub_event();
	});
});

//用户名change事件
function username_event()
{
	var username=$("#input_username").val();
	username=username.replace(/\s+/g,"");//去空
	$("#input_username").val(username);
	if(username!=null&&username!="")
	{
		$.post("admin!ckusername.action?admin.id="+$("#input_id").val()+"&admin.username="+username,function(data){
			if(data.status=="success")
			{
				$("#span_username").text("");
				iscansub="Y";
			}
			else if(data.status=="error")
			{
				$("#span_username").text("用户名已存在!");
				iscansub="N";
			}
		},"json");
	}
}

//添加人员
function addemp_event()
{
	layer.open({
        type: 2,
        skin: 'layui-layer-lan',
        shift:2,
        title: "选择人员",
        fix: false,
        shade: 0.5,
        shadeClose: true,
        maxmin: true,
        scrollbar: false,
        btn:['确认','取消'],
        area: ["80%", "90%"],//弹出框的宽度高度
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
	var username=$("#input_username").val();
	if(username!=null&&username!="")
	{
		if(iscansub=="Y")
		{
			$("#xform").submit();
		}
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