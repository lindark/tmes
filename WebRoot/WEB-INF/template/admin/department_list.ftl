<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>部门用户管理</title>
		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		
		<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/jiuyi/admin/css/style.css"/>
		<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/jiuyi/admin/css/setstyle.css"/>
		<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/ztree/css/zTreeStyle/zTreeStyle.css"/>
		<link rel="stylesheet" href="${base}/template/admin/assets/css/jquery-ui.min.css" />
		
		
		<#include "/WEB-INF/template/common/includelist.ftl"> <!--modify weitao-->
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/admin_list.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/depart/department.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/depart/departinput.js"></script>
		<script src="${base}/template/admin/assets/js/jquery-ui.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
		
		
		<#include "/WEB-INF/template/common/include_adm_top.ftl">

		
<script>
	$(function(){
		
		var zNodes =[
	     		<#list list as department>
	     			{ id:"${department.id}", pId:"${department.parentDept}", name:"${department.deptName}"},
	     		</#list>
	     	];

			jiuyi.admin.depart.initTree(zNodes);
	         
	         
	         $("#ingageTree a:first").trigger("click");
	})
</script>
		
		

<style type="text/css">
	.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}

</style>
	</head>
	<body class="no-skin">
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
			<li class="active">部门用户管理</li>
		</ul><!-- /.breadcrumb -->
	</div>
	
	 <div class="right_content">
	 	 <div class="left_tree left_tree_position hidden-480" id="left_tree">
	        <div class="tree_top">
	            <h5><span>部门列表</span></h5>
	        </div>
	        <div id="treeDiv">
	            <ul id="ingageTree" class="ztree"></ul>
	        </div>
	        <div class="tree_bottom">
	           <!-- <a href="javascript:void(0);" title="部门合并" class="group_merge" id="merge_a">部门合并</a> -->
	        </div>
    	</div><!--left_tree-->
    		<div class="right_grid" id="right_grid">
    		<a href="javascript:void(0);" class="tool_bar arrow_left" id="toggleMenuBar"></a>
    			<div class="top_tool">
                <input type="hidden" id="departId_hidden">
                <a href="javascript:void(0)" title="新建用户" id="userAddBtn" class="green_button">新建用户</a>
                <a href="javascript:void(0)" title="删除用户" id="userDeleteBtn" class="green_button">删除用户</a>
            	</div>
	            <div class="grid_style_01">
	                <div class="list_area">
		                <table id="grid-table"></table>
						<div id="grid-pager"></div>
	                
	                </div>
	                <div id="listGridPager"></div>
	            </div>
	    		
    		</div>
    	
	 </div><!--right_content-->
	
	
</div>


<!--弹出层 weitao add-->
	<div class="dialog-message hide" id="dialog-message">
	</div>

<!--弹出层weitao end-->
</body>
	</script><script type="text/javascript" src="${base}/template/admin/ztree/js/jquery.ztree.core-3.5.min.js">
	</script><script type="text/javascript" src="${base}/template/admin/ztree/js/jquery.ztree.exedit-3.5.min.js">
	</script><script type="text/javascript" src="${base}/template/admin/ztree/js/jquery.ztree.excheck-3.5.min.js">

</html>
