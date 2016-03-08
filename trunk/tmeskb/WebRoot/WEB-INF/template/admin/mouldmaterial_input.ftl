<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑模具物料管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
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
			<li class="active"><#if isAdd??>添加模具物料记录<#else>编辑模具物料记录</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="<#if isAdd??>mouldmaterial!save.action<#else>mouldmaterial!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<div id="inputtabs">
			<ul>
				<li>
					<a href="#tabs-1">模具物料信息</a>
				</li>
				
			</ul>
			
			<div id="tabs-1">
			
				<!--weitao begin modify-->
						<div class="profile-user-info profile-user-info-striped">
									 	<div class="profile-info-row">
										<div class="profile-info-name"> 工厂</div>					
										<div class="profile-info-value">
										        <input type="text" name="mouldmaterial.factory" value="${(mouldmaterial.factory)!}" class=" input input-sm  formText {required: true}" />											   											
										</div>
										<div class="profile-info-name"> 计量点 </div>					
										<div class="profile-info-value">
											<input type="text" name="mouldmaterial.point" value="${(mouldmaterial.point)!}" class=" input input-sm  formText {required: true}" />	
										</div>
									</div>	
								
									
									<div class="profile-info-row">	
										<div class="profile-info-name"> 编码 </div>					
										<div class="profile-info-value">
											<input type="text" name="mouldmaterial.code" value="${(mouldmaterial.code)!}" class=" input input-sm  formText {required: true}" />	
										</div>
										<div class="profile-info-name"> 描述 </div>					
										<div class="profile-info-value">
											<input type="text" name="mouldmaterial.describe" value="${(mouldmaterial.describe)!}" class=" input input-sm  formText {required: true}" />								
										</div>
									</div>
									
									 <div class="profile-info-row">	
										<div class="profile-info-name"> 物料编码</div>					
										<div class="profile-info-value">
											<input type="text" name="mouldmaterial.materialCode" value="${(mouldmaterial.materialCode)!}" class=" input input-sm  formText {required: true}" />	
										</div>
										<div class="profile-info-name"> 特征</div>					
										<div class="profile-info-value">
											<input type="text" name="mouldmaterial.feature" value="${(mouldmaterial.feature)!}" class=" input input-sm  formText {required: true}" />	
										</div>
									</div>	
									
									 <div class="profile-info-row">	
										<div class="profile-info-name"> 总数量</div>					
										<div class="profile-info-value">
											<input type="text" name="mouldmaterial.totalAmount" value="${(mouldmaterial.totalAmount)!}" class=" input input-sm" />	
										</div>
										<div class="profile-info-name"> 年数量</div>					
										<div class="profile-info-value">
											<input type="text" name="mouldmaterial.yearAmount" value="${(mouldmaterial.yearAmount)!}" class=" input input-sm" />								
										</div>
									</div>										
									
									 <div class="profile-info-row">	
									     <div class="profile-info-name"> 文本</div>					
										<div class="profile-info-value">
											<input type="text" name="mouldmaterial.text" value="${(mouldmaterial.text)!}" class=" input input-sm" />	
										</div>
									 </div>
									 
									<div class="profile-info-row">
										<div class="profile-info-name"> 是否启用</div>					
										<div class="profile-info-value">
											<label class="pull-left inline">
					                           <small class="muted smaller-90">已启用:</small>
						                       <input type="radio" class="ace" name="mouldmaterial.state" value="1"<#if (mouldmaterial.state == '1')!> checked</#if> />
						                       <span class="lbl middle"></span>
						                         &nbsp;&nbsp;
					                        </label>						
					                        <label class="pull-left inline">
					                            <small class="muted smaller-90">未启用:</small>
						                        <input type="radio" class="ace" name="mouldmaterial.state" value="2"<#if (isAdd || mouldmaterial.state == '2')!> checked</#if>  />
						                         <span class="lbl middle"></span>
					                        </label>		
										</div>	
									</div>					
						</div>								
			</table>
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