<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>单元同步管理- Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
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
			<li class="active"><#if isAdd??>添加单元<#else>编辑单元</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="<#if isAdd??>factory_unit_syn!save.action<#else>factory_unit_syn!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<div id="inputtabs">
			<ul>
				<li>
					<a href="#tabs-1">添加同步单元</a>
				</li>
				
			</ul>
			
			<div id="tabs-1">
			
				<!--weitao begin modify-->
						<div class="profile-user-info profile-user-info-striped">
						            <div class="profile-info-row">
										<div class="profile-info-name"> 单元编码 </div>					
										<div class="profile-info-value">
											<input type="text" name="factoryUnitSyn.factoryUnitCode" value="${(factoryUnitSyn.factoryUnitCode)!}" class="formText {digits:true,required: true,minlength:2,maxlength: 100,productsCode:true,remote:'factory_unit_syn!checkFactoryUnitCode.action?id=${id }',messages:{remote:'物料编码已存在'}}" />
											<label class="requireField">*</label>
										</div>
									</div>	
						            <div class="profile-info-row">	
										<div class="profile-info-name"> 单元描述 </div>					
										<div class="profile-info-value">
											<input type="text" name="factoryUnitSyn.factoryUnitName" value="${(factoryUnitSyn.factoryUnitName)!}" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
											<label class="requireField">*</label>	
										</div>
									</div>
									<div class="profile-info-row">
										<input type="hidden" name="factoryUnitSyn.cronexpression" value="0 0/10 * * * ?">
										<!-- <div class="profile-info-name"> 同步周期 </div>					
										<div class="profile-info-value">
											<input type="text" name="factoryUnitSyn.cronexpression" value="${(factoryUnitSyn.cronexpression)!}" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
											<label class="requireField">*</label>	
										</div> -->
										<div class="profile-info-name"> 状态</div>					
										<div class="profile-info-value">
											<label class="pull-left inline">
					                           <small class="muted smaller-90">已启用:</small>
						                       <input type="radio" class="ace" name="factoryUnitSyn.state" value="1"<#if (isAdd || factoryUnitSyn.state == '1')!> checked</#if> />
						                       <span class="lbl middle"></span>
						                         &nbsp;&nbsp;
					                        </label>						
					                        <label class="pull-left inline">
					                            <small class="muted smaller-90">未启用:</small>
						                        <input type="radio" class="ace" name="factoryUnitSyn.state" value="2"<#if (factoryUnitSyn.state == '2')!> checked</#if>  />
						                         <span class="lbl middle"></span>
					                        </label>		
										</div>	
									</div>
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