<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>人员信息- Powered By
	${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/includelist.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/browser/adminBrowser.js"></script>
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
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->
							<form class="form-horizontal" id="searchform"
								action="admin!ajlist1.action" role="form">
								<div class="operateBar">
									<div class="form-group">
										<label class="col-sm-2"
											style="text-align:right;">姓名:</label>
										<div class="col-sm-4">
											<input type="text" name="adminName"
												class="input input-sm form-control" value=""
												id="form-field-icon-1">
										</div>


										<label class="col-sm-2" style="text-align:right;">部门:</label>
										<div class="col-sm-4">
											<input type="text" name="adminDeptName"
												class="input input-sm form-control" value=""
												id="form-field-icon-1">
										</div>
									</div>									

									

									<div class="form-group" style="text-align:center">
										<a id="searchButton"
											class="btn btn-white btn-default btn-sm btn-round"> <i
											class="ace-icon fa fa-filter blue"></i> 搜索 </a>
									</div>

								</div>
							</form>


							<table id="grid-table2"></table>

							<div id="grid-pager"></div>
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
<script type="text/javascript">
	function getGridId(){
		var ids=$("#grid-table2").jqGrid('getGridParam','selarrrow');
		if(ids.length <1){
			alert("请选择一条记录");
			return false;
		}
		if(ids.length >1){
			alert("请选择一条记录");
			return false;
		}
		var rowData = $("#grid-table2").jqGrid('getRowData',ids);
		var rowName=rowData.name;
		var work=""+rowName+""+","+ids;
		return work;
	}
	
</script>