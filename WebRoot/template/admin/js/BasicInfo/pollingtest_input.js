$(function(){
	//巡检数量事件
	sample_event();
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
});
//巡检数量事件
function sample_event()
{
	$("#sample_num").change(function(){
		var samplenum=$(this).val().replace(" ","");
		if(samplenum!=null&&samplenum!="")
		{
			var reg=/^[0-9]+(\.[0-9]+)?$/;//整数或小数
			if(reg.test(samplenum))
			{
				var num_qx=getqxnum();
				samplenum=setScale(samplenum,0,"");//精度
				$(this).val(samplenum);
				$("#span_sq").text(samplenum);//合格数量
				$("#input_qulified").val(samplenum);//合格数量
				tocalc(samplenum,num_qx,"");
			}
			else
			{
				//layer.alert("输入不合法!",false);
				layer.msg("输入不合法!", {icon: 5});
				$(this).val("");
				$("#span_sq").text("0");//合格数量
				$("#input_qulified").val("-1");//合格数量
				$("#span_qrate").text("0.00%");//合格率
				$("#span_tip").text("");
			}
		}
		else{
			$("#span_sq").text("0");//合格数量
			$("#input_qulified").val("-1");//合格数量
			$("#span_qrate").text("0.00%");//合格率
			$("#span_tip").text("");
		}
	
	});
}
//计算并赋值：抽取数量，缺陷数量,缺陷数量备胎
function tocalc(samplenum,qxnum,qxnum_bt)
{
	var hgnum=$("#input_qulified").val();//合格数量
	if(qxnum!="")
	{
		hgnum=floatSub(hgnum,qxnum);//减法--合格数量
	}
	if(qxnum_bt!="")
	{
		hgnum=floatAdd(hgnum,qxnum_bt);//加法--合格数量
	}
	if(hgnum<0)
	{
		$("#span_tip").text("缺陷数量不能大于巡检数量!");
		$("#span_sq").text("0");//合格数量
		$("#span_qrate").text("0.00%");//合格率
	}
	else
	{
		$("#span_tip").text("");
		var qrnum=floatDiv(hgnum,samplenum);//除法--合格率
		qrnum=floatMul(qrnum,100);//乘法--合格率
		qrnum=setScale(qrnum,2,"");//精度--合格率
		$("#span_sq").text(hgnum);//合格数量
		$("#span_qrate").text(qrnum+"%");//合格率
	}
	$("#input_qulified").val(hgnum);//合格数量
}

//提交事件:1.刷卡保存，2.刷卡确认
function sub_event(my_id)
{
	var samplenum=$("#sample_num").val();//巡检数量
	if(samplenum==""||samplenum==null)
	{
		layer.msg("巡检数量不能为空!", {icon: 5});
		//layer.alert("巡检数量不能为空!",false);
	}
	else
	{
		var hgnum=$("#input_qulified").val();//合格数量--如果走到这一步，合格数量是一个整数
		if(hgnum<0)
		{
			layer.msg("缺陷数量不能大于巡检数量!", {icon: 5});
			//layer.alert("缺陷数量不能大于巡检数量!",false);
		}
		else
		{
			
			//赋值
			fuzhi(my_id);
			//提交
			$("#inputForm").submit();
		}
	}
}

//赋值
function fuzhi(my_id)
{
	//合格率
	var hgl=$("#span_qrate").text();
	$("#input_qrate").val(hgl);
	//缺陷描述的id--字符串形式
	var qxids=getqxids();
	$("#input_rd").val(qxids);
	//缺陷数量--字符串形式
	var qxnums=getqxnums();
	$("#input_rnum").val(qxnums);
	$("#my_id").val(my_id);//1.保存，2.确认
}