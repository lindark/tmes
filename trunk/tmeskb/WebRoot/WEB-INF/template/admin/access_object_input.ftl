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
					<li class="active"><#if isAdd??>添加权限对象<#else>编辑权限对象</#if></li>
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
								action="<#if isAdd??>access_object!save.action<#else>access_object!update.action</#if>"
								method="post">
								<input type="hidden" name="id" value="${id}" />

								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">功能权限管理</a></li>
									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
										<div class="profile-info-row">
												<div class="profile-info-name">权限对象Key</div>

												<div class="profile-info-value">
													<input type="text" name="accessObject.accObjkey"
														value="${(accessObject.accObjkey)! }"
														class=" input input-sm  formText {required: true}" />
												</div>

											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">权限对象描述</div>

												<div class="profile-info-value">
													<input type="text" name="accessObject.accObjName"
														value="${(accessObject.accObjName)! }"
														class=" input input-sm  formText {required: true}" />
												</div>

											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">权限对象类型</div>

												<div class="profile-info-value">
													<select class="select formText {required: true} chosen-select" name="accessObject.type" id="accessType">
														 <option value="">请选择...</option>
														<#list	allDict as list>
														<option value="${list.dictkey }" <#if (list.dictkey ==accessObject.type)!> selected</#if>>${list.dictvalue }</option>
														</#list>
													</select>
													
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">所属资源</div>

												<div class="profile-info-value">
													<select class="chosen-select formText {required: true,messagePosition:'#resMessagePosition'}" data-placeholder="请选择..." name="accessObject.resource.id">
																	<option value=""></option>
																	<#list	allRes as list>
																		<option value="${list.id}" <#if (list.id ==accessObject.resource.id)!> selected</#if>>${(list.name)! }</option>
																	</#list>
													</select>
													<label id="resMessagePosition"></label>
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
		</div>
		<!-- /.page-content-area -->
	<#include "/WEB-INF/template/admin/admin_footer.ftl">
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->

</body>

</html>

