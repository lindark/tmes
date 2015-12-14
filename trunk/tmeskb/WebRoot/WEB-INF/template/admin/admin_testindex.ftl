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
<script src="${base}/template/admin/js/Main/testindex.js"></script>
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
                                       <!--按钮组start-->
								<div class="col-xs-12 col-sm-12 widget-container-col">
									<div class="widget-box transparent">
										<div class="widget-header">
											<h4 class="widget-title lighter">按钮组</h4>
											<div class="widget-toolbar no-border">
												<a href="#" data-action="collapse"> <i
													class="ace-icon fa fa-chevron-up"></i> </a>

											</div>
										</div>

										<div class="widget-body">
											<divclass="widget-main padding-6 no-padding-left no-padding-right">
												<div class="col-md-2 col-sm-4 access" style="padding:2px;" data-access-list="scrapTest">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled" type="button" id="scrap">
														<i class="ace-icon fa fa-inbox bigger-110"></i> <span
															class="bigger-110 no-text-shadow">报废</span>
													</button>
												</div>
												<div class="col-md-2 col-sm-4 access" style="padding:2px;" data-access-list="reworkTest">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="rework">
														<i class="ace-icon fa fa-exchange bigger-110"></i> <span
															class="bigger-110 no-text-shadow">返工</span>
													</button>
												</div>
												<div class="col-md-2 col-sm-4 access" style="padding:2px;" data-access-list="repairTest">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="repair">
														<i class="ace-icon fa fa-cog bigger-110"></i> <span
															class="bigger-110 no-text-shadow">返修</span>
													</button>
												</div>
												<div class="col-md-2 col-sm-4 access" style="padding:2px;" data-access-list="sampleTest">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="sample">
														<i class="ace-icon fa fa-flag bigger-110"></i> <span
															class="bigger-110 no-text-shadow">抽检</span>
													</button>
												</div>
												<div class="col-md-2 col-sm-4 access" style="padding:2px;" data-access-list="inspectionTest">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="pollingtest">
														<i class="ace-icon fa fa-leaf bigger-110"></i> <span
															class="bigger-110 no-text-shadow">巡检</span>
													</button>
												</div>
												<div class="col-md-2 col-sm-4 access" style="padding:2px;" data-access-list="halfinspectionTest">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="halfinspection">
														<i class="ace-icon fa fa-star-half-o bigger-110"></i> <span
															class="bigger-110 no-text-shadow">半成品巡检</span>
													</button>
												</div>
												<div class="col-md-2 col-sm-4 access" style="padding:2px;" data-access-list="quickresponseTest">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block"
														id="qResponse">
														<i class="ace-icon fa fa-volume-up bigger-110"></i> <span
															class="bigger-110 no-text-shadow">快速响应</span>
													</button>
												</div>
												<div class="col-md-2 col-sm-4 access" style="padding:2px;" data-access-list="attendanceTest">
													<button
														class="btn btn-white btn-success btn-bold btn-round btn-block">
														<i class="ace-icon fa fa-users bigger-110"></i> <span
															class="bigger-110 no-text-shadow">考勤</span>
													</button>
												</div>
											</div>
										</div>
									</div>
								<!--按钮组end-->
							</div>
							
						   <div>&nbsp</div>

							
							<div class="row">
							<div class="col-xs-12 col-sm-12 widget-container-col">
									<div class="widget-box transparent">
										<div class="widget-header widget-header-flat">
											<h4 class="widget-title lighter">
												<i class="ace-icon fa fa-star orange"></i> 正在工作中的班组
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
															<th><i class="ace-icon fa fa-caret-right blue"></i>班组名称
															</th>
															<th><i class="ace-icon fa fa-caret-right blue"></i>所在单元
															</th>

															<th class="hidden-480"><i
																class="ace-icon fa fa-caret-right blue"></i>车间名称</th>
															<th class="hidden-480"><i
																class="ace-icon fa fa-caret-right blue"></i>工厂名称</th>
														</tr>
													</thead>

													<tbody>
														<#list teamList as list>
														<tr>
														   
															<td ><a href="javascript:void(0);" class="maclick">
															       <b class="green" >${list.teamName}</b>
															     </a>
															</td>								
                                                            <td class="hidden-480">${list.factoryUnit.factoryUnitName}</td>
															<td class="hidden-480">${list.factoryUnit.workShop.workShopName}</td>
															<td class="hidden-480">${list.factoryUnit.workShop.factory.factoryName}</td>
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
							</div>
							
						   <div>&nbsp</div>
						   <div>&nbsp</div>
						   
							<div class="row">
							<div class="col-xs-12 col-sm-6 widget-container-col">
									<div class="widget-box transparent">
										<div class="widget-header widget-header-flat">
											<h4 class="widget-title lighter">
												<i class="ace-icon fa fa-star orange"></i> 未确认的巡检单
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
															<th><i class="ace-icon fa fa-caret-right blue"></i>巡检数量
															</th>
															<th><i class="ace-icon fa fa-caret-right blue"></i>合格数量
															</th>
															<th><i class="ace-icon fa fa-caret-right blue"></i>合格率
															<th><i class="ace-icon fa fa-caret-right blue"></i>创建日期
														</tr>
													</thead>

													<tbody>
														<#list pollingtestList as list>
														<tr>			
														    <td class="hidden-480">${list.workingbill.maktx}</td>		
															<td class="hidden-480">${list.pollingtestAmount}</td>
                                                            <td class="hidden-480">${list.qualifiedAmount}</td>
															<td class="hidden-480">${list.passedPercent}</td>
															<td class="hidden-480">${list.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
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
										
							      <div class="col-xs-12 col-sm-6 widget-container-col">
									<div class="widget-box transparent">
										<div class="widget-header widget-header-flat">
											<h4 class="widget-title lighter">
												<i class="ace-icon fa fa-star orange"></i> 未确认的抽检单
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
															<th><i class="ace-icon fa fa-caret-right blue"></i>抽检数量
															</th>
															<th><i class="ace-icon fa fa-caret-right blue"></i>合格数量
															</th>
															<th><i class="ace-icon fa fa-caret-right blue"></i>合格率
															<th><i class="ace-icon fa fa-caret-right blue"></i>创建日期
														</tr>
													</thead>

													<tbody>
														<#list sampleList as list>
														<tr>	
														    <td class="hidden-480">${list.workingBill.maktx}</td>				
															<td class="hidden-480">${list.sampleNum}</td>
                                                            <td class="hidden-480">${list.qulified}</td>
															<td class="hidden-480">${list.qulifiedRate}</td>
															<td class="hidden-480">${list.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
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
							</div>
							
							<div>&nbsp</div>
							<div>&nbsp</div>
							
							
							<div class="row">
							<div class="col-xs-12 col-sm-6 widget-container-col">
									<div class="widget-box transparent">
										<div class="widget-header widget-header-flat">
											<h4 class="widget-title lighter">
												<i class="ace-icon fa fa-star orange"></i> 未确认的报废单
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
															<th><i class="ace-icon fa fa-caret-right blue"></i>创建日期
														</tr>
													</thead>

													<tbody>
														<#list scrapList as list>
														<tr>			
														    <td class="hidden-480">${list.workingBill.maktx}</td>														
															<td class="hidden-480">${list.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
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
										
							      <div class="col-xs-12 col-sm-6 widget-container-col">
									<div class="widget-box transparent">
										<div class="widget-header widget-header-flat">
											<h4 class="widget-title lighter">
												<i class="ace-icon fa fa-star orange"></i> 未确认的半成品巡检单
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
															<th><i class="ace-icon fa fa-caret-right blue"></i>抽检数量
															</th>
															<th><i class="ace-icon fa fa-caret-right blue"></i>合格数量
															</th>
															<th><i class="ace-icon fa fa-caret-right blue"></i>合格率
															<th><i class="ace-icon fa fa-caret-right blue"></i>创建日期
														</tr>
													</thead>

													<tbody>
														<#list sampleList as list>
														<tr>	
														    <td class="hidden-480">${list.workingBill.maktx}</td>				
															<td class="hidden-480">${list.sampleNum}</td>
                                                            <td class="hidden-480">${list.qulified}</td>
															<td class="hidden-480">${list.qulifiedRate}</td>
															<td class="hidden-480">${list.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
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
							</div>
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
		
	</script>

</body>
</html>