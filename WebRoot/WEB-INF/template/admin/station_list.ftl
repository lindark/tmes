<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>工位管理</title>
		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<#include "/WEB-INF/template/common/includelist.ftl"> 
		<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/station_list.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
		<#include "/WEB-INF/template/common/include_adm_top.ftl">
		<script type="text/javascript">
			$(document).keydown(function(event){
				if(event.keyCode==13)
				{
					$("#searchButton").click();
				}
			});
		</script>
	</head>
	<body class="no-skin list">
	
<!-- add by welson 0728 -->	
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
			<li class="active">工位列表</li>
		</ul>
	</div>
		<!-- add by welson 0728 -->
		<!-- /section:basics/content.breadcrumbs -->
		<div class="page-content">
			<div class="page-content-area">
				<div class="row">
					<div class="col-xs-12">
						<!-- PAGE CONTENT BEGINS -->
						<form class="form-horizontal" id="searchform" action="station!ajlist.action" role="form">
							<div class="operateBar">
						   		<div class="form-group">
						   			<label class="col-sm-1 col-md-offset-1" style="text-align:right">工位编码</label>
									<div class="col-sm-4">
										<input type="text" name="stationcode" class="input input-sm form-control" value="" id="form-field-icon-1">
									</div>
						   			<label class="col-sm-1" style="text-align:right">工位名称</label>
									<div class="col-sm-4">
										<input type="text" name="stationname" class="input input-sm form-control" value="" id="form-field-icon-1">
									</div>
						   		</div>
								<div class="form-group">
									<label class="col-sm-1 col-md-offset-1" style="text-align:right">岗位名称</label>
									<div class="col-sm-4">
										<input type="text" name="postname" class="input input-sm form-control" value="" id="form-field-icon-1">
									</div>
									<label class="col-sm-1" style="text-align:right">是否启用</label>
									<div class="col-sm-4">
										<select name="isWork" id="form-field-icon-1" class="input input-sm form-control" style="width:200px;">
											<option value="">&nbsp;</option>
											<option value="Y">已启用</option>
											<option value="N">未启用</option>
										</select>
									</div>
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
	</div><!-- /.main-content -->

	<#include "/WEB-INF/template/admin/admin_footer.ftl">
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">

	<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
		<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
	</a>
</div><!-- /.main-container -->
<!-- inline scripts related to this page -->
<script type="text/javascript">
	/**
	 * 用了ztree 有这个bug，这里是处理。不知道bug如何产生
	 */
	
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
	</body>
</html>
