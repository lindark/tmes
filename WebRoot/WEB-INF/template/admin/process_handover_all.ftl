<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>总体工序交接</title>
		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<#include "/WEB-INF/template/common/includelist.ftl"> <!--modify weitao-->
		<script type="text/javascript" src="${base}/template/admin/js/manage/process_handover_all.js"></script>
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
			<li class="active">总体工序交接</li>
		</ul><!-- /.breadcrumb -->
	</div>
	
	
	<!-- add by welson 0728 -->
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content" id="page-content">	
				<div class="widget-header">
							<h4 class="widget-title lighter">班组信息</h4>
							<div class="widget-toolbar no-border">
								<a href="#" data-action="collapse"> <i
									class="ace-icon fa fa-chevron-up"></i> </a>

							</div>
							
							
						</div>
						<div class="widget-body">
											<div
												class="widget-main padding-6 no-padding-left no-padding-right">
												<div class="profile-user-info profile-user-info-striped">
												   <div class="profile-info-row">
														<div class="profile-info-name">工厂/车间：</div>
														<div class="profile-info-value">
														<input type="hidden" name="processHandoverTop.id" value="${(processHandoverTop.id)!}">
														<input type="hidden" name="processHandoverTop.werk" value="${(admin.team.factoryUnit.workShop.factory.factoryCode)!}">
														<input type="hidden" name="processHandoverTop.wshop" value="${(admin.team.factoryUnit.workShop.workShopCode)!}">
															${(admin.team.factoryUnit.workShop.factory.factoryName)!} &nbsp;&nbsp;&nbsp;    
															${(admin.team.factoryUnit.workShop.workShopName)!}</div>
													</div>

													<div class="profile-info-row">
														<div class="profile-info-name">单元：</div>
														<div class="profile-info-value" >
														<input type="hidden" name="processHandoverTop.factoryUnitCode" value="${(admin.team.factoryUnit.factoryUnitCode)! }">
														<input type="hidden" name="processHandoverTop.factoryUnitName" value="${(admin.team.factoryUnit.factoryUnitName)! }">
														${(admin.team.factoryUnit.factoryUnitName)! }
														</div>										
													</div>
													
													<div class="profile-info-row">
													
													    <div class="profile-info-name">生产日期/班次:</div>
														<div class="profile-info-value">
														<input type="hidden" id="productDate" name="processHandoverTop.productDate" value="${admin.productDate}">
														<input type="hidden" id="shift" name="processHandoverTop.shift" value="${(admin.shift)! }">
														
								       								 ${admin.productDate} &nbsp;&nbsp;&nbsp; 
								       								 <#if (admin.shift == 1)!>
								       								 早 
								       								 <#elseif (admin.shift == 2)!> 
								       								 	白
								       								 <#elseif (admin.shift == 3)!>
								       								 	晚
								       								 <#else>
								       								 </#if>
														</div>
													</div>
												</div>
											</div>
										</div>				
					<div class="page-content-area">
							<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by weitao  -->
									<div>
									<button class="btn btn-white btn-default btn-sm btn-round" id="btn_add" type=button>
										<i class="ace-icon fa fa-folder-open-o"></i>
										刷卡提交
									</button>	
									<button class="btn btn-white btn-default btn-sm btn-round" id="btn_save" type=button>
										<i class="ace-icon fa fa-cloud-upload"></i>
										刷卡确认
									</button>
									<!--  
									<button class="btn btn-white btn-default btn-sm btn-round" id="btn_confirm" type=button>
										<i class="ace-icon glyphicon glyphicon-remove"></i>	
										刷卡撤销
									</button>  -->
									<button class="btn btn-white btn-default btn-sm btn-round" id="btn_back">
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
<script type ="text/javascript">
	
	
   $(function(){
	
	   /*创建工序交接*/
		$("#btn_add").click(function(){
			alert("1");
			var loginid = $("#loginid").val();
		//	window.location.href="process_handover!saveAllProcess.action?loginid="+loginid;
		/*
			$.ajax({	
				url: "process_handover!saveAllProcess.action?",
				data:{"loginid":loginid},
				dataType: "json",
				async: false,
				success: function(data) {
					layer.msg("您的操作已成功!", {icon: 6});
					setTimeout(function(){
						window.location.href="process_handover!allList.action";
					},2000);			
				},error:function(data){
					layer.msg("发生异常!",{icon:5});
				}
			});	*/
			var loginid = $("#loginid").val();
			var url="process_handover!saveAllProcess.action?loginid="+loginid;
			credit.creditCard(url,function(data){
				if(data.status=="success"){
					layer.alert(data.message, {icon: 6},function(){
						window.location.href="process_handover!allList.action";
					}); 
				}else if(data.status=="error"){
					layer.alert(data.message, {
				        closeBtn: 0,
				        icon:5,
				        skin:'error'
				   });
				}		
			})
			
		});
	   
	   $("#btn_save").click(function(){
			//	var loginid = $("#loginid").val();
				//var $save = $(this);
		//		var url = "process_handover!allSubmit.action";
				var id = "";
				id=$("#grid-table").jqGrid('getGridParam','selarrrow');
				if(id==""){
					layer.msg("请选择一条记录!", {icon: 5});
					return false;
				}else{
					var rowData = $("#grid-table").jqGrid('getRowData',id);
					var row_state = rowData.state;
					if(row_state == "2" || row_state =="3"){
						layer.msg("已经确认或已撤销的记录无法再确认!",{icon:5});
						return false;
					}else{
					var url="process_handover!allSubmit.action?id="+id;
					credit.creditCard(url,function(data){
						$.message(data.status,data.message);
						$("#grid-table").trigger("reloadGrid");
					})
				}
			}
	 });
	   $("#btn_confirm").click(function(){
	//		var loginid = $("#loginid").val();
	//		var url = "process_handover!allapproval.action?loginid="+loginid;
		    var id = "";
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			if(id==""){
				layer.msg("请选择一条记录!", {icon: 5});
				return false;
			}else{
				var rowData = $("#grid-table").jqGrid('getRowData',id);
				var row_state = rowData.state;
				if(row_state == "2" || row_state =="3"){
					layer.msg("已经确认或已撤销的记录无法再撤销!",{icon:5});
					return false;
				}else{
				var url="process_handover!allapproval.action?id="+id;
				credit.creditCard(url,function(data){
					$.message(data.status,data.message);
					$("#grid-table").trigger("reloadGrid");
				})
			}
		}
});


		 /*返回*/
		$("#btn_back").click(function(){
			window.history.back();return false;
		});
   });
</script>
</body>
</html>
