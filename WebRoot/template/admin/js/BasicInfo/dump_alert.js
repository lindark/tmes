var xedit="";
$(function(){
	xedit=$("#xedit").val();
	//输入数量事件
	$(".inputmenge").change(function(){
		var $num=$(this);
		num_event($num);
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
	$("#btn_return").click(function(){
		window.history.back();
	});
});

//报废后产出添加弹框中的输入数量事件
function num_event($num)
{
	var num=$num.val().replace(/\s+/g,"");
	var reg=/^[0-9]+(\.[0-9]+)?$/;
	if(reg.test(num))
	{
		num=setScale(num,0,"");//精度
		num=$num.val(num);
	}
	else
	{
		if(num!=null&&num!="")
		{
			layer.alert("输入不合法!",false);
		}
		$num.val("");
	}
}

//提交检验
function sub_event(i)
{
	var url="";
	var dumpid=$("#dumpid").val();
	//刷卡保存
	if(i==1)
	{
		if(xedit=="xedit")
		{
			url="dump!creditupdate.action";
			tosubmit(url,"tosave");
		}
		else if(xedit=="saved")
		{
			/*layer.alert("该数据已保存,是否确认再次新增一条该数据?", {icon: 6},function(){
				url="dump!creditsave.action";
				tosubmit(url,"tosave");
			});*/
			layer.confirm("该数据已保存,是否确认再次新增一条该数据?", {icon: 3,btn:["确定","取消"]},function(){
				url="dump!creditsave.action";
				tosubmit(url,"tosave");
			},function(){
				layer.closeAll();
			});
		}
		else
		{
			url="dump!creditsave.action";
			tosubmit(url,"tosave");
		}
	}
	else if(xedit=="xedit")
	{
		/*layer.alert("修改数据未保存,是否确认提交修改前的数据?", {icon: 3},function(){
			url="dump!creditreply.action";
			tosubmit(url,"toconfrim");
		});*/
		layer.confirm("修改数据未保存,是否确认提交修改前的数据?", {icon: 3,btn:["确定","取消"]},function(){
			url="dump!creditreply.action";
			tosubmit(url,"toconfrim");
		},function(){
			
		});
	}
	else
	{
		//刷卡确认
		if(dumpid==null||dumpid=="")
		{
			layer.alert("数据保存后才可以确认!",false);
		}
		else
		{
			url="dump!creditreply.action";
			tosubmit(url,"toconfrim");
		}
	}
}

//提交
function tosubmit(url,info)
{
	var dt=$("#inputForm").serialize();
	credit.creditCard(url,function(data){
		if(data.status=="success")
		{
			layer.alert("您的操作已成功!", {icon: 6,closeBtn: 0},function(){
				if(xedit=="xedit")
				{
					xedit="updated";
				}
				else
				{
					xedit="saved";
					$("#dumpid").val(data.dumpid);
				}
				if(info=="toconfrim")
				{
					//window.location.href="dump!all.action?loginid="+$("#loginid").val();
					var menges="";
					for(var i=0;i<$(".inputmenge").length;i++)
					{	
						if($(".inputmenge").eq(i).val()!=""){
						var numbers=$(".chargnumber").eq(i).text();
						menges=menges+numbers+":"+($(".inputmenge").eq(i).val())+",";  
						}
					}
						menges.substring(0,menges.length-2);
					var loginid = $("#loginid").val();
					var cardnumber = data.cardnumber;
					var funid = data.funid;
					var map = {};
				/*	map["loginid"]=$("#loginid").val();
					map["cardnumber"]=data.cardnumber;
					map["funid"]= data.funid;
					map["materialCode"]=$("#materialCode").val();
					map["inputmenge"]= menges;*/
					//window.location.href="up_down!addForWuliu.action?map="+map;
					window.location.href="up_down!addForWuliu.action?funid="+funid+"&cardnumber="+cardnumber+"&materialCode="+$("#materialCode").val()+"&inputmenge="+menges+"&loginid"+loginid;
				}
				else
				{
					layer.closeAll();
				}
			});
		}
		else if(data.status=="error")
		{
			layer.alert(data.message, {
		        closeBtn: 0,
		        icon:5,
		        skin:'error'
		    },function(index){
		    	layer.close(index);
			});
		}
	},dt);
}
