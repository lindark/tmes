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
		<script type="text/javascript" src="${base}/template/admin/js/manage/repairin_list.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
		<#include "/WEB-INF/template/common/include_adm_top.ftl">
	</head>
	<body class="no-skin list">

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
					<li class="active">返修收货</li>
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
														<div class="profile-info-name">班组/班次</div>

														<div class="profile-info-value">
															<span class="editable editable-click" id="signup">
																<#if (admin.shift == "1")!>早</#if>
																<#if (admin.shift == "2")!>中</#if>
																<#if (admin.shift == "3")!>晚</#if>
															</span>
														</div>
														<div class="profile-info-name">产品名称</div>

														<div class="profile-info-value">
															<span class="editable editable-click" id="age">${workingbill.maktx}</span>
														</div>
														
													</div>
													
													<div class="profile-info-row">
														<div class="profile-info-name">总返修收货数量</div>

														<div class="profile-info-value">
															<span class="editable editable-click" id="totalAmount">${workingbill.totalRepairinAmount}</span>
														</div>
													</div>

												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row buttons col-md-8 col-sm-4">
								<a id="addCarton" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-folder-open-o"></i>
									创建返修收货单
								</a>
								<a id="confirmCarton" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-cloud-upload"></i>
									刷卡确认
								</a>
								<a id="undoCarton" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon glyphicon glyphicon-remove"></i>
									刷卡撤销
								</a>
								<a id="returnCarton" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-home"></i>
									返回
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
	<div style="display:none">
		<input type="hidden" id="workingBillId" value="${workingbill.id}"/>
	</div>

</body>

</html>
<script type="text/javascript">
	/**
	 * 用了ztree 有这个bug，这里是处理。不知道bug如何产生
	 */

	$(function() {
		var ishead = 0;
		$("#ace-settings-btn").click(function() {
			if (ishead == 0) {
				ishead = 1;
				$("#ace-settings-box").addClass("open");
			} else {
				ishead = 0;
				$("#ace-settings-box").removeClass("open");
			}
		});
		
		$("#addCarton").click(function(){
			var workingBillId = $("#workingBillId").val();
			window.location.href="repairin!add.action?workingBillId="+workingBillId;
			
		});
		$("#confirmCarton").click(function(){
			var workingBillId = $("#workingBillId").val();
			var id = "";
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			if(id==""){
				layer.msg("请选择一条记录!", {icon: 5});
			}else{
				var index = "";
				$.ajax({	
					url: "repairin!creditapproval.action?id="+id+"&workingBillId="+workingBillId,
					//data: $(form).serialize(),
					dataType: "json",
					async: false,
					beforeSend: function(data) {
						$(this).attr("disabled", true);
						index = layer.load();
					},
					success: function(data) {
						layer.close(index);
						$.message(data.status,data.message);
						$("#totalAmount").text(data.totalAmount);
					},error:function(data){
						$.message("error","系统出现问题，请联系系统管理员");
					}
				});
				$("#grid-table").trigger("reloadGrid");
				
				//window.location.href="repairin!confirms.action?id="+id+"&workingBillId="+workingBillId;			
			}
			
		});
		$("#undoCarton").click(function(){
			var workingBillId = $("#workingBillId").val();
			var id = "";
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			if(id==""){
				layer.msg("请选择一条记录!", {icon: 5});
			}else{
				var index = "";
				$.ajax({	
					url: "repairin!creditundo.action?id="+id+"&workingBillId="+workingBillId,
					//data: $(form).serialize(),
					dataType: "json",
					async: false,
					beforeSend: function(data) {
						$(this).attr("disabled", true);
						index = layer.load();
					},
					success: function(data) {
						layer.close(index);
						$.message(data.status,data.message);
						$("#totalAmount").text(data.totalAmount);
					},error:function(data){
						$.message("error","系统出现问题，请联系系统管理员");
					}
				});
				$("#grid-table").trigger("reloadGrid");
				
				//window.location.href="repairin!undo.action?id="+id+"&workingBillId="+workingBillId;			
			}
			
		});
		$("#returnCarton").click(function(){
			window.history.back();
		});
		
		$(".btn-colorpicker").click(function() {
			$(".dropdown-colorpicker").addClass("open");
		})

		var ishead2 = 0;
		$(".light-blue").click(function() {
			if (ishead2 == 0) {
				ishead2 = 1;
				$(this).addClass("open");
			} else {
				ishead2 = 0;
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