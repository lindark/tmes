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
		<script type="text/javascript" src="${base}/template/admin/js/manage/dump_list.js"></script>
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
					<li class="active">转储清单</li>
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
										<input type="hidden" id="type" name="type"value="${type}">
											<h4 class="widget-title lighter">线边仓信息</h4>

											<div class="widget-toolbar no-border">
												<a href="#" data-action="settings"> <i
													class="ace-icon fa fa-cog"></i> </a> <a href="#"
													data-action="reload"> <i class="ace-icon fa fa-refresh"></i>
												</a> <a href="#" data-action="collapse"> <i
													class="ace-icon fa fa-chevron-up"></i> </a> <a href="#"
													data-action="close"> <i class="ace-icon fa fa-times"></i>
												</a>
											</div>
										</div>

										<div class="widget-body">
											<div
												class="widget-main padding-6 no-padding-left no-padding-right">
												<div class="profile-user-info profile-user-info-striped">
													<div class="profile-info-row">
														<div class="profile-info-name">线边仓编号</div>

														<div class="profile-info-value">
															<span class="editable ">${warehouse}</span>
														</div>
														
														
														<div class="profile-info-name">线边仓名称</div>

														<div class="profile-info-value">
															<span class="editable ">${warehouseName}</span>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row buttons col-md-8 col-sm-4">
								<!-- <a id="btn_show" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-book"></i>
									查看
								</a> -->
								<a id="btn_confirm" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-cloud-upload"></i>
									刷卡确认
								</a>
								
								<a id="btn_back" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-home"></i>
									返回 
								</a>
								<a id="btn_nSure" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-pencil-square-o"></i>
									未确认 
								</a>
								<a id="btn_ySure" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-check-square-o"></i>
									已确认
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
		/**
		 * 未确认按钮点击
		 */
		$("#btn_nSure").click(function() {
			var loginid=$("#loginid").val();//当前登录人的id
				window.location.href = "dump!list.action?loginid="+loginid+"&type=1";
		});
		/**
		 * 已确认按钮点击
		 */
		$("#btn_ySure").click(function() {
			var loginid=$("#loginid").val();//当前登录人的id
				window.location.href = "dump!list.action?loginid="+loginid+"&type=2";
		});
	})
	
	
</script>