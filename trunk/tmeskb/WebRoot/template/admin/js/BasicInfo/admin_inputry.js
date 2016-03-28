$(function(){
	//页面右上角点击登录人事件
	login_event();
	
	//直接上级img_boss
	$("#img_boss").click(function(){
		boss_event();
	});
	
	//部门编码img_dept
	$("#img_dept").click(function(){
		dept_event();
	});
	
	//岗位img_post
	$("#img_post").click(function(){
		post_event();
	});
	
	//班组img_team
	$("#img_team").click(function(){
		team_event();
	});
	//单元img_faunit
	$("#img_faunit").click(function(){
		faunit_event();
	});
	//提交
	$("#btn_submit").click(function(){
		sub_event();
	});
	
	//返回
	$("#btn_return").click(function(){
		return_event();
	});
	
	//工号,卡号change()事件
	numchange_event();
});

//直接上级
function boss_event()
{
	layer.open({
        type: 2,
        skin: 'layui-layer-lan',
        shift:2,
        title: "直接上级",
        fix: false,
        shade: 0.5,
        shadeClose: true,
        maxmin: true,
        scrollbar: false,
        btn:['确认','取消'],
        area: ["80%", "80%"],//弹出框的高度，宽度
        content:"admin!beforegetemp.action",
        yes:function(index,layero){//确定
        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        	var info = iframeWin.getGridId();
        	if(info!="baga")
        	{
        		$("#input_boss").val(info.empid);
        		$("#span_boss").text(info.name);
        		layer.close(index);
        	}
        	return false;
        },
        no:function(index)
        {
        	layer.close(index);
        	return false;
        }
    });
	return false;
}
//部门
function dept_event()
{
	layer.open({
        type: 2,
        skin: 'layui-layer-lan',
        shift:2,
        title: "选择部门",
        fix: false,
        shade: 0.5,
        shadeClose: true,
        maxmin: true,
        scrollbar: false,
        btn:['确认','取消'],
        area: ["300px", "400px"],//弹出框的高度，宽度
        content:"department!browser.action",
        yes:function(index,layero){//确定
        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        	var depart = iframeWin.getName();
        	if(depart!=null&&depart!="")
        	{
        		$("#input_dept").val(depart.departid);//部门id
            	$("#span_deptname").text(depart.departName);//部门名称
            	$("#span_deptcode").text(depart.deptcode);//部门编码
        	}
        	layer.close(index);
        	return false;
        },
        no:function(index)
        {
        	layer.close(index);
        	return false;
        }
    });
	return false;
}

//岗位
function post_event()
{
	layer.open({
        type: 2,
        skin: 'layui-layer-lan',
        shift:2,
        title: "选择岗位",
        fix: false,
        shade: 0.5,
        shadeClose: true,
        maxmin: true,
        scrollbar: false,
        btn:['确认','取消'],
        area: ["75%", "75%"],//弹出框的高度，宽度
        content:"post!beforegetpost.action",
        yes:function(index,layero){//确定
        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        	var info = iframeWin.getName();
        	if(info!="baga")
        	{
        		$("#input_post").val(info.postid);
            	$("#span_postname").text(info.postname);
            	//$("#span_workstation").text(info.station);
            	//获取对应的工位
            	getstation(info.postid);
            	layer.close(index);
        	}
        	return false;
        },
        no:function(index)
        {
        	layer.close(index);
        	return false;
        }
    });
	return false;
}

//班组
function team_event()
{
	layer.open({
        type: 2,
        skin: 'layui-layer-lan',
        shift:2,
        title: "选择班组",
        fix: false,
        shade: 0.5,
        shadeClose: true,
        maxmin: true,
        scrollbar: false,
        btn:['确认','取消'],
        area: ["80%", "80%"],//弹出框的高度，宽度
        content:"team!beforegetalllist.action",
        yes:function(index,layero){//确定
        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        	var info = iframeWin.getName();
        	$("#input_team").val(info.teamid);
        	$("#span_teamname").text(info.teamname);
        	layer.close(index);
        	return false;
        },
        no:function(index)
        {
        	layer.close(index);
        	return false;
        }
    });
	return false;
}
//单元
function faunit_event()
{
	layer.open({
        type: 2,
        skin: 'layui-layer-lan',
        shift:2,
        title: "选择单元",
        fix: true,
        shade: 0.5,
        shadeClose: true,
        maxmin: true,
        scrollbar: false,
        btn:['确认','取消'],
        area: ["80%", "80%"],//弹出框的高度，宽度
        content:"factory_unit!factoryunitlist.action",
        yes:function(index,layero){//确定
        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        	var info = iframeWin.getName();
        	$("#infoId").val(info.faunid);
        	$("#infoName").val(info.faunname);
        	$("#infoNames").text(info.faunname);
        	layer.close(index);
        	return false;
        },
        no:function(index)
        {
        	layer.close(index);
        	return false;
        }
    });
	return false;
}
//页面右上角点击登录人事件
function login_event()
{
	var ishead=0;
	$("#ace-settings-btn").click(function(){
		if(ishead==0){
			ishead=1;
			$("#ace-settings-box").addClass("open");
		}else{
			ishead=0;
			$("#ace-settings-box").removeClass("open");
		}
	});
	$(".btn-colorpicker").click(function(){
			$(".dropdown-colorpicker").addClass("open");
	});
	
	var ishead2=0;
	$(".light-blue").click(function(){
		if(ishead2==0){
			ishead2=1;
			$(this).addClass("open");
		}else{
			ishead2=0;
			$(this).removeClass("open");
		}
		
	});
}

//提交
function sub_event()
{
	var worknum=$("#input_worknum").val();//工号
	var cardnum=$("#input_cardnum").val();//卡号
	if(worknum!=null&&worknum!=""&&cardnum!=null&&cardnum!="")
	{
		var url="admin!checknum.action?worknumber="+worknum+"&cardnumber="+cardnum+"&id="+$("#adminid").val();
		$.post(url,function(data){
			if(data.message=="w")
			{
				//只有工号重复
				$("#span_worknum").text("工号已存在!");
			}
			else if(data.message=="c")
			{
				//只有卡号重复
				$("#span_cardnum").text("卡号已存在!");
			}
			else if(data.message=="wc")
			{
				//工号卡号都重复
				$("#span_worknum").text("工号已存在!");
				$("#span_cardnum").text("卡号已存在!");
			}
			else if(data.message=="success")
			{
				//工号卡号都没有重复,可以提交
				$("#xform").submit();
			}
			else
			{
				//系统出现异常
				layer.alert("系统出现异常!",{icon:5, skin:"error"});
			}
		},"json");
	}
	else
	{
		$("#xform").submit();
	}
}

//工号,卡号change()事件
function numchange_event()
{
	$("#input_worknum").change(function(){
		var worknum=$("#input_worknum").val();//工号
		worknum=worknum.replace(/\s+/g,"");//工号去空
		$("#input_worknum").val(worknum);
		$("#span_worknum").text("");
	});
	$("#input_cardnum").change(function(){
		var cardnum=$("#input_cardnum").val();//卡号
		cardnum=cardnum.replace(/\s+/g,"");//卡号去空
		$("#input_cardnum").val(cardnum);
		$("#span_cardnum").text("");
	});
}

//返回
function return_event()
{
	window.location.href="admin!alllistry.action";
}
//获取工位
function getstation(postid)
{
	$.post("admin!getstation.action?postid="+postid,function(data){
		$("#sel_station option").remove();
		$("#sel_station").append(data.message);
		$("#sel_station").chosen();
		$("#sel_station").trigger("chosen:updated");
	},"json");
}