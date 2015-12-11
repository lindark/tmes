<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>人员信息- Powered By
	${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/includelist.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
	<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/adminBrowser.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<link rel="stylesheet" rev="stylesheet" type="text/css" media="all"	href="${base}/template/admin/ztree/css/zTreeStyle/zTreeStyle.css" />
<#include "/WEB-INF/template/common/include_adm_top.ftl">

<style>
body {
	background: #fff;
}
.operateBar{
			padding:3px 0px;
		}
</style>
<script>
	$(function(){
		var $searchButton = $("#searchButton");//搜索
		var $searchform = $("#searchform");
		$searchButton.click(function(){
			var rules = "";
			var ishead= 0;
			$searchform.find(":input").each(function(i){
				if($(this).val()){
					if(ishead==1)
						rules +=",";
					rules += '"' + $(this).attr("name") + '":"' + $(this).val() + '"';
					ishead=1;
				}
				
			});
			ParamJson = '{' + rules + '}';
			var postData = $("#grid-table2").jqGrid("getGridParam", "postData");
	        $.extend(postData, { Param: ParamJson });
	        $("#grid-table2").jqGrid("setGridParam", { search: true }).trigger("reloadGrid", [{ page: 1}]);  //重载JQGrid
		
			return false;
		});
	})
</script>

<script type="text/javascript">

var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			//beforeClick: beforeClick,
			onClick: onClick
		}
	};

var zNodes =[
<#list list as department>
	{ id:"${department.id}", pId:"${(department.parentDept.id)!}", name:"${department.deptName}"},
</#list>

];


function beforeClick(treeId, treeNode, clickFlag) {
	return (treeNode.click != false);
}
function onClick(event, treeId, treeNode, clickFlag) {
	$("#grid-table2").jqGrid('setGridParam',{
		url:"admin!ajlist1.action?departid="+treeNode.id,
		datatype:"json",
		page:1
	}).trigger("reloadGrid");
	
}		

	
	$(function(){
		$.fn.zTree.init($("#ingageTree"), setting, zNodes);
	})
	
	
</script>

</head>
<body class="no-skin input">

	<!-- add by welson 0728 -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>
		<div class="main-content">




			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-3 col-md-3 col-sm-3">
							<div style="border-bottom: #c3daf9 1px solid; border-left: #c3daf9 1px solid;height: 350px; overflow: auto; border-top: #c3daf9 1px solid; border-right: #c3daf9 1px solid;">
								<ul id="ingageTree" class="ztree"></ul>
							</div>
						</div>
						<div class="col-xs-9 col-md-9 col-sm-9 ceshi">
							<form class="form-horizontal" id="searchform"
								action="admin!ajlist1.action" role="form">
								<div class="operateBar">
									姓名:<input class="input input-sm" name="adminName" type="text"/>
									<button id="searchButton" class="btn btn-white btn-default btn-sm btn-round">
												<i class="ace-icon fa fa-filter blue"></i>
												搜索
											</button>
								</div>
								
							</form>
								<table id="grid-table2"></table>
								<div id="grid-pager"></div>
							</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content-area -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->

</body>
<script type="text/javascript"
	src="${base}/template/admin/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/ztree/js/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
</html>
<script type="text/javascript">
	function getGridId(){
		var ids=$("#grid-table2").jqGrid('getGridParam','selarrrow');
		if(ids.length <1){
			alert("请至少选择一条记录");
			return false;
		}
		var arrayObj = new Array();
		for(var i=0;i<ids.length;i++){
			var rowData = $("#grid-table2").jqGrid('getRowData',ids[i]);//获取每一行的对象
			arrayObj[i] = rowData;
			
		}
		return arrayObj;
	}
	

	
	
	
</script>