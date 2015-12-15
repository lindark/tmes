<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>随工单投入产出 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />

<#include "/WEB-INF/template/common/includelist.ftl"> <#if !id??>
<#assign isAdd = true /> <#else> <#assign isEdit = true /> </#if>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
}
</style>
</head>
<body class="no-skin input">

	<!-- add by welson 0728 -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>
		<div class="main-content">




			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->
							<div class="widget-box transparent">
								<div class="widget-header widget-header-large">
									<h3 class="widget-title grey lighter">
										<i class="ace-icon fa fa-leaf green"></i> 这里显示产品名称
									</h3>

									<!-- #section:pages/invoice.info -->
									<div class="widget-toolbar no-border invoice-info">
										<span class="invoice-info-label">产品编码:</span> <span
											class="red">#121212</span> <br> <span
											class="invoice-info-label">生产日期:</span> <span class="blue">04/04/2014</span>
									</div>

									<div class="widget-toolbar hidden-480">
										<a href="#"> <i class="ace-icon fa fa-print"></i> </a>
									</div>

									<!-- /section:pages/invoice.info -->
								</div>
								<div class="widget-body">
									<div class="widget-main padding-24">
										<div class="row">
											<div class="col-sm-6">
												<div class="row">
													<div
														class="col-xs-11 label label-lg label-info arrowed-in arrowed-right">
														<b>投入</b>
													</div>
												</div>

												<div class="row">
													<ul class="list-unstyled spaced">
														<li><i class="ace-icon fa fa-caret-right blue"></i>
															领料数</li>

														<li><i class="ace-icon fa fa-caret-right blue"></i>
															返修收货</li>

														<li><i class="ace-icon fa fa-caret-right blue"></i>
															报废裁切数</li>

													</ul>
												</div>
											</div>
											<!-- /.col -->

											<div class="col-sm-6">
												<div class="row">
													<div
														class="col-xs-11 label label-lg label-success arrowed-in arrowed-right">
														<b>产出</b>
													</div>
												</div>

												<div>
													<ul class="list-unstyled  spaced">
														<li><i class="ace-icon fa fa-caret-right green"></i>
															报废</li>

														<li><i class="ace-icon fa fa-caret-right green"></i>
															返修</li>

														<li><i class="ace-icon fa fa-caret-right green"></i>
															报工</li>
														<li><i class="ace-icon fa fa-caret-right green"></i>
															入库</li>
													</ul>
												</div>
											</div>
											<!-- /.col -->
										</div>


									</div>
								</div>
							</div>
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
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->

</body>

</html>