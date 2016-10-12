<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title></title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/includelist.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/admin_empchoose.js"></script>
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
								action="repairin!historylist.action" role="form">
								<div class="operateBar">
									<div class="form-group">
										<!--  
										<label class="col-sm-1 col-md-offset-1"
											style="text-align:right">姓名:</label>
										<div class="col-sm-4">
											<input type="text" name="empname"
												class="input input-sm form-control" value=""
												id="form-field-icon-1">
										</div> -->
										
										<label class="col-sm-2"
											style="text-align:right;">姓名:</label>
										<div class="col-sm-4">
											<input type="text" id="input_name" name="name" value="" >
										</div>
										<label class="col-sm-2"
											style="text-align:right;">员工工号:</label>
										<div class="col-sm-4">
											<input type="text" id="input_worknum" name="workNumber" value="" >
										</div>
									</div>
									<div class="form-group" style="text-align:center">
										<a id="searchButton"
											class="btn btn-white btn-default btn-sm btn-round">
											 <i class="ace-icon fa fa-filter blue"></i> 搜索
										</a>
									<!-- 	<a  id="excelReport" class="btn btn-white btn-default btn-sm btn-round">
											<i class="ace-icon fa fa-filter blue"></i>Excel导出
										</a> -->
									</div>

								</div>
							</form>


							<table id="grid-table"></table>

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
	/**
	 * 用了ztree 有这个bug，这里是处理。不知道bug如何产生
	 */
	 function getGridId(){
			var ids=$("#grid-table").jqGrid('getGridParam','selarrrow');
			if(ids.length <1){
				alert("请选择一条记录");
				return false;
			}
			if(ids.length >1){
				alert("请选择一条记录");
				return false;
			}
			var rowData = $("#grid-table").jqGrid('getRowData',ids);
			var rowName=rowData.name;
			var rowCode=rowData.workNumber;
			var work=""+ids+""+","+rowName+","+rowCode;
			return work;
		}
	
	$(function(){
		var ishead=0;
		$("#ace-settings-btn").click(function(){
			if(ishead==0){
				ishead=1;
				$("#ace-settings-box").addClass("open");
			}else{
				ishead=0;
				$("#ace-settings-box").removeClass("open");
			}
		});
		$(".btn-colorpicker").click(function(){
				$(".dropdown-colorpicker").addClass("open");
		})
		
		var ishead2=0;
		$(".light-blue").click(function(){
			if(ishead2==0){
				ishead2=1;
				$(this).addClass("open");
			}else{
				ishead2=0;
				$(this).removeClass("open");
			}
			
		})
		
		/*
		var ishead3=0;
		$(".hsub").click(function(){
			if(ishead3==0){
				alert("OK");
				ishead3=1;
				$(".hsub").addClass("open");
				//$(this).find(".submenu").removeClass("nav-hide");
			}else{
				ishead3=0;
				//$(this).removeClass("open");
				//$(this).find(".submenu").removeClass("nav-show").addClass("nav-hide").css("display","none");
			}
			
		})
		*/
	})
	
	
</script>