<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>权限对象管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<#include "/WEB-INF/template/common/include_adm_top.ftl">

<link rel="stylesheet" rev="stylesheet" type="text/css" media="all"
	href="${base}/template/admin/jiuyi/admin/css/style.css" />
<link rel="stylesheet" rev="stylesheet" type="text/css" media="all"
	href="${base}/template/admin/jiuyi/admin/css/setstyle.css" />
<link rel="stylesheet" rev="stylesheet" type="text/css" media="all"
	href="${base}/template/admin/ztree/css/zTreeStyle/zTreeStyle.css" />
<style>
body {
	background: #fff;
}
</style>
<script type="text/javascript">

var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			//beforeClick: beforeClick,
			//onClick: onClick
		}
	};

var zNodes =[
<#list list as department>
	{ id:"${(department.id)!}", pId:"${(department.parentDept.id)!}", name:"${(department.deptName)!}",costcenter:"${(department.costcenter)!}",movetype:"${(department.movetype)!}",movetype1:"${(department.movetype1)!}",deptcode:"${(department.deptCode)!}"},
</#list>

];


function beforeClick(treeId, treeNode, clickFlag) {
	return (treeNode.click != false);
}
function onClick(event, treeId, treeNode, clickFlag) {
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
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

								<ul id="ingageTree" class="ztree"></ul>

							<!-- add by welson 0728 -->
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

<script>
function getName(){
	var zTree = $.fn.zTree.getZTreeObj("ingageTree");
	var nodes = zTree.getSelectedNodes();
	var departid = nodes[0].id;
	var departName = nodes[0].name;
	var costcenter = nodes[0].costcenter;
	var movetype = nodes[0].movetype;
	var movetype1 = nodes[0].movetype1;
	var deptcode=nodes[0].deptcode;
	var depart = {
		"departid":departid,
		"departName":departName,
		"costcenter":costcenter,
		"movetype":movetype,
		"deptcode":deptcode,
		"movetype1":movetype1
	}
	return depart;
}
</script>
