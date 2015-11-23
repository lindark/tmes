<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>返工单</title>
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
		<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/rework_list.js"></script>
		<script src="${base}/template/admin/assets/js/jquery-ui.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
		<#include "/WEB-INF/template/common/include_adm_top.ftl">
		
		

<style type="text/css">
	.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
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
			<li class="active">返工单&nbsp;<span class="pageInfo"></span></li>
		</ul><!-- /.breadcrumb -->
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
													</div>

													<div class="profile-info-row">
														<div class="profile-info-name">产品编号</div>

														<div class="profile-info-value">
															<!--<i class="fa fa-map-marker light-orange bigger-110"></i>-->
															<span class="editable editable-click" id="username">${workingbill.matnr}</span>
															<!--<span	 class="editable editable-click" id="country">Netherlands</span>-->
															<!--<span class="editable editable-click" id="city">Amsterdam</span>-->
														</div>
													</div>

													<div class="profile-info-row">
														<div class="profile-info-name">产品名称</div>

														<div class="profile-info-value">
															<span class="editable editable-click" id="age">${workingbill.maktx}</span>
														</div>
													</div>
													
													<div class="profile-info-row">
														<div class="profile-info-name">班组/班次</div>

														<div class="profile-info-value">
															<span class="editable editable-click" id="signup">2010/06/20</span>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row buttons">
								
									<button class="btn btn-white btn-success btn-bold btn-round " id="addRework">
										<span class="bigger-110 no-text-shadow">创建返工单</span>
									</button>

                                    <button class="btn btn-white btn-success btn-bold btn-round " id="editRework">
										<span class="bigger-110 no-text-shadow">编辑返工单</span>
									</button>

									<button class="btn btn-white btn-success btn-bold btn-round" id="undoRework">
										<span class="bigger-110 no-text-shadow">刷卡撤销</span>
									</button>


									<button class="btn btn-white btn-success btn-bold btn-round" id="returnRework">
										<span class="bigger-110 no-text-shadow">返回</span>
									</button>
				
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
					</div><!-- /.page-content-area -->
				<!-- PAGE CONTENT ENDS -->

	<#include "/WEB-INF/template/admin/admin_footer.ftl">
	</div><!-- /.page-content -->
	</div><!-- /.main-content -->
	</div><!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">	
	<!-- ./ add by welson 0728 -->
	<div style="display:none">
		<input type="hidden" id="workingBillId" value="${workingbill.id}"/>
	</div>

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
		
		$("#addRework").click(function(){
			var workingBillId = $("#workingBillId").val();
			window.location.href="rework!add.action?workingBillId="+$("#workingBillId").val();
			
		});
		
		
		$("#editRework").click(function(){
			var workingBillId = $("#workingBillId").val();
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			if(id.length>1){
	    		alert("只能选择一条返工记录！");
	    		return false;
	    	}if(id==""){
	    		alert("至少选择一条返工记录");
	    		return false;
	    	}else{
	    		window.location.href = "rework!edit.action?id=" + id+"&workingBillId="+$("#workingBillId").val();	    		
	    	}	
		});
		
		
		$("#checkRework").click(function(){
			var workingBillId = $("#workingBillId").val();
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			//alert(id);
			if(id.length>1){
	    		alert("只能选择一条返工记录！");
	    		return false;
	    	}if(id==""){
	    		alert("至少选择一条返工记录");
	    		return false;
	    	}else{
	    		window.location.href = "rework!edit.action?id=" + id+"&workingBillId="+$("#workingBillId").val();	    		
	    	}	
		});
		
		$("#confirmRework").click(function(){
			var workingBillId = $("#workingBillId").val();
			var id = "";
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			if(id==""){
				alert("请选择至少一条返工记录！");
			}else{
				window.location.href="carton!confirms.action?id="+id+"&workingBillId="+$("#workingBillId").val();			
			}
			
		});
		$("#undoRework").click(function(){
			var workingBillId = $("#workingBillId").val();
			var id = "";
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			if(id==""){
				alert("请选择至少一条返工记录！");
			}else{
				window.location.href="rework!undo.action?id="+id+"&workingBillId="+$("#workingBillId").val();			
			}
			
		});
		$("#returnRework").click(function(){
			window.history.back();
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