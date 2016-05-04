$(function(){
	
	//给按钮加事件--添加缺陷信息事件
	addbug_event();
	//缺陷内容事件
	cause_event();
	
	//初始化报废后产出
	scraplater_event();
	//报废后产出添加按钮事件
	$("#btn_add").click(function(){
		sladd_event();
	});
	
	//报废后产出添加框中输入数量事件
	$("#sl_num").change(function(){
		slnum_event();
	});
	
	//刷卡保存
	$("#btn_save").click(function(){
		sub_event("1");
	});
	
	//刷卡确认
	$("#btn_confirm").click(function(){
		sub_event("2");
	});
	
	//返回
	$("#btn_back").click(function(){
		window.history.back();
	});
	
	//添加报废信息
	$("#btn_addmsg").click(function(){
		addmsg_event();
	});
});
//添加报废原因按钮事件
function btn_addbug_event(index)
{
	//选判断责任类型,如果未选择,则隐所有;如果选择了,显示对应的,隐藏其他的
	var dutyval=$("#select_duty"+index).val();//责任类型
	if(dutyval=="baga")
	{
		//未选择,则隐所有
		$("#div_allcause").hide();
	}
	else
	{
		$("#div_allcause").show();
		showorhide_event(dutyval);
	}
	//选择当前行的事件
	rowtobox_event(index);
	layer.open({
	    type: 1,
	    shade:0.52,//遮罩透明度
	    title: "添加报废原因",
	    area:["700px","400px"],//弹出层宽高
	    closeBtn: 1,//0没有关闭按钮，1-3不同样式关闭按钮---右上角的位置
	    shadeClose: false,//点击遮罩层(阴影部分)：true时点击遮罩就关闭，false时不会
	    move:false,//禁止拖拽
	    btn:["确定","取消"],
	    yes:function(){boxtorow_event(index);layer.closeAll();},
	    content: $("#divbox")//可以 引入一个页面如："a.jsp"  
	});
}

//报废数量输入框change事件
function write_bugnum_event(index)
{
	var bugnum=$("#mynum"+index).val().replace(/\s+/g,"");
	if(bugnum!=null&&bugnum!="")
	{
		var reg=/^[0-9]+(\.[0-9]+)?$/;//整数或小数
		if(!reg.test(bugnum))
		{
			layer.alert("输入不合法!",false);
			$("#mynum"+index).val("");
		}
		else
		{
			//bugnum=setScale(bugnum,0,"");//精度
			$("#mynum"+index).val(bugnum);
		}
	}
}

//报废后产出添加按钮事件
function sladd_event()
{
	//给下拉框默认选项
	$("#sl_material").val("baga");
	//清空输入框
	$("#sl_num").val("");
	layer.open({
		type:1,
		shade:0.52,
		title:"报废后产出添加",
		area:["500px","300px"],
		closeBtn:1,
		shadeClose:false,
		move:false,//禁止拖拽
		content:$("#divbox2"),
		btn:["确定","取消"],
		yes:function(){box2torow_event();}
	});
}

//报废后产出添加弹框中的输入数量事件
function slnum_event()
{
	var slnum=$("#sl_num").val().replace(/\s+/g,"");
	var reg=/^[0-9]+(\.[0-9]+)?$/;
	if(reg.test(slnum))
	{
		//slnum=setScale(slnum,0,"");//精度
		slnum=$("#sl_num").val(slnum);
	}
	else
	{
		layer.alert("输入不合法!",false);
		$("#sl_num").val("");
	}
}

//报废后产出添加弹框中的确定事件
function box2torow_event()
{
	//下拉框值
	var select_val=$("#sl_material").val();
	var select_text=$("#opt_"+select_val).text();
	//输入框值
	var slnum_val=$("#sl_num").val();
	//判断下拉框是否为空//判断输入框是否为空
	if(select_val=="baga"||slnum_val==""||slnum_val==null)
	{
		layer.alert("条子名称或数量不能为空!",{
	        closeBtn: 0,
	        icon:5,
	        skin:'error'
	    });
	}
	else
	{
		//给表赋值
		if(slnum_val>0)
		{
			fuzhi_box2totable(select_val,select_text,slnum_val);
			//隐藏已选择的选项
			$("#opt_"+select_val).hide();
		}
		//给下拉框默认选项
		//$("#sl_material").val("baga");
		//清空输入框
		//$("#sl_num").val("");
		layer.closeAll();
	}
}

//报废后产出添加框中的值赋给表中
function fuzhi_box2totable(val,txt,num)
{
	//获取表的行数，确定新增的行值
	var row=$("#tb_scraplater tr").length;
	row=floatSub(row,1);//减法
	var xhtml="<tr>" +
			"<td>"+val+"<input type='hidden' name='list_scraplater["+row+"].slmatterNum' value='"+val+"' /></td>" +
			"<td>"+txt+"<input type='hidden' name='list_scraplater["+row+"].slmatterDes' value='"+txt+"' /></td>" +
			"<td><span id='span_count"+val+"'>"+num+"</span><input id='input_count"+val+"' type='text' name='list_scraplater["+row+"].slmatterCount' value='"+num+"' onblur='inputcount_blur("+val+")' style='display:none;' /></td>" +
			"<td>" +
			"<a onclick='edit_click("+val+")' href='javascript:void(0);'>编辑</a>" +
			"&nbsp;&nbsp;" +
			"<a onclick='del_click("+val+")' href='javascript:void(0);'>删除</a>" +
			"</td>" +
			"</tr>";
	$("#tb_scraplater").append(xhtml);
}

/**报废后产出表事件*/
//1.1编辑
function edit_click(obj)
{
	//span隐藏,input显示并获取焦点
	$("#span_count"+obj).hide();
	$("#input_count"+obj).show();
	$("#input_count"+obj).focus();
}

//1.2编辑时输入框失去焦点事件
function inputcount_blur(obj)
{
	var inputcountval=$("#input_count"+obj).val().replace(/\s+/g,"");
	var reg=/^[0-9]+(\.[0-9]+)?$/;
	if(reg.test(inputcountval))
	{
		//inputcountval=setScale(inputcountval,0,"");
		//span/input重新赋值
		$("#input_count"+obj).val(inputcountval);
		$("#span_count"+obj).text(inputcountval);
		//span显示,input隐藏
		$("#span_count"+obj).show();
		$("#input_count"+obj).hide();
	}
	else
	{
		if(inputcountval!=""&&inputcountval!=null)
		{
			layer.alert("输入不合法!",false);
			$("#input_count"+obj).val("");
		}
		$("#input_count"+obj).focus();
	}
}

//3.删除
function del_click(obj)
{
	$("#span_count"+obj).parent().parent().remove();//删除
	$("#opt_"+obj).show();//对应的下拉框内容释放出来
}

//报废产出后是否为空
function sl_event()
{
	number="0";
	$("#tb_scraplater tr").each(function(){
		var wlcode=($(this).children("td:first").text());
		var num=$("#input_count"+wlcode).val();
		if(num!=null&&num!=""&&num!="0")
		{
			number="1";
		}
	});
	if(number=="0")
	{
		return false;
	}
	else
	{
		return true;
	}
}

//提交
function tosubmit(url)
{
	var xwbid=$("#xwbid").val();
	var dt=$("#inputForm").serialize();
	credit.creditCard(url,function(data){
		if(data.status=="success")
		{
			layer.alert(data.message, {icon: 6},function(){
				window.location.href="scrap!list.action?wbId="+xwbid;
			}); 
		}
		else if(data.status=="error")
		{
			layer.alert(data.message, {
		        closeBtn: 0,
		        icon:5,
		        skin:'error'
		    },function(){
		    	layer.closeAll();
			});
		}					
	},dt);
}

//责任类型变化事件,清空缺陷内容
function selectduty_event(obj)
{	
	$("#span_bug"+obj).text("");
	$("#input_msgbug"+obj).val("");
	$("#input_msgmenge"+obj).val("");
	$("#input_bugnum"+obj).val("");
	$("#input_bugid"+obj).val("");
}

//添加报废信息
function addmsg_event()
{
	//给下拉框默认选项
	$("#sl_bom").val("baga");
	layer.open({
		type:1,
		shade:0.52,
		title:"添加报废信息",
		area:["500px","300px"],
		closeBtn:1,
		shadeClose:false,
		move:false,//禁止拖拽
		content:$("#divbox3"),
		btn:["确定","取消"],
		yes:function(){box3torow_event();}
	});
}

//
function box3torow_event()
{
	//下拉框值
	var select_val=$("#sl_bom").val();
	var select_text=$("#optsm_"+select_val).text();
	if(select_val=="baga")
	{
		layer.alert("物料不能为空!",{
	        closeBtn: 0,
	        icon:5,
	        skin:'error'
	    });
	}
	else
	{
		fuzhi_box3torow(select_val,select_text);
		layer.closeAll();
	}
}

//报废信息删除操作
function delsm_click(num)
{
	$("#a_delsm"+num).parent().parent().remove();
}
