$(function(){
	//数量输入事件
	//num_event();
	//返回
	$("#btn_back").click(function(){
		window.history.back();
	});
});

//输入是否合法
function isright_event(xid)
{
	var reg=/^[0-9]+(\.[0-9]+)?$/;//整数或小数
	var input_num=$("#"+xid).val().replace(/\s+/g,"") //返修数量
	if(reg.test(input_num))
	{
		input_num=setScale(input_num,0,"");//精度
		$("#"+xid).val(input_num);
	}
	else
	{
		layer.alert("输入不合法!",false);
		$("#"+xid).val("");
	}
}

//刷卡保存
function save_event(url)
{
	var dt = $("#inputForm").serialize();
	credit.creditCard(url,function(data){
		if(data.status=="success")
		{
    		layer.alert(data.message, {icon: 6},function(){
    			window.location.href = "sark!list.action";
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