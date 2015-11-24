<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑车间管理 - Powered By ${systemConfig.systemName}</title>
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
					<li class="active"><#if isAdd??>添加车间记录<#else>编辑车间记录</#if></li>
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
								action="<#if isAdd??>work_shop!save.action<#else>work_shop!update.action</#if>"
								method="post">
								<input type="hidden" name="id" value="${id}" />
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">车间信息</a>
										</li>

									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
											
												<div class="profile-info-name">工厂名称</div>
												<div class="profile-info-value">
												    <#if isAdd??>
													<select class="chosen-select" name="factoryId" class="{required: true}"
														style="width:200px;">
														<option value="">请选择...</option> 
														<#list factoryList as list>
														<option value="${list.id}" <#if (list.id == workShop.factory.id)!> selected</#if>>${list.factoryName}</option>
														</#list>
													</select>
													<label class="requireField">*</label>
													<#else>
													${(workShop.factory.factoryName)!}
													<input type="hidden"
														name="factoryId"
														value="${(workShop.factory.id)!}" />
													</#if> 
												</div>
											</div> 
											
											<div class="profile-info-row">
												<div class="profile-info-name">车间编码</div>
												<div class="profile-info-value">
													<#if isAdd??> <input type="text" name="workShop.workShopCode"
														value="${(workShop.workShopCode)!}"
														class=" input input-sm  formText {required: true,minlength:2,maxlength: 100, remote: 'work_shop!checkworkShopCode.action', messages: {remote: '车间编码已存在!'}}" />
													<label class="requireField">*</label>
												    <#else>
													${(workShop.workShopCode)!} <input type="hidden"
														name="workShop.workShopCode"
														value="${(workShop.workShopCode)!}" /> </#if> 											
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">车间名称</div>
												<div class="profile-info-value">
													<input type="text" name="workShop.workShopName"
														value="${(workShop.workShopName)!}"
														class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>
											</div>


											<div class="profile-info-row">
												<div class="profile-info-name">状态</div>
												<div class="profile-info-value">
													<label class="pull-left inline"> <small
														class="muted smaller-90">已启用:</small> <input type="radio"
														class="ace" name="workShop.state" value="1"<#if
														(workShop.state == '1')!> checked</#if> /> <span
														class="lbl middle"></span> &nbsp;&nbsp; </label> <label
														class="pull-left inline"> <small
														class="muted smaller-90">未启用:</small> <input type="radio"
														class="ace" name="workShop.state" value="2"<#if
														(isAdd || workShop.state == '2')!> checked</#if> /> <span
														class="lbl middle"></span> </label>
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
</html>