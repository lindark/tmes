<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑工模维修单 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/unusual/js/model.js"></script>
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
					<li class="active"><#if isAdd??>添加工模维修单<#else>编辑工模维修单</#if></li>
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
								action="<#if isAdd??>model!save.action<#else>model!update.action</#if>"
								method="post">
								<input type="hidden" name="id" value="${id}" /> <input
									type="hidden" name="abnormalId" value="${(abnormal.id)!}" />
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
												<div class="profile-info-name">产品名称</div>

												<div class="profile-info-value">
													<#if isAdd??>
													<button type="button" class="btn btn-xs btn-info"
														id="productId" data-toggle="button">选择</button>
													<input type="text" name="model.products.productsName"
														id="productName1"
														class=" input input-sm  formText {required: true}"
														readOnly="true" /> <input type="hidden"
														name="model.products.id" id="productNa" value="" />
													<#else> ${(model.products.productsName)!} </#if>
												</div>

												<div class="profile-info-name">班组</div>

												<div class="profile-info-value">
												   <#if isAdd??>
												       <input type="text" name="model.teamId.teamName"
														value="${(admin.department.team.teamName)!}"
														class=" input input-sm  formText {required: true}" />
													   <input type="hidden" name="model.teamId.id" value="${(admin.department.team.id)!}"/>
												    <#else>
												       ${(model.teamId.teamName)!}												       
												    </#if>									
												</div>


											</div>

											<div class="profile-info-row">

												<div class="profile-info-name">种类</div>

												<div class="profile-info-value">
													<select name="model.type">
													     <#list allState as list>
								                             <option value="${list.dictkey}"<#if ((isAdd && list.isDefault) || (isEdit && model.type == list.dictkey))!> selected</#if>>${list.dictvalue}</option>
							                             </#list> 
													</select>
																							
												</div>

												<div class="profile-info-name">提报人</div>
												<div class="profile-info-value">
													<#if isAdd??> <input type="text"
														name="model.initiator.name"
														value="${(abnormal.iniitiator.name)!}"
														class=" input input-sm  formText {required: true}" /> <input
														type="hidden" name="model.initiator.id"
														value="${(abnormal.iniitiator.id)!}"
														class=" input input-sm  formText {required: true}" />
													<#else> ${(model.initiator.name)!} </#if>

												</div>
											</div>

							
										
										
							

											<div class="profile-info-row">
												<div class="profile-info-name">通知时间</div>
												<div class="profile-info-value">
													<input type="text" name="model.noticeTime"
														value="${(model.noticeTime)!}"
														class="formText {required: true} datePicker" />
												</div>
												<div class="profile-info-name">到场时间</div>
												<div class="profile-info-value">
													<input type="text" name="model.arriveTime"
														value="${(model.arriveTime)!}"
														class="formText {required: true} datePicker" />
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">维修人员</div>
												<div class="profile-info-value">
													<input type="text" name="model.fixer"
														value="${(model.fixer)!}"
														class=" input input-sm  formText {required: true}" />
												</div>
												<div class="profile-info-name">维修时间</div>
												<div class="profile-info-value">
													<input type="text" name="model.fixTime"
														value="${(model.fixTime)!}"
														class=" input input-sm  formText {required: true}" />
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">检验员</div>
												<div class="profile-info-value">
													<input type="text" name="model.insepector"
														value="${(model.insepector)!}"
														class=" input input-sm  formText {required: true}" />
												</div>
												<div class="profile-info-name">确认时间</div>
												<div class="profile-info-value">
													<input type="text" name="model.confirmTime"
														value="${(model.confirmTime)!}"
														class="formText {required: true} datePicker" />
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">故障原因</div>
												<div class="profile-info-value">
													<input type="text" name="model.faultCause"
														value="${(model.faultCause)!}"
														class=" input input-sm  formText {required: true}" />
												</div>
												<div class="profile-info-name">处理方法与结果</div>
												<div class="profile-info-value">
													<input type="text" name="model.resolve"
														value="${(model.resolve)!}"
														class="input input-sm  formText {required: true}" />
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">长期预防措施</div>
												<div class="profile-info-value">
													<input type="text" name="model.measure"
														value="${(model.measure)!}"
														class=" input input-sm  formText {required: true}" />
												</div>
											</div>

										</div>

										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">不良现象描述</div>
												<div class="profile-info-value">
													<textarea name="model.failDescript" style="width:600px;">${(model.failDescript)!} </textarea>
												</div>
											</div>
										</div>
										</form>
										
                                    <div class="buttonArea">
                                    
                                    <#if isAdd??>
									<button class="btn btn-white btn-default btn-sm btn-round" id="completeModel" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
									</button>&nbsp;&nbsp;	
									<#else>
									</#if>	
									<#if isAdd??><#else>								
									<button class="btn btn-white btn-default btn-sm btn-round" id="checkModel" type=button>
										<i class="ace-icon glyphicon glyphicon-ok"></i>
										刷卡回复
									</button>&nbsp;&nbsp;
									<button class="btn btn-white btn-default btn-sm btn-round" id="confirmModel" type=button>
										<i class="ace-icon glyphicon glyphicon-play-circle"></i>
										刷卡确认
									</button>&nbsp;&nbsp;
									<button class="btn btn-white btn-default btn-sm btn-round" id="closeModel" type=button>
										<i class="ace-icon fa fa-cloud-upload"></i>
										刷卡关闭
									</button>&nbsp;&nbsp;
									</#if>
									<button class="btn btn-white btn-default btn-sm btn-round" id="returnModel" type=button>
										<i class="ace-icon fa fa-home"></i>
										返回
									</button>
										<!-- <input type="submit" class="formButton" value="确  定"
											hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp; <input
											type="button" class="formButton"
											onclick="window.history.back(); return false;" value="返  回"
											hidefocus="true" /> -->
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
											<#list (model.modelLogSet)! as list>
											<tr>
												<td>${(list.createDate)!}</td>
												<td>${(list.info)!}</td>
												<td>${(list.operator)!}</td>
											</tr>
											</#list>
										</tbody>
									</table>

									<table id="tabs-3" class="inputTable tabContent">

									</table>

								

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