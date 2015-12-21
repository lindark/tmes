<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑工序管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/BasicInfo/process_input.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/BasicInfo/matbrower.js"></script>
<style type="text/css">
.mymust {
	color: red;
	font-size: 10px;
}

.p_name {
	width: 420px;
	line-height: 30px;
	border: 1px solid;
	border-color: #d5d5d5;
}
</style>
<script type="text/javascript">
	$().ready(function() {
		// 地区选择菜单
		$(".areaSelect").lSelect({
			url : "${base}/admin/area!ajaxChildrenArea.action"// Json数据获取url
		});
	});
</script>
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
					<li class="active"><#if isAdd??>添加工序记录<#else>编辑工序记录</#if></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm" class="validate"
								action="process!save.action" method="post">
								<input type="hidden" name="id" value="${id}" />
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">产品工序信息</a></li>

									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<!--  <div class="profile-info-name">展开层</div>
												<div class="profile-info-value">
													<input type="text" name="material.spread"
														value="${(material.spread)!}"
														class=" input input-sm formText {required: true,minlength:0,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>-->

												<div class="profile-info-name">产品名称</div>
												<div class="profile-info-value">
													<input type="hidden" id="productId"
														name="material.products.id"
														value="${(material.products.id)!}"
														class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}"
														readonly="readonly" />
													<button type="button" class="btn btn-xs btn-info"
														id="userAddBtn" data-toggle="button">选择</button>
													<span id="productName"></span> <label class="requireField">*</label>
												</div>


												<div class="profile-info-name">产品数量</div>
												<div class="profile-info-value">
													<input type="text" name="material.batch"
														value="${(material.products.productsAmount)!}"
														class=" input input-sm formText {required: true,minlength:1,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>
											</div>
										</div>
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="row buttons col-md-8 col-sm-4">
													<a id="btn_add"
														class="btn btn-white btn-default btn-sm btn-round"> <i
														class="ace-icon glyphicon glyphicon-plus"></i> 增加一行工序
													</a>
												</div>
											</div>
											<div class="profile-info-row">
												<table id="tb_material"
													class="table table-striped table-bordered table-hover">
													<tr>
														<th>产品编号</th>
														<th>产品名称</th>
														<th>产品数量</th>
														<th>工序编码</th>
														<th>工序名称</th>
														<th>工作中心</th>
														<th>计量单位</th>
														<th>版本号</th>
													</tr>
													<#if materialList??> <#assign slnum=0 /> <#list
													materialList as sllist>
													<tr>
														<td>${(sllist.materialCode)! }</td>
														<td>${(sllist.materialName)! }</td>
														<td>${(sllist.materialUnit)! }</td>
														<td>${(sllist.materialAmount)!}</td>
														<td>${(sllist.batch)!}</td>
														<td>${(sllist.isCarton)!}</td>
														<td>${(sllist.version)!}</td>
													</tr>
													<#assign slnum=slnum+1 /> </#list> </#if>
												</table>
											</div>
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
		$("#btn_add").click(function() {
			alert("...");
		});
	})
</script>