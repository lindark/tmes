<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>产品管理 - Powered By ${systemConfig.systemName}</title>
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
					<li class="active">编辑相关Bom</li>
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
								action="products!savematerial.action"
								method="post">
								<input type="hidden" name="id" value="${id}" />
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">相关Bom管理</a>
										</li>

									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">产品编号</div>

												<div class="profile-info-value">
													<span class="editable editable-click">${products.productsCode}</span>
												</div>
											</div>
											
											<div class="profile-info-row">
												<div class="profile-info-name">产品名称</div>

												<div class="profile-info-value">
													<!--<i class="fa fa-map-marker light-orange bigger-110"></i>-->
													<span class="editable editable-click" id="username">${products.productsName}</span>
													<!--<span	 class="editable editable-click" id="country">Netherlands</span>-->
													<!--<span class="editable editable-click" id="city">Amsterdam</span>-->
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">物料组</div>

												<div class="profile-info-value">
													<!--<i class="fa fa-map-marker light-orange bigger-110"></i>-->
													<span class="editable editable-click" id="username">${products.materialGroup}</span>
													<!--<span	 class="editable editable-click" id="country">Netherlands</span>-->
													<!--<span class="editable editable-click" id="city">Amsterdam</span>-->
												</div>
											</div>
											
											<div class="profile-info-row">
												<div class="profile-info-name">相关Bom</div>

												<div class="profile-info-value">
													<select multiple="" name="ids" id="materialChose" class="chosen-select"> 
												        <#list allMaterial as list>
											            <option value="${list.id}">${list.materialName}</option>
										                </#list>   
												    </select>
												</div>
											</div>

										</div>
																				
										<div class="buttonArea">
											<input type="submit" class="formButton" value="确  定"
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
<script>
$(function(){
	$("#materialChose").find("option").each(function(){
		var materialVal = $(this).val();
		var materialid = "";
		<#list products.material as list>
			materialid = "${list.id}";
			if(materialVal == materialid){
				$(this).attr("selected","selected");
			}
		</#list>
		
	})
	$("#materialChose").trigger("chosen:updated");	
})
	
</script>
</html>