<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>æéå¯¹è±¡ç®¡ç - Powered By
	${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/includelist.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/productsbrowser.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
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
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="page-content-area">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" id="searchform" action="products!ajlist.action" role="form">
								   <div class="operateBar">
								   <div class="form-group">
										<label class="col-sm-2" style="text-align:right">产品编码:</label>
										<div class="col-sm-4">
											<input type="text" name="productsCode" class="input input-sm form-control" value="" id="form-field-icon-1">
										</div>
										
										
										<label class="col-sm-2" style="text-align:right">产品名称:</label>
										<div class="col-sm-4">
											<input type="text" name="productsName" class="input input-sm form-control" value="" id="form-field-icon-1">			
										</div>
									</div>
								   
								   
								   
								   	<div class="form-group">
									<!-- 	<label class="col-sm-1 col-md-offset-1" style="text-align:right">状态:</label>
									  <div class="col-sm-4">
										<select name="state" id="form-field-icon-1" class="input input-sm form-control">
							                <option value="">全选</option> 
							                <#list allState as list>
								            <option value="${list.dictkey}"<#if ((isAdd && list.isDefault) || (isEdit && products.state == list.dictkey))!> selected</#if>>${list.dictvalue}</option>
							                </#list>              
						               </select>
									  </div> -->
									  
									 
										
										
										
										
										<!-- <label class="col-sm-1" style="text-align:right">创建日期:</label>
										<div class="col-sm-4">
											<div class="input-daterange input-group">
												<input type="text" class="input-sm form-control datePicker" name="start">
												<span class="input-group-addon">
													<i class="fa fa-exchange"></i>
												</span>

												<input type="text" class="input-sm form-control datePicker" name="end">
											</div>
										</div> -->
									</div>	
										<div class="form-group" style="text-align:center">
											<a id="searchButton" class="btn btn-white btn-default btn-sm btn-round">
												<i class="ace-icon fa fa-filter blue"></i>
												搜索
											</a>
										</div>
										
									</div>
								</form>
								
								
								<table id="grid-table"></table>

								<div id="grid-pager"></div>
								
								
									
								<script type="text/javascript">
									var $path_base = "${base}/template/admin";//in Ace demo this will be used for editurl parameter
								</script>

								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content-area -->
				</div><!-- /.page-content -->
			<!-- /.row -->
		</div>
		<!-- /.page-content-area -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->

</body>