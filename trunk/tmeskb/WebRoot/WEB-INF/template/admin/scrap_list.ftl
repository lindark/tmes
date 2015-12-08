<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>报废单管理</title>
<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<#include "/WEB-INF/template/common/includelist.ftl">
<!--modify weitao-->
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<script type="text/javascript"src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript"src="${base}/template/admin/js/BasicInfo/scrap_list.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
<style type="text/css">
.operateBar {padding: 3px 0px;}
</style>
</head>
<body class="no-skin list">
	<!-- add by welson 0728 -->
	<#include "/WEB-INF/template/admin/admin_navbar.ftl">
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {ace.settings.check('main-container', 'fixed')} catch (e) {}
		</script>
		<#include "/WEB-INF/template/admin/admin_sidebar.ftl">
		<div class="main-content">
			<#include "/WEB-INF/template/admin/admin_acesettingbox.ftl">
			<!-- ./ add by welson 0728 -->
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try {ace.settings.check('breadcrumbs', 'fixed')} catch (e) {}
				</script>
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a
						href="admin!index.action">管理中心</a></li>
					<li class="active">报废单</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">
					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by weitao  -->
							<div class="row">
								<div class="col-xs-12 col-sm-12 widget-container-col">
									<div class="widget-box transparent">
									
										<div class="widget-header">
											<h4 class="widget-title lighter">当前随工单</h4>
											<div class="widget-toolbar no-border">
												<a href="#" data-action="settings"><i class="ace-icon fa fa-cog"></i></a> 
												<a href="#" data-action="reload"><i class="ace-icon fa fa-refresh"></i></a>
												<a href="#" data-action="collapse"><i class="ace-icon fa fa-chevron-up"></i></a>
												<a href="#" data-action="close"><i class="ace-icon fa fa-times"></i></a>
											</div>
										</div>

										<div class="widget-body">
											<div class="widget-main padding-6 no-padding-left no-padding-right">
												<div class="profile-user-info profile-user-info-striped">
													<div class="profile-info-row">
														<div class="profile-info-name">随工单号</div>
														<div class="profile-info-value">
															<span class="editable editable-click">${(workingbill.workingBillCode)!}</span>
														</div>
													</div>

													<div class="profile-info-row">
														<div class="profile-info-name">产品编号</div>
														<div class="profile-info-value">
															<span class="editable editable-click" id="username">${(workingbill.matnr)!}</span>
														</div>
													</div>

													<div class="profile-info-row">
														<div class="profile-info-name">产品名称</div>
														<div class="profile-info-value">
															<span class="editable editable-click" id="age">${(workingbill.maktx)!}</span>
														</div>
													</div>

													<div class="profile-info-row">
														<div class="profile-info-name">班次</div>
														<div class="profile-info-value">
															<#if (admin.shift == "1")!><span class="editable editable-click" id="signup">早</span></#if>
															<#if (admin.shift == "2")!><span class="editable editable-click" id="signup">中</span></#if>
															<#if (admin.shift == "3")!><span class="editable editable-click" id="signup">晚</span></#if>
														</div>
													</div>
													<!-- 
													<div class="profile-info-row">
														<div class="profile-info-name">时间</div>
														<div class="profile-info-value">
															<span class="editable editable-click" id="signup">${(workingbill.productDate)!}</span>
														</div>
													</div>
													-->
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row buttons col-md-8 col-sm-4">
								<a id="btn_creat" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-folder-open-o"></i>
									创建报废单
								</a>
								<a id="btn_confirm" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-cloud-upload"></i>
									刷卡确认
								</a>
								<a id="btn_revoke" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon glyphicon glyphicon-remove"></i>
									刷卡撤销
								</a>
								<a id="btn_edit" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon glyphicon glyphicon-edit"></i>
									编辑
								</a>
								<a id="btn_show" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-book"></i>
									查看
								</a>
								<a id="btn_back" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-home"></i>
									返回
								</a>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<table id="grid-table"></table>

									<div id="grid-pager"></div>
								</div>
							</div>
							<!-- add by weitao -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content-area -->
				<!-- PAGE CONTENT ENDS -->
				<#include "/WEB-INF/template/admin/admin_footer.ftl">
			</div>
			<!-- /.page-content -->
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->
	<input type="hidden" id="wbId" value="${(workingbill.id)!}"/>
</body>
</html>