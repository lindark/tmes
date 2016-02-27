var xid = "";//单元主键ID
var xcode="";//物料编码
$(function() {
	var $tab = $("#tb_material");//随工单table
	$tab.find("tr").click(function(){
		var battr = $(this).eq(0).find("input.ckbox").is(":checked");
		if(battr == false){
			$(this).eq(0).find("input.ckbox").prop("checked",true);
		}else if(battr == true){
			$(this).eq(0).find("input.ckbox").removeAttr("checked");
		}
	});
	
	$(".ckbox").click(function(){
		event.stopPropagation();
	});
	//按钮事件
	btn_event();
});
/**
 * 获取checkbox的ID值
 */
function getCKboxById() {
	xid = "";
	xcode="";
	var ishead = 0;
	$(".ckbox:checked").each(function(){
		var sId = $(this).val();
		if (ishead == 1)
		{
			xid="";
		}
		else
		{
			xid = sId;
			ishead = 1;
			xcode=$(this).parent().find("span").text();
			xcodedes=$(this).parent().parent().eq(1).find("span").text();
		}
	});
	if(xid=="")
	{
		return false;
	}
	return true;
}

//按钮事件
function btn_event()
{
	//添加物料
	$("#btn_add").click(function(){
		add_event();
	});
	//返回
	$("#btn_back").click(function(){
		//window.history.back();
		window.location.href="dump!all.action";
	});
}

//添加物料
function add_event()
{
	var psaddress_val=$("#input_psaddress").val();//配送地点
	var jsaddress_val=$("#input_jsaddress").val();//接收地点
	if(psaddress_val==null||psaddress_val=="")
	{
		layer.alert("配送地点为空,操作失败!",false);
	}
	else
	{
		if(jsaddress_val==null||jsaddress_val=="")
		{
			layer.alert("接收地点为空,操作失败!",false);
		}
		else
		{
			if(!getCKboxById())
			{
				layer.alert("请选择一条数据进行操作 !",false);
			}
			else
			{
				toadd_event();
			}
		}
	}
}

//打开添加物料页面
function toadd_event()
{
	var url="";
	var dumpid=$("#dumpid").val();
	if(dumpid==null||dumpid=="")
	{
		url="dump!beforeaddbatch.action?fuid="+xid+"&materialcode="+xcode;
	}
	else
	{
		url="dump!beforetoupd.action?fuid="+xid+"&materialcode="+xcode+"&id="+dumpid;
	}
	window.location.href=url;
	/*layer.open({
		type:2,
		title:"添加物料",
		//skin: 'layui-layer-lan',
		area: ["90%", "90%"],
		shade:0.52,
		shadeClose:false,
		move:false,
		content:url,
		closeBtn:1,
		btn:["关闭"]
	});*/
}
















