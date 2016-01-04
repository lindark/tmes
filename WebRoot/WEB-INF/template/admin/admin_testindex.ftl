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
											<div class="widget-main no-padding" style="height:250px;overflow:auto;">
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

													<tbody >
														<#list teamList as list>
														<tr>
														   
															<td>
															<input type="hidden" style="width:350px;" class="teamId" name="team.id" value="${(list.id)! }"/>
															<a href="javascript:void(0);" class="matkx">
															       <b class="green" >${list.teamName}</b></a>
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
		var teamid = $(this).prev().val();
		var index = layer.open({
			type : 2,
			skin : 'layui-layer-lan',
			title : "<font size='5px'>当前班组正在生产的随工单</font>",
			fix : false,
			shadeClose : false,
			maxmin : true,
			scrollbar : false,
			btn:['确定'],
			area : [ '800px', '400px' ],//弹出框的高度，宽度
			content : "admin!teamWorkingBill.action?teamid="+teamid
		});
		layer.full(index);//弹出既全屏
	});
	</script>

</body>
</html>