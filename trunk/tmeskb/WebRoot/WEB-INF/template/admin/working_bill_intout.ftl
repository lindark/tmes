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
										<i class="ace-icon fa fa-leaf green"></i> ${workingbill.maktx }
									</h3>

									<!-- #section:pages/invoice.info -->
									<div class="widget-toolbar no-border invoice-info">
										<span class="invoice-info-label">产品编码:</span> <span
											class="red">${workingbill.matnr }</span> <br> <span
											class="invoice-info-label">计划数量:</span> <span class="blue">${workingbill.planCount }</span>
									</div>

									<div class="widget-toolbar hidden-480">
										<a href="#"> <i class="ace-icon fa fa-print"></i> </a>
									</div>

									<!-- /section:pages/invoice.info -->
								</div>
								<div class="widget-body">
									<div class="widget-main padding-24">
										<div class="row">
											<div class="col-sm-12" style="overflow-x:scroll;">
													<table class="table table-striped table-bordered">
															<thead>
																<tr>
																	<th class="center">组件编码</th>
																	<th class="center">组件描述</th>
																	<th class="center">接上班裁切数</th>
																	<th class="center">接上班半成品</th>
																	<th class="center">接上班接角返修品</th>
																	<th class="center">接上班植绒返修品</th>
																	<th class="center">投入异常数</th>
																	<th class="center">接上班零头数</th>
																	<th class="center">抽包异常接班</th>
																	<th class="center">领用数</th>
																	<th class="center">倍数</th>
																	<th class="center">入库数</th>
																	<th class="center">交下班零头数</th>
																	<th class="center">交下班裁切数</th>
																	<th class="center">交下班半成品</th>
																	<th class="center">产出异常数</th>
																	<th class="center">抽包异常交班</th>
																	<th class="center">报废数</th>
																	<th class="center">交下班植绒返修数</th>
																	<th class="center">交下班接角返修数</th>
																	<th class="center">异常表面维修数</th>
																	<th class="center">检验合格数1</th>
																	<th class="center">检验合格数2</th>
																	<th class="center">检验合格数3</th>
																	<th class="center">检验合格数4</th>
																	<th class="center">一次合格率%</th>
																	<th class="center">维修合格接受数</th>
																	<th class="center">数量差异</th>
																	<th class="center">计划达成率</th>
																	<th class="center">报废金额</th>
																</tr>
															</thead>

															<tbody>
															  <#list materialList as list>
															  	<tr>
																	<td class="center">${list.materialCode }</td>
																	<td class="center">${list.materialName }</td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																	<td class="center"></td>
																</tr>
															  </#list>
																
															</tbody>
														</table>
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