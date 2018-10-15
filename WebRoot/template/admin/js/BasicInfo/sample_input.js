$(function(){
	//抽检数量事件
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
function change_sample_num(t) {
    var idval=t.attr("id");
    i=idval.substring(6,idval.length);
    var samplenum=$("#sample_num").val();//抽检数量
    var num_bt=$("#sr_num2"+i).val();//备胎
    var num_qx=t.val().replace(/\s+/g,"");//缺陷
    if(num_qx!=null&&num_qx!=""){
        var reg=/^[0-9]+(\.[0-9]+)?$/;//整数或小数
        if(reg.test(num_qx)){
            num_qx=setScale(num_qx,0,"");//精度--去小数
            t.val(num_qx);
            t.next().val(num_qx);
            // var id = t.next().attr('id');
            // var the_sr_num2 = id.substring(7);
            if(num_qx>=0&&(samplenum!=null&&samplenum!="")){
                if(num_bt>0&&num_bt!=null&&num_bt!=""){
                    tocalc(samplenum,num_qx,num_bt);
                }else{
                    tocalc(samplenum,num_qx,"");
                }
            }else{
                $("#span_tip").text("");
            }
        }else{
            layer.alert("输入不合法!",false);
            t.val("");//缺陷数量
            $("#sr_num2"+i).val("");//缺陷数量--备胎
            if(num_bt!=""&&num_bt!=null&&num_bt>0&&(samplenum!=null&&samplenum!="")){
                tocalc(samplenum,"",num_bt);
            }
        }
    }else{
//				$("#sr_num2"+i).val("");//缺陷数量--备胎
        num_bt = t.next().val();
        t.next().val("");
        //alert(samplenum);
        if(num_bt!=""&&num_bt!=null&&num_bt>0&&(samplenum!=null&&samplenum!=""))
        {
            tocalc(samplenum,"",num_bt);
        }
    }
}
//抽检数量事件
function sample_event()
{
	$("#sample_num").change(function(){
		var samplenum=$(this).val().replace(/\s+/g,"");
		if(samplenum!=null&&samplenum!="")
		{
			var reg=/^[0-9]+(\.[0-9]+)?$/;//整数或小数
			if(reg.test(samplenum))
			{
				if(samplenum>0)
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
					layer.alert("抽检数量必须大于0!",false);
					$(this).val("");
					$("#span_sq").text("0");//合格数量
					$("#input_qulified").val("-1");//合格数量
					$("#span_qrate").text("0.00%");//合格率
					$("#span_tip").text("");
				}
			}
			else
			{
				layer.alert("输入不合法!",false);
				$(this).val("");
				$("#span_sq").text("0");//合格数量
				$("#input_qulified").val("-1");//合格数量
				$("#span_qrate").text("0.00%");//合格率
				$("#span_tip").text("");
			}
		}
		else
		{
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
		$("#span_tip").text("缺陷数量不能大于抽检数量!");
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

//提交
function tosubmit(url)
{
	var xwbid=$("#xwbid").val();
	var dt=$("#inputForm").serialize();
	credit.creditCard(url,function(data){
		if(data.status=="success")
		{
			layer.alert(data.message, {icon: 6},function(){
				window.location.href="sample!list.action?wbId="+xwbid;
			}); 
		}
		else if(data.status=="error")
		{
			layer.alert(data.message, {
		        closeBtn: 0,
		        icon:5,
		        skin:'error'
		    });
		}					
	},dt);
}