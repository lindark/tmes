<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>报废产出对照表 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<script type="text/javascript"
	src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/BasicInfo/matbrower.js"></script>
<style>
body {
	background: #fff;
}
</style>
</head>
<body class="no-skin input">

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
					<li class="active"><#if isAdd??>添加报废产出对照表<#else>编辑报废产出对照表</#if></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm"
								action="<#if isAdd??>scrap_out!save.action<#else>scrap_out!update.action</#if>"
								method="post">


								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">报废产出对照表</a></li>

									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">产品编码</div>
												<div class="profile-info-value">
													<#if isAdd??> <input type="text"
														name="scrapOut.productsCode"
														class="formText {digits:true,required: true,minlength:2,maxlength: 100}" />
													<label class="requireField">*</label> <#else>
													${scrapOut.productsCode} <input type="hidden"
														name="scrapOut.productsCode"
														value="${(scrapOut.productsCode)!}" /> </#if>
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">产品名称</div>
												<div class="profile-info-value">
													<input type="text" name="scrapOut.productsName"
														value="${(scrapOut.productsName)!}"
														class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>
											</div>
                                               
                                               
                                            <div class="profile-info-row">
												<div class="profile-info-name">产品单位</div>
												<div class="profile-info-value">
													<input type="text" name="scrapOut.productsUnit"
														value="${(scrapOut.productsUnit)!}"
														class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>
											</div>   

											<div class="profile-info-row">
												<div class="profile-info-name">物料编码</div>
												<div class="profile-info-value">
													<#if isAdd??> <input type="text"
														name="scrapOut.materialCode"
														class="formText {digits:true,required: true,minlength:2,maxlength: 100}" />
													<label class="requireField">*</label> <#else>
													${scrapOut.materialCode} <input type="hidden"
														name="scrapOut.materialCode"
														value="${(scrapOut.materialCode)!}" /> </#if>
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">物料描述</div>
												<div class="profile-info-value">
													<input type="text" name="scrapOut.materialName"
														value="${(scrapOut.materialName)!}"
														class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>
											</div>
											
											<div class="profile-info-row">
												<div class="profile-info-name">物料描述</div>
												<div class="profile-info-value">
													<input type="text" name="scrapOut.materialUnit"
														value="${(scrapOut.materialUnit)!}"
														class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>
											</div>

											<!--weitao end modify-->


										</div>
										<div class="buttonArea">
											<input type="submit" class="formButton" value="确  定"
												hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp; <input
												type="button" class="formButton"
												onclick="window.history.back(); return false;" value="返  回"
												hidefocus="true" />
										</div>
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
		<#include "/WEB-INF/template/admin/admin_footer.ftl">
	</div>
	<!-- /.page-content -->
	</div>
	<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->

</body>
</html>
<script type="text/javascript">

	$(function() {

		$("#productSeach").click(function() {
			showProducts();
		});

	})
	//读取产品信息
	function showProducts() {
		var title = "选择产品";
		var width = "800px";
		var height = "600px";
		var content = "products!browser.action";
		jiuyi.admin.browser.dialog(title, width, height, content, function(
				index, layero) {

			var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
			//alert(iframeWin);
			var work = iframeWin.getGridId();
			var id = work.split(",");
			$("#productId").val(id[1]);
			$("#productName").text(id[0]);
			$("#productCode").text(id[2]);
			layer.close(index);
			loadData(id[1]);//加载表单数据            
		});
	}
</script>
