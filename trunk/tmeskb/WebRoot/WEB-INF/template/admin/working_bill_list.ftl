<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>SAP生产计划管理</title>
		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<#include "/WEB-INF/template/common/includelist.ftl"> <!--modify weitao-->
		<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/workingbill_list.js"></script>
        <script type="text/javascript" src="${base}/template/admin/js/SystemConfig/user/admin.js"></script>		
		<#include "/WEB-INF/template/common/include_adm_top.ftl">
	
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>		
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/user/admin.js"></script>
		<script src="${base}/template/admin/assets/js/jquery-ui.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
		<style>
			.operateBar{
				padding:3px 0px;
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
			<li class="active">生产计划管理</li>
		</ul><!-- /.breadcrumb -->
	</div>

	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by weitao  -->
									<form class="form-horizontal" id="searchform" action="working_bill!ajlist.action" role="form">
								<!--    <input type="hidden" id="aufnr" name="aufnr" value="${(workingBill.aufnr)!}"/>	
								   <input type="hidden" id="start" name="start" value=""/>	
								   <input type="hidden" id="end" name="end" value=""/>	--> 				
								  
								   <div class="operateBar">
								   	<div class="form-group">
										<label class="col-sm-2" style="text-align:right">随工单编号:</label>
										<div class="col-sm-4">
											<input type="text" name="workingBillCode" class="input input-sm form-control" value="" id="form-field-icon-1">
										</div>
										<label class="col-sm-1" style="text-align:right">生产日期:</label>
										<div class="col-sm-4">
											<div class="input-daterange input-group">
												<input type="text" class="input-sm form-control datePicker" name="start">
												<span class="input-group-addon">
													<i class="fa fa-exchange"></i>
												</span>

												<input type="text" class="input-sm form-control datePicker" name="end">
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2" style="text-align:right">工作中心:</label>
										<div class="col-sm-4">
											<input type="text" name="workCenter" class="input input-sm form-control" value="" id="form-field-icon-1">
										</div>
									</div>
										<div class="form-group" style="text-align:center">
											<a id="searchButton" class="btn btn-white btn-default btn-sm btn-round">
												<i class="ace-icon fa fa-filter blue"></i>
												搜索
											</a>
											<a id="syncButton"  class="btn btn-white btn-default btn-sm btn-round">
												<i class="ace-icon fa fa-filter blue"></i>
												SAP同步
											</a>
											
										</div>
										
									</div>
								</form>
<!-- ////////////////////////////////////////////////////////////////////////////////////////////////////// -->
	<!-- 				<div id="divbox" style="display: none;">
							  <div class="profile-user-info profile-user-info-striped">
							    <div class="profile-info-row">
								<div class="profile-info-value div-value">
										<label class="col-sm-3" style="text-align:right">订单号:</label>
										<div class="col-sm-6">
											<input type="text" name="workingBillCode" class="input input-sm form-control" value="" id="form-field-icon-1">
										</div>
									</div>
							    </div>
							
							   <div class="profile-info-row">
							    <div class="profile-info-value div-value">
										<label class="col-sm-3" style="text-align:right">生产日期:</label>
										<div class="col-sm-7">
											<div class="input-daterange input-group">
												<input type="text" class="input-sm form-control datePicker" name="start">
												<span class="input-group-addon">
													<i class="fa fa-exchange"></i>
												</span>
												<input type="text" class="input-sm form-control datePicker" name="end">
										</div>
									</div>
								</div>
						      </div>
							</div>
						</div> -->		
<!-- ////////////////////////////////////////////////////////////////////////////////////////////////////// -->
									<table id="grid-table"></table>
									<div id="grid-pager"></div>
								<!-- add by weitao -->	
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


</body>

</html>
<script>

	$(function() {
		var $syncButton = $("#syncButton");

		//同步按钮
		$syncButton.click(function() {
			btn_addbug_event();
			loading = new ol.loading({
				id : "page-content"
			});
			loading.show();		
			return false; 
		})
	})

	function btn_addbug_event() {
	/**	var $aufnr = $("#aufnr");
		var $start = $("#start");
		var $end = $("#end");  **/
		var title = "选择手工同步同步随工单";
		var width = "800px";
		var height = "400px";
		var content = "working_bill!browser.action";
		var html = "";
		jiuyi.admin.browser.dialog(title, width, height, content, function(
				index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe的对象
			var choose = iframeWin.getGridId();
			if (choose != "ERROR") {
				var id = choose.split(",");
				//var workcode =id[0];
				var workcode = id[0];
				var aufnr =id[1];
				var start = id[2];
				var end = id[3];
				layer.close(index);
				window.location.href = "working_bill!sync.action?workcode="+workcode+"&start="+start+"&end="+end+"&aufnr="+aufnr;
			}		
		});
	}
	/** layer.open({
	   type: 1,
	   shade:0.52,//遮罩透明度
	   title: "手工同步选择同步随工单",
	   area:["800px","200px"],//弹出层宽高
	   closeBtn: 1,//0没有关闭按钮，1-3不同样式关闭按钮---右上角的位置
	   shadeClose: false,//点击遮罩层(阴影部分)：true时点击遮罩就关闭，false时不会
	   btn:["确定","取消"],
	   yes:function(){layer.closeAll();},
	   content: $("#divbox")//可以 引入一个页面如："a.jsp"  
	}); **/
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