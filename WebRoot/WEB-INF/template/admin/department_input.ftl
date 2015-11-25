<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>部门管理 - Powered By ${systemConfig.systemName}</title>
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
<script type="text/javascript"
	src="${base}/template/admin/js/browser/browserValidate.js"></script>
<script type="text/javascript"
	src="${base }/template/admin/js/jquery.cxselect-1.3.7/js/jquery.cxselect.min.js"></script>
</head>
<body class="no-skin input">

	<!-- add by welson 0728 -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>
		<div class="main-content">

			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form type="post"
								action="<#if isAdd??>department!save.action<#else>department!update.action</#if>"
								id="departform" class="validateajax">
								<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<input type="hidden" name="department.id"
											value="${(department.id)!}" />
										<!--id-->
										<div class="profile-info-name">部门名称</div>

										<div class="profile-info-value">
											<input type="text" name="department.deptName"
												class=" input input-sm  formText {required: true}"
												value="${(department.deptName)!}" />
										</div>

									</div>
									<div class="profile-info-row">
										<div class="profile-info-name">上级部门</div>

										<div class="profile-info-value">
											<#if isAdd??> <input type="hidden"
												name="department.parentDept.id" value="${(pid)!}" /> <span>${(pname)!}</span>
											<#else> <input type="hidden" name="department.parentDept.id"
												value="${(department.parentDept.id)!}" /> <span>${(department.parentDept.deptName)!}</span>
											</#if>
										</div>

									</div>
									<div class="profile-info-row">
										<div class="profile-info-name">所属班组</div>

										<div class="profile-info-value">
											<div class="example">
												<fieldset id="self-data">
													<div class="form">
														<select name="factoryid" class="factory select"  data-first-title="---选择工厂---" data-url="department!getFactory.action" data-json-space="factory"></select>
														<select name="workshopid"	class="workshop select"  data-first-title="---选择车间---" data-url="department!getWorkshop.action" data-json-space="workshop"></select>
														<select name="factoryunitid"	class="factoryunit select"  data-first-title="---选择单元---" data-url="department!getFactoryunit.action" data-json-space="factoryunit"></select>
														<select class="team select" disabled="disabled" data-first-title="---选择班组---" data-url="department!getTeam.action" data-json-space="team"></select>
													</div>
												</fieldset>
											</div>
										</div>

									</div>

								</div>
								<div class="buttonArea" style="display:none">
									<input type="submit" class="formButton" id="submit_btn"
										value="确  定" hidefocus="true" />
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
	</div>
	<!-- /.page-content -->
	</div>
	<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->

</body>
<script type="text/javascript">

	$(function() {
		
		$("#self-data").cxSelect({
			selects: ['factory', 'workshop', 'factoryunit','team'],
			jsonName: 'name',
			jsonValue: 'value'
		},function(select){//回调
			$(select).trigger("chosen:updated"); 
			$(select).chosen({allow_single_deselect:true,no_results_text:"没有找到",search_contains: true});
		});	
		
		
	})

</script>

</html>

