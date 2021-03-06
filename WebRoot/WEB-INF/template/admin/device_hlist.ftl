<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>设备维修清单</title>
		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		
		<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/jiuyi/admin/css/style.css"/>
		<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/jiuyi/admin/css/setstyle.css"/>
		<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/ztree/css/zTreeStyle/zTreeStyle.css"/>
		<link rel="stylesheet" href="${base}/template/admin/assets/css/jquery-ui.min.css" />
		
		
		<#include "/WEB-INF/template/common/includelist.ftl"> <!--modify weitao-->
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>		
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/user/admin.js"></script>
		<script type="text/javascript"src="${base}/template/admin/js/unusual/js/device_hlist.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
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
<div class="main-container" id="main-container">
	<script type="text/javascript">
		try{ace.settings.check('main-container' , 'fixed')}catch(e){}
	</script>
	<div class="main-content">
	
	<!-- ./ add by welson 0728 -->

	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<form class="form-horizontal" id="searchform" action="device!ajlist.action" role="form">
								   	  <input type="hidden" name="abnorId" id="unusualId" value="${(abnormalId)!}" />
								   <div class="operateBar">
								   	<div class="form-group">
										
										<label class="col-sm-2" style="text-align:right">车间:</label>
										<div class="col-sm-4">
											<input type="text" name="workShopName1" class="input input-sm form-control" value="" id="form-field-icon-2">
										</div>
										<label class="col-sm-2" style="text-align:right">维修人:</label>
										<div class="col-sm-4">
											<input type="text" name="repairPerson" class="input input-sm form-control" value="" id="form-field-icon-2">
										</div>						
									</div>
									
										<div class="form-group" style="text-align:center">
											<a id="searchButton" class="btn btn-white btn-default btn-sm btn-round">
												<i class="ace-icon fa fa-filter blue"></i>
												搜索
											</a>										
										</div>
										
									</div>
								</form>
								<div class="row buttons col-md-8 col-sm-4">
							    <a id="return_device" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-book"></i>
									返回
								</a>
								</div>
								<!--  
								<div class="row">
								<div class="col-xs-12">
								<div class="row buttons col-md-8 col-sm-4">
								<a id="btn_edit" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon glyphicon glyphicon-edit"></i>
									编辑
								</a>
								<a id="btn_show" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-book"></i>
									查看
								</a>
								<a id="btn_back" class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-home"></i>
									返回
								</a>
							   </div>
							   </div>
							   </div>-->
								
								
								<div class="row">
								<div class="col-xs-12">
								
								<table id="grid-table"></table>

								<div id="grid-pager"></div>
								</div></div>
								
									
								<script type="text/javascript">
									var $path_base = "${base}/template/admin";//in Ace demo this will be used for editurl parameter
								</script>

								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content-area -->
				<!-- PAGE CONTENT ENDS -->

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

		$("#return_device").click(function(){
			window.history.back();
		});
	})
	
	
</script>