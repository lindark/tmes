$(function(){
	//添加产品子件
	$("#btn_addpiece").click(function(){
		addpiece();
	});
	//返修收货数量是否输入合法
	$("#input_num").change(function(){
		inputnum_event();
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

//添加产品子件
function addpiece()
{
	var title = "添加产品组件";
	var width="800px";
	var height="610px";
	var content="repairin!beforegetpiece.action?info="+$("#wkid").val();
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var infos=iframeWin.getGridId();
		if(infos!=""&&infos!=null)
		{
			var info1=infos.split("?");//分行
			var xhtml="";
			for(var i=0;i<info1.length;i++)
			{
				var info2=info1[i].split(",");//分列
				//info2 --- 0主键ID 1组件编码 2组件名称 3产品数量  4组件数量
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
			$("#tb_repairinpiece tr:gt(0)").remove();//除了第一行,删除所有其他行
			$("#tb_repairinpiece").append(xhtml);
			layer.close(index);
		}
	});
}

//删除
function del_event(obj)
{
	$("#a_"+obj).parent().parent().remove();
}

//
function save_event(url)
{
	var dt = $("#inputForm").serialize();
	credit.creditCard(url,function(data){
		var workingbillid = $("#wkid").val();
		if(data.status=="success"){
    		layer.alert(data.message, {icon: 6},function(){
			window.location.href = "repairin!list.action?workingBillId="+ workingbillid;
    	});
    	}else if(data.status=="error"){
    		layer.alert(data.message,{
    			closeBtn: 0,
    			icon: 5,
    			skin:'error'
    		});
    	}
	},dt);
}

//返修收货数量是否输入合法
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

//成品/组件-单选按钮
function radio_event()
{
	//成品
	if($("#repairintype_cp").attr("checked"))
	{
		$("#div_addpiece").hide();
		$("#tb_repairinpiece tr:gt(0)").remove();//除了第一行,删除所有其他行,两种方法都一样
	}
	//组件
	if($("#repairintype_zj").attr("checked"))
	{
		$("#div_addpiece").show();
	}
}

//成品/组件-单选按钮
function radio_event2()
{
	//成品
	if($("#repairintype_cp").attr("checked"))
	{
		$("#div_addpiece").hide();
	}
	//组件
	if($("#repairintype_zj").attr("checked"))
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
	var content="repairin!beforegetcostcenter.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var info=iframeWin.getGridId();
		if(info!=""&&info!=null)
		{
			$("#span_costcenter").text(info);
			$("#input_costcenter").val(info);
			layer.close(index);
		}
	});
}
//是否可以提交
function iscansave()
{
	var input_num=$("#input_num").val();//返修数量
	if(input_num==""||input_num==null)
	{
		return false;
	}
	return true;
}