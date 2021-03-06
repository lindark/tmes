<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>人员管理</title>
<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/jiuyi/admin/css/style.css"/>
<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/jiuyi/admin/css/setstyle.css"/>
<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/ztree/css/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" href="${base}/template/admin/assets/css/jquery-ui.min.css" />
<#include "/WEB-INF/template/common/includelist.ftl"> 
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/admin_alllistry.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<script src="${base}/template/admin/assets/js/jquery-ui.min.js"></script>
<script src="${base}/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${base}/template/admin/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${base}/template/admin/ztree/js/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${base}/template/admin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>

<#include "/WEB-INF/template/common/include_adm_top.ftl">
<style type="text/css">
	.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	.formspace{margin-left: 25px;font-family: 微软雅黑;font-size: 14px;}
	.div_btn{text-align: center;}
	.div_group{display: block;}
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
	

     <div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
		</script>

		<ul class="breadcrumb">
			<li>
				<i class="ace-icon fa fa-home home-icon"></i>
				<a href="admin!index.action">管理中心</a>
			</li>
			<li class="active">员工管理</li>
		</ul>
	</div>
		<!-- add by welson 0728 -->
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="page-content-area">
						<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by weitao  -->
								 <div class="right_content">
								 	 <div class="left_tree left_tree_position " id="left_tree">
								        <div class="tree_top">
								            <h5><span>人员列表</span></h5>
								        </div>
								        <div id="treeDiv" style="height:100%;max-height:100%;">
								             <ul id="ingageTree" class="ztree"></ul>
								        </div>
								        <div class="tree_bottom">
								          
								        </div>
							    	</div><!--left_tree-->
						    		<div class="right_grid" id="right_grid">
						    		<a href="javascript:void(0);" class="tool_bar arrow_left" id="toggleMenuBar"></a>
						                <input type="hidden" id="departId_hidden">
						                <form class="form-horizontal formspace" id="searchform" action="" role="form">
											<input type="hidden" id="loginid" name="loginid" value="<@sec.authentication property='principal.id' />" />
											<div class="operateBar">
												<div class="form-group div_group">
													<div class="col-xs-12 col-sm-6 col-md-3">
														部门：<input type="text" id="input_dept" name="dept" value="" >
													</div>
													<div class="col-xs-12 col-sm-6 col-md-3">
														工号：<input type="text" id="input_worknum" name="workNumber" value="" >
													</div>
													<div class="col-xs-12 col-sm-6 col-md-3">
														姓名：<input type="text" id="input_name" name="name" value="" >
													</div>
													<div class="col-xs-12 col-sm-6 col-md-3">
														是否离职：
														<select id="sel_islizhi" name="islizhi">
															<option value="baga">&nbsp;</option>
															<option value="N">在职</option>
															<option value="Y">离职</option>
														</select>
													</div>
												</div>
												<div class="div_btn">
													<a id="searchButton" class="btn btn-white btn-default btn-sm btn-round">
														<i class="ace-icon fa fa-filter blue"></i> 搜索
													</a>
													&nbsp;&nbsp;
													<button id="btn_outexcel" type="button" class="btn btn-white btn-default btn-sm btn-round">
														<i class="ace-icon fa fa-cloud-upload"></i>
																导出Excel
													</button>
												</div>
											</div>
										</form>
							            <div class="grid_style_01">
							                <div class="list_area">
								                <table id="grid-table"></table>
												<div id="grid-pager"></div>
							                
							                </div>
							                <div id="listGridPager"></div>
							            </div>
							    		
						    		</div>
						    	
							 </div><!--right_content-->
							<!-- add by weitao -->	
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
</body>
<script type="text/javascript">
$(function(){
	var zNodes =[
 		 	<#list list_department as department>
 		 		{ id:"${department.id}", pId:"${(department.parentDept.id)!}", name:"${department.deptName}"},
 		 	</#list>
	 	];
 	jiuyi.admin.depart.initTree(zNodes);
	<#if deptid??>var url="admin!ajlistemp.action?deptid="+"${(deptid)!}";tostartpage(url);
	<#else>var url="admin!ajlistemp.action";tostartpage(url);</#if>
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
	});
	
	var ishead2=0;
	$(".light-blue").click(function(){
		if(ishead2==0){
			ishead2=1;
			$(this).addClass("open");
		}else{
			ishead2=0;
			$(this).removeClass("open");
		}
		
	});
	
	
	var cookie = $.cookie("z_tree");  
	var cookie1 = $.cookie("z_tree_sel");
    if(cookie){  
        var z_tree = JSON.parse(cookie);  
        var zTree = $.fn.zTree.getZTreeObj("ingageTree");  
        for(var i=0; i< z_tree.length; i++){  
            var node = zTree.getNodeByParam('id', z_tree[i])  
            zTree.expandNode(node, true)  
        }  
    }  
    /*
    if(cookie1){
    	var z_tree = JSON.parse(cookie1);
    	var zTree = $.fn.zTree.getZTreeObj("ingageTree");  
    	for(var i=0;i<z_tree.length;i++){
    		zTree.selectNode(z_tree[i]);
    	}
    }
    */
	
});
$(document).keydown(function(event){
	if(event.keyCode==13)
	{
		$("#searchButton").click();
	}
});
</script>
</html>
