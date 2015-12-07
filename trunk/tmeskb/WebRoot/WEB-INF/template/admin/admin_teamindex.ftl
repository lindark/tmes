 <#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>管理中心 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<link rel="stylesheet"
	href="${base}/template/admin/assets/css/jquery.gritter.css" />
<script src='${base}/template/admin/assets/js/jquery.min.js'></script>
<script src="${base}/template/admin/js/Main/teamindex.js"></script>
<script src="${base}/template/admin/assets/js/jquery.gritter.min.js"></script>
<script src="${base}/template/admin/js/layer/layer.js"></script>
</head>
<body class="no-skin">

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

			<!-- #section:basics/content.breadcrumbs -->
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {
					}
				</script>

				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">管理中心</a>
					</li>
					<li class="active">首页</li>
				</ul>
				<!-- /.breadcrumb -->



				<!-- /section:basics/content.searchbox -->
			</div>

			<!-- /section:basics/content.breadcrumbs -->
			<div class="page-content">


				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">

							<div class="row">

								<div class="col-xs-12 col-sm-6 widget-container-col">
									<!-- #section:custom/widget-box -->
									<div class="widget-box transparent">
										<div class="widget-header">
											<h4 class="widget-title lighter">班组信息</h4>
											<div class="widget-toolbar no-border">
												<a href="#" data-action="collapse"> <i
													class="ace-icon fa fa-chevron-up"></i> </a>

											</div>
										</div>

										<div class="widget-body">
											<div
												class="widget-main padding-6 no-padding-left no-padding-right">
												<div class="profile-user-info profile-user-info-striped">
													<div class="profile-info-row">
														<div class="profile-info-name">工厂：</div>
														<div class="profile-info-value">
															${(admin.department.team.factoryUnit.workShop.factory.factoryName)!
															}</div>
													</div>
													<div class="profile-info-row">
														<div class="profile-info-name">车间：</div>
														<div class="profile-info-value">
															${(admin.department.team.factoryUnit.workShop.workShopName)!
															}</div>
													</div>
													<div class="profile-info-row">
														<div class="profile-info-name">单元：</div>
														<div class="profile-info-value">
															${(admin.department.team.factoryUnit.factoryUnitName)! }</div>
													</div>
													<div class="profile-info-row">
														<div class="profile-info-name">班组：</div>
														<div class="profile-info-value">
															${(admin.department.team.teamName)! }</div>
													</div>
												</div>
											</div>
										</div>
									</div>

									<!-- /section:custom/widget-box -->
								</div>

								<!--按钮组start-->
								<div class="col-xs-12 col-sm-6 widget-container-col">
									<div class="widget-box transparent">
										<div class="widget-header">
											<h4 class="widget-title lighter">按钮组</h4>
											<div class="widget-toolbar no-border">
												<a href="#" data-action="collapse"> <i
													class="ace-icon fa fa-chevron-up"></i> </a>

											</div>
										</div>

										<div class="widget-body">
											<div
												class="widget-main padding-6 no-padding-left no-padding-right">
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="handover">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block"
														id="handoverprocess">
														<i class="ace-icon fa fa-fire bigger-110"></i> <span
															class="bigger-110 no-text-shadow">交接</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="getmaterial">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="pick">
														<i class="ace-icon fa fa-credit-card bigger-110"></i> <span
															class="bigger-110 no-text-shadow">领退料</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="dumpconfirm">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block"
														id="dump">
														<i class="ace-icon fa fa-credit-card bigger-110"></i> <span
															class="bigger-110 no-text-shadow">转储确认</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="scrap">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled" type="button" id="scrap">
														<i class="ace-icon fa fa-inbox bigger-110"></i> <span
															class="bigger-110 no-text-shadow">报废</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="rework">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="rework">
														<i class="ace-icon fa fa-exchange bigger-110"></i> <span
															class="bigger-110 no-text-shadow">返工</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="repair">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="repair">
														<i class="ace-icon fa fa-cog bigger-110"></i> <span
															class="bigger-110 no-text-shadow">返修</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="repairgoods">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="repairin">
														<i class="ace-icon fa fa-glass bigger-110"></i> <span
															class="bigger-110 no-text-shadow">返修收货</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="sample">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="sample">
														<i class="ace-icon fa fa-flag bigger-110"></i> <span
															class="bigger-110 no-text-shadow">抽检</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="inspection">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="pollingtest">
														<i class="ace-icon fa fa-leaf bigger-110"></i> <span
															class="bigger-110 no-text-shadow">巡检</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="halfinspection">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="halfinspection">
														<i class="ace-icon fa fa-star-half-o bigger-110"></i> <span
															class="bigger-110 no-text-shadow">半成品巡检</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="quickresponse">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block"
														id="qResponse">
														<i class="ace-icon fa fa-volume-up bigger-110"></i> <span
															class="bigger-110 no-text-shadow">快速响应</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="sttm">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="dailywork">
														<i class="ace-icon fa fa-share-alt bigger-110"></i> <span
															class="bigger-110 no-text-shadow">报工</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="putstorage">
													<button
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="storage">
														<i class="ace-icon fa fa-tasks bigger-110"></i> <span
															class="bigger-110 no-text-shadow">入库</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="attendance">
													<button
														class="btn btn-white btn-success btn-bold btn-round btn-block">
														<i class="ace-icon fa fa-users bigger-110"></i> <span
															class="bigger-110 no-text-shadow">考勤</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="cartonreceiving">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="carton">
														<i class="ace-icon fa fa-file-archive-o bigger-110"></i> <span
															class="bigger-110 no-text-shadow">纸箱收货</span>
													</button>
												</div>

											</div>
										</div>
									</div>

								</div>
								<!--按钮组end-->
							</div>
							<!-- /section:custom/extra.hr -->
							<div class="row">
								<div class="col-sm-12">
									<div class="widget-box transparent">
										<div class="widget-header widget-header-flat">
											<h4 class="widget-title lighter">
												<i class="ace-icon fa fa-star orange"></i> 今日随工单
											</h4>

											<div class="widget-toolbar">
												<a href="#" data-action="collapse"> <i
													class="ace-icon fa fa-chevron-up"></i> </a>
											</div>
										</div>

										<div class="widget-body">
											<div class="widget-main no-padding">
												<table class="table table-bordered table-striped">
													<thead class="thin-border-bottom">
														<tr>
															<th><i class="ace-icon fa fa-caret-right blue"></i>产品名称
															</th>
															<th><i class="ace-icon fa fa-caret-right blue"></i>计划数量
															</th>

															<th class="hidden-480"><i
																class="ace-icon fa fa-caret-right blue"></i>产品编号</th>
															<th class="hidden-480"><i
																class="ace-icon fa fa-caret-right blue"></i>随工单编号</th>
														</tr>
													</thead>

													<tbody>
														<#list workingbillList as list>
														<tr>
															<td><input type="checkbox" class="ckbox"
																name="WorkingBill.workingBillCode" value="${list.id}">&nbsp;${list.maktx}
															</td>

															<td><b class="green">${list.planCount}</b></td>

															<td class="hidden-480">${list.matnr}</td>
															<td class="hidden-480">${list.workingBillCode}</td>
														</tr>
														</#list>

													</tbody>
												</table>
											</div>
											<!-- /.widget-main -->
										</div>
										<!-- /.widget-body -->
									</div>
									<!-- /.widget-box -->
								</div>
								<!-- /.col -->


							</div>
							<!-- /.row -->

						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.col -->


			</div>
			<!-- /.page-content -->
		</div>
		<!-- /.main-content -->
		<#include "/WEB-INF/template/admin/admin_footer.ftl">
		<!-- /.add by welson 0728  -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">

	<script type="text/javascript">
		$.gritter
				.add({
					// (string | mandatory) the heading of the notification
					title : '通知!',
					// (string | mandatory) the text inside the notification
					text : '您当前尚未绑定"生产日期"和"班次"信息，无法加载数据,请点击<a href="admin!product.action" class="orange">绑定生产日期和班次</a> 进行绑定，绑定后，随工单将自动加载',
					class_name : 'gritter-success'
				});
	</script>

</body>
</html>