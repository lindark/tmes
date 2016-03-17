<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑单元管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/unit.js"></script>
<script type="text/javascript">
$().ready( function() {

	// 地区选择菜单
	$(".areaSelect").lSelect({
		url: "${base}/admin/area!ajaxChildrenArea.action"// Json数据获取url
	});

});
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
			<li class="active"><#if isAdd??>添加单元记录<#else>编辑单元记录</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="<#if isAdd??>factory_unit!save.action<#else>factory_unit!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<div id="inputtabs">
			<ul>
				<li>
					<a href="#tabs-1">单元信息</a>
				</li>
				
			</ul>
			
			<div id="tabs-1">
			
				<!--weitao begin modify-->
						<div class="profile-user-info profile-user-info-striped">
						            <div class="profile-info-row">
										<div class="profile-info-name">车间名称</div>
										<div class="profile-info-value">
											<#if isAdd??><button type="button" class="btn btn-xs btn-info" id="userAddBtn" data-toggle="button">选择</button>			
				                            <input type="text" name="factoryworkShopId" value="" id="workShopId1" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" readonly="readonly"/>
				                            <input type="hidden" name="workShopId" id="workShopId2" value="" />
										    <label class="requireField">*</label>	
										    <#else>
										        ${(factoryUnit.workShop.factory.factoryName)!}     ${(factoryUnit.workShop.workShopName)!}   
										    </#if>
										</div>
									</div>
						            
									<div class="profile-info-row">
										<div class="profile-info-name"> 单元编码 </div>					
										<div class="profile-info-value">
										    <#if isAdd??>
										        <input type="text" name="factoryUnit.factoryUnitCode" value="${(factoryUnit.factoryUnitCode)!}" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100, remote: 'factory_unit!checkFactoryUnitCode.action', messages: {remote: '单元编码已存在!'}}" />
											    <label class="requireField">*</label>	
										    <#else>
										        ${(factoryUnit.factoryUnitCode)!}
										         <input type="hidden" name="factoryUnit.factoryUnitCode" value="${(factoryUnit.factoryUnitCode)!}"/>
										    </#if>										
										</div>
										
										<div class="profile-info-name"> 单元名称 </div>					
										<div class="profile-info-value">
											<input type="text" name="factoryUnit.factoryUnitName" value="${(factoryUnit.factoryUnitName)!}" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
											<label class="requireField">*</label>	
										</div>
									</div>	
									
									<div class="profile-info-row">	
										<div class="profile-info-name">配送仓库编码 </div>					
										<div class="profile-info-value">
											<input type="text" name="factoryUnit.psaddress" value="${(factoryUnit.psaddress)!}" class=" input input-sm  formText {required: true,digits:true}" />
											<label class="requireField">*</label>	
										</div>
										<div class="profile-info-name"> 配送仓库描述</div>					
										<div class="profile-info-value">
											<input type="text" name="factoryUnit.psaddressdes" value="${(factoryUnit.psaddressdes)!}" class=" input input-sm" />
											<!-- <label class="requireField">*</label>	 -->
										</div>
									</div>
									<div class="profile-info-row">	
										<div class="profile-info-name"> 线边仓编码 </div>					
										<div class="profile-info-value">
											<input type="text" name="factoryUnit.warehouse" value="${(factoryUnit.warehouse)!}" class=" input input-sm  formText {required: true,digits:true}" />
											<label class="requireField">*</label>	
										</div>
										<div class="profile-info-name"> 线边仓描述</div>					
										<div class="profile-info-value">
											<input type="text" name="factoryUnit.warehouseName" value="${(factoryUnit.warehouseName)!}" class=" input input-sm" />
										</div>
									</div>
									<div class="profile-info-row">
										<div class="profile-info-name"> 工作中心</div>					
										<div class="profile-info-value">
											<input type="text" name="factoryUnit.workCenter" value="${(factoryUnit.workCenter)!}" id="workCenter" class=" input input-sm  formText {required: true}" />
											<label class="requireField">*</label>	
										</div>
										<div class="profile-info-name">成本中心</div>					
										<div class="profile-info-value">
											<input type="text" name="factoryUnit.costcenter" value="${(factoryUnit.costcenter)!}" id="costcenter" class=" input input-sm  formText {required: true}" />
											<label class="requireField">*</label>	
										</div>
									</div>
									
									<div class="profile-info-row">
										<div class="profile-info-name">是否可以返修/返修收货</div>					
										<div class="profile-info-value">
											<label class="pull-left inline">
					                           <small class="muted smaller-90">是:</small>
						                       <input type="radio" id="input_y" class="ace" name="factoryUnit.iscanrepair" value="Y"<#if (factoryUnit.iscanrepair == 'Y')!> checked</#if> />
						                       <span class="lbl middle"></span>
						                         &nbsp;&nbsp;
					                        </label>						
					                        <label class="pull-left inline">
					                            <small class="muted smaller-90">否:</small>
						                        <input type="radio" id="input_n" class="ace" name="factoryUnit.iscanrepair" value="N"<#if (isAdd ||factoryUnit.iscanrepair == 'N')!> checked</#if>  />
						                         <span class="lbl middle"></span>
					                        </label>		
										</div>
										<div class="profile-info-name">配送仓位编码</div>					
										<div class="profile-info-value">
											<input type="text" name="factoryUnit.psPositionAddress" value="${(factoryUnit.psPositionAddress)!}" class=" input input-sm  formText {required: true}" />
											<label class="requireField">*</label>	
										</div>
									</div>
									<div class="profile-info-row">
										<div class="profile-info-name">成型/挤压</div>					
										<div class="profile-info-value">
											<select id="cxorjc" name="factoryUnit.CXORJC" class="input input-sm form-control" style="width:150px;display: inline;">
												<option value="">&nbsp;</option>
												<#if list_cxorjc??>
													<#list list_cxorjc as xlist>
														<option value="${(xlist.dictkey)! }" <#if (xlist.dictkey==factoryUnit.CXORJC)!>selected</#if>>${(xlist.dictvalue)! }</option>
													</#list>
												</#if>
											</select>
											<span id="span_cxorjc" style="color: red;"></span>
										</div>
										<!-- 
										<div class="profile-info-value">
											<label class="pull-left inline">
					                           <small class="muted smaller-90">成型:</small>
						                       <input type="radio" class="ace" name="factoryUnit.CXORJC" value="CX"<#if (factoryUnit.CXORJC == 'CX')!> checked</#if> />
						                       <span class="lbl middle"></span>
						                         &nbsp;&nbsp;
					                        </label>						
					                        <label class="pull-left inline">
					                            <small class="muted smaller-90">挤出:</small>
						                        <input type="radio" class="ace" name="factoryUnit.CXORJC" value="JC"<#if (factoryUnit.CXORJC == 'JC')!> checked</#if>  />
						                         <span class="lbl middle"></span>
					                        </label>		
										</div>
										 -->
										 <div class="profile-info-name">待发货仓位</div>					
										<div class="profile-info-value">
											<input type="text" name="factoryUnit.delivery" value="${(factoryUnit.delivery)!}" class=" input input-sm  formText {required: true}" />
											<label class="requireField">*</label>	
										</div>
									</div>
									<div class="profile-info-row">
										<div class="profile-info-name"> 状态</div>					
										<div class="profile-info-value">
											<label class="pull-left inline">
					                           <small class="muted smaller-90">已启用:</small>
						                       <input type="radio" class="ace" name="factoryUnit.state" value="1"<#if (factoryUnit.state == '1')!> checked</#if> />
						                       <span class="lbl middle"></span>
						                         &nbsp;&nbsp;
					                        </label>						
					                        <label class="pull-left inline">
					                            <small class="muted smaller-90">未启用:</small>
						                        <input type="radio" class="ace" name="factoryUnit.state" value="2"<#if (isAdd || factoryUnit.state == '2')!> checked</#if>  />
						                         <span class="lbl middle"></span>
					                        </label>		
										</div>	
									</div>
								</div>
								<div class="buttonArea">
									<input type="button" id="input_btn" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
								</div>
							</div>							
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
<script type="text/javascript">
$(function() {
	$("#workCenter").change(function(){
		var reg = /^[0-9]+([,][0-9]+)*$/;
		var workCenter = $(this).val();
		//alert(workCenter);
		if(!reg.test(workCenter)){
			layer.msg("输入不合法!", {icon: 5});
			$(this).val("");
		}
	});
	$("#input_n").click(function(){
		$("#span_cxorjc").text("");
	});
	$("#input_btn").click(function(){
		var select_val=$("#cxorjc").val();
		if($("#input_y").attr("checked")&&select_val=="")
		{
			$("#span_cxorjc").text("可以返修或返修收货时,此内容为必填项！");
		}
		else
		{
			$("#inputForm").submit();
		}
	});
});
</script>