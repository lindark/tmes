<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>物料管理- Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
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
			<li class="active"><#if isAdd??>添加物料<#else>编辑物料</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="<#if isAdd??>material!save.action<#else>material!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<div id="inputtabs">
			<ul>
				<li>
					<a href="#tabs-1">物料管理</a>
				</li>
				
			</ul>
			
			<div id="tabs-1">
			
				<!--weitao begin modify-->
						<div class="profile-user-info profile-user-info-striped">
						            <div class="profile-info-row">
										<div class="profile-info-name"> 物料编码 </div>					
										<div class="profile-info-value">
										<#if isAdd??>
											<input type="text" name="material.materialCode" class="formText {digits:true,required: true,minlength:2,maxlength: 100,productsCode:true,remote:'material!checkMaterialCode.action',messages:{remote:'物料编码已存在'}}" />
											<label class="requireField">*</label>
										<#else>
										    ${material.materialCode}
										    <input type="hidden" name="material.materialCode" value="${(material.materialCode)!}"/>
										</#if>	
										</div>
									</div>	
																		
						            <div class="profile-info-row">	
										<div class="profile-info-name"> 物料描述 </div>					
										<div class="profile-info-value">
											<input type="text" name="material.materialName" value="${(material.materialName)!}" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
											<label class="requireField">*</label>	
										</div>
									</div>
									
						
						             <div class="profile-info-row">
										<div class="profile-info-name"> 工厂 </div>					
										<div class="profile-info-value">
											<select class="chosen-select" name="material.factory.id" class="{required: true}"
														style="width:200px;">
														<option value="">请选择...</option> 
														<#list factoryList as list>
														<option value="${list.id}" <#if (list.id == material.factory.id)!> selected</#if>>${list.factoryCode} ${list.factoryName}</option>
														</#list>
											</select>
											
											
											<label class="requireField">*</label>	
										</div>
									</div>	
									
								  <div class="profile-info-row">	
										<div class="profile-info-name"> 生产管理员 </div>					
										<div class="profile-info-value">
											<input type="text" name="material.productmanager" value="${(material.productmanager)!}" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
											<label class="requireField">*</label>	
										</div>
								</div> 
									
						</div>
				
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
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