<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>质量问题清单</title>
		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<#include "/WEB-INF/template/common/includelist.ftl"> <!--modify weitao-->
		<script type="text/javascript" src="${base}/template/unusual/js/quality_list.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<#include "/WEB-INF/template/common/include_adm_top.ftl">
		<style>
			.operateBar{
				padding:3px 0px;
			}
		</style>
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
	
     <div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
		</script>

		<ul class="breadcrumb">
			<li>
				<i class="ace-icon fa fa-home home-icon"></i>
				<a href="admin!index.action">管理中心</a>
			</li>
			<li class="active">质量问题列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></li>
		</ul>
	</div>	
	
				<div class="page-content">					
					<div class="page-content-area">					
					
						<div class="row">
							<div class="col-xs-12">
						       <form class="form-horizontal" role="form">
								   <div class="operateBar">
								   	<div class="form-group">
										<label class="col-sm-1 col-md-offset-1" style="text-align:right">时间:</label>
										<div class="col-sm-4">
											<input type="text" name="pager.keyword" class="input input-sm form-control" value="" id="form-field-icon-1">
										</div>
										<label class="col-sm-1" style="text-align:right">检验人:</label>
										<div class="col-sm-4">
											<input type="text" name="pager.keyword" class="input input-sm form-control" value="" id="form-field-icon-1">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-1 col-md-offset-1" style="text-align:right">产品名称:</label>
										<div class="col-sm-4">
											<input type="text" name="pager.keyword" class="input input-sm form-control" value="" id="form-field-icon-1">
										</div>
										<label class="col-sm-1" style="text-align:right">状态:</label>
										<div class="col-sm-4">
											<input type="text" name="pager.keyword" class="input input-sm form-control" value="" id="form-field-icon-1">
										</div>
									</div>
										<div class="form-group" style="text-align:center">
											<button id="searchButton" class="btn btn-white btn-default btn-sm btn-round">
											<i class="ace-icon fa fa-cloud-download blue"></i>
											查询
											</button>
										</div>
																								
									</div>
								</form>
								
								<table id="grid-table"></table>

								<div id="grid-pager"></div>
								
								
									
								<script type="text/javascript">
									var $path_base = "${base}/template/admin";//in Ace demo this will be used for editurl parameter
								</script>

								
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="footer">
				<div class="footer-inner">
				
					<div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">Ace</span>
							Application &copy; 2013-2014
						</span>

						&nbsp; &nbsp;
						<span class="action-buttons">
							<a href="#">
								<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a>
						</span>
					</div>

					
				</div>
			</div>

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div>
				
		<script type="text/javascript">
			
		</script>
	</body>
</html>
