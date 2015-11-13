<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑工艺维修单 - Powered By ${systemConfig.systemName}</title>
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
					<li class="active"><#if isAdd??>添加工艺维修单<#else>编辑工艺维修单</#if></li>
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
								action="<#if isAdd??>craft!save.action<#else>craft!update.action</#if>"
								method="post">
								<input type="hidden" name="id" value="${id}" />
                                <input type="hidden" name="abnormalId" value="${(abnormal.id)!}" />
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">基本信息</a></li>
										<li><a href="#tabs-2">单据日志</a></li>
										<li><a href="#tabs-3">相关单据</a></li>
									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">产品编号</div>

												<div class="profile-info-value">
													<select name="craft.productNo">
														<option value="1">40010021</option>
														<option value="2">40010021</option>
													</select>
												</div>
												<div class="profile-info-name">产品名称</div>

												<div class="profile-info-value">
													<input type="text" name="craft.productsName"
														value="${(craft.productsName)!}"
														class=" input input-sm  formText {required: true}" />
												</div>

											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">班组</div>

												<div class="profile-info-value">
												    <#if isAdd??>
												       <input type="text" name="craft.classes"
														value="${(abnormal.teamId)!}"
														class=" input input-sm  formText {required: true}" />
												    <#else>
												       <input type="text" name="craft.classes"
														value="${(craft.classes)!}"
														class=" input input-sm  formText {required: true}" />
												    </#if>									
												</div>
												<div class="profile-info-name">机台号</div>

												<div class="profile-info-value">
													<select name="craft.cabinetCode">
														<option value="1">4003</option>
														<option value="2">4002</option>
													</select>
												</div>
											</div>

                                          </div>

											<div class="profile-user-info profile-user-info-striped">
												<div class="profile-info-row">
													<div class="profile-info-name">制造异常描述</div>
													<div class="profile-info-value">
													     <textarea name="craft.unusualDescription_make" style="width:600px;">${(craft.unusualDescription_make)!} </textarea>			
													</div>
												</div>
											


											
												<div class="profile-info-row">
													<div class="profile-info-name">制造处理措施</div>
													<div class="profile-info-value">
													    <textarea name="craft.unusualDescription_process" style="width:600px;">${(craft.unusualDescription_process)!} </textarea>								
													</div>
												</div>
											


										
												<div class="profile-info-row">
													<div class="profile-info-name">制造处理结果</div>
													<div class="profile-info-value">
													    <textarea name="craft.resultCode_process" style="width:600px;">${(craft.resultCode_process)!} </textarea>				
													</div>
												</div>
											</div>


											<div class="profile-user-info profile-user-info-striped">
												<div class="profile-info-row">
													<div class="profile-info-name">工艺异常分析</div>
													<div class="profile-info-value">
													     <textarea name="craft.unusualDescription_process" style="width:600px;">${(craft.unusualDescription_process)!} </textarea>												
													</div>
												</div>

												<div class="profile-info-row">
													<div class="profile-info-name">工艺处理措施</div>
													<div class="profile-info-value">
													  <textarea name="craft.treatmentMeasure_make" style="width:600px;">${(craft.treatmentMeasure_make)!} </textarea>													
													</div>
												</div>
												<div class="profile-info-row">
													<div class="profile-info-name">工艺处理结果</div>
													<div class="profile-info-value">
													    <textarea name="craft.resultCode_process" style="width:600px;">${(craft.resultCode_process)!} </textarea>												
													</div>
												</div>
											</div>
										

										<!--weitao end modify-->


									</div>

									<table id="tabs-2" class="inputTable tabContent">
										<tbody>
											<tr class="title">
												<th>时间</th>
												<th>内容</th>
												<th>修改人</th>
											</tr>
											<tr>
												<td>2015-09-16 09:20</td>
												<td>张三已刷卡</td>
												<td>张三</td>
											</tr>
										</tbody>
									</table>

									<table id="tabs-3" class="inputTable tabContent">

									</table>

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