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
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/material_input.js"></script>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body{background:#fff;}
.img_addfu{cursor:pointer;margin-left:1px;}
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
											<input type="text" name="material.materialCode"/>
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
										<div class="profile-info-name"> 是否返修发货 </div>					
										<div class="profile-info-value">
										<#if isAdd??>
											<label><input name="material.isRepair" type="radio" checked="checked" value="1" />是 </label> &nbsp;&nbsp;
											<label><input name="material.isRepair" type="radio" value="2" />否 </label>
										<#else>
											<label><input name="material.isRepair" type="radio" value="1" <#if (material.isRepair == "1")!> checked</#if>>是 </label> &nbsp;&nbsp;
											<label><input name="material.isRepair" type="radio" value="2" <#if (material.isRepair == "2")!> checked</#if>>否 </label>
										</#if>
										</div>
									</div>
									
									<div class="profile-info-row">	
										<div class="profile-info-name"> 裁切倍数 </div>					
										<div class="profile-info-value">
											<input type="text" name="material.cqmultiple" value="${(material.cqmultiple)!}" class=" input input-sm formText" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" />
										</div>
									</div>
						
						             <div class="profile-info-row">
										<div class="profile-info-name">单元 </div>			
										<div class="profile-info-value">
											<img id="img_addfu" class="img_addfu" title="添加单元" alt="添加单元" src="${base}/template/shop/images/add_bug.gif" />
											<span id="span_fu">${(material.factoryunit.factoryUnitName)!}</span>
											<input type="hidden" id="input_fu" name="material.factoryunit.id" value="${(material.factoryunit.id)!}" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
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
				<input type="submit" id="btn_save" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
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