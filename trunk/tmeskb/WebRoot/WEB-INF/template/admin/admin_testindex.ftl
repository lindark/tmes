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

								<div class="col-xs-12 col-sm-6 widget-container-col">
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
															<td><b class="green">${list.teamName}</b></td>
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
												<div class="col-md-3 col-sm-1 access" style="padding:2px;" data-access-list="scrapTest">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled" type="button" id="scrap">
														<i class="ace-icon fa fa-inbox bigger-110"></i> <span
															class="bigger-110 no-text-shadow">报废</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="reworkTest">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="rework">
														<i class="ace-icon fa fa-exchange bigger-110"></i> <span
															class="bigger-110 no-text-shadow">返工</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="repairTest">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="repair">
														<i class="ace-icon fa fa-cog bigger-110"></i> <span
															class="bigger-110 no-text-shadow">返修</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="sampleTest">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="sample">
														<i class="ace-icon fa fa-flag bigger-110"></i> <span
															class="bigger-110 no-text-shadow">抽检</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="inspectionTest">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="pollingtest">
														<i class="ace-icon fa fa-leaf bigger-110"></i> <span
															class="bigger-110 no-text-shadow">巡检</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="halfinspectionTest">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block disabled"
														id="halfinspection">
														<i class="ace-icon fa fa-star-half-o bigger-110"></i> <span
															class="bigger-110 no-text-shadow">半成品巡检</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="quickresponseTest">
													<button 
														class="btn btn-white btn-success btn-bold btn-round btn-block"
														id="qResponse">
														<i class="ace-icon fa fa-volume-up bigger-110"></i> <span
															class="bigger-110 no-text-shadow">快速响应</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;" data-access-list="attendanceTest">
													<button
														class="btn btn-white btn-success btn-bold btn-round btn-block">
														<i class="ace-icon fa fa-users bigger-110"></i> <span
															class="bigger-110 no-text-shadow">考勤</span>
													</button>
												</div>
											</div>
										</div>
									</div>

								</div>
								<!--按钮组end-->							
							</div><!-- /.row -->
						</div>
							<!-- /section:custom/extra.hr -->
						<div>&nbsp</div>
						<div>&nbsp</div>
						<div>&nbsp</div>	
						<div class="col-xs-12">	
							<div class="row">
								
								<!-- /.col -->
                               <div class="col-xs-6 col-sm-4 widget-container-col">
										<div class="widget-box widget-color-green">
											<!-- #section:custom/widget-box.options -->
											<div class="widget-header">
												<h5 class="widget-title bigger lighter">
													<i class="ace-icon fa fa-table"></i>
													Tables & Colors
												</h5>

												<div class="widget-toolbar widget-toolbar-light no-border">
													<select id="simple-colorpicker-1" class="hide">
														<option selected="" data-class="blue" value="#307ECC">#307ECC</option>
														<option data-class="blue2" value="#5090C1">#5090C1</option>
														<option data-class="blue3" value="#6379AA">#6379AA</option>
														<option data-class="green" value="#82AF6F">#82AF6F</option>
														<option data-class="green2" value="#2E8965">#2E8965</option>
														<option data-class="green3" value="#5FBC47">#5FBC47</option>
														<option data-class="red" value="#E2755F">#E2755F</option>
														<option data-class="red2" value="#E04141">#E04141</option>
														<option data-class="red3" value="#D15B47">#D15B47</option>
														<option data-class="orange" value="#FFC657">#FFC657</option>
														<option data-class="purple" value="#7E6EB0">#7E6EB0</option>
														<option data-class="pink" value="#CE6F9E">#CE6F9E</option>
														<option data-class="dark" value="#404040">#404040</option>
														<option data-class="grey" value="#848484">#848484</option>
														<option data-class="default" value="#EEE">#EEE</option>
													</select>
												</div>
											</div>

											<!-- /section:custom/widget-box.options -->
											<div class="widget-body">
												<div class="widget-main no-padding">
													<table class="table table-striped table-bordered table-hover">
														<thead class="thin-border-bottom">
															<tr>
																<th>
																	<i class="ace-icon fa fa-user"></i>
																	User
																</th>

																<th>
																	<i>@</i>
																	Email
																</th>
																<th class="hidden-480">Status</th>
															</tr>
														</thead>

														<tbody>
															<tr>
																<td class="">Alex</td>

																<td>
																	<a href="#">alex@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-warning">Pending</span>
																</td>
															</tr>

															<tr>
																<td class="">Fred</td>

																<td>
																	<a href="#">fred@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-success arrowed-in arrowed-in-right">Approved</span>
																</td>
															</tr>

															<tr>
																<td class="">Jack</td>

																<td>
																	<a href="#">jack@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-warning">Pending</span>
																</td>
															</tr>

															<tr>
																<td class="">John</td>

																<td>
																	<a href="#">john@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-inverse arrowed">Blocked</span>
																</td>
															</tr>

															<tr>
																<td class="">James</td>

																<td>
																	<a href="#">james@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-info arrowed-in arrowed-in-right">Online</span>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
									<!-- /.span -->
                                      
                                      <div class="col-xs-6 col-sm-4 widget-container-col">
										<div class="widget-box widget-color-purple">
											<!-- #section:custom/widget-box.options -->
											<div class="widget-header">
												<h5 class="widget-title bigger lighter">
													<i class="ace-icon fa fa-table"></i>
													Tables & Colors
												</h5>

												<div class="widget-toolbar widget-toolbar-light no-border">
													<select id="simple-colorpicker-1" class="hide">
														<option selected="" data-class="blue" value="#307ECC">#307ECC</option>
														<option data-class="blue2" value="#5090C1">#5090C1</option>
														<option data-class="blue3" value="#6379AA">#6379AA</option>
														<option data-class="green" value="#82AF6F">#82AF6F</option>
														<option data-class="green2" value="#2E8965">#2E8965</option>
														<option data-class="green3" value="#5FBC47">#5FBC47</option>
														<option data-class="red" value="#E2755F">#E2755F</option>
														<option data-class="red2" value="#E04141">#E04141</option>
														<option data-class="red3" value="#D15B47">#D15B47</option>
														<option data-class="orange" value="#FFC657">#FFC657</option>
														<option data-class="purple" value="#7E6EB0">#7E6EB0</option>
														<option data-class="pink" value="#CE6F9E">#CE6F9E</option>
														<option data-class="dark" value="#404040">#404040</option>
														<option data-class="grey" value="#848484">#848484</option>
														<option data-class="default" value="#EEE">#EEE</option>
													</select>
												</div>
											</div>

											<!-- /section:custom/widget-box.options -->
											<div class="widget-body">
												<div class="widget-main no-padding">
													<table class="table table-striped table-bordered table-hover">
														<thead class="thin-border-bottom">
															<tr>
																<th>
																	<i class="ace-icon fa fa-user"></i>
																	User
																</th>

																<th>
																	<i>@</i>
																	Email
																</th>
																<th class="hidden-480">Status</th>
															</tr>
														</thead>

														<tbody>
															<tr>
																<td class="">Alex</td>

																<td>
																	<a href="#">alex@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-warning">Pending</span>
																</td>
															</tr>

															<tr>
																<td class="">Fred</td>

																<td>
																	<a href="#">fred@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-success arrowed-in arrowed-in-right">Approved</span>
																</td>
															</tr>

															<tr>
																<td class="">Jack</td>

																<td>
																	<a href="#">jack@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-warning">Pending</span>
																</td>
															</tr>

															<tr>
																<td class="">John</td>

																<td>
																	<a href="#">john@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-inverse arrowed">Blocked</span>
																</td>
															</tr>

															<tr>
																<td class="">James</td>

																<td>
																	<a href="#">james@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-info arrowed-in arrowed-in-right">Online</span>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div><!-- /.span -->
									
									<div class="col-xs-12 col-sm-4 widget-container-col">
										<div class="widget-box widget-color-green">
											<!-- #section:custom/widget-box.options -->
											<div class="widget-header">
												<h5 class="widget-title bigger lighter">
													<i class="ace-icon fa fa-table"></i>
													Tables & Colors
												</h5>

												<div class="widget-toolbar widget-toolbar-light no-border">
													<select id="simple-colorpicker-1" class="hide">
														<option selected="" data-class="blue" value="#307ECC">#307ECC</option>
														<option data-class="blue2" value="#5090C1">#5090C1</option>
														<option data-class="blue3" value="#6379AA">#6379AA</option>
														<option data-class="green" value="#82AF6F">#82AF6F</option>
														<option data-class="green2" value="#2E8965">#2E8965</option>
														<option data-class="green3" value="#5FBC47">#5FBC47</option>
														<option data-class="red" value="#E2755F">#E2755F</option>
														<option data-class="red2" value="#E04141">#E04141</option>
														<option data-class="red3" value="#D15B47">#D15B47</option>
														<option data-class="orange" value="#FFC657">#FFC657</option>
														<option data-class="purple" value="#7E6EB0">#7E6EB0</option>
														<option data-class="pink" value="#CE6F9E">#CE6F9E</option>
														<option data-class="dark" value="#404040">#404040</option>
														<option data-class="grey" value="#848484">#848484</option>
														<option data-class="default" value="#EEE">#EEE</option>
													</select>
												</div>
											</div>

											<!-- /section:custom/widget-box.options -->
											<div class="widget-body">
												<div class="widget-main no-padding">
													<table class="table table-striped table-bordered table-hover">
														<thead class="thin-border-bottom">
															<tr>
																<th>
																	<i class="ace-icon fa fa-user"></i>
																	User
																</th>

																<th>
																	<i>@</i>
																	Email
																</th>
																<th class="hidden-480">Status</th>
															</tr>
														</thead>

														<tbody>
															<tr>
																<td class="">Alex</td>

																<td>
																	<a href="#">alex@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-warning">Pending</span>
																</td>
															</tr>

															<tr>
																<td class="">Fred</td>

																<td>
																	<a href="#">fred@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-success arrowed-in arrowed-in-right">Approved</span>
																</td>
															</tr>

															<tr>
																<td class="">Jack</td>

																<td>
																	<a href="#">jack@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-warning">Pending</span>
																</td>
															</tr>

															<tr>
																<td class="">John</td>

																<td>
																	<a href="#">john@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-inverse arrowed">Blocked</span>
																</td>
															</tr>

															<tr>
																<td class="">James</td>

																<td>
																	<a href="#">james@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-info arrowed-in arrowed-in-right">Online</span>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div><!-- /.span -->
							   </div>
							 </div><!-- /.row -->
							 
							 <div class="col-xs-12">	
							<div class="row">
								
								<!-- /.col -->
                               <div class="col-xs-6 col-sm-4 widget-container-col">
										<div class="widget-box widget-color-purple">
											<!-- #section:custom/widget-box.options -->
											<div class="widget-header">
												<h5 class="widget-title bigger lighter">
													<i class="ace-icon fa fa-table"></i>
													Tables & Colors
												</h5>

												<div class="widget-toolbar widget-toolbar-light no-border">
													<select id="simple-colorpicker-1" class="hide">
														<option selected="" data-class="blue" value="#307ECC">#307ECC</option>
														<option data-class="blue2" value="#5090C1">#5090C1</option>
														<option data-class="blue3" value="#6379AA">#6379AA</option>
														<option data-class="green" value="#82AF6F">#82AF6F</option>
														<option data-class="green2" value="#2E8965">#2E8965</option>
														<option data-class="green3" value="#5FBC47">#5FBC47</option>
														<option data-class="red" value="#E2755F">#E2755F</option>
														<option data-class="red2" value="#E04141">#E04141</option>
														<option data-class="red3" value="#D15B47">#D15B47</option>
														<option data-class="orange" value="#FFC657">#FFC657</option>
														<option data-class="purple" value="#7E6EB0">#7E6EB0</option>
														<option data-class="pink" value="#CE6F9E">#CE6F9E</option>
														<option data-class="dark" value="#404040">#404040</option>
														<option data-class="grey" value="#848484">#848484</option>
														<option data-class="default" value="#EEE">#EEE</option>
													</select>
												</div>
											</div>

											<!-- /section:custom/widget-box.options -->
											<div class="widget-body">
												<div class="widget-main no-padding">
													<table class="table table-striped table-bordered table-hover">
														<thead class="thin-border-bottom">
															<tr>
																<th>
																	<i class="ace-icon fa fa-user"></i>
																	User
																</th>

																<th>
																	<i>@</i>
																	Email
																</th>
																<th class="hidden-480">Status</th>
															</tr>
														</thead>

														<tbody>
															<tr>
																<td class="">Alex</td>

																<td>
																	<a href="#">alex@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-warning">Pending</span>
																</td>
															</tr>

															<tr>
																<td class="">Fred</td>

																<td>
																	<a href="#">fred@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-success arrowed-in arrowed-in-right">Approved</span>
																</td>
															</tr>

															<tr>
																<td class="">Jack</td>

																<td>
																	<a href="#">jack@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-warning">Pending</span>
																</td>
															</tr>

															<tr>
																<td class="">John</td>

																<td>
																	<a href="#">john@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-inverse arrowed">Blocked</span>
																</td>
															</tr>

															<tr>
																<td class="">James</td>

																<td>
																	<a href="#">james@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-info arrowed-in arrowed-in-right">Online</span>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
									<!-- /.span -->
                                      
                                      <div class="col-xs-6 col-sm-4 widget-container-col">
										<div class="widget-box widget-color-green">
											<!-- #section:custom/widget-box.options -->
											<div class="widget-header">
												<h5 class="widget-title bigger lighter">
													<i class="ace-icon fa fa-table"></i>
													Tables & Colors
												</h5>

												<div class="widget-toolbar widget-toolbar-light no-border">
													<select id="simple-colorpicker-1" class="hide">
														<option selected="" data-class="blue" value="#307ECC">#307ECC</option>
														<option data-class="blue2" value="#5090C1">#5090C1</option>
														<option data-class="blue3" value="#6379AA">#6379AA</option>
														<option data-class="green" value="#82AF6F">#82AF6F</option>
														<option data-class="green2" value="#2E8965">#2E8965</option>
														<option data-class="green3" value="#5FBC47">#5FBC47</option>
														<option data-class="red" value="#E2755F">#E2755F</option>
														<option data-class="red2" value="#E04141">#E04141</option>
														<option data-class="red3" value="#D15B47">#D15B47</option>
														<option data-class="orange" value="#FFC657">#FFC657</option>
														<option data-class="purple" value="#7E6EB0">#7E6EB0</option>
														<option data-class="pink" value="#CE6F9E">#CE6F9E</option>
														<option data-class="dark" value="#404040">#404040</option>
														<option data-class="grey" value="#848484">#848484</option>
														<option data-class="default" value="#EEE">#EEE</option>
													</select>
												</div>
											</div>

											<!-- /section:custom/widget-box.options -->
											<div class="widget-body">
												<div class="widget-main no-padding">
													<table class="table table-striped table-bordered table-hover">
														<thead class="thin-border-bottom">
															<tr>
																<th>
																	<i class="ace-icon fa fa-user"></i>
																	User
																</th>

																<th>
																	<i>@</i>
																	Email
																</th>
																<th class="hidden-480">Status</th>
															</tr>
														</thead>

														<tbody>
															<tr>
																<td class="">Alex</td>

																<td>
																	<a href="#">alex@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-warning">Pending</span>
																</td>
															</tr>

															<tr>
																<td class="">Fred</td>

																<td>
																	<a href="#">fred@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-success arrowed-in arrowed-in-right">Approved</span>
																</td>
															</tr>

															<tr>
																<td class="">Jack</td>

																<td>
																	<a href="#">jack@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-warning">Pending</span>
																</td>
															</tr>

															<tr>
																<td class="">John</td>

																<td>
																	<a href="#">john@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-inverse arrowed">Blocked</span>
																</td>
															</tr>

															<tr>
																<td class="">James</td>

																<td>
																	<a href="#">james@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-info arrowed-in arrowed-in-right">Online</span>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div><!-- /.span -->
									
									<div class="col-xs-12 col-sm-4 widget-container-col">
										<div class="widget-box widget-color-purple">
											<!-- #section:custom/widget-box.options -->
											<div class="widget-header">
												<h5 class="widget-title bigger lighter">
													<i class="ace-icon fa fa-table"></i>
													Tables & Colors
												</h5>

												<div class="widget-toolbar widget-toolbar-light no-border">
													<select id="simple-colorpicker-1" class="hide">
														<option selected="" data-class="blue" value="#307ECC">#307ECC</option>
														<option data-class="blue2" value="#5090C1">#5090C1</option>
														<option data-class="blue3" value="#6379AA">#6379AA</option>
														<option data-class="green" value="#82AF6F">#82AF6F</option>
														<option data-class="green2" value="#2E8965">#2E8965</option>
														<option data-class="green3" value="#5FBC47">#5FBC47</option>
														<option data-class="red" value="#E2755F">#E2755F</option>
														<option data-class="red2" value="#E04141">#E04141</option>
														<option data-class="red3" value="#D15B47">#D15B47</option>
														<option data-class="orange" value="#FFC657">#FFC657</option>
														<option data-class="purple" value="#7E6EB0">#7E6EB0</option>
														<option data-class="pink" value="#CE6F9E">#CE6F9E</option>
														<option data-class="dark" value="#404040">#404040</option>
														<option data-class="grey" value="#848484">#848484</option>
														<option data-class="default" value="#EEE">#EEE</option>
													</select>
												</div>
											</div>

											<!-- /section:custom/widget-box.options -->
											<div class="widget-body">
												<div class="widget-main no-padding">
													<table class="table table-striped table-bordered table-hover">
														<thead class="thin-border-bottom">
															<tr>
																<th>
																	<i class="ace-icon fa fa-user"></i>
																	User
																</th>

																<th>
																	<i>@</i>
																	Email
																</th>
																<th class="hidden-480">Status</th>
															</tr>
														</thead>

														<tbody>
															<tr>
																<td class="">Alex</td>

																<td>
																	<a href="#">alex@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-warning">Pending</span>
																</td>
															</tr>

															<tr>
																<td class="">Fred</td>

																<td>
																	<a href="#">fred@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-success arrowed-in arrowed-in-right">Approved</span>
																</td>
															</tr>

															<tr>
																<td class="">Jack</td>

																<td>
																	<a href="#">jack@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-warning">Pending</span>
																</td>
															</tr>

															<tr>
																<td class="">John</td>

																<td>
																	<a href="#">john@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-inverse arrowed">Blocked</span>
																</td>
															</tr>

															<tr>
																<td class="">James</td>

																<td>
																	<a href="#">james@email.com</a>
																</td>

																<td class="hidden-480">
																	<span class="label label-info arrowed-in arrowed-in-right">Online</span>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div><!-- /.span -->
							   </div>
							 </div><!-- /.row -->
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
	
	</script>

</body>
</html>