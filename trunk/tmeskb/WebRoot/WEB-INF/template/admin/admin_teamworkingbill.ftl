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

	
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="main-content">
			<#include "/WEB-INF/template/admin/admin_acesettingbox.ftl">

			

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
											<h4 class="widget-title lighter">选中班组信息</h4>
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
															${(team.factoryUnit.workShop.factory.factoryName)!}</div>
													</div>
													<div class="profile-info-row">
														<div class="profile-info-name">车间：</div>
														<div class="profile-info-value">
															${(team.factoryUnit.workShop.workShopName)!}</div>
													</div>
													<div class="profile-info-row">
														<div class="profile-info-name">单元：</div>
														<div class="profile-info-value">
															${(team.factoryUnit.factoryUnitName)!}</div>
													</div>
													<div class="profile-info-row">
														<div class="profile-info-name">班组：</div>
														<div class="profile-info-value">
															${(team.teamName)! }</div>
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
											<h4 class="widget-title lighter">业务处理</h4>
											<div class="widget-toolbar no-border">
												<a href="#" data-action="collapse"> <i
													class="ace-icon fa fa-chevron-up"></i> </a>
											</div>
										</div>

										<div class="widget-body">
											<div
												class="widget-main padding-6 no-padding-left no-padding-right">
											
												
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="rework_1">
													<button
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="rework">
														<i class="ace-icon fa fa-exchange bigger-110"></i> <span
															class="bigger-110 no-text-shadow">返工</span>
													</button>
												</div>
									
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="sample_1">
													<button
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="sample">
														<i class="ace-icon fa fa-flag bigger-110"></i> <span
															class="bigger-110 no-text-shadow">抽检</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="inspection_1">
													<button
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="pollingtest">
														<i class="ace-icon fa fa-leaf bigger-110"></i> <span
															class="bigger-110 no-text-shadow">巡检</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="halfinspection_1">
													<button
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="halfinspection">
														<i class="ace-icon fa fa-star-half-o bigger-110"></i> <span
															class="bigger-110 no-text-shadow">半成品巡检</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="quickresponse_1">
													<button
														class="btn btn-white btn-success btn-bold btn-round btn-block"
														id="qResponse">
														<i class="ace-icon fa fa-volume-up bigger-110"></i> <span
															class="bigger-110 no-text-shadow">快速响应</span>
													</button>
												</div>
												
												<input type="hidden" id="loginid" value="<@sec.authentication property='principal.id' />" />
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="attendance_1">
													<button
														class="btn btn-white btn-success btn-bold btn-round btn-block" id="kaoqin" type="button">
														<i class="ace-icon fa fa-users bigger-110"></i> <span
															class="bigger-110 no-text-shadow">考勤</span>
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
																name="WorkingBill.workingBillCode" value="${list.id}" />&nbsp;
																<a href="javascript:void(0);" class="a matkx">${list.maktx}</a>
															</td>

															<td><b class="green">${list.planCount}</b>
															</td>

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
	$(".matkx").click(function() {
		var previd = $(this).prev().val();
		var index = layer.open({
			type : 2,
			skin : 'layui-layer-lan',
			title : '投入产出显示表',
			fix : false,
			shadeClose : false,
			maxmin : true,
			scrollbar : false,
			btn:['确定'],
			area : [ '800px', '400px' ],//弹出框的高度，宽度
			content : "working_bill!inout.action?workingbill.id="+previd
		});
		layer.full(index);//弹出既全屏

	});
	</script>

</body>
</html>