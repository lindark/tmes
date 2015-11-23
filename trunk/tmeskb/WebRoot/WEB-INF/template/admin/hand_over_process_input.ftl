<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>权限对象管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />

<#include "/WEB-INF/template/common/includelist.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
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

							<form id="inputForm" class="validateajax"
								action="<#if isAdd??>admin!save.action<#else>admin!update.action</#if>"
								method="post">
								
								<table class="table table-striped table-bordered">
															<thead>
																<tr>
																	<th class="center">#</th>
																	<th>Product</th>
																	<th class="hidden-xs">Description</th>
																	<th class="hidden-480">Discount</th>
																	<th>Total</th>
																</tr>
															</thead>

															<tbody>
																<tr>
																	<td class="center">1</td>

																	<td>
																		<a href="#">google.com</a>
																	</td>
																	<td class="hidden-xs">
																		1 year domain registration
																	</td>
																	<td class="hidden-480"> --- </td>
																	<td>$10</td>
																</tr>

																<tr>
																	<td class="center">2</td>

																	<td>
																		<a href="#">yahoo.com</a>
																	</td>
																	<td class="hidden-xs">
																		5 year domain registration
																	</td>
																	<td class="hidden-480"> 5% </td>
																	<td>$45</td>
																</tr>

																<tr>
																	<td class="center">3</td>
																	<td>Hosting</td>
																	<td class="hidden-xs"> 1 year basic hosting </td>
																	<td class="hidden-480"> 10% </td>
																	<td>$90</td>
																</tr>

																<tr>
																	<td class="center">4</td>
																	<td>Design</td>
																	<td class="hidden-xs"> Theme customization </td>
																	<td class="hidden-480"> 50% </td>
																	<td>$250</td>
																</tr>
															</tbody>
														</table>
								
							</form>

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