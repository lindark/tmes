jQuery(function($) {
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
		
	});
	//查看考勤历史
	$("#btn_viewhistory").click(function(){
		window.location.href="kaoqin!show.action";
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
				var kqid=$("#a_edit"+index).parent().children("input:first").val();
				var url="kaoqin!updateWorkState.action?kaoqin.workState="+val+"&kaoqin.id="+kqid;
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
	var content="kaoqin!beforegetemp.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var infos=iframeWin.getGridId();
		
		var info=infos.split(",");
		var html="<tr><td>"+info[0]+"</td> " +
				  "<td>"+info[1]+"</td>  " +
				  "<td>"+info[2]+"</td>  " +
				  "<td><span id='span_state"+info[0]+"'>"+info[5]+"</span><input id='input_state"+info[0]+"' type='hidden' value='"+info[5]+"' /></td> " +
				  "<td><a id='a_edit"+info[0]+"' class='a_edit'>编辑</a><input type='hidden' value='"+info[7]+"' /></td>" +
				  "</tr>";
		var rows=$("#tab1 tr").length;
		alert(rows);
		
		/*
		var info1=infos.split("?");
		for(var i=0;i<info1.length;i++)
		{
			var info2=info1[i].split(",");
			//info2 --- 0卡号1姓名2技能3班组4班次5工作状态描述6工作状态key7主键ID
			
		}
		*/
		//var id=work.split(",");
		//$("#xfuid").val(id[0]);//单元id
		//$("#label_xfuname").text(id[1]);//单元名称
		layer.close(index); 
	});
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
}