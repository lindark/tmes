<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>领/退料</title>
		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<#include "/WEB-INF/template/common/includelist.ftl"> <!--modify weitao-->
		<script type="text/javascript" src="${base}/template/admin/js/manage/pick_list.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/manage/pick.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
		<#include "/WEB-INF/template/common/include_adm_top.ftl">
		
	<script type="text/javascript">
		var productCode = "${workingbill.matnr}";
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
			<li class="active">领/退料</li>
		</ul><!-- /.breadcrumb -->
	</div>
	
	
	<!-- add by welson 0728 -->
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content" id="page-content">					
					<div class="page-content-area">
							<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by weitao  -->
							<div class="row">
								<div class="col-xs-12 col-sm-12 widget-container-col">
									<div class="widget-box transparent">
										<div class="widget-header">
											<h4 class="widget-title lighter">随工单信息</h4>

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
														<div class="profile-info-name">随工单号</div>

														<div class="profile-info-value">
															<span class="editable editable-click">${workingbill.workingBillCode}</span>
														</div>
														
														
														<div class="profile-info-name">产品编号</div>

														<div class="profile-info-value">
															<!--<i class="fa fa-map-marker light-orange bigger-110"></i>-->
															<span class="editable editable-click" id="username">${workingbill.matnr}</span>
															<!--<span	 class="editable editable-click" id="country">Netherlands</span>-->
															<!--<span class="editable editable-click" id="city">Amsterdam</span>-->
														</div>
													</div>

													<div class="profile-info-row">												
														<div class="profile-info-name">班次</div>
														<div class="profile-info-value">
															<#if (admin.shift == "1")!><span class="editable editable-click" id="signup">早</span></#if>
															<#if (admin.shift == "2")!><span class="editable editable-click" id="signup">中</span></#if>
															<#if (admin.shift == "3")!><span class="editable editable-click" id="signup">晚</span></#if>
														</div>
														
														
														<div class="profile-info-name">产品名称</div>
														<div class="profile-info-value">
															<span class="editable editable-click" id="age">${workingbill.maktx}</span>
														</div>
														
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
									<div>
									<button class="btn btn-white btn-default btn-sm btn-round" id="addPick" type=button>
										<i class="ace-icon fa fa-folder-open-o"></i>
										创建领料单
									</button>
									<!--  <a id="pickBtn" class="btn btn-white btn-default btn-sm btn-round" href="pick_detail!list.action?matnr=${(workingbill.matnr)!}&workingBillId=${workingbill.id}">
										<i class="ace-icon fa fa-folder-open-o"></i>
										创建领料单
									</a>-->
									<button class="btn btn-white btn-default btn-sm btn-round" id="viewPick" type=button>
										<i class="ace-icon glyphicon glyphicon-zoom-in"></i>
										查看领料单
									</button>
									<button class="btn btn-white btn-default btn-sm btn-round" id="confirmPick" type=button>
										<i class="ace-icon fa fa-cloud-upload"></i>
										刷卡确认
									</button>
									<button class="btn btn-white btn-default btn-sm btn-round" id="repealPick" type=button>
										<i class="ace-icon glyphicon glyphicon-remove"></i>
										刷卡撤销
									</button>
									<button class="btn btn-white btn-default btn-sm btn-round" id="returnPick">
										<i class="ace-icon fa fa-home"></i>
										返回
									</button>
								   </div>
							
								<!-- PAGE CONTENT BEGINS -->
								<div class="row">	
									<div class="col-xs-12">					
										<table id="grid-table"></table>
		
										<div id="grid-pager"></div>
									</div>
								</div>
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
					<!-- /section:basics/footer -->
				</div>
			</div>

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->
		
		

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			
		</script>
	</body>
	
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
		
		$("#addPick").click(function(){
			window.location.href="pick_detail!list.action?matnr=${(workingbill.matnr)!}&workingBillId=${workingbill.id}";
			
		});
		
		
		$("#confirmPick").click(function(){
			var id = "";
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			if(id==""){
				alert("请选择至少一条记录！");
			}else{
				window.location.href="pick!confirms.action?id="+id;			
			}
			
		});
		
		$("#repealPick").click(function(){
			var id = "";
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			if(id==""){
				alert("请选择至少一条记录！");
			}else{
				window.location.href="pick!repeal.action?id="+id;			
			}
			
		});
		
		
		$("#viewPick").click(function(){
			var id = "";
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			if(id.length>1){
				alert("只能选择一条领退料记录！");
				return false;
			}if(id==""){
				alert("至少选择一条领退料记录！");
				return false;
			}else{
				window.location.href="pick_detail!view.action?id="+id+"&matnr="+${(workingbill.matnr)!};				
			}			
		});
		
		$("#returnPick").click(function(){
			window.history.back();
		});
	})
	
	
</script>
</html>
