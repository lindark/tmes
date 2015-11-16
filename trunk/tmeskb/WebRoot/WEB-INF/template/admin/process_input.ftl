<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑工序管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<style type="text/css">
	.mymust{color: red;font-size: 10px;}
</style>
<script type="text/javascript">
$().ready( function() {

	// 地区选择菜单
	$(".areaSelect").lSelect({
		url: "${base}/admin/area!ajaxChildrenArea.action"// Json数据获取url
	});
	
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
</script>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body{background:#fff;}
</style>
</head>
<body class="no-skin input">
	
<!-- add by welson 0728 -->	
<#include "/WEB-INF/template/admin/admin_navbar.ftl">
<div class="main-container" id="main-container">
	<script type="text/javascript">
		try{ace.settings.check('main-container' , 'fixed')}catch(e){}
	</script>
	<#include "/WEB-INF/template/admin/admin_sidebar.ftl">
	<div class="main-content">
	<#include "/WEB-INF/template/admin/admin_acesettingbox.ftl">
	
	<!-- ./ add by welson 0728 -->
	
<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
		</script>

		<ul class="breadcrumb">
			<li>
				<i class="ace-icon fa fa-home home-icon"></i>
				<a href="admin!index.action">管理中心</a>
			</li>
			<li class="active"><#if isAdd??>添加工序记录<#else>编辑工序记录</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="<#if isAdd??>process!save.action<#else>process!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" id="xcode" value="${(process.processCode)!}" />
			<div id="inputtabs">
			<ul>
				<li>
					<a href="#tabs-1">工序信息</a>
				</li>
				
			</ul>
			
			<div id="tabs-1">
			
				<!--weitao begin modify-->
						<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<div class="profile-info-name"> <label class="mymust">*</label>工序编码 </div>					
										<div class="profile-info-value">
										<#if isAdd??>
											<input id="processcode" type="text" name="process.processCode" value="${(process.processCode)!}" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />&nbsp;&nbsp;<span id="span_code" class="mymust"></span>
											<!-- <input id="processcode" type="text" name="process.processCode" class="formText {required: true,minlength:2,maxlength: 100,processCode:true,remote:'process!checkProcesssCode.action',messages:{remote:'工序编码已存在'}}" /> -->
										<#else>
										    ${process.processCode}
										    <input id="processcode" type="hidden" name="process.processCode" value="${(process.processCode)!}"/><span id="span_code" style="display: none"></span>
										</#if>	
										</div>
									</div>	
									
									<div class="profile-info-row">	
										<div class="profile-info-name"> <label class="mymust">*</label>工序名称 </div>					
										<div class="profile-info-value">
											<input id="processname" type="text" name="process.processName" value="${(process.processName)!}" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />&nbsp;&nbsp;<span id="span_name" class="mymust"></span>
										</div>
									</div>
									
									<div class="profile-info-row">	
										<div class="profile-info-name"><label class="mymust">*</label> 产品名称 </div>					
										<div class="profile-info-value">
											<input type="text" />
										</div>
									</div>
									
									<div class="profile-info-row">
										<div class="profile-info-name"><label class="mymust">*</label> 状态</div>					
										<div class="profile-info-value">
											<label class="pull-left inline">
					                           <small class="muted smaller-90">已启用:</small>
						                       <input type="radio" class="ace" name="process.state" value="1"<#if (process.state == '1')!> checked</#if> />
						                       <span class="lbl middle"></span>
						                         &nbsp;&nbsp;
					                        </label>						
					                        <label class="pull-left inline">
					                            <small class="muted smaller-90">未启用:</small>
						                        <input type="radio" class="ace" name="process.state" value="2"<#if (isAdd || process.state == '2')!> checked</#if>  />
						                         <span class="lbl middle"></span>
					                        </label>		
										</div>	
									</div>							
						</div>
				
			<div class="buttonArea">
				<input id="btn_sub" type="button" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	
<!-- add by welson 0728 -->	
				</div><!-- /.col -->
				</div><!-- /.row -->

				<!-- PAGE CONTENT ENDS -->
			</div><!-- /.col -->
		</div><!-- /.row -->
	</div><!-- /.page-content-area -->
	<#include "/WEB-INF/template/admin/admin_footer.ftl">
</div><!-- /.page-content -->
				</div><!-- /.main-content -->
	</div><!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">	
	<!-- ./ add by welson 0728 -->


</body>
</html>