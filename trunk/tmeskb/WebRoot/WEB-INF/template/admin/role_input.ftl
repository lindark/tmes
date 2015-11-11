<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑角色 - Powered By ${systemConfig.systemName}</title>
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
					<li class="active"><#if isAdd??>添加角色<#else>编辑角色</#if></li>
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
								action="<#if isAdd??>role!save.action<#else>role!update.action</#if>"
								method="post">
								<input type="hidden" name="id" value="${id}" />

								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">基本信息</a></li>
										<li><a href="#tabs-2">分配资源</a></li>

									</ul>

									<table id="tabs-1" class="inputTable tabContent">
										<tr>
											<th>角色名称:</th>
											<td><input type="text" name="role.name"
												class="formText {required: true, remote: 'role!checkName.action?oldValue=${(role.name?url)!}', messages: {remote: '角色名称已存在!'}}"
												value="${(role.name)!}" /> <label class="requireField">*</label>
											</td>
										</tr>
										<tr>
											<th>角色标识:</th>
											<td><input type="text" name="role.value"
												class="formText {required: true, minlength: 6, prefix: 'ROLE_', remote: 'role!checkValue.action?oldValue=${(role.value?url)!}', messages: {remote: '角色标识已存在!'}}"
												value="${(role.value)!'ROLE_'}"
												title="角色标识长度不能小于6,且必须以ROLE_开头" /> <label
												class="requireField">*</label></td>
										</tr>
										<tr>
											<th>描述:</th>
											<td><input type="text" name="role.description"
												class="formText" value="${(role.description)!}" /></td>
										</tr>
										<#if (role.isSystem)!false>
										<tr>
											<th>&nbsp;</th>
											<td><span class="warnInfo"><span class="icon">&nbsp;</span>系统提示：</b>系统内置角色不允许修改!</span>
											</td>
										</tr>
										</#if>
									</table>
									<table id="tabs-2" class="inputTable tabContent">
										<tr>
											<td colspan="2"><#list allResource as list>
												<div style="width: 30%; float: left;">
													<label><input type="checkbox" name="resourceIds"
														value="${list.id}"<#if
														(role.resourceSet.contains(list) == true)!>
														checked="checked"</#if> />${(list.name)!}</label>
												</div> </#list></td>
										</tr>
									</table>
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