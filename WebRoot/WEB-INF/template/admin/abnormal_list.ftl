<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>异常</title>
		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		
		<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/jiuyi/admin/css/style.css"/>
		<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/jiuyi/admin/css/setstyle.css"/>
		<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/ztree/css/zTreeStyle/zTreeStyle.css"/>
		<link rel="stylesheet" href="${base}/template/admin/assets/css/jquery-ui.min.css" />
		
		
		<#include "/WEB-INF/template/common/includelist.ftl"> <!--modify weitao-->
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>		
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/user/admin.js"></script>
		<script type="text/javascript"src="${base}/template/admin/js/unusual/js/abnormal_list.js"></script>
				<script type="text/javascript"src="${base}/template/admin/js/unusual/js/abnormal.js"></script>
        <script type="text/javascript"src="${base}/template/admin/js/unusual/js/ring.js"></script>
		<script src="${base}/template/admin/assets/js/jquery-ui.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
		<#include "/WEB-INF/template/common/include_adm_top.ftl">
		
		

<style type="text/css">
	.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
    .buttons {
	padding: 10px 5px;
	margin: 6px 3px;
}


.call {
    width: 90px;
	height: 45px;
	font-size: 16px;
}

th {
	height: 28px;
	width: 200px;
	padding-left: 100px;
}

.ui-jqgrid tr.jqgrow td {
white-space: normal !important;
height:auto;
vertical-align:text-top;
padding-top:2px;
}
</style>

<script type="text/javascript">
	
	
	
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
			<li class="active">异常&nbsp;<span class="pageInfo"></span></li>
		</ul><!-- /.breadcrumb -->
	</div>

	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<div class="row">
								<div class="col-xs-12 col-sm-6 widget-container-col ui-sortable">

									<div class="widget-box ui-sortable-handle">
										<div class="widget-header">
											<h4 class="widget-title lighter">当前班组信息</h4>
										</div>

										<div class="widget-body">
											<div class="widget-main">
												<div class="profile-user-info profile-user-info-striped">
													<div class="profile-info-row">
														<div class="profile-info-name"> 工厂</div>
	
														<div class="profile-info-value">
															<span class="editable editable-click" id="username">${(admin.department.team.factoryUnit.workShop.factory.factoryName)!}</span>
														</div>
													</div>
	
													<div class="profile-info-row">
														<div class="profile-info-name">车间</div>
	
														<div class="profile-info-value">
															<span class="editable editable-click" id="username">${(admin.department.team.factoryUnit.workShop.workShopName)!}</span>			
														</div>
													</div>
	
													<div class="profile-info-row">
														<div class="profile-info-name"> 单元 </div>
	
														<div class="profile-info-value">
															<span class="editable editable-click" id="age">${(admin.department.team.factoryUnit.factoryUnitName)!}</span>
														</div>
													</div>
	
													<div class="profile-info-row">
														<div class="profile-info-name"> 班组</div>
	
														<div class="profile-info-value">
															<span class="editable editable-click" id="signup">${admin.department.team.teamName }</span>
														</div>
													</div>
    
												</div>
											</div>
										</div>
									</div>


								</div>
								
								
								
								<div class="col-xs-12 col-sm-6 widget-container-col ui-sortable">
									<div class="widget-box ui-sortable-handle">
										<div class="widget-header">
											<h5 class="widget-title">呼叫人</h5>
										</div>

										<div class="widget-body">
											<div class="widget-main">                                             
												<div style="height:130px;padding-left:186px;padding-top:42px;"> 
												<button class="btn btn-success call" id="callRing">呼叫</button> 												
												</div>												 										   										
											</div>
										</div>
									</div>
								</div>
																								
                               
							</div>																												

							<div class="row buttons">						  
								<div class="col-md-2 col-sm-4" >
									<button	class="btn btn-white btn-success btn-bold btn-round btn-block" id="qualityque">
                                      <span class="bigger-110 no-text-shadow">创建质量问题单</span>
									</button>
								</div>
								 <div class="col-md-2 col-sm-4" >
									<button	class="btn btn-white btn-success btn-bold btn-round btn-block" id="modelrep">
                                      <span class="bigger-110 no-text-shadow">创建工模维修单</span>
									</button>
								</div>
								<div class="col-md-2 col-sm-4" >
									<button	class="btn btn-white btn-success btn-bold btn-round btn-block" id="craftrep">
                                      <span class="bigger-110 no-text-shadow">创建工艺维修单</span>
									</button>
								</div>
								 
								<div class="col-md-2 col-sm-4" >
									<button	class="btn btn-white btn-success btn-bold btn-round btn-block" id="devicerep">
                                      <span class="bigger-110 no-text-shadow">创建设备维修单</span>
									</button>
								</div> 
								<div class="col-md-2 col-sm-4" >
									<button	class="btn btn-white btn-success btn-bold btn-round btn-block" id="responsecar">
                                      <span class="bigger-110 no-text-shadow">响应刷卡</span>
									</button>
								</div>
								<div class="col-md-2 col-sm-4" >
									<button	class="btn btn-white btn-success btn-bold btn-round btn-block" id="cancelcal">
                                      <span class="bigger-110 no-text-shadow">撤销呼叫</span>
									</button>
								</div>
								<!-- 
								<div class="col-md-2 col-sm-4" >
									<button	class="btn btn-white btn-success btn-bold btn-round btn-block" id="closeAbn">
                                      <span class="bigger-110 no-text-shadow">关闭异常</span>
									</button>
								</div> -->
							</div>

							<table id="grid-table1"></table>

							<div id="grid-pager1"></div>

							<script type="text/javascript">
								var $path_base = "${base}/template/admin";
							</script>


						</div>
					</div>	
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

<!-- 弹出层 -->
<div id="dialog-message">
</div>
<!-- 弹出层end -->
</body>

	<script type="text/javascript" src="${base}/template/admin/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${base}/template/admin/ztree/js/jquery.ztree.exedit-3.5.min.js"></script>
	<script type="text/javascript" src="${base}/template/admin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>

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
		
	})
</script>