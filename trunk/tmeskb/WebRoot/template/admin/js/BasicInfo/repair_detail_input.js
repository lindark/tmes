
$(function(){
	
	//表单监控
//	form_ck();
	//提交事件,判断工序名是否为空
	$("#btn_sub").click(function(){
//		var processcode=$("#processcode").val().replace(" ","");//工序编码
		var repairname=$("#repairname").val();//工序名称
		//var productsval=$("#product_name").text().replace(" ","");//产品产品名称
//		var xcode=$("#xcode").val();//工序编码,用于判断：1.不为空表示修改，可以为原名不用判断是否存在，  2.为空表示新增，需要判断是否已存在
		//alert(processcode+","+processname);
//		if(processcode==""||processcode==null)
//		{
//			$("#span_code").text("工序编码不能为空!");
//		}
//		else
//		{
//			$("#span_code").text("");
//		}
		if(repairname==""||repairname==null)
		{
			$("#span_name").text("返修部位名称不能为空!");
			return false;
		}
		$("#inputForm").submit();
//		else
//		{
//			$("#span_name").text("");
//		}
//		if((processcode==""||processcode==null)||(processname==""||processname==null))
//		{
//			//layer.alert("必填数据不能为空,请查看!",8,false);
//		}
//		else
//		{
//			$("#processcode").val(processcode);//去空
//			$("#processname").val(processname);//去空
//			//新增
//			if(xcode==null||xcode=="")
//			{
//				sub_ck(processcode);
//			}
//			else
//			{
//				//修改，因为工序编码为只读不需要判断，如果需要判断的时候用注释部分的
//				$("#inputForm").submit();
//				/*
//				if(xcode==processcode)
//				{
//					$("#inputForm").submit();
//				}
//				else
//				{
//					sub_ck(processcode);
//				}
//				*/
//				
//			}
//		}
	});
});
//表单监控
//function form_ck()
//{
//	$("#processcode").blur(function(){
//		var pcode=$("#processcode").val().replace(" ","");//工序编码
//		if(pcode==""||pcode==null)
//		{
//			$("#span_code").text("工序编码不能为空!");
//			$("#processcode").val("");//清空，针对只有空格的
//		}
//		else
//		{
//			$("#span_code").text("");
//			$("#processcode").val(pcode);//去空
//		}
//	});
//	$("#processname").blur(function(){
//		var pname=$("#processname").val().replace(" ","");//工序名称
//		if(pname==""||pname==null)
//		{
//			$("#span_name").text("工序名称不能为空!");
//			$("#processname").val("");//清空，针对只有空格的
//		}
//		else
//		{
//			$("#span_name").text("");
//			$("#processname").val(pname);//去空
//		}
//	});
//	
//	//选择产品信息
//	$("#product_name").click(function(){
//		showproducts();
//	});
//}

//查检提交
function sub_ck(val)
{
	$.post("process!getCk.action",{info:val},function(data){
		if(data.message=="s")
		{
			$("#inputForm").submit();
		}
		else if(data.message=="e")
		{
			//layer.alert("工序编号已存在,添加失败!",8,false);
			$("#span_code").text("工序编码已存在!");
		}
	},"json");
}
function showproducts()
{
	var title = "选择产品";
	var width="800px";
	var height="632px";
	var content="process!list2.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#productsId").val(id[0]);//产品id
		$("#productsName").val(id[1]);//产品名称
		$("#product_name").text(id[1]);
		layer.close(index); 
	});
}