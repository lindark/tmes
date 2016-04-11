$(function(){
	//添加产品组件
	$("#btn_addpiece").click(function(){
		addpiece();
	});
	/**输入验证*/
	//返修数量
	$("#input_num").change(function(){
		inputnum_event();
	});
	//责任人/批次
	$("#input_duty").change(function(){
		inputduty_event();
	});
	//返修部位
	$("#input_part").change(function(){
		inputpart_event();
	});
	//成品/组件-单选按钮
	$("input[type='radio']").click(function(){
		radio_event();
	});
	
	//添加成本中心
	$("#img_costcenter").click(function(){
		addcostcenter_event();
	});
	radio_event2();
});

//添加产品组件
function addpiece()
{
	var title = "添加产品组件";
	var width="800px";
	var height="610px";
	var content="repair!beforegetpiece.action?info="+$("#wkid").val();
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var infos=iframeWin.getGridId();
		if(infos!=""&&infos!=null)
		{
			var info1=infos.split("?");//分行
			//var xhtml="<th style='width:20%;'>组件编码</th> <th style='width:35%;'>组件描述</th> <th style='width:15%;'>产品数量</th> <th style='width:15%;'>组件数量</th>  <th style='width:15%;'>操作</th>";
			var xhtml="";
			for(var i=0;i<info1.length;i++)
			{
				var info2=info1[i].split(",");//分列
				//info2 --- 0主键ID 1组件编码 2组件名称 3随工单(产品)数量  4组件数量
				if(info2.length>1)
				{
					xhtml+="<tr>" +
					"<td>"+info2[1]+"<input type='hidden' name='list_rp["+i+"].rpcode' value='"+info2[1]+"' /></td>" +
					"<td>"+info2[2]+"<input type='hidden' name='list_rp["+i+"].rpname' value='"+info2[2]+"' /></td>" +
					"<td>"+info2[3]+"<input type='hidden' name='list_rp["+i+"].productnum' value='"+info2[3]+"' /></td>" +
					"<td>"+info2[4]+"<input type='hidden' name='list_rp["+i+"].piecenum' value='"+info2[4]+"' /></td>" +
					"<td><a id='a_"+info2[1]+"' onclick='del_event("+info2[1]+")' style='cursor:pointer;'>删除</a></td>" +
					"</tr>";
				}
			}
			//$("#tb_repairpiece tr:not(:first)").remove();
			$("#tb_repairpiece tr:gt(0)").remove();//除了第一行,删除所有其他行,两种方法都一样
			$("#tb_repairpiece").append(xhtml);
			layer.close(index);
		}
	});
}

//删除
function del_event(obj)
{
	$("#a_"+obj).parent().parent().remove();
}

//刷卡保存
function save_event(url)
{
	var dt = $("#inputForm").serialize();
	credit.creditCard(url,function(data){
		var workingbillid = $("#wkid").val();
		if(data.status=="success")
		{
    		layer.alert(data.message, {icon: 6},function(){
    			window.location.href = "repair!list.action?workingBillId="+ workingbillid;
    		});
    	}
		else if(data.status=="error")
		{
    		layer.alert(data.message,{
    			closeBtn: 0,
    			icon: 5,
    			skin:'error'
    		});
    	}
	},dt);
}

//返修数量是否输入合法
function inputnum_event()
{
	var reg=/^[0-9]+(\.[0-9]+)?$/;//整数或小数
	var input_num=$("#input_num").val().replace(/\s+/g,"") //返修数量
	if(reg.test(input_num))
	{
		input_num=setScale(input_num,0,"");//精度
		$("#input_num").val(input_num);
	}
	else
	{
		layer.alert("输入不合法!",false);
		$("#input_num").val("");
	}
}
//责任人/批次
function inputduty_event()
{
	var input_duty=$("#input_duty").val().replace(/\s+/g,"");
	$("#input_duty").val(input_duty);
}
//返修部位
function inputpart_event()
{
	var input_part=$("#input_part").val().replace(/\s+/g,"");
	$("#input_part").val(input_part);
}

//成品/组件-单选按钮
function radio_event2()
{
	//成品
	if($("#repairtype_cp").attr("checked"))
	{
		$("#div_addpiece").hide();
	}
	//组件
	if($("#repairtype_zj").attr("checked"))
	{
		$("#div_addpiece").show();
	}
}

//成品/组件-单选按钮
function radio_event()
{
	//成品
	if($("#repairtype_cp").attr("checked"))
	{
		$("#div_addpiece").hide();
		$("#tb_repairpiece tr:gt(0)").remove();//除了第一行,删除所有其他行,两种方法都一样
	}
	//组件
	if($("#repairtype_zj").attr("checked"))
	{
		$("#div_addpiece").show();
	}
}

//添加成本中心
function addcostcenter_event()
{
	var title = "添加成本中心";
	var width="800px";
	var height="525px";
	var content="repair!beforegetcostcenter.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var info=iframeWin.getGridId();
		if(info!=""&&info!=null)
		{
			var mydata=info.split(",");
			$("#span_costcenter").text(mydata[0]);
			$("#input_costcenter").val(mydata[0]);
			$("#input_cxorjc").val(mydata[1]);
			layer.close(index);
		}
	});
}

function iscansave()
{
	var input_num=$("#input_num").val();//返修数量
	//var input_duty=$("#input_duty").val();//责任人/批次
	var input_part=$("#input_part").val();//返修部位
	var departmentName=$("#departmentName").val();//成本中心
	if(input_num==""||input_num==null||input_part==""||input_part==null||departmentName==""||departmentName==null)
	{
		return false;
	}
	return true;
}