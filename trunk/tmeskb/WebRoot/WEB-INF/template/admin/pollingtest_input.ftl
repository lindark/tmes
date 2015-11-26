<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>巡检管理 - Powered By ${systemConfig.systemName}</title>
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

.div-name {
	text-align: center;
}

.div-value {
	padding-right: 30px;
	min-width: 200px;
}

.div-value2 {
	text-align: right;
	padding-right: 0px;
	min-width: 200px;
}

.input-value {
	width: 80px;
	height: 30px;
	line-height: 30px;
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
					<li class="active">巡检单</li>
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
								action="<#if isAdd??>pollingtest!save.action<#else>pollingtest!update.action</#if>"
								method="post">
								<input type="hidden" name="id" value="${(id)!}" /> <input
									type="hidden" class="input input-sm"
									name="pollingtest.workingbill.id" value="${workingbill.id} ">
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">巡检单</a></li>

									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">随工单</div>

												<div class="profile-info-value">
													<span>${workingbill.workingBillCode}</span>
												</div>

											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">产品编号</div>

												<div class="profile-info-value">
													<span>${workingbill.matnr}</span>
												</div>
												<div class="profile-info-name">产品名称</div>

												<div class="profile-info-value">
													<span>${workingbill.maktx}</span>
												</div>

											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">巡检数量</div>
												<div class="profile-info-value">
													<input type="text" name="pollingtest.pollingtestAmount"
														value="${(pollingtest.pollingtestAmount)!}"
														class=" input input-sm formText {required: true,min: 0}" />
												</div>

												<div class="profile-info-name">硫化时间</div>
												<div class="profile-info-value">
													<input type="text" name="pollingtest.curingTime1"
														style="width:10%" value="${(pollingtest.curingTime1)!}"
														class=" input input-sm formText {required: true,min: 0}" />
													<input type="text" name="pollingtest.curingTime2"
														style="width:10%" value="${(pollingtest.curingTime2)!}"
														class=" input input-sm formText {required: true,min: 0}" />
													<input type="text" name="pollingtest.curingTime3"
														style="width:10%" value="${(pollingtest.curingTime3)!}"
														class=" input input-sm formText {required: true,min: 0}" />
													<input type="text" name="pollingtest.curingTime4"
														style="width:10%" value="${(pollingtest.curingTime4)!}"
														class=" input input-sm formText {required: true,min: 0}" />
													<span>&nbsp;&nbsp;&nbsp;/秒</span>
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">工艺确认</div>
												<div class="profile-info-value">
													<select name="pollingtest.craftWork" id="form-field-icon-1">
														<option value="">---请选择---</option> <#list allCraftWork as
														list>
														<option value="${list.dictkey}"<#if ((isAdd &&
															list.isDefault) || (isEdit && pollingtest.craftWork ==
															list.dictkey))!> selected</#if>>${list.dictvalue}</option>
														</#list>
													</select>
												</div>

												<div class="profile-info-name">固化时间</div>
												<div class="profile-info-value">
													<input type="text" name="pollingtest.settingTime1"
														style="width:10%" value="${(pollingtest.settingTime1)!}"
														class=" input input-sm formText {required: true,min: 0}" />
													<input type="text" name="pollingtest.settingTime2"
														style="width:10%" value="${(pollingtest.settingTime2)!}"
														class=" input input-sm formText {required: true,min: 0}" />
													<input type="text" name="pollingtest.settingTime3"
														style="width:10%" value="${(pollingtest.settingTime3)!}"
														class=" input input-sm formText {required: true,min: 0}" />
													<input type="text" name="pollingtest.settingTime4"
														style="width:10%" value="${(pollingtest.settingTime4)!}"
														class=" input input-sm formText {required: true,min: 0}" />
													<span>&nbsp;&nbsp;&nbsp;/秒</span>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">尺寸1</div>
												<div class="profile-info-value">
													<input type="text" name="pollingtest.size1"
														value="${(pollingtest.size1)!}"
														class=" input input-sm formText {required: true,min: 0}" />
												</div>

												<div class="profile-info-name">尺寸2</div>
												<div class="profile-info-value">
													<input type="text" name="pollingtest.size2"
														value="${(pollingtest.size2)!}"
														class=" input input-sm formText {required: true,min: 0}" />
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">尺寸3</div>
												<div class="profile-info-value">
													<input type="text" name="pollingtest.size3"
														value="${(pollingtest.size3)!}"
														class=" input input-sm formText {required: true,min: 0}" />
												</div>

												<div class="profile-info-name">合格数量</div>
												<div class="profile-info-value">
													<input type="text" name="pollingtest.qualifiedAmount"
														value="${(pollingtest.qualifiedAmount)!}"
														class=" input input-sm formText {required: true,min: 0}" />
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">合格率</div>
												<div class="profile-info-value">
													<span class="editable editable-click" id="age">100%</span>
												</div>
											</div>
										</div>
										<!--weitao end modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row ceshi">
												<div class="profile-info-value">&nbsp;</div>
											</div>
											<div class="profile-info-name div-name">不合格原因</div>
											<div class="profile-info-row ceshi">
												<div class="profile-info-value div-value">
													<#assign num=0 /> <#list list_cause as list>
													<div class="col-md-2 col-xs-6 col-sm-3 div-value2">
														<input id="sr_id" type="hidden" value="${(list.id)! }" />
														<label>${(list.causeName)! }</label> <input
															id="sr_num${num}" type="text" value=""
															class=" input-value" /> <input id="sr_num2${num}"
															type="hidden" value="" />
													</div>
													<#assign num=num+1 /> </#list>
												</div>
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