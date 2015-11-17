<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>权限对象管理</title>
		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		
		<#include "/WEB-INF/template/common/includelist.ftl"> <!--modify weitao-->

		<#include "/WEB-INF/template/common/include_adm_top.ftl">
	<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
	<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/ermissions/accessobject_list.js"></script>
	<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/ermissions/accessobject.js"></script>
	<style type="text/css">
		.operateBar{
			padding:3px 0px;
		}
	</style>
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
	
	<!-- ./ add by welson 0728 -->
	
<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
		</script>

		<ul class="breadcrumb">
			<li>
				<i class="ace-icon fa fa-home home-icon"></i>
				<a href="admin!index.action">管理中心</a>
			</li>
			<li class="active">权限对象</li>
		</ul><!-- /.breadcrumb -->
	</div>

	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by weitao  -->
									<form action="member!list.action" method="post" id="inputForm">
										<div class="operateBar">
											<a id="addButton" class="btn btn-white btn-sm btn-info btn-round" href="javascript:void(0);">
																<i class="ace-icon fa fa-pencil-square-o blue"></i>
																添加权限对象
											</a>
											<a id="editButton" class="btn btn-white btn-sm btn-info btn-round" href="javascript:void(0);">
																<i class="ace-icon fa fa-pencil-square-o blue"></i>
																修改权限对象
											</a>
											<a id="deleteButton" class="btn btn-white btn-sm btn-info btn-round" href="javascript:void(0);">
																<i class="ace-icon fa fa-pencil-square-o blue"></i>
																删除权限对象
											</a>
											<select name="pager.property">
												<option value="username">
													权限对象名称
												</option>
												<option value="email">
													权限类型
												</option>
											</select>
										
											<input type="text" name="pager.keyword" class="input input-sm" id="form-field-icon-1">
											<button id="searchButton" class="btn btn-white btn-default btn-sm btn-round">
												<i class="ace-icon fa fa-filter blue"></i>
												搜索
											</button>			
										</div>
									</form>
									<table id="grid-table"></table>

									<div id="grid-pager"></div>
								<!-- add by weitao -->	
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content-area -->
				<!-- PAGE CONTENT ENDS -->

	<#include "/WEB-INF/template/admin/admin_footer.ftl">
	</div><!-- /.page-content -->
	</div><!-- /.main-content -->
	</div><!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">	
	<!-- ./ add by welson 0728 -->


</body>

</html>
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