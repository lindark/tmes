<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>交接管理</title>
<meta name="description"
	content="Dynamic tables and grids using jqGrid plugin" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<#include "/WEB-INF/template/common/includelist.ftl">
<!--modify weitao-->
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<script src="${base}/template/admin/assets/js/ace-extra.min.js"></script>
<script src="${base }/template/admin/js/manage/handover.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/manage/handover_list.js"></script>
<style type="text/css">
.ztree li span.button.add {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle
}
.buttonArea{
	padding:2px;
}
}
</style>
</head>
<body class="no-skin list">

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
					<li class="active">交接管理&nbsp;<span class="pageInfo"></span>
					</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<div id="inputtabs">
								<ul>
									<li><a href="#tabs-1">工序交接</a>
									</li>
									<li><a href="#tabs-2">线边仓交接</a>
									</li>
									<li class="over"><a href="#tabs-3">总体交接确认</a>
									</li>
								</ul>
								<div id="tabs-1">
									<div class="widget-box">
										<div
											class="widget-header widget-header-blue widget-header-flat">

											<div class="widget-toolbar"></div>
										</div>

										<div class="widget-body">
											<div class="widget-main">
												<!-- #section:plugins/fuelux.wizard -->
												<div id="fuelux-wizard" data-target="#step-container">
													<!-- #section:plugins/fuelux.wizard.steps -->
													<ul class="wizard-steps">
														<#assign  num=0/>
														<#list processList as list>
															<#assign  num=num+1/>
															<li data-target="#step_${(list.id)! }" class="step-jump">
																<span class="step">${num }</span>
																<span class="title">${(list.processName)! }</span>
																<input type="hidden" class="process" value="${list.id}"/>
															</li>
														</#list>
														
													</ul>

													<!-- /section:plugins/fuelux.wizard.steps -->
												</div>

												<hr />

												<div class="step-content pos-rel" id="step-container">
													<#list processList as list>
														<div class="step-pane" id="step_${(list.id)! }">
															<#list materialList as wList>
																<div class="col-md-4">
																	<input type="hidden" value="${wList.id }"/>
																	<a href="javascript:void(0);" class="maclick">
																		<span class="materialCode">${(wList.materialCode)! } </span>
																		<span class="materialName">${(wList.materialName)! } </span>
																	</a>
																</div>
															</#list>
														</div>
													</#list>
												</div>

												<!-- /section:plugins/fuelux.wizard.container -->


												<!-- /section:plugins/fuelux.wizard -->
											</div>
											<!-- /.widget-main -->
										</div>
										<!-- /.widget-body -->
									</div>
								</div>
								<div id="tabs-2">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<th class="center">库存地点</th>
												<th class="center">物料编码</th>
												<th class="center">物料描述</th>
												<th class="center">数量</th>
											</tr>
										</thead>
	
										<tbody>
											<#list locationonsideList as list>
												<tr>
													<td class="center">${list.locationCode }</td>
													<td class="center">${list.materialCode }</td>
													<td class="center">${list.materialName }</td>
													<td class="center">${list.amount }	</td>
												</tr>
											</#list>
	
										</tbody>
									</table>
								</div>
								<div id="tabs-3">
									<div class="ceshi">
									<table id="grid-table"></table>
		
										<div id="grid-pager"></div>
									</div>
										
									<div class="buttonArea">
										<button class="btn btn-white btn-default btn-sm btn-round btnsubmit" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
										</button>&nbsp;&nbsp;
										<button class="btn btn-white btn-default btn-sm btn-round btnapproval" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡确认
										</button>
									</div>
								</div>
							</div>



							<script type="text/javascript">
								var $path_base = "${base}/template/admin";//in Ace demo this will be used for editurl parameter
							</script>

							<!-- PAGE CONTENT ENDS -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content-area -->
				<!-- PAGE CONTENT ENDS -->

				
			</div>
			<!-- /.page-content -->
		</div>
		<!-- /.main-content -->
		<#include "/WEB-INF/template/admin/admin_footer.ftl">
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->

</body>

<script
	src="${base}/template/admin/assets/js/fuelux/fuelux.wizard.min.js"></script>
<script src="${base}/template/admin/assets/js/jquery.validate.min.js"></script>
<script src="${base}/template/admin/assets/js/additional-methods.min.js"></script>
<script src="${base}/template/admin/assets/js/jquery.maskedinput.min.js"></script>
<script src="${base}/template/admin/assets/js/select2.min.js"></script>

<script type="text/javascript"
	src="${base}/template/admin/js/manage/handovercontrol.js"></script>

</html>
<script type="text/javascript">
	$(function() {
		$("#inputtabs").tabs();
		$(".over").click(function(){
			$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
			$("#grid-table").jqGrid('setGridParam',{
				url:"hand_over_process!ajlist.action",
				datatype:"json",
				page:1
			}).trigger("reloadGrid");
		})
	})
</script>