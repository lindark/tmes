<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>权限对象管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
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
						href="admin!index.action">管理中心</a></li>
					<li class="active">编辑权限对象</li>
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
								action="access_resource!saveaccess.action"
								method="post">
								<input type="hidden" name="id" value="${id}" />

								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">权限对象管理</a></li>

									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="widget-box transparent">
													<div class="widget-header">
														<h5 class="widget-title bigger lighter">按钮</h5>
													</div>

													<div class="widget-body">
														<div class="widget-main no-padding">
															<ul class="list-unstyled list-striped pricing-table-header">
																<li>
																	<#list accessResource.resource.accessobjectSet as list>
																		<label>
																			<input type="checkbox" class="ace" name="accessobjectlist.id" class="{required: true}" value="${list.id}" <#if (accessResource.accessobjectSet.contains(list) == true)!>checked="checked"</#if> />
																			<span class="lbl"> ${(list.accObjName)!}</span>
																		</label>
																	</#list>
																</li>
															</ul>
														</div>
													</div>
												</div>
										<div class="widget-box transparent">
													<div class="widget-header">
														<h5 class="widget-title bigger lighter">下拉框</h5>
													</div>

													<div class="widget-body">
														<div class="widget-main no-padding">
															<ul class="list-unstyled list-striped pricing-table-header">
																<li>Disk Space</li>
															</ul>
														</div>
													</div>
												</div>
										<div class="widget-box transparent">
											<div class="widget-header">
												<h5 class="widget-title bigger lighter">菜单</h5>
											</div>

											<div class="widget-body">
												<div class="widget-main no-padding">
													<ul class="list-unstyled list-striped pricing-table-header">
														<li>${(btnhtml)! }</li>
													</ul>
												</div>
											</div>
										</div>
										<!--weitao end modify-->


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
			<#include "/WEB-INF/template/admin/admin_footer.ftl">
		</div>
		<!-- /.page-content-area -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->

</body>
</html>

