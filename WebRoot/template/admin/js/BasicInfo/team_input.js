
$(function(){
	//表单监控
	form_ck();
	//提交事件,判断工序名是否为空
	$("#btn_sub").click(function(){
		var val_sub_tcode=$("#team_code").val().replace(" ","");//班组编码
		var val_sub_tname=$("#team_name").val().replace(" ","");//班组名称
		var val_sub_xfuid=$("#xfuid").val().replace(" ","");//单元ID,如果ID不为空，则名称也不为空
		var val_sub_xtcode=$("#xcode").val();//工序编码,用于判断：1.不为空表示修改，可以为原名不用判断是否存在，  2.为空表示新增，需要判断是否已存在
		//班组编码
		if(val_sub_tcode==""||val_sub_tcode==null)
		{
			$("#span_tip_tcode").text("班组编码不能为空!");
		}
		else
		{
			$("#span_tip_tcode").text("");
		}
		//班组名称
		if(val_sub_tname==""||val_sub_tname==null)
		{
			$("#span_tip_tname").text("班组名称不能为空!");
		}
		else
		{
			$("#span_tip_tname").text("");
		}
		
		//单元名称
		if(val_sub_xfuid==""||val_sub_xfuid==null)
		{
			$("#span_tip_xfuname").text("单元名称不能为空!");
		}
		else
		{
			$("#span_tip_xfuname").text("");
		}
		if((val_sub_tcode==""||val_sub_tcode==null)||(val_sub_tname==""||val_sub_tname==null)||(val_sub_xfuid==""||val_sub_xfuid==null))
		{
			//layer.alert("必填数据不能为空,请查看!",8,false);
		}
		else
		{
			//新增
			if(val_sub_xtcode==null||val_sub_xtcode=="")
			{
				sub_ck(val_sub_tcode);
			}
			else
			{
				//修改
				if(val_sub_tcode==val_sub_xtcode)
				{
					$("#inputForm").submit();
				}
				else
				{
					sub_ck(val_sub_tcode);
				}
			}
		}
	});
});
//表单监控
function form_ck()
{
	$("#team_code").blur(function(){
		var val_ck_tcode=$("#team_code").val().replace(" ","");//班组编码
		if(val_ck_tcode==""||val_ck_tcode==null)
		{
			$("#span_tip_tcode").text("班组编码不能为空!");
			$("#team_code").val("");//清空，针对只有空格的
		}
		else
		{
			$("#span_tip_tcode").text("");
			$("#team_code").val(val_ck_tcode);//去空
		}
	});
	$("#team_name").blur(function(){
		var val_ck_tname=$("#team_name").val().replace(" ","");//工序名称
		if(val_ck_tname==""||val_ck_tname==null)
		{
			$("#span_tip_tname").text("班组名称不能为空!");
			$("#team_name").val("");//清空，针对只有空格的
		}
		else
		{
			$("#span_tip_tname").text("");
			$("#team_name").val(val_ck_tname);//去空
		}
	});
	
	//选择产品信息
	$("#label_xfuname").click(function(){
		showpfactoryunit();//单元
	});
}

//查检提交
function sub_ck(val)
{
	$.post("team!getCk.action",{info:val},function(data){
		if(data.message=="s")
		{
			$("#inputForm").submit();
		}
		else if(data.message=="e")
		{
			//layer.alert("工序编号已存在,添加失败!",8,false);
			$("#span_tip_tcode").text("班组编码已存在!");
			return false;
		}
	},"json");
}
function showpfactoryunit()
{
	var title = "选择单元";
	var width="800px";
	var height="632px";
	var content="team!list2.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#xfuid").val(id[0]);//单元id
		$("#label_xfuname").text(id[1]);//单元名称
		layer.close(index); 
	});
}