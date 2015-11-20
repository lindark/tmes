<#assign ww=JspTaglibs["/WEB-INF/access.tld"]/> 
<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>管理中心 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<link rel="stylesheet" href="${base}/template/admin/assets/css/jquery.gritter.css" />
<script src='${base}/template/admin/assets/js/jquery.min.js'></script>
<script src="${base}/template/admin/js/Main/teamindex.js"></script>
<script src="${base}/template/admin/assets/js/jquery.gritter.min.js"></script>
</head>
<body class="no-skin">

<#include "/WEB-INF/template/admin/admin_navbar.ftl">
<div class="main-container" id="main-container">
	<script type="text/javascript">
		try{ace.settings.check('main-container' , 'fixed')}catch(e){}
	</script>
	
	<#include "/WEB-INF/template/admin/admin_sidebar.ftl">
	
	<div class="main-content">
	<#include "/WEB-INF/template/admin/admin_acesettingbox.ftl">
	
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
					</script>

					<ul class="breadcrumb">
						<li>
							<i class="ace-icon fa fa-home home-icon"></i>
							<a href="#">管理中心</a>
						</li>
						<li class="active">首页</li>
					</ul><!-- /.breadcrumb -->

					

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
										<div class="widget-box">
											<div class="widget-header">
												<h5 class="widget-title">当前班组信息</h5>

												<!-- #section:custom/widget-box.toolbar -->
												<div class="widget-toolbar">
													<div class="widget-menu">
														<a href="#" data-action="settings" data-toggle="dropdown">
															<i class="ace-icon fa fa-bars"></i>
														</a>

														<ul class="dropdown-menu dropdown-menu-right dropdown-light-blue dropdown-caret dropdown-closer">
															<li>
																<a data-toggle="tab" href="#dropdown1">Option#1</a>
															</li>

															<li>
																<a data-toggle="tab" href="#dropdown2">Option#2</a>
															</li>
														</ul>
													</div>

													<a href="#" data-action="fullscreen" class="orange2">
														<i class="ace-icon fa fa-expand"></i>
													</a>

													<a href="#" data-action="reload">
														<i class="ace-icon fa fa-refresh"></i>
													</a>

													<a href="#" data-action="collapse">
														<i class="ace-icon fa fa-chevron-up"></i>
													</a>

													<a href="#" data-action="close">
														<i class="ace-icon fa fa-times"></i>
													</a>
												</div>

												<!-- /section:custom/widget-box.toolbar -->
											</div>

											<div class="widget-body">
												<div class="widget-main">
													<p class="alert alert-info">
														<div class="hr hr2"></div>
													</p>
													<p class="alert alert-success">
														空空空
													</p>
												</div>
											</div>
										</div>

										<!-- /section:custom/widget-box -->
									</div>
									
									<!--按钮组start-->
									<div class="col-xs-12 col-sm-6 widget-container-col">
										<!-- #section:custom/widget-box -->
										<div class="widget-box">
											<div class="widget-header">
												<h5 class="widget-title">按钮组</h5>

												<!-- #section:custom/widget-box.toolbar -->
												<div class="widget-toolbar">
													<div class="widget-menu">
														<a href="#" data-action="settings" data-toggle="dropdown">
															<i class="ace-icon fa fa-bars"></i>
														</a>

														<ul class="dropdown-menu dropdown-menu-right dropdown-light-blue dropdown-caret dropdown-closer">
															<li>
																<a data-toggle="tab" href="#dropdown1">Option#1</a>
															</li>

															<li>
																<a data-toggle="tab" href="#dropdown2">Option#2</a>
															</li>
														</ul>
													</div>

													<a href="#" data-action="fullscreen" class="orange2">
														<i class="ace-icon fa fa-expand"></i>
													</a>

													<a href="#" data-action="reload">
														<i class="ace-icon fa fa-refresh"></i>
													</a>

													<a href="#" data-action="collapse">
														<i class="ace-icon fa fa-chevron-up"></i>
													</a>

													<a href="#" data-action="close">
														<i class="ace-icon fa fa-times"></i>
													</a>
												</div>

												<!-- /section:custom/widget-box.toolbar -->
											</div>

											<div class="widget-body">
												<div class="widget-main">
													
														<div class="row">
															  <@ww.access accobjkey="handover"/> <!--交接 -->
															  <@ww.access accobjkey="getmaterial"/><!--领退料 -->
															  <@ww.access accobjkey="dumpconfirm"/><!--转储确认 -->
															  <@ww.access accobjkey="scrap"/><!--报废 -->
															  <@ww.access accobjkey="rework"/><!--返工 -->
															  <@ww.access accobjkey="repair"/><!--返修 -->
															  <@ww.access accobjkey="repairgoods"/><!--返修收货 -->
															  <@ww.access accobjkey="sampling"/><!--抽检 -->
															  <@ww.access accobjkey="inspection"/><!--巡检 -->
															  <@ww.access accobjkey="halfinspection"/><!--半成品巡检 -->
															  <@ww.access accobjkey="quickresponse"/><!--快速响应 -->
															  <@ww.access accobjkey="sttm"/><!--报工 -->
															  <@ww.access accobjkey="putstorage"/><!--入库 -->
															  <@ww.access accobjkey="attendance"/><!--考勤 -->
															  <@ww.access accobjkey="cartonreceiving"/><!--纸箱收货 -->
														</div>
														
														
													
												</div>
											</div>
										</div>

										<!-- /section:custom/widget-box -->
									</div>
									<!--按钮组end-->
								</div>
								<!-- /section:custom/extra.hr -->
								<div class="row">
									<div class="col-sm-12">
										<div class="widget-box transparent">
											<div class="widget-header widget-header-flat">
												<h4 class="widget-title lighter">
													<i class="ace-icon fa fa-star orange"></i>
													今日随工单
												</h4>

												<div class="widget-toolbar">
													<a href="#" data-action="collapse">
														<i class="ace-icon fa fa-chevron-up"></i>
													</a>
												</div>
											</div>

											<div class="widget-body">
												<div class="widget-main no-padding">
													<table class="table table-bordered table-striped">
														<thead class="thin-border-bottom">
															<tr>
																<th>
																	<i class="ace-icon fa fa-caret-right blue"></i>产品名称
																</th>
																<th>
																	<i class="ace-icon fa fa-caret-right blue"></i>计划数量
																</th>

																<th class="hidden-480">
																	<i class="ace-icon fa fa-caret-right blue"></i>产品编号
																</th>
																<th class="hidden-480">
																	<i class="ace-icon fa fa-caret-right blue"></i>随工单编号
																</th>
															</tr>
														</thead>

														<tbody>
															<#list workingbillList as list>
																<tr>
																	<td>
																		<input type="checkbox" class="ckbox" name="WorkingBill.workingBillCode" value="${list.id}">&nbsp;${list.maktx}
																	</td>
	
																	<td>
																		<b class="green">${list.planCount}</b>
																	</td>
	
																	<td class="hidden-480">
																		${list.matnr}
																	</td>
																	<td class="hidden-480">
																		${list.workingBillCode}
																	</td>
																</tr>
															</#list>
															
														</tbody>
													</table>
												</div><!-- /.widget-main -->
											</div><!-- /.widget-body -->
										</div><!-- /.widget-box -->
									</div><!-- /.col -->

									
								</div><!-- /.row -->

									</div><!-- /.col -->
								</div><!-- /.row -->

								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content-area -->
				</div><!-- /.page-content -->
				
				
				</div><!-- /.main-content--add by welson 0728  -->
		<#include "/WEB-INF/template/admin/admin_footer.ftl"><!-- /.add by welson 0728  -->
	</div><!-- /.main-container--add by welson 0728 -->			
	
	
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
		
		<script type="text/javascript">
		$.gritter.add({
			// (string | mandatory) the heading of the notification
			title: '通知!',
			// (string | mandatory) the text inside the notification
			text: '您当前尚未绑定"生产日期"和"班次"信息，无法加载数据,请点击<a href="admin!product.action" class="orange">绑定生产日期和班次</a> 进行绑定，绑定后，随工单将自动加载',
			class_name: 'gritter-success'
		});


		</script>
		
</body>
</html>