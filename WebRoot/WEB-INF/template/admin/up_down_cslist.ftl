<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>管理中心</title>
		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<#include "/WEB-INF/template/common/includelist.ftl"> <!--modify weitao-->
		<script type="text/javascript" src="${base}/template/admin/js/manage/updown_cslist.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
		<#include "/WEB-INF/template/common/include_adm_top.ftl">
	</head>
	<body class="no-skin list">
<input type="hidden" id="loginid" value="<@sec.authentication property='principal.id' />" />
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
					<li class="active">超市领料</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by weitao  -->
							<div class="profile-user-info profile-user-info-striped">
								<div class="profile-info-row">												
									<div class="profile-info-name">当前状态</div>
									<div class="profile-info-value">
										<#list updownObjList as list >
											发出仓位:${list[1] },
											物料:${list[2] } &nbsp; ${list[3] },
											数量:${list[0] } </br>
										</#list>
									</div>			
								</div>
							</div>
							<div class="row buttons col-md-8 col-sm-4">
							<form class="form-horizontal" id="searchform" action="up_down!csajlist.action" role="form">	
								<a id="btn_csll" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-cloud-upload"></i>
									超市领料
								</a>
								<a id="btn_show" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-book"></i>
									查看
								</a> 
								<a id="btn_back" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-home"></i>
									返回 
								</a>
									<select name="pager.property">
										<option value="uplgpla">
											发出仓位
										</option>
										<option value="downlgpla">
											接收仓位
										</option>
									</select>
									<input type="text" name="pager.keyword" class="input input-sm" id="form-field-icon-1">
									<button id="searchButton" class="btn btn-white btn-default btn-sm btn-round">
										<i class="ace-icon fa fa-filter blue"></i>
										搜索
									</button>
								</form>
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
		$("#btn_csll").click(function(){
			window.location.href="up_down!add.action?type=updown";
			
		})
		
		/**
		查看
		**/
		$("#btn_show").click(function(){
			
			var id = "";
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			if(id.length>1){
				$.message("error","只能选择一条记录");
				return false;
			}if(id==""){
				$.message("error","请选择一行记录");
				return false;
			}else{
				window.location.href="up_down!view.action?id="+id;				
			}	
		})
		
		$("#btn_back").click(function(){
			window.location.href="admin!index.action";			
		})
		
	})
	
	
</script>