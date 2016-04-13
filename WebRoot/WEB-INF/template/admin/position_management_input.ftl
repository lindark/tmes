<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑单元仓库管理 - Powered By ${systemConfig.systemName}</title>
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
			<li class="active"><#if isAdd??>添加单元仓库记录<#else>编辑单元仓库记录</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="<#if isAdd??>position_management!save.action<#else>position_management!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<div id="inputtabs">
			<ul>
				<li>
					<a href="#tabs-1">单元仓库信息</a>
				</li>
				
			</ul>
			
			<div id="tabs-1">
			
				<!--weitao begin modify-->
						<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<!--  
										<div class="profile-info-name"> 单元</div>					
										<div class="profile-info-value">
										        <input type="text" name="positionManagement.element" value="${(positionManagement.element)!}" class=" input input-sm  formText {required: true}" />											   											
										</div>
										-->
										<div class="profile-info-name">单元信息</div>
										<div class="profile-info-value">
										    <#if isAdd??>
											<img id="unitId" class="img_addbug" title="添加单元信息" alt="添加单元信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />
											<span id="unitName"></span> 
											<input type="hidden" name="positionManagement.factoryUnit.factoryUnitName"
											 id="unitNa" value="" class="formText {required: true}" />
											<input type="hidden" name="positionManagement.factoryUnit.id" 
											id="unitNo" value="" class="formText {required: true}" /> <#else>
											${(positionManagement.factoryUnit.factoryUnitName)!} </#if>
										</div>
									</div>	
									
									<!--  
									<div class="profile-info-row">	
										<div class="profile-info-name"> 单元 </div>					
										<div class="profile-info-value">
											<input type="text" name="positionManagement.element" value="${(positionManagement.element)!}" class=" input input-sm  formText {required: true}" />	
										</div>
										
									</div>
									
									-->
									<div class="profile-info-row">	
										<div class="profile-info-name"> 仓库地点 </div>					
										<div class="profile-info-value">
											<input type="text" name="positionManagement.warehouse" value="${(positionManagement.warehouse)!}" class=" input input-sm  formText {required: true}" />	
										</div>
										
									</div>
									
									 <div class="profile-info-row">	
										<div class="profile-info-name"> 超市仓库</div>					
										<div class="profile-info-value">
											<input type="text" name="positionManagement.supermarketWarehouse" value="${(positionManagement.supermarketWarehouse)!}" class=" input input-sm  formText {required: true}" />	
										</div>
										
									</div>	
									
									 <div class="profile-info-row">	
										<div class="profile-info-name"> 裁切仓库</div>					
										<div class="profile-info-value">
											<input type="text" name="positionManagement.trimWareHouse" value="${(positionManagement.trimWareHouse)!}" class=" input input-sm" />	
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
<script type="text/javascript">
$(function() {	
	
	// 单元弹出框
	$("#unitId").click( function() {
		showUnit();
	})
	
	
	function showUnit(){
	var title = "选择单元";
	var width="800px";
	var height="632px";
	var content="position_management!browser.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		$("#unitName").text(id[1]);
		$("#unitNa").val(id[1]);//单元名称
		$("#unitNo").val(id[0]);
		layer.close(index); 
	});
}
})
</script>
</html>