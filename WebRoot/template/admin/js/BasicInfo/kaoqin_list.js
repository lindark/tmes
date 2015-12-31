var isstartteam="";
var iscancreditcard=""
jQuery(function($) {
	isstartteam=$("#isstartteam").val();
	iscancreditcard=$("#iscancreditcard").val();
	//初始化操作事件
	//按钮事件
	btn_style();
	btn_event();
});

//按钮事件
function btn_event()
{
	//添加代班人
	$("#btn_add").click(function(){
		addemp();
	});
	//开启考勤
	$("#btn_open").click(function(){
		startWorking();
	});
	//查看考勤历史
	$("#btn_viewhistory").click(function(){
		
	});
	//返回
	$("#btn_back").click(function(){
		window.history.back();
	});
}

//编辑事件
function edit_event(index)
{
	var state=$("#input_state"+index).val();
	$("#select_state").val(state);
	layer.open({
		type:1,
		title:"修改员工状态",
		shade:0.52,
		shadeClose:false,
		area:["500px","333px"],
		content:$("#divbox"),
		closeBtn:1,
		btn:["修改提交","取消"],
		yes:function(i){
			layer.close(i);
			var val=$("#select_state").val();
			var txt=$("#select_state option:selected").text();
			if(val!=state)
			{
				$("#span_state"+index).text(txt);
				$("#input_state"+index).val(val);
				if(val=="2"||val=="5"||val=="6")
				{
					//2已上班5出差6代班
					sapn_stype1("span_state"+index);
				}
				else
				{
					sapn_stype2("span_state"+index);
				}
				var url="kaoqin!updateEmpWorkState.action?admin.workstate="+val+"&admin.cardNumber="+index;
				upd_event(url);
			}
		}
	});
}

//确认修改
function upd_event(url)
{
	$.ajax({	
		url: url,
		//data: $(form).serialize(),
		dataType: "json",
		async: false,
		beforeSend: function(data) {
			$(this).attr("disabled", true);
			index = layer.load();
		},
		success: function(data) {
			layer.close(index);
			//$.message(data.status,data.message);
		},error:function(data){
			$.message("error","系统出现问题，请联系系统管理员");
		}
	});
}

//添加员工信息
function addemp()
{
	var title = "添加代班员工";
	var width="800px";
	var height="620px";
	var content="kaoqin!beforegetemp.action?info="+$("#sameteamid").val();
	var html="";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var infos=iframeWin.getGridId();
		if(infos!=""&&infos!=null)
		{
			var info=infos.split(",");
			var info1=infos.split("?");
			for(var i=0;i<info1.length;i++)
			{
				var info2=info1[i].split(",");
				//info2 --- 0卡号1姓名2技能3班组4班次5工作状态描述6工作状态key7主键ID
				if(info2!=null&&info2!="")
				{
					html+="<tr>" +
					"<td>"+info2[0]+"</td>" +
					"<td>"+info2[1]+"</td>" +
					"<td>"+info2[2]+"</td>" +
					"<td>"+info2[3]+"</td>" +
					"<td><span id='span_state"+info2[0]+"' style='color:#008B00;font-weight:bold;'>代班</span><input id='input_state"+info2[0]+"' type='hidden' value='6' /></td> " +
					"<td><a id='a_edit"+info2[0]+"' onclick='edit_event("+info2[0]+")' style='cursor:pointer;'>编辑</a><input type='hidden' value='"+info2[7]+"' /></td>" +
					"</tr>";
				}
			}
			$("#tab1 tr").eq(0).after(html);
			layer.close(index);
		}
	});
}

//开启考勤
function startWorking()
{
	if(isstartteam=="N")
	{
		layer.alert("该班组未开启,无法开启考勤!",{icon:5},false);
	}
	else if(isstartteam=="Y")
	{
		if(iscancreditcard=="N")
		{
			layer.alert("开启考勤后40分钟以内不能重复开启!",{icon:5},false);
		}
		else if(iscancreditcard=="Y")
		{
			var url="kaoqin!creditreply.action?sameTeamId="+$("#sameteamid").val();
			credit.creditCard(url,function(data){
				$.message(data.status,data.message);
				$("#grid-table").trigger("reloadGrid");
			});
		}
	}
}

//样式1，绿色
function sapn_stype1(obj)
{
	$("#"+obj).attr("style","color:#008B00;font-weight:bold;");
}
//样式2，红色
function sapn_stype2(obj)
{
	$("#"+obj).attr("style","color:#b22;font-weight:bold;");
}

//按钮样式
function btn_style()
{
	//添加代班人员
	var $btnadd=$("#btn_add");
	//鼠标移到按钮上时事件
	$btnadd.mouseover(function(){
		$(this).attr("style","background-color:#FF8C69;");
	});
	//鼠标移开按钮时事件
	$btnadd.mouseout(function(){
		$(this).attr("style","background-color:#FFD39B;");
	});
	//鼠标按下按钮事件
	$btnadd.mousedown(function(){
		$(this).attr("style","background-color:#FF8C69;height:52px;box-shadow:1px 3px 3px #CD8162;margin-top:10px;");
	});
	//鼠标按下之后抬起事件
	$btnadd.mouseup(function(){
		$(this).attr("style","background-color:#FF8C69;height:50px;box-shadow:3px 5px 3px #CD8162;margin-top:8px;");
	});
	
	//开启考勤
	btn_style_startkaoqin();
	var $btnopen=$("#btn_open");
	//鼠标移到按钮上时事件
	$btnopen.mouseover(function(){
		$(this).attr("style","background-color:#FF8C69;");
	});
	//鼠标移开按钮时事件
	$btnopen.mouseout(function(){
		$(this).attr("style","background-color:#FFD39B;");
	});
	//鼠标按下按钮事件
	$btnopen.mousedown(function(){
		$(this).attr("style","background-color:#FF8C69;height:52px;box-shadow:1px 3px 3px #CD8162;margin-top:10px;");
	});
	//鼠标按下之后抬起事件
	$btnopen.mouseup(function(){
		$(this).attr("style","background-color:#FF8C69;height:50px;box-shadow:3px 5px 3px #CD8162;margin-top:8px;");
	});

	//查看考勤历史
	/*
	var $btnopen=$("#btn_viewhistory");
	//鼠标移到按钮上时事件
	$btnopen.mouseover(function(){
		$(this).attr("style","background-color:#FF8C69;");
	});
	//鼠标移开按钮时事件
	$btnopen.mouseout(function(){
		$(this).attr("style","background-color:#FFD39B;");
	});
	//鼠标按下按钮事件
	$btnopen.mousedown(function(){
		$(this).attr("style","background-color:#FF8C69;height:52px;box-shadow:1px 3px 3px #CD8162;margin-top:10px;");
	});
	//鼠标按下之后抬起事件
	$btnopen.mouseup(function(){
		$(this).attr("style","background-color:#FF8C69;height:50px;box-shadow:3px 5px 3px #CD8162;margin-top:8px;");
	});
	*/
}

//开启考勤按钮样式
function btn_style_startkaoqin()
{
	var $img_startkaoqi=$("#img_startkaoqin");
	//班组未开启
	if(isstartteam=="N")
	{
		$("#span_startkaoqin").text("考勤未开启");
		$img_startkaoqi.attr("src","/template/admin/images/btn_close.gif");
	}
	else if(isstartteam=="Y")
	{
		//已经开启过考勤
		if(iscancreditcard=="N")
		{
			$("#span_startkaoqin").text("考勤已开启");
			$img_startkaoqi.attr("src","/template/admin/images/btn_open2.gif");
		}
		else if(iscancreditcard=="Y")
		{
			$("#span_startkaoqin").text("考勤未开启");
			$img_startkaoqi.attr("src","/template/admin/images/btn_close.gif");
		}
	}
}
