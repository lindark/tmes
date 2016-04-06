<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>单元管理</title>
		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<#include "/WEB-INF/template/common/includelist.ftl"> 
		<!-- <script type="text/javascript" src="${base}/template/admin/js/BasicInfo/wk_moudlelist.js"></script> -->
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
		<#include "/WEB-INF/template/common/include_adm_top.ftl">
		<script type="text/javascript">
			$(document).keydown(function(event){
				if(event.keyCode==13)
				{
					$("#searchButton").click();
				}
			});
		</script>
	</head>
	<body class="no-skin list">
	
<!-- add by welson 0728 -->	
<div class="main-container" id="main-container">
	<script type="text/javascript">
		try{ace.settings.check('main-container' , 'fixed')}catch(e){}
	</script>
	<div class="main-content">
		<!-- add by welson 0728 -->
			<!-- /section:basics/content.breadcrumbs -->
			<div class="page-content">
				<div class="page-content-area">
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							
							
							<form class="form-horizontal" id="searchform" action="factory_unit!getgetfaunlist.action" role="form">
							<input type="hidden" value="${(matnr)!}" id="matnr">
							<input type="hidden" value="${(funid)!}" id="funid">
							<input type="hidden" value="${(id)!}" id="wbid">
							   <div class="operateBar">
							   <#if pager.list?size!=0>
							   <#list pager.list as list>
								  <div class="form-group"style="text-align:center">
								   <#if moudles?contains(list.station)>
								   <#assign m="t">
								   </#if>
								   	<input type="checkbox" class="wbmodules" name="wbmodules" value="${(list.station)!}"  <#if m=="t">checked</#if>>${(list.station)!}
								  	<#assign m="">
										 <!-- <label class="col-sm-1" style="text-align:right">单元编码</label>
										<div class="col-sm-4">
											<input type="text" name="factoryUnitCode" class="input input-sm form-control" value="" id="form-field-icon-1">
										</div>
										<label class="col-sm-1 col-md-offset-1" style="text-align:right">单元名称</label>
										<div class="col-sm-4">
											<input type="text" name="factoryUnitName" class="input input-sm form-control" value="" id="form-field-icon-1">
										</div> -->
									</div>	
									</#list>
								<#else>
								<div class="form-group"style="text-align:center">未找到相关模具组号
								</div>
								</#if>
									<!-- <div class="form-group" style="text-align:center">
										<a id="sureButton" class="btn btn-white btn-default btn-sm btn-round">
											<i class="ace-icon fa fa-filter blue"></i>
											确定
										</a>
									</div> -->
										
								</div>
							</form>
								
								
							<table id="grid-table"></table>

							<div id="grid-pager"></div>
							
							
								
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

		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div><!-- /.main-container -->
<!-- inline scripts related to this page -->
<script type="text/javascript">
/* function getName()
{
	var ids=$("#grid-table").jqGrid('getGridParam','selarrrow');
	if(ids.length <1)
	{
		layer.alert("请选择一个模具组号",false);
		return "baga";
	}
	else
	{
		var rowData = $("#grid-table").jqGrid('getRowData',ids);
		var info={"id":ids};
		return info;
	}
} */
function getName()
{
	var le=$("input[type='checkbox']:checked");
	/* if(le.length <1)
	{
		layer.alert("请选择一个模具组号",false);
		return "baga";
	}
	else
	{ */
		 var value="";
		   for (var i=0;i<le.length;i++ ){
		     if(le[i].checked){ //判断复选框是否选中
		    	if(value==""){
		    		value = le[i].value;
		    	}else{
		    		value=value+","+le[i].value;
		    	}
		    	 
		  	}
		   }
		return value;
	//}
}
$(document).keydown(function(event){
	if(event.keyCode==13)
	{
		$("#searchButton").click();
	}
});
</script>
</body>
</html>
