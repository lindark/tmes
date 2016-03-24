<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>仓位库存交接</title>
		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<#include "/WEB-INF/template/common/includelist.ftl"> <!--modify weitao-->
		<script type="text/javascript" src="${base}/template/admin/js/manage/locat_hoh_list.js"></script>
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
			<li class="active">仓位库存交接</li>
		</ul><!-- /.breadcrumb -->
	</div>
	
	
	<!-- add by welson 0728 -->
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content" id="page-content">					
					<div class="page-content-area">
							<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by weitao  -->
									<div>
									<button class="btn btn-white btn-default btn-sm btn-round" id="addHandOver" type=button>
										<i class="ace-icon fa fa-folder-open-o"></i>
										创建仓位库存交接
									</button>
									<button class="btn btn-white btn-default btn-sm btn-round" id="viewHandOver" type=button>
										<i class="ace-icon glyphicon glyphicon-zoom-in"></i>
										查看仓位库存交接
									</button>
									<button class="btn btn-white btn-default btn-sm btn-round" id="returnHandOver">
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
		
		$("#addHandOver").click(function(){
			var loginid = $("#loginid").val();//当前登录人的id
			window.location.href="locat_hand_over_header!add.action?loginid="+loginid;
		});
		
		
	/* 	$("#confirmHandOver").click(function(){
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			 if(id.length==0){
				layer.msg("请选择一条记录!", {icon: 5});
				return false;
			}	
				 else{
					 var rowData = $("#grid-table").jqGrid('getRowData',id);
						var row_state = rowData.state;
						if(row_state == "2" || row_state =="3"){
							layer.msg("已经确认或已经撤销的领料单无法再确认!",{icon:5});
							return false;
						}else{
							var loginId = $("#loginid").val();//当前登录人的id
							var url="return_product!creditapproval.action?id="+id+"&loginId="+loginId;
						}	
					
					credit.creditCard(url,function(data){
						$.message(data.status,data.message);
						$("#grid-table").trigger("reloadGrid");
				})
			 } 
		}); */
		
		$("#viewHandOver").click(function(){
			var id = "";
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			if(id.length>1){
				alert("只能选择一条成品入库记录！");
				return false;
			}if(id==""){
				alert("至少选择一条成品入库记录！");
				return false;
			}else{
				var loginId = $("#loginid").val();//当前登录人的id
				window.location.href="locat_hand_over_header!view.action?id="+id+"&loginId="+loginId;				
			}			
		});
		$("#returnHandOver").click(function(){
			window.history.back();
		});
	})
	
	
</script>
</html>
