$(function(){
	
	// 刷卡保存
	$("#btn_save").click(function() {
		sub_event("1");
	});

	// 刷卡提交
	$("#btn_confirm").click(function() {
		sub_event("2")
	});
	
	//返回
	$("#btn_back").click(function(){
		window.history.back();
	});
	
	//给按钮加事件--添加缺陷信息事件
	addbug_event();
	//缺陷内容事件
	cause_event();
	
});


//添加报废原因按钮事件
function btn_addbug_event(index)
{
	//选择当前行的事件
	rowtobox_event(index);
	layer.open({
	    type: 1,
	    shade:0.52,//遮罩透明度
	    title: "添加报废原因",
	    area:["600px","300px"],//弹出层宽高
	    closeBtn: 1,//0没有关闭按钮，1-3不同样式关闭按钮---右上角的位置
	    shadeClose: false,//点击遮罩层(阴影部分)：true时点击遮罩就关闭，false时不会
	    btn:["确定","取消"],
	    yes:function(){boxtorow_event(index);layer.closeAll();},
	    content: $("#divbox")//可以 引入一个页面如："a.jsp"  
	});
}

//报废数量输入框change事件
function write_bugnum_event(index)
{
	var bugnum=$("#mynum"+index).val().replace(" ","");
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
			bugnum=setScale(bugnum,0,"");//精度
			$("#mynum"+index).val(bugnum);
		}
	}
}

/**
 * 按钮事件 
 */
//1.刷卡保存2.刷卡确认
function sub_event(my_id)
{
	$("#my_id").val(my_id);//赋值
	if(my_id=="1")
	{
		$("#inputForm").submit();//提交
	}
	if(my_id=="2")
	{
		if(!sl_event())
		{
			layer.alert("半成品巡检表为空,不能确认!",false);
		}
		else
		{
			$("#inputForm").submit();//提交
		}
	}
}


function sl_event(){
	var i="0";
	$("#tb_itd tr").each(function(){
		var wlcode=($(this).children("td:eq(2)").children("input:first").val());		
		if(wlcode!=null&&wlcode!=""){
			i="1";
		}
	});
	if(i=="1"){
		return true;
	}else{
		return false;
	}		
}






