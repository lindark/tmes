<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>管理中心</title>
<meta name="description"
	content="Dynamic tables and grids using jqGrid plugin" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<#include "/WEB-INF/template/common/includelist.ftl">
<!--modify weitao-->
<script type="text/javascript"
	src="${base}/template/admin/js/manage/locat_hand_over_header_history.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/jqgrid_common.js"></script>
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
					<li class="active">仓位库存交接记录</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" id="searchform" action="locat_hand_over_header!historylist.action" role="form" method="post">
								<div class="operateBar">
									<div class="form-group">
										<label class="col-sm-1 col-md-offset-1" style="text-align:right">库存地点:</label>
										<div class="col-sm-4">											
												<input type="text" class="input input-sm form-control"
													name="locationCode"> 											
										</div>
										<label class="col-sm-1 col-md-offset-1"
											style="text-align:right">仓位:</label>
										<div class="col-sm-4">
											<input type="text" name="lgpla"
												class="input input-sm form-control" value=""
												id="form-field-icon-1">
										</div>								
									
									</div>
									<div class="form-group">	
										<label class="col-sm-1  col-md-offset-1" style="text-align:right">状态:</label>
										<div class="col-sm-4">
											<select name="state" id="form-field-icon-1" class="input input-sm form-control">											
								                <option value=""></option>
								                 <option value="1">未提交</option>
								                <option value="2">未确认</option>
								                <option value="3">已确认</option>
							               </select>
									    </div>									
										<label class="col-sm-1  col-md-offset-1" style="text-align:right">创建日期:</label>
										<div class="col-sm-4">
											<div class="input-daterange input-group">
												<input type="text" class="input-sm form-control datePicker"
													name="start"> <span class="input-group-addon">
													<i class="fa fa-exchange"></i>
												</span> <input type="text" class="input-sm form-control datePicker"
													name="end">
											</div>
										</div>										
									</div>									
									<div class="form-group" style="text-align:center">
										<a id="searchButton"
											class="btn btn-white btn-default btn-sm btn-round"> <i
											class="ace-icon fa fa-filter blue"></i> 搜索
										</a>
										<a  id="excelReport" class="btn btn-white btn-default btn-sm btn-round">
												<i class="ace-icon fa fa-filter blue"></i>Excel导出
										</a>
									</div>

								</div>
							</form>
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
<script type ="text/javascript">
   $(function(){
	   var $excelReport = $("#excelReport");
	   var $searchform = $("#searchform");
       
	   $excelReport.click(function(){
		   $searchform.attr("action","locat_hand_over_header!excelexport.action");
		   $searchform.submit();
	   });	   
	   
   })


</script>
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
		
		//查看
		$("#btn_show").click(function(){
			if(getId2()){
				var rowData = $("#grid-table").jqGrid('getRowData',id);
				var workingBillId=rowData.workingBillId;
				window.location.href="pollingtest!show.action?id="+id+"&workingBillId="+workingBillId;
			}
		});
		
		//得到1条id
		function getId2()
		{
			id=$("#grid-table").jqGrid('getGridParam','selarrrow');
			if(id.length==1)
			{
				return true;
			}
			else
			{
				layer.msg("请选择一条记录!", {icon: 5});
				return false;
			}
		}
		
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