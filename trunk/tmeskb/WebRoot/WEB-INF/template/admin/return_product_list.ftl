<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>中转仓管理</title>
		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<#include "/WEB-INF/template/common/includelist.ftl"> <!--modify weitao-->
		<script type="text/javascript" src="${base}/template/admin/js/manage/return_product_list.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
		<#include "/WEB-INF/template/common/include_adm_top.ftl">
		
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
			<li class="active">中转仓管理</li>
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
									<button class="btn btn-white btn-default btn-sm btn-round" id="addReturnProduct" type=button>
										<i class="ace-icon fa fa-folder-open-o"></i>
										创建中转仓
									</button>
									<button class="btn btn-white btn-default btn-sm btn-round" id="editReturnProduct" type=button>
									    <i class="ace-icon glyphicon glyphicon-edit"></i>
									      编辑中转仓单
								    </button>  							
									<button class="btn btn-white btn-default btn-sm btn-round" id="viewReturnProduct" type=button>
										<i class="ace-icon glyphicon glyphicon-zoom-in"></i>
										查看中转仓单
									</button>
									<button class="btn btn-white btn-default btn-sm btn-round" id="confirmReturnProduct" type=button>
										<i class="ace-icon fa fa-cloud-upload"></i>
										刷卡确认
									</button>
									<!-- <button class="btn btn-white btn-default btn-sm btn-round" id="repealEndProduct" type=button>
										<i class="ace-icon glyphicon glyphicon-remove"></i>
										刷卡撤销
									</button> -->
									<button class="btn btn-white btn-default btn-sm btn-round" id="returnReturnProduct">
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
		
		$("#addReturnProduct").click(function(){
			window.location.href="return_product!add.action";
		});
		
		
		$("#confirmReturnProduct").click(function(){
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			 if(id.length==0){
				layer.msg("请选择一条记录!", {icon: 5});
				return false;
			}	
				 else{
					var url="return_product!creditApproval.action?id="+id;
					credit.creditCard(url,function(data){
						$.message(data.status,data.message);
						$("#grid-table").trigger("reloadGrid");
				})
			 } 
		});
		
		/* $("#repealPick").click(function(){
			var id = "";
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			if(id==""){
				layer.msg("请选择一条记录!", {icon: 5});
				return false;
			}
			else{
				var url="pick!creditundo.action";
				credit.creditCard(url,function(data){
					$.message(data.status,data.message);
					$("#grid-table").trigger("reloadGrid");
				});
			}
		}); */

		$("#editReturnProduct").click(function(){
			var id = "";
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			if(id==""){
				alert("请选择至少一条记录");
				return false;
			}
			if(id.length>1){
				alert("只能选择一条记录");
				return false;
			}
			else{
				var rowData = $("#grid-table").jqGrid('getRowData',id);
				var row_state = rowData.state;
				if(row_state == "2" || row_state =="3"){
					layer.msg("已经确认或已经撤销的领料单无法再编辑!",{icon:5});
					return false;
				}else{
					window.location.href="return_product!edit.action?id="+id;
				}				
			}		
		});
		
		
		$("#viewReturnProduct").click(function(){
			var id = "";
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			
			if(id.length>1){
				alert("只能选择一条成品入库记录！");
				return false;
			}if(id==""){
				alert("至少选择一条成品入库记录！");
				return false;
			}else{
				window.location.href="end_product!view.action?id="+id;				
			}			
		});
		
		
		
		$("#returnRetrunProduct").click(function(){
			window.history.back();
		});
	})
	
	
</script>
</html>
