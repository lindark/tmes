$(function(){
	//部门编码事件
	$("#input_deptcode").change(function(){
		code_event();
	});
	//提交
	$("#btn_submit").click(function(){
		check_event();
	});
	//返回
	$("#btn_return").click(function(){
		window.location.href="department!alllist.action";
	});
	//选择上级部门
	getdept_event();
	//页面右上角点击登录人事件
	login_event();
	//选择部门负责人
	getemp_event();
});

//部门编码事件
function code_event()
{
	var deptcode=$("#input_deptcode").val();
	deptcode=deptcode.replace(/\s+/g,"");//去空
	$("#input_deptcode").val(deptcode);
	if(deptcode!=null&&deptcode!="")
	{
		$.post("department!checkcode.action?deptcode="+deptcode+"&id="+$("#deptid").val(),function(data){
			if(data.status=="success")
			{
				$("#span_code").text("");
			}
			else if(data.status=="error")
			{
				$("#span_code").text("部门编码已存在!");
			}
		},"json");
	}
}

//提交及验证
function check_event()
{
	var deptcode=$("#input_deptcode").val();
	var deptid=$("#deptid").val();
	$.post("department!checkcode.action?deptcode="+deptcode+"&id="+deptid,function(data){
		if(data.status=="success")
		{
			$("#span_code").text("");
			$("#inputForm").submit();
		}
		else if(data.status=="error")
		{
			$("#span_code").text("部门编码已存在!");
		}
	},"json");
}

//选择上级部门
function getdept_event()
{
	var $dept= $(".img_adddept");//部门选择功能img_adddept
	$("#img_adddept").click(function(){
		var $this = $(this);
		$this.attr("disabled",true);
		var content = "department!browser.action?id="+$("#deptid").val();
		var title="部门选择";
		var width="300px";
		var height = "400px";
		
		//var offsetleft = $(this).offset().left;
		//var offsettop = $(this).offset().top;
		//var kjheight = $(this).height();
		layer.open({
	        type: 2,
	        skin: 'layui-layer-lan',
	        shift:2,
	        //offset:[offsettop+kjheight+"px",offsetleft+"px"],
	        title: title,
	        fix: false,
	        shade: 0.5,
	        shadeClose: true,
	        maxmin: true,
	        scrollbar: false,
	        btn:['确认','取消'],
	        //btn:['确认'],
	        area: [width, height],//弹出框的高度，宽度
	        content:content,
	        yes:function(index,layero){//确定
	        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
	        	var depart = iframeWin.getName();
	        	//alert(depart.departid+","+depart.departName);
	        	$("#input_dept").val(depart.departid);
	        	$("#span_dept").text(depart.departName);
	        	layer.close(index);
	        	return false;
	        },
	        no:function(index)
	        {
	        	layer.close(index);
	        	return false;
	        },
	        end:function(index,layero){
	        	$this.removeAttr("disabled");
	        }
	        
	    });
		return false;
	});
}

//选择部门负责人
function getemp_event()
{
	$("#img_addleader").click(function(){
		var title = "添加代班员工";
		var width="800px";
		var height="620px";
		var content="admin!beforegetemp.action";
		var html="";
		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
			var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
			var info=iframeWin.getGridId();
			if(info!="baga")
			{
				$("#input_leader").val(info.empid);//员工ID
				$("#span_leader").text(info.name);//员工姓名
				layer.closeAll();
			}
		});
	});
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