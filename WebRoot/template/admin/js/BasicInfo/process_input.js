
$(function(){
	
	//表单监控
	form_ck();
	//提交事件,判断工序名是否为空
	$("#btn_sub").click(function(){
		var processcode=$("#processcode").val().replace(" ","");//工序编码
		var processname=$("#processname").val().replace(" ","");//工序名称
		var xcode=$("#xcode").val();//工序编码,用于判断：1.不为空表示修改，可以为原名不用判断是否存在，  2.为空表示新增，需要判断是否已存在
		if((processcode==""||processcode==null)||(processname==""||processname==null))
		{
			layer.alert("必填数据不能为空,请查看!",8,false);
		}
		else
		{
			$("#processcode").val(processcode);//去空
			$("#processname").val(processname);//去空
			//新增
			if(xcode==null||xcode=="")
			{
				sub_ck(processcode);
			}
			else
			{
				//修改，因为工序编码为只读不需要判断，如果需要判断的时候用注释部分的
				$("#inputForm").submit();
				/*
				if(xcode==processcode)
				{
					$("#inputForm").submit();
				}
				else
				{
					sub_ck(processcode);
				}
				*/
				
			}
		}
	});
});
//表单监控
function form_ck()
{
	$("#processcode").blur(function(){
		var pcode=$("#processcode").val().replace(" ","");//工序编码
		if(pcode==""||pcode==null)
		{
			$("#span_code").text("工序编码不能为空!");
			$("#processcode").val("");//清空，针对只有空格的
		}
		else
		{
			$("#span_code").text("");
			$("#processcode").val(pcode);//去空
		}
	});
	$("#processname").blur(function(){
		var pname=$("#processname").val().replace(" ","");//工序名称
		if(pname==""||pname==null)
		{
			$("#span_name").text("工序名称不能为空!");
			$("#processname").val("");//清空，针对只有空格的
		}
		else
		{
			$("#span_name").text("");
			$("#processname").val(pname);//去空
		}
	});
	
	//选择产品信息
	$("#product_name").click(function(){
		showproducts();
	});
}

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
			layer.alert("工序编号已存在,添加失败!",8,false);
			$("#span_code").text("工序编码已存在!");
		}
	},"json");
}
var $productsId=$("#productsId");
var $productsName=$("#productsName");
function showproducts()
{
	var title = "选择产品";
	var width="800px";
	var height="500px";
	var content="products!ajlist.action";//"process!browser.action";//"process!getProductsList.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$productsId.val(id[0]);//产品id
		$productsName.val(id[1]);//产品名称
		layer.close(index); 
	});
	/*layer.open({
		type:2,
		area:['800px','500px'],
		fix:false,//不固定
		title:false,
		content:'process!findByPagerAndValue.action'
	});*/
	//var win=window.showModalDialog(url,window);
	/*var win=window.showModalDialog("process!findByPagerAndValue.action","");
	win.location.reload();*/
}


/*var $userAddBtn = $("#userAddBtn");//添加用户
var $workShopId=$("#workShopId1");
var $workShopId2=$("#workShopId2");
*//**
* 添加按钮点击
*//*
$userAddBtn.click(function(){
	
	var title = "车间";
	var width="800px";
	var height="600px";
	var content="work_shop!browser.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
		
   var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
   var work = iframeWin.getGridId();
   var id=work.split(",");
   $workShopId.val(id[0]);
   $workShopId2.val(id[1]);
   layer.close(index);            	          	     	
	});
	
	
});
*/