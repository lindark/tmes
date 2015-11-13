	<!-- 
		<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/jiuyi/admin/css/style.css"/>
		<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/jiuyi/admin/css/setstyle.css"/>
		<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/ztree/css/zTreeStyle/zTreeStyle.css"/>
		
		
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/user/admin_list.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/depart/department.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/user/admin.js"></script>
 -->		
<#include "/WEB-INF/template/common/includelist.ftl">
		
<script>
	$(function(){
		alert("?");
	
	})
</script>
		
		

<style type="text/css">
	.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}

</style>

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by weitao  -->
									 <div class="right_content">
									 	 <div class="left_tree left_tree_position " id="left_tree">
									        <div class="tree_top">
									            <h5><span>部门列表</span></h5>
									        </div>
									        <div id="treeDiv">
									            <ul id="ingageTree1" class="ztree"></ul>
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
							                <a href="javascript:void(0)" title="编辑用户" id="userEditBtn" class="green_button">编辑用户</a>
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
								
			
								<!-- add by weitao -->	
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content-area -->
				<!-- PAGE CONTENT ENDS -->

	</div><!-- /.page-content -->
	<!-- 
	<script type="text/javascript" src="${base}/template/admin/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${base}/template/admin/ztree/js/jquery.ztree.exedit-3.5.min.js"></script>
	<script type="text/javascript" src="${base}/template/admin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
	 -->
