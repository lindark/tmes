<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑单元分配模具- Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript">
	$().ready(function() {

		// 地区选择菜单
		$(".areaSelect").lSelect({
			url : "${base}/admin/area!ajaxChildrenArea.action"// Json数据获取url
		});

	});
</script>
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
}
</style>
</head>
<body class="no-skin input">

	<!-- add by welson 0728 -->
	<#include "/WEB-INF/template/admin/admin_navbar.ftl">
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>
		<#include "/WEB-INF/template/admin/admin_sidebar.ftl">
		<div class="main-content">
			<#include "/WEB-INF/template/admin/admin_acesettingbox.ftl">

			<!-- ./ add by welson 0728 -->

			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {
					}
				</script>

				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a
						href="admin!index.action">管理中心</a>
					</li>
					<li class="active"><#if isAdd??>添加单元分配模具<#else>编辑单元分配模具</#if></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm" class="validate"
								action="<#if isAdd??>unitdistribute_model!save.action<#else>unitdistribute_model!update.action</#if>"
								method="post">
								<input type="hidden" id="input_id" name="id" value="${(unitdistributeModel.id)!}" />
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">相关信息</a>
										</li>

									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
											
												<div class="profile-info-name">单元名称</div>
												<div class="profile-info-value">
												    <#if isAdd??>
														<img id="unitId" class="img_addbug" title="添加单元信息" alt="添加单元信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />
														<span id="unitName"></span> <input type="hidden"
														name="unitdistributeModel.unitName" id="unitNa" value=""
														class="formText {required: true}" />
														<input type="hidden"
														name="unitdistributeModel.factoryunit.id" id="input_fuid" value=""
														class="formText {required: true}" /> 
														<label class="requireField">*</label>
													<#else>
														<img id="unitId" class="img_addbug" title="添加单元信息" alt="添加单元信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />
														<span id="unitName">${(unitdistributeModel.factoryunit.factoryUnitName)! }</span> 
														<input type="hidden" name="unitdistributeModel.unitName" id="unitNa" value="${(unitdistributeModel.factoryunit.factoryUnitName)! }"class="formText {required: true}" />
														
														<input type="hidden" name="unitdistributeModel.factoryunit.id" id="input_fuid" value="${(unitdistributeModel.factoryunit.id)! }" class="formText {required: true}" /> 
														<label class="requireField">*</label>
													
														
													</#if>
												</div>
											</div> 
											
											<div class="profile-info-row">
												<div class="profile-info-name">模具组号</div>
												<div class="profile-info-value">
													<input id="input_statioin" type="text" name="unitdistributeModel.station"
														value="${(unitdistributeModel.station)!}"
														class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
													<label class="requireField">*</label>
												    											
												</div>
											</div>

											<!--  <div class="profile-info-row">
												<div class="profile-info-name">物料名称</div>
												<div class="profile-info-value">
													<input type="text" name="unitdistributeModel.materialName"
														value="${(unitdistributeModel.materialName)!}"
														class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>
											</div>-->


											<div class="profile-info-row">
												<div class="profile-info-name">状态</div>
												<div class="profile-info-value">
													<label class="pull-left inline"> <small
														class="muted smaller-90">已启用:</small> <input type="radio"
														class="ace" name="unitdistributeModel.state" value="1"<#if
														(unitdistributeModel.state == '1')!> checked</#if> /> <span
														class="lbl middle"></span> &nbsp;&nbsp; </label> <label
														class="pull-left inline"> <small
														class="muted smaller-90">未启用:</small> <input type="radio"
														class="ace" name="unitdistributeModel.state" value="2"<#if
														(isAdd || unitdistributeModel.state == '2')!> checked</#if> /> <span
														class="lbl middle"></span> </label>
												</div>
											</div>
										</div>
																				
										<div class="buttonArea">
											<input id="btn_sub" type="button" class="formButton" value="确  定"
												hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp; <input
												type="button" class="formButton"
												onclick="window.history.back(); return false;" value="返  回"
												hidefocus="true" />
										</div>
							</form>

							<!-- add by welson 0728 -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content-area -->
		<#include "/WEB-INF/template/admin/admin_footer.ftl">
	</div>
	<!-- /.page-content -->
	</div>
	<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->


</body>
<script type="text/javascript">
$(function() {	
	// 单元弹出框
	$("#unitId").click( function() {
		showUnit();
	});
	//提交
	$("#btn_sub").click(function(){
		//验证
		ck_event();
	});
});
//单元弹出框
function showUnit()
{
	var title = "选择单元";
	var width="800px";
	var height="632px";
	var content="unitdistribute_product!browser.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#unitName").text(id[1]);
		$("#unitNa").val(id[1]);//单元名称
		$("#input_fuid").val(id[0]);
		layer.close(index); 
	});
}
//验证
function ck_event()
{
	var fuid=$("#input_fuid").val();//单元id
	var umstation=$("#input_statioin").val();//模具组号
	umstation=umstation.replace(/\s+/g,"");//去空
	$("#input_statioin").val(umstation);
	if(fuid!=null&&fuid!=""&&umstation!=null&&umstation!="")
	{
		$.post("unitdistribute_model!checkinfo.action?unitdistributeModel.factoryunit.id="+fuid+"&unitdistributeModel.station="+umstation+"&id="+$("#input_id").val(),function(data){
			if(data.status=="success")
			{
				$("#inputForm").submit();
			}
			else
			{
				layer.alert("单元和模具组号已同时存在!", {
			        closeBtn: 0,
			        icon:5,
			        skin:'error'
			    },function(){
			    	layer.closeAll();
				});
			}
		},"json");
	}
	else
	{
		$("#inputForm").submit();
	}
}
</script>
</html>